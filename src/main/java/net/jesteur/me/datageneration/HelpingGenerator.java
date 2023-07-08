package net.jesteur.me.datageneration;

import net.jesteur.me.block.SimpleBlockSets;
import net.jesteur.me.block.WoodBlockSets;
import net.jesteur.me.datageneration.content.loot_tables.BlockDrops;
import net.jesteur.me.datageneration.content.models.*;
import net.jesteur.me.datageneration.content.tags.MineablePickaxe;
import net.jesteur.me.datageneration.content.tags.Walls;

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
            // Wood
            SimpleBlockModel.blocks.add(set.wood());
            SimpleWallModel.blocks.add(new SimpleWallModel.Wall(set.wood(), set.woodWall()));
            // Log
            SimplePillarModel.blocks.add(new SimplePillarModel.Pillar(set.log()));

            //BlockDrops.blocks.add(set.wood());
            //BlockDrops.blocks.add(set.woodWall());
            //BlockDrops.blocks.add(set.log());
            //BlockDrops.blocks.add(set.logWall());


        }
    }
}
