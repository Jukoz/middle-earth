package net.sevenstars.middleearth.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.impl.recipe.ingredient.CustomIngredientImpl;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.special.forge.MultipleStackRecipeInput;

import java.util.List;

public class InscriptionRecipe implements Recipe<MultipleStackRecipeInput> {
    public final RegistryEntry<Enchantment> enchant;
    public final int level;
    public final List<Ingredient> inputs;

    public InscriptionRecipe(RegistryEntry<Enchantment> enchant, int level, List<Ingredient> recipeItems) {
        this.enchant = enchant;
        this.level = level;
        this.inputs = recipeItems;
    }

    public ItemStack createIcon() {
        return new ItemStack(ModDecorativeBlocks.INSCRIPTION_TABLE);
    }

    @Override
    public boolean matches(MultipleStackRecipeInput input, World world) {
        int i = 0;
        for (int j = 0; j < input.size(); j++) {
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
        }

        return true;
    }

    @Override
    public ItemStack craft(MultipleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack result = input.getStackInSlot(0);
        result.addEnchantment(this.enchant, this.level);
        return result;
    }

    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.addAll(this.inputs);
        return defaultedList;
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
        return null;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return null;
    }

    public static class Type implements RecipeType<InscriptionRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "inscription_table";
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<InscriptionRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "inscription_table";
        private final MapCodec<InscriptionRecipe> codec;
        private final PacketCodec<RegistryByteBuf, InscriptionRecipe> packetCodec;

        protected Serializer() {
            this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    Enchantment.ENTRY_CODEC.fieldOf("enchant").forGetter(recipe -> recipe.enchant),
                    Codec.INT.fieldOf("level").forGetter(recipe -> recipe.level),
                    Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.inputs)
            ).apply(instance, InscriptionRecipe::new));
            this.packetCodec = PacketCodec.ofStatic(Serializer::write, Serializer::read);
        }

        @Override
        public MapCodec<InscriptionRecipe> codec() {
            return this.codec;
        }

        @Override
        public PacketCodec<RegistryByteBuf, InscriptionRecipe> packetCodec() {
            return this.packetCodec;
        }

        private static InscriptionRecipe read(RegistryByteBuf buf) {
            RegistryEntry<Enchantment> enchantment = Enchantment.ENTRY_PACKET_CODEC.decode(buf);
            int level = PacketCodecs.INTEGER.decode(buf);
            int i = buf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i);
            defaultedList.replaceAll(empty -> CustomIngredientImpl.PACKET_CODEC.decode(buf));
            return new InscriptionRecipe(enchantment, level, defaultedList);
        }

        private static void write(RegistryByteBuf buf, InscriptionRecipe recipe) {
            Enchantment.ENTRY_PACKET_CODEC.encode(buf, recipe.enchant);
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                CustomIngredientImpl.PACKET_CODEC.encode(buf, ingredient);
            }
        }
    }
}
