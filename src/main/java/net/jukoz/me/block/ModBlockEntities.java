package net.jukoz.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.bellows.BellowsBlockEntity;
import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.jukoz.me.block.special.beds.CustomBedBlockEntity;
import net.jukoz.me.block.special.fireBlocks.*;
import net.jukoz.me.block.special.reinforcedChest.ReinforcedChestBlockEntity;
import net.jukoz.me.block.special.shapingAnvil.dwarvenTreatedAnvil.DwarvenTreatedAnvilBlockEntity;
import net.jukoz.me.block.special.shapingAnvil.treatedAnvil.TreatedAnvilBlockEntity;
import net.jukoz.me.block.special.wood_pile.WoodPileBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<ForgeBlockEntity> FORGE;
    public static BlockEntityType<TreatedAnvilBlockEntity> TREATED_ANVIL;
    public static BlockEntityType<DwarvenTreatedAnvilBlockEntity> DWARVEN_SHAPING_ANVIL;
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
                FabricBlockEntityTypeBuilder.create(ForgeBlockEntity::new,
                        ModDecorativeBlocks.FORGE).build(null));
        TREATED_ANVIL = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "treated_anvil"),
                FabricBlockEntityTypeBuilder.create(TreatedAnvilBlockEntity::new,
                        ModDecorativeBlocks.TREADTED_ANVIL).build(null));
        DWARVEN_SHAPING_ANVIL = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "dwarven_treated_anvil"),
                FabricBlockEntityTypeBuilder.create(DwarvenTreatedAnvilBlockEntity::new,
                        ModDecorativeBlocks.DWARVEN_TREATED_ANVIL).build(null));
        REINFORCED_CHEST = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "reinforced_chest"),
                FabricBlockEntityTypeBuilder.create(ReinforcedChestBlockEntity::new,
                        ModDecorativeBlocks.REINFORCED_CHEST).build(null));
        BELLOWS = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "bellows"),
                FabricBlockEntityTypeBuilder.create(BellowsBlockEntity::new,
                        ModDecorativeBlocks.BELLOWS).build(null));
        WOOD_PILE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "wood_pile"),
                FabricBlockEntityTypeBuilder.create(WoodPileBlockEntity::new,
                        ModDecorativeBlocks.WOOD_PILE).build(null));
        BIG_BRAZIER = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "big_brazier"),
                FabricBlockEntityTypeBuilder.create(BrazierBlockEntity::new,
                        ModDecorativeBlocks.BIG_BRAZIER).build(null));
        SMALL_BRAZIER = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "small_brazier"),
                FabricBlockEntityTypeBuilder.create(SmallBrazierBlockEntity::new,
                        ModDecorativeBlocks.SMALL_BRAZIER).build(null));
        GILDED_BIG_BRAZIER = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "gilded_big_brazier"),
                FabricBlockEntityTypeBuilder.create(GildedBrazierBlockEntity::new,
                        ModDecorativeBlocks.GILDED_BIG_BRAZIER).build(null));
        GILDED_SMALL_BRAZIER = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "gilded_small_brazier"),
                FabricBlockEntityTypeBuilder.create(GildedSmallBrazierBlockEntity::new,
                        ModDecorativeBlocks.GILDED_SMALL_BRAZIER).build(null));
        FIRE_BOWL = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "fire_bowl"),
                FabricBlockEntityTypeBuilder.create(FireBowlBlockEntity::new,
                        ModDecorativeBlocks.FIRE_BOWL).build(null));
        BONFIRE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "bonfire"),
                FabricBlockEntityTypeBuilder.create(BonfireBlockEntity::new,
                        ModDecorativeBlocks.BONFIRE).build(null));
        CHIMNEY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "chimney"),
                FabricBlockEntityTypeBuilder.create(ChimneyBlockEntity::new,
                        ModDecorativeBlocks.CHIMNEY).build(null));
        BED = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "bed"),
                FabricBlockEntityTypeBuilder.create(CustomBedBlockEntity::new,
                        ModDecorativeBlocks.FANCY_BED, ModDecorativeBlocks.FUR_BED, ModDecorativeBlocks.STRAW_BED).build(null));

        BlockEntityType.BARREL.addSupportedBlock(ModDecorativeBlocks.SMALL_CRATE);
        BlockEntityType.BARREL.addSupportedBlock(ModDecorativeBlocks.THIN_BARREL);
    }
}
