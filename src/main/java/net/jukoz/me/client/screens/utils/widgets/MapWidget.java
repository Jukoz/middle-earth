package net.jukoz.me.client.screens.utils.widgets;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.map.MiddleEarthMapConfigs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class MapWidget extends ModWidget {
    private static final Identifier MIDDLE_EARTH_WORLD_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/map.png");
    private final int uiWidth, uiHeight;
    private int uvX, uvY = 0;
    private int startX, startY = 0;
    private float zoomLevel = 1f;
    private float currentWidth, currentHeight;

    public MapWidget(int mapWidth, int mapHeight) {
        this.uiWidth = mapWidth;
        this.uiHeight = mapHeight;
        this.currentWidth = mapWidth;
        this.currentHeight = mapHeight;
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

        int textureWidth = (int) (uiWidth * 0.5); // Apply zoom?
        int textureHeight = (int) (uiHeight * 0.5); // Apply zoom?

        // TODO : In progress
        context.drawTexture(MIDDLE_EARTH_WORLD_TEXTURE,
                startX, startY,
                uvX, uvY,
                uiWidth,uiHeight,
                (int)currentWidth, (int)currentHeight
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
        if(button == 0 &&  isMouseOver(uiWidth, uiHeight, startX, startY)){
            int newUvX = (int) (this.uvX - deltaX);
            int newUvY = (int) (this.uvY - deltaY);

            float maxRatioX = (float) uiWidth / MiddleEarthMapConfigs.REGION_SIZE * newUvX;
            float maxRatioY = (float) uiHeight / MiddleEarthMapConfigs.REGION_SIZE * newUvY;

            // TODO : Need better control over drag
            // Removed temporarily, will be continued

            //LoggerUtil.logDebugMsg(maxRatioX + " (" + newUvX + "), " + maxRatioY + " (" + newUvY + ")");
            /*
            if(maxRatioX >= 0 && maxRatioX <= 1)
                this.uvX = newUvX;

            if(maxRatioY >= 0 && maxRatioY <= 1)
                this.uvY = newUvY;
             */
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if(verticalAmount > 0){
            zoom();
        } else {
            dezoom();
        }
        return true;
    }

    public void zoom() {
        if(zoomLevel != 15f) {
            zoomLevel = Math.min(15f,  zoomLevel + 1f);
            computeNewOffset();
        }
    }

    public void dezoom() {
        if(zoomLevel != 1f) {
            zoomLevel = Math.max(1f, zoomLevel - 1f);
            computeNewOffset();
        }
    }

    private void computeNewOffset(){
        float newCurrentWidth = uiWidth * zoomLevel;
        float newCurrentHeight = uiHeight * zoomLevel;

        int newUvX = (int) (uvX + ((newCurrentWidth - currentWidth) / 2));
        int newUvY = (int) (uvY + ((newCurrentHeight - currentHeight) / 2));

        currentWidth = newCurrentWidth;
        currentHeight = newCurrentHeight;

        uvX = (int) Math.max(0, Math.min(3000 - newCurrentWidth, newUvX));
        uvY = (int) Math.max(0, Math.min(3000 - newCurrentHeight, newUvY));
    }
}
