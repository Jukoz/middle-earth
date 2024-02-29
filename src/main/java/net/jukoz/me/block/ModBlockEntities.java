package net.jukoz.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.alloyfurnace.AlloyFurnaceEntity;
import net.jukoz.me.block.special.artisantable.ArtisanTableEntity;
import net.jukoz.me.block.special.wood_pile.WoodPileBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<AlloyFurnaceEntity> ALLOY_FURNACE;
    public static BlockEntityType<ArtisanTableEntity> ARTISAN_TABLE;
    public static BlockEntityType<WoodPileBlockEntity> WOOD_PILE;

    public static void registerBlockEntities() {
        ALLOY_FURNACE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(MiddleEarth.MOD_ID, "alloy_furnace"),
                FabricBlockEntityTypeBuilder.create(AlloyFurnaceEntity::new,
                        ModDecorativeBlocks.ALLOY_FURNACE).build(null));
        ARTISAN_TABLE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(MiddleEarth.MOD_ID, "artisan_table"),
                FabricBlockEntityTypeBuilder.create(ArtisanTableEntity::new,
                        ModDecorativeBlocks.ARTISAN_TABLE).build(null));
        WOOD_PILE = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(MiddleEarth.MOD_ID, "wood_pile"),
                FabricBlockEntityTypeBuilder.create(WoodPileBlockEntity::new,
                        ModDecorativeBlocks.WOOD_PILE).build(null));
    }
}
