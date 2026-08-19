package net.sevenstars.middleearth.block.special.doors;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.sevenstars.middleearth.block.special.LargeDoorBlock;

public class LargeDoor3x1 extends LargeDoorBlock {
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 2);

    public LargeDoor3x1(Properties settings) {
        super(settings);
        this.doorHeight = 3;
        this.doorWidth  = 1;
    }

    @Override
    public IntegerProperty getPart() {
        return PART;
    }
}