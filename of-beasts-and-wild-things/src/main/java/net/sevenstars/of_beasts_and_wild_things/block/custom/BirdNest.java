package net.sevenstars.of_beasts_and_wild_things.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.api.entity.ai.brain.MemoryModulesAPI;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntity;
import net.sevenstars.of_beasts_and_wild_things.item.ItemsWT;

import java.util.List;
import java.util.Random;

public class BirdNest extends Block {

    public static final IntegerProperty NEST_LEVEL = IntegerProperty.create("nest_level", 0, 2);

    public BirdNest(Properties settings) {
        super(settings);
        this.registerDefaultState((this.stateDefinition.any()).setValue(NEST_LEVEL, 0));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        Random random = new Random();
        if(player.getItemInHand(player.getUsedItemHand()).isEmpty()){
            int level = state.getValue(NEST_LEVEL);
            if (level > 0 && !world.isClientSide){
                if (level == 1){
                    player.addItem(new ItemStack(ItemsWT.SWAN_EGG));
                    player.addItem(new ItemStack(ItemsWT.SWAN_FEATHER, random.nextInt(3)));
                    world.setBlockAndUpdate(pos, state.setValue(NEST_LEVEL, 0));
                } else if (level == 2){
                    player.addItem(new ItemStack(ItemsWT.SWAN_EGG, 2));
                    player.addItem(new ItemStack(ItemsWT.SWAN_FEATHER, random.nextInt((3 - 1) + 1) + 1));
                    world.setBlockAndUpdate(pos, state.setValue(NEST_LEVEL, 0));
                }

                player.swing(player.getUsedItemHand());

                //TODO to test in multiplayer
                world.playSound(null , pos, SoundEvents.CHICKEN_EGG, SoundSource.BLOCKS, 1.0F, 1.0F);

                angerSwans(world, pos, player);
            }
            
            return InteractionResult.SUCCESS;
        } else {
            return super.useWithoutItem(state, world, pos, player, hit);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide){
            angerSwans(world, pos, player);
        }
        return super.playerWillDestroy(world, pos, state, player);
    }

    private void angerSwans(Level world, BlockPos pos, Player player) {
        if(!world.isClientSide && !player.hasInfiniteMaterials()) {
            AABB box = new AABB(pos.getX() + 10, pos.getY() + 10, pos.getZ() + 10,
                    pos.getX() - 10, pos.getY() - 10, pos.getZ() - 10);

            List<SwanEntity> swans = world.getEntitiesOfClass(SwanEntity.class, box, Entity::isAlive);

            swans.forEach(swan -> {
                swan.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, player);
                swan.getBrain().eraseMemory(MemoryModulesAPI.DEFENDING_HOME);
            });
        }
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return BirdNest.simpleCodec(BirdNest::new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NEST_LEVEL);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(3, 0, 3, 13, 6, 13);
    }
}
