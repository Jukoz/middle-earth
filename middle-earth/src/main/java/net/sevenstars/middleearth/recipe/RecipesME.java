package net.sevenstars.middleearth.recipe;

import net.minecraft.recipe.Recipe;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.sevenstars.middleearth.recipe.inscription.InscriptionRecipe;

public class RecipesME {
    public static RecipeType<ArtisanRecipe> ARTISAN_TABLE;
    public static RecipeType<InscriptionRecipe> INSCRIPTION_TABLE;
    public static RecipeType<CrockpotRecipe> CROCKPOT; // = register("crockpot");
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

        Registry.register(Registries.RECIPE_SERIALIZER,
                MiddleEarth.of(ArtisanRecipe.Serializer.ID),
                ArtisanRecipe.Serializer.INSTANCE);
        ARTISAN_TABLE = Registry.register(Registries.RECIPE_TYPE,
                MiddleEarth.of(ArtisanRecipe.Type.ID),
                ArtisanRecipe.Type.INSTANCE);

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
