package net.sevenstars.middleearth.features.generation.worldfeatures.boulder;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

// biomes still list these because the chunk generator reads their configs to bake the boulders (BakedBoulders),
// so placing them here too would double them
public final class BigBoulderFeature extends Feature<BigBoulderFeatureConfig> {
    public BigBoulderFeature(Codec<BigBoulderFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<BigBoulderFeatureConfig> context) {
        return false;
    }
}
