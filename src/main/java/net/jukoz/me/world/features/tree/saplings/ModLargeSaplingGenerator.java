package net.jukoz.me.world.features.tree.saplings;

import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class ModLargeSaplingGenerator extends LargeTreeSaplingGenerator {
    private final RegistryKey<ConfiguredFeature<?, ?>> treeKey;
    private final RegistryKey<ConfiguredFeature<?, ?>> largeTreeKey;

    public ModLargeSaplingGenerator(RegistryKey<ConfiguredFeature<?, ?>> treeKey, RegistryKey<ConfiguredFeature<?, ?>> largeTreeKey) {
        this.treeKey = treeKey;
        this.largeTreeKey = largeTreeKey;
    }

    @Nullable
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return treeKey;
    }

    @Nullable
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getLargeTreeFeature(Random random) {
        return largeTreeKey;
    }
}
