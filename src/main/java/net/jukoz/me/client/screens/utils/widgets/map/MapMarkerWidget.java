package net.jukoz.me.client.screens.utils.widgets.map;

import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.utils.widgets.ModWidget;
import net.jukoz.me.client.screens.utils.widgets.map.types.MapArrowType;
import net.jukoz.me.client.screens.utils.widgets.map.types.MapMarkerArrowDirections;
import net.jukoz.me.client.screens.utils.widgets.map.types.MapMarkerType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Vector2d;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MapMarkerWidget extends ModWidget {
    private static final Identifier MAP_MARKERS = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/widget/map_markers.png");
    private static final Identifier MAP_ARROWS = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/widget/map_arrows.png");

    private ButtonWidget markerButton;
    private MapMarkerType type;
    private MapArrowType arrowType;
    private MapMarkerArrowDirections arrowDirection;
    private Vector2i runtimeStartCoordinates = null;
    private boolean isArrow;
    private boolean isFocused;
    private List<Text> content;

    public MapMarkerWidget(String name, ButtonWidget.PressAction onPress) {
        super();
        markerButton = ButtonWidget.builder(Text.of(name), onPress).build();
        type = MapMarkerType.NONE;
        arrowType = MapArrowType.NORMAL;
        isArrow = false;
        isFocused = false;
    }

    public void setContent(List<Text> content){
        this.content = content;
    }

    public List<Text> getContent(){
        return this.content;
    }

    public void setType(MapMarkerType type){
        this.type = type;
    }

    public ButtonWidget getButton() {
        return markerButton;
    }

    public void computeFromWorldPosition(MapWidget mapWidget, Vector2d worldPosition){
        Vector2d uvs = mapWidget.getMapPointFromWorldCoordinate(worldPosition);
        computeCentered(mapWidget, uvs);
    }

    public void computeFromMapPosition(MapWidget mapWidget, Vector2d mapPosition){
        Vector2d uvs = mapWidget.getMapPointFromMapCoordinate(mapPosition);
        computeCentered(mapWidget, uvs);
    }

    private void computeCentered(MapWidget mapWidget, Vector2d centerUvs){
        if(centerUvs == null) return;
        MapMarkerArrowDirections outOfBoundDirection = mapWidget.isOutsideBounds(centerUvs, type.size.x /2, type.size.y / 2);

        centerUvs.x -= type.size.x / 2.0;
        centerUvs.y -= type.size.y / 2.0;
        if(outOfBoundDirection == MapMarkerArrowDirections.NONE) {
            computeMarker(centerUvs);
            return;
        }
        computeArrow(mapWidget, outOfBoundDirection, centerUvs);
    }

    protected void computeArrow(MapWidget mapWidget, MapMarkerArrowDirections direction, Vector2d starts) {
        isArrow = true;

        int sizeX = arrowType.size.x;
        int sizeY = arrowType.size.y;

        // Hardcoded, since values are uneven, some randomness can happen
        int minX = mapWidget.startX - (sizeX / 2) + 1;
        int minY = mapWidget.startY - (sizeX / 2) + 1;
        int maxX = mapWidget.startX + mapWidget.uiWidth - sizeX - (sizeX / 4);
        int maxY = mapWidget.startY + mapWidget.uiHeight - sizeY - (sizeY / 4);

        runtimeStartCoordinates = new Vector2i(
                Math.min(maxX, Math.max(minX, (int) starts.x)),
                Math.min(maxY, Math.max(minY, (int) starts.y)));
        arrowDirection = direction;
    }

    protected void computeMarker(Vector2d starts) {
        isArrow = false;

        runtimeStartCoordinates = new Vector2i((int) starts.x, (int) starts.y);
    }

    public void draw(DrawContext context) {
        Vector2i drawStart = new Vector2i(runtimeStartCoordinates.x, runtimeStartCoordinates.y);
        Vector2i drawSize = type.size;
        Vector2i drawUvs = type.uvs;

        Vector2i focusedStart = drawStart;
        Vector2i focusedSize = type.size;
        Vector2i focusedUvs = type.focusedUvs;

        Vector2i buttonSize = type.interactableSize;

        // Only change to arrow if it's an arrow
        if(isArrow){
            // Update start
            Vector2i size = arrowType.size;
            drawStart.x += (drawSize.x / 2) - (size.x / 2);
            drawStart.y += (drawSize.y / 2) - (size.y / 2);
            // Update size
            drawSize = size;
            // Update uvs
            drawUvs = arrowType.getUvs(arrowDirection);
            drawUvs.x = 1;
            // Update focused to be arrow specific
            focusedStart = new Vector2i(drawStart.x, drawStart.y);
            focusedSize = size;
            focusedUvs = arrowType.getFocusedUvs(arrowDirection);
            // Update button size
            buttonSize = size;
        }

        Vector2i buttonStartCoordinates = new Vector2i(drawStart.x, drawStart.y);
        buttonStartCoordinates.x += drawSize.x / 2 - buttonSize.x / 2;
        buttonStartCoordinates.y += drawSize.y / 2 - buttonSize.y / 2;

        markerButton.setDimensionsAndPosition(buttonSize.x, buttonSize.y, buttonStartCoordinates.x, buttonStartCoordinates.y);
        if(!markerButton.active)
            activateButton(true);

        boolean isFocused = markerButton.isFocused();
        boolean mouseIsOver = isMouseOver(buttonSize.x, buttonSize.y, buttonStartCoordinates.x, buttonStartCoordinates.y);
        if(isFocused || mouseIsOver){
            if(isArrow)
                drawUvs = arrowType.getHoveredUvs(arrowDirection);
            else
                drawUvs = type.hoveredUvs;
            if(!getFocusEnabled())
                isFocused = false;
        }
        if(mouseIsOver){
            context.drawTooltip(client.textRenderer, content, Optional.empty(), drawStart.x + 7, drawStart.y);
        }

        // draw marker or arrow
        context.drawTexture((isArrow) ? MAP_ARROWS : MAP_MARKERS,
                drawStart.x, drawStart.y,
                drawUvs.x, drawUvs.y,
                drawSize.x, drawSize.y
        );

        // draw marker or arrow (FOCUSED)
        if(isFocused){
            context.drawTexture((isArrow) ? MAP_ARROWS : MAP_MARKERS,
                    focusedStart.x, focusedStart.y,
                    focusedUvs.x, focusedUvs.y,
                    focusedSize.x, focusedSize.y
            );
        }
    }

    public void activateButton(boolean state){
        markerButton.active = state;
    }

    public Vector2i getCenterCoordinates() {
        Vector2i startCoordinates = runtimeStartCoordinates;
        Vector2i size = (isArrow) ? arrowType.size : type.size;

        startCoordinates.x += size.x / 2;
        startCoordinates.y += size.y / 2;
        return startCoordinates;
    }

    public void assignNewCenter(Vector2i newCenter) {
        Vector2i size = (isArrow) ? arrowType.size : type.size;
        this.runtimeStartCoordinates.x = newCenter.x - (size.x / 2);
        this.runtimeStartCoordinates.y = newCenter.y - (size.y / 2);
    }

    public void updateMarkerType(MapMarkerType mapMarkerType) {
        if(isArrow) return;

        type = mapMarkerType;
        Vector2i center = getCenterCoordinates();
        runtimeStartCoordinates = new Vector2i(center.x - mapMarkerType.size.x / 2, center.y - mapMarkerType.size.y / 2);
    }

    public void addContent(List<Text> appendContent) {
        if(content != null && !content.isEmpty()){
            List<Text> newContent = new ArrayList<>();
            newContent.addAll(this.content);
            newContent.addAll(appendContent);
            this.content = newContent;
        }
    }
}
