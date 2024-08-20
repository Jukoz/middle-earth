package net.jukoz.me.block.special;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class LargeDoorBlock extends Block {

    public static final IntProperty PART = IntProperty.of("part", 0, 127);
    public static final DirectionProperty HORIZONTAL_FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final EnumProperty<DoorHinge> HINGE = Properties.DOOR_HINGE;

    public static final MapCodec<LargeDoorBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance
            .group(Codec.INT.fieldOf("door_height").forGetter(LargeDoorBlock::getDoorHeight),
                    Codec.INT.fieldOf("door_width").forGetter(LargeDoorBlock::getDoorWidth),
                    createSettingsCodec()).apply(instance, LargeDoorBlock::new));

    private final int doorHeight;
    private final int doorWidth;

    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);

    public MapCodec<? extends LargeDoorBlock> getCodec() {
        return CODEC;
    }

    public LargeDoorBlock(int height, int width, Settings settings) {
        super(settings);
        doorHeight = height;
        doorWidth = width;
        this.setDefaultState((((this.stateManager.getDefaultState()).with(HORIZONTAL_FACING, Direction.NORTH)).with(getPart(), 0)).with(OPEN, false).with(HINGE, DoorHinge.LEFT));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(getPart(), HORIZONTAL_FACING, OPEN, HINGE);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing();
        BlockPos blockPos = ctx.getBlockPos();
        BlockPos blockPos1 = ctx.getBlockPos();
        World world = ctx.getWorld();
        boolean canPlace = true;

        if(getHinge(ctx) == DoorHinge.LEFT){
            for (int j = 0; j < doorWidth; j++){
                for (int k = 0; k < doorHeight; k++) {
                    if(!world.getBlockState(blockPos).canReplace(ctx)){
                        canPlace = false;
                    }
                    blockPos = blockPos.up();
                }
                blockPos = blockPos1.offset(direction.rotateYClockwise(), j + 1);
            }

            if(canPlace){
                return this.getDefaultState().with(HORIZONTAL_FACING, direction).with(OPEN, false).with(getPart(), 0).with(HINGE, DoorHinge.LEFT).with(HINGE, this.getHinge(ctx));
            } else{
                return null;
            }
        } else {
            for (int j = 0; j < doorWidth; j++){
                for (int k = 0; k < doorHeight; k++) {
                    if(!world.getBlockState(blockPos).canReplace(ctx)){
                        canPlace = false;
                    }
                    blockPos = blockPos.up();
                }
                blockPos = blockPos1.offset(direction.rotateYCounterclockwise(), j + 1);
            }

            if(canPlace){
                return this.getDefaultState().with(HORIZONTAL_FACING, direction).with(OPEN, false).with(getPart(), 0).with(HINGE, DoorHinge.LEFT).with(HINGE, this.getHinge(ctx));
            } else{
                return null;
            }
        }

    }

    private DoorHinge getHinge(ItemPlacementContext ctx) {
        BlockView blockView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        Direction direction = ctx.getHorizontalPlayerFacing();
        BlockPos blockPos2 = blockPos.up();
        Direction direction2 = direction.rotateYCounterclockwise();
        BlockPos blockPos3 = blockPos.offset(direction2);
        BlockState blockState = blockView.getBlockState(blockPos3);
        BlockPos blockPos4 = blockPos2.offset(direction2);
        BlockState blockState2 = blockView.getBlockState(blockPos4);
        Direction direction3 = direction.rotateYClockwise();
        BlockPos blockPos5 = blockPos.offset(direction3);
        BlockState blockState3 = blockView.getBlockState(blockPos5);
        BlockPos blockPos6 = blockPos2.offset(direction3);
        BlockState blockState4 = blockView.getBlockState(blockPos6);
        int i = (blockState.isFullCube(blockView, blockPos3) ? -1 : 0) + (blockState2.isFullCube(blockView, blockPos4) ? -1 : 0) + (blockState3.isFullCube(blockView, blockPos5) ? 1 : 0) + (blockState4.isFullCube(blockView, blockPos6) ? 1 : 0);
        int j = direction.getOffsetX();
        int k = direction.getOffsetZ();
        Vec3d vec3d = ctx.getHitPos();
        double d = vec3d.x - (double)blockPos.getX();
        double e = vec3d.z - (double)blockPos.getZ();
        return (j >= 0 || !(e < 0.5)) && (j <= 0 || !(e > 0.5)) && (k >= 0 || !(d > 0.5)) && (k <= 0 || !(d < 0.5)) ? DoorHinge.LEFT : DoorHinge.RIGHT;
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient){
            BlockPos blockPos = pos;
            if(state.get(HINGE) == DoorHinge.LEFT){
                for (int i = 0; i < doorWidth; i++){
                    int partIndex = doorHeight * i;
                    for (int j = 0; j < doorHeight; j++) {
                        world.setBlockState(blockPos, (BlockState)state.with(getPart(), partIndex), 3);
                        blockPos = blockPos.up();
                        partIndex++;
                    }
                    blockPos = pos.offset((Direction)state.get(HORIZONTAL_FACING).rotateYClockwise(), i + 1);
                }
            } else {
                for (int i = 0; i < doorWidth; i++){
                    int partIndex = doorHeight * i;
                    for (int j = 0; j < doorHeight; j++) {
                        world.setBlockState(blockPos, (BlockState)state.with(getPart(), partIndex).with(HINGE, DoorHinge.RIGHT), 3);
                        blockPos = blockPos.up();
                        partIndex++;
                    }
                    blockPos = pos.offset((Direction)state.get(HORIZONTAL_FACING).rotateYCounterclockwise(), i + 1);
                }
            }
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        int part = state.get(getPart());
        int column = part / doorHeight;
        int line = part % doorHeight;

        BlockPos blockPos;
        BlockPos blockPos1;
        if(state.get(HINGE) == DoorHinge.LEFT){
            if(state.get(OPEN)){
                blockPos = pos.offset(state.get(HORIZONTAL_FACING).rotateYClockwise().rotateYClockwise(), column);
                for(int i = 0; i < line; i++){
                    blockPos = blockPos.down();
                }
                blockPos1 = blockPos;
            } else {
                blockPos = pos.offset(state.get(HORIZONTAL_FACING).rotateYCounterclockwise(), column);
                for(int i = 0; i < line; i++){
                    blockPos = blockPos.down();
                }
                blockPos1 = blockPos;
            }
            if(state.get(OPEN)) {
                for (int j = 0; j < doorWidth; j++){
                    for (int k = 0; k < doorHeight; k++) {
                        if(j == 0 && k == 0 && !player.isCreative()){
                            world.breakBlock(blockPos, true);
                        } else {
                            world.setBlockState(blockPos, (BlockState)Blocks.AIR.getDefaultState(), 3);
                        }
                        blockPos = blockPos.up();
                    }
                    blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING), j + 1);
                }
            }else {
                for (int j = 0; j < doorWidth; j++){
                    for (int k = 0; k < doorHeight; k++) {
                        if(j == 0 && k == 0 && !player.isCreative()){
                            world.breakBlock(blockPos, true);
                        } else {
                            world.setBlockState(blockPos, (BlockState)Blocks.AIR.getDefaultState(), 3);
                        }
                        blockPos = blockPos.up();
                    }
                    blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYClockwise(), j + 1);
                }
            }
        } else {
            if(state.get(OPEN)){
                blockPos = pos.offset(state.get(HORIZONTAL_FACING).rotateYCounterclockwise().rotateYCounterclockwise(), column);
                for(int i = 0; i < line; i++){
                    blockPos = blockPos.down();
                }
                blockPos1 = blockPos;
            } else {
                blockPos = pos.offset(state.get(HORIZONTAL_FACING).rotateYClockwise(), column);
                for(int i = 0; i < line; i++){
                    blockPos = blockPos.down();
                }
                blockPos1 = blockPos;
            }
            if(state.get(OPEN)) {
                for (int j = 0; j < doorWidth; j++){
                    for (int k = 0; k < doorHeight; k++) {
                        if(j == 0 && k == 0 && !player.isCreative()){
                            world.breakBlock(blockPos, true);
                        } else {
                            world.setBlockState(blockPos, (BlockState)Blocks.AIR.getDefaultState(), 3);
                        }
                        blockPos = blockPos.up();
                    }
                    blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING), j + 1);
                }
            }else {
                for (int j = 0; j < doorWidth; j++){
                    for (int k = 0; k < doorHeight; k++) {
                        if(j == 0 && k == 0 && !player.isCreative()){
                            world.breakBlock(blockPos, true);
                        } else {
                            world.setBlockState(blockPos, (BlockState)Blocks.AIR.getDefaultState(), 3);
                        }
                        blockPos = blockPos.up();
                    }
                    blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYCounterclockwise(), j + 1);
                }
            }
        }

        return super.onBreak(world, pos, state, player);
    }

    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        int part = state.get(getPart());
        int column = part / doorHeight;
        int line = part % doorHeight;

        BlockPos blockPos;
        BlockPos blockPos1;

        if (state.get(HINGE) == DoorHinge.LEFT){
            if(state.get(OPEN)){
                blockPos = pos.offset(state.get(HORIZONTAL_FACING).rotateYClockwise().rotateYClockwise(), column);
                for(int i = 0; i < line; i++){
                    blockPos = blockPos.down();
                }
                blockPos1 = blockPos;

                boolean canClose = true;

                for (int j = 0; j < doorWidth; j++){
                    for (int k = 0; k < doorHeight; k++) {
                        if(j != 0){
                            if(!world.getBlockState(blockPos).isReplaceable()){
                                canClose = false;
                            }
                        }
                        blockPos = blockPos.up();
                    }
                    blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYClockwise(), j + 1);
                }

                blockPos = blockPos1;


                if(canClose){
                    for (int j = 0; j < doorWidth; j++){
                        for (int k = 0; k < doorHeight; k++) {
                            world.setBlockState(blockPos, (BlockState)Blocks.AIR.getDefaultState(), 3);
                            blockPos = blockPos.up();
                        }
                        blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING), j + 1);
                    }

                    blockPos = blockPos1;

                    for (int i = 0; i < doorWidth; i++){
                        int partIndex = doorHeight * i;
                        for (int j = 0; j < doorHeight; j++) {
                            world.setBlockState(blockPos, state.with(OPEN, false).with(getPart(), partIndex), 3);
                            blockPos = blockPos.up();
                            partIndex++;
                        }
                        blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYClockwise(), i + 1);
                    }
                } else {
                    player.sendMessage(Text.translatable("block.me.large_door.blocked"), true);
                }
            }else {
                blockPos = pos.offset(state.get(HORIZONTAL_FACING).rotateYCounterclockwise(), column);
                for(int i = 0; i < line; i++){
                    blockPos = blockPos.down();
                }
                blockPos1 = blockPos;

                boolean canOpen = true;

                for (int j = 0; j < doorWidth; j++){
                    for (int k = 0; k < doorHeight; k++) {
                        if(j != 0){
                            if(!world.getBlockState(blockPos).isReplaceable()){
                                canOpen = false;
                            }
                        }
                        blockPos = blockPos.up();
                    }
                    blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING), j + 1);
                }

                blockPos = blockPos1;

                if(canOpen) {
                    for (int j = 0; j < doorWidth; j++){
                        for (int k = 0; k < doorHeight; k++) {
                            world.setBlockState(blockPos, (BlockState)Blocks.AIR.getDefaultState(), 3);
                            blockPos = blockPos.up();
                        }
                        blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYClockwise(), j + 1);
                    }

                    blockPos = blockPos1;

                    for (int j = 0; j < doorWidth; j++){
                        int partIndex = doorHeight * j;
                        for (int k = 0; k < doorHeight; k++) {
                            world.setBlockState(blockPos, state.with(OPEN, true).with(getPart(), partIndex), 3);
                            blockPos = blockPos.up();
                            partIndex++;
                        }
                        blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING), j + 1);
                    }
                }else {
                    player.sendMessage(Text.translatable("block.me.large_door.blocked"), true);
                }
            }
        }else {
            if(state.get(OPEN)){
                blockPos = pos.offset(state.get(HORIZONTAL_FACING).rotateYCounterclockwise().rotateYCounterclockwise(), column);
                for(int i = 0; i < line; i++){
                    blockPos = blockPos.down();
                }
                blockPos1 = blockPos;

                boolean canClose = true;

                for (int j = 0; j < doorWidth; j++){
                    for (int k = 0; k < doorHeight; k++) {
                        if(j != 0){
                            if(!world.getBlockState(blockPos).isReplaceable()){
                                canClose = false;
                            }
                        }
                        blockPos = blockPos.up();
                    }
                    blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYCounterclockwise(), j + 1);
                }

                blockPos = blockPos1;


                if(canClose){
                    for (int j = 0; j < doorWidth; j++){
                        for (int k = 0; k < doorHeight; k++) {
                            world.setBlockState(blockPos, (BlockState)Blocks.AIR.getDefaultState(), 3);
                            blockPos = blockPos.up();
                        }
                        blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING), j + 1);
                    }

                    blockPos = blockPos1;

                    for (int i = 0; i < doorWidth; i++){
                        int partIndex = doorHeight * i;
                        for (int j = 0; j < doorHeight; j++) {
                            world.setBlockState(blockPos, state.with(OPEN, false).with(getPart(), partIndex), 3);
                            blockPos = blockPos.up();
                            partIndex++;
                        }
                        blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYCounterclockwise(), i + 1);
                    }
                } else {
                    player.sendMessage(Text.translatable("block.me.large_door.blocked"), true);
                }
            }else {
                blockPos = pos.offset(state.get(HORIZONTAL_FACING).rotateYClockwise(), column);
                for(int i = 0; i < line; i++){
                    blockPos = blockPos.down();
                }
                blockPos1 = blockPos;

                boolean canOpen = true;

                for (int j = 0; j < doorWidth; j++){
                    for (int k = 0; k < doorHeight; k++) {
                        if(j != 0){
                            if(!world.getBlockState(blockPos).isReplaceable()){
                                canOpen = false;
                            }
                        }
                        blockPos = blockPos.up();
                    }
                    blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING), j + 1);
                }

                blockPos = blockPos1;

                if(canOpen) {
                    for (int j = 0; j < doorWidth; j++){
                        for (int k = 0; k < doorHeight; k++) {
                            world.setBlockState(blockPos, (BlockState)Blocks.AIR.getDefaultState(), 3);
                            blockPos = blockPos.up();
                        }
                        blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYCounterclockwise(), j + 1);
                    }

                    blockPos = blockPos1;

                    for (int j = 0; j < doorWidth; j++){
                        int partIndex = doorHeight * j;
                        for (int k = 0; k < doorHeight; k++) {
                            world.setBlockState(blockPos, state.with(OPEN, true).with(getPart(), partIndex), 3);
                            blockPos = blockPos.up();
                            partIndex++;
                        }
                        blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING), j + 1);
                    }
                }else {
                    player.sendMessage(Text.translatable("block.me.large_door.blocked"), true);
                }
            }
        }
        this.playOpenCloseSound(player, world, pos, (Boolean)state.get(OPEN));
        world.emitGameEvent(player, this.isOpen(state) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        return ActionResult.success(world.isClient);
    }

    private void playOpenCloseSound(@Nullable Entity entity, World world, BlockPos pos, boolean open) {
        world.playSound(entity, pos, open ? SoundEvents.BLOCK_WOODEN_DOOR_OPEN : SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundCategory.BLOCKS, 1.5F, world.getRandom().nextFloat() * 0.1F + 0.4F);
    }

    public boolean isOpen(BlockState state) {
        return (Boolean)state.get(OPEN);
    }

    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(HORIZONTAL_FACING, rotation.rotate((Direction)state.get(HORIZONTAL_FACING)));
    }

    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return mirror == BlockMirror.NONE ? state : (BlockState)state.rotate(mirror.getRotation((Direction)state.get(HORIZONTAL_FACING)));
    }

    public int getDoorHeight() {
        return doorHeight;
    }

    public int getDoorWidth() {
        return doorWidth;
    }

    public IntProperty getPart() {
        return PART;
    }

    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        boolean var10000;
        switch (type) {
            case LAND:
            case AIR:
                var10000 = (Boolean)state.get(OPEN);
                break;
            case WATER:
                var10000 = false;
                break;
            default:
                throw new MatchException((String)null, (Throwable)null);
        }

        return var10000;
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = (Direction) state.get(HORIZONTAL_FACING);
        VoxelShape var10000 = null;
        if (state.get(HINGE) == DoorHinge.LEFT) {
            if (!state.get(OPEN)) {
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
            if (!state.get(OPEN)) {
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
