package net.jukoz.me.block.special.doors;

import net.jukoz.me.block.special.LargeDoorBlock;
import net.minecraft.state.property.IntProperty;

public class LargeDoor5x2 extends LargeDoorBlock {
    public static final IntProperty PART = IntProperty.of("part", 0, 9);

    public LargeDoor5x2(Settings settings) {
        super(settings);
        this.doorHeight = 5;
        this.doorWidth  = 2;
    }

    @Override
    public IntProperty getPart() {
        return PART;
    }
}