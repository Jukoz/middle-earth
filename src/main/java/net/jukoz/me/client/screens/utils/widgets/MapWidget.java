package net.jukoz.me.client.screens.utils.widgets;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.map.MiddleEarthMapConfigs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class MapWidget extends ModWidget {
    private static final Identifier MIDDLE_EARTH_WORLD_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/map.png");
    private final int uiWidth, uiHeight;
    private int uvX, uvY = 0;
    private int startX, startY = 0;
    private float zoomLevel = 1f;
    private Vector2d currentPointRatio;
    private Vector2d currentMapTargetRatio;

    private Vector2d currentUiTargetRatio;

    private float uiCurrentWidth, uiCurrentHeight;

    public MapWidget(int mapWidth, int mapHeight) {
        this.uiWidth = mapWidth;
        this.uiHeight = mapHeight;
        this.uiCurrentWidth = uiWidth;
        this.uiCurrentHeight = uiHeight;
        // By default, center
        resetFocus();
    }

    private void resetFocus() {
        this.currentPointRatio = new Vector2d(.5, .5); // Center
        updateCurrentMapTargetRatio(zoomLevel);
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
        // TODO : Marker click? Need a better system.. Based on buttons? Based on hovering?
        //LoggerUtil.logDebugMsg("Mouse is clicked at " + mouseX + ", " + mouseY);
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if(button == 0 && mouseIsInside(mouseX, mouseY)) {
            int newUvX = (int) (this.uvX - deltaX);
            int newUvY = (int) (this.uvY - deltaY);

            currentPointRatio.x = (-startX + mouseX) / uiWidth;
            currentPointRatio.y = (-startY + mouseY) / uiHeight;

            computeUvs(newUvX, newUvY);
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if(mouseIsInside(mouseX, mouseY)){
            currentPointRatio.x = (-startX + mouseX) / uiWidth;
            currentPointRatio.y = (-startY + mouseY) / uiHeight;
            if(verticalAmount > 0){
                zoom(1);
                //zoom((float) (0.5f * Math.pow(0.9f, zoomLevel)));
            } else {
                dezoom(1);
                //dezoom((float) (0.5f * Math.pow(0.9f, zoomLevel)));
            }
            LoggerUtil.logDebugMsg("New Uvs : " + this.uvX + ", " + this.uvY);
        }
        return true;
    }

    private boolean mouseIsInside(double mouseX, double mouseY) {
        return ((mouseX > startX && mouseX < startX + uiWidth) && (mouseY > startY && mouseY < startY + uiHeight));
    }

    public void zoom(){
        this.currentPointRatio = new Vector2d(0.5, 0.5);
        zoom(2.5f);
    }
    public void dezoom(){
        this.currentPointRatio = new Vector2d(0.5, 0.5);
        dezoom(2.5f);
    }

    public void zoom(float amount) {
        if(zoomLevel != 35f) {
            double newZoom = Math.min(35f,  zoomLevel + amount);
            updateCurrentMapTargetRatio(zoomLevel);
            zoomLevel =(float)  newZoom;
            computeNewZoom();
        }
    }
    public void dezoom(float amount) {
        if(zoomLevel != 1f) {
            double newZoom = Math.max(1f, zoomLevel - amount);
            updateCurrentMapTargetRatio(zoomLevel);
            zoomLevel = (float) newZoom;
            computeNewZoom();
        }
    }

    private void updateCurrentMapTargetRatio(double zoom){
        double ratioX = (this.uvX + (uiWidth * currentPointRatio.x)) / uiCurrentWidth;
        double ratioY = (this.uvY + (uiHeight * currentPointRatio.y)) / uiCurrentHeight;

        this.currentMapTargetRatio = new Vector2d(ratioX, ratioY);
        this.currentUiTargetRatio = new Vector2d(currentPointRatio.x, currentPointRatio.y);
    }

    private void computeNewZoom(){

        float newUiCurrentWidth = uiWidth * zoomLevel;
        float newUiCurrentHeight = uiHeight * zoomLevel;

        uiCurrentWidth = newUiCurrentWidth;
        uiCurrentHeight = newUiCurrentHeight;

        int newUvX = (int) (uiCurrentWidth * currentMapTargetRatio.x - (uiWidth * currentUiTargetRatio.x));
        int newUvY = (int) (uiCurrentHeight * currentMapTargetRatio.y  - (uiHeight * currentUiTargetRatio.y));

        computeUvs(newUvX, newUvY);
    }

    private void computeUvs(int newUvX, int newUvY){
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
