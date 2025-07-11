package net.sevenstars.middleearth.block.special.structureManager;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.data.NpcEntityData;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerDataLookup;

public class StructureManagerService {
    public static StructureManagerData GetStructureManagerData(World world, Identifier structureManagerDataId){
        var data =  StructureManagerDataLookup.getStructureManagerData(world, structureManagerDataId);
        return data.orElse(null);
    }

    public static NpcEntity SpawnEntity(World world, Identifier npcIdentifier, Identifier factionIdentifier, BlockPos pos, int spawnRadius){
        NpcEntity npcEntityToSpawn = new NpcEntity(ModEntities.NPC, world);
        npcEntityToSpawn.setFaction(factionIdentifier);
        npcEntityToSpawn.setNpcData(npcIdentifier);
        npcEntityToSpawn.setNpcCategory(EntityCategory.MALE.name());

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


        npcEntityToSpawn.setPos(chosenBlockPos.getX(), chosenBlockPos.getY(), chosenBlockPos.getZ());
        npcEntityToSpawn.AssignNpcEntityData(world, new NpcEntityData(factionIdentifier, npcIdentifier, EntityCategory.MALE));

        world.spawnEntity(npcEntityToSpawn);
        return npcEntityToSpawn;
    }
}
