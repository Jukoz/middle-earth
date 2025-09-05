
package net.sevenstars.middleearth.block.special.shelobiteeggs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.ScheduledTickView;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaEntity;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class ShelobiteLarvaEggBlock extends AbstractShelobiteLarvaEgg {
    private static final EnumProperty<Direction> FACING;

    public ShelobiteLarvaEggBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.EAST));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.appendProperties(builder);
    }
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing());
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return world.getBlockState(blockPos).isSolidBlock(world, blockPos);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, net.minecraft.util.math.random.Random random) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(!entity.collidedSoftly && !entity.getType().isIn(EntityTypeTags.ARTHROPOD)){
            breakEgg(world, pos, state);
            super.onSteppedOn(world, pos, state, entity);
        }
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        if(!entity.collidedSoftly && !entity.getType().isIn(EntityTypeTags.ARTHROPOD)) {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
            breakEgg(world, pos, state);
        }

    }

    public static void breakEgg(World world, BlockPos pos, BlockState state) {
        world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.random.nextFloat() * 0.2F);
        world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(state));
        world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
        Random random = new Random();
        int amountOfSpider = random.nextInt(1, 4);
        for(int i = 0; i < amountOfSpider; i++)
            SpawnSpider(pos, world);
        world.removeBlock(pos, false);
    }

    public static void SpawnSpider(BlockPos pos, World world){
        ShelobiteLarvaEntity entity = new ShelobiteLarvaEntity(ModEntities.SHELOBITE_LARVA, world);
        entity.age = 0;
        entity.refreshPositionAndAngles(pos, 0, 0);
        if(world instanceof ServerWorldAccess serverWorldAccess) {
            entity.initialize(serverWorldAccess, serverWorldAccess.getLocalDifficulty(pos), SpawnReason.NATURAL, null);
        }
        world.spawnEntity(entity);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.union(
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0));
    }

    static {
        FACING = Properties.HORIZONTAL_FACING;
    }
}
