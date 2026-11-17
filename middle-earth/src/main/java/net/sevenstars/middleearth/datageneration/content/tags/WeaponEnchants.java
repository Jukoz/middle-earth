package net.sevenstars.middleearth.datageneration.content.tags;

import net.sevenstars.middleearth.common.registration.RegistrationLists;
import net.minecraft.item.Item;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.item.WeaponItemsME;

import java.util.ArrayList;
import java.util.List;

public class WeaponEnchants {
    public static List<Item> weapons = RegistrationLists.WEAPONS;
    static {
        RegistrationLists.prepend(weapons, new ArrayList<Item>() {

    });
    }

    public static List<Item> axes = RegistrationLists.AXES;
    static {
        RegistrationLists.prepend(axes, new ArrayList<Item>() {
    {
        add(ToolItemsME.BRONZE_AXE.asItem());
        add(ToolItemsME.CRUDE_AXE.asItem());
        add(ToolItemsME.BURZUM_STEEL_AXE.asItem());
        add(ToolItemsME.EDHEL_STEEL_AXE.asItem());
        add(ToolItemsME.KHAZAD_STEEL_AXE.asItem());
        add(ToolItemsME.STEEL_AXE.asItem());
        add(ToolItemsME.MITHRIL_AXE.asItem());
    }});
    }

    public static List<Item> daggers = RegistrationLists.DAGGERS;
    static {
        RegistrationLists.prepend(daggers, new ArrayList<Item>() {
    {

    }});
    }

    public static List<Item> swords = RegistrationLists.SWORDS;
    static {
        RegistrationLists.prepend(swords, new ArrayList<Item>() {
    {

    }});
    }

    public static List<Item> sharpWeapons = RegistrationLists.SHARP_WEAPONS;
    static {
        RegistrationLists.prepend(sharpWeapons, new ArrayList<Item>() {
        {

        }});
    }
}
