package net.sevenstars.middleearth.recipe.inscription;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;

import java.util.List;

public class InscriptionRecipe implements Recipe<SingleRecipeInput> {
    public final Holder<Enchantment> enchant;
    public final int level;
    public final List<String> inputWords;
    public final Ingredient inputChisel;
    public final int levelCost;

    public InscriptionRecipe(Holder<Enchantment> enchant, int level, List<String> inputWords, Ingredient inputChisel, int levelCost) {
        this.enchant = enchant;
        this.level = level;
        this.inputWords = inputWords;
        this.inputChisel = inputChisel;
        this.levelCost = levelCost;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModDecorativeBlocks.INSCRIPTION_TABLE);
    }

    @Override
    public boolean matches(SingleRecipeInput input, Level world) {
        return this.inputChisel.test(input.getItem(0));
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        ItemStack result = input.getItem(0).copy();
        ItemEnchantments enchants = result.getEnchantments();
        if(enchants.getLevel(this.enchant) == this.level - 1) {
            result.enchant(this.enchant, this.level);
        }
        return result;
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

    public static class Type implements RecipeType<InscriptionRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "inscription_table";
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<InscriptionRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "inscription_table";
        private final MapCodec<InscriptionRecipe> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, InscriptionRecipe> packetCodec;

        protected Serializer() {
            this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    Enchantment.CODEC.fieldOf("enchantment").forGetter(recipe -> recipe.enchant),
                    Codec.INT.fieldOf("level").forGetter(recipe -> recipe.level),
                    Codec.STRING.listOf().fieldOf("words").forGetter(recipe -> recipe.inputWords),
                    Ingredient.CODEC.fieldOf("chisel").forGetter(recipe -> recipe.inputChisel),
                    Codec.INT.fieldOf("level_cost").forGetter(recipe -> recipe.levelCost)
            ).apply(instance, InscriptionRecipe::new));
            this.packetCodec = StreamCodec.of(Serializer::write, Serializer::read);
        }

        @Override
        public MapCodec<InscriptionRecipe> codec() {
            return this.codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, InscriptionRecipe> streamCodec() {
            return this.packetCodec;
        }

        private static InscriptionRecipe read(RegistryFriendlyByteBuf buf) {
            Holder<Enchantment> enchantment = Enchantment.STREAM_CODEC.decode(buf);
            int level = ByteBufCodecs.INT.decode(buf);

            int i = buf.readVarInt();
            NonNullList<String> defaultedList = NonNullList.withSize(i, "");
            defaultedList.replaceAll(empty -> ByteBufCodecs.STRING_UTF8.decode(buf));

            Ingredient chisel = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);

            int levelCost = ByteBufCodecs.INT.decode(buf);

            return new InscriptionRecipe(enchantment, level, defaultedList, chisel, levelCost);
        }

        private static void write(RegistryFriendlyByteBuf buf, InscriptionRecipe recipe) {
            Enchantment.STREAM_CODEC.encode(buf, recipe.enchant);
            ByteBufCodecs.INT.encode(buf, recipe.level);
            buf.writeVarInt(recipe.inputWords.size());

            for (String string : recipe.inputWords) {
                ByteBufCodecs.STRING_UTF8.encode(buf, string);
            }

            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputChisel);
            ByteBufCodecs.INT.encode(buf, recipe.levelCost);
        }
    }
}
