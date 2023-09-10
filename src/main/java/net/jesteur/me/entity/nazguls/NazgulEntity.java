package net.jesteur.me.entity.nazguls;

import net.jesteur.me.entity.dwarves.durin.DurinDwarfEntity;
import net.jesteur.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jesteur.me.entity.hobbits.HobbitEntity;
import net.jesteur.me.item.ModEquipmentItems;
import net.jesteur.me.item.ModWeaponItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class NazgulEntity extends HostileEntity {
    public static int FADING_TIME = 60;
    private int ticksSinceDeath = 0;
    public NazgulEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
        setEquipment();
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.5);
    }

    private void setEquipment() {
        equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_SWORD));
        equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.AIR));

        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.NAZGUL_CLOAK_HOOD));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.NAZGUL_CLOAK));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.NAZGUL_CLOAK_PANTS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.NAZGUL_MORGUL_BOOTS));
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
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, HobbitEntity.class, true));
    }

    public enum State {
        NEUTRAL,
        ATTACKING,
        ON_FIRE,
        FADING;

    }

    public State getState() {
        if(this.isOnFire()) {
            return State.ON_FIRE;
        }
        if(this.isFading()) {
            return State.FADING;
        }
        else if (this.isAttacking()) {
            return State.ATTACKING;
        }
        return State.NEUTRAL;
    }

    @Override
    public void kill() {

    }

    @Override
    protected void updatePostDeath() {
        ++this.ticksSinceDeath;
        if (this.ticksSinceDeath >= FADING_TIME && this.getWorld() instanceof ServerWorld) {
            this.remove(Entity.RemovalReason.KILLED);
            this.emitGameEvent(GameEvent.ENTITY_DIE);
        }
    }

    public boolean isFading() {
        return ticksSinceDeath > 0;
    }

    public int getFadingTicks() {
        return ticksSinceDeath;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        LivingEntity livingEntity = this.getTarget();
        if (livingEntity != null && livingEntity.isAlive()) {
            // Scream
        }
    }
}
