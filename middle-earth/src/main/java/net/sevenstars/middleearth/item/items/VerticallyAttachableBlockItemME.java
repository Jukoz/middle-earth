package net.sevenstars.middleearth.item.items;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

public class VerticallyAttachableBlockItemME extends BlockItem {
    protected final Block wallBlock;
    private final Direction[] verticalAttachmentDirections;

    public VerticallyAttachableBlockItemME(Block standingBlock, Block wallBlock, Properties settings, Direction[] verticalAttachmentDirections) {
        super(standingBlock, settings);
        this.wallBlock = wallBlock;
        this.verticalAttachmentDirections = verticalAttachmentDirections;
    }

    protected boolean canPlaceAt(LevelReader world, BlockState state, BlockPos pos) {
        return state.canSurvive(world, pos);
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context) {
        BlockState blockState = this.wallBlock.getStateForPlacement(context);
        BlockState blockState2 = null;
        LevelReader worldView = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        Direction[] var6 = context.getNearestLookingDirections();
        int var7 = var6.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            Direction direction = var6[var8];
            for(Direction verticalAttachmentDirection : verticalAttachmentDirections){
                if (direction != verticalAttachmentDirection.getOpposite()) {
                    BlockState blockState3 = direction == verticalAttachmentDirection ? this.getBlock().getStateForPlacement(context) : blockState;
                    if (blockState3 != null && this.canPlaceAt(worldView, blockState3, blockPos)) {
                        blockState2 = blockState3;
                        return worldView.isUnobstructed(blockState2, blockPos, CollisionContext.empty()) ? blockState2 : null;
                    }
                }
            }
        }
        return null;
    }

    public void registerBlocks(Map<Block, Item> map, Item item) {
        super.registerBlocks(map, item);
        map.put(this.wallBlock, item);
    }
}