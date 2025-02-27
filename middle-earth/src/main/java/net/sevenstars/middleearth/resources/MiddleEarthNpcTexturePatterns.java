package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTexturePattern;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.Optional;

public class MiddleEarthNpcTexturePatterns {
    public final static String SKIN_PATH = "npc_skin_pattern";
    public final static String EYE_PATH = "npc_eye_pattern";
    public final static String HAIR_PATH = "npc_hair_pattern";
    public final static String CLOTHING_PATH = "npc_clothing_pattern";

    public static final RegistryKey<Registry<NpcTexturePattern>> SKIN_KEY = ofRegistry(SKIN_PATH);
    public static final RegistryKey<Registry<NpcTexturePattern>> EYE_KEY = ofRegistry(EYE_PATH);
    public static final RegistryKey<Registry<NpcTexturePattern>> HAIR_KEY = ofRegistry(HAIR_PATH);
    public static final RegistryKey<Registry<NpcTexturePattern>> CLOTHING_KEY = ofRegistry(CLOTHING_PATH);


    public final static RegistryKey<NpcTexturePattern> SKIN_COMMON_A = of("common_a", NpcTextureType.SKIN);
    public final static RegistryKey<NpcTexturePattern> SKIN_COMMON_B = of("common_b",  NpcTextureType.SKIN);

    public final static RegistryKey<NpcTexturePattern> EYE_COMMON = of("common", NpcTextureType.EYE);
    public final static RegistryKey<NpcTexturePattern> EYE_SMALL = of("small", NpcTextureType.EYE);

    public final static RegistryKey<NpcTexturePattern> HAIR_SHORT = of("hair_short", NpcTextureType.HAIR);
    public final static RegistryKey<NpcTexturePattern> HAIR_LONG = of("hair_long", NpcTextureType.HAIR);

    public final static RegistryKey<NpcTexturePattern> BEARD_SHORT = of("beard_short", NpcTextureType.BEARD);
    public final static RegistryKey<NpcTexturePattern> BEARD_LARGE = of("beard_large", NpcTextureType.BEARD);

    public final static RegistryKey<NpcTexturePattern> EYEBROW_BASIC = of("eyebrow_basic", NpcTextureType.HAIR);
    public final static RegistryKey<NpcTexturePattern> EYEBROW_SHORT = of("eyebrow_short", NpcTextureType.HAIR);
    public final static RegistryKey<NpcTexturePattern> EYEBROW_LONG = of("eyebrow_long", NpcTextureType.HAIR);


    public final static RegistryKey<NpcTexturePattern> CLOTHING_FABRIC_SKIRT = of("fabric_skirt", NpcTextureType.CLOTHING);
    public final static RegistryKey<NpcTexturePattern> CLOTHING_FABRIC_SKIRT_WITH_STROPHIUM = of("fabric_skirt_with_strophium", NpcTextureType.CLOTHING);

    public MiddleEarthNpcTexturePatterns(){

    }
    public static void bootstrapSkins(Registerable<NpcTexturePattern> registry) {
        register(registry, SKIN_COMMON_A, NpcTextureType.SKIN);
        register(registry, SKIN_COMMON_B, NpcTextureType.SKIN);
    }
    public static void bootstrapEyes(Registerable<NpcTexturePattern> registry) {
        register(registry, EYE_SMALL, NpcTextureType.EYE);
        register(registry, EYE_COMMON, NpcTextureType.EYE);
    }
    public static void bootstrapHairs(Registerable<NpcTexturePattern> registry) {
        register(registry, HAIR_SHORT, NpcTextureType.HAIR);
        register(registry, HAIR_LONG, NpcTextureType.HAIR, true);
        register(registry, EYEBROW_BASIC, NpcTextureType.EYEBROW);
        register(registry, EYEBROW_SHORT, NpcTextureType.EYEBROW);
        register(registry, EYEBROW_LONG, NpcTextureType.EYEBROW);
        register(registry, BEARD_SHORT, NpcTextureType.BEARD);
        register(registry, BEARD_LARGE, NpcTextureType.BEARD, true);
    }
    public static void bootstrapClothings(Registerable<NpcTexturePattern> registry) {
        register(registry, CLOTHING_FABRIC_SKIRT, NpcTextureType.CLOTHING);
        register(registry, CLOTHING_FABRIC_SKIRT_WITH_STROPHIUM, NpcTextureType.CLOTHING);
    }

    private static void register(Registerable<NpcTexturePattern> registry, RegistryKey<NpcTexturePattern> key, NpcTextureType type) {
        NpcTexturePattern pattern = new NpcTexturePattern(key.getValue(), type, false);
        register(registry, key, pattern,  getKey(type));
    }
    private static void register(Registerable<NpcTexturePattern> registry, RegistryKey<NpcTexturePattern> key, NpcTextureType type, boolean hasAddon) {
        NpcTexturePattern pattern = new NpcTexturePattern(key.getValue(), type, hasAddon);
        register(registry, key, pattern,  getKey(type));
    }

    private static RegistryKey<NpcTexturePattern> of(String id, NpcTextureType type) {
        return RegistryKey.of(getKey(type), IdentifierUtil.create(id));
    }

    private static <T> RegistryKey<Registry<T>> ofRegistry(String id) {
        return RegistryKey.ofRegistry(IdentifierUtil.create(id));
    }

    public static void register() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Npc Texture Patterns for " + MiddleEarth.MOD_ID);


        DynamicRegistries.registerSynced(SKIN_KEY, NpcTexturePattern.CODEC);
        DynamicRegistries.registerSynced(EYE_KEY, NpcTexturePattern.CODEC);
        DynamicRegistries.registerSynced(HAIR_KEY, NpcTexturePattern.CODEC);
        DynamicRegistries.registerSynced(CLOTHING_KEY, NpcTexturePattern.CODEC);
    }

    private static void register(Registerable<NpcTexturePattern> registerable, RegistryKey<NpcTexturePattern> registryKey, NpcTexturePattern content, RegistryKey<Registry<NpcTexturePattern>> registryRegistryKey) {
        String name = registryKey.getValue().getPath();
        RegistryKey<NpcTexturePattern> key = RegistryKey.of(registryRegistryKey,Identifier.of(MiddleEarth.MOD_ID,name));
        registerable.register(key, content);
    }

    public static RegistryKey<Registry<NpcTexturePattern>> getKey(NpcTextureType category){
        return switch (category) {
            case NpcTextureType.SKIN -> SKIN_KEY;
            case NpcTextureType.EYE -> EYE_KEY;
            case NpcTextureType.HAIR, NpcTextureType.EYEBROW, NpcTextureType.BEARD -> HAIR_KEY;
            case NpcTextureType.CLOTHING -> CLOTHING_KEY;
        };
    }

    public static Optional<RegistryEntry.Reference<NpcTexturePattern>> get(RegistryWrapper.WrapperLookup registries, NpcTextureType type, Identifier id) {
        return registries.getOrThrow(getKey(type)).streamEntries().filter((pattern) ->
                pattern.value().getCategory() == type && pattern.matchesId(id)).findFirst();
    }

}
