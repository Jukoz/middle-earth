package net.sevenstars.middleearth.gui.structuremanager.structuremarker;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.sevenstars.middleearth.gui.ScreenHandlerRegistryME;

public class StructureMarkerScreenHandler extends ScreenHandler {
    private final StructureMarkerScreenData data;

    public StructureMarkerScreenHandler(int syncId, PlayerInventory playerInventory, StructureMarkerScreenData data) {
        super(ScreenHandlerRegistryME.STRUCTURE_MARKER_SCREEN_HANDLER, syncId);
        this.data = data;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return player.getBlockPos().isWithinDistance(data.pos(), StructureEditing.REACH);
    }

    public StructureMarkerScreenData getData() {
        return data;
    }
}
