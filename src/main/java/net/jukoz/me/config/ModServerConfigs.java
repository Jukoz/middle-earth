package net.jukoz.me.config;

import com.mojang.datafixers.util.Pair;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;

public class ModServerConfigs {
    public static SimpleConfig CONFIG;
    private final static String PATH = MiddleEarth.MOD_ID + "/config-common";
    private static ModConfigProvider configs;
    /**Should players be allowed to change factions when they use the starlight phial?**/
    public static boolean ENABLE_FACTION_RESET;
    /**Should players keep their race when returning to the Overworld**/
    public static boolean ENABLE_KEEP_RACE_ON_DIMENSION_SWAP;
    /**Amount of time before teleporting**/
    public static float DELAY_ON_TELEPORT_CONFIRMATION;
    /**Should players respawn at their selected spawn location in Middle-earth if they die without having a bed assigned?**/
    public static boolean ENABLE_SPAWN_OVERRIDE;
    /**Should players be able to return to the overworld by reusing the starlight phial?**/
    public static boolean ENABLE_RETURN_TO_OVERWORLD;
    /** Allows broadhoof goats to be mounted by players and npcs. **/
    public static boolean ENABLE_MOUNT_BROADHOOF_GOAT;
    /** Allows to craft golden carrots and golden apples in Middle-earth**/
    public static boolean ENABLE_GOLDEN_FOOD_RECIPES;

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
        configs.addDescription("Should players be able to return to the overworld by reusing the starlight phial?");
        configs.addKeyValuePair(new Pair<>("enableReturnToOverworld", true), "boolean");
        configs.addLineJump();

        // Faction configurations
        configs.addComment("Faction configurations");
        configs.addDescription("Should players be allowed to change factions when they use the starlight phial?");
        configs.addKeyValuePair(new Pair<>("enableFactionReset", true), "boolean");
        configs.addDescription("Should players keep their race when returning to the Overworld");
        configs.addKeyValuePair(new Pair<>("enableKeepRaceOnDimensionSwap", true), "boolean");
        configs.addDescription("Amount of time before teleporting");
        configs.addKeyValuePair(new Pair<>("delayOnTeleportConfirmation", 3), "int");
        configs.addLineJump();

        // Mount configurations
        configs.addComment("Mount configurations");
        configs.addDescription("Should players and NPCs be allowed to ride broadhoof goats?");
        configs.addKeyValuePair(new Pair<>("enableMountBroadhoofGoat", true), "boolean");

        // Food configurations
        configs.addComment("Food configurations");
        configs.addDescription("Should players be allowed to craft golden apples and golden carrots in Middle-earth?");
        configs.addKeyValuePair(new Pair<>("enableGoldenFoodRecipes", false), "boolean");
    }

    private static void assignServerConfigs() {
        // World configurations
        ENABLE_SPAWN_OVERRIDE = CONFIG.getOrDefault("enableSpawnOverride", true);
        ENABLE_RETURN_TO_OVERWORLD = CONFIG.getOrDefault("enableReturnToOverworld", true);
        // Faction configurations
        ENABLE_FACTION_RESET = CONFIG.getOrDefault("enableFactionReset", true);
        ENABLE_KEEP_RACE_ON_DIMENSION_SWAP = CONFIG.getOrDefault("enableKeepRaceOnDimensionSwap", true);
        DELAY_ON_TELEPORT_CONFIRMATION = CONFIG.getOrDefault("delayOnTeleportConfirmation", 3);
        // Mount configurations
        ENABLE_MOUNT_BROADHOOF_GOAT = CONFIG.getOrDefault("enableMountBroadhoofGoat", true);
        ENABLE_GOLDEN_FOOD_RECIPES = CONFIG.getOrDefault("enableGoldenFoodRecipes", false);

        LoggerUtil.logDebugMsg("All server configs (" + configs.getConfigsList().size() + ") have been set properly");
    }
}
