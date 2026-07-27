package net.sevenstars.middleearth.world.gen;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.world.features.boulder.BigBoulderFeature;
import net.sevenstars.middleearth.world.features.boulder.BigBoulderFeatureConfig;
import net.sevenstars.middleearth.world.features.chain.ChainFeature;
import net.sevenstars.middleearth.world.features.chain.ChainFeatureConfig;
import net.sevenstars.middleearth.world.features.columns.*;
import net.sevenstars.middleearth.world.features.deltas.ModDeltaFeatures;
import net.sevenstars.middleearth.world.features.growth.MultifaceStateFeature;
import net.sevenstars.middleearth.world.features.growth.MultifaceStateFeatureConfig;
import net.sevenstars.middleearth.world.features.ores.ModOreFeature;
import net.sevenstars.middleearth.world.features.ores.ModOreFeatureConfig;
import net.sevenstars.middleearth.world.features.ores.SurfaceOreFeature;
import net.sevenstars.middleearth.world.features.pillar.PillarFeature;
import net.sevenstars.middleearth.world.features.pillar.PillarFeatureConfig;
import net.sevenstars.middleearth.world.features.tree.backport.FallenTreeFeature;
import net.sevenstars.middleearth.world.features.tree.backport.FallenTreeFeatureConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.sevenstars.middleearth.world.features.platedfood.PlatedFoodFeature;
import net.sevenstars.middleearth.world.features.platedfood.PlatedFoodFeatureConfig;
import net.sevenstars.api.registries.RegistrationBridge;

public class ModFeatures {
    public static Feature<DeltaFeatureConfiguration> DELTA_FEATURE = register("delta_feature", new ModDeltaFeatures(DeltaFeatureConfiguration.CODEC));

    public static Feature<OreConfiguration> SURFACE_ORE = register("surface_ore", new SurfaceOreFeature(OreConfiguration.CODEC));
    public static Feature<ClusterFeatureConfig> CLUSTER = register("cluster", new ClusterFeature(ClusterFeatureConfig.CODEC));
    public static Feature<SmallPointedStoneFeatureConfig> SMALL_POINTED_STONE = register("small_pointed_stone", new SmallPointedStoneFeature(SmallPointedStoneFeatureConfig.CODEC));
    public static Feature<PillarFeatureConfig> PILLAR = register("pillar", new PillarFeature(PillarFeatureConfig.CODEC));
    public static Feature<CaveColumnFeatureConfig> CAVE_COLUMN = register("cave_columns", new CaveColumnFeature(CaveColumnFeatureConfig.CODEC));
    public static Feature<MultifaceStateFeatureConfig> MULTIFACE_PERSISTENT = register("multiface_persistent", new MultifaceStateFeature(MultifaceStateFeatureConfig.CODEC));
    public static Feature<ColumnsFeatureConfig> COLUMNS = register("columns", new ColumnsFeature(ColumnsFeatureConfig.CODEC));
    public static Feature<BigBoulderFeatureConfig> BIG_BOULDER = register("big_boulder", new BigBoulderFeature(BigBoulderFeatureConfig.CODEC));
    public static Feature<ModOreFeatureConfig> ORE = register("ore", new ModOreFeature(ModOreFeatureConfig.CODEC));
    public static Feature<ChainFeatureConfig> CHAIN = register("chain", new ChainFeature(ChainFeatureConfig.CODEC));
    public static Feature<PlatedFoodFeatureConfig> PLATED_FOOD = register("plated_food", new PlatedFoodFeature(PlatedFoodFeatureConfig.CODEC));
    public static Feature<FallenTreeFeatureConfig> FALLEN_TREE = register(
            "fallen_tree", new FallenTreeFeature(FallenTreeFeatureConfig.CODEC));
    public static final Feature<NoneFeatureConfiguration> MIRKWOOD_VINE = register("mirkwood_vine", new MirkwoodVinesFeature(NoneFeatureConfiguration.CODEC));
    public static final Feature<NoneFeatureConfiguration> WILLOW_VINE = register("willow_vine", new WillowVinesFeature(NoneFeatureConfiguration.CODEC));

    public static void init() {
        MiddleEarth.LOGGER.logInfoMsg("Registering new features");
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String name, F feature) {
        return RegistrationBridge.register(
                BuiltInRegistries.FEATURE,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name),
                feature
        );
    }
}
