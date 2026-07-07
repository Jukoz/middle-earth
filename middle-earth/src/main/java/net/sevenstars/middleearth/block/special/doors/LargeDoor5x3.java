package net.sevenstars.middleearth.block.special.doors;

import net.sevenstars.middleearth.block.special.LargeDoorBlock;
import net.minecraft.state.property.IntProperty;

public class LargeDoor5x3 extends LargeDoorBlock {
    public static final IntProperty PART = IntProperty.of("part", 0, 14);

    public LargeDoor5x3(Settings settings) {
        super(settings);
        this.doorHeight = 5;
        this.doorWidth  = 3;
    }

    @Override
    public IntProperty getPart() {
        return PART;
    }
}