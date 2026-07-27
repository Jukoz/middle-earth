package net.sevenstars.middleearth.world.features.tree.saplings;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ModLargeSaplingGenerator {
    private final ResourceKey<ConfiguredFeature<?, ?>> treeKey;
    private final ResourceKey<ConfiguredFeature<?, ?>> largeTreeKey;

    public ModLargeSaplingGenerator(ResourceKey<ConfiguredFeature<?, ?>> treeKey, ResourceKey<ConfiguredFeature<?, ?>> largeTreeKey) {
        this.treeKey = treeKey;
        this.largeTreeKey = largeTreeKey;
    }

    protected ResourceKey<ConfiguredFeature<?, ?>> getTreeFeature(RandomSource random, boolean bees) {
        return treeKey;
    }

    protected ResourceKey<ConfiguredFeature<?, ?>> getLargeTreeFeature(RandomSource random) {
        return largeTreeKey;
    }
}
