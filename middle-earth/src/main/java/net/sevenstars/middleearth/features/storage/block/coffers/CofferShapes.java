package net.sevenstars.middleearth.features.storage.block.coffers;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

import static net.minecraft.block.ChestBlock.FACING;

public class CofferShapes {
    public static VoxelShape larch(BlockState state) {
        if(state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH) {
            return Block.createCuboidShape(1.0, 0.0, 3.0, 15.0, 12.0, 13.0);
        } else {
            return Block.createCuboidShape(3.0, 0.0, 1.0, 13.0, 12.0, 15.0);
        }
    }

    public static VoxelShape pine(BlockState state) {
        if(state.get(FACING) == Direction.NORTH) {
            return Block.createCuboidShape(0.0, 0.0, 1.0, 16.0, 16.0, 14.0);
        } else if(state.get(FACING) == Direction.SOUTH) {
            return Block.createCuboidShape(0.0, 0.0, 2.0, 16.0, 16.0, 15.0);
        } else if(state.get(FACING) == Direction.EAST) {
            return Block.createCuboidShape(2.0, 0.0, 0.0, 15.0, 16.0, 16.0);
        } else {
            return Block.createCuboidShape(1.0, 0.0, 0.0, 14.0, 16.0, 16.0);
        }
    }

    public static VoxelShape spruce(BlockState state) {
        return Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 11.0, 16.0);
    }

    public static VoxelShape fir(BlockState state) {
        if(state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH) {
            return Block.createCuboidShape(3.0, 0.0, 4.0, 13.0, 8.0, 12.0);
        } else {
            return Block.createCuboidShape(4.0, 0.0, 3.0, 12.0, 8.0, 13.0);
        }
    }

    public static VoxelShape beech(BlockState state) {
        if(state.get(FACING) == Direction.NORTH) {
            return Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 8.0, 10.0);
        } else if(state.get(FACING) == Direction.SOUTH) {
            return Block.createCuboidShape(2.0, 0.0, 6.0, 14.0, 8.0, 14.0);
        } else if(state.get(FACING) == Direction.EAST) {
            return Block.createCuboidShape(6.0, 0.0, 2.0, 14.0, 8.0, 14.0);
        } else {
            return Block.createCuboidShape(2.0, 0.0, 2.0, 10.0, 8.0, 14.0);
        }
    }

    public static VoxelShape chestnut(BlockState state) {
        if(state.get(FACING) == Direction.NORTH) {
            return Block.createCuboidShape(0.0, 0.0, 2.0, 16.0, 12.0, 13.0);
        } else if(state.get(FACING) == Direction.SOUTH) {
            return Block.createCuboidShape(0.0, 0.0, 3.0, 16.0, 12.0, 14.0);
        } else if(state.get(FACING) == Direction.EAST) {
            return Block.createCuboidShape(3.0, 0.0, 0.0, 14.0, 12.0, 16.0);
        } else {
            return Block.createCuboidShape(2.0, 0.0, 0.0, 13.0, 12.0, 16.0);
        }
    }

    public static VoxelShape oak(BlockState state) {
        if(state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH) {
            return Block.createCuboidShape(0.0, 0.0, 2.0, 16.0, 14.0, 14.0);
        } else {
            return Block.createCuboidShape(2.0, 0.0, 0.0, 14.0, 14.0, 16.0);
        }
    }

    public static VoxelShape willow(BlockState state) {
        if(state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH) {
            return Block.createCuboidShape(0.0, 0.0, 2.0, 16.0, 16.0, 14.0);
        } else {
            return Block.createCuboidShape(2.0, 0.0, 0.0, 14.0, 16.0, 16.0);
        }
    }
}
