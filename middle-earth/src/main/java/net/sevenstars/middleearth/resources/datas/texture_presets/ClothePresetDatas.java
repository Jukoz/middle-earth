package net.sevenstars.middleearth.resources.datas.texture_presets;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.resources.datas.texture_presets.entities.TextureElementData;

import java.util.ArrayList;
import java.util.List;

public class ClothePresetDatas {

    private List<TextureElementData> bases;
    private List<TextureElementData> overs;
    private List<TextureElementData> extras;
    private int weight;

    public ClothePresetDatas(List<TextureElementData> bases){
        this(bases, null, null, 1);
    }
    public ClothePresetDatas(List<TextureElementData> bases, List<TextureElementData> overs){
        this(bases, overs, null, 1);
    }
    public ClothePresetDatas(List<TextureElementData> bases, List<TextureElementData> overs, int weight){
        this(bases, overs, null, weight);
    }
    public ClothePresetDatas(List<TextureElementData> bases, List<TextureElementData> overs, List<TextureElementData> extras){
        this(bases, overs, extras, 1);
    }
    public ClothePresetDatas(List<TextureElementData> bases, List<TextureElementData> overs, List<TextureElementData> extras, int weight){
        this.bases = bases;
        this.overs = overs;
        this.extras = extras;
        this.weight = weight;
    }

    public List<TextureElementData> getBases(){
        return bases;
    }
    public List<TextureElementData> getOvers(){
        return overs;
    }
    public List<TextureElementData> getExtras(){
        return extras;
    }

    public int getWeight(){
        return weight;
    }

    public NbtCompound writeNbt(){
        var newSource = new NbtCompound();
        if(bases != null){
            var baseList = new NbtList();
            for(int i = 0; i < bases.size(); i++){
                NbtCompound element = bases.get(i).getNbt();
                baseList.add(i, element);
            }
            newSource.put("bases", baseList);
        }
        if(overs != null){
            var overList = new NbtList();
            for(int i = 0; i < overs.size(); i++){
                NbtCompound element = overs.get(i).getNbt();
                overList.add(i, element);
            }
            newSource.put("overs", overList);
        }
        if(extras != null){
            var extraList = new NbtList();
            for(int i = 0; i < extras.size(); i++){
                NbtCompound element = extras.get(i).getNbt();
                extraList.add(i, element);
            }
            newSource.put("extras", extraList);
        }
        if(weight != 1)
            newSource.putInt("weight", weight);

        return newSource;
    }

    public ClothePresetDatas(NbtCompound source){
        if(source.getInt("weight").isPresent())
            this.weight = source.getInt("weight").get();
        else
            this.weight = 1;

        if(source.getList("bases").isPresent()){
            NbtList baseList = source.getList("bases").get();
            this.bases = new ArrayList<>();
            baseList.forEach( x -> {
                if(x.asString().isPresent())
                    this.bases.add(new TextureElementData(Identifier.of(x.toString())));
            });
        }

        if(source.getList("overs").isPresent()){
            NbtList overList = source.getList("overs").get();
            this.overs = new ArrayList<>();
            overList.forEach( x -> {
                if(x.asString().isPresent())
                    this.overs.add(new TextureElementData(Identifier.of(x.asString().get())));
            });
        }

        if(source.getList("extras").isPresent()){
            NbtList extraList = source.getList("extras").get();
            this.extras = new ArrayList<>();
            extraList.forEach( x -> {
                if(x.asString().isPresent())
                    this.extras.add(new TextureElementData(Identifier.of(x.asString().get())));
            });
        }

    }
}
