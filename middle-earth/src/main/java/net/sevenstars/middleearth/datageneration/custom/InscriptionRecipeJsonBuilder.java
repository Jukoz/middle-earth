package net.sevenstars.middleearth.datageneration.custom;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.collection.DefaultedList;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.recipe.AlloyingRecipe;
import net.sevenstars.middleearth.recipe.inscription.InscriptionRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class InscriptionRecipeJsonBuilder implements CraftingRecipeJsonBuilder {

    private final RecipeCategory category;
    public RegistryEntry<Enchantment> enchant;
    public int level;
    private List<String> inputWords;
    private Ingredient chiselInput;
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();
    private String group;

    private final RegistryEntryLookup<Item> registryLookup;

    public InscriptionRecipeJsonBuilder(RegistryEntryLookup<Item> registryLookup, RecipeCategory category, RegistryEntry<Enchantment> enchant, int level, Ingredient chiselInput) {
        this.registryLookup = registryLookup;
        this.category = category;
        this.enchant = enchant;
        this.level = level;
        this.chiselInput = chiselInput;

        this.inputWords = new ArrayList<>();
    }

    @Override
    public CraftingRecipeJsonBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getOutputItem() {
        return ToolItemsME.MITHRIL_CHISEL;
    }

    @Override
    public void offerTo(RecipeExporter exporter, RegistryKey<Recipe<?>> recipeKey) {
        this.validate(recipeKey);
        Advancement.Builder builder = exporter.getAdvancementBuilder().criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeKey)).rewards(AdvancementRewards.Builder.recipe(recipeKey)).criteriaMerger(AdvancementRequirements.CriterionMerger.OR);
        Objects.requireNonNull(builder);
        this.criteria.forEach(builder::criterion);
        InscriptionRecipe inscriptionRecipeBuilder = new InscriptionRecipe(this.enchant, this.level, this.inputWords, this.chiselInput);
        exporter.accept(recipeKey, inscriptionRecipeBuilder, builder.build(recipeKey.getValue().withPrefixedPath("recipes/" + this.category.getName() + "/")));
    }

    public static InscriptionRecipeJsonBuilder createInscriptionRecipe(RegistryEntryLookup<Item> registryLookup, RecipeCategory category, RegistryEntry<Enchantment> enchant, int level, Ingredient chisel) {
        return new InscriptionRecipeJsonBuilder(registryLookup, category, enchant, level, chisel);
    }

    public InscriptionRecipeJsonBuilder addWord(String word) {
        this.inputWords.add(word);

        return this;
    }

    @Override
    public InscriptionRecipeJsonBuilder criterion(String string, AdvancementCriterion<?> advancementCriterion) {
        this.criteria.put(string, advancementCriterion);
        return this;
    }

    private void validate(RegistryKey<Recipe<?>> recipeKey) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + String.valueOf(recipeKey));
        }
    }
}
