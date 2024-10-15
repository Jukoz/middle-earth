package net.jukoz.me.client.screens.utils.widgets.backgrounds.types;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.utils.widgets.UiDirections;
import net.minecraft.util.Identifier;
import org.joml.Vector2i;

public enum BackgroundContainerTypes {
    FULLSCREEN_MAP(Identifier.of(MiddleEarth.MOD_ID,"textures/gui/map_background.png"), 5, 1, 209);
    public final Identifier textureId;

    public final int size;
    private final int uvStartX;
    private final int uvStartY;
    BackgroundContainerTypes(Identifier texture, int size, int uvStartX, int uvStartY){
        this.textureId = texture;
        this.size = size;
        this.uvStartX = uvStartX;
        this.uvStartY = uvStartY;
    }

    public Vector2i getUvForDirection(UiDirections direction){
        return switch (direction) {
            case NORTH_WEST -> new Vector2i(uvStartX, uvStartY);
            case NORTH -> new Vector2i(uvStartX + size + 1, uvStartY);
            case NORTH_EAST -> new Vector2i(uvStartX + ((size + 2) * 2), uvStartY);
            case WEST -> new Vector2i(uvStartX , uvStartY + size + 2);
            case NONE -> new Vector2i(uvStartX + size + 2, uvStartY + size + 2);
            case EAST -> new Vector2i(uvStartX + ((size + 2) * 2), uvStartY + size + 2);
            case SOUTH_WEST -> new Vector2i(uvStartX, uvStartY + ((size + 2) * 2));
            case SOUTH -> new Vector2i(uvStartX + size + 2, uvStartY + ((size + 2) * 2));
            case SOUTH_EAST -> new Vector2i(uvStartX + ((size + 2) * 2), uvStartY + ((size + 2) * 2));
        };
    }
}
