package net.sevenstars.middleearth.entity.elves.galadhrim;

import net.sevenstars.middleearth.entity.NpcEntity;
import net.sevenstars.middleearth.item.ModWeaponItems;
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
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GaladhrimElfEntity extends NpcEntity{

    public GaladhrimElfEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        String name = this.getDefaultName().toString();
        if(name.contains("militia")){
            this.setRank(NpcRank.MILITIA);
            this.setBow(Items.BOW);
        } else if (name.contains("soldier")) {
            this.setRank(NpcRank.SOLDIER);
            this.setBow(ModWeaponItems.LORIEN_BOW);
        }else if (name.contains("knight")) {
            this.setRank(NpcRank.KNIGHT);
        }else if (name.contains("veteran")) {
            this.setRank(NpcRank.VETERAN);
        }else if (name.contains("leader")) {
            this.setRank(NpcRank.LEADER);
        }
    }
    @Override
    protected Identifier getFactionId() {
        return MiddleEarthFactions.LOTHLORIEN.getId();
    }
    @Override
    protected Identifier getRaceId() { return MiddleEarthRaces.ELF.getId(); }

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
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
    }
    public static DefaultAttributeContainer.Builder setKnightAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 27.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.5);
    }
    public static DefaultAttributeContainer.Builder setVeteranAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 29.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0);
    }
    public static DefaultAttributeContainer.Builder setLeaderAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 31.0)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.5)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.5);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        int i = 2;
        initGoodTargetSelector(i);
    }
    @Override
    protected void applyDamage(DamageSource source, float amount) {
        if(source.getAttacker() instanceof GaladhrimElfEntity){
            return;
        }
        super.applyDamage(source, amount);
    }
    public GaladhrimElfVariant getVariant() {
        return GaladhrimElfVariant.byId(this.getId());
    }
}
