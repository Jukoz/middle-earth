package net.sevenstars.middleearth.recipe;

import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static TagKey<Item> DYEABLE = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "dyeable"));

    public static TagKey<Item> REPAIRS_STRAW_ARMOR = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "repairs_straw_armor"));
    public static TagKey<Item> REPAIRS_WOOL_ARMOR = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "repairs_wool_armor"));
    public static TagKey<Item> REPAIRS_FUR_ARMOR = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "repairs_fur_armor"));
    public static TagKey<Item> REPAIRS_FABRIC_ARMOR = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "repairs_fabric_armor"));
    public static TagKey<Item> REPAIRS_BRONZE_ARMOR = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "repairs_bronze_armor"));
    public static TagKey<Item> REPAIRS_CRUDE_ARMOR = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "repairs_crude_armor"));
    public static TagKey<Item> REPAIRS_STEEL_ARMOR = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "repairs_steel_armor"));
    public static TagKey<Item> REPAIRS_KHAZAD_STEEL_ARMOR = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "repairs_khazad_steel_armor"));
    public static TagKey<Item> REPAIRS_EDHEL_STEEL_ARMOR = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "repairs_edhel_steel_armor"));
    public static TagKey<Item> REPAIRS_BURZUM_STEEL_ARMOR = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "repairs_burzum_steel_armor"));

    public static TagKey<Item> COPPER_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "copper_tool_materials"));
    public static TagKey<Item> BRONZE_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "bronze_tool_materials"));
    public static TagKey<Item> CRUDE_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "crude_tool_materials"));
    public static TagKey<Item> STEEL_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "steel_tool_materials"));
    public static TagKey<Item> KHAZAD_STEEL_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "khazad_steel_tool_materials"));
    public static TagKey<Item> EDHEL_STEEL_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "edhel_steel_tool_materials"));
    public static TagKey<Item> BURZUM_STEEL_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "burzum_steel_tool_materials"));
    public static TagKey<Item> MITHRIL_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, Identifier.of(MiddleEarth.MOD_ID, "mithril_tool_materials"));
}
