package net.sevenstars.middleearth.item.utils.armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.ModResourceItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.EnumMap;
import java.util.function.Supplier;

public class ModArmorMaterials {

    public static final ExtendedArmorMaterial STRAW_T1 = registerArmor("straw_t1", Tiers.BASIC, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ModResourceItems.STRAW);

    public static final ExtendedArmorMaterial WOOL_T1 = registerArmor("wool_t1", Tiers.CLOTHING, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ModResourceItems.FUR);

    public static final ExtendedArmorMaterial FUR_T0 = registerArmor("fur_t0", Tiers.CLOTHING, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ModResourceItems.FUR);
    public static final ExtendedArmorMaterial FUR_T1 = registerArmor("fur_t1", Tiers.BASIC, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ModResourceItems.FUR);

    public static final ExtendedArmorMaterial FABRIC_T0 = registerArmor("fabric_t0", Tiers.CLOTHING, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ModResourceItems.FABRIC);
    public static final ExtendedArmorMaterial FABRIC_T1 = registerArmor("fabric_t1", Tiers.BASIC, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ModResourceItems.FABRIC);

    public static final ExtendedArmorMaterial LEATHER_T1 = registerArmor("leather_t1", Tiers.BASIC, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, Items.LEATHER);
    public static final ExtendedArmorMaterial LEATHER_T2 = registerArmor("leather_t2", Tiers.LIGHT, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, Items.LEATHER);
    public static final ExtendedArmorMaterial LEATHER_T3 = registerArmor("leather_t3", Tiers.MEDIUM, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, Items.LEATHER);

    public static final ExtendedArmorMaterial IRON_T2 = registerArmor("iron_t2", Tiers.LIGHT, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, Items.IRON_INGOT);
    public static final ExtendedArmorMaterial IRON_T3 = registerArmor("iron_t3", Tiers.MEDIUM, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, Items.IRON_INGOT);

    public static final ExtendedArmorMaterial BRONZE_T1 = registerArmor("bronze_t1", Tiers.BASIC, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModResourceItems.BRONZE_INGOT);
    public static final ExtendedArmorMaterial BRONZE_T2 = registerArmor("bronze_t2", Tiers.LIGHT, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModResourceItems.BRONZE_INGOT);
    public static final ExtendedArmorMaterial BRONZE_T3 = registerArmor("bronze_t3", Tiers.MEDIUM, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModResourceItems.BRONZE_INGOT);
    public static final ExtendedArmorMaterial BRONZE_T4 = registerArmor("bronze_t4", Tiers.STURDY, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ModResourceItems.BRONZE_INGOT);

    public static final ExtendedArmorMaterial CRUDE_T2 = registerArmor("crude_t2", Tiers.LIGHT, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModResourceItems.CRUDE_INGOT);
    public static final ExtendedArmorMaterial CRUDE_T3 = registerArmor("crude_t3", Tiers.MEDIUM, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModResourceItems.CRUDE_INGOT);
    public static final ExtendedArmorMaterial CRUDE_T4 = registerArmor("crude_t4", Tiers.STURDY, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ModResourceItems.CRUDE_INGOT);

    public static final ExtendedArmorMaterial STEEL_T3 = registerArmor("steel_t3", Tiers.MEDIUM, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModResourceItems.STEEL_INGOT);
    public static final ExtendedArmorMaterial STEEL_T4 = registerArmor("steel_t4", Tiers.STURDY, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ModResourceItems.STEEL_INGOT);
    public static final ExtendedArmorMaterial STEEL_T5 = registerArmor("steel_t5", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, ModResourceItems.STEEL_INGOT);

    public static final ExtendedArmorMaterial DWARVEN_STEEL_T3 = registerArmor("dwarven_steel_t3", Tiers.MEDIUM, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModResourceItems.KHAZAD_STEEL_INGOT);
    public static final ExtendedArmorMaterial DWARVEN_STEEL_T4 = registerArmor("dwarven_steel_t4", Tiers.STURDY, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ModResourceItems.KHAZAD_STEEL_INGOT);
    public static final ExtendedArmorMaterial DWARVEN_STEEL_T5 = registerArmor("dwarven_steel_t5", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, ModResourceItems.KHAZAD_STEEL_INGOT);

    public static final ExtendedArmorMaterial ELVEN_STEEL_T3 = registerArmor("elven_steel_t3", Tiers.MEDIUM, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModResourceItems.EDHEL_STEEL_INGOT);
    public static final ExtendedArmorMaterial ELVEN_STEEL_T4 = registerArmor("elven_steel_t4", Tiers.STURDY, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ModResourceItems.EDHEL_STEEL_INGOT);
    public static final ExtendedArmorMaterial ELVEN_STEEL_T5 = registerArmor("elven_steel_t5", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, ModResourceItems.EDHEL_STEEL_INGOT);

    public static final ExtendedArmorMaterial BURZUM_STEEL_T3 = registerArmor("burzum_steel_t3", Tiers.MEDIUM, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModResourceItems.BURZUM_STEEL_INGOT);
    public static final ExtendedArmorMaterial BURZUM_STEEL_T4 = registerArmor("burzum_steel_t4", Tiers.STURDY, SoundEvents.ITEM_ARMOR_EQUIP_IRON, ModResourceItems.BURZUM_STEEL_INGOT);
    public static final ExtendedArmorMaterial BURZUM_STEEL_T5 = registerArmor("burzum_steel_t5", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, ModResourceItems.BURZUM_STEEL_INGOT);

    public static final ExtendedArmorMaterial LEATHER = registerArmor("leather", Tiers.LIGHT, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, Items.LEATHER);
    public static final ExtendedArmorMaterial MAIL = registerArmor("mail", Tiers.MEDIUM, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, Items.IRON_INGOT);
    public static final ExtendedArmorMaterial PLATE = registerArmor("plate", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModResourceItems.STEEL_INGOT);

    public static final ExtendedArmorMaterial GONDORIAN_HORSE_ARMOR = registerArmor("gondorian", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModResourceItems.STEEL_INGOT);
    public static final ExtendedArmorMaterial ROHIRRIC_HORSE_ARMOR = registerArmor("rohirric", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModResourceItems.STEEL_INGOT);
    public static final ExtendedArmorMaterial DALISH_HORSE_ARMOR = registerArmor("dalish", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModResourceItems.STEEL_INGOT);
    public static final ExtendedArmorMaterial LORIEN_HORSE_ARMOR = registerArmor("lorien", Tiers.HEAVY, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, ModResourceItems.STEEL_INGOT);


    private static ExtendedArmorMaterial registerArmor(String name, Tiers tier, RegistryEntry<SoundEvent> equipSound, Item repairIngredient) {
        EnumMap<EquipmentSlot, Integer> map = new EnumMap<>(EquipmentType.class);
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
        return register(name, map, durabilityMultiplier, enchantability, equipSound, toughness, knockbackResistance, () -> Ingredient.ofItems(repairIngredient), tier);
    }

    private static ExtendedArmorMaterial register(String name, EnumMap<ArmorItem.Type, Integer> defense,
                                                  int durabilityMultiplier, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance,
                                                  Supplier<Ingredient> repairIngredient, Tiers tier) {
        EnumMap<ArmorItem.Type, Integer> enumMap = new EnumMap<ArmorItem.Type, Integer>(EquipmentType.class);
        for (ArmorItem.Type type : EquipmentType.values()) {
            enumMap.put(type, defense.get(type));
        }
        ArmorMaterial material =  new ArmorMaterial(enumMap, enchantability, equipSound, repairIngredient, null, toughness, knockbackResistance);
        return new ExtendedArmorMaterial(Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(MiddleEarth.MOD_ID, name), material), durabilityMultiplier, tier);
    }

    private static RegistryEntry<ArmorMaterial> registerMountArmor(String id, Tiers tier, RegistryEntry<SoundEvent> equipSound, Item repairIngredient) {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(EquipmentType.class);
        float toughness = 0;
        float knockbackResistance = 0;
        int enchantability = 0;
        int durabilityMultiplier;
        switch (tier) {
            case LIGHT -> {
                map.put(EquipmentType.BOOTS, 1);
                map.put(EquipmentType.LEGGINGS, 3);
                map.put(EquipmentType.CHESTPLATE, 4);
                map.put(EquipmentType.HELMET, 1);
                map.put(EquipmentType.BODY, 4);
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
        }
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(MiddleEarth.MOD_ID, id), new ArmorMaterial(map, enchantability, equipSound, () -> Ingredient.ofItems(repairIngredient), null, toughness, knockbackResistance));
    }

    public enum Tiers {
        CLOTHING,
        BASIC,
        LIGHT,
        MEDIUM,
        STURDY,
        HEAVY,
        ;
    }
}
