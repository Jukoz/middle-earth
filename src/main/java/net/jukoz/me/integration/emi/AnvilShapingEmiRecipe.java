package net.jukoz.me.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.recipe.AnvilShapingRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AnvilShapingEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final EmiIngredient input;
    private final EmiStack output;

    public AnvilShapingEmiRecipe(AnvilShapingRecipe recipe){
        this.id = Identifier.of(MiddleEarth.MOD_ID, "/shaping_anvil" + "/" + Registries.ITEM.getId(recipe.getOutput().getItem()).getPath());
        this.input = EmiIngredient.of(recipe.getIngredient());
        this.output = EmiStack.of(recipe.getOutput());
    }
    @Override
    public EmiRecipeCategory getCategory() {
        return ModEmiPlugin.ANVIL_SHAPING_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(input);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 76;
    }

    @Override
    public int getDisplayHeight() {
        return 83;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(input, 0, 0);

        widgets.addSlot(output, 54, 0).recipeContext(this);
    }
}
