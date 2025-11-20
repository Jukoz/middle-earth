package net.sevenstars.middleearth.recipe.inscription;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.impl.recipe.ingredient.CustomIngredientImpl;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.special.forge.MultipleStackRecipeInput;

import java.util.List;

public class InscriptionRecipe implements Recipe<SingleStackRecipeInput> {
    public final RegistryEntry<Enchantment> enchant;
    public final int level;
    public final List<String> inputWords;
    public final Ingredient inputChisel;
    public final float expModifier;

    public InscriptionRecipe(RegistryEntry<Enchantment> enchant, int level, List<String> inputWords, Ingredient inputChisel, float expModifier) {
        this.enchant = enchant;
        this.level = level;
        this.inputWords = inputWords;
        this.inputChisel = inputChisel;
        this.expModifier = expModifier;
    }

    public ItemStack createIcon() {
        return new ItemStack(ModDecorativeBlocks.INSCRIPTION_TABLE);
    }

    @Override
    public boolean matches(SingleStackRecipeInput input, World world) {
        return this.inputChisel.test(input.getStackInSlot(0));
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        ItemStack result = input.getStackInSlot(0).copy();
        ItemEnchantmentsComponent enchants = result.getEnchantments();
        if(enchants.getLevel(this.enchant) == this.level - 1) {
            result.addEnchantment(this.enchant, this.level);
        }
        return result;
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleStackRecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<SingleStackRecipeInput>> getType() {
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
                    Enchantment.ENTRY_CODEC.fieldOf("enchantment").forGetter(recipe -> recipe.enchant),
                    Codec.INT.fieldOf("level").forGetter(recipe -> recipe.level),
                    Codec.STRING.listOf().fieldOf("words").forGetter(recipe -> recipe.inputWords),
                    Ingredient.CODEC.fieldOf("chisel").forGetter(recipe -> recipe.inputChisel),
                    Codec.FLOAT.fieldOf("exp_modifier").forGetter(recipe -> recipe.expModifier)
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
            DefaultedList<String> defaultedList = DefaultedList.ofSize(i);
            defaultedList.replaceAll(empty -> PacketCodecs.STRING.decode(buf));

            Ingredient chisel = Ingredient.PACKET_CODEC.decode(buf);

            float expModifier = PacketCodecs.FLOAT.decode(buf);

            return new InscriptionRecipe(enchantment, level, defaultedList, chisel, expModifier);
        }

        private static void write(RegistryByteBuf buf, InscriptionRecipe recipe) {
            Enchantment.ENTRY_PACKET_CODEC.encode(buf, recipe.enchant);
            buf.writeVarInt(buf.readVarInt());

            for (String string : recipe.inputWords) {
                PacketCodecs.STRING.encode(buf, string);
            }

            Ingredient.PACKET_CODEC.encode(buf, recipe.inputChisel);
            PacketCodecs.FLOAT.encode(buf, recipe.expModifier);
        }
    }
}