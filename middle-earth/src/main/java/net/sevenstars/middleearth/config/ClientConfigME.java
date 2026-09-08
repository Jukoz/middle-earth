package net.sevenstars.middleearth.config;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.resource.language.I18n;
import net.sevenstars.api.config.ConfigProvider;
import net.sevenstars.api.config.SimpleConfig;
import net.sevenstars.api.enums.LangCategory;
import net.sevenstars.middleearth.MiddleEarth;

public class ClientConfigME {
    public static SimpleConfig CONFIG;
    private final static String PATH = MiddleEarth.getModId() + "/config-client";  // FIXME: might be a better way

    /**Should you see the map overlay button in the middle-earth map gui**/
    public static boolean ENABLE_MAP_OVERLAY;
    /**Should you see the experimental npc visuals, or use the simplified version**/
    public static boolean ENABLE_SIMPLIFIED_CHARACTER_RENDERING;
    /**Distance of rendering armor on NPCs**/
    public static int LOD_NPC_ARMOR_DISTANCE;
    /**Distance of rendering NPCs features (long hair, ears, etc.)**/
    public static int LOD_NPC_FEATURES_DISTANCE;
    /**Should glint be disabled in Middle-earth**/
    public static boolean DISABLE_GLINT;

    private static ConfigProvider configs;

    public static void registerConfigs() {
        configs = new ConfigProvider();
        createClientConfigs();
        CONFIG = SimpleConfig.of(PATH).provider(configs).request();
        assignClientConfigs();
    }

    private static void createClientConfigs() {
        configs.addSection(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "client.section.title")));
        configs.addComment(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "client.comment.title.line_1")));
        configs.addLineJump();
        configs.addComment(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "client.comment.gui")));
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "client.description.gui.line_1")));
        configs.addKeyValuePair(new Pair<>("enableMapOverlay", false), "boolean");
        configs.addLineJump();
        configs.addComment(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "client.comment.npc")));
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "client.description.npc.line_1")));
        configs.addKeyValuePair(new Pair<>("enableSimplifiedCharacterRendering", false), "boolean");
        configs.addLineJump();
        configs.addComment(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "client.comment.lod")));
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "client.description.lod.line_1")));
        configs.addKeyValuePair(new Pair<>("npcsArmorDistanceLOD", 48), "int");
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "client.description.lod.line_2")));
        configs.addKeyValuePair(new Pair<>("npcsFeaturesDistanceLOD", 24), "int");
        configs.addLineJump();
        configs.addComment(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "client.comment.glint")));
        configs.addDescription(I18n.translate(MiddleEarth.rawTranslationKeyWithModId(LangCategory.CONFIG, "client.description.glint.line_1")));
        configs.addKeyValuePair(new Pair<>("disableGlint", true), "boolean");
    }

    private static void assignClientConfigs() {
        ENABLE_MAP_OVERLAY = CONFIG.getOrDefault("enableMapOverlay", false);
        ENABLE_SIMPLIFIED_CHARACTER_RENDERING = CONFIG.getOrDefault("enableSimplifiedCharacterRendering", false);
        LOD_NPC_ARMOR_DISTANCE = CONFIG.getOrDefault("npcsArmorDistanceLOD", 48);
        LOD_NPC_FEATURES_DISTANCE = CONFIG.getOrDefault("npcsFeaturesDistanceLOD", 24);
        DISABLE_GLINT = CONFIG.getOrDefault("disableGlint", true);
        MiddleEarth.LOGGER.logDebugMsg("All client configs (" + configs.getConfigsList().size() + ") have been set properly");
    }

    /**
     * To make the edit work without restart the game
     * by the command ingame
     * /middle_earth config reload
     */
    public static void reload() {
        if (CONFIG != null) {
            CONFIG.reload();
            assignClientConfigs();
        }
    }

    public static void reset() {
        if (CONFIG != null) {
            CONFIG.delete();
        }
        registerConfigs();
    }
}
