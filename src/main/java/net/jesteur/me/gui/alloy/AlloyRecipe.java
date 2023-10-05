package net.jesteur.me.gui.alloy;

import net.jesteur.me.block.special.alloy.AlloyBlockEntity;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class AlloyRecipe {
    private final List<Item> inputs;
    private final Item output;
    private final int outputCount;

    public AlloyRecipe(List<Item> inputs, Item output, int outputCount) {
        if(inputs.size() > AlloyBlockEntity.INPUT_SIZE) throw new RuntimeException("The alloy recipe exceeds the inputs size of " + AlloyBlockEntity.INPUT_SIZE + " by " + (inputs.size()));
        this.inputs = inputs;
        this.output = output;
        this.outputCount = outputCount;
    }

    public List<Item> getInputs() {
        return new ArrayList<>(inputs);
    }

    public Item getOutput() {
        return output;
    }

    public int getOutputCount() {
        return outputCount;
    }
}
