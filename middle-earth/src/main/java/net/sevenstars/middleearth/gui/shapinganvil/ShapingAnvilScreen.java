package net.sevenstars.middleearth.gui.shapinganvil;

import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.ArrayList;
import java.util.List;

public class ShapingAnvilScreen extends HandledScreen<ShapingAnvilScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/shaping_anvil.png");

    private float scrollAmount;
    private boolean mouseClicked;
    private int scrollOffset;

    private List<ItemStack> outputs;

    public ShapingAnvilScreen(ShapingAnvilScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        outputs = new ArrayList<>();
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

    }

    public void addRecipe(int index, ItemStack output) {
        boolean exists = outputs.stream().anyMatch(item -> output.getItem().equals(item.getItem()));
        if(exists) return;

        while (outputs.size() < index) {
            outputs.add(ItemStack.EMPTY);
        }
        outputs.add(output);
    }

    private boolean shouldScroll() {
        return this.outputs.size() > 12;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        int i = this.x + 20;
        int j = this.y + 14;
        int k = this.scrollOffset + 12;

        for(int l = this.scrollOffset; l < k; ++l) {
            int m = l - this.scrollOffset;
            double d = mouseX - (double)(i + m % 4 * 16);
            double e = mouseY - (double)(j + m / 4 * 18);
            if (d >= 0.0 && e >= 0.0 && d < 16.0 && e < 18.0 && (this.handler).onButtonClick(this.client.player, l)) {
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                this.handler.setSelectedRecipe(l);
                return true;
            }
        }

        i = this.x + 119;
        j = this.y + 9;
        if (mouseX >= (double)i && mouseX < (double)(i + 12) && mouseY >= (double)j && mouseY < (double)(j + 54)) {
            this.mouseClicked = true;
        }


        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight, 256, 256);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY - 1, -12566464, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, -12566464, false);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        int k = (int)(41.0F * this.scrollAmount);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x + 87, y + 15 + k, 176 + (this.shouldScroll() ? 0 : 12), 20, 12, 15, 256, 256);

        int l = this.x + 19;
        int m = this.y + 14;
        int n = this.scrollOffset + 12;
        this.renderRecipeBackground(context, mouseX, mouseY, l + 1, m, n);
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
        for(int i = this.scrollOffset; i < scrollOffset && i < this.outputs.size(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            int n = 0;
            if (i == (this.handler).getSelectedRecipe()) {
                n += 16;
            } else if (mouseX >= k && mouseY >= m && mouseX < k + 16 && mouseY < m + 18) {
                n += 32;
            }

            context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, k, m - 1, 176 + n, 1, 16, 18, 256,256);
        }
    }

    private void renderRecipeIcons(DrawContext context, int x, int y, int scrollOffset) {
        for (int i = this.scrollOffset; i < scrollOffset && i < this.outputs.size(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + 1 + j % 4 * 16;
            int l = (j + 1) / 4;
            int m = y + l * 18 + 2;
            context.drawItem(outputs.get(i), k, m);
        }

    }
}
