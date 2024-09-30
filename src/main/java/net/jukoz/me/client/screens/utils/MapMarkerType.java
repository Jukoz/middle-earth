package net.jukoz.me.client.screens.utils;

import net.minecraft.client.gui.DrawContext;

public enum MapMarkerType {
    NONE(1, 23, 4, 4),
    DYNAMIC_SPAWN(1, 29, 17, 17),
    CUSTOM_SPAWN(1, 48, 17, 17);
    public final int uvX;
    public final int uvY;
    public final int sizeX;
    public final int sizeY;
    public final int hoveredUvX;
    public final int hoveredUvY;
    public final int focusedUvX;
    public final int focusedUvY;
    MapMarkerType(int uvX, int uvY, int sizeX, int sizeY) {
        this.uvX = uvX;
        this.uvY = uvY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.hoveredUvX = uvX + sizeX + 2;
        this.hoveredUvY = uvY;

        this.focusedUvX = hoveredUvX + sizeX + 2;
        this.focusedUvY = uvY;
    }
}
