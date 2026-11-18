package net.sevenstars.middleearth.features.inscription;

import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.inscription.recipe.InscriptionRecipe;

public class InscriptionRecipes {
    public static RecipeType<InscriptionRecipe> INSCRIPTION_TABLE;

    public static void register() {
        Registry.register(Registries.RECIPE_SERIALIZER,
                MiddleEarth.id(InscriptionRecipe.Serializer.ID),
                InscriptionRecipe.Serializer.INSTANCE);
        INSCRIPTION_TABLE = Registry.register(Registries.RECIPE_TYPE,
                MiddleEarth.id(InscriptionRecipe.Type.ID),
                InscriptionRecipe.Type.INSTANCE);
    }
}
