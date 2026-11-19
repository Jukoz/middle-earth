package net.sevenstars.middleearth.features.npcs.initializer;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.data.NpcTextureData;
import net.sevenstars.middleearth.registries.CharacterPatternsRegistryME;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.features.npcs.classes.NpcClass;
import net.sevenstars.middleearth.features.npcs.classes.NpcUtil;
import net.sevenstars.middleearth.features.npcs.classes.data.MountData;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTexturePattern;
import net.sevenstars.middleearth.resources.datas.texture_presets.ClothingSelection;
import net.sevenstars.middleearth.resources.datas.texture_presets.TexturePresetDataPool;

import java.util.Optional;

public class NpcGenerator {
    private static final String EMISSIVE_SUFFIX = "_emissive";

    public static void generateCharacterTextures(World world, NpcEntity npcEntity) {
        String currentStep = "Fetching datas";
        Identifier currentNpcClassId = npcEntity.getNpcClassIdentifier();

        try{
            currentStep = "Aligning data with npc entity...";
            npcEntity.prepareNpcIdentifier(currentNpcClassId);
            npcEntity.prepare();
            NpcClass currentNpcClass = npcEntity.getNpcClass();

            currentStep = "Applying attributes...";
            currentNpcClass.applyAttributes(npcEntity);

            currentStep = "Getting npc texture data...";
            TexturePresetDataPool textureData = currentNpcClass.getNpcTextureData(world);

            currentStep = "Creating texture identity...";
            TexturePresetDataPool.Identity identity = TexturePresetDataPool.Identity.create(textureData, npcEntity.getNpcCategory());
            if(identity == null)
                identity = TexturePresetDataPool.Identity.create(textureData);

            NpcTextureData entityTextureData = new NpcTextureData();
            currentStep = "Generating skin...";
            entityTextureData = generateSkinTextureData(entityTextureData, identity);
            currentStep = "Generating eyes...";
            entityTextureData = generateEyeTextureData(entityTextureData, identity, currentNpcClass.getNpcTextureData(world).haveEmissiveEyes(identity));
            currentStep = "Generating hair...";
            entityTextureData = generateHairTextureData(entityTextureData, identity, world.getRegistryManager());
            currentStep = "Generating clothing...";
            entityTextureData = generateClothingTextureData(entityTextureData, identity);
            npcEntity.saveNpcTextureData(entityTextureData);

            NpcUtil.equipAll(npcEntity, currentNpcClass.getGear());
            npcEntity.updateAttackType();
            spawnMount(npcEntity);

        } catch (Exception exception){
            MiddleEarth.LOGGER.logError(String.format("NpcEntityInitializer::Couldn't generate %s | Triggered by %s", currentNpcClassId, currentStep), exception);
            // Stops tryToInitializeData retrying every tick
            npcEntity.giveUpInitializing();
        }
    }

    private static void spawnMount(NpcEntity entity) {
        if(entity.getEntityWorld() instanceof ServerWorld serverWorld)
        {
            NpcClass npcClass = entity.getNpcClass();
            if(npcClass == null || !npcClass.hasMount())
                return;
            MountData mountData = npcClass.getMountData();
            mountData.createEntity(serverWorld, entity);
        }
    }

    public static NpcTextureData generateSkinTextureData(NpcTextureData npcTextureData, TexturePresetDataPool.Identity textureIdentity) {
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

    public static NpcTextureData generateEyeTextureData(NpcTextureData npcTextureData, TexturePresetDataPool.Identity textureIdentity, boolean haveEmissiveEyes) {
        Identifier materialId = TexturePresetDataPool.getRawMaterial(textureIdentity, CharacterMaterialTypes.EYE);
        Identifier patternId = TexturePresetDataPool.getRawPattern(textureIdentity, CharacterPatternTypes.EYE);

        npcTextureData = npcTextureData.withEyeTexture(TexturePresetDataPool.buildId(patternId, materialId), TexturePresetDataPool.buildId(Identifier.of(patternId.getPath() + EMISSIVE_SUFFIX), materialId), haveEmissiveEyes);

        return npcTextureData;
    }

    public static NpcTextureData generateHairTextureData(NpcTextureData npcTextureData, TexturePresetDataPool.Identity textureIdentity, DynamicRegistryManager manager) {
        Identifier globalHairMaterialId = TexturePresetDataPool.getRawMaterial(textureIdentity, CharacterMaterialTypes.HAIR);

        // Hair
        Identifier hairPatternId = TexturePresetDataPool.getRawPattern(textureIdentity, CharacterPatternTypes.HAIR);
        Optional<RegistryEntry.Reference<CharacterTexturePattern>> foundHairPattern = CharacterPatternsRegistryME.get(manager, CharacterPatternTypes.HAIR, hairPatternId);
        if(foundHairPattern.isPresent() && foundHairPattern.get().value() instanceof CharacterTexturePattern pattern){
            npcTextureData = npcTextureData.withHairTexture(TexturePresetDataPool.buildId(hairPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcTextureData = npcTextureData.withHairAddonTexture(TexturePresetDataPool.buildAddonId(hairPatternId, globalHairMaterialId));
            }
        }
        // Eyebrow
        Identifier eyebrowPatternId = TexturePresetDataPool.getRawPattern(textureIdentity, CharacterPatternTypes.EYEBROW);
        Optional<RegistryEntry.Reference<CharacterTexturePattern>> foundEyebrowPattern = CharacterPatternsRegistryME.get(manager, CharacterPatternTypes.EYEBROW, eyebrowPatternId);
        if(foundEyebrowPattern.isPresent()){
            npcTextureData = npcTextureData.withEyebrowTexture(TexturePresetDataPool.buildId(eyebrowPatternId, globalHairMaterialId));
        }
        // Beard
        Identifier beardPatternId = TexturePresetDataPool.getRawPattern(textureIdentity, CharacterPatternTypes.BEARD);
        Optional<RegistryEntry.Reference<CharacterTexturePattern>> foundBeardPattern = CharacterPatternsRegistryME.get(manager, CharacterPatternTypes.BEARD, beardPatternId);
        if(foundBeardPattern.isPresent() && foundBeardPattern.get().value() instanceof CharacterTexturePattern pattern){
            npcTextureData = npcTextureData.withBeardTexture(TexturePresetDataPool.buildId(beardPatternId, globalHairMaterialId));
            if(pattern.hasAddonRawValue()){
                npcTextureData = npcTextureData.withBeardAddonTexture(TexturePresetDataPool.buildAddonId(beardPatternId, globalHairMaterialId));
            }
        }
        return npcTextureData;
    }

    public static NpcTextureData generateClothingTextureData(NpcTextureData npcTextureData, TexturePresetDataPool.Identity textureIdentity) {
        ClothingSelection data = TexturePresetDataPool.getClothing(textureIdentity);
        npcTextureData = npcTextureData.withClothingTexture(data.base(), data.over(), data.extra());

        return npcTextureData;
    }
}
