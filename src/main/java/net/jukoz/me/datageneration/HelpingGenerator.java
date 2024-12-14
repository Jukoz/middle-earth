package net.jukoz.me.datageneration;

import net.jukoz.me.block.*;
import net.jukoz.me.datageneration.content.loot_tables.BlockDrops;
import net.jukoz.me.datageneration.content.loot_tables.LeavesDrops;
import net.jukoz.me.datageneration.content.models.*;
import net.jukoz.me.datageneration.content.tags.*;
import net.minecraft.block.PillarBlock;
import net.minecraft.registry.Registries;

import java.util.Objects;

public class HelpingGenerator {

    public static void generateFiles() {
        StoneBlockSets.registerModBlockSets();


        for (StoneBlockSets.SimpleBlockSetMain set : StoneBlockSets.setsMain) {
            SimpleBlockModel.blocks.add(set.base());

            SimpleSlabModel.slabs.add(new SimpleSlabModel.Slab(set.base(), set.slab()));
            SimpleVerticalSlabModel.verticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(set.base(), set.slab(), set.verticalSlab()));
            SimpleStairModel.stairs.add(new SimpleStairModel.Stair(set.base(), set.stairs()));
            SimpleWallModel.blocks.add(new SimpleWallModel.Wall(set.base(), set.wall()));
            SimplePressurePlateModel.pressurePlates.add(new SimplePressurePlateModel.PressurePlate(set.base(), set.pressurePlate()));
            SimpleButtonModel.buttons.add(new SimpleButtonModel.Button(set.base(), set.button()));
            SimpleTrapDoorModel.stoneTrapdoors.add(new SimpleTrapDoorModel.Trapdoor(set.base(), set.trapdoor()));
            SimpleStoneStoolModel.stools.add(set.stool());
            SimpleStoneTableModel.tables.add(set.table());
            SimpleStoneChairModel.chairs.add(set.chair());
            SimpleRocksModel.rocks.add(new SimpleRocksModel.Rocks(set.base(), set.rocks()));

            BlockDrops.blocks.add(set.base());
            BlockDrops.blocks.add(set.slab());
            BlockDrops.blocks.add(set.verticalSlab());
            BlockDrops.blocks.add(set.stairs());
            BlockDrops.blocks.add(set.wall());
            BlockDrops.blocks.add(set.pressurePlate());
            BlockDrops.blocks.add(set.button());
            BlockDrops.blocks.add(set.trapdoor());
            BlockDrops.blocks.add(set.stool());
            BlockDrops.blocks.add(set.table());
            BlockDrops.blocks.add(set.chair());
            BlockDrops.blocks.add(set.rocks());

            MineablePickaxe.blocks.add(set.base());
            MineablePickaxe.blocks.add(set.wall());
            MineablePickaxe.blocks.add(set.slab());
            MineablePickaxe.blocks.add(set.verticalSlab());
            MineablePickaxe.blocks.add(set.stairs());
            MineablePickaxe.blocks.add(set.pressurePlate());
            MineablePickaxe.blocks.add(set.button());
            MineablePickaxe.blocks.add(set.trapdoor());
            MineablePickaxe.blocks.add(set.stool());
            MineablePickaxe.blocks.add(set.table());
            MineablePickaxe.blocks.add(set.chair());
            MineablePickaxe.blocks.add(set.rocks());

            Walls.walls.add(set.wall());
        }

        for (StoneBlockSets.SimpleBlockSet set : StoneBlockSets.sets) {
            SimpleBlockModel.blocks.add(set.base());
            if (set.base().toString().contains("cobble") && !set.base().toString().contains("mossy")) {
                Stones.stones.add(set.base());
            }
            SimpleSlabModel.slabs.add(new SimpleSlabModel.Slab(set.base(), set.slab()));
            SimpleVerticalSlabModel.verticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(set.base(), set.slab(), set.verticalSlab()));
            SimpleStairModel.stairs.add(new SimpleStairModel.Stair(set.base(), set.stairs()));
            SimpleWallModel.blocks.add(new SimpleWallModel.Wall(set.base(), set.wall()));

            BlockDrops.blocks.add(set.base());
            BlockDrops.blocks.add(set.slab());
            BlockDrops.blocks.add(set.verticalSlab());
            BlockDrops.blocks.add(set.stairs());
            BlockDrops.blocks.add(set.wall());

            MineablePickaxe.blocks.add(set.base());
            MineablePickaxe.blocks.add(set.wall());
            MineablePickaxe.blocks.add(set.slab());
            MineablePickaxe.blocks.add(set.verticalSlab());
            MineablePickaxe.blocks.add(set.stairs());

            Walls.walls.add(set.wall());
        }

        for (WoodBlockSets.SimpleBlockSet set : WoodBlockSets.sets) {
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
            if(set.sapling() != null){
                Saplings.saplings.add(set.sapling());
            }

            WoodenSlabs.woodenSlabs.add(set.planksSlab());
            WoodenVerticalSlabs.woodenVericalSlabs.add(set.planksVerticalSlab());
            ModdedStrippedLogs.strippedLogs.add(set.strippedLog());

            LogsThatBurn.logsThatBurn.add(set.log());
            LogsThatBurn.logsThatBurn.add(set.wood());
            LogsThatBurn.logsThatBurn.add(set.strippedLog());
            LogsThatBurn.logsThatBurn.add(set.strippedWood());
        }

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
            SimpleTrapDoorModel.trapdoors.add(new SimpleTrapDoorModel.Trapdoor(set.planks(), set.trapdoor()));
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

            BlockDrops.blocks.add(set.block());
            BlockDrops.blocks.add(set.slab());
            BlockDrops.blocks.add(set.verticalSlab());
            BlockDrops.blocks.add(set.stairs());

            if(set.origin() != null && set.origin().toString().contains("wood")){
                MineableAxe.blocks.add(set.block());
                MineableAxe.blocks.add(set.slab());
                MineableAxe.blocks.add(set.verticalSlab());
                MineableAxe.blocks.add(set.stairs());
            }  else {
                MineablePickaxe.blocks.add(set.block());
                MineablePickaxe.blocks.add(set.slab());
                MineablePickaxe.blocks.add(set.verticalSlab());
                MineablePickaxe.blocks.add(set.stairs());
            }
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

        for(SimpleStoneStoolModel.VanillaStool stool : SimpleStoneStoolModel.vanillaStools){
            BlockDrops.blocks.add(stool.base());
            MineablePickaxe.blocks.add(stool.base());
        }

        for(SimpleStoneTableModel.VanillaTable table : SimpleStoneTableModel.vanillaTables) {
            BlockDrops.blocks.add(table.base());
            MineablePickaxe.blocks.add(table.base());
        }

        for(SimpleStoneChairModel.VanillaChair chair : SimpleStoneChairModel.vanillaChairs){
            BlockDrops.blocks.add(chair.base());
            MineablePickaxe.blocks.add(chair.base());
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
}
