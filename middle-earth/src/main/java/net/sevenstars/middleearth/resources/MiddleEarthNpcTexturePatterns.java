package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.npctextures.NpcTexturePattern;
import net.sevenstars.middleearth.resources.datas.npctextures.NpcTextureType;

import java.util.Optional;

public class MiddleEarthNpcTexturePatterns {
    public final static String SKIN_PATH = "npc_skin_pattern";
    public final static String EYE_PATH = "npc_eye_pattern";
    public static final RegistryKey<Registry<NpcTexturePattern>> SKIN_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, SKIN_PATH));
    public static final RegistryKey<Registry<NpcTexturePattern>> EYE_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, EYE_PATH));

    public final static RegistryKey<NpcTexturePattern> SKIN_COMMON_A = of("common_a", NpcTextureType.SKIN);
    public final static RegistryKey<NpcTexturePattern> SKIN_COMMON_B = of("common_b",  NpcTextureType.SKIN);


    public final static RegistryKey<NpcTexturePattern> EYE_COMMON = of("common", NpcTextureType.EYE);
    public final static RegistryKey<NpcTexturePattern> EYE_SMALL = of("small", NpcTextureType.EYE);


    public static void bootstrap(Registerable<NpcTexturePattern> registry) {
        RegistryEntryLookup<NpcTexturePattern> skinEntryLookup = registry.getRegistryLookup(SKIN_KEY);

        register(registry, skinEntryLookup, "common_a", NpcTextureType.SKIN);
        register(registry, skinEntryLookup, "common_b", NpcTextureType.SKIN);

        RegistryEntryLookup<NpcTexturePattern> eyeEntryLookup = registry.getRegistryLookup(EYE_KEY);

        register(registry, eyeEntryLookup, "common", NpcTextureType.EYE);
        register(registry, eyeEntryLookup, "small", NpcTextureType.EYE);
    }

    private static void register(Registerable<NpcTexturePattern> registry, RegistryEntryLookup<NpcTexturePattern> entryLookup, String id, NpcTextureType type) {
        var registryKey = switch (type) {
            case NpcTextureType.SKIN -> SKIN_KEY;
            case NpcTextureType.EYE -> EYE_KEY;
            default -> null;
        };

        register(registry, entryLookup, create(id, type), of(id, type), registryKey);
    }

    private static RegistryKey<NpcTexturePattern> of(String id, NpcTextureType type) {
        return switch (type) {
            case NpcTextureType.SKIN -> RegistryKey.of(SKIN_KEY, Identifier.of(MiddleEarth.MOD_ID, id));
            case NpcTextureType.EYE -> RegistryKey.of(EYE_KEY, Identifier.of(MiddleEarth.MOD_ID, id));
            default -> null;
        };
    }

    private static NpcTexturePattern create(String id, NpcTextureType type) {
        return new NpcTexturePattern(Identifier.of(MiddleEarth.MOD_ID, id), type);
    }

    public static void register() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Npc Texture Patterns for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(SKIN_KEY, NpcTexturePattern.CODEC);
        DynamicRegistries.registerSynced(EYE_KEY, NpcTexturePattern.CODEC);
    }

    private static void register(Registerable<NpcTexturePattern> registerable, RegistryEntryLookup<NpcTexturePattern> entryLookup, NpcTexturePattern content, RegistryKey<NpcTexturePattern> registryKey, RegistryKey<Registry<NpcTexturePattern>> registryRegistryKey) {
        String name = registryKey.getValue().getPath();
        RegistryKey<NpcTexturePattern> key = RegistryKey.of(registryRegistryKey,Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<NpcTexturePattern>> optionalFaction = entryLookup.getOptional(registryKey);
        optionalFaction.ifPresent(reference -> registerable.register(key, content));
    }
}
