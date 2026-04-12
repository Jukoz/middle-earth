package net.sevenstars.middleearth.entity.npcs.util;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.npcs.renderer.NpcEntityTextureData;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.texture_presets.ClothingSelection;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTexturePattern;

import java.util.Optional;

public class NpcEntityHelper {
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
