package net.sevenstars.middleearth.features.generation.worldfeatures.boulder;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

// the biomes still list these features: their configs and counts are what the chunk generator reads to write the
// boulders with the terrain (BakedBoulders), so placing them here as well would double them
public class BigBoulderFeature extends Feature<BigBoulderFeatureConfig> {
    public BigBoulderFeature(Codec<BigBoulderFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<BigBoulderFeatureConfig> context) {
        return false;
    }
}
