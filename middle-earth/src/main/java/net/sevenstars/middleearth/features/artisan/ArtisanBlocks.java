package net.sevenstars.middleearth.features.artisan;

import net.minecraft.block.*;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.features.artisan.block.ArtisanTable;

public class ArtisanBlocks {
    public static final Block ARTISAN_TABLE = DecorativeBlockRegistryME.registerBlock("artisan_table",
            ArtisanTable::new, AbstractBlock.Settings.copy(Blocks.SMITHING_TABLE).nonOpaque());

    public static final Block ORCISH_ARTISAN_TABLE = DecorativeBlockRegistryME.registerBlock("orcish_artisan_table",
            ArtisanTable::new, AbstractBlock.Settings.copy(Blocks.SMITHING_TABLE).nonOpaque());
}
