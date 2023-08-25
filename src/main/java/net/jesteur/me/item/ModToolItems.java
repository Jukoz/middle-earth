package net.jesteur.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.datageneration.content.models.SimpleHandheldItemModel;
import net.jesteur.me.item.utils.ModItemGroups;
import net.jesteur.me.item.utils.ModToolMaterials;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModToolItems {
    private static final int IRON_DAMAGE = 1;
    private static final int IRON_AXE_DAMAGE = IRON_DAMAGE + 5;
    private static final int IRON_PICKAXE_DAMAGE = IRON_DAMAGE - 1;
    private static final int IRON_HOE_DAMAGE = IRON_DAMAGE - 2;
    private static final float IRON_ATTACK_SPEED = -2.5f;
    private static final float IRON_AXE_ATTACK_SPEED = -3.1f;
    private static final float IRON_HOE_ATTACK_SPEED = -2f;

    public static final Item DWARVEN_PICKAXE = registerItem("dwarven_pickaxe",
            new PickaxeItem(ModToolMaterials.DWARVEN, IRON_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item DWARVEN_AXE = registerItem("dwarven_axe",
            new AxeItem(ModToolMaterials.DWARVEN, IRON_AXE_DAMAGE, IRON_AXE_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item DWARVEN_SHOVEL = registerItem("dwarven_shovel",
            new ShovelItem(ModToolMaterials.DWARVEN, IRON_PICKAXE_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item DWARVEN_HOE = registerItem("dwarven_hoe",
            new HoeItem(ModToolMaterials.DWARVEN, IRON_HOE_DAMAGE, IRON_HOE_ATTACK_SPEED, new FabricItemSettings()));

    public static final Item MORDOR_ORC_PICKAXE = registerItem("mordor_orc_pickaxe",
            new PickaxeItem(ModToolMaterials.MORGUL, IRON_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item MORDOR_ORC_AXE = registerItem("mordor_orc_axe",
            new AxeItem(ModToolMaterials.MORGUL, IRON_AXE_DAMAGE, IRON_AXE_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item MORDOR_ORC_SHOVEL = registerItem("mordor_orc_shovel",
            new ShovelItem(ModToolMaterials.MORGUL, IRON_PICKAXE_DAMAGE, IRON_ATTACK_SPEED, new FabricItemSettings()));
    public static final Item MORDOR_ORC_HOE = registerItem("mordor_orc_hoe",
            new HoeItem(ModToolMaterials.MORGUL, IRON_HOE_DAMAGE, IRON_HOE_ATTACK_SPEED, new FabricItemSettings()));



    private static Item registerItem(String name, Item item) {
        ModItemGroups.TOOLS_CONTENTS.add(item.getDefaultStack());
        SimpleHandheldItemModel.items.add(item);
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }
    public static void registerModItems() {
        MiddleEarth.LOGGER.debug("Registering Mod Tool Items for " + MiddleEarth.MOD_ID);
    }
}
