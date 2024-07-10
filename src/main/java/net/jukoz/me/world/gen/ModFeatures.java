package net.jukoz.me.world.gen;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.features.columns.ColumnFeature;
import net.jukoz.me.world.features.columns.ColumnFeatureConfig;
import net.jukoz.me.world.features.ores.SurfaceOreFeature;
import net.jukoz.me.world.features.pillar.PillarFeature;
import net.jukoz.me.world.features.pillar.PillarFeatureConfig;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class ModFeatures {
    public static Feature<OreFeatureConfig> SURFACE_ORE = register("surface_ore", new SurfaceOreFeature(OreFeatureConfig.CODEC));
    public static Feature<PillarFeatureConfig> PILLAR = register("pillar", new PillarFeature(PillarFeatureConfig.CODEC));
    public static Feature<ColumnFeatureConfig> COLUMN = register("column", new ColumnFeature(ColumnFeatureConfig.CODEC));

    public static void init() {
        LoggerUtil.getInstance().logInfoMsg("Registering new features");
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registries.FEATURE, Identifier.of(MiddleEarth.MOD_ID, name), feature);
    }
}
