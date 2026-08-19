package net.sevenstars.middleearth.world.features.tree.saplings;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class DualSaplingGenerator {
    private final float weight;
    private final ResourceKey<ConfiguredFeature<?, ?>> treeKey1;
    private final ResourceKey<ConfiguredFeature<?, ?>> treeKey2;

    public DualSaplingGenerator(String name, float weight, ResourceKey<ConfiguredFeature<?, ?>> treeKey1, ResourceKey<ConfiguredFeature<?, ?>> treeKey2) {
        this.treeKey1 = treeKey1;
        this.treeKey2 = treeKey2;
        if(weight > 1.0f) throw new IllegalArgumentException("Cannot have a weight more than 1.0f for sapling generator");
        this.weight = weight;
    }

    protected ResourceKey<ConfiguredFeature<?, ?>> getTreeFeature(RandomSource random, boolean bees) {
        if(random.nextDouble() < weight) {
            return treeKey1;
        }
        return treeKey2;
    }
}
