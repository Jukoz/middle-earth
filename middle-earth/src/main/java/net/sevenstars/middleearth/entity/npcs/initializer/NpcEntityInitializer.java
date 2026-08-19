package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventDataLookup;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.List;

public class NpcEntityInitializer {
    public static final ResourceLocation RANDOM = MiddleEarth.of("full_random");

    public static void initializeNpcEntity(ServerLevel serverWorld, NpcEntity npcEntity){
        initializeForServer(serverWorld, npcEntity);
    }

    private static void initializeForServer(ServerLevel serverWorld, NpcEntity npcEntity){
        ResourceLocation npcTypeId = npcEntity.getNpcTypeIdentifier();
        if(!NpcEntityInitializerUtil.characterIdentifierExist(serverWorld, npcTypeId))
        {
            BiomeEventData.ContextualizedBiomeData contextualizedBiomeData = null;
            try{
                contextualizedBiomeData = findContextualizedNpcData(serverWorld, npcEntity);
                if(contextualizedBiomeData == null)
                {
                    npcEntity.discard();
                    return;
                }
                NpcType npcType = contextualizedBiomeData.npcType();
                npcTypeId = npcType.getId();
            } catch (Exception e){
                MiddleEarth.LOGGER.logError("NpcEntity initializer failed : ", e);
                npcEntity.discard();
                return;
            }
        }

        npcEntity.prepareNpcIdentifier(npcTypeId);
        npcEntity.prepare();
        NpcGenerator.generateCharacterTextures(serverWorld, npcEntity);
        npcEntity.updateTargetGoals();
    }

    private static BiomeEventData.ContextualizedBiomeData findContextualizedNpcData(ServerLevel world, NpcEntity npcEntity)  {
        BlockPos blockPos = npcEntity.blockPosition();
        Holder<Biome> biome = world.getBiome(blockPos);
        Registry<Structure> structureRegistry = world.registryAccess().registryOrThrow(Registries.STRUCTURE);
        List<StructureStart> structureStarts = world.structureManager().startsForStructure(world.getChunk(blockPos).getPos(), s -> true);

        for (StructureStart structureStart : structureStarts) {
            Structure structure = structureStart.getStructure();
            ResourceLocation structureId = structureRegistry.getKey(structure);
            BiomeEventData.ContextualizedBiomeData contextualizedBiomeData = BiomeEventDataLookup.findNpcDataForStructure(world, structureId, npcEntity);
            if(contextualizedBiomeData != null){
                return contextualizedBiomeData;
            }
        }

        return BiomeEventDataLookup.findNpcDataForBiome(world, biome, npcEntity);
    }


    public static boolean shouldInitialize(ServerLevel serverWorld, NpcEntity npcEntity){
        ResourceLocation currentNpcDataId = npcEntity.getNpcTypeIdentifier();
        if(currentNpcDataId == null)
            return true;

        if(!npcEntity.hasTextureData())
            return true;
        Registry<NpcType> registry = serverWorld.registryAccess().registryOrThrow(DynamicRegistriesME.NPC_TYPE);
        return !registry.containsKey(currentNpcDataId);
    }

    public static boolean assignBedToNpc(NpcEntity npcEntity, BedBlock bedBlock){
        return true;
    }

    public static void initializeNpcForCurrentData(NpcEntity npcEntity, ServerLevel serverWorld) {
        boolean shouldRefreshVisuals = npcEntity.shouldRefreshVisuals();
        if(shouldRefreshVisuals)
            NpcGenerator.generateCharacterTextures(serverWorld, npcEntity);
    }
}
