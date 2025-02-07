package net.sevenstars.middleearth.item.utils.armor;

import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.recipe.ModTags;

import java.util.EnumMap;

public interface ModArmorMaterials {

    ExtendedArmorMaterial STRAW_T1 = registerArmor("straw_t1", Tiers.BASIC, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ModTags.REPAIRS_STRAW_ARMOR);

    ExtendedArmorMaterial WOOL_T1 = registerArmor("wool_t1", Tiers.CLOTHING, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ModTags.REPAIRS_WOOL_ARMOR);

    ExtendedArmorMaterial FUR_T0 = registerArmor("fur_t0", Tiers.CLOTHING, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ModTags.REPAIRS_FUR_ARMOR);

    ExtendedArmorMaterial FABRIC_T0 = registerArmor("fabric_t0", Tiers.CLOTHING, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ModTags.REPAIRS_FABRIC_ARMOR);
    ExtendedArmorMaterial FABRIC_T1 = registerArmor("fabric_t1", Tiers.BASIC, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ModTags.REPAIRS_FABRIC_ARMOR);

    ExtendedArmorMaterial LEATHER_T1 = registerArmor("leather_t1", Tiers.BASIC, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ItemTags.REPAIRS_LEATHER_ARMOR);
    ExtendedArmorMaterial LEATHER_T2 = registerArmor("leather_t2", Tiers.LIGHT, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ItemTags.REPAIRS_LEATHER_ARMOR);

    ExtendedArmorMaterial IRON_T2 = registerArmor("iron_t2", Tiers.LIGHT, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ItemTags.REPAIRS_IRON_ARMOR);
    ExtendedArmorMaterial IRON_T3 = registerArmor("iron_t3", Tiers.MEDIUM, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ItemTags.REPAIRS_IRON_ARMOR);

    ExtendedArmorMaterial BRONZE_T1 = registerArmor("bronze_t1", Tiers.BASIC, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModTags.REPAIRS_BRONZE_ARMOR);
    ExtendedArmorMaterial BRONZE_T2 = registerArmor("bronze_t2", Tiers.LIGHT, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModTags.REPAIRS_BRONZE_ARMOR);

    ExtendedArmorMaterial CRUDE_T2 = registerArmor("crude_t2", Tiers.LIGHT, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModTags.REPAIRS_CRUDE_ARMOR);
    ExtendedArmorMaterial CRUDE_T3 = registerArmor("crude_t3", Tiers.MEDIUM, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModTags.REPAIRS_CRUDE_ARMOR);

    ExtendedArmorMaterial STEEL_T4 = registerArmor("straw_t4", Tiers.STURDY, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ModTags.REPAIRS_STEEL_ARMOR);
    ExtendedArmorMaterial STEEL_T5 = registerArmor("straw_t5", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, ModTags.REPAIRS_STEEL_ARMOR);

    ExtendedArmorMaterial DWARVEN_STEEL_T4 = registerArmor("dwarven_steel_t4", Tiers.STURDY, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ModTags.REPAIRS_KHAZAD_STEEL_ARMOR);
    ExtendedArmorMaterial DWARVEN_STEEL_T5 = registerArmor("dwarven_steel_t5", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, ModTags.REPAIRS_KHAZAD_STEEL_ARMOR);

    ExtendedArmorMaterial ELVEN_STEEL_T4 = registerArmor("elven_steel_t4", Tiers.STURDY, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ModTags.REPAIRS_EDHEL_STEEL_ARMOR);
    ExtendedArmorMaterial ELVEN_STEEL_T5 = registerArmor("elven_steel_t5", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, ModTags.REPAIRS_EDHEL_STEEL_ARMOR);

    ExtendedArmorMaterial BURZUM_STEEL_T3 = registerArmor("burzum_steel_t3", Tiers.MEDIUM, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModTags.REPAIRS_BURZUM_STEEL_ARMOR);
    ExtendedArmorMaterial BURZUM_STEEL_T4 = registerArmor("burzum_steel_t4", Tiers.STURDY, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ModTags.REPAIRS_BURZUM_STEEL_ARMOR);
    ExtendedArmorMaterial BURZUM_STEEL_T5 = registerArmor("burzum_steel_t5", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, ModTags.REPAIRS_BURZUM_STEEL_ARMOR);

    ExtendedArmorMaterial LEATHER = registerArmor("leather", Tiers.LIGHT, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ItemTags.REPAIRS_LEATHER_ARMOR);
    ExtendedArmorMaterial MAIL = registerArmor("mail", Tiers.MEDIUM, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ItemTags.REPAIRS_IRON_ARMOR);
    ExtendedArmorMaterial PLATE = registerArmor("plate", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModTags.REPAIRS_STEEL_ARMOR);

    ExtendedArmorMaterial GONDORIAN_HORSE_ARMOR = registerArmor("gondorian_horse_armor", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModTags.REPAIRS_STEEL_ARMOR);
    ExtendedArmorMaterial ROHIRRIC_HORSE_ARMOR = registerArmor("rohirric_horse_armor", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModTags.REPAIRS_STEEL_ARMOR);
    ExtendedArmorMaterial DALISH_HORSE_ARMOR = registerArmor("dalish_horse_armor", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModTags.REPAIRS_STEEL_ARMOR);
    ExtendedArmorMaterial LORIEN_HORSE_ARMOR = registerArmor("lorien_horse_armor", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModTags.REPAIRS_EDHEL_STEEL_ARMOR);

    private static ExtendedArmorMaterial registerArmor(String name, Tiers tier, RegistryEntry<SoundEvent> equipSound, TagKey<Item> repairTag) {
        EnumMap<EquipmentType, Integer> map = new EnumMap<>(EquipmentType.class);
        float toughness;
        
        float knockbackResistance;
        int enchantability;
        int durabilityMultiplier;
        switch (tier) {
            case CLOTHING -> {
                map.put(EquipmentType.BOOTS, 1);
                map.put(EquipmentType.LEGGINGS, 1);
                map.put(EquipmentType.CHESTPLATE, 1);
                map.put(EquipmentType.HELMET, 1);
                map.put(EquipmentType.BODY, 1);
                durabilityMultiplier = 5;
                toughness = 0.0f;
                knockbackResistance = 0.0f;
                enchantability = 10;
            }
            case BASIC -> {
                map.put(EquipmentType.BOOTS, 1);
                map.put(EquipmentType.LEGGINGS, 2);
                map.put(EquipmentType.CHESTPLATE, 3);
                map.put(EquipmentType.HELMET, 1);
                map.put(EquipmentType.BODY, 4);
                durabilityMultiplier = 7;
                toughness = 0.0f;
                knockbackResistance = 0.0f;
                enchantability = 10;
            }
            case LIGHT -> {
                map.put(EquipmentType.BOOTS, 1);
                map.put(EquipmentType.LEGGINGS, 3);
                map.put(EquipmentType.CHESTPLATE, 4);
                map.put(EquipmentType.HELMET, 1);
                map.put(EquipmentType.BODY, 5);
                durabilityMultiplier = 11;
                toughness = 0.0f;
                knockbackResistance = 0.0f;
                enchantability = 10;
            }
            case MEDIUM -> {
                map.put(EquipmentType.BOOTS, 2);
                map.put(EquipmentType.LEGGINGS, 4);
                map.put(EquipmentType.CHESTPLATE, 5);
                map.put(EquipmentType.HELMET, 2);
                map.put(EquipmentType.BODY, 6);
                durabilityMultiplier = 15;
                toughness = 0.5f;
                knockbackResistance = 0.0f;
                enchantability = 10;
            }
            case STURDY -> {
                map.put(EquipmentType.BOOTS, 2);
                map.put(EquipmentType.LEGGINGS, 5);
                map.put(EquipmentType.CHESTPLATE, 6);
                map.put(EquipmentType.HELMET, 2);
                map.put(EquipmentType.BODY, 8);
                durabilityMultiplier = 25;
                toughness = 1.0f;
                knockbackResistance = 0.1f;
                enchantability = 10;

            }
            case HEAVY -> {
                map.put(EquipmentType.BOOTS, 3);
                map.put(EquipmentType.LEGGINGS, 6);
                map.put(EquipmentType.CHESTPLATE, 7);
                map.put(EquipmentType.HELMET, 3);
                map.put(EquipmentType.BODY, 10);
                durabilityMultiplier = 35;
                toughness = 2.0f;
                knockbackResistance = 0.1f;
                enchantability = 10;
            }
            default -> {
                map.put(EquipmentType.BOOTS, 1);
                map.put(EquipmentType.LEGGINGS, 2);
                map.put(EquipmentType.CHESTPLATE, 3);
                map.put(EquipmentType.HELMET, 1);
                map.put(EquipmentType.BODY, 3);
                durabilityMultiplier = 5;
                toughness = 0.0f;
                knockbackResistance = 0.0f;
                enchantability = 1;
            }
        }
        return register(name, map, durabilityMultiplier, enchantability, equipSound, toughness, knockbackResistance, repairTag, tier);
    }

    private static ExtendedArmorMaterial register(String name, EnumMap<EquipmentType, Integer> defense,
                                                  int durabilityMultiplier, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance,
                                                  TagKey<Item> repairTag, Tiers tier) {

        EnumMap<EquipmentType, Integer> enumMap = new EnumMap<>(EquipmentType.class);
        for (EquipmentType type : EquipmentType.values()) {
            enumMap.put(type, defense.get(type));
        }
        ArmorMaterial material =  new ArmorMaterial(durabilityMultiplier, enumMap, enchantability, equipSound, toughness, knockbackResistance, repairTag, EquipmentAssetKeys.register(name));
        return new ExtendedArmorMaterial(material, durabilityMultiplier, tier);
    }

    enum Tiers {
        CLOTHING,
        BASIC,
        LIGHT,
        MEDIUM,
        STURDY,
        HEAVY,
        ;
    }
}
