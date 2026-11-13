package net.sevenstars.middleearth.item.utils;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.*;
import net.minecraft.text.Text;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.GenericBlockSetRegistryME;
import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.sevenstars.middleearth.block.registration.StoneBlockSetRegistryME;
import net.sevenstars.middleearth.block.registration.WoodBlockSetRegistryME;
import net.sevenstars.middleearth.item.*;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;

import java.util.LinkedList;
import java.util.List;

public class ItemGroupsME {
    public static final List<ItemStack> STONE_BLOCKS_CONTENTS = new LinkedList<>();
    public static final ItemGroup STONE_BLOCKS = FabricItemGroup.builder()
            .displayName(Text.translatable(MiddleEarth.id("stone_blocks").toTranslationKey("itemGroup")))
            .icon(() -> new ItemStack(StoneBlockSetRegistryME.CALCITE_SET.brickBlocks.base().asItem()))
            .entries((displayContext, entries) -> {
                for (ItemStack item : STONE_BLOCKS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> WOOD_BLOCKS_CONTENTS = new LinkedList<>();
    public static final ItemGroup WOOD_BLOCKS = FabricItemGroup.builder()
            .displayName(Text.translatable(MiddleEarth.id("wood_blocks").toTranslationKey("itemGroup")))
            .icon(() -> new ItemStack(WoodBlockSetRegistryME.WILLOW_SET.logBlocks.log().asItem()))
            .entries((displayContext, entries) -> {
                for (ItemStack item : WOOD_BLOCKS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> MISC_BLOCKS_CONTENTS = new LinkedList<>();
    public static final ItemGroup MISC_BLOCKS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".misc_blocks"))
            .icon(() -> new ItemStack(GenericBlockSetRegistryME.STRAW.blockSet.base().asItem()))
            .entries((displayContext, entries) -> {
                for (ItemStack item : MISC_BLOCKS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> DECORATIVES_BLOCKS_CONTENT = new LinkedList<>();
    public static final ItemGroup DECORATIVES_BLOCKS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".decorative_blocks"))
            .icon(() -> new ItemStack(DecorativeItemsME.DWARVEN_LANTERN))
            .entries((displayContext, entries) -> {
                for (ItemStack item : DECORATIVES_BLOCKS_CONTENT) {
                    entries.add(item);
                };
                entries.addAll(ItemGroupsUtil.addFactionBanners(displayContext.lookup()));
            })
            .build();

    public static final List<ItemStack> NATURE_BLOCKS_CONTENTS = new LinkedList<>();
    public static final ItemGroup NATURE_BLOCKS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".nature_blocks"))
            .icon(() -> new ItemStack(NatureBlockRegistryME.HEATHER.asItem()))
            .entries((displayContext, entries) -> {
                for (ItemStack item : NATURE_BLOCKS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> FOOD_CONTENTS = new LinkedList<>();
    public static final ItemGroup FOOD = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".food_items"))
            .icon(() -> new ItemStack(FoodItemsME.LEMBAS))
            .entries((displayContext, entries) -> {
                for (ItemStack item : FOOD_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> WEAPONS_CONTENTS = new LinkedList<>();
    public static final ItemGroup WEAPONS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".weapon_items"))
            .icon(() -> new ItemStack(WeaponItemsME.GONDORIAN_SWORD))
            .entries((displayContext, entries) -> {
                for (ItemStack item : WEAPONS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> EQUIPMENT_CONTENTS = new LinkedList<>();
    public static final ItemGroup EQUIPMENT = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".equipment_items"))
            .icon(() -> new ItemStack(EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_HELMET))
            .entries((displayContext, entries) -> {
                for (ItemStack item : EQUIPMENT_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> TOOLS_CONTENTS = new LinkedList<>();
    public static final ItemGroup TOOLS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".tool_items"))
            .icon(() -> new ItemStack(ToolItemsME.KHAZAD_STEEL_PICKAXE))
            .entries((displayContext, entries) -> {
                for (ItemStack item : TOOLS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> RESOURCES_CONTENTS = new LinkedList<>();
    public static final ItemGroup RESOURCES = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".resource_items"))
            .icon(() -> new ItemStack(ResourceItemsME.MITHRIL_INGOT))
            .entries((displayContext, entries) -> {
                for (ItemStack item : RESOURCES_CONTENTS) {
                    entries.addAll(ItemGroupsUtil.processResourceItem(item, displayContext));
                }
            })
            .build();

    public static final List<ItemStack> SPAWN_EGGS_CONTENTS = new LinkedList<>();
    public static final ItemGroup SPAWN_EGGS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".spawn_egg_items"))
            .icon(() -> new ItemStack(EggItemsME.BROADHOOF_GOAT_SPAWN_EGG))
            .entries((displayContext, entries) -> {
                for (ItemStack item : SPAWN_EGGS_CONTENTS) {
                    entries.add(item);
                };
                displayContext.lookup().getOptional(DynamicRegistriesME.NPC_TYPE)
                        .ifPresent(registryWrapper -> ItemGroupsUtil.addNpcEggs(
                                entries,
                                registryWrapper,
                                registryEntry -> true,
                                displayContext.lookup(),
                                ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS));
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, MiddleEarth.id("stone_blocks"), STONE_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, MiddleEarth.id("wood_blocks"), WOOD_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, MiddleEarth.id("misc_blocks"), MISC_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, MiddleEarth.id("decorative"), DECORATIVES_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, MiddleEarth.id("nature_blocks"), NATURE_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, MiddleEarth.id("food_items"), FOOD);
        Registry.register(Registries.ITEM_GROUP, MiddleEarth.id("weapon_items"), WEAPONS);
        Registry.register(Registries.ITEM_GROUP, MiddleEarth.id("equipment_items"), EQUIPMENT);
        Registry.register(Registries.ITEM_GROUP, MiddleEarth.id("tool_items"), TOOLS);
        Registry.register(Registries.ITEM_GROUP, MiddleEarth.id("resource_items"), RESOURCES);
        Registry.register(Registries.ITEM_GROUP, MiddleEarth.id("spawn_egg_items"), SPAWN_EGGS);
    }

}
