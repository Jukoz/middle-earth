package net.sevenstars.middleearth.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

import java.util.function.Function;

public class NatureBlockItemsME {

    /**
     * Middle-earth mod Nature Items registry for blocks
     */

    public static final Item SMALL_LILY_PADS = registerItem("small_lily_pads",
            (settings) -> new PlaceOnWaterBlockItem(ModNatureBlocks.SMALL_LILY_PADS, settings), new Item.Properties());
    public static final Item SMALL_FLOWERING_LILY_PADS = registerItem("small_flowering_lily_pads",
            (settings) ->  new PlaceOnWaterBlockItem(ModNatureBlocks.SMALL_FLOWERING_LILY_PADS, settings), new Item.Properties());
    public static final Item LILY_PADS = registerItem("lily_pads",
            (settings) ->  new PlaceOnWaterBlockItem(ModNatureBlocks.LILY_PADS, settings), new Item.Properties());
    public static final Item FLOWERING_LILY_PADS = registerItem("flowering_lily_pads",
            (settings) ->  new PlaceOnWaterBlockItem(ModNatureBlocks.FLOWERING_LILY_PADS, settings), new Item.Properties());
    public static final Item LARGE_LILY_PAD = registerItem("large_lily_pad",
            (settings) ->  new PlaceOnWaterBlockItem(ModNatureBlocks.LARGE_LILY_PAD, settings), new Item.Properties());
    public static final Item LARGE_FLOWERING_LILY_PAD = registerItem("large_flowering_lily_pad",
            (settings) ->  new PlaceOnWaterBlockItem(ModNatureBlocks.LARGE_FLOWERING_LILY_PAD, settings), new Item.Properties());

    public static final Item DUCKWEED = registerItem("duckweed",
            (settings) ->  new PlaceOnWaterBlockItem(ModNatureBlocks.DUCKWEED, settings), new Item.Properties());

    public static final Item FLOATING_ICE = registerItem("floating_ice",
            (settings) -> new PlaceOnWaterBlockItem(ModNatureBlocks.FLOATING_ICE, settings), new Item.Properties());

    private static Item registerItem(String name, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = factory.apply(settings);
        ItemGroupsME.NATURE_BLOCKS_CONTENTS.add(item.getDefaultInstance());
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.ITEM, name));
        return RegistrationBridge.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Nature Items for " + MiddleEarth.MOD_ID);
    }
}
