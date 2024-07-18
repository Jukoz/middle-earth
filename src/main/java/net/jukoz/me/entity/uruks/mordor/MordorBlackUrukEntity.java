package net.jukoz.me.entity.uruks.mordor;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.humans.rohan.RohanHumanEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MordorBlackUrukEntity extends NpcEntity {

    public MordorBlackUrukEntity(EntityType<? extends NpcEntity> entityType, World world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if (name.contains("soldier")) {
            this.setRank(RANK.KNIGHT);
        }else if (name.contains("veteran")) {
            this.setRank(RANK.VETERAN);
        }else if (name.contains("leader")) {
            this.setRank(RANK.LEADER);
        }
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData);
        Random random = world.getRandom();
        this.initEquipment(random, difficulty);
        return entityData;
    }
    public static DefaultAttributeContainer.Builder setKnightAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 22.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.5);
    }
    public static DefaultAttributeContainer.Builder setVeteranAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0);
    }
    public static DefaultAttributeContainer.Builder setLeaderAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 26.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.5);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        int i = 2;
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GondorHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, RohanHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, LongbeardDwarfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, BanditHumanEntity.class, true));
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);

        switch (this.getRank()){
            case KNIGHT -> knightEquipment(random);
            case VETERAN -> veteranEquipment(random);
            case LEADER -> leaderEquipment(random);
        }
    }

    private void knightEquipment(Random random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.MORDOR_BLACK_URUK_SCALE_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.MORDOR_BLACK_URUK_SCALE_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.MORDOR_BLACK_URUK_SCALE_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.MORDOR_BLACK_URUK_SCALE_BOOTS));

        float val = random.nextFloat();
        if(val >= 0.75f){
            //equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_BATTLEAXE));
        } else if (val < 0.75f && val > 0.20f) {
            //equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.MORDOR_SHIELD));
        } else if (val <= 0.20f) {
            //equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.MORDOR_SHIELD));
        }
    }

    private void veteranEquipment(Random random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.MORDOR_BLACK_URUK_PLATE_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.MORDOR_BLACK_URUK_PLATE_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.MORDOR_BLACK_URUK_PLATE_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.MORDOR_BLACK_URUK_PLATE_BOOTS));

        float val = random.nextFloat();
        if(val >= 0.50f){
            //equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_BATTLEAXE));
        }  else {
            //equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.MORDOR_SHIELD));
        }
    }

    private void leaderEquipment(Random random){
        {
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.MORDOR_BLACK_URUK_COMMANDER_HELMET));
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.MORDOR_BLACK_URUK_PLATE_CHESTPLATE));
            equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.MORDOR_BLACK_URUK_PLATE_LEGGINGS));
            equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.MORDOR_BLACK_URUK_PLATE_BOOTS));

            float val = random.nextFloat();
            if(val >= 0.50f){
                //equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_BATTLEAXE));
            }  else {
                //equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_PIKE));
                equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.MORDOR_SHIELD));
            }
        }
    }

    public MordorBlackUrukVariant getVariant() {
        return MordorBlackUrukVariant.byId(this.getId());
    }
}
