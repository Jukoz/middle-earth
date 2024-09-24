package net.jukoz.me.client.screens.utils.widgets;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.map.MiddleEarthMapConfigs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.joml.Vector2i;

public class MapWidget extends ModWidget {
    private static final Identifier MIDDLE_EARTH_WORLD_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/map.png");
    private final int uiWidth, uiHeight;
    private int uvX, uvY = 0;
    private int startX, startY = 0;
    private float zoomLevel = 1f;
    private Vector2i currentOffsetTarget;

    private float uiCurrentWidth, uiCurrentHeight;

    public MapWidget(int mapWidth, int mapHeight) {
        this.uiWidth = mapWidth;
        this.uiHeight = mapHeight;
        this.uiCurrentWidth = uiWidth;
        this.uiCurrentHeight = uiHeight;
        // By default, center
        this.currentOffsetTarget = new Vector2i(uiWidth / 2, uiHeight / 2);
    }

    public void setStartCoordinates(int startX, int startY){
        this.startX = startX;
        this.startY = startY;
    }

    public void drawCentered(DrawContext context, int centerX, int startY){
        int startX = centerX - (uiWidth / 2);
        draw(context, startX, startY);
    }

    public void drawAnchored(DrawContext context, int anchorX, int startY, boolean isLeftAnchor){
        int startX = anchorX;
        if(!isLeftAnchor)
            startX -= uiWidth;

        draw(context, startX, startY);
    }

    /**
     * Need to set start positions before being used, by default it's 0,0.
     * @param context
     */
    public void draw(DrawContext context){
        draw(context, this.startX, this.startY);
    }

    private void draw(DrawContext context, int startX, int startY){
        this.startX = startX;
        this.startY = startY;

        // TODO : In progress
        context.drawTexture(MIDDLE_EARTH_WORLD_TEXTURE,
                startX, startY,
                uvX, uvY,
                uiWidth,uiHeight,
                (int)uiCurrentWidth, (int)uiCurrentHeight
        );
        /*
        (int) (MAP_IMAGE_WIDTH * getZoomLevel() * minZoom),
                (int) (MAP_IMAGE_HEIGHT * getZoomLevel() * minZoom));

         */
    }



    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // TODO : Marker click? Need a better system..
        //LoggerUtil.logDebugMsg("Mouse is clicked at " + mouseX + ", " + mouseY);
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if(button == 0 && mouseIsInside(mouseX, mouseY)) {
            int newUvX = (int) (this.uvX - deltaX);
            int newUvY = (int) (this.uvY - deltaY);

            currentOffsetTarget.x = (int) (-startX + mouseX);
            currentOffsetTarget.y = (int) (-startY + mouseY);

            setNewUv(newUvX, newUvY);
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if(mouseIsInside(mouseX, mouseY)){
            currentOffsetTarget.x = (int) (-startX + mouseX);
            currentOffsetTarget.y = (int) (-startY + mouseY);
            if(verticalAmount > 0){
                zoom((float) (0.5f * Math.pow(0.9f, zoomLevel)));
            } else {
                dezoom((float) (0.5f * Math.pow(0.9f, zoomLevel)));
            }
        }
        return true;
    }

    private boolean mouseIsInside(double mouseX, double mouseY) {
        return ((mouseX > startX && mouseX < startX + uiWidth) && (mouseY > startY && mouseY < startY + uiHeight));
    }

    public void zoom(){
        zoom(1f);
    }
    public void zoom(float amount) {
        if(zoomLevel != 15f) {
            zoomLevel = Math.min(15f,  zoomLevel + amount);
            computeNewOffset();
        }
    }
    public void dezoom(){
        dezoom(1f);
    }
    public void dezoom(float amount) {
        if(zoomLevel != 1f) {
            zoomLevel = Math.max(1f, zoomLevel - amount);
            computeNewOffset();
        }
    }

    private void computeNewOffset(){
        float newUiCurrentWidth = uiWidth * zoomLevel;
        float newUiCurrentHeight = uiHeight * zoomLevel;

        // 0.5 means centered
        float xRatio = (float) currentOffsetTarget.x / uiWidth;
        float yRatio = (float) currentOffsetTarget.y / uiHeight;

        LoggerUtil.logDebugMsg(xRatio + ", " + yRatio);

        float differenceX = (newUiCurrentWidth - uiCurrentWidth);
        float differenceY = (newUiCurrentHeight - uiCurrentHeight);

        uiCurrentWidth = newUiCurrentWidth;
        uiCurrentHeight = newUiCurrentHeight;

        setNewUv((int) (uvX + differenceX * xRatio), (int) (uvY + differenceY * yRatio));
    }

    private void setNewUv(int newUvX, int newUvY){
        int maxWidth = (int) (uiWidth * zoomLevel) - uiWidth;
        int maxHeight = (int) (uiHeight * zoomLevel) - uiHeight;;

        float computedX = Math.min(maxWidth, newUvX);
        computedX = Math.max(0, computedX);

        float computedY = Math.min(maxHeight, newUvY);
        computedY = Math.max(0, computedY);

        this.uvX = (int) computedX;
        this.uvY = (int) computedY;
    }
}
