package net.sevenstars.middleearth.block.special.structureManager.features;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.initializer.NpcEntityBuilder;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerDataLookup;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StructureManagerService {
    private static final Map<ResourceKey<Level>, Set<BlockPos>> STRUCTURE_MANAGERS = new HashMap<>();

    public static StructureManagerData getStructureManagerData(Level world, ResourceLocation structureManagerDataId){
        if(world == null)
            return null;
        var data =  StructureManagerDataLookup.getStructureManagerData(world, structureManagerDataId);
        return data.orElse(null);
    }

    public static LivingEntity spawnEntity(ServerLevel world, StructureSpawnNestPool pool, BlockPos pos, int spawnRadius){
        var random = world.getRandom();
        int chances = 5;
        BlockPos chosenBlockPos = null;
        for(BlockPos blockPos : BlockPos.randomInCube(random, chances, pos, spawnRadius)){
            blockPos = blockPos.atY(pos.getY());
            if(world.getBlockState(blockPos).isAir() && world.getBlockState(blockPos.above()).isAir()){
                chosenBlockPos = blockPos;
                break;
            }
        }
        if(chosenBlockPos == null)
            chosenBlockPos = pos;

        Entity entity;
        if(pool.getEntityType() == EntitiesME.NPC){

            entity = new NpcEntityBuilder(world, chosenBlockPos)
                    .withNpcType(pool.getNpcIdentifier().get())
                    .build();
        } else {
            entity = pool.getEntityType().create(world);
            if(entity instanceof Mob mob)
                mob.finalizeSpawn(world, world.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null);
            entity.setPos(chosenBlockPos.getCenter());
        }

        if(entity instanceof LivingEntity livEntity){
            world.addFreshEntity(entity);
            return livEntity;
        }
        return null;
    }

    public static void register(StructureManagerBlockEntity manager) {
        Level world = manager.getLevel();
        if (world == null || world.isClientSide())
            return;
        STRUCTURE_MANAGERS.computeIfAbsent(world.dimension(), key -> new HashSet<>())
                          .add(manager.getBlockPos().immutable());
    }

    public static void unregister(StructureManagerBlockEntity manager) {
        Level world = manager.getLevel();
        if (world == null || world.isClientSide())
            return;
        Set<BlockPos> managers = STRUCTURE_MANAGERS.get(world.dimension());
        if (managers == null)
            return;

        managers.remove(manager.getBlockPos());
        if (managers.isEmpty())
            STRUCTURE_MANAGERS.remove(world.dimension());
    }

    public static boolean isClose(Level world, BlockPos pos, int radius) {
        Set<BlockPos> managers = STRUCTURE_MANAGERS.get(world.dimension());

        if (managers == null) {
            return false;
        }

        int radiusSquared = radius * radius;

        for (BlockPos managerPos : managers) {
            if (managerPos.distSqr(pos) <= radiusSquared) {
                return true;
            }
        }

        return false;
    }

    public static StructureManagerBlockEntity getClosest(Level world, BlockPos pos, int radius) {
        Set<BlockPos> managers = STRUCTURE_MANAGERS.get(world.dimension());

        if (managers == null) {
            return null;
        }

        int radiusSquared = radius * radius;
        double closestDistance = Double.MAX_VALUE;
        StructureManagerBlockEntity closest = null;

        for (BlockPos managerPos : managers) {
            double distance = managerPos.distSqr(pos);

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
