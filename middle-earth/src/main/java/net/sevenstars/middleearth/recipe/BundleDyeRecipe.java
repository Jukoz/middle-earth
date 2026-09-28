package net.sevenstars.middleearth.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public final class BundleDyeRecipe extends ShapelessRecipe {
    public static final RecipeSerializer<BundleDyeRecipe> SERIALIZER = new RecipeSerializer<>() {
        private final MapCodec<BundleDyeRecipe> codec = RecipeSerializer.SHAPELESS_RECIPE.codec()
                .xmap(BundleDyeRecipe::new, recipe -> recipe);
        private final StreamCodec<RegistryFriendlyByteBuf, BundleDyeRecipe> streamCodec =
                RecipeSerializer.SHAPELESS_RECIPE.streamCodec().map(BundleDyeRecipe::new, recipe -> recipe);

        @Override
        public MapCodec<BundleDyeRecipe> codec() {
            return codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BundleDyeRecipe> streamCodec() {
            return streamCodec;
        }
    };

    public BundleDyeRecipe(ShapelessRecipe recipe) {
        super(recipe.getGroup(), recipe.category(), recipe.getResultItem(null), recipe.getIngredients());
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        for (ItemStack ingredient : input.items()) {
            if (ingredient.getItem() instanceof BundleItem) {
                return ingredient.transmuteCopy(getResultItem(registries).getItem(), 1);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
