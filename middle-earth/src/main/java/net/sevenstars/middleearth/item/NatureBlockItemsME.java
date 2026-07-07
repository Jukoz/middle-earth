package net.sevenstars.middleearth.item;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.minecraft.item.Item;
import net.minecraft.item.PlaceableOnWaterItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

import java.util.function.Function;

public class NatureBlockItemsME {

    /**
     * Middle-earth mod Nature Items registry for blocks
     */

    public static final Item SMALL_LILY_PADS = registerItem("small_lily_pads",
            (settings) -> new PlaceableOnWaterItem(ModNatureBlocks.SMALL_LILY_PADS, settings), new Item.Settings());
    public static final Item SMALL_FLOWERING_LILY_PADS = registerItem("small_flowering_lily_pads",
            (settings) ->  new PlaceableOnWaterItem(ModNatureBlocks.SMALL_FLOWERING_LILY_PADS, settings), new Item.Settings());
    public static final Item LILY_PADS = registerItem("lily_pads",
            (settings) ->  new PlaceableOnWaterItem(ModNatureBlocks.LILY_PADS, settings), new Item.Settings());
    public static final Item FLOWERING_LILY_PADS = registerItem("flowering_lily_pads",
            (settings) ->  new PlaceableOnWaterItem(ModNatureBlocks.FLOWERING_LILY_PADS, settings), new Item.Settings());
    public static final Item LARGE_LILY_PAD = registerItem("large_lily_pad",
            (settings) ->  new PlaceableOnWaterItem(ModNatureBlocks.LARGE_LILY_PAD, settings), new Item.Settings());
    public static final Item LARGE_FLOWERING_LILY_PAD = registerItem("large_flowering_lily_pad",
            (settings) ->  new PlaceableOnWaterItem(ModNatureBlocks.LARGE_FLOWERING_LILY_PAD, settings), new Item.Settings());

    public static final Item DUCKWEED = registerItem("duckweed",
            (settings) ->  new PlaceableOnWaterItem(ModNatureBlocks.DUCKWEED, settings), new Item.Settings());

    public static final Item FLOATING_ICE = registerItem("floating_ice",
            (settings) -> new PlaceableOnWaterItem(ModNatureBlocks.FLOATING_ICE, settings), new Item.Settings());

    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ItemGroupsME.NATURE_BLOCKS_CONTENTS.add(item.getDefaultStack());
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.ITEM, name));
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Nature Items for " + MiddleEarth.MOD_ID);
    }
}
