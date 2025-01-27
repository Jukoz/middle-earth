package net.sevenstars.middleearth.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.model.BlockStatesLoader;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.List;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class MEModelLoader extends ModelLoader {
    public static final SpriteIdentifier KITE_SHIELD_BASE;
    public static final SpriteIdentifier KITE_SHIELD_BASE_NO_PATTERN;

    public static final SpriteIdentifier HEATER_SHIELD_BASE;
    public static final SpriteIdentifier HEATER_SHIELD_BASE_NO_PATTERN;

    public static final SpriteIdentifier ROUND_SHIELD_BASE;
    public static final SpriteIdentifier ROUND_SHIELD_BASE_NO_PATTERN;

    public MEModelLoader(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels, Map<Identifier, List<BlockStatesLoader.SourceTrackedData>> blockStates) {
        super(blockColors, profiler, jsonUnbakedModels, blockStates);
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
