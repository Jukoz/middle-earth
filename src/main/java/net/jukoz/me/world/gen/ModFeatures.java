package net.jukoz.me.world.gen;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.features.boulder.BigBoulderFeature;
import net.jukoz.me.world.features.boulder.BigBoulderFeatureConfig;
import net.jukoz.me.world.features.columns.*;
import net.jukoz.me.world.features.ores.ModOreFeature;
import net.jukoz.me.world.features.ores.ModOreFeatureConfig;
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
    public static Feature<ClusterFeatureConfig> CLUSTER = register("cluster", new ClusterFeature(ClusterFeatureConfig.CODEC));
    public static Feature<SmallPointedStoneFeatureConfig> SMALL_POINTED_STONE = register("small_pointed_stone", new SmallPointedStoneFeature(SmallPointedStoneFeatureConfig.CODEC));
    public static Feature<PillarFeatureConfig> PILLAR = register("pillar", new PillarFeature(PillarFeatureConfig.CODEC));
    public static Feature<CaveColumnFeatureConfig> CAVE_COLUMN = register("cave_columns", new CaveColumnFeature(CaveColumnFeatureConfig.CODEC));
    public static Feature<ColumnsFeatureConfig> COLUMNS = register("columns", new ColumnsFeature(ColumnsFeatureConfig.CODEC));
    public static Feature<BigBoulderFeatureConfig> BIG_BOULDER = register("big_boulder", new BigBoulderFeature(BigBoulderFeatureConfig.CODEC));
    public static Feature<ModOreFeatureConfig> ORE = register("ore", new ModOreFeature(ModOreFeatureConfig.CODEC));

    public static void init() {
        LoggerUtil.logInfoMsg("Registering new features");
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registries.FEATURE, Identifier.of(MiddleEarth.MOD_ID, name), feature);
    }
}
