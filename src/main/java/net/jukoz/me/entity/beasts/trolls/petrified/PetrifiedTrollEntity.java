package net.jukoz.me.entity.beasts.trolls.petrified;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.PickaxeItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PetrifiedTrollEntity extends MobEntity {

    public PetrifiedTrollEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
        this.setAiDisabled(true);
        this.setBodyYaw(this.getYaw());
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        Entity entity = source.getAttacker();

        if(entity instanceof PlayerEntity) {
            if(((PlayerEntity) entity).getMainHandStack().getItem() instanceof PickaxeItem) {
                return super.damage(source, 10.0f);
            }
        }
        return false;
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_STONE_BREAK;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_STONE_BREAK;
    }
}
