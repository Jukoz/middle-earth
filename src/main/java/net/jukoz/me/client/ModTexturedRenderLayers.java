package net.jukoz.me.client;

import net.jukoz.me.MiddleEarth;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ModTexturedRenderLayers extends TexturedRenderLayers {
    public static final SpriteIdentifier HEATER_SHIELD_BASE;
    private static final Map<Identifier, SpriteIdentifier> HEATER_SHIELD_PATTERN_TEXTURES;

    static {
        HEATER_SHIELD_BASE = new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, Identifier.of("entity/heater_shield/base"));
        HEATER_SHIELD_PATTERN_TEXTURES = new HashMap<>();

    }

    public static SpriteIdentifier getShieldPatternTextureId(RegistryEntry<BannerPattern> pattern) {
        return (SpriteIdentifier)HEATER_SHIELD_PATTERN_TEXTURES.computeIfAbsent(((BannerPattern)pattern.value()).assetId(), (id) -> {
            Identifier identifier = id.withPrefixedPath("entity/heater_shield/");
            return new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, identifier);
        });
    }

}
