package net.sevenstars.middleearth.recipe;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.sevenstars.middleearth.recipe.inscription.InscriptionRecipe;

import java.util.function.Supplier;

public class RecipesME {
    public static final Supplier<RecipeType<ArtisanRecipe>> ARTISAN_TABLE_SUPPLIER = regRecipe("artisan_table", () ->
            registerRecipeType(ArtisanRecipe.Type.ID, ArtisanRecipe.Type.INSTANCE), ArtisanRecipe.Type.ID, ArtisanRecipe.Serializer.INSTANCE);

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier, RecipeType<T> recipeType) {
        return Registry.register(Registries.RECIPE_TYPE,
                MiddleEarth.of(identifier),
                recipeType);
    }

    public static <T extends Recipe<?>, B extends RecipeType<?>> Supplier<B> regRecipe(String name, Supplier<B> supplier, String serializerId, RecipeSerializer<T> recipeSerializer) {
        return registerSupplier(name, supplier, serializerId, recipeSerializer);
    }

    public static <R, T extends R, B extends Recipe<?>> Supplier<T> registerSupplier(String name, Supplier<T> supplier, String serializerId, RecipeSerializer<B> recipeSerializer) {
        T object = supplier.get();
        Registry.register(Registries.RECIPE_SERIALIZER,
            MiddleEarth.of(serializerId),
            recipeSerializer);
        return () -> object;
    }

    public static RecipeType<InscriptionRecipe> INSCRIPTION_TABLE;
    public static RecipeType<CrockpotRecipe> CROCKPOT;
    public static RecipeType<AnvilShapingRecipe> ANVIL_SHAPING;
    public static RecipeType<AlloyingRecipe> FORGE;

    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER,
                MiddleEarth.of(AlloyingRecipe.Serializer.ID),
                AlloyingRecipe.Serializer.INSTANCE);
        FORGE = Registry.register(Registries.RECIPE_TYPE,
                MiddleEarth.of(AlloyingRecipe.Type.ID),
                AlloyingRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER,
                MiddleEarth.of(AnvilShapingRecipe.Serializer.ID),
                AnvilShapingRecipe.Serializer.INSTANCE);
        ANVIL_SHAPING = Registry.register(Registries.RECIPE_TYPE,
                MiddleEarth.of(AnvilShapingRecipe.Type.ID),
                AnvilShapingRecipe.Type.INSTANCE);

        //Registry.register(Registries.RECIPE_SERIALIZER,
        //        MiddleEarth.of(ArtisanRecipe.Serializer.ID),
        //        ArtisanRecipe.Serializer.INSTANCE);
        //ARTISAN_TABLE = Registry.register(Registries.RECIPE_TYPE,
        //        MiddleEarth.of(ArtisanRecipe.Type.ID),
        //        ArtisanRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER,
                MiddleEarth.of(InscriptionRecipe.Serializer.ID),
                InscriptionRecipe.Serializer.INSTANCE);
        INSCRIPTION_TABLE = Registry.register(Registries.RECIPE_TYPE,
                MiddleEarth.of(InscriptionRecipe.Type.ID),
                InscriptionRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER,
                MiddleEarth.of(CrockpotRecipe.Serializer.ID),
                CrockpotRecipe.Serializer.INSTANCE);
        CROCKPOT = Registry.register(Registries.RECIPE_TYPE,
                MiddleEarth.of(CrockpotRecipe.Type.ID),
                CrockpotRecipe.Type.INSTANCE);
    }

    static <T extends Recipe<?>> RecipeType<T> register(final String id) {
        return Registry.register(Registries.RECIPE_TYPE, MiddleEarth.of(id), new RecipeType<T>() {
            public String toString() {
                return id;
            }
        });
    }
}
