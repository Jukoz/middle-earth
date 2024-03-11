package net.jukoz.me.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ArtisanRecipe implements Recipe<Inventory> {
    public final Identifier id;
    public final ItemStack output;
    public final DefaultedList<Ingredient> inputs;

    public ArtisanRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItems) {
        this.id = id;
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
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
        System.out.println("Crafted");
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output;
    }

    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.addAll(this.inputs);
        return defaultedList;
    }

    @Override
    public Identifier getId() {
        return id;
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

        @Override
        public ArtisanRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));
            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(ingredients.size(), Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(ingredients.get(i), true);
                if (ingredient.isEmpty()) continue;
                inputs.set(i, ingredient);
            }

            return new ArtisanRecipe(id, output, inputs);
        }

        @Override
        public ArtisanRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }

            ItemStack output = buf.readItemStack();
            return new ArtisanRecipe(id, output, inputs);
        }

        @Override
        public void write(PacketByteBuf buf, ArtisanRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buf);
            }
            buf.writeItemStack(recipe.getOutput(null));
        }
    }
}
