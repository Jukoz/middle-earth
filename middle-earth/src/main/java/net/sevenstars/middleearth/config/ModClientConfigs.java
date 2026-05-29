package net.sevenstars.middleearth.config;

import com.mojang.datafixers.util.Pair;
import net.sevenstars.middleearth.MiddleEarth;

public class ModClientConfigs {
    public static SimpleConfig CONFIG;
    private final static String PATH = MiddleEarth.MOD_ID + "/config-client";

    /**Should you see the map overlay button in the middle-earth map gui**/
    public static boolean ENABLE_MAP_OVERLAY;
    /**Should you see the experimental npc visuals, or use the simplified version**/
    public static boolean ENABLE_SIMPLIFIED_CHARACTER_RENDERING;

    private static ModConfigProvider configs;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createClientConfigs();
        CONFIG = SimpleConfig.of(PATH).provider(configs).request();
        assignClientConfigs();
    }

    private static void createClientConfigs() {
        configs.addSection("Client Configs");
        configs.addComment("This file stores client configuration options for the Middle-earth mod.");
        configs.addLineJump();
        configs.addComment("GUI configurations");
        configs.addDescription("Should you see the map overlay button in the middle-earth map gui?");
        configs.addKeyValuePair(new Pair<>("enableMapOverlay", false), "boolean");
        configs.addLineJump();
        configs.addComment("NPC simplified model");
        configs.addDescription("Should you see the experimental npc visuals, or use the simplified version.");
        configs.addKeyValuePair(new Pair<>("enableSimplifiedCharacterRendering", false), "boolean");
    }

    private static void assignClientConfigs() {
        ENABLE_MAP_OVERLAY = CONFIG.getOrDefault("enableMapOverlay", false);
        ENABLE_SIMPLIFIED_CHARACTER_RENDERING = CONFIG.getOrDefault("enableSimplifiedCharacterRendering", false);
        MiddleEarth.LOGGER.logDebugMsg("All client configs (" + configs.getConfigsList().size() + ") have been set properly");
    }
}