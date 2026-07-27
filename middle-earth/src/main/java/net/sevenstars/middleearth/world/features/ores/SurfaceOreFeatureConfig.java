package net.sevenstars.middleearth.world.features.ores;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
public class SurfaceOreFeatureConfig extends OreConfiguration {

    public static final Codec<SurfaceOreFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.list(TargetBlockState.CODEC).fieldOf("targets").forGetter(config -> config.targetStates),
                    Codec.INT.fieldOf("size").forGetter(config -> config.size),
                    Codec.FLOAT.fieldOf("discardOnAirChance").forGetter(config -> config.discardChanceOnAirExposure)
            ).apply(instance, SurfaceOreFeatureConfig::new));

    public SurfaceOreFeatureConfig(List<TargetBlockState> targets, int size, float discardOnAirChance) {
        super(targets, size, discardOnAirChance);
    }

    public SurfaceOreFeatureConfig(RuleTest test, BlockState state, int size) {
        super(test, state, size);
    }
}

