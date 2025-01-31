package net.sevenstars.middleearth.datageneration.custom;

import net.minecraft.recipe.Recipe;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.recipe.AnvilShapingRecipe;
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
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class AnvilShapingRecipeJsonBuilder implements CraftingRecipeJsonBuilder {

    private final RecipeCategory category;
    private Ingredient input;
    private final Item output;
    private final int amount;
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();
    private String group;

    public AnvilShapingRecipeJsonBuilder(RecipeCategory category, Item output, int amount ) {
        this.category = category;
        this.output = output;
        this.amount = amount;
    }

    @Override
    public CraftingRecipeJsonBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getOutputItem() {
        return this.output;
    }

    @Override
    public void offerTo(RecipeExporter exporter, RegistryKey<Recipe<?>> recipeKey) {
        this.validate(recipeKey);
        Advancement.Builder builder = exporter.getAdvancementBuilder().criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeKey)).rewards(AdvancementRewards.Builder.recipe(recipeId)).criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        Objects.requireNonNull(builder);
        this.criteria.forEach(builder::criterion);
        AnvilShapingRecipe anvilShapingRecipe = new AnvilShapingRecipe(this.input, new ItemStack(this.output), this.amount);
        exporter.accept(recipeKey, anvilShapingRecipe, builder.build(recipeKey.withPrefixedPath("recipes/" + this.category.getName() + "/")));
    }

    public static AnvilShapingRecipeJsonBuilder createAnvilShapingRecipe(RecipeCategory category, Item output, int amount) {
        return new AnvilShapingRecipeJsonBuilder(category, output, amount);
    }

    public AnvilShapingRecipeJsonBuilder input(TagKey<Item> tag) {
        return this.input(Ingredient.fromTag(tag));
    }

    public AnvilShapingRecipeJsonBuilder input(ItemConvertible itemProvider) {
        return this.input((ItemConvertible)itemProvider, 1);
    }

    public AnvilShapingRecipeJsonBuilder input(ItemConvertible itemProvider, int size) {
        for(int i = 0; i < size; ++i) {
            this.input(Ingredient.ofItems(new ItemConvertible[]{itemProvider}));
        }
        return this;
    }

    public AnvilShapingRecipeJsonBuilder input(Ingredient ingredient) {
        return this.input((Ingredient) ingredient, 1);
    }

    public AnvilShapingRecipeJsonBuilder input(Ingredient ingredient, int size) {
            this.input = ingredient;

        return this;
    }

    @Override
    public AnvilShapingRecipeJsonBuilder criterion(String string, AdvancementCriterion<?> advancementCriterion) {
        this.criteria.put(string, advancementCriterion);
        return this;
    }

    private void validate(Identifier recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + String.valueOf(recipeId));
        }
    }
}
