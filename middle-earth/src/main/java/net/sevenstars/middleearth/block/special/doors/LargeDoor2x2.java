package net.sevenstars.middleearth.block.special.doors;

import net.sevenstars.middleearth.block.special.LargeDoorBlock;
import net.minecraft.state.property.IntProperty;

public class LargeDoor2x2 extends LargeDoorBlock {
    public static final IntProperty PART = IntProperty.of("part", 0, 3);

    public LargeDoor2x2(Settings settings) {
        super(settings);
        this.doorHeight = 2;
        this.doorWidth  = 2;
    }

    @Override
    public IntProperty getPart() {
        return PART;
    }
}