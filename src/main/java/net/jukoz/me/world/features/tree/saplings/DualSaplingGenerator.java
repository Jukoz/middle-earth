package net.jukoz.me.world.features.tree.saplings;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class DualSaplingGenerator {
    private final float weight;
    private final RegistryKey<ConfiguredFeature<?, ?>> treeKey1;
    private final RegistryKey<ConfiguredFeature<?, ?>> treeKey2;

    public DualSaplingGenerator(String name, float weight, RegistryKey<ConfiguredFeature<?, ?>> treeKey1, RegistryKey<ConfiguredFeature<?, ?>> treeKey2) {
        this.treeKey1 = treeKey1;
        this.treeKey2 = treeKey2;
        if(weight > 1.0f) throw new IllegalArgumentException("Cannot have a weight more than 1.0f for sapling generator");
        this.weight = weight;
    }

    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        if(Math.random() < weight) {
            return treeKey1;
        }
        return treeKey2;
    }
}
