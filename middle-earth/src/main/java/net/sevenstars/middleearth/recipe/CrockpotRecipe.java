package net.sevenstars.middleearth.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.block.special.forge.MultipleStackRecipeInput;

import java.util.List;

public class CrockpotRecipe implements Recipe<MultipleStackRecipeInput> {
    public final int ingredientsAmount;
    public final List<Ingredient> inputs;
    public final ItemStack output;

    public CrockpotRecipe(int ingredientsAmount, List<Ingredient> inputs, ItemStack output) {
        this.ingredientsAmount = ingredientsAmount;
        this.inputs = inputs;
        this.output = output;
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> defaultedList = NonNullList.create();
        defaultedList.addAll(this.inputs);
        return defaultedList;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean matches(MultipleStackRecipeInput input, Level world) {
        if(world.isClientSide()) return false;
        int i = 0;
        for (int j = 0; j < input.size(); j++) {
            ItemStack itemStack = input.getItem(j);
            if (itemStack.isEmpty()) continue;
            i++;
        }
        if(i != this.inputs.size()) return false;

        for (int j = 0; j < inputs.size(); j++) {
            if(!inputs.get(j).test(input.getItem(j))) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(MultipleStackRecipeInput input, HolderLookup.Provider lookup) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider lookup) {
        return this.output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<CrockpotRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "crockpot";
    }

    public static class Serializer implements RecipeSerializer<CrockpotRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "crockpot";
        private final MapCodec<CrockpotRecipe> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, CrockpotRecipe> packetCodec;

        protected Serializer() {
            this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    Codec.INT.fieldOf("ingredients_amount").forGetter(recipe -> recipe.ingredientsAmount),
                    Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputs),
                    ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output)
                    ).apply(instance, CrockpotRecipe::new));

            this.packetCodec = StreamCodec.of(Serializer::write, Serializer::read);
        }

        @Override
        public MapCodec<CrockpotRecipe> codec() {
            return this.codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CrockpotRecipe> streamCodec() {
            return this.packetCodec;
        }

        private static CrockpotRecipe read(RegistryFriendlyByteBuf buf) {
            int ingredientsAmount = ByteBufCodecs.INT.decode(buf);
            int ingredientCount = buf.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);
            ingredients.replaceAll(empty -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
            return new CrockpotRecipe(ingredientsAmount, ingredients, output);
        }

        private static void write(RegistryFriendlyByteBuf buf, CrockpotRecipe recipe) {
            ByteBufCodecs.INT.encode(buf, recipe.ingredientsAmount);
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }
            ItemStack.STREAM_CODEC.encode(buf, recipe.output);
        }
    }
}
