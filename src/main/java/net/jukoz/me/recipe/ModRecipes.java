package net.jukoz.me.recipe;

import net.jukoz.me.MiddleEarth;
import net.minecraft.recipe.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static RecipeType<ArtisanRecipe> ARTISAN_TABLE;

    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER,
                Identifier.of(MiddleEarth.MOD_ID, AlloyingRecipe.Serializer.ID),
                AlloyingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, AlloyingRecipe.Type.ID),
                AlloyingRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER,
                Identifier.of(MiddleEarth.MOD_ID, AnvilShapingRecipe.Serializer.ID),
                AnvilShapingRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, AnvilShapingRecipe.Type.ID),
                AnvilShapingRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER,
                Identifier.of(MiddleEarth.MOD_ID, ArtisanRecipe.Serializer.ID),
                ArtisanRecipe.Serializer.INSTANCE);
        ARTISAN_TABLE = Registry.register(Registries.RECIPE_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, ArtisanRecipe.Type.ID),
                ArtisanRecipe.Type.INSTANCE);
    }
}
