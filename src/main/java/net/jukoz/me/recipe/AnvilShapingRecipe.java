package net.jukoz.me.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.item.ModDataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class AnvilShapingRecipe implements Recipe<SingleStackRecipeInput> {
    protected final Ingredient input;
    protected final ItemStack output;

    public AnvilShapingRecipe(Ingredient input, ItemStack output) {
        this.output = output;
        this.input = input;
    }

    public ItemStack createIcon() {
        return new ItemStack(ModDecorativeBlocks.TREADTED_ANVIL);
    }

    @Override
    public boolean matches(SingleStackRecipeInput input, World world) {
        if(input.getStackInSlot(0).isEmpty()) return false;

        if(input.getStackInSlot(0).get(ModDataComponentTypes.TEMPERATURE_DATA) == null) return false;
        return true;
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    public ItemStack getOutput() {
        return output;
    }

    public Ingredient getIngredient() {
        return this.input;
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
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<AnvilShapingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "anvil_shaping";
        private final MapCodec<AnvilShapingRecipe> codec;
        private final PacketCodec<RegistryByteBuf, AnvilShapingRecipe> packetCodec;

        protected Serializer() {
            this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.input),
                    ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output)
            ).apply(instance, AnvilShapingRecipe::new));

            this.packetCodec = PacketCodec.ofStatic(AnvilShapingRecipe.Serializer::write, AnvilShapingRecipe.Serializer::read);
        }

        @Override
        public MapCodec<AnvilShapingRecipe> codec() {
            return this.codec;
        }

        @Override
        public PacketCodec<RegistryByteBuf, AnvilShapingRecipe> packetCodec() {
            return this.packetCodec;
        }

        private static AnvilShapingRecipe read(RegistryByteBuf buf) {
            Ingredient input = Ingredient.PACKET_CODEC.decode(buf);
            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            return new AnvilShapingRecipe(input,output);
        }

        private static void write(RegistryByteBuf buf, AnvilShapingRecipe recipe) {
            Ingredient.PACKET_CODEC.encode(buf, recipe.input);
            ItemStack.PACKET_CODEC.encode(buf, recipe.output);
        }
    }
}
