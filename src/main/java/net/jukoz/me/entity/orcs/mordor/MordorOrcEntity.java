package net.jukoz.me.entity.orcs.mordor;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.dwarves.durin.DurinDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.humans.rohan.RohanHumanEntity;
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
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MordorOrcEntity extends NpcEntity {
    public MordorOrcEntity(EntityType<? extends NpcEntity> entityType, World world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("snaga")){
            this.setRank(RANK.MILITIA);
            this.setBow(Items.BOW);
        } else if (name.contains("soldier")) {
            this.setRank(RANK.SOLDIER);
            this.setBow(Items.BOW);
        }
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        Random random = world.getRandom();
        this.initEquipment(random, difficulty);
        return entityData;
    }

    public static DefaultAttributeContainer.Builder setSoldierAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        int i = 2;
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GondorHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, RohanHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, DurinDwarfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, BanditHumanEntity.class, true));
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        switch (this.getRank()){
            case MILITIA -> militiaEquipment(random);
            case SOLDIER -> soldierEquipment(random);
        }
    }

    private void militiaEquipment(Random random){
        float val = random.nextFloat();
        if(val >= 0.30f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.RUSTY_KETTLE_HAT));
        } else  {
            equipStack(EquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
        }

        equipStack(EquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));

        float val2 = random.nextFloat();

        if(val2 >= 0.50f){
            equipStack(EquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));
        }

        equipStack(EquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));


        float val3 = random.nextFloat();
        if(val3 >= 0.55f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (val3 < 0.55f && val3 > 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        } else if (val3 <= 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_DAGGER));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    private void soldierEquipment(Random random){
        float val = random.nextFloat();
        if(val >= 0.30f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.MORDOR_ORC_MAIL_HELMET));
        } else  {
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.RUSTY_KETTLE_HAT));
        }

        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.MORDOR_ORC_MAIL_CHESTPLATE));


        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.MORDOR_ORC_MAIL_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.MORDOR_ORC_MAIL_BOOTS));


        float val3 = random.nextFloat();
        if(val3 >= 0.55f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (val3 < 0.55f && val3 > 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.MORDOR_SHIELD));
        } else if (val3 <= 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORDOR_ORC_SCIMITAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.MORDOR_SHIELD));
        }
    }

    public MordorOrcVariant getVariant() {
        return MordorOrcVariant.byId(this.getId());
    }
}
