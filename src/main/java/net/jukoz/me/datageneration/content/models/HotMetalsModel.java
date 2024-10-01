package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class HotMetalsModel {

    public record ShapingTag(TagKey tagKey, Item output, int amount) {}
    public record ShapingItem(Item item, Item output, int amount) {}

    public static List<Item> ingots = new ArrayList<>() {
        {
            add(Items.COPPER_INGOT);
            add(Items.IRON_INGOT);
            add(Items.GOLD_INGOT);
            add(ModResourceItems.BRONZE_INGOT);
            add(ModResourceItems.CRUDE_INGOT);
            add(ModResourceItems.STEEL_INGOT);
            add(ModResourceItems.KHAZAD_STEEL_INGOT);
            add(ModResourceItems.EDHEL_STEEL_INGOT);
            add(ModResourceItems.BURZUM_STEEL_INGOT);
            add(ModResourceItems.MITHRIL_INGOT);
            add(Items.NETHERITE_INGOT);
        }
    };

    public static List<Item> nuggets = new ArrayList<>() {
        {
            add(Items.IRON_NUGGET);
            add(Items.GOLD_NUGGET);
            add(ModResourceItems.BRONZE_NUGGET);
            add(ModResourceItems.CRUDE_NUGGET);
            add(ModResourceItems.STEEL_NUGGET);
            add(ModResourceItems.KHAZAD_STEEL_NUGGET);
            add(ModResourceItems.EDHEL_STEEL_NUGGET);
            add(ModResourceItems.BURZUM_STEEL_NUGGET);
            add(ModResourceItems.MITHRIL_NUGGET);
        }
    };

    public static List<Item> items = new ArrayList<>() {
        {
            add(ModResourceItems.AXE_HEAD);
            add(ModResourceItems.PICKAXE_HEAD);
            add(ModResourceItems.SHOVEL_HEAD);
            add(ModResourceItems.HOE_HEAD);
            add(ModResourceItems.BLADE);
            add(ModResourceItems.SHORT_BLADE);
            add(ModResourceItems.LONG_BLADE);
            add(ModResourceItems.SWORD_HILT);
            add(ModResourceItems.ROD);
            add(ModResourceItems.LARGE_ROD);
        }
    };

    public static List<ShapingTag> shapesTag = new ArrayList<>() {
        {
            add(new ShapingTag(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "ingot_shaping")), ModResourceItems.SHORT_BLADE, 80));
            add(new ShapingTag(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "ingot_shaping")), ModResourceItems.SHOVEL_HEAD, 80));
            add(new ShapingTag(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "ingot_shaping")), ModResourceItems.SWORD_HILT, 80));
        }
    };

    public static List<ShapingItem> shapesItem = new ArrayList<>() {
        {
            add(new ShapingItem(ModResourceItems.ROD, ModResourceItems.BLADE, 120));
            add(new ShapingItem(ModResourceItems.ROD, ModResourceItems.HOE_HEAD, 120));

            add(new ShapingItem(ModResourceItems.LARGE_ROD, ModResourceItems.LONG_BLADE, 160));
            add(new ShapingItem(ModResourceItems.LARGE_ROD, ModResourceItems.AXE_HEAD, 160));
            add(new ShapingItem(ModResourceItems.LARGE_ROD, ModResourceItems.PICKAXE_HEAD, 160));
        }
    };
}
