package net.jesteur.me.item.items;

import net.jesteur.me.entity.pebble.PebbleEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class PebbleItem extends Item{
    public static final float DAMAGE = 2f;
    private static final float BASE_STRENGTH = 0.6f;
    private static final float CHARGE_STRENGTH = 0.9f;
    private static final int STRENGTH_CHARGE_TIME = 20; // 1s charge for full strength
    public PebbleItem(Item.Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity playerEntity = (PlayerEntity)user;
        int i = this.getMaxUseTime(stack) - remainingUseTicks;
        if (i < 7) {
            return;
        }
        if(i > STRENGTH_CHARGE_TIME) i = STRENGTH_CHARGE_TIME;
        float percentage = (float) i / STRENGTH_CHARGE_TIME;

        if (!world.isClient) {
            PebbleEntity pebbleEntity = new PebbleEntity(world, user, DAMAGE * percentage);
            pebbleEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, BASE_STRENGTH + (CHARGE_STRENGTH * percentage), 1.0f);

            world.spawnEntity(pebbleEntity);
            world.playSoundFromEntity(null, pebbleEntity, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 1.0f, 0.7f);
            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
    }
}
