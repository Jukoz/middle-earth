package net.sevenstars.middleearth.recipe.inscription;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.special.forge.MultipleStackRecipeInput;

public class InscriptionRecipe implements Recipe<MultipleStackRecipeInput> {
    public final RegistryEntry<Enchantment> enchant;
    public final int level;
    public final Ingredient inputCatalyst;
    public final Ingredient inputChisel;

    public InscriptionRecipe(RegistryEntry<Enchantment> enchant, int level, Ingredient inputCatalyst, Ingredient inputChisel) {
        this.enchant = enchant;
        this.level = level;
        this.inputCatalyst = inputCatalyst;
        this.inputChisel = inputChisel;
    }

    public ItemStack createIcon() {
        return new ItemStack(ModDecorativeBlocks.INSCRIPTION_TABLE);
    }

    @Override
    public boolean matches(MultipleStackRecipeInput input, World world) {
        /*int i = 0;
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

        ItemEnchantmentsComponent enchants = input.getStackInSlot(0).getEnchantments();
        if(enchants.getLevel(this.enchant) != this.level - 1) {
            return false;
        }*/

        return true;
    }

    @Override
    public ItemStack craft(MultipleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        ItemStack result = input.getStackInSlot(0).copy();
        ItemEnchantmentsComponent enchants = result.getEnchantments();
        if(enchants.getLevel(this.enchant) == this.level - 1) {
            result.addEnchantment(this.enchant, this.level);
        }
        return result;
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
                    Ingredient.CODEC.fieldOf("ingredientCatalyst").forGetter(recipe -> recipe.inputCatalyst),
                    Ingredient.CODEC.fieldOf("ingredientChisel").forGetter(recipe -> recipe.inputChisel)
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
            return new InscriptionRecipe(enchantment, level, Ingredient.ofItem(Items.ACACIA_LOG), Ingredient.ofItem(Items.ACACIA_LOG));
        }

        private static void write(RegistryByteBuf buf, InscriptionRecipe recipe) {
            Enchantment.ENTRY_PACKET_CODEC.encode(buf, recipe.enchant);
            buf.writeVarInt(buf.readVarInt());
            /*buf.writeVarInt(buf.read());
            for (Ingredient ingredient : recipe.inputs) {
                CustomIngredientImpl.PACKET_CODEC.encode(buf, ingredient);
            }*/
        }
    }
}
