package net.sevenstars.middleearth.block.utils;

public enum WoodBlockTypes {
    STEM_BLOCKS("", "_stem"),
    LOG_BLOCKS("", ""),
    STRIPPED_LOG_BLOCKS("stripped_", ""),
    PLANK_BLOCKS("", "_planks"),
    REDSTONE_BLOCKS("", ""),
    FURNITURE_BLOCKS("", ""),
    ROOFING_BLOCKS("", "_roofing"),
    SHINGLE_BLOCKS("", "_shingles"),
    LEAVES("", "_leaves"),
    ;

    private final String prefix;
    private final String suffix;

    WoodBlockTypes(String prefix, String suffix){
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }
}
