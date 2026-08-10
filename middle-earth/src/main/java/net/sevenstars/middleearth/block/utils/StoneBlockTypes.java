package net.sevenstars.middleearth.block.utils;

public enum StoneBlockTypes {
    BASE_BLOCKS("", ""),
    PILLAR_BASE_BLOCKS("", ""),
    COBBLED_BLOCKS("cobbled_", ""),
    COBBLESTONE_BLOCKS("", ""),
    BRICK_BLOCKS("", "_bricks"),
    POLISHED_BRICK_BLOCKS("polished_", "_bricks"),
    TILE_BLOCKS("","_tiles"),
    SMOOTH_BLOCKS("smooth_", ""),
    POLISHED_BLOCKS("polished_", ""),
    POLISHED_BLOCKS_PILLAR("polished_", ""),
    BRICKWORK_BLOCKS("", "_brickwork"),
    PILLAR_BLOCKS("", "_pillar"),
    CHISELED_BLOCKS("chiseled_", ""),
    CHISELED_BLOCKS_NO_RESTRICTION("chiseled_", ""),
    OLD_BLOCKS("old_", ""),
    OLD_BLOCKS_PILLAR("old_", ""),
    CARVED_WINDOW("", "_carved_window"),
    ;

    private final String prefix;
    private final String suffix;

    StoneBlockTypes(String prefix, String suffix){
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
