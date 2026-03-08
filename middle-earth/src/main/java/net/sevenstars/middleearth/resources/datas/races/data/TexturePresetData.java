package net.sevenstars.middleearth.resources.datas.races.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.CharacterPatternsME;
import net.sevenstars.middleearth.resources.datas.npcs.data.CharacterClothingData;
import net.sevenstars.middleearth.resources.datas.npcs.data.ClothePresets;
import net.sevenstars.middleearth.resources.datas.npcs.data.TexturePresets;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureMaterial;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTexturePattern;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TexturePresetData {
    public final static String EMPTY_VALUE_KEY = "NONE";
    private int weight;
    List<String> bodyPatterns;
    List<String> headPatterns;
    List<String> scarPatterns;
    List<String> earPatterns;
    List<String> nosePatterns;
    List<String> eyePatterns;
    List<String> hairPatterns;
    List<String> eyebrowPatterns;
    List<String> beardPatterns;

    List<String> skinMaterials;
    List<String> eyeMaterials;
    List<String> hairMaterials;

    List<ClothePresets> clothePresets;

    private boolean haveEmissiveEyes;

    public TexturePresetData(){
        this.weight = 1;
        bodyPatterns = new ArrayList<>();
        headPatterns = new ArrayList<>();
        scarPatterns = new ArrayList<>();
        earPatterns = new ArrayList<>();
        nosePatterns = new ArrayList<>();
        skinMaterials = new ArrayList<>();

        eyePatterns = new ArrayList<>();
        eyeMaterials = new ArrayList<>();

        hairPatterns = new ArrayList<>();
        eyebrowPatterns = new ArrayList<>();
        beardPatterns = new ArrayList<>();
        hairMaterials = new ArrayList<>();

        clothePresets = new ArrayList<>();
    }

    public TexturePresetData(NbtCompound compound) {
        this.weight = compound.contains("weight") ? compound.getInt("weight").get() : 1;

        bodyPatterns = new ArrayList<>();
        headPatterns = new ArrayList<>();
        scarPatterns = new ArrayList<>();
        earPatterns = new ArrayList<>();
        nosePatterns = new ArrayList<>();
        skinMaterials = new ArrayList<>();

        eyePatterns = new ArrayList<>();
        eyeMaterials = new ArrayList<>();

        hairPatterns = new ArrayList<>();
        eyebrowPatterns = new ArrayList<>();
        beardPatterns = new ArrayList<>();
        hairMaterials = new ArrayList<>();

        clothePresets = new ArrayList<>();

        fetchElements(compound, NpcTextureType.SKIN);
        fetchElements(compound, NpcTextureType.BODY);
        fetchElements(compound, NpcTextureType.HEAD);
        fetchElements(compound, NpcTextureType.SCAR);
        fetchElements(compound, NpcTextureType.EAR);
        fetchElements(compound, NpcTextureType.NOSE);
        fetchElements(compound, NpcTextureType.EYE);
        fetchElements(compound, NpcTextureType.HAIR);
        fetchElements(compound, NpcTextureType.EYEBROW);
        fetchElements(compound, NpcTextureType.BEARD);
        fetchElements(compound, NpcTextureType.CLOTHE_PRESETS);

        loadHeadPool(compound);
    }

    private void loadHeadPool(NbtCompound compound) {

    }

    public NbtCompound getNbt(){
        NbtCompound nbt = new NbtCompound();
        if(weight != 1){
            nbt.putInt("weight", weight);
        }
        if(isListFilled(skinMaterials)){
            NbtCompound compound = new NbtCompound();
            compound.put("materials", createStringList(skinMaterials));
            nbt.put(NpcTextureType.SKIN.name(), compound);
        }
        if(isListFilled(bodyPatterns)){
            NbtCompound compound = new NbtCompound();
            compound.put("patterns", createStringList(bodyPatterns));
            nbt.put(NpcTextureType.BODY.name(), compound);
        }
        if(isListFilled(headPatterns)){
            NbtCompound compound = new NbtCompound();
            compound.put("patterns", createStringList(headPatterns));

            nbt.put(NpcTextureType.HEAD.name(), compound);
        }
        if(isListFilled(scarPatterns)){
            NbtCompound compound = new NbtCompound();
            compound.put("patterns", createStringList(scarPatterns));
            nbt.put(NpcTextureType.SCAR.name(), compound);
        }
        if(isListFilled(earPatterns)){
            NbtCompound compound = new NbtCompound();
            compound.put("patterns", createStringList(earPatterns));
            nbt.put(NpcTextureType.EAR.name(), compound);
        }
        if(isListFilled(nosePatterns)){
            NbtCompound compound = new NbtCompound();
            compound.put("patterns", createStringList(nosePatterns));
            nbt.put(NpcTextureType.NOSE.name(), compound);
        }
        if(isListFilled(eyePatterns) || isListFilled(eyeMaterials)){
            NbtCompound compound = new NbtCompound();

            compound.put("patterns", createStringList(eyePatterns));
            compound.put("materials", createStringList(eyeMaterials));

            if(haveEmissiveEyes)
                compound.putBoolean("is_emissive", true);

            nbt.put(NpcTextureType.EYE.name(), compound);
        }
        if(isListFilled(hairPatterns) || isListFilled(hairMaterials)){
            NbtCompound compound = new NbtCompound();

            compound.put("patterns", createStringList(hairPatterns));
            compound.put("materials", createStringList(hairMaterials));

            nbt.put(NpcTextureType.HAIR.name(), compound);
        }
        if(isListFilled(eyebrowPatterns)){
            NbtCompound compound = new NbtCompound();
            compound.put("patterns", createStringList(eyebrowPatterns));
            nbt.put(NpcTextureType.EYEBROW.name(), compound);
        }
        if(isListFilled(beardPatterns)){
            NbtCompound compound = new NbtCompound();
            compound.put("patterns", createStringList(beardPatterns));
            nbt.put(NpcTextureType.BEARD.name(), compound);
        }
        if(isListFilled(clothePresets)){
            var list = new NbtList();
            for(int i = 0; i < this.clothePresets.size(); i++){
                list.add(i, clothePresets.get(i).writeNbt());
            }

            nbt.put(NpcTextureType.CLOTHE_PRESETS.name(), list);
        }

        return nbt;
    }

    private <T> boolean isListFilled(List<T> listToCheck){
        return (listToCheck != null && !listToCheck.isEmpty());
    }

    private NbtList createStringList(List<String> values){
        NbtList nbtList = new NbtList();
        for(int i = 0; i < values.size(); i++){
            nbtList.add(i, NbtString.of(values.get(i)));
        }
        return nbtList;
    }

    private void fetchElements(NbtCompound compound, NpcTextureType type){
        if(compound.contains(type.name())){
            if(type == NpcTextureType.CLOTHE_PRESETS){
                if(compound.getList(NpcTextureType.CLOTHE_PRESETS.name()).isPresent()){
                    NbtList listClothePresets = compound.getList(NpcTextureType.CLOTHE_PRESETS.name()).get();

                    listClothePresets.forEach(x -> {
                        if(x.asCompound().isPresent())
                            this.clothePresets.add(new ClothePresets(x.asCompound().get()));
                    });
                }
                return;
            }

            NbtCompound value = compound.getCompound(type.name()).get();

            if(value.contains("patterns")){
                List<String> fetchedValues = getStringListFromNbtList(value, "patterns");
                switch (type){
                    case BODY -> bodyPatterns.addAll(fetchedValues);
                    case HEAD -> headPatterns.addAll(fetchedValues);
                    case SCAR -> scarPatterns.addAll(fetchedValues);
                    case EAR -> earPatterns.addAll(fetchedValues);
                    case NOSE -> nosePatterns.addAll(fetchedValues);
                    case EYE -> eyePatterns.addAll(fetchedValues);
                    case HAIR -> hairPatterns.addAll(fetchedValues);
                    case EYEBROW -> eyebrowPatterns.addAll(fetchedValues);
                    case BEARD -> beardPatterns.addAll(fetchedValues);
                }
            }
            if(value.contains("materials")){
                List<String> fetchedValues = getStringListFromNbtList(value, "materials");
                switch (type){
                    case SKIN -> skinMaterials.addAll(fetchedValues);
                    case EYE -> eyeMaterials.addAll(fetchedValues);
                    case HAIR -> hairMaterials.addAll(fetchedValues);
                }
            }

            if(type == NpcTextureType.EYE && value.contains("is_emissive")){
                this.haveEmissiveEyes = value.getBoolean("is_emissive").get();
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

    public TexturePresetData withClothes(List<ClothePresets> clothePresets){
        if(clothePresets != null){
            this.clothePresets.addAll(clothePresets);
        }
        return this;
    }

    public TexturePresetData clearClothes(){
        this.clothePresets.clear();
        return this;
    }

    public TexturePresetData withPatterns(NpcTextureType type, List<RegistryKey<NpcTexturePattern>> patterns){
        if(patterns != null)
            patterns.forEach(x -> {
                if(x == null){
                    addToPattern(type, EMPTY_VALUE_KEY);
                } else {
                    addToPattern(type, x.getValue().getPath());
                }
            });
        return this;
    }

    public TexturePresetData withPatternValues(NpcTextureType type, List<String> patterns){
        if(patterns != null)
            patterns.forEach(x -> {
                if(x == null){
                    addToPattern(type, EMPTY_VALUE_KEY);
                } else {
                    addToPattern(type, Identifier.of(x).getPath());
                }
            });
        return this;
    }
    public TexturePresetData overwritePatterns(NpcTextureType type, List<RegistryKey<NpcTexturePattern>> patterns){
        clearAllPatterns(type);
        withPatterns(type, patterns);
        return this;
    }
    public TexturePresetData clearPatterns(NpcTextureType type) {
        clearAllPatterns(type);
        return this;
    }
    public TexturePresetData withMaterials(NpcTextureType type, List<RegistryKey<NpcTextureMaterial>> materials){
        materials.forEach(x -> addToMaterial(type, x.getValue().getPath()));
        return this;
    }
    public TexturePresetData withMaterialValues(NpcTextureType type, List<String> materials){
        materials.forEach(x -> addToMaterial(type, Identifier.of(x).getPath()));
        return this;
    }
    public TexturePresetData overwriteMaterials(NpcTextureType type, List<RegistryKey<NpcTextureMaterial>> materials){
        clearAllMaterials(type);
        withMaterials(type, materials);
        return this;
    }
    public TexturePresetData clearMaterials(NpcTextureType type) {
        clearAllMaterials(type);
        return this;
    }

    public int getWeight(){
        return weight;
    }

    public void addToPattern(NpcTextureType npcTextureType, String value) {
        switch (npcTextureType){
            case BODY -> bodyPatterns.add(value);
            case HEAD -> headPatterns.add(value);
            case SCAR -> scarPatterns.add(value);
            case EAR -> earPatterns.add(value);
            case NOSE -> nosePatterns.add(value);
            case EYE -> eyePatterns.add(value);
            case HAIR -> hairPatterns.add(value);
            case EYEBROW -> eyebrowPatterns.add(value);
            case BEARD -> beardPatterns.add(value);
        };
    }
    private void clearAllPatterns(NpcTextureType npcTextureType) {
        switch (npcTextureType){
            case BODY -> bodyPatterns.clear();
            case HEAD -> headPatterns.clear();
            case SCAR -> scarPatterns.clear();
            case EAR -> earPatterns.clear();
            case NOSE -> nosePatterns.clear();
            case EYE -> eyePatterns.clear();
            case HAIR -> hairPatterns.clear();
            case EYEBROW -> eyebrowPatterns.clear();
            case BEARD -> beardPatterns.clear();
        };
    }

    public void addToMaterial(NpcTextureType npcTextureType, String value) {
        switch (npcTextureType){
            case SKIN -> skinMaterials.add(value);
            case EYE -> eyeMaterials.add(value);
            case HAIR, EYEBROW, BEARD -> hairMaterials.add(value);
        };
    }
    private void clearAllMaterials(NpcTextureType npcTextureType) {
        switch (npcTextureType){
            case SKIN -> skinMaterials.clear();
            case EYE -> eyeMaterials.clear();
            case HAIR, EYEBROW, BEARD -> hairMaterials.clear();
        };
    }

    public List<String> getPatterns(NpcTextureType npcTextureType) {
        return switch (npcTextureType){
            case BODY -> bodyPatterns;
            case HEAD -> headPatterns;
            case EAR -> earPatterns;
            case NOSE -> nosePatterns;
            case SCAR -> scarPatterns;
            case EYE -> eyePatterns;
            case HAIR -> hairPatterns;
            case EYEBROW -> eyebrowPatterns;
            case BEARD -> beardPatterns;
            default -> new ArrayList<>();
        };
    }
    public List<String> getMaterials(NpcTextureType npcTextureType) {
        return switch (npcTextureType) {
            case SKIN, BODY, HEAD, SCAR, EAR, NOSE -> skinMaterials;
            case EYE -> eyeMaterials;
            case HAIR, EYEBROW, BEARD -> hairMaterials;
            default -> new ArrayList<>();
        };
    }

    public Boolean haveEmissiveEyes() {
        return haveEmissiveEyes;
    }

    public TexturePresetData copy() {
        var copiedNpcTextureDataPreset = new TexturePresetData();
        for(String value : getMaterials(NpcTextureType.SKIN))
            copiedNpcTextureDataPreset.addToMaterial(NpcTextureType.SKIN, value);
        for(String value : getPatterns(NpcTextureType.HEAD))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.HEAD, value);
        for(String value : getPatterns(NpcTextureType.BODY))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.BODY, value);
        for(String value : getPatterns(NpcTextureType.NOSE))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.NOSE, value);
        for(String value : getPatterns(NpcTextureType.EAR))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.EAR, value);

        for(String value : getMaterials(NpcTextureType.EYE))
            copiedNpcTextureDataPreset.addToMaterial(NpcTextureType.EYE, value);
        for(String value : getPatterns(NpcTextureType.EYE))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.EYE, value);

        for(String value : getMaterials(NpcTextureType.HAIR))
            copiedNpcTextureDataPreset.addToMaterial(NpcTextureType.HAIR, value);
        for(String value : getPatterns(NpcTextureType.EYEBROW))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.EYEBROW, value);
        for(String value : getPatterns(NpcTextureType.HAIR))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.HAIR, value);
        for(String value : getPatterns(NpcTextureType.BEARD))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.BEARD, value);

        copiedNpcTextureDataPreset.clothePresets = clothePresets;

        return copiedNpcTextureDataPreset;
    }

    public CharacterClothingData getClothingData() {
        List<ClothePresets> listBasedOnWeights = new ArrayList<>();
        clothePresets.forEach(x -> {
            int weight = x.getWeight();
            for(int i = 0; i < weight; i++){
                listBasedOnWeights.add(x);
            }
        });

        Random random = new Random();
        if(listBasedOnWeights.isEmpty()){
            MiddleEarth.LOGGER.logDebugMsg("Couldn't find clothes for " + this.getNbt());
            return new CharacterClothingData(null, null, null);
        }
        int randomIndex = random.nextInt(listBasedOnWeights.size());

        ClothePresets clothePreset = listBasedOnWeights.get(randomIndex);

        Identifier baseId = null;
        Identifier overId = null;
        Identifier extraId = null;

        var bases = clothePreset.getBases();
        if(bases != null && !bases.isEmpty()){
            baseId  = bases.get(random.nextInt(bases.size()));
        }
        var overs = clothePreset.getOvers();
        if(overs != null && !overs.isEmpty()){
            overId  = overs.get(random.nextInt(overs.size()));
        }
        var extras = clothePreset.getExtras();
        if(extras != null && !extras.isEmpty()){
            extraId  = extras.get(random.nextInt(extras.size()));
        }

        return new CharacterClothingData(baseId, overId, extraId);
    }
}
