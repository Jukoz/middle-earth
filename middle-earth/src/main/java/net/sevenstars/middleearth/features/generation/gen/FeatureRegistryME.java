package net.sevenstars.middleearth.features.generation.gen;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.generation.worldfeatures.boulder.BigBoulderFeature;
import net.sevenstars.middleearth.features.generation.worldfeatures.boulder.BigBoulderFeatureConfig;
import net.sevenstars.middleearth.features.generation.worldfeatures.chain.ChainFeature;
import net.sevenstars.middleearth.features.generation.worldfeatures.chain.ChainFeatureConfig;
import net.sevenstars.middleearth.features.generation.worldfeatures.columns.*;
import net.sevenstars.middleearth.features.generation.worldfeatures.deltas.DeltaFeature;
import net.sevenstars.middleearth.features.generation.worldfeatures.growth.MultifaceStateFeature;
import net.sevenstars.middleearth.features.generation.worldfeatures.growth.MultifaceStateFeatureConfig;
import net.sevenstars.middleearth.features.generation.worldfeatures.ores.FastOreFeature;
import net.sevenstars.middleearth.features.generation.worldfeatures.ores.OreFeature;
import net.sevenstars.middleearth.features.generation.worldfeatures.ores.OreFeatureConfigME;
import net.sevenstars.middleearth.features.generation.worldfeatures.ores.SurfaceOreFeature;
import net.sevenstars.middleearth.features.generation.worldfeatures.pillar.PillarFeature;
import net.sevenstars.middleearth.features.generation.worldfeatures.pillar.PillarFeatureConfig;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.sevenstars.middleearth.features.generation.worldfeatures.platedfood.PlatedFoodFeature;
import net.sevenstars.middleearth.features.generation.worldfeatures.platedfood.PlatedFoodFeatureConfig;

public class FeatureRegistryME {
    public static Feature<DeltaFeatureConfig> DELTA_FEATURE = register("delta_feature", new DeltaFeature(DeltaFeatureConfig.CODEC));

    public static Feature<OreFeatureConfig> SURFACE_ORE = register("surface_ore", new SurfaceOreFeature(OreFeatureConfig.CODEC));
    public static Feature<ClusterFeatureConfig> CLUSTER = register("cluster", new ClusterFeature(ClusterFeatureConfig.CODEC));
    public static Feature<SmallPointedStoneFeatureConfig> SMALL_POINTED_STONE = register("small_pointed_stone", new SmallPointedStoneFeature(SmallPointedStoneFeatureConfig.CODEC));
    public static Feature<PillarFeatureConfig> PILLAR = register("pillar", new PillarFeature(PillarFeatureConfig.CODEC));
    public static Feature<CaveColumnFeatureConfig> CAVE_COLUMN = register("cave_columns", new CaveColumnFeature(CaveColumnFeatureConfig.CODEC));
    public static Feature<MultifaceStateFeatureConfig> MULTIFACE_PERSISTENT = register("multiface_persistent", new MultifaceStateFeature(MultifaceStateFeatureConfig.CODEC));
    public static Feature<ColumnsFeatureConfig> COLUMNS = register("columns", new ColumnsFeature(ColumnsFeatureConfig.CODEC));
    public static Feature<BigBoulderFeatureConfig> BIG_BOULDER = register("big_boulder", new BigBoulderFeature(BigBoulderFeatureConfig.CODEC));
    public static Feature<OreFeatureConfig> FAST_ORE = register("fast_ore", new FastOreFeature(OreFeatureConfig.CODEC));
    public static Feature<OreFeatureConfigME> ORE = register("ore", new OreFeature(OreFeatureConfigME.CODEC));
    public static Feature<ChainFeatureConfig> CHAIN = register("chain", new ChainFeature(ChainFeatureConfig.CODEC));
    public static Feature<PlatedFoodFeatureConfig> PLATED_FOOD = register("plated_food", new PlatedFoodFeature(PlatedFoodFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> MIRKWOOD_VINE = register("mirkwood_vine", new MirkwoodVinesFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> WILLOW_VINE = register("willow_vine", new WillowVinesFeature(DefaultFeatureConfig.CODEC));

    public static void init() {
        MiddleEarth.LOGGER.logInfoMsg("Registering new features");
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registries.FEATURE, Identifier.of(MiddleEarth.MOD_ID, name), feature);
    }
}
