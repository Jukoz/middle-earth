package net.sevenstars.middleearth.datageneration.custom;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.recipe.AlloyingRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class AlloyRecipeJsonBuilder implements RecipeBuilder {

    private final RecipeCategory category;
    private final NonNullList<Ingredient> inputs = NonNullList.create();
    private final String metalOutput;
    private final int metalAmount;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private String group;
    private final int xp;

    private final HolderGetter<Item> registryLookup;

    public AlloyRecipeJsonBuilder(HolderGetter<Item> registryLookup, RecipeCategory category, String metalOutput, int metalAmount, int xp) {
        this.registryLookup = registryLookup;
        this.category = category;
        this.metalOutput = metalOutput;
        this.metalAmount = metalAmount;
        this.xp = xp;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return ResourceItemsME.ROD;
    }

    @Override
    public void save(RecipeOutput exporter, ResourceLocation recipeId) {
        this.validate(recipeId);
        Advancement.Builder builder = exporter.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(builder);
        this.criteria.forEach(builder::addCriterion);
        AlloyingRecipe alloyRecipeJsonBuilder = new AlloyingRecipe((String)Objects.requireNonNullElse(this.group, ""),
                RecipeBuilder.determineBookCategory(this.category), this.metalOutput, this.inputs, this.metalAmount, this.xp);
        exporter.accept(recipeId, alloyRecipeJsonBuilder, builder.build(recipeId.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    public String getOutputMetal() {
        return this.metalOutput;
    }

    public int getMetalAmount() {
        return this.metalAmount;
    }

    public static AlloyRecipeJsonBuilder createAlloyRecipe(HolderGetter<Item> registryLookup, RecipeCategory category, String output, int amount, int xp) {
        return new AlloyRecipeJsonBuilder(registryLookup, category, output, amount, xp);
    }

    public AlloyRecipeJsonBuilder input(TagKey<Item> tag) {
        return this.input(Ingredient.of(tag));
    }

    public AlloyRecipeJsonBuilder input(ItemLike itemProvider) {
        return this.input((ItemLike)itemProvider, 1);
    }

    public AlloyRecipeJsonBuilder input(ItemLike itemProvider, int size) {
        for(int i = 0; i < size; ++i) {
            this.input(Ingredient.of(new ItemLike[]{itemProvider}));
        }
        return this;
    }

    public AlloyRecipeJsonBuilder input(Ingredient ingredient) {
        return this.input((Ingredient)ingredient, 1);
    }

    public AlloyRecipeJsonBuilder input(Ingredient ingredient, int size) {
        for(int i = 0; i < size; ++i) {
            this.inputs.add(ingredient);
        }
        return this;
    }

    @Override
    public AlloyRecipeJsonBuilder unlockedBy(String string, Criterion<?> advancementCriterion) {
        this.criteria.put(string, advancementCriterion);
        return this;
    }

    private void validate(ResourceLocation recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + String.valueOf(recipeId));
        }
    }
}
