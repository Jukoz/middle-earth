package net.sevenstars.middleearth.features.smithing;

import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.smithing.recipe.AlloyingRecipe;
import net.sevenstars.middleearth.features.smithing.recipe.AnvilShapingRecipe;

public class SmithingRecipes {
    public static RecipeType<AnvilShapingRecipe> ANVIL_SHAPING;
    public static RecipeType<AlloyingRecipe> FORGE;

    public static void register() {
        Registry.register(Registries.RECIPE_SERIALIZER,
                MiddleEarth.id(AlloyingRecipe.Serializer.ID),
                AlloyingRecipe.Serializer.INSTANCE);
        FORGE = Registry.register(Registries.RECIPE_TYPE,
                MiddleEarth.id(AlloyingRecipe.Type.ID),
                AlloyingRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER,
                MiddleEarth.id(AnvilShapingRecipe.Serializer.ID),
                AnvilShapingRecipe.Serializer.INSTANCE);
        ANVIL_SHAPING = Registry.register(Registries.RECIPE_TYPE,
                MiddleEarth.id(AnvilShapingRecipe.Type.ID),
                AnvilShapingRecipe.Type.INSTANCE);
    }
}
