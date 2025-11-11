package net.sevenstars.middleearth.gui.inscriptiontable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.CyclingSlotIcon;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.Iterator;
import java.util.List;

@Environment(value= EnvType.CLIENT)
public class InscriptionTableScreen extends HandledScreen<InscriptionTableScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/inscription_table.png");

    private static final Identifier SCROLLER_TEXTURE = Identifier.ofVanilla("container/villager/scroller");
    private static final Identifier SCROLLER_DISABLED_TEXTURE = Identifier.ofVanilla("container/villager/scroller_disabled");

    private static final Identifier EMPTY_SLOT_EMERALD_TEXTURE = Identifier.ofVanilla("container/slot/emerald");
    private static final Identifier EMPTY_SLOT_DIAMOND_TEXTURE = Identifier.ofVanilla("container/slot/diamond");
    private static final Identifier EMPTY_SLOT_LAPIS_LAZULI_TEXTURE = Identifier.ofVanilla("container/slot/lapis_lazuli");
    private static final Identifier EMPTY_SLOT_ADAMANT_TEXTURE = IdentifierUtil.create("container/slot/adamant");
    private static final Identifier EMPTY_SLOT_RUBY_TEXTURE = IdentifierUtil.create("container/slot/ruby");
    private static final Identifier EMPTY_SLOT_SAPPHIRE_TEXTURE = IdentifierUtil.create("container/slot/sapphire");

    private static final Identifier FONT_ID = Identifier.ofVanilla("alt");
    private static final Style STYLE = Style.EMPTY.withFont(FONT_ID);

    private int selectedIndex;
    int indexStartOffset;
    private boolean scrolling;

    private final CyclingSlotIcon catalystSlotIcon = new CyclingSlotIcon(0);

    private final WidgetInscriptionButtonPage[] words = new WidgetInscriptionButtonPage[11];

    public InscriptionTableScreen(InscriptionTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 275;
        this.backgroundHeight = 183;
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        this.catalystSlotIcon.updateTexture(List.of(
                EMPTY_SLOT_LAPIS_LAZULI_TEXTURE,
                EMPTY_SLOT_EMERALD_TEXTURE,
                EMPTY_SLOT_DIAMOND_TEXTURE,
                EMPTY_SLOT_ADAMANT_TEXTURE,
                EMPTY_SLOT_RUBY_TEXTURE,
                EMPTY_SLOT_SAPPHIRE_TEXTURE));
    }

    @Override
    protected void init() {
        super.init();
        titleX = 6;
        playerInventoryTitleX = 108;
        playerInventoryTitleY = 92;

        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        int k = j + 22;

        for(int l = 0; l < 11; ++l) {
            this.words[l] = this.addDrawableChild(new WidgetInscriptionButtonPage(i + 5, k, l, (button) -> {
                if (button instanceof WidgetInscriptionButtonPage) {
                    this.selectedIndex = ((WidgetInscriptionButtonPage) button).getIndex() + this.indexStartOffset;
                }
            }));
            k += 14;
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;

        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight, 512, 256);

        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, i + 104, j + 7, 282, 134, 166, 16, 512, 256);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, i + 170, j + 26, 282, 174, 36, 16, 512, 256);

        if (this.handler.hasGem()){
            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, i + 130, j + 48, 310, 88, 26, 26, 512, 256);
        }

        this.catalystSlotIcon.render(this.handler, context, delta, this.x, this.y);

        StringBuilder stringBuilder = new StringBuilder();

        handler.getWords().forEach(word -> {
            stringBuilder.append(word);
            stringBuilder.append(" ");
        });

        StringVisitable stringVisitable = textRenderer.getTextHandler().trimToWidth(Text.literal(stringBuilder.toString()), 159, Style.EMPTY);
        context.drawWrappedText(this.textRenderer, stringVisitable, i + 107, j + 12, 159, ColorHelper.fullAlpha((-9937334 & 16711422) >> 1), false);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.renderScrollbar(context, i, j);

        Iterator words = this.handler.getWords().iterator();

        String word;
        int m = 0;
        int n = j + 24;
        while(words.hasNext()) {
            word = (String) words.next();
            if (this.canScroll(this.handler.getWords().size()) && (m < this.indexStartOffset || m >= 11 + this.indexStartOffset)) {
                ++m;
            } else {
                context.drawText(this.textRenderer, word, i + 11, n, Colors.WHITE, false);
                n += 14;
                ++m;
            }
        }

        for (WidgetInscriptionButtonPage widgetButtonPage : this.words) {
            widgetButtonPage.visible = widgetButtonPage.index < this.handler.getWords().size();
        }

        drawMouseoverTooltip(context, mouseX, mouseY);

        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private boolean canScroll(int listSize) {
        return listSize > 11;
    }

    private void renderScrollbar(DrawContext context, int x, int y) {
        int i = this.handler.getWords().size() + 1 - 11;
        if (i > 1) {
            int j = 153 - (27 + (i - 1) * 153 / i);
            int k = 1 + j / i + 153 / i;
            int m = Math.min(127, this.indexStartOffset * k);
            if (this.indexStartOffset == i - 1) {
                m = 127;
            }

            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, SCROLLER_TEXTURE, x + 94, y + 22 + m, 6, 27);
        } else {
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, SCROLLER_DISABLED_TEXTURE, x + 94, y + 22, 6, 27);
        }
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (!super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)) {
            int i = this.handler.getWords().size();
            if (this.canScroll(i)) {
                int j = i - 11;
                this.indexStartOffset = MathHelper.clamp((int) ((double) this.indexStartOffset - verticalAmount), 0, j);
            }

        }
        return true;
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        int i = this.handler.getWords().size();
        if (this.scrolling) {
            int j = this.y + 18;
            int k = j + 153;
            int l = i - 11;
            float f = ((float)mouseY - (float)j - 13.5F) / ((float)(k - j) - 27.0F);
            f = f * (float)l + 0.5F;
            this.indexStartOffset = MathHelper.clamp((int)f, 0, l);
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        if (this.canScroll((this.handler).getWords().size()) && mouseX > (double)(i + 94) && mouseX < (double)(i + 94 + 6) && mouseY > (double)(j + 22) && mouseY <= (double)(j + 22 + 153 + 1)) {
            this.scrolling = true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    static class WidgetInscriptionButtonPage extends ButtonWidget {
        final int index;

        private static final ButtonTextures TEXTURES = new ButtonTextures(IdentifierUtil.create("word_button"), IdentifierUtil.create("word_button_selected"), IdentifierUtil.create("word_button_highlighted"));

        public WidgetInscriptionButtonPage(final int x, final int y, final int index, final ButtonWidget.PressAction onPress) {
            super(x, y, 86, 14, ScreenTexts.EMPTY, onPress, DEFAULT_NARRATION_SUPPLIER);
            this.index = index;
            this.visible = false;
        }

        public int getIndex() {
            return this.index;
        }

        @Override
        protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, TEXTURES.get(this.active, this.isSelected()), this.getX(), this.getY(), this.getWidth(), this.getHeight(), ColorHelper.getWhite(this.alpha));
            int i = ColorHelper.withAlpha(this.alpha, this.active ? -1 : -6250336);
            this.drawMessage(context, minecraftClient.textRenderer, i);
        }
    }
}
