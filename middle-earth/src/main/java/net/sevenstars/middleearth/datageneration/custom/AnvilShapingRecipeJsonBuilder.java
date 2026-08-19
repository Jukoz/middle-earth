package net.sevenstars.middleearth.datageneration.custom;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.sevenstars.middleearth.recipe.AnvilShapingRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class AnvilShapingRecipeJsonBuilder implements RecipeBuilder {

    private final RecipeCategory category;
    private Ingredient input;
    private final Item output;
    private final int amount;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private String group;

    private final HolderGetter<Item> registryLookup;

    public AnvilShapingRecipeJsonBuilder(HolderGetter<Item> registryLookup, RecipeCategory category, Item output, int amount ) {
        this.registryLookup = registryLookup;
        this.category = category;
        this.output = output;
        this.amount = amount;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return this.output;
    }

    @Override
    public void save(RecipeOutput exporter, ResourceLocation recipeId) {
        this.validate(recipeId);
        Advancement.Builder builder = exporter.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(builder);
        this.criteria.forEach(builder::addCriterion);
        AnvilShapingRecipe anvilShapingRecipe = new AnvilShapingRecipe(this.input, new ItemStack(this.output), this.amount);
        exporter.accept(recipeId, anvilShapingRecipe, builder.build(recipeId.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    public static AnvilShapingRecipeJsonBuilder createAnvilShapingRecipe(HolderGetter<Item> registryLookup, RecipeCategory category, Item output, int amount) {
        return new AnvilShapingRecipeJsonBuilder(registryLookup, category, output, amount);
    }

    public AnvilShapingRecipeJsonBuilder input(TagKey<Item> tag) {
        return this.input(Ingredient.of(tag));
    }

    public AnvilShapingRecipeJsonBuilder input(ItemLike itemProvider) {
        return this.input((ItemLike)itemProvider, 1);
    }

    public AnvilShapingRecipeJsonBuilder input(ItemLike itemProvider, int size) {
        for(int i = 0; i < size; ++i) {
            this.input(Ingredient.of(new ItemLike[]{itemProvider}));
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
    public AnvilShapingRecipeJsonBuilder unlockedBy(String string, Criterion<?> advancementCriterion) {
        this.criteria.put(string, advancementCriterion);
        return this;
    }

    private void validate(ResourceLocation recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + String.valueOf(recipeId));
        }
    }
}
