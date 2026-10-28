package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventDataLookup;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.List;
import java.util.Optional;

public class NpcEntityInitializer {
    public static void initializeNpcEntity(ServerWorld serverWorld, NpcEntity npcEntity){
        initializeForServer(serverWorld, npcEntity);
    }

    private static void initializeForServer(ServerWorld serverWorld, NpcEntity npcEntity){
        Identifier npcTypeId = npcEntity.getNpcTypeIdentifier();
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

    private static BiomeEventData.ContextualizedBiomeData findContextualizedNpcData(ServerWorld world, NpcEntity npcEntity)  {
        BlockPos blockPos = npcEntity.getBlockPos();
        RegistryEntry<Biome> biome = world.getBiome(blockPos);
        Registry<Structure> structureRegistry = world.getRegistryManager().getOrThrow(RegistryKeys.STRUCTURE);
        List<StructureStart> structureStarts = world.getStructureAccessor().getStructureStarts(world.getChunk(blockPos).getPos(), s -> true);

        for (StructureStart structureStart : structureStarts) {
            Structure structure = structureStart.getStructure();
            Identifier structureId = structureRegistry.getId(structure);
            BiomeEventData.ContextualizedBiomeData contextualizedBiomeData = BiomeEventDataLookup.findNpcDataForStructure(world, structureId, npcEntity);
            if(contextualizedBiomeData != null){
                return contextualizedBiomeData;
            }
        }

        return BiomeEventDataLookup.findNpcDataForBiome(world, biome, npcEntity);
    }

    public static boolean shouldInitialize(ServerWorld serverWorld, NpcEntity npcEntity){
        Identifier currentNpcDataId = npcEntity.getNpcTypeIdentifier();
        if(currentNpcDataId == null)
            return true;

        DynamicRegistryManager registryManager = serverWorld.getRegistryManager();
        Optional<RegistryEntry.Reference<NpcType>> optionalEntry = registryManager.getOptional(DynamicRegistriesME.NPC_TYPE).get().getEntry(currentNpcDataId);


        if(!npcEntity.hasTextureData())
            return true;
        return optionalEntry.isEmpty();
    }
}
