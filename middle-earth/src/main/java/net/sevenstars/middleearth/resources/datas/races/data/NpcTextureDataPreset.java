package net.sevenstars.middleearth.resources.datas.races.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class NpcTextureDataPreset {
    private int weight;
    List<String> skinTextures;
    List<String> eyeTextures;
    List<String> hairTextures;
    List<String> clothingTextures;
    private boolean haveEmissiveEyes;

    public NpcTextureDataPreset(NbtCompound compound) {
        this.weight = compound.contains("weight") ? compound.getInt("weight") : 1;
        this.skinTextures = fetchStringElements(compound, "skin");
        this.eyeTextures = fetchStringElements(compound, "eye");
        this.haveEmissiveEyes = compound.contains("emissive_eyes") && compound.getBoolean("emissive_eyes");
        this.hairTextures = fetchStringElements(compound, "hair");
        this.clothingTextures = fetchStringElements(compound, "clothing");
    }

    public NbtCompound getNbtCompound(){
        NbtCompound nbt = new NbtCompound();
        if(weight != 1){
            nbt.putInt("weight", weight);
        }
        if(isListEmpty(skinTextures)){
            nbt.put("skin", createStringList(skinTextures));
        }
        if(isListEmpty(eyeTextures)){
            nbt.put("eye", createStringList(eyeTextures));
        }
        if(isListEmpty(hairTextures)){
            nbt.put("hair", createStringList(hairTextures));
        }
        if(isListEmpty(clothingTextures)){
            nbt.put("clothing", createStringList(clothingTextures));
        }
        if(haveEmissiveEyes != null)

        return nbt;
    }

    private <T> boolean isListEmpty(List<T> listToCheck){
        return (listToCheck != null && !listToCheck.isEmpty());
    }

    private NbtList createStringList(List<String> values){
        NbtList nbtList = new NbtList();
        for(int i = 0; i < values.size(); i++){
            nbtList.add(i, NbtString.of(values.get(i)));
        }
        return nbtList;
    }

    private List<String> fetchStringElements(NbtCompound compound, String key){
        List<String> values = new ArrayList<>();
        if(compound.contains(key)){
            NbtList nbtList = compound.getList(key, NbtElement.STRING_TYPE);
            for(int i = 0; i < nbtList.size(); i++){
                values.add(nbtList.getString(i));
            }
        }
        return values;
    }

    public NpcTextureDataPreset(){
        this.weight = 1;
    }

    public NpcTextureDataPreset(List<Identifier> skinTextures, List<Identifier> eyeTextures, List<Identifier> eyebrowTextures, List<Identifier> facialHairTextures, List<Identifier> hairTextures, List<Identifier> clothingTextures) {
        this(skinTextures, eyeTextures, eyebrowTextures, facialHairTextures, hairTextures, clothingTextures, 1);
    }

    public NpcTextureDataPreset(List<Identifier> skinTextures, List<Identifier> eyeTextures, List<Identifier> eyebrowTextures, List<Identifier> facialHairTextures, List<Identifier> hairTextures, List<Identifier> clothingTextures, int weight) {
        this.weight = weight;
    }

    public NpcTextureDataPreset withWeight(int weight){
        this.weight = weight;
        return this;
    }

    public int getWeight(){
        return weight;
    }
}
