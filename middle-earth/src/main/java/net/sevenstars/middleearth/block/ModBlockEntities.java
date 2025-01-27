package net.sevenstars.middleearth.block;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.beds.CustomBedBlockEntity;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlockEntity;
import net.sevenstars.middleearth.block.special.fireBlocks.*;
import net.sevenstars.middleearth.block.special.forge.ForgeBlockEntity;
import net.sevenstars.middleearth.block.special.reinforcedChest.ReinforcedChestBlockEntity;
import net.sevenstars.middleearth.block.special.shapingAnvil.TreatedAnvilBlockEntity;
import net.sevenstars.middleearth.block.special.wood_pile.WoodPileBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<ForgeBlockEntity> FORGE;
    public static BlockEntityType<TreatedAnvilBlockEntity> TREATED_ANVIL;
    public static BlockEntityType<ReinforcedChestBlockEntity> REINFORCED_CHEST;
    public static BlockEntityType<BellowsBlockEntity> BELLOWS;
    public static BlockEntityType<WoodPileBlockEntity> WOOD_PILE;
    public static BlockEntityType<BrazierBlockEntity> BIG_BRAZIER;
    public static BlockEntityType<SmallBrazierBlockEntity> SMALL_BRAZIER;
    public static BlockEntityType<GildedBrazierBlockEntity> GILDED_BIG_BRAZIER;
    public static BlockEntityType<GildedSmallBrazierBlockEntity> GILDED_SMALL_BRAZIER;
    public static BlockEntityType<FireBowlBlockEntity> FIRE_BOWL;
    public static BlockEntityType<BonfireBlockEntity> BONFIRE;
    public static BlockEntityType<ChimneyBlockEntity> CHIMNEY;
    public static BlockEntityType<CustomBedBlockEntity> BED;

    public static void registerBlockEntities() {

        FORGE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "forge"),
                BlockEntityType.Builder.create(ForgeBlockEntity::new,
                        ModDecorativeBlocks.FORGE).build(null));
        TREATED_ANVIL = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "treated_anvil"),
                BlockEntityType.Builder.create(TreatedAnvilBlockEntity::new,
                        ModDecorativeBlocks.TREATED_ANVIL,
                        ModDecorativeBlocks.DWARVEN_TREATED_ANVIL,
                        ModDecorativeBlocks.ELVEN_TREATED_ANVIL,
                        ModDecorativeBlocks.ORCISH_TREATED_ANVIL).build(null));
        REINFORCED_CHEST = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "reinforced_chest"),
                BlockEntityType.Builder.create(ReinforcedChestBlockEntity::new,
                        ModDecorativeBlocks.REINFORCED_CHEST).build(null));
        BELLOWS = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "bellows"),
                BlockEntityType.Builder.create(BellowsBlockEntity::new,
                        ModDecorativeBlocks.BELLOWS).build(null));
        WOOD_PILE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "wood_pile"),
                BlockEntityType.Builder.create(WoodPileBlockEntity::new,
                        ModDecorativeBlocks.WOOD_PILE).build(null));
        BIG_BRAZIER = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "big_brazier"),
                BlockEntityType.Builder.create(BrazierBlockEntity::new,
                        ModDecorativeBlocks.BIG_BRAZIER).build(null));
        SMALL_BRAZIER = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "small_brazier"),
                BlockEntityType.Builder.create(SmallBrazierBlockEntity::new,
                        ModDecorativeBlocks.SMALL_BRAZIER).build(null));
        GILDED_BIG_BRAZIER = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "gilded_big_brazier"),
                BlockEntityType.Builder.create(GildedBrazierBlockEntity::new,
                        ModDecorativeBlocks.GILDED_BIG_BRAZIER).build(null));
        GILDED_SMALL_BRAZIER = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "gilded_small_brazier"),
                BlockEntityType.Builder.create(GildedSmallBrazierBlockEntity::new,
                        ModDecorativeBlocks.GILDED_SMALL_BRAZIER).build(null));
        FIRE_BOWL = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "fire_bowl"),
                BlockEntityType.Builder.create(FireBowlBlockEntity::new,
                        ModDecorativeBlocks.FIRE_BOWL).build(null));
        BONFIRE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "bonfire"),
                BlockEntityType.Builder.create(BonfireBlockEntity::new,
                        ModDecorativeBlocks.BONFIRE).build(null));
        CHIMNEY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "chimney"),
                BlockEntityType.Builder.create(ChimneyBlockEntity::new,
                        ModDecorativeBlocks.CHIMNEY).build(null));
        BED = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "bed"),
                BlockEntityType.Builder.create(CustomBedBlockEntity::new,
                        ModDecorativeBlocks.FANCY_BED, ModDecorativeBlocks.FUR_BED, ModDecorativeBlocks.STRAW_BED).build(null));

        BlockEntityType.BARREL.addSupportedBlock(ModDecorativeBlocks.SMALL_CRATE);
        BlockEntityType.BARREL.addSupportedBlock(ModDecorativeBlocks.THIN_BARREL);
    }
}
