package net.sevenstars.middleearth.entity.npcs;

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
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.data.NpcEntityTextureData;
import net.sevenstars.middleearth.resources.NpcME;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventDataLookup;
import net.sevenstars.middleearth.resources.datas.npcs.NpcUtil;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcTextureData;

public class NpcEntityInitializer {
    public static void initializeNpcEntity(ServerWorld serverWorld, NpcEntity npcEntity){
        Identifier currentNpcDataId = npcEntity.getNpcDataId();
        DynamicRegistryManager registryManager = serverWorld.getRegistryManager();
        var optionalEntry = registryManager.getOptional(NpcME.KEY).get().getEntry(currentNpcDataId);
        if (optionalEntry.isEmpty()) {
            BlockPos blockPos = npcEntity.getBlockPos();
            RegistryEntry<Biome> biome = serverWorld.getBiome(blockPos);
            BiomeEventData.FoundNpcReturn foundNpcReturn = BiomeEventDataLookup.findNpcDataForBiome(serverWorld, biome, npcEntity);
            if(foundNpcReturn != null){
                currentNpcDataId =  foundNpcReturn.npcData().getId();
            } else {
                npcEntity.discard();
            }

            // Assign mount if needed
            if(foundNpcReturn.mountEntity() != null && !npcEntity.hasVehicle()){
                var mount = foundNpcReturn.mountEntity().spawn(serverWorld, npcEntity.getBlockPos(), SpawnReason.JOCKEY);
                if(mount instanceof HorseEntity horseEntity)
                    horseEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(npcEntity.getBlockPos()), SpawnReason.JOCKEY, null);
                else if(mount instanceof AbstractBeastEntity beastEntity){
                    beastEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(npcEntity.getBlockPos()), SpawnReason.JOCKEY, null);
                }

                if(mount instanceof AbstractHorseEntity abstractHorseEntity){
                    abstractHorseEntity.setOwner(npcEntity);
                    abstractHorseEntity.setTame(true);
                    if(foundNpcReturn.mountArmor() != null)
                        abstractHorseEntity.equipBodyArmor(foundNpcReturn.mountArmor());
                }
                if(foundNpcReturn.mountEntity().isIn(TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of("can_equip_saddle"))) && mount instanceof EquipmentHolder equipmentHolder){
                    equipmentHolder.equipStack(EquipmentSlot.SADDLE, new ItemStack(Items.SADDLE));
                }

                npcEntity.startRiding(mount);
            }
        }

        // Get npc data
        try{
            var npcData = registryManager.getOptional(NpcME.KEY).get().getEntry(currentNpcDataId).get().value();
            npcEntity.setNpcData(currentNpcDataId);
            npcEntity.setFactionId(npcData.getFaction());
            npcEntity.setNpcCategory(npcData.getNpcTextureData(serverWorld).getRandomCategory());
            npcData.applyAttributes(npcEntity);

            NpcTextureData textureData = npcData.getNpcTextureData(serverWorld);

            NpcTextureData.Identity identity = NpcTextureData.Identity.create(textureData, npcEntity.getNpcCategory());
            if(identity == null)
                identity = NpcTextureData.Identity.create(textureData);

            NpcEntityTextureData entityTextureData = new NpcEntityTextureData();
            entityTextureData = NpcEntityHelper.generateSkinTextureData(entityTextureData, identity);
            entityTextureData = NpcEntityHelper.generateEyeTextureData(entityTextureData, identity, npcData.getNpcTextureData(serverWorld).haveEmissiveEyes(identity)); // Make it not hardcoded
            entityTextureData = NpcEntityHelper.generateHairTextureData(entityTextureData, identity, serverWorld.getRegistryManager());
            entityTextureData = NpcEntityHelper.generateClothingTextureData(entityTextureData, identity);
            npcEntity.setNpcTextureData(entityTextureData);

            NpcUtil.equipAll(npcEntity, npcData.getGear());
        } catch (Exception exception){
            npcEntity.discard();
        }
    }

    public static boolean shouldInitialize(ServerWorld serverWorld, NpcEntity npcEntity){
        Identifier currentNpcDataId = npcEntity.getNpcDataId();
        if(currentNpcDataId == null)
            return true;
        DynamicRegistryManager registryManager = serverWorld.getRegistryManager();
        var optionalEntry = registryManager.getOptional(NpcME.KEY).get().getEntry(currentNpcDataId);
        if(!npcEntity.hasTextureData())
            return true;
        return optionalEntry.isEmpty();
    }
}
