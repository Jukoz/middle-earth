package net.jukoz.me.entity.elves.galadhrim;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.nazguls.NazgulEntity;
import net.jukoz.me.entity.orcs.misties.MistyGoblinEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.uruks.misties.MistyHobgoblinEntity;
import net.jukoz.me.entity.uruks.mordor.MordorBlackUrukEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GaladhrimElfEntity extends NpcEntity{

    public GaladhrimElfEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("militia")){
            this.setRank(RANK.MILITIA);
            this.setBow(Items.BOW);
        } else if (name.contains("soldier")) {
            this.setRank(RANK.SOLDIER);
            this.setBow(ModWeaponItems.LORIEN_BOW);
        }else if (name.contains("knight")) {
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

    public static DefaultAttributeContainer.Builder setSoldierAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }
    public static DefaultAttributeContainer.Builder setKnightAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 27.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.5);
    }
    public static DefaultAttributeContainer.Builder setVeteranAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 29.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0);
    }
    public static DefaultAttributeContainer.Builder setLeaderAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 31.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.5);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        int i = 2;
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, TrollEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorBlackUrukEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyHobgoblinEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorOrcEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyGoblinEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MirkwoodSpiderEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, NazgulEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, BanditHumanEntity.class, true));
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        switch (this.getRank()){
            case MILITIA -> militiaEquipment(random);
            case SOLDIER -> soldierEquipment(random);
            case KNIGHT -> knightEquipment(random);
            case VETERAN -> veteranEquipment(random);
            case LEADER -> leaderEquipment(random);
        }
    }

    private void militiaEquipment(Random random){
        int[] colors = {
                0x767b85,
                0x747983,
                0x61626e,
                0x505258
        };
        ItemStack hood = new ItemStack(ModEquipmentItems.HOOD);
        ItemStack leatherHelmet = new ItemStack(Items.LEATHER_HELMET);
        ItemStack leatherChestplate = new ItemStack(Items.LEATHER_CHESTPLATE);
        ItemStack leatherLeggings = new ItemStack(Items.LEATHER_LEGGINGS);
        ItemStack leatherBoots = new ItemStack(Items.LEATHER_BOOTS);
        DyedColorComponent.setColor(hood, List.of(DyeItem.byColor(DyeColor.byId(colors[0]))));
        DyedColorComponent.setColor(leatherHelmet, List.of(DyeItem.byColor(DyeColor.byId(colors[1]))));
        DyedColorComponent.setColor(leatherChestplate, List.of(DyeItem.byColor(DyeColor.byId(colors[1]))));
        DyedColorComponent.setColor(leatherLeggings, List.of(DyeItem.byColor(DyeColor.byId(colors[2]))));
        DyedColorComponent.setColor(leatherBoots, List.of(DyeItem.byColor(DyeColor.byId(colors[3]))));

        if(random.nextFloat() >= 0.30f){
            equipStack(EquipmentSlot.HEAD, hood);
        } else  {
            equipStack(EquipmentSlot.HEAD, leatherHelmet);
            equipStack(EquipmentSlot.CHEST, leatherChestplate);
        }
        if(random.nextFloat() >= 0.50f){
            equipStack(EquipmentSlot.LEGS, leatherLeggings);
        }
        equipStack(EquipmentSlot.FEET, leatherBoots);

        float rand = random.nextFloat();
        if(rand >= 0.55f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (rand > 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_SPEAR));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_DAGGER));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    private void soldierEquipment(Random random){
        
        //equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.LORIEN_MAIL_HELMET));
        //equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.LORIEN_MAIL_CHESTPLATE));
        //equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.LORIEN_MAIL_LEGGINGS));
        //equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.LORIEN_MAIL_BOOTS));

        float val3 = random.nextFloat();
        if(val3 >= 0.55f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_BOW));
        } else if (val3 > 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.LORIEN_SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.LORIEN_SHIELD));
        }
    }

    private void knightEquipment(Random random){
        //equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.LORIEN_SCALE_HELMET));
        //equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.LORIEN_SCALE_CHESTPLATE));
        //equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.LORIEN_SCALE_LEGGINGS));
        //equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.LORIEN_SCALE_BOOTS));

        float val = random.nextFloat();
        if(val >= 0.75f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.LORIEN_SHIELD));
        } else if (val >= 0.50f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_GLAIVE));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.LORIEN_SHIELD));
        }
    }

    private void veteranEquipment(Random random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.GALADHRIM_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.GALADHRIM_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.GALADHRIM_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.GALADHRIM_BOOTS));

        if(random.nextFloat() >= 0.50f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.LORIEN_SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_GLAIVE));
        }
    }

    private void leaderEquipment(Random random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.GALADHRIM_LORD_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.GALADHRIM_LORD_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.GALADHRIM_LORD_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.GALADHRIM_LORD_BOOTS));

        if (random.nextFloat() < 0.50f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_GLAIVE));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.LORIEN_SPEAR));
        }
    }
    public GaladhrimElfVariant getVariant() {
        return GaladhrimElfVariant.byId(this.getId());
    }
}
