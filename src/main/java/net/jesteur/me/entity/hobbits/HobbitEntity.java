package net.jesteur.me.entity.hobbits;

import net.jesteur.me.entity.orcs.mordor.MordorOrcEntity;
import net.jesteur.me.entity.pebble.PebbleEntity;
import net.jesteur.me.entity.trolls.TrollEntity;
import net.jesteur.me.entity.trolls.cave.CaveTrollEntity;
import net.jesteur.me.item.ModRessourceItems;
import net.jesteur.me.item.items.PebbleItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class HobbitEntity extends HostileEntity implements RangedAttackMob {

    public HobbitEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0);
    }

    @Override
    protected void initGoals() {
        int i = 0;
        this.goalSelector.add(++i, new SwimGoal(this));
        this.goalSelector.add(++i, new FleeEntityGoal<>(this, TrollEntity.class, 8.0f, 0.8f, 1.2f));
        this.goalSelector.add(++i, new FleeEntityGoal<>(this, MordorOrcEntity.class, 8.0f, 0.8f, 1.2f));
        this.goalSelector.add(++i, new ProjectileAttackGoal(this, 1.0, 12, 24, 20.0f));
        this.goalSelector.add(++i, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(++i, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(++i, new LookAroundGoal(this));
        i = 0;
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorOrcEntity.class, true));
    }

    public HobbitVariant getVariant() {
        return HobbitVariant.byId(this.getId());
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        Item item = ModRessourceItems.PEBBLE;
        ItemStack itemStack = new ItemStack(item);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333) - this.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);

        PebbleEntity pebbleEntity = new PebbleEntity(getWorld(), this, PebbleItem.DAMAGE);
        pebbleEntity.setItem(itemStack);
        pebbleEntity.setVelocity(d, e + g * (double)0.2f, f, 0.8f, 14 - getWorld().getDifficulty().getId() * 4);
        getWorld().spawnEntity(pebbleEntity);
        this.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.05f + 0.8f));
    }



}
