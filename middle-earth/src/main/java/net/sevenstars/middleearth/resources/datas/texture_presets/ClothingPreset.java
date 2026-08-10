package net.sevenstars.middleearth.resources.datas.texture_presets;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
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

    public ClothingPreset(NbtCompound source) {
        bases = new WeightedPool<>();
        overs = new WeightedPool<>();
        extras = new WeightedPool<>();

        if(source.getList("bases").isPresent()){
            NbtList baseList = source.getList("bases").get();
            baseList.forEach( x -> {
                bases.add(new WeightedIdentifier(x));
            });
        }

        if(source.getList("overs").isPresent()){
            NbtList overList = source.getList("overs").get();
            overList.forEach( x -> {
                overs.add(new WeightedIdentifier(x));
            });
        }

        if(source.getList("extras").isPresent()){
            NbtList extraList = source.getList("extras").get();
            extraList.forEach( x -> {
                extras.add(new WeightedIdentifier(x));
            });
        }
    }

    public NbtElement getNbt(NbtElement newNbt) {
        if(bases != null){
            var baseList = new NbtList();
            for(int i = 0; i < bases.size(); i++){
                NbtElement element = bases.get(i).getNbt();
                baseList.add(i, element);
            }
            newNbt.asCompound().get().put("bases", baseList);
        }
        if(overs != null){
            var overList = new NbtList();
            for(int i = 0; i < overs.size(); i++){
                NbtElement element = overs.get(i).getNbt();
                overList.add(i, element);
            }
            newNbt.asCompound().get().put("overs", overList);
        }
        if(extras != null){
            var extraList = new NbtList();
            for(int i = 0; i < extras.size(); i++){
                NbtElement element = extras.get(i).getNbt();
                extraList.add(i, element);
            }
            newNbt.asCompound().get().put("extras", extraList);
        }
        return newNbt;
    }
}
