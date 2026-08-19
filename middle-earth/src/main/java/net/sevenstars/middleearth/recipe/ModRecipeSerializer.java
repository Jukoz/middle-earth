package net.sevenstars.middleearth.recipe;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.middleearth.MiddleEarth;

public final class ModRecipeSerializer {
    private ModRecipeSerializer() {
    }

    public static final SimpleCraftingRecipeSerializer<BackAttachmentRecipe> CUSTOM_ARMOR_BACK_ATTACHMENT =
            register("custom_armor_back_attachment", new SimpleCraftingRecipeSerializer<>(BackAttachmentRecipe::new));
    public static final SimpleCraftingRecipeSerializer<BackAttachmentRemovalRecipe> CUSTOM_ARMOR_BACK_ATTACHMENT_REMOVAL =
            register("custom_armor_back_attachment_removal", new SimpleCraftingRecipeSerializer<>(BackAttachmentRemovalRecipe::new));
    public static final SimpleCraftingRecipeSerializer<HelmetAttachmentRecipe> CUSTOM_ARMOR_HELMET_ATTACHMENT =
            register("custom_armor_helmet_attachment", new SimpleCraftingRecipeSerializer<>(HelmetAttachmentRecipe::new));
    public static final SimpleCraftingRecipeSerializer<HelmetAttachmentRemovalRecipe> CUSTOM_ARMOR_HELMET_ATTACHMENT_REMOVAL =
            register("custom_armor_helmet_attachment_removal", new SimpleCraftingRecipeSerializer<>(HelmetAttachmentRemovalRecipe::new));
    public static final SimpleCraftingRecipeSerializer<MountArmorAddonRemovalRecipe> CUSTOM_MOUNT_ARMOR_ADDON_REMOVAL =
            register("custom_mount_armor_addon_removal", new SimpleCraftingRecipeSerializer<>(MountArmorAddonRemovalRecipe::new));
    public static final SimpleCraftingRecipeSerializer<MountArmorSideSkullAddonRecipe> CUSTOM_MOUNT_ARMOR_SIDE_SKULL_ADDON =
            register("custom_mount_armor_side_skull_addon", new SimpleCraftingRecipeSerializer<>(MountArmorSideSkullAddonRecipe::new));
    public static final SimpleCraftingRecipeSerializer<MountArmorTopSkullAddonRecipe> CUSTOM_MOUNT_ARMOR_TOP_SKULL_ADDON =
            register("custom_mount_armor_top_skull_addon", new SimpleCraftingRecipeSerializer<>(MountArmorTopSkullAddonRecipe::new));

    public static final SimpleCraftingRecipeSerializer<CustomItemDecorationRecipe> CUSTOM_ITEM_DECORATION =
            register("custom_item_decoration", new SimpleCraftingRecipeSerializer<>(CustomItemDecorationRecipe::new));

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return RegistrationBridge.register(
                BuiltInRegistries.RECIPE_SERIALIZER,
                ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, id),
                serializer
        );
    }

    public static void registerRecipeSerializers(){
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Recipe Serializers for " + MiddleEarth.MOD_ID);
    }
}
