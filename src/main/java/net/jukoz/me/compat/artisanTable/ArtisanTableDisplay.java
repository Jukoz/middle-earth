package net.jukoz.me.compat.artisanTable;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.jukoz.me.recipe.ArtisanRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArtisanTableDisplay extends BasicDisplay {
    public ArtisanTableDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public ArtisanTableDisplay(ArtisanRecipe recipe){
        super(getInputs(recipe), Collections.singletonList(EntryIngredients.of(recipe.getOutput(BasicDisplay.registryAccess()))));
    }

    private static List<EntryIngredient> getInputs(ArtisanRecipe recipe) {
        if (recipe == null) return Collections.emptyList();
        List<EntryIngredient> list = new ArrayList<>(6);
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
        if(recipe.getIngredients().size() > 4){
            list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(4)));
        }
        if(recipe.getIngredients().size() > 5){
            list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(5)));
        }
        return list;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ArtisanTableCategory.ARTISAN_TABLE;
    }
}
