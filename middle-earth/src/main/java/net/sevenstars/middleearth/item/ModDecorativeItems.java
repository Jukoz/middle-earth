package net.sevenstars.middleearth.item;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.special.LargeDoorBlock;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.item.items.ArkenstoneItem;
import net.sevenstars.middleearth.item.items.CustomSpawnEggItem;
import net.sevenstars.middleearth.item.items.DoorBlockItem;
import net.sevenstars.middleearth.item.utils.ModItemGroups;
import net.sevenstars.middleearth.item.utils.ModVerticallyAttachableBlockItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;

public class ModDecorativeItems {
    public static final Item DWARVEN_LANTERN = registerItem("dwarven_lantern",
            new ModVerticallyAttachableBlockItem(ModDecorativeBlocks.DWARVEN_LANTERN, ModDecorativeBlocks.WALL_DWARVEN_LANTERN, new Item.Settings(), new Direction[]{Direction.DOWN, Direction.UP}));
    public static final Item CRYSTAL_LAMP = registerItem("crystal_lamp",
            new ModVerticallyAttachableBlockItem(ModDecorativeBlocks.CRYSTAL_LAMP, ModDecorativeBlocks.WALL_CRYSTAL_LAMP, new Item.Settings(), new Direction[]{Direction.DOWN, Direction.UP}));
    public static final Item SILVER_LANTERN = registerItem("silver_lantern",
            new ModVerticallyAttachableBlockItem(ModDecorativeBlocks.SILVER_LANTERN, ModDecorativeBlocks.WALL_SILVER_LANTERN, new Item.Settings(), new Direction[]{Direction.DOWN, Direction.UP}));
    public static final Item ELVEN_LANTERN = registerItem("elven_lantern",
            new ModVerticallyAttachableBlockItem(ModDecorativeBlocks.ELVEN_LANTERN, ModDecorativeBlocks.WALL_ELVEN_LANTERN, new Item.Settings(), new Direction[]{Direction.DOWN, Direction.UP}));

    public static final Item SCONCE = registerItem("sconce",
            new VerticallyAttachableBlockItem(ModDecorativeBlocks.SCONCE, ModDecorativeBlocks.WALL_SCONCE, new Item.Settings(), Direction.DOWN));
    public static final Item GILDED_SCONCE = registerItem("gilded_sconce",
            new VerticallyAttachableBlockItem(ModDecorativeBlocks.GILDED_SCONCE, ModDecorativeBlocks.GILDED_WALL_SCONCE, new Item.Settings(), Direction.DOWN));
    public static final Item ORCISH_SCONCE = registerItem("orcish_sconce",
            new VerticallyAttachableBlockItem(ModDecorativeBlocks.ORCISH_SCONCE, ModDecorativeBlocks.ORCISH_WALL_SCONCE, new Item.Settings(), Direction.DOWN));

    public static final Item FORGE = registerItem("forge",
            new BlockItem(ModDecorativeBlocks.FORGE, new Item.Settings()));
    public static final Item TREATED_ANVIL = registerItem("treated_anvil",
            new BlockItem(ModDecorativeBlocks.TREATED_ANVIL, new Item.Settings()));
    public static final Item DWARVEN_TREATED_ANVIL = registerItem("dwarven_treated_anvil",
            new BlockItem(ModDecorativeBlocks.DWARVEN_TREATED_ANVIL, new Item.Settings()));
    public static final Item ELVEN_TREATED_ANVIL = registerItem("elven_treated_anvil",
            new BlockItem(ModDecorativeBlocks.ELVEN_TREATED_ANVIL, new Item.Settings()));
    public static final Item ORCISH_TREATED_ANVIL = registerItem("orcish_treated_anvil",
            new BlockItem(ModDecorativeBlocks.ORCISH_TREATED_ANVIL, new Item.Settings()));
    public static final Item BELLOWS = registerItem("bellows",
            new BlockItem(ModDecorativeBlocks.BELLOWS, new Item.Settings()));
    public static final Item ARTISAN_TABLE = registerItem("artisan_table",
            new BlockItem(ModDecorativeBlocks.ARTISAN_TABLE, new Item.Settings()));

    public static final Item SMALL_CRATE = registerItem("small_crate",
            new BlockItem(ModDecorativeBlocks.SMALL_CRATE, new Item.Settings()));
    public static final Item THIN_BARREL = registerItem("thin_barrel",
            new BlockItem(ModDecorativeBlocks.THIN_BARREL, new Item.Settings()));
    public static final Item REINFORCED_CHEST = registerItem("reinforced_chest",
            new BlockItem(ModDecorativeBlocks.REINFORCED_CHEST, new Item.Settings()));

    public static final Item WOOD_PILE = registerItem("wood_pile",
            new BlockItem(ModDecorativeBlocks.WOOD_PILE, new Item.Settings()));

    public static final Item TALL_BLACK_PINE_DOOR = registerItem("tall_black_pine_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.TALL_BLACK_PINE_DOOR, new Item.Settings().maxCount(16)));

    public static final Item OAK_STABLE_DOOR = registerItem("oak_stable_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.OAK_STABLE_DOOR, new Item.Settings().maxCount(16)));
    public static final Item REINFORCED_SPRUCE_DOOR = registerItem("reinforced_spruce_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.REINFORCED_SPRUCE_DOOR, new Item.Settings().maxCount(16)));
    public static final Item REINFORCED_BLACK_PINE_DOOR = registerItem("reinforced_black_pine_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.REINFORCED_BLACK_PINE_DOOR, new Item.Settings().maxCount(16)));
    public static final Item SIMPLE_LARCH_GATE = registerItem("simple_larch_gate",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.SIMPLE_LARCH_GATE, new Item.Settings().maxCount(16)));
    public static final Item RICKETY_SIMPLE_LARCH_DOOR = registerItem("rickety_simple_larch_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.RICKETY_SIMPLE_LARCH_DOOR, new Item.Settings().maxCount(16)));
    public static final Item SPRUCE_STABLE_DOOR = registerItem("spruce_stable_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.SPRUCE_STABLE_DOOR, new Item.Settings().maxCount(16)));

    public static final Item LARGE_STURDY_DOOR = registerItem("large_sturdy_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.LARGE_STURDY_DOOR, new Item.Settings().maxCount(16)));

    public static final Item LARCH_HOBBIT_DOOR = registerItem("larch_hobbit_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.LARCH_HOBBIT_DOOR, new Item.Settings().maxCount(16)));
    public static final Item SPRUCE_HOBBIT_DOOR = registerItem("spruce_hobbit_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.SPRUCE_HOBBIT_DOOR, new Item.Settings().maxCount(16)));
    public static final Item BLUE_HOBBIT_DOOR = registerItem("blue_hobbit_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.BLUE_HOBBIT_DOOR, new Item.Settings().maxCount(16)));
    public static final Item GREEN_HOBBIT_DOOR = registerItem("green_hobbit_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.GREEN_HOBBIT_DOOR, new Item.Settings().maxCount(16)));
    public static final Item LIGHT_BLUE_HOBBIT_DOOR = registerItem("light_blue_hobbit_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.LIGHT_BLUE_HOBBIT_DOOR, new Item.Settings().maxCount(16)));
    public static final Item RED_HOBBIT_DOOR = registerItem("red_hobbit_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.RED_HOBBIT_DOOR, new Item.Settings().maxCount(16)));
    public static final Item YELLOW_HOBBIT_DOOR = registerItem("yellow_hobbit_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.YELLOW_HOBBIT_DOOR, new Item.Settings().maxCount(16)));

    public static final Item GREAT_GONDORIAN_GATE = registerItem("great_gondorian_gate",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.GREAT_GONDORIAN_GATE, new Item.Settings().maxCount(16)));

    public static final Item GREAT_DWARVEN_GATE = registerItem("great_dwarven_gate",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.GREAT_DWARVEN_GATE, new Item.Settings().maxCount(16)));
    public static final Item HIDDEN_DWARVEN_DOOR = registerItem("hidden_dwarven_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.HIDDEN_DWARVEN_DOOR, new Item.Settings().maxCount(16)));
    public static final Item VARNISHED_DWARVEN_DOOR = registerItem("varnished_dwarven_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR, new Item.Settings().maxCount(16)));
    public static final Item RUINED_DWARVEN_DOOR = registerItem("ruined_dwarven_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.RUINED_DWARVEN_DOOR, new Item.Settings().maxCount(16)));

    public static final Item GREAT_ELVEN_GATE = registerItem("great_elven_gate",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.GREAT_ELVEN_GATE, new Item.Settings().maxCount(16)));

    public static final Item GREAT_ORCISH_GATE = registerItem("great_orcish_gate",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.GREAT_ORCISH_GATE, new Item.Settings().maxCount(16)));

    public static final Item FIRE_OF_ORTHANC = registerItem("fire_of_orthanc",
            new BlockItem(ModDecorativeBlocks.FIRE_OF_ORTHANC, new Item.Settings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item TORCH_OF_ORTHANC = registerItem("torch_of_orthanc",
            new BlockItem(ModDecorativeBlocks.TORCH_OF_ORTHANC, new Item.Settings()));

    public static final Item TROLL_STATUE = registerItem("troll_statue",
            new CustomSpawnEggItem(ModEntities.PETRIFIED_TROLL, new Item.Settings().maxCount(1)));

    public static final Item ARKENSTONE = registerItem("arkenstone",
            new ArkenstoneItem(ModDecorativeBlocks.ARKENSTONE, ModDecorativeBlocks.WALL_ARKENSTONE, new Item.Settings().fireproof(), Direction.DOWN));


    private static Item registerItem(String name, Item item) {
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Decorative Items for " + MiddleEarth.MOD_ID);
    }
}
