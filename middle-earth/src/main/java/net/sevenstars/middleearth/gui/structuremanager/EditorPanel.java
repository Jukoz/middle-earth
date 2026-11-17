package net.sevenstars.middleearth.gui.structuremanager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

// The look shared by the builder's screens: a framed window with a title bar, drawn over the world (which stays
// visible behind it), and darker wells that hold lists and readouts
@Environment(value = EnvType.CLIENT)
public class EditorPanel {
    public static final int PADDING = 6;
    public static final int TITLE_HEIGHT = 16;
    public static final int FOOTER_HEIGHT = 28;
    public static final int TEXT = 0xFFFFFFFF;
    public static final int MUTED = 0xFF9AA3AD;
    public static final int ACCENT = 0xFFD4A94F;
    public static final int WARNING = 0xFFFF6B5E;
    private static final int SHADOW = 0x70000000;
    private static final int EDGE = 0xFF05070A;
    private static final int RIM = 0xFF4A525E;
    private static final int BODY = 0xF2181C22;
    private static final int TITLE_BAR = 0xFF262C35;
    private static final int WELL = 0xFF0D1014;
    private static final int WELL_RIM = 0xFF2C333D;
    private static final int LINE = 0xFF333A45;
    private static final int SHADOW_OFFSET = 3;

    // The window, with its title on the left of the title bar and a subject on the right
    public static void window(DrawContext context, TextRenderer textRenderer, int x, int y, int width, int height, Text title, @Nullable Text about) {
        context.fill(x + SHADOW_OFFSET, y + SHADOW_OFFSET, x + width + SHADOW_OFFSET, y + height + SHADOW_OFFSET, SHADOW);
        context.fill(x - 1, y - 1, x + width + 1, y + height + 1, EDGE);
        context.fill(x, y, x + width, y + height, RIM);
        context.fill(x + 1, y + 1, x + width - 1, y + height - 1, BODY);
        context.fill(x + 1, y + 1, x + width - 1, y + TITLE_HEIGHT, TITLE_BAR);
        context.fill(x + 1, y + TITLE_HEIGHT, x + width - 1, y + TITLE_HEIGHT + 1, ACCENT);
        int textY = y + 1 + (TITLE_HEIGHT - textRenderer.fontHeight) / 2;
        context.drawTextWithShadow(textRenderer, title, x + PADDING, textY, ACCENT);
        if(about != null)
            context.drawTextWithShadow(textRenderer, about, x + width - PADDING - textRenderer.getWidth(about), textY, MUTED);
    }

    // The divider line above the buttons at the bottom of a window
    public static void footer(DrawContext context, int x, int y, int width, int height) {
        divider(context, x + 1, y + height - FOOTER_HEIGHT, width - 2);
    }

    public static void well(DrawContext context, int x, int y, int width, int height) {
        context.fill(x - 1, y - 1, x + width + 1, y + height + 1, WELL_RIM);
        context.fill(x, y, x + width, y + height, WELL);
    }

    public static void divider(DrawContext context, int x, int y, int width) {
        context.fill(x, y, x + width, y + 1, LINE);
    }

    public static void label(DrawContext context, TextRenderer textRenderer, Text text, int x, int y) {
        context.drawTextWithShadow(textRenderer, text, x, y, MUTED);
    }
}
