package net.jukoz.me.client.screens.utils.widgets;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.utils.MapMarkerType;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Vector2d;

public class MapMarkerWidget extends ModWidget {
    private static final Identifier MAP_MARKERS = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/widget/map_markers.png");

    ButtonWidget markerButton;
    MapMarkerType type;

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

    public void drawFromWorldPosition(DrawContext context, MapWidget mapWidget, Vector2d worldPosition){
        Vector2d uvs = mapWidget.getMapPointFromWorldCoordinate(worldPosition);
        drawCentered(context, mapWidget, uvs);
    }

    public void drawFromMapPosition(DrawContext context, MapWidget mapWidget, Vector2d mapPosition){
        Vector2d uvs = mapWidget.getMapPointFromMapCoordinate(mapPosition);
        drawCentered(context, mapWidget, uvs);
    }

    private void drawCentered(DrawContext context, MapWidget mapWidget, Vector2d centerUvs){
        if(centerUvs == null) return;
        MapMarkerArrowDirection outOfBoundDirection = mapWidget.isOutsideBounds(centerUvs, type.sizeX /2, type.sizeY / 2);

        centerUvs.x -= type.sizeX / 2.0;
        centerUvs.y -= type.sizeY / 2.0;
        if(outOfBoundDirection == MapMarkerArrowDirection.NONE) {
            draw(context, centerUvs);
            return;
        }
        drawArrow(context, mapWidget, outOfBoundDirection, centerUvs);
    }

    protected void drawArrow(DrawContext context, MapWidget mapWidget, MapMarkerArrowDirection direction, Vector2d starts) {
        context.drawTexture(MAP_MARKERS,
                Math.min(mapWidget.startX + mapWidget.uiWidth - direction.sizeX, Math.max(mapWidget.startX, (int) starts.x)),
                Math.min(mapWidget.startY + mapWidget.uiHeight - direction.sizeY, Math.max(mapWidget.startY, (int) starts.y)),
                direction.uvX, direction.uvY,
                direction.sizeX, direction.sizeY
        );
    }

    protected void draw(DrawContext context, Vector2d starts) {
        context.drawTexture(MAP_MARKERS,
                (int) starts.x,
                (int) starts.y,
                type.uvX, type.uvY,
                type.sizeX, type.sizeY
        );
    }
}
