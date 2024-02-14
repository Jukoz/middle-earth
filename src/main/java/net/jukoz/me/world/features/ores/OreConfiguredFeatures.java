package net.jukoz.me.world.features.ores;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;

public class OreConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> COARSE_DIRT_ORE = registerKey("coarse_dirt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DRY_DIRT_ORE = registerKey("dry_dirt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRAVEL_ORE = registerKey("gravel_disk");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PODZOL_ORE = registerKey("podzol_disk");
    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        TagMatchRuleTest dirtTest = new TagMatchRuleTest(BlockTags.DIRT);

        ConfiguredFeatures.register(featureRegisterable, COARSE_DIRT_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.COARSE_DIRT.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, DRY_DIRT_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.DRY_DIRT.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, GRAVEL_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.GRAVEL.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, PODZOL_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.PODZOL.getDefaultState(), 48, 0.4f));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }
}
