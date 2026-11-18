package net.sevenstars.middleearth.features.storage;

import net.minecraft.block.entity.BlockEntityType;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;
import net.sevenstars.middleearth.features.storage.block.coffers.BeechCofferBlockEntity;
import net.sevenstars.middleearth.features.storage.block.coffers.ChestnutCofferBlockEntity;
import net.sevenstars.middleearth.features.storage.block.coffers.FirCofferBlockEntity;
import net.sevenstars.middleearth.features.storage.block.coffers.LarchCofferBlockEntity;
import net.sevenstars.middleearth.features.storage.block.coffers.OakCofferBlockEntity;
import net.sevenstars.middleearth.features.storage.block.coffers.PineCofferBlockEntity;
import net.sevenstars.middleearth.features.storage.block.coffers.SpruceCofferBlockEntity;
import net.sevenstars.middleearth.features.storage.block.coffers.WillowCofferBlockEntity;
import net.sevenstars.middleearth.features.storage.block.reinforcedChest.ReinforcedChestBlockEntity;
import net.sevenstars.middleearth.features.storage.block.sack.SackBlockEntity;
import net.sevenstars.middleearth.features.storage.block.wood_pile.WoodPileBlockEntity;

public class StorageBlockEntities {
    public static BlockEntityType<LarchCofferBlockEntity> LARCH_COFFER = BlockEntityRegistryME.register("larch_coffer", LarchCofferBlockEntity::new, StorageBlocks.LARCH_COFFER);

    public static BlockEntityType<PineCofferBlockEntity> PINE_COFFER = BlockEntityRegistryME.register("pine_coffer", PineCofferBlockEntity::new, StorageBlocks.PINE_COFFER);

    public static BlockEntityType<SpruceCofferBlockEntity> SPRUCE_COFFER = BlockEntityRegistryME.register("spruce_coffer", SpruceCofferBlockEntity::new, StorageBlocks.SPRUCE_COFFER);

    public static BlockEntityType<FirCofferBlockEntity> FIR_COFFER = BlockEntityRegistryME.register("fir_coffer", FirCofferBlockEntity::new, StorageBlocks.FIR_COFFER);

    public static BlockEntityType<BeechCofferBlockEntity> BEECH_COFFER = BlockEntityRegistryME.register("beech_coffer", BeechCofferBlockEntity::new, StorageBlocks.BEECH_COFFER);

    public static BlockEntityType<ChestnutCofferBlockEntity> CHESTNUT_COFFER = BlockEntityRegistryME.register("chestnut_coffer", ChestnutCofferBlockEntity::new, StorageBlocks.CHESTNUT_COFFER);

    public static BlockEntityType<OakCofferBlockEntity> OAK_COFFER = BlockEntityRegistryME.register("oak_coffer", OakCofferBlockEntity::new, StorageBlocks.OAK_COFFER);

    public static BlockEntityType<WillowCofferBlockEntity> WILLOW_COFFER = BlockEntityRegistryME.register("willow_coffer", WillowCofferBlockEntity::new, StorageBlocks.WILLOW_COFFER);

    public static BlockEntityType<SackBlockEntity> SACK = BlockEntityRegistryME.register("sack", SackBlockEntity::new, StorageBlocks.SACK);

    public static BlockEntityType<ReinforcedChestBlockEntity> REINFORCED_CHEST = BlockEntityRegistryME.register("reinforced_chest", ReinforcedChestBlockEntity::new,
            StorageBlocks.REINFORCED_CHEST);

    public static BlockEntityType<WoodPileBlockEntity> WOOD_PILE = BlockEntityRegistryME.register("wood_pile", WoodPileBlockEntity::new,
            StorageBlocks.WOOD_PILE);
}
