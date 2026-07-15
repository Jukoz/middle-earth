package net.sevenstars.middleearth.entity.npcs.initializer;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityTextureData;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcUtil;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTexturePattern;
import net.sevenstars.middleearth.resources.datas.texture_presets.ClothingSelection;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;

import java.util.Optional;

public class NpcGenerator {
    public static void generateCharacterTextures(World world, NpcEntity npcEntity) {
        // Get npc data
        String currentStep = "Fetching datas";
        Identifier currentNpcTypeId = npcEntity.getNpcTypeIdentifier();

        try{
            currentStep = "Aligning data with npc entity...";
            npcEntity.prepareNpcIdentifier(currentNpcTypeId);
            npcEntity.prepare();
            NpcType currentNpcType = npcEntity.getNpcType();

            currentStep = "Applying attributes...";
            currentNpcType.applyAttributes(npcEntity);

            currentStep = "Getting npc texture data...";
            TexturePresetDataPool textureData = currentNpcType.getNpcTextureData(world);

            currentStep = "Creating texture identity...";
            TexturePresetDataPool.Identity identity = TexturePresetDataPool.Identity.create(textureData, npcEntity.getNpcCategory());
            if(identity == null)
                identity = TexturePresetDataPool.Identity.create(textureData);

            NpcEntityTextureData entityTextureData = new NpcEntityTextureData();
            currentStep = "Generating skin...";
            entityTextureData = generateSkinTextureData(entityTextureData, identity);
            currentStep = "Generating eyes...";
            entityTextureData = generateEyeTextureData(entityTextureData, identity, currentNpcType.getNpcTextureData(world).haveEmissiveEyes(identity));
            currentStep = "Generating hair...";
            entityTextureData = generateHairTextureData(entityTextureData, identity, world.getRegistryManager());
            currentStep = "Generating clothing...";
            entityTextureData = generateClothingTextureData(entityTextureData, identity);
            npcEntity.setNpcTextureData(entityTextureData);

            NpcUtil.equipAll(npcEntity, currentNpcType.getGear());
            npcEntity.updateAttackType();
        } catch (Exception exception){
            MiddleEarth.LOGGER.logError(String.format("NpcEntityInitializer::Couldn't generate %s because of : %s | Triggered by %s", currentNpcTypeId, exception.getLocalizedMessage(), currentStep));
        }
    }

    public static NpcEntityTextureData generateSkinTextureData(NpcEntityTextureData npcTextureData, TexturePresetDataPool.Identity textureIdentity) {
        Identifier materialId = TexturePresetDataPool.getRawMaterial(textureIdentity, CharacterMaterialTypes.SKIN);
        Identifier bodyPatternId = TexturePresetDataPool.getRawPattern(textureIdentity, CharacterPatternTypes.BODY);
        Identifier headPatternId = TexturePresetDataPool.getRawPattern(textureIdentity, CharacterPatternTypes.HEAD);
        Identifier feetPatternId = TexturePresetDataPool.getRawPattern(textureIdentity, CharacterPatternTypes.FEET);
        Identifier earPatternId = TexturePresetDataPool.getRawPattern(textureIdentity, CharacterPatternTypes.EAR);
        Identifier nosePatternId = TexturePresetDataPool.getRawPattern(textureIdentity, CharacterPatternTypes.NOSE);
        Identifier scarPatternId = TexturePresetDataPool.getRawPattern(textureIdentity, CharacterPatternTypes.SCAR);

        npcTextureData = npcTextureData.withSkinTexture(TexturePresetDataPool.buildId(bodyPatternId, materialId));
        npcTextureData = npcTextureData.withHeadTexture(TexturePresetDataPool.buildId(headPatternId, materialId));

        if(scarPatternId != null){
            npcTextureData = npcTextureData.withScarTexture(TexturePresetDataPool.buildId(scarPatternId, materialId));
        }
        if(earPatternId != null){
            npcTextureData = npcTextureData.withEarTexture(TexturePresetDataPool.buildId(earPatternId, materialId));
        }
        if(nosePatternId != null){
            npcTextureData = npcTextureData.withNoseTexture(TexturePresetDataPool.buildId(nosePatternId, materialId));
        }
        if(feetPatternId != null){
            npcTextureData = npcTextureData.withFeetTexture(TexturePresetDataPool.buildId(feetPatternId, materialId));
        }

        npcTextureData.withSimplifiedPreset(textureIdentity.preset().getSimplifiedSkin());
        return npcTextureData;
    }

    public static NpcEntityTextureData generateEyeTextureData(NpcEntityTextureData npcEntityTextureData, TexturePresetDataPool.Identity textureIdentity, boolean haveEmissiveEyes) {
        Identifier materialId = TexturePresetDataPool.getRawMaterial(textureIdentity, CharacterMaterialTypes.EYE);
        Identifier patternId = TexturePresetDataPool.getRawPattern(textureIdentity, CharacterPatternTypes.EYE);

        npcEntityTextureData = npcEntityTextureData.withEyeTexture(TexturePresetDataPool.buildId(patternId, materialId), TexturePresetDataPool.buildId(Identifier.of(patternId.getPath() + "_emissive"), materialId), haveEmissiveEyes);

        return npcEntityTextureData;
    }

    public static NpcEntityTextureData generateHairTextureData(NpcEntityTextureData npcEntityTextureData, TexturePresetDataPool.Identity textureIdentity, DynamicRegistryManager manager) {
        Identifier globalHairMaterialId = TexturePresetDataPool.getRawMaterial(textureIdentity, CharacterMaterialTypes.HAIR);

        // Hair
        Identifier hairPatternId = TexturePresetDataPool.getRawPattern(textureIdentity, CharacterPatternTypes.HAIR);
        Optional<RegistryEntry.Reference<CharacterTexturePattern>> foundHairPattern = CharacterPatternsRegistryME.get(manager, CharacterPatternTypes.HAIR, hairPatternId);
        if(foundHairPattern.isPresent() && foundHairPattern.get().value() instanceof CharacterTexturePattern pattern){
            npcEntityTextureData = npcEntityTextureData.withHairTexture(TexturePresetDataPool.buildId(hairPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcEntityTextureData = npcEntityTextureData.withHairAddonTexture(TexturePresetDataPool.buildAddonId(hairPatternId, globalHairMaterialId));
            }
        }
        // Eyebrow
        Identifier eyebrowPatternId = TexturePresetDataPool.getRawPattern(textureIdentity, CharacterPatternTypes.EYEBROW);
        Optional<RegistryEntry.Reference<CharacterTexturePattern>> foundEyebrowPattern = CharacterPatternsRegistryME.get(manager, CharacterPatternTypes.EYEBROW, eyebrowPatternId);
        if(foundEyebrowPattern.isPresent()){
            npcEntityTextureData = npcEntityTextureData.withEyebrowTexture(TexturePresetDataPool.buildId(eyebrowPatternId, globalHairMaterialId));
        }
        // Beard
        Identifier beardPatternId = TexturePresetDataPool.getRawPattern(textureIdentity, CharacterPatternTypes.BEARD);
        Optional<RegistryEntry.Reference<CharacterTexturePattern>> foundBeardPattern = CharacterPatternsRegistryME.get(manager, CharacterPatternTypes.BEARD, beardPatternId);
        if(foundBeardPattern.isPresent() && foundBeardPattern.get().value() instanceof CharacterTexturePattern pattern){
            npcEntityTextureData = npcEntityTextureData.withBeardTexture(TexturePresetDataPool.buildId(beardPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcEntityTextureData = npcEntityTextureData.withBeardAddonTexture(TexturePresetDataPool.buildAddonId(beardPatternId, globalHairMaterialId));
            }
        }
        return npcEntityTextureData;
    }

    public static NpcEntityTextureData generateClothingTextureData(NpcEntityTextureData npcEntityTextureData, TexturePresetDataPool.Identity textureIdentity) {
        ClothingSelection data = TexturePresetDataPool.getClothing(textureIdentity);
        npcEntityTextureData = npcEntityTextureData.withClothingTexture(data.base(), data.over(), data.extra());

        return npcEntityTextureData;
    }
}
