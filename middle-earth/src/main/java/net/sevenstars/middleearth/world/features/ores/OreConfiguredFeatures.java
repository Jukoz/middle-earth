package net.sevenstars.middleearth.world.features.ores;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.registration.StoneBlockSets;
import net.sevenstars.middleearth.world.gen.ModFeatures;
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
    public static final RegistryKey<ConfiguredFeature<?, ?>> ANDESITE_ORE = registerKey("andesite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ASH_BLOCK_ORE = registerKey("ash_block_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ASHEN_DIRT_ORE = registerKey("ashen_dirt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ASHEN_STONE_DIRT_ORE = registerKey("ashen_dirt_stone_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ASHEN_GRAVEL = registerKey("ashen_gravel");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ASHEN_SAND = registerKey("ashen_sand");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ASHEN_GRAVEL_DIRT = registerKey("ashen_gravel_dirt");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ASHEN_GRAVEL_SAND = registerKey("ashen_gravel_sand");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ASHEN_STONE_GRAVEL = registerKey("ashen_stone_gravel");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ASHEN_STONE_SAND = registerKey("ashen_stone_sand");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BASALT_ORE = registerKey("basalt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SMOOTH_BASALT_ORE = registerKey("smooth_basalt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BLACK_SAND_ORE = registerKey("black_sand_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> BLUE_TUFF_ORE = registerKey("blue_tuff_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CALCITE_ORE = registerKey("calcite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> COARSE_DIRT_ORE = registerKey("coarse_dirt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> COARSE_LOAM_ORE = registerKey("coarse_loam_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> COARSE_PEAT_ORE = registerKey("coarse_peat_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> COARSE_SILT_ORE = registerKey("coarse_silt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CORRUPTED_MOSS_DISK = registerKey("corrupted_moss_disk");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DIORITE_ORE = registerKey("diorite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DIRT_TO_GRASS_ORE = registerKey("dirt_to_grass_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ROOTED_DIRT_ORE = registerKey("rooted_dirt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRASSY_DIRT_ORE = registerKey("grassy_dirt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRASSY_CHALKSOIL_ORE = registerKey("grassy_chalksoil_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRASSY_LOAM_ORE = registerKey("grassy_loam_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRASSY_PEAT_ORE = registerKey("grassy_peat_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRASSY_SILT_ORE = registerKey("grassy_silt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DIRTY_ROOTS_ORE = registerKey("dirty_roots");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DOLOMITE_ORE = registerKey("dolomite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DRIPSTONE_ORE = registerKey("dripstone_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DRY_DIRT_ORE = registerKey("dry_dirt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FOREST_MOSS_DISK = registerKey("forest_moss_disk");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRAVEL_ORE = registerKey("gravel_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SNOWY_DIRT_ORE = registerKey("snowy_dirt_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SNOWY_GRASS_ORE = registerKey("snowy_grass_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRANITE_ORE = registerKey("granite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> STONE_GRASS_ORE = registerKey("stone_grass_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SAND_TO_GRASS_ORE = registerKey("sand_to_grass_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GONLUIN_GRASS_ORE = registerKey("gonluin_grass_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LIMESTONE_ORE = registerKey("limestone_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> LORIEN_PODZOL_ORE = registerKey("lorien_podzol_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MIRE_ORE = registerKey("mire_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> MUD_ORE = registerKey("mud_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PACKED_MUD_ORE = registerKey("packed_mud_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> OLD_PODZOL_ORE = registerKey("old_podzol_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> OLD_STONE_PODZOL_ORE = registerKey("old_stone_podzol_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PODZOL_ORE = registerKey("podzol_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> POWDER_SNOW_ORE = registerKey("powder_snow_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> RIVER_SAND_ORE = registerKey("river_sand_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SAND_ORE = registerKey("sand_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SNOW_BLOCK_ORE = registerKey("snow_block_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SOUL_SAND_ORE = registerKey("soul_sand_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> CALCITE_STONE_ORE = registerKey("calcite_stone_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRASS_TO_STONE_ORE = registerKey("grass_to_stone_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GRASS_TO_GRANITE_ORE = registerKey("grass_to_granite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> TUFF_ORE = registerKey("stone_tuff_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> TURF_ORE = registerKey("turf_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> WHITE_SAND_ORE = registerKey("white_sand_ore");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        TagMatchRuleTest dirtTest = new TagMatchRuleTest(BlockTags.DIRT);
        BlockMatchRuleTest grassTest = new BlockMatchRuleTest(Blocks.GRASS_BLOCK);
        BlockMatchRuleTest chalkGrassTest = new BlockMatchRuleTest(ModBlocks.PEAT_GRASS_BLOCK);
        BlockMatchRuleTest loamGrassTest = new BlockMatchRuleTest(ModBlocks.LOAM_GRASS_BLOCK);
        BlockMatchRuleTest peatGrassTest = new BlockMatchRuleTest(ModBlocks.PEAT_GRASS_BLOCK);
        BlockMatchRuleTest siltGrassTest = new BlockMatchRuleTest(ModBlocks.SILT_GRASS_BLOCK);
        BlockMatchRuleTest ashenStoneTest = new BlockMatchRuleTest(StoneBlockSets.ASHENSTONE_SET.baseBlocks.base());
        BlockMatchRuleTest ashenGravelTest = new BlockMatchRuleTest(ModBlocks.ASHEN_GRAVEL);
        TagMatchRuleTest sandTest = new TagMatchRuleTest(BlockTags.SAND);
        TagMatchRuleTest stoneTest = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);

        List<OreFeatureConfig.Target> ashenStoneList = List.of(
                OreFeatureConfig.createTarget(dirtTest, ModBlocks.ASH_BLOCK.getDefaultState()),
                OreFeatureConfig.createTarget(stoneTest, ModBlocks.ASH_BLOCK.getDefaultState()),
                OreFeatureConfig.createTarget(ashenStoneTest, ModBlocks.ASH_BLOCK.getDefaultState()));
        List<OreFeatureConfig.Target> calciteList = List.of(
                OreFeatureConfig.createTarget(stoneTest, Blocks.CALCITE.getDefaultState()),
                OreFeatureConfig.createTarget(dirtTest, Blocks.CALCITE.getDefaultState()));
        List<OreFeatureConfig.Target> powderSnowList = List.of(
                OreFeatureConfig.createTarget(dirtTest, Blocks.POWDER_SNOW.getDefaultState()),
                OreFeatureConfig.createTarget(new TagMatchRuleTest(BlockTags.SNOW), Blocks.POWDER_SNOW.getDefaultState()));

        ConfiguredFeatures.register(featureRegisterable, ANDESITE_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, Blocks.ANDESITE.getDefaultState(), 64, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, ASH_BLOCK_ORE, Feature.ORE,
                new OreFeatureConfig(ashenStoneList, 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, ASHEN_DIRT_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.ASHEN_DIRT.getDefaultState(), 64, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, ASHEN_STONE_DIRT_ORE, Feature.ORE,
                new OreFeatureConfig(ashenStoneTest, ModBlocks.ASHEN_DIRT.getDefaultState(), 64, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, ASHEN_GRAVEL, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.ASHEN_GRAVEL.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, ASHEN_SAND, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.ASHEN_SAND.getDefaultState(), 48, 0.25f));

        ConfiguredFeatures.register(featureRegisterable, ASHEN_GRAVEL_DIRT, Feature.ORE,
                new OreFeatureConfig(ashenGravelTest, ModBlocks.ASHEN_DIRT.getDefaultState(), 48, 0.25f));
        ConfiguredFeatures.register(featureRegisterable, ASHEN_GRAVEL_SAND, Feature.ORE,
                new OreFeatureConfig(ashenGravelTest, ModBlocks.ASHEN_SAND.getDefaultState(), 48, 0.25f));

        ConfiguredFeatures.register(featureRegisterable, ASHEN_STONE_GRAVEL, Feature.ORE,
                new OreFeatureConfig(ashenStoneTest, ModBlocks.ASHEN_GRAVEL.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, ASHEN_STONE_SAND, Feature.ORE,
                new OreFeatureConfig(ashenStoneTest, ModBlocks.ASHEN_SAND.getDefaultState(), 48, 0.25f));

        ConfiguredFeatures.register(featureRegisterable, BASALT_ORE, Feature.ORE,
                new OreFeatureConfig(ashenStoneTest, Blocks.BASALT.getDefaultState(), 64, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, SMOOTH_BASALT_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, Blocks.SMOOTH_BASALT.getDefaultState(), 64, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, BLACK_SAND_ORE, Feature.ORE,
                new OreFeatureConfig(ashenStoneTest, ModBlocks.BLACK_SAND.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, CALCITE_ORE, Feature.ORE,
                new OreFeatureConfig(calciteList, 64, 0.2f));

        ConfiguredFeatures.register(featureRegisterable, BLUE_TUFF_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, StoneBlockSets.BLUE_TUFF_SET.baseBlocks.base().getDefaultState(), 64));

        ConfiguredFeatures.register(featureRegisterable, COARSE_DIRT_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.COARSE_DIRT.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, COARSE_LOAM_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.COARSE_LOAM.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, COARSE_PEAT_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.COARSE_PEAT.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, COARSE_SILT_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.COARSE_SILT.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, CORRUPTED_MOSS_DISK, Feature.DISK,
                new DiskFeatureConfig(PredicatedStateProvider.of(ModNatureBlocks.CORRUPTED_MOSS_BLOCK),
                        BlockPredicate.matchingBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.STONE)), UniformIntProvider.create(1, 3), 1));

        ConfiguredFeatures.register(featureRegisterable, DIORITE_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, Blocks.DIORITE.getDefaultState(), 64, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, DIRT_TO_GRASS_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.GRASS_BLOCK.getDefaultState(), 64, 0.2f));

        ConfiguredFeatures.register(featureRegisterable, DIRTY_ROOTS_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.DIRTY_ROOTS.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, ROOTED_DIRT_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.ROOTED_DIRT.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, GRASSY_DIRT_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.GRASSY_DIRT.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, GRASSY_CHALKSOIL_ORE, Feature.ORE,
                new OreFeatureConfig(chalkGrassTest, ModBlocks.GRASSY_CHALKSOIL.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, GRASSY_LOAM_ORE, Feature.ORE,
                new OreFeatureConfig(loamGrassTest, ModBlocks.GRASSY_LOAM.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, GRASSY_PEAT_ORE, Feature.ORE,
                new OreFeatureConfig(peatGrassTest, ModBlocks.GRASSY_PEAT.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, GRASSY_SILT_ORE, Feature.ORE,
                new OreFeatureConfig(siltGrassTest, ModBlocks.GRASSY_SILT.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, DOLOMITE_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, StoneBlockSets.DOLOMITE_SET.baseBlocks.base().getDefaultState(), 64, 0.25f));

        ConfiguredFeatures.register(featureRegisterable, DRIPSTONE_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, Blocks.DRIPSTONE_BLOCK.getDefaultState(), 64, 0.25f));

        ConfiguredFeatures.register(featureRegisterable, DRY_DIRT_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.DRY_DIRT.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, FOREST_MOSS_DISK, Feature.DISK,
                new DiskFeatureConfig(PredicatedStateProvider.of(ModNatureBlocks.FOREST_MOSS_BLOCK),
                        BlockPredicate.matchingBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.STONE)), UniformIntProvider.create(1, 3), 1));

        ConfiguredFeatures.register(featureRegisterable, GRAVEL_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.GRAVEL.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, SNOWY_DIRT_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.SNOWY_DIRT.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, SNOWY_GRASS_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.SNOWY_GRASS_BLOCK.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, GRANITE_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, Blocks.GRANITE.getDefaultState(), 64, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, SAND_TO_GRASS_ORE, ModFeatures.SURFACE_ORE,
                new SurfaceOreFeatureConfig(sandTest, Blocks.GRASS_BLOCK.getDefaultState(), 64));

        ConfiguredFeatures.register(featureRegisterable, STONE_GRASS_ORE, ModFeatures.SURFACE_ORE,
                new SurfaceOreFeatureConfig(stoneTest, Blocks.GRASS_BLOCK.getDefaultState(), 64));

        ConfiguredFeatures.register(featureRegisterable, GONLUIN_GRASS_ORE, Feature.DISK,
                new DiskFeatureConfig(PredicatedStateProvider.of(Blocks.GRASS_BLOCK),
                        BlockPredicate.matchingBlocks(List.of(StoneBlockSets.KHAGALABAN_SET.baseBlocks.base())), UniformIntProvider.create(3, 6), 1));

        ConfiguredFeatures.register(featureRegisterable, OLD_PODZOL_ORE, Feature.ORE,
                new OreFeatureConfig(grassTest, ModNatureBlocks.OLD_PODZOL.getDefaultState(), 64, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, OLD_STONE_PODZOL_ORE, Feature.ORE,
                new OreFeatureConfig(ashenStoneTest, ModNatureBlocks.OLD_PODZOL.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, LIMESTONE_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, StoneBlockSets.LIMESTONE_SET.baseBlocks.base().getDefaultState(), 64, 0.25f));
        ConfiguredFeatures.register(featureRegisterable, LORIEN_PODZOL_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModNatureBlocks.LORIEN_PODZOL.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, MIRE_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.MIRE.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, MUD_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.MUD.getDefaultState(), 48, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, PACKED_MUD_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.PACKED_MUD.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, PODZOL_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.PODZOL.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, POWDER_SNOW_ORE, Feature.ORE,
                new OreFeatureConfig(powderSnowList, 41));

        ConfiguredFeatures.register(featureRegisterable, RIVER_SAND_ORE, Feature.DISK,
                new DiskFeatureConfig(PredicatedStateProvider.of(ModBlocks.RIVER_SAND), BlockPredicate.matchingBlocks(List.of(Blocks.DIRT, Blocks.SAND, Blocks.GRASS_BLOCK)), UniformIntProvider.create(2, 5), 2));

        ConfiguredFeatures.register(featureRegisterable, SAND_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.SAND.getDefaultState(), 48, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, SNOW_BLOCK_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, Blocks.SNOW_BLOCK.getDefaultState(), 64, 0.5f));

        ConfiguredFeatures.register(featureRegisterable, SOUL_SAND_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.SOUL_SAND.getDefaultState(), 32, 0.4f));

        ConfiguredFeatures.register(featureRegisterable, CALCITE_STONE_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, Blocks.STONE.getDefaultState(), 64, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, GRASS_TO_STONE_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.STONE.getDefaultState(), 64, 0.25f));
        ConfiguredFeatures.register(featureRegisterable, GRASS_TO_GRANITE_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, Blocks.GRANITE.getDefaultState(), 64, 0.25f));

        ConfiguredFeatures.register(featureRegisterable, TUFF_ORE, Feature.ORE,
                new OreFeatureConfig(stoneTest, Blocks.TUFF.getDefaultState(), 48, 0.25f));

        ConfiguredFeatures.register(featureRegisterable, TURF_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.TURF.getDefaultState(), 64, 0.25f));

        ConfiguredFeatures.register(featureRegisterable, WHITE_SAND_ORE, Feature.ORE,
                new OreFeatureConfig(dirtTest, ModBlocks.WHITE_SAND.getDefaultState(), 48, 0.4f));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }
}
