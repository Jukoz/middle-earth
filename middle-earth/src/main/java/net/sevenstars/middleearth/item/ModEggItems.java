package net.sevenstars.middleearth.item;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.models.SimpleSpawnEggItemModel;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.item.utils.ModItemGroups;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEggItems {

    public static final Item HOBBIT_CIVILIAN_SPAWN_EGG = registerItem("hobbit_civilian_spawn_egg",
            new SpawnEggItem(ModEntities.HOBBIT_CIVILIAN, 11131454, 13810794, new Item.Settings()));
    public static final Item HOBBIT_BOUNDER_SPAWN_EGG = registerItem("hobbit_bounder_spawn_egg",
            new SpawnEggItem(ModEntities.HOBBIT_BOUNDER, 11131454, 13810794, new Item.Settings()));
    public static final Item HOBBIT_SHIRRIFF_SPAWN_EGG = registerItem("hobbit_shirriff_spawn_egg",
            new SpawnEggItem(ModEntities.HOBBIT_SHIRRIFF, 11131454, 13810794, new Item.Settings()));
    
    public static final Item GONDORIAN_MILITIA_SPAWN_EGG = registerItem("gondorian_militia_spawn_egg",
            new SpawnEggItem(ModEntities.GONDORIAN_MILITIA, 1644825, 14869218, new Item.Settings()));
    public static final Item GONDORIAN_SOLDIER_SPAWN_EGG = registerItem("gondorian_soldier_spawn_egg",
            new SpawnEggItem(ModEntities.GONDORIAN_SOLDIER, 1644825, 14869218, new Item.Settings()));
    public static final Item GONDORIAN_KNIGHT_SPAWN_EGG = registerItem("gondorian_knight_spawn_egg",
            new SpawnEggItem(ModEntities.GONDORIAN_KNIGHT, 1644825, 14869218, new Item.Settings()));
    public static final Item GONDORIAN_VETERAN_SPAWN_EGG = registerItem("gondorian_veteran_spawn_egg",
            new SpawnEggItem(ModEntities.GONDORIAN_VETERAN, 1644825, 14869218, new Item.Settings()));
    public static final Item GONDORIAN_LEADER_SPAWN_EGG = registerItem("gondorian_leader_spawn_egg",
            new SpawnEggItem(ModEntities.GONDORIAN_LEADER, 1644825, 14869218, new Item.Settings()));

    public static final Item ROHIRRIM_MILITIA_SPAWN_EGG = registerItem("rohirrim_militia_spawn_egg",
            new SpawnEggItem(ModEntities.ROHIRRIM_MILITIA, 9132338, 32526, new Item.Settings()));
    public static final Item ROHIRRIM_SOLDIER_SPAWN_EGG = registerItem("rohirrim_soldier_spawn_egg",
            new SpawnEggItem(ModEntities.ROHIRRIM_SOLDIER, 9132338, 32526, new Item.Settings()));
    public static final Item ROHIRRIM_KNIGHT_SPAWN_EGG = registerItem("rohirrim_knight_spawn_egg",
            new SpawnEggItem(ModEntities.ROHIRRIM_KNIGHT, 9132338, 32526, new Item.Settings()));
    public static final Item ROHIRRIM_VETERAN_SPAWN_EGG = registerItem("rohirrim_veteran_spawn_egg",
            new SpawnEggItem(ModEntities.ROHIRRIM_VETERAN, 9132338, 32526, new Item.Settings()));
    public static final Item ROHIRRIM_LEADER_SPAWN_EGG = registerItem("rohirrim_leader_spawn_egg",
            new SpawnEggItem(ModEntities.ROHIRRIM_LEADER, 9132338, 32526, new Item.Settings()));

    public static final Item DALISH_MILITIA_SPAWN_EGG = registerItem("dalish_militia_spawn_egg",
            new SpawnEggItem(ModEntities.DALISH_MILITIA, 0x5c88d5, 0xd5ae2d, new Item.Settings()));
    public static final Item DALISH_SOLDIER_SPAWN_EGG = registerItem("dalish_soldier_spawn_egg",
            new SpawnEggItem(ModEntities.DALISH_SOLDIER, 0x5c88d5, 0xd5ae2d, new Item.Settings()));
    public static final Item DALISH_KNIGHT_SPAWN_EGG = registerItem("dalish_knight_spawn_egg",
            new SpawnEggItem(ModEntities.DALISH_KNIGHT, 0x5c88d5, 0xd5ae2d, new Item.Settings()));
    public static final Item DALISH_VETERAN_SPAWN_EGG = registerItem("dalish_veteran_spawn_egg",
            new SpawnEggItem(ModEntities.DALISH_VETERAN, 0x5c88d5, 0xd5ae2d, new Item.Settings()));
    public static final Item DALISH_LEADER_SPAWN_EGG = registerItem("dalish_leader_spawn_egg",
            new SpawnEggItem(ModEntities.DALISH_LEADER, 0x5c88d5, 0xd5ae2d, new Item.Settings()));

    public static final Item LONGBEARD_MILITIA_SPAWN_EGG = registerItem("longbeard_militia_spawn_egg",
            new SpawnEggItem(ModEntities.LONGBEARD_MILITIA, 4541263, 8090735, new Item.Settings()));
    public static final Item LONGBEARD_SOLDIER_SPAWN_EGG = registerItem("longbeard_soldier_spawn_egg",
            new SpawnEggItem(ModEntities.LONGBEARD_SOLDIER, 4541263, 8090735, new Item.Settings()));
    public static final Item LONGBEARD_ELITE_SPAWN_EGG = registerItem("longbeard_elite_spawn_egg",
            new SpawnEggItem(ModEntities.LONGBEARD_ELITE, 4541263, 8090735, new Item.Settings()));
    public static final Item LONGBEARD_VETERAN_SPAWN_EGG = registerItem("longbeard_veteran_spawn_egg",
            new SpawnEggItem(ModEntities.LONGBEARD_VETERAN, 4541263, 8090735, new Item.Settings()));
    public static final Item LONGBEARD_LEADER_SPAWN_EGG = registerItem("longbeard_leader_spawn_egg",
            new SpawnEggItem(ModEntities.LONGBEARD_LEADER, 4541263, 8090735, new Item.Settings()));

    public static final Item LORIEN_MILITIA_SPAWN_EGG = registerItem("lorien_militia_spawn_egg",
            new SpawnEggItem(ModEntities.LORIEN_MILITIA, 15388227, 8950161, new Item.Settings()));
    public static final Item LORIEN_SOLDIER_SPAWN_EGG = registerItem("lorien_soldier_spawn_egg",
            new SpawnEggItem(ModEntities.LORIEN_SOLDIER, 15388227, 8950161, new Item.Settings()));
    public static final Item LORIEN_KNIGHT_SPAWN_EGG = registerItem("lorien_knight_spawn_egg",
            new SpawnEggItem(ModEntities.LORIEN_KNIGHT, 15388227, 8950161, new Item.Settings()));
    public static final Item LORIEN_VETERAN_SPAWN_EGG = registerItem("lorien_veteran_spawn_egg",
            new SpawnEggItem(ModEntities.LORIEN_VETERAN, 15388227, 8950161, new Item.Settings()));
    public static final Item LORIEN_LEADER_SPAWN_EGG = registerItem("lorien_leader_spawn_egg",
            new SpawnEggItem(ModEntities.LORIEN_LEADER, 15388227, 8950161, new Item.Settings()));

    public static final Item MORDOR_ORC_SNAGA_SPAWN_EGG = registerItem("mordor_orc_snaga_spawn_egg",
            new SpawnEggItem(ModEntities.MORDOR_ORC_SNAGA, 4209210, 7682104, new Item.Settings()));
    public static final Item MORDOR_ORC_SOLDIER_SPAWN_EGG = registerItem("mordor_orc_soldier_spawn_egg",
            new SpawnEggItem(ModEntities.MORDOR_ORC_SOLDIER, 4209210, 7682104, new Item.Settings()));
    public static final Item MORDOR_BLACK_URUK_SOLDIER_SPAWN_EGG = registerItem("mordor_black_uruk_soldier_spawn_egg",
            new SpawnEggItem(ModEntities.MORDOR_BLACK_URUK_SOLDIER, 4209210, 7682104, new Item.Settings()));
    public static final Item MORDOR_BLACK_URUK_veteran_SPAWN_EGG = registerItem("mordor_black_uruk_veteran_spawn_egg",
            new SpawnEggItem(ModEntities.MORDOR_BLACK_URUK_VETERAN, 4209210, 7682104, new Item.Settings()));
    public static final Item MORDOR_BLACK_URUK_leader_SPAWN_EGG = registerItem("mordor_black_uruk_leader_spawn_egg",
            new SpawnEggItem(ModEntities.MORDOR_BLACK_URUK_LEADER, 4209210, 7682104, new Item.Settings()));

    public static final Item MISTY_GOBLIN_SNAGA_SPAWN_EGG = registerItem("misty_goblin_snaga_spawn_egg",
            new SpawnEggItem(ModEntities.MISTY_GOBLIN_SNAGA, 0x835449, 0x3f1817, new Item.Settings()));
    public static final Item MISTY_GOBLIN_WARRIOR_SPAWN_EGG = registerItem("misty_goblin_warrior_spawn_egg",
            new SpawnEggItem(ModEntities.MISTY_GOBLIN_WARRIOR, 0x835449, 0x3f1817, new Item.Settings()));
    public static final Item MISTY_HOBGOBLIN_SOLDIER_SPAWN_EGG = registerItem("misty_hobgoblin_soldier_spawn_egg",
            new SpawnEggItem(ModEntities.MISTY_HOBGOBLIN_SOLDIER, 0x835449, 0x3f1817, new Item.Settings()));
    public static final Item MISTY_HOBGOBLIN_veteran_SPAWN_EGG = registerItem("misty_hobgoblin_veteran_spawn_egg",
            new SpawnEggItem(ModEntities.MISTY_HOBGOBLIN_VETERAN, 0x835449, 0x3f1817, new Item.Settings()));
    public static final Item MISTY_HOBGOBLIN_leader_SPAWN_EGG = registerItem("misty_hobgoblin_leader_spawn_egg",
            new SpawnEggItem(ModEntities.MISTY_HOBGOBLIN_LEADER, 0x835449, 0x3f1817, new Item.Settings()));

    public static final Item ISENGARD_ORC_SNAGA_SPAWN_EGG = registerItem("isengard_orc_snaga_spawn_egg",
            new SpawnEggItem(ModEntities.ISENGARD_ORC_SNAGA, 0x751200 ,0xc7b8a9, new Item.Settings()));
    public static final Item ISENGARD_ORC_WARRIOR_SPAWN_EGG = registerItem("isengard_orc_warrior_spawn_egg",
            new SpawnEggItem(ModEntities.ISENGARD_ORC_WARRIOR,  0x751200 ,0xc7b8a9, new Item.Settings()));
    public static final Item ISENGARD_URUK_HAI_SOLDIER_SPAWN_EGG = registerItem("isengard_uruk_hai_soldier_spawn_egg",
            new SpawnEggItem(ModEntities.ISENGARD_URUK_HAI_SOLDIER,  0x751200 ,0xc7b8a9, new Item.Settings()));
    public static final Item ISENGARD_URUK_HAI_veteran_SPAWN_EGG = registerItem("isengard_uruk_hai_veteran_spawn_egg",
            new SpawnEggItem(ModEntities.ISENGARD_URUK_HAI_VETERAN,  0x751200 ,0xc7b8a9, new Item.Settings()));
    public static final Item ISENGARD_URUK_HAI_leader_SPAWN_EGG = registerItem("isengard_uruk_hai_leader_spawn_egg",
            new SpawnEggItem(ModEntities.ISENGARD_URUK_HAI_LEADER,  0x751200 ,0xc7b8a9, new Item.Settings()));

    public static final Item BANDIT_MILITIA_SPAWN_EGG = registerItem("bandit_militia_spawn_egg",
            new SpawnEggItem(ModEntities.BANDIT_MILITIA, 4541263, 9132338, new Item.Settings()));
    public static final Item BANDIT_SOLDIER_SPAWN_EGG = registerItem("bandit_soldier_spawn_egg",
            new SpawnEggItem(ModEntities.BANDIT_SOLDIER, 4541263, 9132338, new Item.Settings()));
    public static final Item BANDIT_CHIEFTAIN_SPAWN_EGG = registerItem("bandit_chieftain_spawn_egg",
            new SpawnEggItem(ModEntities.BANDIT_CHIEFTAIN, 4541263, 9132338, new Item.Settings()));

    public static final Item WILD_GOBLIN_SPAWN_EGG = registerItem("wild_goblin_spawn_egg",
            new SpawnEggItem(ModEntities.WILD_GOBLIN, 4541263, 9132338, new Item.Settings()));

    public static final Item BARROW_WIGHT_SPAWN_EGG = registerItem("barrow_wight_spawn_egg",
            new SpawnEggItem(ModEntities.BARROW_WIGHT, 1852734, 2456136, new Item.Settings()));

    public static final Item BROADHOOF_GOAT_SPAWN_EGG = registerItem("broadhoof_goat_spawn_egg",
            new SpawnEggItem(ModEntities.BROADHOOF_GOAT, 7367010, 1381137, new Item.Settings()));

    public static final Item WARG_SPAWN_EGG = registerItem("warg_spawn_egg",
            new SpawnEggItem(ModEntities.WARG, 3812644, 14931405, new Item.Settings()));

    public static final Item STONE_TROLL_SPAWN_EGG = registerItem("stone_troll_spawn_egg",
            new SpawnEggItem(ModEntities.STONE_TROLL, 10517857, 5257516, new Item.Settings()));

    public static final Item SNOW_TROLL_SPAWN_EGG = registerItem("snow_troll_spawn_egg",
            new SpawnEggItem(ModEntities.SNOW_TROLL, 12770027, 9739424, new Item.Settings()));

    public static final Item MIRKWOOD_SPIDER_SPAWN_EGG = registerItem("mirkwood_spider_spawn_egg",
            new SpawnEggItem(ModEntities.MIRKWOOD_SPIDER, 657930, 11669520, new Item.Settings()));

    // Animals
    public static final Item DEER_SPAWN_EGG = registerItem("deer_spawn_egg",
            new SpawnEggItem(ModEntities.DEER, 9132338, 14403249, new Item.Settings()));
    public static final Item SWAN_SPAWN_EGG = registerItem("swan_spawn_egg",
            new SpawnEggItem(ModEntities.SWAN, 14869218,11842231, new Item.Settings()));
    public static final Item PHEASANT_SPAWN_EGG = registerItem("pheasant_spawn_egg",
            new SpawnEggItem(ModEntities.PHEASANT,6172963, 13740645, new Item.Settings()));
    public static final Item SNAIL_SPAWN_EGG = registerItem("snail_spawn_egg",
            new SpawnEggItem(ModEntities.SNAIL, 7765379, 7165482, new Item.Settings()));


    private static Item registerItem(String name, Item item) {
        ModItemGroups.SPAWN_EGGS_CONTENTS.add(item.getDefaultStack());
        SimpleSpawnEggItemModel.items.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Egg Items for " + MiddleEarth.MOD_ID);
    }
}
