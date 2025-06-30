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
public class NpcTexturePatternsME {
    public record Keys(){
        public static final RegistryKey<Registry<NpcTexturePattern>> SKIN_KEY = ofRegistry("npc_skin_pattern");
        public static final RegistryKey<Registry<NpcTexturePattern>> EYE_KEY = ofRegistry("npc_eye_pattern");
        public static final RegistryKey<Registry<NpcTexturePattern>> HAIR_KEY = ofRegistry("npc_hair_pattern");
        public static final RegistryKey<Registry<NpcTexturePattern>> CLOTHING_KEY = ofRegistry("npc_clothing_pattern");
    }

    public record Body(){
        public final static RegistryKey<NpcTexturePattern> FEMALE = of("body_female",  NpcTextureType.BODY);
        public final static RegistryKey<NpcTexturePattern> MUSCULAR = of("body_muscular", NpcTextureType.BODY);
        public final static RegistryKey<NpcTexturePattern> SLIM = of("body_slim",  NpcTextureType.BODY);
    }
    public record Head(){
        public final static RegistryKey<NpcTexturePattern> FEMALE = of("head_female", NpcTextureType.HEAD);
        public final static RegistryKey<NpcTexturePattern> GOBLIN = of("head_goblin",  NpcTextureType.HEAD);
        public final static RegistryKey<NpcTexturePattern> GOBLIN_SMALL = of("head_goblin_small",  NpcTextureType.HEAD);
        public final static RegistryKey<NpcTexturePattern> GOBLIN_SMALL_THICK_BROW = of("head_goblin_thick_brow",  NpcTextureType.HEAD);
        public final static RegistryKey<NpcTexturePattern> GOBLIN_SMALL_VERY_WIDE = of("head_goblin_very_wide",  NpcTextureType.HEAD);
        public final static RegistryKey<NpcTexturePattern> GOBLIN_SMALL_WISE = of("head_goblin_wise",  NpcTextureType.HEAD);
        public final static RegistryKey<NpcTexturePattern> MALE = of("head_male",  NpcTextureType.HEAD);
        public final static RegistryKey<NpcTexturePattern> URUK_DUMB = of("head_uruk_dumb",  NpcTextureType.HEAD);
        public final static RegistryKey<NpcTexturePattern> URUK_TALL_DUMB = of("head_uruk_tall_dumb",  NpcTextureType.HEAD);
    }
    public record Ear(){
        public final static RegistryKey<NpcTexturePattern> CUBE = of("ear_cube", NpcTextureType.EAR);
        public final static RegistryKey<NpcTexturePattern> ANGLED_POINTY = of("ear_pointy",  NpcTextureType.EAR);
        public final static RegistryKey<NpcTexturePattern> FLAT_POINTY = of("ear_flat_pointy",  NpcTextureType.EAR);
        public final static RegistryKey<NpcTexturePattern> FLAT_ROUND = of("ear_flat_round",  NpcTextureType.EAR);
        public final static RegistryKey<NpcTexturePattern> FLAT_SMALL = of("ear_flat_small",  NpcTextureType.EAR);
    }
    public record Nose(){
        public final static RegistryKey<NpcTexturePattern> CUBE = of("nose_cube", NpcTextureType.NOSE);
        public final static RegistryKey<NpcTexturePattern> VILLAGER = of("nose_villager", NpcTextureType.NOSE);
        public final static RegistryKey<NpcTexturePattern> LARGE_CUBE_CENTER = of("nose_large_center", NpcTextureType.NOSE);
        public final static RegistryKey<NpcTexturePattern> LARGE_CUBE_HIGH = of("nose_large_high", NpcTextureType.NOSE);
    }

    public record Scar(){
        public final static RegistryKey<NpcTexturePattern> EYE_RIGHT = of("scar_eye_right", NpcTextureType.SCAR);
    }

    public record Eye() {
        public final static RegistryKey<NpcTexturePattern> COMMON = of("eye_common", NpcTextureType.EYE);
        public final static RegistryKey<NpcTexturePattern> SMALL = of("eye_small", NpcTextureType.EYE);
        public final static RegistryKey<NpcTexturePattern> SMALL_HIGH_WIDE = of("eye_small_high_wide", NpcTextureType.EYE);
        public final static RegistryKey<NpcTexturePattern> SMALL_LOW_WIDE = of("eye_small_low_wide", NpcTextureType.EYE);
        public final static RegistryKey<NpcTexturePattern> SMALL_WIDE = of("eye_small_wide", NpcTextureType.EYE);
    }

    public record Hair() {
        public final static RegistryKey<NpcTexturePattern> BALD_DREADLOCKS_ORNAMENTED = of("hair_bald_dreadlocks_ornamented", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> BALD_SIDES = of("hair_bald_sides", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> BALD_SMALL_DREADLOCKS = of("hair_bald_small_dreadlocks", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> BOWL = of("hair_bowl", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> DIRTY_MOP = of("hair_dirty_mop", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> FLAT_LONG = of("hair_flat_long", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> LONG = of("hair_long", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> PONYTAIL_SHORT_ORNAMENTED = of("hair_ponytail_short_ornamented", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> SHARP = of("hair_sharp", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> SHORT = of("hair_short", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> SIDE_BALDING = of("hair_side_balding", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> TOP_BALDING = of("hair_top_balding", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTexturePattern> UNCUT = of("hair_uncut", NpcTextureType.HAIR);
    }

    public record Beard() {
        public final static RegistryKey<NpcTexturePattern> CLEAN = of("beard_clean", NpcTextureType.BEARD);
        public final static RegistryKey<NpcTexturePattern> DUAL_ORNAMENTED = of("beard_dual_ornamented", NpcTextureType.BEARD);
        public final static RegistryKey<NpcTexturePattern> LARGE = of("beard_large", NpcTextureType.BEARD);
        public final static RegistryKey<NpcTexturePattern> LONG_SINGLE_ORNAMENTED = of("beard_long_single_ornamented", NpcTextureType.BEARD);
        public final static RegistryKey<NpcTexturePattern> SHORT = of("beard_short", NpcTextureType.BEARD);
        public final static RegistryKey<NpcTexturePattern> SINGLE = of("beard_single", NpcTextureType.BEARD);
        public final static RegistryKey<NpcTexturePattern> UNCLEAN_ORNAMENTED = of("beard_unclean_ornamented", NpcTextureType.BEARD);
    }

    public record Eyebrow() {
        public final static RegistryKey<NpcTexturePattern> BASIC = of("eyebrow_basic", NpcTextureType.EYEBROW);
        public final static RegistryKey<NpcTexturePattern> LONG = of("eyebrow_long", NpcTextureType.EYEBROW);
        public final static RegistryKey<NpcTexturePattern> SHORT = of("eyebrow_short", NpcTextureType.EYEBROW);
        public final static RegistryKey<NpcTexturePattern> UNI = of("eyebrow_uni", NpcTextureType.EYEBROW);
    }

    public record Clothing(){
        public final static RegistryKey<NpcTexturePattern> FULL_TOGA = of("clothing_full_toga", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTexturePattern> ROBE = of("clothing_robe", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTexturePattern> SKIRT = of("clothing_skirt", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTexturePattern> SKIRT_WITH_STROPHIUM = of("clothing_skirt_with_strophium", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTexturePattern> TOGA = of("clothing_toga", NpcTextureType.CLOTHING);
    }

    public static void bootstrapSkins(Registerable<NpcTexturePattern> registry) {
        register(registry, Body.FEMALE, NpcTextureType.SKIN);
        register(registry, Body.MUSCULAR, NpcTextureType.SKIN);
        register(registry, Body.SLIM, NpcTextureType.SKIN);

        register(registry, Head.FEMALE, NpcTextureType.HEAD);
        register(registry, Head.GOBLIN, NpcTextureType.HEAD);
        register(registry, Head.GOBLIN_SMALL, NpcTextureType.HEAD);
        register(registry, Head.GOBLIN_SMALL_THICK_BROW, NpcTextureType.HEAD);
        register(registry, Head.GOBLIN_SMALL_VERY_WIDE, NpcTextureType.HEAD);
        register(registry, Head.GOBLIN_SMALL_WISE, NpcTextureType.HEAD);
        register(registry, Head.MALE, NpcTextureType.HEAD);
        register(registry, Head.URUK_DUMB, NpcTextureType.HEAD);
        register(registry, Head.URUK_TALL_DUMB, NpcTextureType.HEAD);

        register(registry, Ear.CUBE, NpcTextureType.EAR);
        register(registry, Ear.ANGLED_POINTY, NpcTextureType.EAR);
        register(registry, Ear.FLAT_POINTY, NpcTextureType.EAR);
        register(registry, Ear.FLAT_ROUND, NpcTextureType.EAR);
        register(registry, Ear.FLAT_SMALL, NpcTextureType.EAR);

        register(registry, Nose.CUBE, NpcTextureType.NOSE);
        register(registry, Nose.VILLAGER, NpcTextureType.NOSE);
        register(registry, Nose.LARGE_CUBE_CENTER, NpcTextureType.NOSE);
        register(registry, Nose.LARGE_CUBE_HIGH, NpcTextureType.NOSE);

        register(registry, Scar.EYE_RIGHT, NpcTextureType.SCAR);
    }

    public static void bootstrapEyes(Registerable<NpcTexturePattern> registry) {
        register(registry, Eye.COMMON, NpcTextureType.EYE);
        register(registry, Eye.SMALL, NpcTextureType.EYE);
        register(registry, Eye.SMALL_HIGH_WIDE, NpcTextureType.EYE);
        register(registry, Eye.SMALL_LOW_WIDE, NpcTextureType.EYE);
        register(registry, Eye.SMALL_WIDE, NpcTextureType.EYE);
    }

    public static void bootstrapHairs(Registerable<NpcTexturePattern> registry) {
        register(registry, Hair.BALD_DREADLOCKS_ORNAMENTED, NpcTextureType.HAIR, true);
        register(registry, Hair.BALD_SIDES, NpcTextureType.HAIR);
        register(registry, Hair.BALD_SMALL_DREADLOCKS, NpcTextureType.HAIR, true);
        register(registry, Hair.BOWL, NpcTextureType.HAIR);
        register(registry, Hair.DIRTY_MOP, NpcTextureType.HAIR, true);
        register(registry, Hair.FLAT_LONG, NpcTextureType.HAIR, true);
        register(registry, Hair.LONG, NpcTextureType.HAIR, true);
        register(registry, Hair.PONYTAIL_SHORT_ORNAMENTED, NpcTextureType.HAIR);
        register(registry, Hair.SHARP, NpcTextureType.HAIR);
        register(registry, Hair.SHORT, NpcTextureType.HAIR);
        register(registry, Hair.SIDE_BALDING, NpcTextureType.HAIR);
        register(registry, Hair.TOP_BALDING, NpcTextureType.HAIR,true);
        register(registry, Hair.UNCUT, NpcTextureType.HAIR);

        register(registry, Eyebrow.BASIC, NpcTextureType.EYEBROW);
        register(registry, Eyebrow.LONG, NpcTextureType.EYEBROW);
        register(registry, Eyebrow.SHORT, NpcTextureType.EYEBROW);
        register(registry, Eyebrow.UNI, NpcTextureType.EYEBROW);

        register(registry, Beard.CLEAN, NpcTextureType.BEARD, true);
        register(registry, Beard.DUAL_ORNAMENTED, NpcTextureType.BEARD, true);
        register(registry, Beard.LARGE, NpcTextureType.BEARD, true);
        register(registry, Beard.LONG_SINGLE_ORNAMENTED, NpcTextureType.BEARD, true);
        register(registry, Beard.SHORT, NpcTextureType.BEARD);
        register(registry, Beard.SINGLE, NpcTextureType.BEARD, true);
        register(registry, Beard.UNCLEAN_ORNAMENTED, NpcTextureType.BEARD, true);
    }
    public static void bootstrapClothings(Registerable<NpcTexturePattern> registry) {
        register(registry, Clothing.FULL_TOGA, NpcTextureType.CLOTHING);
        register(registry, Clothing.ROBE, NpcTextureType.CLOTHING);
        register(registry, Clothing.SKIRT, NpcTextureType.CLOTHING);
        register(registry, Clothing.SKIRT_WITH_STROPHIUM, NpcTextureType.CLOTHING);
        register(registry, Clothing.TOGA, NpcTextureType.CLOTHING);
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
