package net.sevenstars.middleearth.permissions;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.server.permission.PermissionAPI;
import net.neoforged.neoforge.server.permission.events.PermissionGatherEvent;
import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import net.neoforged.neoforge.server.permission.nodes.PermissionTypes;
import net.sevenstars.middleearth.MiddleEarth;

public class PermissionsME {
    public static final PermissionNode<Boolean> MAP_TELEPORT = new PermissionNode<>(
            MiddleEarth.MOD_ID,
            "map-teleport",
            PermissionTypes.BOOLEAN,
            (player, playerId, context) -> player != null && player.createCommandSourceStack().hasPermission(2)
    );

    public static void register() {
        NeoForge.EVENT_BUS.addListener(PermissionsME::registerNodes);
    }

    private static void registerNodes(PermissionGatherEvent.Nodes event) {
        event.addNodes(MAP_TELEPORT);
    }

    public static boolean checkMapTeleport(ServerPlayer player) {
        return PermissionAPI.getPermission(player, MAP_TELEPORT);
    }
}
