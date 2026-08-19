package net.sevenstars.middleearth.block.special.dirts;

import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class CustomFarmlandBlock extends FarmBlock {

    private final Block target;

    public CustomFarmlandBlock(Properties settings, Block target) {
        super(settings);
        this.target = target;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return !this.defaultBlockState().canSurvive(ctx.getLevel(), ctx.getClickedPos()) ? target.defaultBlockState() : super.getStateForPlacement(ctx);
    }

    public static void setToDirt(@Nullable Entity entity, BlockState state, Level world, BlockPos pos, Block target) {
        BlockState blockState = pushEntitiesUp(state, target.defaultBlockState(), world, pos);
        world.setBlockAndUpdate(pos, blockState);
        world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, blockState));
    }

    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(world, pos)) {
            setToDirt((Entity)null, state, world, pos, target);
        }

    }

    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int i = (Integer)state.getValue(MOISTURE);
        if (!isNearWater(world, pos) && !world.isRainingAt(pos.above())) {
            if (i > 0) {
                world.setBlock(pos, (BlockState)state.setValue(MOISTURE, i - 1), 2);
            } else if (!shouldMaintainFarmland(world, pos)) {
                setToDirt((Entity)null, state, world, pos, target);
            }
        } else if (i < 7) {
            world.setBlock(pos, (BlockState)state.setValue(MOISTURE, 7), 2);
        }

    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (world instanceof ServerLevel serverWorld) {
            if ((double)world.random.nextFloat() < fallDistance - 0.5 && entity instanceof LivingEntity && (entity instanceof Player || serverWorld.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) && entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512F) {
                setToDirt((Entity)null, state, world, pos, target);
            }
        }
        entity.causeFallDamage(fallDistance, 1.0F, entity.damageSources().fall());
    }

    private static boolean shouldMaintainFarmland(BlockGetter world, BlockPos pos) {
        return world.getBlockState(pos.above()).is(BlockTags.MAINTAINS_FARMLAND);
    }

    private static boolean isNearWater(LevelReader world, BlockPos pos) {
        Iterator var2 = BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4)).iterator();

        BlockPos blockPos;
        do {
            if (!var2.hasNext()) {
                return false;
            }

            blockPos = (BlockPos)var2.next();
        } while(!world.getFluidState(blockPos).is(FluidTags.WATER));

        return true;
    }
}
