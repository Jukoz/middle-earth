package net.jukoz.me.world.features.columns;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.SmallDripstoneFeatureConfig;

public class SmallPointedStoneFeatureConfig implements FeatureConfig {
    public static final Codec<SmallPointedStoneFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_taller_dripstone").orElse(0.2F).forGetter((config) -> {
            return config.chanceOfTallerDripstone;
        }), BlockState.CODEC.fieldOf("blockstate").forGetter((config) -> {
            return config.blockState;
        }), BlockState.CODEC.fieldOf("pointed_blockstate").forGetter((config) -> {
            return config.pointedBlockState;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_directional_spread").orElse(0.7F).forGetter((config) -> {
            return config.chanceOfDirectionalSpread;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius2").orElse(0.5F).forGetter((config) -> {
            return config.chanceOfSpreadRadius2;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius3").orElse(0.5F).forGetter((config) -> {
            return config.chanceOfSpreadRadius3;
        })).apply(instance, SmallPointedStoneFeatureConfig::new);
    });
    public final float chanceOfTallerDripstone;
    public final BlockState blockState;
    public final BlockState pointedBlockState;
    public final float chanceOfDirectionalSpread;
    public final float chanceOfSpreadRadius2;
    public final float chanceOfSpreadRadius3;

    public SmallPointedStoneFeatureConfig(float chanceOfTallerDripstone, BlockState blockState, BlockState pointedBlockState,
                                       float chanceOfDirectionalSpread, float chanceOfSpreadRadius2, float chanceOfSpreadRadius3) {
        this.chanceOfTallerDripstone = chanceOfTallerDripstone;
        this.blockState = blockState;
        this.pointedBlockState = pointedBlockState;
        this.chanceOfDirectionalSpread = chanceOfDirectionalSpread;
        this.chanceOfSpreadRadius2 = chanceOfSpreadRadius2;
        this.chanceOfSpreadRadius3 = chanceOfSpreadRadius3;
    }
}