package net.sevenstars.middleearth.compat.artisantable;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.compat.REICommonPluginME;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableInputsShape;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;

import java.util.ArrayList;
import java.util.List;

public final class ArtisanTableDisplay extends BasicDisplay {
    public static final DisplaySerializer<ArtisanTableDisplay> SERIALIZER =
            BasicDisplay.Serializer.ofRecipeLess(
                    (inputs, outputs, tag) ->
                            new ArtisanTableDisplay(inputs, outputs, tag.getString("category")),
                    (display, tag) -> tag.putString("category", display.category)
            );

    private final String category;

    public ArtisanTableDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, String category) {
        super(inputs, outputs);
        this.category = category;
    }

    public ArtisanTableDisplay(ArtisanRecipe recipe) {
        this(getInputs(recipe), List.of(EntryIngredients.of(recipe.getOutput())), recipe.category);
    }

    public static List<EntryIngredient> getInputs(ArtisanRecipe recipe) {
        List<Ingredient> ingredients = recipe.getIngredients();
        List<EntryIngredient> inputs = new ArrayList<>(EntryIngredients.ofIngredients(ingredients));

        for (int index = 0; index < ingredients.size() && index < inputs.size(); index++) {
            Ingredient ingredient = ingredients.get(index);
            if (!(ingredient.getCustomIngredient() instanceof DataComponentIngredient componentIngredient)) {
                continue;
            }

            int inputIndex = index;
            componentIngredient.getItems()
                    .filter(ArtisanTableDisplay::isSmithingPart)
                    .findFirst()
                    .map(ItemStack::copy)
                    .ifPresent(stack -> inputs.set(inputIndex, EntryIngredients.of(stack)));
        }

        return inputs;
    }

    private static boolean isSmithingPart(ItemStack stack) {
        ArmorTrim trim = stack.get(DataComponents.TRIM);
        return trim != null && trim.pattern().is(MiddleEarth.of("smithing_part"));
    }

    public String getCategory() {
        return this.category;
    }

    public ArtisanTableInputsShape getArtisanTableInputShape() {
        return ArtisanTableInputsShape.getShape(this.category);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return REICommonPluginME.ARTISAN_TABLE_CATEGORY;
    }
}
