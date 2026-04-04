package net.sevenstars.middleearth.resources.datas.texture_presets;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.sevenstars.api.dtos.WeightedIdentifier;
import net.sevenstars.api.dtos.WeightedPool;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;

import java.util.ArrayList;
import java.util.List;

public class TexturePreset {
    public final static WeightedIdentifier EMPTY_VALUE_KEY = new WeightedIdentifier(Identifier.of("empty"));

    public final static String PATTERNS = "patterns";
    public final static String MATERIALS = "materials";
    public final static String IS_EMISSIVE = "is_emissive";
    public final static String CLOTHES = "CLOTHES";

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
    }

    public TexturePreset(NbtCompound compound){
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
    }

    public NbtCompound getNbt(NbtCompound nbt) {

        if(skinMaterials.isFilled()){
            NbtCompound compound = new NbtCompound();
            compound.put(MATERIALS, createTextureElementList(skinMaterials));
            nbt.put(CharacterMaterialTypes.SKIN.name(), compound);
        }
        if(bodyPatterns.isFilled()){
            NbtCompound compound = new NbtCompound();
            compound.put(PATTERNS, createTextureElementList(bodyPatterns));
            nbt.put(CharacterPatternTypes.BODY.name(), compound);
        }
        if(feetPatterns.isFilled()){
            NbtCompound compound = new NbtCompound();
            compound.put(PATTERNS, createTextureElementList(feetPatterns));
            nbt.put(CharacterPatternTypes.FEET.name(), compound);
        }
        if(headPatterns.isFilled()){
            NbtCompound compound = new NbtCompound();
            compound.put(PATTERNS, createTextureElementList(headPatterns));
            nbt.put(CharacterPatternTypes.HEAD.name(), compound);
        }
        if(scarPatterns.isFilled()){
            NbtCompound compound = new NbtCompound();
            compound.put(PATTERNS, createTextureElementList(scarPatterns));
            nbt.put(CharacterPatternTypes.SCAR.name(), compound);
        }
        if(earPatterns.isFilled()){
            NbtCompound compound = new NbtCompound();
            compound.put(PATTERNS, createTextureElementList(earPatterns));
            nbt.put(CharacterPatternTypes.EAR.name(), compound);
        }
        if(nosePatterns.isFilled()){
            NbtCompound compound = new NbtCompound();
            compound.put(PATTERNS, createTextureElementList(nosePatterns));
            nbt.put(CharacterPatternTypes.NOSE.name(), compound);
        }
        if(eyePatterns.isFilled() || eyeMaterials.isFilled()){
            NbtCompound compound = new NbtCompound();

            compound.put(PATTERNS, createTextureElementList(eyePatterns));
            compound.put(MATERIALS, createTextureElementList(eyeMaterials));

            if(haveEmissiveEyes)
                compound.putBoolean(IS_EMISSIVE, true);

            nbt.put(CharacterPatternTypes.EYE.name(), compound);
        }
        if(hairPatterns.isFilled() || hairMaterials.isFilled()){
            NbtCompound compound = new NbtCompound();

            compound.put(PATTERNS, createTextureElementList(hairPatterns));
            compound.put(MATERIALS, createTextureElementList(hairMaterials));

            nbt.put(CharacterMaterialTypes.HAIR.name(), compound);
        }
        if(eyebrowPatterns.isFilled()){
            NbtCompound compound = new NbtCompound();
            compound.put(PATTERNS, createTextureElementList(eyebrowPatterns));
            nbt.put(CharacterPatternTypes.EYEBROW.name(), compound);
        }
        if(beardPatterns.isFilled()){
            NbtCompound compound = new NbtCompound();
            compound.put(PATTERNS, createTextureElementList(beardPatterns));
            nbt.put(CharacterPatternTypes.BEARD.name(), compound);
        }
        if(characterClothePresets.isFilled()){
            var list = new NbtList();
            for(int i = 0; i < this.characterClothePresets.size(); i++){
                list.add(i, characterClothePresets.get(i).getNbt());
            }
            nbt.put(CLOTHES, list);
        }
        return nbt;
    }


    private NbtList createTextureElementList(WeightedPool<WeightedIdentifier> values){
        return values.getNbt();
    }

    private void fetchMaterials(NbtCompound compound, CharacterMaterialTypes type){
        if(compound.contains(type.name())){
            NbtCompound value = compound.getCompound(type.name()).get();

            if(value.contains(MATERIALS)){
                var materials = value.get(MATERIALS);
                if(materials != null){
                    NbtList nbtList = materials.asNbtList().get();

                    List<NbtElement> fetchedValues = nbtList.stream().toList();
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


    private void fetchPatterns(NbtCompound compound, CharacterPatternTypes type){
        if(compound.contains(type.name())){
            NbtCompound value = compound.getCompound(type.name()).get();
            if(value.contains(PATTERNS)){
                var patterns = value.get(PATTERNS);
                if(patterns != null){
                    NbtList nbtList = patterns.asNbtList().get();

                    List<NbtElement> fetchedValues = nbtList.stream().toList();
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
                this.haveEmissiveEyes = value.getBoolean(IS_EMISSIVE).get();
            }
        }
    }

    private void fetchClothes(NbtCompound compound){
        if(compound.contains(CLOTHES)){
            if(compound.getList(CLOTHES).isPresent()){
                NbtList listClothePresets = compound.getList(CLOTHES).get();

                listClothePresets.forEach(x -> {
                    if(x.asCompound().isPresent()){
                        this.characterClothePresets.add(new WeightedClothingPresetHolder(x.asCompound().get()));
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
