package net.jukoz.me.world.biomes;

import net.minecraft.block.Block;

import java.util.ArrayList;

public class BlocksLayeringData {
    public ArrayList<LayerData> layers;

    public BlocksLayeringData(BlocksLayeringData blocksLayeringData) {
        this.layers = new ArrayList<>(blocksLayeringData.layers);
    }

    public BlocksLayeringData() {
        this.layers = new ArrayList<>();
    }

    public BlocksLayeringData addLayerData(float percentage, Block block) {
        if (percentage <= 0.0f || percentage > 1.0f) {
            throw new ArithmeticException("Cannot add a layer with a percentage that isn't between 0.0f and 1.0f");
        }
        layers.add(new LayerData(percentage, block));
        return this;
    }

    public class LayerData {
        public float percentage;
        public Block block;

        public LayerData(float percentage, Block block) {
            this.percentage = percentage;
            this.block = block;
        }
    }
}
