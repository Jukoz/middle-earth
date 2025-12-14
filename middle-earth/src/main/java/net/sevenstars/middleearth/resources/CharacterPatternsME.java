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

/**
 * Middle-earth mod npc texture patterns registry<br>
 * Used to register the different patterns<br>
 * <b><u>Datadriven content, do not use during runtime!</u></b>
 * <hr>
 */
public class CharacterPatternsME {

    public record Keys(){
        public static final RegistryKey<Registry<NpcTexturePattern>> SKIN_KEY = ofRegistry("character_skin_pattern");
        public static final RegistryKey<Registry<NpcTexturePattern>> EYE_KEY = ofRegistry("character_eye_pattern");
        public static final RegistryKey<Registry<NpcTexturePattern>> HAIR_KEY = ofRegistry("character_hair_pattern");
        public static final RegistryKey<Registry<NpcTexturePattern>> CLOTHING_KEY = ofRegistry("character_clothing_pattern");
    }

    public record Skins() {
        public record Body(){
            public final static RegistryKey<NpcTexturePattern> FAT                      = of("body_fat",  NpcTextureType.BODY);
            public final static RegistryKey<NpcTexturePattern> FEMALE                   = of("body_female",  NpcTextureType.BODY);
            public final static RegistryKey<NpcTexturePattern> MUSCULAR                 = of("body_muscular",  NpcTextureType.BODY);
            public final static RegistryKey<NpcTexturePattern> SKIN_TO_BONE             = of("body_skin_to_bone",  NpcTextureType.BODY);
            public final static RegistryKey<NpcTexturePattern> SLIM                     = of("body_slim",  NpcTextureType.BODY);
        }

        public record Head(){
            public final static RegistryKey<NpcTexturePattern> FEMALE                   = of("head_female", NpcTextureType.HEAD);
            public final static RegistryKey<NpcTexturePattern> GOBLIN                   = of("head_goblin",  NpcTextureType.HEAD);
            public final static RegistryKey<NpcTexturePattern> GOBLIN_SMALL             = of("head_goblin_small",  NpcTextureType.HEAD);
            public final static RegistryKey<NpcTexturePattern> GOBLIN_SMALL_THICK_BROW  = of("head_goblin_thick_brow",  NpcTextureType.HEAD);
            public final static RegistryKey<NpcTexturePattern> GOBLIN_SMALL_VERY_WIDE   = of("head_goblin_very_wide",  NpcTextureType.HEAD);
            public final static RegistryKey<NpcTexturePattern> GOBLIN_SMALL_WISE        = of("head_goblin_wise",  NpcTextureType.HEAD);
            public final static RegistryKey<NpcTexturePattern> MALE                     = of("head_male",  NpcTextureType.HEAD);
            public final static RegistryKey<NpcTexturePattern> URUK_DUMB                = of("head_uruk_dumb",  NpcTextureType.HEAD);
            public final static RegistryKey<NpcTexturePattern> URUK_TALL_DUMB           = of("head_uruk_tall_dumb",  NpcTextureType.HEAD);
        }

        public record Ear(){
            public final static RegistryKey<NpcTexturePattern> LARGE_POINTY             = of("ear_large_pointy",  NpcTextureType.EAR);
            public final static RegistryKey<NpcTexturePattern> NORMAL                   = of("ear_normal",  NpcTextureType.EAR);
            public final static RegistryKey<NpcTexturePattern> POINTY                   = of("ear_pointy",  NpcTextureType.EAR);
            public final static RegistryKey<NpcTexturePattern> SMALL                    = of("ear_small",  NpcTextureType.EAR);
            public final static RegistryKey<NpcTexturePattern> SMALL_POINTY             = of("ear_small_pointy",  NpcTextureType.EAR);
            public final static RegistryKey<NpcTexturePattern> SQUARE                   = of("ear_square",  NpcTextureType.EAR);
            public final static RegistryKey<NpcTexturePattern> SQUARE_POINTY            = of("ear_square_pointy",  NpcTextureType.EAR);
            public final static RegistryKey<NpcTexturePattern> WIDE_POINTY              = of("ear_wide_pointy",  NpcTextureType.EAR);
        }

        public record Nose(){
            public final static RegistryKey<NpcTexturePattern> CUBE                     = of("nose_cube", NpcTextureType.NOSE);
            public final static RegistryKey<NpcTexturePattern> VILLAGER                 = of("nose_villager", NpcTextureType.NOSE);
            public final static RegistryKey<NpcTexturePattern> LARGE_CUBE_CENTER        = of("nose_large_center", NpcTextureType.NOSE);
            public final static RegistryKey<NpcTexturePattern> LARGE_CUBE_HIGH          = of("nose_large_high", NpcTextureType.NOSE);
        }

        public record Scar(){
            public final static RegistryKey<NpcTexturePattern> EYE_RIGHT                = of("scar_eye_right", NpcTextureType.SCAR);
            public final static RegistryKey<NpcTexturePattern> EYE_LEFT                 = of("scar_eye_left", NpcTextureType.SCAR);
        }
    }

    public record Eyes() {
        public record Eye() {
            public final static RegistryKey<NpcTexturePattern> COMMON                   = of("eye_common", NpcTextureType.EYE);
            public final static RegistryKey<NpcTexturePattern> BLIND_LEFT               = of("eye_blind_left", NpcTextureType.EYE);
            public final static RegistryKey<NpcTexturePattern> BLIND_RIGHT              = of("eye_blind_right", NpcTextureType.EYE);
            public final static RegistryKey<NpcTexturePattern> SMALL                    = of("eye_small", NpcTextureType.EYE);
            public final static RegistryKey<NpcTexturePattern> SMALL_HIGH_WIDE          = of("eye_small_high_wide", NpcTextureType.EYE);
            public final static RegistryKey<NpcTexturePattern> SMALL_LOW_WIDE           = of("eye_small_low_wide", NpcTextureType.EYE);
            public final static RegistryKey<NpcTexturePattern> SMALL_WIDE               = of("eye_small_wide", NpcTextureType.EYE);
        }
    }

    public record Hairs() {
        public class Hair {
            public final static RegistryKey<NpcTexturePattern> BALD_DREADLOCKS_ORNAMENTED = of("hair_bald_dreadlocks_ornamented", NpcTextureType.HAIR);
            public final static RegistryKey<NpcTexturePattern> BALD_SIDES = of("hair_bald_sides", NpcTextureType.HAIR);
            public final static RegistryKey<NpcTexturePattern> BALD_SMALL_DREADLOCKS = of("hair_bald_small_dreadlocks", NpcTextureType.HAIR);
            public final static RegistryKey<NpcTexturePattern> SIDE_BALDING = of("hair_side_balding", NpcTextureType.HAIR);
            public final static RegistryKey<NpcTexturePattern> TOP_BALDING = of("hair_top_balding", NpcTextureType.HAIR);
            public final static RegistryKey<NpcTexturePattern> BOWL = of("hair_bowl", NpcTextureType.HAIR);
            public final static RegistryKey<NpcTexturePattern> DIRTY_MOP = of("hair_dirty_mop", NpcTextureType.HAIR);
            public final static RegistryKey<NpcTexturePattern> FLAT_LONG = of("hair_flat_long", NpcTextureType.HAIR);
            public final static RegistryKey<NpcTexturePattern> LONG = of("hair_long", NpcTextureType.HAIR);
            public final static RegistryKey<NpcTexturePattern> PONYTAIL_SHORT_ORNAMENTED = of("hair_ponytail_short_ornamented", NpcTextureType.HAIR);
            public final static RegistryKey<NpcTexturePattern> SHARP = of("hair_sharp", NpcTextureType.HAIR);
            public final static RegistryKey<NpcTexturePattern> SHORT = of("hair_short", NpcTextureType.HAIR);
            public final static RegistryKey<NpcTexturePattern> UNCUT = of("hair_uncut", NpcTextureType.HAIR);
            public final static RegistryKey<NpcTexturePattern> VERY_LONG = of("hair_very_long", NpcTextureType.HAIR);
            public final static RegistryKey<NpcTexturePattern> SEMI_LONG = of("hair_semi_long", NpcTextureType.HAIR);
        }

        public record Beard() {
            public final static RegistryKey<NpcTexturePattern> BROAD                    = of("beard_broad", NpcTextureType.BEARD);
            public final static RegistryKey<NpcTexturePattern> CHUNKY_BRAIDS            = of("beard_chunky_braids", NpcTextureType.BEARD);
            public final static RegistryKey<NpcTexturePattern> CLEAN                    = of("beard_clean", NpcTextureType.BEARD);
            public final static RegistryKey<NpcTexturePattern> DUAL_LARGE_ORNAMENTED    = of("beard_dual_large_ornamented", NpcTextureType.BEARD);
            public final static RegistryKey<NpcTexturePattern> DUAL_ORNAMENTED          = of("beard_dual_ornamented", NpcTextureType.BEARD);
            public final static RegistryKey<NpcTexturePattern> FANCY_MUSTACHE_ORNAMENTED= of("beard_fancy_mustache_ornamented", NpcTextureType.BEARD);
            public final static RegistryKey<NpcTexturePattern> LARGE                    = of("beard_large", NpcTextureType.BEARD);
            public final static RegistryKey<NpcTexturePattern> LONG_BRAIDS_ORNAMENTED   = of("beard_long_braids_ornamented", NpcTextureType.BEARD);
            public final static RegistryKey<NpcTexturePattern> LONG_SINGLE_ORNAMENTED   = of("beard_long_single_ornamented", NpcTextureType.BEARD);
            public final static RegistryKey<NpcTexturePattern> SHORT                    = of("beard_short", NpcTextureType.BEARD);
            public final static RegistryKey<NpcTexturePattern> SINGLE                   = of("beard_single", NpcTextureType.BEARD);
            public final static RegistryKey<NpcTexturePattern> UNCLEAN_ORNAMENTED       = of("beard_unclean_ornamented", NpcTextureType.BEARD);
            public final static RegistryKey<NpcTexturePattern> VERY_BROAD               = of("beard_very_broad", NpcTextureType.BEARD);
            public final static RegistryKey<NpcTexturePattern> VERY_LARGE_MUSTACHE      = of("beard_very_large_mustache", NpcTextureType.BEARD);
            public final static RegistryKey<NpcTexturePattern> VERY_LONG                = of("beard_very_long", NpcTextureType.BEARD);
            public final static RegistryKey<NpcTexturePattern> SEMI_LONG                = of("beard_semi_long", NpcTextureType.BEARD);
        }

        public record Eyebrow() {
            public final static RegistryKey<NpcTexturePattern> BASIC = of("eyebrow_basic", NpcTextureType.EYEBROW);
            public final static RegistryKey<NpcTexturePattern> LONG = of("eyebrow_long", NpcTextureType.EYEBROW);
            public final static RegistryKey<NpcTexturePattern> SHORT = of("eyebrow_short", NpcTextureType.EYEBROW);
            public final static RegistryKey<NpcTexturePattern> UNI = of("eyebrow_uni", NpcTextureType.EYEBROW);
            public final static RegistryKey<NpcTexturePattern> THICK = of("eyebrow_thick", NpcTextureType.EYEBROW);
        }
    }

    public record Clothing(){
        public final static RegistryKey<NpcTexturePattern> FULL_TOGA = of("clothing_full_toga", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTexturePattern> ROBE = of("clothing_robe", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTexturePattern> SKIRT = of("clothing_skirt", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTexturePattern> SKIRT_WITH_STROPHIUM = of("clothing_skirt_with_strophium", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTexturePattern> TOGA = of("clothing_toga", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTexturePattern> THONG = of("clothing_thong", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTexturePattern> PANTS = of("clothing_pants", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTexturePattern> DWARVEN_GARMENT = of("clothing_dwarven_garment", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTexturePattern> DWARVEN_GARMENT_WITH_PANTS = of("clothing_dwarven_garment_with_pants", NpcTextureType.CLOTHING);

    }

    public static void bootstrapSkins(Registerable<NpcTexturePattern> registry) {
        register(registry, Skins.Body.FAT, NpcTextureType.SKIN);
        register(registry, Skins.Body.FEMALE, NpcTextureType.SKIN);
        register(registry, Skins.Body.MUSCULAR, NpcTextureType.SKIN);
        register(registry, Skins.Body.SKIN_TO_BONE, NpcTextureType.SKIN);
        register(registry, Skins.Body.SLIM, NpcTextureType.SKIN);

        register(registry, Skins.Head.FEMALE, NpcTextureType.HEAD);
        register(registry, Skins.Head.GOBLIN, NpcTextureType.HEAD);
        register(registry, Skins.Head.GOBLIN_SMALL, NpcTextureType.HEAD);
        register(registry, Skins.Head.GOBLIN_SMALL_THICK_BROW, NpcTextureType.HEAD);
        register(registry, Skins.Head.GOBLIN_SMALL_VERY_WIDE, NpcTextureType.HEAD);
        register(registry, Skins.Head.GOBLIN_SMALL_WISE, NpcTextureType.HEAD);
        register(registry, Skins.Head.MALE, NpcTextureType.HEAD);
        register(registry, Skins.Head.URUK_DUMB, NpcTextureType.HEAD);
        register(registry, Skins.Head.URUK_TALL_DUMB, NpcTextureType.HEAD);

        register(registry, Skins.Ear.LARGE_POINTY, NpcTextureType.EAR);
        register(registry, Skins.Ear.NORMAL, NpcTextureType.EAR);
        register(registry, Skins.Ear.POINTY, NpcTextureType.EAR);
        register(registry, Skins.Ear.SMALL, NpcTextureType.EAR);
        register(registry, Skins.Ear.SMALL_POINTY, NpcTextureType.EAR);
        register(registry, Skins.Ear.SQUARE, NpcTextureType.EAR);
        register(registry, Skins.Ear.SQUARE_POINTY, NpcTextureType.EAR);
        register(registry, Skins.Ear.WIDE_POINTY, NpcTextureType.EAR);

        register(registry, Skins.Nose.CUBE, NpcTextureType.NOSE);
        register(registry, Skins.Nose.VILLAGER, NpcTextureType.NOSE);
        register(registry, Skins.Nose.LARGE_CUBE_CENTER, NpcTextureType.NOSE);
        register(registry, Skins.Nose.LARGE_CUBE_HIGH, NpcTextureType.NOSE);

        register(registry, Skins.Scar.EYE_RIGHT, NpcTextureType.SCAR);
        register(registry, Skins.Scar.EYE_LEFT, NpcTextureType.SCAR);
    }

    public static void bootstrapEyes(Registerable<NpcTexturePattern> registry) {
        register(registry, Eyes.Eye.COMMON, NpcTextureType.EYE);
        register(registry, Eyes.Eye.BLIND_LEFT, NpcTextureType.EYE);
        register(registry, Eyes.Eye.BLIND_RIGHT, NpcTextureType.EYE);
        register(registry, Eyes.Eye.SMALL, NpcTextureType.EYE);
        register(registry, Eyes.Eye.SMALL_HIGH_WIDE, NpcTextureType.EYE);
        register(registry, Eyes.Eye.SMALL_LOW_WIDE, NpcTextureType.EYE);
        register(registry, Eyes.Eye.SMALL_WIDE, NpcTextureType.EYE);
    }

    public static void bootstrapHairs(Registerable<NpcTexturePattern> registry) {
        register(registry, Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED, NpcTextureType.HAIR, true);
        register(registry, Hairs.Hair.BALD_SIDES, NpcTextureType.HAIR);
        register(registry, Hairs.Hair.BALD_SMALL_DREADLOCKS, NpcTextureType.HAIR, true);
        register(registry, Hairs.Hair.BOWL, NpcTextureType.HAIR);
        register(registry, Hairs.Hair.DIRTY_MOP, NpcTextureType.HAIR, true);
        register(registry, Hairs.Hair.FLAT_LONG, NpcTextureType.HAIR, true);
        register(registry, Hairs.Hair.LONG, NpcTextureType.HAIR, true);
        register(registry, Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED, NpcTextureType.HAIR);
        register(registry, Hairs.Hair.SHARP, NpcTextureType.HAIR);
        register(registry, Hairs.Hair.SHORT, NpcTextureType.HAIR);
        register(registry, Hairs.Hair.SIDE_BALDING, NpcTextureType.HAIR);
        register(registry, Hairs.Hair.TOP_BALDING, NpcTextureType.HAIR,true);
        register(registry, Hairs.Hair.UNCUT, NpcTextureType.HAIR);
        register(registry, Hairs.Hair.VERY_LONG, NpcTextureType.HAIR, true);
        register(registry, Hairs.Hair.SEMI_LONG, NpcTextureType.HAIR, true);

        register(registry, Hairs.Eyebrow.BASIC, NpcTextureType.EYEBROW);
        register(registry, Hairs.Eyebrow.LONG, NpcTextureType.EYEBROW);
        register(registry, Hairs.Eyebrow.SHORT, NpcTextureType.EYEBROW);
        register(registry, Hairs.Eyebrow.UNI, NpcTextureType.EYEBROW);
        register(registry, Hairs.Eyebrow.THICK, NpcTextureType.EYEBROW);

        register(registry, Hairs.Beard.BROAD, NpcTextureType.BEARD, true);
        register(registry, Hairs.Beard.CHUNKY_BRAIDS, NpcTextureType.BEARD, true);
        register(registry, Hairs.Beard.CLEAN, NpcTextureType.BEARD, true);
        register(registry, Hairs.Beard.DUAL_LARGE_ORNAMENTED, NpcTextureType.BEARD, true);
        register(registry, Hairs.Beard.LONG_SINGLE_ORNAMENTED, NpcTextureType.BEARD, true);
        register(registry, Hairs.Beard.FANCY_MUSTACHE_ORNAMENTED, NpcTextureType.BEARD, true);
        register(registry, Hairs.Beard.DUAL_ORNAMENTED, NpcTextureType.BEARD, true);
        register(registry, Hairs.Beard.LARGE, NpcTextureType.BEARD, true);
        register(registry, Hairs.Beard.LONG_BRAIDS_ORNAMENTED, NpcTextureType.BEARD, true);
        register(registry, Hairs.Beard.SHORT, NpcTextureType.BEARD, false);
        register(registry, Hairs.Beard.SINGLE, NpcTextureType.BEARD, true);
        register(registry, Hairs.Beard.UNCLEAN_ORNAMENTED, NpcTextureType.BEARD, true);
        register(registry, Hairs.Beard.VERY_BROAD, NpcTextureType.BEARD, true);
        register(registry, Hairs.Beard.VERY_LARGE_MUSTACHE, NpcTextureType.BEARD, true);
        register(registry, Hairs.Beard.VERY_LONG, NpcTextureType.BEARD, true);
        register(registry, Hairs.Beard.SEMI_LONG, NpcTextureType.BEARD, true);
    }

    public static void bootstrapClothings(Registerable<NpcTexturePattern> registry) {
        register(registry, Clothing.FULL_TOGA, NpcTextureType.CLOTHING);
        register(registry, Clothing.ROBE, NpcTextureType.CLOTHING);
        register(registry, Clothing.SKIRT, NpcTextureType.CLOTHING);
        register(registry, Clothing.SKIRT_WITH_STROPHIUM, NpcTextureType.CLOTHING);
        register(registry, Clothing.TOGA, NpcTextureType.CLOTHING);
        register(registry, Clothing.PANTS, NpcTextureType.CLOTHING);
        register(registry, Clothing.DWARVEN_GARMENT, NpcTextureType.CLOTHING);
        register(registry, Clothing.DWARVEN_GARMENT_WITH_PANTS, NpcTextureType.CLOTHING);
        register(registry, Clothing.THONG, NpcTextureType.CLOTHING);
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
        return RegistryKey.of(getKey(type), IdentifierUtil.build(id));
    }

    private static <T> RegistryKey<Registry<T>> ofRegistry(String id) {
        return RegistryKey.ofRegistry(IdentifierUtil.build(id));
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
            case NpcTextureType.SKIN, NpcTextureType.BODY, NpcTextureType.HEAD, NpcTextureType.SCAR, NpcTextureType.EAR, NpcTextureType.NOSE -> Keys.SKIN_KEY;
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
