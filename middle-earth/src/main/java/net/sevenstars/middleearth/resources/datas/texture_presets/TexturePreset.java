package net.sevenstars.middleearth.resources.datas.texture_presets;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.api.dtos.WeightedIdentifier;
import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;

import java.util.ArrayList;
import java.util.List;

public class TexturePreset {
    public final static WeightedIdentifier EMPTY_VALUE_KEY = new WeightedIdentifier(ResourceLocation.parse("empty"));

    public final static String PATTERNS = "patterns";
    public final static String MATERIALS = "materials";
    public final static String IS_EMISSIVE = "is_emissive";
    public final static String CLOTHES = "clothes";
    public final static String SIMPLIFIED = "simplified";

    public WeightedPool<WeightedIdentifier> bodyPatterns;
    public WeightedPool<WeightedIdentifier> headPatterns;
    public WeightedPool<WeightedIdentifier> feetPatterns;
    public WeightedPool<WeightedIdentifier> scarPatterns;
    public WeightedPool<WeightedIdentifier> earPatterns;
    public WeightedPool<WeightedIdentifier> nosePatterns;
    public WeightedPool<WeightedIdentifier> eyePatterns;
    public WeightedPool<WeightedIdentifier> hairPatterns;
    public WeightedPool<WeightedIdentifier> eyebrowPatterns;
    public WeightedPool<WeightedIdentifier> beardPatterns;
    public WeightedPool<WeightedIdentifier> skinMaterials;
    public WeightedPool<WeightedIdentifier> eyeMaterials;
    public WeightedPool<WeightedIdentifier> hairMaterials;
    public WeightedPool<WeightedSimplifiedTexturePresetHolder> simplifiedTextures;

    public WeightedPool<WeightedClothingPresetHolder> characterClothePresets;

    public boolean haveEmissiveEyes;

    public TexturePreset(){
        bodyPatterns = new WeightedPool<>();
        headPatterns = new WeightedPool<>();
        scarPatterns = new WeightedPool<>();
        earPatterns = new WeightedPool<>();
        nosePatterns = new WeightedPool<>();
        feetPatterns = new WeightedPool<>();
        skinMaterials = new WeightedPool<>();

        eyePatterns = new WeightedPool<>();
        eyeMaterials = new WeightedPool<>();

        hairPatterns = new WeightedPool<>();
        eyebrowPatterns = new WeightedPool<>();
        beardPatterns = new WeightedPool<>();
        hairMaterials = new WeightedPool<>();

        characterClothePresets = new WeightedPool<>();

        simplifiedTextures = new WeightedPool<>();
    }

    public TexturePreset(CompoundTag compound){
        this();

        characterClothePresets = new WeightedPool<>();

        fetchMaterials(compound, CharacterMaterialTypes.SKIN);
        fetchMaterials(compound, CharacterMaterialTypes.EYE);
        fetchMaterials(compound, CharacterMaterialTypes.HAIR);

        fetchPatterns(compound, CharacterPatternTypes.FEET);
        fetchPatterns(compound, CharacterPatternTypes.BODY);
        fetchPatterns(compound, CharacterPatternTypes.HEAD);
        fetchPatterns(compound, CharacterPatternTypes.SCAR);
        fetchPatterns(compound, CharacterPatternTypes.EAR);
        fetchPatterns(compound, CharacterPatternTypes.NOSE);

        fetchPatterns(compound, CharacterPatternTypes.EYE);

        fetchPatterns(compound, CharacterPatternTypes.HAIR);
        fetchPatterns(compound, CharacterPatternTypes.EYEBROW);
        fetchPatterns(compound, CharacterPatternTypes.BEARD);

        fetchClothes(compound);
        fetchSimplifiedTextures(compound);
    }


    public CompoundTag getNbt(CompoundTag nbt) {

        if(skinMaterials.isFilled()){
            CompoundTag compound = new CompoundTag();
            compound.put(MATERIALS, createTextureElementList(skinMaterials));
            nbt.put(CharacterMaterialTypes.SKIN.name(), compound);
        }
        if(bodyPatterns.isFilled()){
            CompoundTag compound = new CompoundTag();
            compound.put(PATTERNS, createTextureElementList(bodyPatterns));
            nbt.put(CharacterPatternTypes.BODY.name(), compound);
        }
        if(feetPatterns.isFilled()){
            CompoundTag compound = new CompoundTag();
            compound.put(PATTERNS, createTextureElementList(feetPatterns));
            nbt.put(CharacterPatternTypes.FEET.name(), compound);
        }
        if(headPatterns.isFilled()){
            CompoundTag compound = new CompoundTag();
            compound.put(PATTERNS, createTextureElementList(headPatterns));
            nbt.put(CharacterPatternTypes.HEAD.name(), compound);
        }
        if(scarPatterns.isFilled()){
            CompoundTag compound = new CompoundTag();
            compound.put(PATTERNS, createTextureElementList(scarPatterns));
            nbt.put(CharacterPatternTypes.SCAR.name(), compound);
        }
        if(earPatterns.isFilled()){
            CompoundTag compound = new CompoundTag();
            compound.put(PATTERNS, createTextureElementList(earPatterns));
            nbt.put(CharacterPatternTypes.EAR.name(), compound);
        }
        if(nosePatterns.isFilled()){
            CompoundTag compound = new CompoundTag();
            compound.put(PATTERNS, createTextureElementList(nosePatterns));
            nbt.put(CharacterPatternTypes.NOSE.name(), compound);
        }
        if(eyePatterns.isFilled() || eyeMaterials.isFilled()){
            CompoundTag compound = new CompoundTag();

            compound.put(PATTERNS, createTextureElementList(eyePatterns));
            compound.put(MATERIALS, createTextureElementList(eyeMaterials));

            if(haveEmissiveEyes)
                compound.putBoolean(IS_EMISSIVE, true);

            nbt.put(CharacterPatternTypes.EYE.name(), compound);
        }
        if(hairPatterns.isFilled() || hairMaterials.isFilled()){
            CompoundTag compound = new CompoundTag();

            compound.put(PATTERNS, createTextureElementList(hairPatterns));
            compound.put(MATERIALS, createTextureElementList(hairMaterials));

            nbt.put(CharacterMaterialTypes.HAIR.name(), compound);
        }
        if(eyebrowPatterns.isFilled()){
            CompoundTag compound = new CompoundTag();
            compound.put(PATTERNS, createTextureElementList(eyebrowPatterns));
            nbt.put(CharacterPatternTypes.EYEBROW.name(), compound);
        }
        if(beardPatterns.isFilled()){
            CompoundTag compound = new CompoundTag();
            compound.put(PATTERNS, createTextureElementList(beardPatterns));
            nbt.put(CharacterPatternTypes.BEARD.name(), compound);
        }
        if(characterClothePresets.isFilled()){
            nbt.put(CLOTHES, characterClothePresets.getNbt());
        }
        if(simplifiedTextures.isFilled()){
            nbt.put(SIMPLIFIED, simplifiedTextures.getNbt());
        }
        return nbt;
    }


    private ListTag createTextureElementList(WeightedPool<WeightedIdentifier> values){
        return values.getNbt();
    }

    private void fetchMaterials(CompoundTag compound, CharacterMaterialTypes type){
        if(compound.contains(type.name())){
            CompoundTag value = compound.getCompound(type.name());

            if(value.contains(MATERIALS)){
                var materials = value.get(MATERIALS);
                if(materials instanceof ListTag nbtList){
                    List<Tag> fetchedValues = nbtList.stream().toList();
                    List<WeightedIdentifier> weightedList = new ArrayList<>();

                    for(var fetchedValue : fetchedValues){
                        weightedList.add(new WeightedIdentifier(fetchedValue));
                    }

                    switch (type){
                        case SKIN -> skinMaterials.addAll(weightedList);
                        case EYE -> eyeMaterials.addAll(weightedList);
                        case HAIR -> hairMaterials.addAll(weightedList);
                    }
                }
            }
        }
    }


    private void fetchPatterns(CompoundTag compound, CharacterPatternTypes type){
        if(compound.contains(type.name())){
            CompoundTag value = compound.getCompound(type.name());
            if(value.contains(PATTERNS)){
                var patterns = value.get(PATTERNS);
                if(patterns instanceof ListTag nbtList){
                    List<Tag> fetchedValues = nbtList.stream().toList();
                    List<WeightedIdentifier> weightedIdentifiers = new ArrayList<>();

                    for(var fetchedValue : fetchedValues){
                        weightedIdentifiers.add(new WeightedIdentifier(fetchedValue));
                    }

                    switch (type){
                        case BODY       -> bodyPatterns     .addAll(weightedIdentifiers);
                        case HEAD       -> headPatterns     .addAll(weightedIdentifiers);
                        case FEET       -> feetPatterns     .addAll(weightedIdentifiers);
                        case SCAR       -> scarPatterns     .addAll(weightedIdentifiers);
                        case EAR        -> earPatterns      .addAll(weightedIdentifiers);
                        case NOSE       -> nosePatterns     .addAll(weightedIdentifiers);
                        case EYE        -> eyePatterns      .addAll(weightedIdentifiers);
                        case HAIR       -> hairPatterns     .addAll(weightedIdentifiers);
                        case EYEBROW    -> eyebrowPatterns  .addAll(weightedIdentifiers);
                        case BEARD      -> beardPatterns    .addAll(weightedIdentifiers);
                    }
                }
            }

            if(type == CharacterPatternTypes.EYE && value.contains(IS_EMISSIVE)){
                this.haveEmissiveEyes = value.getBoolean(IS_EMISSIVE);
            }
        }
    }

    private void fetchClothes(CompoundTag compound){
        if(compound.contains(CLOTHES)){
            if(compound.get(CLOTHES) instanceof ListTag listClothePresets){
                listClothePresets.forEach(x -> {
                    if(x instanceof CompoundTag clothingPreset){
                        this.characterClothePresets.add(new WeightedClothingPresetHolder(clothingPreset));
                    }
                });
            }
        }
    }


    private void fetchSimplifiedTextures(CompoundTag compound) {
        if(compound.contains(SIMPLIFIED)){
            if (compound.get(SIMPLIFIED) instanceof ListTag listSimplifiedTextures) {
                listSimplifiedTextures.forEach(x -> {
                    if (x instanceof CompoundTag simplifiedTexture) {
                        this.simplifiedTextures.add(new WeightedSimplifiedTexturePresetHolder(simplifiedTexture));
                    }
                });
            }
        }
    }

    public void withEmissiveEyes(boolean value){
        this.haveEmissiveEyes = value;
    }

    public void withClothes(List<WeightedClothingPresetHolder> characterClothePresets){
        if(characterClothePresets != null){
            this.characterClothePresets.addAll(characterClothePresets);
        }
    }

    public void clearClothes(){
        this.characterClothePresets.clear();
    }

    public void withSimplifiedTextures(List<WeightedSimplifiedTexturePresetHolder> simplifiedTextures){
        if(simplifiedTextures != null){
            this.simplifiedTextures.addAll(simplifiedTextures);
        }
    }

    public void clearSimplifiedTextures(){
        this.simplifiedTextures.clear();
    }

    public void withPatterns(CharacterPatternTypes type, List<WeightedIdentifier> patterns){
        if(patterns != null)
            patterns.forEach(x -> {
                if(x == null){
                    addToPattern(type, EMPTY_VALUE_KEY);
                } else {
                    addToPattern(type, x);
                }
            });
    }

    public void withPatterns(CharacterPatternTypes type, WeightedPool<WeightedIdentifier> patterns){
        withPatterns(type, patterns.elements);
    }

    public void clearPatterns(CharacterPatternTypes type) {
        clearAllPatterns(type);
    }
    public void withMaterials(CharacterMaterialTypes type, List<WeightedIdentifier> materials){
        materials.forEach(x -> addToMaterial(type, x));
    }
    public void withMaterials(CharacterMaterialTypes type, WeightedPool<WeightedIdentifier> materials){
        this.withMaterials(type, materials.elements);
    }

    public void clearMaterials(CharacterMaterialTypes type) {
        clearAllMaterials(type);
    }

    public void addToPattern(CharacterPatternTypes patternType, WeightedIdentifier value) {
        switch (patternType){
            case BODY       -> bodyPatterns     .add(value);
            case HEAD       -> headPatterns     .add(value);
            case FEET       -> feetPatterns     .add(value);
            case SCAR       -> scarPatterns     .add(value);
            case EAR        -> earPatterns      .add(value);
            case NOSE       -> nosePatterns     .add(value);
            case EYE        -> eyePatterns      .add(value);
            case HAIR       -> hairPatterns     .add(value);
            case EYEBROW    -> eyebrowPatterns  .add(value);
            case BEARD      -> beardPatterns    .add(value);
        };
    }
    private void clearAllPatterns(CharacterPatternTypes patternType) {
        switch (patternType){
            case BODY       -> bodyPatterns     .clear();
            case HEAD       -> headPatterns     .clear();
            case FEET       -> feetPatterns     .clear();
            case SCAR       -> scarPatterns     .clear();
            case EAR        -> earPatterns      .clear();
            case NOSE       -> nosePatterns     .clear();
            case EYE        -> eyePatterns      .clear();
            case HAIR       -> hairPatterns     .clear();
            case EYEBROW    -> eyebrowPatterns  .clear();
            case BEARD      -> beardPatterns    .clear();
        };
    }

    public void addToMaterial(CharacterMaterialTypes materialType, WeightedIdentifier value) {
        switch (materialType){
            case SKIN -> skinMaterials.add(value);
            case EYE -> eyeMaterials.add(value);
            case HAIR -> hairMaterials.add(value);
        };
    }
    private void clearAllMaterials(CharacterMaterialTypes materialType) {
        switch (materialType){
            case SKIN -> skinMaterials.clear();
            case EYE -> eyeMaterials.clear();
            case HAIR -> hairMaterials.clear();
        };
    }

    public WeightedPool<WeightedIdentifier> getPatterns(CharacterPatternTypes patternTypes) {
        return switch (patternTypes){
            case BODY       -> bodyPatterns;
            case HEAD       -> headPatterns;
            case FEET       -> feetPatterns;
            case SCAR       -> scarPatterns;
            case EAR        -> earPatterns;
            case NOSE       -> nosePatterns;
            case EYE        -> eyePatterns;
            case HAIR       -> hairPatterns;
            case EYEBROW    -> eyebrowPatterns;
            case BEARD      -> beardPatterns;
        };
    }
    public WeightedPool<WeightedIdentifier> getMaterials(CharacterMaterialTypes materialType) {
        return switch (materialType) {
            case SKIN -> skinMaterials;
            case EYE -> eyeMaterials;
            case HAIR -> hairMaterials;
        };
    }

    public Boolean haveEmissiveEyes() {
        return haveEmissiveEyes;
    }



}
