package net.sevenstars.middleearth.item.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ScaffoldingItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.WoodenScaffoldingBlock;

public class WoodenScaffoldingItem extends ScaffoldingItem {
    public WoodenScaffoldingItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        BlockPos blockPos = context.getBlockPos();
        World world = context.getWorld();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = this.getBlock();
        if (blockState.isOf(block)) {
            Direction direction;
            if (context.shouldCancelInteraction()) {
                direction = context.hitsInsideBlock() ? context.getSide().getOpposite() : context.getSide();
            } else {
                direction = context.getSide() == Direction.UP ? context.getHorizontalPlayerFacing() : Direction.UP;
            }

            int horizontalDistance = 0;
            BlockPos.Mutable mutable = blockPos.mutableCopy().move(direction);

            while (horizontalDistance < 7) {
                if (!world.isClient && !world.isInBuildLimit(mutable)) {
                    if (context.getPlayer() instanceof ServerPlayerEntity serverPlayer && mutable.getY() > world.getTopYInclusive()) {
                        serverPlayer.sendMessageToClient(
                                Text.translatable("build.tooHigh", world.getTopYInclusive()).formatted(Formatting.RED),
                                true
                        );
                    }
                    return null;
                }

                blockState = world.getBlockState(mutable);
                if (!blockState.isOf(block)) {
                    return blockState.canReplace(context) ? ItemPlacementContext.offset(context, mutable, direction) : null;
                }

                mutable.move(direction);
                if (direction.getAxis().isHorizontal()) {
                    horizontalDistance++;
                }
            }

            return null;
        }

        return WoodenScaffoldingBlock.calculateDistance(world, blockPos, block) == 7 ? null : context;
    }

    @Override
    protected boolean checkStatePlacement() {
        return false;
    }
}
