package net.sevenstars.middleearth.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AbstractCraftingScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.sevenstars.middleearth.gui.rings.RingWornSlot;
import net.sevenstars.middleearth.item.items.rings.RingSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Adds the worn ring slot (above the offhand) and sends shift-clicked wearable rings into it. */
@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerRingMixin extends AbstractCraftingScreenHandler {
    protected PlayerScreenHandlerRingMixin(ScreenHandlerType<?> type, int syncId, int width, int height) {
        super(type, syncId, width, height);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void middleearth$addRingSlot(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {
        this.addSlot(new RingWornSlot(owner, 77, 44));
    }

    @Inject(method = "quickMove", at = @At("HEAD"), cancellable = true)
    private void middleearth$quickMoveRing(PlayerEntity player, int index, CallbackInfoReturnable<ItemStack> cir) {
        if (index < PlayerScreenHandler.INVENTORY_START || index >= RingSlot.SCREEN_SLOT) {
            return;
        }
        Slot from = this.slots.get(index);
        Slot ring = this.slots.get(RingSlot.SCREEN_SLOT);
        if (!from.hasStack() || ring.hasStack() || !ring.canInsert(from.getStack())) {
            return;
        }
        ring.setStack(from.getStack().copy());
        from.setStack(ItemStack.EMPTY);
        cir.setReturnValue(ItemStack.EMPTY);
    }
}
