package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.npcs.data.NpcEntityTextureData;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.texture_presets.ClothingData;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDatas;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTexturePattern;

import java.util.Optional;

public class NpcEntityHelper {
    public static NpcEntityTextureData generateSkinTextureData(NpcEntityTextureData npcTextureData, TexturePresetDatas.Identity textureIdentity) {
        Identifier materialId = TexturePresetDatas.getRawMaterial(textureIdentity, CharacterMaterialTypes.SKIN);
        Identifier bodyPatternId = TexturePresetDatas.getRawPattern(textureIdentity, CharacterPatternTypes.BODY);
        Identifier headPatternId = TexturePresetDatas.getRawPattern(textureIdentity, CharacterPatternTypes.HEAD);
        Identifier feetPatternId = TexturePresetDatas.getRawPattern(textureIdentity, CharacterPatternTypes.FEET);
        Identifier earPatternId = TexturePresetDatas.getRawPattern(textureIdentity, CharacterPatternTypes.EAR);
        Identifier nosePatternId = TexturePresetDatas.getRawPattern(textureIdentity, CharacterPatternTypes.NOSE);
        Identifier scarPatternId = TexturePresetDatas.getRawPattern(textureIdentity, CharacterPatternTypes.SCAR);

        npcTextureData = npcTextureData.withSkinTexture(TexturePresetDatas.buildId(bodyPatternId, materialId));
        npcTextureData = npcTextureData.withHeadTexture(TexturePresetDatas.buildId(headPatternId, materialId));

        if(scarPatternId != null){
            npcTextureData = npcTextureData.withScarTexture(TexturePresetDatas.buildId(scarPatternId, materialId));
        }
        if(earPatternId != null){
            npcTextureData = npcTextureData.withEarTexture(TexturePresetDatas.buildId(earPatternId, materialId));
        }
        if(nosePatternId != null){
            npcTextureData = npcTextureData.withNoseTexture(TexturePresetDatas.buildId(nosePatternId, materialId));
        }
        if(feetPatternId != null){
            npcTextureData = npcTextureData.withFeetTexture(TexturePresetDatas.buildId(feetPatternId, materialId));
        }

        return npcTextureData;
    }

    public static NpcEntityTextureData generateEyeTextureData(NpcEntityTextureData npcEntityTextureData, TexturePresetDatas.Identity textureIdentity, boolean haveEmissiveEyes) {
        Identifier materialId = TexturePresetDatas.getRawMaterial(textureIdentity, CharacterMaterialTypes.EYE);
        Identifier patternId = TexturePresetDatas.getRawPattern(textureIdentity, CharacterPatternTypes.EYE);

        npcEntityTextureData = npcEntityTextureData.withEyeTexture(TexturePresetDatas.buildId(patternId, materialId), TexturePresetDatas.buildId(Identifier.of(patternId.getPath() + "_emissive"), materialId), haveEmissiveEyes);

        return npcEntityTextureData;
    }

    public static NpcEntityTextureData generateHairTextureData(NpcEntityTextureData npcEntityTextureData, TexturePresetDatas.Identity textureIdentity, DynamicRegistryManager manager) {
        Identifier globalHairMaterialId = TexturePresetDatas.getRawMaterial(textureIdentity, CharacterMaterialTypes.HAIR);

        // Hair
        Identifier hairPatternId = TexturePresetDatas.getRawPattern(textureIdentity, CharacterPatternTypes.HAIR);
        Optional<RegistryEntry.Reference<CharacterTexturePattern>> foundHairPattern = CharacterPatternsRegistryME.get(manager, CharacterPatternTypes.HAIR, hairPatternId);
        if(foundHairPattern.isPresent() && foundHairPattern.get().value() instanceof CharacterTexturePattern pattern){
            npcEntityTextureData = npcEntityTextureData.withHairTexture(TexturePresetDatas.buildId(hairPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcEntityTextureData = npcEntityTextureData.withHairAddonTexture(TexturePresetDatas.buildAddonId(hairPatternId, globalHairMaterialId));
            }
        }
        // Eyebrow
        Identifier eyebrowPatternId = TexturePresetDatas.getRawPattern(textureIdentity, CharacterPatternTypes.EYEBROW);
        Optional<RegistryEntry.Reference<CharacterTexturePattern>> foundEyebrowPattern = CharacterPatternsRegistryME.get(manager, CharacterPatternTypes.EYEBROW, eyebrowPatternId);
        if(foundEyebrowPattern.isPresent()){
            npcEntityTextureData = npcEntityTextureData.withEyebrowTexture(TexturePresetDatas.buildId(eyebrowPatternId, globalHairMaterialId));
        }
        // Beard
        Identifier beardPatternId = TexturePresetDatas.getRawPattern(textureIdentity, CharacterPatternTypes.BEARD);
        Optional<RegistryEntry.Reference<CharacterTexturePattern>> foundBeardPattern = CharacterPatternsRegistryME.get(manager, CharacterPatternTypes.BEARD, beardPatternId);
        if(foundBeardPattern.isPresent() && foundBeardPattern.get().value() instanceof CharacterTexturePattern pattern){
            npcEntityTextureData = npcEntityTextureData.withBeardTexture(TexturePresetDatas.buildId(beardPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcEntityTextureData = npcEntityTextureData.withBeardAddonTexture(TexturePresetDatas.buildAddonId(beardPatternId, globalHairMaterialId));
            }
        }
        return npcEntityTextureData;
    }

    public static NpcEntityTextureData generateClothingTextureData(NpcEntityTextureData npcEntityTextureData, TexturePresetDatas.Identity textureIdentity) {
        ClothingData data = TexturePresetDatas.getClothing(textureIdentity);
        npcEntityTextureData = npcEntityTextureData.withClothingTexture(data.base(), data.over(), data.extra());

        return npcEntityTextureData;
    }
}
