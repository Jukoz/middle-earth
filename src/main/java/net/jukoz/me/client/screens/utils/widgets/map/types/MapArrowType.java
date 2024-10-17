package net.jukoz.me.client.screens.utils.widgets.map.types;

import net.jukoz.me.client.screens.utils.widgets.UiDirections;
import org.joml.Vector2i;

import java.util.HashMap;

public enum MapArrowType {
    NORMAL(1, 1,11,11),
    GOLDEN(49, 1,11,11);
    final HashMap<UiDirections, Integer> basicUvPerDirection;
    public final Vector2i size;
    final int baseUvX;
    final int hoveredUvX;
    final int focusedUvX;
    MapArrowType(int startUvX, int startUvY, int sizeX, int sizeY) {
        this.size = new Vector2i(sizeX, sizeY);

        this.baseUvX = startUvX;
        this.hoveredUvX = baseUvX + sizeX + 2;
        this.focusedUvX = hoveredUvX + sizeX + 2;

        this.basicUvPerDirection = new HashMap<>();
        // Setting all the uvs
        int currentUvY = startUvY;
        this.basicUvPerDirection.put(UiDirections.NORTH, currentUvY);
        currentUvY += sizeY + 2;
        this.basicUvPerDirection.put(UiDirections.SOUTH, currentUvY);
        currentUvY += sizeY + 2;
        this.basicUvPerDirection.put(UiDirections.WEST, currentUvY);
        currentUvY += sizeY + 2;
        this.basicUvPerDirection.put(UiDirections.EAST, currentUvY);
        currentUvY += sizeY + 2;
        this.basicUvPerDirection.put(UiDirections.NORTH_WEST, currentUvY);
        currentUvY += sizeY + 2;
        this.basicUvPerDirection.put(UiDirections.SOUTH_WEST, currentUvY);
        currentUvY += sizeY + 2;
        this.basicUvPerDirection.put(UiDirections.NORTH_EAST, currentUvY);
        currentUvY += sizeY + 2;
        this.basicUvPerDirection.put(UiDirections.SOUTH_EAST, currentUvY);
    }

    public Vector2i getUvs(UiDirections direction){
        return new Vector2i(baseUvX, this.basicUvPerDirection.get(direction));
    }
    public Vector2i getHoveredUvs(UiDirections direction){
        return new Vector2i(hoveredUvX, this.basicUvPerDirection.get(direction));
    }
    public Vector2i getFocusedUvs(UiDirections direction){
        return new Vector2i(focusedUvX, this.basicUvPerDirection.get(direction));
    }
}
