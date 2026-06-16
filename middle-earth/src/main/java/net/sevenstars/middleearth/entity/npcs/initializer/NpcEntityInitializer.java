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
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventDataLookup;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

public class NpcEntityInitializer {
    public static final Identifier RANDOM = MiddleEarth.of("full_random");

    public static void initializeNpcEntity(ServerWorld serverWorld, NpcEntity npcEntity){
        initializeForServer(serverWorld, npcEntity);
    }

    private static void initializeForServer(ServerWorld serverWorld, NpcEntity npcEntity){
        Identifier currentNpcDataId = npcEntity.getNpcDataIdentifier();

        if(Objects.equals(currentNpcDataId, RANDOM)){
            var ids = serverWorld.getRegistryManager().getOptional(DynamicRegistriesME.NPC).get().getIds();

            Random random = new Random();
            currentNpcDataId = ids.stream().toList().get(random.nextInt(ids.size()));
        } else if(!NpcEntityInitializerUtil.characterIdentifierExist(serverWorld, currentNpcDataId)){
            BiomeEventData.ContextualizedBiomeData contextualizedBiomeData = null;
            try{
                contextualizedBiomeData = findContextualizedNpcData(serverWorld, npcEntity);
                currentNpcDataId = contextualizedBiomeData.npcData().getId();
            } catch (Exception e){
                npcEntity.discard();
                return;
            }

            if(contextualizedBiomeData.hasMount() != null && !npcEntity.hasVehicle()) {
                generateMountData(serverWorld, contextualizedBiomeData, npcEntity);
            }
        }

        NpcGenerator.generateCharacterTextures(serverWorld, currentNpcDataId, npcEntity);
        npcEntity.setInitializationTick();
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
        BiomeEventData.ContextualizedBiomeData contextualizedBiomeData = BiomeEventDataLookup.findNpcDataForBiome(world, biome, npcEntity);
        if(contextualizedBiomeData != null){
            return contextualizedBiomeData;
        } else {
            throw new Exception();
        }
    }


    public static boolean shouldInitialize(ServerWorld serverWorld, NpcEntity npcEntity){
        Identifier currentNpcDataId = npcEntity.getNpcDataIdentifier();
        if(currentNpcDataId == null)
            return true;

        DynamicRegistryManager registryManager = serverWorld.getRegistryManager();
        Optional<RegistryEntry.Reference<NpcData>> optionalEntry = registryManager.getOptional(DynamicRegistriesME.NPC).get().getEntry(currentNpcDataId);


        if(!npcEntity.hasTextureData())
            return true;
        return optionalEntry.isEmpty();
    }

    public static boolean assignBedToNpc(NpcEntity npcEntity, BedBlock bedBlock){
        return true;
    }

    public static void initializeNpcForCurrentData(NpcEntity npcEntity, ServerWorld serverWorld, Identifier npcDataId) {
        boolean shouldRefreshVisuals = npcEntity.getNpcTextureData().needToBeRefreshed();
        if(shouldRefreshVisuals)
            NpcGenerator.generateCharacterTextures(serverWorld, npcDataId, npcEntity);
    }
}
