package net.jesteur.me.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.datageneration.content.models.SimpleWallModel;
import net.jesteur.me.item.utils.ModItemGroups;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockSets {
    public static SimpleBlockSet ASHEN_BRICKS = registerBrickSet("ashen_bricks");
    public static SimpleBlockSet GONDOR_BRICKS = registerBrickSet("gondor_bricks");


    public static SimpleBlockSet[] sets = new SimpleBlockSet[] {
            ASHEN_BRICKS,
            GONDOR_BRICKS
    };

    public record SimpleBlockSet(Block bricks, Block slab, Block stairs, Block wall) {
    }

    private static SimpleBlockSet registerBrickSet(String name) {

        Block bricks = ModBlocks.registerBlock(name, new Block(FabricBlockSettings.of(Material.STONE)
                .strength(ModBlocks.STONE_STRENGTH).requiresTool()));

        Block slab = ModBlocks.registerBlock(name + "_slab", new SlabBlock(FabricBlockSettings
                .of(Material.STONE).strength(ModBlocks.STONE_STRENGTH, ModBlocks.SLAB_RESISTANCE).requiresTool()));

        Block stairs = ModBlocks.registerBlock(name + "_stairs", new StairsBlock(bricks.getDefaultState(), FabricBlockSettings
                .of(Material.STONE).strength(ModBlocks.STONE_STRENGTH).requiresTool()));

        Block wall = ModBlocks.registerBlock(name + "_wall", new WallBlock(AbstractBlock.Settings.copy(bricks)
                .strength(ModBlocks.STONE_STRENGTH).requiresTool()));


        return new SimpleBlockSet(bricks, slab, stairs, wall);
    }

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.debug("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }
}
