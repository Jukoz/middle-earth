package net.jesteur.me.gui.alloy;

import net.jesteur.me.item.ModRessourceItems;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlloyRecipes {
    private static List<AlloyRecipe> alloyRecipes;

    public void generateRecipes() {
        alloyRecipes = Arrays.asList(
          new AlloyRecipe(Arrays.asList(Items.IRON_INGOT, Items.COAL), ModRessourceItems.DWARVEN_STEEL, 1),
          new AlloyRecipe(Arrays.asList(Items.RAW_IRON, Items.COAL), ModRessourceItems.DWARVEN_STEEL, 1),
          new AlloyRecipe(Arrays.asList(Items.IRON_INGOT, Items.COAL), ModRessourceItems.DWARVEN_STEEL, 1),
          new AlloyRecipe(Arrays.asList(Items.RAW_IRON, Items.COAL), ModRessourceItems.DWARVEN_STEEL, 1)
        );

    }
}
