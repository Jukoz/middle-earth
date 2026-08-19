package net.sevenstars.middleearth.block.special.doors;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.sevenstars.middleearth.block.special.LargeDoorBlock;

public class LargeDoor4x2 extends LargeDoorBlock {
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 7);

    public LargeDoor4x2(Properties settings) {
        super(settings);
        this.doorHeight = 4;
        this.doorWidth  = 2;
    }

    @Override
    public IntegerProperty getPart() {
        return PART;
    }
}