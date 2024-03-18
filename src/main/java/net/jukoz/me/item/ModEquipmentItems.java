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
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModEquipmentItems {

    public static final Item GAMBESON = registerItem("gambeson",
            new CustomChestplateItem(ModArmorMaterials.GAMBESON, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.DYEABLE)));

    // Dyeable cloaks
    public static final Item CLOAK_HOOD = registerItem("cloak_hood",
            new CustomHelmetItem(ModArmorMaterials.CLOAK, ArmorItem.Type.HELMET, new FabricItemSettings(), List.of(CustomHelmetItem.Customizations.HOOD)));
    public static final Item CLOAK = registerItem("cloak",
            new CustomChestplateItem(ModArmorMaterials.CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));

    // Tunic cloak
    public static final Item TUNIC_CLOAK = registerItem("tunic_cloak",
            new CustomChestplateItem(ModArmorMaterials.TUNIC_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));

    // Fur cloaks
    public static final Item FUR_CLOAK_HOOD = registerGeneratedItem("fur_cloak_hood",
            new CustomHelmetItem(ModArmorMaterials.FUR_CLOAK, ArmorItem.Type.HELMET, new FabricItemSettings(),  List.of(CustomHelmetItem.Customizations.HOOD)));
    public static final Item FUR_CLOAK = registerGeneratedItem("fur_cloak",
            new CustomChestplateItem(ModArmorMaterials.FUR_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));


    // Chainmail fur cloaks
    public static final Item CHAINMAIL_FUR_CLOAK = registerGeneratedItem("chainmail_fur_cloak",
            new CustomChestplateItem(ModArmorMaterials.CHAINMAIL_FUR_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));

    public static final Item RUSTY_KETTLE_HAT = registerGeneratedItem("rusty_kettle_hat",
            new CustomHelmetItem(ModArmorMaterials.RUSTY_KETTLE_HAT, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item KETTLE_HAT = registerGeneratedItem("kettle_hat",
            new CustomHelmetItem(ModArmorMaterials.KETTLE_HAT, ArmorItem.Type.HELMET, new FabricItemSettings()));

    public static final Item HOBBIT_SHIRRIFF_HAT_GREEN = registerGeneratedItem("hobbit_shirriff_hat_green",
            new CustomHelmetItem(ModArmorMaterials.HOBBIT_SHIRRIFF_HAT, ArmorItem.Type.HELMET, new FabricItemSettings(),  List.of(CustomHelmetItem.Customizations.FEATHER)));
    public static final Item HOBBIT_SHIRRIFF_HAT_BROWN = registerGeneratedItem("hobbit_shirriff_hat_brown",
            new CustomHelmetItem(ModArmorMaterials.HOBBIT_SHIRRIFF_HAT, ArmorItem.Type.HELMET, new FabricItemSettings(),  List.of(CustomHelmetItem.Customizations.FEATHER)));

    //GONDOR
    public static final Item GONDORIAN_MAIL_HELMET = registerGeneratedItem("gondorian_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item GONDORIAN_MAIL_CHESTPLATE = registerGeneratedItem("gondorian_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item GONDORIAN_MAIL_LEGGINGS = registerGeneratedItem("gondorian_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item GONDORIAN_MAIL_BOOTS = registerGeneratedItem("gondorian_mail_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item GONDORIAN_REINFORCED_MAIL_HELMET = registerGeneratedItem("gondorian_reinforced_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_REINFORCED_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item GONDORIAN_REINFORCED_MAIL_CHESTPLATE = registerGeneratedItem("gondorian_reinforced_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_REINFORCED_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item GONDORIAN_REINFORCED_MAIL_LEGGINGS = registerGeneratedItem("gondorian_reinforced_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_REINFORCED_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item GONDORIAN_REINFORCED_MAIL_BOOTS = registerGeneratedItem("gondorian_reinforced_mail_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_REINFORCED_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item GONDORIAN_PLATE_HELMET = registerGeneratedItem("gondorian_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item GONDORIAN_PLATE_CHESTPLATE = registerGeneratedItem("gondorian_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item GONDORIAN_PLATE_LEGGINGS = registerGeneratedItem("gondorian_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item GONDORIAN_PLATE_BOOTS = registerGeneratedItem("gondorian_plate_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_PLATE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item GONDORIAN_CITADEL_GUARD_HELMET = registerGeneratedItem("gondorian_citadel_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_CITADEL_GUARD_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item GONDORIAN_CITADEL_GUARD_CHESTPLATE = registerGeneratedItem("gondorian_citadel_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_CITADEL_GUARD_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));
    public static final Item GONDORIAN_CITADEL_GUARD_LEGGINGS = registerGeneratedItem("gondorian_citadel_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_CITADEL_GUARD_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item GONDORIAN_CITADEL_GUARD_BOOTS = registerGeneratedItem("gondorian_citadel_guard_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_CITADEL_GUARD_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    
    public static final Item GONDORIAN_FOUNTAIN_GUARD_HELMET = registerGeneratedItem("gondorian_fountain_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.GONDORIAN_FOUNTAIN_GUARD_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE = registerGeneratedItem("gondorian_fountain_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.GONDORIAN_FOUNTAIN_GUARD_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_LEGGINGS = registerGeneratedItem("gondorian_fountain_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.GONDORIAN_FOUNTAIN_GUARD_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item GONDORIAN_FOUNTAIN_GUARD_BOOTS = registerGeneratedItem("gondorian_fountain_guard_boots",
            new CustomBootsItem(ModArmorMaterials.GONDORIAN_FOUNTAIN_GUARD_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item GONDORIAN_SHIELD = registerItem("gondorian_shield",
            new CustomShieldItem(new FabricItemSettings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")));

    public static final Item GONDORIAN_HORSE_ARMOR = registerGeneratedItem("gondorian_horse_armor",
            new CustomHorseArmorItem(7, "gondorian", new FabricItemSettings().maxCount(1), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".gondor")));

    //ROHAN
    public static final Item ROHIRRIC_MAIL_HELMET = registerGeneratedItem("rohirric_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.ROHIRRIC_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item ROHIRRIC_MAIL_CHESTPLATE = registerGeneratedItem("rohirric_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));
    public static final Item ROHIRRIC_MAIL_LEGGINGS = registerGeneratedItem("rohirric_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.ROHIRRIC_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item ROHIRRIC_MAIL_BOOTS = registerGeneratedItem("rohirric_mail_boots",
            new CustomBootsItem(ModArmorMaterials.ROHIRRIC_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item ROHIRRIC_SCALE_HELMET = registerGeneratedItem("rohirric_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.ROHIRRIC_SCALE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item ROHIRRIC_SCALE_CHESTPLATE = registerGeneratedItem("rohirric_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));
    public static final Item ROHIRRIC_SCALE_LEGGINGS = registerGeneratedItem("rohirric_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.ROHIRRIC_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item ROHIRRIC_SCALE_BOOTS = registerGeneratedItem("rohirric_scale_boots",
            new CustomBootsItem(ModArmorMaterials.ROHIRRIC_SCALE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item ROHIRRIC_ROYAL_GUARD_HELMET = registerGeneratedItem("rohirric_royal_guard_helmet",
            new CustomHelmetItem(ModArmorMaterials.ROHIRRIC_ROYAL_GUARD_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item ROHIRRIC_ROYAL_GUARD_CHESTPLATE = registerGeneratedItem("rohirric_royal_guard_chestplate",
            new CustomChestplateItem(ModArmorMaterials.ROHIRRIC_ROYAL_GUARD_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));
    public static final Item ROHIRRIC_ROYAL_GUARD_LEGGINGS = registerGeneratedItem("rohirric_royal_guard_leggings",
            new CustomLeggingsItem(ModArmorMaterials.ROHIRRIC_ROYAL_GUARD_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item ROHIRRIC_ROYAL_GUARD_BOOTS = registerGeneratedItem("rohirric_royal_guard_boots",
            new CustomBootsItem(ModArmorMaterials.ROHIRRIC_ROYAL_GUARD_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item EORLING_MARSHALL_HELMET = registerGeneratedItem("eorling_marshall_helmet",
            new CustomHelmetItem(ModArmorMaterials.EORLING_MARSHALL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item EORLING_MARSHALL_CHESTPLATE = registerGeneratedItem("eorling_marshall_chestplate",
            new CustomChestplateItem(ModArmorMaterials.EORLING_MARSHALL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));
    public static final Item EORLING_MARSHALL_LEGGINGS = registerGeneratedItem("eorling_marshall_leggings",
            new CustomLeggingsItem(ModArmorMaterials.EORLING_MARSHALL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item EORLING_MARSHALL_BOOTS = registerGeneratedItem("eorling_marshall_boots",
            new CustomBootsItem(ModArmorMaterials.EORLING_MARSHALL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item HORSE_LORD_HELMET = registerGeneratedItem("horse_lord_helmet",
            new CustomHelmetItem(ModArmorMaterials.HORSE_LORD_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item HORSE_LORD_CHESTPLATE = registerGeneratedItem("horse_lord_chestplate",
            new CustomChestplateItem(ModArmorMaterials.HORSE_LORD_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));
    public static final Item HORSE_LORD_LEGGINGS = registerGeneratedItem("horse_lord_leggings",
            new CustomLeggingsItem(ModArmorMaterials.HORSE_LORD_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item HORSE_LORD_BOOTS = registerGeneratedItem("horse_lord_boots",
            new CustomBootsItem(ModArmorMaterials.HORSE_LORD_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item ROHIRRIC_SHIELD = registerItem("rohirric_shield",
            new CustomShieldItem(new FabricItemSettings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));

    public static final Item ROHIRRIC_HORSE_ARMOR = registerGeneratedItem("rohirric_horse_armor",
            new CustomHorseArmorItem(10, "rohirric", new FabricItemSettings().maxCount(1), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".rohan")));


    //DWARVES
    public static final Item DWARVEN_PARTISAN_CHESTPLATE = registerGeneratedItem("dwarven_partisan_chestplate",
            new CustomChestplateItem(ModArmorMaterials.DWARVEN_PARTISAN, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));
    public static final Item DWARVEN_PARTISAN_LEGGINGS = registerGeneratedItem("dwarven_partisan_leggings",
            new CustomLeggingsItem(ModArmorMaterials.DWARVEN_PARTISAN, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item DWARVEN_PARTISAN_BOOTS = registerGeneratedItem("dwarven_partisan_boots",
            new CustomBootsItem(ModArmorMaterials.DWARVEN_PARTISAN, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    //EREBOR
    public static final Item EREBOR_MAIL_HELMET = registerGeneratedItem("erebor_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.EREBOR_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item EREBOR_MAIL_CHESTPLATE = registerGeneratedItem("erebor_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.EREBOR_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));
    public static final Item EREBOR_MAIL_LEGGINGS = registerGeneratedItem("erebor_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.EREBOR_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item EREBOR_MAIL_BOOTS = registerGeneratedItem("erebor_mail_boots",
            new CustomBootsItem(ModArmorMaterials.EREBOR_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item EREBOR_SCALE_HELMET = registerGeneratedItem("erebor_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.EREBOR_SCALE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item EREBOR_SCALE_CHESTPLATE = registerGeneratedItem("erebor_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.EREBOR_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.POUCH)));
    public static final Item EREBOR_SCALE_LEGGINGS = registerGeneratedItem("erebor_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.EREBOR_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item EREBOR_SCALE_BOOTS = registerGeneratedItem("erebor_scale_boots",
            new CustomBootsItem(ModArmorMaterials.EREBOR_SCALE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item EREBOR_PLATE_HELMET = registerGeneratedItem("erebor_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.EREBOR_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item EREBOR_PLATE_CHESTPLATE = registerGeneratedItem("erebor_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.EREBOR_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.POUCH)));
    public static final Item EREBOR_PLATE_LEGGINGS = registerGeneratedItem("erebor_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.EREBOR_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item EREBOR_PLATE_BOOTS = registerGeneratedItem("erebor_plate_boots",
            new CustomBootsItem(ModArmorMaterials.EREBOR_PLATE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item EREBOR_COMMANDER_HELMET = registerGeneratedItem("erebor_commander_helmet",
            new CustomHelmetItem(ModArmorMaterials.EREBOR_COMMANDER_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item EREBOR_COMMANDER_CHESTPLATE = registerGeneratedItem("erebor_commander_chestplate",
            new CustomChestplateItem(ModArmorMaterials.EREBOR_COMMANDER_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));
    public static final Item EREBOR_COMMANDER_LEGGINGS = registerGeneratedItem("erebor_commander_leggings",
            new CustomLeggingsItem(ModArmorMaterials.EREBOR_COMMANDER_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item EREBOR_COMMANDER_BOOTS = registerGeneratedItem("erebor_commander_boots",
            new CustomBootsItem(ModArmorMaterials.EREBOR_COMMANDER_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LONGBEARD_SHIELD = registerItem("longbeard_shield",
            new CustomShieldItem(new FabricItemSettings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".longbeards")));

    public static final Item LORIEN_HORSE_ARMOR = registerGeneratedItem("lorien_horse_armor",
            new CustomHorseArmorItem(7, "lorien", new FabricItemSettings().maxCount(1), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")));

    //LOTHLORIEN
    public static final Item LORIEN_MAIL_HELMET = registerGeneratedItem("lorien_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.LORIEN_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(), List.of(CustomHelmetItem.Customizations.HOOD)));
    public static final Item LORIEN_MAIL_CHESTPLATE = registerGeneratedItem("lorien_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LORIEN_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));
    public static final Item LORIEN_MAIL_LEGGINGS = registerGeneratedItem("lorien_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LORIEN_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LORIEN_MAIL_BOOTS = registerGeneratedItem("lorien_mail_boots",
            new CustomBootsItem(ModArmorMaterials.LORIEN_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LORIEN_SCALE_HELMET = registerGeneratedItem("lorien_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.LORIEN_SCALE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(), List.of(CustomHelmetItem.Customizations.HOOD)));
    public static final Item LORIEN_SCALE_CHESTPLATE = registerGeneratedItem("lorien_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LORIEN_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));
    public static final Item LORIEN_SCALE_LEGGINGS = registerGeneratedItem("lorien_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LORIEN_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LORIEN_SCALE_BOOTS = registerGeneratedItem("lorien_scale_boots",
            new CustomBootsItem(ModArmorMaterials.LORIEN_SCALE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LORIEN_PLATE_HELMET = registerGeneratedItem("lorien_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.LORIEN_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(), List.of(CustomHelmetItem.Customizations.HOOD)));
    public static final Item LORIEN_PLATE_CHESTPLATE = registerGeneratedItem("lorien_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LORIEN_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));
    public static final Item LORIEN_PLATE_LEGGINGS = registerGeneratedItem("lorien_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LORIEN_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LORIEN_PLATE_BOOTS = registerGeneratedItem("lorien_plate_boots",
            new CustomBootsItem(ModArmorMaterials.LORIEN_PLATE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LORIEN_COMMANDER_HELMET = registerGeneratedItem("lorien_commander_helmet",
            new CustomHelmetItem(ModArmorMaterials.LORIEN_COMMANDER_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(), List.of(CustomHelmetItem.Customizations.HOOD)));
    public static final Item LORIEN_COMMANDER_CHESTPLATE = registerGeneratedItem("lorien_commander_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LORIEN_COMMANDER_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));
    public static final Item LORIEN_COMMANDER_LEGGINGS = registerGeneratedItem("lorien_commander_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LORIEN_COMMANDER_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LORIEN_COMMANDER_BOOTS = registerGeneratedItem("lorien_commander_boots",
            new CustomBootsItem(ModArmorMaterials.LORIEN_COMMANDER_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LORIEN_SHIELD = registerItem("lorien_shield",
            new CustomShieldItem(new FabricItemSettings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".lothlorien")));

    //MORDOR
    public static final Item MORDOR_ORC_MAIL_HELMET = registerGeneratedItem("mordor_orc_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.MORDOR_ORC_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item MORDOR_ORC_MAIL_CHESTPLATE = registerGeneratedItem("mordor_orc_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MORDOR_ORC_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item MORDOR_ORC_MAIL_LEGGINGS = registerGeneratedItem("mordor_orc_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MORDOR_ORC_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MORDOR_ORC_MAIL_BOOTS = registerGeneratedItem("mordor_orc_mail_boots",
            new CustomBootsItem(ModArmorMaterials.MORDOR_ORC_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MORDOR_BLACK_URUK_SCALE_HELMET = registerGeneratedItem("mordor_black_uruk_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.MORDOR_BLACK_URUK_SCALE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item MORDOR_BLACK_URUK_SCALE_CHESTPLATE = registerGeneratedItem("mordor_black_uruk_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MORDOR_BLACK_URUK_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item MORDOR_BLACK_URUK_SCALE_LEGGINGS = registerGeneratedItem("mordor_black_uruk_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MORDOR_BLACK_URUK_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MORDOR_BLACK_URUK_SCALE_BOOTS = registerGeneratedItem("mordor_black_uruk_scale_boots",
            new CustomBootsItem(ModArmorMaterials.MORDOR_BLACK_URUK_SCALE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MORDOR_BLACK_URUK_PLATE_HELMET = registerGeneratedItem("mordor_black_uruk_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item MORDOR_BLACK_URUK_PLATE_CHESTPLATE = registerGeneratedItem("mordor_black_uruk_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item MORDOR_BLACK_URUK_PLATE_LEGGINGS = registerGeneratedItem("mordor_black_uruk_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MORDOR_BLACK_URUK_PLATE_BOOTS = registerGeneratedItem("mordor_black_uruk_plate_boots",
            new CustomBootsItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MORDOR_BLACK_URUK_COMMANDER_HELMET = registerGeneratedItem("mordor_black_uruk_commander_helmet",
            new CustomHelmetItem(ModArmorMaterials.MORDOR_BLACK_URUK_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(), List.of(CustomHelmetItem.Customizations.SKULL)));

    public static final Item MORDOR_SHIELD = registerItem("mordor_shield",
            new CustomShieldItem(new FabricItemSettings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".mordor")));

    public static final Item NAZGUL_CLOAK_HOOD = registerGeneratedItem("nazgul_cloak_hood",
            new CustomHelmetItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.HELMET, new FabricItemSettings(),List.of(CustomHelmetItem.Customizations.HOOD)));
    public static final Item NAZGUL_CLOAK = registerGeneratedItem("nazgul_cloak",
            new CustomChestplateItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.CAPE)));
    public static final Item NAZGUL_PANTS = registerGeneratedItem("nazgul_pants",
            new CustomLeggingsItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item NAZGUL_BOOTS = registerGeneratedItem("nazgul_boots",
            new CustomBootsItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    //MISTY MOUNTAINS ORCS
    public static final Item MISTY_GOBLIN_MAIL_HELMET = registerGeneratedItem("misty_goblin_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.MISTY_GOBLIN_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item MISTY_GOBLIN_MAIL_CHESTPLATE = registerGeneratedItem("misty_goblin_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MISTY_GOBLIN_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item MISTY_GOBLIN_MAIL_LEGGINGS = registerGeneratedItem("misty_goblin_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MISTY_GOBLIN_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MISTY_GOBLIN_MAIL_BOOTS = registerGeneratedItem("misty_goblin_mail_boots",
            new CustomBootsItem(ModArmorMaterials.MISTY_GOBLIN_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MISTY_HOBGOBLIN_SCALE_HELMET = registerGeneratedItem("misty_hobgoblin_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.MISTY_HOBGOBLIN_SCALE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item MISTY_HOBGOBLIN_SCALE_CHESTPLATE = registerGeneratedItem("misty_hobgoblin_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MISTY_HOBGOBLIN_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item MISTY_HOBGOBLIN_SCALE_LEGGINGS = registerGeneratedItem("misty_hobgoblin_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MISTY_HOBGOBLIN_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MISTY_HOBGOBLIN_SCALE_BOOTS = registerGeneratedItem("misty_hobgoblin_scale_boots",
            new CustomBootsItem(ModArmorMaterials.MISTY_HOBGOBLIN_SCALE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MISTY_HOBGOBLIN_PLATE_HELMET = registerGeneratedItem("misty_hobgoblin_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item MISTY_HOBGOBLIN_PLATE_CHESTPLATE = registerGeneratedItem("misty_hobgoblin_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item MISTY_HOBGOBLIN_PLATE_LEGGINGS = registerGeneratedItem("misty_hobgoblin_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MISTY_HOBGOBLIN_PLATE_BOOTS = registerGeneratedItem("misty_hobgoblin_plate_boots",
            new CustomBootsItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MISTY_HOBGOBLIN_COMMANDER_HELMET = registerGeneratedItem("misty_hobgoblin_commander_helmet",
            new CustomChestplateItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item MISTY_HOBGOBLIN_COMMANDER_CHESTPLATE = registerGeneratedItem("misty_hobgoblin_commander_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MISTY_HOBGOBLIN_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), List.of(CustomChestplateItem.Customizations.IMPALED_SKULLS)));

    public static final Item MISTY_MOUNTAINS_SHIELD = registerItem("misty_mountains_shield",
            new CustomShieldItem(new FabricItemSettings().maxCount(1).maxDamage(450), Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".misty_orcs")));

    public static final Item STEEL_TROLL_ARMOR = registerGeneratedItem("steel_troll_armor",
            new TrollArmorItem(10, "steel", new FabricItemSettings().maxCount(1)));

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
