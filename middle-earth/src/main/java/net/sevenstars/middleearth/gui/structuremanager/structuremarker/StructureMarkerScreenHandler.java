package net.sevenstars.middleearth.gui.structuremanager.structuremarker;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.gui.ScreenHandlerRegistryME;
import net.sevenstars.middleearth.network.packets.client2server.PacketStructureMarkerUpdateBlockEntityRequest;

public class StructureMarkerScreenHandler extends ScreenHandler {
    private final StructureMarkerScreenData data;
    private MarkerKind kind;

    public StructureMarkerScreenHandler(int syncId, PlayerInventory playerInventory, StructureMarkerScreenData data) {
        super(ScreenHandlerRegistryME.STRUCTURE_MARKER_SCREEN_HANDLER, syncId);
        this.data = data;
        this.kind = data.kind();
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return player.getBlockPos().isWithinDistance(data.pos(), 8);
    }

    public MarkerKind getKind() {
        return kind;
    }

    public void selectNext() {
        kind = kind.next();
        ClientPlayNetworking.send(new PacketStructureMarkerUpdateBlockEntityRequest(data.pos(), kind));
    }
}
