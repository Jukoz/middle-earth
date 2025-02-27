package net.sevenstars.middleearth.resources.datas.races.data.npctextures;

public enum NpcTextureType {
    SKIN("skin"),
    EYE("eye"),
    HAIR("hair"),
    CLOTHING("clothing");


    private final String path;
    NpcTextureType(String path){
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }
}
