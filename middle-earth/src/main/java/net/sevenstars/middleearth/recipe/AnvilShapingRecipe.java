package net.sevenstars.middleearth.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.item.DataComponentTypesME;

public class AnvilShapingRecipe implements Recipe<SingleRecipeInput> {
    protected final Ingredient input;
    protected final ItemStack output;
    protected final int amount;

    public AnvilShapingRecipe(Ingredient input, ItemStack output, int amount) {
        this.output = output;
        this.input = input;
        this.amount = amount;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModDecorativeBlocks.TREATED_ANVIL);
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level world) {
        if(input.item().isEmpty()) return false;

        if(input.item().get(DataComponentTypesME.TEMPERATURE_DATA) == null) return false;

        return this.input.test(input.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider lookup) {
        return this.output.copy();
    }

    public ItemStack getOutput() {
        return output;
    }

    public Ingredient getIngredient() {
        return this.input;
    }

    public int getAmount() {
        return this.amount;
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

    public static class Type implements RecipeType<AnvilShapingRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "anvil_shaping";
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<AnvilShapingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "anvil_shaping";
        private final MapCodec<AnvilShapingRecipe> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, AnvilShapingRecipe> packetCodec;

        protected Serializer() {
            this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.input),
                    ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
                    Codec.INT.fieldOf("amount").forGetter(recipe -> recipe.amount)
            ).apply(instance, AnvilShapingRecipe::new));

            this.packetCodec = StreamCodec.of(Serializer::write, Serializer::read);
        }

        @Override
        public MapCodec<AnvilShapingRecipe> codec() {
            return this.codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AnvilShapingRecipe> streamCodec() {
            return this.packetCodec;
        }

        private static AnvilShapingRecipe read(RegistryFriendlyByteBuf buf) {
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
            ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
            int amount = ByteBufCodecs.INT.decode(buf);
            return new AnvilShapingRecipe(input,output, amount);
        }

        private static void write(RegistryFriendlyByteBuf buf, AnvilShapingRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.input);
            ItemStack.STREAM_CODEC.encode(buf, recipe.output);
            ByteBufCodecs.INT.encode(buf, recipe.amount);
        }
    }
}
