package net.sevenstars.middleearth.block.registration;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.beds.CustomBedBlockEntity;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlockEntity;
import net.sevenstars.middleearth.block.special.coffers.*;
import net.sevenstars.middleearth.block.special.fireBlocks.*;
import net.sevenstars.middleearth.block.special.forge.ForgeBlockEntity;
import net.sevenstars.middleearth.block.special.plate.PlateBlockEntity;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestBlockEntity;
import net.sevenstars.middleearth.block.special.sack.SackBlockEntity;
import net.sevenstars.middleearth.block.special.shapingAnvil.ShapingAnvilBlockEntity;
import net.sevenstars.middleearth.block.special.shapingAnvil.stoneanvil.StoneAnvilBlockEntity;
import net.sevenstars.middleearth.block.special.skull.OldSkullBlockEntity;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.block.special.structureManager.nest.StructureNestBlockEntity;
import net.sevenstars.middleearth.block.special.wood_pile.WoodPileBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

public class BlockEntityRegistryME {
    public static BlockEntityType<ForgeBlockEntity> FORGE = register("forge", ForgeBlockEntity::new,
            DecorativeBlockRegistryME.FORGE);
    public static BlockEntityType<StoneAnvilBlockEntity> STONE_ANVIL = register("stone_anvil", StoneAnvilBlockEntity::new,
            DecorativeBlockRegistryME.STONE_ANVIL);
    public static BlockEntityType<ShapingAnvilBlockEntity> TREATED_ANVIL = register("treated_anvil", ShapingAnvilBlockEntity::new,
            DecorativeBlockRegistryME.TREATED_ANVIL,
            DecorativeBlockRegistryME.DWARVEN_TREATED_ANVIL,
            DecorativeBlockRegistryME.ELVEN_TREATED_ANVIL,
            DecorativeBlockRegistryME.ORCISH_TREATED_ANVIL);
    public static BlockEntityType<StructureManagerBlockEntity> STRUCTURE_MANAGER = register("structure_manager", StructureManagerBlockEntity::new,
            DecorativeBlockRegistryME.STRUCTURE_MANAGER,
            DecorativeBlockRegistryME.ORC_STRUCTURE_MANAGER);
    public static BlockEntityType<StructureManagerBlockEntity> ORC_STRUCTURE_MANAGER = register("orc_structure_manager", StructureManagerBlockEntity::new,
            DecorativeBlockRegistryME.ORC_STRUCTURE_MANAGER);
    public static BlockEntityType<StructureNestBlockEntity> STRUCTURE_NEST = register("structure_nest", StructureNestBlockEntity::new,
            DecorativeBlockRegistryME.STRUCTURE_NEST);

    public static BlockEntityType<LarchCofferBlockEntity> LARCH_COFFER = register("larch_coffer", LarchCofferBlockEntity::new, DecorativeBlockRegistryME.LARCH_COFFER);
    public static BlockEntityType<PineCofferBlockEntity> PINE_COFFER = register("pine_coffer", PineCofferBlockEntity::new, DecorativeBlockRegistryME.PINE_COFFER);
    public static BlockEntityType<SpruceCofferBlockEntity> SPRUCE_COFFER = register("spruce_coffer", SpruceCofferBlockEntity::new, DecorativeBlockRegistryME.SPRUCE_COFFER);
    public static BlockEntityType<FirCofferBlockEntity> FIR_COFFER = register("fir_coffer", FirCofferBlockEntity::new, DecorativeBlockRegistryME.FIR_COFFER);
    public static BlockEntityType<BeechCofferBlockEntity> BEECH_COFFER = register("beech_coffer", BeechCofferBlockEntity::new, DecorativeBlockRegistryME.BEECH_COFFER);
    public static BlockEntityType<ChestnutCofferBlockEntity> CHESTNUT_COFFER = register("chestnut_coffer", ChestnutCofferBlockEntity::new, DecorativeBlockRegistryME.CHESTNUT_COFFER);
    public static BlockEntityType<OakCofferBlockEntity> OAK_COFFER = register("oak_coffer", OakCofferBlockEntity::new, DecorativeBlockRegistryME.OAK_COFFER);
    public static BlockEntityType<WillowCofferBlockEntity> WILLOW_COFFER = register("willow_coffer", WillowCofferBlockEntity::new, DecorativeBlockRegistryME.WILLOW_COFFER);

    public static BlockEntityType<OldSkullBlockEntity> OLD_SKULL = register("old_skull", OldSkullBlockEntity::new, DecorativeBlockRegistryME.OLD_SKULL);

    public static BlockEntityType<SackBlockEntity> SACK = register("sack", SackBlockEntity::new, DecorativeBlockRegistryME.SACK);

    public static BlockEntityType<ReinforcedChestBlockEntity> REINFORCED_CHEST = register("reinforced_chest", ReinforcedChestBlockEntity::new,
            DecorativeBlockRegistryME.REINFORCED_CHEST);
    public static BlockEntityType<BellowsBlockEntity> BELLOWS = register("bellows", BellowsBlockEntity::new,
            DecorativeBlockRegistryME.BELLOWS);
    /*public static BlockEntityType<CrockpotBlockEntity> CROCKPOT = register("crockpot", CrockpotBlockEntity::new,
            ModDecorativeBlocks.CERAMIC_CROCKPOT,
            ModDecorativeBlocks.CROCKPOT);*/
    public static BlockEntityType<PlateBlockEntity> PLATE = register("plate", PlateBlockEntity::new,
            DecorativeBlockRegistryME.SILVER_PLATE, DecorativeBlockRegistryME.CERAMIC_PLATE, DecorativeBlockRegistryME.ROTTEN_PLATE);
    public static BlockEntityType<WoodPileBlockEntity> WOOD_PILE = register("wood_pile", WoodPileBlockEntity::new,
            DecorativeBlockRegistryME.WOOD_PILE);
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
        BlockEntityType.BARREL.addSupportedBlock(DecorativeBlockRegistryME.SMALL_CRATE);
        BlockEntityType.BARREL.addSupportedBlock(DecorativeBlockRegistryME.THIN_BARREL);
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

        BlockEntityType.TRIAL_SPAWNER.addSupportedBlock(DecorativeBlockRegistryME.BRIGAND_TRIAL_SPAWNER);
        BlockEntityType.TRIAL_SPAWNER.addSupportedBlock(DecorativeBlockRegistryME.SPIDER_TRIAL_SPAWNER);
        BlockEntityType.VAULT.addSupportedBlock(DecorativeBlockRegistryME.BRIGAND_VAULT);
        BlockEntityType.VAULT.addSupportedBlock(DecorativeBlockRegistryME.SPIDER_VAULT);
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name,
                                                                       FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
                                                                       Block... blocks) {
        Identifier id = Identifier.of(MiddleEarth.MOD_ID, name);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.BLOCK_ENTITY_TYPE, name));

        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
}
