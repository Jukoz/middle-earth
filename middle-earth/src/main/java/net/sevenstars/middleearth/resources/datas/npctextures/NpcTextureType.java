package net.sevenstars.middleearth.resources.datas.npctextures;

public enum NpcTextureType {
    SKIN("skin"),
    EYE("eye"),
    HAIR("hair"),
    EARS("ears"),
    FEET("feet"),
    NOSE("nose"),
    CLOTHING("clothing");

    private final String path;
    NpcTextureType(String path){
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }
}
