package net.sevenstars.middleearth.datageneration;

import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.block.special.*;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.sevenstars.middleearth.block.utils.BlockRecordTypes;
import net.sevenstars.middleearth.block.utils.StoneBlockTypes;
import net.sevenstars.middleearth.block.utils.setBuilders.GenericBlockSetBuilder;
import net.sevenstars.middleearth.block.utils.setBuilders.SimpleBlockSetBuilder;
import net.sevenstars.middleearth.block.utils.setBuilders.StoneBlockSetBuilder;
import net.sevenstars.middleearth.block.utils.setBuilders.WoodBlockSetBuilder;
import net.sevenstars.middleearth.datageneration.content.loot_tables.BlockDrops;
import net.sevenstars.middleearth.datageneration.content.models.*;
import net.sevenstars.middleearth.datageneration.content.tags.*;

import java.util.Objects;

public class HelpingGenerator {
    //TODO recipe for all
    //TODO tags for all

    public static void generateFiles() {

        for (StoneBlockSetBuilder set : StoneBlockSetRegistryME.stoneSetsList){
            set.existingList.forEach(stoneBlockTypes -> {
                switch (stoneBlockTypes){
                    case BASE_BLOCKS -> {
                        mainStoneBlocks(set.baseBlocks);
                        MineablePickaxe.baseStoneOverworld.add(set.baseBlocks.base());
                    }
                    case COBBLED_BLOCKS,COBBLESTONE_BLOCKS,PILLAR_BASE_BLOCKS -> {
                        regularBlocks(set.cobblestoneBlocks, MiningTool.PICKAXE);
                        if(set.hasMossy) regularBlocks(set.mossyCobblestoneBlocks, MiningTool.PICKAXE);
                    }
                    case BRICK_BLOCKS, POLISHED_BRICK_BLOCKS -> {
                        regularBlocks(set.brickBlocks, MiningTool.PICKAXE);
                        if(set.hasMossy) regularBlocks(set.mossyBrickBlocks, MiningTool.PICKAXE);
                        if(set.hasCracked) regularBlocks(set.crackedBrickBlocks, MiningTool.PICKAXE);
                    }
                    case TILE_BLOCKS -> {
                        regularBlocks(set.tileBlocks, MiningTool.PICKAXE);
                        if(set.hasMossy) regularBlocks(set.mossyTileBlocks, MiningTool.PICKAXE);
                        if(set.hasCracked) regularBlocks(set.crackedTileBlocks, MiningTool.PICKAXE);
                    }
                    case SMOOTH_BLOCKS -> {
                        regularBlocks(set.smoothBlocks, MiningTool.PICKAXE);
                        if(set.hasMossy) regularBlocks(set.mossySmoothBlocks, MiningTool.PICKAXE);
                        if(set.hasCracked) regularBlocks(set.crackedSmoothBlocks, MiningTool.PICKAXE);
                    }
                    case POLISHED_BLOCKS, POLISHED_BLOCKS_PILLAR -> {
                        regularBlocks(set.polishedBlocks, MiningTool.PICKAXE);
                        if(set.hasMossy) regularBlocks(set.mossyPolishedBlocks, MiningTool.PICKAXE);
                        if(set.hasCracked) regularBlocks(set.crackedPolishedBlocks, MiningTool.PICKAXE);
                    }
                    case BRICKWORK_BLOCKS -> regularBlocks(set.brickworkBlocks, MiningTool.PICKAXE);
                    case OLD_BLOCKS, OLD_BLOCKS_PILLAR -> {
                        regularBlocks(set.oldBlocks, MiningTool.PICKAXE);
                        MineablePickaxe.baseStoneOverworld.add(set.oldBlocks.base());
                    }
                    case PILLAR_BLOCKS -> {
                        pillarBlocks(set.pillarBlocks, set.brickBlocks.base(), MiningTool.PICKAXE);
                        if(set.hasMossy) pillarBlocks(set.mossyPillarBlocks, set.pillarBlocks.base(), MiningTool.PICKAXE);
                        if(set.hasCracked) pillarBlocks(set.crackedPillarBlocks, set.pillarBlocks.base(), MiningTool.PICKAXE);
                    }
                    case CHISELED_BLOCKS -> {
                        if (set.existingList.contains(StoneBlockTypes.BASE_BLOCKS)) pillarBlocks(set.chiseledBlocks, set.baseBlocks.base(), MiningTool.PICKAXE);
                        if (set.existingList.contains(StoneBlockTypes.BRICK_BLOCKS)) pillarBlocks(set.chiseledBricksBlocks, set.brickBlocks.base(), MiningTool.PICKAXE);
                        if (set.existingList.contains(StoneBlockTypes.POLISHED_BRICK_BLOCKS)) pillarBlocks(set.chiseledBricksBlocks, set.brickBlocks.base(), MiningTool.PICKAXE);
                        if (set.existingList.contains(StoneBlockTypes.POLISHED_BLOCKS) || set.existingList.contains(StoneBlockTypes.POLISHED_BLOCKS_PILLAR))
                            pillarBlocks(set.chiseledPolishedBlocks, set.polishedBlocks.base(), MiningTool.PICKAXE);
                        if (set.existingList.contains(StoneBlockTypes.TILE_BLOCKS)) pillarBlocks(set.chiseledTilesBlocks, set.tileBlocks.base(), MiningTool.PICKAXE);
                        if (set.existingList.contains(StoneBlockTypes.SMOOTH_BLOCKS)) pillarBlocks(set.chiseledSmoothBlocks, set.smoothBlocks.base(), MiningTool.PICKAXE);
                    }
                    case CHISELED_BLOCKS_NO_RESTRICTION -> {
                        pillarBlocks(set.chiseledBlocks, null, MiningTool.PICKAXE);
                        pillarBlocks(set.chiseledBricksBlocks, null, MiningTool.PICKAXE);
                        pillarBlocks(set.chiseledPolishedBlocks, null, MiningTool.PICKAXE);
                        pillarBlocks(set.chiseledTilesBlocks, null, MiningTool.PICKAXE);
                        pillarBlocks(set.chiseledSmoothBlocks, null, MiningTool.PICKAXE);
                    }
                    case CARVED_WINDOW -> {
                        if(set.baseBlocks == null) carvedWindowBlocks(set.carvedWindows, set.cobblestoneBlocks.base());
                        else carvedWindowBlocks(set.carvedWindows, set.baseBlocks.base());
                    }
                }
            });
        }

        for (WoodBlockSetBuilder set : WoodBlockSetRegistryME.woodSetsList){
            set.existingList.forEach(woodBlockTypes -> {
                switch (woodBlockTypes){
                    case LOG_BLOCKS -> {
                        woodBlocks(set.logBlocks);
                        if (!set.vanilla) NaturalLogs.logs.add(set.logBlocks.log());
                    }
                    case NETHER_STEM_BLOCKS -> woodBlocks(set.logBlocks);
                    case MUSHROOM_STEM_BLOCKS -> mushroomStemBlocks(set.mushroomStemBlocks);
                    case STRIPPED_LOG_BLOCKS, STRIPPED_STEM_BLOCKS -> {
                        woodBlocks(set.strippedLogBlocks);
                    }
                    case PLANK_BLOCKS -> plankBlocks(set.planksBlocks, set.vanilla);
                    case SHINGLE_BLOCKS -> {
                        Shingles.shingles.add(set.shinglesBlocks.base());
                        regularBlocks(set.shinglesBlocks, MiningTool.AXE);
                    }
                    case ROOFING_BLOCKS -> regularBlocks(set.roofingBlocks, MiningTool.AXE);
                    case FURNITURE_BLOCKS -> furnitureBlocks(set.furnitureBlocks, set.planksBlocks.base());
                    case REDSTONE_BLOCKS -> woodRedstoneBlocks(set.redstoneBlocks, set.planksBlocks.base());
                    case LEAVES -> {
                        if (!Objects.equals(Registries.BLOCK.getId(set.leaves).getNamespace(), "minecraft")){
                            if (set.setName.contains("beech")){
                                LeavesSets.grayscaleLeaves.add(set.leaves);
                            } else {
                                LeavesSets.leaves.add(set.leaves);
                            }
                        }
                    }
                }
            });
        }

        for (GenericBlockSetBuilder set : GenericBlockSetRegistryME.genericSetsList) {
            regularBlocks(set.blockSet, BlockSetTools.of(set));
        }
        for (SimpleBlockSetBuilder set : GenericBlockSetRegistryME.simpleSetsList) {
            regularBlocks(set.blockSet, BlockSetTools.of(set));
        }

        SimpleBlockModel.blocks.addAll(LeavesSets.leaves);

        for (SimpleVerticalSlabModel.VerticalSlab set : SimpleVerticalSlabModel.vanillaVerticalSlabs) {
            BlockDrops.blocks.add(set.verticalSlab());
        }

        for(SimpleWoodStoolModel.VanillaStool stool : SimpleWoodStoolModel.vanillaStools){
            BlockDrops.blocks.add(stool.base());
            MineableAxe.blocks.add(stool.base());
        }

        for(SimpleWoodBenchModel.VanillaBench bench : SimpleWoodBenchModel.vanillaBenchs){
            BlockDrops.blocks.add(bench.base());
            MineableAxe.blocks.add(bench.base());
        }

        for(SimpleWoodTableModel.VanillaTable table : SimpleWoodTableModel.vanillaTables) {
            BlockDrops.blocks.add(table.base());
            MineableAxe.blocks.add(table.base());
        }

        for(SimpleWoodChairModel.VanillaChair chair : SimpleWoodChairModel.vanillaChairs){
            BlockDrops.blocks.add(chair.base());
            MineableAxe.blocks.add(chair.base());
        }

        SimpleWallModel.vanillaWalls.forEach(block -> {
            Walls.walls.add(block.wall());
        });

        SimpleWallModel.vanillaWoodWalls.forEach(block -> {
            Walls.walls.add(block.wall());
            MineableAxe.blocks.add(block.wall());
            NotMineableWithPickaxe.walls.add(block.wall());
        });

        SimpleWallModel.vanillaStrippedWalls.forEach(block -> {
            Walls.walls.add(block.wall());
            MineableAxe.blocks.add(block.wall());
            NotMineableWithPickaxe.walls.add(block.wall());
        });

        SimpleFenceModel.vanillaWoodFences.forEach(block -> {
            Fences.fences.add(block.fence());
            MineableAxe.blocks.add(block.fence());
        });

        SimpleFenceModel.vanillaStrippedFences.forEach(block -> {
            Fences.fences.add(block.fence());
            MineableAxe.blocks.add(block.fence());
        });
        

        for (OreStoneSetRegistryME.OreRockSet set : OreStoneSetRegistryME.sets) {
            if(set.coal_ore() != null){
                SimpleBlockModel.blocks.add(set.coal_ore());
                MineablePickaxe.blocks.add(set.coal_ore());
            }
            if(set.copper_ore() != null){
                SimpleBlockModel.blocks.add(set.copper_ore());
                MineablePickaxe.blocks.add(set.copper_ore());
            }
            if(set.tin_ore() != null){
                SimpleBlockModel.blocks.add(set.tin_ore());
                MineablePickaxe.blocks.add(set.tin_ore());
            }
            if(set.lead_ore() != null){
                SimpleBlockModel.blocks.add(set.lead_ore());
                MineablePickaxe.blocks.add(set.lead_ore());
            }
            if(set.silver_ore() != null){
                SimpleBlockModel.blocks.add(set.silver_ore());
                MineablePickaxe.blocks.add(set.silver_ore());
            }
            if(set.gold_ore() != null){
                SimpleBlockModel.blocks.add(set.gold_ore());
                MineablePickaxe.blocks.add(set.gold_ore());
            }
            if(set.iron_ore() != null){
                SimpleBlockModel.blocks.add(set.iron_ore());
                MineablePickaxe.blocks.add(set.iron_ore());
            }
            if(set.mithril_ore() != null){
                SimpleBlockModel.blocks.add(set.mithril_ore());
                MineablePickaxe.blocks.add(set.mithril_ore());
            }
            if(set.adamant_ore() != null){
                SimpleBlockModel.blocks.add(set.adamant_ore());
                MineablePickaxe.blocks.add(set.adamant_ore());
            }
            if(set.emerald_ore() != null){
                SimpleBlockModel.blocks.add(set.emerald_ore());
                MineablePickaxe.blocks.add(set.emerald_ore());
            }
            if(set.ruby_ore() != null){
                SimpleBlockModel.blocks.add(set.ruby_ore());
                MineablePickaxe.blocks.add(set.ruby_ore());
            }
            if(set.sapphire_ore() != null){
                SimpleBlockModel.blocks.add(set.sapphire_ore());
                MineablePickaxe.blocks.add(set.sapphire_ore());
            }
        }
    }

    public static void addBlocksToLists(Block block, Block base, Block origin, MiningTool tool){
        switch(tool) {
            case AXE -> MineableAxe.blocks.add(block);
            case PICKAXE -> MineablePickaxe.blocks.add(block);
            case SHOVEL -> MineableShovel.blocks.add(block);
            case HOE -> MineableHoe.blocks.add(block);
            case NONE -> {}
        }

        BlockDrops.blocks.add(block);

        String blockName = block.getName().toString();
        String baseName = base.getName().toString();

        if (!(Objects.equals(Registries.BLOCK.getId(block).getNamespace(), "minecraft"))){
            final boolean isPillar = blockName.contains("carved_window")
                    || blockName.contains("chiseled")
                    || blockName.contains("drystone")
                    || blockName.contains("pillar");
            final boolean woodModel = (blockName.contains("_wood") && !(blockName.contains("gilded") || blockName.contains("beam"))) || blockName.contains("hyphae");
            switch (block){
                case PillarBlock pillarBlock -> {
                    if (woodModel){
                        SimpleBlockModel.woodBlocks.add(pillarBlock);
                    } else {
                        SimplePillarModel.blocks.add(new SimplePillarModel.Pillar(pillarBlock));
                    }
                }
                case SlabBlock slabBlock -> {
                    if (blockName.contains("stripped")){
                        SimpleSlabModel.strippedSlabs.add(new SimpleSlabModel.Slab(base, origin));
                    } else if (woodModel){
                        SimpleSlabModel.woodSlabs.add(new SimpleSlabModel.Slab(base, origin));
                    } else{
                        SimpleSlabModel.slabs.add(new SimpleSlabModel.Slab(base, slabBlock));
                    }
                }
                case VerticalSlabBlock verticalSlabBlock -> {
                    if (isPillar) {
                        SimpleVerticalSlabModel.columnVerticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(base, origin, verticalSlabBlock));
                    }else if (blockName.contains("stripped")){
                        SimpleVerticalSlabModel.strippedVerticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(base, origin, verticalSlabBlock));
                    } else if (woodModel){
                        SimpleVerticalSlabModel.woodVerticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(base, origin, verticalSlabBlock));
                    } else if (baseName.contains("planks")){
                        SimpleVerticalSlabModel.plansVerticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(base, origin, verticalSlabBlock));
                    } else{
                        SimpleVerticalSlabModel.verticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(base, origin, verticalSlabBlock));
                    }
                }
                case StairsBlock stairsBlock -> {
                    if (blockName.contains("stripped")){
                        SimpleStairModel.strippedStairs.add(new SimpleStairModel.Stair(base, stairsBlock));
                    } else if (woodModel){
                        SimpleStairModel.woodStairs.add(new SimpleStairModel.Stair(base, stairsBlock));
                    } else{
                        SimpleStairModel.stairs.add(new SimpleStairModel.Stair(base, stairsBlock));
                    }
                }
                case WallBlock wallBlock -> {
                    Walls.walls.add(wallBlock);
                    if (tool != MiningTool.PICKAXE) NotMineableWithPickaxe.walls.add(wallBlock);
                    if (isPillar){
                        SimpleWallModel.columnWalls.add(new SimpleWallModel.Wall(base, wallBlock));
                    } else if (blockName.contains("stripped")){
                        SimpleWallModel.strippedWalls.add(new SimpleWallModel.Wall(base, wallBlock));
                    } else  {
                        SimpleWallModel.blocks.add(new SimpleWallModel.Wall(base, wallBlock));
                    }
                }
                case FenceBlock fenceBlock -> {
                    Fences.fences.add(fenceBlock);
                    if (blockName.contains("stripped")){
                        SimpleFenceModel.strippedFences.add(new SimpleFenceModel.Fence(base, fenceBlock));
                    } else {
                        SimpleFenceModel.blocks.add(new SimpleFenceModel.Fence(base, fenceBlock));
                    }
                }
                case FenceGateBlock fenceGateBlock -> {
                    FenceGates.fenceGates.add(fenceGateBlock);
                    SimpleFenceGateModel.blocks.add(new SimpleFenceGateModel.FenceGate(base, fenceGateBlock));
                }
                case PaneBlock paneBlock -> SimplePaneModel.panes.add(new SimplePaneModel.Pane(base, paneBlock));
                case PressurePlateBlock pressurePlateBlock -> {
                    PressurePlates.pressurePlates.add(pressurePlateBlock);
                    SimplePressurePlateModel.pressurePlates.add(new SimplePressurePlateModel.PressurePlate(base, pressurePlateBlock));
                }
                case ButtonBlock buttonBlock -> {
                    // Wooden buttons are named "<wood>_button", so the "_wood" name check above misses them; use the tool instead
                    if(tool == MiningTool.AXE) Buttons.woodButtons.add(buttonBlock);
                    else Buttons.stoneButtons.add(buttonBlock);
                    SimpleButtonModel.buttons.add(new SimpleButtonModel.Button(base, buttonBlock));
                }
                case TrapdoorBlock trapdoorBlock -> {
                    Trapdoors.trapdoors.add(trapdoorBlock);
                    if (baseName.contains("planks")){
                        SimpleTrapDoorModel.trapdoors.add(new SimpleTrapDoorModel.Trapdoor(base, trapdoorBlock, true));
                    } else {
                        SimpleTrapDoorModel.trapdoors.add(new SimpleTrapDoorModel.Trapdoor(base, trapdoorBlock, false));
                    }
                }
                case StoolBlock stoolBlock -> SimpleStoneStoolModel.stools.add(new SimpleStoneStoolModel.Stool(base, stoolBlock));
                case WoodStoolBlock stoolBlock -> SimpleWoodStoolModel.stools.add(stoolBlock);
                case StoneTableBlock tableBlock -> SimpleStoneTableModel.tables.add(new SimpleStoneTableModel.Table(base, tableBlock));
                case WoodTableBlock tableBlock -> SimpleWoodTableModel.tables.add(tableBlock);
                case StoneChairBlock chairBlock -> SimpleStoneChairModel.chairs.add(new SimpleStoneChairModel.Chair(base, chairBlock));
                case WoodChairBlock chairBlock -> SimpleWoodChairModel.chairs.add(chairBlock);
                case WoodBenchBlock benchBlock -> SimpleWoodBenchModel.benchs.add(benchBlock);
                case DoorBlock doorBlock -> {
                    Doors.doors.add(doorBlock);
                    SimpleDoorModel.doors.add(new SimpleDoorModel.Door(base, doorBlock));
                }
                case ThickLadderBlock ladderBlock -> SimpleLadderModel.ladders.add(new SimpleLadderModel.Ladder(base, ladderBlock));
                case RocksBlock rocksBlock -> SimpleRocksModel.rocks.add(new SimpleRocksModel.Rocks(base, rocksBlock));
                case Block block1 -> {
                    if (blockName.contains("carved_window")){
                        SimplePillarModel.carvedWindows.add(new SimplePillarModel.StonePillar(block1, origin));
                    } else {
                        SimpleBlockModel.blocks.add(block1);
                    }
                }
            }
        }
    }

    public static void regularBlocks(BlockRecordTypes.RegularSet set, MiningTool tool) {
        BlockRecordTypes.RegularSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.base(), set.slab(), tool));
    }

    public static void regularBlocks(BlockRecordTypes.SimpleBlocks set, MiningTool tool) {
        BlockRecordTypes.SimpleBlocks.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.base(), set.slab(), tool));
    }

    public static void pillarBlocks(BlockRecordTypes.PillarSet set, Block origin, MiningTool tool) {
        BlockRecordTypes.PillarSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.base(), set.verticalSlab(), tool));
    }

    public static void carvedWindowBlocks(BlockRecordTypes.CarvedWindow set, Block origin) {
        BlockRecordTypes.CarvedWindow.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.block(), origin, MiningTool.PICKAXE));
    }

    public static void mainStoneBlocks(BlockRecordTypes.BaseStoneSet set){
        BlockRecordTypes.BaseStoneSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.base(), set.slab(), MiningTool.PICKAXE));
    }

    public static void plankBlocks(BlockRecordTypes.PlanksSet set, boolean vanilla) {
        BlockRecordTypes.PlanksSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.base(), set.slab(), MiningTool.AXE));
        WoodenVerticalSlabs.woodenVericalSlabs.add(set.verticalSlab());
        Planks.planks.add(set.base());
        if(!vanilla) Planks.moddedPlanks.add(set.base());
        Planks.planksSlabs.add(set.slab());
        if(!vanilla) WoodenVariants.stairs.add(set.stairs());
    }

    public static void woodBlocks(BlockRecordTypes.WoodSet set) {
        BlockRecordTypes.WoodSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.wood(), set.slab(), MiningTool.AXE));
        Logs.logs.add(set.log());
        Logs.logs.add(set.wood());
    }

    public static void mushroomStemBlocks(BlockRecordTypes.MushroomStemSet set) {
        BlockRecordTypes.MushroomStemSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.stem(), set.slab(), MiningTool.AXE));
    }

    public static void furnitureBlocks(BlockRecordTypes.WoodFurnitureBlocks set, Block base) {
        BlockRecordTypes.WoodFurnitureBlocks.getAllBlocks(set).forEach(block -> addBlocksToLists(block, base, null, MiningTool.AXE));
    }

    public static void woodRedstoneBlocks(BlockRecordTypes.WoodRedstoneBlocks set, Block base) {
        BlockRecordTypes.WoodRedstoneBlocks.getAllBlocks(set).forEach(block -> addBlocksToLists(block, base, null, MiningTool.AXE));
        if (!Objects.equals(Registries.BLOCK.getId(set.door()).getNamespace(), "minecraft")) {
            WoodenVariants.doors.add(set.door());
            WoodenVariants.trapdoors.add(set.trapdoor());
            WoodenVariants.pressurePlates.add(set.pressurePlate());
        }
    }
}
