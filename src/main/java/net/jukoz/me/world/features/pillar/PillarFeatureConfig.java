package net.jukoz.me.world.features.pillar;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;

public class PillarFeatureConfig implements FeatureConfig {
    public static final Codec<PillarFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
            (Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range")).orElse(30).forGetter(config -> config.floorToCeilingSearchRange),
            (IntProvider.createValidatingCodec(1, 60).fieldOf("column_radius")).forGetter(config -> config.columnRadius),
            (FloatProvider.createValidatedCodec(0.0f, 20.0f).fieldOf("height_scale")).forGetter(config -> config.heightScale),
            (Codec.floatRange(0.1f, 1.0f).fieldOf("max_column_radius_to_cave_height_ratio")).forGetter(config -> Float.valueOf(config.maxColumnRadiusToCaveHeightRatio)),
            (FloatProvider.createValidatedCodec(0.1f, 10.0f).fieldOf("stalactite_bluntness")).forGetter(config -> config.stalactiteBluntness),
            (FloatProvider.createValidatedCodec(0.1f, 10.0f).fieldOf("stalagmite_bluntness")).forGetter(config -> config.stalagmiteBluntness),
            (FloatProvider.createValidatedCodec(0.0f, 2.0f).fieldOf("wind_speed")).forGetter(config -> config.windSpeed),
            (Codec.intRange(0, 100).fieldOf("min_radius_for_wind")).forGetter(config -> config.minRadiusForWind),
            (Codec.floatRange(0.0f, 5.0f).fieldOf("min_bluntness_for_wind")).forGetter(config -> Float.valueOf(config.minBluntnessForWind)),
            (BlockState.CODEC.fieldOf("blockstate")).forGetter(config -> config.blockState)).apply(instance, PillarFeatureConfig::new));

    public final int floorToCeilingSearchRange;
    public final IntProvider columnRadius;
    public final FloatProvider heightScale;
    public final float maxColumnRadiusToCaveHeightRatio;
    public final FloatProvider stalactiteBluntness;
    public final FloatProvider stalagmiteBluntness;
    public final FloatProvider windSpeed;
    public final int minRadiusForWind;
    public final float minBluntnessForWind;
    public final BlockState blockState;

    public PillarFeatureConfig(int floorToCeilingSearchRange, IntProvider columnRadius, FloatProvider heightScale,
                               float maxColumnRadiusToCaveHeightRatio, FloatProvider stalactiteBluntness, FloatProvider stalagmiteBluntness,
                               FloatProvider windSpeed, int minRadiusForWind, float minBluntnessForWind, BlockState blockstate) {
        this.floorToCeilingSearchRange = floorToCeilingSearchRange;
        this.columnRadius = columnRadius;
        this.heightScale = heightScale;
        this.maxColumnRadiusToCaveHeightRatio = maxColumnRadiusToCaveHeightRatio;
        this.stalactiteBluntness = stalactiteBluntness;
        this.stalagmiteBluntness = stalagmiteBluntness;
        this.windSpeed = windSpeed;
        this.minRadiusForWind = minRadiusForWind;
        this.minBluntnessForWind = minBluntnessForWind;
        this.blockState = blockstate;
    }
}

