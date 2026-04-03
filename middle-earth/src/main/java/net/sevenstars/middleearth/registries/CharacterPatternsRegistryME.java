package net.sevenstars.middleearth.registries;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.texture_presets.entities.CharacterTexturePattern;
import net.sevenstars.middleearth.resources.datas.common.NpcTextureType;
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
            public final static RegistryKey<CharacterTexturePattern> FAT                          = of("body_fat",  NpcTextureType.BODY);
            public final static RegistryKey<CharacterTexturePattern> FEMALE                       = of("body_female",  NpcTextureType.BODY);
            public final static RegistryKey<CharacterTexturePattern> MUSCULAR                     = of("body_muscular",  NpcTextureType.BODY);
            public final static RegistryKey<CharacterTexturePattern> SKIN_TO_BONE                 = of("body_skin_to_bone",  NpcTextureType.BODY);
            public final static RegistryKey<CharacterTexturePattern> SLIM                         = of("body_slim",  NpcTextureType.BODY);
        }

        public record Feet(){
            public final static RegistryKey<CharacterTexturePattern> NORMAL                       = of("feet_normal", NpcTextureType.FEET);
        }

        public record Head(){
            ///  Eyes Level : 4 <br> Type : Normal
            public final static RegistryKey<CharacterTexturePattern> FEMALE                       = of("head_female", NpcTextureType.HEAD);
            ///  Eyes Level : 4 <br> Type : Normal
            public final static RegistryKey<CharacterTexturePattern> MALE                         = of("head_male",  NpcTextureType.HEAD);
            ///  Eyes Level : 4 <br> Type : Normal, Wide
            public final static RegistryKey<CharacterTexturePattern> GOBLIN                       = of("head_goblin",  NpcTextureType.HEAD);
            ///  Eyes Level : 4 <br> Type : Normal, Wide
            public final static RegistryKey<CharacterTexturePattern> GOBLIN_SMALL                 = of("head_goblin_small",  NpcTextureType.HEAD);
            ///  Eyes Level : 4 <br> Type : Normal, Small, Wide
            public final static RegistryKey<CharacterTexturePattern> GOBLIN_SMALL_THICK_BROW      = of("head_goblin_thick_brow",  NpcTextureType.HEAD);
            ///  Eyes Level : 4 <br> Type : Normal, Small, Wide
            public final static RegistryKey<CharacterTexturePattern> GOBLIN_SMALL_VERY_WIDE       = of("head_goblin_very_wide",  NpcTextureType.HEAD);
            ///  Eyes Level : 4 <br> Type : Normal, Small, Wide
            public final static RegistryKey<CharacterTexturePattern> GOBLIN_SMALL_WISE            = of("head_goblin_wise",  NpcTextureType.HEAD);
            ///  Eyes Level : 5 <br> Type : Normal, Wide
            public final static RegistryKey<CharacterTexturePattern> URUK_DUMB                    = of("head_uruk_dumb",  NpcTextureType.HEAD);
            ///  Eyes Level : 6 <br> Type : Wide
            public final static RegistryKey<CharacterTexturePattern> URUK_TALL_DUMB               = of("head_uruk_tall_dumb",  NpcTextureType.HEAD);
        }

        public record Ear(){
            public final static RegistryKey<CharacterTexturePattern> LARGE_POINTY                 = of("ear_large_pointy",  NpcTextureType.EAR);
            public final static RegistryKey<CharacterTexturePattern> NORMAL                       = of("ear_normal",  NpcTextureType.EAR);
            public final static RegistryKey<CharacterTexturePattern> POINTY                       = of("ear_pointy",  NpcTextureType.EAR);
            public final static RegistryKey<CharacterTexturePattern> SMALL                        = of("ear_small",  NpcTextureType.EAR);
            public final static RegistryKey<CharacterTexturePattern> SMALL_POINTY                 = of("ear_small_pointy",  NpcTextureType.EAR);
            public final static RegistryKey<CharacterTexturePattern> SQUARE                       = of("ear_square",  NpcTextureType.EAR);
            public final static RegistryKey<CharacterTexturePattern> SQUARE_POINTY                = of("ear_square_pointy",  NpcTextureType.EAR);
            public final static RegistryKey<CharacterTexturePattern> WIDE_POINTY                  = of("ear_wide_pointy",  NpcTextureType.EAR);
        }

        public record Nose(){
            public final static RegistryKey<CharacterTexturePattern> CUBE                         = of("nose_cube", NpcTextureType.NOSE);
            public final static RegistryKey<CharacterTexturePattern> VILLAGER                     = of("nose_villager", NpcTextureType.NOSE);
            public final static RegistryKey<CharacterTexturePattern> LARGE_CUBE_CENTER            = of("nose_large_center", NpcTextureType.NOSE);
            public final static RegistryKey<CharacterTexturePattern> LARGE_CUBE_HIGH              = of("nose_large_high", NpcTextureType.NOSE);
        }

        public record Scar(){
            public final static RegistryKey<CharacterTexturePattern> EYE_RIGHT                    = of("scar_eye_right", NpcTextureType.SCAR);
            public final static RegistryKey<CharacterTexturePattern> EYE_LEFT                     = of("scar_eye_left", NpcTextureType.SCAR);
        }
    }

    public record Eyes() {
        public record Eye() {
            ///  Eyes Level : 4 <br> Type : Normal
            public final static RegistryKey<CharacterTexturePattern> BLIND_LEFT                   = of("eye_blind_left", NpcTextureType.EYE);
            ///  Eyes Level : 4 <br> Type : Normal
            public final static RegistryKey<CharacterTexturePattern> BLIND_RIGHT                  = of("eye_blind_right", NpcTextureType.EYE);
            ///  Eyes Level : 4 <br> Type : Normal
            public final static RegistryKey<CharacterTexturePattern> COMMON                       = of("eye_common", NpcTextureType.EYE);
            ///  Eyes Level : 5 <br> Type : Normal
            public final static RegistryKey<CharacterTexturePattern> COMMON_HIGH                  = of("eye_common_high", NpcTextureType.EYE);
            ///  Eyes Level : 4 <br> Type : Small (Narrow)
            public final static RegistryKey<CharacterTexturePattern> SMALL                        = of("eye_small", NpcTextureType.EYE);
            ///  Eyes Level : 5 <br> Type : Small (Wide)
            public final static RegistryKey<CharacterTexturePattern> SMALL_HIGH_WIDE              = of("eye_small_high_wide", NpcTextureType.EYE);
            ///  Eyes Level : 6 <br> Type : Small (Wide)
            public final static RegistryKey<CharacterTexturePattern> SMALL_VERY_HIGH_WIDE         = of("eye_small_very_high_wide", NpcTextureType.EYE);
            ///  Eyes Level : 4 <br> Type : Small (Wide)
            public final static RegistryKey<CharacterTexturePattern> SMALL_WIDE                   = of("eye_small_wide", NpcTextureType.EYE);
        }
    }

    public record Hairs() {
        public class Hair {
            public final static RegistryKey<CharacterTexturePattern> BALD_DREADLOCKS_ORNAMENTED   = of("hair_bald_dreadlocks_ornamented", NpcTextureType.HAIR);
            public final static RegistryKey<CharacterTexturePattern> BALD_SIDES                   = of("hair_bald_sides", NpcTextureType.HAIR);
            public final static RegistryKey<CharacterTexturePattern> BALD_SMALL_DREADLOCKS        = of("hair_bald_small_dreadlocks", NpcTextureType.HAIR);
            public final static RegistryKey<CharacterTexturePattern> SIDE_BALDING                 = of("hair_side_balding", NpcTextureType.HAIR);
            public final static RegistryKey<CharacterTexturePattern> TOP_BALDING                  = of("hair_top_balding", NpcTextureType.HAIR);
            public final static RegistryKey<CharacterTexturePattern> BOWL                         = of("hair_bowl", NpcTextureType.HAIR);
            public final static RegistryKey<CharacterTexturePattern> DIRTY_MOP                    = of("hair_dirty_mop", NpcTextureType.HAIR);
            public final static RegistryKey<CharacterTexturePattern> FLAT_LONG                    = of("hair_flat_long", NpcTextureType.HAIR);
            public final static RegistryKey<CharacterTexturePattern> LONG                         = of("hair_long", NpcTextureType.HAIR);
            public final static RegistryKey<CharacterTexturePattern> PONYTAIL_SHORT_ORNAMENTED    = of("hair_ponytail_short_ornamented", NpcTextureType.HAIR);
            public final static RegistryKey<CharacterTexturePattern> SHARP                        = of("hair_sharp", NpcTextureType.HAIR);
            public final static RegistryKey<CharacterTexturePattern> SHORT                        = of("hair_short", NpcTextureType.HAIR);
            public final static RegistryKey<CharacterTexturePattern> UNCUT                        = of("hair_uncut", NpcTextureType.HAIR);
            public final static RegistryKey<CharacterTexturePattern> VERY_LONG                    = of("hair_very_long", NpcTextureType.HAIR);
            public final static RegistryKey<CharacterTexturePattern> SEMI_LONG                    = of("hair_semi_long", NpcTextureType.HAIR);
        }

        public record Beard() {
            public final static RegistryKey<CharacterTexturePattern> BROAD                        = of("beard_broad", NpcTextureType.BEARD);
            public final static RegistryKey<CharacterTexturePattern> CHUNKY_BRAIDS                = of("beard_chunky_braids", NpcTextureType.BEARD);
            public final static RegistryKey<CharacterTexturePattern> CLEAN                        = of("beard_clean", NpcTextureType.BEARD);
            public final static RegistryKey<CharacterTexturePattern> DUAL_LARGE_ORNAMENTED        = of("beard_dual_large_ornamented", NpcTextureType.BEARD);
            public final static RegistryKey<CharacterTexturePattern> DUAL_ORNAMENTED              = of("beard_dual_ornamented", NpcTextureType.BEARD);
            public final static RegistryKey<CharacterTexturePattern> FANCY_MUSTACHE_ORNAMENTED    = of("beard_fancy_mustache_ornamented", NpcTextureType.BEARD);
            public final static RegistryKey<CharacterTexturePattern> LARGE                        = of("beard_large", NpcTextureType.BEARD);
            public final static RegistryKey<CharacterTexturePattern> LONG_BRAIDS_ORNAMENTED       = of("beard_long_braids_ornamented", NpcTextureType.BEARD);
            public final static RegistryKey<CharacterTexturePattern> LONG_SINGLE_ORNAMENTED       = of("beard_long_single_ornamented", NpcTextureType.BEARD);
            public final static RegistryKey<CharacterTexturePattern> SHORT                        = of("beard_short", NpcTextureType.BEARD);
            public final static RegistryKey<CharacterTexturePattern> SINGLE                       = of("beard_single", NpcTextureType.BEARD);
            public final static RegistryKey<CharacterTexturePattern> UNCLEAN_ORNAMENTED           = of("beard_unclean_ornamented", NpcTextureType.BEARD);
            public final static RegistryKey<CharacterTexturePattern> VERY_BROAD                   = of("beard_very_broad", NpcTextureType.BEARD);
            public final static RegistryKey<CharacterTexturePattern> VERY_LARGE_MUSTACHE          = of("beard_very_large_mustache", NpcTextureType.BEARD);
            public final static RegistryKey<CharacterTexturePattern> VERY_LONG                    = of("beard_very_long", NpcTextureType.BEARD);
            public final static RegistryKey<CharacterTexturePattern> SEMI_LONG                    = of("beard_semi_long", NpcTextureType.BEARD);
        }

        public record Eyebrow() {
            public final static RegistryKey<CharacterTexturePattern> BASIC                        = of("eyebrow_basic", NpcTextureType.EYEBROW);
            public final static RegistryKey<CharacterTexturePattern> LONG                         = of("eyebrow_long", NpcTextureType.EYEBROW);
            public final static RegistryKey<CharacterTexturePattern> SHORT                        = of("eyebrow_short", NpcTextureType.EYEBROW);
            public final static RegistryKey<CharacterTexturePattern> UNI                          = of("eyebrow_uni", NpcTextureType.EYEBROW);
            public final static RegistryKey<CharacterTexturePattern> THICK                        = of("eyebrow_thick", NpcTextureType.EYEBROW);
        }
    }

    public static void bootstrapSkins(Registerable<CharacterTexturePattern> registry) {
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

    public static void bootstrapEyes(Registerable<CharacterTexturePattern> registry) {
        register(registry, Eyes.Eye.BLIND_LEFT, NpcTextureType.EYE);
        register(registry, Eyes.Eye.BLIND_RIGHT, NpcTextureType.EYE);
        register(registry, Eyes.Eye.COMMON, NpcTextureType.EYE);
        register(registry, Eyes.Eye.COMMON_HIGH, NpcTextureType.EYE);
        register(registry, Eyes.Eye.SMALL, NpcTextureType.EYE);
        register(registry, Eyes.Eye.SMALL_HIGH_WIDE, NpcTextureType.EYE);
        register(registry, Eyes.Eye.SMALL_VERY_HIGH_WIDE, NpcTextureType.EYE);
        register(registry, Eyes.Eye.SMALL_WIDE, NpcTextureType.EYE);
    }

    public static void bootstrapHairs(Registerable<CharacterTexturePattern> registry) {
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

    private static void register(Registerable<CharacterTexturePattern> registry, RegistryKey<CharacterTexturePattern> key, NpcTextureType type) {
        CharacterTexturePattern pattern = new CharacterTexturePattern(key.getValue(), type, false);
        register(registry, key, pattern,  getKey(type));
    }
    private static void register(Registerable<CharacterTexturePattern> registry, RegistryKey<CharacterTexturePattern> key, NpcTextureType type, boolean hasAddon) {
        CharacterTexturePattern pattern = new CharacterTexturePattern(key.getValue(), type, hasAddon);
        register(registry, key, pattern,  getKey(type));
    }

    private static RegistryKey<CharacterTexturePattern> of(String id, NpcTextureType type) {
        return RegistryKey.of(getKey(type), IdentifierUtil.build(id));
    }

    private static void register(Registerable<CharacterTexturePattern> registerable, RegistryKey<CharacterTexturePattern> registryKey, CharacterTexturePattern content, RegistryKey<Registry<CharacterTexturePattern>> registryRegistryKey) {
        String name = registryKey.getValue().getPath();
        RegistryKey<CharacterTexturePattern> key = RegistryKey.of(registryRegistryKey,Identifier.of(MiddleEarth.MOD_ID,name));
        registerable.register(key, content);
    }

    public static RegistryKey<Registry<CharacterTexturePattern>> getKey(NpcTextureType category){
        return switch (category) {
            case NpcTextureType.SKIN, NpcTextureType.BODY, NpcTextureType.FEET, NpcTextureType.HEAD, NpcTextureType.SCAR, NpcTextureType.EAR, NpcTextureType.NOSE -> DynamicRegistriesME.SKIN_PATTERN;
            case NpcTextureType.EYE -> DynamicRegistriesME.EYE_PATTERN;
            case NpcTextureType.HAIR, NpcTextureType.EYEBROW, NpcTextureType.BEARD -> DynamicRegistriesME.HAIR_PATTERN;
            case CLOTHE_PRESETS -> null;
        };
    }

    public static Optional<RegistryEntry.Reference<CharacterTexturePattern>> get(RegistryWrapper.WrapperLookup registries, NpcTextureType type, Identifier id) {
        return registries.getOrThrow(getKey(type)).streamEntries().filter((pattern) ->
                pattern.value().getCategory() == type && pattern.matchesId(id)).findFirst();
    }

}
