package net.sevenstars.middleearth.compat.artisantable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.fabric.impl.recipe.ingredient.builtin.ComponentsIngredient;
import net.minecraft.component.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.compat.REICommonPluginME;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableInputsShape;
import net.sevenstars.middleearth.mixin.ComponentsIngredientMixin;
import net.sevenstars.middleearth.recipe.ArtisanRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArtisanTableDisplay extends BasicDisplay {
    protected String category;

    public static final DisplaySerializer<ArtisanTableDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(ArtisanTableDisplay::getInputEntries),
                    EntryIngredient.codec().listOf().fieldOf("outputs").forGetter(ArtisanTableDisplay::getOutputEntries),
                    Codec.STRING.fieldOf("category").forGetter(ArtisanTableDisplay::getCategory)
            ).apply(instance, ArtisanTableDisplay::new)),
            PacketCodec.tuple(
                    EntryIngredient.streamCodec().collect(PacketCodecs.toList()),
                    ArtisanTableDisplay::getInputEntries,
                    EntryIngredient.streamCodec().collect(PacketCodecs.toList()),
                    ArtisanTableDisplay::getOutputEntries,
                    PacketCodecs.STRING,
                    ArtisanTableDisplay::getCategory,
                    ArtisanTableDisplay::new
            )
    );

    public ArtisanTableDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, String category) {
        super(inputs, outputs);
        this.category = category;
    }

    public ArtisanTableDisplay(ArtisanRecipe recipe) {
        this(getInputs(recipe), getOutputs(recipe), recipe.category);
    }

    public static List<EntryIngredient> getInputs(ArtisanRecipe recipe) {
        List<EntryIngredient> inputs = new ArrayList<>(EntryIngredients.ofIngredients(recipe.getIngredients()));
        int index = 0;
        for(Ingredient ingredient : recipe.getIngredients()) {
            if(index > inputs.size() || index > recipe.inputs.size()) break;
            ComponentsIngredient customIngredient = (ComponentsIngredient) ingredient.getCustomIngredient();
            if(customIngredient != null) {
                ComponentsIngredientMixin ingredientComponentsAccessor = (ComponentsIngredientMixin) customIngredient;
                ComponentChanges changedComponents = ingredientComponentsAccessor.getComponentChanges();
                if(changedComponents != null && changedComponents.get(DataComponentTypes.TRIM).isPresent()) {
                    ArmorTrim trim = changedComponents.get(DataComponentTypes.TRIM).get();
                    if(trim.pattern().matchesId(MiddleEarth.of("smithing_part"))) {
                        ItemStack stackComponentsSmithing = new ItemStack(customIngredient.getMatchingItems().toList().getFirst());
                        stackComponentsSmithing.set(DataComponentTypes.TRIM, trim);
                        EntryIngredient entryIngredient = EntryIngredients.of(stackComponentsSmithing);
                        inputs.set(index, entryIngredient);
                    }
                }
            }
            index++;
        }
        return inputs;
    }

    private static List<EntryIngredient> getOutputs(ArtisanRecipe recipe) {
        return List.of(EntryIngredients.of(recipe.getOutput()));
    }

    public static ArtisanTableDisplay of(ArtisanRecipe recipe) {
        List<EntryIngredient> inputs = EntryIngredients.ofIngredients(recipe.getIngredients());
        List<EntryIngredient> outputs = Collections.singletonList(EntryIngredients.of(recipe.getOutput()));

        return new ArtisanTableDisplay(inputs, outputs, recipe.category);
    }

    public String getCategory() {
        return this.category;
    }

    public ArtisanTableInputsShape getArtisanTableInputShape() {
        return ArtisanTableInputsShape.getShape(this.category);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return REICommonPluginME.ARTISAN_TABLE_CATEGORY;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
}
