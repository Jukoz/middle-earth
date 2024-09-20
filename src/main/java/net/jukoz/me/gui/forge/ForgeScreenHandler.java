package net.jukoz.me.gui.forge;

import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.jukoz.me.gui.ModScreenHandlers;
import net.jukoz.me.network.packets.C2S.ForgeOutputPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ForgeScreenHandler extends ScreenHandler{
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    protected final World world;
    private BlockPos pos;


    public ForgeScreenHandler(int syncId, PlayerInventory playerInventory, Object o) {
        this(syncId, playerInventory, new SimpleInventory(6), new ArrayPropertyDelegate(6));
        this.pos = ((BlockPos) o);
    }

    public ForgeScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandlers.FORGE_SCREEN_HANDLER, syncId);
        checkSize(inventory, 6);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = delegate;
        this.world = playerInventory.player.getWorld();
        pos = BlockPos.ORIGIN;
        int maxItemStack = 64;

        this.addSlot(new ForgeFuelSlot(inventory, this, 0, 39, 53));
        this.addSlot(new ForgeSlot(inventory, 1, 12, 17, maxItemStack));
        this.addSlot(new ForgeSlot(inventory, 2, 30, 17, maxItemStack));
        this.addSlot(new ForgeSlot(inventory, 3, 48, 17, maxItemStack));
        this.addSlot(new ForgeSlot(inventory, 4, 66, 17, maxItemStack));

        if(this.propertyDelegate.get(3) == 0){
            maxItemStack = 1;
            this.addSlot( new ForgeOutputSlot(playerInventory.player, inventory, 5, 144, 17, false));
            // Heating mode
        } else {
            this.addSlot( new ForgeOutputSlot(playerInventory.player, inventory, 5, 144, 17, true));
        }

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(delegate);
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack stack = ItemStack.EMPTY;
        Slot invSlot = this.slots.get(slot);

        if(invSlot.hasStack()) {
            ItemStack originalStack = invSlot.getStack();
            if(slot < this.inventory.size()) {
                if(!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size() - 1, false)) {
                return ItemStack.EMPTY;
            }
            if (originalStack.isEmpty()) {
                invSlot.setStack(ItemStack.EMPTY);
            } else {
                invSlot.markDirty();
            }
        }
        return stack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public boolean isCooking() {
        return propertyDelegate.get(1) > 0;
    }

    public boolean hasBellows() {
        return propertyDelegate.get(3) > 0;
    }

    public boolean heatingMode() {
        return propertyDelegate.get(3) == 0;
    }

    public float getScaledCooking() {
        int cooking = this.propertyDelegate.get(1);
        int maxCooking = this.propertyDelegate.get(2);
        if(maxCooking == 0) maxCooking = 200;

        return (float) cooking / maxCooking;
    }

    public float getScaledStoredLiquid() {
        int storedLiquid = this.propertyDelegate.get(4);
        return (float) storedLiquid / ForgeBlockEntity.MAX_STORAGE;
    }

    public int getStoredLiquid() {
        return this.propertyDelegate.get(4);
    }

    public int getCurrentMetal() {
        return this.propertyDelegate.get(5);
    }

    public int checkMaxOutput(){
        int liquid = propertyDelegate.get(4);
        if(liquid >= 432){
            return 4;
        }
        if(liquid >= 288){
            return 3;
        }
        if(liquid >= 144){
            return 2;
        }
        if(liquid >= 16){
            return 1;
        }
        return 0;
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public float getScaledProgress() {
        int progress = this.propertyDelegate.get(0);

        return (float) progress / ForgeBlockEntity.MAX_PROGRESS;
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
