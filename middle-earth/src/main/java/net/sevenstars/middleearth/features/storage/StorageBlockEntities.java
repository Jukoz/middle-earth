package net.sevenstars.middleearth.features.storage;

import net.sevenstars.middleearth.features.storage.block.coffers.CofferBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;
import net.sevenstars.middleearth.features.storage.block.reinforcedChest.ReinforcedChestBlockEntity;
import net.sevenstars.middleearth.features.storage.block.sack.SackBlockEntity;
import net.sevenstars.middleearth.features.storage.block.wood_pile.WoodPileBlockEntity;

public class StorageBlockEntities {
    public static BlockEntityType<CofferBlockEntity> LARCH_COFFER = BlockEntityRegistryME.register("larch_coffer", (pos, state) -> new CofferBlockEntity(StorageBlockEntities.LARCH_COFFER, pos, state), StorageBlocks.LARCH_COFFER);

    public static BlockEntityType<CofferBlockEntity> PINE_COFFER = BlockEntityRegistryME.register("pine_coffer", (pos, state) -> new CofferBlockEntity(StorageBlockEntities.PINE_COFFER, pos, state), StorageBlocks.PINE_COFFER);

    public static BlockEntityType<CofferBlockEntity> SPRUCE_COFFER = BlockEntityRegistryME.register("spruce_coffer", (pos, state) -> new CofferBlockEntity(StorageBlockEntities.SPRUCE_COFFER, pos, state), StorageBlocks.SPRUCE_COFFER);

    public static BlockEntityType<CofferBlockEntity> FIR_COFFER = BlockEntityRegistryME.register("fir_coffer", (pos, state) -> new CofferBlockEntity(StorageBlockEntities.FIR_COFFER, pos, state), StorageBlocks.FIR_COFFER);

    public static BlockEntityType<CofferBlockEntity> BEECH_COFFER = BlockEntityRegistryME.register("beech_coffer", (pos, state) -> new CofferBlockEntity(StorageBlockEntities.BEECH_COFFER, pos, state), StorageBlocks.BEECH_COFFER);

    public static BlockEntityType<CofferBlockEntity> CHESTNUT_COFFER = BlockEntityRegistryME.register("chestnut_coffer", (pos, state) -> new CofferBlockEntity(StorageBlockEntities.CHESTNUT_COFFER, pos, state), StorageBlocks.CHESTNUT_COFFER);

    public static BlockEntityType<CofferBlockEntity> OAK_COFFER = BlockEntityRegistryME.register("oak_coffer", (pos, state) -> new CofferBlockEntity(StorageBlockEntities.OAK_COFFER, pos, state), StorageBlocks.OAK_COFFER);

    public static BlockEntityType<CofferBlockEntity> WILLOW_COFFER = BlockEntityRegistryME.register("willow_coffer", (pos, state) -> new CofferBlockEntity(StorageBlockEntities.WILLOW_COFFER, pos, state), StorageBlocks.WILLOW_COFFER);

    public static BlockEntityType<SackBlockEntity> SACK = BlockEntityRegistryME.register("sack", SackBlockEntity::new,
            StorageBlocks.SACK,
            StorageBlocks.WHITE_SACK,
            StorageBlocks.BLACK_SACK,
            StorageBlocks.BLUE_SACK,
            StorageBlocks.BROWN_SACK,
            StorageBlocks.CYAN_SACK,
            StorageBlocks.GREEN_SACK,
            StorageBlocks.GRAY_SACK,
            StorageBlocks.LIGHT_BLUE_SACK,
            StorageBlocks.LIGHT_GRAY_SACK,
            StorageBlocks.LIME_SACK,
            StorageBlocks.MAGENTA_SACK,
            StorageBlocks.ORANGE_SACK,
            StorageBlocks.PINK_SACK,
            StorageBlocks.PURPLE_SACK,
            StorageBlocks.RED_SACK,
            StorageBlocks.YELLOW_SACK);

    public static BlockEntityType<ReinforcedChestBlockEntity> REINFORCED_CHEST = BlockEntityRegistryME.register("reinforced_chest", ReinforcedChestBlockEntity::new,
            StorageBlocks.REINFORCED_CHEST);

    public static BlockEntityType<WoodPileBlockEntity> WOOD_PILE = BlockEntityRegistryME.register("wood_pile", WoodPileBlockEntity::new,
            StorageBlocks.WOOD_PILE);
}
