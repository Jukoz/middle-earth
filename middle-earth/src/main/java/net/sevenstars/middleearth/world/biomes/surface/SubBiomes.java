package net.sevenstars.middleearth.world.biomes.surface;

import net.sevenstars.middleearth.world.biomes.MEBiomeKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class SubBiomes {
    public static HashMap<RegistryKey<Biome>, SubBiome> subBiomesMap;

    public static void loadSubBiomes() {
        subBiomesMap = new HashMap<>();

        subBiomesMap.put(MEBiomeKeys.ANDUIN_VALES, new SubBiome()
                .addSubBiomeData(-1.0f, -0.1f, MEBiomeKeys.ANDUIN_VALES_FOREST));
        subBiomesMap.put(MEBiomeKeys.DALE, new SubBiome()
                .addSubBiomeData(-1.0f, -0.27f, MEBiomeKeys.DALE_FOREST)
                .addSubBiomeData(0.31f, 2.01f, MEBiomeKeys.DALE_MEADOW));
        subBiomesMap.put(MEBiomeKeys.LINDON, new SubBiome(56, 1.25f)
                .addSubBiomeData(-1.0f, -0.31f, MEBiomeKeys.LINDON_MEADOW)
                .addSubBiomeData(0.22f, 0.44f, MEBiomeKeys.LINDON_FOREST)
                .addSubBiomeData(0.44f, 2.0f, MEBiomeKeys.LINDON_HIDDEN_BLOSSOM));
        subBiomesMap.put(MEBiomeKeys.OLD_ANGMAR, new SubBiome()
                .addSubBiomeData(-1.0f, -0.2f, MEBiomeKeys.OLD_ANGMAR_FOREST)
                .addSubBiomeData(0.22f, 0.27f, MEBiomeKeys.OLD_ANGMAR_COLD_HILL, true)
                .addSubBiomeData(0.27f, 2.0f, MEBiomeKeys.OLD_ANGMAR_FROZEN_HILL, true));
        subBiomesMap.put(MEBiomeKeys.OLD_ARTHEDAIN, new SubBiome()
                .addSubBiomeData(-1.0f, -0.28f, MEBiomeKeys.OLD_ARTHEDAIN_FOREST)
                .addSubBiomeData(0.25f, 1.01f, MEBiomeKeys.OLD_ARTHEDAIN_MEADOW));
        subBiomesMap.put(MEBiomeKeys.OLD_CARDOLAN, new SubBiome(64)
                .addSubBiomeData(-1.0f, -0.15f, MEBiomeKeys.OLD_CARDOLAN_FOREST)
                .addSubBiomeData(0.25f, 2.01f, MEBiomeKeys.OLD_CARDOLAN_HILL, true));
        subBiomesMap.put(MEBiomeKeys.OLD_RHUDAUR, new SubBiome()
                .addSubBiomeData(-1.0f, -0.07f, MEBiomeKeys.OLD_RHUDAUR_FOREST)
                .addSubBiomeData(0.18f, 2.01f, MEBiomeKeys.OLD_RHUDAUR_HILL, true));
        subBiomesMap.put(MEBiomeKeys.SHIRE, new SubBiome(72)
                .addSubBiomeData(-1.0f, -0.35f, MEBiomeKeys.SHIRE_WOODS)
                .addSubBiomeData(0.35f, 2.01f, MEBiomeKeys.SHIRE_HILLS, true));
        subBiomesMap.put(MEBiomeKeys.SHIRE_EDGE, new SubBiome(72)
                .addSubBiomeData(-1.0f, -0.27f, MEBiomeKeys.SHIRE_WOODS)
                .addSubBiomeData(0.32f, 2.01f, MEBiomeKeys.SHIRE_HILLS, true));
        subBiomesMap.put(MEBiomeKeys.EREGION, new SubBiome()
                .addSubBiomeData(-1.0f, -0.08f, MEBiomeKeys.EREGION_FOREST)
                .addSubBiomeData(0.28f, 1.0f, MEBiomeKeys.EREGION_GLADE));
        subBiomesMap.put(MEBiomeKeys.ENEDWAITH, new SubBiome()
                .addSubBiomeData(-1.0f, -0.63f, MEBiomeKeys.ENEDWAITH_WHEAT_FIELD)
                .addSubBiomeData(0.35f, 1.0f, MEBiomeKeys.ENEDWAITH_FIELD));
        subBiomesMap.put(MEBiomeKeys.NORTHERN_DUNLAND, new SubBiome(96)
                .addSubBiomeData(-1.0f, -0.35f, MEBiomeKeys.NORTHERN_DUNLAND_GLADE)
                .addSubBiomeData(0.31f, 2.01f, MEBiomeKeys.DUNLAND_HILLS, true));

        subBiomesMap.put(MEBiomeKeys.THE_WOLD, new SubBiome()
                .addSubBiomeData(-1.0f, -0.76f, MEBiomeKeys.THE_WOLD_WHEAT_FIELD)
                .addSubBiomeData(0.35f, 1.0f, MEBiomeKeys.THE_WOLD_WHEAT_FIELD));

        subBiomesMap.put(MEBiomeKeys.DORWINION, new SubBiome()
                .addSubBiomeData(-1.0f, -0.35f, MEBiomeKeys.DORWINION_LAVENDER_FIELD)
                .addSubBiomeData(0.36f, 2.01f, MEBiomeKeys.DORWINION_LAVENDER_FIELD));

        subBiomesMap.put(MEBiomeKeys.BLUE_MOUNTAINS_FOOTHILLS, new SubBiome(56, 0.65f)
                .addSubBiomeData(-1.0f, -0.2f, MEBiomeKeys.BLUE_MOUNTAINS_WOODS)
                .addSubBiomeData(0.2f, 2.01f, MEBiomeKeys.BLUE_MOUNTAINS_WOODS));
        subBiomesMap.put(MEBiomeKeys.BLUE_MOUNTAINS_BASE, new SubBiome(56, 0.65f)
                .addSubBiomeData(-1.0f, -0.25f, MEBiomeKeys.BLUE_MOUNTAINS_WOODS)
                .addSubBiomeData(0.25f, 2.01f, MEBiomeKeys.BLUE_MOUNTAINS_WOODS));
        subBiomesMap.put(MEBiomeKeys.BLUE_MOUNTAINS, new SubBiome(56, 0.65f)
                .addSubBiomeData(-1.0f, -0.25f, MEBiomeKeys.BLUE_MOUNTAINS_WOODS)
                .addSubBiomeData(0.25f, 2.01f, MEBiomeKeys.BLUE_MOUNTAINS_WOODS));

        subBiomesMap.put(MEBiomeKeys.ANORIEN, new SubBiome(32)
                .addSubBiomeData(-1.0f, -0.37f, MEBiomeKeys.GONDOR_FOREST)
                .addSubBiomeData(0.36f, 2.01f, MEBiomeKeys.GONDOR_HILL, true));

        subBiomesMap.put(MEBiomeKeys.PELENNOR_FIELDS, new SubBiome()
                .addSubBiomeData(-1.0f, -0.51f, MEBiomeKeys.PELENNOR_WHEAT_FIELD)
                .addSubBiomeData(0.31f, 2.01f, MEBiomeKeys.PELENNOR_WHEAT_FIELD));

        subBiomesMap.put(MEBiomeKeys.LOSSARNACH, new SubBiome(180, 1.2f)
                .addSubBiomeData(-1.0f, -0.32f, MEBiomeKeys.GONDOR_FOREST)
                .addSubBiomeData(0.38f, 2.01f, MEBiomeKeys.LOSSARNACH_CHERRY_BLOSSOM, true));
        subBiomesMap.put(MEBiomeKeys.LOSSARNACH_VALLEY, new SubBiome(64, 0.28f)
                .addSubBiomeData(-1.0f, -0.4f, MEBiomeKeys.LOSSARNACH_VALLEY_RED)
                .addSubBiomeData(-0.4f, -0.26f, MEBiomeKeys.LOSSARNACH_VALLEY_ORANGE)
                .addSubBiomeData(-0.26f, -0.11f, MEBiomeKeys.LOSSARNACH_VALLEY_YELLOW)
                .addSubBiomeData(-0.11f, -0.09f, MEBiomeKeys.LOSSARNACH_VALLEY_GREEN)

                .addSubBiomeData(0.09f, 0.11f, MEBiomeKeys.LOSSARNACH_VALLEY_GREEN)
                .addSubBiomeData(0.11f, 0.26f, MEBiomeKeys.LOSSARNACH_VALLEY_YELLOW)
                .addSubBiomeData(0.26f, 0.4f, MEBiomeKeys.LOSSARNACH_VALLEY_ORANGE)
                .addSubBiomeData(0.4f, 2.01f, MEBiomeKeys.LOSSARNACH_VALLEY_RED)
        );
        subBiomesMap.put(MEBiomeKeys.LEBENNIN, new SubBiome(64)
                .addSubBiomeData(-1.0f, -0.32f, MEBiomeKeys.GONDOR_FOREST)
                .addSubBiomeData(0.34f, 2.01f, MEBiomeKeys.LEBENNIN_HILLS, true)
        );
        subBiomesMap.put(MEBiomeKeys.LAMEDON, new SubBiome(64)
                .addSubBiomeData(-1.0f, -0.40f, MEBiomeKeys.GONDOR_FOREST)
                .addSubBiomeData(0.31f, 2.01f, MEBiomeKeys.LAMEDON_HILLS, true)
        );
        subBiomesMap.put(MEBiomeKeys.BELFALAS, new SubBiome()
                .addSubBiomeData(-1.0f, -0.33f, MEBiomeKeys.BELFALAS_FOREST)
                .addSubBiomeData(0.34f, 2.01f, MEBiomeKeys.BELFALAS_FOREST)
        );
        subBiomesMap.put(MEBiomeKeys.BLACKROOT_VALE, new SubBiome()
                .addSubBiomeData(-1.0f, -0.3f, MEBiomeKeys.BLACKROOT_FOREST)
                .addSubBiomeData(0.31f, 2.01f, MEBiomeKeys.BLACKROOT_FOREST)
        );
        subBiomesMap.put(MEBiomeKeys.GONDOR, new SubBiome()
                .addSubBiomeData(-1.0f, -0.3f, MEBiomeKeys.GONDOR_FOREST)
                .addSubBiomeData(0.3f, 2.01f, MEBiomeKeys.GONDOR_HILL, true)
        );

        subBiomesMap.put(MEBiomeKeys.ROHAN, new SubBiome(64, 1.1f)
                .addSubBiomeData(-1.0f, -0.33f, MEBiomeKeys.ROHAN_FOREST)
                .addSubBiomeData(0.36f, 2.01f, MEBiomeKeys.ROHAN_FIELD, false));

        subBiomesMap.put(MEBiomeKeys.LOTHLORIEN, new SubBiome()
                .addSubBiomeData(-1.0f, -0.42f, MEBiomeKeys.LOTHLORIEN_BLOSSOM)
                .addSubBiomeData(-0.09f, 0.07f, MEBiomeKeys.LOTHLORIEN_GLADE));

        subBiomesMap.put(MEBiomeKeys.MINHIRIATH, new SubBiome()
                .addSubBiomeData(-1.0f, -0.42f, MEBiomeKeys.MINHIRIATH_WHEAT_FIELD));

        subBiomesMap.put(MEBiomeKeys.MIRKWOOD, new SubBiome()
                .addSubBiomeData(-1.0f, -0.33f, MEBiomeKeys.WEBBED_WOODS));
        subBiomesMap.put(MEBiomeKeys.WOODLAND_REALM, new SubBiome()
                .addSubBiomeData(-1.0f, -0.36f, MEBiomeKeys.WOODLAND_GLADE)
                .addSubBiomeData(0.37f, 1.0f, MEBiomeKeys.WOODLAND_GLADE));
        subBiomesMap.put(MEBiomeKeys.DARK_MIRKWOOD, new SubBiome()
                .addSubBiomeData(-1.0f, -0.3f, MEBiomeKeys.WEBBED_DARK_WOODS));

        subBiomesMap.put(MEBiomeKeys.GUNDABAD_PLAINS, new SubBiome(96)
                .addSubBiomeData(-1.0f, -0.34f, MEBiomeKeys.NORTHERN_RHOVANION_FOREST)
                .addSubBiomeData(0.33f, 2.01f, MEBiomeKeys.NORTHERN_RHOVANION_HILLS, true));
        subBiomesMap.put(MEBiomeKeys.GREY_PLAINS, new SubBiome(96)
                .addSubBiomeData(-1.0f, -0.32f, MEBiomeKeys.NORTHERN_RHOVANION_FOREST)
                .addSubBiomeData(0.33f, 2.01f, MEBiomeKeys.NORTHERN_RHOVANION_HILLS, true));
        subBiomesMap.put(MEBiomeKeys.GREY_MOUNTAINS_BASE, new SubBiome()
                .addSubBiomeData(-1.0f, -0.36f, MEBiomeKeys.GREY_ASHEN_WOODS)
                .addSubBiomeData(0.35f, 2.01f, MEBiomeKeys.GREY_ASHEN_WOODS));
        subBiomesMap.put(MEBiomeKeys.LONELY_MOUNTAIN_TAIGA, new SubBiome(96)
                .addSubBiomeData(-1.0f, -0.3f, MEBiomeKeys.NORTHERN_RHOVANION_FOREST)
                .addSubBiomeData(0.33f, 2.01f, MEBiomeKeys.NORTHERN_RHOVANION_HILLS, true));
        subBiomesMap.put(MEBiomeKeys.IRON_HILLS_PLAINS, new SubBiome(96)
                .addSubBiomeData(-1.0f, -0.3f, MEBiomeKeys.NORTHERN_RHOVANION_FOREST)
                .addSubBiomeData(0.33f, 2.01f, MEBiomeKeys.NORTHERN_RHOVANION_HILLS, true));

        subBiomesMap.put(MEBiomeKeys.ITHILIEN, new SubBiome()
                .addSubBiomeData(-1.0f, -0.37f, MEBiomeKeys.ITHILIEN_GLADE)
                .addSubBiomeData(0.35f, 2.01f, MEBiomeKeys.ITHILIEN_GLADE));
        subBiomesMap.put(MEBiomeKeys.ITHILIEN_WASTES, new SubBiome()
                .addSubBiomeData(-1.0f, -0.37f, MEBiomeKeys.ITHILIEN_WASTES_GLADE)
                .addSubBiomeData(0.35f, 2.01f, MEBiomeKeys.ITHILIEN_WASTES_GLADE));

        subBiomesMap.put(MEBiomeKeys.GORGOROTH, new SubBiome()
                .addSubBiomeData(-1.0f, -0.34f, MEBiomeKeys.GORGOROTH_ASHEN_WOODS)
                .addSubBiomeData(0.41f, 2.01f, MEBiomeKeys.GORGOROTH_DELTA));
        subBiomesMap.put(MEBiomeKeys.MORDOR, new SubBiome(144)
                .addSubBiomeData(-1.0f, -0.3f, MEBiomeKeys.MORDOR_ASHEN_FOREST)
                .addSubBiomeData(0.27f, 2.01f, MEBiomeKeys.MORDOR_HILL, true));
        subBiomesMap.put(MEBiomeKeys.MORGUL_VALE, new SubBiome()
                .addSubBiomeData(-1.0f, -0.3f, MEBiomeKeys.MORGUL_FOREST)
                .addSubBiomeData(0.3f, 1.0f, MEBiomeKeys.MORGUL_FOREST));
        subBiomesMap.put(MEBiomeKeys.NURN, new SubBiome(144)
                .addSubBiomeData(-1.0f, -0.31f, MEBiomeKeys.NURN_FOREST)
                .addSubBiomeData(0.27f, 2.01f, MEBiomeKeys.NURN_HILL, true));
        subBiomesMap.put(MEBiomeKeys.NURN_EDGE, new SubBiome()
                .addSubBiomeData(-1.0f, -0.33f, MEBiomeKeys.NURN_EDGE_WOODS));
        subBiomesMap.put(MEBiomeKeys.UDUN, new SubBiome(144)
                .addSubBiomeData(-1.0f, -0.31f, MEBiomeKeys.MORDOR_ASHEN_FOREST)
                .addSubBiomeData(0.29f, 2.01f, MEBiomeKeys.MORDOR_HILL, true));


        subBiomesMap.put(MEBiomeKeys.EASTERN_RHOVANION, new SubBiome()
                .addSubBiomeData(-1.0f, -0.35f, MEBiomeKeys.EASTERN_RHOVANION_FOREST)
                .addSubBiomeData(0.34f, 2.01f, MEBiomeKeys.EASTERN_RHOVANION_FOREST));
        subBiomesMap.put(MEBiomeKeys.SOUTHEAST_RHOVANION, new SubBiome()
                .addSubBiomeData(-1.0f, -0.32f, MEBiomeKeys.EASTERN_RHOVANION_FOREST)
                .addSubBiomeData(0.33f, 2.01f, MEBiomeKeys.SOUTHEAST_RHOVANION_FIELD));

        subBiomesMap.put(MEBiomeKeys.RHUN, new SubBiome(48, 1.3f)
                .addSubBiomeData(-1.0f, -0.31f, MEBiomeKeys.RHUN_FIELD)
                .addSubBiomeData(0.22f, 0.45f, MEBiomeKeys.RHUN_FOREST)
                .addSubBiomeData(0.45f, 2.0f, MEBiomeKeys.RHUN_HIDDEN_BLOSSOM));

        subBiomesMap.put(MEBiomeKeys.UMBAR, new SubBiome()
                .addSubBiomeData(-1.0f, -0.36f, MEBiomeKeys.UMBAR_WOODS));
        subBiomesMap.put(MEBiomeKeys.HARAD, new SubBiome()
                .addSubBiomeData(-1.0f, -0.37f, MEBiomeKeys.HARAD_WOODS)
                .addSubBiomeData(0.36f, 1.0f, MEBiomeKeys.HARAD_WOODS));
    }

    public static boolean isSubBiome(RegistryKey<Biome> biomeRegistryKey) {
        AtomicBoolean containsBiome = new AtomicBoolean(false);
        subBiomesMap.forEach((key, value) -> {
            if(value.containsSubBiome(biomeRegistryKey)) {
                containsBiome.set(true);
            }
        });
        return containsBiome.get();
    }

    public static SubBiome getSubBiome(RegistryKey<Biome> biomeRegistryKey) {
        return subBiomesMap.get(biomeRegistryKey);
    }

    public static SubBiome getSubBiomeFromChild(RegistryKey<Biome> biomeRegistryKey) {
        for(Map.Entry<RegistryKey<Biome>, SubBiome> entry : subBiomesMap.entrySet()) {
            if(entry.getValue().containsSubBiome(biomeRegistryKey)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
