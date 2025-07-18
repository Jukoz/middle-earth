package net.sevenstars.middleearth.datageneration.content.tags;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;

import java.util.ArrayList;
import java.util.List;

public class WeaponEnchants {
    public static List<Item> axes = new ArrayList<>() {
    {
        add(ToolItemsME.BRONZE_AXE.asItem());
        add(ToolItemsME.CRUDE_AXE.asItem());
        add(ToolItemsME.BURZUM_STEEL_AXE.asItem());
        add(ToolItemsME.EDHEL_STEEL_PICKAXE.asItem());
        add(ToolItemsME.KHAZAD_STEEL_AXE.asItem());
        add(ToolItemsME.STEEL_AXE.asItem());
        add(ToolItemsME.MITHRIL_AXE.asItem());
    }};

    public static List<Item> daggers = new ArrayList<>() {
    {

    }};

    public static List<Item> swords = new ArrayList<>() {
    {

    }};

    public static List<Item> sharpWeapons = new ArrayList<>() {
        {

        }};
}
