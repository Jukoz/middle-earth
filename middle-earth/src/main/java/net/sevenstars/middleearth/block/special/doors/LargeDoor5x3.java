package net.sevenstars.middleearth.block.special.doors;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.sevenstars.middleearth.block.special.LargeDoorBlock;

public class LargeDoor5x3 extends LargeDoorBlock {
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 14);

    public LargeDoor5x3(Properties settings) {
        super(settings);
        this.doorHeight = 5;
        this.doorWidth  = 3;
    }

    @Override
    public IntegerProperty getPart() {
        return PART;
    }
}