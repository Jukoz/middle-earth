package net.jukoz.me.entity.humans.bandit;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.humans.rohan.RohanHumanEntity;
import net.jukoz.me.entity.nazguls.NazgulEntity;
import net.jukoz.me.entity.orcs.misties.MistyGoblinEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.jukoz.me.entity.uruks.misties.MistyHobgoblinEntity;
import net.jukoz.me.entity.uruks.mordor.MordorBlackUrukEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BanditHumanEntity extends NpcEntity{

    public BanditHumanEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("militia")){
            this.setRank(RANK.MILITIA);
            this.setBow(Items.BOW);
        } else if (name.contains("soldier")) {
            this.setRank(RANK.SOLDIER);
            this.setBow(Items.BOW);
        }else if (name.contains("knight")) {
            this.setRank(RANK.KNIGHT);
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
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }
    public static DefaultAttributeContainer.Builder setKnightAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 22.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.5);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        int i = 2;
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, TrollEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorBlackUrukEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyHobgoblinEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorOrcEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyGoblinEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MirkwoodSpiderEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, NazgulEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GondorHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, RohanHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, LongbeardDwarfEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, ShireHobbitEntity.class, true));
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
        switch (this.getRank()){
            case MILITIA -> militiaEquipment(random);
            case SOLDIER -> soldierEquipment(random);
            case KNIGHT -> knightEquipment(random);
        }
    }

    private void militiaEquipment(Random random){
        int[] colors = {
                0x8c8572,
                0x897f64,
                0x6d685b,
                0x504e48
        };
        ItemStack leatherHelmet = new ItemStack(Items.LEATHER_HELMET);
        ItemStack leatherChestplate = new ItemStack(Items.LEATHER_CHESTPLATE);
        ItemStack leatherLeggings = new ItemStack(Items.LEATHER_LEGGINGS);
        ItemStack leatherBoots = new ItemStack(Items.LEATHER_BOOTS);
        DyedColorComponent.setColor(leatherHelmet, List.of(DyeItem.byColor(DyeColor.byId(colors[0]))));
        DyedColorComponent.setColor(leatherChestplate, List.of(DyeItem.byColor(DyeColor.byId(colors[1]))));
        DyedColorComponent.setColor(leatherLeggings, List.of(DyeItem.byColor(DyeColor.byId(colors[2]))));
        DyedColorComponent.setColor(leatherBoots, List.of(DyeItem.byColor(DyeColor.byId(colors[3]))));

        if(random.nextFloat() >= 0.30f){
            equipStack(EquipmentSlot.HEAD, leatherHelmet);
        }
        equipStack(EquipmentSlot.CHEST, leatherChestplate);
        if(random.nextFloat() >= 0.50f){
            equipStack(EquipmentSlot.LEGS, leatherLeggings);
        }
        equipStack(EquipmentSlot.FEET, leatherBoots);

        float val3 = random.nextFloat();
        if(val3 >= 0.7f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (val3 > 0.2f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.BRONZE_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.IRON_DAGGER));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    private void soldierEquipment(Random random){
        ItemStack gambeson = new ItemStack(ModEquipmentItems.GAMBESON);
        DyedColorComponent.setColor(gambeson, List.of(DyeItem.byColor(DyeColor.byId(0x897f64))));

        if(random.nextFloat() >= 0.50f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
        } else  {
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.KETTLE_HAT));
        }
        if(random.nextFloat() >= 0.30f){
            equipStack(EquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
        } else  {
            equipStack(EquipmentSlot.CHEST, gambeson);
        }
        equipStack(EquipmentSlot.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(Items.CHAINMAIL_BOOTS));

        float val3 = random.nextFloat();
        if(val3 >= 0.65f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if ( val3 > 0.3f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.BRONZE_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.BRONZE_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    private void knightEquipment(Random random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(Items.IRON_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(Items.CHAINMAIL_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(Items.CHAINMAIL_LEGGINGS));

        float val = random.nextFloat();
        if(val >= 0.75f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
        } else if (val > 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.BRONZE_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    public BanditHumanVariant getVariant() {
        return BanditHumanVariant.byId(this.getId());
    }
}
