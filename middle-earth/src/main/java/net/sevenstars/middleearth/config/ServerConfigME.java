package net.sevenstars.middleearth.config;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.resource.language.I18n;
import net.sevenstars.api.config.ConfigProvider;
import net.sevenstars.api.config.SimpleConfig;
import net.sevenstars.api.enums.LangCategory;
import net.sevenstars.middleearth.MiddleEarth;

public class ServerConfigME {
    public static SimpleConfig CONFIG;
    private final static String PATH = MiddleEarth.getModId() + "/config-common";  // FIXME: might be a better way
    private static ConfigProvider configs;

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
    /** Allows to generation of procedural structures in Middle-earth**/
    public static boolean ENABLE_PROCEDURAL_STRUCTURES;
    /** Allows broadhoof goats to be mounted by players and npcs. **/
    public static boolean ENABLE_MOUNT_BROADHOOF_GOAT;
    /** Allows to craft golden carrots and golden apples in Middle-earth**/
    public static boolean ENABLE_GOLDEN_FOOD_RECIPES;
    /** Allows to place Golems with blocks patterns in Middle-earth**/
    public static boolean ENABLE_GOLEMS;
    /** Amount of levels that a sharpness enchant can have in Middle-earth**/
    public static int SHARPNESS_MAX_LEVEL;
    /** Amount of levels that a power enchant can have in Middle-earth**/
    public static int POWER_MAX_LEVEL;
    /** Global Mob Cap**/
    public static int GLOBAL_MOB_CAP;

    public static void registerConfigs() {
        configs = new ConfigProvider();
        createServerConfigs();
        CONFIG = SimpleConfig.of(PATH).provider(configs).request();
        assignServerConfigs();
    }

    private static void createServerConfigs() {
        configs.addSection(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.section.title")));
        configs.addComment(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.comment.title.line_1")));
        configs.addLineJump();

        // World configurations
        configs.addComment(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.comment.world")));
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.description.world.line_1")));
        configs.addKeyValuePair(new Pair<>("enableSpawnOverride", true), "boolean");
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.description.world.line_2")));
        configs.addKeyValuePair(new Pair<>("enableReturnToOverworld", true), "boolean");
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.description.world.line_3")));
        configs.addKeyValuePair(new Pair<>("enableProceduralStructures", true), "boolean");
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.description.world.line_4")));
        configs.addKeyValuePair(new Pair<>("globalMobCap", 50), "int");
        configs.addLineJump();

        // PlayerFactionPayload configurations
        configs.addComment(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.comment.player")));
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.description.player.line_1")));
        configs.addKeyValuePair(new Pair<>("enableFactionReset", true), "boolean");
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.description.player.line_2")));
        configs.addKeyValuePair(new Pair<>("enableKeepRaceOnDimensionSwap", true), "boolean");
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.description.player.line_3")));
        configs.addKeyValuePair(new Pair<>("delayOnTeleportConfirmation", 3), "int");
        configs.addLineJump();

        // Mount configurations
        configs.addComment(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.comment.mount")));
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.description.mount.line_1")));
        configs.addKeyValuePair(new Pair<>("enableMountBroadhoofGoat", true), "boolean");

        // Food configurations
        configs.addComment(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.comment.food")));
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.description.food.line_1")));
        configs.addKeyValuePair(new Pair<>("enableGoldenFoodRecipes", false), "boolean");

        // Golem configurations
        configs.addComment(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.comment.golem")));
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.description.golem.line_1")));
        configs.addKeyValuePair(new Pair<>("enableGolems", false), "boolean");

        // Enchants configurations
        configs.addComment(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.comment.enchant")));
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.description.enchant.line_1")));
        configs.addKeyValuePair(new Pair<>("sharpnessMaxLevel", 3), "int");
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "server.description.enchant.line_2")));
        configs.addKeyValuePair(new Pair<>("powerMaxLevel", 3), "int");
    }

    private static void assignServerConfigs() {
        // World configurations
        ENABLE_SPAWN_OVERRIDE = CONFIG.getOrDefault("enableSpawnOverride", true);
        ENABLE_RETURN_TO_OVERWORLD = CONFIG.getOrDefault("enableReturnToOverworld", true);
        ENABLE_PROCEDURAL_STRUCTURES = CONFIG.getOrDefault("enableProceduralStructures", true);
        GLOBAL_MOB_CAP = CONFIG.getOrDefault("globalMobCap", 50);

        // PlayerFactionPayload configurations
        ENABLE_FACTION_RESET = CONFIG.getOrDefault("enableFactionReset", true);
        ENABLE_KEEP_RACE_ON_DIMENSION_SWAP = CONFIG.getOrDefault("enableKeepRaceOnDimensionSwap", true);
        DELAY_ON_TELEPORT_CONFIRMATION = CONFIG.getOrDefault("delayOnTeleportConfirmation", 3);
        // Mount configurations
        ENABLE_MOUNT_BROADHOOF_GOAT = CONFIG.getOrDefault("enableMountBroadhoofGoat", true);

        ENABLE_GOLDEN_FOOD_RECIPES = CONFIG.getOrDefault("enableGoldenFoodRecipes", false);
        ENABLE_GOLEMS = CONFIG.getOrDefault("enableGolems", false);

        SHARPNESS_MAX_LEVEL = CONFIG.getOrDefault("sharpnessMaxLevel", 3);
        POWER_MAX_LEVEL = CONFIG.getOrDefault("powerMaxLevel", 3);

        MiddleEarth.LOGGER.logDebugMsg("All server configs (" + configs.getConfigsList().size() + ") have been set properly");
    }

    /**
     * To make the edit work without restart the game
     * by the command ingame
     * /middle_earth config reload
     */
    public static void reload() {
        if (CONFIG != null) {
            CONFIG.reload();
            assignServerConfigs();
        }
    }

    public static void reset() {
        if (CONFIG != null) {
            CONFIG.delete();
        }
        registerConfigs();
    }
}
