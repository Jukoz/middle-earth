package net.sevenstars.middleearth.mixin.client;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.slot.Slot;
import net.sevenstars.middleearth.gui.rings.RingSlotInventory;
import net.sevenstars.middleearth.item.items.rings.RingSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Moves the worn ring slot right of the armour (mirroring the offhand) on the creative inventory tab and frames it. */
@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenRingMixin {
    @Unique
    private static final int RING_X = 127;
    @Unique
    private static final int RING_Y = 20;

    @Shadow
    private static ItemGroup selectedTab;

    @Inject(method = "setSelectedTab", at = @At("TAIL"))
    private void middleearth$placeRingSlot(ItemGroup group, CallbackInfo ci) {
        if (group.getType() != ItemGroup.Type.INVENTORY) {
            return;
        }
        for (Slot slot : ((HandledScreen<?>) (Object) this).getScreenHandler().slots) {
            if (slot.inventory instanceof RingSlotInventory) {
                ((SlotAccessor) slot).middleearth$setX(RING_X);
                ((SlotAccessor) slot).middleearth$setY(RING_Y);
            }
        }
    }

    @Inject(method = "drawBackground", at = @At("TAIL"))
    private void middleearth$drawRingFrame(DrawContext context, float deltaTicks, int mouseX, int mouseY, CallbackInfo ci) {
        if (selectedTab.getType() == ItemGroup.Type.INVENTORY) {
            HandledScreenAccessor screen = (HandledScreenAccessor) this;
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, RingSlot.FRAME_SPRITE,
                    screen.middleearth$getX() + RING_X - 1, screen.middleearth$getY() + RING_Y - 1, 18, 18);
        }
    }
}
