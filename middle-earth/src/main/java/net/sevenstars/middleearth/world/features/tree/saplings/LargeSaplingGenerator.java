package net.sevenstars.middleearth.world.features.tree.saplings;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class LargeSaplingGenerator {
    private final RegistryKey<ConfiguredFeature<?, ?>> treeKey;
    private final RegistryKey<ConfiguredFeature<?, ?>> largeTreeKey;

    public LargeSaplingGenerator(RegistryKey<ConfiguredFeature<?, ?>> treeKey, RegistryKey<ConfiguredFeature<?, ?>> largeTreeKey) {
        this.treeKey = treeKey;
        this.largeTreeKey = largeTreeKey;
    }

    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return treeKey;
    }

    protected RegistryKey<ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
        return largeTreeKey;
    }
}
