package net.sevenstars.middleearth.block.special.fire_of_orthanc;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.item.DecorativeItemsME;
import org.jetbrains.annotations.Nullable;

public class FireOfOrthancBlock extends Block {
    public static final MapCodec<FireOfOrthancBlock> CODEC = simpleCodec(FireOfOrthancBlock::new);
    public static final VoxelShape OUTLINE_SHAPE = Block.box(2, 0, 2, 14, 12, 14);

    public FireOfOrthancBlock(Properties settings) {
        super(settings);
    }

    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!stack.is(DecorativeItemsME.TORCH_OF_ORTHANC)) {
            return super.useItemOn(stack, state, world, pos, player, hand, hit);
        } else {
            explode(world, pos, player);
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
            Item item = stack.getItem();
            if (!player.hasInfiniteMaterials()) {
                if (stack.is(Items.FLINT_AND_STEEL)) {
                    stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                } else {
                    stack.shrink(1);
                }
            }
            player.awardStat(Stats.ITEM_USED.get(item));
            return ItemInteractionResult.SUCCESS;
        }
    }

    public void explode(Level world, BlockPos pos, @Nullable LivingEntity igniter) {
        if (!world.isClientSide) {
            FireOfOrthancEntity fireOfOrthancEntity = new FireOfOrthancEntity(world, (double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5, igniter, true);
            world.addFreshEntity(fireOfOrthancEntity);
            world.gameEvent(igniter, GameEvent.PRIME_FUSE, pos);
        } else {
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.25F, 0.8F);
        }
    }

    public void onDestroyedByExplosion(Level world, BlockPos pos, Explosion explosion) {
        if (!world.isClientSide) {
            FireOfOrthancEntity fireOfOrthancEntity = new FireOfOrthancEntity(world, (double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5, explosion.getIndirectSourceEntity(), false);
            int i = fireOfOrthancEntity.getFuse();
            fireOfOrthancEntity.setFuse((short)(world.random.nextInt(i / 2) + i / 2));
            world.addFreshEntity(fireOfOrthancEntity);
        }
    }

    public boolean dropFromExplosion(Explosion explosion) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return OUTLINE_SHAPE;
    }
}
