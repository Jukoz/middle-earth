package net.jukoz.me.client.screens.utils;

public enum CycledSelectionButtonType {
    NORMAL(0,0),
    SILVER(0, 38),
    GOLD(0, 76);

    public final static int FOCUS_UV_X = 103;
    public final static int FOCUS_UV_Y = 95;
    public final static int WIDTH = 102;
    public final static int HEIGHT = 18;
    public final int uvX;
    public final int uvY;
    public final int hoveredUvX;
    public final int hoveredUvY;
    CycledSelectionButtonType(int uvX, int uvY) {
        this.uvX = uvX;
        this.uvY = uvY;

        this.hoveredUvX = uvX;
        this.hoveredUvY = uvY + HEIGHT + 1;
    }
}
