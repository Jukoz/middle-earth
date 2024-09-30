package net.jukoz.me.client.screens.utils.widgets.map;

public enum MapMarkerArrowDirection {
    NONE(0,0),
    NORTH(125, 1),
    NORTH_EAST(158, 1),
    EAST(136, 12),
    SOUTH_EAST(158, 12),
    SOUTH(125, 12),
    SOUTH_WEST(147, 12),
    WEST(136, 1),
    NORTH_WEST(147, 1);
    public int uvX, uvY;
    public int sizeX = 9;
    public int sizeY = 9;
    MapMarkerArrowDirection(int uvX, int uvY) {
        this.uvX = uvX;
        this.uvY = uvY;
    }
}