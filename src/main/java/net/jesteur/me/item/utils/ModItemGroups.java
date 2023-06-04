package net.jesteur.me.item.utils;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.block.ModBlockSets;
import net.jesteur.me.block.ModNatureBlocks;
import net.jesteur.me.item.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup BLOCKS = FabricItemGroup.builder(new Identifier(MiddleEarth.MOD_ID, "blocks"))
            .displayName(Text.literal("Blocks"))
            .icon(() -> new ItemStack(ModBlockSets.ASHEN_BRICKS.base().asItem()))
            .build();
    public static final ItemGroup NATURE_BLOCKS = FabricItemGroup.builder(new Identifier(MiddleEarth.MOD_ID, "nature_blocks"))
            .displayName(Text.literal("Nature Blocks"))
            .icon(() -> new ItemStack(ModNatureBlocks.MORDOR_LICHEN.asItem()))
            .build();
    public static final ItemGroup FOOD = FabricItemGroup.builder(new Identifier(MiddleEarth.MOD_ID, "food"))
            .displayName(Text.literal("Food"))
            .icon(() -> new ItemStack(ModFoodItems.LEMBAS))
            .build();
    public static final ItemGroup WEAPONS = FabricItemGroup.builder(new Identifier(MiddleEarth.MOD_ID, "weapons"))
            .displayName(Text.literal("Weapons"))
            .icon(() -> new ItemStack(ModWeaponItems.GONDOR_SWORD))
            .build();
    public static final ItemGroup TOOLS = FabricItemGroup.builder(new Identifier(MiddleEarth.MOD_ID, "tools"))
            .displayName(Text.literal("Tools"))
            .icon(() -> new ItemStack(ModToolItems.DWARVEN_PICKAXE))
            .build();
    public static final ItemGroup RESOURCES = FabricItemGroup.builder(new Identifier(MiddleEarth.MOD_ID, "resources"))
            .displayName(Text.literal("Resources"))
            .icon(() -> new ItemStack(ModRessourceItems.MITHRIL_INGOT))
            .build();

    public static final ItemGroup SPAWN_EGGS = FabricItemGroup.builder(new Identifier(MiddleEarth.MOD_ID, "spawn_eggs"))
            .displayName(Text.literal("Spawn Eggs"))
            .icon(() -> new ItemStack(ModEggItems.MORDOR_ORC_SPAWN_EGG))
            .build();
}
