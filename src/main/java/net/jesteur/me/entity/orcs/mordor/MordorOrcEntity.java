package net.jesteur.me.entity.orcs.mordor;

import net.jesteur.me.entity.dwarves.durin.DurinDwarfEntity;
import net.jesteur.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jesteur.me.entity.elves.galadhrim.GaladhrimElfVariant;
import net.jesteur.me.item.ModWeaponItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class MordorOrcEntity extends HostileEntity {
    public MordorOrcEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_SWORD));
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
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
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, DurinDwarfEntity.class, true));
    }

    public MordorOrcVariant getVariant() {
        return MordorOrcVariant.byId(this.getId());
    }
}
