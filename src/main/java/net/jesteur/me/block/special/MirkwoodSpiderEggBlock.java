package net.jesteur.me.block.special;

import net.jesteur.me.entity.ModEntities;
import net.jesteur.me.entity.spider.MirkwoodSpiderEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class MirkwoodSpiderEggBlock extends Block {
    private static final DirectionProperty FACING;

    public MirkwoodSpiderEggBlock(Settings settings) {
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
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return attachedDirection(state).getOpposite() == direction && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    protected Direction attachedDirection(BlockState state) {
        return state.get(FACING);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(entity.getType() != ModEntities.MIRKWOOD_SPIDER){
            this.breakEgg(world, pos, state);
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.onLandedUpon(world, state, pos, entity, fallDistance);
        breakEgg(world, pos, state);
    }

    private void breakEgg(World world, BlockPos pos, BlockState state) {
        world.playSound((PlayerEntity)null, pos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.random.nextFloat() * 0.2F);
        world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(state));
        world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
        Random random = new Random();
        int amountOfSpider = random.nextInt(1, 4);
        for(int i = 0; i < amountOfSpider; i++)
            SpawnSpider(pos, world);
        world.removeBlock(pos, false);
    }

    private void SpawnSpider(BlockPos pos, World world){
        MirkwoodSpiderEntity entity = new MirkwoodSpiderEntity(ModEntities.MIRKWOOD_SPIDER, world);
        entity.age = 0;
        entity.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
        world.spawnEntity(entity);
    }

    static {
        FACING = Properties.FACING;
    }
}

