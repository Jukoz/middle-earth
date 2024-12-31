package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.world.biomes.MEBiomeKeys;
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

        subBiomesMap.put(MEBiomeKeys.ANORIEN, new SubBiome(144)
                .addSubBiomeData(-1.0f, -0.37f, MEBiomeKeys.GONDOR_FOREST)
                .addSubBiomeData(0.39f, 2.01f, MEBiomeKeys.GONDOR_HILL, true));
        subBiomesMap.put(MEBiomeKeys.PELENNOR_FIELDS, new SubBiome()
                .addSubBiomeData(-1.0f, -0.41f, MEBiomeKeys.GONDOR_FOREST)
                .addSubBiomeData(0.42f, 2.01f, MEBiomeKeys.GONDOR_FOREST));

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
                .addSubBiomeData(0.36f, 2.01f, MEBiomeKeys.ROHAN_HILLS, true));

        subBiomesMap.put(MEBiomeKeys.LOTHLORIEN, new SubBiome()
                .addSubBiomeData(-1.0f, -0.42f, MEBiomeKeys.LOTHLORIEN_BLOSSOM)
                .addSubBiomeData(-0.09f, 0.07f, MEBiomeKeys.LOTHLORIEN_GLADE));

        subBiomesMap.put(MEBiomeKeys.MIRKWOOD, new SubBiome()
                .addSubBiomeData(-1.0f, -0.33f, MEBiomeKeys.WEBBED_WOODS));
        subBiomesMap.put(MEBiomeKeys.DARK_MIRKWOOD, new SubBiome()
                .addSubBiomeData(-1.0f, -0.3f, MEBiomeKeys.WEBBED_DARK_WOODS));

        subBiomesMap.put(MEBiomeKeys.GUNDABAD_PLAINS, new SubBiome(96)
                .addSubBiomeData(-1.0f, -0.34f, MEBiomeKeys.NORTHERN_RHOVANION_FOREST)
                .addSubBiomeData(0.33f, 2.01f, MEBiomeKeys.NORTHERN_RHOVANION_HILLS, true));
        subBiomesMap.put(MEBiomeKeys.GREY_PLAINS, new SubBiome(96)
                .addSubBiomeData(-1.0f, -0.32f, MEBiomeKeys.NORTHERN_RHOVANION_FOREST)
                .addSubBiomeData(0.33f, 2.01f, MEBiomeKeys.NORTHERN_RHOVANION_HILLS, true));
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
                .addSubBiomeData(0.33f, 2.01f, MEBiomeKeys.SOUTHEAST_RHOVANION_FIELDS));

        subBiomesMap.put(MEBiomeKeys.UMBAR, new SubBiome()
                .addSubBiomeData(-1.0f, -0.36f, MEBiomeKeys.UMBAR_WOODS));
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
