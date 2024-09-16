package net.jukoz.me.block.special.doors;

import net.jukoz.me.block.special.LargeDoorBlock;
import net.minecraft.state.property.IntProperty;

public class LargeDoor10x5 extends LargeDoorBlock {
    public static final IntProperty PART = IntProperty.of("part", 0, 49);

    public LargeDoor10x5(Settings settings) {
        super(settings);
        this.doorHeight = 10;
        this.doorWidth  = 5;
    }

    @Override
    public IntProperty getPart() {
        return PART;
    }
}