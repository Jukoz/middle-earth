package net.jukoz.me.client.screens.utils.widgets.backgrounds;

import net.jukoz.me.client.screens.utils.widgets.ModWidget;
import net.jukoz.me.client.screens.utils.widgets.UiDirections;
import net.jukoz.me.client.screens.utils.widgets.backgrounds.types.BackgroundContainerTypes;
import net.minecraft.client.gui.DrawContext;
import org.joml.Vector2i;

public class BackgroundContainerWidget extends ModWidget {
    BackgroundContainerTypes type;
    public BackgroundContainerWidget(BackgroundContainerTypes type){
        this.type = type;
    }

    public void draw(DrawContext context, int startX, int startY, int sizeX, int sizeY){
        int size = type.size;
        Vector2i uv = type.getUvForDirection(UiDirections.NORTH_WEST);
        context.drawTexture(type.textureId, startX, startY, uv.x, uv.y, size, size);
        uv = type.getUvForDirection(UiDirections.NORTH);
        context.drawTexture(type.textureId, );
        context.drawTexture(type.textureId, startX + size, startY, sizeX - 10, 5, 7, 208, 5, size, 5, size);
        uv = type.getUvForDirection(UiDirections.NORTH_EAST);
        context.drawTexture(type.textureId, startX + sizeX - size, startY, uv.x, uv.y, size, size);
    }
}
