package net.sevenstars.middleearth.utils;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ItemTagsME {
    //TODO actually gen the tags
    public static TagKey<Item> REPAIRS_STRAW_ARMOR = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("repairs_straw_armor"));
    public static TagKey<Item> REPAIRS_WOOL_ARMOR = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("repairs_wool_armor"));
    public static TagKey<Item> REPAIRS_FUR_ARMOR = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("repairs_fur_armor"));
    public static TagKey<Item> REPAIRS_FABRIC_ARMOR = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("repairs_fabric_armor"));
    public static TagKey<Item> REPAIRS_BRONZE_ARMOR = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("repairs_bronze_armor"));
    public static TagKey<Item> REPAIRS_CRUDE_ARMOR = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("repairs_crude_armor"));
    public static TagKey<Item> REPAIRS_STEEL_ARMOR = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("repairs_steel_armor"));
    public static TagKey<Item> REPAIRS_KHAZAD_STEEL_ARMOR = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("repairs_khazad_steel_armor"));
    public static TagKey<Item> REPAIRS_EDHEL_STEEL_ARMOR = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("repairs_edhel_steel_armor"));
    public static TagKey<Item> REPAIRS_BURZUM_STEEL_ARMOR = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("repairs_burzum_steel_armor"));

    public static TagKey<Item> BONE_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("bone_tool_materials"));
    public static TagKey<Item> COPPER_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("copper_tool_materials"));
    public static TagKey<Item> BRONZE_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("bronze_tool_materials"));
    public static TagKey<Item> CRUDE_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("crude_tool_materials"));
    public static TagKey<Item> STEEL_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("steel_tool_materials"));
    public static TagKey<Item> KHAZAD_STEEL_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("khazad_steel_tool_materials"));
    public static TagKey<Item> EDHEL_STEEL_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("edhel_steel_tool_materials"));
    public static TagKey<Item> BURZUM_STEEL_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("burzum_steel_tool_materials"));
    public static TagKey<Item> MITHRIL_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("mithril_tool_materials"));

    public static TagKey<Item> SPIDER_TOOL_MATERIALS = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("spider_tool_materials"));

    public static TagKey<Item> HORSE_ARMORS = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("horse_armor"));
    public static TagKey<Item> WARG_ARMORS = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("warg_armor"));
    public static TagKey<Item> ELK_FOOD = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("elk_food"));

    public static TagKey<Item> CHARACTER_HELMET_SHOW_EARS = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("character_helmet_should_show_ears"));
    public static TagKey<Item> CHARACTER_HELMET_HIDE_HAIR = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("character_helmet_should_hide_hair"));
    public static TagKey<Item> CHARACTER_HELMET_HIDE_BEARD = TagKey.of(RegistryKeys.ITEM, IdentifierUtil.build("character_helmet_should_hide_beard"));
}
