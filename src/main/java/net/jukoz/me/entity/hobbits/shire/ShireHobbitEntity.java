package net.jukoz.me.entity.hobbits.shire;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.humans.rohan.RohanHumanEntity;
import net.jukoz.me.entity.orcs.misties.MistyGoblinEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.projectile.pebble.PebbleEntity;
import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.uruks.misties.MistyHobgoblinEntity;
import net.jukoz.me.entity.uruks.mordor.MordorBlackUrukEntity;
import net.jukoz.me.item.ModResourceItems;
import net.jukoz.me.item.items.weapons.ranged.PebbleItem;
import net.jukoz.me.resources.MiddleEarthFactions;
import net.jukoz.me.resources.MiddleEarthRaces;
import net.jukoz.me.resources.datas.npcs.data.NpcRank;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ShireHobbitEntity extends NpcEntity {
    private static float FLEE_DISTANCE = 8f;
    private static float FLEE_SPEED_MIN = 0.8f;
    private static float FLEE_SPEED_MAX = 1.2f;

    public ShireHobbitEntity(EntityType<? extends NpcEntity> entityType, World world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("civilian")){
            this.setRank(NpcRank.CIVILIAN);
        }else if(name.contains("bounder")){
            this.setRank(NpcRank.MILITIA);
            this.setBow(Items.BOW);
        }else if (name.contains("shirriff")) {
            this.setRank(NpcRank.SOLDIER);
        }
    }
    @Override
    protected Identifier getFactionId() {
        return MiddleEarthFactions.SHIRE.getId();
    }
    @Override
    protected Identifier getRaceId() { return MiddleEarthRaces.HOBBIT.getId(); }
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
        this.goalSelector.add(++i, new ProjectileAttackGoal(this, 1.0, 12, 24, 20.0f));
        this.goalSelector.add(++i, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(++i, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(++i, new LookAroundGoal(this));
        i = 1;
        this.targetSelector.add(++i, new RevengeGoal(this).setGroupRevenge());
        initGoodTargetSelector(i);
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

    public ShireHobbitVariant getVariant() {
        return ShireHobbitVariant.byId(this.getId());
    }

    @Override
    protected void applyDamage(DamageSource source, float amount) {
        if(source.getAttacker() instanceof ShireHobbitEntity){
            return;
        }
        super.applyDamage(source, amount);
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
