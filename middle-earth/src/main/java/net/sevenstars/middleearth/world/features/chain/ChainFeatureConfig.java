package net.sevenstars.middleearth.world.features.chain;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.List;

public class ChainFeatureConfig implements FeatureConfig {
    public static final Codec<ChainFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.INT.fieldOf("max_length").forGetter(config ->config.maxLength),
                    Codec.BOOL.fieldOf("fallback").forGetter(config -> config.fallback),
                    Direction.CODEC.fieldOf("direction").forGetter(config -> config.direction),
                    Direction.CODEC.fieldOf("block_direction").forGetter(config -> config.blockDirection),
                    BlockState.CODEC.fieldOf("chain_block").forGetter(config -> config.chainBlock),
                    BlockState.CODEC.fieldOf("start_block").forGetter(config -> config.startBlock),
                    BlockState.CODEC.fieldOf("end_block").forGetter(config -> config.endBlock)
            ).apply(instance, ChainFeatureConfig::new));

    public final int maxLength;
    public final boolean fallback;
    public final Direction direction;
    public final Direction blockDirection;
    public final BlockState chainBlock;
    public final BlockState startBlock;
    public final BlockState endBlock;

    public ChainFeatureConfig(int maxLength, boolean fallback, Direction direction, Direction blockDirection, BlockState chainBlock, BlockState startBlock, BlockState endBlock) {
        this.maxLength = maxLength;
        this.fallback = fallback;
        this.direction = direction;
        this.blockDirection = blockDirection;
        this.chainBlock = chainBlock;
        this.startBlock = startBlock;
        this.endBlock = endBlock;
    }
    public ChainFeatureConfig(int maxLength, boolean fallback, Direction direction, Direction blockDirection, BlockState chainBlock) {
        this.maxLength = maxLength;
        this.fallback = fallback;
        this.direction = direction;
        this.blockDirection = blockDirection;
        this.chainBlock = chainBlock;
        this.startBlock = chainBlock;
        this.endBlock = chainBlock;
    }
}

