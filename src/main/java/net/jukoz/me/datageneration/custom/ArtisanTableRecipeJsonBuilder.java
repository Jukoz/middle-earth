package net.jukoz.me.datageneration.custom;

import net.fabricmc.fabric.impl.recipe.ingredient.CustomIngredientImpl;
import net.jukoz.me.recipe.ArtisanRecipe;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ArtisanTableRecipeJsonBuilder implements CraftingRecipeJsonBuilder {

    private final RecipeCategory category;
    private final String tab;
    private final DefaultedList<Ingredient> inputs = DefaultedList.of();
    private final ItemStack output;
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();
    private String group;

    public ArtisanTableRecipeJsonBuilder(RecipeCategory category, ItemStack output, String tab) {
        this.category = category;
        this.output = output;
        this.tab = tab;
    }

    @Override
    public CraftingRecipeJsonBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getOutputItem() {
        return this.output.getItem();
    }

    public static ArtisanTableRecipeJsonBuilder createAlloyRecipe(RecipeCategory category, ItemStack output, String tab) {
        return new ArtisanTableRecipeJsonBuilder(category, output, tab);
    }

    public ArtisanTableRecipeJsonBuilder input(TagKey<Item> tag) {
        return this.input(Ingredient.fromTag(tag));
    }

    public ArtisanTableRecipeJsonBuilder input(ItemConvertible itemProvider) {
        return this.input((ItemConvertible)itemProvider, 1);
    }

    public ArtisanTableRecipeJsonBuilder componentInput(ItemStack stack) {
            return this.input(CustomIngredientImpl.ofStacks(stack), 1);
    }

    public ArtisanTableRecipeJsonBuilder input(ItemConvertible itemProvider, int size) {
        for(int i = 0; i < size; ++i) {
            this.input(Ingredient.ofItems(new ItemConvertible[]{itemProvider}));
        }
        return this;
    }

    public ArtisanTableRecipeJsonBuilder input(Ingredient ingredient) {
        return this.input((Ingredient)ingredient, 1);
    }

    public ArtisanTableRecipeJsonBuilder input(Ingredient ingredient, int size) {
        for(int i = 0; i < size; ++i) {
            this.inputs.add(ingredient);
        }
        return this;
    }

    @Override
    public void offerTo(RecipeExporter exporter, Identifier recipeId) {
        this.validate(recipeId);
        Advancement.Builder builder = exporter.getAdvancementBuilder().criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        Objects.requireNonNull(builder);
        this.criteria.forEach(builder::criterion);
        ArtisanRecipe artisanRecipe = new ArtisanRecipe(this.tab, this.output, this.inputs);
        exporter.accept(recipeId, artisanRecipe, builder.build(recipeId.withPrefixedPath("recipes/" + this.category.getName() + "/")));
    }

    @Override
    public ArtisanTableRecipeJsonBuilder criterion(String string, AdvancementCriterion<?> advancementCriterion) {
        this.criteria.put(string, advancementCriterion);
        return this;
    }

    private void validate(Identifier recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + String.valueOf(recipeId));
        }
    }
}
