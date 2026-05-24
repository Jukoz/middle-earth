package net.sevenstars.middleearth.resources.datas.texture_presets;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

public class SimplifiedTexturePreset {
    public Identifier base;
    public Identifier hair;
    public Identifier feet;
    public Identifier nose;
    public Identifier ear;

    public SimplifiedTexturePreset() {
    }

    public SimplifiedTexturePreset(NbtCompound source) {
        source.getString("base").ifPresent(value -> this.base = MiddleEarth.fetchId(value));
        source.getString("hair").ifPresent(value -> this.hair = MiddleEarth.fetchId(value));
        source.getString("feet").ifPresent(value -> this.feet = MiddleEarth.fetchId(value));
        source.getString("nose").ifPresent(value -> this.nose = MiddleEarth.fetchId(value));
        source.getString("ear").ifPresent(value -> this.ear = MiddleEarth.fetchId(value));
    }


    public static SimplifiedTexturePreset create(Identifier base) {
        SimplifiedTexturePreset texturePreset = new SimplifiedTexturePreset();
        texturePreset.base = base;
        return texturePreset;
    }

    public SimplifiedTexturePreset withHair(Identifier hair){
        this.hair = hair;
        return this;
    }

    public SimplifiedTexturePreset withFeet(Identifier feet){
        this.feet = feet;
        return this;
    }

    public SimplifiedTexturePreset withNose(Identifier nose){
        this.nose = nose;
        return this;
    }

    public SimplifiedTexturePreset withEar(Identifier ear){
        this.ear = ear;
        return this;
    }


    public NbtElement getNbt(NbtElement newNbt) {
        if(base != null){
            newNbt.asCompound().get().putString("base", this.base.toString());
        }
        if(hair != null){
            newNbt.asCompound().get().putString("hair", this.hair.toString());
        }
        if(feet != null){
            newNbt.asCompound().get().putString("feet", this.feet.toString());
        }
        if(nose != null){
            newNbt.asCompound().get().putString("nose", this.nose.toString());
        }
        if(ear != null){
            newNbt.asCompound().get().putString("ear", this.ear.toString());
        }
        return newNbt;
    }
}
