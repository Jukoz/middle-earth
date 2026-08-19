package net.sevenstars.middleearth.item.items.weapons.ranged;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.entity.projectile.pinecone.PineconeEntity;

public class PineconeItem extends PebbleItem {
    public static final float DAMAGE = 2f;
    private static final float BASE_STRENGTH = 0.6f;
    private static final float CHARGE_STRENGTH = 0.9f;
    private static final int STRENGTH_CHARGE_TIME = 20; // 1s charge for full strength
    public PineconeItem(Properties settings) {
        super(settings);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof Player)) {
            return;
        }
        Player playerEntity = (Player)user;
        int i = this.getUseDuration(stack, user) - remainingUseTicks;
        if (i < 7) {
            return;
        }
        if(i > STRENGTH_CHARGE_TIME) i = STRENGTH_CHARGE_TIME;
        float percentage = (float) i / STRENGTH_CHARGE_TIME;

        if (!world.isClientSide) {
            PineconeEntity pineconeEntity = new PineconeEntity(world, user, DAMAGE * percentage);
            pineconeEntity.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0f, BASE_STRENGTH + (CHARGE_STRENGTH * percentage), 1.0f);

            world.addFreshEntity(pineconeEntity);
            world.playSound(null, pineconeEntity, SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 1.0f, 0.7f);
            if (!playerEntity.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        playerEntity.awardStat(Stats.ITEM_USED.get(this));
    }
}
