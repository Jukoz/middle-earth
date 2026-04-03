package net.sevenstars.middleearth.resources.datas.texture_presets.entities;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.registries.CharacterClothesRegistryME;

public class TextureElementData {
    private Identifier textureId;
    private int weight;

    public TextureElementData(NbtCompound compound){
        if(compound.getString("id").isPresent())
            textureId = Identifier.of(compound.getString("id").get());
        else
            textureId = null;

        if(compound.getString("weight").isPresent())
            weight = compound.getInt("weight").get();
        else
            weight = 1;
    }

    public TextureElementData(Identifier id){
        this.textureId = id;
        this.weight = 1;
    }

    public TextureElementData(Identifier id, int weight){
        this.textureId = id;
        this.weight = weight;
    }

    public static TextureElementData material(RegistryKey<CharacterTextureMaterial> materialId){
        return new TextureElementData(materialId.getValue(), 1);
    }

    public static TextureElementData pattern(RegistryKey<CharacterTexturePattern> patternId){
        return new TextureElementData(patternId.getValue(), 1);
    }

    public static TextureElementData clothe(Identifier clotheId){
        return  new TextureElementData(clotheId, 1);
    }

    public TextureElementData withWeight(int weight){
        this.weight = weight;
        return this;
    }

    public NbtCompound getNbt(){
        NbtCompound compound = new NbtCompound();

        compound.putString("id", textureId.toString());
        if(weight != 1)
            compound.putInt("weight", weight);

        return compound;
    }

    public Identifier getId() {
        return textureId;
    }

    public int getWeight(){
        return weight;
    }
}
