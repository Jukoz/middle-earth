package net.jukoz.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.datageneration.content.models.SimpleItemModel;
import net.jukoz.me.item.items.*;
import net.jukoz.me.item.utils.ModArmorMaterials;
import net.jukoz.me.item.utils.ModItemGroups;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEquipmentItems {

    public static final Item GAMBESON = registerItem("gambeson",
            new CustomChestplateItem(ModArmorMaterials.GAMBESON, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), false, true));

    // Dyeable cloaks
    public static final Item CLOAK = registerItem("cloak",
            new DyeableArmorItem(ModArmorMaterials.CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item CLOAK_HOOD = registerItem("cloak_hood",
            new DyeableArmorItem(ModArmorMaterials.CLOAK, ArmorItem.Type.HELMET, new FabricItemSettings()));
    // Tunic cloak
    public static final Item TUNIC_CLOAK = registerItem("tunic_cloak",
            new DyeableArmorItem(ModArmorMaterials.TUNIC_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));

    // Fur cloaks
    public static final Item FUR_CLOAK = registerGeneratedItem("fur_cloak",
            new CustomChestplateItem(ModArmorMaterials.FUR_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), true, false));
    public static final Item FUR_CLOAK_HOOD = registerGeneratedItem("fur_cloak_hood",
            new CustomHelmetItem(ModArmorMaterials.FUR_CLOAK, ArmorItem.Type.HELMET, new FabricItemSettings(), true));

    // Chainmail fur cloaks
    public static final Item CHAINMAIL_FUR_CLOAK = registerGeneratedItem("chainmail_fur_cloak",
            new CustomChestplateItem(ModArmorMaterials.CHAINMAIL_FUR_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), true, false));

    public static final Item RUSTY_KETTLE_HAT = registerGeneratedItem("rusty_kettle_hat",
            new CustomHelmetItem(ModArmorMaterials.RUSTY_KETTLE_HAT, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item KETTLE_HAT = registerGeneratedItem("kettle_hat",
            new CustomHelmetItem(ModArmorMaterials.KETTLE_HAT, ArmorItem.Type.HELMET, new FabricItemSettings(),false));

    public static final Item HOBBIT_SHIRRIFF_HAT_GREEN = registerGeneratedItem("hobbit_shirriff_hat_green",
            new CustomHelmetItem(ModArmorMaterials.HOBBIT_SHIRRIFF_HAT, ArmorItem.Type.HELMET, new FabricItemSettings(), false));
    public static final Item HOBBIT_SHIRRIFF_HAT_BROWN = registerGeneratedItem("hobbit_shirriff_hat_brown",
            new CustomHelmetItem(ModArmorMaterials.HOBBIT_SHIRRIFF_HAT, ArmorItem.Type.HELMET, new FabricItemSettings(), false));

    public static final Item GONDORIAN_MAIL_HELMET = registerGeneratedItem("gondorian_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item GONDORIAN_MAIL_CHESTPLATE = registerGeneratedItem("gondorian_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), true, false));
    public static final Item GONDORIAN_MAIL_LEGGINGS = registerGeneratedItem("gondorian_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item GONDORIAN_MAIL_BOOTS = registerGeneratedItem("gondorian_mail_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item GONDORIAN_REINFORCED_MAIL_HELMET = registerGeneratedItem("gondorian_reinforced_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_REINFORCED_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item GONDORIAN_REINFORCED_MAIL_CHESTPLATE = registerGeneratedItem("gondorian_reinforced_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_REINFORCED_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), true, false));
    public static final Item GONDORIAN_REINFORCED_MAIL_LEGGINGS = registerGeneratedItem("gondorian_reinforced_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_REINFORCED_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item GONDORIAN_REINFORCED_MAIL_BOOTS = registerGeneratedItem("gondorian_reinforced_mail_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_REINFORCED_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item GONDORIAN_PLATE_HELMET = registerGeneratedItem("gondorian_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item GONDORIAN_PLATE_CHESTPLATE = registerGeneratedItem("gondorian_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), false, false));
    public static final Item GONDORIAN_PLATE_LEGGINGS = registerGeneratedItem("gondorian_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item GONDORIAN_PLATE_BOOTS = registerGeneratedItem("gondorian_plate_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_PLATE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item GONDORIAN_CITADEL_GUARD_HELMET = registerGeneratedItem("gondorian_citadel_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_CITADEL_GUARD_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item GONDORIAN_CITADEL_GUARD_CHESTPLATE = registerGeneratedItem("gondorian_citadel_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_CITADEL_GUARD_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), true, false));
    public static final Item GONDORIAN_CITADEL_GUARD_LEGGINGS = registerGeneratedItem("gondorian_citadel_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_CITADEL_GUARD_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item GONDORIAN_CITADEL_GUARD_BOOTS = registerGeneratedItem("gondorian_citadel_guard_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_CITADEL_GUARD_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    
    public static final Item GONDORIAN_FOUNTAIN_GUARD_HELMET = registerGeneratedItem("gondorian_fountain_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_FOUNTAIN_GUARD_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE = registerGeneratedItem("gondorian_fountain_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_FOUNTAIN_GUARD_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), true, false));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_LEGGINGS = registerGeneratedItem("gondorian_fountain_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_FOUNTAIN_GUARD_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_BOOTS = registerGeneratedItem("gondorian_fountain_guard_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_FOUNTAIN_GUARD_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item GONDORIAN_SHIELD = registerItem("gondorian_shield",
            new ShieldItem(new FabricItemSettings().maxCount(1).maxDamage(336)));

    public static final Item ROHIRRIC_MAIL_HELMET = registerGeneratedItem("rohirric_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.ROHIRRIC_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item ROHIRRIC_MAIL_CHESTPLATE = registerGeneratedItem("rohirric_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), true, false));
    public static final Item ROHIRRIC_MAIL_LEGGINGS = registerGeneratedItem("rohirric_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.ROHIRRIC_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item ROHIRRIC_MAIL_BOOTS = registerGeneratedItem("rohirric_mail_boots",
            new CustomBootsItem(ModArmorMaterials.ROHIRRIC_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item ROHIRRIC_SCALE_HELMET = registerGeneratedItem("rohirric_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.ROHIRRIC_SCALE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item ROHIRRIC_SCALE_CHESTPLATE = registerGeneratedItem("rohirric_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), true, false));
    public static final Item ROHIRRIC_SCALE_LEGGINGS = registerGeneratedItem("rohirric_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.ROHIRRIC_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item ROHIRRIC_SCALE_BOOTS = registerGeneratedItem("rohirric_scale_boots",
            new CustomBootsItem(ModArmorMaterials.ROHIRRIC_SCALE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item ROHIRRIC_PLATE_HELMET = registerGeneratedItem("rohirric_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.ROHIRRIC_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item ROHIRRIC_PLATE_CHESTPLATE = registerGeneratedItem("rohirric_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), false, false));
    public static final Item ROHIRRIC_PLATE_LEGGINGS = registerGeneratedItem("rohirric_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.ROHIRRIC_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item ROHIRRIC_PLATE_BOOTS = registerGeneratedItem("rohirric_plate_boots",
            new CustomBootsItem(ModArmorMaterials.ROHIRRIC_PLATE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item ROHIRRIC_SHIELD = registerItem("rohirric_shield",
            new ShieldItem(new FabricItemSettings().maxCount(1).maxDamage(336)));

    public static final Item LONGBEARD_MAIL_HELMET = registerGeneratedItem("longbeard_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.LONGBEARD_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item LONGBEARD_MAIL_CHESTPLATE = registerGeneratedItem("longbeard_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LONGBEARD_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false, false));
    public static final Item LONGBEARD_MAIL_LEGGINGS = registerGeneratedItem("longbeard_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LONGBEARD_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LONGBEARD_MAIL_BOOTS = registerGeneratedItem("longbeard_mail_boots",
            new CustomBootsItem(ModArmorMaterials.LONGBEARD_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LONGBEARD_SCALE_HELMET = registerGeneratedItem("longbeard_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.LONGBEARD_SCALE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item LONGBEARD_SCALE_CHESTPLATE = registerGeneratedItem("longbeard_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LONGBEARD_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false, false));
    public static final Item LONGBEARD_SCALE_LEGGINGS = registerGeneratedItem("longbeard_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LONGBEARD_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LONGBEARD_SCALE_BOOTS = registerGeneratedItem("longbeard_scale_boots",
            new CustomBootsItem(ModArmorMaterials.LONGBEARD_SCALE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LONGBEARD_PLATE_HELMET = registerGeneratedItem("longbeard_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.LONGBEARD_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item LONGBEARD_PLATE_CHESTPLATE = registerGeneratedItem("longbeard_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LONGBEARD_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false, false));
    public static final Item LONGBEARD_PLATE_LEGGINGS = registerGeneratedItem("longbeard_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LONGBEARD_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LONGBEARD_PLATE_BOOTS = registerGeneratedItem("longbeard_plate_boots",
            new CustomBootsItem(ModArmorMaterials.LONGBEARD_PLATE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LONGBEARD_COMMANDER_HELMET = registerGeneratedItem("longbeard_commander_helmet",
            new CustomHelmetItem(ModArmorMaterials.LONGBEARD_COMMANDER_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item LONGBEARD_COMMANDER_CHESTPLATE = registerGeneratedItem("longbeard_commander_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LONGBEARD_COMMANDER_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false, false));
    public static final Item LONGBEARD_COMMANDER_LEGGINGS = registerGeneratedItem("longbeard_commander_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LONGBEARD_COMMANDER_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LONGBEARD_COMMANDER_BOOTS = registerGeneratedItem("longbeard_commander_boots",
            new CustomBootsItem(ModArmorMaterials.LONGBEARD_COMMANDER_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LONGBEARD_SHIELD = registerItem("longbeard_shield",
            new ShieldItem(new FabricItemSettings().maxCount(1).maxDamage(336)));

    public static final Item LORIEN_MAIL_HELMET = registerGeneratedItem("lorien_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.LORIEN_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item LORIEN_MAIL_CHESTPLATE = registerGeneratedItem("lorien_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LORIEN_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false, false));
    public static final Item LORIEN_MAIL_LEGGINGS = registerGeneratedItem("lorien_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LORIEN_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LORIEN_MAIL_BOOTS = registerGeneratedItem("lorien_mail_boots",
            new CustomBootsItem(ModArmorMaterials.LORIEN_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LORIEN_SCALE_HELMET = registerGeneratedItem("lorien_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.LORIEN_SCALE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item LORIEN_SCALE_CHESTPLATE = registerGeneratedItem("lorien_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LORIEN_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false, false));
    public static final Item LORIEN_SCALE_LEGGINGS = registerGeneratedItem("lorien_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LORIEN_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LORIEN_SCALE_BOOTS = registerGeneratedItem("lorien_scale_boots",
            new CustomBootsItem(ModArmorMaterials.LORIEN_SCALE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LORIEN_PLATE_HELMET = registerGeneratedItem("lorien_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.LORIEN_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),true));
    public static final Item LORIEN_PLATE_CHESTPLATE = registerGeneratedItem("lorien_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LORIEN_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), true, false));
    public static final Item LORIEN_PLATE_LEGGINGS = registerGeneratedItem("lorien_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LORIEN_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LORIEN_PLATE_BOOTS = registerGeneratedItem("lorien_plate_boots",
            new CustomBootsItem(ModArmorMaterials.LORIEN_PLATE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LORIEN_SHIELD = registerItem("lorien_shield",
            new ShieldItem(new FabricItemSettings().maxCount(1).maxDamage(336)));
    
    public static final Item MORDOR_ORC_MAIL_HELMET = registerGeneratedItem("mordor_orc_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.MORDOR_ORC_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item MORDOR_ORC_MAIL_CHESTPLATE = registerGeneratedItem("mordor_orc_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MORDOR_ORC_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false, false));
    public static final Item MORDOR_ORC_MAIL_LEGGINGS = registerGeneratedItem("mordor_orc_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MORDOR_ORC_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MORDOR_ORC_MAIL_BOOTS = registerGeneratedItem("mordor_orc_mail_boots",
            new CustomBootsItem(ModArmorMaterials.MORDOR_ORC_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MORDOR_BLACK_URUK_SCALE_HELMET = registerGeneratedItem("mordor_black_uruk_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.MORDOR_BLACK_URUK_SCALE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item MORDOR_BLACK_URUK_SCALE_CHESTPLATE = registerGeneratedItem("mordor_black_uruk_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MORDOR_BLACK_URUK_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false, false));
    public static final Item MORDOR_BLACK_URUK_SCALE_LEGGINGS = registerGeneratedItem("mordor_black_uruk_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MORDOR_BLACK_URUK_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MORDOR_BLACK_URUK_SCALE_BOOTS = registerGeneratedItem("mordor_black_uruk_scale_boots",
            new CustomBootsItem(ModArmorMaterials.MORDOR_BLACK_URUK_SCALE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MORDOR_BLACK_URUK_PLATE_HELMET = registerGeneratedItem("mordor_black_uruk_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item MORDOR_BLACK_URUK_PLATE_CHESTPLATE = registerGeneratedItem("mordor_black_uruk_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false, false));
    public static final Item MORDOR_BLACK_URUK_PLATE_LEGGINGS = registerGeneratedItem("mordor_black_uruk_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MORDOR_BLACK_URUK_PLATE_BOOTS = registerGeneratedItem("mordor_black_uruk_plate_boots",
            new CustomBootsItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MORDOR_BLACK_URUK_COMMANDER_HELMET = registerGeneratedItem("mordor_black_uruk_commander_helmet",
            new CustomHelmetItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));

    public static final Item MORDOR_SHIELD = registerItem("mordor_shield",
            new ShieldItem(new FabricItemSettings().maxCount(1).maxDamage(336)));

    public static final Item NAZGUL_CLOAK_HOOD = registerGeneratedItem("nazgul_cloak_hood",
            new CustomHelmetItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.HELMET, new FabricItemSettings(),true));
    public static final Item NAZGUL_CLOAK = registerGeneratedItem("nazgul_cloak",
            new CustomChestplateItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),true, false));
    public static final Item NAZGUL_PANTS = registerGeneratedItem("nazgul_pants",
            new CustomLeggingsItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item NAZGUL_BOOTS = registerGeneratedItem("nazgul_boots",
            new CustomBootsItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MISTY_GOBLIN_MAIL_HELMET = registerGeneratedItem("misty_goblin_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.MISTY_GOBLIN_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item MISTY_GOBLIN_MAIL_CHESTPLATE = registerGeneratedItem("misty_goblin_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MISTY_GOBLIN_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false, false));
    public static final Item MISTY_GOBLIN_MAIL_LEGGINGS = registerGeneratedItem("misty_goblin_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MISTY_GOBLIN_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MISTY_GOBLIN_MAIL_BOOTS = registerGeneratedItem("misty_goblin_mail_boots",
            new CustomBootsItem(ModArmorMaterials.MISTY_GOBLIN_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MISTY_HOBGOBLIN_SCALE_HELMET = registerGeneratedItem("misty_hobgoblin_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.MISTY_HOBGOBLIN_SCALE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item MISTY_HOBGOBLIN_SCALE_CHESTPLATE = registerGeneratedItem("misty_hobgoblin_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MISTY_HOBGOBLIN_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false, false));
    public static final Item MISTY_HOBGOBLIN_SCALE_LEGGINGS = registerGeneratedItem("misty_hobgoblin_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MISTY_HOBGOBLIN_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MISTY_HOBGOBLIN_SCALE_BOOTS = registerGeneratedItem("misty_hobgoblin_scale_boots",
            new CustomBootsItem(ModArmorMaterials.MISTY_HOBGOBLIN_SCALE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MISTY_HOBGOBLIN_PLATE_HELMET = registerGeneratedItem("misty_hobgoblin_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item MISTY_HOBGOBLIN_PLATE_CHESTPLATE = registerGeneratedItem("misty_hobgoblin_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false, false));
    public static final Item MISTY_HOBGOBLIN_PLATE_LEGGINGS = registerGeneratedItem("misty_hobgoblin_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MISTY_HOBGOBLIN_PLATE_BOOTS = registerGeneratedItem("misty_hobgoblin_plate_boots",
            new CustomBootsItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MISTY_HOBGOBLIN_COMMANDER_CHESTPLATE = registerGeneratedItem("misty_hobgoblin_commander_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false, false));

    public static final Item MISTY_MOUNTAINS_SHIELD = registerItem("misty_mountains_shield",
            new ShieldItem(new FabricItemSettings().maxCount(1).maxDamage(336)));

    public static final Item STEEL_TROLL_ARMOR = registerGeneratedItem("steel_troll_armor",
            new TrollArmorItem(10, "steel", new FabricItemSettings().maxCount(1)));

    public static final Item ROHIRRIC_HORSE_ARMOR = registerGeneratedItem("rohirric_horse_armor",
            new HorseArmorItem(10, "rohirric", new FabricItemSettings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerGeneratedItem(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        SimpleItemModel.items.add(item);
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.debug("Registering Mod Equipment Items for " + MiddleEarth.MOD_ID);
    }
}
