package net.sevenstars.middleearth.gui.structuremanager;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.network.packets.C2S.PacketStructureManagerUpdateBlockEntityRequest;

public class StructureManagerScreenHandler extends ScreenHandler {

    private final World world;
    private StructureManagerScreenData data;
    StructureManagerBlockEntity blockEntity;

    // Client side Constructor
    public StructureManagerScreenHandler(int syncId, PlayerInventory playerInventory, StructureManagerScreenData structureManagerScreenData) {
        super(ModScreenHandlers.STRUCTURE_MANAGER_SCREEN_HANDLER, syncId);
        this.world = playerInventory.player.getWorld();
        this.data = structureManagerScreenData;
        this.blockEntity = (StructureManagerBlockEntity) this.world.getBlockEntity(data.getPos());
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public void selectIdentifier(PlayerEntity player, Identifier identifier) {
        this.data.setCurrentId(identifier);
        ClientPlayNetworking.send(new PacketStructureManagerUpdateBlockEntityRequest(data.getPos(), data.getCurrentId(), data.getIsActive()));
    }

    public void updateClientData(Identifier structureManagerDataId, boolean isActive) {
        this.data.setCurrentId(structureManagerDataId);
        this.data.setActive(isActive);
    }

    public BlockPos getPos() {
        return this.data.getPos();
    }

    public Identifier getCurrentKey() {
        return this.data.getCurrentId();
    }

    public boolean getIsActive() {
        return this.data.getIsActive();
    }
}

