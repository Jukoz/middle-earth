package net.jukoz.me.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.impl.recipe.ingredient.CustomIngredientImpl;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.block.special.forge.MultipleStackRecipeInput;
import net.jukoz.me.item.ModDataComponentTypes;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class ArtisanRecipe implements Recipe<MultipleStackRecipeInput> {
    public final String category;
    public final ItemStack output;
    public final String disposition;
    public final List<Ingredient> inputs;

    public ArtisanRecipe(String category, ItemStack output, List<Ingredient> recipeItems, String disposition) {
        this.category = category;
        this.output = output;
        this.inputs = recipeItems;
        this.disposition = disposition;
    }

    public ArtisanRecipe(String category, ItemStack output, List<Ingredient> recipeItems) {
        this.category = category;
        this.output = output;
        this.inputs = recipeItems;
        this.disposition = null;
    }

    public ItemStack createIcon() {
        return new ItemStack(ModDecorativeBlocks.ARTISAN_TABLE);
    }

    @Override
    public boolean matches(MultipleStackRecipeInput input, World world) {
        int i = 0;
        for (int j = 0; j < input.getSize(); j++) {
            ItemStack itemStack = input.getStackInSlot(j);
            if (itemStack.isEmpty()) continue;
            i++;
        }

        if(i != this.inputs.size()) return false;

        for (int j = 0; j < inputs.size(); j++) {
            Ingredient ingredient = this.inputs.get(j);
            if (!ingredient.test(input.getStackInSlot(j))) {
                return false;
            }

            if (ingredient.getMatchingStacks().length == 1){
                for (ItemStack itemStack2 : ingredient.getMatchingStacks()) {
                    ItemStack inputCopy = input.getStackInSlot(j).copy();
                    inputCopy.remove(DataComponentTypes.PROFILE);
                    inputCopy.remove(DataComponentTypes.MAX_DAMAGE);
                    inputCopy.remove(DataComponentTypes.INSTRUMENT);
                    inputCopy.remove(ModDataComponentTypes.DYE_DATA);

                    ItemStack inputCopy2 = itemStack2.copy();
                    inputCopy2.remove(DataComponentTypes.MAX_DAMAGE);

                    if (!ItemStack.areItemsAndComponentsEqual(inputCopy2, inputCopy)) return false;
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack craft(MultipleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
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

    public String getDisposition() {
        return disposition;
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
                    Codec.STRING.fieldOf("category").forGetter(recipe -> recipe.category),
                    ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
                    CustomIngredientImpl.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputs),
                    Codec.STRING.fieldOf("disposition").forGetter(recipe -> recipe.disposition)
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
            String category = buf.readString();
            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            int i = buf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll(empty -> CustomIngredientImpl.PACKET_CODEC.decode(buf));
            String disposition = buf.readString();
            return new ArtisanRecipe(category, output, defaultedList, disposition);
        }

        private static void write(RegistryByteBuf buf, ArtisanRecipe recipe) {
            buf.writeString(recipe.category);
            ItemStack.PACKET_CODEC.encode(buf, recipe.output);
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                CustomIngredientImpl.PACKET_CODEC.encode(buf, ingredient);
            }
            buf.writeString(recipe.disposition);
        }
    }
}
