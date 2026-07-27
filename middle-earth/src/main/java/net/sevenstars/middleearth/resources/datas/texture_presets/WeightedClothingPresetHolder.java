package net.sevenstars.middleearth.resources.datas.texture_presets;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
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

    public WeightedClothingPresetHolder(CompoundTag source){
        super(source);
        this.item = new ClothingPreset(source);
    }

    public ResourceLocation getRandomBase(){
        WeightedIdentifier data = item.bases.getRandom();
        return computeData(data);
    }

    public ResourceLocation getRandomOver(){
        WeightedIdentifier data = item.overs.getRandom();
        return computeData(data);
    }

    public ResourceLocation getRandomExtra(){
        WeightedIdentifier data = item.extras.getRandom();
        return computeData(data);
    }

    private ResourceLocation computeData(WeightedIdentifier data) {
        if(data == null)
            return null;
        ResourceLocation foundItem = data.getItem();
        ResourceLocation emptyId = ResourceLocation.parse("empty");
        if(foundItem.equals(emptyId))
            return null;
        return data.getItem();
    }

    @Override
    public WeightedClothingPresetHolder withWeight(int newWeight) {
        this.weight = newWeight;
        return this;
    }

    @Override
    public Tag getNbt(){
        Tag newNbt = super.getNbt();
        if(newNbt == null)
            newNbt = new CompoundTag();
        return this.item.getNbt(newNbt);
    }
}
