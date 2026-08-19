package net.sevenstars.middleearth.datageneration.custom;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.recipe.inscription.InscriptionRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class InscriptionRecipeJsonBuilder implements RecipeBuilder {

    private final RecipeCategory category;
    public Holder<Enchantment> enchant;
    public int level;
    private List<String> inputWords;
    private Ingredient chiselInput;
    private int levelCost;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    private String group;

    private final HolderGetter<Item> registryLookup;

    public InscriptionRecipeJsonBuilder(HolderGetter<Item> registryLookup, RecipeCategory category, Holder<Enchantment> enchant, int level, int levelCost) {
        this.registryLookup = registryLookup;
        this.category = category;
        this.enchant = enchant;
        this.level = level;
        this.chiselInput = chiselInput;
        this.levelCost = levelCost;

        this.inputWords = new ArrayList<>();
    }

    public InscriptionRecipeJsonBuilder chisel(TagKey<Item> tag) {
        this.chiselInput = Ingredient.of(tag);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return ToolItemsME.MITHRIL_CHISEL;
    }

    @Override
    public void save(RecipeOutput exporter, ResourceLocation recipeId) {
        this.validate(recipeId);
        Advancement.Builder builder = exporter.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(builder);
        this.criteria.forEach(builder::addCriterion);
        InscriptionRecipe inscriptionRecipeBuilder = new InscriptionRecipe(this.enchant, this.level, this.inputWords, this.chiselInput, this.levelCost);
        exporter.accept(recipeId, inscriptionRecipeBuilder, builder.build(MiddleEarth.ofPath(
                "recipes", this.category.getFolderName(), recipeId.getPath()
        )));
    }

    public static InscriptionRecipeJsonBuilder createInscriptionRecipe(HolderGetter<Item> registryLookup, RecipeCategory category, Holder<Enchantment> enchant, int level, int levelCost) {
        return new InscriptionRecipeJsonBuilder(registryLookup, category, enchant, level, levelCost);
    }

    public InscriptionRecipeJsonBuilder addWord(String word) {
        this.inputWords.add(word);

        return this;
    }

    @Override
    public InscriptionRecipeJsonBuilder unlockedBy(String string, Criterion<?> advancementCriterion) {
        this.criteria.put(string, advancementCriterion);
        return this;
    }

    private void validate(ResourceLocation recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }
}
