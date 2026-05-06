package net.sevenstars.middleearth.compat.forge;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.sevenstars.middleearth.recipe.AlloyingRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AlloyingDisplay extends BasicDisplay {
    protected String output;
    protected int amount;

    public static final DisplaySerializer<AlloyingDisplay> SERIALIZER = DisplaySerializer.of(
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    EntryIngredient.codec().listOf().fieldOf("inputs").forGetter(AlloyingDisplay::getInputEntries),
                    Codec.STRING.fieldOf("output").forGetter(AlloyingDisplay::getOutput),
                    Codec.INT.fieldOf("amount").forGetter(AlloyingDisplay::getAmount)
            ).apply(instance, AlloyingDisplay::new)),
            PacketCodec.tuple(
                    EntryIngredient.streamCodec().collect(PacketCodecs.toList()),
                    AlloyingDisplay::getInputEntries,
                    PacketCodecs.STRING,
                    AlloyingDisplay::getOutput,
                    PacketCodecs.INTEGER,
                    AlloyingDisplay::getAmount,
                    AlloyingDisplay::new
            )
    );

    public AlloyingDisplay(List<EntryIngredient> inputs, String output, int amount) {
        super(inputs, List.of());
        this.output = output;
        this.amount = amount;
    }

    public AlloyingDisplay(AlloyingRecipe recipe) {
        this(getInputs(recipe), recipe.output, recipe.amount);
    }

    public static List<EntryIngredient> getInputs(AlloyingRecipe recipe) {
        List<EntryIngredient> inputs = new ArrayList<>(EntryIngredients.ofIngredients(recipe.getIngredients()));
        return inputs;
    }

    public String getOutput() {
        return this.output;
    }

    public int getAmount() {
        return this.amount;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return AlloyingCategory.FORGE;
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }
}
