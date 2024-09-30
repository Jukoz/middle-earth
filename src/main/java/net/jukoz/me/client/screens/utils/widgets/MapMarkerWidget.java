package net.jukoz.me.client.screens.utils.widgets;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.utils.MapMarkerType;
import net.jukoz.me.utils.LoggerUtil;
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
        worldPosition = mapWidget.getMapPointFromWorldCoordinate(worldPosition);
        drawCentered(context, (int) worldPosition.x, (int) worldPosition.y);
    }

    public void drawFromMapPosition(DrawContext context, MapWidget mapWidget, Vector2d mapPosition){
        mapPosition = mapWidget.getMapPointFromMapCoordinate(mapPosition);
        drawCentered(context, (int) mapPosition.x, (int) mapPosition.y);
    }

    private void drawCentered(DrawContext context, int centerX, int centerY){
        int startX = centerX - (type.sizeX / 2);
        centerY -= type.sizeY / 2;
        draw(context, startX, centerY);
    }

    protected void draw(DrawContext context, int startX, int startY) {
        context.drawTexture(MAP_MARKERS,
                startX,
                startY,
                type.uvX, type.uvY,
                type.sizeX, type.sizeY
        );
    }
}
