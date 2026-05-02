package net.sevenstars.middleearth.gui.shapinganvil;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreenHandler;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.network.packets.C2S.AnvilIndexPacket;
import net.sevenstars.middleearth.recipe.AnvilShapingRecipe;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;

import java.util.List;

public class ShapingAnvilScreen extends HandledScreen<ShapingAnvilScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/shaping_anvil.png");

    private float scrollAmount;
    private boolean mouseClicked;
    private int scrollOffset;

    public ShapingAnvilScreen(ShapingAnvilScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        //if (this.leftOutputCycleButton.mouseClicked(mouseX, mouseY, button)) {
            ClientPlayNetworking.send(new AnvilIndexPacket(true, handler.getPos().getX(),handler.getPos().getY(),handler.getPos().getZ()));
        //}

        // (this.rightOutputCycleButton.mouseClicked(mouseX, mouseY, button)) {
            ClientPlayNetworking.send(new AnvilIndexPacket(false, handler.getPos().getX(),handler.getPos().getY(),handler.getPos().getZ()));
        //}

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight, 256, 256);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        /*context.drawItem(ToolItemsME.SMITHING_HAMMER.getDefaultStack(), x + 81, y + 34);

        renderHammerTooltip(context, mouseX, mouseY);

        if (this.handler.getOutputStack().isEmpty()){
            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 79, y + 15, 177, 115,18, 18, 256, 256);
        } else {
            context.drawItem(this.handler.getOutputStack(), x + 80, y + 16);
            renderOutputTooltip(context, mouseX, mouseY);
        }*/

        int l = this.x + 76;
        int m = this.y + 14;
        int n = this.scrollOffset + 12;
        this.renderRecipeBackground(context, mouseX, mouseY, l, m, n);
        this.renderRecipeIcons(context, l, m, n);
    }

    private void renderOutputTooltip(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        if (mouseX >= x + 79 && mouseX <= x + 96 && mouseY >= y + 16 && mouseY <= y + 33){
            context.drawTooltip(this.client.textRenderer, handler.getOutputStack().getItem().getName(), mouseX, mouseY);
        }
    }

    private void renderHammerTooltip(DrawContext context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        if (mouseX >= x + 79 && mouseX <= x + 96 && mouseY >= y + 34 && mouseY <= y + 51){
            context.drawOrderedTooltip(this.client.textRenderer,
                    Lists.transform(List.of(Text.translatable("tooltip." + MiddleEarth.MOD_ID +".anvil_hammer"),
                            Text.translatable("tooltip." + MiddleEarth.MOD_ID +".anvil_hammer_2")),
                            Text::asOrderedText), mouseX, mouseY);
        }
    }

    private void renderRecipeBackground(DrawContext context, int mouseX, int mouseY, int x, int y, int scrollOffset) {
        for(int i = this.scrollOffset; i < scrollOffset && i < this.handler.getAvailableRecipeCount(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            int n = this.backgroundHeight;
            if (i == (this.handler).getSelectedRecipe()) {
                n += 18;
            } else if (mouseX >= k && mouseY >= m && mouseX < k + 16 && mouseY < m + 18) {
                n += 36;
            }

            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, k, m - 1, 0, n, 16, 18, 256,256);
        }

    }

    private void renderRecipeIcons(DrawContext context, int x, int y, int scrollOffset) {
        List<RecipeEntry<AnvilShapingRecipe>> list = this.handler.getAvailableRecipes();
        for (int i = this.scrollOffset; i < scrollOffset && i < this.handler.getAvailableRecipeCount(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            context.drawItem(list.get(i).value().getOutput(), k, m);
        }

    }
}
