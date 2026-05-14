package net.sevenstars.middleearth.resources.datas.texture_presets;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.sevenstars.api.dtos.WeightedIdentifier;
import net.sevenstars.api.dtos.WeightedItem;
import net.sevenstars.api.dtos.WeightedPool;

import java.util.List;

public class WeightedClothingPresetHolder extends WeightedItem<ClothingPreset> {
    public WeightedClothingPresetHolder(List<WeightedIdentifier> bases){
        this(bases, null, null, 1);
    }

    public WeightedClothingPresetHolder(List<WeightedIdentifier> bases, List<WeightedIdentifier> overs){
        this(bases, overs, null, 1);
    }

    public WeightedClothingPresetHolder(List<WeightedIdentifier> bases, List<WeightedIdentifier> overs, int weight){
        this(bases, overs, null, weight);
    }

    public WeightedClothingPresetHolder(List<WeightedIdentifier> bases, List<WeightedIdentifier> overs, List<WeightedIdentifier> extras){
        this(bases, overs, extras, 1);
    }

    public WeightedClothingPresetHolder(List<WeightedIdentifier> bases, List<WeightedIdentifier> overs, List<WeightedIdentifier> extras, int weight){
        this.item = new ClothingPreset(new WeightedPool<>(bases), new WeightedPool<>(overs), new WeightedPool<>(extras));
        this.weight = weight;
    }

    public WeightedClothingPresetHolder(NbtCompound source){
        super(source);
        this.item = new ClothingPreset(source);
    }

    public Identifier getRandomBase(){
        WeightedIdentifier data = item.bases.getRandom();
        if(data == null)
            return null;
        return data.getItem();
    }

    public Identifier getRandomOver(){
        WeightedIdentifier data = item.overs.getRandom();
        if(data == null)
            return null;
        return data.getItem();
    }

    public Identifier getRandomExtra(){
        WeightedIdentifier data = item.extras.getRandom();
        if(data == null)
            return null;
        return data.getItem();
    }

    public int getWeight(){
        return weight;
    }

    @Override
    public WeightedClothingPresetHolder withWeight(int newWeight) {
        this.weight = newWeight;
        return this;
    }

    @Override
    public NbtElement getNbt(){
        NbtElement newNbt = super.getNbt();
        if(newNbt == null)
            newNbt = new NbtCompound();
        return this.item.getNbt(newNbt);
    }
}
