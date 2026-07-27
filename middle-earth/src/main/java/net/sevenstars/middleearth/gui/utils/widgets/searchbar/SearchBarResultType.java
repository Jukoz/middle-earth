package net.sevenstars.middleearth.gui.utils.widgets.searchbar;

import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;

public enum SearchBarResultType
{
    NORMAL(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/gui/widget/search_widget.png"), 0,75, 0, 89),
    SUB(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/gui/widget/search_widget.png"), 0,103, 0, 117);

    public static final int WIDTH = 93;
    public static final int HEIGHT = 14;

    private ResourceLocation textureId;
    private int uvX;
    private int uvY;
    private int activeUvX;
    private int activeUvY;

    SearchBarResultType(ResourceLocation textureId, int uvX, int uvY, int activeUvX, int activeUvY){
        this.textureId = textureId;
        this.uvX = uvX;
        this.uvY = uvY;
        this.activeUvX = activeUvX;
        this.activeUvY = activeUvY;
    }

    public ResourceLocation getTextureId(){
        return textureId;
    }
    public int getUvX(){
        return uvX;
    }
    public int getUvY(){
        return uvY;
    }
    public int getActiveUvX(){
        return activeUvX;
    }
    public int getActiveUvY(){
        return activeUvY;
    }
}
