package net.sevenstars.middleearth.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.forge.MultipleStackRecipeInput;

import java.util.List;

public class CrockpotRecipe implements Recipe<MultipleStackRecipeInput> {
    public final int ingredientsAmount;
    public final List<Ingredient> inputs;
    public final ItemStack output;

    private IngredientPlacement ingredientPlacement;

    public CrockpotRecipe(int ingredientsAmount, List<Ingredient> inputs, ItemStack output) {
        this.ingredientsAmount = ingredientsAmount;
        this.inputs = inputs;
        this.output = output;
    }

    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.addAll(this.inputs);
        return defaultedList;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public boolean matches(MultipleStackRecipeInput input, World world) {
        if(world.isClient()) return false;
        int i = 0;
        for (int j = 0; j < input.size(); j++) {
            ItemStack itemStack = input.getStackInSlot(j);
            if (itemStack.isEmpty()) continue;
            i++;
        }
        if(i != this.inputs.size()) return false;

        for (int j = 0; j < inputs.size(); j++) {
            if(!inputs.get(j).test(input.getStackInSlot(j))) return false;
        }
        return true;
    }

    @Override
    public ItemStack craft(MultipleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return this.output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<MultipleStackRecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<MultipleStackRecipeInput>> getType() {
        return Type.INSTANCE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = IngredientPlacement.forShapeless(this.inputs);
        }

        return this.ingredientPlacement;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return RecipeBookCategories.FURNACE_MISC;
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
        private final PacketCodec<RegistryByteBuf, CrockpotRecipe> packetCodec;

        protected Serializer() {
            this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    Codec.INT.fieldOf("ingredients_amount").forGetter(recipe -> recipe.ingredientsAmount),
                    Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputs),
                    ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output)
                    ).apply(instance, CrockpotRecipe::new));

            this.packetCodec = PacketCodec.ofStatic(Serializer::write, Serializer::read);
        }

        @Override
        public MapCodec<CrockpotRecipe> codec() {
            return this.codec;
        }

        @Override
        public PacketCodec<RegistryByteBuf, CrockpotRecipe> packetCodec() {
            return this.packetCodec;
        }

        private static CrockpotRecipe read(RegistryByteBuf buf) {
            int ingredientsAmount = buf.readVarInt();
            DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(ingredientsAmount);
            ingredients.replaceAll(empty -> Ingredient.PACKET_CODEC.decode(buf));
            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            return new CrockpotRecipe(ingredientsAmount, ingredients, output);
        }

        private static void write(RegistryByteBuf buf, CrockpotRecipe recipe) {
            PacketCodecs.INTEGER.encode(buf, recipe.ingredientsAmount);
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }
            ItemStack.PACKET_CODEC.encode(buf, recipe.output);
        }
    }
}
