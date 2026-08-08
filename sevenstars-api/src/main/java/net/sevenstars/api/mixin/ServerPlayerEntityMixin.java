package net.sevenstars.api.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.packet.s2c.play.OpenHorseScreenS2CPacket;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.sevenstars.api.entity.AbstractMountEntity;
import net.sevenstars.api.gui.mount_inventory.MountScreenHandler;
import net.sevenstars.api.utils.IServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity implements IServerPlayerEntity {
    @Shadow
    public ServerPlayNetworkHandler networkHandler;
    @Shadow
    private int screenHandlerSyncId;
    @Shadow
    private void incrementScreenHandlerSyncId() { }
    @Shadow
    private void onScreenHandlerOpened(ScreenHandler screenHandler) { }

    public ServerPlayerEntityMixin(World world, GameProfile profile) {
        super(world, profile);
    }

    @Unique
    public void openMountInventory(AbstractMountEntity mount, Inventory inventory) {
        if (this.currentScreenHandler != this.playerScreenHandler) {
            this.closeHandledScreen();
        }

        this.incrementScreenHandlerSyncId();
        int columns = mount.getInventoryColumns();
        this.networkHandler.sendPacket(new OpenHorseScreenS2CPacket(this.screenHandlerSyncId, columns, mount.getId()));
        this.currentScreenHandler = new MountScreenHandler(this.screenHandlerSyncId, this.getInventory(), inventory, mount, columns);
        this.onScreenHandlerOpened(this.currentScreenHandler);
    }
}
