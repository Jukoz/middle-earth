package net.sevenstars.middleearth.resources.datas.npc_types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Trade {
    public static final Codec<Trade> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.CODEC.fieldOf("input").forGetter(Trade::getInput),
            Codec.INT.fieldOf("input_rand").forGetter(Trade::getInputRand),
            ItemStack.OPTIONAL_CODEC.optionalFieldOf("second_input", ItemStack.EMPTY).forGetter(Trade::getSecondInput),
            Codec.INT.fieldOf("second_input_rand").forGetter(Trade::getSecondInputRand),
            ItemStack.CODEC.fieldOf("output").forGetter(Trade::getOutput),
            Codec.INT.fieldOf("xp").forGetter(Trade::getXp),
            Codec.INT.fieldOf("level").forGetter(Trade::getLevel),
            Codec.FLOAT.fieldOf("price_multiplier").forGetter(Trade::getPriceMultiplier)
    ).apply(instance, Trade::new));

    private final ItemStack input;
    private final int inputRand;
    private final ItemStack secondInput;
    private final int secondInputRand;
    private final ItemStack output;
    private final int xp;
    private final int level;
    private final float priceMultiplier;

    public Trade(ItemStack input, int inputRand, ItemStack secondInput, int secondInputRand, ItemStack output, int xp, int level, float priceMultiplier) {
        this.input = input;
        this.inputRand = inputRand;
        this.secondInput = secondInput;
        this.secondInputRand = secondInputRand;
        this.output = output;
        this.xp = xp;
        this.level = level;
        this.priceMultiplier = priceMultiplier;
    }

    public Trade(ItemStack input, int inputRand, ItemStack output, int xp, int level, float priceMultiplier) {
        this(input, inputRand, ItemStack.EMPTY, 0, output, xp, level, priceMultiplier);
    }

    public Trade(ItemStack input, ItemStack output, int xp, int level, float priceMultiplier) {
        this(input, 0, ItemStack.EMPTY, 0, output, xp, level, priceMultiplier);
    }

    public Trade(ItemStack input, ItemStack secondInput, ItemStack output, int xp, int level) {
        this(input, 0, secondInput, 0, output, xp, level, 0.05f);
    }

    public Trade(ItemStack input, int inputRand, ItemStack output, int xp, int level) {
        this(input, inputRand, ItemStack.EMPTY, 0, output, xp, level, 0.05f);
    }

    public Trade(ItemStack input, int inputRand, ItemStack output, int xp, int level, int priceMultiplier) {
        this(input, inputRand, ItemStack.EMPTY, 0, output, xp, level, priceMultiplier);
    }

    public Trade(ItemStack input, ItemStack output, int xp, int level) {
        this(input, 0, ItemStack.EMPTY, 0, output, xp, level, 0.05f);
    }

    //public Identifier getId() {
    //    return id;
    //}

    public ItemStack getInput() {
        return input;
    }

    public int getInputRand() {
        return inputRand;
    }

    public ItemStack getSecondInput() {
        return secondInput;
    }

    public int getSecondInputRand() {
        return secondInputRand;
    }

    public ItemStack getOutput() {
        return output;
    }

    public int getXp() {
        return xp;
    }

    public int getLevel() {
        return level;
    }

    public float getPriceMultiplier() {
        return priceMultiplier;
    }
}
