package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.MiddleEarth;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class LargeDoorBlock extends Block {

    //TODO CLEAN CLASS

    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 127);
    public static final EnumProperty<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;

    public static final MapCodec<LargeDoorBlock> CODEC = LargeDoorBlock.simpleCodec(LargeDoorBlock::new);

    protected int doorHeight;
    protected int doorWidth;

    protected static final VoxelShape NORTH_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
    protected static final VoxelShape SOUTH_SHAPE = Block.box(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape EAST_SHAPE = Block.box(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape WEST_SHAPE = Block.box(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);

    public MapCodec<? extends LargeDoorBlock> codec() {
        return CODEC;
    }

    public LargeDoorBlock(Properties settings) {
        super(settings.noOcclusion());
        doorHeight = 1;
        doorWidth = 1;
        this.registerDefaultState((((this.stateDefinition.any()).setValue(HORIZONTAL_FACING, Direction.NORTH)).setValue(getPart(), 0)).setValue(OPEN, false).setValue(HINGE, DoorHingeSide.LEFT));
    }

    //TODO improve this door to door invisibility
    protected boolean skipRendering(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.is(this) || super.skipRendering(state, stateFrom, direction);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(getPart(), HORIZONTAL_FACING, OPEN, HINGE);
    }

    //Get the origin of the door based on the state and pos
    private BlockPos getOrigin(BlockPos pos, BlockState state){
        BlockPos blockPos;
        int part = state.getValue(getPart());

        int column = part / doorHeight;
        int line = part % doorHeight;

        if (state.getValue(OPEN)) {
            blockPos = pos.relative(state.getValue(HORIZONTAL_FACING).getClockWise().getClockWise(), column);
            for (int i = 0; i < line; i++) {
                blockPos = blockPos.below();
            }
        } else {
            if(state.getValue(HINGE) == DoorHingeSide.LEFT) {
                blockPos = pos.relative(state.getValue(HORIZONTAL_FACING).getCounterClockWise(), column);
            }else{
                blockPos = pos.relative(state.getValue(HORIZONTAL_FACING).getClockWise(), column);
            }
            for (int i = 0; i < line; i++) {
                blockPos = blockPos.below();
            }
        }

       return blockPos;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction direction = ctx.getHorizontalDirection();
        BlockPos blockPos = ctx.getClickedPos();
        BlockPos blockPos1 = ctx.getClickedPos();
        Level world = ctx.getLevel();
        boolean canPlace = true;

        //Check space for placement
        for (int j = 0; j < doorWidth; j++){
            for (int k = 0; k < doorHeight; k++) {
                if(!world.getBlockState(blockPos).canBeReplaced(ctx)){
                    canPlace = false;
                }
                blockPos = blockPos.above();
            }
            if(getHinge(ctx) == DoorHingeSide.LEFT) {
                blockPos = blockPos1.relative(direction.getClockWise(), j + 1);
            }else{
                blockPos = blockPos1.relative(direction.getCounterClockWise(), j + 1);
            }
        }

        if(canPlace){
            return this.defaultBlockState().setValue(HORIZONTAL_FACING, direction).setValue(OPEN, false).setValue(getPart(), 0).setValue(HINGE, this.getHinge(ctx));
        } else{
            return null;
        }
    }

    private DoorHingeSide getHinge(BlockPlaceContext ctx) {
        BlockGetter blockView = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();
        Direction direction = ctx.getHorizontalDirection();
        BlockPos blockPos2 = blockPos.above();
        Direction direction2 = direction.getCounterClockWise();
        BlockPos blockPos3 = blockPos.relative(direction2);
        BlockState blockState = blockView.getBlockState(blockPos3);
        BlockPos blockPos4 = blockPos2.relative(direction2);
        BlockState blockState2 = blockView.getBlockState(blockPos4);
        Direction direction3 = direction.getClockWise();
        BlockPos blockPos5 = blockPos.relative(direction3);
        BlockState blockState3 = blockView.getBlockState(blockPos5);
        BlockPos blockPos6 = blockPos2.relative(direction3);
        BlockState blockState4 = blockView.getBlockState(blockPos6);
        int i = (blockState.isCollisionShapeFullBlock(blockView, blockPos3) ? -1 : 0) + (blockState2.isCollisionShapeFullBlock(blockView, blockPos4) ? -1 : 0) + (blockState3.isCollisionShapeFullBlock(blockView, blockPos5) ? 1 : 0) + (blockState4.isCollisionShapeFullBlock(blockView, blockPos6) ? 1 : 0);
        int j = direction.getStepX();
        int k = direction.getStepZ();
        Vec3 vec3d = ctx.getClickLocation();
        double d = vec3d.x - (double)blockPos.getX();
        double e = vec3d.z - (double)blockPos.getZ();
        return (j >= 0 || !(e < 0.5)) && (j <= 0 || !(e > 0.5)) && (k >= 0 || !(d > 0.5)) && (k <= 0 || !(d < 0.5)) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
    }

    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClientSide){
            BlockPos blockPos = pos;
            //Places all additional blocks after placement is checked
            for (int i = 0; i < doorWidth; i++){
                int partIndex = doorHeight * i;
                for (int j = 0; j < doorHeight; j++) {
                    world.setBlock(blockPos, (BlockState)state.setValue(getPart(), partIndex), 3);
                    blockPos = blockPos.above();
                    partIndex++;
                }
                if(state.getValue(HINGE) == DoorHingeSide.LEFT) {
                    blockPos = pos.relative((Direction)state.getValue(HORIZONTAL_FACING).getClockWise(), i + 1);
                }else {
                    blockPos = pos.relative((Direction) state.getValue(HORIZONTAL_FACING).getCounterClockWise(), i + 1);
                }
            }
        }
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        BlockPos blockPos = getOrigin(pos, state);
        BlockPos blockPos1 = blockPos;

        //Breaks all blocks based on origin
        for (int j = 0; j < doorWidth; j++){
            for (int k = 0; k < doorHeight; k++) {
                world.destroyBlock(blockPos, j == 0 && k == 0 && !player.isCreative());
                blockPos = blockPos.above();
            }
            if(state.getValue(OPEN)){
                blockPos = blockPos1.relative((Direction)state.getValue(HORIZONTAL_FACING), j + 1);
            } else if(state.getValue(HINGE) == DoorHingeSide.LEFT) {
                blockPos = blockPos1.relative((Direction)state.getValue(HORIZONTAL_FACING).getClockWise(), j + 1);
            }else {
                blockPos = blockPos1.relative((Direction)state.getValue(HORIZONTAL_FACING).getCounterClockWise(), j + 1);
            }
        }

        return super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    protected void onExplosionHit(BlockState state, Level world, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> stackMerger) {
        BlockPos blockPos = getOrigin(pos, state);
        BlockPos blockPos1 = blockPos;

        //Breaks all blocks based on origin
        for (int j = 0; j < doorWidth; j++){
            for (int k = 0; k < doorHeight; k++) {
                world.destroyBlock(blockPos, j == 0 && k == 0);
                blockPos = blockPos.above();
            }
            if(state.getValue(OPEN)){
                blockPos = blockPos1.relative((Direction)state.getValue(HORIZONTAL_FACING), j + 1);
            } else if(state.getValue(HINGE) == DoorHingeSide.LEFT) {
                blockPos = blockPos1.relative((Direction)state.getValue(HORIZONTAL_FACING).getClockWise(), j + 1);
            }else {
                blockPos = blockPos1.relative((Direction)state.getValue(HORIZONTAL_FACING).getCounterClockWise(), j + 1);
            }
        }

        super.onExplosionHit(state, world, pos, explosion, stackMerger);
    }

    //Check space for door opening/closing
    private boolean canToggle(BlockPos origin, BlockState state, Level world) {
        BlockPos blockPos = origin;
        BlockPos blockPos1 = blockPos;
        for (int j = 0; j < doorWidth; j++){
            for (int k = 0; k < doorHeight; k++) {
                if(j != 0){
                    if(!world.getBlockState(blockPos).canBeReplaced()){
                        return false;
                    }
                }
                blockPos = blockPos.above();
            }
            if(state.getValue(OPEN)){
                if(state.getValue(HINGE) == DoorHingeSide.LEFT) {
                    blockPos = blockPos1.relative((Direction)state.getValue(HORIZONTAL_FACING).getClockWise(), j + 1);
                } else {
                    blockPos = blockPos1.relative((Direction) state.getValue(HORIZONTAL_FACING).getCounterClockWise(), j + 1);
                }
            } else {
                blockPos = blockPos1.relative((Direction)state.getValue(HORIZONTAL_FACING), j + 1);
            }
        }
        return true;
    }

    //Door opening
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        BlockPos blockPos = getOrigin(pos, state);
        BlockPos blockPos1 = blockPos;

        if(canToggle(blockPos,state,world)){
            //Remove blocks
            for (int j = 0; j < doorWidth; j++){
                for (int k = 0; k < doorHeight; k++) {
                    world.setBlock(blockPos, (BlockState)Blocks.AIR.defaultBlockState(), 3);
                    blockPos = blockPos.above();
                }
                if(state.getValue(OPEN)) {
                    blockPos = blockPos1.relative((Direction)state.getValue(HORIZONTAL_FACING), j + 1);
                }else {
                    if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
                        blockPos = blockPos1.relative((Direction)state.getValue(HORIZONTAL_FACING).getClockWise(), j + 1);
                    }else{
                        blockPos = blockPos1.relative((Direction)state.getValue(HORIZONTAL_FACING).getCounterClockWise(), j + 1);
                    }
                }
            }

            blockPos = blockPos1;

            //Place Blocks
            for (int i = 0; i < doorWidth; i++){
                int partIndex = doorHeight * i;
                for (int j = 0; j < doorHeight; j++) {
                    if(state.getValue(OPEN)){
                        world.setBlock(blockPos, state.setValue(OPEN, false).setValue(getPart(), partIndex), 3);
                    } else {
                        world.setBlock(blockPos, state.setValue(OPEN, true).setValue(getPart(), partIndex), 3);
                    }
                    blockPos = blockPos.above();
                    partIndex++;
                }
                if(!state.getValue(OPEN)){
                    blockPos = blockPos1.relative((Direction)state.getValue(HORIZONTAL_FACING), i + 1);
                }else{
                    if(state.getValue(HINGE) == DoorHingeSide.LEFT){
                        blockPos = blockPos1.relative((Direction)state.getValue(HORIZONTAL_FACING).getClockWise(), i + 1);

                    } else {
                        blockPos = blockPos1.relative((Direction)state.getValue(HORIZONTAL_FACING).getCounterClockWise(), i + 1);

                    }
                }
            }
            this.playOpenCloseSound(player, world, pos, (Boolean)state.getValue(OPEN));
            world.gameEvent(player, this.isOpen(state) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        } else {
            player.displayClientMessage(Component.translatable("alert.%s.large_door.blocked".formatted(MiddleEarth.MOD_ID)), true);
        }

        return InteractionResult.SUCCESS;
    }

    private void playOpenCloseSound(@Nullable Entity entity, Level world, BlockPos pos, boolean open) {
        world.playSound(entity, pos, open ? SoundEvents.WOODEN_DOOR_OPEN : SoundEvents.WOODEN_DOOR_CLOSE, SoundSource.BLOCKS, 1.5F, world.getRandom().nextFloat() * 0.1F + 0.4F);
    }

    public boolean isOpen(BlockState state) {
        return (Boolean)state.getValue(OPEN);
    }

    protected BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState)state.setValue(HORIZONTAL_FACING, rotation.rotate((Direction)state.getValue(HORIZONTAL_FACING)));
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        return mirror == Mirror.NONE ? state : (BlockState)state.rotate(mirror.getRotation((Direction)state.getValue(HORIZONTAL_FACING)));
    }

    public int getDoorHeight() {
        return doorHeight;
    }

    public int getDoorWidth() {
        return doorWidth;
    }

    public IntegerProperty getPart() {
        return PART;
    }

    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return switch (type) {
            case LAND, AIR -> (Boolean) state.getValue(OPEN);
            case WATER -> false;
            default -> throw new MatchException((String) null, (Throwable) null);
        };
    }

    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction direction = (Direction) state.getValue(HORIZONTAL_FACING);
        VoxelShape var10000 = null;
        if (state.getValue(HINGE) == DoorHingeSide.LEFT) {
            if (!state.getValue(OPEN)) {
                return switch (direction) {
                    case WEST -> EAST_SHAPE;
                    case EAST -> WEST_SHAPE;
                    case SOUTH -> NORTH_SHAPE;
                    default -> SOUTH_SHAPE;
                };
            } else {
                return switch (direction) {
                    case WEST -> SOUTH_SHAPE;
                    case EAST -> NORTH_SHAPE;
                    case SOUTH -> EAST_SHAPE;
                    default -> WEST_SHAPE;
                };
            }
        } else {
            if (!state.getValue(OPEN)) {
                return switch (direction) {
                    case WEST -> EAST_SHAPE;
                    case EAST -> WEST_SHAPE;
                    case SOUTH -> NORTH_SHAPE;
                    default -> SOUTH_SHAPE;
                };
            } else {
                return switch (direction) {
                    case WEST -> NORTH_SHAPE;
                    case EAST -> SOUTH_SHAPE;
                    case SOUTH -> WEST_SHAPE;
                    default -> EAST_SHAPE;
                };
            }
        }
    }
}
