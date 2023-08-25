package net.jesteur.me.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.block.special.HaradPlant;
import net.jesteur.me.block.special.MordorPlant;
import net.jesteur.me.block.special.StrawBerryBushBlock;
import net.jesteur.me.block.special.ToughBerryBushBlock;
import net.jesteur.me.item.utils.ModItemGroups;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import static net.jesteur.me.block.WoodBlockSets.LEAVES_STRENGTH;

public class ModNatureBlocks {
    public static final Block TOUGH_BERRY_BUSH = registerBlock("tough_berry_bush",
            new ToughBerryBushBlock(FabricBlockSettings.copyOf(Blocks.SWEET_BERRY_BUSH).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH)), true);
    public static final Block MORDOR_LICHEN = registerBlock("mordor_lichen",
            new MordorPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ).mapColor(DyeColor.GRAY)), false);
    public static final Block MORDOR_LICHEN_FAN = registerBlock("mordor_lichen_fan",
            new MordorPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XZ).mapColor(DyeColor.GRAY)), false);
    public static final Block BROWN_GRASS = registerBlock("brown_grass",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ).mapColor(DyeColor.GREEN)), false);
    public static final Block GREEN_SHRUB = registerBlock("green_shrub",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ).mapColor(DyeColor.GREEN)), false);
    public static final Block TAN_SHRUB = registerBlock("tan_shrub",
            new HaradPlant(FabricBlockSettings.copyOf(Blocks.GRASS).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XYZ).mapColor(DyeColor.GREEN)), false);
    public static final Block YELLOW_FLOWER = registerBlock("yellow_flower",
            new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 0, FabricBlockSettings.copyOf(Blocks.DANDELION)), false);
    public static final Block LEBETHRON_LEAVES = registerBlock("lebethron_leaves",
            new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES).strength(LEAVES_STRENGTH).sounds(BlockSoundGroup.GRASS)), false);


    public static final Block STRAWBERRY_BUSH = registerBlock("strawberry_bush",
            new StrawBerryBushBlock(FabricBlockSettings.copyOf(Blocks.SWEET_BERRY_BUSH).ticksRandomly().noCollision().breakInstantly().sounds(BlockSoundGroup.SWEET_BERRY_BUSH)), true);




    public static Block registerBlock(String name, Block block, boolean absent) {
        if(!absent) ModNatureBlocks.registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(MiddleEarth.MOD_ID, name), block);
    }

    static Item registerBlockItem(String name, Block block) {
        var item =  Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));

        Item.BLOCK_ITEMS.put(block, item);
        ModItemGroups.NATURE_BLOCKS_CONTENTS.add(item.getDefaultStack());
        return item;
    }

    public static void registerModBlocks() {
        MiddleEarth.LOGGER.debug("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }
}
