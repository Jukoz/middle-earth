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
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.block.special.forge.MultipleStackRecipeInput;

import java.util.List;

public class AlloyingRecipe implements Recipe<MultipleStackRecipeInput> {
    public final String output;
    public final int amount;
    public final List<Ingredient> inputs;
    private final CraftingBookCategory category;
    private final String group;
    private final int xp;

    public AlloyingRecipe(String group, CraftingBookCategory category, String output, List<Ingredient> recipeItems, int amount, int xp) {
        this.output = output;
        this.group = group;
        this.inputs = recipeItems;
        this.amount = amount;
        this.category = category;
        this.xp = xp;
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> defaultedList = NonNullList.create();
        defaultedList.addAll(this.inputs);
        return defaultedList;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public CraftingBookCategory getCategory() {
        return this.category;
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
        return ItemStack.EMPTY;
    }

    public String craftAlloy(MultipleStackRecipeInput input, HolderLookup.Provider lookup) {
        return output;
    }

    public String getAlloyResult() {
        return output;
    }

    public int getAmount() {
        return amount;
    }

    public int getXp() {
        return xp;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider lookup) {
        return ItemStack.EMPTY;
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
        private final StreamCodec<RegistryFriendlyByteBuf, AlloyingRecipe> packetCodec;

        protected Serializer() {
            this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                    CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(recipe -> recipe.category),
                    Codec.STRING.fieldOf("output").forGetter(recipe -> recipe.output),
                    Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputs),
                    Codec.INT.fieldOf("amount").forGetter(recipe -> recipe.amount),
                    Codec.INT.fieldOf("xp").forGetter(recipe -> recipe.xp)
                    ).apply(instance, AlloyingRecipe::new));

            this.packetCodec = StreamCodec.of(Serializer::write, Serializer::read);
        }

        @Override
        public MapCodec<AlloyingRecipe> codec() {
            return this.codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AlloyingRecipe> streamCodec() {
            return this.packetCodec;
        }

        private static AlloyingRecipe read(RegistryFriendlyByteBuf buf) {
            String string = buf.readUtf();
            CraftingBookCategory craftingRecipeCategory = (CraftingBookCategory)buf.readEnum(CraftingBookCategory.class);
            String output = ByteBufCodecs.STRING_UTF8.decode(buf);
            int amount = ByteBufCodecs.INT.decode(buf);
            int i = buf.readVarInt();
            NonNullList<Ingredient> defaultedList = NonNullList.withSize(i, Ingredient.EMPTY);
            defaultedList.replaceAll(empty -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            int xp = buf.readVarInt();
            return new AlloyingRecipe(string, craftingRecipeCategory, output, defaultedList, amount, xp);
        }

        private static void write(RegistryFriendlyByteBuf buf, AlloyingRecipe recipe) {
            buf.writeUtf(recipe.group);
            buf.writeEnum(recipe.category);
            ByteBufCodecs.STRING_UTF8.encode(buf, recipe.output);
            ByteBufCodecs.INT.encode(buf, recipe.amount);
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }
            buf.writeVarInt(recipe.xp);
        }
    }
}
