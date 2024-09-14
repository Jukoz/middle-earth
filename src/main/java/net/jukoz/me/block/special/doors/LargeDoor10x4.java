package net.jukoz.me.block.special.doors;

import net.jukoz.me.block.special.LargeDoorBlock;
import net.minecraft.state.property.IntProperty;

public class LargeDoor10x4 extends LargeDoorBlock {
    public static final IntProperty PART = IntProperty.of("part", 0, 39);

    public LargeDoor10x4(int height, int width, Settings settings) {
        super(height, width, settings);
    }

    @Override
    public IntProperty getPart() {
        return PART;
    }
}