package net.jukoz.me.entity.dwarves.durin;

import net.jukoz.me.entity.nazguls.NazgulEntity;
import net.jukoz.me.entity.orcs.misties.MistyOrcEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.uruks.misties.MistyUrukEntity;
import net.jukoz.me.entity.uruks.mordor.MordorUrukEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModToolItems;
import net.jukoz.me.item.ModWeaponItems;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DurinDwarfEntity extends PathAwareEntity {
    public DurinDwarfEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
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

    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 22.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        float randomVal = random.nextFloat();

        equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.AIR));

        if (randomVal < 0.4f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModToolItems.DWARVEN_STEEL_AXE));
        } else if (randomVal < 0.8f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModToolItems.DWARVEN_STEEL_PICKAXE));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.DWARVEN_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }

        randomVal = random.nextFloat();
        if(randomVal < 0.05f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.FUR_CLOAK_HOOD));
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.FUR_CLOAK));
        } else if(randomVal < 0.10f) {
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.FUR_CLOAK_HOOD));
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.CHAINMAIL_FUR_CLOAK));
        } else if(randomVal < 0.20f) {
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.FUR_CLOAK));
        } else if(randomVal < 0.30f) {
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.CHAINMAIL_FUR_CLOAK));
        }
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
        this.targetSelector.add(++i, new RevengeGoal(this));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, TrollEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorUrukEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyUrukEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorOrcEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyOrcEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MirkwoodSpiderEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, NazgulEntity.class, true));
    }

    public DurinDwarfVariant getVariant() {
        return DurinDwarfVariant.byId(this.getId());
    }

}
