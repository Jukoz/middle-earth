package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.npcs.data.NpcEntityTextureData;
import net.sevenstars.middleearth.resources.CharacterPatternsME;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTexturePattern;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.Optional;

public class NpcEntityHelper {
    public static NpcEntityTextureData generateSkinTextureData(NpcEntityTextureData npcTextureData, NpcTextureData.Identity textureIdentity) {
        Identifier materialId = NpcTextureData.getRawMaterial(textureIdentity, NpcTextureType.SKIN);
        Identifier bodyPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.BODY);
        Identifier headPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.HEAD);
        Identifier earPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.EAR);
        Identifier nosePatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.NOSE);
        Identifier scarPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.SCAR);

        npcTextureData = npcTextureData.withSkinTexture(NpcTextureData.buildId(bodyPatternId, materialId));
        npcTextureData = npcTextureData.withHeadTexture(NpcTextureData.buildId(headPatternId, materialId));

        if(scarPatternId != null){
            npcTextureData = npcTextureData.withScarTexture(NpcTextureData.buildId(scarPatternId, materialId));
        }
        if(earPatternId != null){
            npcTextureData = npcTextureData.withEarTexture(NpcTextureData.buildId(earPatternId, materialId));
        }
        if(nosePatternId != null){
            npcTextureData = npcTextureData.withNoseTexture(NpcTextureData.buildId(nosePatternId, materialId));
        }

        return npcTextureData;
    }

    public static NpcEntityTextureData generateEyeTextureData(NpcEntityTextureData npcEntityTextureData, NpcTextureData.Identity textureIdentity, boolean haveEmissiveEyes) {
        Identifier materialId = NpcTextureData.getRawMaterial(textureIdentity, NpcTextureType.EYE);
        Identifier patternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.EYE);

        npcEntityTextureData = npcEntityTextureData.withEyeTexture(NpcTextureData.buildId(patternId, materialId), NpcTextureData.buildId(Identifier.of(patternId.getPath() + "_emissive"), materialId), haveEmissiveEyes);

        return npcEntityTextureData;
    }

    public static NpcEntityTextureData generateHairTextureData(NpcEntityTextureData npcEntityTextureData, NpcTextureData.Identity textureIdentity, DynamicRegistryManager manager) {
        Identifier globalHairMaterialId = NpcTextureData.getRawMaterial(textureIdentity, NpcTextureType.HAIR);

        // Hair
        Identifier hairPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.HAIR);
        Optional<RegistryEntry.Reference<NpcTexturePattern>> foundHairPattern = CharacterPatternsME.get(manager, NpcTextureType.HAIR, hairPatternId);
        if(foundHairPattern.isPresent() && foundHairPattern.get().value() instanceof NpcTexturePattern pattern){
            npcEntityTextureData = npcEntityTextureData.withHairTexture(NpcTextureData.buildId(hairPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcEntityTextureData = npcEntityTextureData.withHairAddonTexture(NpcTextureData.buildAddonId(hairPatternId, globalHairMaterialId));
            }
        }
        // Eyebrow
        Identifier eyebrowPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.EYEBROW);
        Optional<RegistryEntry.Reference<NpcTexturePattern>> foundEyebrowPattern = CharacterPatternsME.get(manager, NpcTextureType.EYEBROW, eyebrowPatternId);
        if(foundEyebrowPattern.isPresent()){
            npcEntityTextureData = npcEntityTextureData.withEyebrowTexture(NpcTextureData.buildId(eyebrowPatternId, globalHairMaterialId));
        }
        // Beard
        Identifier beardPatternId = NpcTextureData.getRawPattern(textureIdentity, NpcTextureType.BEARD);
        Optional<RegistryEntry.Reference<NpcTexturePattern>> foundBeardPattern = CharacterPatternsME.get(manager, NpcTextureType.BEARD, beardPatternId);
        if(foundBeardPattern.isPresent() && foundBeardPattern.get().value() instanceof NpcTexturePattern pattern){
            npcEntityTextureData = npcEntityTextureData.withBeardTexture(NpcTextureData.buildId(beardPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcEntityTextureData = npcEntityTextureData.withBeardAddonTexture(NpcTextureData.buildAddonId(beardPatternId, globalHairMaterialId));
            }
        }
        return npcEntityTextureData;
    }

    public static NpcEntityTextureData generateClothingTextureData(NpcEntityTextureData npcEntityTextureData, NpcTextureData.Identity textureIdentity) {
        npcEntityTextureData = npcEntityTextureData.withClothingTexture(NpcTextureData.getTextureWithMaterial(textureIdentity, NpcTextureType.CLOTHING));

        return npcEntityTextureData;
    }
}
