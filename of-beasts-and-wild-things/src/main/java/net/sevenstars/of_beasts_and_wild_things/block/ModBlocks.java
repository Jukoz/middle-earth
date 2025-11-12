package net.sevenstars.of_beasts_and_wild_things.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.block.custom.BirdNest;
import net.sevenstars.of_beasts_and_wild_things.datageneration.content.TranslationEntries;
import net.sevenstars.of_beasts_and_wild_things.item.ItemGroupsWT;

import java.util.List;
import java.util.function.Function;

public class ModBlocks {

    public static final Block BIRD_NEST = registerBlock("bird_nest",
            BirdNest::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque().noCollision(), ItemGroupsWT.BLOCKS_CONTENTS);

    public static Block registerBlock(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, List<ItemStack> group){
        Block block = (Block)factory.apply(settings.registryKey(keyOfBlock(name)));
        registerBlockItem(name, block);
        group.add(block.asItem().getDefaultStack());
        TranslationEntries.blockEntries.add(block);
        return Registry.register(Registries.BLOCK, keyOfBlock(name), block);
    }

    static void registerBlockItem(String name, Block block) {
        var item =  Registry.register(Registries.ITEM, Identifier.of(OfBeastsAndWildThings.MOD_ID, name),
                new BlockItem(block, new Item.Settings().registryKey(keyOfItem(name))));
        Item.BLOCK_ITEMS.put(block, item);
        TranslationEntries.itemEntries.add(item);
    }

    public static RegistryKey<Block> keyOfBlock(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(OfBeastsAndWildThings.MOD_ID, id));
    }

    public static RegistryKey<Item> keyOfItem(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(OfBeastsAndWildThings.MOD_ID, id));
    }

    public static void registerModBlocks() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering ModBlocks for " + OfBeastsAndWildThings.MOD_ID);
    }
}
