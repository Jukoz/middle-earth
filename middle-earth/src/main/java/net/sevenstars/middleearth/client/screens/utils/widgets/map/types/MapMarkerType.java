package net.sevenstars.middleearth.client.screens.utils.widgets.map.types;

import org.joml.Vector2i;

public enum MapMarkerType {
    NONE(1, 1, 4, 4, 2, 2),
    DYNAMIC_SPAWN(1, 7, 17, 17, 8, 8),
    STACKED_SPAWNS(1, 26, 17, 17, 12, 12),
    CUSTOM_SPAWN(1, 45, 17, 17, 8, 8);
    public final Vector2i size;
    public final Vector2i interactableSize;
    public final Vector2i uvs;
    public final Vector2i hoveredUvs;
    public final Vector2i focusedUvs;

    MapMarkerType(int uvX, int uvY, int sizeX, int sizeY, int interactableSizeX, int interactableSizeY) {
        this.size = new Vector2i(sizeX, sizeY);
        this.interactableSize = new Vector2i(interactableSizeX, interactableSizeY);

        this.uvs = new Vector2i(uvX, uvY);
        this.hoveredUvs = new Vector2i(uvX + sizeX + 2, uvY);
        this.focusedUvs = new Vector2i(hoveredUvs.x + sizeX + 2, uvY);
    }
}
