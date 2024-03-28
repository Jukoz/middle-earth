package net.jukoz.me.entity.humans.gondor;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.nazguls.NazgulEntity;
import net.jukoz.me.entity.orcs.misties.MistyGoblinEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.jukoz.me.entity.uruks.misties.MistyHobgoblinEntity;
import net.jukoz.me.entity.uruks.mordor.MordorBlackUrukEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GondorHumanEntity extends NpcEntity{

    public GondorHumanEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("militia")){
            this.setRank(RANK.MILITIA);
            this.setBow(Items.BOW);
        } else if (name.contains("soldier")) {
            this.setRank(RANK.SOLDIER);
            this.setBow(ModWeaponItems.GONDOR_BOW);
        }else if (name.contains("knight")) {
            this.setRank(RANK.KNIGHT);
        }else if (name.contains("veteran")) {
            this.setRank(RANK.VETERAN);
        }else if (name.contains("leader")) {
            this.setRank(RANK.LEADER);
        }
    }

    @Override
    protected boolean shouldDropLoot() {
        return false;
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
                0x57565f,
                0x4d4a57,
                0x444448,
                0x2f2d31
        };
        DyeableItem item = (DyeableItem)ModEquipmentItems.GAMBESON;
        ItemStack gambeson = new ItemStack(ModEquipmentItems.GAMBESON);
        ItemStack leatherHelmet = new ItemStack(Items.LEATHER_HELMET);
        ItemStack leatherChestplate = new ItemStack(Items.LEATHER_CHESTPLATE);
        ItemStack leatherLeggings = new ItemStack(Items.LEATHER_LEGGINGS);
        ItemStack leatherBoots = new ItemStack(Items.LEATHER_BOOTS);
        item.setColor(gambeson, colors[1]);
        item.setColor(leatherHelmet, colors[0]);
        item.setColor(leatherChestplate, colors[1]);
        item.setColor(leatherLeggings, colors[2]);
        item.setColor(leatherBoots, colors[3]);

        if(random.nextFloat() >= 0.30f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.KETTLE_HAT));
        } else  {
            equipStack(EquipmentSlot.HEAD, leatherHelmet);
        }
        if(random.nextFloat() >= 0.30f){
            equipStack(EquipmentSlot.CHEST, gambeson);
        } else  {
            equipStack(EquipmentSlot.CHEST, leatherChestplate);
        }
        if(random.nextFloat() >= 0.50f){
            equipStack(EquipmentSlot.LEGS, leatherLeggings);
        }
        equipStack(EquipmentSlot.FEET, leatherBoots);

        float val3 = random.nextFloat();
        if(val3 >= 0.7f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        } else if (val3 >= 0.3f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_DAGGER));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        }
    }

    private void soldierEquipment(Random random){
        int[] colors = {
                0x4d4a57
        };
        int colorIndex = random.nextInt(1);
        DyeableItem item = (DyeableItem)ModEquipmentItems.GAMBESON;
        ItemStack stack = new ItemStack((Item)item);
        item.setColor(stack, colors[colorIndex]);

        if(random.nextFloat() >= 0.30f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.GONDORIAN_MAIL_HELMET));
        } else  {
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.KETTLE_HAT));
        }
        if(random.nextFloat() >= 0.30f){
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.GONDORIAN_MAIL_CHESTPLATE));
        } else  {
            equipStack(EquipmentSlot.CHEST, stack);
        }

        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.GONDORIAN_MAIL_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.GONDORIAN_MAIL_BOOTS));


        float val3 = random.nextFloat();
        if(val3 >= 0.6f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_BOW));
        } else if (val3 > 0.30f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_SPEAR));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.GONDORIAN_SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.GONDORIAN_SHIELD));
        }
    }

    private void knightEquipment(Random random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.GONDORIAN_REINFORCED_MAIL_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.GONDORIAN_REINFORCED_MAIL_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.GONDORIAN_REINFORCED_MAIL_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.GONDORIAN_REINFORCED_MAIL_BOOTS));

        float val = random.nextFloat();
        if(val >= 0.75f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_BATTLEAXE));
        } else if (val > 0.20f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_SWORD));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.GONDORIAN_SHIELD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.GONDORIAN_SHIELD));
        }
    }

    private void veteranEquipment(Random random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.GONDORIAN_PLATE_HELMET));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.GONDORIAN_PLATE_CHESTPLATE));
        equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.GONDORIAN_PLATE_LEGGINGS));
        equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.GONDORIAN_PLATE_BOOTS));

        float val = random.nextFloat();
        if(val >= 0.75f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_BATTLEAXE));
        } else if (val >= 0.50f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_LONGSWORD));
        } else {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_PIKE));
            equipStack(EquipmentSlot.OFFHAND, new ItemStack(ModEquipmentItems.GONDORIAN_SHIELD));
        }
    }

    private void leaderEquipment(Random random){
        float randomVal =random.nextFloat();
        if(randomVal < 0.50f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_HELMET));
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_CHESTPLATE));
            equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_LEGGINGS));
            equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.GONDORIAN_CITADEL_GUARD_BOOTS));

            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_LONGSWORD));
        } else {
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_HELMET));
            equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE));
            equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_LEGGINGS));
            equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.GONDORIAN_FOUNTAIN_GUARD_BOOTS));

            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.GONDOR_PIKE));
        }
    }

    public GondorHumanVariant getVariant() {
        return GondorHumanVariant.byId(this.getId());
    }

}
