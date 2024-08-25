package net.jukoz.me.item;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.block.special.LargeDoorBlock;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.item.items.CustomSpawnEggItem;
import net.jukoz.me.item.items.DoorBlockItem;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.item.utils.ModVerticallyAttachableBlockItem;
import net.jukoz.me.utils.LoggerUtil;
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
    public static final Item SILVER_LANTERN = registerItem("silver_lantern",
            new ModVerticallyAttachableBlockItem(ModDecorativeBlocks.SILVER_LANTERN, ModDecorativeBlocks.WALL_SILVER_LANTERN, new Item.Settings(), new Direction[]{Direction.DOWN, Direction.UP}));

    public static final Item SCONCE = registerItem("sconce",
            new VerticallyAttachableBlockItem(ModDecorativeBlocks.SCONCE, ModDecorativeBlocks.WALL_SCONCE, new Item.Settings(), Direction.DOWN));
    public static final Item GILDED_SCONCE = registerItem("gilded_sconce",
            new VerticallyAttachableBlockItem(ModDecorativeBlocks.GILDED_SCONCE, ModDecorativeBlocks.GILDED_WALL_SCONCE, new Item.Settings(), Direction.DOWN));

    public static final Item ALLOY_FURNACE = registerItem("alloy_furnace",
            new BlockItem(ModDecorativeBlocks.ALLOY_FURNACE, new Item.Settings()));
    public static final Item ARTISAN_TABLE = registerItem("artisan_table",
            new BlockItem(ModDecorativeBlocks.ARTISAN_TABLE, new Item.Settings()));

    public static final Item REINFORCED_CHEST = registerItem("reinforced_chest",
            new BlockItem(ModDecorativeBlocks.REINFORCED_CHEST, new Item.Settings()));

    public static final Item WOOD_PILE = registerItem("wood_pile",
            new BlockItem(ModDecorativeBlocks.WOOD_PILE, new Item.Settings()));

    public static final Item LARCH_HOBBIT_DOOR = registerItem("larch_hobbit_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.LARCH_HOBBIT_DOOR, new Item.Settings().maxCount(16)));
    public static final Item SPRUCE_HOBBIT_DOOR = registerItem("spruce_hobbit_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.SPRUCE_HOBBIT_DOOR, new Item.Settings().maxCount(16)));
    public static final Item BLUE_HOBBIT_DOOR = registerItem("blue_hobbit_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.BLUE_HOBBIT_DOOR, new Item.Settings().maxCount(16)));
    public static final Item GREEN_HOBBIT_DOOR = registerItem("green_hobbit_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.GREEN_HOBBIT_DOOR, new Item.Settings().maxCount(16)));
    public static final Item RED_HOBBIT_DOOR = registerItem("red_hobbit_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.RED_HOBBIT_DOOR, new Item.Settings().maxCount(16)));
    public static final Item YELLOW_HOBBIT_DOOR = registerItem("yellow_hobbit_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.YELLOW_HOBBIT_DOOR, new Item.Settings().maxCount(16)));

    public static final Item REINFORCED_SPRUCE_DOOR = registerItem("reinforced_spruce_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.REINFORCED_SPRUCE_DOOR, new Item.Settings().maxCount(16)));

    public static final Item GREAT_DWARVEN_GATE = registerItem("great_dwarven_gate",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.GREAT_DWARVEN_GATE, new Item.Settings().maxCount(16)));
    public static final Item VARNISHED_DWARVEN_DOOR = registerItem("varnished_dwarven_door",
            new DoorBlockItem((LargeDoorBlock) ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR, new Item.Settings().maxCount(16)));

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

    private static Item registerItem(String name, Item item) {
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        LoggerUtil.logDebugMsg("Registering Mod Decorative Items for " + MiddleEarth.MOD_ID);
    }
}
