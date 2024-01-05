package net.jukoz.me.world.features.tree.saplings;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class ModSaplingGenerator extends SaplingGenerator {
    private final RegistryKey<ConfiguredFeature<?, ?>> treeKey;

    public ModSaplingGenerator(RegistryKey<ConfiguredFeature<?, ?>> treeKey) {
        this.treeKey = treeKey;
    }

    @Nullable
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return treeKey;
    }
}
