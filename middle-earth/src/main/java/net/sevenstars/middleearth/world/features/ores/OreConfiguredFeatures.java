package net.sevenstars.middleearth.world.features.ores;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.GenericBlockSets;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.registration.StoneBlockSets;
import net.sevenstars.middleearth.world.gen.ModFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import java.util.List;

public class OreConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ANDESITE_ORE = registerKey("andesite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASH_BLOCK_ORE = registerKey("ash_block_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHEN_DIRT_ORE = registerKey("ashen_dirt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHEN_STONE_DIRT_ORE = registerKey("ashen_dirt_stone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHEN_GRAVEL = registerKey("ashen_gravel");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHEN_SAND = registerKey("ashen_sand");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHEN_GRAVEL_DIRT = registerKey("ashen_gravel_dirt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHEN_GRAVEL_SAND = registerKey("ashen_gravel_sand");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHEN_STONE_GRAVEL = registerKey("ashen_stone_gravel");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHEN_STONE_SAND = registerKey("ashen_stone_sand");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BASALT_ORE = registerKey("basalt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMOOTH_BASALT_ORE = registerKey("smooth_basalt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_SAND_ORE = registerKey("black_sand_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_TUFF_ORE = registerKey("blue_tuff_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_ORE = registerKey("calcite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_DIRT_ORE = registerKey("coarse_dirt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_CHALKSOIL_ORE = registerKey("coarse_chalksoil_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_LOAM_ORE = registerKey("coarse_loam_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_PEAT_ORE = registerKey("coarse_peat_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_SILT_ORE = registerKey("coarse_silt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORRUPTED_MOSS_DISK = registerKey("corrupted_moss_disk");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DIORITE_ORE = registerKey("diorite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DIRT_TO_GRASS_ORE = registerKey("dirt_to_grass_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ROOTED_DIRT_ORE = registerKey("rooted_dirt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASSY_DIRT_ORE = registerKey("grassy_dirt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASSY_CHALKSOIL_ORE = registerKey("grassy_chalksoil_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASSY_LOAM_ORE = registerKey("grassy_loam_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASSY_PEAT_ORE = registerKey("grassy_peat_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASSY_SILT_ORE = registerKey("grassy_silt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_LOAM_TO_GRASSY = registerKey("coarse_loam_to_grassy");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASSY_LOAM_TO_GRASS = registerKey("grassy_loam_to_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COARSE_LOAM_TO_FOUL_DIRT = registerKey("coarse_loam_to_foul_dirt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FOUL_DIRT_TO_WASTE_PILE = registerKey("foul_dirt_to_waste_pile");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRAVEL_TO_SILT_ORE = registerKey("gravel_to_silt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILT_TO_GRASSY_ORE = registerKey("silt_to_grassy_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILT_TO_COARSE_ORE = registerKey("silt_to_coarse_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DIRTY_ROOTS_ORE = registerKey("dirty_roots");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DOLOMITE_ORE = registerKey("dolomite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DRIPSTONE_ORE = registerKey("dripstone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DRY_DIRT_ORE = registerKey("dry_dirt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FOREST_MOSS_DISK = registerKey("forest_moss_disk");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRAVEL_ORE = registerKey("gravel_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SNOWY_DIRT_ORE = registerKey("snowy_dirt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SNOWY_GRASS_ORE = registerKey("snowy_grass_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRANITE_ORE = registerKey("granite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_GRASS_ORE = registerKey("stone_grass_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SAND_TO_GRASS_ORE = registerKey("sand_to_grass_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GONLUIN_GRASS_ORE = registerKey("gonluin_grass_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIMESTONE_ORE = registerKey("limestone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LORIEN_PODZOL_ORE = registerKey("lorien_podzol_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MIRE_ORE = registerKey("mire_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MUD_ORE = registerKey("mud_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PACKED_MUD_ORE = registerKey("packed_mud_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OLD_PODZOL_ORE = registerKey("old_podzol_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OLD_STONE_PODZOL_ORE = registerKey("old_stone_podzol_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PODZOL_ORE = registerKey("podzol_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POWDER_SNOW_ORE = registerKey("powder_snow_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RIVER_SAND_ORE = registerKey("river_sand_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SAND_ORE = registerKey("sand_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SNOW_BLOCK_ORE = registerKey("snow_block_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SOUL_SAND_ORE = registerKey("soul_sand_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALCITE_STONE_ORE = registerKey("calcite_stone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_TO_STONE_ORE = registerKey("grass_to_stone_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_TO_GRANITE_ORE = registerKey("grass_to_granite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TUFF_ORE = registerKey("stone_tuff_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TURF_ORE = registerKey("turf_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_SAND_ORE = registerKey("white_sand_ore");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable) {
        TagMatchTest dirtTest = new TagMatchTest(BlockTags.DIRT);
        BlockMatchTest grassTest = new BlockMatchTest(Blocks.GRASS_BLOCK);
        BlockMatchTest chalkGrassTest = new BlockMatchTest(ModBlocks.CHALKSOIL_GRASS_BLOCK);
        BlockMatchTest loamGrassTest = new BlockMatchTest(ModBlocks.LOAM_GRASS_BLOCK);
        BlockMatchTest coarseLoamTest = new BlockMatchTest(ModBlocks.COARSE_LOAM);
        BlockMatchTest grassyLoamTest = new BlockMatchTest(ModBlocks.GRASSY_LOAM);
        BlockMatchTest peatGrassTest = new BlockMatchTest(ModBlocks.PEAT_GRASS_BLOCK);
        BlockMatchTest siltGrassTest = new BlockMatchTest(ModBlocks.SILT_GRASS_BLOCK);
        BlockMatchTest ashenStoneTest = new BlockMatchTest(StoneBlockSets.ASHENSTONE_SET.baseBlocks.base());
        BlockMatchTest ashenGravelTest = new BlockMatchTest(ModBlocks.ASHEN_GRAVEL);
        BlockMatchTest foulDirtTest = new BlockMatchTest(ModBlocks.FOUL_DIRT);
        BlockMatchTest gravelTest = new BlockMatchTest(Blocks.GRAVEL);
        TagMatchTest sandTest = new TagMatchTest(BlockTags.SAND);
        TagMatchTest stoneTest = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);

        List<OreConfiguration.TargetBlockState> ashenStoneList = List.of(
                OreConfiguration.target(dirtTest, ModBlocks.ASH_BLOCK.defaultBlockState()),
                OreConfiguration.target(stoneTest, ModBlocks.ASH_BLOCK.defaultBlockState()),
                OreConfiguration.target(ashenStoneTest, ModBlocks.ASH_BLOCK.defaultBlockState()));
        List<OreConfiguration.TargetBlockState> calciteList = List.of(
                OreConfiguration.target(stoneTest, Blocks.CALCITE.defaultBlockState()),
                OreConfiguration.target(dirtTest, Blocks.CALCITE.defaultBlockState()));
        List<OreConfiguration.TargetBlockState> powderSnowList = List.of(
                OreConfiguration.target(dirtTest, Blocks.POWDER_SNOW.defaultBlockState()),
                OreConfiguration.target(new TagMatchTest(BlockTags.SNOW), Blocks.POWDER_SNOW.defaultBlockState()));

        FeatureUtils.register(featureRegisterable, ANDESITE_ORE, Feature.ORE,
                new OreConfiguration(stoneTest, Blocks.ANDESITE.defaultBlockState(), 64, 0.4f));

        FeatureUtils.register(featureRegisterable, ASH_BLOCK_ORE, Feature.ORE,
                new OreConfiguration(ashenStoneList, 48, 0.4f));

        FeatureUtils.register(featureRegisterable, ASHEN_DIRT_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, ModBlocks.ASHEN_DIRT.defaultBlockState(), 64, 0.4f));
        FeatureUtils.register(featureRegisterable, ASHEN_STONE_DIRT_ORE, Feature.ORE,
                new OreConfiguration(ashenStoneTest, ModBlocks.ASHEN_DIRT.defaultBlockState(), 64, 0.4f));

        FeatureUtils.register(featureRegisterable, ASHEN_GRAVEL, Feature.ORE,
                new OreConfiguration(dirtTest, ModBlocks.ASHEN_GRAVEL.defaultBlockState(), 48, 0.4f));
        FeatureUtils.register(featureRegisterable, ASHEN_SAND, Feature.ORE,
                new OreConfiguration(dirtTest, ModBlocks.ASHEN_SAND.defaultBlockState(), 48, 0.25f));

        FeatureUtils.register(featureRegisterable, ASHEN_GRAVEL_DIRT, Feature.ORE,
                new OreConfiguration(ashenGravelTest, ModBlocks.ASHEN_DIRT.defaultBlockState(), 48, 0.25f));
        FeatureUtils.register(featureRegisterable, ASHEN_GRAVEL_SAND, Feature.ORE,
                new OreConfiguration(ashenGravelTest, ModBlocks.ASHEN_SAND.defaultBlockState(), 48, 0.25f));

        FeatureUtils.register(featureRegisterable, ASHEN_STONE_GRAVEL, Feature.ORE,
                new OreConfiguration(ashenStoneTest, ModBlocks.ASHEN_GRAVEL.defaultBlockState(), 48, 0.4f));
        FeatureUtils.register(featureRegisterable, ASHEN_STONE_SAND, Feature.ORE,
                new OreConfiguration(ashenStoneTest, ModBlocks.ASHEN_SAND.defaultBlockState(), 48, 0.25f));

        FeatureUtils.register(featureRegisterable, BASALT_ORE, Feature.ORE,
                new OreConfiguration(ashenStoneTest, Blocks.BASALT.defaultBlockState(), 64, 0.4f));

        FeatureUtils.register(featureRegisterable, SMOOTH_BASALT_ORE, Feature.ORE,
                new OreConfiguration(stoneTest, Blocks.SMOOTH_BASALT.defaultBlockState(), 64, 0.4f));

        FeatureUtils.register(featureRegisterable, BLACK_SAND_ORE, Feature.ORE,
                new OreConfiguration(ashenStoneTest, ModBlocks.BLACK_SAND.defaultBlockState(), 48, 0.4f));

        FeatureUtils.register(featureRegisterable, CALCITE_ORE, Feature.ORE,
                new OreConfiguration(calciteList, 64, 0.2f));

        FeatureUtils.register(featureRegisterable, BLUE_TUFF_ORE, Feature.ORE,
                new OreConfiguration(stoneTest, StoneBlockSets.BLUE_TUFF_SET.baseBlocks.base().defaultBlockState(), 64));

        FeatureUtils.register(featureRegisterable, COARSE_DIRT_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, Blocks.COARSE_DIRT.defaultBlockState(), 48, 0.4f));
        FeatureUtils.register(featureRegisterable, COARSE_CHALKSOIL_ORE, Feature.ORE,
                new OreConfiguration(chalkGrassTest, ModBlocks.COARSE_CHALKSOIL.defaultBlockState(), 48, 0.4f));
        FeatureUtils.register(featureRegisterable, COARSE_LOAM_ORE, Feature.ORE,
                new OreConfiguration(loamGrassTest, ModBlocks.COARSE_LOAM.defaultBlockState(), 48, 0.4f));
        FeatureUtils.register(featureRegisterable, COARSE_PEAT_ORE, Feature.ORE,
                new OreConfiguration(peatGrassTest, ModBlocks.COARSE_PEAT.defaultBlockState(), 48, 0.4f));
        FeatureUtils.register(featureRegisterable, COARSE_SILT_ORE, Feature.ORE,
                new OreConfiguration(siltGrassTest, ModBlocks.COARSE_SILT.defaultBlockState(), 48, 0.4f));

        FeatureUtils.register(featureRegisterable, CORRUPTED_MOSS_DISK, Feature.DISK,
                new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModNatureBlocks.CORRUPTED_MOSS_BLOCK),
                        BlockPredicate.matchesBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.STONE)), UniformInt.of(1, 3), 1));

        FeatureUtils.register(featureRegisterable, DIORITE_ORE, Feature.ORE,
                new OreConfiguration(stoneTest, Blocks.DIORITE.defaultBlockState(), 64, 0.4f));

        FeatureUtils.register(featureRegisterable, DIRT_TO_GRASS_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, Blocks.GRASS_BLOCK.defaultBlockState(), 64, 0.2f));


        FeatureUtils.register(featureRegisterable, DIRTY_ROOTS_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, ModBlocks.DIRTY_ROOTS.defaultBlockState(), 48, 0.4f));
        FeatureUtils.register(featureRegisterable, ROOTED_DIRT_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, Blocks.ROOTED_DIRT.defaultBlockState(), 48, 0.4f));

        FeatureUtils.register(featureRegisterable, GRASSY_DIRT_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, ModBlocks.GRASSY_DIRT.defaultBlockState(), 48, 0.4f));
        FeatureUtils.register(featureRegisterable, GRASSY_CHALKSOIL_ORE, Feature.ORE,
                new OreConfiguration(chalkGrassTest, ModBlocks.GRASSY_CHALKSOIL.defaultBlockState(), 48, 0.4f));
        FeatureUtils.register(featureRegisterable, GRASSY_LOAM_ORE, Feature.ORE,
                new OreConfiguration(loamGrassTest, ModBlocks.GRASSY_LOAM.defaultBlockState(), 48, 0.4f));
        FeatureUtils.register(featureRegisterable, GRASSY_PEAT_ORE, Feature.ORE,
                new OreConfiguration(peatGrassTest, ModBlocks.GRASSY_PEAT.defaultBlockState(), 48, 0.4f));
        FeatureUtils.register(featureRegisterable, GRASSY_SILT_ORE, Feature.ORE,
                new OreConfiguration(siltGrassTest, ModBlocks.GRASSY_SILT.defaultBlockState(), 48, 0.4f));


        FeatureUtils.register(featureRegisterable, COARSE_LOAM_TO_GRASSY, Feature.ORE,
                new OreConfiguration(dirtTest, ModBlocks.GRASSY_LOAM.defaultBlockState(), 64, 0.2f));
        FeatureUtils.register(featureRegisterable, GRASSY_LOAM_TO_GRASS, Feature.ORE,
                new OreConfiguration(grassyLoamTest, ModBlocks.LOAM_GRASS_BLOCK.defaultBlockState(), 48, 0.65f));
        FeatureUtils.register(featureRegisterable, COARSE_LOAM_TO_FOUL_DIRT, Feature.ORE,
                new OreConfiguration(dirtTest, ModBlocks.FOUL_DIRT.defaultBlockState(), 64, 0.2f));
        FeatureUtils.register(featureRegisterable, FOUL_DIRT_TO_WASTE_PILE, Feature.ORE,
                new OreConfiguration(foulDirtTest, ModBlocks.WASTE_PILE.defaultBlockState(), 48, 0.65f));

        FeatureUtils.register(featureRegisterable, GRAVEL_TO_SILT_ORE, Feature.ORE,
                new OreConfiguration(gravelTest, ModBlocks.SILT.defaultBlockState(), 64, 0.2f));
        FeatureUtils.register(featureRegisterable, SILT_TO_GRASSY_ORE, Feature.ORE,
                new OreConfiguration(gravelTest, ModBlocks.GRASSY_SILT.defaultBlockState(), 48, 0.8f));
        FeatureUtils.register(featureRegisterable, SILT_TO_COARSE_ORE, Feature.ORE,
                new OreConfiguration(gravelTest, ModBlocks.COARSE_SILT.defaultBlockState(), 48, 0.8f));

        FeatureUtils.register(featureRegisterable, DOLOMITE_ORE, Feature.ORE,
                new OreConfiguration(stoneTest, StoneBlockSets.DOLOMITE_SET.baseBlocks.base().defaultBlockState(), 64, 0.25f));

        FeatureUtils.register(featureRegisterable, DRIPSTONE_ORE, Feature.ORE,
                new OreConfiguration(stoneTest, Blocks.DRIPSTONE_BLOCK.defaultBlockState(), 64, 0.25f));

        FeatureUtils.register(featureRegisterable, DRY_DIRT_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, ModBlocks.DRY_DIRT.defaultBlockState(), 48, 0.4f));

        FeatureUtils.register(featureRegisterable, FOREST_MOSS_DISK, Feature.DISK,
                new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModNatureBlocks.FOREST_MOSS_BLOCK),
                        BlockPredicate.matchesBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.STONE)), UniformInt.of(1, 3), 1));

        FeatureUtils.register(featureRegisterable, GRAVEL_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, Blocks.GRAVEL.defaultBlockState(), 48, 0.4f));

        FeatureUtils.register(featureRegisterable, SNOWY_DIRT_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, ModBlocks.SNOWY_DIRT.defaultBlockState(), 48, 0.4f));

        FeatureUtils.register(featureRegisterable, SNOWY_GRASS_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, ModBlocks.SNOWY_GRASS_BLOCK.defaultBlockState(), 48, 0.4f));

        FeatureUtils.register(featureRegisterable, GRANITE_ORE, Feature.ORE,
                new OreConfiguration(stoneTest, Blocks.GRANITE.defaultBlockState(), 64, 0.4f));

        FeatureUtils.register(featureRegisterable, SAND_TO_GRASS_ORE, ModFeatures.SURFACE_ORE,
                new SurfaceOreFeatureConfig(sandTest, Blocks.GRASS_BLOCK.defaultBlockState(), 64));

        FeatureUtils.register(featureRegisterable, STONE_GRASS_ORE, ModFeatures.SURFACE_ORE,
                new SurfaceOreFeatureConfig(stoneTest, Blocks.GRASS_BLOCK.defaultBlockState(), 64));

        FeatureUtils.register(featureRegisterable, GONLUIN_GRASS_ORE, Feature.DISK,
                new DiskConfiguration(RuleBasedBlockStateProvider.simple(Blocks.GRASS_BLOCK),
                        BlockPredicate.matchesBlocks(List.of(StoneBlockSets.KHAGALABAN_SET.baseBlocks.base())), UniformInt.of(3, 6), 1));

        FeatureUtils.register(featureRegisterable, OLD_PODZOL_ORE, Feature.ORE,
                new OreConfiguration(grassTest, ModNatureBlocks.OLD_PODZOL.defaultBlockState(), 64, 0.4f));
        FeatureUtils.register(featureRegisterable, OLD_STONE_PODZOL_ORE, Feature.ORE,
                new OreConfiguration(ashenStoneTest, ModNatureBlocks.OLD_PODZOL.defaultBlockState(), 48, 0.4f));

        FeatureUtils.register(featureRegisterable, LIMESTONE_ORE, Feature.ORE,
                new OreConfiguration(stoneTest, StoneBlockSets.LIMESTONE_SET.baseBlocks.base().defaultBlockState(), 64, 0.25f));
        FeatureUtils.register(featureRegisterable, LORIEN_PODZOL_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, ModNatureBlocks.LORIEN_PODZOL.defaultBlockState(), 48, 0.4f));

        FeatureUtils.register(featureRegisterable, MIRE_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, ModBlocks.MIRE.defaultBlockState(), 48, 0.4f));

        FeatureUtils.register(featureRegisterable, MUD_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, Blocks.MUD.defaultBlockState(), 48, 0.4f));
        FeatureUtils.register(featureRegisterable, PACKED_MUD_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, Blocks.PACKED_MUD.defaultBlockState(), 48, 0.4f));

        FeatureUtils.register(featureRegisterable, PODZOL_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, Blocks.PODZOL.defaultBlockState(), 48, 0.4f));

        FeatureUtils.register(featureRegisterable, POWDER_SNOW_ORE, Feature.ORE,
                new OreConfiguration(powderSnowList, 41));

        FeatureUtils.register(featureRegisterable, RIVER_SAND_ORE, Feature.DISK,
                new DiskConfiguration(RuleBasedBlockStateProvider.simple(ModBlocks.RIVER_SAND), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, Blocks.SAND, Blocks.GRASS_BLOCK)), UniformInt.of(2, 5), 2));

        FeatureUtils.register(featureRegisterable, SAND_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, Blocks.SAND.defaultBlockState(), 48, 0.4f));

        FeatureUtils.register(featureRegisterable, SNOW_BLOCK_ORE, Feature.ORE,
                new OreConfiguration(stoneTest, Blocks.SNOW_BLOCK.defaultBlockState(), 64, 0.5f));

        FeatureUtils.register(featureRegisterable, SOUL_SAND_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, Blocks.SOUL_SAND.defaultBlockState(), 32, 0.4f));

        FeatureUtils.register(featureRegisterable, CALCITE_STONE_ORE, Feature.ORE,
                new OreConfiguration(stoneTest, Blocks.STONE.defaultBlockState(), 64, 0.4f));
        FeatureUtils.register(featureRegisterable, GRASS_TO_STONE_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, Blocks.STONE.defaultBlockState(), 64, 0.25f));
        FeatureUtils.register(featureRegisterable, GRASS_TO_GRANITE_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, Blocks.GRANITE.defaultBlockState(), 64, 0.25f));

        FeatureUtils.register(featureRegisterable, TUFF_ORE, Feature.ORE,
                new OreConfiguration(stoneTest, Blocks.TUFF.defaultBlockState(), 48, 0.25f));

        FeatureUtils.register(featureRegisterable, TURF_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, ModBlocks.TURF.defaultBlockState(), 64, 0.25f));

        FeatureUtils.register(featureRegisterable, WHITE_SAND_ORE, Feature.ORE,
                new OreConfiguration(dirtTest, ModBlocks.WHITE_SAND.defaultBlockState(), 48, 0.4f));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name));
    }
}
