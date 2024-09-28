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
    private final static int DRAG_COOLDOWN = 25;

    public boolean canZoomIn;
    public boolean canZoomOut;

    private final int uiWidth, uiHeight;
    private double uvX, uvY = 0;
    private int startX, startY = 0;
    private float zoomLevel = getMinZoom();
    private float zoomTarget = zoomLevel;
    private Vector2d currentPointRatio;
    private Vector2d currentMapTargetRatio;
    private Vector2d currentUiTargetRatio;
    private float uiCurrentWidth, uiCurrentHeight;
    private boolean isDragging = false;
    private Vector2d nextUvs = null;
    private float cooldown = 0;

    public MapWidget(int mapWidth, int mapHeight) {
        this.uiWidth = mapWidth;
        this.uiHeight = mapHeight;
        this.uiCurrentWidth = uiWidth;
        this.uiCurrentHeight = uiHeight;
        this.canZoomOut = false;
        this.canZoomIn = true;
        this.currentPointRatio = new Vector2d(.5, .5);
        updateCurrentMapTargetRatio(zoomLevel);
    }

    private float getZoomTransitionSpeed(){
        return 0.35f * (zoomLevel / 4f);
    }
    private float getMaxZoom(){
        return 70f;
    }

    private float getMinZoom(){
        return 1f;
    }

    private Identifier getMapTexture(){
        return Identifier.of(MiddleEarth.MOD_ID,"textures/map.png");
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

        if(cooldown > 0) {
            cooldown = Math.max(cooldown - 1, 1);
        }
        if(nextUvs != null){
            this.uvX = nextUvs.x;
            this.uvY = nextUvs.y;
            nextUvs = null;
            zoomLevel = zoomTarget;

        } else if(zoomLevel != zoomTarget){
            float zoomModifier = getZoomTransitionSpeed();
            if(zoomLevel > zoomTarget){
                zoomLevel = Math.max(zoomTarget, zoomLevel - zoomModifier);
            } else {
                zoomLevel = Math.min(zoomTarget, zoomLevel + zoomModifier);
            }
            computeZoom();
        }

        context.drawTexture(getMapTexture(),
                startX, startY,
                (float) uvX, (float) uvY,
                uiWidth,uiHeight,
                (int)uiCurrentWidth, (int)uiCurrentHeight
        );
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(cooldown <= 1 && mouseIsInside(mouseX, mouseY)){
            isDragging = true;
        }
        return true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        isDragging = false;
        cooldown = 0;
        return true;
    }

    public void setCurrentPointRatioToCursor(double mouseX, double mouseY){
        currentPointRatio.x = (-startX + mouseX) / uiWidth;
        currentPointRatio.y = (-startY + mouseY) / uiHeight;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if(button == 0 && cooldown == 0 && (isDragging || mouseIsInside(mouseX, mouseY))) {
            int newUvX = (int) (this.uvX - deltaX);
            int newUvY = (int) (this.uvY - deltaY);
            setCurrentPointRatioToCursor(mouseX, mouseY);
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
            float zoomAmount = 1f + (zoomTarget / 4f);
            if(verticalAmount > 0 && zoomLevel != getMaxZoom()){
                setCurrentPointRatioToCursor(mouseX, mouseY);
                zoom(zoomAmount);
            } else if(verticalAmount < 0 && zoomLevel != getMinZoom()) {
                setCurrentPointRatioToCursor(mouseX, mouseY);
                dezoom(zoomAmount);
            }

        }
        return true;
    }

    private boolean mouseIsInside(double mouseX, double mouseY) {
        return ((mouseX > startX && mouseX < startX + uiWidth) && (mouseY > startY && mouseY < startY + uiHeight));
    }

    public void zoomClick(){
        cooldown = DRAG_COOLDOWN;
        isDragging = false;
        this.currentPointRatio = new Vector2d(0.5, 0.5);
        zoom(1f + (zoomTarget / 2f));
    }

    public void dezoomClick(){
        cooldown = DRAG_COOLDOWN;
        isDragging = false;
        this.currentPointRatio = new Vector2d(0.5, 0.5);
        dezoom(1f + (zoomTarget / 2f));

    }

    public void zoom(float amount) {
        float maxZoom = getMaxZoom();
        if(zoomTarget != maxZoom) {
            this.canZoomOut = true;
            double newZoom = Math.min(maxZoom,  zoomTarget + amount);
            zoomTarget = (float)newZoom;
            if(zoomTarget == maxZoom){
                this.canZoomIn = false;
            }
            updateCurrentMapTargetRatio(zoomLevel);
        }
    }
    public void dezoom(float amount) {
        float minZoom = getMinZoom();
        if(zoomTarget != minZoom) {
            this.canZoomIn = true;
            double newZoom = Math.max(minZoom, zoomTarget - amount);
            zoomTarget = (float)newZoom;
            if(zoomTarget == minZoom){
                this.canZoomOut = false;
            }
            updateCurrentMapTargetRatio(zoomLevel);
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
