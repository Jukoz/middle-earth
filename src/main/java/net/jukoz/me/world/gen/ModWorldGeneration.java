package net.jukoz.me.world.gen;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.biomes.caves.ModCaveBiomes;
import net.jukoz.me.world.features.ores.SurfaceOreFeature;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;

public class ModWorldGeneration {
    public static Feature<OreFeatureConfig> SURFACE_ORE = register("surface_ore", new SurfaceOreFeature(OreFeatureConfig.CODEC));

    public static void generateModWorldGen() {
        ModCaveBiomes.init();
        ModTreeGeneration.generateTrees();
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registries.FEATURE, new Identifier(MiddleEarth.MOD_ID, name), feature);
    }
}
