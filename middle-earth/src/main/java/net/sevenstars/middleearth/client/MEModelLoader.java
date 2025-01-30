package net.sevenstars.middleearth.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.model.ModelBaker;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class MEModelLoader extends ModelBaker {
    public static final SpriteIdentifier KITE_SHIELD_BASE;
    public static final SpriteIdentifier KITE_SHIELD_BASE_NO_PATTERN;

    public static final SpriteIdentifier HEATER_SHIELD_BASE;
    public static final SpriteIdentifier HEATER_SHIELD_BASE_NO_PATTERN;

    public static final SpriteIdentifier ROUND_SHIELD_BASE;
    public static final SpriteIdentifier ROUND_SHIELD_BASE_NO_PATTERN;

    public MEModelLoader(Map<ModelIdentifier, UnbakedModel> models, Map<Identifier, UnbakedModel> resolvedModels, UnbakedModel missingModel) {
        super(models, resolvedModels, missingModel);
    }

    static {
        KITE_SHIELD_BASE = new SpriteIdentifier(TexturedRenderLayers.SHIELD_PATTERNS_ATLAS_TEXTURE, Identifier.of(MiddleEarth.MOD_ID, "entity/kite_shield_base"));
        KITE_SHIELD_BASE_NO_PATTERN = new SpriteIdentifier(TexturedRenderLayers.SHIELD_PATTERNS_ATLAS_TEXTURE, Identifier.of(MiddleEarth.MOD_ID, "entity/kite_shield_base_nopattern"));

        HEATER_SHIELD_BASE = new SpriteIdentifier(TexturedRenderLayers.SHIELD_PATTERNS_ATLAS_TEXTURE, Identifier.of(MiddleEarth.MOD_ID, "entity/heater_shield_base"));
        HEATER_SHIELD_BASE_NO_PATTERN = new SpriteIdentifier(TexturedRenderLayers.SHIELD_PATTERNS_ATLAS_TEXTURE, Identifier.of(MiddleEarth.MOD_ID, "entity/heater_shield_base_nopattern"));

        ROUND_SHIELD_BASE = new SpriteIdentifier(TexturedRenderLayers.SHIELD_PATTERNS_ATLAS_TEXTURE, Identifier.of(MiddleEarth.MOD_ID, "entity/round_shield_base"));
        ROUND_SHIELD_BASE_NO_PATTERN = new SpriteIdentifier(TexturedRenderLayers.SHIELD_PATTERNS_ATLAS_TEXTURE, Identifier.of(MiddleEarth.MOD_ID, "entity/round_shield_base_nopattern"));
    }

}
