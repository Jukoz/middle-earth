package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class WebbingBlock extends MultifaceGrowthBlock {
    public static final BooleanProperty PERSISTENT = Properties.PERSISTENT;
    public static final MapCodec<WebbingBlock> CODEC = createCodec(WebbingBlock::new);
    private final MultifaceGrower grower = new MultifaceGrower(this);

    public WebbingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(PERSISTENT, true));
    }

    @Override
    public MapCodec<? extends MultifaceGrowthBlock> getCodec() {
        return CODEC;
    }

    @Override
    public MultifaceGrower getGrower() {
        return this.grower;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PERSISTENT);
        super.appendProperties(builder);
    }

    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler) {
        Vec3d vec3d = new Vec3d(0.75, 0.50, 0.75);
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.hasStatusEffect(StatusEffects.WEAVING)) {
                vec3d = new Vec3d(0.90, 0.75, 0.90);
            }
        }

        entity.slowMovement(state, vec3d);
    }

    protected boolean hasRandomTicks(BlockState state) {
        return !(Boolean)state.get(PERSISTENT);
    }

    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.shouldDecay(state)) {
            world.removeBlock(pos, false);
        }
    }

    protected boolean shouldDecay(BlockState state) {
        return !(Boolean)state.get(PERSISTENT);
    }
}
