package net.sevenstars.middleearth.features.smithing;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.features.smithing.block.bellows.BellowsBlock;
import net.sevenstars.middleearth.features.smithing.block.forge.ForgeBlock;
import net.sevenstars.middleearth.features.smithing.block.shapingAnvil.dwarvenTreatedAnvil.DwarvenShapingAnvilBlock;
import net.sevenstars.middleearth.features.smithing.block.shapingAnvil.elvenTreatedAnvil.ElvenTreatedAnvilblock;
import net.sevenstars.middleearth.features.smithing.block.shapingAnvil.orcishTreatedAnvil.OrcishTreatedAnvilblock;
import net.sevenstars.middleearth.features.smithing.block.shapingAnvil.stoneanvil.StoneAnvilBlock;
import net.sevenstars.middleearth.features.smithing.block.shapingAnvil.treatedAnvil.TreatedAnvilblock;

public class SmithingBlocks {
    public static final Block FORGE = DecorativeBlockRegistryME.registerBlock("forge",
            ForgeBlock::new, AbstractBlock.Settings.copy(Blocks.BRICKS).luminance(state -> state.get(Properties.LIT) ? 15 : 0).strength(1.65f).requiresTool());

    public static final Block STONE_ANVIL = DecorativeBlockRegistryME.registerBlock("stone_anvil",
            StoneAnvilBlock::new, AbstractBlock.Settings.copy(Blocks.STONE).requiresTool().nonOpaque());

    public static final Block TREATED_ANVIL = DecorativeBlockRegistryME.registerBlock("treated_anvil",
            TreatedAnvilblock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(1.65f).requiresTool().nonOpaque());
    public static final Block DWARVEN_TREATED_ANVIL = DecorativeBlockRegistryME.registerBlock("dwarven_treated_anvil",
            DwarvenShapingAnvilBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(1.65f).requiresTool().nonOpaque());
    public static final Block ELVEN_TREATED_ANVIL = DecorativeBlockRegistryME.registerBlock("elven_treated_anvil",
            ElvenTreatedAnvilblock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(1.65f).requiresTool().nonOpaque());
    public static final Block ORCISH_TREATED_ANVIL = DecorativeBlockRegistryME.registerBlock("orcish_treated_anvil",
            OrcishTreatedAnvilblock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK).strength(1.65f).requiresTool().nonOpaque());

    public static final Block BELLOWS = DecorativeBlockRegistryME.registerBlock("bellows",
            BellowsBlock::new, AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque());

    public static void register() {
    }
}
