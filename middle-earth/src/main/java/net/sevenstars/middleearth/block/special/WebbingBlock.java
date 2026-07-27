package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class WebbingBlock extends MultifaceBlock {
    public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
    public static final MapCodec<WebbingBlock> CODEC = simpleCodec(WebbingBlock::new);
    private final MultifaceSpreader grower = new MultifaceSpreader(this);

    public WebbingBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(PERSISTENT, true));
    }

    @Override
    public MapCodec<? extends MultifaceBlock> codec() {
        return CODEC;
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return this.grower;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PERSISTENT);
        super.createBlockStateDefinition(builder);
    }

    protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        Vec3 vec3d = new Vec3(0.75, 0.50, 0.75);
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(MobEffects.WEAVING)) {
                vec3d = new Vec3(0.90, 0.75, 0.90);
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

    protected boolean isRandomlyTicking(BlockState state) {
        return !(Boolean)state.getValue(PERSISTENT);
    }

    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (this.shouldDecay(state)) {
            world.removeBlock(pos, false);
        }
    }

    protected boolean shouldDecay(BlockState state) {
        return !(Boolean)state.getValue(PERSISTENT);
    }
}
