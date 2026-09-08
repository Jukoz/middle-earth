package net.sevenstars.api;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.sevenstars.api.utils.IdentifierUtil;
import net.sevenstars.api.utils.LoggerUtil;

public abstract class AbstractModInitializer implements ModInitializer {
    private static AbstractModInitializer INSTANCE;

    protected String modId;
    protected boolean isDebug;
    protected LoggerUtil logger;
    protected String modVersion = "1.0.2-1.21.8-beta";

    protected AbstractModInitializer(String id, boolean shouldBeDebug) {
        modId = id;
        isDebug = shouldBeDebug;
        logger = new LoggerUtil(modId, isDebug);
        INSTANCE = this;
    }

    public static LoggerUtil logger() {
        return INSTANCE.logger;
    }
    public static String namespace() {
        return INSTANCE.modId;
    }
    public static Identifier id(String path) {
        return IdentifierUtil.build(INSTANCE.modId, path);
    }

    public static Identifier idAggregate(String... names) {
        return IdentifierUtil.buildAggregate(INSTANCE.modId, names);
    }

    public Identifier idAggregate(char delimiter, String... names) {
        return id(IdentifierUtil.createAggregateValue(delimiter, names));
    }

    public Identifier ofId(String stringId) {
        return IdentifierUtil.getIdentifierFromString(stringId);
    }

    public static void logRegistryMessage(String registry) {
        INSTANCE.logger.logDebugMsg("Registering Mod " +  registry + " for " + id());
    }
}
