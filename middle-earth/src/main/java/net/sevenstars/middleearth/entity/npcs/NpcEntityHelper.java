package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.npcs.data.NpcEntityTextureData;
import net.sevenstars.middleearth.resources.CharacterPatternsME;
import net.sevenstars.middleearth.resources.datas.npcs.data.CharacterClothingData;
import net.sevenstars.middleearth.resources.datas.npcs.data.TexturePresets;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTexturePattern;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.Optional;

public class NpcEntityHelper {
    public static NpcEntityTextureData generateSkinTextureData(NpcEntityTextureData npcTextureData, TexturePresets.Identity textureIdentity) {
        Identifier materialId = TexturePresets.getRawMaterial(textureIdentity, NpcTextureType.SKIN);
        Identifier bodyPatternId = TexturePresets.getRawPattern(textureIdentity, NpcTextureType.BODY);
        Identifier headPatternId = TexturePresets.getRawPattern(textureIdentity, NpcTextureType.HEAD);
        Identifier earPatternId = TexturePresets.getRawPattern(textureIdentity, NpcTextureType.EAR);
        Identifier nosePatternId = TexturePresets.getRawPattern(textureIdentity, NpcTextureType.NOSE);
        Identifier scarPatternId = TexturePresets.getRawPattern(textureIdentity, NpcTextureType.SCAR);

        npcTextureData = npcTextureData.withSkinTexture(TexturePresets.buildId(bodyPatternId, materialId));
        npcTextureData = npcTextureData.withHeadTexture(TexturePresets.buildId(headPatternId, materialId));

        if(scarPatternId != null){
            npcTextureData = npcTextureData.withScarTexture(TexturePresets.buildId(scarPatternId, materialId));
        }
        if(earPatternId != null){
            npcTextureData = npcTextureData.withEarTexture(TexturePresets.buildId(earPatternId, materialId));
        }
        if(nosePatternId != null){
            npcTextureData = npcTextureData.withNoseTexture(TexturePresets.buildId(nosePatternId, materialId));
        }

        return npcTextureData;
    }

    public static NpcEntityTextureData generateEyeTextureData(NpcEntityTextureData npcEntityTextureData, TexturePresets.Identity textureIdentity, boolean haveEmissiveEyes) {
        Identifier materialId = TexturePresets.getRawMaterial(textureIdentity, NpcTextureType.EYE);
        Identifier patternId = TexturePresets.getRawPattern(textureIdentity, NpcTextureType.EYE);

        npcEntityTextureData = npcEntityTextureData.withEyeTexture(TexturePresets.buildId(patternId, materialId), TexturePresets.buildId(Identifier.of(patternId.getPath() + "_emissive"), materialId), haveEmissiveEyes);

        return npcEntityTextureData;
    }

    public static NpcEntityTextureData generateHairTextureData(NpcEntityTextureData npcEntityTextureData, TexturePresets.Identity textureIdentity, DynamicRegistryManager manager) {
        Identifier globalHairMaterialId = TexturePresets.getRawMaterial(textureIdentity, NpcTextureType.HAIR);

        // Hair
        Identifier hairPatternId = TexturePresets.getRawPattern(textureIdentity, NpcTextureType.HAIR);
        Optional<RegistryEntry.Reference<NpcTexturePattern>> foundHairPattern = CharacterPatternsME.get(manager, NpcTextureType.HAIR, hairPatternId);
        if(foundHairPattern.isPresent() && foundHairPattern.get().value() instanceof NpcTexturePattern pattern){
            npcEntityTextureData = npcEntityTextureData.withHairTexture(TexturePresets.buildId(hairPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcEntityTextureData = npcEntityTextureData.withHairAddonTexture(TexturePresets.buildAddonId(hairPatternId, globalHairMaterialId));
            }
        }
        // Eyebrow
        Identifier eyebrowPatternId = TexturePresets.getRawPattern(textureIdentity, NpcTextureType.EYEBROW);
        Optional<RegistryEntry.Reference<NpcTexturePattern>> foundEyebrowPattern = CharacterPatternsME.get(manager, NpcTextureType.EYEBROW, eyebrowPatternId);
        if(foundEyebrowPattern.isPresent()){
            npcEntityTextureData = npcEntityTextureData.withEyebrowTexture(TexturePresets.buildId(eyebrowPatternId, globalHairMaterialId));
        }
        // Beard
        Identifier beardPatternId = TexturePresets.getRawPattern(textureIdentity, NpcTextureType.BEARD);
        Optional<RegistryEntry.Reference<NpcTexturePattern>> foundBeardPattern = CharacterPatternsME.get(manager, NpcTextureType.BEARD, beardPatternId);
        if(foundBeardPattern.isPresent() && foundBeardPattern.get().value() instanceof NpcTexturePattern pattern){
            npcEntityTextureData = npcEntityTextureData.withBeardTexture(TexturePresets.buildId(beardPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcEntityTextureData = npcEntityTextureData.withBeardAddonTexture(TexturePresets.buildAddonId(beardPatternId, globalHairMaterialId));
            }
        }
        return npcEntityTextureData;
    }

    public static NpcEntityTextureData generateClothingTextureData(NpcEntityTextureData npcEntityTextureData, TexturePresets.Identity textureIdentity) {
        CharacterClothingData data = TexturePresets.getClothing(textureIdentity);
        npcEntityTextureData = npcEntityTextureData.withClothingTexture(data.base(), data.over(), data.extra());

        return npcEntityTextureData;
    }
}
