package net.jukoz.me.block.special.shapingAnvil.dwarvenTreatedAnvil;

import net.jukoz.me.block.ModBlockEntities;
import net.jukoz.me.block.special.shapingAnvil.AbstractShapingAnvilBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

public class DwarvenTreatedAnvilBlockEntity extends AbstractShapingAnvilBlockEntity {
    public DwarvenTreatedAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DWARVEN_SHAPING_ANVIL, pos, state);
    }

    public DwarvenTreatedAnvilBlockEntity(BlockEntityType type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}
