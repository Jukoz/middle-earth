package net.sevenstars.middleearth.compat.forge;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.compat.REICommonPluginME;
import net.sevenstars.middleearth.recipe.AlloyingRecipe;

import java.util.ArrayList;
import java.util.List;

public final class AlloyingDisplay extends BasicDisplay {
    public static final DisplaySerializer<AlloyingDisplay> SERIALIZER =
            BasicDisplay.Serializer.ofRecipeLess(
                    (inputs, outputs, tag) ->
                            new AlloyingDisplay(inputs, tag.getString("output"), tag.getInt("amount")),
                    (display, tag) -> {
                        tag.putString("output", display.output);
                        tag.putInt("amount", display.amount);
                    }
            );

    final String output;
    final int amount;

    public AlloyingDisplay(List<EntryIngredient> inputs, String output, int amount) {
        super(inputs, List.of(EntryIngredients.of(new ItemStack(MetalTypes.fromValue(output).getIngot()))));
        this.output = output;
        this.amount = amount;
    }

    public AlloyingDisplay(AlloyingRecipe recipe) {
        this(getInputs(recipe), recipe.output, recipe.amount);
    }

    public static List<EntryIngredient> getInputs(AlloyingRecipe recipe) {
        return new ArrayList<>(EntryIngredients.ofIngredients(recipe.getIngredients()));
    }

    public String getOutput() {
        return this.output;
    }

    public int getAmount() {
        return this.amount;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return REICommonPluginME.FORGE_CATEGORY;
    }
}
