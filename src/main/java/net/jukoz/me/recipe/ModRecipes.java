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
                Identifier.of(MiddleEarth.MOD_ID, AlloyRecipe.Serializer.ID),
                AlloyRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, AlloyRecipe.Type.ID),
                AlloyRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER,
                Identifier.of(MiddleEarth.MOD_ID, ArtisanRecipe.Serializer.ID),
                ArtisanRecipe.Serializer.INSTANCE);
        ARTISAN_TABLE = Registry.register(Registries.RECIPE_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, ArtisanRecipe.Type.ID),
                ArtisanRecipe.Type.INSTANCE);
    }
}
