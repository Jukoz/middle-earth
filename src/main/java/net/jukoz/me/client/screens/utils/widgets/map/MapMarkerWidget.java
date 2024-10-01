package net.jukoz.me.client.screens.utils.widgets.map;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.utils.widgets.ModWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Vector2d;
import org.joml.Vector2i;

public class MapMarkerWidget extends ModWidget {
    private static final Identifier MAP_MARKERS = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/widget/map_markers.png");

    ButtonWidget markerButton;
    MapMarkerType type;

    // runtime
    Vector2i runtimeStartCoordinates = null;
    Vector2i runtimeUvs = null;
    Vector2i runtimeHaveManyUvs = null;
    Vector2i runtimeSize = null;
    boolean hasMany = false;
    boolean isArrow = false;

    public MapMarkerWidget(String name, ButtonWidget.PressAction onPress) {
        super();
        markerButton = ButtonWidget.builder(Text.of(name), onPress).build();
        type = MapMarkerType.NONE;
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
        MapMarkerArrowDirection outOfBoundDirection = mapWidget.isOutsideBounds(centerUvs, type.sizeX /2, type.sizeY / 2);

        centerUvs.x -= type.sizeX / 2.0;
        centerUvs.y -= type.sizeY / 2.0;
        if(outOfBoundDirection == MapMarkerArrowDirection.NONE) {
            computeMarker(centerUvs);
            return;
        }
        computeArrow(mapWidget, outOfBoundDirection, centerUvs);
    }

    protected void computeArrow(MapWidget mapWidget, MapMarkerArrowDirection direction, Vector2d starts) {
        int minX = mapWidget.startX;
        int minY = mapWidget.startY;
        int maxX = mapWidget.startX + mapWidget.uiWidth - direction.sizeX;
        int maxY = mapWidget.startY + mapWidget.uiHeight - direction.sizeY;

        runtimeStartCoordinates = new Vector2i(
                Math.min(maxX, Math.max(minX, (int) starts.x)),
                Math.min(maxY, Math.max(minY, (int) starts.y)));
        runtimeUvs = new Vector2i(direction.uvX, direction.uvY);
        runtimeSize = new Vector2i(direction.sizeX, direction.sizeY);
        runtimeHaveManyUvs = null;
        isArrow = true;
    }

    protected void computeMarker(Vector2d starts) {
        runtimeStartCoordinates = new Vector2i((int) starts.x, (int) starts.y);
        runtimeUvs = new Vector2i(type.uvX, type.uvY);
        runtimeSize = new Vector2i(type.sizeX, type.sizeY);
        runtimeHaveManyUvs = new Vector2i(type.manyOverlayUvX, type.manyOverlayUvY);
        isArrow = false;
    }

    public void draw(DrawContext context) {
        context.drawTexture(MAP_MARKERS,
                runtimeStartCoordinates.x,
                runtimeStartCoordinates.y,
                runtimeUvs.x, runtimeUvs.y,
                runtimeSize.x, runtimeSize.y
        );
        if(hasMany && runtimeHaveManyUvs != null){
            context.drawTexture(MAP_MARKERS,
                    runtimeStartCoordinates.x,
                    runtimeStartCoordinates.y,
                    runtimeHaveManyUvs.x, runtimeHaveManyUvs.y,
                    runtimeSize.x, runtimeSize.y
            );
        }
    }

    public Vector2i getCenterCoordinates() {
        Vector2i startCoordinates = runtimeStartCoordinates;
        startCoordinates.x += runtimeSize.x / 2;
        startCoordinates.y += runtimeSize.y / 2;
        return startCoordinates;
    }

    public void assignNewCenter(Vector2i newCenter) {
        this.runtimeStartCoordinates.x = newCenter.x - (runtimeSize.x / 2);
        this.runtimeStartCoordinates.y = newCenter.y - (runtimeSize.y / 2);
    }

    public void updateMarkerType(MapMarkerType mapMarkerType) {
        if(isArrow) return;

        type = mapMarkerType;
        Vector2i center = getCenterCoordinates();
        runtimeSize = new Vector2i(mapMarkerType.sizeX, mapMarkerType.sizeY);
        runtimeStartCoordinates = new Vector2i(center.x - mapMarkerType.sizeX / 2, center.y - mapMarkerType.sizeY / 2);
        runtimeUvs = new Vector2i(mapMarkerType.uvX , mapMarkerType.uvY);
        if(hasMany)
            runtimeHaveManyUvs = new Vector2i(mapMarkerType.manyOverlayUvX , mapMarkerType.manyOverlayUvY);
    }
}
