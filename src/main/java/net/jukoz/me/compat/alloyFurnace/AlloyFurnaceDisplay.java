package net.jukoz.me.compat.alloyFurnace;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.jukoz.me.recipe.AlloyRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlloyFurnaceDisplay extends BasicDisplay {

    private double cookTime;

    public AlloyFurnaceDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public AlloyFurnaceDisplay(AlloyRecipe recipe){
        super(getInputs(recipe), Collections.singletonList(EntryIngredients.of(recipe.getOutput(BasicDisplay.registryAccess()))));
    }

    private static List<EntryIngredient> getInputs(AlloyRecipe recipe) {
        if (recipe == null) return Collections.emptyList();
        List<EntryIngredient> list = new ArrayList<>(4);
        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(0)));
        if(recipe.getIngredients().size() > 1){
            list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(1)));
        }
        if(recipe.getIngredients().size() > 2){
            list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(2)));
        }
        if(recipe.getIngredients().size() > 3){
            list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(3)));
        }
        return list;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return AlloyFurnaceCategory.ALLOY_FURNACE;
    }
}
