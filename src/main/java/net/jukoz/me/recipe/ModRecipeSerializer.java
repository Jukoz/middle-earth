package net.jukoz.me.recipe;


import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.logging.Logger;

public class ModRecipeSerializer {

    public static SpecialRecipeSerializer<CustomArmorDyeRecipe> CUSTOM_ARMOR_DYE;
    public static SpecialRecipeSerializer<ArmorCapeRecipe> CUSTOM_ARMOR_CAPE;
    public static SpecialRecipeSerializer<ArmorHoodRecipe> CUSTOM_ARMOR_HOOD;

    public static void registerRecipeSerializers(){
        CUSTOM_ARMOR_DYE = register("custom_armor_dye", new SpecialRecipeSerializer<CustomArmorDyeRecipe>(CustomArmorDyeRecipe::new));
        CUSTOM_ARMOR_CAPE = register("custom_armor_cape", new SpecialRecipeSerializer<ArmorCapeRecipe>(ArmorCapeRecipe::new));
        CUSTOM_ARMOR_HOOD = register("custom_armor_hood", new SpecialRecipeSerializer<ArmorHoodRecipe>(ArmorHoodRecipe::new));

        LoggerUtil.getInstance().logDebugMsg("Registering Mod Recipe Serializers for " + MiddleEarth.MOD_ID);
    }

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(MiddleEarth.MOD_ID, id), serializer);
    }
}
