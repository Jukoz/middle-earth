package net.sevenstars.middleearth.block.special.doors;

import net.sevenstars.middleearth.block.special.LargeDoorBlock;
import net.minecraft.state.property.IntProperty;

public class LargeDoor10x4 extends LargeDoorBlock {
    public static final IntProperty PART = IntProperty.of("part", 0, 39);

    public LargeDoor10x4(Settings settings) {
        super(settings);
        this.doorHeight = 10;
        this.doorWidth  = 4;
    }

    @Override
    public IntProperty getPart() {
        return PART;
    }
}