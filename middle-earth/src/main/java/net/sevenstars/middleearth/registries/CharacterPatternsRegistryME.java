package net.sevenstars.middleearth.registries;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTexturePattern;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.Optional;

/**
 * Middle-earth mod npc texture patterns registry<br>
 * Used to register the different patterns<br>
 * <b><u>Datadriven content, do not use during runtime!</u></b>
 * <hr>
 */
public class CharacterPatternsRegistryME {
    public record Skins() {
        public record Body(){
            public final static RegistryKey<CharacterTexturePattern> FAT                          = of("body_fat",  CharacterPatternTypes.BODY);
            public final static RegistryKey<CharacterTexturePattern> FEMALE                       = of("body_female",  CharacterPatternTypes.BODY);
            public final static RegistryKey<CharacterTexturePattern> MUSCULAR                     = of("body_muscular",  CharacterPatternTypes.BODY);
            public final static RegistryKey<CharacterTexturePattern> SKIN_TO_BONE                 = of("body_skin_to_bone",  CharacterPatternTypes.BODY);
            public final static RegistryKey<CharacterTexturePattern> SLIM                         = of("body_slim",  CharacterPatternTypes.BODY);
        }

        public record Feet(){
            public final static RegistryKey<CharacterTexturePattern> NORMAL                       = of("feet_normal", CharacterPatternTypes.FEET);
        }

        public record Head(){
            ///  Eyes Level : 4 <br> Type : Normal
            public final static RegistryKey<CharacterTexturePattern> FEMALE                       = of("head_female", CharacterPatternTypes.HEAD);
            ///  Eyes Level : 4 <br> Type : Normal
            public final static RegistryKey<CharacterTexturePattern> MALE                         = of("head_male",  CharacterPatternTypes.HEAD);
            ///  Eyes Level : 4 <br> Type : Normal, Wide
            public final static RegistryKey<CharacterTexturePattern> GOBLIN                       = of("head_goblin",  CharacterPatternTypes.HEAD);
            ///  Eyes Level : 4 <br> Type : Normal, Wide
            public final static RegistryKey<CharacterTexturePattern> GOBLIN_SMALL                 = of("head_goblin_small",  CharacterPatternTypes.HEAD);
            ///  Eyes Level : 4 <br> Type : Normal, Small, Wide
            public final static RegistryKey<CharacterTexturePattern> GOBLIN_SMALL_THICK_BROW      = of("head_goblin_thick_brow",  CharacterPatternTypes.HEAD);
            ///  Eyes Level : 4 <br> Type : Normal, Small, Wide
            public final static RegistryKey<CharacterTexturePattern> GOBLIN_SMALL_VERY_WIDE       = of("head_goblin_very_wide",  CharacterPatternTypes.HEAD);
            ///  Eyes Level : 4 <br> Type : Normal, Small, Wide
            public final static RegistryKey<CharacterTexturePattern> GOBLIN_SMALL_WISE            = of("head_goblin_wise",  CharacterPatternTypes.HEAD);
            ///  Eyes Level : 5 <br> Type : Normal, Wide
            public final static RegistryKey<CharacterTexturePattern> URUK_DUMB                    = of("head_uruk_dumb",  CharacterPatternTypes.HEAD);
            ///  Eyes Level : 6 <br> Type : Wide
            public final static RegistryKey<CharacterTexturePattern> URUK_TALL_DUMB               = of("head_uruk_tall_dumb",  CharacterPatternTypes.HEAD);
        }

        public record Ear(){
            public final static RegistryKey<CharacterTexturePattern> LARGE_POINTY                 = of("ear_large_pointy",  CharacterPatternTypes.EAR);
            public final static RegistryKey<CharacterTexturePattern> NORMAL                       = of("ear_normal",  CharacterPatternTypes.EAR);
            public final static RegistryKey<CharacterTexturePattern> POINTY                       = of("ear_pointy",  CharacterPatternTypes.EAR);
            public final static RegistryKey<CharacterTexturePattern> SMALL                        = of("ear_small",  CharacterPatternTypes.EAR);
            public final static RegistryKey<CharacterTexturePattern> SMALL_POINTY                 = of("ear_small_pointy",  CharacterPatternTypes.EAR);
            public final static RegistryKey<CharacterTexturePattern> SQUARE                       = of("ear_square",  CharacterPatternTypes.EAR);
            public final static RegistryKey<CharacterTexturePattern> SQUARE_POINTY                = of("ear_square_pointy",  CharacterPatternTypes.EAR);
            public final static RegistryKey<CharacterTexturePattern> WIDE_POINTY                  = of("ear_wide_pointy",  CharacterPatternTypes.EAR);
        }

        public record Nose(){
            public final static RegistryKey<CharacterTexturePattern> CUBE                         = of("nose_cube", CharacterPatternTypes.NOSE);
            public final static RegistryKey<CharacterTexturePattern> VILLAGER                     = of("nose_villager", CharacterPatternTypes.NOSE);
            public final static RegistryKey<CharacterTexturePattern> LARGE_CUBE_CENTER            = of("nose_large_center", CharacterPatternTypes.NOSE);
            public final static RegistryKey<CharacterTexturePattern> LARGE_CUBE_HIGH              = of("nose_large_high", CharacterPatternTypes.NOSE);
        }

        public record Scar(){
            public final static RegistryKey<CharacterTexturePattern> EYE_RIGHT                    = of("scar_eye_right", CharacterPatternTypes.SCAR);
            public final static RegistryKey<CharacterTexturePattern> EYE_LEFT                     = of("scar_eye_left", CharacterPatternTypes.SCAR);
        }
    }

    public record Eyes() {
        public record Eye() {
            ///  Eyes Level : 4 <br> Type : Normal
            public final static RegistryKey<CharacterTexturePattern> BLIND_LEFT                   = of("eye_blind_left", CharacterPatternTypes.EYE);
            ///  Eyes Level : 4 <br> Type : Normal
            public final static RegistryKey<CharacterTexturePattern> BLIND_RIGHT                  = of("eye_blind_right", CharacterPatternTypes.EYE);
            ///  Eyes Level : 4 <br> Type : Normal
            public final static RegistryKey<CharacterTexturePattern> COMMON                       = of("eye_common", CharacterPatternTypes.EYE);
            ///  Eyes Level : 5 <br> Type : Normal
            public final static RegistryKey<CharacterTexturePattern> COMMON_HIGH                  = of("eye_common_high", CharacterPatternTypes.EYE);
            ///  Eyes Level : 4 <br> Type : Small (Narrow)
            public final static RegistryKey<CharacterTexturePattern> SMALL                        = of("eye_small", CharacterPatternTypes.EYE);
            ///  Eyes Level : 5 <br> Type : Small (Wide)
            public final static RegistryKey<CharacterTexturePattern> SMALL_HIGH_WIDE              = of("eye_small_high_wide", CharacterPatternTypes.EYE);
            ///  Eyes Level : 6 <br> Type : Small (Wide)
            public final static RegistryKey<CharacterTexturePattern> SMALL_VERY_HIGH_WIDE         = of("eye_small_very_high_wide", CharacterPatternTypes.EYE);
            ///  Eyes Level : 4 <br> Type : Small (Wide)
            public final static RegistryKey<CharacterTexturePattern> SMALL_WIDE                   = of("eye_small_wide", CharacterPatternTypes.EYE);
        }
    }

    public record Hairs() {
        public class Hair {
            public final static RegistryKey<CharacterTexturePattern> BALD_DREADLOCKS_ORNAMENTED   = of("hair_bald_dreadlocks_ornamented", CharacterPatternTypes.HAIR);
            public final static RegistryKey<CharacterTexturePattern> BALD_SIDES                   = of("hair_bald_sides", CharacterPatternTypes.HAIR);
            public final static RegistryKey<CharacterTexturePattern> BALD_SMALL_DREADLOCKS        = of("hair_bald_small_dreadlocks", CharacterPatternTypes.HAIR);
            public final static RegistryKey<CharacterTexturePattern> SIDE_BALDING                 = of("hair_side_balding", CharacterPatternTypes.HAIR);
            public final static RegistryKey<CharacterTexturePattern> TOP_BALDING                  = of("hair_top_balding", CharacterPatternTypes.HAIR);
            public final static RegistryKey<CharacterTexturePattern> BOWL                         = of("hair_bowl", CharacterPatternTypes.HAIR);
            public final static RegistryKey<CharacterTexturePattern> DIRTY_MOP                    = of("hair_dirty_mop", CharacterPatternTypes.HAIR);
            public final static RegistryKey<CharacterTexturePattern> FLAT_LONG                    = of("hair_flat_long", CharacterPatternTypes.HAIR);
            public final static RegistryKey<CharacterTexturePattern> LONG                         = of("hair_long", CharacterPatternTypes.HAIR);
            public final static RegistryKey<CharacterTexturePattern> PONYTAIL_SHORT_ORNAMENTED    = of("hair_ponytail_short_ornamented", CharacterPatternTypes.HAIR);
            public final static RegistryKey<CharacterTexturePattern> SHARP                        = of("hair_sharp", CharacterPatternTypes.HAIR);
            public final static RegistryKey<CharacterTexturePattern> SHORT                        = of("hair_short", CharacterPatternTypes.HAIR);
            public final static RegistryKey<CharacterTexturePattern> UNCUT                        = of("hair_uncut", CharacterPatternTypes.HAIR);
            public final static RegistryKey<CharacterTexturePattern> VERY_LONG                    = of("hair_very_long", CharacterPatternTypes.HAIR);
            public final static RegistryKey<CharacterTexturePattern> SEMI_LONG                    = of("hair_semi_long", CharacterPatternTypes.HAIR);
        }

        public record Beard() {
            public final static RegistryKey<CharacterTexturePattern> BROAD                        = of("beard_broad", CharacterPatternTypes.BEARD);
            public final static RegistryKey<CharacterTexturePattern> CHUNKY_BRAIDS                = of("beard_chunky_braids", CharacterPatternTypes.BEARD);
            public final static RegistryKey<CharacterTexturePattern> CLEAN                        = of("beard_clean", CharacterPatternTypes.BEARD);
            public final static RegistryKey<CharacterTexturePattern> DUAL_LARGE_ORNAMENTED        = of("beard_dual_large_ornamented", CharacterPatternTypes.BEARD);
            public final static RegistryKey<CharacterTexturePattern> DUAL_ORNAMENTED              = of("beard_dual_ornamented", CharacterPatternTypes.BEARD);
            public final static RegistryKey<CharacterTexturePattern> FANCY_MUSTACHE_ORNAMENTED    = of("beard_fancy_mustache_ornamented", CharacterPatternTypes.BEARD);
            public final static RegistryKey<CharacterTexturePattern> LARGE                        = of("beard_large", CharacterPatternTypes.BEARD);
            public final static RegistryKey<CharacterTexturePattern> LONG_BRAIDS_ORNAMENTED       = of("beard_long_braids_ornamented", CharacterPatternTypes.BEARD);
            public final static RegistryKey<CharacterTexturePattern> LONG_SINGLE_ORNAMENTED       = of("beard_long_single_ornamented", CharacterPatternTypes.BEARD);
            public final static RegistryKey<CharacterTexturePattern> SHORT                        = of("beard_short", CharacterPatternTypes.BEARD);
            public final static RegistryKey<CharacterTexturePattern> SINGLE                       = of("beard_single", CharacterPatternTypes.BEARD);
            public final static RegistryKey<CharacterTexturePattern> UNCLEAN_ORNAMENTED           = of("beard_unclean_ornamented", CharacterPatternTypes.BEARD);
            public final static RegistryKey<CharacterTexturePattern> VERY_BROAD                   = of("beard_very_broad", CharacterPatternTypes.BEARD);
            public final static RegistryKey<CharacterTexturePattern> VERY_LARGE_MUSTACHE          = of("beard_very_large_mustache", CharacterPatternTypes.BEARD);
            public final static RegistryKey<CharacterTexturePattern> VERY_LONG                    = of("beard_very_long", CharacterPatternTypes.BEARD);
            public final static RegistryKey<CharacterTexturePattern> SEMI_LONG                    = of("beard_semi_long", CharacterPatternTypes.BEARD);
        }

        public record Eyebrow() {
            public final static RegistryKey<CharacterTexturePattern> BASIC                        = of("eyebrow_basic", CharacterPatternTypes.EYEBROW);
            public final static RegistryKey<CharacterTexturePattern> LONG                         = of("eyebrow_long", CharacterPatternTypes.EYEBROW);
            public final static RegistryKey<CharacterTexturePattern> SHORT                        = of("eyebrow_short", CharacterPatternTypes.EYEBROW);
            public final static RegistryKey<CharacterTexturePattern> UNI                          = of("eyebrow_uni", CharacterPatternTypes.EYEBROW);
            public final static RegistryKey<CharacterTexturePattern> THICK                        = of("eyebrow_thick", CharacterPatternTypes.EYEBROW);
        }
    }

    public static void bootstrapSkins(Registerable<CharacterTexturePattern> registry) {
        register(registry, Skins.Body.FAT, CharacterPatternTypes.BODY);
        register(registry, Skins.Body.FEMALE, CharacterPatternTypes.BODY);
        register(registry, Skins.Body.MUSCULAR, CharacterPatternTypes.BODY);
        register(registry, Skins.Body.SKIN_TO_BONE, CharacterPatternTypes.BODY);
        register(registry, Skins.Body.SLIM, CharacterPatternTypes.BODY);

        register(registry, Skins.Head.FEMALE, CharacterPatternTypes.HEAD);
        register(registry, Skins.Head.GOBLIN, CharacterPatternTypes.HEAD);
        register(registry, Skins.Head.GOBLIN_SMALL, CharacterPatternTypes.HEAD);
        register(registry, Skins.Head.GOBLIN_SMALL_THICK_BROW, CharacterPatternTypes.HEAD);
        register(registry, Skins.Head.GOBLIN_SMALL_VERY_WIDE, CharacterPatternTypes.HEAD);
        register(registry, Skins.Head.GOBLIN_SMALL_WISE, CharacterPatternTypes.HEAD);
        register(registry, Skins.Head.MALE, CharacterPatternTypes.HEAD);
        register(registry, Skins.Head.URUK_DUMB, CharacterPatternTypes.HEAD);
        register(registry, Skins.Head.URUK_TALL_DUMB, CharacterPatternTypes.HEAD);

        register(registry, Skins.Ear.LARGE_POINTY, CharacterPatternTypes.EAR);
        register(registry, Skins.Ear.NORMAL, CharacterPatternTypes.EAR);
        register(registry, Skins.Ear.POINTY, CharacterPatternTypes.EAR);
        register(registry, Skins.Ear.SMALL, CharacterPatternTypes.EAR);
        register(registry, Skins.Ear.SMALL_POINTY, CharacterPatternTypes.EAR);
        register(registry, Skins.Ear.SQUARE, CharacterPatternTypes.EAR);
        register(registry, Skins.Ear.SQUARE_POINTY, CharacterPatternTypes.EAR);
        register(registry, Skins.Ear.WIDE_POINTY, CharacterPatternTypes.EAR);

        register(registry, Skins.Nose.CUBE, CharacterPatternTypes.NOSE);
        register(registry, Skins.Nose.VILLAGER, CharacterPatternTypes.NOSE);
        register(registry, Skins.Nose.LARGE_CUBE_CENTER, CharacterPatternTypes.NOSE);
        register(registry, Skins.Nose.LARGE_CUBE_HIGH, CharacterPatternTypes.NOSE);

        register(registry, Skins.Scar.EYE_RIGHT, CharacterPatternTypes.SCAR);
        register(registry, Skins.Scar.EYE_LEFT, CharacterPatternTypes.SCAR);
    }

    public static void bootstrapEyes(Registerable<CharacterTexturePattern> registry) {
        register(registry, Eyes.Eye.BLIND_LEFT, CharacterPatternTypes.EYE);
        register(registry, Eyes.Eye.BLIND_RIGHT, CharacterPatternTypes.EYE);
        register(registry, Eyes.Eye.COMMON, CharacterPatternTypes.EYE);
        register(registry, Eyes.Eye.COMMON_HIGH, CharacterPatternTypes.EYE);
        register(registry, Eyes.Eye.SMALL, CharacterPatternTypes.EYE);
        register(registry, Eyes.Eye.SMALL_HIGH_WIDE, CharacterPatternTypes.EYE);
        register(registry, Eyes.Eye.SMALL_VERY_HIGH_WIDE, CharacterPatternTypes.EYE);
        register(registry, Eyes.Eye.SMALL_WIDE, CharacterPatternTypes.EYE);
    }

    public static void bootstrapHairs(Registerable<CharacterTexturePattern> registry) {
        register(registry, Hairs.Hair.BALD_DREADLOCKS_ORNAMENTED, CharacterPatternTypes.HAIR, true);
        register(registry, Hairs.Hair.BALD_SIDES, CharacterPatternTypes.HAIR);
        register(registry, Hairs.Hair.BALD_SMALL_DREADLOCKS, CharacterPatternTypes.HAIR, true);
        register(registry, Hairs.Hair.BOWL, CharacterPatternTypes.HAIR);
        register(registry, Hairs.Hair.DIRTY_MOP, CharacterPatternTypes.HAIR, true);
        register(registry, Hairs.Hair.FLAT_LONG, CharacterPatternTypes.HAIR, true);
        register(registry, Hairs.Hair.LONG, CharacterPatternTypes.HAIR, true);
        register(registry, Hairs.Hair.PONYTAIL_SHORT_ORNAMENTED, CharacterPatternTypes.HAIR);
        register(registry, Hairs.Hair.SHARP, CharacterPatternTypes.HAIR);
        register(registry, Hairs.Hair.SHORT, CharacterPatternTypes.HAIR);
        register(registry, Hairs.Hair.SIDE_BALDING, CharacterPatternTypes.HAIR);
        register(registry, Hairs.Hair.TOP_BALDING, CharacterPatternTypes.HAIR,true);
        register(registry, Hairs.Hair.UNCUT, CharacterPatternTypes.HAIR);
        register(registry, Hairs.Hair.VERY_LONG, CharacterPatternTypes.HAIR, true);
        register(registry, Hairs.Hair.SEMI_LONG, CharacterPatternTypes.HAIR, true);

        register(registry, Hairs.Eyebrow.BASIC, CharacterPatternTypes.EYEBROW);
        register(registry, Hairs.Eyebrow.LONG, CharacterPatternTypes.EYEBROW);
        register(registry, Hairs.Eyebrow.SHORT, CharacterPatternTypes.EYEBROW);
        register(registry, Hairs.Eyebrow.UNI, CharacterPatternTypes.EYEBROW);
        register(registry, Hairs.Eyebrow.THICK, CharacterPatternTypes.EYEBROW);

        register(registry, Hairs.Beard.BROAD, CharacterPatternTypes.BEARD, true);
        register(registry, Hairs.Beard.CHUNKY_BRAIDS, CharacterPatternTypes.BEARD, true);
        register(registry, Hairs.Beard.CLEAN, CharacterPatternTypes.BEARD, true);
        register(registry, Hairs.Beard.DUAL_LARGE_ORNAMENTED, CharacterPatternTypes.BEARD, true);
        register(registry, Hairs.Beard.LONG_SINGLE_ORNAMENTED, CharacterPatternTypes.BEARD, true);
        register(registry, Hairs.Beard.FANCY_MUSTACHE_ORNAMENTED, CharacterPatternTypes.BEARD, true);
        register(registry, Hairs.Beard.DUAL_ORNAMENTED, CharacterPatternTypes.BEARD, true);
        register(registry, Hairs.Beard.LARGE, CharacterPatternTypes.BEARD, true);
        register(registry, Hairs.Beard.LONG_BRAIDS_ORNAMENTED, CharacterPatternTypes.BEARD, true);
        register(registry, Hairs.Beard.SHORT, CharacterPatternTypes.BEARD, false);
        register(registry, Hairs.Beard.SINGLE, CharacterPatternTypes.BEARD, true);
        register(registry, Hairs.Beard.UNCLEAN_ORNAMENTED, CharacterPatternTypes.BEARD, true);
        register(registry, Hairs.Beard.VERY_BROAD, CharacterPatternTypes.BEARD, true);
        register(registry, Hairs.Beard.VERY_LARGE_MUSTACHE, CharacterPatternTypes.BEARD, true);
        register(registry, Hairs.Beard.VERY_LONG, CharacterPatternTypes.BEARD, true);
        register(registry, Hairs.Beard.SEMI_LONG, CharacterPatternTypes.BEARD, true);
    }

    private static void register(Registerable<CharacterTexturePattern> registry, RegistryKey<CharacterTexturePattern> key, CharacterPatternTypes type) {
        CharacterTexturePattern pattern = new CharacterTexturePattern(key.getValue(), type, false);
        register(registry, key, pattern,  getKey(type));
    }
    private static void register(Registerable<CharacterTexturePattern> registry, RegistryKey<CharacterTexturePattern> key, CharacterPatternTypes type, boolean hasAddon) {
        CharacterTexturePattern pattern = new CharacterTexturePattern(key.getValue(), type, hasAddon);
        register(registry, key, pattern,  getKey(type));
    }

    private static RegistryKey<CharacterTexturePattern> of(String id, CharacterPatternTypes type) {
        return RegistryKey.of(getKey(type), IdentifierUtil.build(id));
    }

    private static void register(Registerable<CharacterTexturePattern> registerable, RegistryKey<CharacterTexturePattern> registryKey, CharacterTexturePattern content, RegistryKey<Registry<CharacterTexturePattern>> registryRegistryKey) {
        String name = registryKey.getValue().getPath();
        RegistryKey<CharacterTexturePattern> key = RegistryKey.of(registryRegistryKey,Identifier.of(MiddleEarth.MOD_ID,name));
        registerable.register(key, content);
    }

    public static RegistryKey<Registry<CharacterTexturePattern>> getKey(CharacterPatternTypes type){
        return switch (type) {
            case CharacterPatternTypes.BODY, CharacterPatternTypes.FEET, CharacterPatternTypes.HEAD, CharacterPatternTypes.SCAR, CharacterPatternTypes.EAR, CharacterPatternTypes.NOSE -> DynamicRegistriesME.SKIN_PATTERN;
            case CharacterPatternTypes.EYE -> DynamicRegistriesME.EYE_PATTERN;
            case CharacterPatternTypes.HAIR, CharacterPatternTypes.EYEBROW, CharacterPatternTypes.BEARD -> DynamicRegistriesME.HAIR_PATTERN;
        };
    }

    public static Optional<RegistryEntry.Reference<CharacterTexturePattern>> get(RegistryWrapper.WrapperLookup registries, CharacterPatternTypes type, Identifier id) {
        return registries.getOrThrow(getKey(type)).streamEntries().filter((pattern) ->
                pattern.value().getPatternType() == type && pattern.matchesId(id)).findFirst();
    }

}
