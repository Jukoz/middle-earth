package net.sevenstars.middleearth.resources.datas.npcs.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.ArrayList;
import java.util.List;

public class ClothePresets {

    private List<Identifier> bases;
    private List<Identifier> overs;
    private List<Identifier> extras;
    private int weight;

    public ClothePresets(List<Identifier> bases){
        this(bases, null, null, 1);
    }
    public ClothePresets(List<Identifier> bases, List<Identifier> overs){
        this(bases, overs, null, 1);
    }
    public ClothePresets(List<Identifier> bases, List<Identifier> overs, int weight){
        this(bases, overs, null, weight);
    }
    public ClothePresets(List<Identifier> bases, List<Identifier> overs, List<Identifier> extras){
        this(bases, overs, extras, 1);
    }
    public ClothePresets(List<Identifier> bases, List<Identifier> overs, List<Identifier> extras, int weight){
        this.bases = bases;
        this.overs = overs;
        this.extras = extras;
        this.weight = weight;
    }

    public List<Identifier> getBases(){
        return bases;
    }
    public List<Identifier> getOvers(){
        return overs;
    }
    public List<Identifier> getExtras(){
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
                baseList.add(i, NbtString.of(bases.get(i).toString()));
            }
            newSource.put("bases", baseList);
        }
        if(overs != null){
            var overList = new NbtList();
            for(int i = 0; i < overs.size(); i++){
                overList.add(i, NbtString.of(overs.get(i).toString()));
            }
            newSource.put("overs", overList);
        }
        if(extras != null){
            var extraList = new NbtList();
            for(int i = 0; i < extras.size(); i++){
                extraList.add(i, NbtString.of(extras.get(i).toString()));
            }
            newSource.put("extras", extraList);
        }
        if(weight != 1)
            newSource.putInt("weight", weight);

        return newSource;
    }

    public ClothePresets(NbtCompound source){
        if(source.getInt("weight").isPresent())
            this.weight = source.getInt("weight").get();
        else
            this.weight = 1;

        if(source.getList("bases").isPresent()){
            NbtList baseList = source.getList("bases").get();
            this.bases = new ArrayList<>();
            baseList.forEach( x -> {
                if(x.asString().isPresent())
                    this.bases.add(Identifier.of(x.asString().get()));
            });
        }

        if(source.getList("overs").isPresent()){
            NbtList overList = source.getList("overs").get();
            this.overs = new ArrayList<>();
            overList.forEach( x -> {
                if(x.asString().isPresent())
                    this.overs.add(Identifier.of(x.asString().get()));
            });
        }

        if(source.getList("extras").isPresent()){
            NbtList extraList = source.getList("extras").get();
            this.extras = new ArrayList<>();
            extraList.forEach( x -> {
                if(x.asString().isPresent())
                    this.extras.add(Identifier.of(x.asString().get()));
            });
        }

    }
}
