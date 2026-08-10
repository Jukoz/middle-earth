package net.sevenstars.middleearth.block.special.doors;

import net.minecraft.state.property.IntProperty;
import net.sevenstars.middleearth.block.special.LargeDoorBlock;

public class LargeDoor1x2 extends LargeDoorBlock {
    public static final IntProperty PART = IntProperty.of("part", 0, 1);

    public LargeDoor1x2(Settings settings) {
        super(settings);
        this.doorHeight = 1;
        this.doorWidth  = 2;
    }

    @Override
    public IntProperty getPart() {
        return PART;
    }
}