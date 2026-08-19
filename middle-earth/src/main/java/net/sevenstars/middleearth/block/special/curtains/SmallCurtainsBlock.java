package net.sevenstars.middleearth.block.special.curtains;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SmallCurtainsBlock extends HorizontalDirectionalBlock {
    public static final BooleanProperty OPEN;
    public static final MapCodec<SmallCurtainsBlock> CODEC = simpleCodec(SmallCurtainsBlock::new);

    public SmallCurtainsBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide && player.getAbilities().mayBuild) {
            world.setBlockAndUpdate(pos, state.cycle(OPEN));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case WEST -> Block.box(14, 2, 0, 16, 16, 16);
            case EAST -> Block.box(0, 2, 0, 2, 16, 16);
            case SOUTH -> Block.box(0, 2, 0, 16, 16, 2);
            case NORTH -> Block.box(0, 2, 14, 16, 16, 16);
            default -> Shapes.box(0, 2, 0, 16, 16, 16);
        };
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{OPEN, FACING});
    }

    static {
        OPEN = BlockStateProperties.OPEN;
    }
}
