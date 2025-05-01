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

public class MiddleEarthNpcTextureMaterials {
    public record Keys(){
        public static final RegistryKey<Registry<NpcTextureMaterial>> SKIN_KEY = ofRegistry("npc_skin_material");
        public static final RegistryKey<Registry<NpcTextureMaterial>> EYE_KEY = ofRegistry("npc_eye_material");
        public static final RegistryKey<Registry<NpcTextureMaterial>> HAIR_KEY = ofRegistry("npc_hair_material");
        public static final RegistryKey<Registry<NpcTextureMaterial>> CLOTHING_KEY = ofRegistry("npc_clothing_material");
    }

    public record Skin(){
        public final static RegistryKey<NpcTextureMaterial> PALE = of("pale", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> TAN = of("tan", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> NEUTRAL = of("neutral", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> OLIVE = of("olive", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> BROWN = of("brown", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> GREENISH = of("greenish", NpcTextureType.SKIN);
        public final static RegistryKey<NpcTextureMaterial> TAN_DESATURATED = of("tan_desaturated", NpcTextureType.SKIN);
    }
    public record Eye() {
        public final static RegistryKey<NpcTextureMaterial> BLACK = of("black", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> BLUE = of("blue", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> BROWN = of("brown", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> DARK_GREEN = of("dark_green", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> DEEP_BLUE = of("deep_blue", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> GREEN = of("green", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> NAVY = of("navy", NpcTextureType.EYE);
        public final static RegistryKey<NpcTextureMaterial> YELLOW = of("yellow", NpcTextureType.EYE);

    }
    public record Hair() {
        public final static RegistryKey<NpcTextureMaterial> BLACK_BEADS = of("black_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BLACK_COPPER = of("black_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BLACK_GOLD = of("black_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BROWN_BEADS = of("brown_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BROWN_COPPER = of("brown_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BROWN_GOLD = of("brown_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DARK_BROWN_BEADS = of("dark_brown_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DARK_BROWN_COPPER = of("dark_brown_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> DARK_BROWN_GOLD = of("dark_brown_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BLONDE_BEADS = of("blonde_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BLONDE_COPPER = of("blonde_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> BLONDE_GOLD = of("blonde_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> STRAW_BEADS = of("straw_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> STRAW_COPPER = of("straw_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> STRAW_GOLD = of("straw_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> WHITE_BEADS = of("white_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> WHITE_COPPER = of("white_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> WHITE_GOLD = of("white_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GRAY_BEADS = of("gray_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GRAY_COPPER = of("gray_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> GRAY_GOLD = of("gray_and_gold", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> ORANGE_BEADS = of("orange_and_beads", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> ORANGE_COPPER = of("orange_and_copper", NpcTextureType.HAIR);
        public final static RegistryKey<NpcTextureMaterial> ORANGE_GOLD = of("orange_and_gold", NpcTextureType.HAIR);
    }

    public record Clothing() {
        public final static RegistryKey<NpcTextureMaterial> WHITE = of("white", NpcTextureType.CLOTHING);
        public final static RegistryKey<NpcTextureMaterial> BROWN = of("brown", NpcTextureType.CLOTHING);
    }


    public static void bootstrapSkins(Registerable<NpcTextureMaterial> registry) {
        register(registry, Skin.TAN, NpcTextureType.SKIN);
        register(registry, Skin.PALE, NpcTextureType.SKIN);
        register(registry, Skin.NEUTRAL, NpcTextureType.SKIN);
        register(registry, Skin.OLIVE, NpcTextureType.SKIN);
    }
    public static void bootstrapEyes(Registerable<NpcTextureMaterial> registry) {
        register(registry, Eye.BLACK, NpcTextureType.EYE);
        register(registry, Eye.BLUE, NpcTextureType.EYE);
        register(registry, Eye.BROWN, NpcTextureType.EYE);
        register(registry, Eye.DARK_GREEN, NpcTextureType.EYE);
        register(registry, Eye.DEEP_BLUE, NpcTextureType.EYE);
        register(registry, Eye.GREEN, NpcTextureType.EYE);
        register(registry, Eye.NAVY, NpcTextureType.EYE);
        register(registry, Eye.YELLOW, NpcTextureType.EYE);
    }
    public static void bootstrapHairs(Registerable<NpcTextureMaterial> registry) {
        register(registry, Hair.BLACK_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.BROWN_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.DARK_BROWN_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.BLONDE_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.STRAW_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.ORANGE_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.WHITE_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.GRAY_GOLD, NpcTextureType.HAIR);
        register(registry, Hair.BLACK_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.BROWN_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.DARK_BROWN_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.BLONDE_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.STRAW_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.ORANGE_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.WHITE_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.GRAY_BEADS, NpcTextureType.HAIR);
        register(registry, Hair.BLACK_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.BROWN_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.DARK_BROWN_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.BLONDE_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.STRAW_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.ORANGE_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.WHITE_COPPER, NpcTextureType.HAIR);
        register(registry, Hair.GRAY_COPPER, NpcTextureType.HAIR);
    }

    public static void bootstrapClothings(Registerable<NpcTextureMaterial> registry) {
        register(registry, Clothing.WHITE, NpcTextureType.CLOTHING);
        register(registry, Clothing.BROWN, NpcTextureType.CLOTHING);
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
        return RegistryKey.of(getKey(type), IdentifierUtil.create(id));
    }

    private static <T> RegistryKey<Registry<T>> ofRegistry(String id) {
        return RegistryKey.ofRegistry(IdentifierUtil.create(id));
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
