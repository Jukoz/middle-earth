package net.sevenstars.middleearth.features.smithing;

import net.minecraft.block.entity.BlockEntityType;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;
import net.sevenstars.middleearth.features.smithing.block.bellows.BellowsBlockEntity;
import net.sevenstars.middleearth.features.smithing.block.forge.ForgeBlockEntity;
import net.sevenstars.middleearth.features.smithing.block.shapingAnvil.ShapingAnvilBlockEntity;
import net.sevenstars.middleearth.features.smithing.block.shapingAnvil.stoneanvil.StoneAnvilBlockEntity;

public class SmithingBlockEntities {
    public static BlockEntityType<ForgeBlockEntity> FORGE = BlockEntityRegistryME.register("forge", ForgeBlockEntity::new,
            SmithingBlocks.FORGE);
    public static BlockEntityType<StoneAnvilBlockEntity> STONE_ANVIL = BlockEntityRegistryME.register("stone_anvil", StoneAnvilBlockEntity::new,
            SmithingBlocks.STONE_ANVIL);
    public static BlockEntityType<ShapingAnvilBlockEntity> TREATED_ANVIL = BlockEntityRegistryME.register("treated_anvil", ShapingAnvilBlockEntity::new,
            SmithingBlocks.TREATED_ANVIL,
            SmithingBlocks.DWARVEN_TREATED_ANVIL,
            SmithingBlocks.ELVEN_TREATED_ANVIL,
            SmithingBlocks.ORCISH_TREATED_ANVIL);
    public static BlockEntityType<BellowsBlockEntity> BELLOWS = BlockEntityRegistryME.register("bellows", BellowsBlockEntity::new,
            SmithingBlocks.BELLOWS);
}
