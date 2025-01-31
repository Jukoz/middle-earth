package net.sevenstars.middleearth.block;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
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
    public static BlockEntityType<ForgeBlockEntity> FORGE = register("forge", ForgeBlockEntity::new,
            ModDecorativeBlocks.FORGE);
    public static BlockEntityType<TreatedAnvilBlockEntity> TREATED_ANVIL = register("treated_anvil", TreatedAnvilBlockEntity::new,
            ModDecorativeBlocks.TREATED_ANVIL,
            ModDecorativeBlocks.DWARVEN_TREATED_ANVIL,
            ModDecorativeBlocks.ELVEN_TREATED_ANVIL,
            ModDecorativeBlocks.ORCISH_TREATED_ANVIL);;
    public static BlockEntityType<ReinforcedChestBlockEntity> REINFORCED_CHEST = register("reinforced_chest", ReinforcedChestBlockEntity::new,
            ModDecorativeBlocks.REINFORCED_CHEST);;
    public static BlockEntityType<BellowsBlockEntity> BELLOWS = register("bellows", BellowsBlockEntity::new,
            ModDecorativeBlocks.BELLOWS);;
    public static BlockEntityType<WoodPileBlockEntity> WOOD_PILE = register("wood_pile", WoodPileBlockEntity::new,
            ModDecorativeBlocks.WOOD_PILE);;
    public static BlockEntityType<BrazierBlockEntity> BIG_BRAZIER = register("big_brazier", BrazierBlockEntity::new,
            ModDecorativeBlocks.BIG_BRAZIER);;
    public static BlockEntityType<SmallBrazierBlockEntity> SMALL_BRAZIER = register("small_brazier", SmallBrazierBlockEntity::new,
            ModDecorativeBlocks.SMALL_BRAZIER);;
    public static BlockEntityType<GildedBrazierBlockEntity> GILDED_BIG_BRAZIER = register("gilded_big_brazier", GildedBrazierBlockEntity::new,
            ModDecorativeBlocks.GILDED_BIG_BRAZIER);;
    public static BlockEntityType<GildedSmallBrazierBlockEntity> GILDED_SMALL_BRAZIER = register("gilded_small_brazier", GildedSmallBrazierBlockEntity::new,
            ModDecorativeBlocks.GILDED_SMALL_BRAZIER);;
    public static BlockEntityType<FireBowlBlockEntity> FIRE_BOWL = register("fire_bowl", FireBowlBlockEntity::new,
            ModDecorativeBlocks.FIRE_BOWL);;
    public static BlockEntityType<BonfireBlockEntity> BONFIRE = register("bonfire", BonfireBlockEntity::new,
            ModDecorativeBlocks.BONFIRE);;
    public static BlockEntityType<ChimneyBlockEntity> CHIMNEY = register("chimney", ChimneyBlockEntity::new,
            ModDecorativeBlocks.CHIMNEY);;
    public static BlockEntityType<CustomBedBlockEntity> BED = register("bed", CustomBedBlockEntity::new,
            ModDecorativeBlocks.FANCY_BED,
            ModDecorativeBlocks.FUR_BED,
            ModDecorativeBlocks.STRAW_BED);;

    public static void registerBlockEntities() {
        BlockEntityType.BARREL.addSupportedBlock(ModDecorativeBlocks.SMALL_CRATE);
        BlockEntityType.BARREL.addSupportedBlock(ModDecorativeBlocks.THIN_BARREL);
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(String name,
                                                                       FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
                                                                       Block... blocks) {
        Identifier id = Identifier.of(MiddleEarth.MOD_ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
}
