package net.sevenstars.middleearth.resources.datas.texture_presets;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.sevenstars.api.dtos.WeightedIdentifier;
import net.sevenstars.api.dtos.WeightedPool;

public class ClothingPreset {
    public WeightedPool<WeightedIdentifier> bases;
    public WeightedPool<WeightedIdentifier> overs;
    public WeightedPool<WeightedIdentifier> extras;

    public ClothingPreset(WeightedPool<WeightedIdentifier> bases, WeightedPool<WeightedIdentifier> overs, WeightedPool<WeightedIdentifier> extras) {
        this.bases = bases;
        this.overs = overs;
        this.extras = extras;
    }

    public ClothingPreset(CompoundTag source) {
        bases = new WeightedPool<>();
        overs = new WeightedPool<>();
        extras = new WeightedPool<>();

        if(source.get("bases") instanceof ListTag baseList){
            baseList.forEach( x -> {
                bases.add(new WeightedIdentifier(x));
            });
        }

        if(source.get("overs") instanceof ListTag overList){
            overList.forEach( x -> {
                overs.add(new WeightedIdentifier(x));
            });
        }

        if(source.get("extras") instanceof ListTag extraList){
            extraList.forEach( x -> {
                extras.add(new WeightedIdentifier(x));
            });
        }
    }

    public Tag getNbt(Tag newNbt) {
        if (!(newNbt instanceof CompoundTag compound)) {
            return newNbt;
        }
        if(bases != null){
            compound.put("bases", bases.getNbt());
        }
        if(overs != null){
            compound.put("overs", overs.getNbt());
        }
        if(extras != null){
            compound.put("extras", extras.getNbt());
        }
        return newNbt;
    }
}
