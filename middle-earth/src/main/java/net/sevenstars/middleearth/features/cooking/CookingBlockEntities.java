package net.sevenstars.middleearth.features.cooking;

import net.minecraft.block.entity.BlockEntityType;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;
import net.sevenstars.middleearth.features.cooking.block.plate.PlateBlockEntity;

public class CookingBlockEntities {
    public static BlockEntityType<PlateBlockEntity> PLATE = BlockEntityRegistryME.register("plate", PlateBlockEntity::new,
            CookingBlocks.SILVER_PLATE, CookingBlocks.CERAMIC_PLATE, CookingBlocks.ROTTEN_PLATE);

    public static void register() {
    }
}
