package net.jukoz.me.config;

import com.mojang.datafixers.util.Pair;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;

public class ModServerConfigs {
    public static SimpleConfig CONFIG;
    private final static String PATH = MiddleEarth.MOD_ID + "/config-common";
    private static ModConfigProvider configs;

    /** Allows broadhoof goats to be mounted by players and npcs. **/
    public static boolean ENABLE_MOUNT_BROADHOOF_GOAT;
    /** Allows players to change faction everytime they go in the ME dimension. **/
    public static boolean ENABLE_FACTION_CHANGE;
    /** Override the player's respawn coordinate when they die without their bed when they are in the ME dimension. **/
    public static boolean ENABLE_SPAWN_OVERRIDE;
    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createServerConfigs();
        CONFIG = SimpleConfig.of(PATH).provider(configs).request();
        assignServerConfigs();
    }

    private static void createServerConfigs() {
        configs.addSection("Server Configs");
        configs.addComment("This file stores server/host configuration options for the Middle-earth mod.");
        configs.addLineJump();

        // World configurations
        configs.addComment("World configurations");
        configs.addDescription("Should players respawn at their selected spawn location in Middle-earth if they die without having a bed assigned?");
        configs.addKeyValuePair(new Pair<>("enableSpawnOverride", true), "boolean");
        configs.addLineJump();

        // Faction configurations
        configs.addComment("Faction configurations");
        configs.addDescription("Should players be allowed to change factions every time they use the starlight phial?");
        configs.addKeyValuePair(new Pair<>("enableFactionUpdate", true), "boolean");
        configs.addLineJump();

        // Mount configurations
        configs.addComment("Mount configurations");
        configs.addDescription("Should players and NPCs be allowed to ride broadhoof goats?");
        configs.addKeyValuePair(new Pair<>("enableMountBroadhoofGoat", true), "boolean");
    }

    private static void assignServerConfigs() {
        ENABLE_FACTION_CHANGE = CONFIG.getOrDefault("enableFactionUpdate", true);
        ENABLE_MOUNT_BROADHOOF_GOAT = CONFIG.getOrDefault("enableMountBroadhoofGoat", true);
        ENABLE_SPAWN_OVERRIDE = CONFIG.getOrDefault("enableSpawnOverride", true);

        LoggerUtil.getInstance().logDebugMsg("All server configs (" + configs.getConfigsList().size() + ") have been set properly");
    }
}
