package net.sevenstars.middleearth.recipe;

import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static RecipeType<ArtisanRecipe> ARTISAN_TABLE;
    public static RecipeType<AnvilShapingRecipe> ANVIL_SHAPING;
    public static RecipeType<AlloyingRecipe> FORGE;

    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER,
                Identifier.of(MiddleEarth.MOD_ID, AlloyingRecipe.Serializer.ID),
                AlloyingRecipe.Serializer.INSTANCE);
        FORGE = Registry.register(Registries.RECIPE_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, AlloyingRecipe.Type.ID),
                AlloyingRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER,
                Identifier.of(MiddleEarth.MOD_ID, AnvilShapingRecipe.Serializer.ID),
                AnvilShapingRecipe.Serializer.INSTANCE);
        ANVIL_SHAPING = Registry.register(Registries.RECIPE_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, AnvilShapingRecipe.Type.ID),
                AnvilShapingRecipe.Type.INSTANCE);

        /*Registry.register(Registries.RECIPE_SERIALIZER,
                Identifier.of(MiddleEarth.MOD_ID, ArtisanRecipe.Serializer.ID),
                ArtisanRecipe.Serializer.INSTANCE);*/
        ARTISAN_TABLE = Registry.register(Registries.RECIPE_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, ArtisanRecipe.Type.ID),
                ArtisanRecipe.Type.INSTANCE);
    }
}
