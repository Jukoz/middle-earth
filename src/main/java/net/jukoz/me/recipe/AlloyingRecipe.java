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
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class AlloyingRecipe implements Recipe<MultipleStackRecipeInput> {
    public final String output;
    public final int amount;
    public final List<Ingredient> inputs;
    final CraftingRecipeCategory category;
    final String group;

    public AlloyingRecipe(String group, CraftingRecipeCategory category, String output, List<Ingredient> recipeItems, int amount) {
        this.output = output;
        this.group = group;
        this.inputs = recipeItems;
        this.amount = amount;
        this.category = category;
    }

    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.addAll(this.inputs);
        return defaultedList;
    }

    public String getGroup() {
        return this.group;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    public CraftingRecipeCategory getCategory() {
        return this.category;
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

    public String craftAlloy(MultipleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
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

    public String getAlloyResult(RegistryWrapper.WrapperLookup registriesLookup) {
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

    public static class Type implements RecipeType<AlloyingRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "alloying";
    }

    public static class Serializer implements RecipeSerializer<AlloyingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "alloying";
        private final MapCodec<AlloyingRecipe> codec;
        private final PacketCodec<RegistryByteBuf, AlloyingRecipe> packetCodec;

        protected Serializer() {
            this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                    CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter(recipe -> recipe.category),
                    Codec.STRING.fieldOf("output").forGetter(recipe -> recipe.output),
                    Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputs),
                    Codec.INT.fieldOf("amount").forGetter(recipe -> recipe.amount)
                    ).apply(instance, AlloyingRecipe::new));

            this.packetCodec = PacketCodec.ofStatic(Serializer::write, Serializer::read);
        }

        @Override
        public MapCodec<AlloyingRecipe> codec() {
            return this.codec;
        }

        @Override
        public PacketCodec<RegistryByteBuf, AlloyingRecipe> packetCodec() {
            return this.packetCodec;
        }

        private static AlloyingRecipe read(RegistryByteBuf buf) {
            String string = buf.readString();
            CraftingRecipeCategory craftingRecipeCategory = (CraftingRecipeCategory)buf.readEnumConstant(CraftingRecipeCategory.class);
            String output = PacketCodecs.STRING.decode(buf);
            int amount = PacketCodecs.INTEGER.decode(buf);
            int i = buf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll(empty -> Ingredient.PACKET_CODEC.decode(buf));
            return new AlloyingRecipe(string, craftingRecipeCategory, output, defaultedList, amount);
        }

        private static void write(RegistryByteBuf buf, AlloyingRecipe recipe) {
            buf.writeString(recipe.group);
            buf.writeEnumConstant(recipe.category);
            PacketCodecs.STRING.encode(buf, recipe.output);
            PacketCodecs.INTEGER.encode(buf, recipe.amount);
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }
        }
    }
}
