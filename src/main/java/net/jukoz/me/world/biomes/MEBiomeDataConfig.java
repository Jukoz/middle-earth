package net.jukoz.me.world.biomes;

import net.minecraft.block.AbstractBlock;

public class MEBiomeDataConfig {
    public class BiomeGenerationData {
        public byte[] biomeWeight;
        public double noiseModifier;
        public double heightModifier;

        public BiomeGenerationData biomeWeight(byte[] biomeWeight) {
            this.biomeWeight = biomeWeight;
            return this;
        }

        public BiomeGenerationData noiseModifier(double noiseModifier) {
            this.noiseModifier = noiseModifier;
            return this;
        }

        public BiomeGenerationData heightModifier(double noiseModifier) {
            this.heightModifier = noiseModifier;
            return this;
        }
        public BiomeGenerationData(){

        }
    }

    public class SlopeMap {

    }

}
