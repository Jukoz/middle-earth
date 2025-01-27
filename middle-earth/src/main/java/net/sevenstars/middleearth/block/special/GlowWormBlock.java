package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.sevenstars.middleearth.block.ModNatureBlocks;
import net.sevenstars.middleearth.item.ModResourceItems;
import net.minecraft.block.AbstractPlantBlock;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class GlowWormBlock extends AbstractPlantBlock {
    public static final MapCodec<GlowWormBlock> CODEC = GlowWormBlock.createCodec(GlowWormBlock::new);
    public static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public GlowWormBlock(Settings settings) {
        super(settings, Direction.DOWN, SHAPE, false);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        ItemStack itemStack = player.getStackInHand(hand);

        if (itemStack.isOf(Items.GLASS_BOTTLE)) {
            itemStack.decrement(1);
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (itemStack.isEmpty()) {
                player.setStackInHand(hand, new ItemStack(ModResourceItems.GLOWWORM_BOTTLE));
            } else if (!player.getInventory().insertStack(new ItemStack(ModResourceItems.GLOWWORM_BOTTLE))) {
                player.dropItem(new ItemStack(ModResourceItems.GLOWWORM_BOTTLE), false);
            }
            world.removeBlock(pos,false);
        }
        return ActionResult.success(world.isClient);
    }

    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) ModNatureBlocks.GLOWWORM_WEBBING;
    }

    @Override
    protected MapCodec<GlowWormBlock> getCodec() {
        return CODEC;
    }
}
