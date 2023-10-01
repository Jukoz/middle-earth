package net.jesteur.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.block.special.alloy.AlloyBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<AlloyBlockEntity> ALLOY;

    public static void registerBlockEntities() {
        ALLOY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(MiddleEarth.MOD_ID, "alloy"),
                FabricBlockEntityTypeBuilder.create(AlloyBlockEntity::new,
                        ModDecorativeBlocks.ALLOY).build(null));
    }
}
