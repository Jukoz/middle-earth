package net.sevenstars.middleearth.features.smithing;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Unit;
import net.sevenstars.middleearth.common.registration.ItemRegistrar;
import net.sevenstars.middleearth.datageneration.content.models.SimpleHandheldItemModel;
import net.sevenstars.middleearth.features.smithing.item.SmithingHammerItem;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.sevenstars.middleearth.item.utils.ToolMaterialsME;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SmithingItems {
    private static final ItemRegistrar TOOLS = ItemRegistrar.forGroup(ItemGroupsME.TOOLS_CONTENTS);
    private static final ItemRegistrar DECORATIVES = ItemRegistrar.forGroup(ItemGroupsME.DECORATIVES_BLOCKS_CONTENT);

    public static final Item FORGE = DECORATIVES.register("forge",
            (settings) -> new BlockItem(SmithingBlocks.FORGE, settings), new Item.Settings());

    public static final Item STONE_ANVIL = DECORATIVES.register("stone_anvil",
            (settings) -> new BlockItem(SmithingBlocks.STONE_ANVIL, settings), new Item.Settings());

    public static final Item TREATED_ANVIL = DECORATIVES.register("treated_anvil",
            (settings) -> new BlockItem(SmithingBlocks.TREATED_ANVIL, settings), new Item.Settings());
    public static final Item DWARVEN_TREATED_ANVIL = DECORATIVES.register("dwarven_treated_anvil",
            (settings) -> new BlockItem(SmithingBlocks.DWARVEN_TREATED_ANVIL, settings), new Item.Settings());
    public static final Item ELVEN_TREATED_ANVIL = DECORATIVES.register("elven_treated_anvil",
            (settings) -> new BlockItem(SmithingBlocks.ELVEN_TREATED_ANVIL, settings), new Item.Settings());
    public static final Item ORCISH_TREATED_ANVIL = DECORATIVES.register("orcish_treated_anvil",
            (settings) -> new BlockItem(SmithingBlocks.ORCISH_TREATED_ANVIL, settings), new Item.Settings());
    public static final Item BELLOWS = DECORATIVES.register("bellows",
            (settings) -> new BlockItem(SmithingBlocks.BELLOWS, settings), new Item.Settings());

    public static List<Item> smithingHammers = new ArrayList<>();

    public static final Item COPPER_SMITHING_HAMMER = registerHammer("copper_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.COPPER_HAMMER, -3.5f), new Item.Settings().enchantable(8));
    public static final Item BRONZE_SMITHING_HAMMER = registerHammer("bronze_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.BRONZE_HAMMER, -3.35f), new Item.Settings().enchantable(10));
    public static final Item CRUDE_SMITHING_HAMMER = registerHammer("crude_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.CRUDE_HAMMER, -3.35f), new Item.Settings().enchantable(10));

    public static final Item SMITHING_HAMMER = registerHammer("smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.STEEL_HAMMER, -3.2f), new Item.Settings().enchantable(12));
    public static final Item NOBLE_SMITHING_HAMMER = registerHammer("noble_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.NOBLE_STEEL_HAMMER, -3.0f), new Item.Settings().enchantable(15));

    public static final Item DWARVEN_SMITHING_HAMMER = registerHammer("dwarven_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.KHAZAD_STEEL_HAMMER, -3.2f), new Item.Settings().enchantable(12));
    public static final Item DWARVEN_NOBLE_SMITHING_HAMMER = registerHammer("dwarven_noble_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.KHAZAD_NOBLE_STEEL_HAMMER, -3.0f), new Item.Settings().enchantable(15));

    public static final Item ELVEN_SMITHING_HAMMER = registerHammer("elven_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.EDHEL_STEEL_HAMMER, -3.2f), new Item.Settings().enchantable(12));
    public static final Item ELVEN_NOBLE_SMITHING_HAMMER = registerHammer("elven_noble_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.EDHEL_NOBLE_STEEL_HAMMER, -3.0f), new Item.Settings().enchantable(15));

    public static final Item ORCISH_SMITHING_HAMMER = registerHammer("orcish_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.BURZUM_STEEL_HAMMER, -3.2f), new Item.Settings().enchantable(12));
    public static final Item ORCISH_ELITE_SMITHING_HAMMER = registerHammer("orcish_elite_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.BURZUM_NOBLE_STEEL_HAMMER, -3.0f), new Item.Settings().enchantable(15));

    public static final Item MITHRIL_SMITHING_HAMMER = registerHammer("mithril_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.MITHRIL_HAMMER, -2.5f), new Item.Settings().enchantable(20)
                    .component(DataComponentTypes.UNBREAKABLE, Unit.INSTANCE));

    private static Item registerHammer(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = TOOLS.register(name, factory, settings);
        SimpleHandheldItemModel.items.add(item);
        smithingHammers.add(item);
        return item;
    }
}
