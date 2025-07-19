package net.sevenstars.middleearth.gui.structuremanager.structurenest;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.structureManager.nest.StructureNestBlockEntity;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.network.packets.C2S.PacketStructureNestUpdateBlockEntityRequest;

import java.util.Optional;

public class StructureNestScreenHandler  extends ScreenHandler {

    private final World world;
    private StructureNestScreenData data;
    StructureNestBlockEntity blockEntity;

    // Client side Constructor
    public StructureNestScreenHandler(int syncId, PlayerInventory playerInventory, StructureNestScreenData structureNestScreenData) {
        super(ModScreenHandlers.STRUCTURE_NEST_SCREEN_HANDLER, syncId);
        this.world = playerInventory.player.getWorld();
        this.data = structureNestScreenData;
        this.blockEntity = (StructureNestBlockEntity) this.world.getBlockEntity(data.getPos());
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public Identifier getManagerKey() {
        return data.getStructureManagerId();
    }

    public Identifier getNestKey() {
        return data.getStructureNestId();
    }

    public void selectManagerId(ClientPlayerEntity player, Identifier identifier) {
        this.data.setStructureManagerId(identifier);
        this.data.setStructureNestId(null);
        updateBlockEntity();
    }

    public void selectNestId(ClientPlayerEntity player, Identifier identifier) {
        this.data.setStructureNestId(identifier);
        updateBlockEntity();
    }

    private void updateBlockEntity() {
        ClientPlayNetworking.send(new PacketStructureNestUpdateBlockEntityRequest(data.getPos(), Optional.ofNullable(getManagerKey()), Optional.ofNullable(getNestKey())));
    }
}
