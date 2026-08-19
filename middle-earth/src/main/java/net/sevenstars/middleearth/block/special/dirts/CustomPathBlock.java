package net.sevenstars.middleearth.block.special.dirts;

import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CustomPathBlock extends DirtPathBlock {

    private final Block target;

    public CustomPathBlock(Properties settings, Block target) {
        super(settings);
        this.target = target;
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return !this.defaultBlockState().canSurvive(ctx.getLevel(), ctx.getClickedPos()) ? Block.pushEntitiesUp(this.defaultBlockState(), target.defaultBlockState(), ctx.getLevel(), ctx.getClickedPos()) : super.getStateForPlacement(ctx);
    }

    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        CustomFarmlandBlock.setToDirt((Entity)null, state, world, pos, target);
    }
}
