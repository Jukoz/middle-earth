package net.sevenstars.middleearth.features.cooking;

import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.cooking.recipe.CrockpotRecipe;

public class CookingRecipes {
    public static RecipeType<CrockpotRecipe> CROCKPOT;

    public static void register() {
        Registry.register(Registries.RECIPE_SERIALIZER,
                MiddleEarth.id(CrockpotRecipe.Serializer.ID),
                CrockpotRecipe.Serializer.INSTANCE);
        CROCKPOT = Registry.register(Registries.RECIPE_TYPE,
                MiddleEarth.id(CrockpotRecipe.Type.ID),
                CrockpotRecipe.Type.INSTANCE);
    }
}
