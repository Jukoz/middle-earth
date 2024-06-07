package net.jukoz.me.recipe;

import net.jukoz.me.MiddleEarth;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface ModRecipeSerializer {
    RecipeSerializer<CustomArmorDyeRecipe> CUSTOM_ARMOR_DYE = register("custom_armor_dye", new SpecialRecipeSerializer(CustomArmorDyeRecipe::new));


    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return (S) Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(MiddleEarth.MOD_ID, id), serializer);
    }
}
