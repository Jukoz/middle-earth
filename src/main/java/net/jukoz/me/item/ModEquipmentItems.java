package net.jukoz.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.items.TrollArmorItem;
import net.jukoz.me.item.utils.ModArmorMaterials;
import net.jukoz.me.item.utils.ModItemGroups;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEquipmentItems {

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
            new ArmorItem(ModArmorMaterials.FUR_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item FUR_CLOAK_HOOD = registerItem("fur_cloak_hood",
            new ArmorItem(ModArmorMaterials.FUR_CLOAK, ArmorItem.Type.HELMET, new FabricItemSettings()));
    // Chainmail fur cloaks
    public static final Item CHAINMAIL_FUR_CLOAK = registerItem("chainmail_fur_cloak",
            new ArmorItem(ModArmorMaterials.CHAINMAIL_FUR_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));

    public static final Item NAZGUL_CLOAK_HOOD = registerItem("nazgul_cloak_hood",
            new ArmorItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item NAZGUL_CLOAK = registerItem("nazgul_cloak",
            new ArmorItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item NAZGUL_PANTS = registerItem("nazgul_pants",
            new ArmorItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item NAZGUL_BOOTS = registerItem("nazgul_boots",
            new ArmorItem(ModArmorMaterials.NAZGUL_CLOAK, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item MORDOR_ORC_HELMET = registerItem("mordor_orc_helmet",
            new ArmorItem(ModArmorMaterials.MORDOR_ORC_ARMOR, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item MORDOR_ORC_CHESTPLATE = registerItem("mordor_orc_chestplate",
            new ArmorItem(ModArmorMaterials.MORDOR_ORC_ARMOR, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item MORDOR_ORC_LEGGINGS = registerItem("mordor_orc_leggings",
            new ArmorItem(ModArmorMaterials.MORDOR_ORC_ARMOR, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item MORDOR_ORC_BOOTS = registerItem("mordor_orc_boots",
            new ArmorItem(ModArmorMaterials.MORDOR_ORC_ARMOR, ArmorItem.Type.BOOTS, new FabricItemSettings()));

    public static final Item STEEL_TROLL_ARMOR = registerItem("steel_troll_armor",
            new TrollArmorItem(10, "steel", new FabricItemSettings().maxCount(1)));

    public static final Item GONDOR_SHIELD = registerItem("gondor_shield",
            new ShieldItem(new FabricItemSettings().maxCount(1).maxDamage(336)));
    public static final Item ROHAN_SHIELD = registerItem("rohan_shield",
            new ShieldItem(new FabricItemSettings().maxCount(1).maxDamage(336)));
    public static final Item LONGBEARD_SHIELD = registerItem("longbeard_shield",
            new ShieldItem(new FabricItemSettings().maxCount(1).maxDamage(336)));
    public static final Item LOTHLORIEN_SHIELD = registerItem("lothlorien_shield",
            new ShieldItem(new FabricItemSettings().maxCount(1).maxDamage(336)));
    public static final Item MORDOR_SHIELD = registerItem("mordor_shield",
            new ShieldItem(new FabricItemSettings().maxCount(1).maxDamage(336)));
    public static final Item MISTY_MOUNTAINS_SHIELD = registerItem("misty_mountains_shield",
            new ShieldItem(new FabricItemSettings().maxCount(1).maxDamage(336)));

    public static final Item ROHIRRIC_HORSE_ARMOR = registerItem("rohirric_horse_armor",
            new HorseArmorItem(10, "rohirric", new FabricItemSettings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.debug("Registering Mod Equipment Items for " + MiddleEarth.MOD_ID);
    }
}
