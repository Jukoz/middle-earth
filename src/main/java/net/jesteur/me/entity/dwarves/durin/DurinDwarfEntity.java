package net.jesteur.me.entity.dwarves.durin;

import net.jesteur.me.entity.orcs.mordor.MordorOrcEntity;
import net.jesteur.me.entity.trolls.TrollEntity;
import net.jesteur.me.entity.trolls.cave.CaveTrollEntity;
import net.jesteur.me.item.ModToolItems;
import net.jesteur.me.item.ModWeaponItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class DurinDwarfEntity extends HostileEntity {
    public DurinDwarfEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        double random = Math.random();
        if (random < 0.4f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModToolItems.DWARVEN_AXE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.AIR));
        } else if (random < 0.8f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModToolItems.DWARVEN_PICKAXE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.AIR));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.DWARVEN_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }

        random = Math.random();
        if(random < 0.4f) {
            equipStack(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
        }

    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 22.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }

    public static enum State {
        NEUTRAL,
        MINING,
        ATTACKING,
        CELEBRATING;

    }

    public State getState() {
        if (this.isAttacking()) {
            return State.ATTACKING;
        }
        return State.NEUTRAL;
    }

    @Override
    protected void initGoals() {
        int i = 0;
        this.goalSelector.add(++i, new SwimGoal(this));
        this.goalSelector.add(++i, new MeleeAttackGoal(this, 1.2f, false));
        this.goalSelector.add(++i, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(++i, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(++i, new LookAroundGoal(this));
        i = 0;
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, TrollEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorOrcEntity.class, true));
    }

    public DurinDwarfVariant getVariant() {
        return DurinDwarfVariant.byId(this.getId());
    }
}
