package net.jukoz.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.minecraft.block.*;

import java.util.ArrayList;
import java.util.List;

public class OreRockSets {
    public static final float STONE_STRENGTH = 2.0f;
    public static final float DEEPSLATE_STRENGTH = 2.5f;

    public static OreRockSet ASHEN_ROCK = registerOreSet("ashen_rock", STONE_STRENGTH, null);
    public static OreRockSet BLUE_ROCK = registerOreSet("gonluin", STONE_STRENGTH, null);
    public static OreRockSet DOLOMITE = registerOreSet("dolomite", STONE_STRENGTH, null);
    public static OreRockSet CALCITE = registerOreSet("calcite", STONE_STRENGTH, null);

    // Vanilla
    public static CompleteOreRockSet DEEPSLATE = registerVanillaOreSet("deepslate_", DEEPSLATE_STRENGTH, null);
    public static CompleteOreRockSet STONE = registerVanillaOreSet("", STONE_STRENGTH, null);

    public static OreRockSet[] sets = new OreRockSet[] {
            ASHEN_ROCK,
            BLUE_ROCK,
            DOLOMITE,
            CALCITE,
    };

    public static CompleteOreRockSet[] vanillaSets = new CompleteOreRockSet[] {
            DEEPSLATE,
            STONE
    };

    public record OreRockSet(Block iron_ore, Block gold_ore, Block copper_ore, Block coal_ore, Block tin_ore, Block lead_ore, Block silver_ore) {
    }

    public record CompleteOreRockSet(Block tin_ore, Block lead_ore, Block silver_ore) {
    }

    public static OreRockSet registerOreSet(String rockName, float strength_mult, Block source) {
        Block iron_ore = ModNatureBlocks.registerBlock(
                rockName + "_iron_ore", new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(
                        STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool().dropsLike(Blocks.IRON_ORE)), false);

        Block gold_ore = ModNatureBlocks.registerBlock(
                rockName + "_gold_ore", new Block(FabricBlockSettings.copyOf(Blocks.GOLD_ORE).strength(
                        STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool().dropsLike(Blocks.GOLD_ORE)), false);

        Block copper_ore = ModNatureBlocks.registerBlock(
                rockName + "_copper_ore", new Block(FabricBlockSettings.copyOf(Blocks.COPPER_ORE).strength(
                        STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool().dropsLike(Blocks.COPPER_ORE)), false);

        Block coal_ore = ModNatureBlocks.registerBlock(
                rockName + "_coal_ore", new Block(FabricBlockSettings.copyOf(Blocks.COAL_ORE).strength(
                        STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool().dropsLike(Blocks.COAL_ORE)), false);

        Block tin_ore = ModNatureBlocks.registerBlock(
                rockName + "_tin_ore", new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(
                        STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool()), false);

        Block lead_ore = ModNatureBlocks.registerBlock(
                rockName + "_lead_ore", new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(
                        STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool()), false);

        Block silver_ore = ModNatureBlocks.registerBlock(
                rockName + "_silver_ore", new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(
                        STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool()), false);

        return new OreRockSet(iron_ore, gold_ore, copper_ore, coal_ore, tin_ore, lead_ore, silver_ore);
    }

    public static CompleteOreRockSet registerVanillaOreSet(String rockname, float strength_mult, Block source) {
        Block tin_ore = ModNatureBlocks.registerBlock(
                rockname + "tin_ore", new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(
                        STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool()), false);

        Block lead_ore = ModNatureBlocks.registerBlock(
                rockname + "lead_ore", new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(
                        STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool()), false);

        Block silver_ore = ModNatureBlocks.registerBlock(
                rockname + "silver_ore", new Block(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(
                        STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool()), false);

        return new CompleteOreRockSet(tin_ore, lead_ore, silver_ore);
    }




    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.debug("Registering OreSets for " + MiddleEarth.MOD_ID);
    }
}
