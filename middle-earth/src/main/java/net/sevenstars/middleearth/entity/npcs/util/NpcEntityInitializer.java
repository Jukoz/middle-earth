package net.sevenstars.middleearth.entity.npcs.util;

import net.minecraft.block.BedBlock;
import net.minecraft.client.world.ClientWorld;
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
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityTextureData;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventDataLookup;
import net.sevenstars.middleearth.resources.datas.npcs.NpcUtil;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;

public class NpcEntityInitializer {
    public static void initializeNpcEntity(ClientWorld clientWorld, NpcEntity npcEntity){
        initializeForClient(clientWorld, npcEntity);
    }

    public static void initializeNpcEntity(ServerWorld serverWorld, NpcEntity npcEntity){
        initializeForServer(serverWorld, npcEntity);
    }

    private static void initializeForServer(ServerWorld serverWorld, NpcEntity npcEntity){
        Identifier currentNpcDataId = npcEntity.getNpcDataId();
        if(!characterIdentifierExist(serverWorld, currentNpcDataId)){
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

        generateCharacterTextures(serverWorld, currentNpcDataId, npcEntity);
    }

    private static void initializeForClient(World clientWorld, NpcEntity npcEntity) {
        Identifier currentNpcDataId = npcEntity.getNpcDataId();
        if(characterIdentifierExist(clientWorld, currentNpcDataId)){
            generateCharacterTextures(clientWorld, currentNpcDataId, npcEntity);
        }
    }

    private static void generateCharacterTextures(World world, Identifier currentNpcDataId, NpcEntity npcEntity) {
        // Get npc data
        String currentStep = "Fetching datas";
        try{
            var npcData = world.getRegistryManager().getOptional(DynamicRegistriesME.NPC).get().getEntry(currentNpcDataId).get().value();
            npcEntity.setNpcData(currentNpcDataId);
            npcEntity.setFactionId(npcData.getFactionIdentifier());
            npcEntity.setNpcCategory(npcData.getNpcTextureData(world).getRandomCategory());
            npcData.applyAttributes(npcEntity);

            TexturePresetDataPool textureData = npcData.getNpcTextureData(world);

            TexturePresetDataPool.Identity identity = TexturePresetDataPool.Identity.create(textureData, npcEntity.getNpcCategory());
            if(identity == null)
                identity = TexturePresetDataPool.Identity.create(textureData);

            NpcEntityTextureData entityTextureData = new NpcEntityTextureData();
            currentStep = "Generating skin...";
            entityTextureData = NpcEntityHelper.generateSkinTextureData(entityTextureData, identity);
            currentStep = "Generating eyes...";
            entityTextureData = NpcEntityHelper.generateEyeTextureData(entityTextureData, identity, npcData.getNpcTextureData(world).haveEmissiveEyes(identity));
            currentStep = "Generating hair...";
            entityTextureData = NpcEntityHelper.generateHairTextureData(entityTextureData, identity, world.getRegistryManager());
            currentStep = "Generating clothing...";
            entityTextureData = NpcEntityHelper.generateClothingTextureData(entityTextureData, identity);
            npcEntity.setNpcTextureData(entityTextureData);

            NpcUtil.equipAll(npcEntity, npcData.getGear());
        } catch (Exception exception){
            MiddleEarth.LOGGER.logError(String.format("NpcEntityInitializer::Couldn't generate %s because of : %s | Triggered by %s", currentNpcDataId, exception.getLocalizedMessage(), currentStep));
            if(!npcEntity.isRemoved())
                npcEntity.discard();
        }
    }

    private static void generateMountData(ServerWorld serverWorld, BiomeEventData.ContextualizedBiomeData contextualizedBiomeData, NpcEntity npcEntity) {
        // Assign mount if needed
        if(contextualizedBiomeData.hasMount() != null && !npcEntity.hasVehicle()){
            var mount = contextualizedBiomeData.hasMount().spawn(serverWorld, npcEntity.getBlockPos(), SpawnReason.JOCKEY);
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

    private static BiomeEventData.ContextualizedBiomeData findContextualizedNpcData(World world, NpcEntity npcEntity) throws Exception {
        BlockPos blockPos = npcEntity.getBlockPos();
        RegistryEntry<Biome> biome = world.getBiome(blockPos);
        BiomeEventData.ContextualizedBiomeData contextualizedBiomeData = BiomeEventDataLookup.findNpcDataForBiome(world, biome, npcEntity);
        if(contextualizedBiomeData != null){
            return contextualizedBiomeData;
        } else {
            throw new Exception();
        }
    }

    private static boolean characterIdentifierExist(World world, Identifier unitIdentifier){
        DynamicRegistryManager registryManager = world.getRegistryManager();
        var optionalEntry = registryManager.getOptional(DynamicRegistriesME.NPC).get().getEntry(unitIdentifier);
        return !optionalEntry.isEmpty();
    }

    public static boolean shouldInitialize(ServerWorld serverWorld, NpcEntity npcEntity){
        Identifier currentNpcDataId = npcEntity.getNpcDataId();
        if(currentNpcDataId == null)
            return true;
        DynamicRegistryManager registryManager = serverWorld.getRegistryManager();
        var optionalEntry = registryManager.getOptional(DynamicRegistriesME.NPC).get().getEntry(currentNpcDataId);
        if(!npcEntity.hasTextureData())
            return true;
        return optionalEntry.isEmpty();
    }

    public static boolean assignBedToNpc(NpcEntity npcEntity, BedBlock bedBlock){
        return true;
    }
}
