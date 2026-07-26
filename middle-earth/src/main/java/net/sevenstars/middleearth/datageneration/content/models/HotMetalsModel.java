package net.sevenstars.middleearth.datageneration.content.models;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.ResourceItemsME;
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
            add(ResourceItemsME.TIN_INGOT);
            add(ResourceItemsME.LEAD_INGOT);
            add(ResourceItemsME.SILVER_INGOT);
            add(ResourceItemsME.BRONZE_INGOT);
            add(ResourceItemsME.CRUDE_INGOT);
            add(ResourceItemsME.STEEL_INGOT);
            add(ResourceItemsME.KHAZAD_STEEL_INGOT);
            add(ResourceItemsME.EDHEL_STEEL_INGOT);
            add(ResourceItemsME.BURZUM_STEEL_INGOT);
            add(ResourceItemsME.MITHRIL_INGOT);
            add(Items.NETHERITE_INGOT);
        }
    };

    public static List<Item> nuggets = new ArrayList<>() {
        {
            add(Items.IRON_NUGGET);
            add(Items.GOLD_NUGGET);
            add(ResourceItemsME.BRONZE_NUGGET);
            add(ResourceItemsME.CRUDE_NUGGET);
            add(ResourceItemsME.STEEL_NUGGET);
            add(ResourceItemsME.KHAZAD_STEEL_NUGGET);
            add(ResourceItemsME.EDHEL_STEEL_NUGGET);
            add(ResourceItemsME.BURZUM_STEEL_NUGGET);
            add(ResourceItemsME.MITHRIL_NUGGET);
        }
    };

    public static List<Item> nuggies = new ArrayList<>() {
        {
            add(ResourceItemsME.PTEROSAUR_NUGGET);
            add(ResourceItemsME.THERAPOD_NUGGET);
            add(ResourceItemsME.CERATOPSIAN_NUGGET);
            add(ResourceItemsME.THYREOPHORAN_NUGGET);
            add(ResourceItemsME.SAUROPOD_NUGGET);
        }
    };

    public static List<Item> items = new ArrayList<>() {
        {
            add(ResourceItemsME.AXE_HEAD);
            add(ResourceItemsME.PICKAXE_HEAD);
            add(ResourceItemsME.SHOVEL_HEAD);
            add(ResourceItemsME.HOE_HEAD);
            add(ResourceItemsME.BLADE);
            add(ResourceItemsME.SHORT_BLADE);
            add(ResourceItemsME.LONG_BLADE);
            add(ResourceItemsME.SWORD_HILT);
            add(ResourceItemsME.ROD);
            add(ResourceItemsME.LARGE_ROD);

            add(ResourceItemsME.MAIL_RING);
            add(ResourceItemsME.SCALE);
            add(ResourceItemsME.ARMOR_PLATE);
            add(ResourceItemsME.HELMET_PLATE);
            add(ResourceItemsME.SHIELD_BORDER);
            add(ResourceItemsME.SHIELD_PLATE);
        }
    };

    public static List<Item> hotItems = new ArrayList<>() {
        {
            add(ResourceItemsME.ROD);
            add(ResourceItemsME.LARGE_ROD);
            add(ResourceItemsME.ARMOR_PLATE);
        }
    };

    public static List<ShapingTag> shapesTag = new ArrayList<>() {
        {
            //add(new ShapingTag(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "nugget_shaping")), ModResourceItems.MAIL_RING, 25));
            //add(new ShapingTag(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "nugget_shaping")), ModResourceItems.SCALE, 25));

            add(new ShapingTag(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "ingot_shaping")), ResourceItemsME.SHORT_BLADE, 80));
            add(new ShapingTag(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "ingot_shaping")), ResourceItemsME.SHOVEL_HEAD, 80));
            add(new ShapingTag(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "ingot_shaping")), ResourceItemsME.SWORD_HILT, 80));
            add(new ShapingTag(TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "ingot_shaping")), ResourceItemsME.SHIELD_BORDER, 80));
        }
    };

    public static List<ShapingItem> shapesItem = new ArrayList<>() {
        {
            add(new ShapingItem(ResourceItemsME.ROD, ResourceItemsME.BLADE, 120));
            add(new ShapingItem(ResourceItemsME.ROD, ResourceItemsME.HOE_HEAD, 120));

            add(new ShapingItem(ResourceItemsME.LARGE_ROD, ResourceItemsME.LONG_BLADE, 160));
            add(new ShapingItem(ResourceItemsME.LARGE_ROD, ResourceItemsME.AXE_HEAD, 160));
            add(new ShapingItem(ResourceItemsME.LARGE_ROD, ResourceItemsME.PICKAXE_HEAD, 160));

            add(new ShapingItem(ResourceItemsME.ARMOR_PLATE, ResourceItemsME.HELMET_PLATE, 160));
            add(new ShapingItem(ResourceItemsME.ARMOR_PLATE, ResourceItemsME.SHIELD_PLATE, 160));
        }
    };
}
