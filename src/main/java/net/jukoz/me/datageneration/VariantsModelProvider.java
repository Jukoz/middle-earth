package net.jukoz.me.datageneration;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class VariantsModelProvider {
    public static Identifier getInventoryModelIdentifierVariant(Item item) {
        return Registries.ITEM.getId(item).withPrefixedPath("item/").withSuffixedPath("_inventory");
    }

    public static Identifier getHotModelIdentifierVariant(Item item) {
        return Registries.ITEM.getId(item).withPrefixedPath("item/").withSuffixedPath("_hot");
    }
}
