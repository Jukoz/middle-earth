package net.jukoz.me.datageneration;
import net.jukoz.me.utils.LoggerUtil;
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
    public static Identifier getPullLongbowModel(Item item, float progress) {
        String predicate;
        if(progress < 0.65f) {
            predicate = "0";
        } else if(progress < 0.9f) {
            predicate = "1";
        }else {
            predicate = "2";
        }
        System.out.println("Pull: " + progress + " : " + predicate);
        return Registries.ITEM.getId(item).withPrefixedPath("item/").withSuffixedPath("_pulling_" + predicate + "_inventory");
    }
}
