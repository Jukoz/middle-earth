package net.jukoz.me.entity.humans.bandit;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.dale.DaleHumanEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.humans.rohan.RohanHumanEntity;
import net.jukoz.me.entity.orcs.isengard.IsengardOrcEntity;
import net.jukoz.me.entity.orcs.misties.MistyGoblinEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.orcs.wild.WildGoblinEntity;
import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.jukoz.me.entity.uruks.isengard.IsengardUrukHaiEntity;
import net.jukoz.me.entity.uruks.misties.MistyHobgoblinEntity;
import net.jukoz.me.entity.uruks.mordor.MordorBlackUrukEntity;
import net.jukoz.me.resources.MiddleEarthFactions;
import net.jukoz.me.resources.MiddleEarthRaces;
import net.jukoz.me.resources.datas.npcs.data.NpcRank;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BanditHumanEntity extends NpcEntity{

    public BanditHumanEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("militia")){
            this.setRank(NpcRank.MILITIA);
            this.setBow(Items.BOW);
        } else if (name.contains("soldier")) {
            this.setRank(NpcRank.SOLDIER);
            this.setBow(Items.BOW);
        }else if (name.contains("chieftain")) {
            this.setRank(NpcRank.KNIGHT);
        }
    }

    @Override
    protected Identifier getFactionId() {
        return MiddleEarthFactions.BANDIT.getId();
    }
    @Override
    protected Identifier getRaceId() { return MiddleEarthRaces.HUMAN.getId(); }
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
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }
    public static DefaultAttributeContainer.Builder setKnightAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 22.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.5);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        int index = 4;
        index = initNeutralTargetSelector(index);
        this.targetSelector.add(++index, new ActiveTargetGoal<>(this, WildGoblinEntity.class, true));
    }

    @Override
    protected void applyDamage(DamageSource source, float amount) {
        if(source.getAttacker() instanceof BanditHumanEntity){
            return;
        }
        super.applyDamage(source, amount);
    }

    public BanditHumanVariant getVariant() {
        return BanditHumanVariant.byId(this.getId());
    }
}
