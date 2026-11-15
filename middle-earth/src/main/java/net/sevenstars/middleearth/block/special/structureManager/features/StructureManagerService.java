package net.sevenstars.middleearth.block.special.structureManager.features;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registry;
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
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerDataLookup;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;

import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import java.util.List;
import java.util.Optional;
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

    // A nest is named for its place, and no two places share one
    public static @Nullable SpawnNestNodeData findNest(World world, Identifier nestId) {
        Registry<StructureManagerData> places = world.getRegistryManager().getOptional(DynamicRegistriesME.STRUCTURE_MANAGER_DATA).orElse(null);
        if(places == null)
            return null;
        for(StructureManagerData place : places) {
            SpawnNestNodeData nest = place.findSpawnNest(nestId);
            if(nest != null)
                return nest;
        }
        return null;
    }

    // The nest a bed's people are of: of its own place, or without one of the place its manager was given
    public static @Nullable SpawnNestNodeData nestFor(World world, BedTag tag, Optional<Identifier> managerPlace) {
        Identifier place = tag.place().or(() -> managerPlace).orElse(null);
        StructureManagerData data = place == null ? null : getStructureManagerData(world, place);
        return data == null ? null : data.findPeopleNest(tag.nestKind());
    }

    // A spawn marker names a nest whole, or as those placed before did only its kind, which is then of the manager's place
    public static @Nullable SpawnNestNodeData nestForMarker(World world, String value, Optional<Identifier> managerPlace) {
        if(value.indexOf(Identifier.NAMESPACE_SEPARATOR) >= 0) {
            Identifier nestId = Identifier.tryParse(value);
            SpawnNestNodeData nest = nestId == null ? null : findNest(world, nestId);
            return nest != null && nest.isPeople() ? nest : null;
        }
        StructureManagerData data = managerPlace.map(place -> getStructureManagerData(world, place)).orElse(null);
        return data == null ? null : data.findPeopleNest(value);
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

    // Whose bed a tagged bed is: the manager its builder tied it to, or else the nearest of the structure's managers that has
    // a nest of its kind. Told from what the templates of the structure hold, so that it is the same answer whichever of the
    // managers are loaded, and one bed is never born by two
    public static @Nullable BlockPos ownerOf(World world, StructureFeatureData.Bed bed, List<StructureFeatureData.Manager> managers) {
        if(bed.tag().isEmpty())
            return null;
        StructureFeatureData.Manager owner = null;
        for(StructureFeatureData.Manager manager : managers) {
            if(nestFor(world, bed.tag().get(), manager.data()) == null)
                continue;
            if(bed.manager().isPresent() && bed.manager().get().equals(manager.pos()))
                return manager.pos();
            if(owner == null || isNearer(manager.pos(), owner.pos(), bed.pos()))
                owner = manager;
        }
        return owner == null ? null : owner.pos();
    }

    private static boolean isNearer(BlockPos manager, BlockPos other, BlockPos bed) {
        double distance = manager.getSquaredDistance(bed);
        double otherDistance = other.getSquaredDistance(bed);
        return distance < otherDistance || distance == otherDistance && manager.compareTo(other) < 0;
    }

    // For a place whose templates say nothing of its managers: the nearest of those that are loaded
    public static boolean isNearestBearer(ServerWorld world, BlockPos bed, BedTag tag, RoutineSite site, StructureManagerBlockEntity asking) {
        Set<BlockPos> managers = STRUCTURE_MANAGERS.get(world.getRegistryKey());
        if (managers == null)
            return false;
        for (BlockPos managerPos : managers) {
            if (managerPos.equals(asking.getPos()) || !world.isChunkLoaded(managerPos.getX() >> 4, managerPos.getZ() >> 4))
                continue;
            if (!(world.getBlockEntity(managerPos) instanceof StructureManagerBlockEntity manager) || !manager.canBear(tag) || RoutineSites.forManager(world, managerPos) != site)
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
}
