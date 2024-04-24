package net.jukoz.me.entity.hobbits.shire;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.nazguls.NazgulEntity;
import net.jukoz.me.entity.orcs.misties.MistyGoblinEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.projectile.pebble.PebbleEntity;
import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.uruks.misties.MistyHobgoblinEntity;
import net.jukoz.me.entity.uruks.mordor.MordorBlackUrukEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModResourceItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.item.items.PebbleItem;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShireHobbitEntity extends NpcEntity {
    private static float FLEE_DISTANCE = 8f;
    private static float FLEE_SPEED_MIN = 0.8f;
    private static float FLEE_SPEED_MAX = 1.2f;

    public ShireHobbitEntity(EntityType<? extends NpcEntity> entityType, World world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("civilian")){
            this.setRank(RANK.CIVILIAN);
        }else if(name.contains("bounder")){
            this.setRank(RANK.MILITIA);
            this.setBow(Items.BOW);
        }else if (name.contains("shirriff")) {
            this.setRank(RANK.SOLDIER);
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

    @Override
    protected void initGoals() {
        int i = 0;
        this.goalSelector.add(++i, new SwimGoal(this));
        this.goalSelector.add(++i, new FleeEntityGoal<>(this, TrollEntity.class, FLEE_DISTANCE, FLEE_SPEED_MIN, FLEE_SPEED_MAX));
        this.goalSelector.add(++i, new FleeEntityGoal<>(this, MordorBlackUrukEntity.class, FLEE_DISTANCE, FLEE_SPEED_MIN, FLEE_SPEED_MAX));
        this.goalSelector.add(++i, new FleeEntityGoal<>(this, MistyHobgoblinEntity.class, FLEE_DISTANCE, FLEE_SPEED_MIN, FLEE_SPEED_MAX));
        this.goalSelector.add(++i, new FleeEntityGoal<>(this, MordorOrcEntity.class, FLEE_DISTANCE, FLEE_SPEED_MIN, FLEE_SPEED_MAX));
        this.goalSelector.add(++i, new FleeEntityGoal<>(this, MistyGoblinEntity.class, FLEE_DISTANCE, FLEE_SPEED_MIN, FLEE_SPEED_MAX));
        this.goalSelector.add(++i, new FleeEntityGoal<>(this, BanditHumanEntity.class, FLEE_DISTANCE, FLEE_SPEED_MIN, FLEE_SPEED_MAX));
        this.goalSelector.add(++i, new FleeEntityGoal<>(this, MirkwoodSpiderEntity.class, FLEE_DISTANCE, FLEE_SPEED_MIN, FLEE_SPEED_MAX));
        this.goalSelector.add(++i, new FleeEntityGoal<>(this, NazgulEntity.class, FLEE_DISTANCE, FLEE_SPEED_MIN, FLEE_SPEED_MAX));
        this.goalSelector.add(++i, new ProjectileAttackGoal(this, 1.0, 12, 24, 20.0f));
        this.goalSelector.add(++i, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(++i, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(++i, new LookAroundGoal(this));
        i = 1;
        this.targetSelector.add(++i, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, TrollEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyHobgoblinEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorBlackUrukEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MordorOrcEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MistyGoblinEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, BanditHumanEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, MirkwoodSpiderEntity.class, true));
        this.targetSelector.add(++i, new ActiveTargetGoal<>(this, NazgulEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder setSoldierAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0);
    }
    public static DefaultAttributeContainer.Builder setKnightAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 22.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.5);
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);

        switch (this.getRank()){
            case CIVILIAN -> civilianEquipement(random);
            case MILITIA -> militiaEquipment(random);
            case SOLDIER -> soldierEquipment(random);
        }
    }

    private void civilianEquipement(Random random){
        float randomVal = random.nextFloat();
        if(randomVal < 0.03f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.FISHING_ROD));
        } else if (randomVal < 0.15f) {
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModResourceItems.PEBBLE));
        } else if(randomVal < 0.20f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.APPLE));
        }  else if(randomVal < 0.25f){
            equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.CARROT));
        }

        randomVal = random.nextFloat();
        if (randomVal < 0.15f) {
            int[] colors = {
                    0x375c23,
                    0x535c23,
                    0x665723
            };
            int colorIndex = random.nextInt(3);
            ItemStack cloakHood = new ItemStack((Item)ModEquipmentItems.CLOAK_HOOD);
            ItemStack cloak = new ItemStack((Item)ModEquipmentItems.CLOAK);

            DyedColorComponent.setColor(cloak, List.of(DyeItem.byColor(DyeColor.byId(colors[colorIndex]))));


            if(randomVal < 0.07f) {
                DyedColorComponent.setColor(cloakHood, List.of(DyeItem.byColor(DyeColor.byId(colors[colorIndex]))));
                equipStack(EquipmentSlot.HEAD, cloakHood);
            }
            equipStack(EquipmentSlot.CHEST, cloak);
        }
    }

    private void militiaEquipment(Random random){
        equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.KETTLE_HAT));
        equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.GAMBESON));
        equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.IRON_DAGGER));
    }

    private void soldierEquipment(Random random){
        float  randomVal = random.nextFloat();
        equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        if(randomVal < 0.50f){
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.HOBBIT_SHIRRIFF_HAT_BROWN));
        } else {
            equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.HOBBIT_SHIRRIFF_HAT_GREEN));
        }
    }

    public ShireHobbitVariant getVariant() {
        return ShireHobbitVariant.byId(this.getId());
    }

    @Override
    public void shootAt(LivingEntity target, float pullProgress) {
        Item item = ModResourceItems.PEBBLE;
        ItemStack itemStack = new ItemStack(item);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333) - this.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);

        PebbleEntity pebbleEntity = new PebbleEntity(getWorld(), this, PebbleItem.DAMAGE);
        pebbleEntity.setItem(itemStack);
        pebbleEntity.setVelocity(d, e + g * (double)0.2f, f, 0.8f, 14 - getWorld().getDifficulty().getId() * 4);
        getWorld().spawnEntity(pebbleEntity);
        this.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.05f + 0.8f));
    }
}
