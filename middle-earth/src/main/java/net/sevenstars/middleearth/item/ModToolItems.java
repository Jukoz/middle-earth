package net.sevenstars.middleearth.item;

import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.ModBlocks;
import net.sevenstars.middleearth.datageneration.content.models.SimpleBigItemModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleHandheldItemModel;
import net.sevenstars.middleearth.item.items.PipeItem;
import net.sevenstars.middleearth.item.items.SmithingHammerItem;
import net.sevenstars.middleearth.item.items.weapons.CustomAxeWeaponItem;
import net.sevenstars.middleearth.item.utils.ModItemGroups;
import net.sevenstars.middleearth.item.utils.ModToolMaterials;

import java.util.function.Function;

public class ModToolItems {
    private static final int IRON_DAMAGE = 1;
    private static final int IRON_AXE_DAMAGE = IRON_DAMAGE + 5;
    private static final int IRON_PICKAXE_DAMAGE = IRON_DAMAGE - 1;
    private static final int IRON_HOE_DAMAGE = IRON_DAMAGE - 2;
    private static final float IRON_ATTACK_SPEED = -2.5f;
    private static final float IRON_AXE_ATTACK_SPEED = -3.1f;
    private static final float IRON_HOE_ATTACK_SPEED = -2f;

    public static final Item COPPER_SMITHING_HAMMER = registerItemHandheld("copper_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ModToolMaterials.COPPER_HAMMER, -3.5f), new Item.Settings());

    public static final Item SMITHING_HAMMER = registerItemHandheld("smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ModToolMaterials.STEEL_HAMMER, -3.25f), new Item.Settings());
    public static final Item NOBLE_SMITHING_HAMMER = registerItemHandheld("noble_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ModToolMaterials.NOBLE_STEEL_HAMMER, -3.0f), new Item.Settings());

    public static final Item DWARVEN_SMITHING_HAMMER = registerItemHandheld("dwarven_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ModToolMaterials.KHAZAD_STEEL_HAMMER, -3.25f), new Item.Settings());
    public static final Item DWARVEN_NOBLE_SMITHING_HAMMER = registerItemHandheld("dwarven_noble_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ModToolMaterials.KHAZAD_NOBLE_STEEL_HAMMER, -3.0f), new Item.Settings());

    public static final Item ELVEN_SMITHING_HAMMER = registerItemHandheld("elven_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ModToolMaterials.EDHEL_STEEL_HAMMER, -3.25f), new Item.Settings());
    public static final Item ELVEN_NOBLE_SMITHING_HAMMER = registerItemHandheld("elven_noble_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ModToolMaterials.EDHEL_NOBLE_STEEL_HAMMER, -3.0f), new Item.Settings());

    public static final Item ORCISH_SMITHING_HAMMER = registerItemHandheld("orcish_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ModToolMaterials.BURZUM_STEEL_HAMMER, -3.25f), new Item.Settings());
    public static final Item ORCISH_ELITE_SMITHING_HAMMER = registerItemHandheld("orcish_elite_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ModToolMaterials.BURZUM_NOBLE_STEEL_HAMMER, -3.0f), new Item.Settings());

    public static final Item MITHRIL_SMITHING_HAMMER = registerItemHandheld("mithril_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ModToolMaterials.MITHRIL_HAMMER, -2.5f), new Item.Settings());

    public static final Item BRONZE_PICKAXE = registerItemHandheld("bronze_pickaxe",
            (settings) -> new PickaxeItem(ModToolMaterials.BRONZE, 0.5f, -2.8f, settings), new Item.Settings());
    public static final Item BRONZE_AXE = registerItemHandheld("bronze_axe",
            (settings) -> new CustomAxeWeaponItem(settings, ModToolMaterials.BRONZE), new Item.Settings());
    public static final Item BRONZE_SHOVEL = registerItemHandheld("bronze_shovel",
            (settings) -> new ShovelItem(ModToolMaterials.BRONZE, 1.5f, -3.0f, settings), new Item.Settings());
    public static final Item BRONZE_HOE = registerItemHandheld("bronze_hoe",
            (settings) -> new HoeItem(ModToolMaterials.BRONZE, -2.0f, -1.0f, settings), new Item.Settings());

    public static final Item CRUDE_PICKAXE = registerItemHandheld("crude_pickaxe",
            (settings) -> new PickaxeItem(ModToolMaterials.CRUDE, 1.0f, -2.8f, settings), new Item.Settings());
    public static final Item CRUDE_AXE = registerItemDualModel("crude_axe",
            (settings) -> new AxeItem(ModToolMaterials.CRUDE, 6.0f, -3.0f, settings), new Item.Settings());
    public static final Item CRUDE_SHOVEL = registerItemHandheld("crude_shovel",
            (settings) -> new ShovelItem(ModToolMaterials.CRUDE, 1.5f, -3.0f, settings), new Item.Settings());
    public static final Item CRUDE_HOE = registerItemHandheld("crude_hoe",
            (settings) -> new HoeItem(ModToolMaterials.CRUDE, -2.0f, -1.0f, settings), new Item.Settings());

    public static final Item STEEL_PICKAXE = registerItemHandheld("steel_pickaxe",
            (settings) -> new PickaxeItem(ModToolMaterials.STEEL, 1.0f, -2.8f, settings), new Item.Settings());
    public static final Item STEEL_AXE = registerItemDualModel("steel_axe",
            (settings) -> new CustomAxeWeaponItem(settings,ModToolMaterials.STEEL), new Item.Settings());
    public static final Item STEEL_SHOVEL = registerItemHandheld("steel_shovel",
            (settings) -> new ShovelItem(ModToolMaterials.STEEL, 1.5f, -3.0f, settings), new Item.Settings());
    public static final Item STEEL_HOE = registerItemHandheld("steel_hoe",
            (settings) -> new HoeItem(ModToolMaterials.STEEL, -2.0f, -1.0f, settings), new Item.Settings());

    public static final Item BURZUM_STEEL_PICKAXE = registerItemHandheld("burzum_steel_pickaxe",
            (settings) -> new PickaxeItem(ModToolMaterials.BURZUM_STEEL, 1.0f, -2.8f, settings), new Item.Settings());
    public static final Item BURZUM_STEEL_AXE = registerItemDualModel("burzum_steel_axe",
            (settings) -> new CustomAxeWeaponItem(settings, ModToolMaterials.BURZUM_STEEL), new Item.Settings());
    public static final Item BURZUM_STEEL_SHOVEL = registerItemHandheld("burzum_steel_shovel",
            (settings) -> new ShovelItem(ModToolMaterials.BURZUM_STEEL, 1.5f, -3.0f, settings), new Item.Settings());
    public static final Item BURZUM_STEEL_HOE = registerItemHandheld("burzum_steel_hoe",
            (settings) -> new HoeItem(ModToolMaterials.BURZUM_STEEL, -2.0f, -1.0f, settings), new Item.Settings());

    public static final Item EDHEL_STEEL_PICKAXE = registerItemHandheld("edhel_steel_pickaxe",
            (settings) -> new PickaxeItem(ModToolMaterials.EDHEL_STEEL, 1.0f, -2.8f, settings), new Item.Settings());
    public static final Item EDHEL_STEEL_AXE = registerItemDualModel("edhel_steel_axe",
            (settings) -> new AxeItem(ModToolMaterials.EDHEL_STEEL, 6.0f, -3.0f, settings), new Item.Settings());
    public static final Item EDHEL_STEEL_SHOVEL = registerItemHandheld("edhel_steel_shovel",
            (settings) -> new ShovelItem(ModToolMaterials.EDHEL_STEEL, 1.5f, -3.0f, settings), new Item.Settings());
    public static final Item EDHEL_STEEL_HOE = registerItemHandheld("edhel_steel_hoe",
            (settings) -> new HoeItem(ModToolMaterials.EDHEL_STEEL, -2.0f, -1.0f, settings), new Item.Settings());
    
    public static final Item KHAZAD_STEEL_PICKAXE = registerItemHandheld("khazad_steel_pickaxe",
            (settings) -> new PickaxeItem(ModToolMaterials.KHAZAD_STEEL, 1.0f, -2.8f, settings), new Item.Settings());
    public static final Item KHAZAD_STEEL_AXE = registerItemHandheld("khazad_steel_axe",
            (settings) -> new AxeItem(ModToolMaterials.KHAZAD_STEEL, 6.0f, -3.0f, settings), new Item.Settings());
    public static final Item KHAZAD_STEEL_SHOVEL = registerItemHandheld("khazad_steel_shovel",
            (settings) -> new ShovelItem(ModToolMaterials.KHAZAD_STEEL, 1.5f, -3.0f, settings), new Item.Settings());
    public static final Item KHAZAD_STEEL_HOE = registerItemHandheld("khazad_steel_hoe",
            (settings) -> new HoeItem(ModToolMaterials.KHAZAD_STEEL, -2.0f, -1.0f, settings), new Item.Settings());

    public static final Item MITHRIL_PICKAXE = registerItemHandheld("mithril_pickaxe",
            (settings) -> new PickaxeItem(ModToolMaterials.MITHRIL, 1.0f, -2.7f, settings), new Item.Settings().fireproof());
    public static final Item MITHRIL_AXE = registerItemHandheld("mithril_axe",
            (settings) -> new CustomAxeWeaponItem(settings, ModToolMaterials.MITHRIL), new Item.Settings().fireproof());
    public static final Item MITHRIL_SHOVEL = registerItemHandheld("mithril_shovel",
            (settings) -> new ShovelItem(ModToolMaterials.MITHRIL, 1.5f, -3.0f, settings), new Item.Settings().fireproof());
    public static final Item MITHRIL_HOE = registerItemHandheld("mithril_hoe",
            (settings) -> new HoeItem(ModToolMaterials.MITHRIL, -2.0f, -1.0f, settings), new Item.Settings().fireproof());

    public static final Item PIPE = registerItem2dGUI3dPerson("pipe",
            (settings) -> new PipeItem(settings, 3), new Item.Settings().maxCount(1));
    public static final Item CLAY_PIPE = registerItem2dGUI3dPerson("clay_pipe",
            (settings) -> new PipeItem(settings, 3), new Item.Settings().maxCount(1));
    public static final Item RIVERBEND_PIPE = registerItem2dGUI3dPerson("riverbend_pipe",
            (settings) -> new PipeItem(settings, 3 ), new Item.Settings().maxCount(1));
    public static final Item BRIMMINGBEND_PIPE = registerItem2dGUI3dPerson("brimmingbend_pipe",
            (settings) -> new PipeItem(settings, 5), new Item.Settings().maxCount(1));
    public static final Item LONGBOTTOM_PIPE = registerItem2dGUI3dPerson("longbottom_pipe",
            (settings) -> new PipeItem(settings, 5), new Item.Settings().maxCount(1));
    
    private static Item registerItemHandheld(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ModItemGroups.TOOLS_CONTENTS.add(item.getDefaultStack());
        SimpleHandheldItemModel.items.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }

    private static Item registerItemDualModel(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        ModItemGroups.TOOLS_CONTENTS.add(item.getDefaultStack());
        SimpleBigItemModel.items.add(item);
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }
    
    public static Item registerItem2dGUI3dPerson(String name, Function<Item.Settings, Item> factory, Item.Settings settings){
        Item item = (Item)factory.apply(settings.registryKey(ModBlocks.keyOfItem(name)));
        SimpleBigItemModel.genericItems.add(item);
        ModItemGroups.TOOLS_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, Identifier.of(MiddleEarth.MOD_ID, name), item);
    }
    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Tool Items for " + MiddleEarth.MOD_ID);
    }

}
