package net.sevenstars.middleearth.client;

import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.AtlasesME;

import java.util.HashMap;
import java.util.Map;

public class ModTexturedRenderLayers extends TexturedRenderLayers {
    public static final SpriteIdentifier HEATER_SHIELD_BASE;
    public static final SpriteIdentifier KITE_SHIELD_BASE;
    public static final SpriteIdentifier ROUND_SHIELD_BASE;

    private static final Map<Identifier, SpriteIdentifier> HEATER_SHIELD_PATTERN_TEXTURES;
    private static final Map<Identifier, SpriteIdentifier> KITE_SHIELD_PATTERN_TEXTURES;
    private static final Map<Identifier, SpriteIdentifier> ROUND_SHIELD_PATTERN_TEXTURES;

    public static final Identifier CHARACTER_ATLAS_TEXTURES = AtlasesME.getAtlasPath(AtlasesME.CHARACTER_TEXTURES);
    public static final RenderLayer CHARACTER_TEXTURES_RENDER_LAYER;
    public static final RenderLayer CHARACTER_TEXTURES_EMISSIVE_RENDER_LAYER;


    static {
        CHARACTER_TEXTURES_RENDER_LAYER = RenderLayer.getEntityCutoutNoCull(CHARACTER_ATLAS_TEXTURES);
        CHARACTER_TEXTURES_EMISSIVE_RENDER_LAYER = RenderLayer.getEntityTranslucentEmissive(CHARACTER_ATLAS_TEXTURES);

        HEATER_SHIELD_BASE = new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, MiddleEarth.ofPath("entity", "heater_shield", "base" ));
        KITE_SHIELD_BASE = new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, MiddleEarth.ofPath("entity", "kite_shield", "base" ));
        ROUND_SHIELD_BASE = new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, MiddleEarth.ofPath("entity", "round_shield", "base" ));

        HEATER_SHIELD_PATTERN_TEXTURES = new HashMap<>();
        KITE_SHIELD_PATTERN_TEXTURES = new HashMap<>();
        ROUND_SHIELD_PATTERN_TEXTURES = new HashMap<>();
    }

    public static RenderLayer getCharacterTexturesRenderLayer() {
        return CHARACTER_TEXTURES_RENDER_LAYER;
    }

    public static RenderLayer getCharacterTexturesEmissiveRenderLayer() {
        return CHARACTER_TEXTURES_EMISSIVE_RENDER_LAYER;
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
