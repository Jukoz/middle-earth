package net.jukoz.me.block.special.shapingAnvil.treatedAnvil;

import net.jukoz.me.block.ModBlockEntities;
import net.jukoz.me.block.special.shapingAnvil.AbstractShapingAnvilBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class TreatedAnvilBlockEntity extends AbstractShapingAnvilBlockEntity {
    public TreatedAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TREATED_ANVIL, pos, state);
    }

    public TreatedAnvilBlockEntity(BlockEntityType type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
