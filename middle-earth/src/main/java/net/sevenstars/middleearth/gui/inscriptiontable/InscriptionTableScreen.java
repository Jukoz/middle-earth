package net.sevenstars.middleearth.gui.inscriptiontable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOfferList;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.recipe.inscription.InscriptionWordBank;

@Environment(value= EnvType.CLIENT)
public class InscriptionTableScreen extends HandledScreen<InscriptionTableScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/inscription_table.png");

    private static final Identifier SCROLLER_TEXTURE = Identifier.ofVanilla("container/villager/scroller");
    private static final Identifier SCROLLER_DISABLED_TEXTURE = Identifier.ofVanilla("container/villager/scroller_disabled");

    private static final Identifier FONT_ID = Identifier.ofVanilla("alt");
    private static final Style STYLE = Style.EMPTY.withFont(FONT_ID);

    private final StringBuilder words;

    private int selectedIndex;
    int indexStartOffset;
    private boolean scrolling;

    public InscriptionTableScreen(InscriptionTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 275;
        this.backgroundHeight = 183;

        StringBuilder stringBuilder = new StringBuilder();

        Random random = Random.create();
        int k = random.nextInt(12);
        stringBuilder.append(InscriptionWordBank.wordBank.get(null).get(k));
        stringBuilder.append(" ");
        k = random.nextInt(12);
        stringBuilder.append(InscriptionWordBank.wordBank.get(null).get(k));

        System.out.println(stringBuilder);

        this.words = stringBuilder;
    }

    @Override
    protected void init() {
        super.init();
        titleX = 6;
        playerInventoryTitleX = 108;
        playerInventoryTitleY = 92;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight, 512, 256);

        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, i + 104, j + 7, 282, 134, 166, 16, 512, 256);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, i + 170, j + 26, 282, 174, 36, 16, 512, 256);

        StringVisitable stringVisitable = textRenderer.getTextHandler().trimToWidth(Text.literal(this.words.toString()).fillStyle(STYLE), 100, Style.EMPTY);
        context.drawWrappedText(this.textRenderer, stringVisitable, i + 107, j + 12, 100, ColorHelper.fullAlpha((-9937334 & 16711422) >> 1), false);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        int k = j + 16 + 1;
        int l = i + 5 + 5;
        this.renderScrollbar(context, i, j);

        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private boolean canScroll(int listSize) {
        return listSize > 11;
    }

    private void renderScrollbar(DrawContext context, int x, int y) {
        int i = 3;
        if (i > 1) {
            int j = 139 - (27 + (i - 1) * 139 / i);
            int k = 1 + j / i + 139 / i;
            int m = Math.min(113, this.indexStartOffset * k);
            if (this.indexStartOffset == i - 1) {
                m = 113;
            }

            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, SCROLLER_TEXTURE, x + 94, y + 22 + m, 6, 27);
        } else {
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, SCROLLER_DISABLED_TEXTURE, x + 94, y + 22, 6, 27);
        }
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)) {
            return true;
        } else {
            int i = 13; //((InscriptionTableScreenHandler)this.handler).getRecipes().size();
            if (this.canScroll(i)) {
                int j = i - 11;
                this.indexStartOffset = MathHelper.clamp((int)((double)this.indexStartOffset - verticalAmount), 0, j);
            }

            return true;
        }
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        int i = 13; //((MerchantScreenHandler)this.handler).getRecipes().size();
        if (this.scrolling) {
            int j = this.y + 18;
            int k = j + 139;
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
        if (this.canScroll(13) && mouseX > (double)(i + 94) && mouseX < (double)(i + 94 + 6) && mouseY > (double)(j + 18) && mouseY <= (double)(j + 18 + 139 + 1)) {
            this.scrolling = true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }
}
