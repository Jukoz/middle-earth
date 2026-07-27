package net.sevenstars.middleearth.block.special.crockpot;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.sevenstars.middleearth.MiddleEarth;

public class CrockpotScreen extends AbstractContainerScreen<CrockpotScreenHandler> {
    private static final ResourceLocation WATER_TEXTURE = ResourceLocation.withDefaultNamespace("textures/block/water_still.png");
    private static final ResourceLocation TEXTURE = MiddleEarth.ofPath( "textures", "gui", "crockpot.png");
    private static final ResourceLocation BUBBLES_TEXTURE = ResourceLocation.withDefaultNamespace("container/brewing_stand/bubbles");
    private static final int[] BUBBLE_PROGRESS = new int[]{29, 24, 20, 16, 11, 6, 0};
    private static final int LIQUID_SIZE = 24;

    private static final int PROGRESS_ARROW_SIZE = 24;

    public CrockpotScreen(CrockpotScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        titleLabelX = (imageWidth - font.width(title)) / 2;
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics context, float deltaTicks, int mouseX, int mouseY) {
        context.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
        context.setColor(0.4F, 2.0F / 3.0F, 14.0F / 15.0F, 1.0F);
        context.blit(WATER_TEXTURE, this.leftPos + 120, this.topPos + 31, 0, 0, 24, 24, 16, 16);
        context.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
