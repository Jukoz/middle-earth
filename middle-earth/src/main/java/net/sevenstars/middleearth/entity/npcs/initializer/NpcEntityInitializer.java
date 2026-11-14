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
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventDataLookup;
import net.sevenstars.middleearth.resources.datas.npc_classes.NpcClass;

import java.util.List;
import java.util.Optional;

public class NpcEntityInitializer {
    public static void initializeNpcEntity(ServerWorld serverWorld, NpcEntity npcEntity){
        initializeForServer(serverWorld, npcEntity);
    }

    private static void initializeForServer(ServerWorld serverWorld, NpcEntity npcEntity){
        Identifier npcClassId = npcEntity.getNpcClassIdentifier();
        PartyData party = null;
        Identifier job = null;
        if(!NpcEntityInitializerUtil.characterIdentifierExist(serverWorld, npcClassId))
        {
            BiomeEventData.ContextualizedBiomeData contextualizedBiomeData = null;
            try{
                contextualizedBiomeData = findContextualizedNpcData(serverWorld, npcEntity);
                if(contextualizedBiomeData == null)
                {
                    npcEntity.discard();
                    return;
                }
                NpcClass npcClass = contextualizedBiomeData.npcClass();
                npcClassId = npcClass.getId();
                party = contextualizedBiomeData.party();
                job = contextualizedBiomeData.job();
            } catch (Exception e){
                MiddleEarth.LOGGER.logError("NpcEntity initializer failed : ", e);
                npcEntity.discard();
                return;
            }
        }

        npcEntity.prepareNpcIdentifier(npcClassId);
        npcEntity.prepare();
        NpcGenerator.generateCharacterTextures(serverWorld, npcEntity);
        if(job != null)
            npcEntity.takeJob(job);

        if(party != null)
            NpcPartySpawner.spawn(serverWorld, npcEntity, party);
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
        Identifier currentNpcDataId = npcEntity.getNpcClassIdentifier();
        if(currentNpcDataId == null)
            return true;

        DynamicRegistryManager registryManager = serverWorld.getRegistryManager();
        Optional<RegistryEntry.Reference<NpcClass>> optionalEntry = registryManager.getOptional(DynamicRegistriesME.NPC_CLASS).get().getEntry(currentNpcDataId);


        if(!npcEntity.hasTextureData())
            return true;
        return optionalEntry.isEmpty();
    }
}
