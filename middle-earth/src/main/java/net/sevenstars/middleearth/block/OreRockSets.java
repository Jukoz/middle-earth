package net.sevenstars.middleearth.block;

import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.Arrays;
import java.util.List;

public class OreRockSets {
    public static final float STONE_STRENGTH = 2.0f;
    public static final float DEEPSLATE_STRENGTH = 2.5f;
    public static final float NURGON_STRENGTH = 3.0f;
    public static final float MEDGON_STRENGTH = 4.0f;

    public static OreRockSet STONE = registerOreSet("", STONE_STRENGTH,
            List.of(ORES.TIN_ORE), Blocks.STONE);
    public static OreRockSet GONLUIN = registerOreSet("gonluin_", STONE_STRENGTH,
            Arrays.asList(ORES.COAL_ORE, ORES.COPPER_ORE, ORES.TIN_ORE), StoneBlockSets.GONLUIN.base());
    public static OreRockSet ASHEN = registerOreSet("ashen_", STONE_STRENGTH,
            Arrays.asList(ORES.COAL_ORE, ORES.COPPER_ORE, ORES.TIN_ORE), StoneBlockSets.ASHEN_STONE.base());
    public static OreRockSet LIMESTONE = registerOreSet("limestone_", STONE_STRENGTH,
            Arrays.asList(ORES.COAL_ORE, ORES.COPPER_ORE, ORES.TIN_ORE), StoneBlockSets.LIMESTONE.base());
    public static OreRockSet CALCITE = registerOreSet("calcite_", STONE_STRENGTH,
            Arrays.asList(ORES.COAL_ORE, ORES.COPPER_ORE, ORES.TIN_ORE), Blocks.CALCITE);
    public static OreRockSet SLATE = registerOreSet("slate_", STONE_STRENGTH,
            Arrays.asList(ORES.COAL_ORE, ORES.COPPER_ORE, ORES.TIN_ORE), Blocks.CALCITE);
    public static OreRockSet IRONSTONE = registerOreSet("ironstone_", STONE_STRENGTH,
            Arrays.asList(ORES.COAL_ORE, ORES.COPPER_ORE, ORES.TIN_ORE), Blocks.CALCITE);

    public static OreRockSet DEEPSLATE = registerOreSet("deepslate_", DEEPSLATE_STRENGTH,
            Arrays.asList(ORES.TIN_ORE, ORES.LEAD_ORE), Blocks.DEEPSLATE);

    public static OreRockSet NURGON = registerOreSet("nurgon_", NURGON_STRENGTH,
            Arrays.asList(ORES.TIN_ORE, ORES.LEAD_ORE, ORES.SILVER_ORE, ORES.GOLD_ORE, ORES.IRON_ORE), StoneBlockSets.NURGON.base());

    public static OreRockSet MEDGON = registerOreSet("medgon_", MEDGON_STRENGTH,
            Arrays.asList(ORES.LEAD_ORE, ORES.SILVER_ORE, ORES.GOLD_ORE, ORES.IRON_ORE, ORES.MITHRIL_ORE), StoneBlockSets.MEDGON.base());

    public static OreRockSet[] sets = new OreRockSet[] {
            STONE,
            GONLUIN,
            ASHEN,
            LIMESTONE,
            CALCITE,
            SLATE,
            IRONSTONE,
            DEEPSLATE,
            NURGON,
            MEDGON,
    };

    public record OreRockSet(Block coal_ore, Block copper_ore, Block tin_ore, Block lead_ore, Block silver_ore, Block gold_ore, Block iron_ore, Block mithril_ore, Block origin) {
    }


    public static OreRockSet registerOreSet(String rockName, float strength_mult, List<ORES> ores, Block origin) {

        Block coal_ore = null;
        Block copper_ore = null;
        Block tin_ore = null;
        Block lead_ore = null;
        Block silver_ore = null;
        Block gold_ore = null;
        Block iron_ore = null;
        Block mithril_ore = null;

        if(ores.contains(ORES.COAL_ORE)){
            coal_ore = ModNatureBlocks.registerBlock(
                    rockName + "coal_ore", (settings) -> new ExperienceDroppingBlock(UniformIntProvider.create(0, 2), settings),
                    AbstractBlock.Settings.copy(Blocks.COAL_ORE).strength(STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool(), true);
        }

        if(ores.contains(ORES.COPPER_ORE)) {
            copper_ore = ModNatureBlocks.registerBlock(
                    rockName + "copper_ore", Block::new, AbstractBlock.Settings.copy(Blocks.COPPER_ORE).strength(
                            STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool(), true);
        }

        if(ores.contains(ORES.TIN_ORE)) {
            tin_ore = ModNatureBlocks.registerBlock(
                    rockName + "tin_ore", Block::new, AbstractBlock.Settings.copy(Blocks.IRON_ORE).strength(
                            STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool(), true);
        }

        if(ores.contains(ORES.LEAD_ORE)) {
            lead_ore = ModNatureBlocks.registerBlock(
                    rockName + "lead_ore", Block::new, AbstractBlock.Settings.copy(Blocks.IRON_ORE).strength(
                            STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool(), true);
        }

        if(ores.contains(ORES.SILVER_ORE)) {
            silver_ore = ModNatureBlocks.registerBlock(
                    rockName + "silver_ore", Block::new, AbstractBlock.Settings.copy(Blocks.IRON_ORE).strength(
                            STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool(), true);
        }

        if(ores.contains(ORES.GOLD_ORE)) {
            gold_ore = ModNatureBlocks.registerBlock(
                    rockName + "gold_ore", Block::new, AbstractBlock.Settings.copy(Blocks.GOLD_ORE).strength(
                            STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool(), true);
        }

        if(ores.contains(ORES.IRON_ORE)) {
            iron_ore = ModNatureBlocks.registerBlock(
                    rockName + "iron_ore", Block::new, AbstractBlock.Settings.copy(Blocks.IRON_ORE).strength(
                            STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool(), true);
        }

        if(ores.contains(ORES.MITHRIL_ORE)) {
            mithril_ore = ModNatureBlocks.registerBlock(
                    rockName + "mithril_ore", Block::new, AbstractBlock.Settings.copy(Blocks.IRON_ORE).strength(
                            STONE_STRENGTH * strength_mult, 3*strength_mult).requiresTool(), true);
        }

        return new OreRockSet(coal_ore, copper_ore, tin_ore, lead_ore, silver_ore, gold_ore, iron_ore, mithril_ore, origin);
    }

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.logDebugMsg("Registering OreSets for " + MiddleEarth.MOD_ID);
    }

    enum ORES{
        COAL_ORE,
        COPPER_ORE,
        TIN_ORE,
        LEAD_ORE,
        SILVER_ORE,
        GOLD_ORE,
        IRON_ORE,
        MITHRIL_ORE,
        ;
    }
}
