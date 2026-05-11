package net.sevenstars.middleearth.entity.npcs.util;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityTextureData;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;
import net.sevenstars.middleearth.resources.datas.npcs.NpcUtil;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;

import java.util.Optional;

public class NpcInitializerUtil {
    public static boolean characterIdentifierExist(World world, Identifier unitIdentifier){
        DynamicRegistryManager registryManager = world.getRegistryManager();
        Optional<RegistryEntry.Reference<NpcData>> optionalEntry = registryManager.getOptional(DynamicRegistriesME.NPC).get().getEntry(unitIdentifier);
        return !optionalEntry.isEmpty();
    }

    public static void generateCharacterTextures(World world, Identifier currentNpcDataId, NpcEntity npcEntity) {
        // Get npc data
        String currentStep = "Fetching datas";
        try{
            DynamicRegistryManager registryManager = world.getRegistryManager();
            Optional<Registry<NpcData>> npcRegistry = registryManager.getOptional(DynamicRegistriesME.NPC);
            NpcData npcData = npcRegistry.get().get(currentNpcDataId);
            npcEntity.setNpcData(npcData);
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
}
