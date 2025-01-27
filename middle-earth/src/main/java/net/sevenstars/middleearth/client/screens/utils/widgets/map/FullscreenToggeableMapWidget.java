package net.sevenstars.middleearth.client.screens.utils.widgets.map;

import net.minecraft.client.gui.DrawContext;
import org.joml.Vector2d;
import org.joml.Vector2i;

public class FullscreenToggeableMapWidget extends MapWidget {
    private static Vector2i fullscreenSize;
    public FullscreenToggeableMapWidget(int mapWidth, int mapHeight) {
        super(mapWidth, mapHeight);
    }
    public void drawFullscreen(DrawContext context, int sideMargins) {
        Vector2i newFullscreenSize = new Vector2i((context.getScaledWindowWidth() - (sideMargins * 2)), (context.getScaledWindowHeight()  - (sideMargins * 2)));
        if(fullscreenSize == null){
            setNewFullscreenState(newFullscreenSize);
        } else {
            fullscreenSize = newFullscreenSize;
        }
        super.draw(context, sideMargins, sideMargins);
    }
    public void drawCentered(DrawContext context, int centerX, int startY) {
        if(fullscreenSize != null){
            setNewFullscreenState(null);
        }
        super.drawCentered(context, centerX, startY);
    }
    private void setNewFullscreenState(Vector2i newSize) {
        Vector2d mapCenter = getCurrentMapCenterRatio();
        fullscreenSize = newSize;
        this.uiCurrentWidth = getWidth() * zoomLevel;
        this.uiCurrentHeight = getHeight() * zoomLevel;
        instantCenterOnRatio(mapCenter);
    }
    @Override
    protected boolean mouseIsInside(double mouseX, double mouseY) {
        return super.mouseIsInside(mouseX, mouseY);
    }
    @Override
    protected int getWidth() {
        if(fullscreenSize != null) return fullscreenSize.x;
        return super.getWidth();
    }
    @Override
    protected int getHeight() {
        if(fullscreenSize != null) return fullscreenSize.y;
        return super.getHeight();
    }
}
