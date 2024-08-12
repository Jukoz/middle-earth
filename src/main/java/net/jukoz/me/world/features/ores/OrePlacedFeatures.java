package net.jukoz.me.world.features.ores;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.features.underground.CavesConfiguredFeatures;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.MiscConfiguredFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.heightprovider.TrapezoidHeightProvider;
import net.minecraft.world.gen.placementmodifier.*;

public class OrePlacedFeatures {
    public static final RegistryKey<PlacedFeature> ANDESITE_ORE = registerKey("andesite_ore");
    public static final RegistryKey<PlacedFeature> ASH_BLOCK_ORE = registerKey("ash_block_ore");
    public static final RegistryKey<PlacedFeature> ASHEN_DIRT_ORE = registerKey("ashen_dirt_ore");
    public static final RegistryKey<PlacedFeature> ASHEN_DIRT_STONE_ORE = registerKey("ashen_dirt_stone_ore");
    public static final RegistryKey<PlacedFeature> ASHEN_GRAVEL = registerKey("ashen_gravel");
    public static final RegistryKey<PlacedFeature> ASHEN_SAND = registerKey("ashen_sand");
    public static final RegistryKey<PlacedFeature> ASHEN_STONE_GRAVEL = registerKey("ashen_stone_gravel");
    public static final RegistryKey<PlacedFeature> ASHEN_STONE_SAND = registerKey("ashen_stone_sand");
    public static final RegistryKey<PlacedFeature> BASALT_ORE = registerKey("basalt_ore");
    public static final RegistryKey<PlacedFeature> SMOOTH_BASALT_ORE = registerKey("smooth_basalt_ore");
    public static final RegistryKey<PlacedFeature> BLACK_SAND_ORE = registerKey("black_sand_ore");
    public static final RegistryKey<PlacedFeature> BLUE_TUFF_ORE = registerKey("blue_tuff_ore");
    public static final RegistryKey<PlacedFeature> CALCITE_ORE = registerKey("calcite_ore");
    public static final RegistryKey<PlacedFeature> RARE_CALCITE_ORE = registerKey("rare_calcite_ore");
    public static final RegistryKey<PlacedFeature> COARSE_DIRT_ORE = registerKey("coarse_dirt_ore");
    public static final RegistryKey<PlacedFeature> CORRUPTED_MOSS_DISK = registerKey("corrupted_moss_disk");
    public static final RegistryKey<PlacedFeature> DIORITE_ORE = registerKey("diorite_ore");
    public static final RegistryKey<PlacedFeature> DIRT_TO_GRASS_ORE = registerKey("dirt_to_grass_ore");
    public static final RegistryKey<PlacedFeature> DOLOMITE_ORE = registerKey("dolomite_ore");
    public static final RegistryKey<PlacedFeature> DRIPSTONE_ORE = registerKey("dripstone_ore");
    public static final RegistryKey<PlacedFeature> DRY_DIRT_ORE = registerKey("dry_dirt_ore");
    public static final RegistryKey<PlacedFeature> FOREST_MOSS_DISK = registerKey("forest_moss_disk");
    public static final RegistryKey<PlacedFeature> FROZEN_STONE_ORE = registerKey("frozen_stone_ore");
    public static final RegistryKey<PlacedFeature> GRANITE_ORE = registerKey("granite_ore");
    public static final RegistryKey<PlacedFeature> GRAVEL_ORE = registerKey("gravel_ore");
    public static final RegistryKey<PlacedFeature> STONE_GRASS_ORE = registerKey("stone_grass_ore");
    public static final RegistryKey<PlacedFeature> STONE_GRASS_ABUNDANT_ORE = registerKey("stone_grass_abundant_ore");
    public static final RegistryKey<PlacedFeature> OLD_PODZOL_ORE = registerKey("old_podzol_ore");
    public static final RegistryKey<PlacedFeature> LIMESTONE_ORE = registerKey("limestone_ore");
    public static final RegistryKey<PlacedFeature> MIRE_ORE = registerKey("mire_ore");
    public static final RegistryKey<PlacedFeature> ABUNDANT_MUD_ORE = registerKey("abundant_mud_ore");
    public static final RegistryKey<PlacedFeature> MUD_ORE = registerKey("mud_ore");
    public static final RegistryKey<PlacedFeature> PACKED_MUD_ORE = registerKey("packed_mud_ore");
    public static final RegistryKey<PlacedFeature> PODZOL_ORE = registerKey("podzol_ore");
    public static final RegistryKey<PlacedFeature> POWDER_SNOW_ORE = registerKey("powder_snow_ore");
    public static final RegistryKey<PlacedFeature> RIVER_SAND = registerKey("river_sand");
    public static final RegistryKey<PlacedFeature> DISK_SAND = registerKey("disk_sand");
    public static final RegistryKey<PlacedFeature> SAND_ORE = registerKey("sand_ore");
    public static final RegistryKey<PlacedFeature> SNOW_ORE = registerKey("snow_ore");
    public static final RegistryKey<PlacedFeature> SOUL_SAND_ORE = registerKey("soul_sand_ore");
    public static final RegistryKey<PlacedFeature> CALCITE_STONE_ORE = registerKey("calcite_stone_ore");
    public static final RegistryKey<PlacedFeature> GRASS_STONE_ORE = registerKey("grass_stone_ore");
    public static final RegistryKey<PlacedFeature> TERRACOTTA_ORE = registerKey("terracotta_ore");
    public static final RegistryKey<PlacedFeature> TUFF_ORE = registerKey("tuff_ore");
    public static final RegistryKey<PlacedFeature> TURF_ORE = registerKey("turf_ore");
    public static final RegistryKey<PlacedFeature> ABUNDANT_TUFF_ORE = registerKey("abundant_tuff_ore");
    public static final RegistryKey<PlacedFeature> WHITE_SAND_ORE = registerKey("white_sand_ore");

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> andesite = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ANDESITE_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> ashBlock = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ASH_BLOCK_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> ashenDirt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ASHEN_DIRT_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> ashenDirtStone = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ASHEN_DIRT_STONE_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> ashenGravel = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ASHEN_GRAVEL);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> ashenSand = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ASHEN_SAND);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> ashenStoneGravel = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ASHEN_STONE_GRAVEL);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> ashenStoneSand = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ASHEN_STONE_SAND);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> basalt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.BASALT_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> smoothBasalt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.SMOOTH_BASALT_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> blackSand = registryEntryLookup.getOrThrow(OreConfiguredFeatures.BLACK_SAND_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> blueTuff = registryEntryLookup.getOrThrow(OreConfiguredFeatures.BLUE_TUFF_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> calcite = registryEntryLookup.getOrThrow(OreConfiguredFeatures.CALCITE_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> coarseDirt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.COARSE_DIRT_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> corruptedMoss = registryEntryLookup.getOrThrow(OreConfiguredFeatures.CORRUPTED_MOSS_DISK);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> diorite = registryEntryLookup.getOrThrow(OreConfiguredFeatures.DIORITE_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> dirtToGrass = registryEntryLookup.getOrThrow(OreConfiguredFeatures.DIRT_TO_GRASS_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> dolomite = registryEntryLookup.getOrThrow(OreConfiguredFeatures.DOLOMITE_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> dripstone = registryEntryLookup.getOrThrow(OreConfiguredFeatures.DRIPSTONE_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> dryDirt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.DRY_DIRT_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> forestMoss = registryEntryLookup.getOrThrow(OreConfiguredFeatures.FOREST_MOSS_DISK);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> frozenStone = registryEntryLookup.getOrThrow(OreConfiguredFeatures.FROZEN_STONE_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> snowBlock = registryEntryLookup.getOrThrow(OreConfiguredFeatures.SNOW_BLOCK_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> soulSand = registryEntryLookup.getOrThrow(OreConfiguredFeatures.SOUL_SAND_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> granite = registryEntryLookup.getOrThrow(OreConfiguredFeatures.GRANITE_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> gravel = registryEntryLookup.getOrThrow(OreConfiguredFeatures.GRAVEL_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> stoneGrass = registryEntryLookup.getOrThrow(OreConfiguredFeatures.STONE_GRASS_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oldPodzol = registryEntryLookup.getOrThrow(OreConfiguredFeatures.OLD_PODZOL_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> limestone = registryEntryLookup.getOrThrow(OreConfiguredFeatures.LIMESTONE_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mire = registryEntryLookup.getOrThrow(OreConfiguredFeatures.MIRE_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mud = registryEntryLookup.getOrThrow(OreConfiguredFeatures.MUD_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> packedMud = registryEntryLookup.getOrThrow(OreConfiguredFeatures.PACKED_MUD_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> podzol = registryEntryLookup.getOrThrow(OreConfiguredFeatures.PODZOL_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> powderSnow = registryEntryLookup.getOrThrow(OreConfiguredFeatures.POWDER_SNOW_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> riverSand = registryEntryLookup.getOrThrow(OreConfiguredFeatures.RIVER_SAND_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> diskSand = registryEntryLookup.getOrThrow(MiscConfiguredFeatures.DISK_SAND);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> sand = registryEntryLookup.getOrThrow(OreConfiguredFeatures.SAND_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> calciteStone = registryEntryLookup.getOrThrow(OreConfiguredFeatures.CALCITE_STONE_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> grassStone = registryEntryLookup.getOrThrow(OreConfiguredFeatures.GRASS_STONE_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> terracotta = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_TERRACOTTA);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> stoneTuff = registryEntryLookup.getOrThrow(OreConfiguredFeatures.TUFF_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> turf = registryEntryLookup.getOrThrow(OreConfiguredFeatures.TURF_ORE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> lightGrayConcretePowder = registryEntryLookup.getOrThrow(OreConfiguredFeatures.WHITE_SAND_ORE);

        PlacementModifier abundant = PlacedFeatures.createCountExtraModifier(2, 0.5f, 1);
        PlacementModifier frequent = PlacedFeatures.createCountExtraModifier(1, 0.5f, 1);
        PlacementModifier veryCommon = RarityFilterPlacementModifier.of(1);
        PlacementModifier common = RarityFilterPlacementModifier.of(2);
        PlacementModifier uncommon = PlacedFeatures.createCountExtraModifier(0, 0.25f, 1);
        PlacementModifier rare = PlacedFeatures.createCountExtraModifier(0, 0.1f, 1);

        PlacedFeatures.register(featureRegisterable, ANDESITE_ORE, andesite, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, ASH_BLOCK_ORE, ashBlock, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, ASHEN_DIRT_ORE, ashenDirt, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, ASHEN_DIRT_STONE_ORE, ashenDirtStone, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, ASHEN_GRAVEL, ashenGravel, veryCommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, ASHEN_SAND, ashenSand, veryCommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, ASHEN_STONE_GRAVEL, ashenStoneGravel, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, ASHEN_STONE_SAND, ashenStoneSand, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BASALT_ORE, basalt, uncommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SMOOTH_BASALT_ORE, smoothBasalt, frequent,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BLACK_SAND_ORE, blackSand, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, BLUE_TUFF_ORE, blueTuff, veryCommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, CALCITE_ORE, calcite, veryCommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, RARE_CALCITE_ORE, calcite, uncommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, COARSE_DIRT_ORE, coarseDirt, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, CORRUPTED_MOSS_DISK, corruptedMoss, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, DIORITE_ORE, diorite, abundant,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, DIRT_TO_GRASS_ORE, dirtToGrass, abundant,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, DOLOMITE_ORE, dolomite, veryCommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, DRIPSTONE_ORE, dripstone, veryCommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, DRY_DIRT_ORE, dryDirt, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FOREST_MOSS_DISK, forestMoss, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, FROZEN_STONE_ORE, frozenStone, PlacedFeatures.createCountExtraModifier(3, 0.5f, 1),
                HeightRangePlacementModifier.of(TrapezoidHeightProvider.create(YOffset.fixed(180), YOffset.fixed(460))),
                PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, GRANITE_ORE, granite, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, GRAVEL_ORE, gravel, rare,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, STONE_GRASS_ORE, stoneGrass, veryCommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, STONE_GRASS_ABUNDANT_ORE, stoneGrass, abundant,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, OLD_PODZOL_ORE, oldPodzol, veryCommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, LIMESTONE_ORE, limestone, veryCommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MIRE_ORE, mire, veryCommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, ABUNDANT_MUD_ORE, mud, abundant,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, MUD_ORE, mud, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PACKED_MUD_ORE, packedMud, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, PODZOL_ORE, podzol, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, POWDER_SNOW_ORE, powderSnow, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, RIVER_SAND, riverSand,
                CountPlacementModifier.of(2), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP,
                BlockFilterPlacementModifier.of(BlockPredicate.matchingFluids(Fluids.WATER)), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, DISK_SAND, diskSand,
                CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP,
                BlockFilterPlacementModifier.of(BlockPredicate.matchingFluids(Fluids.WATER)), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SAND_ORE, sand, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SNOW_ORE, snowBlock, PlacedFeatures.createCountExtraModifier(3, 0.5f, 1),
                HeightRangePlacementModifier.of(TrapezoidHeightProvider.create(YOffset.fixed(180), YOffset.fixed(460))),
                PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SOUL_SAND_ORE, soulSand, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, CALCITE_STONE_ORE, calciteStone, abundant,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, GRASS_STONE_ORE, grassStone, veryCommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, TERRACOTTA_ORE, terracotta, veryCommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, TUFF_ORE, stoneTuff, veryCommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, TURF_ORE, turf, veryCommon,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, ABUNDANT_TUFF_ORE, stoneTuff, abundant,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, WHITE_SAND_ORE, lightGrayConcretePowder, common,
                SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }
}
