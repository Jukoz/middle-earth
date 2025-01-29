package net.sevenstars.middleearth.block.special.fire_of_orthanc;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.ActionResult;
import net.sevenstars.middleearth.item.ModDecorativeItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class FireOfOrthancBlock extends Block {
    public static final MapCodec<FireOfOrthancBlock> CODEC = createCodec(FireOfOrthancBlock::new);
    public static final VoxelShape OUTLINE_SHAPE = Block.createCuboidShape(2, 0, 2, 14, 12, 14);

    public FireOfOrthancBlock(Settings settings) {
        super(settings);
    }

    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!stack.isOf(ModDecorativeItems.TORCH_OF_ORTHANC)) {
            return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
        } else {
            explode(world, pos, player);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
            Item item = stack.getItem();
            if (!player.isInCreativeMode()) {
                if (stack.isOf(Items.FLINT_AND_STEEL)) {
                    stack.damage(1, player, LivingEntity.getSlotForHand(hand));
                } else {
                    stack.decrement(1);
                }
            }
            player.incrementStat(Stats.USED.getOrCreateStat(item));
            return ActionResult.SUCCESS;
        }
    }

    public void explode(World world, BlockPos pos, @Nullable LivingEntity igniter) {
        if (!world.isClient) {
            FireOfOrthancEntity fireOfOrthancEntity = new FireOfOrthancEntity(world, (double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5, igniter, true);
            world.spawnEntity(fireOfOrthancEntity);
            world.emitGameEvent(igniter, GameEvent.PRIME_FUSE, pos);
        } else {
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.25F, 0.8F);
        }
    }

    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        if (!world.isClient) {
            FireOfOrthancEntity fireOfOrthancEntity = new FireOfOrthancEntity(world, (double)pos.getX() + 0.5, pos.getY(), (double)pos.getZ() + 0.5, explosion.getCausingEntity(), false);
            int i = fireOfOrthancEntity.getFuse();
            fireOfOrthancEntity.setFuse((short)(world.random.nextInt(i / 2) + i / 2));
            world.spawnEntity(fireOfOrthancEntity);
        }
    }

    public boolean shouldDropItemsOnExplosion(Explosion explosion) {
        return false;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }
}
