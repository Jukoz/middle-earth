package net.sevenstars.middleearth.world.features.tree.saplings;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class ModSaplingGenerator {
    private final RegistryKey<ConfiguredFeature<?, ?>> treeKey;

    public ModSaplingGenerator(RegistryKey<ConfiguredFeature<?, ?>> treeKey) {
        this.treeKey = treeKey;
    }

    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return treeKey;
    }
}
