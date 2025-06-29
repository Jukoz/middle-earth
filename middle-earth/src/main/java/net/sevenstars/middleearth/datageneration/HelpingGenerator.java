package net.sevenstars.middleearth.datageneration;

import net.minecraft.block.*;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.block.special.*;
import net.sevenstars.middleearth.block.special.verticalSlabs.VerticalSlabBlock;
import net.sevenstars.middleearth.block.utils.BlockRecordTypes;
import net.sevenstars.middleearth.block.utils.StoneBlockTypes;
import net.sevenstars.middleearth.block.utils.setBuilders.StoneBlockSetBuilder;
import net.sevenstars.middleearth.block.utils.setBuilders.WoodBlockSetBuilder;
import net.sevenstars.middleearth.datageneration.content.loot_tables.BlockDrops;
import net.sevenstars.middleearth.datageneration.content.loot_tables.LeavesDrops;
import net.minecraft.registry.Registries;
import net.sevenstars.middleearth.datageneration.content.models.*;
import net.sevenstars.middleearth.datageneration.content.tags.*;

import java.util.Objects;

public class HelpingGenerator {
    //TODO recipe for stone blocks
    //TODO tags for stone blocks
    //TODO top models for pillar stuff
    //TODO add carved windows to builder
    //TODO bricks -> brick issue

    public static void generateFiles() {
        for (StoneBlockSetBuilder set : StoneBlockSets.stoneSetsList){
            set.existingList.forEach(stoneBlockTypes -> {
                switch (stoneBlockTypes){
                    case BASE_BLOCKS -> mainStoneBlocks(set.baseBlocks);
                    case COBBLED_BLOCKS,COBBLESTONE_BLOCKS -> {
                        regularBlocks(set.cobblestoneBlocks);
                        if(set.hasMossy) regularBlocks(set.mossyCobblestoneBlocks);
                    }
                    case BRICK_BLOCKS, POLISHED_BRICK_BLOCKS -> {
                        regularBlocks(set.brickBlocks);
                        if(set.hasMossy) regularBlocks(set.mossyBrickBlocks);
                        if(set.hasCracked) regularBlocks(set.crackedBrickBlocks);
                    }
                    case TILE_BLOCKS -> {
                        regularBlocks(set.tileBlocks);
                        if(set.hasMossy) regularBlocks(set.mossyTileBlocks);
                        if(set.hasCracked) regularBlocks(set.crackedTileBlocks);
                    }
                    case SMOOTH_BLOCKS -> {
                        regularBlocks(set.smoothBlocks);
                        if(set.hasMossy) regularBlocks(set.mossySmoothBlocks);
                        if(set.hasCracked) regularBlocks(set.crackedSmoothBlocks);
                    }
                    case POLISHED_BLOCKS, POLISHED_BLOCKS_PILLAR -> {
                        regularBlocks(set.polishedBlocks);
                        if(set.hasMossy) regularBlocks(set.mossyPolishedBlocks);
                        if(set.hasCracked) regularBlocks(set.crackedPolishedBlocks);
                    }
                    case BRICKWORK_BLOCKS -> regularBlocks(set.brickworkBlocks);
                    case OLD_BLOCKS, OLD_BLOCKS_PILLAR -> regularBlocks(set.oldBlocks);
                    case PILLAR_BLOCKS -> {
                        pillarBlocks(set.pillarBlocks, set.brickBlocks.base());
                        if(set.hasMossy) pillarBlocks(set.mossyPillarBlocks, set.pillarBlocks.base());
                        if(set.hasCracked) pillarBlocks(set.crackedPillarBlocks, set.pillarBlocks.base());
                    }
                    case CHISELED_BLOCKS -> {
                        if (set.existingList.contains(StoneBlockTypes.BASE_BLOCKS)) pillarBlocks(set.chiseledBlocks, set.baseBlocks.slab());
                        if (set.existingList.contains(StoneBlockTypes.BRICK_BLOCKS)) pillarBlocks(set.chiseledBricksBlocks, set.brickBlocks.slab());
                        if (set.existingList.contains(StoneBlockTypes.POLISHED_BLOCKS)) pillarBlocks(set.chiseledPolishedBlocks, set.polishedBlocks.slab());
                        if (set.existingList.contains(StoneBlockTypes.TILE_BLOCKS)) pillarBlocks(set.chiseledTilesBlocks, set.tileBlocks.slab());
                        if (set.existingList.contains(StoneBlockTypes.SMOOTH_BLOCKS)) pillarBlocks(set.chiseledSmoothBlocks, set.smoothBlocks.slab());
                    }
                    case CARVED_WINDOW -> {
                        carvedWindowBlocks(set.carvedWindows, set.baseBlocks.base());
                    }
                }
            });
        }

        for (WoodBlockSetBuilder set : WoodBlockSets.woodSetsList){
            set.existingList.forEach(stoneBlockTypes -> {
                switch (stoneBlockTypes){
                    case LOG_BLOCKS -> woodBlocks(set.logBlocks);
                    case STRIPPED_LOG_BLOCKS -> woodBlocks(set.strippedLogBlocks);
                    case PLANK_BLOCKS -> plankBlocks(set.planksBlocks);
                    case SHINGLE_BLOCKS -> regularBlocks(set.shinglesBlocks);
                    case ROOFING_BLOCKS -> regularBlocks(set.roofingBlocks);
                    case FURNITURE_BLOCKS -> furnitureBlocks(set.furnitureBlocks, set.planksBlocks.base());
                    case REDSTONE_BLOCKS -> woodRedstoneBlocks(set.redstoneBlocks, set.planksBlocks.base());
                    case LEAVES -> {
                        if (!Objects.equals(Registries.BLOCK.getId(set.leaves).getNamespace(), "minecraft")){
                            LeavesSets.blocks.add(set.leaves);
                        }
                    }
                }
            });
        }

        /*for (WoodBlockSets.SimpleBlockSet set : WoodBlockSets.sets) {
            if(set.leaves() != null) {
                LeavesSets.blocks.add(set.leaves());
                if(set.sapling() != null){
                    LeavesDrops.blocks.add(new LeavesDrops.LeavesDrop(set.leaves(), set.sapling()));
                }
            }
            if(set.sapling() != null){
                BlockDrops.blocks.add(set.sapling());
            }
            SimplePillarModel.blocks.add(new SimplePillarModel.Pillar(set.log()));
            SimplePillarModel.blocks.add(new SimplePillarModel.Pillar(set.strippedLog()));

            SimpleBlockModel.woodBlocks.add(set.wood());
            SimpleBlockModel.woodBlocks.add(set.strippedWood());

            SimpleBlockModel.blocks.add(set.planks());

            SimpleWallModel.blocks.add(new SimpleWallModel.Wall(set.log(), set.woodWall()));
            SimpleWallModel.strippedWalls.add(new SimpleWallModel.Wall(set.strippedWood(), set. strippedWoodWall()));

            SimpleFenceModel.blocks.add(new SimpleFenceModel.Fence(set.planks(), set.planksFence()));
            SimpleFenceModel.blocks.add(new SimpleFenceModel.Fence(set.log(), set.woodFence()));
            SimpleFenceModel.strippedFences.add(new SimpleFenceModel.Fence(set.strippedWood(), set.strippedWoodFence()));

            SimpleSlabModel.slabs.add(new SimpleSlabModel.Slab(set.planks(), set.planksSlab()));
            SimpleSlabModel.woodSlabs.add(new SimpleSlabModel.Slab(set.wood(), set.woodSlab()));
            SimpleSlabModel.strippedSlabs.add(new SimpleSlabModel.Slab(set.strippedWood(), set.strippedWoodSlab()));

            SimpleVerticalSlabModel.verticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(set.planks(), set.planksSlab(), set.planksVerticalSlab()));
            SimpleVerticalSlabModel.woodVerticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(set.wood(), set.woodSlab(), set.woodVerticalSlab()));
            SimpleVerticalSlabModel.strippedVerticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(set.strippedWood(), set.strippedWoodSlab(), set.strippedWoodVerticalSlab()));

            SimpleStairModel.stairs.add(new SimpleStairModel.Stair(set.planks(), set.planksStairs()));
            SimpleStairModel.woodStairs.add(new SimpleStairModel.Stair(set.wood(), set.woodStairs()));
            SimpleStairModel.strippedStairs.add(new SimpleStairModel.Stair(set.strippedWood(), set.strippedWoodStairs()));

            SimpleFenceGateModel.blocks.add(new SimpleFenceGateModel.FenceGate(set.planks(), set.planksGate()));
            SimpleButtonModel.buttons.add(new SimpleButtonModel.Button(set.planks(), set.button()));
            SimplePressurePlateModel.pressurePlates.add(new SimplePressurePlateModel.PressurePlate(set.planks(), set.pressurePlate()));
            SimpleTrapDoorModel.trapdoors.add(new SimpleTrapDoorModel.Trapdoor(set.planks(), set.trapdoor()));
            SimpleDoorModel.doors.add(new SimpleDoorModel.Door(set.planks(), set.door()));

            SimpleWoodStoolModel.stools.add(set.stool());
            SimpleWoodBenchModel.benchs.add(set.bench());
            SimpleWoodTableModel.tables.add(set.table());
            SimpleWoodChairModel.chairs.add(set.chair());

            SimpleLadderModel.ladders.add(new SimpleLadderModel.Ladder(set.planks(), set.ladder()));

            BlockDrops.blocks.add(set.log());
            BlockDrops.blocks.add(set.strippedLog());
            BlockDrops.blocks.add(set.wood());
            BlockDrops.blocks.add(set.strippedWood());
            BlockDrops.blocks.add(set.woodWall());
            BlockDrops.blocks.add(set.strippedWoodWall());
            BlockDrops.blocks.add(set.woodFence());
            BlockDrops.blocks.add(set.strippedWoodFence());
            BlockDrops.blocks.add(set.planks());
            BlockDrops.blocks.add(set.planksSlab());
            BlockDrops.blocks.add(set.woodSlab());
            BlockDrops.blocks.add(set.strippedWoodSlab());
            BlockDrops.blocks.add(set.planksVerticalSlab());
            BlockDrops.blocks.add(set.woodVerticalSlab());
            BlockDrops.blocks.add(set.strippedWoodVerticalSlab());
            BlockDrops.blocks.add(set.planksStairs());
            BlockDrops.blocks.add(set.woodStairs());
            BlockDrops.blocks.add(set.strippedWoodStairs());
            BlockDrops.blocks.add(set.planksFence());
            BlockDrops.blocks.add(set.planksGate());
            BlockDrops.blocks.add(set.button());
            BlockDrops.blocks.add(set.pressurePlate());
            BlockDrops.blocks.add(set.trapdoor());
            BlockDrops.blocks.add(set.door());
            BlockDrops.blocks.add(set.stool());
            BlockDrops.blocks.add(set.bench());
            BlockDrops.blocks.add(set.table());
            BlockDrops.blocks.add(set.chair());
            BlockDrops.blocks.add(set.ladder());

            MineableAxe.blocks.add(set.log());
            MineableAxe.blocks.add(set.strippedLog());
            MineableAxe.blocks.add(set.wood());
            MineableAxe.blocks.add(set.strippedWood());
            MineableAxe.blocks.add(set.woodWall());
            MineableAxe.blocks.add(set.strippedWoodWall());
            MineableAxe.blocks.add(set.woodFence());
            MineableAxe.blocks.add(set.strippedWoodFence());
            MineableAxe.blocks.add(set.planks());
            MineableAxe.blocks.add(set.planksSlab());
            MineableAxe.blocks.add(set.woodSlab());
            MineableAxe.blocks.add(set.strippedWoodSlab());
            MineableAxe.blocks.add(set.planksVerticalSlab());
            MineableAxe.blocks.add(set.woodVerticalSlab());
            MineableAxe.blocks.add(set.strippedWoodVerticalSlab());
            MineableAxe.blocks.add(set.planksStairs());
            MineableAxe.blocks.add(set.woodStairs());
            MineableAxe.blocks.add(set.strippedWoodStairs());
            MineableAxe.blocks.add(set.planksFence());
            MineableAxe.blocks.add(set.planksGate());
            MineableAxe.blocks.add(set.button());
            MineableAxe.blocks.add(set.pressurePlate());
            MineableAxe.blocks.add(set.trapdoor());
            MineableAxe.blocks.add(set.door());
            MineableAxe.blocks.add(set.stool());
            MineableAxe.blocks.add(set.bench());
            MineableAxe.blocks.add(set.table());
            MineableAxe.blocks.add(set.chair());
            MineableAxe.blocks.add(set.ladder());

            if(set.leaves() != null) MineableHoe.blocks.add(set.leaves());

            Buttons.buttons.add(set.button());
            Fences.fences.add(set.planksFence());
            Fences.fences.add(set.woodFence());
            Fences.fences.add(set.strippedWoodFence());
            FenceGates.fenceGates.add(set.planksGate());
            Logs.logs.add(set.log());
            Logs.logs.add(set.strippedLog());
            Logs.logs.add(set.wood());
            Logs.logs.add(set.strippedWood());
            PressurePlates.pressurePlates.add(set.pressurePlate());
            Walls.walls.add(set.woodWall());
            Walls.walls.add(set.strippedWoodWall());
            Planks.planks.add(set.planks());
            Doors.doors.add(set.door());
            Trapdoors.trapdoors.add(set.trapdoor());

            WoodenSlabs.woodenSlabs.add(set.planksSlab());
            WoodenVerticalSlabs.woodenVericalSlabs.add(set.planksVerticalSlab());
            ModdedStrippedLogs.strippedLogs.add(set.strippedLog());

            LogsThatBurn.logsThatBurn.add(set.log());
            LogsThatBurn.logsThatBurn.add(set.wood());
            LogsThatBurn.logsThatBurn.add(set.strippedLog());
            LogsThatBurn.logsThatBurn.add(set.strippedWood());
        }*/

        SimpleBlockModel.blocks.addAll(LeavesSets.blocks);

        for (MushroomBlockSets.MushroomBlockSet set : MushroomBlockSets.sets) {
            if(!Objects.equals(Registries.BLOCK.getId(set.stem()).getPath(), "mushroom_stem")) {
                SimpleBlockModel.blocks.add(set.stem());

                MineableAxe.blocks.add(set.stem());
            }

            SimpleWallModel.blocks.add(new SimpleWallModel.Wall(set.stem(), set.stemWall()));
            SimpleFenceModel.blocks.add(new SimpleFenceModel.Fence(set.stem(), set.stemFence()));
            SimpleBlockModel.blocks.add(set.planks());
            SimpleSlabModel.slabs.add(new SimpleSlabModel.Slab(set.planks(), set.planksSlab()));
            SimpleVerticalSlabModel.verticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(set.planks(), set.planksSlab(), set.planksVerticalSlab()));
            SimpleStairModel.stairs.add(new SimpleStairModel.Stair(set.planks(), set.planksStairs()));
            SimpleFenceModel.blocks.add(new SimpleFenceModel.Fence(set.planks(), set.planksFence()));
            SimpleFenceGateModel.blocks.add(new SimpleFenceGateModel.FenceGate(set.planks(), set.planksGate()));
            SimpleButtonModel.buttons.add(new SimpleButtonModel.Button(set.planks(), set.button()));
            SimplePressurePlateModel.pressurePlates.add(new SimplePressurePlateModel.PressurePlate(set.planks(), set.pressurePlate()));
            SimpleTrapDoorModel.trapdoors.add(new SimpleTrapDoorModel.Trapdoor(set.planks(), set.trapdoor(), true));
            SimpleDoorModel.doors.add(new SimpleDoorModel.Door(set.planks(), set.door()));
            SimpleLadderModel.ladders.add(new SimpleLadderModel.Ladder(set.planks(), set.ladder()));

            BlockDrops.blocks.add(set.stemWall());
            BlockDrops.blocks.add(set.stemFence());
            BlockDrops.blocks.add(set.planks());
            BlockDrops.blocks.add(set.planksSlab());
            BlockDrops.blocks.add(set.planksVerticalSlab());
            BlockDrops.blocks.add(set.planksStairs());
            BlockDrops.blocks.add(set.planksFence());
            BlockDrops.blocks.add(set.planksGate());
            BlockDrops.blocks.add(set.button());
            BlockDrops.blocks.add(set.pressurePlate());
            BlockDrops.blocks.add(set.trapdoor());
            BlockDrops.blocks.add(set.door());
            BlockDrops.blocks.add(set.stool());
            BlockDrops.blocks.add(set.bench());
            BlockDrops.blocks.add(set.table());
            BlockDrops.blocks.add(set.chair());
            BlockDrops.blocks.add(set.ladder());

            MineableAxe.blocks.add(set.stemWall());
            MineableAxe.blocks.add(set.stemFence());
            MineableAxe.blocks.add(set.planks());
            MineableAxe.blocks.add(set.planksSlab());
            MineableAxe.blocks.add(set.planksVerticalSlab());
            MineableAxe.blocks.add(set.planksStairs());
            MineableAxe.blocks.add(set.planksFence());
            MineableAxe.blocks.add(set.planksGate());
            MineableAxe.blocks.add(set.button());
            MineableAxe.blocks.add(set.pressurePlate());
            MineableAxe.blocks.add(set.trapdoor());
            MineableAxe.blocks.add(set.door());
            MineableAxe.blocks.add(set.stool());
            MineableAxe.blocks.add(set.bench());
            MineableAxe.blocks.add(set.table());
            MineableAxe.blocks.add(set.chair());
            MineableAxe.blocks.add(set.ladder());

            Buttons.buttons.add(set.button());
            Fences.fences.add(set.planksFence());
            Fences.fences.add(set.stemFence());
            FenceGates.fenceGates.add(set.planksGate());
            Walls.walls.add(set.stemWall());
            PressurePlates.pressurePlates.add(set.pressurePlate());
            Planks.planks.add(set.planks());
            Doors.doors.add(set.door());
            Trapdoors.trapdoors.add(set.trapdoor());
            WoodenSlabs.woodenSlabs.add(set.planksSlab());

            SimpleWoodStoolModel.stools.add(set.stool());
            SimpleWoodBenchModel.benchs.add(set.bench());
            SimpleWoodTableModel.tables.add(set.table());
            SimpleWoodChairModel.chairs.add(set.chair());
        }

        for (OtherBlockSets.RoofBlockSet set : OtherBlockSets.sets) {
            SimpleBlockModel.blocks.add(set.block());
            SimpleSlabModel.slabs.add(new SimpleSlabModel.Slab(set.block(), set.slab()));
            SimpleVerticalSlabModel.verticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(set.block(), set.slab(), set.verticalSlab()));
            SimpleStairModel.stairs.add(new SimpleStairModel.Stair(set.block(), set.stairs()));
            SimpleWallModel.blocks.add(new SimpleWallModel.Wall(set.block(), set.wall()));

            BlockDrops.blocks.add(set.block());
            BlockDrops.blocks.add(set.slab());
            BlockDrops.blocks.add(set.verticalSlab());
            BlockDrops.blocks.add(set.stairs());
            BlockDrops.blocks.add(set.wall());

            if(set.origin() != null && (set.origin().toString().contains("planks") || set.block().toString().contains("roofing"))){
                MineableAxe.blocks.add(set.block());
                MineableAxe.blocks.add(set.slab());
                MineableAxe.blocks.add(set.verticalSlab());
                MineableAxe.blocks.add(set.stairs());
                MineableAxe.blocks.add(set.wall());
            } else if (set.block().toString().contains("thatch")) {
                MineableHoe.blocks.add(set.block());
                MineableHoe.blocks.add(set.slab());
                MineableHoe.blocks.add(set.verticalSlab());
                MineableHoe.blocks.add(set.stairs());
                MineableHoe.blocks.add(set.wall());
            } else {
                MineablePickaxe.blocks.add(set.block());
                MineablePickaxe.blocks.add(set.slab());
                MineablePickaxe.blocks.add(set.verticalSlab());
                MineablePickaxe.blocks.add(set.stairs());
                MineablePickaxe.blocks.add(set.wall());
            }

            Walls.walls.add(set.wall());
            if (set.block().toString().contains("shingles")) {
                Shingles.shingles.add(set.block());
            }
        }

        for (OtherBlockSets.MiscBlockSet set : OtherBlockSets.specialWoodSets) {
            if (set.block() instanceof PillarBlock){
                SimplePillarModel.blocks.add(new SimplePillarModel.Pillar(set.block()));
            } else {
                SimpleBlockModel.blocks.add(set.block());
            }
            SimpleSlabModel.slabs.add(new SimpleSlabModel.Slab(set.block(), set.slab()));
            SimpleVerticalSlabModel.verticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(set.block(), set.slab(), set.verticalSlab()));
            SimpleStairModel.stairs.add(new SimpleStairModel.Stair(set.block(), set.stairs()));
            SimpleWallModel.blocks.add(new SimpleWallModel.Wall(set.block(), set.wall()));

            BlockDrops.blocks.add(set.block());
            BlockDrops.blocks.add(set.slab());
            BlockDrops.blocks.add(set.verticalSlab());
            BlockDrops.blocks.add(set.stairs());
            BlockDrops.blocks.add(set.wall());

            MineableAxe.blocks.add(set.block());
            MineableAxe.blocks.add(set.slab());
            MineableAxe.blocks.add(set.verticalSlab());
            MineableAxe.blocks.add(set.stairs());
            MineableAxe.blocks.add(set.wall());

            Walls.walls.add(set.wall());
        }

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
        });

        SimpleWallModel.vanillaStrippedWalls.forEach(block -> {
            Walls.walls.add(block.wall());
            MineableAxe.blocks.add(block.wall());
        });

        SimpleFenceModel.vanillaWoodFences.forEach(block -> {
            Fences.fences.add(block.fence());
            MineableAxe.blocks.add(block.fence());
        });

        SimpleFenceModel.vanillaStrippedFences.forEach(block -> {
            Fences.fences.add(block.fence());
            MineableAxe.blocks.add(block.fence());
        });
        

        for (OreRockSets.OreRockSet set : OreRockSets.sets) {
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
        }
    }

    public static void addBlocksToLists(Block block, Block base, Block slab){
        MineablePickaxe.blocks.add(block);
        BlockDrops.blocks.add(block);

        String blockName = block.getName().toString();

        if (!(Objects.equals(Registries.BLOCK.getId(block).getNamespace(), "minecraft"))){
            switch (block){
                case PillarBlock pillarBlock -> {
                    if (blockName.contains("_wood")){
                        SimpleBlockModel.woodBlocks.add(pillarBlock);
                    } else {
                        SimplePillarModel.blocks.add(new SimplePillarModel.Pillar(pillarBlock));
                    }
                }
                case SlabBlock slabBlock -> {
                    if (blockName.contains("stripped")){
                        SimpleSlabModel.strippedSlabs.add(new SimpleSlabModel.Slab(base, slab));
                    } else if (blockName.contains("wood")){
                        SimpleSlabModel.woodSlabs.add(new SimpleSlabModel.Slab(base, slab));
                    } else{
                        SimpleSlabModel.slabs.add(new SimpleSlabModel.Slab(base, slabBlock));
                    }
                }
                case VerticalSlabBlock verticalSlabBlock -> {
                    if (blockName.contains("carved_window")
                            || blockName.contains("chiseled")
                            || blockName.contains("pillar")) {
                        SimpleVerticalSlabModel.columnVerticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(base, slab, verticalSlabBlock));
                    }else if (blockName.contains("stripped")){
                        SimpleVerticalSlabModel.strippedVerticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(base, slab, verticalSlabBlock));
                    } else if (blockName.contains("wood")){
                        SimpleVerticalSlabModel.woodVerticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(base, slab, verticalSlabBlock));
                    } else{
                        SimpleVerticalSlabModel.verticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(base, slab, verticalSlabBlock));
                    }
                }
                case StairsBlock stairsBlock -> {
                    if (blockName.contains("stripped")){
                        SimpleStairModel.strippedStairs.add(new SimpleStairModel.Stair(base, stairsBlock));
                    } else if (blockName.contains("wood")){
                        SimpleStairModel.woodStairs.add(new SimpleStairModel.Stair(base, stairsBlock));
                    } else{
                        SimpleStairModel.stairs.add(new SimpleStairModel.Stair(base, stairsBlock));
                    }
                }
                case WallBlock wallBlock -> {
                    Walls.walls.add(wallBlock);
                    if (blockName.contains("stripped")){
                        SimpleWallModel.strippedWalls.add(new SimpleWallModel.Wall(base, wallBlock));
                    } else {
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
                    Buttons.buttons.add(buttonBlock);
                    SimpleButtonModel.buttons.add(new SimpleButtonModel.Button(base, buttonBlock));
                }
                case TrapdoorBlock trapdoorBlock -> {
                    Trapdoors.trapdoors.add(trapdoorBlock);
                    SimpleTrapDoorModel.trapdoors.add(new SimpleTrapDoorModel.Trapdoor(base, trapdoorBlock, true));
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
                        SimplePillarModel.carvedWindows.add(new SimplePillarModel.StonePillar(block1, base));
                    } else {
                        SimpleBlockModel.blocks.add(block1);
                    }
                }
            }
        }
    }

    public static void regularBlocks(BlockRecordTypes.RegularSet set) {
        BlockRecordTypes.RegularSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.base(), set.slab()));
    }

    public static void pillarBlocks(BlockRecordTypes.PillarSet set, Block origin) {
        BlockRecordTypes.PillarSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, origin, set.verticalSlab()));
    }

    public static void carvedWindowBlocks(BlockRecordTypes.CarvedWindow set, Block origin) {
        BlockRecordTypes.CarvedWindow.getAllBlocks(set).forEach(block -> addBlocksToLists(block, origin, null));
    }

    public static void mainStoneBlocks(BlockRecordTypes.BaseStoneSet set){
        BlockRecordTypes.BaseStoneSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.base(), set.slab()));
    }

    public static void plankBlocks(BlockRecordTypes.PlanksSet set) {
        BlockRecordTypes.PlanksSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.base(), set.slab()));
    }

    public static void woodBlocks(BlockRecordTypes.WoodSet set) {
        BlockRecordTypes.WoodSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.log(), set.slab()));
    }

    public static void furnitureBlocks(BlockRecordTypes.WoodFurnitureBlocks set, Block base) {
        BlockRecordTypes.WoodFurnitureBlocks.getAllBlocks(set).forEach(block -> addBlocksToLists(block, base, null));
    }

    public static void woodRedstoneBlocks(BlockRecordTypes.WoodRedstoneBlocks set, Block base) {
        BlockRecordTypes.WoodRedstoneBlocks.getAllBlocks(set).forEach(block -> addBlocksToLists(block, base, null));
    }
}
