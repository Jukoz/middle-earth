package net.sevenstars.middleearth.item.items;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.sevenstars.middleearth.network.packets.S2C.PacketOpenMapScreen;
import net.sevenstars.middleearth.permissions.PermissionsME;

public class MiddleEarthMapItem extends Item {
    public MiddleEarthMapItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        if(user instanceof ServerPlayerEntity serverPlayerEntity) {
            boolean canTeleport = PermissionsME.checkMapTeleport(serverPlayerEntity);
            PacketOpenMapScreen packet = new PacketOpenMapScreen(canTeleport);
            ServerPlayNetworking.send(serverPlayerEntity, packet);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
