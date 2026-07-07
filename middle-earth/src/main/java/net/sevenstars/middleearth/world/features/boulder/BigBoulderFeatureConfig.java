package net.sevenstars.middleearth.world.features.boulder;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.List;

public class BigBoulderFeatureConfig implements FeatureConfig {
    public static final Codec<BigBoulderFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.FLOAT.fieldOf("width").forGetter(config ->config.width),
                    Codec.FLOAT.fieldOf("length").forGetter(config ->config.length),
                    Codec.FLOAT.fieldOf("height").forGetter(config -> config.height),
                    Codec.FLOAT.fieldOf("random_size").forGetter(config -> config.randomSize),
                    Codec.FLOAT.fieldOf("randomness").forGetter(config -> config.randomness),
                    Codec.list(BlockState.CODEC).fieldOf("blockstates").forGetter(config -> config.blockStates)
            ).apply(instance, BigBoulderFeatureConfig::new));

    public final float width;
    public final float length;
    public final float height;
    public final float randomSize;
    public final float randomness;
    public final List<BlockState> blockStates;

    public BigBoulderFeatureConfig(float width, float length, float height, float randomSize, float randomness, List<BlockState> blockStates) {
        this.width = width;
        this.length = length;
        this.height = height;
        this.randomSize = randomSize;
        this.randomness = randomness;
        this.blockStates = blockStates;
    }
}

