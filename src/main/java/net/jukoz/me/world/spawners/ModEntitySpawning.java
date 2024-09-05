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
                new EntitySpawningSettings(ModEntities.MISTY_GOBLIN_SNAGA, 1, 3, 45, true),
                new EntitySpawningSettings(ModEntities.MISTY_GOBLIN_WARRIOR, 1, 4, 35, true),
                new EntitySpawningSettings(ModEntities.MISTY_HOBGOBLIN_SOLDIER, 1, 3, 16, true),
                new EntitySpawningSettings(ModEntities.MISTY_HOBGOBLIN_VETERAN, 1, 2, 4, true)
        );
        List<EntitySpawningSettings> goblinsSpawnSettings = List.of(
                new EntitySpawningSettings(ModEntities.MISTY_GOBLIN_SNAGA, 1, 3, 38, true),
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
                new EntitySpawningSettings(ModEntities.MORDOR_ORC_SNAGA, 1, 3, 40, true),
                new EntitySpawningSettings(ModEntities.MORDOR_ORC_SOLDIER, 1, 4, 50, true),
                new EntitySpawningSettings(ModEntities.MORDOR_BLACK_URUK_SOLDIER, 1, 3, 10, true)
        );
        List<EntitySpawningSettings> mordorSpawnSettings = List.of(
                new EntitySpawningSettings(ModEntities.MORDOR_ORC_SNAGA, 1, 3, 30),
                new EntitySpawningSettings(ModEntities.MORDOR_ORC_SOLDIER, 1, 4, 40),
                new EntitySpawningSettings(ModEntities.MORDOR_BLACK_URUK_SOLDIER, 1, 3, 20),
                new EntitySpawningSettings(ModEntities.MORDOR_BLACK_URUK_VETERAN, 1, 2, 8),
                new EntitySpawningSettings(ModEntities.MORDOR_BLACK_URUK_LEADER, 1, 1, 2)
        );
        List<EntitySpawningSettings> caveTrolls = List.of(
                new EntitySpawningSettings(ModEntities.CAVE_TROLL, 1, 1, 12, true)
        );
        List<EntitySpawningSettings> mirkwoodSpiders = List.of(
                new EntitySpawningSettings(ModEntities.MIRKWOOD_SPIDER, 2, 5, 100, true)
        );

        List<EntitySpawningSettings> stoneTroll  = List.of(new EntitySpawningSettings(ModEntities.STONE_TROLL, 0, 1, 20, true));
        List<EntitySpawningSettings> goblinsAndBandits = Stream.concat(wildGoblinsSpawnSettings.stream(), banditSpawnSettings.stream()).toList();
        List<EntitySpawningSettings> goblinsAndStoneTroll = Stream.concat(wildGoblinsSpawnSettings.stream(), stoneTroll.stream()).toList();
        List<EntitySpawningSettings> goblinsAndLongbeards = Stream.concat(wildGoblinsSpawnSettings.stream(), wildLongBeardsSpawnSettings.stream()).toList();
        List<EntitySpawningSettings> goblinsAndCaveTrolls = Stream.concat(goblinsSpawnSettings.stream(), caveTrolls.stream()).toList();
        List<EntitySpawningSettings> gondorAndMordor = Stream.concat(gondorSpawnSettings.stream(), wildMordorSpawnSettings.stream()).toList();
        List<EntitySpawningSettings> gondorGarrisonAndMordor = Stream.concat(garrisonGondorSpawnSettings.stream(), wildMordorSpawnSettings.stream()).toList();
        List<EntitySpawningSettings> mordorAndSpiders = Stream.concat(wildMordorSpawnSettings.stream(), mirkwoodSpiders.stream()).toList();
        List<EntitySpawningSettings> wildMordorAndCaveTrolls = Stream.concat(wildMordorSpawnSettings.stream(), caveTrolls.stream()).toList();

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
        spawns.put(MEBiomeKeys.OLD_RHUDAUR, goblinsAndStoneTroll);
        spawns.put(MEBiomeKeys.OLD_CARDOLAN, banditSpawnSettings);
        spawns.put(MEBiomeKeys.OLD_CARDOLAN_FOREST, banditSpawnSettings);
        spawns.put(MEBiomeKeys.OLD_CARDOLAN_HILL, banditSpawnSettings);
        spawns.put(MEBiomeKeys.EREGION, goblinsAndBandits);
        spawns.put(MEBiomeKeys.ENEDWAITH, goblinsAndBandits);
        spawns.put(MEBiomeKeys.MISTY_MOUNTAINS_BASE, goblinsAndCaveTrolls);
        spawns.put(MEBiomeKeys.MISTY_MOUNTAINS, goblinsSpawnSettings);
        spawns.put(MEBiomeKeys.GREY_MOUNTAINS, wildGoblinsSpawnSettings);
        spawns.put(MEBiomeKeys.GREY_PLAINS, goblinsAndLongbeards);

        spawns.put(MEBiomeKeys.LOTHLORIEN, List.of(
                new EntitySpawningSettings(ModEntities.LORIEN_MILITIA, 1, 3, 24),
                new EntitySpawningSettings(ModEntities.LORIEN_SOLDIER, 1, 4, 45),
                new EntitySpawningSettings(ModEntities.LORIEN_KNIGHT, 1, 3, 20),
                new EntitySpawningSettings(ModEntities.LORIEN_VETERAN, 1, 2, 8),
                new EntitySpawningSettings(ModEntities.LORIEN_LEADER, 1, 1, 3)
        ));
        spawns.put(MEBiomeKeys.LORIEN_EDGE, List.of(
                new EntitySpawningSettings(ModEntities.LORIEN_MILITIA, 1, 3, 3),
                new EntitySpawningSettings(ModEntities.LORIEN_SOLDIER, 1, 4, 4),
                new EntitySpawningSettings(ModEntities.LORIEN_KNIGHT, 1, 3, 2)
        ));

        spawns.put(MEBiomeKeys.LONELY_MOUNTAIN_BASE, longBeardsSpawnSettings);
        spawns.put(MEBiomeKeys.LONELY_MOUNTAIN, longBeardsSpawnSettings);
        spawns.put(MEBiomeKeys.LONELY_MOUNTAIN_TAIGA, goblinsAndLongbeards);
        spawns.put(MEBiomeKeys.IRON_HILLS, longBeardsSpawnSettings);
        spawns.put(MEBiomeKeys.IRON_HILLS_BASE, longBeardsSpawnSettings);
        spawns.put(MEBiomeKeys.IRON_FOOTHILLS, longBeardsSpawnSettings);
        spawns.put(MEBiomeKeys.IRON_HILLS_PLAINS, goblinsAndLongbeards);
        spawns.put(MEBiomeKeys.DALE_RIVERSIDE, goblinsAndLongbeards);

        spawns.put(MEBiomeKeys.MIRKWOOD, List.of(new EntitySpawningSettings(ModEntities.MIRKWOOD_SPIDER, 1, 5)));
        spawns.put(MEBiomeKeys.DARK_MIRKWOOD, mordorAndSpiders);
        spawns.put(MEBiomeKeys.DOL_GULDUR, mordorSpawnSettings);

        spawns.put(MEBiomeKeys.ROHAN, rohanSpawnSettings);

        spawns.put(MEBiomeKeys.ANORIEN, gondorAndMordor);
        spawns.put(MEBiomeKeys.ANORIEN_RIVERSIDE, gondorAndMordor);
        spawns.put(MEBiomeKeys.LEBENNIN, ruralGondorSpawnSettings);
        spawns.put(MEBiomeKeys.LAMEDON, ruralGondorSpawnSettings);
        spawns.put(MEBiomeKeys.GONDOR, ruralGondorSpawnSettings);
        spawns.put(MEBiomeKeys.BELFALAS, gondorSpawnSettings);

        spawns.put(MEBiomeKeys.ITHILIEN, gondorGarrisonAndMordor);
        spawns.put(MEBiomeKeys.ITHILIEN_WASTES, wildMordorSpawnSettings);
        spawns.put(MEBiomeKeys.MORDOR_WASTES, wildMordorSpawnSettings);

        spawns.put(MEBiomeKeys.MORDOR, mordorSpawnSettings);
        spawns.put(MEBiomeKeys.NURN, wildMordorSpawnSettings);
        spawns.put(MEBiomeKeys.MORDOR_MOUNTAINS_FOOTHILLS, wildMordorAndCaveTrolls);
        spawns.put(MEBiomeKeys.MORDOR_MOUNTAINS, wildMordorSpawnSettings);
    }

    public static List<EntitySpawningSettings> getSpawnsAt(RegistryKey<Biome> biomeRegistryKey) {
        if(spawns.containsKey(biomeRegistryKey)) return spawns.get(biomeRegistryKey);
        return null;
    }
}
