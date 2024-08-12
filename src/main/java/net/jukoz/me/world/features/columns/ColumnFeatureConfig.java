package net.jukoz.me.world.features.columns;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.BasaltColumnsFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.function.BiFunction;

public class ColumnFeatureConfig implements FeatureConfig {
    public static final Codec<ColumnFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    IntProvider.createValidatingCodec(0, 3).fieldOf("reach").forGetter(config -> config.reach),
                    IntProvider.createValidatingCodec(1, 10).fieldOf("height").forGetter(config -> config.height),
                    BlockState.CODEC.fieldOf("blockstate").forGetter(config -> config.blockState)
            ).apply(instance, ColumnFeatureConfig::new));

    private final IntProvider reach;
    private final IntProvider height;
    private final BlockState blockState;

    public ColumnFeatureConfig(IntProvider reach, IntProvider height, BlockState blockState) {
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

