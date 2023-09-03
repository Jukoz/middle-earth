package net.jesteur.me.item.utils;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.block.SimpleBlockSets;
import net.jesteur.me.block.ModNatureBlocks;
import net.jesteur.me.item.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;

public class ModItemGroups {

    public static final List<ItemStack> BLOCKS_CONTENTS = new LinkedList<>();
    public static final ItemGroup BLOCKS = FabricItemGroup.builder()
            .displayName(Text.literal("Blocks"))
            .icon(() -> new ItemStack(SimpleBlockSets.CALCITE_BRICKS.base().asItem()))
            .entries((displayContext, entries) -> {
                for (ItemStack item : BLOCKS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> NATURE_BLOCKS_CONTENTS = new LinkedList<>();
    public static final ItemGroup NATURE_BLOCKS = FabricItemGroup.builder()
            .displayName(Text.literal("Nature Blocks"))
            .icon(() -> new ItemStack(ModNatureBlocks.TAN_SHRUB.asItem()))
            .entries((displayContext, entries) -> {
                for (ItemStack item : NATURE_BLOCKS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> FOOD_CONTENTS = new LinkedList<>();
    public static final ItemGroup FOOD = FabricItemGroup.builder()
            .displayName(Text.literal("Food"))
            .icon(() -> new ItemStack(ModFoodItems.LEMBAS))
            .entries((displayContext, entries) -> {
                for (ItemStack item : FOOD_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> WEAPONS_CONTENTS = new LinkedList<>();
    public static final ItemGroup WEAPONS = FabricItemGroup.builder()
            .displayName(Text.literal("Weapons"))
            .icon(() -> new ItemStack(ModWeaponItems.GONDOR_SWORD))
            .entries((displayContext, entries) -> {
                for (ItemStack item : WEAPONS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> EQUIPMENT_CONTENTS = new LinkedList<>();
    public static final ItemGroup EQUIPMENT = FabricItemGroup.builder()
            .displayName(Text.literal("Equipment"))
            .icon(() -> new ItemStack(ModEquipmentItems.CLOAK))
            .entries((displayContext, entries) -> {
                for (ItemStack item : EQUIPMENT_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> TOOLS_CONTENTS = new LinkedList<>();
    public static final ItemGroup TOOLS = FabricItemGroup.builder()
            .displayName(Text.literal("Tools"))
            .icon(() -> new ItemStack(ModToolItems.DWARVEN_PICKAXE))
            .entries((displayContext, entries) -> {
                for (ItemStack item : TOOLS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> RESOURCES_CONTENTS = new LinkedList<>();
    public static final ItemGroup RESOURCES = FabricItemGroup.builder()
            .displayName(Text.literal("Resources"))
            .icon(() -> new ItemStack(ModRessourceItems.MITHRIL_INGOT))
            .entries((displayContext, entries) -> {
                for (ItemStack item : RESOURCES_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static final List<ItemStack> SPAWN_EGGS_CONTENTS = new LinkedList<>();

    public static final ItemGroup SPAWN_EGGS = FabricItemGroup.builder()
            .displayName(Text.literal("Spawn Eggs"))
            .icon(() -> new ItemStack(ModEggItems.HOBBIT_SPAWN_EGG))
            .entries((displayContext, entries) -> {
                for (ItemStack item : SPAWN_EGGS_CONTENTS) {
                    entries.add(item);
                }
            })
            .build();

    public static void register() {
        Registry.register(Registries.ITEM_GROUP, new Identifier(MiddleEarth.MOD_ID, "blocks"), BLOCKS);
        Registry.register(Registries.ITEM_GROUP, new Identifier(MiddleEarth.MOD_ID, "nature_blocks"), NATURE_BLOCKS);
        Registry.register(Registries.ITEM_GROUP, new Identifier(MiddleEarth.MOD_ID, "food"), FOOD);
        Registry.register(Registries.ITEM_GROUP, new Identifier(MiddleEarth.MOD_ID, "weapons"), WEAPONS);
        Registry.register(Registries.ITEM_GROUP, new Identifier(MiddleEarth.MOD_ID, "equipment"), EQUIPMENT);
        Registry.register(Registries.ITEM_GROUP, new Identifier(MiddleEarth.MOD_ID, "tools"), TOOLS);
        Registry.register(Registries.ITEM_GROUP, new Identifier(MiddleEarth.MOD_ID, "resources"), RESOURCES);
        Registry.register(Registries.ITEM_GROUP, new Identifier(MiddleEarth.MOD_ID, "spawn_eggs"), SPAWN_EGGS);

    }

}
