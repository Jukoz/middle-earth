package net.sevenstars.middleearth.block.special.structureManager.features;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.initializer.NpcEntityBuilder;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerDataLookup;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;

import java.util.*;

public class StructureManagerService {
    private static final Map<RegistryKey<World>, Set<BlockPos>> STRUCTURE_MANAGERS = new HashMap<>();

    public static StructureManagerData GetStructureManagerData(World world, Identifier structureManagerDataId){
        if(world == null)
            return null;
        var data =  StructureManagerDataLookup.getStructureManagerData(world, structureManagerDataId);
        return data.orElse(null);
    }

    public static LivingEntity SpawnEntity(ServerWorld world, StructureSpawnNestPool pool, BlockPos pos, int spawnRadius){
        var random = world.getRandom();
        int chances = 5;
        BlockPos chosenBlockPos = null;
        for(BlockPos blockPos : BlockPos.iterateRandomly(random, chances, pos, spawnRadius)){
            blockPos = blockPos.withY(pos.getY());
            if(world.getBlockState(blockPos).isAir() && world.getBlockState(blockPos.up()).isAir()){
                chosenBlockPos = blockPos;
                break;
            }
        }
        if(chosenBlockPos == null)
            chosenBlockPos = pos;


        LivingEntity entity;
        if(pool.getEntityType() == EntitiesME.NPC){

            entity = new NpcEntityBuilder(world, chosenBlockPos)
                    .withNpcType(pool.getNpcIdentifier().get())
                    .build();
        } else {
            entity = (LivingEntity) pool.getEntityType().create(world, SpawnReason.STRUCTURE);
            if(entity instanceof AbstractHorseEntity horse)
                horse.initialize(world, world.getLocalDifficulty(pos), SpawnReason.STRUCTURE, null);
            entity.setPosition(chosenBlockPos.toCenterPos());
        }

        if(entity instanceof LivingEntity livEntity){
            world.spawnEntity(entity);
            return livEntity;
        }
        return null;
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
