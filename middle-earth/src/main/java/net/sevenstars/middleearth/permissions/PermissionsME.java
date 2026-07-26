package net.sevenstars.middleearth.permissions;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.network.ServerPlayerEntity;

public class PermissionsME {
    public static boolean checkMapTeleport(ServerPlayerEntity player) {
        return Permissions.check(player, "middle-earth.map-teleport", 2);
    }
}
