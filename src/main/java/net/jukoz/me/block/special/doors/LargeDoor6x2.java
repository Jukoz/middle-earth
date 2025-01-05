package net.jukoz.me.block.special.doors;

import net.jukoz.me.block.special.LargeDoorBlock;
import net.minecraft.state.property.IntProperty;

public class LargeDoor6x2 extends LargeDoorBlock {
    public static final IntProperty PART = IntProperty.of("part", 0, 11);

    public LargeDoor6x2(Settings settings) {
        super(settings);
        this.doorHeight = 6;
        this.doorWidth  = 2;
    }

    @Override
    public IntProperty getPart() {
        return PART;
    }
}