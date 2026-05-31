package net.sevenstars.middleearth.gui.playerbook;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import net.sevenstars.middleearth.item.EggItemsME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import org.joml.Matrix3x2fStack;

import java.util.List;

@Environment(EnvType.CLIENT)
public class PlayerBookScreen extends Screen {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/player_book.png");
    private static final int WIDTH = 320;
    private static final int HEIGHT = 220;
    private List<Chapter> chapters;

    public PlayerBookScreen(Text title) {
        super(title);
        chapters = List.of(new Chapter("Getting Started", ResourceItemsME.STARLIGHT_PHIAL),
                new Chapter("Mining", ToolItemsME.STEEL_PICKAXE),
                new Chapter("Smithing", ToolItemsME.SMITHING_HAMMER),
                new Chapter("Enchanting", DecorativeItemsME.INSCRIPTION_TABLE),
                new Chapter("Mounts", EggItemsME.WARG_SPAWN_EGG),
                new Chapter("Dungeons", Blocks.TRIAL_SPAWNER.asItem()));
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

        drawScaledText(textRenderer, context, Text.literal("Middle-earth").formatted(Formatting.UNDERLINE).formatted(Formatting.BOLD),
                startX + (int)(WIDTH * 0.375), startY + (int)(HEIGHT * 0.11f), 1.5f, Colors.BLACK, true);

        String paragraph = "This mod is about the famous universe of Middle-earth.\n " +
                "You will find a brand new dimension with custom blocks, items, entity, structures and more!";
        context.drawWrappedText(textRenderer, Text.literal(paragraph), startX + 36, startY + (int)(HEIGHT * 0.22f), 120, Colors.BLACK, false);

        drawScaledText(textRenderer, context, Text.literal("Chapters").formatted(Formatting.UNDERLINE).formatted(Formatting.BOLD),
                startX + (int)(WIDTH * 0.75), startY + (int)(HEIGHT * 0.11f), 1.5f, Colors.BLACK, true);
        int i = 0;
        for(Chapter chapter : chapters) {
            MutableText text = Text.literal(chapter.name);
            context.drawItem(chapter.icon.getDefaultStack(), startX + (int)(WIDTH * 0.5f) + 12, startY + (int)(HEIGHT * 0.22f) - 4 + (i * 18));
            context.drawText(textRenderer, text, startX + (int)(WIDTH * 0.5f) + 32, startY + (int)(HEIGHT * 0.22f) + (i * 18), Colors.BLACK, false);
            i++;
        }
    }

    public static void drawScaledText(TextRenderer textRenderer, DrawContext context, Text text, int x, int y, float scale, int color, boolean centered) {
        Matrix3x2fStack matrices = context.getMatrices();
        matrices.pushMatrix();
        matrices.scale(scale);

        if (centered) {
            context.drawText(textRenderer, text, (int)(x / scale) - (int)((textRenderer.getWidth(text) / 2) * scale),
                    (int)(y / scale), color, false);
        } else {
            context.drawText(textRenderer, text, x, y, color, false);
        }

        matrices.popMatrix();
    }

    private class Chapter {
        public String name;
        public Item icon;

        public Chapter(String name, Item icon) {
            this.name = name;
            this.icon = icon;
        }
    }
}
