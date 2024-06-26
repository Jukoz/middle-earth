package net.jukoz.me.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class ArtisanRecipe implements Recipe<Inventory> {
    public final ItemStack output;
    public final List<Ingredient> inputs;

    public ArtisanRecipe(ItemStack output, List<Ingredient> recipeItems) {
        this.output = output;
        this.inputs = recipeItems;
    }

    public ItemStack createIcon() {
        return new ItemStack(ModDecorativeBlocks.ARTISAN_TABLE);
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        int i = 0;
        for (int j = 0; j < inventory.size(); ++j) {
            ItemStack itemStack = inventory.getStack(j);
            if (itemStack.isEmpty()) continue;
            ++i;
        }
        if(i != this.inputs.size()) return false;

        for (int j = 0; j < inputs.size(); j++) {
            if(!inputs.get(j).test(inventory.getStack(j))) return false;
        }
        return true;
    }

    @Override
    public ItemStack craft(Inventory inventory, RegistryWrapper.WrapperLookup lookup) {
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

    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.addAll(this.inputs);
        return defaultedList;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ArtisanRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "artisan_table";
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<ArtisanRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "artisan_table";
        private final MapCodec<ArtisanRecipe> codec;
        private final PacketCodec<RegistryByteBuf, ArtisanRecipe> packetCodec;

        protected Serializer() {
            this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
                    Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputs)
            ).apply(instance, ArtisanRecipe::new));

            this.packetCodec = PacketCodec.ofStatic(ArtisanRecipe.Serializer::write, ArtisanRecipe.Serializer::read);
        }

        @Override
        public MapCodec<ArtisanRecipe> codec() {
            return this.codec;
        }

        @Override
        public PacketCodec<RegistryByteBuf, ArtisanRecipe> packetCodec() {
            return this.packetCodec;
        }

        private static ArtisanRecipe read(RegistryByteBuf buf) {
            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            int i = buf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll(empty -> Ingredient.PACKET_CODEC.decode(buf));
            return new ArtisanRecipe(output, defaultedList);
        }

        private static void write(RegistryByteBuf buf, ArtisanRecipe recipe) {
            ItemStack.PACKET_CODEC.encode(buf, recipe.output);
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }
        }
    }
}
