package net.sevenstars.middleearth.resources.datas.texture_presets.entities;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.texture_presets.ClothingData;
import net.sevenstars.middleearth.resources.datas.texture_presets.ClothePresetDatas;
import net.sevenstars.middleearth.resources.datas.common.NpcTextureType;

import java.util.*;

public class TexturePresetData {
    public final static TextureElementData EMPTY_VALUE_KEY = new TextureElementData(Identifier.of("empty"));
    private int weight;
    List<TextureElementData> bodyPatterns;
    List<TextureElementData> headPatterns;
    List<TextureElementData> feetPatterns;
    List<TextureElementData> scarPatterns;
    List<TextureElementData> earPatterns;
    List<TextureElementData> nosePatterns;
    List<TextureElementData> eyePatterns;
    List<TextureElementData> hairPatterns;
    List<TextureElementData> eyebrowPatterns;
    List<TextureElementData> beardPatterns;
    List<TextureElementData> skinMaterials;
    List<TextureElementData> eyeMaterials;
    List<TextureElementData> hairMaterials;

    List<ClothePresetDatas> characterClothePresets;

    private boolean haveEmissiveEyes;

    public TexturePresetData(){
        this.weight = 1;
        bodyPatterns = new ArrayList<>();
        headPatterns = new ArrayList<>();
        scarPatterns = new ArrayList<>();
        earPatterns = new ArrayList<>();
        nosePatterns = new ArrayList<>();
        feetPatterns = new ArrayList<>();
        skinMaterials = new ArrayList<>();

        eyePatterns = new ArrayList<>();
        eyeMaterials = new ArrayList<>();

        hairPatterns = new ArrayList<>();
        eyebrowPatterns = new ArrayList<>();
        beardPatterns = new ArrayList<>();
        hairMaterials = new ArrayList<>();

        characterClothePresets = new ArrayList<>();
    }

    public TexturePresetData(NbtCompound compound) {
        this.weight = compound.contains("weight") ? compound.getInt("weight").get() : 1;

        bodyPatterns = new ArrayList<>();
        headPatterns = new ArrayList<>();
        feetPatterns = new ArrayList<>();
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

        characterClothePresets = new ArrayList<>();

        fetchElements(compound, NpcTextureType.SKIN);
        fetchElements(compound, NpcTextureType.FEET);
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
            compound.put("materials", createTextureElementList(skinMaterials));
            nbt.put(NpcTextureType.SKIN.name(), compound);
        }
        if(isListFilled(bodyPatterns)){
            NbtCompound compound = new NbtCompound();
            compound.put("patterns", createTextureElementList(bodyPatterns));
            nbt.put(NpcTextureType.BODY.name(), compound);
        }
        if(isListFilled(feetPatterns)){
            NbtCompound compound = new NbtCompound();
            compound.put("patterns", createTextureElementList(feetPatterns));
            nbt.put(NpcTextureType.FEET.name(), compound);
        }
        if(isListFilled(headPatterns)){
            NbtCompound compound = new NbtCompound();
            compound.put("patterns", createTextureElementList(headPatterns));

            nbt.put(NpcTextureType.HEAD.name(), compound);
        }
        if(isListFilled(scarPatterns)){
            NbtCompound compound = new NbtCompound();
            compound.put("patterns", createTextureElementList(scarPatterns));
            nbt.put(NpcTextureType.SCAR.name(), compound);
        }
        if(isListFilled(earPatterns)){
            NbtCompound compound = new NbtCompound();
            compound.put("patterns", createTextureElementList(earPatterns));
            nbt.put(NpcTextureType.EAR.name(), compound);
        }
        if(isListFilled(nosePatterns)){
            NbtCompound compound = new NbtCompound();
            compound.put("patterns", createTextureElementList(nosePatterns));
            nbt.put(NpcTextureType.NOSE.name(), compound);
        }
        if(isListFilled(eyePatterns) || isListFilled(eyeMaterials)){
            NbtCompound compound = new NbtCompound();

            compound.put("patterns", createTextureElementList(eyePatterns));
            compound.put("materials", createTextureElementList(eyeMaterials));

            if(haveEmissiveEyes)
                compound.putBoolean("is_emissive", true);

            nbt.put(NpcTextureType.EYE.name(), compound);
        }
        if(isListFilled(hairPatterns) || isListFilled(hairMaterials)){
            NbtCompound compound = new NbtCompound();

            compound.put("patterns", createTextureElementList(hairPatterns));
            compound.put("materials", createTextureElementList(hairMaterials));

            nbt.put(NpcTextureType.HAIR.name(), compound);
        }
        if(isListFilled(eyebrowPatterns)){
            NbtCompound compound = new NbtCompound();
            compound.put("patterns", createTextureElementList(eyebrowPatterns));
            nbt.put(NpcTextureType.EYEBROW.name(), compound);
        }
        if(isListFilled(beardPatterns)){
            NbtCompound compound = new NbtCompound();
            compound.put("patterns", createTextureElementList(beardPatterns));
            nbt.put(NpcTextureType.BEARD.name(), compound);
        }
        if(isListFilled(characterClothePresets)){
            var list = new NbtList();
            for(int i = 0; i < this.characterClothePresets.size(); i++){
                list.add(i, characterClothePresets.get(i).writeNbt());
            }

            nbt.put(NpcTextureType.CLOTHE_PRESETS.name(), list);
        }

        return nbt;
    }

    private <T> boolean isListFilled(List<T> listToCheck){
        return (listToCheck != null && !listToCheck.isEmpty());
    }

    private NbtList createTextureElementList(List<TextureElementData> values){
        NbtList nbtList = new NbtList();
        for(int i = 0; i < values.size(); i++){
            nbtList.add(i, values.get(i).getNbt());
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
                            this.characterClothePresets.add(new ClothePresetDatas(x.asCompound().get()));
                    });
                }
                return;
            }
            NbtCompound value = compound.getCompound(type.name()).get();

            if(value.contains("patterns")){
                var patterns = value.get("patterns");
                if(patterns != null){
                    NbtList nbtList = patterns.asNbtList().get();

                    List<NbtElement> fetchedValues = nbtList.stream().toList();
                    List<TextureElementData> textureElementDatas = new ArrayList<>();

                    for(var fetchedValue : fetchedValues){
                        textureElementDatas.add(new TextureElementData(fetchedValue.asCompound().get()));
                    }

                    switch (type){
                        case BODY -> bodyPatterns.addAll(textureElementDatas);
                        case HEAD -> headPatterns.addAll(textureElementDatas);
                        case FEET -> feetPatterns.addAll(textureElementDatas);
                        case SCAR -> scarPatterns.addAll(textureElementDatas);
                        case EAR -> earPatterns.addAll(textureElementDatas);
                        case NOSE -> nosePatterns.addAll(textureElementDatas);
                        case EYE -> eyePatterns.addAll(textureElementDatas);
                        case HAIR -> hairPatterns.addAll(textureElementDatas);
                        case EYEBROW -> eyebrowPatterns.addAll(textureElementDatas);
                        case BEARD -> beardPatterns.addAll(textureElementDatas);
                    }
                }
            }
            if(value.contains("materials")){
                var materials = value.get("materials");
                if(materials != null){
                    NbtList nbtList = materials.asNbtList().get();

                    List<NbtElement> fetchedValues = nbtList.stream().toList();
                    List<TextureElementData> textureElementDatas = new ArrayList<>();

                    for(var fetchedValue : fetchedValues){
                        textureElementDatas.add(new TextureElementData(fetchedValue.asCompound().get()));
                    }

                    switch (type){
                        case SKIN -> skinMaterials.addAll(textureElementDatas);
                        case EYE -> eyeMaterials.addAll(textureElementDatas);
                        case HAIR -> hairMaterials.addAll(textureElementDatas);
                    }
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

    public TexturePresetData withPatterns(NpcTextureType type, List<TextureElementData> patterns){
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

    public TexturePresetData withPatternValues(NpcTextureType type, List<TextureElementData> patterns){
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
    public TexturePresetData clearPatterns(NpcTextureType type) {
        clearAllPatterns(type);
        return this;
    }
    public TexturePresetData withMaterials(NpcTextureType type, List<TextureElementData> materials){
        materials.forEach(x -> addToMaterial(type, x));
        return this;
    }
    public TexturePresetData withMaterialValues(NpcTextureType type, List<TextureElementData> materials){
        materials.forEach(x -> addToMaterial(type, x));
        return this;
    }
    public TexturePresetData clearMaterials(NpcTextureType type) {
        clearAllMaterials(type);
        return this;
    }

    public int getWeight(){
        return weight;
    }

    public void addToPattern(NpcTextureType npcTextureType, TextureElementData value) {
        switch (npcTextureType){
            case BODY -> bodyPatterns.add(value);
            case HEAD -> headPatterns.add(value);
            case FEET -> feetPatterns.add(value);
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
            case FEET -> feetPatterns.clear();
            case SCAR -> scarPatterns.clear();
            case EAR -> earPatterns.clear();
            case NOSE -> nosePatterns.clear();
            case EYE -> eyePatterns.clear();
            case HAIR -> hairPatterns.clear();
            case EYEBROW -> eyebrowPatterns.clear();
            case BEARD -> beardPatterns.clear();
        };
    }

    public void addToMaterial(NpcTextureType npcTextureType, TextureElementData value) {
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

    public List<TextureElementData> getPatterns(NpcTextureType npcTextureType) {
        return switch (npcTextureType){
            case BODY -> bodyPatterns;
            case HEAD -> headPatterns;
            case FEET -> feetPatterns;
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
    public List<TextureElementData> getMaterials(NpcTextureType npcTextureType) {
        return switch (npcTextureType) {
            case SKIN, BODY, HEAD, FEET, SCAR, EAR, NOSE -> skinMaterials;
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
        for(TextureElementData value : getMaterials(NpcTextureType.SKIN))
            copiedNpcTextureDataPreset.addToMaterial(NpcTextureType.SKIN, value);
        for(TextureElementData value : getPatterns(NpcTextureType.HEAD))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.HEAD, value);
        for(TextureElementData value : getPatterns(NpcTextureType.BODY))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.BODY, value);
        for(TextureElementData value : getPatterns(NpcTextureType.FEET))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.FEET, value);
        for(TextureElementData value : getPatterns(NpcTextureType.NOSE))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.NOSE, value);
        for(TextureElementData value : getPatterns(NpcTextureType.EAR))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.EAR, value);

        for(TextureElementData value : getMaterials(NpcTextureType.EYE))
            copiedNpcTextureDataPreset.addToMaterial(NpcTextureType.EYE, value);
        for(TextureElementData value : getPatterns(NpcTextureType.EYE))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.EYE, value);

        for(TextureElementData value : getMaterials(NpcTextureType.HAIR))
            copiedNpcTextureDataPreset.addToMaterial(NpcTextureType.HAIR, value);
        for(TextureElementData value : getPatterns(NpcTextureType.EYEBROW))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.EYEBROW, value);
        for(TextureElementData value : getPatterns(NpcTextureType.HAIR))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.HAIR, value);
        for(TextureElementData value : getPatterns(NpcTextureType.BEARD))
            copiedNpcTextureDataPreset.addToPattern(NpcTextureType.BEARD, value);

        copiedNpcTextureDataPreset.characterClothePresets = characterClothePresets;

        return copiedNpcTextureDataPreset;
    }

    public ClothingData getClothingData() {
        List<ClothePresetDatas> listBasedOnWeights = new ArrayList<>();
        characterClothePresets.forEach(x -> {
            int weight = x.getWeight();
            for(int i = 0; i < weight; i++){
                listBasedOnWeights.add(x);
            }
        });

        Random random = new Random();
        if(listBasedOnWeights.isEmpty()){
            MiddleEarth.LOGGER.logDebugMsg("Couldn't find clothes for " + this.getNbt());
            return new ClothingData(null, null, null);
        }
        int randomIndex = random.nextInt(listBasedOnWeights.size());

        ClothePresetDatas clothePreset = listBasedOnWeights.get(randomIndex);

        Identifier baseId = null;
        Identifier overId = null;
        Identifier extraId = null;

        var bases = clothePreset.getBases();
        if(bases != null && !bases.isEmpty()){
            baseId = bases.get(random.nextInt(bases.size())).getId();
        }
        var overs = clothePreset.getOvers();
        if(overs != null && !overs.isEmpty()){
            overId = overs.get(random.nextInt(overs.size())).getId();
        }
        var extras = clothePreset.getExtras();
        if(extras != null && !extras.isEmpty()){
            extraId = extras.get(random.nextInt(extras.size())).getId();
        }

        return new ClothingData(baseId, overId, extraId);
    }
}
