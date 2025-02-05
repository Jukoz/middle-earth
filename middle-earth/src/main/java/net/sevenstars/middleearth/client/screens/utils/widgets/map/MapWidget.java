package net.sevenstars.middleearth.client.screens.utils.widgets.map;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.screens.utils.widgets.ModWidget;
import net.sevenstars.middleearth.client.screens.utils.widgets.UiDirections;
import net.sevenstars.middleearth.world.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.world.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.world.chunkgen.map.ImageUtils;
import net.sevenstars.middleearth.world.map.MiddleEarthMapConfigs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.joml.Vector2d;
import org.joml.Vector2i;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MapWidget extends ModWidget {
    protected final static double MAP_TO_WORLD_RATIO = (double) MiddleEarthMapConfigs.REGION_SIZE / MiddleEarthMapConfigs.FULL_MAP_SIZE;
    protected final int uiWidth, uiHeight;
    protected int startX, startY = 0;
    protected float uiCurrentWidth, uiCurrentHeight;
    private final static int DRAG_COOLDOWN = 25;
    protected static Double uvX;
    protected static Double uvY;
    protected static Float zoomLevel;
    private Float zoomTarget;
    private boolean isForcingTargetMovement = false;
    private Vector2d forcedCurrentMapCenterTargetRatio;
    protected static Vector2d currentPointRatio;
    private static Vector2d currentMapTargetRatio;
    private static Vector2d currentUiTargetRatio;
    private boolean isDragging = false;
    private Vector2d nextUvs = null;
    private float cooldown = 0;

    protected static boolean isOverlayEnabled = false;

    BufferedImage mapImage;

    public MapWidget(int mapWidth, int mapHeight) {
        this.uiWidth = mapWidth;
        this.uiHeight = mapHeight;
        this.uiCurrentWidth = uiWidth;
        this.uiCurrentHeight = uiHeight;
        if(zoomLevel == null)
            zoomLevel = getMinZoom();
        zoomTarget = zoomLevel;
        if(currentPointRatio == null)
            currentPointRatio = new Vector2d(.5, .5);
        try{
            mapImage = ImageUtils.fetchResourceImage(MiddleEarthMapConfigs.INITIAL_IMAGE);
        } catch (IOException e) {
            MiddleEarth.LOGGER.logDebugMsg("MapWidget::Couldn't find %s".formatted(MiddleEarthMapConfigs.INITIAL_IMAGE));
        }

        if(uvX == null || uvY == null){
            uvX = 0.0;
            uvY = 0.0;
            updateCurrentMapTargetRatio();
        }
        computeZoom(true);
    }
    private float getZoomTransitionSpeed(){
        return 0.35f * (zoomLevel / 4f);
    }
    private float getMovementSpeed(){
        return zoomLevel / 4;
    }
    private float getMaxZoom(){
        return 100f;
    }
    private float getMinZoom(){
        return 1f;
    }
    protected Identifier getMapTexture(){
        return Identifier.of(MiddleEarth.MOD_ID,"textures/map.png");
    }
    protected Identifier getOverlayMapTexture(){
        return Identifier.of(MiddleEarth.MOD_ID,"textures/map_overlay.png");
    }
    public void setOverlayState(boolean state){
        isOverlayEnabled = state;
    }

    public boolean isOverlayEnabled(){
        return isOverlayEnabled;
    }

    public boolean haveForcedMapTarget(){
        return isForcingTargetMovement;
    }
    public Vector2d getMapPointFromMapCoordinate(Vector2d point){
        int mapSize = MiddleEarthMapConfigs.REGION_SIZE;
        point.x = (point.x / mapSize * getCurrentWidth()) - uvX + startX;
        point.y = (point.y / mapSize * getCurrentWidth()) - uvY + startY;
        return point;
    }

    public Vector2d getMapPointFromWorldCoordinate(Vector2d point){
        point.x *= MAP_TO_WORLD_RATIO;
        point.y *= MAP_TO_WORLD_RATIO;
        return getMapPointFromMapCoordinate(point);
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

    protected void draw(DrawContext context, int startX, int startY){
        this.startX = startX;
        this.startY = startY;

        if(cooldown > 0) {
            cooldown = Math.max(cooldown - 1, 1);
        }
        if(nextUvs != null){
            uvX = nextUvs.x;
            uvY = nextUvs.y;
            nextUvs = null;
            zoomLevel = zoomTarget;
        }
        computeZoom();
        if(forcedCurrentMapCenterTargetRatio != null){
          computeForcedMovement();
        }
        drawMapTexture(context, startX, startY);
        if(isOverlayEnabled){
            drawOverlayMapTexture(context, startX, startY);
        }
    }

    protected void drawMapTexture(DrawContext context, int startX, int startY) {
        int size = Math.max(getCurrentWidth(), getCurrentHeight());

        context.drawTexture(getMapTexture(),
                startX, startY,
                uvX.floatValue(), uvY.floatValue(),
                getWidth(), getHeight(),
                size, size
        );
    }

    protected void drawOverlayMapTexture(DrawContext context, int startX, int startY) {
        int size = Math.max(getCurrentWidth(), getCurrentHeight());

        context.drawTexture(getOverlayMapTexture(),
                startX, startY,
                uvX.floatValue(), uvY.floatValue(),
                getWidth(), getHeight(),
                size, size
        );
    }

    protected int getCurrentWidth() {
        return (int) uiCurrentWidth;
    }
    protected int getCurrentHeight() {
        return (int) uiCurrentHeight;
    }
    private void computeForcedMovement() {
        Vector2d currentUvs = new Vector2d(uvX.intValue(), uvY.intValue());
        Vector2d targetUV = new Vector2d(
                (getCurrentWidth() * forcedCurrentMapCenterTargetRatio.x) - (getWidth() / 2.0),
                (getCurrentWidth() * forcedCurrentMapCenterTargetRatio.y) - (getHeight() / 2.0)
        );
        if((int) targetUV.x != (int) currentUvs.x || (int) targetUV.y != (int) currentUvs.y){
            targetUV = verifyUvs(targetUV.x, targetUV.y);

            double distanceForSpeed = targetUV.distance(currentUvs);
            float basicSpeed = getMovementSpeed();
            double speed = Math.min(Math.max(basicSpeed, basicSpeed * (distanceForSpeed / 20)), distanceForSpeed);
            double radians = Math.toRadians(getDegreeAngleFromVectors(targetUV, currentUvs));
            double directionX = (Math.cos(radians)) * speed;
            if(currentUvs.x > targetUV.x)
                directionX *= -1;

            double directionY = (Math.sin(radians)) * speed;
            if(directionX < 0)
                directionY *= -1;

            int distance = (int) Math.round(targetUV.distance(currentUvs));
            if(distance > 4)
                computeUvs(uvX + directionX, uvY + directionY);
        }
        currentMapTargetRatio = getCurrentMapCenterRatio();
    }

    protected int getWidth() {
        return uiWidth;
    }
    protected int getHeight() {
        return uiHeight;
    }

    private double getDegreeAngleFromVectors(Vector2d source, Vector2d target) {
        double m = (target.y - source.y) / (target.x - source.x);
        double radians = Math.atan(m);
        return radians * (180 / Math.PI);
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
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if(button == 0 && cooldown == 0 && (isDragging || mouseIsInside(mouseX, mouseY))) {
            clearFocus();
            int newUvX = (int) (uvX - deltaX);
            int newUvY = (int) (uvY - deltaY);
            setCurrentPointRatioToCursor(mouseX, mouseY);
            updateCurrentMapTargetRatio();
            zoomTarget = zoomLevel; // Cancels the zoom

            computeUvs(newUvX, newUvY);
            this.nextUvs = null;
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if(mouseIsInside(mouseX, mouseY)){
            clearFocus();
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

    public void setCurrentPointRatioToCursor(double mouseX, double mouseY){
        currentPointRatio.x = (-startX + mouseX) / getWidth();
        currentPointRatio.y = (-startY + mouseY) / getHeight();
    }


    protected boolean mouseIsInside(double mouseX, double mouseY) {
        return ((mouseX > startX && mouseX < startX + getWidth()) && (mouseY > startY && mouseY < startY + getHeight()));
    }

    public void zoomClick(){
        addCooldown();
        isDragging = false;
        currentPointRatio = new Vector2d(0.5, 0.5);
        zoom(1f + (zoomTarget / 2f));
    }

    public void dezoomClick(){
        addCooldown();
        isDragging = false;
        currentPointRatio = new Vector2d(0.5, 0.5);
        dezoom(1f + (zoomTarget / 2f));

    }

    public void addCooldown(){
        cooldown = DRAG_COOLDOWN;
    }

    public void zoom(float amount) {
        float maxZoom = getMaxZoom();
        if(zoomTarget != maxZoom) {
            double newZoom = Math.min(maxZoom,  zoomTarget + amount);
            zoomTarget = (float)newZoom;
            updateCurrentMapTargetRatio();
        }
    }
    public void dezoom(float amount) {
        float minZoom = getMinZoom();
        if(zoomTarget != minZoom) {
            double newZoom = Math.max(minZoom, zoomTarget - amount);
            zoomTarget = (float)newZoom;
            updateCurrentMapTargetRatio();
        }
    }

    public boolean canZoomIn(){
        return zoomTarget != getMaxZoom();
    }

    public boolean canZoomOut(){
        return zoomTarget != getMinZoom();
    }

    public Vector2d getCurrentMapCenterRatio() {
        int maxSize = Math.max(getCurrentWidth(), getCurrentHeight());
        double ratioX = (uvX + (getWidth() * 0.5)) / maxSize;
        double ratioY = (uvY + (getHeight() * 0.5)) / maxSize;
        return new Vector2d(ratioX, ratioY);
    }
    public void instantCenterOnRatio(Vector2d mapCenter) {
        int maxSize = Math.max(getCurrentWidth(), getCurrentHeight());
        double newUvX = (maxSize * mapCenter.x) - (getWidth() * 0.5);
        double newUvY = (maxSize * mapCenter.y) - (getHeight() * 0.5);
        computeUvs(newUvX, newUvY);
        currentPointRatio = new Vector2d(0.5,0.5);
        updateCurrentMapTargetRatio();
        computeZoom();
    }

    private void updateCurrentMapTargetRatio(){
        double ratioX = (uvX + (getWidth() * currentPointRatio.x)) / getCurrentWidth();
        double ratioY = (uvY + (getHeight() * currentPointRatio.y)) / getCurrentHeight();

        currentMapTargetRatio = new Vector2d(ratioX, ratioY);
        currentUiTargetRatio = new Vector2d(currentPointRatio.x, currentPointRatio.y);
    }

    protected void computeZoom(){
        computeZoom(false);
    }

    protected void computeZoom(boolean forced){
        if(!forced && zoomLevel.equals(zoomTarget))
            return;

        float zoomModifier = getZoomTransitionSpeed();
        if(zoomLevel > zoomTarget){
            zoomLevel = Math.max(zoomTarget, zoomLevel - zoomModifier);
        } else {
            zoomLevel = Math.min(zoomTarget, zoomLevel + zoomModifier);
        }

        float newUiCurrentWidth = getWidth() * zoomLevel;
        float newUiCurrentHeight = getHeight() * zoomLevel;

        uiCurrentWidth = newUiCurrentWidth;
        uiCurrentHeight = newUiCurrentHeight;

        double newUvX = getCurrentWidth() * currentMapTargetRatio.x - (getWidth() * currentUiTargetRatio.x);
        double newUvY = getCurrentHeight() * currentMapTargetRatio.y  - (getHeight() * currentUiTargetRatio.y);

        computeUvs(newUvX, newUvY);
    }

    protected void computeUvs(double newUvX, double newUvY){
        Vector2d computedUvs = verifyUvs(newUvX, newUvY);
        uvX = computedUvs.x;
        uvY = computedUvs.y;
    }

    protected Vector2d verifyUvs(double newUvX, double newUvY){
        int height = getHeight();
        int width = getWidth();

        int maxSquareSize = Math.max(height, width);

        int maxWidth = (int) ((maxSquareSize * zoomLevel) - width);
        int maxHeight = (int) ((maxSquareSize * zoomLevel) - height);

        double computedX = Math.min(maxWidth, newUvX);
        computedX = Math.max(0, computedX);

        double computedY = Math.min(maxHeight, newUvY);
        computedY = Math.max(0, computedY);

        return new Vector2d(computedX, computedY);
    }

    public void moveTo(Vector2i worldCoordinates, Vector2d desiredZoomTargetMax){
        double minimumZoom = Math.max(getMinZoom(), desiredZoomTargetMax.x);
        double maximumZoom = Math.min(getMaxZoom(), desiredZoomTargetMax.y);
        zoomTarget = (float) Math.min(maximumZoom, Math.max(minimumZoom, zoomLevel));
        forcedCurrentMapCenterTargetRatio = new Vector2d(
                (double)worldCoordinates.x * MAP_TO_WORLD_RATIO / MiddleEarthMapConfigs.REGION_SIZE,
                (double)worldCoordinates.y * MAP_TO_WORLD_RATIO / MiddleEarthMapConfigs.REGION_SIZE
        );
        isForcingTargetMovement = true;
        currentPointRatio = new Vector2d(0.5, 0.5);
    }

    public Vector2d getCurrentMapRatio(double mouseX, double mouseY) {
        if(!mouseIsInside(mouseX, mouseY))
            return null;
        int size =  Math.max(getWidth(), getHeight());
        int currentSize =  Math.max(getCurrentWidth(), getCurrentHeight());

        double mouseRatioX = (-startX + mouseX) / size;
        double mouseRatioY = (-startY + mouseY) / size;
        return new Vector2d(
                (uvX + (size * mouseRatioX)) / currentSize,
                (uvY + ( size * mouseRatioY)) / currentSize
        );
    }

    public void clearFocus() {
        forcedCurrentMapCenterTargetRatio = null;
        isForcingTargetMovement = false;
    }

    public UiDirections isOutsideBounds(Vector2d uvs, int offsetX, int offsetY) {
        boolean outOfBoundNorth = uvs.y - offsetY < startY;
        boolean outOfBoundSouth = uvs.y + offsetY  > startY + getHeight();
        boolean outOfBoundEast = uvs.x + offsetX > startX + getWidth();
        boolean outOfBoundWest = uvs.x - offsetX < startX;

        if(outOfBoundNorth){
            if(outOfBoundEast)
                return UiDirections.NORTH_EAST;
            if(outOfBoundWest)
                return UiDirections.NORTH_WEST;
            return UiDirections.NORTH;
        }
        if(outOfBoundSouth){
            if(outOfBoundEast)
                return UiDirections.SOUTH_EAST;
            if(outOfBoundWest)
                return UiDirections.SOUTH_WEST;
            return UiDirections.SOUTH;
        }
        if(outOfBoundEast)
            return UiDirections.EAST;
        if(outOfBoundWest)
            return UiDirections.WEST;

        return UiDirections.NONE;
    }

    public UiDirections isOutsideBounds(Vector2d uvs, int offsetX, int offsetY, Rectangle2D borders) {
        boolean outOfBoundNorth = uvs.y - offsetY < startY + borders.getY();
        boolean outOfBoundSouth = uvs.y + offsetY  > startY + borders.getY() + borders.getHeight();
        boolean outOfBoundEast = uvs.x + offsetX > startX + borders.getX() + getWidth();
        boolean outOfBoundWest = uvs.x - offsetX < startX  + borders.getX();

        if(outOfBoundNorth){
            if(outOfBoundEast)
                return UiDirections.NORTH_EAST;
            if(outOfBoundWest)
                return UiDirections.NORTH_WEST;
            return UiDirections.NORTH;
        }
        if(outOfBoundSouth){
            if(outOfBoundEast)
                return UiDirections.SOUTH_EAST;
            if(outOfBoundWest)
                return UiDirections.SOUTH_WEST;
            return UiDirections.SOUTH;
        }
        if(outOfBoundEast)
            return UiDirections.EAST;
        if(outOfBoundWest)
            return UiDirections.WEST;

        return UiDirections.NONE;
    }

    public MapBasedCustomBiome getBiomeAt(int x, int y) {
        try{
            MapBasedCustomBiome biome = MapBasedBiomePool.getBiomeByColor(mapImage.getRGB(x, y));
            if(biome == null)
                return MapBasedBiomePool.defaultBiome;
            else return biome;
        } catch (Exception e) {
            return MapBasedBiomePool.defaultBiome;
        }
    }
}
