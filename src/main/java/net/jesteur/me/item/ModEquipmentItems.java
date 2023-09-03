package net.jesteur.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.item.utils.ModArmorMaterials;
import net.jesteur.me.item.utils.ModItemGroups;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.Item;
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
    public static final Item CHAINMAIL_CLOAK = registerItem("chainmail_fur_cloak",
            new ArmorItem(ModArmorMaterials.CHAINMAIL_FUR_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        ModItemGroups.EQUIPMENT_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.debug("Registering Mod Equipment Items for " + MiddleEarth.MOD_ID);
    }
}
