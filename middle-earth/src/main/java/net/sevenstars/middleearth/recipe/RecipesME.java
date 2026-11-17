package net.sevenstars.middleearth.recipe;

import net.sevenstars.middleearth.features.cooking.recipe.CrockpotRecipe;


import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.sevenstars.middleearth.features.inscription.recipe.InscriptionRecipe;

import java.util.function.Supplier;

public class RecipesME {
    public static final Supplier<RecipeType<ArtisanRecipe>> ARTISAN_TABLE_SUPPLIER = regRecipe("artisan_table", () ->
            registerRecipeType(ArtisanRecipe.Type.ID, ArtisanRecipe.Type.INSTANCE), ArtisanRecipe.Type.ID, ArtisanRecipe.Serializer.INSTANCE);

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier, RecipeType<T> recipeType) {
        return Registry.register(Registries.RECIPE_TYPE,
                MiddleEarth.id(identifier),
                recipeType);
    }

    public static <T extends Recipe<?>, B extends RecipeType<?>> Supplier<B> regRecipe(String name, Supplier<B> supplier, String serializerId, RecipeSerializer<T> recipeSerializer) {
        return registerSupplier(name, supplier, serializerId, recipeSerializer);
    }

    public static <R, T extends R, B extends Recipe<?>> Supplier<T> registerSupplier(String name, Supplier<T> supplier, String serializerId, RecipeSerializer<B> recipeSerializer) {
        T object = supplier.get();
        Registry.register(Registries.RECIPE_SERIALIZER,
            MiddleEarth.id(serializerId),
            recipeSerializer);
        return () -> object;
    }


    public static void registerRecipes() {
        //Registry.register(Registries.RECIPE_SERIALIZER,
        //        MiddleEarth.of(ArtisanRecipe.Serializer.ID),
        //        ArtisanRecipe.Serializer.INSTANCE);
        //ARTISAN_TABLE = Registry.register(Registries.RECIPE_TYPE,
        //        MiddleEarth.of(ArtisanRecipe.Type.ID),
        //        ArtisanRecipe.Type.INSTANCE);

    }

    static <T extends Recipe<?>> RecipeType<T> register(final String id) {
        return Registry.register(Registries.RECIPE_TYPE, MiddleEarth.id(id), new RecipeType<T>() {
            public String toString() {
                return id;
            }
        });
    }
}
