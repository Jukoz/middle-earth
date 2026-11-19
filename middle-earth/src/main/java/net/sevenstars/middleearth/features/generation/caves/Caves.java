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
import net.minecraft.world.gen.structure.StructureType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.generation.worldfeatures.underground.NotInDeepsPlacementModifier;

public class Caves {
    public static final StructureType<MistyDwarfMineStructure> MISTY_DWARF_MINE = () -> MistyDwarfMineStructure.CODEC;

    public static final Feature<?> MISTY_MINE_MITHRIL = new MistyMineMithrilFeature();

    public static void register() {
        Registry.register(Registries.STRUCTURE_TYPE, MiddleEarth.id("misty_dwarf_mine"), MISTY_DWARF_MINE);
        Registry.register(Registries.STRUCTURE_PROCESSOR, MiddleEarth.id("mine_shell"), MineShellProcessor.TYPE);
        MineTunnelPiece.register();
        Registry.register(Registries.FEATURE, MiddleEarth.id("misty_mine_mithril"), MISTY_MINE_MITHRIL);
        Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, MiddleEarth.id("not_in_deeps"), NotInDeepsPlacementModifier.TYPE);
        // after every other cave feature; mountain caves outside the Misty Mountains share these biomes and the feature does nothing without a mine
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(
                        BiomeKeyRegistryME.IZHERABAN_CAVE, BiomeKeyRegistryME.LIMESTONE_CAVE, BiomeKeyRegistryME.DOLOMITE_CAVE,
                        BiomeKeyRegistryME.DRIPSTONE_CAVE, BiomeKeyRegistryME.MOUNTAIN_CAVE, BiomeKeyRegistryME.BASIC_CAVE,
                        BiomeKeyRegistryME.LUSH_CAVE, BiomeKeyRegistryME.FUNGUS_CAVE, BiomeKeyRegistryME.ICE_CAVE,
                        BiomeKeyRegistryME.MITHRIL_CAVE, BiomeKeyRegistryME.NAMELESS_DEEPS,
                        BiomeKeyRegistryME.GNAWED_TUNNELS, BiomeKeyRegistryME.GLOWWORM_GROTTO),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, RegistryKey.of(RegistryKeys.PLACED_FEATURE, MiddleEarth.id("misty_mine_mithril")));
    }
}
