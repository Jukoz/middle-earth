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
import net.sevenstars.middleearth.block.special.shapingAnvil.ShapingAnvilBlockEntity;
import net.sevenstars.middleearth.block.special.shapingAnvil.stoneanvil.StoneAnvilBlockEntity;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.block.special.structureManager.nest.StructureNestBlockEntity;
import net.sevenstars.middleearth.block.special.wood_pile.WoodPileBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.registries.RegistryAliasesME;

public class ModBlockEntities {
    public static BlockEntityType<ForgeBlockEntity> FORGE = register("forge", ForgeBlockEntity::new,
            ModDecorativeBlocks.FORGE);
    public static BlockEntityType<StoneAnvilBlockEntity> STONE_ANVIL = register("stone_anvil", StoneAnvilBlockEntity::new,
            ModDecorativeBlocks.STONE_ANVIL);
    public static BlockEntityType<ShapingAnvilBlockEntity> TREATED_ANVIL = register("treated_anvil", ShapingAnvilBlockEntity::new,
            ModDecorativeBlocks.TREATED_ANVIL,
            ModDecorativeBlocks.DWARVEN_TREATED_ANVIL,
            ModDecorativeBlocks.ELVEN_TREATED_ANVIL,
            ModDecorativeBlocks.ORCISH_TREATED_ANVIL);
    public static BlockEntityType<StructureManagerBlockEntity> STRUCTURE_MANAGER = register("structure_manager", StructureManagerBlockEntity::new,
            ModDecorativeBlocks.STRUCTURE_MANAGER);
    public static BlockEntityType<StructureNestBlockEntity> STRUCTURE_NEST = register("structure_nest", StructureNestBlockEntity::new,
            ModDecorativeBlocks.STRUCTURE_NEST);

    public static BlockEntityType<LarchCofferBlockEntity> LARCH_COFFER = register("larch_coffer", LarchCofferBlockEntity::new, ModDecorativeBlocks.LARCH_COFFER);
    public static BlockEntityType<PineCofferBlockEntity> PINE_COFFER = register("pine_coffer", PineCofferBlockEntity::new, ModDecorativeBlocks.PINE_COFFER);
    public static BlockEntityType<SpruceCofferBlockEntity> SPRUCE_COFFER = register("spruce_coffer", SpruceCofferBlockEntity::new, ModDecorativeBlocks.SPRUCE_COFFER);
    public static BlockEntityType<FirCofferBlockEntity> FIR_COFFER = register("fir_coffer", FirCofferBlockEntity::new, ModDecorativeBlocks.FIR_COFFER);
    public static BlockEntityType<BeechCofferBlockEntity> BEECH_COFFER = register("beech_coffer", BeechCofferBlockEntity::new, ModDecorativeBlocks.BEECH_COFFER);
    public static BlockEntityType<ChestnutCofferBlockEntity> CHESTNUT_COFFER = register("chestnut_coffer", ChestnutCofferBlockEntity::new, ModDecorativeBlocks.CHESTNUT_COFFER);
    public static BlockEntityType<OakCofferBlockEntity> OAK_COFFER = register("oak_coffer", OakCofferBlockEntity::new, ModDecorativeBlocks.OAK_COFFER);
    public static BlockEntityType<WillowCofferBlockEntity> WILLOW_COFFER = register("willow_coffer", WillowCofferBlockEntity::new, ModDecorativeBlocks.WILLOW_COFFER);

    public static BlockEntityType<ReinforcedChestBlockEntity> REINFORCED_CHEST = register("reinforced_chest", ReinforcedChestBlockEntity::new,
            ModDecorativeBlocks.REINFORCED_CHEST);
    public static BlockEntityType<BellowsBlockEntity> BELLOWS = register("bellows", BellowsBlockEntity::new,
            ModDecorativeBlocks.BELLOWS);
    /*public static BlockEntityType<CrockpotBlockEntity> CROCKPOT = register("crockpot", CrockpotBlockEntity::new,
            ModDecorativeBlocks.CERAMIC_CROCKPOT,
            ModDecorativeBlocks.CROCKPOT);*/
    public static BlockEntityType<PlateBlockEntity> PLATE = register("plate", PlateBlockEntity::new,
            ModDecorativeBlocks.SILVER_PLATE, ModDecorativeBlocks.CERAMIC_PLATE, ModDecorativeBlocks.ROTTEN_PLATE);
    public static BlockEntityType<WoodPileBlockEntity> WOOD_PILE = register("wood_pile", WoodPileBlockEntity::new,
            ModDecorativeBlocks.WOOD_PILE);
    public static BlockEntityType<BrazierBlockEntity> BIG_BRAZIER = register("big_brazier", BrazierBlockEntity::new,
            ModDecorativeBlocks.BIG_BRAZIER);
    public static BlockEntityType<SmallBrazierBlockEntity> SMALL_BRAZIER = register("small_brazier", SmallBrazierBlockEntity::new,
            ModDecorativeBlocks.SMALL_BRAZIER);
    public static BlockEntityType<GildedBrazierBlockEntity> GILDED_BIG_BRAZIER = register("gilded_big_brazier", GildedBrazierBlockEntity::new,
            ModDecorativeBlocks.GILDED_BIG_BRAZIER);
    public static BlockEntityType<GildedSmallBrazierBlockEntity> GILDED_SMALL_BRAZIER = register("gilded_small_brazier", GildedSmallBrazierBlockEntity::new,
            ModDecorativeBlocks.GILDED_SMALL_BRAZIER);
    public static BlockEntityType<FireBowlBlockEntity> FIRE_BOWL = register("fire_bowl", FireBowlBlockEntity::new,
            ModDecorativeBlocks.FIRE_BOWL);
    public static BlockEntityType<BonfireBlockEntity> BONFIRE = register("bonfire", BonfireBlockEntity::new,
            ModDecorativeBlocks.BONFIRE);
    public static BlockEntityType<ChimneyBlockEntity> CHIMNEY = register("chimney", ChimneyBlockEntity::new,
            ModDecorativeBlocks.CHIMNEY);
    public static BlockEntityType<CustomBedBlockEntity> BED = register("bed", CustomBedBlockEntity::new,
            ModDecorativeBlocks.FANCY_BED,
            ModDecorativeBlocks.FUR_BED,
            ModDecorativeBlocks.STRAW_BED);

    public static void registerBlockEntities() {
        BlockEntityType.BARREL.addSupportedBlock(ModDecorativeBlocks.SMALL_CRATE);
        BlockEntityType.BARREL.addSupportedBlock(ModDecorativeBlocks.THIN_BARREL);
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name,
                                                                       FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
                                                                       Block... blocks) {
        Identifier id = Identifier.of(MiddleEarth.MOD_ID, name);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.BLOCK_ENTITY_TYPE, name));

        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
}
