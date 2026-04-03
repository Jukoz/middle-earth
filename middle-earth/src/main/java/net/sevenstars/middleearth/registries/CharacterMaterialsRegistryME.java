package net.sevenstars.middleearth.registries;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.texture_presets.entities.CharacterTextureMaterial;
import net.sevenstars.middleearth.resources.datas.common.NpcTextureType;
import net.sevenstars.middleearth.utils.IdentifierUtil;

/**
 * Middle-earth mod npc texture materials registry<br>
 * Used to register the different palette colors<br>
 * <b><u>Datadriven content, do not use during runtime!</u></b>
 * <hr>
 */
public class CharacterMaterialsRegistryME {
    public record Skin(){
        public final static RegistryKey<CharacterTextureMaterial> BEIGE = of("beige", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> BROWN = of("brown", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> DARK_BEIGE = of("dark_beige", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> DARK_GREEN = of("dark_green", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> DARK_PINK = of("dark_pink", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> GREEN = of("green", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> GREY = of("grey", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> LIGHT_GREY = of("light_grey", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> MURKY_GREEN = of("murky_green", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> OLIVE = of("olive", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> PALE = of("pale", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> PALE_GREEN = of("pale_green", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> PALE_WHITE = of("pale_white", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> PINK = of("pink", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> RED = of("red", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> RUST = of("rust", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> SANDY_GREEN = of("sandy_green", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> SLIGHT_BROWN = of("slight_brown", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> SWAMPY_GREEN = of("swampy_green", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> TAN = of("tan", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> TAN_DESATURATED = of("tan_desaturated", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> WINE = of("wine", NpcTextureType.SKIN);
        public final static RegistryKey<CharacterTextureMaterial> YELLOWISH_GREEN = of("yellowish_green", NpcTextureType.SKIN);
    }

    public record Eye() {
        public final static RegistryKey<CharacterTextureMaterial> BLACK = of("black", NpcTextureType.EYE);
        public final static RegistryKey<CharacterTextureMaterial> BLUE = of("blue", NpcTextureType.EYE);
        public final static RegistryKey<CharacterTextureMaterial> BROWN = of("brown", NpcTextureType.EYE);
        public final static RegistryKey<CharacterTextureMaterial> DARK_GREEN = of("dark_green", NpcTextureType.EYE);
        public final static RegistryKey<CharacterTextureMaterial> DEEP_BLUE = of("deep_blue", NpcTextureType.EYE);
        public final static RegistryKey<CharacterTextureMaterial> GREEN = of("green", NpcTextureType.EYE);
        public final static RegistryKey<CharacterTextureMaterial> ICE = of("ice", NpcTextureType.EYE);
        public final static RegistryKey<CharacterTextureMaterial> NAVY = of("navy", NpcTextureType.EYE);
        public final static RegistryKey<CharacterTextureMaterial> PALE = of("pale", NpcTextureType.EYE);
        public final static RegistryKey<CharacterTextureMaterial> RED = of("red", NpcTextureType.EYE);
        public final static RegistryKey<CharacterTextureMaterial> YELLOW = of("yellow", NpcTextureType.EYE);
    }
    public record Hair() {
        public final static RegistryKey<CharacterTextureMaterial> BLACK_ALMANDINE = of("black_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> BLACK_BEADS = of("black_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> BLACK_COPPER = of("black_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> BLACK_GOLD = of("black_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> BLACK_SILVER = of("black_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> COLD_BLACK_ALMANDINE = of("cold_black_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> COLD_BLACK_BEADS = of("cold_black_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> COLD_BLACK_COPPER = of("cold_black_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> COLD_BLACK_GOLD = of("cold_black_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> COLD_BLACK_SILVER = of("cold_black_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> BROWN_ALMANDINE = of("brown_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> BROWN_BEADS = of("brown_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> BROWN_COPPER = of("brown_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> BROWN_GOLD = of("brown_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> BROWN_SILVER = of("brown_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> DARK_BROWN_ALMANDINE = of("dark_brown_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> DARK_BROWN_BEADS = of("dark_brown_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> DARK_BROWN_COPPER = of("dark_brown_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> DARK_BROWN_GOLD = of("dark_brown_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> DARK_BROWN_SILVER = of("dark_brown_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> BLONDE_ALMANDINE = of("blonde_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> BLONDE_BEADS = of("blonde_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> BLONDE_COPPER = of("blonde_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> BLONDE_GOLD = of("blonde_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> BLONDE_SILVER = of("blonde_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> DIRTY_BLONDE_ALMANDINE = of("dirty_blonde_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> DIRTY_BLONDE_BEADS = of("dirty_blonde_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> DIRTY_BLONDE_COPPER = of("dirty_blonde_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> DIRTY_BLONDE_GOLD = of("dirty_blonde_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> DIRTY_BLONDE_SILVER = of("dirty_blonde_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> DIRTY_BROWN_ALMANDINE = of("dirty_brown_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> DIRTY_BROWN_BEADS = of("dirty_brown_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> DIRTY_BROWN_COPPER = of("dirty_brown_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> DIRTY_BROWN_GOLD = of("dirty_brown_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> DIRTY_BROWN_SILVER = of("dirty_brown_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> GREASY_ALMANDINE = of("greasy_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> GREASY_BEADS = of("greasy_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> GREASY_COPPER = of("greasy_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> GREASY_GOLD = of("greasy_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> GREASY_SILVER = of("greasy_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> STRAW_ALMANDINE = of("straw_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> STRAW_BEADS = of("straw_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> STRAW_COPPER = of("straw_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> STRAW_GOLD = of("straw_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> STRAW_SILVER = of("straw_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> WHITE_ALMANDINE = of("white_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> WHITE_BEADS = of("white_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> WHITE_COPPER = of("white_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> WHITE_GOLD = of("white_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> WHITE_SILVER = of("white_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> GRAY_ALMANDINE = of("gray_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> GRAY_BEADS = of("gray_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> GRAY_COPPER = of("gray_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> GRAY_GOLD = of("gray_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> GRAY_SILVER = of("gray_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> GINGER_ALMANDINE = of("ginger_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> GINGER_BEADS = of("ginger_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> GINGER_COPPER = of("ginger_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> GINGER_GOLD = of("ginger_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<CharacterTextureMaterial> GINGER_SILVER = of("ginger_and_silver", NpcTextureType.HAIR);
    }

    public static void bootstrapSkins(Registerable<CharacterTextureMaterial> registry) {
        register(registry, Skin.BEIGE, NpcTextureType.SKIN);
        register(registry, Skin.BROWN, NpcTextureType.SKIN);
        register(registry, Skin.DARK_BEIGE, NpcTextureType.SKIN);
        register(registry, Skin.DARK_GREEN, NpcTextureType.SKIN);
        register(registry, Skin.DARK_PINK, NpcTextureType.SKIN);
        register(registry, Skin.GREEN, NpcTextureType.SKIN);
        register(registry, Skin.GREY, NpcTextureType.SKIN);
        register(registry, Skin.LIGHT_GREY, NpcTextureType.SKIN);
        register(registry, Skin.MURKY_GREEN, NpcTextureType.SKIN);
        register(registry, Skin.OLIVE, NpcTextureType.SKIN);
        register(registry, Skin.PALE, NpcTextureType.SKIN);
        register(registry, Skin.PALE_GREEN, NpcTextureType.SKIN);
        register(registry, Skin.PALE_WHITE, NpcTextureType.SKIN);
        register(registry, Skin.PINK, NpcTextureType.SKIN);
        register(registry, Skin.RED, NpcTextureType.SKIN);
        register(registry, Skin.RUST, NpcTextureType.SKIN);
        register(registry, Skin.SANDY_GREEN, NpcTextureType.SKIN);
        register(registry, Skin.SLIGHT_BROWN, NpcTextureType.SKIN);
        register(registry, Skin.SWAMPY_GREEN, NpcTextureType.SKIN);
        register(registry, Skin.TAN, NpcTextureType.SKIN);
        register(registry, Skin.TAN_DESATURATED, NpcTextureType.SKIN);
        register(registry, Skin.WINE, NpcTextureType.SKIN);
        register(registry, Skin.YELLOWISH_GREEN, NpcTextureType.SKIN);
    }

    public static void bootstrapEyes(Registerable<CharacterTextureMaterial> registry) {
        register(registry, Eye.BLACK, NpcTextureType.EYE);
        register(registry, Eye.BLUE, NpcTextureType.EYE);
        register(registry, Eye.BROWN, NpcTextureType.EYE);
        register(registry, Eye.DARK_GREEN, NpcTextureType.EYE);
        register(registry, Eye.DEEP_BLUE, NpcTextureType.EYE);
        register(registry, Eye.GREEN, NpcTextureType.EYE);
        register(registry, Eye.ICE, NpcTextureType.EYE);
        register(registry, Eye.NAVY, NpcTextureType.EYE);
        register(registry, Eye.PALE, NpcTextureType.EYE);
        register(registry, Eye.RED, NpcTextureType.EYE);
        register(registry, Eye.YELLOW, NpcTextureType.EYE);
    }
    public static void bootstrapHairs(Registerable<CharacterTextureMaterial> registry) {
        register(registry, Hair.BLACK_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.COLD_BLACK_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.BROWN_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.DARK_BROWN_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.BLONDE_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.DIRTY_BLONDE_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.DIRTY_BROWN_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.GREASY_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.STRAW_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.GINGER_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.WHITE_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.GRAY_GOLD, NpcTextureType.HAIR);

        register(registry, Hair.BLACK_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.COLD_BLACK_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.BROWN_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.DARK_BROWN_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.BLONDE_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.DIRTY_BLONDE_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.DIRTY_BROWN_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.GREASY_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.STRAW_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.GINGER_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.WHITE_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.GRAY_BEADS, NpcTextureType.HAIR);

        register(registry, Hair.BLACK_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.COLD_BLACK_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.BROWN_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.DARK_BROWN_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.BLONDE_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.DIRTY_BLONDE_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.DIRTY_BROWN_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.GREASY_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.STRAW_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.GINGER_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.WHITE_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.GRAY_COPPER, NpcTextureType.HAIR);

        register(registry, Hair.BLACK_ALMANDINE, NpcTextureType.HAIR);
        register(registry, Hair.COLD_BLACK_ALMANDINE, NpcTextureType.HAIR);
        register(registry, Hair.BROWN_ALMANDINE, NpcTextureType.HAIR);
        register(registry, Hair.DARK_BROWN_ALMANDINE, NpcTextureType.HAIR);
        register(registry, Hair.BLONDE_ALMANDINE, NpcTextureType.HAIR);
        register(registry, Hair.DIRTY_BLONDE_ALMANDINE, NpcTextureType.HAIR);
        register(registry, Hair.DIRTY_BROWN_ALMANDINE, NpcTextureType.HAIR);
        register(registry, Hair.GREASY_ALMANDINE, NpcTextureType.HAIR);
        register(registry, Hair.STRAW_ALMANDINE, NpcTextureType.HAIR);
        register(registry, Hair.GINGER_ALMANDINE, NpcTextureType.HAIR);
        register(registry, Hair.WHITE_ALMANDINE, NpcTextureType.HAIR);
        register(registry, Hair.GRAY_ALMANDINE, NpcTextureType.HAIR);

        register(registry, Hair.BLACK_SILVER, NpcTextureType.HAIR);
        register(registry, Hair.COLD_BLACK_SILVER, NpcTextureType.HAIR);
        register(registry, Hair.BROWN_SILVER, NpcTextureType.HAIR);
        register(registry, Hair.DARK_BROWN_SILVER, NpcTextureType.HAIR);
        register(registry, Hair.BLONDE_SILVER, NpcTextureType.HAIR);
        register(registry, Hair.DIRTY_BLONDE_SILVER, NpcTextureType.HAIR);
        register(registry, Hair.DIRTY_BROWN_SILVER, NpcTextureType.HAIR);
        register(registry, Hair.GREASY_SILVER, NpcTextureType.HAIR);
        register(registry, Hair.STRAW_SILVER, NpcTextureType.HAIR);
        register(registry, Hair.GINGER_SILVER, NpcTextureType.HAIR);
        register(registry, Hair.WHITE_SILVER, NpcTextureType.HAIR);
        register(registry, Hair.GRAY_SILVER, NpcTextureType.HAIR);
    }

    private static void register(Registerable<CharacterTextureMaterial> registry, RegistryKey<CharacterTextureMaterial> key, NpcTextureType type) {
        CharacterTextureMaterial pattern = new CharacterTextureMaterial(key.getValue(), type);
        register(registry, key, pattern,  getKey(type));
    }
    private static void register(Registerable<CharacterTextureMaterial> registerable, RegistryKey<CharacterTextureMaterial> registryKey, CharacterTextureMaterial content, RegistryKey<Registry<CharacterTextureMaterial>> registryRegistryKey) {
        String name = registryKey.getValue().getPath();
        RegistryKey<CharacterTextureMaterial> key = RegistryKey.of(registryRegistryKey,Identifier.of(MiddleEarth.MOD_ID,name));
        registerable.register(key, content);
    }

    private static RegistryKey<CharacterTextureMaterial> of(String id, NpcTextureType type) {
        return RegistryKey.of(getKey(type), IdentifierUtil.build(id));
    }

    public static RegistryKey<Registry<CharacterTextureMaterial>> getKey(NpcTextureType category){
        return switch (category) {
            case NpcTextureType.SKIN -> DynamicRegistriesME.SKIN_MATERIAL;
            case NpcTextureType.EYE -> DynamicRegistriesME.EYE_MATERIAL;
            case NpcTextureType.HAIR -> DynamicRegistriesME.HAIR_MATERIAL;
            default -> null;
        };
    }
}
