package net.sevenstars.middleearth.gui.inscriptiontable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.recipe.inscription.InscriptionWordBank;

@Environment(value= EnvType.CLIENT)
public class InscriptionTableScreen extends HandledScreen<InscriptionTableScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/inscription_table.png");

    private static final Identifier FONT_ID = Identifier.ofVanilla("alt");
    private static final Style STYLE = Style.EMPTY.withFont(FONT_ID);

    private final StringBuilder words;

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
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
