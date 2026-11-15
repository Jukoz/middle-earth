package net.sevenstars.middleearth.gui.structuremanager.bedtag;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.sevenstars.middleearth.gui.ScreenHandlerRegistryME;
import net.sevenstars.middleearth.network.packets.client2server.PacketBedTagUpdateRequest;

public class BedTagScreenHandler extends ScreenHandler {
    private final BedTagScreenData data;

    public BedTagScreenHandler(int syncId, PlayerInventory playerInventory, BedTagScreenData data) {
        super(ScreenHandlerRegistryME.BED_TAG_SCREEN_HANDLER, syncId);
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

    public BedTagScreenData getData() {
        return data;
    }

    public void save(String nestKind, String job, String npcClass, boolean resetLinks) {
        ClientPlayNetworking.send(new PacketBedTagUpdateRequest(data.pos(), nestKind, job, npcClass, resetLinks));
    }
}
