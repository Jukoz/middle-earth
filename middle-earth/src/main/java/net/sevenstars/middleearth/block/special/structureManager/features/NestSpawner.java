package net.sevenstars.middleearth.block.special.structureManager.features;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.initializer.NpcEntityBuilder;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;
import org.jetbrains.annotations.Nullable;

public class NestSpawner {
    private static final int SPAWN_ATTEMPTS = 12;

    public static LivingEntity spawnEntity(ServerWorld world, StructureSpawnNestPool pool, BlockPos pos, int spawnRadius){
        var random = world.getRandom();
        BlockPos chosenBlockPos = null;
        for(BlockPos blockPos : BlockPos.iterateRandomly(random, SPAWN_ATTEMPTS, pos, spawnRadius)){
            blockPos = blockPos.withY(pos.getY());
            // Air alone isn't enough, since a well's mouth is air too
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
        world.spawnEntity(npc);
        return npc;
    }

    public static @Nullable LivingEntity spawnEntityAt(ServerWorld world, StructureSpawnNestPool pool, BlockPos chosenBlockPos){
        if(pool.getEntityType() == EntitiesME.NPC){
            if(pool.getNpcIdentifier().isEmpty())
                return null;
            return spawnNpcAt(world, pool.getNpcIdentifier().get(), chosenBlockPos);
        }
        if(!(pool.getEntityType().create(world, SpawnReason.STRUCTURE) instanceof LivingEntity entity))
            return null;
        if(entity instanceof MobEntity mob)
            mob.initialize(world, world.getLocalDifficulty(chosenBlockPos), SpawnReason.STRUCTURE, null);
        entity.setPosition(chosenBlockPos.toCenterPos());
        world.spawnEntity(entity);
        return entity;
    }
}
