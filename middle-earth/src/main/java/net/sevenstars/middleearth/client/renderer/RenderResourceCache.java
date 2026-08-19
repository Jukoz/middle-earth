package net.sevenstars.middleearth.client.renderer;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.dataComponents.SeasonDataComponent;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.sevenstars.middleearth.registries.AtlasesME;

import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class RenderResourceCache {
    private static final int MAX_NPC_TEXTURES_PER_PREFIX = 1024;
    private static final int MAX_ARMOR_VARIANT = 256;
    private static final Map<Item, ArmorTextures> ARMOR_TEXTURES = new IdentityHashMap<>();
    private static final EnumMap<NpcPrefix, PrefixCache> NPC_TEXTURES = new EnumMap<>(NpcPrefix.class);
    private static final EnumMap<HelmetAttachmentsME, HelmetAttachmentTextures> HELMET_ATTACHMENTS =
            new EnumMap<>(HelmetAttachmentsME.class);
    private static final EnumMap<BackAttachmentsME, TexturePair> BACK_ATTACHMENTS =
            new EnumMap<>(BackAttachmentsME.class);
    private static final EnumMap<SeasonDataComponent.Season, CrownTextures> CROWN_TEXTURES =
            new EnumMap<>(SeasonDataComponent.Season.class);
    private static final CrownTextures DEFAULT_CROWN =
            crownTextures("textures/models/armor/woodland_realm_crown.png");

    static {
        for (NpcPrefix prefix : NpcPrefix.values()) {
            NPC_TEXTURES.put(prefix, new PrefixCache(prefix.prefix));
        }
        for (HelmetAttachmentsME attachment : HelmetAttachmentsME.values()) {
            String path = "textures/models/helmet_attachment/" + attachment.getName();
            HELMET_ATTACHMENTS.put(attachment, new HelmetAttachmentTextures(
                    texturePair(path + ".png"),
                    texturePair(path + "_down.png")));
        }
        for (BackAttachmentsME attachment : BackAttachmentsME.values()) {
            BACK_ATTACHMENTS.put(attachment,
                    texturePair("textures/models/back_attachment/" + attachment.getName() + ".png"));
        }
        for (SeasonDataComponent.Season season : SeasonDataComponent.Season.values()) {
            String suffix = switch (season) {
                case SPRING -> "_spring";
                case SUMMER -> "_summer";
                case AUTUMN -> "_autumn";
                case WINTER -> "_winter";
                case DEAD -> "";
            };
            CROWN_TEXTURES.put(season,
                    suffix.isEmpty()
                            ? DEFAULT_CROWN
                            : crownTextures("textures/models/armor/woodland_realm_crown" + suffix + ".png"));
        }
    }

    private RenderResourceCache() {
    }

    public static ResourceLocation npcTexture(ResourceLocation texture, NpcPrefix prefix) {
        if (texture == null) {
            return null;
        }
        return NPC_TEXTURES.get(prefix).getOrCreate(texture);
    }

    public static void registerArmorItem(Item item, boolean cacheVariants) {
        ArmorTextures current = ARMOR_TEXTURES.get(item);
        if (current == null || cacheVariants && !current.hasVariants()) {
            ARMOR_TEXTURES.put(item, createArmorTextures(item, cacheVariants));
        }
    }

    public static ArmorTextures armor(Item item) {
        ArmorTextures textures = ARMOR_TEXTURES.get(item);
        if (textures == null) {
            textures = createArmorTextures(item, false);
            ARMOR_TEXTURES.put(item, textures);
        }
        return textures;
    }

    public static TexturePair helmetAttachment(HelmetAttachmentsME attachment, boolean down) {
        HelmetAttachmentTextures textures = HELMET_ATTACHMENTS.get(attachment);
        return down ? textures.down : textures.up;
    }

    public static TexturePair backAttachment(BackAttachmentsME attachment) {
        return BACK_ATTACHMENTS.get(attachment);
    }

    public static CrownTextures crown(SeasonDataComponent.Season season) {
        return season == null ? DEFAULT_CROWN : CROWN_TEXTURES.get(season);
    }

    private static ArmorTextures createArmorTextures(Item item, boolean cacheVariants) {
        String itemPath = BuiltInRegistries.ITEM.getKey(item).getPath();
        String basePath = "textures/models/armor/" + itemPath + ".png";
        TexturePair base = texturePair(basePath);
        TexturePair genericAddition = texturePair(replaceSuffix(basePath, ".png", "_addition.png"));
        TexturePair helmetAddition = basePath.endsWith("_helmet.png")
                ? texturePair(replaceSuffix(basePath, "_helmet.png", "_addition.png"))
                : genericAddition;
        TexturePair chestplateAddition = basePath.endsWith("_chestplate.png")
                ? texturePair(replaceSuffix(basePath, "_chestplate.png", "_addition.png"))
                : genericAddition;
        TexturePair[] variants = null;
        if (cacheVariants) {
            variants = new TexturePair[MAX_ARMOR_VARIANT + 1];
            variants[0] = helmetAddition;
            for (int variant = 1; variant <= MAX_ARMOR_VARIANT; variant++) {
                variants[variant] = basePath.endsWith("_helmet.png")
                        ? texturePair(replaceSuffix(basePath, "_helmet.png", "_addition_" + variant + ".png"))
                        : genericAddition;
            }
        }
        return new ArmorTextures(base, genericAddition, helmetAddition, chestplateAddition, variants);
    }

    private static CrownTextures crownTextures(String path) {
        return new CrownTextures(texturePair(path), texturePair(replaceSuffix(path, ".png", "_addition.png")));
    }

    private static TexturePair texturePair(String path) {
        ResourceLocation base = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, path);
        ResourceLocation overlay = ResourceLocation.fromNamespaceAndPath(
                MiddleEarth.MOD_ID, replaceSuffix(path, ".png", "_overlay.png"));
        return new TexturePair(base, overlay);
    }

    private static String replaceSuffix(String value, String suffix, String replacement) {
        return value.substring(0, value.length() - suffix.length()) + replacement;
    }

    public enum NpcPrefix {
        SKIN(AtlasesME.SKIN_PREFIX),
        HAIR(AtlasesME.HAIR_PREFIX),
        EYE(AtlasesME.EYE_PREFIX),
        CLOTHES_BASE(AtlasesME.CLOTHES_BASE_PREFIX),
        CLOTHES_OVER(AtlasesME.CLOTHES_OVER_PREFIX),
        CLOTHES_EXTRA(AtlasesME.CLOTHES_EXTRA_PREFIX);

        private final ResourceLocation prefix;

        NpcPrefix(ResourceLocation prefix) {
            this.prefix = prefix;
        }
    }

    public record TexturePair(ResourceLocation base, ResourceLocation overlay) {
    }

    public record CrownTextures(TexturePair base, TexturePair addition) {
    }

    public static final class ArmorTextures {
        private final TexturePair base;
        private final TexturePair genericAddition;
        private final TexturePair helmetAddition;
        private final TexturePair chestplateAddition;
        private final TexturePair[] helmetVariants;

        private ArmorTextures(TexturePair base, TexturePair genericAddition, TexturePair helmetAddition,
                              TexturePair chestplateAddition, TexturePair[] helmetVariants) {
            this.base = base;
            this.genericAddition = genericAddition;
            this.helmetAddition = helmetAddition;
            this.chestplateAddition = chestplateAddition;
            this.helmetVariants = helmetVariants;
        }

        public TexturePair base() {
            return this.base;
        }

        public TexturePair genericAddition() {
            return this.genericAddition;
        }

        public TexturePair helmetAddition() {
            return this.helmetAddition;
        }

        public TexturePair chestplateAddition() {
            return this.chestplateAddition;
        }

        public TexturePair helmetVariantAddition(int variant) {
            if (this.helmetVariants == null || variant <= 0 || variant > MAX_ARMOR_VARIANT) {
                return this.helmetAddition;
            }
            return this.helmetVariants[variant];
        }

        private boolean hasVariants() {
            return this.helmetVariants != null;
        }
    }

    private record HelmetAttachmentTextures(TexturePair up, TexturePair down) {
    }

    private static final class PrefixCache extends LinkedHashMap<ResourceLocation, ResourceLocation> {
        private final String pathPrefix;

        private PrefixCache(ResourceLocation prefix) {
            super(128, 0.75F, true);
            this.pathPrefix = prefix.getPath() + "/";
        }

        private ResourceLocation getOrCreate(ResourceLocation texture) {
            ResourceLocation cached = this.get(texture);
            if (cached == null) {
                cached = texture.withPrefix(this.pathPrefix);
                this.put(texture, cached);
            }
            return cached;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<ResourceLocation, ResourceLocation> eldest) {
            return this.size() > MAX_NPC_TEXTURES_PER_PREFIX;
        }
    }
}
