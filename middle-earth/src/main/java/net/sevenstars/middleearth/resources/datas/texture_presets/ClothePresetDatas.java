package net.sevenstars.middleearth.resources.datas.texture_presets;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.sevenstars.api.dtos.WeightedItem;
import net.sevenstars.api.dtos.WeightedList;
import net.sevenstars.api.dtos.WeightedIdentifier;

import java.util.ArrayList;
import java.util.List;

public class ClothePresetDatas extends WeightedItem<ClothePresetDatas.Preset> {

    protected record Preset(WeightedList<WeightedIdentifier> bases, WeightedList<WeightedIdentifier> overs, WeightedList<WeightedIdentifier> extras){

    }

    public ClothePresetDatas(List<WeightedIdentifier> bases){
        this(bases, null, null, 1);
    }
    public ClothePresetDatas(List<WeightedIdentifier> bases, List<WeightedIdentifier> overs){
        this(bases, overs, null, 1);
    }
    public ClothePresetDatas(List<WeightedIdentifier> bases, List<WeightedIdentifier> overs, int weight){
        this(bases, overs, null, weight);
    }
    public ClothePresetDatas(List<WeightedIdentifier> bases, List<WeightedIdentifier> overs, List<WeightedIdentifier> extras){
        this(bases, overs, extras, 1);
    }
    public ClothePresetDatas(List<WeightedIdentifier> bases, List<WeightedIdentifier> overs, List<WeightedIdentifier> extras, int weight){
        this.item = new Preset(new WeightedList<>(bases), new WeightedList<>(overs), new WeightedList<>(extras));
        this.weight = weight;
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
    public NbtElement getNbt(){
        NbtElement newNbt = super.getNbt();
        if(newNbt == null)
            newNbt = new NbtCompound();
        if(item.bases != null){
            var baseList = new NbtList();
            for(int i = 0; i < item.bases.size(); i++){
                NbtElement element = item.bases.get(i).getNbt();
                baseList.add(i, element);
            }
            newNbt.asCompound().get().put("bases", baseList);
        }
        if(item.overs != null){
            var overList = new NbtList();
            for(int i = 0; i < item.overs.size(); i++){
                NbtElement element = item.overs.get(i).getNbt();
                overList.add(i, element);
            }
            newNbt.asCompound().get().put("overs", overList);
        }
        if(item.extras != null){
            var extraList = new NbtList();
            for(int i = 0; i < item.extras.size(); i++){
                NbtElement element = item.extras.get(i).getNbt();
                extraList.add(i, element);
            }
            newNbt.asCompound().get().put("extras", extraList);
        }
        return newNbt;
    }

    public ClothePresetDatas(NbtCompound source){
        this.weight = 1;

        if(source.getInt("weight").isPresent())
            this.weight = source.getInt("weight").get();

        List<WeightedIdentifier> bases = new ArrayList<>();
        List<WeightedIdentifier> overs = new ArrayList<>();
        List<WeightedIdentifier> extras = new ArrayList<>();

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
        this.item = new Preset(new WeightedList<>(bases), new WeightedList<>(overs), new WeightedList<>(extras));
    }
}
