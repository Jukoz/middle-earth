package net.sevenstars.middleearth.resources.datas.texture_presets;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.sevenstars.api.dtos.WeightedList;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.common.WeightedIdentifier;

import java.util.*;

public class TexturePresetData {
    public final static String WEIGHT = "weight";
    public final static String PATTERNS = "patterns";
    public final static String MATERIALS = "materials";
    public final static String IS_EMISSIVE = "is_emissive";
    public final static String CLOTHES = "CLOTHES";

    public final static WeightedIdentifier EMPTY_VALUE_KEY = new WeightedIdentifier(Identifier.of("empty"));

    private int weight;
    WeightedList<WeightedIdentifier> bodyPatterns;
    WeightedList<WeightedIdentifier> headPatterns;
    WeightedList<WeightedIdentifier> feetPatterns;
    WeightedList<WeightedIdentifier> scarPatterns;
    WeightedList<WeightedIdentifier> earPatterns;
    WeightedList<WeightedIdentifier> nosePatterns;
    WeightedList<WeightedIdentifier> eyePatterns;
    WeightedList<WeightedIdentifier> hairPatterns;
    WeightedList<WeightedIdentifier> eyebrowPatterns;
    WeightedList<WeightedIdentifier> beardPatterns;
    WeightedList<WeightedIdentifier> skinMaterials;
    WeightedList<WeightedIdentifier> eyeMaterials;
    WeightedList<WeightedIdentifier> hairMaterials;

    WeightedList<ClothePresetDatas> characterClothePresets;

    private boolean haveEmissiveEyes;

    public TexturePresetData(){
        this.weight = 1;
        bodyPatterns = new WeightedList<>();
        headPatterns = new WeightedList<>();
        scarPatterns = new WeightedList<>();
        earPatterns = new WeightedList<>();
        nosePatterns = new WeightedList<>();
        feetPatterns = new WeightedList<>();
        skinMaterials = new WeightedList<>();

        eyePatterns = new WeightedList<>();
        eyeMaterials = new WeightedList<>();

        hairPatterns = new WeightedList<>();
        eyebrowPatterns = new WeightedList<>();
        beardPatterns = new WeightedList<>();
        hairMaterials = new WeightedList<>();

        characterClothePresets = new WeightedList<>();
    }

    public TexturePresetData(NbtCompound compound) {
        this.weight = compound.contains(WEIGHT) ? compound.getInt(WEIGHT).get() : 1;

        bodyPatterns = new WeightedList<>();
        headPatterns = new WeightedList<>();
        feetPatterns = new WeightedList<>();
        scarPatterns = new WeightedList<>();
        earPatterns = new WeightedList<>();
        nosePatterns = new WeightedList<>();
        skinMaterials = new WeightedList<>();

        eyePatterns = new WeightedList<>();
        eyeMaterials = new WeightedList<>();

        hairPatterns = new WeightedList<>();
        eyebrowPatterns = new WeightedList<>();
        beardPatterns = new WeightedList<>();
        hairMaterials = new WeightedList<>();

        characterClothePresets = new WeightedList<>();

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

        loadHeadPool(compound);
    }

    private void loadHeadPool(NbtCompound compound) {

    }

    public NbtCompound getNbt(){
        NbtCompound nbt = new NbtCompound();
        if(weight != 1){
            nbt.putInt(WEIGHT, weight);
        }
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

    private NbtList createTextureElementList(WeightedList<WeightedIdentifier> values){
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
                        weightedList.add(new WeightedIdentifier(fetchedValue.asCompound().get()));
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
                        weightedIdentifiers.add(new WeightedIdentifier(fetchedValue.asCompound().get()));
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
                        this.characterClothePresets.add(new ClothePresetDatas(x.asCompound().get()));
                    }
                });
            }
        }
    }

    private List<String> getStringListFromNbtList(NbtCompound nbt, String name){
        NbtList nbtList = nbt.getList(name).get();
        List<String> fetchedValues = new ArrayList<>();
        for(int i = 0; i < nbtList.size(); i++){
            fetchedValues.add(nbtList.getString(i).get());
        }
        return fetchedValues;
    }

    public TexturePresetData withWeight(int weight){
        this.weight = weight;
        return this;
    }

    public TexturePresetData withEmissiveEyes(boolean value){
        this.haveEmissiveEyes = value;
        return this;
    }

    public TexturePresetData withClothes(List<ClothePresetDatas> characterClothePresets){
        if(characterClothePresets != null){
            this.characterClothePresets.addAll(characterClothePresets);
        }
        return this;
    }

    public TexturePresetData clearClothes(){
        this.characterClothePresets.clear();
        return this;
    }

    public TexturePresetData withPatterns(CharacterPatternTypes type, List<WeightedIdentifier> patterns){
        if(patterns != null)
            patterns.forEach(x -> {
                if(x == null){
                    addToPattern(type, EMPTY_VALUE_KEY);
                } else {
                    addToPattern(type, x);
                }
            });
        return this;
    }

    public TexturePresetData withPatterns(CharacterPatternTypes type, WeightedList<WeightedIdentifier> patterns){
        return withPatterns(type, patterns.elements);
    }

    public TexturePresetData clearPatterns(CharacterPatternTypes type) {
        clearAllPatterns(type);
        return this;
    }
    public TexturePresetData withMaterials(CharacterMaterialTypes type, List<WeightedIdentifier> materials){
        materials.forEach(x -> addToMaterial(type, x));
        return this;
    }
    public TexturePresetData withMaterials(CharacterMaterialTypes type, WeightedList<WeightedIdentifier> materials){
        return this.withMaterials(type, materials.elements);
    }

    public TexturePresetData clearMaterials(CharacterMaterialTypes type) {
        clearAllMaterials(type);
        return this;
    }

    public int getWeight(){
        return weight;
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

    public WeightedList<WeightedIdentifier> getPatterns(CharacterPatternTypes patternTypes) {
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
    public WeightedList<WeightedIdentifier> getMaterials(CharacterMaterialTypes materialType) {
        return switch (materialType) {
            case SKIN -> skinMaterials;
            case EYE -> eyeMaterials;
            case HAIR -> hairMaterials;
        };
    }

    public Boolean haveEmissiveEyes() {
        return haveEmissiveEyes;
    }

    public TexturePresetData copy() {
        var copiedNpcTextureDataPreset = new TexturePresetData();
        for(WeightedIdentifier value : getMaterials(CharacterMaterialTypes.SKIN).elements)
            copiedNpcTextureDataPreset.addToMaterial(CharacterMaterialTypes.SKIN, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.HEAD).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.HEAD, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.BODY).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.BODY, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.FEET).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.FEET, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.NOSE).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.NOSE, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.EAR).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.EAR, value);

        for(WeightedIdentifier value : getMaterials(CharacterMaterialTypes.EYE).elements)
            copiedNpcTextureDataPreset.addToMaterial(CharacterMaterialTypes.EYE, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.EYE).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.EYE, value);

        for(WeightedIdentifier value : getMaterials(CharacterMaterialTypes.HAIR).elements)
            copiedNpcTextureDataPreset.addToMaterial(CharacterMaterialTypes.HAIR, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.EYEBROW).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.EYEBROW, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.HAIR).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.HAIR, value);
        for(WeightedIdentifier value : getPatterns(CharacterPatternTypes.BEARD).elements)
            copiedNpcTextureDataPreset.addToPattern(CharacterPatternTypes.BEARD, value);

        copiedNpcTextureDataPreset.characterClothePresets = characterClothePresets;

        return copiedNpcTextureDataPreset;
    }

    public ClothingData getClothingData() {
        if(characterClothePresets.isEmpty()){
            MiddleEarth.LOGGER.logDebugMsg("Couldn't find clothes for " + this.getNbt());
            return new ClothingData(null, null, null);
        }
        ClothePresetDatas clothePreset = characterClothePresets.getRandom();

        Identifier baseId = clothePreset.getRandomBase();
        Identifier overId = clothePreset.getRandomOver();
        Identifier extraId = clothePreset.getRandomExtra();

        return new ClothingData(baseId, overId, extraId);
    }
}
