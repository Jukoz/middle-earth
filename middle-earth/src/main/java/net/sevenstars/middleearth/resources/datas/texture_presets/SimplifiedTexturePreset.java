package net.sevenstars.middleearth.resources.datas.texture_presets;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;

public class SimplifiedTexturePreset {
    public ResourceLocation base;
    public ResourceLocation hair;
    public ResourceLocation feet;
    public ResourceLocation nose;
    public ResourceLocation ear;

    public SimplifiedTexturePreset() {
    }

    public SimplifiedTexturePreset(CompoundTag source) {
        if (source.contains("base", Tag.TAG_STRING))
            this.base = MiddleEarth.fetchId(source.getString("base"));
        if (source.contains("hair", Tag.TAG_STRING))
            this.hair = MiddleEarth.fetchId(source.getString("hair"));
        if (source.contains("feet", Tag.TAG_STRING))
            this.feet = MiddleEarth.fetchId(source.getString("feet"));
        if (source.contains("nose", Tag.TAG_STRING))
            this.nose = MiddleEarth.fetchId(source.getString("nose"));
        if (source.contains("ear", Tag.TAG_STRING))
            this.ear = MiddleEarth.fetchId(source.getString("ear"));
    }


    public static SimplifiedTexturePreset create(ResourceLocation base) {
        SimplifiedTexturePreset texturePreset = new SimplifiedTexturePreset();
        texturePreset.base = base;
        return texturePreset;
    }

    public SimplifiedTexturePreset withHair(ResourceLocation hair){
        this.hair = hair;
        return this;
    }

    public SimplifiedTexturePreset withFeet(ResourceLocation feet){
        this.feet = feet;
        return this;
    }

    public SimplifiedTexturePreset withNose(ResourceLocation nose){
        this.nose = nose;
        return this;
    }

    public SimplifiedTexturePreset withEar(ResourceLocation ear){
        this.ear = ear;
        return this;
    }


    public Tag getNbt(Tag newNbt) {
        if (!(newNbt instanceof CompoundTag compound)) {
            return newNbt;
        }
        if(base != null){
            compound.putString("base", this.base.toString());
        }
        if(hair != null){
            compound.putString("hair", this.hair.toString());
        }
        if(feet != null){
            compound.putString("feet", this.feet.toString());
        }
        if(nose != null){
            compound.putString("nose", this.nose.toString());
        }
        if(ear != null){
            compound.putString("ear", this.ear.toString());
        }
        return newNbt;
    }
}
