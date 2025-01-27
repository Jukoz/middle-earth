package net.sevenstars.middleearth.entity.orcs.isengard;

import net.sevenstars.middleearth.entity.NpcEntity;
import net.sevenstars.middleearth.entity.orcs.OrcNpcEntity;
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
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IsengardOrcEntity extends OrcNpcEntity {
    public IsengardOrcEntity(EntityType<? extends NpcEntity> entityType, World world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("snaga")){
            this.setRank(NpcRank.MILITIA);
            this.setBow(Items.BOW);
        } else if (name.contains("warrior")) {
            this.setRank(NpcRank.SOLDIER);
            this.setBow(Items.BOW);
        }
    }
    @Override
    protected Identifier getFactionId() {
        return MiddleEarthFactions.ISENGARD.getId();
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
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        int index = 4;
        initNeutralTargetSelector(index);
    }
    @Override
    protected void applyDamage(DamageSource source, float amount) {
        if(source.getAttacker() instanceof IsengardOrcEntity){
            return;
        }
        super.applyDamage(source, amount);
    }
    public IsengardOrcVariant getVariant() {
        return IsengardOrcVariant.byId(this.getId());
    }
}
