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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ArtisanTableRecipeJsonBuilder implements RecipeBuilder {

    private final RecipeCategory category;
    private final String tab;
    private final NonNullList<Ingredient> inputs = NonNullList.create();
    private final ItemStack output;
    private final DispositionType dispositionType;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private final int xp;
    private String group;

    private final HolderGetter<Item> registryLookup;

    public ArtisanTableRecipeJsonBuilder(HolderGetter<Item> registryLookup, RecipeCategory category, ItemStack output,
                                         String tab, DispositionType dispositionType, int xp) {
        this.registryLookup = registryLookup;
        this.category = category;
        this.output = output;
        this.tab = tab;
        this.dispositionType = dispositionType;
        this.xp = xp;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return this.output.getItem();
    }

    @Override
    public void save(RecipeOutput exporter, ResourceLocation recipeId) {
        this.validate(recipeId);
        Advancement.Builder builder = exporter.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(builder);
        this.criteria.forEach(builder::addCriterion);
        ArtisanRecipe artisanRecipe = new ArtisanRecipe(this.tab, this.output, this.inputs, this.dispositionType.toString().toLowerCase(), this.xp);
        exporter.accept(recipeId, artisanRecipe, builder.build(recipeId.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    public static ArtisanTableRecipeJsonBuilder createArtisanRecipe(HolderGetter<Item> registryLookup, RecipeCategory category,
                                                                    ItemStack output, String tab, DispositionType dispositionType, int xp) {
        return new ArtisanTableRecipeJsonBuilder(registryLookup, category, output, tab, dispositionType, xp);
    }

    public static ArtisanTableRecipeJsonBuilder createArtisanRecipe(HolderGetter<Item> registryLookup, RecipeCategory category,
                                                                    ItemStack output, String tab, DispositionType dispositionType) {
        return new ArtisanTableRecipeJsonBuilder(registryLookup, category, output, tab, dispositionType, 0);
    }

    public static ArtisanTableRecipeJsonBuilder createArtisanRecipe(HolderGetter<Item> registryLookup, RecipeCategory category, ItemStack output, String tab) {
        return new ArtisanTableRecipeJsonBuilder(registryLookup, category, output, tab, null, 0);
    }

    public ArtisanTableRecipeJsonBuilder input(TagKey<Item> tag) {
        return this.input(Ingredient.of(tag));
    }

    public ArtisanTableRecipeJsonBuilder input(ItemLike itemProvider) {
        return this.input(itemProvider, 1);
    }

    public ArtisanTableRecipeJsonBuilder componentInput(Ingredient ingredient) {
        return this.input(ingredient, 1);
    }

    public ArtisanTableRecipeJsonBuilder input(ItemLike itemProvider, int size) {
        for(int i = 0; i < size; ++i) {
            this.input(Ingredient.of(itemProvider));
        }
        return this;
    }

    public ArtisanTableRecipeJsonBuilder input(Ingredient ingredient) {
        return this.input(ingredient, 1);
    }

    public ArtisanTableRecipeJsonBuilder input(Ingredient ingredient, int size) {
        for(int i = 0; i < size; ++i) {
            this.inputs.add(ingredient);
        }
        return this;
    }

    @Override
    public ArtisanTableRecipeJsonBuilder unlockedBy(String string, Criterion<?> advancementCriterion) {
        this.criteria.put(string, advancementCriterion);
        return this;
    }

    private void validate(ResourceLocation recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + String.valueOf(recipeId));
        }
    }
}
