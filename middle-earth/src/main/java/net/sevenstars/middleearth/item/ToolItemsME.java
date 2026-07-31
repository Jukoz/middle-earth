package net.sevenstars.middleearth.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.datageneration.content.models.SimpleBigItemModel;
import net.sevenstars.middleearth.datageneration.content.models.SimpleHandheldItemModel;
import net.sevenstars.middleearth.item.items.PipeItem;
import net.sevenstars.middleearth.item.items.SmithingHammerItem;
import net.sevenstars.middleearth.item.items.weapons.CustomAxeWeaponItem;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.sevenstars.middleearth.item.utils.ToolMaterialsME;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ToolItemsME {

    /**
     * Middle-earth mod Tool Items registry
     */

    private static final int IRON_DAMAGE = 1;
    private static final int IRON_AXE_DAMAGE = IRON_DAMAGE + 5;
    private static final int IRON_PICKAXE_DAMAGE = IRON_DAMAGE - 1;
    private static final int IRON_HOE_DAMAGE = IRON_DAMAGE - 2;
    private static final float IRON_ATTACK_SPEED = -2.5f;
    private static final float IRON_AXE_ATTACK_SPEED = -3.1f;
    private static final float IRON_HOE_ATTACK_SPEED = -2f;

    public static final List<Item> smithingHammers = new ArrayList<>();

    public static final Item COPPER_SMITHING_HAMMER = registerSmithingHammerItem("copper_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.COPPER_HAMMER, -3.5f, 8), new Item.Properties());
    public static final Item BRONZE_SMITHING_HAMMER = registerSmithingHammerItem("bronze_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.BRONZE_HAMMER, -3.35f, 10), new Item.Properties());
    public static final Item CRUDE_SMITHING_HAMMER = registerSmithingHammerItem("crude_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.CRUDE_HAMMER, -3.35f, 10), new Item.Properties());

    public static final Item SMITHING_HAMMER = registerSmithingHammerItem("smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.STEEL_HAMMER, -3.2f, 12), new Item.Properties());
    public static final Item NOBLE_SMITHING_HAMMER = registerSmithingHammerItem("noble_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.NOBLE_STEEL_HAMMER, -3.0f, 15), new Item.Properties());

    public static final Item DWARVEN_SMITHING_HAMMER = registerSmithingHammerItem("dwarven_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.KHAZAD_STEEL_HAMMER, -3.2f, 12), new Item.Properties());
    public static final Item DWARVEN_NOBLE_SMITHING_HAMMER = registerSmithingHammerItem("dwarven_noble_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.KHAZAD_NOBLE_STEEL_HAMMER, -3.0f, 15), new Item.Properties());

    public static final Item ELVEN_SMITHING_HAMMER = registerSmithingHammerItem("elven_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.EDHEL_STEEL_HAMMER, -3.2f, 12), new Item.Properties());
    public static final Item ELVEN_NOBLE_SMITHING_HAMMER = registerSmithingHammerItem("elven_noble_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.EDHEL_NOBLE_STEEL_HAMMER, -3.0f, 15), new Item.Properties());

    public static final Item ORCISH_SMITHING_HAMMER = registerSmithingHammerItem("orcish_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.BURZUM_STEEL_HAMMER, -3.2f, 12), new Item.Properties());
    public static final Item ORCISH_ELITE_SMITHING_HAMMER = registerSmithingHammerItem("orcish_elite_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.BURZUM_NOBLE_STEEL_HAMMER, -3.0f, 15), new Item.Properties());

    public static final Item MITHRIL_SMITHING_HAMMER = registerSmithingHammerItem("mithril_smithing_hammer",
            (settings) -> new SmithingHammerItem(settings, ToolMaterialsME.MITHRIL_HAMMER, -2.5f, 20), new Item.Properties()
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(true)));

    public static final Item IRON_CHISEL = registerItemHandheld("iron_chisel",
            Item::new, new Item.Properties().stacksTo(1)
                    .component(DataComponents.MAX_DAMAGE, 4));
    public static final Item STEEL_CHISEL = registerItemHandheld("steel_chisel",
            Item::new, new Item.Properties().stacksTo(1)
                    .component(DataComponents.MAX_DAMAGE, 16));
    public static final Item MITHRIL_CHISEL = registerItemHandheld("mithril_chisel",
            Item::new, new Item.Properties().stacksTo(1));

    public static final Item BRONZE_PICKAXE = registerItemHandheld("bronze_pickaxe",
            (settings) -> new PickaxeItem(ToolMaterialsME.BRONZE, settings),
            new Item.Properties().attributes(PickaxeItem.createAttributes(ToolMaterialsME.BRONZE, 0.5f, -2.8f)));
    public static final Item BRONZE_AXE = registerItemHandheld("bronze_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.BRONZE, settings), new Item.Properties());
    public static final Item BRONZE_SHOVEL = registerItemHandheld("bronze_shovel",
            (settings) -> new ShovelItem(ToolMaterialsME.BRONZE, settings),
            new Item.Properties().attributes(ShovelItem.createAttributes(ToolMaterialsME.BRONZE, 1.5f, -3.0f)));
    public static final Item BRONZE_HOE = registerItemHandheld("bronze_hoe",
            (settings) -> new HoeItem(ToolMaterialsME.BRONZE, settings),
            new Item.Properties().attributes(HoeItem.createAttributes(ToolMaterialsME.BRONZE, -2.0f, -1.0f)));

    public static final Item CRUDE_PICKAXE = registerItemHandheld("crude_pickaxe",
            (settings) -> new PickaxeItem(ToolMaterialsME.CRUDE, settings),
            new Item.Properties().attributes(PickaxeItem.createAttributes(ToolMaterialsME.CRUDE, 0.5f, -2.8f)));
    public static final Item CRUDE_AXE = registerItemDualModel("crude_axe",
            (settings) -> new AxeItem(ToolMaterialsME.CRUDE, settings),
            new Item.Properties().attributes(AxeItem.createAttributes(ToolMaterialsME.CRUDE, 6.0f, -3.0f)));
    public static final Item CRUDE_SHOVEL = registerItemHandheld("crude_shovel",
            (settings) -> new ShovelItem(ToolMaterialsME.CRUDE, settings),
            new Item.Properties().attributes(ShovelItem.createAttributes(ToolMaterialsME.CRUDE, 1.5f, -3.0f)));
    public static final Item CRUDE_HOE = registerItemHandheld("crude_hoe",
            (settings) -> new HoeItem(ToolMaterialsME.CRUDE, settings),
            new Item.Properties().attributes(HoeItem.createAttributes(ToolMaterialsME.CRUDE, -2.0f, -1.0f)));

    public static final Item STEEL_PICKAXE = registerItemHandheld("steel_pickaxe",
            (settings) -> new PickaxeItem(ToolMaterialsME.STEEL, settings),
            new Item.Properties().attributes(PickaxeItem.createAttributes(ToolMaterialsME.STEEL, 1.0f, -2.8f)));
    public static final Item STEEL_AXE = registerItemDualModel("steel_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.STEEL, settings), new Item.Properties());
    public static final Item STEEL_SHOVEL = registerItemHandheld("steel_shovel",
            (settings) -> new ShovelItem(ToolMaterialsME.STEEL, settings),
            new Item.Properties().attributes(ShovelItem.createAttributes(ToolMaterialsME.STEEL, 1.5f, -3.0f)));
    public static final Item STEEL_HOE = registerItemHandheld("steel_hoe",
            (settings) -> new HoeItem(ToolMaterialsME.STEEL, settings),
            new Item.Properties().attributes(HoeItem.createAttributes(ToolMaterialsME.STEEL, -2.0f, -1.0f)));

    public static final Item BURZUM_STEEL_PICKAXE = registerItemHandheld("burzum_steel_pickaxe",
            (settings) -> new PickaxeItem(ToolMaterialsME.BURZUM_STEEL, settings),
            new Item.Properties().attributes(PickaxeItem.createAttributes(ToolMaterialsME.BURZUM_STEEL, 1.0f, -2.8f)));
    public static final Item BURZUM_STEEL_AXE = registerItemDualModel("burzum_steel_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.BURZUM_STEEL, settings), new Item.Properties());
    public static final Item BURZUM_STEEL_SHOVEL = registerItemHandheld("burzum_steel_shovel",
            (settings) -> new ShovelItem(ToolMaterialsME.BURZUM_STEEL, settings),
            new Item.Properties().attributes(ShovelItem.createAttributes(ToolMaterialsME.BURZUM_STEEL, 1.5f, -3.0f)));
    public static final Item BURZUM_STEEL_HOE = registerItemHandheld("burzum_steel_hoe",
            (settings) -> new HoeItem(ToolMaterialsME.BURZUM_STEEL, settings),
            new Item.Properties().attributes(HoeItem.createAttributes(ToolMaterialsME.BURZUM_STEEL, -2.0f, -1.0f)));

    public static final Item EDHEL_STEEL_PICKAXE = registerItemHandheld("edhel_steel_pickaxe",
            (settings) -> new PickaxeItem(ToolMaterialsME.EDHEL_STEEL, settings),
            new Item.Properties().attributes(PickaxeItem.createAttributes(ToolMaterialsME.EDHEL_STEEL, 1.0f, -2.8f)));
    public static final Item EDHEL_STEEL_AXE = registerItemDualModel("edhel_steel_axe",
            (settings) -> new AxeItem(ToolMaterialsME.EDHEL_STEEL, settings),
            new Item.Properties().attributes(AxeItem.createAttributes(ToolMaterialsME.EDHEL_STEEL, 6.0f, -3.0f)));
    public static final Item EDHEL_STEEL_SHOVEL = registerItemHandheld("edhel_steel_shovel",
            (settings) -> new ShovelItem(ToolMaterialsME.EDHEL_STEEL, settings),
            new Item.Properties().attributes(ShovelItem.createAttributes(ToolMaterialsME.EDHEL_STEEL, 1.5f, -3.0f)));
    public static final Item EDHEL_STEEL_HOE = registerItemHandheld("edhel_steel_hoe",
            (settings) -> new HoeItem(ToolMaterialsME.EDHEL_STEEL, settings),
            new Item.Properties().attributes(HoeItem.createAttributes(ToolMaterialsME.EDHEL_STEEL, -2.0f, -1.0f)));
    
    public static final Item KHAZAD_STEEL_PICKAXE = registerItemHandheld("khazad_steel_pickaxe",
            (settings) -> new PickaxeItem(ToolMaterialsME.KHAZAD_STEEL, settings),
            new Item.Properties().attributes(PickaxeItem.createAttributes(ToolMaterialsME.KHAZAD_STEEL, 1.0f, -2.8f)));
    public static final Item KHAZAD_STEEL_AXE = registerItemHandheld("khazad_steel_axe",
            (settings) -> new AxeItem(ToolMaterialsME.KHAZAD_STEEL, settings),
            new Item.Properties().attributes(AxeItem.createAttributes(ToolMaterialsME.KHAZAD_STEEL, 6.0f, -3.0f)));
    public static final Item KHAZAD_STEEL_SHOVEL = registerItemHandheld("khazad_steel_shovel",
            (settings) -> new ShovelItem(ToolMaterialsME.KHAZAD_STEEL, settings),
            new Item.Properties().attributes(ShovelItem.createAttributes(ToolMaterialsME.KHAZAD_STEEL, 1.5f, -3.0f)));
    public static final Item KHAZAD_STEEL_HOE = registerItemHandheld("khazad_steel_hoe",
            (settings) -> new HoeItem(ToolMaterialsME.KHAZAD_STEEL, settings),
            new Item.Properties().attributes(HoeItem.createAttributes(ToolMaterialsME.KHAZAD_STEEL, -2.0f, -1.0f)));

    public static final Item MITHRIL_PICKAXE = registerItemHandheld("mithril_pickaxe",
            (settings) -> new PickaxeItem(ToolMaterialsME.MITHRIL, settings),
            new Item.Properties().fireResistant()
                    .attributes(PickaxeItem.createAttributes(ToolMaterialsME.MITHRIL, 1.0f, -2.7f))
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(true)));
    public static final Item MITHRIL_AXE = registerItemDualModel("mithril_axe",
            (settings) -> new CustomAxeWeaponItem(ToolMaterialsME.MITHRIL, settings), new Item.Properties().fireResistant()
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(true)));
    public static final Item MITHRIL_SHOVEL = registerItemHandheld("mithril_shovel",
            (settings) -> new ShovelItem(ToolMaterialsME.MITHRIL, settings), new Item.Properties().fireResistant()
                    .attributes(ShovelItem.createAttributes(ToolMaterialsME.MITHRIL, 1.5f, -3.0f))
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(true)));
    public static final Item MITHRIL_HOE = registerItemHandheld("mithril_hoe",
            (settings) -> new HoeItem(ToolMaterialsME.MITHRIL, settings), new Item.Properties().fireResistant()
                    .attributes(HoeItem.createAttributes(ToolMaterialsME.MITHRIL, -2.0f, 1.0f))
                    .component(DataComponents.UNBREAKABLE, new Unbreakable(true)));

    public static final Item PIPE = registerItem2dGUI3dPerson("pipe",
            (settings) -> new PipeItem(settings, 3), new Item.Properties().stacksTo(1));
    public static final Item CLAY_PIPE = registerItem2dGUI3dPerson("clay_pipe",
            (settings) -> new PipeItem(settings, 3), new Item.Properties().stacksTo(1));
    public static final Item RIVERBEND_PIPE = registerItem2dGUI3dPerson("riverbend_pipe",
            (settings) -> new PipeItem(settings, 3 ), new Item.Properties().stacksTo(1));
    public static final Item BRIMMINGBEND_PIPE = registerItem2dGUI3dPerson("brimmingbend_pipe",
            (settings) -> new PipeItem(settings, 5), new Item.Properties().stacksTo(1));
    public static final Item LONGBOTTOM_PIPE = registerItem2dGUI3dPerson("longbottom_pipe",
            (settings) -> new PipeItem(settings, 5), new Item.Properties().stacksTo(1));
    
    private static Item registerItemHandheld(String name, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = factory.apply(settings);
        ItemGroupsME.TOOLS_CONTENTS.add(item.getDefaultInstance());
        SimpleHandheldItemModel.items.add(item);
        return registerItem(item, name);
    }

    private static Item registerSmithingHammerItem(String name, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = registerItemHandheld(name, factory, settings);
        smithingHammers.add(item);
        return item;
    }

    private static Item registerItemDualModel(String name, Function<Item.Properties, Item> factory, Item.Properties settings) {
        Item item = factory.apply(settings);
        ItemGroupsME.TOOLS_CONTENTS.add(item.getDefaultInstance());
        SimpleBigItemModel.items.add(item);
        return registerItem(item, name);
    }
    
    public static Item registerItem2dGUI3dPerson(String name, Function<Item.Properties, Item> factory, Item.Properties settings){
        Item item = factory.apply(settings);
        SimpleBigItemModel.genericItems.add(item);
        ItemGroupsME.TOOLS_CONTENTS.add(item.getDefaultInstance());
        return registerItem(item, name);
    }

    private static Item registerItem(Item item, String name){
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.ITEM, name));
        return RegistrationBridge.register(BuiltInRegistries.ITEM, ModBlocks.keyOfItem(name).location(), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Tool Items for " + MiddleEarth.MOD_ID);
    }

}
