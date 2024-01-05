package net.jukoz.me.world.features.tree.saplings;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class DualSaplingGenerator extends SaplingGenerator {
    private final float weight;
    private final RegistryKey<ConfiguredFeature<?, ?>> treeKey1;
    private final RegistryKey<ConfiguredFeature<?, ?>> treeKey2;

    public DualSaplingGenerator(float weight, RegistryKey<ConfiguredFeature<?, ?>> treeKey1, RegistryKey<ConfiguredFeature<?, ?>> treeKey2) {
        this.treeKey1 = treeKey1;
        this.treeKey2 = treeKey2;
        if(weight > 1.0f) throw new IllegalArgumentException("Cannot have a weight more than 1.0f for sapling generator");
        this.weight = weight;
    }

    @Nullable
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        if(Math.random() < weight) {
            return treeKey1;
        }
        return treeKey2;
    }
}
