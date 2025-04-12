package net.sevenstars.middleearth.gui.structuremanager;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.gui.ModScreenHandlers;

public class StructureManagerScreenHandler extends ScreenHandler {
    protected BlockPos pos;
    protected final World world;

    public StructureManagerScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos blockPos) {
        this(syncId, playerInventory);
        this.pos = blockPos;
    }

    public StructureManagerScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHandlers.STRUCTURE_MANAGER_SCREEN_HANDLER, syncId);
        this.world = playerInventory.player.getWorld();
        this.pos = BlockPos.ORIGIN;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
