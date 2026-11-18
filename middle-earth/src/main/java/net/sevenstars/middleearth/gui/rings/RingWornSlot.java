package net.sevenstars.middleearth.gui.rings;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.item.items.rings.RingSlot;

/** The worn ring slot added to the player's inventory screen. */
public class RingWornSlot extends Slot {
    private final PlayerEntity owner;

    public RingWornSlot(PlayerEntity owner, int x, int y) {
        super(new RingSlotInventory(owner), 0, x, y);
        this.owner = owner;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return RingSlot.canWear(stack, owner.getRegistryManager());
    }

    @Override
    public boolean canTakeItems(PlayerEntity player) {
        if (!RingSlot.canRemove(player, getStack())) {
            if (!player.getWorld().isClient) {
                RingSlot.bound(player);
            }
            return false;
        }
        return super.canTakeItems(player);
    }

    @Override
    public int getMaxItemCount() {
        return 1;
    }

    @Override
    public Identifier getBackgroundSprite() {
        return RingSlot.EMPTY_SPRITE;
    }
}
