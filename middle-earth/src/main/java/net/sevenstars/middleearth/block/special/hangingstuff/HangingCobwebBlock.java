package net.sevenstars.middleearth.block.special.hangingstuff;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class HangingCobwebBlock extends CustomHangingBlock {
    public HangingCobwebBlock(Properties settings) {
        super(settings);
    }

    protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        Vec3 vec3d = new Vec3(0.25, 0.05000000074505806, 0.25);
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(MobEffects.WEAVING)) {
                vec3d = new Vec3(0.5, 0.25, 0.5);
            }
        }

        entity.makeStuckInBlock(state, vec3d);
    }


    @Override
    protected void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClientSide) {
            for (Direction direction : Direction.values()) {
                if (world.getFluidState(pos.relative(direction)).is(net.minecraft.tags.FluidTags.WATER)) {
                    world.destroyBlock(pos, true);
                    return;
                }
            }
        }
        super.neighborChanged(state, world, pos, sourceBlock, sourcePos, notify);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return false;
    }
}
