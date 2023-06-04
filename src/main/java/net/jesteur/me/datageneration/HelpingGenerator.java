package net.jesteur.me.datageneration;

import net.jesteur.me.block.ModBlockSets;
import net.jesteur.me.datageneration.content.loot_tables.BlockDrops;
import net.jesteur.me.datageneration.content.models.SimpleBlockModel;
import net.jesteur.me.datageneration.content.models.SimpleSlabModel;
import net.jesteur.me.datageneration.content.models.SimpleStairModel;
import net.jesteur.me.datageneration.content.models.SimpleWallModel;
import net.jesteur.me.datageneration.content.tags.MineablePickaxe;
import net.jesteur.me.datageneration.content.tags.Walls;

public class HelpingGenerator {

    public static void generateFiles() {
        ModBlockSets.registerModBlockSets();

        for (ModBlockSets.SimpleBlockSet set : ModBlockSets.sets) {
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
    }
}
