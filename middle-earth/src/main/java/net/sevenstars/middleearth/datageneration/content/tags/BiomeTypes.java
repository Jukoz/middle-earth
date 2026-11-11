package net.sevenstars.middleearth.datageneration.content.tags;

import static net.sevenstars.middleearth.world.biomes.BiomeKeyRegistryME.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// Only convention (c:) tags. Vanilla structures such as pillager outposts and shipwrecks pick their biomes
// from the vanilla is_* tags, so adding our biomes to those would spawn them in Middle-earth.
public class BiomeTypes {
    private static final Map<RegistryKey<Biome>, EnumSet<BiomeType>> BIOMES = new HashMap<>();

    static {
        add(BiomeType.PLAINS,
                ANDUIN_VALES, ANORIEN, BELERIAND_ISLAND, BELFALAS, BLACKROOT_VALE, CORSAIR_COASTS, DALE, DALE_CITY,
                DALE_MEADOW, DALE_RIVERSIDE, DARK_ANDUIN_VALES, DORWINION, DORWINION_LAVENDER_FIELD, DRUWAITH_IAUR,
                EAST_BIGHT, EASTERN_NURN, EASTERN_RHOVANION, ENEDWAITH, ENEDWAITH_FIELD, ENEDWAITH_WHEAT_FIELD,
                EREGION, EREGION_GLADE, GONDOR, GREY_PLAINS, GUNDABAD_PLAINS, HARONDOR, HIGH_MOOR, IRON_HILLS_PLAINS,
                ISENGARD, ITHILIEN_GLADE, ITHILIEN_WASTES_GLADE, LAMEDON, LEBENNIN, LINDON, LINDON_MEADOW, LOSSARNACH,
                LOSSARNACH_VALLEY, MINHIRIATH, MINHIRIATH_WHEAT_FIELD, NAN_CURUNIR, NORTHERN_DUNLAND_GLADE, NURN,
                NURN_EDGE, OLD_ANGMAR, OLD_ARTHEDAIN, OLD_ARTHEDAIN_MEADOW, OLD_CARDOLAN, OLD_RHUDAUR, OSGILIATH,
                PELENNOR_FIELDS, PELENNOR_WHEAT_FIELD, RHUN, RHUN_FIELD, ROHAN, ROHAN_FIELD, SHIRE, SHIRE_EDGE,
                SOUTHEAST_RHOVANION, SOUTHEAST_RHOVANION_FIELD, THE_ANGLE, THE_WOLD, THE_WOLD_WHEAT_FIELD, TOLFALAS,
                UMBAR, WOODLAND_GLADE);

        add(BiomeType.SNOWY_PLAINS,
                FORODWAITH, NORTHERN_WASTELANDS, SOUTHERN_FOROCHEL);

        add(BiomeType.FOREST,
                ANDUIN_VALES_FOREST, AUTUMN_WOODLAND, BELFALAS_FOREST, BLACKROOT_FOREST, DALE_FOREST, DARK_MIRKWOOD,
                DARK_MIRKWOOD_EDGE, DUNLAND, EASTERN_RHOVANION_FOREST, EREGION_FOREST, FANGORN, GONDOR_FOREST,
                HIGH_MOOR_VALE, ITHILIEN, ITHILIEN_WASTES, LINDON_FOREST, LINDON_HIDDEN_BLOSSOM, LORIEN_EDGE,
                LOSSARNACH_CHERRY_BLOSSOM, LOSSARNACH_VALLEY_GREEN, LOSSARNACH_VALLEY_ORANGE, LOSSARNACH_VALLEY_RED,
                LOSSARNACH_VALLEY_YELLOW, LOTHLORIEN, LOTHLORIEN_BLOSSOM, LOTHLORIEN_GLADE, MIRKWOOD, MIRKWOOD_EDGE,
                MIRKWOOD_FOOTHILLS, MIRKWOOD_MOUNTAINS_BASE, MORGUL_FOREST, NEN_HITHOEL_FOREST, NURN_EDGE_WOODS,
                NURN_FOREST, OLD_ARTHEDAIN_FOREST, OLD_CARDOLAN_FOREST, RHUN_FOREST, RHUN_HIDDEN_BLOSSOM, ROHAN_FOREST,
                SARN_GEBIR_WILDLANDS, SHIRE_FOREST, SHIRE_WOODS, THE_OLD_FOREST, TROLLSHAWS, UMBAR_WOODS,
                WEBBED_DARK_WOODS, WEBBED_WOODS, WOODLAND_FOOTHILLS, WOODLAND_HILLS, WOODLAND_REALM);

        add(BiomeType.TAIGA,
                BLUE_MOUNTAINS_WOODS, DUNLAND_FOOTHILLS, DUNLAND_HILLS, GREY_ASHEN_WOODS, GREY_FOREST, GUNDABAD_WOODS,
                HILLS_OF_EVENDIM, LONELY_MOUNTAIN_TAIGA, NORTHERN_RHOVANION_FOREST, OLD_ANGMAR_FOREST,
                OLD_CARDOLAN_HILL, OLD_RHUDAUR_FOREST, OLD_RHUDAUR_HILL);

        add(BiomeType.HILL,
                ANORIEN_FOOTHILLS, BARROW_DOWNS, BELFALAS_HILLS, BLUE_MOUNTAINS_FOOTHILLS, DOL_GULDUR_HILL,
                DORWINION_HILLS, DUNLAND_FOOTHILLS, DUNLAND_HILLS, EMYN_MUIL, EMYN_MUIL_CLIFFS, EMYN_MUIL_PEAKS,
                FANGORN_FOOTHILLS, GONDOR_HILL, HIGH_MOOR_HILLS, HILLS_OF_EVENDIM, IRON_FOOTHILLS, ISENGARD_HILL,
                LAMEDON_HILLS, LEBENNIN_HILLS, LONELY_MOUNTAIN_FOOTHILLS, MIRKWOOD_FOOTHILLS, MORDOR_HILL, NORTH_DOWNS,
                NORTHERN_RHOVANION_HILLS, NURN_HILL, OLD_ANGMAR_COLD_HILL, OLD_ANGMAR_FROZEN_HILL,
                OLD_ARTHEDAIN_FOOTHILL, OLD_CARDOLAN_HILL, OLD_RHUDAUR_HILL, SHIRE_HILLS, THE_WHITE_DOWNS,
                WOODLAND_FOOTHILLS, WOODLAND_HILLS);

        add(BiomeType.MOUNTAIN,
                BLUE_MOUNTAINS, BLUE_MOUNTAINS_HIGH_LANDS, CARADHRAS, CELEBDIL, EPHEL_DUATH, ERED_LITHUI, FANUIDHOL,
                GREY_MOUNTAINS, IRON_HILLS, LONELY_MOUNTAIN, MIRKWOOD_MOUNTAINS, MISTY_MOUNTAINS, MOUNT_DOOM,
                MOUNT_DOOM_PIT, MOUNT_GUNDABAD, WHITE_MOUNTAINS);

        add(BiomeType.MOUNTAIN_SLOPE,
                BLUE_MOUNTAINS_BASE, CARADHRAS_BASE, CELEBDIL_BASE, EPHEL_DUATH_BASE, ERED_LITHUI_BASE, FANUIDHOL_BASE,
                GREY_MOUNTAINS_BASE, IRON_HILLS_BASE, LONELY_MOUNTAIN_BASE, MIRKWOOD_MOUNTAINS_BASE,
                MISTY_MOUNTAINS_BASE, MOUNT_GUNDABAD_BASE, WHITE_MOUNTAINS_BASE);

        add(BiomeType.MOUNTAIN_PEAK,
                BLUE_MOUNTAINS_PEAKS, CARADHRAS_PEAKS, CELEBDIL_PEAKS, EPHEL_DUATH_PEAKS, ERED_LITHUI_PEAKS,
                FANUIDHOL_PEAKS, GREY_MOUNTAINS_PEAKS, IRON_HILLS_PEAKS, LONELY_MOUNTAIN_PEAKS,
                MIRKWOOD_MOUNTAINS_PEAKS, MISTY_MOUNTAINS_PEAKS, MOUNT_GUNDABAD_PEAKS, WHITE_MOUNTAINS_PEAKS);

        add(BiomeType.SWAMP,
                DEAD_MARSHES, DEAD_MARSHES_WATER, ETHIR_ANDUIN, LONG_MARSHES, MANGROVE_POND, MIRKWOOD_MARSHES,
                MIRKWOOD_SWAMP, NINDALF, NORTHERN_MIRKWOOD_MARSHES, NORTHERN_MIRKWOOD_SWAMP);

        add(BiomeType.RIVER,
                ANORIEN_RIVERSIDE, EMYN_MUIL_POND, ETHIR_ANDUIN_RIVER_DELTA, FROZEN_POND, GREAT_RIVER, LONG_LAKE,
                MIRKWOOD_RIVER, MORGUL_RIVER, NEN_HITHOEL, NEN_HITHOEL_RAPIDS, NURN_RIVER, POND, RIVER, RIVER_RUNNING,
                WASTE_POND);

        add(BiomeType.OCEAN,
                FROZEN_OCEAN, NURN_SEA, OCEAN, OCEAN_COAST, SEA_OF_RHUN);

        add(BiomeType.BEACH,
                BELFALAS_BEACH, LEBENNIN_SHORES, LINDON_SHORES, LONG_LAKE_SHORES, NEN_HITHOEL_SHORES,
                SARN_GEBIR_SHORES);

        add(BiomeType.STONY_SHORES,
                LINDON_SHORES_CLIFFS);

        add(BiomeType.DESERT,
                HARAD_DESERT, OASIS);

        add(BiomeType.SAVANNA,
                HARAD, HARAD_WOODS);

        add(BiomeType.WASTELAND,
                BROWN_LANDS, DAGORLAD, DESOLATED_LANDS, GORGOROTH, GORGOROTH_ASHEN_WOODS, GORGOROTH_DELTA, MORDOR,
                MORDOR_ASHEN_FOREST, MORDOR_HILL, MORDOR_WASTES, TOROGWAITH, UDUN, WITHERED_HEATH);

        add(BiomeType.SNOWY,
                BLUE_MOUNTAINS_PEAKS, CARADHRAS, CARADHRAS_PEAKS, CELEBDIL, CELEBDIL_PEAKS, FANUIDHOL, FANUIDHOL_PEAKS,
                FORODWAITH, GREY_MOUNTAINS_PEAKS, LONELY_MOUNTAIN_PEAKS, MIRKWOOD_MOUNTAINS_PEAKS, MISTY_MOUNTAINS,
                MISTY_MOUNTAINS_PEAKS, MOUNT_GUNDABAD_PEAKS, NORTHERN_WASTELANDS, OLD_ANGMAR_FROZEN_HILL,
                SOUTHERN_FOROCHEL, WHITE_MOUNTAINS_PEAKS);

        add(BiomeType.AQUATIC_ICY,
                FROZEN_OCEAN, FROZEN_POND);

        add(BiomeType.CAVE,
                BASALT_CAVE, BASIC_CAVE, DOLOMITE_CAVE, DRIPSTONE_CAVE, DRY_CAVE, FUNGUS_CAVE, GALONN_CAVE,
                GILDED_CAVE, ICE_CAVE, IZHERABAN_CAVE, LIMESTONE_CAVE, LUSH_CAVE, MAGMA_CAVE, MITHRIL_CAVE,
                MOUNTAIN_CAVE, MUD_CAVE);

        none(
                DOL_GULDUR, MORGUL_VALE);
    }

    public static Set<BiomeType> of(RegistryKey<Biome> biome) {
        EnumSet<BiomeType> types = BIOMES.get(biome);
        if (types == null) throw new IllegalStateException("No biome types declared for biome " + biome.getValue());
        return types;
    }

    public static Set<RegistryKey<Biome>> declaredBiomes() {
        return BIOMES.keySet();
    }

    @SafeVarargs
    private static void add(BiomeType type, RegistryKey<Biome>... biomes) {
        for (RegistryKey<Biome> biome : biomes) {
            EnumSet<BiomeType> types = BIOMES.get(biome);
            if (types == null) {
                types = EnumSet.noneOf(BiomeType.class);
                BIOMES.put(biome, types);
            } else if (types.isEmpty()) {
                throw new IllegalStateException("Biome declared as none and " + type + ": " + biome.getValue());
            }
            if (!types.add(type)) throw new IllegalStateException("Biome declared twice as " + type + ": " + biome.getValue());
        }
    }

    @SafeVarargs
    private static void none(RegistryKey<Biome>... biomes) {
        for (RegistryKey<Biome> biome : biomes) {
            if (BIOMES.put(biome, EnumSet.noneOf(BiomeType.class)) != null) throw new IllegalStateException("Biome declared twice: " + biome.getValue());
        }
    }
}
