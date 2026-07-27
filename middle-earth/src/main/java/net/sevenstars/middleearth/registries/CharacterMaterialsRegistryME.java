package net.sevenstars.middleearth.registries;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.texture_presets.CharacterTextureMaterial;

/**
 * Middle-earth mod npc texture materials registry<br>
 * Used to register the different palette colors<br>
 * <b><u>Datadriven content, do not use during runtime!</u></b>
 * <hr>
 */
public class CharacterMaterialsRegistryME {
    public record Skin(){
        public final static ResourceKey<CharacterTextureMaterial> BEIGE = of("beige", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> BROWN = of("brown", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> DARK_BEIGE = of("dark_beige", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> DARK_GREEN = of("dark_green", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> DARK_PINK = of("dark_pink", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> GREEN = of("green", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> GREY = of("grey", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> LIGHT_GREY = of("light_grey", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> MURKY_GREEN = of("murky_green", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> OLIVE = of("olive", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> PALE = of("pale", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> PALE_GREEN = of("pale_green", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> PALE_WHITE = of("pale_white", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> PINK = of("pink", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> RED = of("red", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> RUST = of("rust", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> SANDY_GREEN = of("sandy_green", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> SLIGHT_BROWN = of("slight_brown", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> SWAMPY_GREEN = of("swampy_green", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> TAN = of("tan", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> TAN_DESATURATED = of("tan_desaturated", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> WINE = of("wine", CharacterMaterialTypes.SKIN);
        public final static ResourceKey<CharacterTextureMaterial> YELLOWISH_GREEN = of("yellowish_green", CharacterMaterialTypes.SKIN);
    }

    public record Eye() {
        public final static ResourceKey<CharacterTextureMaterial> BLACK = of("black", CharacterMaterialTypes.EYE);
        public final static ResourceKey<CharacterTextureMaterial> BLUE = of("blue", CharacterMaterialTypes.EYE);
        public final static ResourceKey<CharacterTextureMaterial> BROWN = of("brown", CharacterMaterialTypes.EYE);
        public final static ResourceKey<CharacterTextureMaterial> DARK_GREEN = of("dark_green", CharacterMaterialTypes.EYE);
        public final static ResourceKey<CharacterTextureMaterial> DEEP_BLUE = of("deep_blue", CharacterMaterialTypes.EYE);
        public final static ResourceKey<CharacterTextureMaterial> GREEN = of("green", CharacterMaterialTypes.EYE);
        public final static ResourceKey<CharacterTextureMaterial> ICE = of("ice", CharacterMaterialTypes.EYE);
        public final static ResourceKey<CharacterTextureMaterial> NAVY = of("navy", CharacterMaterialTypes.EYE);
        public final static ResourceKey<CharacterTextureMaterial> PALE = of("pale", CharacterMaterialTypes.EYE);
        public final static ResourceKey<CharacterTextureMaterial> RED = of("red", CharacterMaterialTypes.EYE);
        public final static ResourceKey<CharacterTextureMaterial> YELLOW = of("yellow", CharacterMaterialTypes.EYE);
    }
    public record Hair() {
        public final static ResourceKey<CharacterTextureMaterial> BLACK_ALMANDINE = of("black_and_almandine", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> BLACK_BEADS = of("black_and_beads", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> BLACK_COPPER = of("black_and_copper", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> BLACK_GOLD = of("black_and_gold", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> BLACK_SILVER = of("black_and_silver", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> COLD_BLACK_ALMANDINE = of("cold_black_and_almandine", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> COLD_BLACK_BEADS = of("cold_black_and_beads", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> COLD_BLACK_COPPER = of("cold_black_and_copper", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> COLD_BLACK_GOLD = of("cold_black_and_gold", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> COLD_BLACK_SILVER = of("cold_black_and_silver", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> BROWN_ALMANDINE = of("brown_and_almandine", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> BROWN_BEADS = of("brown_and_beads", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> BROWN_COPPER = of("brown_and_copper", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> BROWN_GOLD = of("brown_and_gold", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> BROWN_SILVER = of("brown_and_silver", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> DARK_BROWN_ALMANDINE = of("dark_brown_and_almandine", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> DARK_BROWN_BEADS = of("dark_brown_and_beads", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> DARK_BROWN_COPPER = of("dark_brown_and_copper", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> DARK_BROWN_GOLD = of("dark_brown_and_gold", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> DARK_BROWN_SILVER = of("dark_brown_and_silver", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> BLONDE_ALMANDINE = of("blonde_and_almandine", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> BLONDE_BEADS = of("blonde_and_beads", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> BLONDE_COPPER = of("blonde_and_copper", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> BLONDE_GOLD = of("blonde_and_gold", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> BLONDE_SILVER = of("blonde_and_silver", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> DIRTY_BLONDE_ALMANDINE = of("dirty_blonde_and_almandine", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> DIRTY_BLONDE_BEADS = of("dirty_blonde_and_beads", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> DIRTY_BLONDE_COPPER = of("dirty_blonde_and_copper", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> DIRTY_BLONDE_GOLD = of("dirty_blonde_and_gold", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> DIRTY_BLONDE_SILVER = of("dirty_blonde_and_silver", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> DIRTY_BROWN_ALMANDINE = of("dirty_brown_and_almandine", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> DIRTY_BROWN_BEADS = of("dirty_brown_and_beads", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> DIRTY_BROWN_COPPER = of("dirty_brown_and_copper", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> DIRTY_BROWN_GOLD = of("dirty_brown_and_gold", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> DIRTY_BROWN_SILVER = of("dirty_brown_and_silver", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> GREASY_ALMANDINE = of("greasy_and_almandine", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> GREASY_BEADS = of("greasy_and_beads", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> GREASY_COPPER = of("greasy_and_copper", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> GREASY_GOLD = of("greasy_and_gold", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> GREASY_SILVER = of("greasy_and_silver", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> STRAW_ALMANDINE = of("straw_and_almandine", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> STRAW_BEADS = of("straw_and_beads", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> STRAW_COPPER = of("straw_and_copper", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> STRAW_GOLD = of("straw_and_gold", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> STRAW_SILVER = of("straw_and_silver", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> WHITE_ALMANDINE = of("white_and_almandine", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> WHITE_BEADS = of("white_and_beads", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> WHITE_COPPER = of("white_and_copper", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> WHITE_GOLD = of("white_and_gold", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> WHITE_SILVER = of("white_and_silver", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> GRAY_ALMANDINE = of("gray_and_almandine", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> GRAY_BEADS = of("gray_and_beads", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> GRAY_COPPER = of("gray_and_copper", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> GRAY_GOLD = of("gray_and_gold", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> GRAY_SILVER = of("gray_and_silver", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> GINGER_ALMANDINE = of("ginger_and_almandine", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> GINGER_BEADS = of("ginger_and_beads", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> GINGER_COPPER = of("ginger_and_copper", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> GINGER_GOLD = of("ginger_and_gold", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> GINGER_SILVER = of("ginger_and_silver", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> RED_ALMANDINE = of("red_and_almandine", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> RED_BEADS = of("red_and_beads", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> RED_COPPER = of("red_and_copper", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> RED_GOLD = of("red_and_gold", CharacterMaterialTypes.HAIR);
        public final static ResourceKey<CharacterTextureMaterial> RED_SILVER = of("red_and_silver", CharacterMaterialTypes.HAIR);
    }

    public static void bootstrapSkins(BootstrapContext<CharacterTextureMaterial> registry) {
        register(registry, Skin.BEIGE, CharacterMaterialTypes.SKIN);
        register(registry, Skin.BROWN, CharacterMaterialTypes.SKIN);
        register(registry, Skin.DARK_BEIGE, CharacterMaterialTypes.SKIN);
        register(registry, Skin.DARK_GREEN, CharacterMaterialTypes.SKIN);
        register(registry, Skin.DARK_PINK, CharacterMaterialTypes.SKIN);
        register(registry, Skin.GREEN, CharacterMaterialTypes.SKIN);
        register(registry, Skin.GREY, CharacterMaterialTypes.SKIN);
        register(registry, Skin.LIGHT_GREY, CharacterMaterialTypes.SKIN);
        register(registry, Skin.MURKY_GREEN, CharacterMaterialTypes.SKIN);
        register(registry, Skin.OLIVE, CharacterMaterialTypes.SKIN);
        register(registry, Skin.PALE, CharacterMaterialTypes.SKIN);
        register(registry, Skin.PALE_GREEN, CharacterMaterialTypes.SKIN);
        register(registry, Skin.PALE_WHITE, CharacterMaterialTypes.SKIN);
        register(registry, Skin.PINK, CharacterMaterialTypes.SKIN);
        register(registry, Skin.RED, CharacterMaterialTypes.SKIN);
        register(registry, Skin.RUST, CharacterMaterialTypes.SKIN);
        register(registry, Skin.SANDY_GREEN, CharacterMaterialTypes.SKIN);
        register(registry, Skin.SLIGHT_BROWN, CharacterMaterialTypes.SKIN);
        register(registry, Skin.SWAMPY_GREEN, CharacterMaterialTypes.SKIN);
        register(registry, Skin.TAN, CharacterMaterialTypes.SKIN);
        register(registry, Skin.TAN_DESATURATED, CharacterMaterialTypes.SKIN);
        register(registry, Skin.WINE, CharacterMaterialTypes.SKIN);
        register(registry, Skin.YELLOWISH_GREEN, CharacterMaterialTypes.SKIN);
    }

    public static void bootstrapEyes(BootstrapContext<CharacterTextureMaterial> registry) {
        register(registry, Eye.BLACK, CharacterMaterialTypes.EYE);
        register(registry, Eye.BLUE, CharacterMaterialTypes.EYE);
        register(registry, Eye.BROWN, CharacterMaterialTypes.EYE);
        register(registry, Eye.DARK_GREEN, CharacterMaterialTypes.EYE);
        register(registry, Eye.DEEP_BLUE, CharacterMaterialTypes.EYE);
        register(registry, Eye.GREEN, CharacterMaterialTypes.EYE);
        register(registry, Eye.ICE, CharacterMaterialTypes.EYE);
        register(registry, Eye.NAVY, CharacterMaterialTypes.EYE);
        register(registry, Eye.PALE, CharacterMaterialTypes.EYE);
        register(registry, Eye.RED, CharacterMaterialTypes.EYE);
        register(registry, Eye.YELLOW, CharacterMaterialTypes.EYE);
    }
    public static void bootstrapHairs(BootstrapContext<CharacterTextureMaterial> registry) {
        register(registry, Hair.BLACK_GOLD, CharacterMaterialTypes.HAIR);
        register(registry, Hair.COLD_BLACK_GOLD, CharacterMaterialTypes.HAIR);
        register(registry, Hair.BROWN_GOLD, CharacterMaterialTypes.HAIR);
        register(registry, Hair.DARK_BROWN_GOLD, CharacterMaterialTypes.HAIR);
        register(registry, Hair.BLONDE_GOLD, CharacterMaterialTypes.HAIR);
        register(registry, Hair.DIRTY_BLONDE_GOLD, CharacterMaterialTypes.HAIR);
        register(registry, Hair.DIRTY_BROWN_GOLD, CharacterMaterialTypes.HAIR);
        register(registry, Hair.GREASY_GOLD, CharacterMaterialTypes.HAIR);
        register(registry, Hair.STRAW_GOLD, CharacterMaterialTypes.HAIR);
        register(registry, Hair.GINGER_GOLD, CharacterMaterialTypes.HAIR);
        register(registry, Hair.RED_GOLD, CharacterMaterialTypes.HAIR);
        register(registry, Hair.WHITE_GOLD, CharacterMaterialTypes.HAIR);
        register(registry, Hair.GRAY_GOLD, CharacterMaterialTypes.HAIR);

        register(registry, Hair.BLACK_BEADS, CharacterMaterialTypes.HAIR);
        register(registry, Hair.COLD_BLACK_BEADS, CharacterMaterialTypes.HAIR);
        register(registry, Hair.BROWN_BEADS, CharacterMaterialTypes.HAIR);
        register(registry, Hair.DARK_BROWN_BEADS, CharacterMaterialTypes.HAIR);
        register(registry, Hair.BLONDE_BEADS, CharacterMaterialTypes.HAIR);
        register(registry, Hair.DIRTY_BLONDE_BEADS, CharacterMaterialTypes.HAIR);
        register(registry, Hair.DIRTY_BROWN_BEADS, CharacterMaterialTypes.HAIR);
        register(registry, Hair.GREASY_BEADS, CharacterMaterialTypes.HAIR);
        register(registry, Hair.STRAW_BEADS, CharacterMaterialTypes.HAIR);
        register(registry, Hair.GINGER_BEADS, CharacterMaterialTypes.HAIR);
        register(registry, Hair.RED_BEADS, CharacterMaterialTypes.HAIR);
        register(registry, Hair.WHITE_BEADS, CharacterMaterialTypes.HAIR);
        register(registry, Hair.GRAY_BEADS, CharacterMaterialTypes.HAIR);

        register(registry, Hair.BLACK_COPPER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.COLD_BLACK_COPPER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.BROWN_COPPER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.DARK_BROWN_COPPER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.BLONDE_COPPER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.DIRTY_BLONDE_COPPER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.DIRTY_BROWN_COPPER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.GREASY_COPPER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.STRAW_COPPER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.GINGER_COPPER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.RED_COPPER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.WHITE_COPPER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.GRAY_COPPER, CharacterMaterialTypes.HAIR);

        register(registry, Hair.BLACK_ALMANDINE, CharacterMaterialTypes.HAIR);
        register(registry, Hair.COLD_BLACK_ALMANDINE, CharacterMaterialTypes.HAIR);
        register(registry, Hair.BROWN_ALMANDINE, CharacterMaterialTypes.HAIR);
        register(registry, Hair.DARK_BROWN_ALMANDINE, CharacterMaterialTypes.HAIR);
        register(registry, Hair.BLONDE_ALMANDINE, CharacterMaterialTypes.HAIR);
        register(registry, Hair.DIRTY_BLONDE_ALMANDINE, CharacterMaterialTypes.HAIR);
        register(registry, Hair.DIRTY_BROWN_ALMANDINE, CharacterMaterialTypes.HAIR);
        register(registry, Hair.GREASY_ALMANDINE, CharacterMaterialTypes.HAIR);
        register(registry, Hair.STRAW_ALMANDINE, CharacterMaterialTypes.HAIR);
        register(registry, Hair.GINGER_ALMANDINE, CharacterMaterialTypes.HAIR);
        register(registry, Hair.RED_ALMANDINE, CharacterMaterialTypes.HAIR);
        register(registry, Hair.WHITE_ALMANDINE, CharacterMaterialTypes.HAIR);
        register(registry, Hair.GRAY_ALMANDINE, CharacterMaterialTypes.HAIR);

        register(registry, Hair.BLACK_SILVER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.COLD_BLACK_SILVER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.BROWN_SILVER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.DARK_BROWN_SILVER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.BLONDE_SILVER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.DIRTY_BLONDE_SILVER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.DIRTY_BROWN_SILVER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.GREASY_SILVER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.STRAW_SILVER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.GINGER_SILVER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.RED_SILVER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.WHITE_SILVER, CharacterMaterialTypes.HAIR);
        register(registry, Hair.GRAY_SILVER, CharacterMaterialTypes.HAIR);
    }

    private static void register(BootstrapContext<CharacterTextureMaterial> registry, ResourceKey<CharacterTextureMaterial> key, CharacterMaterialTypes type) {
        CharacterTextureMaterial pattern = new CharacterTextureMaterial(key.location(), type);
        register(registry, key, pattern, getKey(type));
    }
    private static void register(BootstrapContext<CharacterTextureMaterial> registerable, ResourceKey<CharacterTextureMaterial> registryKey, CharacterTextureMaterial content, ResourceKey<Registry<CharacterTextureMaterial>> registryRegistryKey) {
        String name = registryKey.location().getPath();
        ResourceKey<CharacterTextureMaterial> key = ResourceKey.create(registryRegistryKey,ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID,name));
        registerable.register(key, content);
    }

    private static ResourceKey<CharacterTextureMaterial> of(String idPath, CharacterMaterialTypes type) {
        return ResourceKey.create(getKey(type), MiddleEarth.of(idPath));
    }

    public static ResourceKey<Registry<CharacterTextureMaterial>> getKey(CharacterMaterialTypes type){
        return switch (type) {
            case CharacterMaterialTypes.SKIN -> DynamicRegistriesME.SKIN_MATERIAL;
            case CharacterMaterialTypes.EYE -> DynamicRegistriesME.EYE_MATERIAL;
            case CharacterMaterialTypes.HAIR -> DynamicRegistriesME.HAIR_MATERIAL;
            default -> null;
        };
    }
}
