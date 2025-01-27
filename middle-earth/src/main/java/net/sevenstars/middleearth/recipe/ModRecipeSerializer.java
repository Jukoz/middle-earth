package net.sevenstars.middleearth.recipe;


import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.LoggerUtil;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipeSerializer<T extends Recipe<?>> {

    public static final SpecialRecipeSerializer<CustomArmorDyeRecipe> CUSTOM_ARMOR_DYE = register("custom_armor_dye", new SpecialRecipeSerializer<>(CustomArmorDyeRecipe::new));;
    public static final SpecialRecipeSerializer<ArmorCapeRecipe> CUSTOM_ARMOR_CAPE = register("custom_armor_cape", new SpecialRecipeSerializer<>(ArmorCapeRecipe::new));
    public static final SpecialRecipeSerializer<ArmorCapeRemovalRecipe> CUSTOM_ARMOR_CAPE_REMOVAL = register("custom_armor_cape_removal", new SpecialRecipeSerializer<>(ArmorCapeRemovalRecipe::new));
    public static final SpecialRecipeSerializer<ArmorHoodRecipe> CUSTOM_ARMOR_HOOD = register("custom_armor_hood", new SpecialRecipeSerializer<>(ArmorHoodRecipe::new));
    public static final SpecialRecipeSerializer<ArmorHoodRemovalRecipe> CUSTOM_ARMOR_HOOD_REMOVAL = register("custom_armor_hood_removal", new SpecialRecipeSerializer<>(ArmorHoodRemovalRecipe::new));
    public static final SpecialRecipeSerializer<MountArmorAddonRemovalRecipe> CUSTOM_MOUNT_ARMOR_ADDON_REMOVAL = register("custom_mount_armor_addon_removal", new SpecialRecipeSerializer<>(MountArmorAddonRemovalRecipe::new));
    public static final SpecialRecipeSerializer<MountArmorSideSkullAddonRecipe> CUSTOM_MOUNT_ARMOR_SIDE_SKULL_ADDON = register("custom_mount_armor_side_skull_addon", new SpecialRecipeSerializer<>(MountArmorSideSkullAddonRecipe::new));
    public static final SpecialRecipeSerializer<MountArmorTopSkullAddonRecipe> CUSTOM_MOUNT_ARMOR_TOP_SKULL_ADDON = register("custom_mount_armor_top_skull_addon", new SpecialRecipeSerializer<>(MountArmorTopSkullAddonRecipe::new));

    public static final SpecialRecipeSerializer<CustomItemDecorationRecipe> CUSTOM_ITEM_DECORATION = register("custom_item_decoration", new SpecialRecipeSerializer<>(CustomItemDecorationRecipe::new));

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of(MiddleEarth.MOD_ID, id), serializer);
    }

    public static void registerRecipeSerializers(){
        LoggerUtil.logDebugMsg("Registering Mod Recipe Serializers for " + MiddleEarth.MOD_ID);
    }
}
