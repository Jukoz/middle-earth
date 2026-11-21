package net.sevenstars.middleearth.gui.cavetroll;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;
import net.sevenstars.middleearth.gui.ScreenHandlerRegistryME;

/** The cave troll's saddle, club, banner and chest slots. */
public class CaveTrollScreenHandler extends ScreenHandler {
    private static final int EQUIPMENT_SLOTS = 3;

    private final CaveTrollEntity troll;
    private final int chestSlots;

    // Client side
    public CaveTrollScreenHandler(int syncId, PlayerInventory playerInventory, int entityId) {
        this(syncId, playerInventory, (CaveTrollEntity)playerInventory.player.getEntityWorld().getEntityById(entityId));
    }

    public CaveTrollScreenHandler(int syncId, PlayerInventory playerInventory, CaveTrollEntity troll) {
        super(ScreenHandlerRegistryME.CAVE_TROLL_SCREEN_HANDLER, syncId);
        this.troll = troll;
        Inventory equipment = new CaveTrollEquipmentInventory(troll);
        int columns = troll.getInventoryColumns();
        this.chestSlots = columns * 3;

        this.addSlot(new Slot(equipment, CaveTrollEquipmentInventory.SADDLE, 8, 18) {
            @Override
            public boolean canInsert(ItemStack stack) {
                EquippableComponent equippable = stack.get(DataComponentTypes.EQUIPPABLE);
                return equippable != null && equippable.slot() == EquipmentSlot.SADDLE && !this.hasStack() && troll.isTame();
            }

            @Override
            public boolean isEnabled() {
                return troll.isTame();
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }

            @Override
            public Identifier getBackgroundSprite() {
                return Identifier.ofVanilla("container/slot/saddle");
            }
        });
        this.addSlot(new Slot(equipment, CaveTrollEquipmentInventory.WEAPON, 8, 36) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return troll.isTrollWeapon(stack) && !this.hasStack();
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }

            @Override
            public Identifier getBackgroundSprite() {
                return Identifier.ofVanilla("container/slot/sword");
            }
        });

        this.addSlot(new Slot(equipment, CaveTrollEquipmentInventory.BANNER, 8, 54) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return CaveTrollEntity.isBanner(stack) && !this.hasStack();
            }

            @Override
            public int getMaxItemCount() {
                return 1;
            }
        });

        if(columns > 0) {
            SimpleInventory chest = troll.getChestInventory();
            for(int row = 0; row < 3; row++) {
                for(int column = 0; column < columns; column++) {
                    this.addSlot(new Slot(chest, column + row * columns, 80 + column * 18, 18 + row * 18));
                }
            }
        }

        for(int row = 0; row < 3; row++) {
            for(int column = 0; column < 9; column++) {
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }
        for(int column = 0; column < 9; column++) {
            this.addSlot(new Slot(playerInventory, column, 8 + column * 18, 142));
        }
    }

    public CaveTrollEntity getTroll() {
        return troll;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return troll.isAlive() && troll.distanceTo(player) < 8.0f;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        Slot slot = this.slots.get(slotIndex);
        if(!slot.hasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getStack();
        ItemStack original = stack.copy();
        int trollEnd = EQUIPMENT_SLOTS + chestSlots;

        if(slotIndex < trollEnd) { // Troll to player
            if(!this.insertItem(stack, trollEnd, this.slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        }
        else if(!(this.getSlot(0).canInsert(stack) && this.insertItem(stack, 0, 1, false))
                && !(this.getSlot(1).canInsert(stack) && this.insertItem(stack, 1, 2, false))
                && !(this.getSlot(2).canInsert(stack) && this.insertItem(stack, 2, 3, false))
                && !(chestSlots > 0 && this.insertItem(stack, EQUIPMENT_SLOTS, trollEnd, false))) {
            return ItemStack.EMPTY;
        }

        if(stack.isEmpty()) {
            slot.setStack(ItemStack.EMPTY);
        }
        else {
            slot.markDirty();
        }
        return original;
    }
}
