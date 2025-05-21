package net.sevenstars.middleearth.resources.datas.races.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureMaterial;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTexturePattern;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.ArrayList;
import java.util.List;

public class NpcTextureDataPreset {
    public final static String EMPTY_VALUE_KEY = "NONE";
    private int weight;
    List<String> skinPatterns;
    List<String> earPatterns;
    List<String> nosePatterns;
    List<String> eyePatterns;
    List<String> hairPatterns;
    List<String> eyebrowPatterns;
    List<String> beardPatterns;
    List<String> clothingPatterns;

    List<String> skinMaterials;
    List<String> eyeMaterials;
    List<String> hairMaterials;
    List<String> clothingMaterials;
    
    private boolean haveEmissiveEyes;

    public NpcTextureDataPreset(NbtCompound compound) {
        this.weight = compound.contains("weight") ? compound.getInt("weight").get() : 1;

        skinPatterns = new ArrayList<>();
        earPatterns = new ArrayList<>();
        nosePatterns = new ArrayList<>();
        skinMaterials = new ArrayList<>();

        eyePatterns = new ArrayList<>();
        eyeMaterials = new ArrayList<>();

        hairPatterns = new ArrayList<>();
        eyebrowPatterns = new ArrayList<>();
        beardPatterns = new ArrayList<>();
        hairMaterials = new ArrayList<>();

        clothingPatterns = new ArrayList<>();
        clothingMaterials = new ArrayList<>();

        fetchElements(compound, NpcTextureType.SKIN);
        fetchElements(compound, NpcTextureType.EAR);
        fetchElements(compound, NpcTextureType.NOSE);
        fetchElements(compound, NpcTextureType.EYE);
        fetchElements(compound, NpcTextureType.HAIR);
        fetchElements(compound, NpcTextureType.EYEBROW);
        fetchElements(compound, NpcTextureType.BEARD);
        fetchElements(compound, NpcTextureType.CLOTHING);
    }

    public NbtCompound getNbt(){
        NbtCompound nbt = new NbtCompound();
        if(weight != 1){
            nbt.putInt("weight", weight);
        }
        if(isListFilled(skinPatterns) || isListFilled(skinMaterials)){
            NbtCompound compound = new NbtCompound();

            compound.put("patterns", createStringList(skinPatterns));
            compound.put("materials", createStringList(skinMaterials));

            nbt.put(NpcTextureType.SKIN.name(), compound);
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
        if(isListFilled(clothingPatterns) || isListFilled(clothingMaterials)){
            NbtCompound compound = new NbtCompound();

            compound.put("patterns", createStringList(clothingPatterns));
            compound.put("materials", createStringList(clothingMaterials));

            nbt.put(NpcTextureType.CLOTHING.name(), compound);
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
            NbtCompound value = compound.getCompound(type.name()).get();

            if(value.contains("patterns")){
                List<String> fetchedValues = getStringListFromNbtList(value, "patterns");
                switch (type){
                    case SKIN -> skinPatterns.addAll(fetchedValues);
                    case EAR -> earPatterns.addAll(fetchedValues);
                    case NOSE -> nosePatterns.addAll(fetchedValues);
                    case EYE -> eyePatterns.addAll(fetchedValues);
                    case HAIR -> hairPatterns.addAll(fetchedValues);
                    case EYEBROW -> eyebrowPatterns.addAll(fetchedValues);
                    case BEARD -> beardPatterns.addAll(fetchedValues);
                    case CLOTHING -> clothingPatterns.addAll(fetchedValues);
                }
            }
            if(value.contains("materials")){
                List<String> fetchedValues = getStringListFromNbtList(value, "materials");
                switch (type){
                    case SKIN -> skinMaterials.addAll(fetchedValues);
                    case EYE -> eyeMaterials.addAll(fetchedValues);
                    case HAIR -> hairMaterials.addAll(fetchedValues);
                    case CLOTHING -> clothingMaterials.addAll(fetchedValues);
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

    public NpcTextureDataPreset(){
        this.weight = 1;
        skinPatterns = new ArrayList<>();
        skinMaterials = new ArrayList<>();
        earPatterns = new ArrayList<>();
        nosePatterns = new ArrayList<>();

        eyePatterns = new ArrayList<>();
        eyeMaterials = new ArrayList<>();

        hairPatterns = new ArrayList<>();
        eyebrowPatterns = new ArrayList<>();
        beardPatterns = new ArrayList<>();
        hairMaterials = new ArrayList<>();

        clothingPatterns = new ArrayList<>();
        clothingMaterials = new ArrayList<>();
    }

    public NpcTextureDataPreset withWeight(int weight){
        this.weight = weight;
        return this;
    }

    public NpcTextureDataPreset withEmissiveEyes(boolean value){
        this.haveEmissiveEyes = value;
        return this;
    }

    public NpcTextureDataPreset withPatterns(NpcTextureType type, List<RegistryKey<NpcTexturePattern>> patterns){
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

    public NpcTextureDataPreset withMaterials(NpcTextureType type, List<RegistryKey<NpcTextureMaterial>> materials){
        materials.forEach(x -> addToMaterial(type, x.getValue().getPath()));
        return this;
    }

    public int getWeight(){
        return weight;
    }

    public void addToPattern(NpcTextureType npcTextureType, String value) {
        switch (npcTextureType){
            case SKIN -> skinPatterns.add(value);
            case EAR -> earPatterns.add(value);
            case NOSE -> nosePatterns.add(value);
            case EYE -> eyePatterns.add(value);
            case HAIR -> hairPatterns.add(value);
            case EYEBROW -> eyebrowPatterns.add(value);
            case BEARD -> beardPatterns.add(value);
            case CLOTHING -> clothingPatterns.add(value);
        };
    }
    public void addToMaterial(NpcTextureType npcTextureType, String value) {
        switch (npcTextureType){
            case SKIN, EAR, NOSE -> skinMaterials.add(value);
            case EYE -> eyeMaterials.add(value);
            case HAIR, EYEBROW, BEARD -> hairMaterials.add(value);
            case CLOTHING -> clothingMaterials.add(value);
        };
    }

    public List<String> getPatterns(NpcTextureType npcTextureType) {
        return switch (npcTextureType){
            case SKIN -> skinPatterns;
            case BODY -> skinPatterns;
            case HEAD -> skinPatterns;
            case EAR -> earPatterns;
            case NOSE -> nosePatterns;
            case SCAR -> skinPatterns;
            case EYE -> eyePatterns;
            case HAIR -> hairPatterns;
            case EYEBROW -> eyebrowPatterns;
            case BEARD -> beardPatterns;
            case CLOTHING -> clothingPatterns;
        };
    }
    public List<String> getMaterials(NpcTextureType npcTextureType) {

        return switch (npcTextureType) {
            case SKIN, BODY, HEAD, SCAR, EAR, NOSE -> skinMaterials;
            case EYE -> eyeMaterials;
            case HAIR, EYEBROW, BEARD -> hairMaterials;
            case CLOTHING -> clothingMaterials;
        };
    }

    public Boolean haveEmissiveEyes() {
        return haveEmissiveEyes;
    }
}
