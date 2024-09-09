package net.jukoz.me.item.utils;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.StoneBlockSets;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.WoodBlockSets;
import net.jukoz.me.item.*;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class ModItemGroups {

    public static final List<ItemStack> STONE_BLOCKS_CONTENTS = new LinkedList<>();
    public static final ItemGroup STONE_BLOCKS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".stone_blocks"))
            .icon(() -> new ItemStack(StoneBlockSets.CALCITE_BRICKS.base().asItem()))
            .entries((displayContext, entries) -> {
                for (ItemStack item : STONE_BLOCKS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> WOOD_BLOCKS_CONTENTS = new LinkedList<>();
    public static final ItemGroup WOOD_BLOCKS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".wood_blocks"))
            .icon(() -> new ItemStack(WoodBlockSets.WILLOW.log().asItem()))
            .entries((displayContext, entries) -> {
                for (ItemStack item : WOOD_BLOCKS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> MISC_BLOCKS_CONTENTS = new LinkedList<>();
    public static final ItemGroup MISC_BLOCKS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".misc_blocks"))
            .icon(() -> new ItemStack(ModBlocks.STRAW_BLOCK.asItem()))
            .entries((displayContext, entries) -> {
                for (ItemStack item : MISC_BLOCKS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> DECORATIVES_BLOCKS_CONTENT = new LinkedList<>();
    public static final ItemGroup DECORATIVES_BLOCKS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".decorative_blocks"))
            .icon(() -> new ItemStack(ModDecorativeItems.DWARVEN_LANTERN))
            .entries((displayContext, entries) -> {
                for (ItemStack item : DECORATIVES_BLOCKS_CONTENT) {
                    entries.add(item);
                }
                for(ItemStack item : ModBannerItems.getList(displayContext.lookup().getWrapperOrThrow(RegistryKeys.BANNER_PATTERN))) {
                    try {
                        entries.add(item);
                    } catch (Exception e) {
                        LoggerUtil.logError(e.toString());
                    }
                }
            })
            .build();

    public static final List<ItemStack> NATURE_BLOCKS_CONTENTS = new LinkedList<>();
    public static final ItemGroup NATURE_BLOCKS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".nature_blocks"))
            .icon(() -> new ItemStack(ModNatureBlocks.HEATHER_BUSH.asItem()))
            .entries((displayContext, entries) -> {
                for (ItemStack item : NATURE_BLOCKS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> FOOD_CONTENTS = new LinkedList<>();
    public static final ItemGroup FOOD = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".food_items"))
            .icon(() -> new ItemStack(ModFoodItems.LEMBAS))
            .entries((displayContext, entries) -> {
                for (ItemStack item : FOOD_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> WEAPONS_CONTENTS = new LinkedList<>();
    public static final ItemGroup WEAPONS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".weapon_items"))
            .icon(() -> new ItemStack(ModWeaponItems.GONDORIAN_SWORD))
            .entries((displayContext, entries) -> {
                for (ItemStack item : WEAPONS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> EQUIPMENT_CONTENTS = new LinkedList<>();
    public static final ItemGroup EQUIPMENT = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".equipment_items"))
            .icon(() -> new ItemStack(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_HELMET))
            .entries((displayContext, entries) -> {
                for (ItemStack item : EQUIPMENT_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> TOOLS_CONTENTS = new LinkedList<>();
    public static final ItemGroup TOOLS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".tool_items"))
            .icon(() -> new ItemStack(ModToolItems.KHAZAD_STEEL_PICKAXE))
            .entries((displayContext, entries) -> {
                for (ItemStack item : TOOLS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> RESOURCES_CONTENTS = new LinkedList<>();
    public static final ItemGroup RESOURCES = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".resource_items"))
            .icon(() -> new ItemStack(ModResourceItems.MITHRIL_INGOT))
            .entries((displayContext, entries) -> {
                for (ItemStack item : RESOURCES_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> SPAWN_EGGS_CONTENTS = new LinkedList<>();

    public static final ItemGroup SPAWN_EGGS = FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup." + MiddleEarth.MOD_ID + ".spawn_egg_items"))
            .icon(() -> new ItemStack(ModEggItems.HOBBIT_CIVILIAN_SPAWN_EGG))
            .entries((displayContext, entries) -> {
                for (ItemStack item : SPAWN_EGGS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "stone_blocks"), STONE_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "wood_blocks"), WOOD_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "misc_blocks"), MISC_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "decorative"), DECORATIVES_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "nature_blocks"), NATURE_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "food_items"), FOOD);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "weapon_items"), WEAPONS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "equipment_items"), EQUIPMENT);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "tool_items"), TOOLS);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "resource_items"), RESOURCES);
        Registry.register(Registries.ITEM_GROUP, Identifier.of(MiddleEarth.MOD_ID, "spawn_egg_items"), SPAWN_EGGS);
    }

}
