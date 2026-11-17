package net.sevenstars.middleearth.common.registration;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

import java.util.List;
import java.util.function.Function;

public class ItemRegistrar {
    // A null group keeps the item out of the creative tabs.
    public static Item register(String name, Function<Item.Settings, Item> factory, Item.Settings settings, List<ItemStack> group) {
        Item item = factory.apply(settings.registryKey(BlockRegistrar.keyOfItem(name)));
        if (group != null) {
            group.add(item.getDefaultStack());
        }
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.ITEM, name));
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }
}
