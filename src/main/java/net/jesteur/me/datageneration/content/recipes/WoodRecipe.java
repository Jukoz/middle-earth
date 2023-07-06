package net.jesteur.me.datageneration.content.recipes;

import net.jesteur.me.block.ModBlocks;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class WoodRecipe {
    public record WoodRecipeRecord(Block wood, Block log) {

    }

    public static List<WoodRecipeRecord> recipes = new ArrayList<>() {
        {
            add(new WoodRecipeRecord(ModBlocks.MALLORN_WOOD, ModBlocks.MALLORN_LOG));
        }
    };

}
