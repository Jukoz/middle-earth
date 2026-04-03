package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.npcs.data.NpcEntityTextureData;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.texture_presets.ClothingData;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDatas;
import net.sevenstars.middleearth.resources.datas.texture_presets.entities.CharacterTexturePattern;
import net.sevenstars.middleearth.resources.datas.common.NpcTextureType;

import java.util.Optional;

public class NpcEntityHelper {
    public static NpcEntityTextureData generateSkinTextureData(NpcEntityTextureData npcTextureData, TexturePresetDatas.Identity textureIdentity) {
        Identifier materialId = TexturePresetDatas.getRawMaterial(textureIdentity, NpcTextureType.SKIN);
        Identifier bodyPatternId = TexturePresetDatas.getRawPattern(textureIdentity, NpcTextureType.BODY);
        Identifier headPatternId = TexturePresetDatas.getRawPattern(textureIdentity, NpcTextureType.HEAD);
        Identifier feetPatternId = TexturePresetDatas.getRawPattern(textureIdentity, NpcTextureType.FEET);
        Identifier earPatternId = TexturePresetDatas.getRawPattern(textureIdentity, NpcTextureType.EAR);
        Identifier nosePatternId = TexturePresetDatas.getRawPattern(textureIdentity, NpcTextureType.NOSE);
        Identifier scarPatternId = TexturePresetDatas.getRawPattern(textureIdentity, NpcTextureType.SCAR);

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
        Identifier materialId = TexturePresetDatas.getRawMaterial(textureIdentity, NpcTextureType.EYE);
        Identifier patternId = TexturePresetDatas.getRawPattern(textureIdentity, NpcTextureType.EYE);

        npcEntityTextureData = npcEntityTextureData.withEyeTexture(TexturePresetDatas.buildId(patternId, materialId), TexturePresetDatas.buildId(Identifier.of(patternId.getPath() + "_emissive"), materialId), haveEmissiveEyes);

        return npcEntityTextureData;
    }

    public static NpcEntityTextureData generateHairTextureData(NpcEntityTextureData npcEntityTextureData, TexturePresetDatas.Identity textureIdentity, DynamicRegistryManager manager) {
        Identifier globalHairMaterialId = TexturePresetDatas.getRawMaterial(textureIdentity, NpcTextureType.HAIR);

        // Hair
        Identifier hairPatternId = TexturePresetDatas.getRawPattern(textureIdentity, NpcTextureType.HAIR);
        Optional<RegistryEntry.Reference<CharacterTexturePattern>> foundHairPattern = CharacterPatternsRegistryME.get(manager, NpcTextureType.HAIR, hairPatternId);
        if(foundHairPattern.isPresent() && foundHairPattern.get().value() instanceof CharacterTexturePattern pattern){
            npcEntityTextureData = npcEntityTextureData.withHairTexture(TexturePresetDatas.buildId(hairPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcEntityTextureData = npcEntityTextureData.withHairAddonTexture(TexturePresetDatas.buildAddonId(hairPatternId, globalHairMaterialId));
            }
        }
        // Eyebrow
        Identifier eyebrowPatternId = TexturePresetDatas.getRawPattern(textureIdentity, NpcTextureType.EYEBROW);
        Optional<RegistryEntry.Reference<CharacterTexturePattern>> foundEyebrowPattern = CharacterPatternsRegistryME.get(manager, NpcTextureType.EYEBROW, eyebrowPatternId);
        if(foundEyebrowPattern.isPresent()){
            npcEntityTextureData = npcEntityTextureData.withEyebrowTexture(TexturePresetDatas.buildId(eyebrowPatternId, globalHairMaterialId));
        }
        // Beard
        Identifier beardPatternId = TexturePresetDatas.getRawPattern(textureIdentity, NpcTextureType.BEARD);
        Optional<RegistryEntry.Reference<CharacterTexturePattern>> foundBeardPattern = CharacterPatternsRegistryME.get(manager, NpcTextureType.BEARD, beardPatternId);
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
