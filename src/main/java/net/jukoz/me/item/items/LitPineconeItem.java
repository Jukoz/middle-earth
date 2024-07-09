package net.jukoz.me.item.items;

import net.jukoz.me.entity.projectile.pinecone.LitPineconeEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class LitPineconeItem extends PineconeItem{
    public static final float DAMAGE = 2f;
    private static final float BASE_STRENGTH = 0.6f;
    private static final float CHARGE_STRENGTH = 0.9f;
    private static final int STRENGTH_CHARGE_TIME = 20; // 1s charge for full strength
    public LitPineconeItem(Settings settings) {
        super(settings);
    }


    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity playerEntity = (PlayerEntity)user;
        int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
        if (i < 7) {
            return;
        }
        if(i > STRENGTH_CHARGE_TIME) i = STRENGTH_CHARGE_TIME;
        float percentage = (float) i / STRENGTH_CHARGE_TIME;

        if (!world.isClient) {
            LitPineconeEntity litPineconeEntity = new LitPineconeEntity(world, user, DAMAGE * percentage);
            litPineconeEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, BASE_STRENGTH + (CHARGE_STRENGTH * percentage), 1.0f);

            world.spawnEntity(litPineconeEntity);
            world.playSoundFromEntity(null, litPineconeEntity, SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.PLAYERS, 1.0f, 0.7f);
            if (!playerEntity.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
    }
}
