package net.sevenstars.api.dtos;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class WeightedIdentifier extends WeightedItem<ResourceLocation> {
    public WeightedIdentifier(Tag element){
        super(element);

        if (element instanceof StringTag) {
            this.item = ResourceLocation.parse(element.getAsString());
        } else if (element instanceof CompoundTag compound && compound.contains("id", Tag.TAG_STRING)) {
            this.item = ResourceLocation.parse(compound.getString("id"));
        }
    }
    public WeightedIdentifier(ResourceLocation value) {
        super(value);
    }
    public WeightedIdentifier(ResourceLocation value, int i) {
        super(value, i);
    }
    public static WeightedIdentifier fromIdentifier(ResourceLocation id){
        return new WeightedIdentifier(id, 1);
    }
    public static WeightedIdentifier fromKey(ResourceKey key){
        return new WeightedIdentifier(key.location(), 1);
    }

    @Override
    public WeightedIdentifier withWeight(int newWeight) {
        this.weight = newWeight;
        return this;
    }

    @Override
    public Tag getNbt(){
        Tag newNbt = super.getNbt();
        if(newNbt == null)
            return StringTag.valueOf(item.toString());

        ((CompoundTag) newNbt).putString("id", this.item.toString());
        return newNbt;
    }
}
