package net.sevenstars.middleearth.block.special.doors;

import net.sevenstars.middleearth.block.special.LargeDoorBlock;
import net.minecraft.state.property.IntProperty;

public class LargeDoor3x1 extends LargeDoorBlock {
    public static final IntProperty PART = IntProperty.of("part", 0, 2);

    public LargeDoor3x1(Settings settings) {
        super(settings);
        this.doorHeight = 3;
        this.doorWidth  = 1;
    }

    @Override
    public IntProperty getPart() {
        return PART;
    }
}