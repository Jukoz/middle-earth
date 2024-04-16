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
/*
    HOBBIT_SHIRRIFF_HAT("hobbit_shirriff_hat", 1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER.value(),
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shire"), null),

    GAMBESON("gambeson", 2, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    CLOAK("cloak", 1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    TUNIC_CLOAK("tunic_cloak", 1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    FUR_CLOAK("fur_cloak", 1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    CHAINMAIL_FUR_CLOAK("chainmail_fur_cloak", 3, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    RUSTY_KETTLE_HAT("rusty_kettle_hat", 2, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    KETTLE_HAT("kettle_hat", 2, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null),

    NAZGUL_CLOAK("nazgul_cloak", 3, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> Ingredient.ofItems(ModResourceItems.MORGUL_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null),

    GONDORIAN_MAIL_ARMOR("gondorian_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null),

    GONDORIAN_REINFORCED_MAIL_ARMOR("gondorian_reinforced_mail", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null),

    GONDORIAN_PLATE_ARMOR("gondorian_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null),

    GONDORIAN_CITADEL_GUARD_ARMOR("gondorian_citadel_guard", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null),

    GONDORIAN_FOUNTAIN_GUARD_ARMOR("gondorian_fountain_guard", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null),

    ROHIRRIC_MAIL_ARMOR("rohirric_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null),

    ROHIRRIC_SCALE_ARMOR("rohirric_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null),

    ROHIRRIC_ROYAL_GUARD_ARMOR("rohirric_royal_guard", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null),

    EORLING_MARSHALL_ARMOR("eorling_marshall", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null),

    HORSE_LORD_ARMOR("horse_lord", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null),

    DWARVEN_PARTISAN("dwarven_partisan", 2, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> Ingredient.ofItems(Items.LEATHER), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dwarven")),

    EREBOR_MAIL_ARMOR("erebor_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")),

    EREBOR_SCALE_ARMOR("erebor_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")),

    EREBOR_PLATE_ARMOR("erebor_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")),

    EREBOR_COMMANDER_ARMOR("erebor_commander", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor")),

    LORIEN_MAIL_ARMOR("lorien_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null),

    LORIEN_SCALE_ARMOR("lorien_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null),

    LORIEN_PLATE_ARMOR("lorien_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null),

    LORIEN_COMMANDER_ARMOR("lorien_commander", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null),

    MORDOR_ORC_MAIL_ARMOR("mordor_orc_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ORC_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null),

    MORDOR_BLACK_URUK_SCALE_ARMOR("mordor_black_uruk_scale",  4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null),

    MORDOR_BLACK_URUK_PLATE_ARMOR("mordor_black_uruk_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null),

    MORDOR_BLACK_URUK_COMMANDER_ARMOR("mordor_black_uruk_commander", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null),

    MISTY_GOBLIN_MAIL_ARMOR("misty_goblin_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ORC_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), null),

    MISTY_HOBGOBLIN_SCALE_ARMOR("misty_hobgoblin_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gundabad")),

    MISTY_HOBGOBLIN_PLATE_ARMOR("misty_hobgoblin_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gundabad")),
*/
    public static final ExtendedArmorMaterial HOBBIT_SHERRIF_HAT = registerArmor("hobbit_shirriff_hat", 1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> Ingredient.ofItems(Items.LEATHER),
            List.of(new ArmorMaterial.Layer(new Identifier("hobbit_shirriff_hat"), "", false)), new ArmorMaterial.Layer(new Identifier("hobbit_shirriff_hat"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shire"), null);

    public static final ExtendedArmorMaterial GAMBESON = registerArmor("gambeson", 2, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> Ingredient.ofItems(Items.LEATHER),
            List.of(new ArmorMaterial.Layer(new Identifier("gambeson"), "", true), new ArmorMaterial.Layer(new Identifier("gambeson"), "_overlay", false)), new ArmorMaterial.Layer(new Identifier("gambeson"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null);

    public static final ExtendedArmorMaterial CLOAK = registerArmor("cloak", 2, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> Ingredient.ofItems(Items.LEATHER),
            List.of(new ArmorMaterial.Layer(new Identifier("cloak"), "", true), new ArmorMaterial.Layer(new Identifier("cloak"), "_overlay", false)), new ArmorMaterial.Layer(new Identifier("cloak"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null);

    public static final ExtendedArmorMaterial TUNIC_CLOAK = registerArmor("tunic_cloak", 2, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, () -> Ingredient.ofItems(Items.LEATHER),
            List.of(new ArmorMaterial.Layer(new Identifier("tunic_cloak"), "", true), new ArmorMaterial.Layer(new Identifier("tunic_cloak"), "_overlay", false)), new ArmorMaterial.Layer(new Identifier("tunic_cloak"), "", false),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null);

    public static final ExtendedArmorMaterial FUR_CLOAK = registerArmor("fur_cloak", 1, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> Ingredient.ofItems(Items.IRON_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null);

    public static final ExtendedArmorMaterial CHAINMAIL_FUR_CLOAK = registerArmor("chainmail_fur_cloak", 3, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,
            () -> Ingredient.ofItems(Items.IRON_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null);

    public static final ExtendedArmorMaterial RUSTY_KETTLE_HAT = registerArmor("rusty_kettle_hat", 2, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null);

    public static final ExtendedArmorMaterial KETTLE_HAT = registerArmor("kettle_hat", 2, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), null);

    public static final ExtendedArmorMaterial NAZGUL_CLOAK = registerArmor("nazgul_cloak", 3, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> Ingredient.ofItems(ModResourceItems.MORGUL_STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null);

    public static final ExtendedArmorMaterial GONDORIAN_MAIL_ARMOR = registerArmor("gondorian_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null);

    public static final ExtendedArmorMaterial GONDORIAN_REINFORCED_MAIL_ARMOR = registerArmor("gondorian_reinforced_mail", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null);

    public static final ExtendedArmorMaterial GONDORIAN_PLATE_ARMOR = registerArmor("gondorian_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null);

    public static final ExtendedArmorMaterial GONDORIAN_CITADEL_GUARD_ARMOR = registerArmor("gondorian_citadel_guard", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null);

    public static final ExtendedArmorMaterial GONDORIAN_FOUNTAIN_GUARD_ARMOR = registerArmor("gondorian_fountain_guard", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor"), null);

    public static final ExtendedArmorMaterial ROHIRRIC_MAIL_ARMOR = registerArmor("rohirric_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null);

    public static final ExtendedArmorMaterial ROHIRRIC_SCALE_ARMOR = registerArmor("rohirric_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null);

    public static final ExtendedArmorMaterial ROHIRRIC_ROYAL_GUARD_ARMOR = registerArmor("rohirric_royal_guard", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null);

    public static final ExtendedArmorMaterial EORLING_MARSHALL_ARMOR = registerArmor("eorling_marshall", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null);

    public static final ExtendedArmorMaterial HORSE_LORD_ARMOR = registerArmor("horse_lord", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan"), null);

    public static final ExtendedArmorMaterial DWARVEN_PARTISAN = registerArmor("dwarven_partisan", 2, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
            () -> Ingredient.ofItems(Items.LEATHER),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".dwarven"));

    public static final ExtendedArmorMaterial EREBOR_MAIL_ARMOR = registerArmor("erebor_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"));

    public static final ExtendedArmorMaterial EREBOR_SCALE_ARMOR = registerArmor("erebor_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"));

    public static final ExtendedArmorMaterial EREBOR_PLATE_ARMOR = registerArmor("erebor_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"));

    public static final ExtendedArmorMaterial EREBOR_COMMANDER_ARMOR = registerArmor("erebor_commander", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.DWARVEN_STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".erebor"));

    public static final ExtendedArmorMaterial LORIEN_MAIL_ARMOR = registerArmor("lorien_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(Items.IRON_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null);

    public static final ExtendedArmorMaterial LORIEN_SCALE_ARMOR = registerArmor("lorien_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null);

    public static final ExtendedArmorMaterial LORIEN_PLATE_ARMOR = registerArmor("lorien_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null);

    public static final ExtendedArmorMaterial LORIEN_COMMANDER_ARMOR = registerArmor("lorien_commander", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ELVEN_STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien"), null);

    public static final ExtendedArmorMaterial MORDOR_ORC_MAIL_ARMOR = registerArmor("mordor_orc_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ORC_STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null);

    public static final ExtendedArmorMaterial MORDOR_BLACK_URUK_SCALE_ARMOR = registerArmor("mordor_black_uruk_scale",  4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null);

    public static final ExtendedArmorMaterial MORDOR_BLACK_URUK_PLATE_ARMOR = registerArmor("mordor_black_uruk_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null);

    public static final ExtendedArmorMaterial MORDOR_BLACK_URUK_COMMANDER_ARMOR = registerArmor("mordor_black_uruk_commander", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor"), null);

    public static final ExtendedArmorMaterial MISTY_GOBLIN_MAIL_ARMOR = registerArmor("misty_goblin_mail", 3, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.ORC_STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), null);

    public static final ExtendedArmorMaterial MISTY_HOBGOBLIN_SCALE_ARMOR = registerArmor("misty_hobgoblin_scale", 4, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gundabad"));

    public static final ExtendedArmorMaterial MISTY_HOBGOBLIN_PLATE_ARMOR = registerArmor("misty_hobgoblin_plate", 5, SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(ModResourceItems.URUK_STEEL_INGOT),
            Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs"), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gundabad"));

    private static ExtendedArmorMaterial registerArmor(String id, int tier, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredient, List<ArmorMaterial.Layer> layers,
                                                       ArmorMaterial.Layer layer, MutableText faction, MutableText subFaction) {
        EnumMap<ArmorItem.Type, Integer> map = null;
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
        return new ExtendedArmorMaterial(Registry.registerReference(Registries.ARMOR_MATERIAL, new Identifier(id), material), durabilityMultiplier, layer, faction, subFaction, tier);
    }
}
