package net.sevenstars.middleearth.features.inscription;

import net.minecraft.block.*;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.features.inscription.block.InscriptionTableblock;

public class InscriptionBlocks {
    public static final Block INSCRIPTION_TABLE = DecorativeBlockRegistryME.registerBlock("inscription_table",
            InscriptionTableblock::new, AbstractBlock.Settings.copy(Blocks.SMITHING_TABLE).nonOpaque());
}
