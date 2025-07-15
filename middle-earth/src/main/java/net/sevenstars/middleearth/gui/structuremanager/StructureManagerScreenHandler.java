package net.sevenstars.middleearth.gui.structuremanager;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.gui.ModScreenHandlers;

public class StructureManagerScreenHandler extends ScreenHandler {

    private final World world;
    private StructureManagerScreenData data;
    StructureManagerBlockEntity blockEntity;

    // Client side Constructor
    // Server side Constructor
    public StructureManagerScreenHandler(int syncId, PlayerInventory playerInventory, StructureManagerScreenData data) {
        super(ModScreenHandlers.STRUCTURE_MANAGER_SCREEN_HANDLER, syncId);
        this.data = data;
        this.world = playerInventory.player.getWorld();
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
        //updateServerData(player);
    }

    public void updateClientData(Identifier structureManagerDataId, boolean isActive) {
        this.data.setCurrentId(structureManagerDataId);
        this.data.setActive(isActive);
    }

    public void requestClientDataRefresh(){
        //if(world.isClient)
            //ClientPlayNetworking.send(PacketStructureManagerRefreshRequest.INSTANCE);
    }

    public void updateServerData(PlayerEntity player){
        //if(!world.isClient)
            //ClientPlayNetworking.send(new PacketStructureManagerUpdateBlockEntityRequest(currentId, isActive));
    }

    public BlockPos getPos() {
        return data.getPos();
    }
}

