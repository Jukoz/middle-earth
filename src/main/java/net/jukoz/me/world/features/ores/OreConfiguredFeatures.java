package net.jukoz.me.world.features.ores;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.StoneBlockSets;
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
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.PredicatedStateProvider;

import java.util.List;

public class OreConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> ANDESITE_ORE = registerKey("andesite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ASH_BLOCK_ORE = registerKey("ash_block_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ASHEN_DIRT_ORE = registerKey("ashen_dirt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ASHEN_DIRT_STONE_ORE = registerKey("ashen_dirt_stone_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BASALT_ORE = registerKey("basalt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BLACK_CONCRETE_POWDER_ORE = registerKey("black_concrete_powder_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BLUE_TUFF_ORE = registerKey("blue_tuff_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CALCITE_ORE = registerKey("calcite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> COARSE_DIRT_ORE = registerKey("coarse_dirt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CORRUPTED_MOSS_DISK = registerKey("corrupted_moss_disk");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DIORITE_ORE = registerKey("diorite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DOLOMITE_ORE = registerKey("dolomite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DRIPSTONE_ORE = registerKey("dripstone_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DRY_DIRT_ORE = registerKey("dry_dirt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FOREST_MOSS_DISK = registerKey("forest_moss_disk");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FROZEN_STONE_ORE = registerKey("frozen_stone_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRAVEL_ORE = registerKey("gravel_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRANITE_ORE = registerKey("granite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> STONE_GRASS_ORE = registerKey("stone_grass_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GONLUIN_GRASS_ORE = registerKey("gonluin_grass_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LIMESTONE_ORE = registerKey("limestone_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MUD_ORE = registerKey("mud_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> OLD_PODZOL_ORE = registerKey("old_podzol_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PODZOL_ORE = registerKey("podzol_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SAND_ORE = registerKey("sand_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SNOW_BLOCK_ORE = registerKey("snow_block_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CALCITE_STONE_ORE = registerKey("calcite_stone_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRASS_STONE_ORE = registerKey("grass_stone_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> TUFF_ORE = registerKey("stone_tuff_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LIGHT_GRAY_CONCRETE_POWDER_ORE = registerKey("light_gray_concrete_powder_ore");
    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        TagMatchRuleTest dirtTest = new TagMatchRuleTest(BlockTags.DIRT);
        BlockMatchRuleTest grassTest = new BlockMatchRuleTest(Blocks.GRASS_BLOCK);
        BlockMatchRuleTest ashenStoneTest = new BlockMatchRuleTest(StoneBlockSets.ASHEN_STONE.base());
        TagMatchRuleTest stoneTest = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);

        ConfiguredFeatures.register(featureRegisterable, ANDESITE_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, Blocks.ANDESITE.getDefaultState(), 64, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, ASH_BLOCK_ORE, Feature.ORE,
                new OreFeatureConfig(ashenStoneTest, ModBlocks.ASH_BLOCK.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, ASHEN_DIRT_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.ASHEN_DIRT.getDefaultState(), 64, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, ASHEN_DIRT_STONE_ORE, Feature.ORE,
                new OreFeatureConfig(ashenStoneTest, ModBlocks.ASHEN_DIRT.getDefaultState(), 64, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, BASALT_ORE, Feature.ORE,
                new OreFeatureConfig(ashenStoneTest, Blocks.BASALT.getDefaultState(), 64, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, BLACK_CONCRETE_POWDER_ORE, Feature.ORE,
                new OreFeatureConfig(ashenStoneTest, Blocks.BLACK_CONCRETE_POWDER.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, CALCITE_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, Blocks.CALCITE.getDefaultState(), 64));

        ConfiguredFeatures.register(featureRegisterable, BLUE_TUFF_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, StoneBlockSets.BLUE_TUFF.base().getDefaultState(), 64));

        ConfiguredFeatures.register(featureRegisterable, COARSE_DIRT_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.COARSE_DIRT.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, CORRUPTED_MOSS_DISK, Feature.DISK,
                new DiskFeatureConfig(PredicatedStateProvider.of(ModNatureBlocks.CORRUPTED_MOSS_BLOCK),
                        BlockPredicate.matchingBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.STONE)), UniformIntProvider.create(1, 3), 1));

        ConfiguredFeatures.register(featureRegisterable, DIORITE_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, Blocks.DIORITE.getDefaultState(), 64, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, DOLOMITE_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, StoneBlockSets.DOLOMITE.base().getDefaultState(), 64, 0.25f));

        ConfiguredFeatures.register(featureRegisterable, DRIPSTONE_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, Blocks.DRIPSTONE_BLOCK.getDefaultState(), 64, 0.25f));

        ConfiguredFeatures.register(featureRegisterable, DRY_DIRT_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.DRY_DIRT.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, FOREST_MOSS_DISK, Feature.DISK,
                new DiskFeatureConfig(PredicatedStateProvider.of(ModNatureBlocks.FOREST_MOSS_BLOCK),
                        BlockPredicate.matchingBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.STONE)), UniformIntProvider.create(1, 3), 1));

        ConfiguredFeatures.register(featureRegisterable, FROZEN_STONE_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, StoneBlockSets.FROZEN_STONE.base().getDefaultState(), 64, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, GRAVEL_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.GRAVEL.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, GRANITE_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, Blocks.GRANITE.getDefaultState(), 64, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, STONE_GRASS_ORE, Feature.DISK,
                new DiskFeatureConfig(PredicatedStateProvider.of(Blocks.GRASS_BLOCK),
                        BlockPredicate.matchingBlockTag(BlockTags.BASE_STONE_OVERWORLD), UniformIntProvider.create(2, 6), 1));
        ConfiguredFeatures.register(featureRegisterable, GONLUIN_GRASS_ORE, Feature.DISK,
                new DiskFeatureConfig(PredicatedStateProvider.of(Blocks.GRASS_BLOCK),
                        BlockPredicate.matchingBlocks(List.of(StoneBlockSets.GONLUIN.base())), UniformIntProvider.create(3, 6), 1));

        ConfiguredFeatures.register(featureRegisterable, OLD_PODZOL_ORE, Feature.ORE,
                new OreFeatureConfig(grassTest, ModNatureBlocks.OLD_PODZOL.getDefaultState(), 64, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, LIMESTONE_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, StoneBlockSets.LIMESTONE.base().getDefaultState(), 64, 0.25f));

        ConfiguredFeatures.register(featureRegisterable, MUD_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.MUD.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, PODZOL_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.PODZOL.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, SAND_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.SAND.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, SNOW_BLOCK_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, Blocks.SNOW_BLOCK.getDefaultState(), 64, 0.5f));

        ConfiguredFeatures.register(featureRegisterable, CALCITE_STONE_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, Blocks.STONE.getDefaultState(), 64, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, GRASS_STONE_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.STONE.getDefaultState(), 64, 0.25f));

        ConfiguredFeatures.register(featureRegisterable, TUFF_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, Blocks.TUFF.getDefaultState(), 48, 0.25f));

        ConfiguredFeatures.register(featureRegisterable, LIGHT_GRAY_CONCRETE_POWDER_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.LIGHT_GRAY_CONCRETE_POWDER.getDefaultState(), 48, 0.4f));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }
}
