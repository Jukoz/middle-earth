package net.sevenstars.middleearth.client;

import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.HashMap;
import java.util.Map;

public class ModTexturedRenderLayers extends TexturedRenderLayers {
    public static final SpriteIdentifier HEATER_SHIELD_BASE;
    public static final SpriteIdentifier KITE_SHIELD_BASE;
    public static final SpriteIdentifier ROUND_SHIELD_BASE;

    private static final Map<Identifier, SpriteIdentifier> HEATER_SHIELD_PATTERN_TEXTURES;
    private static final Map<Identifier, SpriteIdentifier> KITE_SHIELD_PATTERN_TEXTURES;
    private static final Map<Identifier, SpriteIdentifier> ROUND_SHIELD_PATTERN_TEXTURES;



    public static final Identifier NPC_SKIN_TEXTURES_ATLAS_TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/atlas/npc_skin_textures.png");
    private static final RenderLayer NPC_SKIN_TEXTURES_RENDER_LAYER;


    public static final Identifier NPC_EYE_TEXTURES_ATLAS_TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/atlas/npc_eye_textures.png");
    private static final RenderLayer NPC_EYE_TEXTURES_RENDER_LAYER;
    private static final RenderLayer NPC_EYE_TEXTURES_EMISSIVE_RENDER_LAYER;

    public static final Identifier NPC_HAIR_TEXTURES_ATLAS_TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/atlas/npc_hair_textures.png");
    private static final RenderLayer NPC_HAIR_TEXTURES_RENDER_LAYER;

    public static final Identifier NPC_CLOTHING_TEXTURES_ATLAS_TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/atlas/npc_clothing_textures.png");
    private static final RenderLayer NPC_CLOTHING_TEXTURES_RENDER_LAYER;

    static {
        NPC_SKIN_TEXTURES_RENDER_LAYER = RenderLayer.getEntityCutoutNoCull(NPC_SKIN_TEXTURES_ATLAS_TEXTURE);

        NPC_EYE_TEXTURES_RENDER_LAYER = RenderLayer.getEntityCutoutNoCull(NPC_EYE_TEXTURES_ATLAS_TEXTURE);
        NPC_EYE_TEXTURES_EMISSIVE_RENDER_LAYER = RenderLayer.getEntityTranslucentEmissive(NPC_EYE_TEXTURES_ATLAS_TEXTURE);

        NPC_HAIR_TEXTURES_RENDER_LAYER = RenderLayer.getEntityCutoutNoCull(NPC_HAIR_TEXTURES_ATLAS_TEXTURE);

        NPC_CLOTHING_TEXTURES_RENDER_LAYER = RenderLayer.getEntityCutoutNoCull(NPC_CLOTHING_TEXTURES_ATLAS_TEXTURE);


        HEATER_SHIELD_BASE = new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, Identifier.of("entity/heater_shield/base"));
        KITE_SHIELD_BASE = new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, Identifier.of("entity/kite_shield/base"));
        ROUND_SHIELD_BASE = new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, Identifier.of("entity/round_shield/base"));


        HEATER_SHIELD_PATTERN_TEXTURES = new HashMap<>();
        KITE_SHIELD_PATTERN_TEXTURES = new HashMap<>();
        ROUND_SHIELD_PATTERN_TEXTURES = new HashMap<>();
    }

    public static RenderLayer getNpcSkinTexturesRenderLayer() {
        return NPC_SKIN_TEXTURES_RENDER_LAYER;
    }
    public static RenderLayer getNpcEyeTexturesRenderLayer(boolean isEmissive) {
        return (isEmissive)
                ? NPC_EYE_TEXTURES_EMISSIVE_RENDER_LAYER
                : NPC_EYE_TEXTURES_RENDER_LAYER;
    }
    public static RenderLayer getNpcHairTexturesRenderLayer() {
        return NPC_HAIR_TEXTURES_RENDER_LAYER;
    }
    public static RenderLayer getNpcClothingTexturesRenderLayer() {
        return NPC_CLOTHING_TEXTURES_RENDER_LAYER;
    }



    public static SpriteIdentifier getHeaterShieldPatternTextureId(RegistryEntry<BannerPattern> pattern) {
        return (SpriteIdentifier)HEATER_SHIELD_PATTERN_TEXTURES.computeIfAbsent(((BannerPattern)pattern.value()).assetId(), (id) -> {
            Identifier identifier = id.withPrefixedPath("entity/heater_shield/");
            return new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, identifier);
        });
    }

    public static SpriteIdentifier getKiteShieldPatternTextureId(RegistryEntry<BannerPattern> pattern) {
        return (SpriteIdentifier)KITE_SHIELD_PATTERN_TEXTURES.computeIfAbsent(((BannerPattern)pattern.value()).assetId(), (id) -> {
            Identifier identifier = id.withPrefixedPath("entity/kite_shield/");
            return new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, identifier);
        });
    }

    public static SpriteIdentifier getRoundShieldPatternTextureId(RegistryEntry<BannerPattern> pattern) {
        return (SpriteIdentifier)ROUND_SHIELD_PATTERN_TEXTURES.computeIfAbsent(((BannerPattern)pattern.value()).assetId(), (id) -> {
            Identifier identifier = id.withPrefixedPath("entity/round_shield/");
            return new SpriteIdentifier(SHIELD_PATTERNS_ATLAS_TEXTURE, identifier);
        });
    }
}
