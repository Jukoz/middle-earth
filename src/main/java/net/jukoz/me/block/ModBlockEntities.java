package net.jukoz.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.jukoz.me.block.special.reinforcedChest.ReinforcedChestBlockEntity;
import net.jukoz.me.block.special.treatedAnvil.TreatedAnvilBlock;
import net.jukoz.me.block.special.treatedAnvil.TreatedAnvilBlockEntity;
import net.jukoz.me.block.special.wood_pile.WoodPileBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<ForgeBlockEntity> FORGE;
    public static BlockEntityType<TreatedAnvilBlockEntity> TREATED_ANVIL;
    public static BlockEntityType<ReinforcedChestBlockEntity> REINFORCED_CHEST;
    public static BlockEntityType<WoodPileBlockEntity> WOOD_PILE;

    public static void registerBlockEntities() {
        FORGE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "forge"),
                FabricBlockEntityTypeBuilder.create(ForgeBlockEntity::new,
                        ModDecorativeBlocks.FORGE).build(null));
        TREATED_ANVIL = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "forge"),
                FabricBlockEntityTypeBuilder.create(TreatedAnvilBlockEntity::new,
                        ModDecorativeBlocks.TREADTED_ANVIL).build(null));
        REINFORCED_CHEST = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "reinforced_chest"),
                FabricBlockEntityTypeBuilder.create(ReinforcedChestBlockEntity::new,
                        ModDecorativeBlocks.REINFORCED_CHEST).build(null));
        WOOD_PILE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, "wood_pile"),
                FabricBlockEntityTypeBuilder.create(WoodPileBlockEntity::new,
                        ModDecorativeBlocks.WOOD_PILE).build(null));
    }
}
