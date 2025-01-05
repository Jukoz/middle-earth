package net.jukoz.me.config;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;

public class ModClientConfigs {
    public static SimpleConfig CONFIG;
    private final static String PATH = MiddleEarth.MOD_ID + "/config-client";
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
        configs.addComment("We do not have any client side configuration available.");
    }

    private static void assignClientConfigs() {
        LoggerUtil.logDebugMsg("All client configs (" + configs.getConfigsList().size() + ") have been set properly");
    }
}