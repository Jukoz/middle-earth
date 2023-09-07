package net.jesteur.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.block.ModDecorativeBlocks;
import net.jesteur.me.item.utils.ModArmorMaterials;
import net.jesteur.me.item.utils.ModItemGroups;
import net.minecraft.block.Blocks;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class ModDecorativeItems {
    public static final Item SKULL_LANTERN = registerItem("skull_lantern",
            new VerticallyAttachableBlockItem(ModDecorativeBlocks.SKULL_LANTERN, ModDecorativeBlocks.WALL_SKULL_LANTERN, new Item.Settings(), Direction.DOWN));


    private static Item registerItem(String name, Item item) {
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.debug("Registering Mod Decorative Items for " + MiddleEarth.MOD_ID);
    }
}
