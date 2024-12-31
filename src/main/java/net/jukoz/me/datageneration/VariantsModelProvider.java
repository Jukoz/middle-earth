package net.jukoz.me.datageneration;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;


public class VariantsModelProvider {
    public static Identifier getInventoryModelIdentifierVariant(Item item) {
        return Registries.ITEM.getId(item).withPrefixedPath("item/").withSuffixedPath("_inventory");
    }

    public static Identifier getInventoryLongbowModelIdentifierVariant(Item item, int stage) {
        return Registries.ITEM.getId(item).withPrefixedPath("item/").withSuffixedPath("_pulling_" + stage + "_inventory");
    }

    public static Identifier getHotModelIdentifierVariant(Item item) {
        return Registries.ITEM.getId(item).withPrefixedPath("item/").withSuffixedPath("_hot");
    }

    public static Identifier getPullModelIdentifierVariant(Item item) {
        return Registries.ITEM.getId(item).withPrefixedPath("item/").withSuffixedPath("_pull");
    }

    public static Identifier getInventoryModelBrokenItem(Item item) {
        return Registries.ITEM.getId(item).withPrefixedPath("item/").withSuffixedPath("_broken_inventory");
    }
    public static Identifier getInventoryModelGlowingItem(Item item) {
        return Registries.ITEM.getId(item).withPrefixedPath("item/").withSuffixedPath("_glowing_inventory");
    }
}
