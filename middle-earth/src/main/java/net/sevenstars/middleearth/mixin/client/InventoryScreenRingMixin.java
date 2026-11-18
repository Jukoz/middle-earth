package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.sevenstars.middleearth.item.items.rings.RingSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Frames the worn ring slot above the offhand in the survival inventory. */
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenRingMixin {
    @Inject(method = "drawBackground", at = @At("TAIL"))
    private void middleearth$drawRingFrame(DrawContext context, float deltaTicks, int mouseX, int mouseY, CallbackInfo ci) {
        HandledScreenAccessor screen = (HandledScreenAccessor) this;
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, RingSlot.FRAME_SPRITE,
                screen.middleearth$getX() + 76, screen.middleearth$getY() + 43, 18, 18);
    }
}
