package net.sevenstars.middleearth.gui.playerbook;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

@Environment(EnvType.CLIENT)
public class PlayerBookScreen extends Screen {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/player_book.png");
    private static final int WIDTH = 320;
    private static final int HEIGHT = 220;

    public PlayerBookScreen(Text title) {
        super(title);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        int centerX = context.getScaledWindowWidth() / 2;
        int startX = centerX - (WIDTH / 2);
        int startY = (context.getScaledWindowHeight() / 2) - (HEIGHT / 2);

        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE,
                startX, startY, 0, 0,
                WIDTH, HEIGHT, 384, 384);
    }
}
