package net.sevenstars.middleearth.world.features.ores;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.world.features.underground.CavesConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.material.Fluids;

public class OrePlacedFeatures {
    public static final ResourceKey<PlacedFeature> ANDESITE_ORE = registerKey("andesite_ore");
    public static final ResourceKey<PlacedFeature> ASH_BLOCK_ORE = registerKey("ash_block_ore");
    public static final ResourceKey<PlacedFeature> ASHEN_DIRT_ORE = registerKey("ashen_dirt_ore");
    public static final ResourceKey<PlacedFeature> ASHEN_STONE_DIRT_ORE = registerKey("ashen_dirt_stone_ore");
    public static final ResourceKey<PlacedFeature> ASHEN_STONE_DIRT_COMMON_ORE = registerKey("ashen_dirt_stone_common_ore");
    public static final ResourceKey<PlacedFeature> ASHEN_GRAVEL = registerKey("ashen_gravel");
    public static final ResourceKey<PlacedFeature> ASHEN_SAND = registerKey("ashen_sand");
    public static final ResourceKey<PlacedFeature> ASHEN_GRAVEL_DIRT = registerKey("ashen_gravel_dirt");
    public static final ResourceKey<PlacedFeature> ASHEN_GRAVEL_SAND = registerKey("ashen_gravel_sand");
    public static final ResourceKey<PlacedFeature> ASHEN_STONE_GRAVEL = registerKey("ashen_stone_gravel");
    public static final ResourceKey<PlacedFeature> ASHEN_STONE_SAND = registerKey("ashen_stone_sand");
    public static final ResourceKey<PlacedFeature> BASALT_ORE = registerKey("basalt_ore");
    public static final ResourceKey<PlacedFeature> SMOOTH_BASALT_ORE = registerKey("smooth_basalt_ore");
    public static final ResourceKey<PlacedFeature> BLACK_SAND_ORE = registerKey("black_sand_ore");
    public static final ResourceKey<PlacedFeature> BLUE_TUFF_ORE = registerKey("blue_tuff_ore");
    public static final ResourceKey<PlacedFeature> CALCITE_ORE = registerKey("calcite_ore");
    public static final ResourceKey<PlacedFeature> RARE_CALCITE_ORE = registerKey("rare_calcite_ore");
    public static final ResourceKey<PlacedFeature> COARSE_DIRT_ORE = registerKey("coarse_dirt_ore");
    public static final ResourceKey<PlacedFeature> COARSE_LOAM_ORE = registerKey("coarse_loam_ore");
    public static final ResourceKey<PlacedFeature> COARSE_CHALKSOIL_ORE = registerKey("coarse_chalksoil_ore");
    public static final ResourceKey<PlacedFeature> COARSE_PEAT_ORE = registerKey("coarse_peat_ore");
    public static final ResourceKey<PlacedFeature> COARSE_SILT_ORE = registerKey("coarse_silt_ore");
    public static final ResourceKey<PlacedFeature> CORRUPTED_MOSS_DISK = registerKey("corrupted_moss_disk");
    public static final ResourceKey<PlacedFeature> DIORITE_ORE = registerKey("diorite_ore");
    public static final ResourceKey<PlacedFeature> DIRT_TO_GRASS_ORE = registerKey("dirt_to_grass_ore");
    public static final ResourceKey<PlacedFeature> ROOTED_DIRT_ORE = registerKey("rooted_dirt_ore");
    public static final ResourceKey<PlacedFeature> GRASSY_DIRT_ORE = registerKey("grassy_dirt_ore");
    public static final ResourceKey<PlacedFeature> GRASSY_CHALKSOIL_ORE = registerKey("grassy_chalksoil_ore");
    public static final ResourceKey<PlacedFeature> GRASSY_LOAM_ORE = registerKey("grassy_loam_ore");
    public static final ResourceKey<PlacedFeature> GRASSY_PEAT_ORE = registerKey("grassy_peat_ore");
    public static final ResourceKey<PlacedFeature> GRASSY_SILT_ORE = registerKey("grassy_silt_ore");
    public static final ResourceKey<PlacedFeature> COARSE_LOAM_TO_GRASSY = registerKey("coarse_loam_to_grassy");
    public static final ResourceKey<PlacedFeature> GRASSY_LOAM_TO_GRASS = registerKey("grassy_loam_to_grass");
    public static final ResourceKey<PlacedFeature> COARSE_LOAM_TO_FOUL_DIRT = registerKey("coarse_loam_to_foul_dirt");
    public static final ResourceKey<PlacedFeature> FOUL_DIRT_TO_WASTE_PILE = registerKey("foul_dirt_to_waste_pile");
    public static final ResourceKey<PlacedFeature> GRAVEL_TO_SILT_ORE = registerKey("gravel_to_silt_ore");
    public static final ResourceKey<PlacedFeature> SILT_TO_GRASSY_ORE = registerKey("silt_to_grassy_ore");
    public static final ResourceKey<PlacedFeature> SILT_TO_COARSE_ORE = registerKey("silt_to_coarse_ore");
    public static final ResourceKey<PlacedFeature> DIRTY_ROOTS_ORE = registerKey("dirty_roots");
    public static final ResourceKey<PlacedFeature> DOLOMITE_ORE = registerKey("dolomite_ore");
    public static final ResourceKey<PlacedFeature> DRIPSTONE_ORE = registerKey("dripstone_ore");
    public static final ResourceKey<PlacedFeature> DRY_DIRT_ORE = registerKey("dry_dirt_ore");
    public static final ResourceKey<PlacedFeature> FOREST_MOSS_DISK = registerKey("forest_moss_disk");
    public static final ResourceKey<PlacedFeature> GRANITE_ORE = registerKey("granite_ore");
    public static final ResourceKey<PlacedFeature> GRAVEL_ORE = registerKey("gravel_ore");
    public static final ResourceKey<PlacedFeature> SNOWY_DIRT_ORE = registerKey("snowy_dirty_ore");
    public static final ResourceKey<PlacedFeature> SNOWY_GRASS_ORE = registerKey("snowy_grass_ore");
    public static final ResourceKey<PlacedFeature> STONE_GRASS_ORE = registerKey("stone_grass_ore");
    public static final ResourceKey<PlacedFeature> SAND_TO_GRASS_ORE = registerKey("sand_to_grass_ore");
    public static final ResourceKey<PlacedFeature> STONE_GRASS_ABUNDANT_ORE = registerKey("stone_grass_abundant_ore");
    public static final ResourceKey<PlacedFeature> OLD_PODZOL_ORE = registerKey("old_podzol_ore");
    public static final ResourceKey<PlacedFeature> STONE_OLD_PODZOL_ORE = registerKey("stone_old_podzol_ore");
    public static final ResourceKey<PlacedFeature> LIMESTONE_ORE = registerKey("limestone_ore");
    public static final ResourceKey<PlacedFeature> LORIEN_PODZOL_ORE = registerKey("lorien_podzol_ore");
    public static final ResourceKey<PlacedFeature> MIRE_ORE = registerKey("mire_ore");
    public static final ResourceKey<PlacedFeature> ABUNDANT_MUD_ORE = registerKey("abundant_mud_ore");
    public static final ResourceKey<PlacedFeature> MUD_ORE = registerKey("mud_ore");
    public static final ResourceKey<PlacedFeature> PACKED_MUD_ORE = registerKey("packed_mud_ore");
    public static final ResourceKey<PlacedFeature> PODZOL_ORE = registerKey("podzol_ore");
    public static final ResourceKey<PlacedFeature> ABUNDANT_PODZOL_ORE = registerKey("abundant_podzol_ore");
    public static final ResourceKey<PlacedFeature> POWDER_SNOW_ORE = registerKey("powder_snow_ore");
    public static final ResourceKey<PlacedFeature> RIVER_SAND = registerKey("river_sand");
    public static final ResourceKey<PlacedFeature> DISK_SAND = registerKey("disk_sand");
    public static final ResourceKey<PlacedFeature> SAND_ORE = registerKey("sand_ore");
    public static final ResourceKey<PlacedFeature> SNOW_ORE = registerKey("snow_ore");
    public static final ResourceKey<PlacedFeature> SOUL_SAND_ORE = registerKey("soul_sand_ore");
    public static final ResourceKey<PlacedFeature> CALCITE_STONE_ORE = registerKey("calcite_to_stone_ore");
    public static final ResourceKey<PlacedFeature> GRASS_TO_STONE_ORE = registerKey("grass_to_stone_ore");
    public static final ResourceKey<PlacedFeature> GRASS_TO_GRANITE_ORE = registerKey("grass_to_granite_ore");
    public static final ResourceKey<PlacedFeature> TERRACOTTA_ORE = registerKey("terracotta_ore");
    public static final ResourceKey<PlacedFeature> TUFF_ORE = registerKey("tuff_ore");
    public static final ResourceKey<PlacedFeature> ABUNDANT_TUFF_ORE = registerKey("abundant_tuff_ore");
    public static final ResourceKey<PlacedFeature> TURF_ORE = registerKey("turf_ore");
    public static final ResourceKey<PlacedFeature> COMMON_TURF_ORE = registerKey("common_turf_ore");
    public static final ResourceKey<PlacedFeature> WHITE_SAND_ORE = registerKey("white_sand_ore");

    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable) {
        HolderGetter<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.lookup(Registries.CONFIGURED_FEATURE);
        Holder.Reference<ConfiguredFeature<?, ?>> andesite = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ANDESITE_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> ashBlock = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ASH_BLOCK_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> ashenDirt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ASHEN_DIRT_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> ashenStoneDirt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ASHEN_STONE_DIRT_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> ashenGravel = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ASHEN_GRAVEL);
        Holder.Reference<ConfiguredFeature<?, ?>> ashenSand = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ASHEN_SAND);
        Holder.Reference<ConfiguredFeature<?, ?>> ashenGravelDirt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ASHEN_GRAVEL_DIRT);
        Holder.Reference<ConfiguredFeature<?, ?>> ashenGravelSand = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ASHEN_GRAVEL_SAND);
        Holder.Reference<ConfiguredFeature<?, ?>> ashenStoneGravel = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ASHEN_STONE_GRAVEL);
        Holder.Reference<ConfiguredFeature<?, ?>> ashenStoneSand = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ASHEN_STONE_SAND);
        Holder.Reference<ConfiguredFeature<?, ?>> basalt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.BASALT_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> smoothBasalt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.SMOOTH_BASALT_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> blackSand = registryEntryLookup.getOrThrow(OreConfiguredFeatures.BLACK_SAND_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> blueTuff = registryEntryLookup.getOrThrow(OreConfiguredFeatures.BLUE_TUFF_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> calcite = registryEntryLookup.getOrThrow(OreConfiguredFeatures.CALCITE_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> coarseDirt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.COARSE_DIRT_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> coarseChalksoil = registryEntryLookup.getOrThrow(OreConfiguredFeatures.COARSE_CHALKSOIL_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> coarseLoam = registryEntryLookup.getOrThrow(OreConfiguredFeatures.COARSE_LOAM_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> coarsePeat = registryEntryLookup.getOrThrow(OreConfiguredFeatures.COARSE_PEAT_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> coarseSilt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.COARSE_SILT_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> corruptedMoss = registryEntryLookup.getOrThrow(OreConfiguredFeatures.CORRUPTED_MOSS_DISK);
        Holder.Reference<ConfiguredFeature<?, ?>> diorite = registryEntryLookup.getOrThrow(OreConfiguredFeatures.DIORITE_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> dirtToGrass = registryEntryLookup.getOrThrow(OreConfiguredFeatures.DIRT_TO_GRASS_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> rootedDirt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ROOTED_DIRT_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> grassyDirt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.GRASSY_DIRT_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> grassyChalksoil = registryEntryLookup.getOrThrow(OreConfiguredFeatures.GRASSY_CHALKSOIL_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> grassyLoam = registryEntryLookup.getOrThrow(OreConfiguredFeatures.GRASSY_LOAM_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> grassyPeat = registryEntryLookup.getOrThrow(OreConfiguredFeatures.GRASSY_PEAT_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> grassySilt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.GRASSY_SILT_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> gravelToSilt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.GRAVEL_TO_SILT_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> coarseLoamToGrassy = registryEntryLookup.getOrThrow(OreConfiguredFeatures.COARSE_LOAM_TO_GRASSY);
        Holder.Reference<ConfiguredFeature<?, ?>> grassyLoamToGrass = registryEntryLookup.getOrThrow(OreConfiguredFeatures.GRASSY_LOAM_TO_GRASS);
        Holder.Reference<ConfiguredFeature<?, ?>> coarseLoamToFoulDirt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.COARSE_LOAM_TO_FOUL_DIRT);
        Holder.Reference<ConfiguredFeature<?, ?>> foulDirtToWastePile = registryEntryLookup.getOrThrow(OreConfiguredFeatures.FOUL_DIRT_TO_WASTE_PILE);
        Holder.Reference<ConfiguredFeature<?, ?>> siltToGrassy = registryEntryLookup.getOrThrow(OreConfiguredFeatures.SILT_TO_GRASSY_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> siltToCoarse = registryEntryLookup.getOrThrow(OreConfiguredFeatures.SILT_TO_COARSE_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> dirtyRoots = registryEntryLookup.getOrThrow(OreConfiguredFeatures.DIRTY_ROOTS_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> dolomite = registryEntryLookup.getOrThrow(OreConfiguredFeatures.DOLOMITE_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> dripstone = registryEntryLookup.getOrThrow(OreConfiguredFeatures.DRIPSTONE_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> dryDirt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.DRY_DIRT_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> forestMoss = registryEntryLookup.getOrThrow(OreConfiguredFeatures.FOREST_MOSS_DISK);
        Holder.Reference<ConfiguredFeature<?, ?>> snowBlock = registryEntryLookup.getOrThrow(OreConfiguredFeatures.SNOW_BLOCK_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> soulSand = registryEntryLookup.getOrThrow(OreConfiguredFeatures.SOUL_SAND_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> granite = registryEntryLookup.getOrThrow(OreConfiguredFeatures.GRANITE_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> gravel = registryEntryLookup.getOrThrow(OreConfiguredFeatures.GRAVEL_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> snowyDirt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.SNOWY_DIRT_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> snowyGrass = registryEntryLookup.getOrThrow(OreConfiguredFeatures.SNOWY_GRASS_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> stoneGrass = registryEntryLookup.getOrThrow(OreConfiguredFeatures.STONE_GRASS_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> sandToGrass = registryEntryLookup.getOrThrow(OreConfiguredFeatures.SAND_TO_GRASS_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> oldPodzol = registryEntryLookup.getOrThrow(OreConfiguredFeatures.OLD_PODZOL_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> stoneOldPodzol = registryEntryLookup.getOrThrow(OreConfiguredFeatures.OLD_STONE_PODZOL_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> limestone = registryEntryLookup.getOrThrow(OreConfiguredFeatures.LIMESTONE_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> lorienPodzol = registryEntryLookup.getOrThrow(OreConfiguredFeatures.LORIEN_PODZOL_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> mire = registryEntryLookup.getOrThrow(OreConfiguredFeatures.MIRE_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> mud = registryEntryLookup.getOrThrow(OreConfiguredFeatures.MUD_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> packedMud = registryEntryLookup.getOrThrow(OreConfiguredFeatures.PACKED_MUD_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> podzol = registryEntryLookup.getOrThrow(OreConfiguredFeatures.PODZOL_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> powderSnow = registryEntryLookup.getOrThrow(OreConfiguredFeatures.POWDER_SNOW_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> riverSand = registryEntryLookup.getOrThrow(OreConfiguredFeatures.RIVER_SAND_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> diskSand = registryEntryLookup.getOrThrow(MiscOverworldFeatures.DISK_SAND);
        Holder.Reference<ConfiguredFeature<?, ?>> sand = registryEntryLookup.getOrThrow(OreConfiguredFeatures.SAND_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> calciteStone = registryEntryLookup.getOrThrow(OreConfiguredFeatures.CALCITE_STONE_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> grassStone = registryEntryLookup.getOrThrow(OreConfiguredFeatures.GRASS_TO_STONE_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> grassGranite = registryEntryLookup.getOrThrow(OreConfiguredFeatures.GRASS_TO_GRANITE_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> terracotta = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_TERRACOTTA);
        Holder.Reference<ConfiguredFeature<?, ?>> stoneTuff = registryEntryLookup.getOrThrow(OreConfiguredFeatures.TUFF_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> turf = registryEntryLookup.getOrThrow(OreConfiguredFeatures.TURF_ORE);
        Holder.Reference<ConfiguredFeature<?, ?>> lightGrayConcretePowder = registryEntryLookup.getOrThrow(OreConfiguredFeatures.WHITE_SAND_ORE);

        PlacementModifier abundant = PlacementUtils.countExtra(2, 0.5f, 1);
        PlacementModifier frequent = PlacementUtils.countExtra(1, 0.5f, 1);
        PlacementModifier veryCommon = RarityFilter.onAverageOnceEvery(1);
        PlacementModifier common = RarityFilter.onAverageOnceEvery(2);
        PlacementModifier uncommon = PlacementUtils.countExtra(0, 0.25f, 1);
        PlacementModifier rare = PlacementUtils.countExtra(0, 0.1f, 1);

        PlacementUtils.register(featureRegisterable, ANDESITE_ORE, andesite, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, ASH_BLOCK_ORE, ashBlock, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, ASHEN_DIRT_ORE, ashenDirt, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, ASHEN_STONE_DIRT_ORE, ashenStoneDirt, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, ASHEN_STONE_DIRT_COMMON_ORE, ashenStoneDirt, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, ASHEN_GRAVEL, ashenGravel, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, ASHEN_SAND, ashenSand, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, ASHEN_GRAVEL_DIRT, ashenGravelDirt, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, ASHEN_GRAVEL_SAND, ashenGravelSand, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, ASHEN_STONE_GRAVEL, ashenStoneGravel, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, ASHEN_STONE_SAND, ashenStoneSand, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, BASALT_ORE, basalt, uncommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, SMOOTH_BASALT_ORE, smoothBasalt, frequent,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, BLACK_SAND_ORE, blackSand, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, BLUE_TUFF_ORE, blueTuff, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, CALCITE_ORE, calcite, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, RARE_CALCITE_ORE, calcite, uncommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, COARSE_DIRT_ORE, coarseDirt, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, COARSE_CHALKSOIL_ORE, coarseChalksoil, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, COARSE_LOAM_ORE, coarseLoam, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, COARSE_PEAT_ORE, coarsePeat, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, COARSE_SILT_ORE, coarseSilt, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, CORRUPTED_MOSS_DISK, corruptedMoss, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, DIORITE_ORE, diorite, abundant,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, DIRT_TO_GRASS_ORE, dirtToGrass, abundant,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, ROOTED_DIRT_ORE, rootedDirt, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, GRASSY_DIRT_ORE, grassyDirt, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, GRASSY_CHALKSOIL_ORE, grassyChalksoil, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, GRASSY_LOAM_ORE, grassyLoam, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, GRASSY_PEAT_ORE, grassyPeat, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, GRASSY_SILT_ORE, grassySilt, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, COARSE_LOAM_TO_GRASSY, coarseLoamToGrassy, frequent,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, GRASSY_LOAM_TO_GRASS, grassyLoamToGrass, frequent,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, COARSE_LOAM_TO_FOUL_DIRT, coarseLoamToFoulDirt, frequent,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FOUL_DIRT_TO_WASTE_PILE, foulDirtToWastePile, frequent,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, GRAVEL_TO_SILT_ORE, gravelToSilt, frequent,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, SILT_TO_GRASSY_ORE, siltToGrassy, frequent,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, SILT_TO_COARSE_ORE, siltToCoarse, frequent,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, DIRTY_ROOTS_ORE, dirtyRoots, frequent,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, DOLOMITE_ORE, dolomite, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, DRIPSTONE_ORE, dripstone, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, DRY_DIRT_ORE, dryDirt, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, FOREST_MOSS_DISK, forestMoss, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, GRANITE_ORE, granite, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, GRAVEL_ORE, gravel, rare,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, SNOWY_DIRT_ORE, snowyDirt, rare,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, SNOWY_GRASS_ORE, snowyGrass, rare,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, STONE_GRASS_ORE, stoneGrass, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, STONE_GRASS_ABUNDANT_ORE, stoneGrass, abundant,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, SAND_TO_GRASS_ORE, sandToGrass, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, OLD_PODZOL_ORE, oldPodzol, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, STONE_OLD_PODZOL_ORE, stoneOldPodzol, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, LIMESTONE_ORE, limestone, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, LORIEN_PODZOL_ORE, lorienPodzol, frequent,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, MIRE_ORE, mire, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, ABUNDANT_MUD_ORE, mud, abundant,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, MUD_ORE, mud, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PACKED_MUD_ORE, packedMud, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, PODZOL_ORE, podzol, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, ABUNDANT_PODZOL_ORE, podzol, abundant,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, POWDER_SNOW_ORE, powderSnow, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, RIVER_SAND, riverSand,
                CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, DISK_SAND, diskSand,
                CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)), BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, SAND_ORE, sand, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, SNOW_ORE, snowBlock, PlacementUtils.countExtra(3, 0.5f, 1),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.absolute(180), VerticalAnchor.absolute(460))),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, SOUL_SAND_ORE, soulSand, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, CALCITE_STONE_ORE, calciteStone, abundant,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, GRASS_TO_STONE_ORE, grassStone, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, GRASS_TO_GRANITE_ORE, grassGranite, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, TERRACOTTA_ORE, terracotta, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, TUFF_ORE, stoneTuff, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, TURF_ORE, turf, veryCommon,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, COMMON_TURF_ORE, turf, frequent,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, ABUNDANT_TUFF_ORE, stoneTuff, abundant,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, WHITE_SAND_ORE, lightGrayConcretePowder, common,
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name));
    }
}
