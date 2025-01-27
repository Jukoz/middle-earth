package net.sevenstars.middleearth.world.gen;

import net.sevenstars.middleearth.utils.RegistryUtils;
import net.sevenstars.middleearth.world.features.tree.foliages.OvalFoliagePlacer;
import net.sevenstars.middleearth.world.features.tree.foliages.PalmFoliagePlacer;
import net.sevenstars.middleearth.world.features.tree.trunks.ArcTrunkPlacer;
import net.sevenstars.middleearth.world.features.tree.trunks.CanopyTrunkPlacer;
import net.sevenstars.middleearth.world.features.tree.trunks.LargeTrunkPlacer;
import net.sevenstars.middleearth.world.features.tree.trunks.SpruceTrunkPlacer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
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
