package net.jukoz.me.entity.orcs.wild;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.orcs.OrcNpcEntity;
import net.jukoz.me.resources.MiddleEarthFactions;
import net.jukoz.me.resources.MiddleEarthRaces;
import net.jukoz.me.resources.datas.npcs.data.NpcRank;
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
import net.minecraft.world.WorldAccess;
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
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.125f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.65)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0);
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
            if(world.getLightLevel(getBlockPos()) < 5){ // add Y 
                return false;
            }
        }
        return super.canSpawn(world, spawnReason);
    }

    @Override
    public boolean isPersistent() {
        return false;
    }
}
