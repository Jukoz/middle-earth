package net.jukoz.me.world.features.ores;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.world.features.tree.trunks.ArcTrunkPlacer;
import net.minecraft.block.BlockState;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;
public class SurfaceOreFeatureConfig extends OreFeatureConfig {

    public static final Codec<SurfaceOreFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.list(Target.CODEC).fieldOf("targets").forGetter(config -> config.targets),
                    Codec.INT.fieldOf("size").forGetter(config -> config.size),
                    Codec.FLOAT.fieldOf("discardOnAirChance").forGetter(config -> config.discardOnAirChance)
            ).apply(instance, SurfaceOreFeatureConfig::new));

    public SurfaceOreFeatureConfig(List<OreFeatureConfig.Target> targets, int size, float discardOnAirChance) {
        super(targets, size, discardOnAirChance);
    }

    public SurfaceOreFeatureConfig(RuleTest test, BlockState state, int size) {
        super(test, state, size);
    }
}

