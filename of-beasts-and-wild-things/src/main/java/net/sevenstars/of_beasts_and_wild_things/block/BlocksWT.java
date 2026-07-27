package net.sevenstars.of_beasts_and_wild_things.block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.block.custom.BirdNest;
import net.sevenstars.of_beasts_and_wild_things.datageneration.content.TranslationEntries;
import net.sevenstars.of_beasts_and_wild_things.item.ItemGroupsWT;
import net.sevenstars.api.registries.RegistrationBridge;

import java.util.List;
import java.util.function.Function;

public class BlocksWT {

    public static final Block BIRD_NEST = registerBlock("bird_nest",
            BirdNest::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion().noCollission(), ItemGroupsWT.BLOCKS_CONTENTS);

    public static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, List<ItemStack> group){
        Block block = factory.apply(settings);
        registerBlockItem(name, block);
        group.add(block.asItem().getDefaultInstance());
        TranslationEntries.blockEntries.add(block);
        return RegistrationBridge.register(BuiltInRegistries.BLOCK, OfBeastsAndWildThings.of(name), block);
    }

    static void registerBlockItem(String name, Block block) {
        var item = RegistrationBridge.register(BuiltInRegistries.ITEM, OfBeastsAndWildThings.of(name),
                new BlockItem(block, new Item.Properties()));
        Item.BY_BLOCK.put(block, item);
    }

    public static ResourceKey<Block> keyOfBlock(String id) {
        return ResourceKey.create(Registries.BLOCK, OfBeastsAndWildThings.of(id));
    }

    public static ResourceKey<Item> keyOfItem(String id) {
        return ResourceKey.create(Registries.ITEM, OfBeastsAndWildThings.of(id));
    }

    public static void registerModBlocks() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering ModBlocks for " + OfBeastsAndWildThings.MOD_ID);
    }
}
