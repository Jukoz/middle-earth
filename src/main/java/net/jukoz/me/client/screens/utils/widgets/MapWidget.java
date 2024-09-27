package net.jukoz.me.client.screens.utils.widgets;

import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
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
    private double uvX, uvY = 0;
    private int startX, startY = 0;
    private float zoomLevel = 1f;
    private float zoomTarget = zoomLevel;
    private final float ZOOM_TRANSITION_SPEED = 1f / 5f;

    private Vector2d currentPointRatio;
    private Vector2d currentMapTargetRatio;

    private Vector2d currentUiTargetRatio;

    private float uiCurrentWidth, uiCurrentHeight;

    private Vector2d nextUvs = null;

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

        if(nextUvs != null){
            this.uvX = nextUvs.x;
            this.uvY = nextUvs.y;
            nextUvs = null;
        } else {
            if(zoomLevel != zoomTarget){
                float zoomModifier = ZOOM_TRANSITION_SPEED;
                if(zoomLevel > zoomTarget){
                    zoomLevel = Math.max(zoomTarget, zoomLevel - zoomModifier);
                } else {
                    zoomLevel = Math.min(zoomTarget, zoomLevel + zoomModifier);
                }
            }
            computeZoom();
        }
        context.drawTexture(MIDDLE_EARTH_WORLD_TEXTURE,
                startX, startY,
                (float) uvX, (float) uvY,
                uiWidth,uiHeight,
                (int)uiCurrentWidth, (int)uiCurrentHeight
        );
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
            updateCurrentMapTargetRatio(zoomLevel);
            zoomTarget = zoomLevel; // Cancels the zoom

            computeUvs(newUvX, newUvY);
            this.nextUvs = new Vector2d(this.uvX, this.uvY);
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
        if(zoomTarget != 35f) {
            double newZoom = Math.min(35f,  zoomTarget + amount);
            updateCurrentMapTargetRatio(zoomLevel);
            zoomTarget = (float)newZoom;
        }
    }
    public void dezoom(float amount) {
        if(zoomTarget != 1f) {
            double newZoom = Math.max(1f, zoomTarget - amount);
            updateCurrentMapTargetRatio(zoomLevel);
            zoomTarget = (float)newZoom;
        }
    }

    private void updateCurrentMapTargetRatio(double zoom){
        double ratioX = (this.uvX + (uiWidth * currentPointRatio.x)) / uiCurrentWidth;
        double ratioY = (this.uvY + (uiHeight * currentPointRatio.y)) / uiCurrentHeight;

        this.currentMapTargetRatio = new Vector2d(ratioX, ratioY);
        this.currentUiTargetRatio = new Vector2d(currentPointRatio.x, currentPointRatio.y);
    }

    private void computeZoom(){

        float newUiCurrentWidth = uiWidth * zoomLevel;
        float newUiCurrentHeight = uiHeight * zoomLevel;

        uiCurrentWidth = newUiCurrentWidth;
        uiCurrentHeight = newUiCurrentHeight;

        double newUvX = uiCurrentWidth * currentMapTargetRatio.x - (uiWidth * currentUiTargetRatio.x);
        double newUvY = uiCurrentHeight * currentMapTargetRatio.y  - (uiHeight * currentUiTargetRatio.y);

        computeUvs(newUvX, newUvY);
    }

    private void computeUvs(double newUvX, double newUvY){
        int maxWidth = (int) (uiWidth * zoomLevel) - uiWidth;
        int maxHeight = (int) (uiHeight * zoomLevel) - uiHeight;;

        double computedX = Math.min(maxWidth, newUvX);
        computedX = Math.max(0, computedX);

        double computedY = Math.min(maxHeight, newUvY);
        computedY = Math.max(0, computedY);

        this.uvX = computedX;
        this.uvY = computedY;
    }
}
