package net.jukoz.me.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class FireOfOrthancBlock extends Block {
    public static final MapCodec<FireOfOrthancBlock> CODEC = createCodec(FireOfOrthancBlock::new);
    public static final VoxelShape OUTLINE_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(3.0, 3.0, 3.0, 13.0, 13.0, 13.0),
            Block.createCuboidShape(6.0, 13.0, 6.0, 10.0, 15.0, 10.0));
    public static float explosionForce = 11.5f;

    public FireOfOrthancBlock(Settings settings) {
        super(settings);
    }

    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!stack.isOf(Items.FLINT_AND_STEEL) && !stack.isOf(Items.FIRE_CHARGE)) {
            return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
        } else {
            explode(world, pos, player);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
            Item item = stack.getItem();
            if (!player.isCreative()) {
                if (stack.isOf(Items.FLINT_AND_STEEL)) {
                    stack.damage(1, player, LivingEntity.getSlotForHand(hand));
                } else {
                    stack.decrement(1);
                }
            }
            player.incrementStat(Stats.USED.getOrCreateStat(item));
            return ItemActionResult.success(world.isClient);
        }
    }

    public void explode(World world, BlockPos pos, PlayerEntity player) {
        if(!world.isClient()) {
            float randX = (float) (Math.random() - 0.5f);
            float randY = (float) Math.random() / 3;
            float randZ = (float) (Math.random() - 0.5f);
            world.createExplosion(player, pos.getX() + randX, pos.getY() + randY, pos.getZ() + randZ, explosionForce, World.ExplosionSourceType.TNT);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }
}
