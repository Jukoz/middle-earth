package net.jukoz.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.items.EmptyPhialItem;
import net.jukoz.me.item.items.MiddleEarthMapItem;
import net.jukoz.me.item.items.StarlightPhialItem;
import net.jukoz.me.item.items.PebbleItem;
import net.jukoz.me.item.utils.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRessourceItems {
    public static final Item BRONZE_INGOT = registerItem("bronze_ingot",
            new Item(new FabricItemSettings()));
    public static final Item BRONZE_NUGGET = registerItem("bronze_nugget",
            new Item(new FabricItemSettings()));
    public static final Item DWARVEN_STEEL = registerItem("dwarven_steel",
            new Item(new FabricItemSettings()));
    public static final Item DWARVEN_STEEL_NUGGET = registerItem("dwarven_steel_nugget",
            new Item(new FabricItemSettings()));
            
    public static final Item MIDDLE_EARTH_MAP = registerItem("middle_earth_map",
            new MiddleEarthMapItem(new FabricItemSettings().maxCount(1)));

    public static final Item MITHRIL_INGOT = registerItem("mithril_ingot",
            new Item(new FabricItemSettings().fireproof()));
    public static final Item RAW_MITHRIL = registerItem("raw_mithril",
            new Item(new FabricItemSettings().fireproof()));
    public static final Item MITHRIL_NUGGET = registerItem("mithril_nugget",
            new Item(new FabricItemSettings().fireproof()));

    public static final Item RAW_TIN = registerItem("raw_tin",
            new Item(new FabricItemSettings()));
    public static final Item TIN_INGOT = registerItem("tin_ingot",
            new Item(new FabricItemSettings()));
    public static final Item TIN_NUGGET = registerItem("tin_nugget",
            new Item(new FabricItemSettings()));

    public static final Item MORGUL_INGOT = registerItem("morgul_ingot",
            new Item(new FabricItemSettings()));
    public static final Item ORC_STEEL = registerItem("orc_steel",
            new Item(new FabricItemSettings()));
    public static final Item ORC_STEEL_NUGGET = registerItem("orc_steel_nugget",
            new Item(new FabricItemSettings()));
    public static final Item ORC_BONE = registerItem("orc_bone",
            new Item(new FabricItemSettings()));
    public static final Item WARG_BONE = registerItem("warg_bone",
            new Item(new FabricItemSettings()));
    public static final Item EMPTY_PHIAL = registerItem("empty_phial",
            new EmptyPhialItem(new FabricItemSettings()));
    public static final Item WATER_PHIAL = registerItem("water_phial",
            new Item(new FabricItemSettings().maxCount(1)));
    public static final Item STARLIGHT_PHIAL = registerItem("starlight_phial",
            new StarlightPhialItem(new FabricItemSettings().maxCount(1)));
    public static final Item PEBBLE = registerItem("pebble",
            new PebbleItem(new FabricItemSettings()));
    public static final Item STRAW = registerItem("straw",
            new Item(new FabricItemSettings()));
    public static final Block REEDS = registerBlock("reeds",
            new TallPlantBlock(FabricBlockSettings.copyOf(Blocks.TALL_GRASS).strength(0.2f)), false);

    public static final Item DUCK_FEATHER = registerItem("duck_feather",
            new Item(new FabricItemSettings()));
    public static final Item SWAN_FEATHER = registerItem("swan_feather",
            new Item(new FabricItemSettings()));

    public static Block registerBlock(String name, Block block, boolean absent) {
        if(!absent) registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, name), block);
    }
    static Item registerBlockItem(String name, Block block) {
        var item =  Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));

        Item.BLOCK_ITEMS.put(block, item);
        ModItemGroups.RESOURCES_CONTENTS.add(item.getDefaultStack());
        return item;
    }

    private static Item registerItem(String name, Item item) {
        ModItemGroups.RESOURCES_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.debug("Registering Mod Resource Items for " + MiddleEarth.MOD_ID);
    }
}
