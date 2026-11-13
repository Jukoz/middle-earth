package net.sevenstars.middleearth.world.biomes.surface;

import net.sevenstars.middleearth.world.biomes.BiomeKeyRegistryME;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class SubBiomes {
    public static HashMap<RegistryKey<Biome>, SubBiome> subBiomesMap;

    public static void loadSubBiomes() {
        subBiomesMap = new HashMap<>();

        subBiomesMap.put(BiomeKeyRegistryME.ANDUIN_VALES, new SubBiome()
                .addSubBiomeData(-1.0f, -0.1f, BiomeKeyRegistryME.ANDUIN_VALES_FOREST));
        subBiomesMap.put(BiomeKeyRegistryME.DALE, new SubBiome()
                .addSubBiomeData(-1.0f, -0.27f, BiomeKeyRegistryME.DALE_FOREST)
                .addSubBiomeData(0.31f, 2.01f, BiomeKeyRegistryME.DALE_MEADOW));
        subBiomesMap.put(BiomeKeyRegistryME.LINDON, new SubBiome(56, 1.25f)
                .addSubBiomeData(-1.0f, -0.31f, BiomeKeyRegistryME.LINDON_MEADOW)
                .addSubBiomeData(0.22f, 0.44f, BiomeKeyRegistryME.LINDON_FOREST)
                .addSubBiomeData(0.44f, 2.0f, BiomeKeyRegistryME.LINDON_HIDDEN_BLOSSOM));
        subBiomesMap.put(BiomeKeyRegistryME.OLD_ANGMAR, new SubBiome()
                .addSubBiomeData(-1.0f, -0.2f, BiomeKeyRegistryME.OLD_ANGMAR_FOREST)
                .addSubBiomeData(0.22f, 0.27f, BiomeKeyRegistryME.OLD_ANGMAR_COLD_HILL, true)
                .addSubBiomeData(0.27f, 2.0f, BiomeKeyRegistryME.OLD_ANGMAR_FROZEN_HILL, true));
        subBiomesMap.put(BiomeKeyRegistryME.OLD_ARTHEDAIN, new SubBiome()
                .addSubBiomeData(-1.0f, -0.28f, BiomeKeyRegistryME.OLD_ARTHEDAIN_FOREST)
                .addSubBiomeData(0.25f, 1.01f, BiomeKeyRegistryME.OLD_ARTHEDAIN_MEADOW));
        subBiomesMap.put(BiomeKeyRegistryME.OLD_CARDOLAN, new SubBiome(64)
                .addSubBiomeData(-1.0f, -0.15f, BiomeKeyRegistryME.OLD_CARDOLAN_FOREST)
                .addSubBiomeData(0.25f, 2.01f, BiomeKeyRegistryME.OLD_CARDOLAN_HILL, true));
        subBiomesMap.put(BiomeKeyRegistryME.OLD_RHUDAUR, new SubBiome()
                .addSubBiomeData(-1.0f, -0.07f, BiomeKeyRegistryME.OLD_RHUDAUR_FOREST)
                .addSubBiomeData(0.18f, 2.01f, BiomeKeyRegistryME.OLD_RHUDAUR_HILL, true));
        subBiomesMap.put(BiomeKeyRegistryME.SHIRE, new SubBiome(72)
                .addSubBiomeData(-1.0f, -0.35f, BiomeKeyRegistryME.SHIRE_WOODS)
                .addSubBiomeData(0.35f, 2.01f, BiomeKeyRegistryME.SHIRE_HILLS, true));
        subBiomesMap.put(BiomeKeyRegistryME.SHIRE_EDGE, new SubBiome(72)
                .addSubBiomeData(-1.0f, -0.27f, BiomeKeyRegistryME.SHIRE_WOODS)
                .addSubBiomeData(0.32f, 2.01f, BiomeKeyRegistryME.SHIRE_HILLS, true));
        subBiomesMap.put(BiomeKeyRegistryME.EREGION, new SubBiome()
                .addSubBiomeData(-1.0f, -0.08f, BiomeKeyRegistryME.EREGION_FOREST)
                .addSubBiomeData(0.28f, 1.0f, BiomeKeyRegistryME.EREGION_GLADE));
        subBiomesMap.put(BiomeKeyRegistryME.ENEDWAITH, new SubBiome()
                .addSubBiomeData(-1.0f, -0.63f, BiomeKeyRegistryME.ENEDWAITH_WHEAT_FIELD)
                .addSubBiomeData(0.35f, 1.0f, BiomeKeyRegistryME.ENEDWAITH_FIELD));
        subBiomesMap.put(BiomeKeyRegistryME.DUNLAND, new SubBiome(96)
                .addSubBiomeData(-1.0f, -0.35f, BiomeKeyRegistryME.NORTHERN_DUNLAND_GLADE)
                .addSubBiomeData(0.31f, 2.01f, BiomeKeyRegistryME.DUNLAND_HILLS, true));

        subBiomesMap.put(BiomeKeyRegistryME.THE_WOLD, new SubBiome()
                .addSubBiomeData(-1.0f, -0.76f, BiomeKeyRegistryME.THE_WOLD_WHEAT_FIELD)
                .addSubBiomeData(0.35f, 1.0f, BiomeKeyRegistryME.THE_WOLD_WHEAT_FIELD));

        subBiomesMap.put(BiomeKeyRegistryME.DORWINION, new SubBiome()
                .addSubBiomeData(-1.0f, -0.35f, BiomeKeyRegistryME.DORWINION_LAVENDER_FIELD)
                .addSubBiomeData(0.36f, 2.01f, BiomeKeyRegistryME.DORWINION_LAVENDER_FIELD));

        subBiomesMap.put(BiomeKeyRegistryME.BLUE_MOUNTAINS_FOOTHILLS, new SubBiome(56, 0.65f)
                .addSubBiomeData(-1.0f, -0.2f, BiomeKeyRegistryME.BLUE_MOUNTAINS_WOODS)
                .addSubBiomeData(0.2f, 2.01f, BiomeKeyRegistryME.BLUE_MOUNTAINS_WOODS));
        subBiomesMap.put(BiomeKeyRegistryME.BLUE_MOUNTAINS_BASE, new SubBiome(56, 0.65f)
                .addSubBiomeData(-1.0f, -0.25f, BiomeKeyRegistryME.BLUE_MOUNTAINS_WOODS)
                .addSubBiomeData(0.25f, 2.01f, BiomeKeyRegistryME.BLUE_MOUNTAINS_WOODS));
        subBiomesMap.put(BiomeKeyRegistryME.BLUE_MOUNTAINS, new SubBiome(56, 0.65f)
                .addSubBiomeData(-1.0f, -0.25f, BiomeKeyRegistryME.BLUE_MOUNTAINS_WOODS)
                .addSubBiomeData(0.25f, 2.01f, BiomeKeyRegistryME.BLUE_MOUNTAINS_WOODS));

        subBiomesMap.put(BiomeKeyRegistryME.ANORIEN, new SubBiome(32)
                .addSubBiomeData(-1.0f, -0.37f, BiomeKeyRegistryME.GONDOR_FOREST)
                .addSubBiomeData(0.36f, 2.01f, BiomeKeyRegistryME.GONDOR_HILL, true));

        subBiomesMap.put(BiomeKeyRegistryME.PELENNOR_FIELDS, new SubBiome()
                .addSubBiomeData(-1.0f, -0.51f, BiomeKeyRegistryME.PELENNOR_WHEAT_FIELD)
                .addSubBiomeData(0.31f, 2.01f, BiomeKeyRegistryME.PELENNOR_WHEAT_FIELD));

        subBiomesMap.put(BiomeKeyRegistryME.LOSSARNACH, new SubBiome(180, 1.2f)
                .addSubBiomeData(-1.0f, -0.32f, BiomeKeyRegistryME.GONDOR_FOREST)
                .addSubBiomeData(0.38f, 2.01f, BiomeKeyRegistryME.LOSSARNACH_CHERRY_BLOSSOM, true));
        subBiomesMap.put(BiomeKeyRegistryME.LOSSARNACH_VALLEY, new SubBiome(64, 0.28f)
                .addSubBiomeData(-1.0f, -0.4f, BiomeKeyRegistryME.LOSSARNACH_VALLEY_RED)
                .addSubBiomeData(-0.4f, -0.26f, BiomeKeyRegistryME.LOSSARNACH_VALLEY_ORANGE)
                .addSubBiomeData(-0.26f, -0.11f, BiomeKeyRegistryME.LOSSARNACH_VALLEY_YELLOW)
                .addSubBiomeData(-0.11f, -0.09f, BiomeKeyRegistryME.LOSSARNACH_VALLEY_GREEN)

                .addSubBiomeData(0.09f, 0.11f, BiomeKeyRegistryME.LOSSARNACH_VALLEY_GREEN)
                .addSubBiomeData(0.11f, 0.26f, BiomeKeyRegistryME.LOSSARNACH_VALLEY_YELLOW)
                .addSubBiomeData(0.26f, 0.4f, BiomeKeyRegistryME.LOSSARNACH_VALLEY_ORANGE)
                .addSubBiomeData(0.4f, 2.01f, BiomeKeyRegistryME.LOSSARNACH_VALLEY_RED)
        );
        subBiomesMap.put(BiomeKeyRegistryME.LEBENNIN, new SubBiome(64)
                .addSubBiomeData(-1.0f, -0.32f, BiomeKeyRegistryME.GONDOR_FOREST)
                .addSubBiomeData(0.34f, 2.01f, BiomeKeyRegistryME.LEBENNIN_HILLS, true)
        );
        subBiomesMap.put(BiomeKeyRegistryME.LAMEDON, new SubBiome(64)
                .addSubBiomeData(-1.0f, -0.40f, BiomeKeyRegistryME.GONDOR_FOREST)
                .addSubBiomeData(0.31f, 2.01f, BiomeKeyRegistryME.LAMEDON_HILLS, true)
        );
        subBiomesMap.put(BiomeKeyRegistryME.BELFALAS, new SubBiome()
                .addSubBiomeData(-1.0f, -0.33f, BiomeKeyRegistryME.BELFALAS_FOREST)
                .addSubBiomeData(0.34f, 2.01f, BiomeKeyRegistryME.BELFALAS_FOREST)
        );
        subBiomesMap.put(BiomeKeyRegistryME.BLACKROOT_VALE, new SubBiome()
                .addSubBiomeData(-1.0f, -0.3f, BiomeKeyRegistryME.BLACKROOT_FOREST)
                .addSubBiomeData(0.31f, 2.01f, BiomeKeyRegistryME.BLACKROOT_FOREST)
        );
        subBiomesMap.put(BiomeKeyRegistryME.GONDOR, new SubBiome()
                .addSubBiomeData(-1.0f, -0.3f, BiomeKeyRegistryME.GONDOR_FOREST)
                .addSubBiomeData(0.3f, 2.01f, BiomeKeyRegistryME.GONDOR_HILL, true)
        );

        subBiomesMap.put(BiomeKeyRegistryME.ROHAN, new SubBiome(64, 1.1f)
                .addSubBiomeData(-1.0f, -0.33f, BiomeKeyRegistryME.ROHAN_FOREST)
                .addSubBiomeData(0.36f, 2.01f, BiomeKeyRegistryME.ROHAN_FIELD, false));

        subBiomesMap.put(BiomeKeyRegistryME.LOTHLORIEN, new SubBiome()
                .addSubBiomeData(-1.0f, -0.42f, BiomeKeyRegistryME.LOTHLORIEN_BLOSSOM)
                .addSubBiomeData(-0.09f, 0.07f, BiomeKeyRegistryME.LOTHLORIEN_GLADE));

        subBiomesMap.put(BiomeKeyRegistryME.MINHIRIATH, new SubBiome()
                .addSubBiomeData(-1.0f, -0.42f, BiomeKeyRegistryME.MINHIRIATH_WHEAT_FIELD));

        subBiomesMap.put(BiomeKeyRegistryME.MIRKWOOD, new SubBiome()
                .addSubBiomeData(-1.0f, -0.33f, BiomeKeyRegistryME.WEBBED_WOODS));
        subBiomesMap.put(BiomeKeyRegistryME.WOODLAND_REALM, new SubBiome()
                .addSubBiomeData(-1.0f, -0.35f, BiomeKeyRegistryME.WOODLAND_GLADE)
                .addSubBiomeData(0.3f, 1.0f, BiomeKeyRegistryME.AUTUMN_WOODLAND));
        subBiomesMap.put(BiomeKeyRegistryME.DARK_MIRKWOOD, new SubBiome()
                .addSubBiomeData(-1.0f, -0.3f, BiomeKeyRegistryME.WEBBED_DARK_WOODS));

        subBiomesMap.put(BiomeKeyRegistryME.GUNDABAD_PLAINS, new SubBiome(96)
                .addSubBiomeData(-1.0f, -0.34f, BiomeKeyRegistryME.GUNDABAD_WOODS)
                .addSubBiomeData(0.33f, 2.01f, BiomeKeyRegistryME.GUNDABAD_WOODS));
        subBiomesMap.put(BiomeKeyRegistryME.GREY_PLAINS, new SubBiome(96)
                .addSubBiomeData(-1.0f, -0.32f, BiomeKeyRegistryME.GREY_ASHEN_WOODS)
                .addSubBiomeData(0.33f, 2.01f, BiomeKeyRegistryME.GREY_FOREST));
        subBiomesMap.put(BiomeKeyRegistryME.GREY_MOUNTAINS_BASE, new SubBiome()
                .addSubBiomeData(-1.0f, -0.36f, BiomeKeyRegistryME.GREY_ASHEN_WOODS)
                .addSubBiomeData(0.35f, 2.01f, BiomeKeyRegistryME.GREY_ASHEN_WOODS));
        subBiomesMap.put(BiomeKeyRegistryME.LONELY_MOUNTAIN_TAIGA, new SubBiome(96)
                .addSubBiomeData(-1.0f, -0.3f, BiomeKeyRegistryME.NORTHERN_RHOVANION_FOREST)
                .addSubBiomeData(0.33f, 2.01f, BiomeKeyRegistryME.NORTHERN_RHOVANION_HILLS, true));
        subBiomesMap.put(BiomeKeyRegistryME.IRON_HILLS_PLAINS, new SubBiome(96)
                .addSubBiomeData(-1.0f, -0.3f, BiomeKeyRegistryME.NORTHERN_RHOVANION_FOREST)
                .addSubBiomeData(0.33f, 2.01f, BiomeKeyRegistryME.NORTHERN_RHOVANION_HILLS, true));

        subBiomesMap.put(BiomeKeyRegistryME.ITHILIEN, new SubBiome()
                .addSubBiomeData(-1.0f, -0.37f, BiomeKeyRegistryME.ITHILIEN_GLADE)
                .addSubBiomeData(0.35f, 2.01f, BiomeKeyRegistryME.ITHILIEN_GLADE));
        subBiomesMap.put(BiomeKeyRegistryME.ITHILIEN_WASTES, new SubBiome()
                .addSubBiomeData(-1.0f, -0.37f, BiomeKeyRegistryME.ITHILIEN_WASTES_GLADE)
                .addSubBiomeData(0.35f, 2.01f, BiomeKeyRegistryME.ITHILIEN_WASTES_GLADE));

        subBiomesMap.put(BiomeKeyRegistryME.GORGOROTH, new SubBiome()
                .addSubBiomeData(-1.0f, -0.34f, BiomeKeyRegistryME.GORGOROTH_ASHEN_WOODS)
                .addSubBiomeData(0.41f, 2.01f, BiomeKeyRegistryME.GORGOROTH_DELTA));
        subBiomesMap.put(BiomeKeyRegistryME.MORDOR, new SubBiome(144)
                .addSubBiomeData(-1.0f, -0.3f, BiomeKeyRegistryME.MORDOR_ASHEN_FOREST)
                .addSubBiomeData(0.27f, 2.01f, BiomeKeyRegistryME.MORDOR_HILL, true));
        subBiomesMap.put(BiomeKeyRegistryME.MORGUL_VALE, new SubBiome()
                .addSubBiomeData(-1.0f, -0.3f, BiomeKeyRegistryME.MORGUL_FOREST)
                .addSubBiomeData(0.3f, 1.0f, BiomeKeyRegistryME.MORGUL_FOREST));
        subBiomesMap.put(BiomeKeyRegistryME.NURN, new SubBiome(144)
                .addSubBiomeData(-1.0f, -0.31f, BiomeKeyRegistryME.NURN_FOREST)
                .addSubBiomeData(0.27f, 2.01f, BiomeKeyRegistryME.NURN_HILL, true));
        subBiomesMap.put(BiomeKeyRegistryME.NURN_EDGE, new SubBiome()
                .addSubBiomeData(-1.0f, -0.33f, BiomeKeyRegistryME.NURN_EDGE_WOODS));
        subBiomesMap.put(BiomeKeyRegistryME.UDUN, new SubBiome(144)
                .addSubBiomeData(-1.0f, -0.31f, BiomeKeyRegistryME.MORDOR_ASHEN_FOREST)
                .addSubBiomeData(0.29f, 2.01f, BiomeKeyRegistryME.MORDOR_HILL, true));


        subBiomesMap.put(BiomeKeyRegistryME.EASTERN_RHOVANION, new SubBiome()
                .addSubBiomeData(-1.0f, -0.35f, BiomeKeyRegistryME.EASTERN_RHOVANION_FOREST)
                .addSubBiomeData(0.34f, 2.01f, BiomeKeyRegistryME.EASTERN_RHOVANION_FOREST));
        subBiomesMap.put(BiomeKeyRegistryME.SOUTHEAST_RHOVANION, new SubBiome()
                .addSubBiomeData(-1.0f, -0.32f, BiomeKeyRegistryME.EASTERN_RHOVANION_FOREST)
                .addSubBiomeData(0.33f, 2.01f, BiomeKeyRegistryME.SOUTHEAST_RHOVANION_FIELD));

        subBiomesMap.put(BiomeKeyRegistryME.RHUN, new SubBiome(48, 1.3f)
                .addSubBiomeData(-1.0f, -0.31f, BiomeKeyRegistryME.RHUN_FIELD)
                .addSubBiomeData(0.22f, 0.45f, BiomeKeyRegistryME.RHUN_FOREST)
                .addSubBiomeData(0.45f, 2.0f, BiomeKeyRegistryME.RHUN_HIDDEN_BLOSSOM));

        subBiomesMap.put(BiomeKeyRegistryME.UMBAR, new SubBiome()
                .addSubBiomeData(-1.0f, -0.36f, BiomeKeyRegistryME.UMBAR_WOODS));
        subBiomesMap.put(BiomeKeyRegistryME.HARAD, new SubBiome()
                .addSubBiomeData(-1.0f, -0.37f, BiomeKeyRegistryME.HARAD_WOODS)
                .addSubBiomeData(0.36f, 1.0f, BiomeKeyRegistryME.HARAD_WOODS));
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
