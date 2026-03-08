package net.sevenstars.middleearth.client;

import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.resources.AtlasesME;

import java.util.HashMap;
import java.util.Map;

public class ModTexturedRenderLayers extends TexturedRenderLayers {
    public static final SpriteIdentifier HEATER_SHIELD_BASE;
    public static final SpriteIdentifier KITE_SHIELD_BASE;
    public static final SpriteIdentifier ROUND_SHIELD_BASE;

    private static final Map<Identifier, SpriteIdentifier> HEATER_SHIELD_PATTERN_TEXTURES;
    private static final Map<Identifier, SpriteIdentifier> KITE_SHIELD_PATTERN_TEXTURES;
    private static final Map<Identifier, SpriteIdentifier> ROUND_SHIELD_PATTERN_TEXTURES;

    public static final Identifier CHARACTER_SKIN_ATLAS_TEXTURE = AtlasesME.getAtlasPath(AtlasesME.CHARACTER_SKINS);
    private static final RenderLayer CHARACTER_SKINS_RENDER_LAYER;

    public static final Identifier CHARACTER_EYES_ATLAS_TEXTURE = AtlasesME.getAtlasPath(AtlasesME.CHARACTER_EYES);
    private static final RenderLayer CHARACTER_EYES_RENDER_LAYER;
    private static final RenderLayer CHARACTER_EYES_EMISSIVE_RENDER_LAYER;

    public static final Identifier CHARACTER_HAIRS_ATLAS_TEXTURE = AtlasesME.getAtlasPath(AtlasesME.CHARACTER_HAIRS);
    private static final RenderLayer CHARACTER_HAIRS_RENDER_LAYER;

    public static final Identifier CHARACTER_CLOTHES_ATLAS_TEXTURE = AtlasesME.getAtlasPath(AtlasesME.CHARACTER_CLOTHES);
    private static final RenderLayer CHARACTER_CLOTHES_RENDER_LAYER;

    static {
        CHARACTER_SKINS_RENDER_LAYER = RenderLayer.getEntityCutout(CHARACTER_SKIN_ATLAS_TEXTURE);
        CHARACTER_EYES_RENDER_LAYER = RenderLayer.getEntityCutout(CHARACTER_EYES_ATLAS_TEXTURE);
        CHARACTER_HAIRS_RENDER_LAYER = RenderLayer.getEntityCutout(CHARACTER_HAIRS_ATLAS_TEXTURE);
        CHARACTER_CLOTHES_RENDER_LAYER = RenderLayer.getEntityCutout(CHARACTER_CLOTHES_ATLAS_TEXTURE);

        CHARACTER_EYES_EMISSIVE_RENDER_LAYER = RenderLayer.getEntityTranslucentEmissive(CHARACTER_EYES_ATLAS_TEXTURE);

        HEATER_SHIELD_BASE = new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, Identifier.of("entity/heater_shield/base"));
        KITE_SHIELD_BASE = new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, Identifier.of("entity/kite_shield/base"));
        ROUND_SHIELD_BASE = new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, Identifier.of("entity/round_shield/base"));

        HEATER_SHIELD_PATTERN_TEXTURES = new HashMap<>();
        KITE_SHIELD_PATTERN_TEXTURES = new HashMap<>();
        ROUND_SHIELD_PATTERN_TEXTURES = new HashMap<>();
    }

    public static RenderLayer getCharacterSkinsRenderLayer() {
        return CHARACTER_SKINS_RENDER_LAYER;
    }
    public static RenderLayer getCharacterEyesTexturesRenderLayer(boolean isEmissive) {
        return (isEmissive)
                ? CHARACTER_EYES_EMISSIVE_RENDER_LAYER
                : CHARACTER_EYES_RENDER_LAYER;
    }
    public static RenderLayer getCharacterHairsRenderLayer() {
        return CHARACTER_HAIRS_RENDER_LAYER;
    }
    public static RenderLayer getCharacterClothingsRenderLayer() {
        return CHARACTER_CLOTHES_RENDER_LAYER;
    }



    public static SpriteIdentifier getHeaterShieldPatternTextureId(RegistryEntry<BannerPattern> pattern) {
        return HEATER_SHIELD_PATTERN_TEXTURES.computeIfAbsent((pattern.value()).assetId(), (id) -> {
            Identifier identifier = id.withPrefixedPath("entity/heater_shield/");
            return new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, identifier);
        });
    }

    public static SpriteIdentifier getKiteShieldPatternTextureId(RegistryEntry<BannerPattern> pattern) {
        return KITE_SHIELD_PATTERN_TEXTURES.computeIfAbsent((pattern.value()).assetId(), (id) -> {
            Identifier identifier = id.withPrefixedPath("entity/kite_shield/");
            return new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, identifier);
        });
    }

    public static SpriteIdentifier getRoundShieldPatternTextureId(RegistryEntry<BannerPattern> pattern) {
        return ROUND_SHIELD_PATTERN_TEXTURES.computeIfAbsent((pattern.value()).assetId(), (id) -> {
            Identifier identifier = id.withPrefixedPath("entity/round_shield/");
            return new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, identifier);
        });
    }
}
