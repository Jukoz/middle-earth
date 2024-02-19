package net.jukoz.me.world.features.ores;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.PredicatedStateProvider;

import java.util.List;

public class OreConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> BASALT_ORE = registerKey("basalt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> COARSE_DIRT_ORE = registerKey("coarse_dirt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CORRUPTED_MOSS_DISK = registerKey("corrupted_moss_disk");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DRY_DIRT_ORE = registerKey("dry_dirt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FOREST_MOSS_DISK = registerKey("forest_moss_disk");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRAVEL_ORE = registerKey("gravel_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> OLD_PODZOL_ORE = registerKey("old_podzol_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ASHEN_DIRT_ORE = registerKey("ashen_dirt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MUD_ORE = registerKey("mud_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PODZOL_ORE = registerKey("podzol_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LIGHT_GRAY_CONCRETE_POWDER_ORE = registerKey("light_gray_concrete_powder_ore");
    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        TagMatchRuleTest dirtTest = new TagMatchRuleTest(BlockTags.DIRT);
        BlockMatchRuleTest grassTest = new BlockMatchRuleTest(Blocks.GRASS_BLOCK);
        BlockMatchRuleTest mordorTest = new BlockMatchRuleTest(ModBlocks.ASH_BLOCK);

        ConfiguredFeatures.register(featureRegisterable, BASALT_ORE, Feature.ORE,
                new OreFeatureConfig(mordorTest, Blocks.BASALT.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, COARSE_DIRT_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.COARSE_DIRT.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, CORRUPTED_MOSS_DISK, Feature.DISK,
                new DiskFeatureConfig(PredicatedStateProvider.of(ModNatureBlocks.CORRUPTED_MOSS_BLOCK),
                        BlockPredicate.matchingBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.STONE)), UniformIntProvider.create(1, 3), 1));

        ConfiguredFeatures.register(featureRegisterable, DRY_DIRT_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.DRY_DIRT.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, FOREST_MOSS_DISK, Feature.DISK,
                new DiskFeatureConfig(PredicatedStateProvider.of(ModNatureBlocks.FOREST_MOSS_BLOCK),
                        BlockPredicate.matchingBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.STONE)), UniformIntProvider.create(1, 3), 1));

        ConfiguredFeatures.register(featureRegisterable, GRAVEL_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.GRAVEL.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, OLD_PODZOL_ORE, Feature.ORE,
                new OreFeatureConfig(grassTest, ModNatureBlocks.OLD_PODZOL.getDefaultState(), 64, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, ASHEN_DIRT_ORE, Feature.ORE,
                new OreFeatureConfig(mordorTest, ModBlocks.ASHEN_DIRT.getDefaultState(), 64, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, MUD_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.MUD.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, PODZOL_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.PODZOL.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, LIGHT_GRAY_CONCRETE_POWDER_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.LIGHT_GRAY_CONCRETE_POWDER.getDefaultState(), 48, 0.4f));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }
}
