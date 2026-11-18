package net.sevenstars.middleearth.common.registration;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

import java.util.List;
import java.util.function.Function;

public class BlockRegistrar {
    public static RegistryKey<Block> keyOfBlock(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MiddleEarth.MOD_ID, id));
    }

    public static RegistryKey<Item> keyOfItem(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, id));
    }

    public static Block register(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = factory.apply(settings.registryKey(keyOfBlock(name)));
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.BLOCK, name));
        return Registry.register(Registries.BLOCK, keyOfBlock(name), block);
    }

    public static Block registerWithItem(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, Item.Settings itemSettings, List<ItemStack> group) {
        Block block = factory.apply(settings.registryKey(keyOfBlock(name)));
        registerBlockItem(name, block, itemSettings);
        group.add(block.asItem().getDefaultStack());
        TranslationEntries.blockEntries.add(block);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.BLOCK, name));
        return Registry.register(Registries.BLOCK, keyOfBlock(name), block);
    }

    public static void registerBlockItem(String name, Block block, Item.Settings itemSettings) {
        var item = Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name),
                new BlockItem(block, itemSettings.registryKey(keyOfItem(name))));
        Item.BLOCK_ITEMS.put(block, item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.ITEM, name));
    }
}
