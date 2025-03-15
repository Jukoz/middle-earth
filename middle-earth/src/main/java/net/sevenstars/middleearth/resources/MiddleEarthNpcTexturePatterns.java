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
    public record Keys(){
        public static final RegistryKey<Registry<NpcTexturePattern>> SKIN_KEY = ofRegistry("npc_skin_pattern");
        public static final RegistryKey<Registry<NpcTexturePattern>> EYE_KEY = ofRegistry("npc_eye_pattern");
        public static final RegistryKey<Registry<NpcTexturePattern>> HAIR_KEY = ofRegistry("npc_hair_pattern");
        public static final RegistryKey<Registry<NpcTexturePattern>> CLOTHING_KEY = ofRegistry("npc_clothing_pattern");
    }

    public record Skin(){
        public final static RegistryKey<NpcTexturePattern> MUSCULAR = of("skin_muscular", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTexturePattern> FEMALE = of("skin_female",  NpcTextureType.SKIN);
        public final static RegistryKey<NpcTexturePattern> SLIM = of("skin_slim",  NpcTextureType.SKIN);
    }
    public record Ear(){
        public final static RegistryKey<NpcTexturePattern> CUBE = of("ear_cube", NpcTextureType.EAR);
        public final static RegistryKey<NpcTexturePattern> POINTY = of("ear_pointy",  NpcTextureType.EAR);
    }
    public record Nose(){
        public final static RegistryKey<NpcTexturePattern> CUBE = of("nose_cube", NpcTextureType.NOSE);
        public final static RegistryKey<NpcTexturePattern> LARGE_CUBE = of("nose_large_cube", NpcTextureType.NOSE);
    }
    public record Eye() {
        public final static RegistryKey<NpcTexturePattern> COMMON = of("common", NpcTextureType.EYE);
        public final static RegistryKey<NpcTexturePattern> SMALL = of("small", NpcTextureType.EYE);
    }

    public record Hair() {
        public final static RegistryKey<NpcTexturePattern> SHORT = of("hair_short", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> BALD_SIDES = of("hair_bald_sides", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> BOWL = of("hair_bowl", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> UNCUT = of("hair_uncut", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> SHARP = of("hair_sharp", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> LONG = of("hair_long", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> FLAT_LONG = of("hair_flat_long", NpcTextureType.HAIR);
    }

    public record Beard() {
        public final static RegistryKey<NpcTexturePattern> SHORT = of("beard_short", NpcTextureType.BEARD);
        public final static RegistryKey<NpcTexturePattern> SINGLE = of("beard_single", NpcTextureType.BEARD);
        public final static RegistryKey<NpcTexturePattern> LONG_SINGLE_ORNAMENTED = of("beard_long_single_ornamented", NpcTextureType.BEARD);
        public final static RegistryKey<NpcTexturePattern> CLEAN = of("beard_clean", NpcTextureType.BEARD);
        public final static RegistryKey<NpcTexturePattern> LARGE = of("beard_large", NpcTextureType.BEARD);
        public final static RegistryKey<NpcTexturePattern> UNCLEAN_ORNAMENTED = of("beard_unclean_ornamented", NpcTextureType.BEARD);
        public final static RegistryKey<NpcTexturePattern> DUAL_ORNAMENTED = of("beard_dual_ornamented", NpcTextureType.BEARD);
    }

    public record Eyebrow() {
        public final static RegistryKey<NpcTexturePattern> BASIC = of("eyebrow_basic", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> SHORT = of("eyebrow_short", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> LONG = of("eyebrow_long", NpcTextureType.HAIR);
    }


    public record Clothing(){
        public final static RegistryKey<NpcTexturePattern> FABRIC_SKIRT = of("fabric_skirt", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTexturePattern> FABRIC_SKIRT_WITH_STROPHIUM = of("fabric_skirt_with_strophium", NpcTextureType.CLOTHING);
    }


    public static void bootstrapSkins(Registerable<NpcTexturePattern> registry) {
        register(registry, Skin.MUSCULAR, NpcTextureType.SKIN);
        register(registry, Skin.FEMALE, NpcTextureType.SKIN);
        register(registry, Skin.SLIM, NpcTextureType.SKIN);

        register(registry, Ear.CUBE, NpcTextureType.EAR);
        register(registry, Ear.POINTY, NpcTextureType.EAR);

        register(registry, Nose.CUBE, NpcTextureType.NOSE);
        register(registry, Nose.LARGE_CUBE, NpcTextureType.NOSE);
    }
    public static void bootstrapEyes(Registerable<NpcTexturePattern> registry) {
        register(registry, Eye.SMALL, NpcTextureType.EYE);
        register(registry, Eye.COMMON, NpcTextureType.EYE);
    }
    public static void bootstrapHairs(Registerable<NpcTexturePattern> registry) {
        register(registry, Hair.SHORT, NpcTextureType.HAIR);
        register(registry, Hair.LONG, NpcTextureType.HAIR, true);
        register(registry, Hair.FLAT_LONG, NpcTextureType.HAIR, true);
        register(registry, Hair.BOWL, NpcTextureType.HAIR);
        register(registry, Hair.UNCUT, NpcTextureType.HAIR);
        register(registry, Hair.SHARP, NpcTextureType.HAIR);
        register(registry, Hair.BALD_SIDES, NpcTextureType.HAIR);

        register(registry, Eyebrow.BASIC, NpcTextureType.EYEBROW);
        register(registry, Eyebrow.SHORT, NpcTextureType.EYEBROW);
        register(registry, Eyebrow.LONG, NpcTextureType.EYEBROW);

        register(registry, Beard.SHORT, NpcTextureType.BEARD);
        register(registry, Beard.SINGLE, NpcTextureType.BEARD, true);
        register(registry, Beard.CLEAN, NpcTextureType.BEARD, true);
        register(registry, Beard.LARGE, NpcTextureType.BEARD, true);
        register(registry, Beard.LONG_SINGLE_ORNAMENTED, NpcTextureType.BEARD, true);
        register(registry, Beard.UNCLEAN_ORNAMENTED, NpcTextureType.BEARD, true);
        register(registry, Beard.DUAL_ORNAMENTED, NpcTextureType.BEARD, true);
    }
    public static void bootstrapClothings(Registerable<NpcTexturePattern> registry) {
        register(registry, Clothing.FABRIC_SKIRT, NpcTextureType.CLOTHING);
        register(registry, Clothing.FABRIC_SKIRT_WITH_STROPHIUM, NpcTextureType.CLOTHING);
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
        DynamicRegistries.registerSynced(Keys.SKIN_KEY, NpcTexturePattern.CODEC);
        DynamicRegistries.registerSynced(Keys.EYE_KEY, NpcTexturePattern.CODEC);
        DynamicRegistries.registerSynced(Keys.HAIR_KEY, NpcTexturePattern.CODEC);
        DynamicRegistries.registerSynced(Keys.CLOTHING_KEY, NpcTexturePattern.CODEC);
    }

    private static void register(Registerable<NpcTexturePattern> registerable, RegistryKey<NpcTexturePattern> registryKey, NpcTexturePattern content, RegistryKey<Registry<NpcTexturePattern>> registryRegistryKey) {
        String name = registryKey.getValue().getPath();
        RegistryKey<NpcTexturePattern> key = RegistryKey.of(registryRegistryKey,Identifier.of(MiddleEarth.MOD_ID,name));
        registerable.register(key, content);
    }

    public static RegistryKey<Registry<NpcTexturePattern>> getKey(NpcTextureType category){
        return switch (category) {
            case NpcTextureType.SKIN, NpcTextureType.EAR, NpcTextureType.NOSE -> Keys.SKIN_KEY;
            case NpcTextureType.EYE -> Keys.EYE_KEY;
            case NpcTextureType.HAIR, NpcTextureType.EYEBROW, NpcTextureType.BEARD -> Keys.HAIR_KEY;
            case NpcTextureType.CLOTHING -> Keys.CLOTHING_KEY;
        };
    }

    public static Optional<RegistryEntry.Reference<NpcTexturePattern>> get(RegistryWrapper.WrapperLookup registries, NpcTextureType type, Identifier id) {
        return registries.getOrThrow(getKey(type)).streamEntries().filter((pattern) ->
                pattern.value().getCategory() == type && pattern.matchesId(id)).findFirst();
    }

}
