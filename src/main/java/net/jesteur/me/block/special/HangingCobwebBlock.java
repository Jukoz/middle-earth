package net.jesteur.me.block.special;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class HangingCobwebBlock extends Block {
    public HangingCobwebBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.up(), Direction.DOWN) && !world.isWater(pos);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.slowMovement(state, new Vec3d(0.25, 0.05f, 0.25));
    }
}
