package net.jesteur.me.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jesteur.me.block.special.alloy.AlloyScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class AlloyScreen<T extends AlloyScreenHandler> extends HandledScreen<T> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/furnace.png");
    public AlloyScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int k;
        int i = this.x;
        int j = this.y;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        if (((AlloyScreenHandler)this.handler).isBurning()) {
            k = ((AlloyScreenHandler)this.handler).getFuelProgress();
            context.drawTexture(TEXTURE, i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }
        k = ((AlloyScreenHandler)this.handler).getCookProgress();
        context.drawTexture(TEXTURE, i + 79, j + 34, 176, 14, k + 1, 16);
    }
}
