package net.sevenstars.middleearth.block.special.structureManager;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.races.data.EntityCategory;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerDataLookup;

public class StructureManagerService {
    public static StructureManagerData GetStructureManagerData(World world, Identifier structureManagerDataId){
        if(world == null)
            return null;
        var data =  StructureManagerDataLookup.getStructureManagerData(world, structureManagerDataId);
        return data.orElse(null);
    }

    public static NpcEntity SpawnEntity(World world, Identifier npcIdentifier, Identifier factionIdentifier, BlockPos pos, int spawnRadius){
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

        var entity = world.getRegistryManager().getOptional(RegistryKeys.ENTITY_TYPE).get().get(Identifier.of(MiddleEarth.MOD_ID, "npc")).create(world, SpawnReason.STRUCTURE);

        if(entity instanceof LivingEntity livEntity){
        }

        var npcEntityToSpawn = NpcEntity.create(world, chosenBlockPos)
                .withCategory(EntityCategory.MALE)
                // .withArchetype()
                .withNpcData(npcIdentifier)
                .withFaction(factionIdentifier);
        world.spawnEntity(npcEntityToSpawn);
        return npcEntityToSpawn;
    }
}
