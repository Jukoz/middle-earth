package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTexturePattern;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;

import java.util.Optional;

public class MiddleEarthNpcTexturePatterns {
    public final static String SKIN_PATH = "npc_skin_pattern";
    public final static String EYE_PATH = "npc_eye_pattern";
    public final static String HAIR_PATH = "npc_hair_pattern";
    public final static String CLOTHING_PATH = "npc_clothing_pattern";

    public static final RegistryKey<Registry<NpcTexturePattern>> SKIN_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, SKIN_PATH));
    public static final RegistryKey<Registry<NpcTexturePattern>> EYE_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, EYE_PATH));
    public static final RegistryKey<Registry<NpcTexturePattern>> HAIR_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, HAIR_PATH));
    public static final RegistryKey<Registry<NpcTexturePattern>> CLOTHING_KEY = RegistryKey.ofRegistry(Identifier.of(MiddleEarth.MOD_ID, CLOTHING_PATH));



    public final static RegistryKey<NpcTexturePattern> SKIN_COMMON_A = of("common_a", NpcTextureType.SKIN);
    public final static RegistryKey<NpcTexturePattern> SKIN_COMMON_B = of("common_b",  NpcTextureType.SKIN);

    public final static RegistryKey<NpcTexturePattern> EYE_COMMON = of("common", NpcTextureType.EYE);
    public final static RegistryKey<NpcTexturePattern> EYE_SMALL = of("small", NpcTextureType.EYE);

    public final static RegistryKey<NpcTexturePattern> HAIR_SHORT = of("short", NpcTextureType.HAIR);


    public final static RegistryKey<NpcTexturePattern> CLOTHING_FABRIC_SKIRT = of("fabric_skirt", NpcTextureType.CLOTHING);
    public final static RegistryKey<NpcTexturePattern> CLOTHING_FABRIC_SKIRT_WITH_STROPHIUM = of("fabric_skirt_with_strophium", NpcTextureType.CLOTHING);


    public static void bootstrap(Registerable<NpcTexturePattern> registry) {
        RegistryEntryLookup<NpcTexturePattern> skinEntryLookup = registry.getRegistryLookup(SKIN_KEY);
        register(registry, skinEntryLookup, "common_a", NpcTextureType.SKIN);
        register(registry, skinEntryLookup, "common_b", NpcTextureType.SKIN);

        RegistryEntryLookup<NpcTexturePattern> eyeEntryLookup = registry.getRegistryLookup(EYE_KEY);
        register(registry, eyeEntryLookup, "common", NpcTextureType.EYE);
        register(registry, eyeEntryLookup, "small", NpcTextureType.EYE);

        RegistryEntryLookup<NpcTexturePattern> hairEntryLookup = registry.getRegistryLookup(HAIR_KEY);
        register(registry, hairEntryLookup, "short", NpcTextureType.HAIR);

        RegistryEntryLookup<NpcTexturePattern> clothingEntryLookup = registry.getRegistryLookup(CLOTHING_KEY);
        register(registry, clothingEntryLookup, "fabric_skirt", NpcTextureType.CLOTHING);
        register(registry, clothingEntryLookup, "fabric_skirt_with_strophium", NpcTextureType.CLOTHING);

    }

    private static void register(Registerable<NpcTexturePattern> registry, RegistryEntryLookup<NpcTexturePattern> entryLookup, String id, NpcTextureType type) {
        var registryKey = switch (type) {
            case NpcTextureType.SKIN -> SKIN_KEY;
            case NpcTextureType.EYE -> EYE_KEY;
            case NpcTextureType.HAIR -> HAIR_KEY;
            case NpcTextureType.CLOTHING -> CLOTHING_KEY;
            default -> null;
        };

        register(registry, entryLookup, create(id, type), of(id, type), registryKey);
    }

    private static RegistryKey<NpcTexturePattern> of(String id, NpcTextureType type) {
        return switch (type) {
            case NpcTextureType.SKIN -> RegistryKey.of(SKIN_KEY, Identifier.of(MiddleEarth.MOD_ID, id));
            case NpcTextureType.EYE -> RegistryKey.of(EYE_KEY, Identifier.of(MiddleEarth.MOD_ID, id));
            case NpcTextureType.HAIR -> RegistryKey.of(HAIR_KEY, Identifier.of(MiddleEarth.MOD_ID, id));
            case NpcTextureType.CLOTHING -> RegistryKey.of(CLOTHING_KEY, Identifier.of(MiddleEarth.MOD_ID, id));
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
        DynamicRegistries.registerSynced(HAIR_KEY, NpcTexturePattern.CODEC);
        DynamicRegistries.registerSynced(CLOTHING_KEY, NpcTexturePattern.CODEC);
    }

    private static void register(Registerable<NpcTexturePattern> registerable, RegistryEntryLookup<NpcTexturePattern> entryLookup, NpcTexturePattern content, RegistryKey<NpcTexturePattern> registryKey, RegistryKey<Registry<NpcTexturePattern>> registryRegistryKey) {
        String name = registryKey.getValue().getPath();
        RegistryKey<NpcTexturePattern> key = RegistryKey.of(registryRegistryKey,Identifier.of(MiddleEarth.MOD_ID,name));

        Optional<RegistryEntry.Reference<NpcTexturePattern>> optionalFaction = entryLookup.getOptional(registryKey);
        optionalFaction.ifPresent(reference -> registerable.register(key, content));
    }
}
