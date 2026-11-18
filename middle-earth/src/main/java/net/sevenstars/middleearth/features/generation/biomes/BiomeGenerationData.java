package net.sevenstars.middleearth.features.generation.biomes;

public class BiomeGenerationData {
    // The way the ridges of a mountain run, SMOOTH keeps the plain noise of a single peak
    public enum RidgeAxis { NORTH_SOUTH, EAST_WEST, ISOTROPIC, SMOOTH }

    public RidgeAxis ridgeAxis = RidgeAxis.ISOTROPIC;
    public float ridgeAmplitude = 11; // blocks per unit of ridge noise and of mountain multiplier
    public byte[] biomeWeight;
    public double noiseModifier; /** Between 0 and 2; */
    public double heightModifier; /** Between 0 and 2; */

    public BiomeGenerationData(BiomeGenerationData data) {
        this.biomeWeight = data.biomeWeight;
        this.noiseModifier = data.noiseModifier;
        this.heightModifier = data.heightModifier;
        this.ridgeAxis = data.ridgeAxis;
        this.ridgeAmplitude = data.ridgeAmplitude;
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

    public BiomeGenerationData ridges(RidgeAxis axis, float amplitude) {
        BiomeGenerationData newInstance = new BiomeGenerationData(this);
        newInstance.ridgeAxis = axis;
        newInstance.ridgeAmplitude = amplitude;
        return newInstance;
    }
}
