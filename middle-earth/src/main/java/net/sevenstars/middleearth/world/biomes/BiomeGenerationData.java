package net.sevenstars.middleearth.world.biomes;

public class BiomeGenerationData {
    public byte[] biomeWeight;
    public double noiseModifier; /** Between 0 and 2; */
    public double heightModifier; /** Between 0 and 2; */

    public BiomeGenerationData(BiomeGenerationData data) {
        this.biomeWeight = data.biomeWeight;
        this.noiseModifier = data.noiseModifier;
        this.heightModifier = data.heightModifier;
    }

    public BiomeGenerationData() {
    }

    public BiomeGenerationData expansionWeight(byte[] biomeWeight) {
        BiomeGenerationData newInstance = new BiomeGenerationData(this);
        newInstance.biomeWeight = biomeWeight;
        return newInstance;
    }

    public BiomeGenerationData noiseModifier(double noiseModifier) {
        BiomeGenerationData newInstance = new BiomeGenerationData(this);
        newInstance.noiseModifier = noiseModifier;
        return newInstance;
    }

    public BiomeGenerationData heightModifier(double heightModifier) {
        BiomeGenerationData newInstance = new BiomeGenerationData(this);
        newInstance.heightModifier = heightModifier;
        return newInstance;
    }
}
