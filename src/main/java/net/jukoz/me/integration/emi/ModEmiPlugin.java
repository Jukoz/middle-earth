package net.jukoz.me.integration.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModDecorativeBlocks;
import net.jukoz.me.recipe.AlloyingRecipe;
import net.jukoz.me.recipe.AnvilShapingRecipe;
import net.jukoz.me.recipe.ArtisanRecipe;
import net.jukoz.me.recipe.ModRecipes;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.util.Identifier;

public class ModEmiPlugin implements EmiPlugin {
    public static final Identifier MY_SPRITE_SHEET = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/e.png");
    public static final EmiStack ARTISAN_TABLE = EmiStack.of(ModDecorativeBlocks.ARTISAN_TABLE);
    public static final EmiRecipeCategory ARTISAN_TABLE_CATEGORY
            = new EmiRecipeCategory(Identifier.of(MiddleEarth.MOD_ID, "artisan_table"), ARTISAN_TABLE, new EmiTexture(MY_SPRITE_SHEET, 0, 0, 16, 16));

    public static final EmiStack ANVIL_SHAPING = EmiStack.of(ModDecorativeBlocks.TREADTED_ANVIL);
    public static final EmiRecipeCategory ANVIL_SHAPING_CATEGORY
            = new EmiRecipeCategory(Identifier.of(MiddleEarth.MOD_ID, "anvil_shaping"), ANVIL_SHAPING, new EmiTexture(MY_SPRITE_SHEET, 0, 0, 16, 16));

    public static final EmiStack FORGE = EmiStack.of(ModDecorativeBlocks.FORGE);
    public static final EmiRecipeCategory FORGE_CATEGORY
            = new EmiRecipeCategory(Identifier.of(MiddleEarth.MOD_ID, "forge"), FORGE, new EmiTexture(MY_SPRITE_SHEET, 0, 0, 16, 16));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(ARTISAN_TABLE_CATEGORY);
        registry.addWorkstation(ARTISAN_TABLE_CATEGORY, ARTISAN_TABLE);
        for (ArtisanRecipe recipe : getRecipes(registry, ModRecipes.ARTISAN_TABLE)) {
            registry.addRecipe(new ArtisanTableEmiRecipe(recipe));
        }

        registry.addCategory(ANVIL_SHAPING_CATEGORY);
        registry.addWorkstation(ANVIL_SHAPING_CATEGORY, EmiStack.of(ModDecorativeBlocks.TREADTED_ANVIL));
        registry.addWorkstation(ANVIL_SHAPING_CATEGORY, EmiStack.of(ModDecorativeBlocks.DWARVEN_TREATED_ANVIL));
        registry.addWorkstation(ANVIL_SHAPING_CATEGORY, EmiStack.of(ModDecorativeBlocks.ELVEN_TREATED_ANVIL));
        registry.addWorkstation(ANVIL_SHAPING_CATEGORY, EmiStack.of(ModDecorativeBlocks.ORCISH_TREATED_ANVIL));
        for (AnvilShapingRecipe recipe : getRecipes(registry, ModRecipes.ANVIL_SHAPING)) {
            registry.addRecipe(new AnvilShapingEmiRecipe(recipe));
        }

        registry.addCategory(FORGE_CATEGORY);
        registry.addWorkstation(FORGE_CATEGORY, FORGE);
        for (AlloyingRecipe recipe : getRecipes(registry, ModRecipes.FORGE)) {
            registry.addRecipe(new ForgeEmiRecipe(recipe));
        }
    }

    private static <C extends RecipeInput, T extends Recipe<C>> Iterable<T> getRecipes(EmiRegistry registry, RecipeType<T> type) {
        return registry.getRecipeManager().listAllOfType(type).stream().map(e -> e.value())::iterator;
    }
}
