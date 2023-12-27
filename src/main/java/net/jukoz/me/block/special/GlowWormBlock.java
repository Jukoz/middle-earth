package net.jukoz.me.block.special;

import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.item.ModRessourceItems;
import net.minecraft.block.*;
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
    public static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public GlowWormBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.DOWN, SHAPE, false);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (itemStack.isOf(Items.GLASS_BOTTLE)) {
            itemStack.decrement(1);
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (itemStack.isEmpty()) {
                player.setStackInHand(hand, new ItemStack(ModRessourceItems.GLOWWORM_BOTTLE));
            } else if (!player.getInventory().insertStack(new ItemStack(ModRessourceItems.GLOWWORM_BOTTLE))) {
                player.dropItem(new ItemStack(ModRessourceItems.GLOWWORM_BOTTLE), false);
            }
            world.removeBlock(pos,false);
        }
        return ActionResult.success(world.isClient);
    }

    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) ModNatureBlocks.GLOWWORM_WEBBING;
    }
}
