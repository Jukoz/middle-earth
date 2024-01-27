package net.jukoz.me.item.items;

import net.jukoz.me.item.ModToolItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PowderSnowBucketItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class CustomPowderSnowBucket extends PowderSnowBucketItem {
    public CustomPowderSnowBucket(Block block, SoundEvent placeSound, Settings settings) {
        super(block, placeSound, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ActionResult actionResult = super.useOnBlock(context);
        PlayerEntity playerEntity = context.getPlayer();
        if (actionResult.isAccepted() && playerEntity != null && !playerEntity.isCreative()) {
            Hand hand = context.getHand();
            playerEntity.setStackInHand(hand, ModToolItems.BRONZE_BUCKET.getDefaultStack());
        }

        return actionResult;
    }
}
