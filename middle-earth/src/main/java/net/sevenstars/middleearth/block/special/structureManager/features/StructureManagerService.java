package net.sevenstars.middleearth.block.special.structureManager.features;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.util.NpcEntityBuilder;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerDataLookup;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;

public class StructureManagerService {
    public static StructureManagerData GetStructureManagerData(World world, Identifier structureManagerDataId){
        if(world == null)
            return null;
        var data =  StructureManagerDataLookup.getStructureManagerData(world, structureManagerDataId);
        return data.orElse(null);
    }

    public static LivingEntity SpawnEntity(World world, StructureSpawnNestPool pool, BlockPos pos, int spawnRadius){
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
                    .withNpcData(pool.getNpcIdentifier().get())
                    .build();
        } else {
            entity = (LivingEntity) pool.getEntityType().create(world, SpawnReason.STRUCTURE);
            entity.setPosition(chosenBlockPos.toCenterPos());
        }

        if(entity instanceof LivingEntity livEntity){
            world.spawnEntity(entity);
            return livEntity;
        }
        return null;
    }
}
