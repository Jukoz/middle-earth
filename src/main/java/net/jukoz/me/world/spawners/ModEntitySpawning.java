package net.jukoz.me.world.spawners;

import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class ModEntitySpawning {
    private static HashMap<RegistryKey<Biome>, List<EntitySpawningSettings>> spawns = new HashMap<>();

    public static void addSpawns() {
        List<EntitySpawningSettings> banditSpawnSettings = List.of(
                new EntitySpawningSettings(ModEntities.BANDIT_MILITIA, 1, 4, 50, true),
                new EntitySpawningSettings(ModEntities.BANDIT_SOLDIER, 1, 3, 40, true),
                new EntitySpawningSettings(ModEntities.BANDIT_CHIEFTAIN, 1, 2, 10, true)
        );
        List<EntitySpawningSettings> wildGoblinsSpawnSettings = List.of(
                new EntitySpawningSettings(ModEntities.MISTY_GOBLIN_SNAGA, 1, 3, 25, true),
                new EntitySpawningSettings(ModEntities.WARG, 2, 5, 20, true),
                new EntitySpawningSettings(ModEntities.MISTY_GOBLIN_WARRIOR, 1, 4, 35, true),
                new EntitySpawningSettings(ModEntities.MISTY_HOBGOBLIN_SOLDIER, 1, 3, 16, true),
                new EntitySpawningSettings(ModEntities.MISTY_HOBGOBLIN_VETERAN, 1, 2, 4, true)
        );
        List<EntitySpawningSettings> goblinsSpawnSettings = List.of(
                new EntitySpawningSettings(ModEntities.MISTY_GOBLIN_SNAGA, 1, 3, 20, true),
                new EntitySpawningSettings(ModEntities.WARG, 2, 5, 18, true),
                new EntitySpawningSettings(ModEntities.MISTY_GOBLIN_WARRIOR, 1, 4, 32, true),
                new EntitySpawningSettings(ModEntities.MISTY_HOBGOBLIN_SOLDIER, 1, 3, 20, true),
                new EntitySpawningSettings(ModEntities.MISTY_HOBGOBLIN_VETERAN, 1, 2, 8, true),
                new EntitySpawningSettings(ModEntities.MISTY_HOBGOBLIN_LEADER, 1, 1, 2, true)
        );
        List<EntitySpawningSettings> wildLongBeardsSpawnSettings = List.of(
                new EntitySpawningSettings(ModEntities.LONGBEARD_MILITIA, 1, 3, 35),
                new EntitySpawningSettings(ModEntities.LONGBEARD_SOLDIER, 1, 4, 45),
                new EntitySpawningSettings(ModEntities.LONGBEARD_ELITE, 1, 3, 15),
                new EntitySpawningSettings(ModEntities.LONGBEARD_VETERAN, 1, 2, 5)
        );
        List<EntitySpawningSettings> longBeardsSpawnSettings = List.of(
                new EntitySpawningSettings(ModEntities.LONGBEARD_MILITIA, 1, 3, 25),
                new EntitySpawningSettings(ModEntities.LONGBEARD_SOLDIER, 1, 4, 45),
                new EntitySpawningSettings(ModEntities.LONGBEARD_ELITE, 1, 3, 20),
                new EntitySpawningSettings(ModEntities.LONGBEARD_VETERAN, 1, 2, 8),
                new EntitySpawningSettings(ModEntities.LONGBEARD_LEADER, 1, 1, 2)
        );
        List<EntitySpawningSettings> rohanSpawnSettings = List.of(
                new EntitySpawningSettings(ModEntities.ROHIRRIM_MILITIA, 1, 3, 25),
                new EntitySpawningSettings(ModEntities.ROHIRRIM_SOLDIER, 1, 4, 45),
                new EntitySpawningSettings(ModEntities.ROHIRRIM_KNIGHT, 1, 3, 20),
                new EntitySpawningSettings(ModEntities.ROHIRRIM_VETERAN, 1, 2, 8),
                new EntitySpawningSettings(ModEntities.ROHIRRIM_LEADER, 1, 1, 2)
        );
        List<EntitySpawningSettings> daleSpawnSettings = List.of(
                new EntitySpawningSettings(ModEntities.DALISH_MILITIA, 1, 3, 25),
                new EntitySpawningSettings(ModEntities.DALISH_SOLDIER, 1, 4, 45),
                new EntitySpawningSettings(ModEntities.DALISH_KNIGHT, 1, 3, 20),
                new EntitySpawningSettings(ModEntities.DALISH_VETERAN, 1, 2, 8),
                new EntitySpawningSettings(ModEntities.DALISH_LEADER, 1, 1, 2)
        );
        List<EntitySpawningSettings> lothlorienSpawnSettings = List.of(
                new EntitySpawningSettings(ModEntities.LORIEN_MILITIA, 1, 3, 24),
                new EntitySpawningSettings(ModEntities.LORIEN_SOLDIER, 1, 4, 45),
                new EntitySpawningSettings(ModEntities.LORIEN_KNIGHT, 1, 3, 20),
                new EntitySpawningSettings(ModEntities.LORIEN_VETERAN, 1, 2, 8),
                new EntitySpawningSettings(ModEntities.LORIEN_LEADER, 1, 1, 3)
        );
        List<EntitySpawningSettings> ruralGondorSpawnSettings = List.of(
                new EntitySpawningSettings(ModEntities.GONDORIAN_MILITIA, 1, 3, 60),
                new EntitySpawningSettings(ModEntities.GONDORIAN_SOLDIER, 1, 4, 40),
                new EntitySpawningSettings(ModEntities.GONDORIAN_KNIGHT, 1, 3, 10)
        );
        List<EntitySpawningSettings> gondorSpawnSettings = List.of(
                new EntitySpawningSettings(ModEntities.GONDORIAN_MILITIA, 1, 3, 25),
                new EntitySpawningSettings(ModEntities.GONDORIAN_SOLDIER, 1, 4, 45),
                new EntitySpawningSettings(ModEntities.GONDORIAN_KNIGHT, 1, 3, 20),
                new EntitySpawningSettings(ModEntities.GONDORIAN_VETERAN, 1, 2, 8),
                new EntitySpawningSettings(ModEntities.GONDORIAN_LEADER, 1, 1, 2)
        );
        List<EntitySpawningSettings> garrisonGondorSpawnSettings = List.of(
                new EntitySpawningSettings(ModEntities.GONDORIAN_MILITIA, 1, 3, 22),
                new EntitySpawningSettings(ModEntities.GONDORIAN_SOLDIER, 1, 4, 50),
                new EntitySpawningSettings(ModEntities.GONDORIAN_KNIGHT, 1, 3, 20),
                new EntitySpawningSettings(ModEntities.GONDORIAN_VETERAN, 1, 2, 8)
        );
        List<EntitySpawningSettings> wildMordorSpawnSettings = List.of(
                new EntitySpawningSettings(ModEntities.MORDOR_ORC_SNAGA, 1, 3, 25, true),
                new EntitySpawningSettings(ModEntities.WARG, 2, 5, 20, true),
                new EntitySpawningSettings(ModEntities.MORDOR_ORC_SOLDIER, 1, 4, 45, true),
                new EntitySpawningSettings(ModEntities.MORDOR_BLACK_URUK_SOLDIER, 1, 3, 10, true)
        );
        List<EntitySpawningSettings> mordorSpawnSettings = List.of(
                new EntitySpawningSettings(ModEntities.MORDOR_ORC_SNAGA, 1, 3, 16),
                new EntitySpawningSettings(ModEntities.WARG, 2, 5, 16),
                new EntitySpawningSettings(ModEntities.MORDOR_ORC_SOLDIER, 1, 4, 38),
                new EntitySpawningSettings(ModEntities.MORDOR_BLACK_URUK_SOLDIER, 1, 3, 20),
                new EntitySpawningSettings(ModEntities.MORDOR_BLACK_URUK_VETERAN, 1, 2, 8),
                new EntitySpawningSettings(ModEntities.MORDOR_BLACK_URUK_LEADER, 1, 1, 2)
        );
        List<EntitySpawningSettings> isengardSpawnSettings = List.of(
                new EntitySpawningSettings(ModEntities.ISENGARD_ORC_SNAGA, 1, 3, 16),
                new EntitySpawningSettings(ModEntities.WARG, 2, 5, 16),
                new EntitySpawningSettings(ModEntities.ISENGARD_ORC_WARRIOR, 1, 4, 38),
                new EntitySpawningSettings(ModEntities.ISENGARD_URUK_HAI_SOLDIER, 1, 3, 20),
                new EntitySpawningSettings(ModEntities.ISENGARD_URUK_HAI_VETERAN, 1, 2, 8),
                new EntitySpawningSettings(ModEntities.ISENGARD_URUK_HAI_LEADER, 1, 1, 2)
        );
        List<EntitySpawningSettings> mirkwoodSpiders = List.of(
                new EntitySpawningSettings(ModEntities.MIRKWOOD_SPIDER, 2, 5, 100, true)
        );

        List<EntitySpawningSettings> stoneTroll  = List.of(new EntitySpawningSettings(ModEntities.STONE_TROLL, 0, 1, 20, true));
        List<EntitySpawningSettings> goblinsAndBandits = Stream.concat(wildGoblinsSpawnSettings.stream(), banditSpawnSettings.stream()).toList();
        List<EntitySpawningSettings> goblinsAndStoneTroll = Stream.concat(wildGoblinsSpawnSettings.stream(), stoneTroll.stream()).toList();
        List<EntitySpawningSettings> goblinsAndLongbeards = Stream.concat(wildGoblinsSpawnSettings.stream(), wildLongBeardsSpawnSettings.stream()).toList();
        List<EntitySpawningSettings> gondorAndMordor = Stream.concat(gondorSpawnSettings.stream(), wildMordorSpawnSettings.stream()).toList();
        List<EntitySpawningSettings> gondorGarrisonAndMordor = Stream.concat(garrisonGondorSpawnSettings.stream(), wildMordorSpawnSettings.stream()).toList();
        List<EntitySpawningSettings> mordorAndSpiders = Stream.concat(wildMordorSpawnSettings.stream(), mirkwoodSpiders.stream()).toList();

        spawns.put(MEBiomeKeys.BARROW_DOWNS, List.of(new EntitySpawningSettings(ModEntities.BARROW_WIGHT, 0, 1)));

        spawns.put(MEBiomeKeys.TROLLSHAWS, List.of(
                new EntitySpawningSettings(ModEntities.STONE_TROLL, 1, 2, 1, true),
                new EntitySpawningSettings(ModEntities.MISTY_GOBLIN_SNAGA, 1, 3, 2, true) ,
                new EntitySpawningSettings(ModEntities.MISTY_GOBLIN_WARRIOR, 1, 2, 2, true)
        ));
        spawns.put(MEBiomeKeys.FORODWAITH, List.of(new EntitySpawningSettings(ModEntities.SNOW_TROLL, 0, 1)));

        spawns.put(MEBiomeKeys.SHIRE, List.of(
                new EntitySpawningSettings(ModEntities.HOBBIT_CIVILIAN, 1, 4, 7),
                new EntitySpawningSettings(ModEntities.HOBBIT_SHIRRIFF, 1, 1, 1)
        ));
        spawns.put(MEBiomeKeys.SHIRE_EDGE, List.of(
                new EntitySpawningSettings(ModEntities.HOBBIT_CIVILIAN, 1, 4, 6),
                new EntitySpawningSettings(ModEntities.HOBBIT_BOUNDER, 1, 4, 3),
                new EntitySpawningSettings(ModEntities.HOBBIT_SHIRRIFF, 1, 1, 1)
        ));

        spawns.put(MEBiomeKeys.ANDUIN_VALES, wildGoblinsSpawnSettings);
        spawns.put(MEBiomeKeys.ANDUIN_VALES_FOREST, wildGoblinsSpawnSettings);
        spawns.put(MEBiomeKeys.OLD_ANGMAR, wildGoblinsSpawnSettings);
        spawns.put(MEBiomeKeys.OLD_ANGMAR_FOREST, wildGoblinsSpawnSettings);
        spawns.put(MEBiomeKeys.OLD_ANGMAR_FROZEN_HILL, wildGoblinsSpawnSettings);
        spawns.put(MEBiomeKeys.OLD_RHUDAUR, goblinsSpawnSettings);
        spawns.put(MEBiomeKeys.OLD_RHUDAUR_FOREST, goblinsAndStoneTroll);
        spawns.put(MEBiomeKeys.OLD_RHUDAUR_HILL, goblinsAndStoneTroll);
        spawns.put(MEBiomeKeys.OLD_CARDOLAN, banditSpawnSettings);
        spawns.put(MEBiomeKeys.OLD_CARDOLAN_FOREST, banditSpawnSettings);
        spawns.put(MEBiomeKeys.OLD_CARDOLAN_HILL, banditSpawnSettings);
        spawns.put(MEBiomeKeys.EREGION, goblinsAndBandits);
        spawns.put(MEBiomeKeys.EREGION_FOREST, goblinsAndBandits);
        spawns.put(MEBiomeKeys.EREGION_GLADE, goblinsAndBandits);
        spawns.put(MEBiomeKeys.ENEDWAITH, goblinsAndBandits);
        spawns.put(MEBiomeKeys.MOUNT_GUNDABAD_BASE, goblinsSpawnSettings);
        spawns.put(MEBiomeKeys.MOUNT_GUNDABAD, goblinsSpawnSettings);
        spawns.put(MEBiomeKeys.GUNDABAD_PLAINS, goblinsSpawnSettings);
        spawns.put(MEBiomeKeys.MISTY_MOUNTAINS, goblinsSpawnSettings);
        spawns.put(MEBiomeKeys.GREY_MOUNTAINS, wildGoblinsSpawnSettings);
        spawns.put(MEBiomeKeys.GREY_MOUNTAINS_BASE, wildGoblinsSpawnSettings);
        spawns.put(MEBiomeKeys.GREY_PLAINS, goblinsAndLongbeards);
        spawns.put(MEBiomeKeys.GREY_ASHEN_WOODS, goblinsAndLongbeards);
        spawns.put(MEBiomeKeys.DESOLATED_LANDS, goblinsAndLongbeards);

        spawns.put(MEBiomeKeys.ISENGARD, isengardSpawnSettings);
        spawns.put(MEBiomeKeys.NAN_CURUNIR, isengardSpawnSettings);

        spawns.put(MEBiomeKeys.LOTHLORIEN, lothlorienSpawnSettings);
        spawns.put(MEBiomeKeys.LOTHLORIEN_GLADE, lothlorienSpawnSettings);
        spawns.put(MEBiomeKeys.LOTHLORIEN_BLOSSOM, lothlorienSpawnSettings);
        spawns.put(MEBiomeKeys.LORIEN_EDGE, List.of(
                new EntitySpawningSettings(ModEntities.LORIEN_MILITIA, 1, 3, 3),
                new EntitySpawningSettings(ModEntities.LORIEN_SOLDIER, 1, 4, 4),
                new EntitySpawningSettings(ModEntities.LORIEN_KNIGHT, 1, 3, 2)
        ));

        spawns.put(MEBiomeKeys.LONELY_MOUNTAIN_BASE, longBeardsSpawnSettings);
        spawns.put(MEBiomeKeys.LONELY_MOUNTAIN, longBeardsSpawnSettings);
        spawns.put(MEBiomeKeys.LONELY_MOUNTAIN_FOOTHILLS, longBeardsSpawnSettings);
        spawns.put(MEBiomeKeys.LONELY_MOUNTAIN_TAIGA, goblinsAndLongbeards);
        spawns.put(MEBiomeKeys.IRON_HILLS, longBeardsSpawnSettings);
        spawns.put(MEBiomeKeys.IRON_HILLS_BASE, longBeardsSpawnSettings);
        spawns.put(MEBiomeKeys.IRON_FOOTHILLS, longBeardsSpawnSettings);
        spawns.put(MEBiomeKeys.IRON_HILLS_PLAINS, goblinsAndLongbeards);
        spawns.put(MEBiomeKeys.DALE_RIVERSIDE, goblinsAndLongbeards);
        spawns.put(MEBiomeKeys.DALE_FOREST, banditSpawnSettings);

        spawns.put(MEBiomeKeys.MIRKWOOD, List.of(new EntitySpawningSettings(ModEntities.MIRKWOOD_SPIDER, 1, 3)));
        spawns.put(MEBiomeKeys.WEBBED_WOODS, List.of(new EntitySpawningSettings(ModEntities.MIRKWOOD_SPIDER, 3, 6)));
        spawns.put(MEBiomeKeys.DARK_MIRKWOOD, mordorAndSpiders);
        spawns.put(MEBiomeKeys.WEBBED_DARK_WOODS, List.of(new EntitySpawningSettings(ModEntities.MIRKWOOD_SPIDER, 3, 6)));
        spawns.put(MEBiomeKeys.DOL_GULDUR, mordorSpawnSettings);

        spawns.put(MEBiomeKeys.DALE, daleSpawnSettings);
        spawns.put(MEBiomeKeys.DALE_CITY, daleSpawnSettings);
        spawns.put(MEBiomeKeys.DALE_MEADOW, daleSpawnSettings);

        spawns.put(MEBiomeKeys.ROHAN, rohanSpawnSettings);
        spawns.put(MEBiomeKeys.ROHAN_FIELD, rohanSpawnSettings);

        spawns.put(MEBiomeKeys.ANORIEN, gondorAndMordor);
        spawns.put(MEBiomeKeys.ANORIEN_RIVERSIDE, gondorAndMordor);
        spawns.put(MEBiomeKeys.PELENNOR_FIELDS, gondorSpawnSettings);
        spawns.put(MEBiomeKeys.LOSSARNACH, ruralGondorSpawnSettings);
        spawns.put(MEBiomeKeys.LOSSARNACH_VALLEY, ruralGondorSpawnSettings);
        spawns.put(MEBiomeKeys.LEBENNIN, ruralGondorSpawnSettings);
        spawns.put(MEBiomeKeys.LAMEDON, ruralGondorSpawnSettings);
        spawns.put(MEBiomeKeys.GONDOR, ruralGondorSpawnSettings);
        spawns.put(MEBiomeKeys.GONDOR_HILL, ruralGondorSpawnSettings);
        spawns.put(MEBiomeKeys.BELFALAS, gondorSpawnSettings);
        spawns.put(MEBiomeKeys.BELFALAS_HILLS, ruralGondorSpawnSettings);
        spawns.put(MEBiomeKeys.BLACKROOT_VALE, gondorSpawnSettings);

        spawns.put(MEBiomeKeys.OSGILIATH, gondorAndMordor);
        spawns.put(MEBiomeKeys.ITHILIEN, gondorGarrisonAndMordor);
        spawns.put(MEBiomeKeys.ITHILIEN_GLADE, gondorGarrisonAndMordor);
        spawns.put(MEBiomeKeys.ITHILIEN_WASTES, wildMordorSpawnSettings);
        spawns.put(MEBiomeKeys.ITHILIEN_WASTES_GLADE, wildMordorSpawnSettings);
        spawns.put(MEBiomeKeys.MORDOR_WASTES, wildMordorSpawnSettings);

        spawns.put(MEBiomeKeys.DAGORLAD, wildMordorSpawnSettings);
        spawns.put(MEBiomeKeys.GORGOROTH, mordorSpawnSettings);
        spawns.put(MEBiomeKeys.GORGOROTH_ASHEN_WOODS, mordorSpawnSettings);
        spawns.put(MEBiomeKeys.MORDOR, mordorSpawnSettings);
        spawns.put(MEBiomeKeys.MORDOR_ASHEN_FOREST, mordorSpawnSettings);
        spawns.put(MEBiomeKeys.MORDOR_HILL, mordorSpawnSettings);
        spawns.put(MEBiomeKeys.MORGUL_VALE, mordorSpawnSettings);
        spawns.put(MEBiomeKeys.MORGUL_FOREST, mordorSpawnSettings);
        spawns.put(MEBiomeKeys.TOROGWAITH, mordorSpawnSettings);
        spawns.put(MEBiomeKeys.UDUN, mordorSpawnSettings);
        spawns.put(MEBiomeKeys.NURN, wildMordorSpawnSettings);
        spawns.put(MEBiomeKeys.NURN_FOREST, wildMordorSpawnSettings);
        spawns.put(MEBiomeKeys.NURN_HILL, wildMordorSpawnSettings);
        spawns.put(MEBiomeKeys.NURN_EDGE, wildMordorSpawnSettings);
        spawns.put(MEBiomeKeys.NURN_EDGE_WOODS, wildMordorSpawnSettings);
        spawns.put(MEBiomeKeys.ERED_LITHUI, wildMordorSpawnSettings);
        spawns.put(MEBiomeKeys.ERED_LITHUI_BASE, wildMordorSpawnSettings);
        spawns.put(MEBiomeKeys.ERED_LITHUI_PEAKS, wildMordorSpawnSettings);
        spawns.put(MEBiomeKeys.EPHEL_DUATH, wildMordorSpawnSettings);
        spawns.put(MEBiomeKeys.EPHEL_DUATH_BASE, wildMordorSpawnSettings);
        spawns.put(MEBiomeKeys.EPHEL_DUATH_PEAKS, wildMordorSpawnSettings);
    }

    public static List<EntitySpawningSettings> getSpawnsAt(RegistryKey<Biome> biomeRegistryKey) {
        if(spawns.containsKey(biomeRegistryKey)) return spawns.get(biomeRegistryKey);
        return null;
    }
}
