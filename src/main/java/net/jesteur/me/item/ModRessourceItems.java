package net.jesteur.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.item.items.StarlightPhialItem;
import net.jesteur.me.item.items.PebbleItem;
import net.jesteur.me.item.utils.ModItemGroups;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRessourceItems {

    public static final Item MITHRIL_INGOT = registerItem("mithril_ingot",
            new Item(new FabricItemSettings().fireproof()));
    public static final Item RAW_MITHRIL = registerItem("raw_mithril",
            new Item(new FabricItemSettings().fireproof()));
    public static final Item MITHRIL_NUGGET = registerItem("mithril_nugget",
            new Item(new FabricItemSettings().fireproof()));
    public static final Item MORGUL_INGOT = registerItem("morgul_ingot",
            new Item(new FabricItemSettings()));
    public static final Item ORC_BONE = registerItem("orc_bone",
            new Item(new FabricItemSettings()));
    public static final Item WARG_BONE = registerItem("warg_bone",
            new Item(new FabricItemSettings()));
    public static final Item EMPTY_PHIAL = registerItem("empty_phial",
            new Item(new FabricItemSettings()));
    public static final Item STARLIGHT_PHIAL = registerItem("starlight_phial",
            new StarlightPhialItem(new FabricItemSettings().maxCount(1)));
    public static final Item PEBBLE = registerItem("pebble",
            new PebbleItem(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        ModItemGroups.RESOURCES_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.debug("Registering Mod Resource Items for " + MiddleEarth.MOD_ID);
    }
}
