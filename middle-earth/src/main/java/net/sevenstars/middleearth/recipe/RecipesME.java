package net.sevenstars.middleearth.recipe;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.recipe.inscription.InscriptionRecipe;

import java.util.function.Supplier;

public final class RecipesME {
    private RecipesME() {
    }

    public static RecipeType<ArtisanRecipe> ARTISAN_TABLE;
    public static RecipeType<InscriptionRecipe> INSCRIPTION_TABLE;
    public static RecipeType<CrockpotRecipe> CROCKPOT;
    public static RecipeType<AnvilShapingRecipe> ANVIL_SHAPING;
    public static RecipeType<AlloyingRecipe> FORGE;
    public static final Supplier<RecipeType<ArtisanRecipe>> ARTISAN_TABLE_SUPPLIER = () -> ARTISAN_TABLE;

    public static void registerRecipes() {
        registerSerializer(AlloyingRecipe.Serializer.ID, AlloyingRecipe.Serializer.INSTANCE);
        FORGE = registerType(AlloyingRecipe.Type.ID, AlloyingRecipe.Type.INSTANCE);

        registerSerializer(AnvilShapingRecipe.Serializer.ID, AnvilShapingRecipe.Serializer.INSTANCE);
        ANVIL_SHAPING = registerType(AnvilShapingRecipe.Type.ID, AnvilShapingRecipe.Type.INSTANCE);

        registerSerializer(ArtisanRecipe.Serializer.ID, ArtisanRecipe.Serializer.INSTANCE);
        ARTISAN_TABLE = registerType(ArtisanRecipe.Type.ID, ArtisanRecipe.Type.INSTANCE);

        registerSerializer(InscriptionRecipe.Serializer.ID, InscriptionRecipe.Serializer.INSTANCE);
        INSCRIPTION_TABLE = registerType(InscriptionRecipe.Type.ID, InscriptionRecipe.Type.INSTANCE);

        registerSerializer(CrockpotRecipe.Serializer.ID, CrockpotRecipe.Serializer.INSTANCE);
        CROCKPOT = registerType(CrockpotRecipe.Type.ID, CrockpotRecipe.Type.INSTANCE);
    }

    private static <T extends Recipe<?>> void registerSerializer(String id, RecipeSerializer<T> serializer) {
        RegistrationBridge.register(BuiltInRegistries.RECIPE_SERIALIZER, resourceId(id), serializer);
    }

    private static <T extends Recipe<?>> RecipeType<T> registerType(String id, RecipeType<T> type) {
        return RegistrationBridge.register(BuiltInRegistries.RECIPE_TYPE, resourceId(id), type);
    }

    private static ResourceLocation resourceId(String path) {
        return ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, path);
    }
}
