package net.sevenstars.middleearth.gui.structuremanager.structurenest;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.gui.ModScreenHandlers;

public class StructureNestScreenHandler  extends ScreenHandler {

    protected Identifier identifier;

    public StructureNestScreenHandler(int syncId, PlayerInventory playerInventory, Identifier identifier) {
        super(ModScreenHandlers.STRUCTURE_NEST_SCREEN_HANDLER, syncId);

        this.identifier = identifier;
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
