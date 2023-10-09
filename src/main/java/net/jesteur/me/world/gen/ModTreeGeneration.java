package net.jesteur.me.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.mixin.TrunkPlacerTypeInvoker;
import net.jesteur.me.utils.RegistryUtils;
import net.jesteur.me.world.biomes.MEBiomeKeys;
import net.jesteur.me.world.features.BigTrunkPlacer;
import net.jesteur.me.world.features.ModPlacedFeatures;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTreeGeneration {
    public static final Registry<TrunkPlacerType<?>> registry = Registries.TRUNK_PLACER_TYPE;
    public static final TrunkPlacerType<BigTrunkPlacer> RICH_TRUNK_PLACER = RegistryUtils.register(
            registry, "big_trunk", new TrunkPlacerType<>(BigTrunkPlacer.CODEC)
    );
    //public static final TrunkPlacerType<BigTrunkPlacer> RICH_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister(MiddleEarth.MOD_ID + ":big_trunk", BigTrunkPlacer.CODEC);
    public static void generateTrees() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(MEBiomeKeys.MIRKWOOD),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.MIRKWOOD_PLACED_TREE_KEY);

    }
}
