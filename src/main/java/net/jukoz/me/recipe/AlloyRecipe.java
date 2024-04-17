package net.jukoz.me.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class AlloyRecipe implements Recipe<SimpleInventory> {
    public final Identifier id;
    public final ItemStack output;
    public final List<Ingredient> inputs;

    public AlloyRecipe(Identifier id, ItemStack output, List<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.inputs = recipeItems;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if(world.isClient()) return false;
        int i = 0;
        for (int j = 1; j < inventory.size() - 1; ++j) { // We avoid first and last indexes, because it's for fuel & output.
            ItemStack itemStack = inventory.getStack(j);
            if (itemStack.isEmpty()) continue;
            ++i;
        }
        if(i != this.inputs.size()) return false;

        for (int j = 0; j < inputs.size(); j++) {
            if(!inputs.get(j).test(inventory.getStack(j + 1))) return false;
        }
        return true;
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, RegistryWrapper.WrapperLookup lookup) {
        return output;
    }

    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.addAll(this.inputs);
        return defaultedList;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
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

    public static class Type implements RecipeType<AlloyRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "alloy_furnace";
    }

    public static class Serializer implements RecipeSerializer<AlloyRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "alloy_furnace";
        private final MapCodec<AlloyRecipe> codec;
        private final PacketCodec<RegistryByteBuf, AlloyRecipe> packetCodec;

        protected Serializer() {
            this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                            Identifier.CODEC.fieldOf("id").forGetter(recipe -> recipe.id),
                            ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
                            Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("inputs").forGetter(recipe -> recipe.inputs)
                    ).apply(instance, AlloyRecipe::new));

            this.packetCodec = PacketCodec.ofStatic(Serializer::write, Serializer::read);
        }

        @Override
        public MapCodec<AlloyRecipe> codec() {
            return this.codec;
        }

        @Override
        public PacketCodec<RegistryByteBuf, AlloyRecipe> packetCodec() {
            return this.packetCodec;
        }

        private static AlloyRecipe read(RegistryByteBuf buf) {
            Identifier id = buf.readIdentifier();
            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            int i = buf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll(empty -> Ingredient.PACKET_CODEC.decode(buf));
            return new AlloyRecipe(id, output, defaultedList);
        }

        private static void write(RegistryByteBuf buf, AlloyRecipe recipe) {
            buf.writeIdentifier(recipe.id);
            ItemStack.PACKET_CODEC.encode(buf, recipe.output);
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }
        }
    }
}
