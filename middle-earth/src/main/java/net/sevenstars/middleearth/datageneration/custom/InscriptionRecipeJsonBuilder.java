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
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.recipe.inscription.InscriptionRecipe;
import net.sevenstars.middleearth.utils.IdentifierUtil;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class InscriptionRecipeJsonBuilder implements CraftingRecipeJsonBuilder {

    private final RecipeCategory category;
    public RegistryEntry<Enchantment> enchant;
    public int level;
    private List<String> inputWords;
    private Ingredient chiselInput;
    private int levelCost;
    private final Map<String, AdvancementCriterion<?>> criteria = new LinkedHashMap<>();
    private String group;

    private final RegistryEntryLookup<Item> registryLookup;

    public InscriptionRecipeJsonBuilder(RegistryEntryLookup<Item> registryLookup, RecipeCategory category, RegistryEntry<Enchantment> enchant, int level, Ingredient chiselInput, int levelCost) {
        this.registryLookup = registryLookup;
        this.category = category;
        this.enchant = enchant;
        this.level = level;
        this.chiselInput = chiselInput;
        this.levelCost = levelCost;

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
        InscriptionRecipe inscriptionRecipeBuilder = new InscriptionRecipe(this.enchant, this.level, this.inputWords, this.chiselInput, this.levelCost);
        exporter.accept(recipeKey, inscriptionRecipeBuilder, builder.build(IdentifierUtil.create("recipes/" + this.category.getName() + "/" + "inscription_" + this.enchant.getKey().get().getRegistry().getPath() + "_" + this.level)));
    }

    public static InscriptionRecipeJsonBuilder createInscriptionRecipe(RegistryEntryLookup<Item> registryLookup, RecipeCategory category, RegistryEntry<Enchantment> enchant, int level, Ingredient chisel, int levelCost) {
        return new InscriptionRecipeJsonBuilder(registryLookup, category, enchant, level, chisel, levelCost);
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
            throw new IllegalStateException("No way of obtaining recipe " + recipeKey);
        }
    }
}
