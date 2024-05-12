package net.jukoz.me.entity;

import net.jukoz.me.entity.goals.CustomBowAttackGoal;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class NpcEntity extends PathAwareEntity implements RangedAttackMob {

    private Item bow;
    private final CustomBowAttackGoal<NpcEntity> bowAttackGoal = new CustomBowAttackGoal<NpcEntity>(this, 1.0, 16, 30.0f);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.5, false);
    public RANK rank;

    protected NpcEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.updateAttackType();
        for (int i = 0; i < 4; i++) {
            Arrays.fill(this.armorDropChances, 0.0f);
        }
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.updateAttackType();
        return entityData;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this, this.getClass()).setGroupRevenge());
    }

    public void updateAttackType() {
        if (this.getWorld() != null && !this.getWorld().isClient) {
            this.goalSelector.remove(this.meleeAttackGoal);
            this.goalSelector.remove(this.bowAttackGoal);
            ItemStack itemStack = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, this.bow));

            if (itemStack.isOf(this.bow)) {
                this.bowAttackGoal.setAttackInterval(20);
                this.goalSelector.add(2, this.bowAttackGoal);
            } else {
                this.goalSelector.add(2, this.meleeAttackGoal);
            }
        }
    }

    public Item getBow(){
        return this.bow;
    }

    public void setBow(Item bow){
        this.bow = bow;
    }

    public RANK getRank() {
        return rank;
    }

    public void setRank(RANK rank) {
        this.rank = rank;
    }

    public static enum State {
        NEUTRAL,
        ATTACKING,
    }

    public NpcEntity.State getState() {
        if (this.isAttacking()) {
            return NpcEntity.State.ATTACKING;
        }
        return NpcEntity.State.NEUTRAL;
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
    }

    @Override
    public boolean isPersistent() {
        return super.isPersistent();
    }

    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        super.equipStack(slot, stack);
        if (!this.getWorld().isClient) {
            this.updateAttackType();
        }
    }

    @Override
    public boolean canUseRangedWeapon(RangedWeaponItem weapon) {
        return weapon == getBow();
    }

    public void attack(LivingEntity target, float pullProgress) {
        ItemStack itemStack = this.getProjectileType(this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, getBow())));
        PersistentProjectileEntity persistentProjectileEntity = this.createArrowProjectile(itemStack, pullProgress);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333) - persistentProjectileEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        persistentProjectileEntity.setVelocity(d, e + g * 0.20000000298023224, f, 1.6F, (float)(14 - this.getWorld().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.getWorld().spawnEntity(persistentProjectileEntity);
    }

    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
        return ProjectileUtil.createArrowProjectile(this, arrow, damageModifier);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.updateAttackType();
    }

    @Override
    public int getXpToDrop() {
        int exp = 0;
        switch (this.getRank()){
            case MILITIA -> exp = 10;
            case SOLDIER -> exp = 15;
            case KNIGHT -> exp = 20;
            case VETERAN -> exp = 25;
            case LEADER -> exp = 30;
        }
        return exp;
    }


    @Override
    protected void applyDamage(DamageSource source, float amount) {
        super.applyDamage(source, amount);
    }

    public enum RANK{
        CIVILIAN,
        MILITIA,
        SOLDIER,
        KNIGHT,
        VETERAN,
        LEADER
    }
}
