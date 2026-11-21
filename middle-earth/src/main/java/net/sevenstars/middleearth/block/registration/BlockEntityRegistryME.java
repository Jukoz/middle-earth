package net.sevenstars.middleearth.block.registration;

import net.sevenstars.middleearth.features.storage.StorageBlocks;


import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.beds.CustomBedBlockEntity;
import net.sevenstars.middleearth.block.special.fireBlocks.*;
import net.sevenstars.middleearth.features.cooking.block.plate.PlateBlockEntity;
import net.sevenstars.middleearth.features.storage.block.reinforcedChest.ReinforcedChestBlockEntity;
import net.sevenstars.middleearth.features.storage.block.sack.SackBlockEntity;
import net.sevenstars.middleearth.block.special.skull.OldSkullBlockEntity;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.block.special.structureManager.nest.StructureNestBlockEntity;
import net.sevenstars.middleearth.features.storage.block.wood_pile.WoodPileBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

public class BlockEntityRegistryME {
    public static BlockEntityType<StructureManagerBlockEntity> STRUCTURE_MANAGER = register("structure_manager", StructureManagerBlockEntity::new,
            DecorativeBlockRegistryME.STRUCTURE_MANAGER,
            DecorativeBlockRegistryME.ORC_STRUCTURE_MANAGER);
    public static BlockEntityType<StructureManagerBlockEntity> ORC_STRUCTURE_MANAGER = register("orc_structure_manager", StructureManagerBlockEntity::new,
            DecorativeBlockRegistryME.ORC_STRUCTURE_MANAGER);
    public static BlockEntityType<StructureNestBlockEntity> STRUCTURE_NEST = register("structure_nest", StructureNestBlockEntity::new,
            DecorativeBlockRegistryME.STRUCTURE_NEST);


    public static BlockEntityType<OldSkullBlockEntity> OLD_SKULL = register("old_skull", OldSkullBlockEntity::new, DecorativeBlockRegistryME.OLD_SKULL);

    public static BlockEntityType<SackBlockEntity> SACK = register("sack", SackBlockEntity::new,
            DecorativeBlockRegistryME.SACK,
            DecorativeBlockRegistryME.WHITE_SACK,
            DecorativeBlockRegistryME.BLACK_SACK,
            DecorativeBlockRegistryME.BLUE_SACK,
            DecorativeBlockRegistryME.BROWN_SACK,
            DecorativeBlockRegistryME.CYAN_SACK,
            DecorativeBlockRegistryME.GREEN_SACK,
            DecorativeBlockRegistryME.GRAY_SACK,
            DecorativeBlockRegistryME.LIGHT_BLUE_SACK,
            DecorativeBlockRegistryME.LIGHT_GRAY_SACK,
            DecorativeBlockRegistryME.LIME_SACK,
            DecorativeBlockRegistryME.MAGENTA_SACK,
            DecorativeBlockRegistryME.ORANGE_SACK,
            DecorativeBlockRegistryME.PINK_SACK,
            DecorativeBlockRegistryME.PURPLE_SACK,
            DecorativeBlockRegistryME.RED_SACK,
            DecorativeBlockRegistryME.YELLOW_SACK);


    /*public static BlockEntityType<CrockpotBlockEntity> CROCKPOT = register("crockpot", CrockpotBlockEntity::new,
            ModDecorativeBlocks.CERAMIC_CROCKPOT,
            ModDecorativeBlocks.CROCKPOT);*/
    public static BlockEntityType<BrazierBlockEntity> BIG_BRAZIER = register("big_brazier", BrazierBlockEntity::new,
            DecorativeBlockRegistryME.BIG_BRAZIER);
    public static BlockEntityType<SmallBrazierBlockEntity> SMALL_BRAZIER = register("small_brazier", SmallBrazierBlockEntity::new,
            DecorativeBlockRegistryME.SMALL_BRAZIER);
    public static BlockEntityType<GildedBrazierBlockEntity> GILDED_BIG_BRAZIER = register("gilded_big_brazier", GildedBrazierBlockEntity::new,
            DecorativeBlockRegistryME.GILDED_BIG_BRAZIER);
    public static BlockEntityType<GildedSmallBrazierBlockEntity> GILDED_SMALL_BRAZIER = register("gilded_small_brazier", GildedSmallBrazierBlockEntity::new,
            DecorativeBlockRegistryME.GILDED_SMALL_BRAZIER);
    public static BlockEntityType<FireBowlBlockEntity> FIRE_BOWL = register("fire_bowl", FireBowlBlockEntity::new,
            DecorativeBlockRegistryME.FIRE_BOWL);
    public static BlockEntityType<BonfireBlockEntity> BONFIRE = register("bonfire", BonfireBlockEntity::new,
            DecorativeBlockRegistryME.BONFIRE);
    public static BlockEntityType<ChimneyBlockEntity> CHIMNEY = register("chimney", ChimneyBlockEntity::new,
            DecorativeBlockRegistryME.CHIMNEY);
    public static BlockEntityType<CustomBedBlockEntity> BED = register("bed", CustomBedBlockEntity::new,
            DecorativeBlockRegistryME.FANCY_BED,
            DecorativeBlockRegistryME.FUR_BED,
            DecorativeBlockRegistryME.STRAW_BED);

    public static void registerBlockEntities() {
        BlockEntityType.BARREL.addSupportedBlock(StorageBlocks.SMALL_CRATE);
        BlockEntityType.BARREL.addSupportedBlock(StorageBlocks.THIN_BARREL);
        BlockEntityType.DECORATED_POT.addSupportedBlock(DecorativeBlockRegistryME.AMPHORA);
        BlockEntityType.DECORATED_POT.addSupportedBlock(DecorativeBlockRegistryME.BROWN_AMPHORA);
        BlockEntityType.DECORATED_POT.addSupportedBlock(DecorativeBlockRegistryME.BROWN_JUG);
        BlockEntityType.DECORATED_POT.addSupportedBlock(DecorativeBlockRegistryME.GRAY_POT);
        BlockEntityType.DECORATED_POT.addSupportedBlock(DecorativeBlockRegistryME.LARGE_JUG);
        BlockEntityType.DECORATED_POT.addSupportedBlock(DecorativeBlockRegistryME.GRAY_VASE);
        BlockEntityType.DECORATED_POT.addSupportedBlock(DecorativeBlockRegistryME.BROWN_JAR);
        BlockEntityType.DECORATED_POT.addSupportedBlock(DecorativeBlockRegistryME.CLAY_JAR);
        BlockEntityType.DECORATED_POT.addSupportedBlock(DecorativeBlockRegistryME.GRAY_JAR);
        BlockEntityType.DECORATED_POT.addSupportedBlock(DecorativeBlockRegistryME.BROWN_FAT_POT);
        BlockEntityType.DECORATED_POT.addSupportedBlock(DecorativeBlockRegistryME.FAT_POT);
        BlockEntityType.DECORATED_POT.addSupportedBlock(DecorativeBlockRegistryME.GRAY_FAT_POT);
        BlockEntityType.DECORATED_POT.addSupportedBlock(DecorativeBlockRegistryME.POT_OF_GOLD);

        BlockEntityType.LECTERN.addSupportedBlock(DecorativeBlockRegistryME.STONE_LECTERN);
        BlockEntityType.CHISELED_BOOKSHELF.addSupportedBlock(DecorativeBlockRegistryME.CHISELED_DOLOMITE_BOOKSHELF);

        BlockEntityType.TRIAL_SPAWNER.addSupportedBlock(DecorativeBlockRegistryME.BRIGAND_TRIAL_SPAWNER);
        BlockEntityType.TRIAL_SPAWNER.addSupportedBlock(DecorativeBlockRegistryME.SPIDER_TRIAL_SPAWNER);
        BlockEntityType.VAULT.addSupportedBlock(DecorativeBlockRegistryME.BRIGAND_VAULT);
        BlockEntityType.VAULT.addSupportedBlock(DecorativeBlockRegistryME.SPIDER_VAULT);
    }

    public static <T extends BlockEntity> BlockEntityType<T> register(String name,
                                                                       FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
                                                                       Block... blocks) {
        Identifier id = Identifier.of(MiddleEarth.MOD_ID, name);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.BLOCK_ENTITY_TYPE, name));

        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
}
