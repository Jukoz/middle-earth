package net.sevenstars.middleearth.datageneration.providers.recipes;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.recipe.lapidary.LapidaryKnowledge;
import net.sevenstars.middleearth.recipe.lapidary.LapidaryRecipeLayouts;
import net.sevenstars.middleearth.recipe.lapidary.LapidaryTools;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.ImpossibleCriterion;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

/**
 * The recipes of the lost lapidary arts: everything on the way to the Masterwork and Relic Lapidary Tools. Their ids
 * are under lapidary/masterwork/ or lapidary/relic/ (see {@link LapidaryKnowledge}): they need
 * the scroll and never unlock by themselves (an impossible criterion), only by reading the scroll.
 * <p>
 * Made from what Middle-earth and vanilla already have: gem glass is the only new material. Metal rods and plates
 * are ME's generic rod and armor plate; the metal is their trim, so the ingredients match the trim component.
 */
public class LapidaryRecipeProvider extends FabricRecipeProvider {
    public LapidaryRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public String getName() {
        return "LapidaryRecipes";
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            final RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);
            final AdvancementCriterion<?> never = Criteria.IMPOSSIBLE.create(new ImpossibleCriterion.Conditions());

            @Override
            public void generate() {
                for (LapidaryTools tools : LapidaryTools.values()) {
                    for (LapidaryRecipeLayouts.Layout layout : LapidaryRecipeLayouts.all(tools)) {
                        ShapedRecipeJsonBuilder builder = ShapedRecipeJsonBuilder.create(itemLookup, RecipeCategory.MISC,
                            layout.output(), layout.count()).group("lapidary_" + layout.art());
                        layout.pattern().forEach(builder::pattern);
                        layout.key().forEach((symbol, part) -> builder.input(symbol, ingredient(part)));
                        builder.criterion("never", never).offerTo(exporter, id(layout.art(), idOf(layout.output())));
                    }
                }
            }

            private Ingredient ingredient(LapidaryRecipeLayouts.Part part) {
                return switch (part) {
                    case LapidaryRecipeLayouts.OfItem of -> Ingredient.ofItem(of.item());
                    case LapidaryRecipeLayouts.OfTag of -> ingredientFromTag(of.tag());
                    case LapidaryRecipeLayouts.Metal of -> metal(of.shape(), of.metal());
                };
            }

            private String id(String art, String name) {
                return Identifier.of(MiddleEarth.MOD_ID, LapidaryKnowledge.RECIPE_FOLDER + art + "/" + name).toString();
            }

            /** A generic rod or plate of one metal (the metal is its trim material). */
            private Ingredient metal(Item shape, String metalName) {
                ArmorTrim trim = LapidaryRecipeLayouts.trim(registries, metalName);
                return DefaultCustomIngredients.components(Ingredient.ofItem(shape),
                    ComponentChanges.builder().add(DataComponentTypes.TRIM, trim).build());
            }

            private String idOf(Item item) {
                return net.minecraft.registry.Registries.ITEM.getId(item).getPath();
            }
        };
    }
}
