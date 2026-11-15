package net.sevenstars.middleearth.gui.structuremanager.structuremarker;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.gui.ScreenHandlerRegistryME;
import net.sevenstars.middleearth.network.packets.client2server.PacketStructureMarkerEditRequest;

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

    public void save(MarkerKind kind, String value, int count, boolean respawns, Direction facing) {
        ClientPlayNetworking.send(new PacketStructureMarkerEditRequest(data.pos(), data.isNew(), false, kind, value, count, respawns, facing));
    }

    public void remove() {
        ClientPlayNetworking.send(new PacketStructureMarkerEditRequest(data.pos(), false, true, data.kind(), "", 1, false, data.facing()));
    }
}
