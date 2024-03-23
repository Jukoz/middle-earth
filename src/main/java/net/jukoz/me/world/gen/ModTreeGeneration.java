package net.jukoz.me.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;

import net.jukoz.me.utils.RegistryUtils;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.features.tree.foliages.PalmFoliagePlacer;
import net.jukoz.me.world.features.tree.trunks.ArcTrunkPlacer;
import net.jukoz.me.world.features.tree.trunks.CanopyTrunkPlacer;
import net.jukoz.me.world.features.tree.trunks.LargeTrunkPlacer;
import net.jukoz.me.world.features.tree.ModTreePlacedFeatures;
import net.jukoz.me.world.features.tree.foliages.OvalFoliagePlacer;
import net.jukoz.me.world.features.tree.trunks.SpruceTrunkPlacer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTreeGeneration {
    public static final Registry<TrunkPlacerType<?>> trunkRegistry = Registries.TRUNK_PLACER_TYPE;
    public static final Registry<FoliagePlacerType<?>> foliageRegistry = Registries.FOLIAGE_PLACER_TYPE;

    public static final TrunkPlacerType<ArcTrunkPlacer> ARC_TRUNK_PLACER = RegistryUtils.register(
            trunkRegistry, "arc_trunk", new TrunkPlacerType<>(ArcTrunkPlacer.CODEC)
    );
    public static final TrunkPlacerType<LargeTrunkPlacer> LARGE_TRUNK_PLACER = RegistryUtils.register(
            trunkRegistry, "large_trunk", new TrunkPlacerType<>(LargeTrunkPlacer.CODEC)
    );
    public static final TrunkPlacerType<CanopyTrunkPlacer> CANOPY_TRUNK_PLACER = RegistryUtils.register(
            trunkRegistry, "canopy_trunk", new TrunkPlacerType<>(CanopyTrunkPlacer.CODEC)
    );
    public static final TrunkPlacerType<SpruceTrunkPlacer> SPRUCE_TRUNK_PLACER = RegistryUtils.register(
            trunkRegistry, "spruce_trunk", new TrunkPlacerType<>(SpruceTrunkPlacer.CODEC)
    );

    public static final FoliagePlacerType<OvalFoliagePlacer> OVAL_FOLIAGE = RegistryUtils.register(
            foliageRegistry, "oval_foliage", new FoliagePlacerType<>(OvalFoliagePlacer.CODEC)
    );
    public static final FoliagePlacerType<PalmFoliagePlacer> PALM_FOLIAGE = RegistryUtils.register(
            foliageRegistry, "palm_foliage", new FoliagePlacerType<>(PalmFoliagePlacer.CODEC)
    );

    public static void generateTrees() {
    }
}
