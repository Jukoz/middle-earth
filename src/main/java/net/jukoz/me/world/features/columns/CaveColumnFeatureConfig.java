package net.jukoz.me.world.features.columns;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;

public class CaveColumnFeatureConfig implements FeatureConfig {
    public static final Codec<CaveColumnFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    IntProvider.createValidatingCodec(0, 3).fieldOf("reach").forGetter(config -> config.reach),
                    IntProvider.createValidatingCodec(1, 10).fieldOf("height").forGetter(config -> config.height),
                    BlockState.CODEC.fieldOf("blockstate").forGetter(config -> config.blockState)
            ).apply(instance, CaveColumnFeatureConfig::new));

    private final IntProvider reach;
    private final IntProvider height;
    private final BlockState blockState;

    public CaveColumnFeatureConfig(IntProvider reach, IntProvider height, BlockState blockState) {
        this.reach = reach;
        this.height = height;
        this.blockState = blockState;
    }

    public IntProvider getReach() {
        return this.reach;
    }

    public IntProvider getHeight() {
        return this.height;
    }

    public BlockState getBlockState() {
        return this.blockState;
    }
}

