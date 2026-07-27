package net.sevenstars.middleearth.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.special.forge.MultipleStackRecipeInput;
import java.util.List;

public class ArtisanRecipe implements Recipe<MultipleStackRecipeInput> {
    public final String category;
    public final ItemStack output;
    public final String disposition;
    public final List<Ingredient> inputs;
    public final int xp;

    public ArtisanRecipe(String category, ItemStack output, List<Ingredient> recipeItems, String disposition, int xp) {
        this.category = category;
        this.output = output;
        this.inputs = recipeItems;
        this.disposition = disposition;
        this.xp = xp;
    }

    public ArtisanRecipe(String category, ItemStack output, List<Ingredient> recipeItems) {
        this.category = category;
        this.output = output;
        this.inputs = recipeItems;
        this.disposition = "neutral";
        this.xp = 0;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModDecorativeBlocks.ARTISAN_TABLE);
    }

    @Override
    public boolean matches(MultipleStackRecipeInput input, Level world) {
        int i = 0;
        for (int j = 0; j < input.size(); j++) {
            ItemStack itemStack = input.getItem(j);
            if (itemStack.isEmpty()) continue;
            i++;
        }

        if(i != this.inputs.size()) return false;

        for (int j = 0; j < inputs.size(); j++) {
            Ingredient ingredient = this.inputs.get(j);
            if (!ingredient.test(input.getItem(j))) {
                return false;
            }

            /*if (ingredient.getMatchingStacks().length == 1){
                for (ItemStack itemStack2 : ingredient.getMatchingStacks()) {
                    if (!Objects.equals(itemStack2.get(DataComponentTypes.TRIM), input.getStackInSlot(j).get(DataComponentTypes.TRIM))) return false;
                }
            }*/
        }

        return true;
    }

    @Override
    public ItemStack assemble(MultipleStackRecipeInput input, HolderLookup.Provider lookup) {
        return this.output.copy();
    }

    public ItemStack getOutput() {
        return output;
    }

    public int getXp() {
        return xp;
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> defaultedList = NonNullList.create();
        defaultedList.addAll(this.inputs);
        return defaultedList;
    }

    public String getDisposition() {
        return disposition;
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

    public static class Type implements RecipeType<ArtisanRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "artisan_table";
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<ArtisanRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "artisan_table";
        private final MapCodec<ArtisanRecipe> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, ArtisanRecipe> packetCodec;

        protected Serializer() {
            this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    Codec.STRING.fieldOf("category").forGetter(recipe -> recipe.category),
                    ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output),
                    Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputs),
                    Codec.STRING.fieldOf("disposition").forGetter(recipe -> recipe.disposition),
                    Codec.INT.fieldOf("xp").forGetter(recipe -> recipe.xp)
            ).apply(instance, ArtisanRecipe::new));

            this.packetCodec = StreamCodec.of(Serializer::write, Serializer::read);
        }

        @Override
        public MapCodec<ArtisanRecipe> codec() {
            return this.codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ArtisanRecipe> streamCodec() {
            return this.packetCodec;
        }

        private static ArtisanRecipe read(RegistryFriendlyByteBuf buf) {
            String category = buf.readUtf();
            ItemStack output = ItemStack.STREAM_CODEC.decode(buf);
            int i = buf.readVarInt();
            NonNullList<Ingredient> defaultedList = NonNullList.withSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll(empty -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            String disposition = buf.readUtf();
            int xp = buf.readVarInt();
            return new ArtisanRecipe(category, output, defaultedList, disposition, xp);
        }

        private static void write(RegistryFriendlyByteBuf buf, ArtisanRecipe recipe) {
            buf.writeUtf(recipe.category);
            ItemStack.STREAM_CODEC.encode(buf, recipe.output);
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }
            buf.writeUtf(recipe.disposition);
            buf.writeVarInt(recipe.xp);
        }
    }
}
