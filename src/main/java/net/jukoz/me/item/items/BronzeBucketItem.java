package net.jukoz.me.item.items;

import net.jukoz.me.item.ModRessourceItems;
import net.jukoz.me.item.ModToolItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class BronzeBucketItem extends BucketItem {
    private final Fluid fluid;

    public BronzeBucketItem(Fluid fluid, Settings settings) {
        super(fluid, settings);
        this.fluid = fluid;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                if (world.getBlockState(blockHitResult.getBlockPos()) == Blocks.LAVA.getDefaultState()) {
                    user.getStackInHand(hand).decrement(1);
                    user.giveItemStack(ModToolItems.BROKEN_BRONZE_BUCKET.getDefaultStack());
                    return TypedActionResult.pass(itemStack);
                }
            }
        }else {
            return super.use(world, user, hand);
        }
        return TypedActionResult.pass(itemStack);
    }
}
