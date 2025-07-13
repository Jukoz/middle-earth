package net.sevenstars.of_beasts_and_wild_things.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntity;
import net.sevenstars.of_beasts_and_wild_things.item.ModItems;

import java.util.List;
import java.util.Random;

public class BirdNest extends Block {

    public static final IntProperty NEST_LEVEL = IntProperty.of("nest_level", 0, 2);

    public BirdNest(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(NEST_LEVEL, 0));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Random random = new Random();
        if (!world.isClient){
            if(player.getStackInHand(player.getActiveHand()).isEmpty()){
                int level = state.get(NEST_LEVEL);
                if (level == 1){
                    player.giveItemStack(new ItemStack(ModItems.SWAN_EGG));
                    player.giveItemStack(new ItemStack(ModItems.SWAN_FEATHER, random.nextInt(3)));
                    world.setBlockState(pos, state.with(NEST_LEVEL, 0));
                } else if (level == 2){
                    player.giveItemStack(new ItemStack(ModItems.SWAN_EGG, 2));
                    player.giveItemStack(new ItemStack(ModItems.SWAN_FEATHER, random.nextInt((3 - 1) + 1) + 1));
                    world.setBlockState(pos, state.with(NEST_LEVEL, 0));
                }

                return ActionResult.SUCCESS;
            } else {
                return super.onUse(state, world, pos, player, hit);
            }
        } else {
            return super.onUse(state, world, pos, player, hit);
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient){
            Box box = new Box(pos.getX() + 10, pos.getY() + 10, pos.getZ() + 10,
                    pos.getX() - 10, pos.getY() - 10, pos.getZ() - 10);

            List<SwanEntity> swans = world.getEntitiesByClass(SwanEntity.class, box, Entity::isAlive);

            swans.forEach(swan -> {
                swan.getBrain().remember(MemoryModuleType.ATTACK_TARGET, player);
            });
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return BirdNest.createCodec(BirdNest::new);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NEST_LEVEL);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(3, 0, 3, 13, 6, 13);
    }
}
