package net.jukoz.me.entity.dwarves.longbeards;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.orcs.misties.MistyGoblinEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.uruks.misties.MistyHobgoblinEntity;
import net.jukoz.me.entity.uruks.mordor.MordorBlackUrukEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModToolItems;
import net.jukoz.me.item.ModWeaponItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
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

public class LongbeardDwarfEntity extends NpcEntity {
    public LongbeardDwarfEntity(EntityType<? extends NpcEntity> entityType, World world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("militia")){
            this.setRank(RANK.MILITIA);
            this.setBow(Items.BOW);
        } else if (name.contains("soldier")) {
            this.setRank(RANK.SOLDIER);
            this.setBow(Items.BOW);
        }else if (name.contains("elite")) {
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
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 22.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }
    public static DefaultAttributeContainer.Builder setKnightAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.5);
    }
    public static DefaultAttributeContainer.Builder setVeteranAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 26.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0);
    }
    public static DefaultAttributeContainer.Builder setLeaderAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 28.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
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
                2113882
        };
        int colorIndex = random.nextInt(1);

        ItemStack leatherHelmet = new ItemStack((Item)Items.LEATHER_HELMET);
        DyedColorComponent.setColor(leatherHelmet, List.of(DyeItem.byColor(DyeColor.byId(colors[colorIndex]))));

        float val = random.nextFloat();
        if(val >= 0.30f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.FUR_HOOD));
        } else  {
            equipStack(EquipmentSlot.HEAD, leatherHelmet);
        }

        float val1 = random.nextFloat();
        if(val1 >= 0.30f){
            //equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.DWARVEN_PARTISAN_CHESTPLATE));
        } else  {
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.FUR_CLOAK));

        }

        float val2 = random.nextFloat();

        if(val2 >= 0.50f){
            //equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.DWARVEN_PARTISAN_LEGGINGS));
        }

        //equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.DWARVEN_PARTISAN_BOOTS));


        float val3 = random.nextFloat();
        if(val3 >= 0.55f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (val3 < 0.55f && val3 > 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.EREBOR_DAGGER));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        } else if (val3 <= 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.EREBOR_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    private void soldierEquipment(Random random){

        float val = random.nextFloat();
        if(val >= 0.30f){
            //equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.EREBOR_MAIL_HELMET));
        } else  {
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.FUR_HOOD));
        }

        float val1 = random.nextFloat();
        if(val1 >= 0.30f){
            //equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.EREBOR_MAIL_CHESTPLATE));
        }

        //equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.EREBOR_MAIL_LEGGINGS));
        //equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.EREBOR_MAIL_BOOTS));


        float val3 = random.nextFloat();
        if(val3 >= 0.55f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (val3 < 0.55f && val3 > 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.EREBOR_LONGSWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.EREBOR_HEAVY_SHIELD));
        } else if (val3 <= 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.EREBOR_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.EREBOR_HEAVY_SHIELD));
        }
    }

    private void knightEquipment(Random random){
        //equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.EREBOR_SCALE_HELMET));
        //equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.EREBOR_SCALE_CHESTPLATE));
        //equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.EREBOR_SCALE_LEGGINGS));
        //equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.EREBOR_SCALE_BOOTS));

        float val = random.nextFloat();
        if(val >= 0.75f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.EREBOR_LONGSWORD));
        } else if (val < 0.75f && val > 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.EREBOR_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.EREBOR_HEAVY_SHIELD));
        } else if (val <= 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.EREBOR_NOBLE_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.EREBOR_HEAVY_SHIELD));
        }
    }

    private void veteranEquipment(Random random){
        //equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.EREBOR_PLATE_HELMET));
        //equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.EREBOR_PLATE_CHESTPLATE));
        //equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.EREBOR_PLATE_LEGGINGS));
        //equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.EREBOR_PLATE_BOOTS));

        float val = random.nextFloat();
        if(val >= 0.75f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.EREBOR_NOBLE_LONGSWORD));
        } else if (val < 0.75f && val >= 0.50f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.EREBOR_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.EREBOR_HEAVY_SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.EREBOR_NOBLE_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.EREBOR_HEAVY_SHIELD));
        }
    }

    private void leaderEquipment(Random random){
        //equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.EREBOR_COMMANDER_HELMET));
        //equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.EREBOR_COMMANDER_CHESTPLATE));
        //equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.EREBOR_COMMANDER_LEGGINGS));
        //equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.EREBOR_COMMANDER_BOOTS));

        equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.EREBOR_NOBLE_LONGSWORD));
    }

    public LongbeardDwarfVariant getVariant() {
        return LongbeardDwarfVariant.byId(this.getId());
    }

}
