package net.jukoz.me.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.recipe.AlloyingRecipe;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ForgeEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> inputs;
    private final String output;
    private final int outputAmount;

    public ForgeEmiRecipe(AlloyingRecipe recipe){
        this.id = Identifier.of(MiddleEarth.MOD_ID, "/forge" + "/" + recipe.getAlloyResult() + recipe.getAmount());
        this.inputs = recipe.getIngredients().stream().map(i -> EmiIngredient.of(i)).toList();
        this.output = recipe.getAlloyResult();
        this.outputAmount = recipe.getAmount();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ModEmiPlugin.FORGE_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of();
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
        for (int i = 0; i < inputs.size(); i++){
            widgets.addSlot(inputs.get(i), 20 * i, 0);
        }

        widgets.addText(Text.translatable("tooltip." + MiddleEarth.MOD_ID +".liquid_" + output), 0, 30, 0, false);
        widgets.addText(Text.of(String.valueOf(outputAmount)), 0, 40, 0, false);
    }
}
