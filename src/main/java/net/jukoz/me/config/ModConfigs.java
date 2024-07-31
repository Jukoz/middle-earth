package net.jukoz.me.config;

import com.mojang.datafixers.util.Pair;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;
    private final static String PATH = MiddleEarth.MOD_ID + "/config";

    /** Allows rams to be mounted by players or npcs **/
    public static boolean SERVER_MOUNT_RAM_RIDEABLE; // Allows rams to be mounted by players/npcs
    /** Allows players to change faction when going in the ME dimension. **/
    public static boolean SERVER_FACTION_CAN_BE_CHANGED;
    /** Override the player's respawn coordinate when they die without bed when they are in the ME dimension. **/
    public static boolean SERVER_WORLD_SPAWN_OVERRIDE;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(PATH).provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addSection("Server Configs");
        configs.addKeyValuePair(new Pair<>("server.faction.change.allowed", true), "boolean");
        configs.addKeyValuePair(new Pair<>("server.mount.ram.rideable", true), "boolean");
        configs.addKeyValuePair(new Pair<>("server.world.spawn.override", true), "boolean");

        configs.addSpace();
        configs.addSection("Client Configs");
        configs.addSection("none");
    }

    private static void assignConfigs() {
        SERVER_FACTION_CAN_BE_CHANGED = CONFIG.getOrDefault("server.faction.change.allowed", true);
        SERVER_MOUNT_RAM_RIDEABLE = CONFIG.getOrDefault("server.mount.ram.rideable", true);
        SERVER_WORLD_SPAWN_OVERRIDE = CONFIG.getOrDefault("server.world.spawn.override", true);

        LoggerUtil.getInstance().logDebugMsg("All " + configs.getConfigsList().size() + " have been set properly");
    }
}