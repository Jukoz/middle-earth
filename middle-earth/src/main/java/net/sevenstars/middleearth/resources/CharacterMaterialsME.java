package net.sevenstars.middleearth.resources;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureMaterial;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;
import net.sevenstars.middleearth.utils.IdentifierUtil;

/**
 * Middle-earth mod npc texture materials registry<br>
 * Used to register the different palette colors<br>
 * <b><u>Datadriven content, do not use during runtime!</u></b>
 * <hr>
 */
public class CharacterMaterialsME {
    public record Keys(){
        public static final RegistryKey<Registry<NpcTextureMaterial>> SKIN_KEY = ofRegistry("character_skin_material");
        public static final RegistryKey<Registry<NpcTextureMaterial>> EYE_KEY = ofRegistry("character_eye_material");
        public static final RegistryKey<Registry<NpcTextureMaterial>> HAIR_KEY = ofRegistry("character_hair_material");
        public static final RegistryKey<Registry<NpcTextureMaterial>> CLOTHING_KEY = ofRegistry("character_clothing_material");
    }

    public record Skin(){
        public final static RegistryKey<NpcTextureMaterial> BEIGE = of("beige", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> BROWN = of("brown", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> DARK_BEIGE = of("dark_beige", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> DARK_GREEN = of("dark_green", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> DARK_PINK = of("dark_pink", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> GREEN = of("green", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> GREY = of("grey", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> LIGHT_GREY = of("light_grey", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> MURKY_GREEN = of("murky_green", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> OLIVE = of("olive", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> PALE = of("pale", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> PALE_GREEN = of("pale_green", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> PALE_WHITE = of("pale_white", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> PINK = of("pink", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> RED = of("red", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> RUST = of("rust", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> SANDY_GREEN = of("sandy_green", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> SLIGHT_BROWN = of("slight_brown", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> SWAMPY_GREEN = of("swampy_green", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> TAN = of("tan", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> TAN_DESATURATED = of("tan_desaturated", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> WINE = of("wine", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> YELLOWISH_GREEN = of("yellowish_green", NpcTextureType.SKIN);
    }

    public record Eye() {
        public final static RegistryKey<NpcTextureMaterial> BLACK = of("black", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> BLUE = of("blue", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> BROWN = of("brown", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> DARK_GREEN = of("dark_green", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> DEEP_BLUE = of("deep_blue", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> GREEN = of("green", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> ICE = of("ice", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> NAVY = of("navy", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> PALE = of("pale", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> RED = of("red", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> YELLOW = of("yellow", NpcTextureType.EYE);
    }
    public record Hair() {
        public final static RegistryKey<NpcTextureMaterial> BLACK_ALMANDINE = of("black_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BLACK_BEADS = of("black_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BLACK_COPPER = of("black_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BLACK_GOLD = of("black_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BLACK_SILVER = of("black_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> COLD_BLACK_ALMANDINE = of("cold_black_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> COLD_BLACK_BEADS = of("cold_black_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> COLD_BLACK_COPPER = of("cold_black_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> COLD_BLACK_GOLD = of("cold_black_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> COLD_BLACK_SILVER = of("cold_black_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BROWN_ALMANDINE = of("brown_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BROWN_BEADS = of("brown_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BROWN_COPPER = of("brown_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BROWN_GOLD = of("brown_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BROWN_SILVER = of("brown_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DARK_BROWN_ALMANDINE = of("dark_brown_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DARK_BROWN_BEADS = of("dark_brown_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DARK_BROWN_COPPER = of("dark_brown_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DARK_BROWN_GOLD = of("dark_brown_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DARK_BROWN_SILVER = of("dark_brown_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BLONDE_ALMANDINE = of("blonde_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BLONDE_BEADS = of("blonde_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BLONDE_COPPER = of("blonde_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BLONDE_GOLD = of("blonde_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BLONDE_SILVER = of("blonde_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DIRTY_BLONDE_ALMANDINE = of("dirty_blonde_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DIRTY_BLONDE_BEADS = of("dirty_blonde_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DIRTY_BLONDE_COPPER = of("dirty_blonde_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DIRTY_BLONDE_GOLD = of("dirty_blonde_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DIRTY_BLONDE_SILVER = of("dirty_blonde_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DIRTY_BROWN_ALMANDINE = of("dirty_brown_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DIRTY_BROWN_BEADS = of("dirty_brown_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DIRTY_BROWN_COPPER = of("dirty_brown_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DIRTY_BROWN_GOLD = of("dirty_brown_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DIRTY_BROWN_SILVER = of("dirty_brown_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GREASY_ALMANDINE = of("greasy_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GREASY_BEADS = of("greasy_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GREASY_COPPER = of("greasy_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GREASY_GOLD = of("greasy_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GREASY_SILVER = of("greasy_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> STRAW_ALMANDINE = of("straw_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> STRAW_BEADS = of("straw_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> STRAW_COPPER = of("straw_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> STRAW_GOLD = of("straw_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> STRAW_SILVER = of("straw_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> WHITE_ALMANDINE = of("white_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> WHITE_BEADS = of("white_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> WHITE_COPPER = of("white_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> WHITE_GOLD = of("white_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> WHITE_SILVER = of("white_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GRAY_ALMANDINE = of("gray_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GRAY_BEADS = of("gray_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GRAY_COPPER = of("gray_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GRAY_GOLD = of("gray_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GRAY_SILVER = of("gray_and_silver", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GINGER_ALMANDINE = of("ginger_and_almandine", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GINGER_BEADS = of("ginger_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GINGER_COPPER = of("ginger_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GINGER_GOLD = of("ginger_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GINGER_SILVER = of("ginger_and_silver", NpcTextureType.HAIR);
    }

    public record Clothing() {
        public final static RegistryKey<NpcTextureMaterial> WHITE = of("white", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTextureMaterial> BROWN = of("brown", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTextureMaterial> ROT_GREEN = of("rot_green", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTextureMaterial> GRAY = of("gray", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTextureMaterial> BLUE_AND_COPPER = of("blue_and_copper", NpcTextureType.CLOTHING);
    }

    public static void bootstrapSkins(Registerable<NpcTextureMaterial> registry) {
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

    public static void bootstrapEyes(Registerable<NpcTextureMaterial> registry) {
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
    public static void bootstrapHairs(Registerable<NpcTextureMaterial> registry) {
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

    public static void bootstrapClothings(Registerable<NpcTextureMaterial> registry) {
        register(registry, Clothing.WHITE, NpcTextureType.CLOTHING);
        register(registry, Clothing.BROWN, NpcTextureType.CLOTHING);
        register(registry, Clothing.ROT_GREEN, NpcTextureType.CLOTHING);
        register(registry, Clothing.GRAY, NpcTextureType.CLOTHING);
        register(registry, Clothing.BLUE_AND_COPPER, NpcTextureType.CLOTHING);
    }

    public static void register() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Npc Texture Materials for " + MiddleEarth.MOD_ID);
        DynamicRegistries.registerSynced(Keys.SKIN_KEY, NpcTextureMaterial.CODEC);
        DynamicRegistries.registerSynced(Keys.EYE_KEY, NpcTextureMaterial.CODEC);
        DynamicRegistries.registerSynced(Keys.HAIR_KEY, NpcTextureMaterial.CODEC);
        DynamicRegistries.registerSynced(Keys.CLOTHING_KEY, NpcTextureMaterial.CODEC);
    }

    private static void register(Registerable<NpcTextureMaterial> registry, RegistryKey<NpcTextureMaterial> key, NpcTextureType type) {
        NpcTextureMaterial pattern = new NpcTextureMaterial(key.getValue(), type);
        register(registry, key, pattern,  getKey(type));
    }
    private static void register(Registerable<NpcTextureMaterial> registerable, RegistryKey<NpcTextureMaterial> registryKey, NpcTextureMaterial content, RegistryKey<Registry<NpcTextureMaterial>> registryRegistryKey) {
        String name = registryKey.getValue().getPath();
        RegistryKey<NpcTextureMaterial> key = RegistryKey.of(registryRegistryKey,Identifier.of(MiddleEarth.MOD_ID,name));
        registerable.register(key, content);
    }

    private static RegistryKey<NpcTextureMaterial> of(String id, NpcTextureType type) {
        return RegistryKey.of(getKey(type), IdentifierUtil.build(id));
    }

    private static <T> RegistryKey<Registry<T>> ofRegistry(String id) {
        return RegistryKey.ofRegistry(IdentifierUtil.build(id));
    }

    public static RegistryKey<Registry<NpcTextureMaterial>> getKey(NpcTextureType category){
        return switch (category) {
            case NpcTextureType.SKIN -> Keys.SKIN_KEY;
            case NpcTextureType.EYE -> Keys.EYE_KEY;
            case NpcTextureType.HAIR -> Keys.HAIR_KEY;
            case NpcTextureType.CLOTHING -> Keys.CLOTHING_KEY;
            default -> null;
        };
    }
}
