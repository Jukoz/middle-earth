package net.jukoz.me.world.features.ores;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.List;

public class ModOreFeatureConfig implements FeatureConfig {
    public static final Codec<ModOreFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.list(ModOreFeatureConfig.Target.CODEC).fieldOf("targets").forGetter((config) -> {
            return config.targets;
        }), BlockState.CODEC.fieldOf("blockstate").forGetter((config) -> {
            return config.blockState;
        }), Codec.intRange(0, 64).fieldOf("size").forGetter((config) -> {
            return config.size;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("discard_chance_on_air_exposure").forGetter((config) -> {
            return config.discardOnAirChance;
        })).apply(instance, ModOreFeatureConfig::new);
    });
    public final List<ModOreFeatureConfig.Target> targets;
    public final BlockState blockState;
    public final int size;
    public final float discardOnAirChance;

    public ModOreFeatureConfig(List<Target> targets, BlockState blockState, int size, float discardOnAirChance) {
        this.size = size;
        this.blockState = blockState;
        this.targets = targets;
        this.discardOnAirChance = discardOnAirChance;
    }

    public ModOreFeatureConfig(RuleTest test, BlockState state, BlockState blockState, int size, float discardOnAirChance) {
        this(ImmutableList.of(new Target(test, state)), blockState, size, discardOnAirChance);
    }

    public ModOreFeatureConfig(RuleTest test, BlockState state, BlockState blockState, int size) {
        this(ImmutableList.of(new Target(test, state)), blockState, size, 0.0F);
    }

    public static Target createTarget(RuleTest test, BlockState state) {
        return new Target(test, state);
    }

    public static class Target {
        public static final Codec<Target> CODEC = RecordCodecBuilder.create((instance) -> {
            return instance.group(RuleTest.TYPE_CODEC.fieldOf("target").forGetter((target) -> {
                return target.target;
            }), BlockState.CODEC.fieldOf("state").forGetter((target) -> {
                return target.state;
            })).apply(instance, Target::new);
        });
        public final RuleTest target;
        public final BlockState state;

        Target(RuleTest target, BlockState state) {
            this.target = target;
            this.state = state;
        }
    }
}