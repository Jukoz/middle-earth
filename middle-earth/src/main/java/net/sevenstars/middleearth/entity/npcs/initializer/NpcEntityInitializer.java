package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.block.BedBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentHolder;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventDataLookup;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class NpcEntityInitializer {
    public static final Identifier RANDOM = MiddleEarth.of("full_random");

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

            if(contextualizedBiomeData.hasMount() != null && !npcEntity.hasVehicle()) {
                generateMountData(serverWorld, contextualizedBiomeData, npcEntity);
            }
        }

        npcEntity.prepareNpcIdentifier(npcTypeId);
        npcEntity.prepare();
        NpcGenerator.generateCharacterTextures(serverWorld, npcEntity);
        npcEntity.updateTargetGoals();
    }


    private static void generateMountData(ServerWorld serverWorld, BiomeEventData.ContextualizedBiomeData contextualizedBiomeData, NpcEntity npcEntity) {
        // Assign mount if needed
        if(contextualizedBiomeData.hasMount() != null && !npcEntity.hasVehicle()){
            Entity mount = contextualizedBiomeData.hasMount().spawn(serverWorld, npcEntity.getBlockPos(), SpawnReason.JOCKEY);
            if(mount instanceof HorseEntity horseEntity)
                horseEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(npcEntity.getBlockPos()), SpawnReason.JOCKEY, null);
            else if(mount instanceof AbstractBeastEntity beastEntity){
                beastEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(npcEntity.getBlockPos()), SpawnReason.JOCKEY, null);
            }

            if(mount instanceof AbstractHorseEntity abstractHorseEntity){
                abstractHorseEntity.setOwner(npcEntity);
                abstractHorseEntity.setTame(true);
                if(contextualizedBiomeData.mountArmor() != null)
                    abstractHorseEntity.equipBodyArmor(contextualizedBiomeData.mountArmor());

                if(mount instanceof AbstractBeastEntity abstractBeastEntity){
                    abstractBeastEntity.tameBeast(npcEntity);
                }
            }
            if(contextualizedBiomeData.hasMount().isIn(TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of("can_equip_saddle"))) && mount instanceof EquipmentHolder equipmentHolder){
                equipmentHolder.equipStack(EquipmentSlot.SADDLE, new ItemStack(Items.SADDLE));
            }

            npcEntity.startRiding(mount);
        }
    }

    private static BiomeEventData.ContextualizedBiomeData findContextualizedNpcData(ServerWorld world, NpcEntity npcEntity) throws Exception {
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

        BiomeEventData.ContextualizedBiomeData contextualizedBiomeData = BiomeEventDataLookup.findNpcDataForBiome(world, biome, npcEntity);
        if(contextualizedBiomeData != null){
            return contextualizedBiomeData;
        } else {
            //throw new Exception();
        }
        return contextualizedBiomeData;
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

    public static boolean assignBedToNpc(NpcEntity npcEntity, BedBlock bedBlock){
        return true;
    }

    public static void initializeNpcForCurrentData(NpcEntity npcEntity, ServerWorld serverWorld) {
        boolean shouldRefreshVisuals = npcEntity.shouldRefreshVisuals();
        if(shouldRefreshVisuals)
            NpcGenerator.generateCharacterTextures(serverWorld, npcEntity);
    }
}
