package net.sevenstars.middleearth.block.special.curtains;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CurtainsBlock extends HorizontalFacingBlock {
    public static final BooleanProperty OPEN;
    public static final MapCodec<CurtainsBlock> CODEC = createCodec(CurtainsBlock::new);

    public CurtainsBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient && player.getAbilities().allowModifyWorld) {
            world.setBlockState(pos, state.cycle(OPEN));
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(Properties.HORIZONTAL_FACING)) {
            case WEST -> Block.createCuboidShape(14, 2, 0, 16, 16, 16);
            case EAST -> Block.createCuboidShape(0, 2, 0, 2, 16, 16);
            case SOUTH -> Block.createCuboidShape(0, 2, 0, 16, 16, 2);
            case NORTH -> Block.createCuboidShape(0, 2, 14, 16, 16, 16);
            default -> VoxelShapes.cuboid(0, 2, 0, 16, 16, 16);
        };
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{OPEN, FACING});
    }

    static {
        OPEN = Properties.OPEN;
    }
}
