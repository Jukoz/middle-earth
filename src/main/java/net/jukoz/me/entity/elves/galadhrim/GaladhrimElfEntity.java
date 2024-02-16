package net.jukoz.me.entity.elves.galadhrim;

import net.jukoz.me.entity.goals.BowAttackGoal;
import net.jukoz.me.entity.nazguls.NazgulEntity;
import net.jukoz.me.entity.orcs.misties.MistyOrcEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.uruks.misties.MistyUrukEntity;
import net.jukoz.me.entity.uruks.mordor.MordorUrukEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GaladhrimElfEntity extends PathAwareEntity implements RangedAttackMob {
    private final BowAttackGoal bowAttackGoal = new BowAttackGoal(this, 1.0, 16, 24.0f);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2, false);

    public GaladhrimElfEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        Random random = world.getRandom();
        this.initEquipment(random, difficulty);
        return entityData;
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);

        equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_BOW));
        equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.AIR));

        float randomVal = random.nextFloat();

        if (randomVal < 0.35f) {
            int[] colors = {
                    0x809c9c,
                    0x808c9c,
                    0x435e52
            };
            int colorIndex = random.nextInt(3);

            DyeableItem item = (DyeableItem)ModEquipmentItems.CLOAK;
            ItemStack stack = new ItemStack((Item)item);
            item.setColor(stack, colors[colorIndex]);

            equipStack(EquipmentSlot.CHEST, stack);
        }
    }


    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }

    @Override
    protected void initGoals() {
        int i = 0;
        this.goalSelector.add(++i, new BowAttackGoal<>(this, 1.0, 16, 24.0f));
        this.goalSelector.add(++i, new SwimGoal(this));
        //this.goalSelector.add(++i, new MeleeAttackGoal(this, 1.2f, false));
        this.goalSelector.add(++i, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(++i, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(++i, new LookAroundGoal(this));
        i = 0;
        this.targetSelector.add(++i, new RevengeGoal(this));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, TrollEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorUrukEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyUrukEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorOrcEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyOrcEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MirkwoodSpiderEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, NazgulEntity.class, true));
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        ItemStack itemStack = this.getProjectileType(this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW)));
        PersistentProjectileEntity persistentProjectileEntity = this.createArrowProjectile(itemStack, pullProgress);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333) - persistentProjectileEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        persistentProjectileEntity.setVelocity(d, e + g * (double)0.2f, f, 1.8f, 14 - getWorld().getDifficulty().getId() * 4);
        this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.05f + 0.8f));
        getWorld().spawnEntity(persistentProjectileEntity);
    }

    protected PersistentProjectileEntity createArrowProjectile(ItemStack arrow, float damageModifier) {
        return ProjectileUtil.createArrowProjectile(this, arrow, damageModifier);
    }

    @Override
    public boolean canUseRangedWeapon(RangedWeaponItem weapon) {
        return weapon == Items.BOW;
    }

    public GaladhrimElfVariant getVariant() {
        return GaladhrimElfVariant.byId(this.getId());
    }
}
