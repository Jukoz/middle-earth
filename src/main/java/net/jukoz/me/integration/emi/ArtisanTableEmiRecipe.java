package net.jukoz.me.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.artisantable.ArtisanTableInputsShape;
import net.jukoz.me.gui.artisantable.InputType;
import net.jukoz.me.recipe.ArtisanRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArtisanTableEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> inputs;
    private final List<EmiStack> output;
    private final ArtisanTableInputsShape shape;

    public ArtisanTableEmiRecipe(ArtisanRecipe recipe){
        this.id = Identifier.of(MiddleEarth.MOD_ID, "/artisan_table" + "/" + Registries.ITEM.getId(recipe.getOutput().getItem()).getPath());
        this.inputs = recipe.getIngredients().stream().map(i -> EmiIngredient.of(i)).toList();
        this.output = List.of(EmiStack.of(recipe.getOutput()));
        this.shape = ArtisanTableInputsShape.getShape(recipe.category);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return ModEmiPlugin.ARTISAN_TABLE_CATEGORY;
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
        return output;
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
        SlotWidget[][] inputSlots = new SlotWidget[3][3];
        int index = 0;
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                if (index < getInputs().size()){
                    InputType inputType = shape.getInputType(x,y);
                    if (inputType != InputType.NONE){
                        inputSlots[y][x] = (SlotWidget) widgets.addSlot(inputs.get(index++), 13 + 18*x, 18 * y);
                    }
                }
            }
        }

        widgets.addSlot(output.getFirst(), 31, 65).recipeContext(this);
    }
}
