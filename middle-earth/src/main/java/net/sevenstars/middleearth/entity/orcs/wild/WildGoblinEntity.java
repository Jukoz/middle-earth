package net.sevenstars.middleearth.entity.orcs.wild;

import net.sevenstars.middleearth.entity.NpcEntity;
import net.sevenstars.middleearth.entity.humans.bandit.BanditHumanEntity;
import net.sevenstars.middleearth.entity.orcs.OrcNpcEntity;
import net.sevenstars.middleearth.resources.MiddleEarthFactions;
import net.sevenstars.middleearth.resources.MiddleEarthRaces;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

public class WildGoblinEntity extends OrcNpcEntity {
    public WildGoblinEntity(EntityType<? extends NpcEntity> entityType, World world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("wild")){
            this.setRank(NpcRank.SOLDIER);
        }
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        int index = 4;
        index = initNeutralTargetSelector(index);
        this.targetSelector.add(++index, new ActiveTargetGoal<>(this, BanditHumanEntity.class, true));
    }


    @Override
    protected Identifier getFactionId() {
        return MiddleEarthFactions.BANDIT.getId();
    }

    @Override
    protected Identifier getRaceId() { return MiddleEarthRaces.ORC.getId(); }

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
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.25);
    }
    @Override
    protected void applyDamage(DamageSource source, float amount) {
        if(source.getAttacker() instanceof WildGoblinEntity){
            return;
        }
        super.applyDamage(source, amount);
    }
    public WildGoblinVariant getVariant() {
        return WildGoblinVariant.byId(this.getId());
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        if(spawnReason == SpawnReason.NATURAL || spawnReason == SpawnReason.CHUNK_GENERATION ){
            if(world.getLightLevel(getBlockPos()) > 5 || getBlockY() > 24){
                return false;
            }
        }
        return super.canSpawn(world, spawnReason);
    }

    @Override
    protected void dropEquipment(ServerWorld world, DamageSource source, boolean causedByPlayer) {
        return;
    }

    @Override
    public void dropAllEquipment() {
        return;
    }

    @Override
    public boolean canSpawn(WorldView world) {
        return super.canSpawn(world);
    }

    @Override
    public boolean isPersistent() {
        return false;
    }
}
