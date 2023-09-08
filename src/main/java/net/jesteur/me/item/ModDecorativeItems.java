package net.jesteur.me.item;

import net.jesteur.me.MiddleEarth;
import net.jesteur.me.block.ModDecorativeBlocks;
import net.jesteur.me.item.utils.ModItemGroups;
import net.jesteur.me.item.utils.ModVerticallyAttachableBlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

public class ModDecorativeItems {
    public static final Item SILVER_LANTERN = registerItem("silver_lantern",
            new ModVerticallyAttachableBlockItem(ModDecorativeBlocks.SILVER_LANTERN, ModDecorativeBlocks.WALL_SILVER_LANTERN, new Item.Settings(), new Direction[]{Direction.DOWN, Direction.UP}));


    private static Item registerItem(String name, Item item) {
        ModItemGroups.DECORATIVES_BLOCKS_CONTENT.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.debug("Registering Mod Decorative Items for " + MiddleEarth.MOD_ID);
    }
}
