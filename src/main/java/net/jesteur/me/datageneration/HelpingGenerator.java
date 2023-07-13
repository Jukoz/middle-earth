package net.jesteur.me.datageneration;

import net.jesteur.me.block.SimpleBlockSets;
import net.jesteur.me.block.WoodBlockSets;
import net.jesteur.me.datageneration.content.loot_tables.BlockDrops;
import net.jesteur.me.datageneration.content.loot_tables.LeavesDrops;
import net.jesteur.me.datageneration.content.models.*;
import net.jesteur.me.datageneration.content.tags.*;

public class HelpingGenerator {

    public static void generateFiles() {
        SimpleBlockSets.registerModBlockSets();

        for (SimpleBlockSets.SimpleBlockSet set : SimpleBlockSets.sets) {
            SimpleBlockModel.blocks.add(set.base());
            SimpleSlabModel.blocks.add(new SimpleSlabModel.Slab(set.base(), set.slab()));
            SimpleStairModel.blocks.add(new SimpleStairModel.Stair(set.base(), set.stairs()));
            SimpleWallModel.blocks.add(new SimpleWallModel.Wall(set.base(), set.wall()));

            BlockDrops.blocks.add(set.base());
            BlockDrops.blocks.add(set.slab());
            BlockDrops.blocks.add(set.stairs());
            BlockDrops.blocks.add(set.wall());

            MineablePickaxe.blocks.add(set.base());
            MineablePickaxe.blocks.add(set.wall());
            MineablePickaxe.blocks.add(set.slab());
            MineablePickaxe.blocks.add(set.stairs());

            Walls.walls.add(set.wall());
        }

        for (WoodBlockSets.SimpleBlockSet set : WoodBlockSets.sets) {
            SimpleBlockModel.blocks.add(set.leaves());
            SimplePillarModel.blocks.add(new SimplePillarModel.Pillar(set.log()));
            SimpleWallModel.blocks.add(new SimpleWallModel.Wall(set.log(), set.woodWall()));
            SimpleBlockModel.blocks.add(set.planks());
            SimpleSlabModel.blocks.add(new SimpleSlabModel.Slab(set.planks(), set.planksSlab()));
            SimpleStairModel.blocks.add(new SimpleStairModel.Stair(set.planks(), set.planksStairs()));
            SimpleFenceModel.blocks.add(new SimpleFenceModel.Fence(set.planks(), set.planksFence()));
            SimpleFenceGateModel.blocks.add(new SimpleFenceGateModel.FenceGate(set.planks(), set.planksGate()));
            SimpleButtonModel.blocks.add(new SimpleButtonModel.Button(set.planks(), set.button()));
            SimplePressurePlateModel.blocks.add(new SimplePressurePlateModel.PressurePlate(set.planks(), set.pressurePlate()));

            LeavesDrops.blocks.add(set.leaves());
            BlockDrops.blocks.add(set.log());
            BlockDrops.blocks.add(set.wood());
            BlockDrops.blocks.add(set.woodWall());
            BlockDrops.blocks.add(set.planks());
            BlockDrops.blocks.add(set.planksSlab());
            BlockDrops.blocks.add(set.planksStairs());
            BlockDrops.blocks.add(set.planksFence());
            BlockDrops.blocks.add(set.planksGate());
            BlockDrops.blocks.add(set.button());
            BlockDrops.blocks.add(set.pressurePlate());

            MineableAxe.blocks.add(set.log());
            MineableAxe.blocks.add(set.wood());
            MineableAxe.blocks.add(set.woodWall());
            MineableAxe.blocks.add(set.planks());
            MineableAxe.blocks.add(set.planksSlab());
            MineableAxe.blocks.add(set.planksStairs());
            MineableAxe.blocks.add(set.planksFence());
            MineableAxe.blocks.add(set.planksGate());
            MineableAxe.blocks.add(set.button());
            MineableAxe.blocks.add(set.pressurePlate());

            Buttons.buttons.add(set.button());
            Fences.fences.add(set.planksFence());
            FenceGates.fenceGates.add(set.planksGate());
            Logs.logs.add(set.log());
            Logs.logs.add(set.wood());
            Logs.logs.add(set.woodWall());
            PressurePlates.pressurePlates.add(set.pressurePlate());
            Walls.walls.add(set.woodWall());
            Planks.planks.add(set.planks());
        }
    }
}
