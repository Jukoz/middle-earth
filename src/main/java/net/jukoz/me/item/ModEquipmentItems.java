package net.jukoz.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.datageneration.content.models.SimpleItemModel;
import net.jukoz.me.item.items.*;
import net.jukoz.me.item.utils.ModArmorMaterials;
import net.jukoz.me.item.utils.ModItemGroups;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.SaddleItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEquipmentItems {

    public static final Item GAMBESON = registerGeneratedItem("gambeson",
            new CustomChestplateItem(ModArmorMaterials.GAMBESON, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), false));

    // Dyeable cloaks
    public static final Item CLOAK = registerItem("cloak",
            new DyeableArmorItem(ModArmorMaterials.CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item CLOAK_HOOD = registerItem("cloak_hood",
            new DyeableArmorItem(ModArmorMaterials.CLOAK, ArmorItem.Type.HELMET, new FabricItemSettings()));
    // Tunic cloak
    public static final Item TUNIC_CLOAK = registerItem("tunic_cloak",
            new DyeableArmorItem(ModArmorMaterials.TUNIC_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));

    // Fur cloaks
    public static final Item FUR_CLOAK = registerItem("fur_cloak",
            new CustomChestplateItem(ModArmorMaterials.FUR_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), true));
    public static final Item FUR_CLOAK_HOOD = registerItem("fur_cloak_hood",
            new CustomChestplateItem(ModArmorMaterials.FUR_CLOAK, ArmorItem.Type.HELMET, new FabricItemSettings(), true));
    // Chainmail fur cloaks
    public static final Item CHAINMAIL_FUR_CLOAK = registerGeneratedItem("chainmail_fur_cloak",
            new CustomChestplateItem(ModArmorMaterials.CHAINMAIL_FUR_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), true));

    public static final Item NAZGUL_CLOAK_HOOD = registerGeneratedItem("nazgul_cloak_hood",
            new CustomHelmetItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.HELMET, new FabricItemSettings(),true));
    public static final Item NAZGUL_CLOAK = registerGeneratedItem("nazgul_cloak",
            new CustomChestplateItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),true));
    public static final Item NAZGUL_PANTS = registerGeneratedItem("nazgul_pants",
            new CustomLeggingsItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item NAZGUL_BOOTS = registerGeneratedItem("nazgul_boots",
            new CustomBootsItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item ROHAN_MAIL_HELMET = registerGeneratedItem("rohan_mail_helmet",
            new CustomChestplateItem(ModArmorMaterials.ROHAN_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item ROHAN_MAIL_CHESTPLATE = registerGeneratedItem("rohan_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.ROHAN_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), true));
    public static final Item ROHAN_MAIL_LEGGINGS = registerGeneratedItem("rohan_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.ROHAN_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item ROHAN_MAIL_BOOTS = registerGeneratedItem("rohan_mail_boots",
            new CustomBootsItem(ModArmorMaterials.ROHAN_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item ROHAN_SCALE_HELMET = registerGeneratedItem("rohan_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.ROHAN_SCALE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item ROHAN_SCALE_CHESTPLATE = registerGeneratedItem("rohan_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.ROHAN_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), true));
    public static final Item ROHAN_SCALE_LEGGINGS = registerGeneratedItem("rohan_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.ROHAN_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item ROHAN_SCALE_BOOTS = registerGeneratedItem("rohan_scale_boots",
            new CustomBootsItem(ModArmorMaterials.ROHAN_SCALE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LONGBEARD_MAIL_HELMET = registerGeneratedItem("longbeard_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.LONGBEARD_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item LONGBEARD_MAIL_CHESTPLATE = registerGeneratedItem("longbeard_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LONGBEARD_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false));
    public static final Item LONGBEARD_MAIL_LEGGINGS = registerGeneratedItem("longbeard_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LONGBEARD_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LONGBEARD_MAIL_BOOTS = registerGeneratedItem("longbeard_mail_boots",
            new CustomBootsItem(ModArmorMaterials.LONGBEARD_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LONGBEARD_SCALE_HELMET = registerGeneratedItem("longbeard_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.LONGBEARD_SCALE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item LONGBEARD_SCALE_CHESTPLATE = registerGeneratedItem("longbeard_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LONGBEARD_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false));
    public static final Item LONGBEARD_SCALE_LEGGINGS = registerGeneratedItem("longbeard_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LONGBEARD_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LONGBEARD_SCALE_BOOTS = registerGeneratedItem("longbeard_scale_boots",
            new CustomBootsItem(ModArmorMaterials.LONGBEARD_SCALE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LONGBEARD_PLATE_HELMET = registerGeneratedItem("longbeard_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.LONGBEARD_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item LONGBEARD_PLATE_CHESTPLATE = registerGeneratedItem("longbeard_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LONGBEARD_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),true));
    public static final Item LONGBEARD_PLATE_LEGGINGS = registerGeneratedItem("longbeard_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LONGBEARD_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LONGBEARD_PLATE_BOOTS = registerGeneratedItem("longbeard_plate_boots",
            new CustomBootsItem(ModArmorMaterials.LONGBEARD_PLATE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LONGBEARD_COMMANDER_HELMET = registerGeneratedItem("longbeard_commander_helmet",
            new CustomHelmetItem(ModArmorMaterials.LONGBEARD_COMMANDER_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item LONGBEARD_COMMANDER_CHESTCOMMANDER = registerGeneratedItem("longbeard_commander_chestcommander",
            new CustomChestplateItem(ModArmorMaterials.LONGBEARD_COMMANDER_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),true));
    public static final Item LONGBEARD_COMMANDER_LEGGINGS = registerGeneratedItem("longbeard_commander_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LONGBEARD_COMMANDER_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LONGBEARD_COMMANDER_BOOTS = registerGeneratedItem("longbeard_commander_boots",
            new CustomBootsItem(ModArmorMaterials.LONGBEARD_COMMANDER_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LOTHLORIEN_MAIL_HELMET = registerGeneratedItem("lothlorien_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.LOTHLORIEN_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item LOTHLORIEN_MAIL_CHESTPLATE = registerGeneratedItem("lothlorien_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LOTHLORIEN_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false));
    public static final Item LOTHLORIEN_MAIL_LEGGINGS = registerGeneratedItem("lothlorien_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LOTHLORIEN_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LOTHLORIEN_MAIL_BOOTS = registerGeneratedItem("lothlorien_mail_boots",
            new CustomBootsItem(ModArmorMaterials.LOTHLORIEN_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LOTHLORIEN_SCALE_HELMET = registerGeneratedItem("lothlorien_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.LOTHLORIEN_SCALE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item LOTHLORIEN_SCALE_CHESTPLATE = registerGeneratedItem("lothlorien_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LOTHLORIEN_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false));
    public static final Item LOTHLORIEN_SCALE_LEGGINGS = registerGeneratedItem("lothlorien_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LOTHLORIEN_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LOTHLORIEN_SCALE_BOOTS = registerGeneratedItem("lothlorien_scale_boots",
            new CustomBootsItem(ModArmorMaterials.LOTHLORIEN_SCALE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item LOTHLORIEN_PLATE_HELMET = registerGeneratedItem("lothlorien_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.LOTHLORIEN_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),true));
    public static final Item LOTHLORIEN_PLATE_CHESTPLATE = registerGeneratedItem("lothlorien_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.LOTHLORIEN_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(), true));
    public static final Item LOTHLORIEN_PLATE_LEGGINGS = registerGeneratedItem("lothlorien_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.LOTHLORIEN_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item LOTHLORIEN_PLATE_BOOTS = registerGeneratedItem("lothlorien_plate_boots",
            new CustomBootsItem(ModArmorMaterials.LOTHLORIEN_PLATE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MORDOR_URUK_PLATE_HELMET = registerGeneratedItem("mordor_uruk_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.MORDOR_URUK_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item MORDOR_URUK_PLATE_CHESTPLATE = registerGeneratedItem("mordor_uruk_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MORDOR_URUK_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false));
    public static final Item MORDOR_URUK_PLATE_LEGGINGS = registerGeneratedItem("mordor_uruk_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MORDOR_URUK_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MORDOR_URUK_PLATE_BOOTS = registerGeneratedItem("mordor_uruk_plate_boots",
            new CustomBootsItem(ModArmorMaterials.MORDOR_URUK_PLATE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MISTY_ORC_MAIL_HELMET = registerGeneratedItem("misty_orc_mail_helmet",
            new CustomHelmetItem(ModArmorMaterials.MISTY_ORC_MAIL_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item MISTY_ORC_MAIL_CHESTPLATE = registerGeneratedItem("misty_orc_mail_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MISTY_ORC_MAIL_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false));
    public static final Item MISTY_ORC_MAIL_LEGGINGS = registerGeneratedItem("misty_orc_mail_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MISTY_ORC_MAIL_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MISTY_ORC_MAIL_BOOTS = registerGeneratedItem("misty_orc_mail_boots",
            new CustomBootsItem(ModArmorMaterials.MISTY_ORC_MAIL_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MISTY_URUK_SCALE_HELMET = registerGeneratedItem("misty_uruk_scale_helmet",
            new CustomHelmetItem(ModArmorMaterials.MISTY_URUK_SCALE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item MISTY_URUK_SCALE_CHESTPLATE = registerGeneratedItem("misty_uruk_scale_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MISTY_URUK_SCALE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false));
    public static final Item MISTY_URUK_SCALE_LEGGINGS = registerGeneratedItem("misty_uruk_scale_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MISTY_URUK_SCALE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MISTY_URUK_SCALE_BOOTS = registerGeneratedItem("misty_uruk_scale_boots",
            new CustomBootsItem(ModArmorMaterials.MISTY_URUK_SCALE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MISTY_URUK_PLATE_HELMET = registerGeneratedItem("misty_uruk_plate_helmet",
            new CustomHelmetItem(ModArmorMaterials.MISTY_URUK_PLATE_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings(),false));
    public static final Item MISTY_URUK_PLATE_CHESTPLATE = registerGeneratedItem("misty_uruk_plate_chestplate",
            new CustomChestplateItem(ModArmorMaterials.MISTY_URUK_PLATE_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings(),false));
    public static final Item MISTY_URUK_PLATE_LEGGINGS = registerGeneratedItem("misty_uruk_plate_leggings",
            new CustomLeggingsItem(ModArmorMaterials.MISTY_URUK_PLATE_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MISTY_URUK_PLATE_BOOTS = registerGeneratedItem("misty_uruk_plate_boots",
            new CustomBootsItem(ModArmorMaterials.MISTY_URUK_PLATE_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item BEAST_CHAINS = registerGeneratedItem("beast_chains",
            new SaddleItem(new FabricItemSettings().maxCount(1)));

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
