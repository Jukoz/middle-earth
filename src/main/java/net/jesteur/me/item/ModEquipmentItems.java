package net.jesteur.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.item.utils.ModArmorMaterials;
import net.jesteur.me.item.utils.ModItemGroups;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEquipmentItems {

    public static final Item CLOAK_HOOD = registerItem("cloak_hood",
            new ArmorItem(ModArmorMaterials.LEATHER_CLOAK, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item CLOAK = registerItem("cloak",
            new ArmorItem(ModArmorMaterials.LEATHER_CLOAK, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));


    private static Item registerItem(String name, Item item) {
        ModItemGroups.WEAPONS_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.debug("Registering Mod Egg Items for " + MiddleEarth.MOD_ID);
    }
}
