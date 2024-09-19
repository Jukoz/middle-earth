package net.jukoz.me.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.block.special.forge.MultipleStackRecipeInput;
import net.jukoz.me.item.ModResourceItems;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class HeatingRecipe implements Recipe<MultipleStackRecipeInput> {
    public final String output;
    public final List<Ingredient> inputs;

    public HeatingRecipe(String output, List<Ingredient> recipeItems) {
        this.output = output;
        this.inputs = recipeItems;
    }

    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.addAll(this.inputs);
        return defaultedList;
    }

    @Override
    public boolean matches(MultipleStackRecipeInput input, World world) {
        if(world.isClient()) return false;
        int i = 0;
        for (int j = 0; j < input.getSize(); j++) {
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
        return null;
    }

    public String createHeatMetal(MultipleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return new ItemStack(ModResourceItems.ROD);
    }

    public String getHeatingResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<HeatingRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "heating";
    }

    public static class Serializer implements RecipeSerializer<HeatingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "heating";
        private final MapCodec<HeatingRecipe> codec;
        private final PacketCodec<RegistryByteBuf, HeatingRecipe> packetCodec;

        protected Serializer() {
            this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    Codec.STRING.fieldOf("output").forGetter(recipe -> recipe.output),
                            Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputs)
                    ).apply(instance, HeatingRecipe::new));

            this.packetCodec = PacketCodec.ofStatic(Serializer::write, Serializer::read);
        }

        @Override
        public MapCodec<HeatingRecipe> codec() {
            return this.codec;
        }

        @Override
        public PacketCodec<RegistryByteBuf, HeatingRecipe> packetCodec() {
            return this.packetCodec;
        }

        private static HeatingRecipe read(RegistryByteBuf buf) {
            String output = PacketCodecs.STRING.decode(buf);
            int i = buf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll(empty -> Ingredient.PACKET_CODEC.decode(buf));
            return new HeatingRecipe(output, defaultedList);
        }

        private static void write(RegistryByteBuf buf, HeatingRecipe recipe) {
            PacketCodecs.STRING.encode(buf, recipe.output);
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }
        }
    }
}
