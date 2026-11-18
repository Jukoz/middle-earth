package net.sevenstars.middleearth.features.generation.caves;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.Feature;
import net.sevenstars.middleearth.features.generation.biomes.BiomeKeyRegistryME;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.chunk.placement.StructurePlacementType;
import net.minecraft.world.gen.structure.StructureType;
import net.sevenstars.middleearth.MiddleEarth;

public class Caves {
    public static final StructurePlacementType<MistyShaftPlacement> MISTY_SHAFT_PLACEMENT = () -> MistyShaftPlacement.CODEC;
    public static final StructureType<MistyShaftBridgeStructure> MISTY_SHAFT_BRIDGE = () -> MistyShaftBridgeStructure.CODEC;
    public static final StructureType<MistyDwarfMineStructure> MISTY_DWARF_MINE = () -> MistyDwarfMineStructure.CODEC;

    public static final Feature<?> MISTY_ROAD_CLEANUP = new MistyRoadCleanupFeature();

    public static void register() {
        Registry.register(Registries.STRUCTURE_PLACEMENT, MiddleEarth.id("misty_shaft"), MISTY_SHAFT_PLACEMENT);
        Registry.register(Registries.STRUCTURE_TYPE, MiddleEarth.id("misty_shaft_bridge"), MISTY_SHAFT_BRIDGE);
        Registry.register(Registries.STRUCTURE_TYPE, MiddleEarth.id("misty_dwarf_mine"), MISTY_DWARF_MINE);
        Registry.register(Registries.FEATURE, MiddleEarth.id("misty_road_cleanup"), MISTY_ROAD_CLEANUP);
        // after every other cave feature; mountain caves outside the Misty Mountains share these biomes and the feature does nothing there
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                        BiomeKeyRegistryME.IZHERABAN_CAVE, BiomeKeyRegistryME.LIMESTONE_CAVE, BiomeKeyRegistryME.DOLOMITE_CAVE,
                        BiomeKeyRegistryME.DRIPSTONE_CAVE, BiomeKeyRegistryME.MOUNTAIN_CAVE, BiomeKeyRegistryME.BASIC_CAVE,
                        BiomeKeyRegistryME.LUSH_CAVE, BiomeKeyRegistryME.FUNGUS_CAVE, BiomeKeyRegistryME.MITHRIL_CAVE),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, MiddleEarth.id("misty_road_cleanup")));
    }
}
