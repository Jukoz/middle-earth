package net.jukoz.me.item;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.item.Item;
import net.minecraft.item.PlaceableOnWaterItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModNatureBlockItems {

    public static final Item SMALL_LILY_PADS = registerItem("small_lily_pads", new PlaceableOnWaterItem(ModNatureBlocks.SMALL_LILY_PADS, new Item.Settings()));
    public static final Item DUCKWEED = registerItem("duckweed", new PlaceableOnWaterItem(ModNatureBlocks.DUCKWEED, new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        ModItemGroups.NATURE_BLOCKS_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        LoggerUtil.getInstance().logDebugMsg("Registering Mod Nature Items for " + MiddleEarth.MOD_ID);
    }
}