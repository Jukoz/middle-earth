package net.jesteur.me.datageneration;

import net.jesteur.me.block.ModBlockSets;
import net.jesteur.me.block.ModBlocks;
import net.jesteur.me.datageneration.content.models.SimpleBlockModel;
import net.jesteur.me.datageneration.content.models.SimpleSlabModel;
import net.jesteur.me.datageneration.content.models.SimpleStairModel;
import net.jesteur.me.datageneration.content.models.SimpleWallModel;
import net.jesteur.me.datageneration.content.tags.MineablePickaxe;
import net.jesteur.me.datageneration.content.tags.Walls;

public class HelpingGenerator {

    public static void doThings () {
        ModBlockSets.registerModBlockSets();

        for (ModBlockSets.SimpleBlockSet set : ModBlockSets.sets) {
            SimpleBlockModel.blocks.add(set.bricks());
            SimpleSlabModel.blocks.add(new SimpleSlabModel.Slab(set.bricks(), set.slab()));
            SimpleStairModel.blocks.add(new SimpleStairModel.Stair(set.bricks(), set.stairs()));
            SimpleWallModel.blocks.add(new SimpleWallModel.Wall(set.bricks(), set.wall()));

            MineablePickaxe.blocks.add(set.bricks());
            MineablePickaxe.blocks.add(set.wall());
            MineablePickaxe.blocks.add(set.slab());
            MineablePickaxe.blocks.add(set.stairs());

            Walls.walls.add(set.wall());
        }

    }

}
