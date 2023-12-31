package net.jukoz.me.datageneration;

import net.jukoz.me.block.*;
import net.jukoz.me.datageneration.content.loot_tables.BlockDrops;
import net.jukoz.me.datageneration.content.loot_tables.LeavesDrops;
import net.jukoz.me.datageneration.content.models.*;
import net.jukoz.me.datageneration.content.tags.*;
import net.minecraft.registry.Registries;

import java.util.Objects;

public class HelpingGenerator {

    public static void generateFiles() {
        StoneBlockSets.registerModBlockSets();

        for (StoneBlockSets.SimpleBlockSet set : StoneBlockSets.sets) {
            SimpleBlockModel.blocks.add(set.base());
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
                SimpleBlockModel.blocks.add(set.leaves());
                LeavesDrops.blocks.add(set.leaves());
            }
            SimplePillarModel.blocks.add(new SimplePillarModel.Pillar(set.log()));
            SimpleWallModel.blocks.add(new SimpleWallModel.Wall(set.log(), set.woodWall()));
            SimpleBlockModel.blocks.add(set.planks());
            SimpleSlabModel.slabs.add(new SimpleSlabModel.Slab(set.planks(), set.planksSlab()));
            SimpleVerticalSlabModel.verticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(set.planks(), set.planksSlab(), set.planksVerticalSlab()));
            SimpleStairModel.stairs.add(new SimpleStairModel.Stair(set.planks(), set.planksStairs()));
            SimpleFenceModel.blocks.add(new SimpleFenceModel.Fence(set.planks(), set.planksFence()));
            SimpleFenceGateModel.blocks.add(new SimpleFenceGateModel.FenceGate(set.planks(), set.planksGate()));
            SimpleButtonModel.blocks.add(new SimpleButtonModel.Button(set.planks(), set.button()));
            SimplePressurePlateModel.blocks.add(new SimplePressurePlateModel.PressurePlate(set.planks(), set.pressurePlate()));
            SimpleTrapDoorModel.trapdoors.add(new SimpleTrapDoorModel.Trapdoor(set.planks(), set.trapdoor()));
            SimpleDoorModel.doors.add(new SimpleDoorModel.Door(set.planks(), set.door()));

            BlockDrops.blocks.add(set.log());
            BlockDrops.blocks.add(set.wood());
            BlockDrops.blocks.add(set.woodWall());
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

            MineableAxe.blocks.add(set.log());
            MineableAxe.blocks.add(set.wood());
            MineableAxe.blocks.add(set.woodWall());
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

            Buttons.buttons.add(set.button());
            Fences.fences.add(set.planksFence());
            FenceGates.fenceGates.add(set.planksGate());
            Logs.logs.add(set.log());
            Logs.logs.add(set.wood());
            Logs.logs.add(set.woodWall());
            PressurePlates.pressurePlates.add(set.pressurePlate());
            Walls.walls.add(set.woodWall());
            Planks.planks.add(set.planks());
            Doors.doors.add(set.door());
            Trapdoors.trapdoors.add(set.trapdoor());
        }

        for (MushroomBlockSets.MushroomBlockSet set : MushroomBlockSets.sets) {
            if(!Objects.equals(Registries.BLOCK.getId(set.stem()).getPath(), "mushroom_stem")) {
                SimpleBlockModel.blocks.add(set.stem());

                MineableAxe.blocks.add(set.stem());
            }


            SimpleWallModel.blocks.add(new SimpleWallModel.Wall(set.stem(), set.stemWall()));
            SimpleBlockModel.blocks.add(set.planks());
            SimpleSlabModel.slabs.add(new SimpleSlabModel.Slab(set.planks(), set.planksSlab()));
            SimpleVerticalSlabModel.verticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(set.planks(), set.planksSlab(), set.planksVerticalSlab()));
            SimpleStairModel.stairs.add(new SimpleStairModel.Stair(set.planks(), set.planksStairs()));
            SimpleFenceModel.blocks.add(new SimpleFenceModel.Fence(set.planks(), set.planksFence()));
            SimpleFenceGateModel.blocks.add(new SimpleFenceGateModel.FenceGate(set.planks(), set.planksGate()));
            SimpleButtonModel.blocks.add(new SimpleButtonModel.Button(set.planks(), set.button()));
            SimplePressurePlateModel.blocks.add(new SimplePressurePlateModel.PressurePlate(set.planks(), set.pressurePlate()));
            SimpleTrapDoorModel.trapdoors.add(new SimpleTrapDoorModel.Trapdoor(set.planks(), set.trapdoor()));
            SimpleDoorModel.doors.add(new SimpleDoorModel.Door(set.planks(), set.door()));

            BlockDrops.blocks.add(set.stemWall());
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

            MineableAxe.blocks.add(set.stemWall());
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

            Buttons.buttons.add(set.button());
            Fences.fences.add(set.planksFence());
            FenceGates.fenceGates.add(set.planksGate());
            Walls.walls.add(set.stemWall());
            PressurePlates.pressurePlates.add(set.pressurePlate());
            Planks.planks.add(set.planks());
            Doors.doors.add(set.door());
            Trapdoors.trapdoors.add(set.trapdoor());
        }

        for (RoofBlockSets.RoofBlockSet set : RoofBlockSets.sets) {
            SimpleBlockModel.blocks.add(set.block());
            SimpleSlabModel.slabs.add(new SimpleSlabModel.Slab(set.block(), set.slab()));
            SimpleVerticalSlabModel.verticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(set.block(), set.slab(), set.verticalSlab()));
            SimpleStairModel.stairs.add(new SimpleStairModel.Stair(set.block(), set.stairs()));

            BlockDrops.blocks.add(set.block());
            BlockDrops.blocks.add(set.slab());
            BlockDrops.blocks.add(set.verticalSlab());
            BlockDrops.blocks.add(set.stairs());

            if(set.origin() != null && set.origin().toString().contains("planks")){
                MineableAxe.blocks.add(set.block());
                MineableAxe.blocks.add(set.slab());
                MineableAxe.blocks.add(set.verticalSlab());
                MineableAxe.blocks.add(set.stairs());
            } else {
                MineablePickaxe.blocks.add(set.block());
                MineablePickaxe.blocks.add(set.slab());
                MineablePickaxe.blocks.add(set.verticalSlab());
                MineablePickaxe.blocks.add(set.stairs());
            }

        }

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
