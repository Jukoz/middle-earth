package net.sevenstars.middleearth.entity.uruks.mordor;

import net.sevenstars.middleearth.entity.NpcEntity;
import net.sevenstars.middleearth.entity.uruks.UrukNpcEntity;
import net.sevenstars.middleearth.resources.MiddleEarthFactions;
import net.sevenstars.middleearth.resources.MiddleEarthRaces;
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MordorBlackUrukEntity extends UrukNpcEntity {

    public MordorBlackUrukEntity(EntityType<? extends NpcEntity> entityType, World world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if (name.contains("soldier")) {
            this.setRank(NpcRank.KNIGHT);
        }else if (name.contains("veteran")) {
            this.setRank(NpcRank.VETERAN);
        }else if (name.contains("leader")) {
            this.setRank(NpcRank.LEADER);
        }
    }
    @Override
    protected Identifier getFactionId() {
        return MiddleEarthFactions.MORDOR.getId();
    }
    @Override
    protected Identifier getRaceId() { return MiddleEarthRaces.URUK.getId(); }
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
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.5);
    }
    public static DefaultAttributeContainer.Builder setVeteranAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0);
    }
    public static DefaultAttributeContainer.Builder setLeaderAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 26.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.5);
    }
    @Override
    protected void applyDamage(DamageSource source, float amount) {
        if(source.getAttacker() instanceof MordorBlackUrukEntity){
            return;
        }
        super.applyDamage(source, amount);
    }
    public MordorBlackUrukVariant getVariant() {
        return MordorBlackUrukVariant.byId(this.getId());
    }
}
