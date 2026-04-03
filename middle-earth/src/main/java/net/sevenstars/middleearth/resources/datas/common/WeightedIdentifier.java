package net.sevenstars.middleearth.resources.datas.common;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.api.dtos.WeightedItem;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTextureMaterial;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTexturePattern;

public class WeightedIdentifier extends WeightedItem<Identifier> {
    public WeightedIdentifier(NbtCompound compound){
        super();

        if(compound.getString("id").isPresent())
            this.item = Identifier.of(compound.getString("id").get());

        if(compound.getInt("weight").isPresent())
            this.weight = compound.getInt("weight").get();
    }
    public WeightedIdentifier(Identifier value) {
        super(value);
    }
    public WeightedIdentifier(Identifier value, int i) {
        super(value, i);
    }

    public static WeightedIdentifier material(RegistryKey<CharacterTextureMaterial> materialId){
        return new WeightedIdentifier(materialId.getValue(), 1);
    }

    public static WeightedIdentifier pattern(RegistryKey<CharacterTexturePattern> patternId){
        return new WeightedIdentifier(patternId.getValue(), 1);
    }

    public static WeightedIdentifier clothe(Identifier clotheId){
        return new WeightedIdentifier(clotheId, 1);
    }

    public WeightedIdentifier withWeight(int weight){
        this.weight = weight;
        return this;
    }

    @Override
    public NbtCompound getNbt(){
        NbtCompound compound = new NbtCompound();

        compound.putString("id", item.toString());
        if(weight != 1)
            compound.putInt("weight", weight);

        return compound;
    }
}
