package net.jesteur.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.item.utils.ModItemGroups;
import net.jesteur.me.item.utils.ModToolMaterials;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModWeaponItems {
    private static final int IRON_DAMAGE = 3;
    private static final float IRON_ATTACKS_SPEED = -2.3f;

    public static final Item GONDOR_SWORD = registerItem("gondor_sword",
            new SwordItem(ToolMaterials.IRON, IRON_DAMAGE, IRON_ATTACKS_SPEED, new FabricItemSettings()));
    public static final Item MORDOR_ORC_SWORD = registerItem("mordor_orc_sword",
            new SwordItem(ModToolMaterials.MORGUL, IRON_DAMAGE, IRON_ATTACKS_SPEED, new FabricItemSettings()));
    public static final Item DWARVEN_SWORD = registerItem("dwarven_sword",
            new SwordItem(ModToolMaterials.DWARVEN, IRON_DAMAGE, IRON_ATTACKS_SPEED, new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        ModItemGroups.WEAPONS_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }
    public static void registerModItems() {
        MiddleEarth.LOGGER.debug("Registering Mod Weapon Items for " + MiddleEarth.MOD_ID);
    }
}
