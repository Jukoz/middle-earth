package net.sevenstars.middleearth.world.features.tree.backport;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;

public class FallenTreeFeatureConfig implements FeatureConfiguration {
    public static final Codec<FallenTreeFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            BlockStateProvider.CODEC
                                    .fieldOf("trunk_provider")
                                    .forGetter(config -> config.trunkProvider),
                            IntProvider.codec(0, 16)
                                    .fieldOf("log_length")
                                    .forGetter(config -> config.logLength),
                            TreeDecorator.CODEC
                                    .listOf()
                                    .fieldOf("stump_decorators")
                                    .forGetter(config -> config.stumpDecorators),
                            TreeDecorator.CODEC
                                    .listOf()
                                    .fieldOf("log_decorators")
                                    .forGetter(config -> config.logDecorators)
                    )
                    .apply(instance, FallenTreeFeatureConfig::new)
    );

    public final BlockStateProvider trunkProvider;
    public final IntProvider logLength;
    public final List<TreeDecorator> stumpDecorators;
    public final List<TreeDecorator> logDecorators;

    protected FallenTreeFeatureConfig(
            BlockStateProvider trunkProvider,
            IntProvider logLength,
            List<TreeDecorator> stumpDecorators,
            List<TreeDecorator> logDecorators
    ) {
        this.trunkProvider = trunkProvider;
        this.logLength = logLength;
        this.stumpDecorators = stumpDecorators;
        this.logDecorators = logDecorators;
    }

    public static class Builder {
        private final BlockStateProvider trunkProvider;
        private final IntProvider logLength;
        private List<TreeDecorator> stumpDecorators = new ArrayList<>();
        private List<TreeDecorator> logDecorators = new ArrayList<>();

        public Builder(BlockStateProvider trunkProvider, IntProvider logLength) {
            this.trunkProvider = trunkProvider;
            this.logLength = logLength;
        }

        public Builder stumpDecorators(List<TreeDecorator> stumpDecorators) {
            this.stumpDecorators = stumpDecorators;
            return this;
        }

        public Builder logDecorators(List<TreeDecorator> logDecorators) {
            this.logDecorators = logDecorators;
            return this;
        }

        public FallenTreeFeatureConfig build() {
            return new FallenTreeFeatureConfig(
                    this.trunkProvider,
                    this.logLength,
                    this.stumpDecorators,
                    this.logDecorators
            );
        }
    }
}
