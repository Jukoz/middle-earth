package net.sevenstars.middleearth.item.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ScaffoldingBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.sevenstars.middleearth.block.special.ReinforcedScaffoldingBlock;

public class ReinforcedScaffoldingItem extends ScaffoldingBlockItem {
    public ReinforcedScaffoldingItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Override
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        Level world = context.getLevel();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = this.getBlock();
        if (blockState.is(block)) {
            Direction direction;
            if (context.isSecondaryUseActive()) {
                direction = context.isInside() ? context.getClickedFace().getOpposite() : context.getClickedFace();
            } else {
                direction = context.getClickedFace() == Direction.UP ? context.getHorizontalDirection() : Direction.UP;
            }

            int horizontalDistance = 0;
            BlockPos.MutableBlockPos mutable = blockPos.mutable().move(direction);

            while (horizontalDistance < ReinforcedScaffoldingBlock.MAX_SUPPORT_DISTANCE) {
                if (!world.isClientSide && !world.isInWorldBounds(mutable)) {
                    if (context.getPlayer() instanceof ServerPlayer serverPlayer && mutable.getY() > world.getMaxBuildHeight()) {
                        serverPlayer.sendSystemMessage(
                                Component.translatable("build.tooHigh", world.getMaxBuildHeight()).withStyle(ChatFormatting.RED),
                                true
                        );
                    }
                    return null;
                }

                blockState = world.getBlockState(mutable);
                if (!blockState.is(block)) {
                    return blockState.canBeReplaced(context) ? BlockPlaceContext.at(context, mutable, direction) : null;
                }

                mutable.move(direction);
                if (direction.getAxis().isHorizontal()) {
                    horizontalDistance++;
                }
            }

            return null;
        }

        return ReinforcedScaffoldingBlock.calculateDistance(world, blockPos, block) == ReinforcedScaffoldingBlock.MAX_SUPPORT_DISTANCE ? null : context;
    }

    @Override
    protected boolean mustSurvive() {
        return false;
    }
}
