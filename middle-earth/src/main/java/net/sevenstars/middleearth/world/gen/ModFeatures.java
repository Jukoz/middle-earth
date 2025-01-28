package net.sevenstars.middleearth.world.gen;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.world.features.boulder.BigBoulderFeature;
import net.sevenstars.middleearth.world.features.boulder.BigBoulderFeatureConfig;
import net.sevenstars.middleearth.world.features.columns.*;
import net.sevenstars.middleearth.world.features.deltas.ModDeltaFeatures;
import net.sevenstars.middleearth.world.features.ores.ModOreFeature;
import net.sevenstars.middleearth.world.features.ores.ModOreFeatureConfig;
import net.sevenstars.middleearth.world.features.ores.SurfaceOreFeature;
import net.sevenstars.middleearth.world.features.pillar.PillarFeature;
import net.sevenstars.middleearth.world.features.pillar.PillarFeatureConfig;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;

public class ModFeatures {
    public static Feature<DeltaFeatureConfig> DELTA_FEATURE = register("delta_feature", new ModDeltaFeatures(DeltaFeatureConfig.CODEC));

    public static Feature<OreFeatureConfig> SURFACE_ORE = register("surface_ore", new SurfaceOreFeature(OreFeatureConfig.CODEC));
    public static Feature<ClusterFeatureConfig> CLUSTER = register("cluster", new ClusterFeature(ClusterFeatureConfig.CODEC));
    public static Feature<SmallPointedStoneFeatureConfig> SMALL_POINTED_STONE = register("small_pointed_stone", new SmallPointedStoneFeature(SmallPointedStoneFeatureConfig.CODEC));
    public static Feature<PillarFeatureConfig> PILLAR = register("pillar", new PillarFeature(PillarFeatureConfig.CODEC));
    public static Feature<CaveColumnFeatureConfig> CAVE_COLUMN = register("cave_columns", new CaveColumnFeature(CaveColumnFeatureConfig.CODEC));
    public static Feature<ColumnsFeatureConfig> COLUMNS = register("columns", new ColumnsFeature(ColumnsFeatureConfig.CODEC));
    public static Feature<BigBoulderFeatureConfig> BIG_BOULDER = register("big_boulder", new BigBoulderFeature(BigBoulderFeatureConfig.CODEC));
    public static Feature<ModOreFeatureConfig> ORE = register("ore", new ModOreFeature(ModOreFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> MIRKWOOD_VINE = register("mirkwood_vine", new MirkwoodVinesFeature(DefaultFeatureConfig.CODEC));

    public static void init() {
        MiddleEarth.LOGGER.logInfoMsg("Registering new features");
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registries.FEATURE, Identifier.of(MiddleEarth.MOD_ID, name), feature);
    }
}
