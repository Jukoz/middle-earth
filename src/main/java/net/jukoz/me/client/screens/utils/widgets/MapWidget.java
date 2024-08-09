package net.jukoz.me.client.screens.utils.widgets;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.map.MiddleEarthMapConfigs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class MapWidget {
    private static final Identifier MAP_UI_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/map_ui.png");
    private static final Identifier MAP_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/map.png");
    int startX = 0;
    int startY = 0;
    private int mapWidth;
    private int mapHeight;
    int mouseX = 0;
    int mouseY = 0;

    int uvX = 0;
    int uvY = 0;

    public MapWidget(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    public void updateMouse(int mouseX, int mouseY){
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public void setStartCoordinates(int startX, int startY){
        this.startX = startX;
        this.startY = startY;
    }

    public void drawCentered(DrawContext context, int centerX, int startY){
        int startX = centerX - (mapWidth / 2);
        draw(context, startX, startY);
    }

    public void drawAnchored(DrawContext context, int anchorX, int startY, boolean isLeftAnchor){
        int startX = anchorX;
        if(!isLeftAnchor)
            startX -= mapWidth;

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
        // (int)(3000 / 26.25), // 0 - 26.25 [full zoom in - full zoom out]
        context.drawTexture(MAP_TEXTURE, startX, startY,
                // UV (x,y)
                uvX, uvY,
                mapWidth,
                mapHeight,
                3000 / (3000 / mapWidth),
                3000 / (3000 / mapHeight)
        );
    }



    public void mouseClicked(double mouseX, double mouseY, int button) {
        // TODO : Marker click? Need a better system..
        //LoggerUtil.logDebugMsg("Mouse is clicked at " + mouseX + ", " + mouseY);
    }

    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if(button == 0 &&  isMouseInside(mouseX, mouseY)){
            int newUvX = (int) (this.uvX - deltaX);
            int newUvY = (int) (this.uvY - deltaY);

            float maxRatioX = (float) mapWidth / MiddleEarthMapConfigs.REGION_SIZE * newUvX;
            float maxRatioY = (float) mapHeight / MiddleEarthMapConfigs.REGION_SIZE * newUvY;

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
    }

    private boolean isMouseInside(double mouseX, double mouseY){
        return (mouseX >= startX && mouseX <= startX + mapWidth
                && mouseY >= startY && mouseY <= startY + mapHeight);
    }

    public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        // TODO : Zoom control
        //LoggerUtil.logDebugMsg("Mouse is scrolled at " + mouseX + ", " + mouseY);
    }

    public void setSize(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }
}
