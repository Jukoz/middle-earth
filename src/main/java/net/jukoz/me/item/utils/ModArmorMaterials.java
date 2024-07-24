package net.jukoz.me.item.utils;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {

    public static final ExtendedArmorMaterial HOBBIT_SHERRIF_HAT = registerArmor("hobbit_shirriff_hat", 1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> Ingredient.ofItems(Items.LEATHER),
            List.of(new ArmorMaterial.Layer(Identifier.of("hobbit_shirriff_hat"), "", false)), new ArmorMaterial.Layer(Identifier.of("hobbit_shirriff_hat"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shire"), null);

    public static final ExtendedArmorMaterial GAMBESON = registerArmor("gambeson", 2, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> Ingredient.ofItems(Items.LEATHER),
            List.of(new ArmorMaterial.Layer(Identifier.of("gambeson"), "", true), new ArmorMaterial.Layer(Identifier.of("gambeson"), "_overlay", false)), new ArmorMaterial.Layer(Identifier.of("gambeson"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null);

    public static final ExtendedArmorMaterial CLOAK = registerArmor("cloak", 2, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> Ingredient.ofItems(Items.LEATHER),
            List.of(new ArmorMaterial.Layer(Identifier.of("cloak"), "", true), new ArmorMaterial.Layer(Identifier.of("cloak"), "_overlay", false)), new ArmorMaterial.Layer(Identifier.of("cloak"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null);

    public static final ExtendedArmorMaterial TUNIC_CLOAK = registerArmor("tunic_cloak", 2, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> Ingredient.ofItems(Items.LEATHER),
            List.of(new ArmorMaterial.Layer(Identifier.of("tunic_cloak"), "", true), new ArmorMaterial.Layer(Identifier.of("tunic_cloak"), "_overlay", false)), new ArmorMaterial.Layer(Identifier.of("tunic_cloak"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null);

    public static final ExtendedArmorMaterial FUR_CLOAK = registerArmor("fur_cloak", 1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> Ingredient.ofItems(Items.IRON_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("fur_cloak"), "", false)), new ArmorMaterial.Layer(Identifier.of("fur_cloak"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null);

    public static final ExtendedArmorMaterial CHAINMAIL_FUR_CLOAK = registerArmor("chainmail_fur_cloak", 3, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, () -> Ingredient.ofItems(Items.IRON_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("chainmail_fur_cloak"), "", false)), new ArmorMaterial.Layer(Identifier.of("chainmail_fur_cloak"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null);

    public static final ExtendedArmorMaterial RUSTY_KETTLE_HAT = registerArmor("rusty_kettle_hat", 2, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(Items.IRON_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("rusty_kettle_hat"), "", false)), new ArmorMaterial.Layer(Identifier.of("rusty_kettle_hat"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null);

    public static final ExtendedArmorMaterial KETTLE_HAT = registerArmor("kettle_hat", 2, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(Items.IRON_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("kettle_hat"), "", false)), new ArmorMaterial.Layer(Identifier.of("kettle_hat"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null);

    public static final ExtendedArmorMaterial NAZGUL_CLOAK = registerArmor("nazgul_cloak", 3, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> Ingredient.ofItems(ModResourceItems.MORGUL_STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("nazgul_cloak"), "", false)), new ArmorMaterial.Layer(Identifier.of("nazgul_cloak"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null);

    public static final ExtendedArmorMaterial GONDORIAN_MAIL_ARMOR = registerArmor("gondorian_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(Items.IRON_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("gondorian_mail"), "", false)), new ArmorMaterial.Layer(Identifier.of("gondorian_mail"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null);

    public static final ExtendedArmorMaterial GONDORIAN_REINFORCED_MAIL_ARMOR = registerArmor("gondorian_reinforced_mail", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("gondorian_reinforced_mail"), "", false)), new ArmorMaterial.Layer(Identifier.of("gondorian_reinforced_mail"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null);

    public static final ExtendedArmorMaterial GONDORIAN_PLATE_ARMOR = registerArmor("gondorian_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("gondorian_plate"), "", false)), new ArmorMaterial.Layer(Identifier.of("gondorian_plate"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null);

    public static final ExtendedArmorMaterial GONDORIAN_CITADEL_GUARD_ARMOR = registerArmor("gondorian_citadel_guard", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("gondorian_citadel_guard"), "", false)), new ArmorMaterial.Layer(Identifier.of("gondorian_citadel_guard"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null);

    public static final ExtendedArmorMaterial GONDORIAN_FOUNTAIN_GUARD_ARMOR = registerArmor("gondorian_fountain_guard", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("gondorian_fountain_guard"), "", false)), new ArmorMaterial.Layer(Identifier.of("gondorian_fountain_guard"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null);

    public static final ExtendedArmorMaterial ROHIRRIC_MAIL_ARMOR = registerArmor("rohirric_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(Items.IRON_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("rohirric_mail"), "", false)), new ArmorMaterial.Layer(Identifier.of("rohirric_mail"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null);

    public static final ExtendedArmorMaterial ROHIRRIC_SCALE_ARMOR = registerArmor("rohirric_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("rohirric_scale"), "", false)), new ArmorMaterial.Layer(Identifier.of("rohirric_scale"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null);

    public static final ExtendedArmorMaterial ROHIRRIC_ROYAL_GUARD_ARMOR = registerArmor("rohirric_royal_guard", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("rohirric_royal_guard"), "", false)), new ArmorMaterial.Layer(Identifier.of("rohirric_royal_guard"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null);

    public static final ExtendedArmorMaterial EORLING_MARSHAL_ARMOR = registerArmor("eorling_marshal", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("eorling_marshal"), "", false)), new ArmorMaterial.Layer(Identifier.of("eorling_marshal"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null);

    public static final ExtendedArmorMaterial HORSE_LORD_ARMOR = registerArmor("horse_lord", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("horse_lord"), "", false)), new ArmorMaterial.Layer(Identifier.of("horse_lord"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null);

    public static final ExtendedArmorMaterial DWARVEN_PARTISAN = registerArmor("dwarven_partisan", 2, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> Ingredient.ofItems(Items.LEATHER),
            List.of(new ArmorMaterial.Layer(Identifier.of("dwarven_partisan"), "", false)), new ArmorMaterial.Layer(Identifier.of("dwarven_partisan"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dwarven"));

    public static final ExtendedArmorMaterial EREBOR_MAIL_ARMOR = registerArmor("erebor_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(Items.IRON_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("erebor_mail"), "", false)), new ArmorMaterial.Layer(Identifier.of("erebor_mail"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"));

    public static final ExtendedArmorMaterial EREBOR_SCALE_ARMOR = registerArmor("erebor_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("erebor_scale"), "", false)), new ArmorMaterial.Layer(Identifier.of("erebor_scale"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"));

    public static final ExtendedArmorMaterial EREBOR_PLATE_ARMOR = registerArmor("erebor_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("erebor_plate"), "", false)), new ArmorMaterial.Layer(Identifier.of("erebor_plate"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"));

    public static final ExtendedArmorMaterial EREBOR_COMMANDER_ARMOR = registerArmor("erebor_commander", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("erebor_commander"), "", false)), new ArmorMaterial.Layer(Identifier.of("erebor_commander"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"));

    public static final ExtendedArmorMaterial LORIEN_MAIL_ARMOR = registerArmor("lorien_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(Items.IRON_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("lorien_mail"), "", false)), new ArmorMaterial.Layer(Identifier.of("lorien_mail"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null);

    public static final ExtendedArmorMaterial LORIEN_SCALE_ARMOR = registerArmor("lorien_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("lorien_scale"), "", false)), new ArmorMaterial.Layer(Identifier.of("lorien_scale"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null);

    public static final ExtendedArmorMaterial LORIEN_PLATE_ARMOR = registerArmor("lorien_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("lorien_plate"), "", false)), new ArmorMaterial.Layer(Identifier.of("lorien_plate"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null);

    public static final ExtendedArmorMaterial LORIEN_COMMANDER_ARMOR = registerArmor("lorien_commander", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("lorien_commander"), "", false)), new ArmorMaterial.Layer(Identifier.of("lorien_commander"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null);

    public static final ExtendedArmorMaterial MORDOR_ORC_MAIL_ARMOR = registerArmor("mordor_orc_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.SLAG_SCRAP),
            List.of(new ArmorMaterial.Layer(Identifier.of("mordor_orc_mail"), "", false)), new ArmorMaterial.Layer(Identifier.of("mordor_orc_mail"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null);

    public static final ExtendedArmorMaterial MORDOR_BLACK_URUK_SCALE_ARMOR = registerArmor("mordor_black_uruk_scale",  4, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("mordor_black_uruk_scale"), "", false)), new ArmorMaterial.Layer(Identifier.of("mordor_black_uruk_scale"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null);

    public static final ExtendedArmorMaterial MORDOR_BLACK_URUK_PLATE_ARMOR = registerArmor("mordor_black_uruk_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("mordor_black_uruk_plate"), "", false)), new ArmorMaterial.Layer(Identifier.of("mordor_black_uruk_plate"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null);

    public static final ExtendedArmorMaterial MORDOR_BLACK_URUK_COMMANDER_ARMOR = registerArmor("mordor_black_uruk_commander", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("mordor_black_uruk_commander"), "", false)), new ArmorMaterial.Layer(Identifier.of("mordor_black_uruk_commander"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null);

    public static final ExtendedArmorMaterial MISTY_GOBLIN_MAIL_ARMOR = registerArmor("misty_goblin_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.SLAG_SCRAP),
            List.of(new ArmorMaterial.Layer(Identifier.of("misty_goblin_mail"), "", false)), new ArmorMaterial.Layer(Identifier.of("misty_goblin_mail"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), null);

    public static final ExtendedArmorMaterial MISTY_HOBGOBLIN_SCALE_ARMOR = registerArmor("misty_hobgoblin_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("misty_hobgoblin_scale"), "", false)), new ArmorMaterial.Layer(Identifier.of("misty_hobgoblin_scale"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gundabad"));

    public static final ExtendedArmorMaterial MISTY_HOBGOBLIN_PLATE_ARMOR = registerArmor("misty_hobgoblin_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON, () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of("misty_hobgoblin_plate"), "", false)), new ArmorMaterial.Layer(Identifier.of("misty_hobgoblin_plate"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gundabad"));

    private static ExtendedArmorMaterial registerArmor(String id, int tier, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredient, List<ArmorMaterial.Layer> layers,
                                                       ArmorMaterial.Layer layer, MutableText faction, MutableText subFaction) {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        float toughness;
        float knockbackResistance;
        int enchantability;
        int durabilityMultiplier;
        switch (tier) {
            case 1 -> {
                map.put(ArmorItem.Type.BOOTS, 1);
                map.put(ArmorItem.Type.LEGGINGS, 2);
                map.put(ArmorItem.Type.CHESTPLATE, 3);
                map.put(ArmorItem.Type.HELMET, 1);
                map.put(ArmorItem.Type.BODY, 4);
                durabilityMultiplier = 7;
                toughness = 0.0f;
                knockbackResistance = 0.0f;
                enchantability = 10;
            }
            case 2 -> {
                map.put(ArmorItem.Type.BOOTS, 1);
                map.put(ArmorItem.Type.LEGGINGS, 3);
                map.put(ArmorItem.Type.CHESTPLATE, 4);
                map.put(ArmorItem.Type.HELMET, 1);
                map.put(ArmorItem.Type.BODY, 5);
                durabilityMultiplier = 11;
                toughness = 0.0f;
                knockbackResistance = 0.0f;
                enchantability = 10;
            }
            case 3 -> {
                map.put(ArmorItem.Type.BOOTS, 2);
                map.put(ArmorItem.Type.LEGGINGS, 4);
                map.put(ArmorItem.Type.CHESTPLATE, 5);
                map.put(ArmorItem.Type.HELMET, 2);
                map.put(ArmorItem.Type.BODY, 6);
                durabilityMultiplier = 15;
                toughness = 0.5f;
                knockbackResistance = 0.0f;
                enchantability = 10;
            }
            case 4 -> {
                map.put(ArmorItem.Type.BOOTS, 2);
                map.put(ArmorItem.Type.LEGGINGS, 5);
                map.put(ArmorItem.Type.CHESTPLATE, 6);
                map.put(ArmorItem.Type.HELMET, 5);
                map.put(ArmorItem.Type.BODY, 8);
                durabilityMultiplier = 25;
                toughness = 1.0f;
                knockbackResistance = 0.1f;
                enchantability = 10;

            }
            case 5 -> {
                map.put(ArmorItem.Type.BOOTS, 3);
                map.put(ArmorItem.Type.LEGGINGS, 6);
                map.put(ArmorItem.Type.CHESTPLATE, 7);
                map.put(ArmorItem.Type.HELMET, 3);
                map.put(ArmorItem.Type.BODY, 10);
                durabilityMultiplier = 35;
                toughness = 2.0f;
                knockbackResistance = 0.1f;
                enchantability = 10;
            }
            default -> {
                map.put(ArmorItem.Type.BOOTS, 1);
                map.put(ArmorItem.Type.LEGGINGS, 2);
                map.put(ArmorItem.Type.CHESTPLATE, 3);
                map.put(ArmorItem.Type.HELMET, 1);
                map.put(ArmorItem.Type.BODY, 3);
                durabilityMultiplier = 5;
                toughness = 0.0f;
                knockbackResistance = 0.0f;
                enchantability = 1;
            }
        }
        return register(id, map, durabilityMultiplier, enchantability, equipSound, toughness, knockbackResistance, repairIngredient, layers, layer, faction, subFaction, tier);
    }

    private static ExtendedArmorMaterial register(String id, EnumMap<ArmorItem.Type, Integer> defense,
                                                         int durabilityMultiplier, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance,
                                                         Supplier<Ingredient> repairIngredient, List<ArmorMaterial.Layer> layers,
                                                         ArmorMaterial.Layer layer, MutableText faction, MutableText subFaction, int tier) {
        EnumMap<ArmorItem.Type, Integer> enumMap = new EnumMap<ArmorItem.Type, Integer>(ArmorItem.Type.class);
        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            enumMap.put(type, defense.get(type));
        }
        ArmorMaterial material =  new ArmorMaterial(enumMap, enchantability, equipSound, repairIngredient, layers, toughness, knockbackResistance);
        return new ExtendedArmorMaterial(Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(id), material), durabilityMultiplier, layer, faction, subFaction, tier);
    }
}
