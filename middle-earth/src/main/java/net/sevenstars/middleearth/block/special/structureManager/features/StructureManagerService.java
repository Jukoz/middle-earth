package net.sevenstars.middleearth.block.special.structureManager.features;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.entity.EntitiesME;
import org.jetbrains.annotations.Nullable;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.initializer.NpcEntityBuilder;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerDataLookup;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StructureManagerService {
    private static final int SPAWN_ATTEMPTS = 12;
    private static final Map<RegistryKey<World>, Set<BlockPos>> STRUCTURE_MANAGERS = new HashMap<>();

    // Managers are only taken off the list as their chunks unload, which a stopping server does not wait for
    public static void register() {
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> STRUCTURE_MANAGERS.clear());
    }

    public static StructureManagerData getStructureManagerData(World world, Identifier structureManagerDataId){
        if(world == null)
            return null;
        var data =  StructureManagerDataLookup.getStructureManagerData(world, structureManagerDataId);
        return data.orElse(null);
    }

    public static LivingEntity spawnEntity(ServerWorld world, StructureSpawnNestPool pool, BlockPos pos, int spawnRadius){
        var random = world.getRandom();
        BlockPos chosenBlockPos = null;
        for(BlockPos blockPos : BlockPos.iterateRandomly(random, SPAWN_ATTEMPTS, pos, spawnRadius)){
            blockPos = blockPos.withY(pos.getY());
            // Air alone is also what the mouth of a well is made of
            BlockPos below = blockPos.down();
            if(world.getBlockState(blockPos).isAir() && world.getBlockState(blockPos.up()).isAir()
                    && world.getBlockState(below).isSideSolidFullSquare(world, below, Direction.UP)){
                chosenBlockPos = blockPos;
                break;
            }
        }
        if(chosenBlockPos == null)
            chosenBlockPos = pos;
        return spawnEntityAt(world, pool, chosenBlockPos);
    }

    public static @Nullable LivingEntity spawnNpcAt(ServerWorld world, Identifier npcClass, BlockPos pos) {
        NpcEntity npc = new NpcEntityBuilder(world, pos).withNpcClass(npcClass).build();
        if(npc == null)
            return null;
        world.spawnEntity(npc);
        return npc;
    }

    public static LivingEntity spawnEntityAt(ServerWorld world, StructureSpawnNestPool pool, BlockPos chosenBlockPos){
        LivingEntity entity;
        if(pool.getEntityType() == EntitiesME.NPC){
            if(pool.getNpcIdentifier().isEmpty())
                return null;
            entity = new NpcEntityBuilder(world, chosenBlockPos)
                    .withNpcClass(pool.getNpcIdentifier().get())
                    .build();
        } else {
            if(!(pool.getEntityType().create(world, SpawnReason.STRUCTURE) instanceof LivingEntity created))
                return null;
            entity = created;
            if(entity instanceof MobEntity mob)
                mob.initialize(world, world.getLocalDifficulty(chosenBlockPos), SpawnReason.STRUCTURE, null);
            entity.setPosition(chosenBlockPos.toCenterPos());
        }
        if(entity == null)
            return null;
        world.spawnEntity(entity);
        return entity;
    }

    public static void register(StructureManagerBlockEntity manager) {
        World world = manager.getWorld();
        if (world == null || world.isClient())
            return;
        STRUCTURE_MANAGERS.computeIfAbsent(world.getRegistryKey(), key -> new HashSet<>())
                          .add(manager.getPos().toImmutable());
    }

    public static void unregister(StructureManagerBlockEntity manager) {
        World world = manager.getWorld();
        if (world == null || world.isClient())
            return;
        Set<BlockPos> managers = STRUCTURE_MANAGERS.get(world.getRegistryKey());
        if (managers == null)
            return;

        managers.remove(manager.getPos());
        if (managers.isEmpty())
            STRUCTURE_MANAGERS.remove(world.getRegistryKey());
    }

    // A structure can have several managers, and a bed is born by the nearest of them that has a nest for its kind
    public static boolean isNearestBearer(ServerWorld world, BlockPos bed, String nestKind, RoutineSite site, StructureManagerBlockEntity asking) {
        Set<BlockPos> managers = STRUCTURE_MANAGERS.get(world.getRegistryKey());
        if (managers == null)
            return false;
        for (BlockPos managerPos : managers) {
            if (managerPos.equals(asking.getPos()) || !world.isChunkLoaded(managerPos.getX() >> 4, managerPos.getZ() >> 4))
                continue;
            if (!(world.getBlockEntity(managerPos) instanceof StructureManagerBlockEntity manager) || !manager.canBear(nestKind) || RoutineSites.forManager(world, managerPos) != site)
                continue;
            if (manager.ownsBed(bed))
                return false;
            double distance = managerPos.getSquaredDistance(bed);
            double own = asking.getPos().getSquaredDistance(bed);
            if (distance < own || distance == own && managerPos.compareTo(asking.getPos()) < 0)
                return false;
        }
        return true;
    }

    public static boolean isClose(World world, BlockPos pos, int radius) {
        Set<BlockPos> managers = STRUCTURE_MANAGERS.get(world.getRegistryKey());

        if (managers == null) {
            return false;
        }

        int radiusSquared = radius * radius;

        for (BlockPos managerPos : managers) {
            if (managerPos.getSquaredDistance(pos) <= radiusSquared) {
                return true;
            }
        }

        return false;
    }

    public static StructureManagerBlockEntity getClosest(World world, BlockPos pos, int radius) {
        Set<BlockPos> managers = STRUCTURE_MANAGERS.get(world.getRegistryKey());

        if (managers == null) {
            return null;
        }

        int radiusSquared = radius * radius;
        double closestDistance = Double.MAX_VALUE;
        StructureManagerBlockEntity closest = null;

        for (BlockPos managerPos : managers) {
            double distance = managerPos.getSquaredDistance(pos);

            if (distance > radiusSquared || distance >= closestDistance) {
                continue;
            }

            BlockEntity blockEntity = world.getBlockEntity(managerPos);

            if (blockEntity instanceof StructureManagerBlockEntity structureManager) {
                closestDistance = distance;
                closest = structureManager;
            }
        }

        return closest;
    }
}
