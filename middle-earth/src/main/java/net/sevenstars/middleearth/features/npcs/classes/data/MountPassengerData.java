package net.sevenstars.middleearth.features.npcs.classes.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.classes.NpcClass;

import java.util.Optional;
import java.util.Random;

public class MountPassengerData {
    public static class Fields {
        public static final String ENTITY_TYPE = "entity_type";
        public static final String NPC_CLASS = "npc_type";
        public static final String WEIGHT = "weight";
        public static final String DISCARD_CHANCE = "discard_chance";
    }

    public static final Codec<MountPassengerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf(MountPassengerData.Fields.ENTITY_TYPE).forGetter(MountPassengerData::getEntityType),
            Identifier.CODEC.optionalFieldOf(MountPassengerData.Fields.NPC_CLASS).forGetter(MountPassengerData::getOptionalNpcClass),
            Codec.INT.optionalFieldOf(MountPassengerData.Fields.WEIGHT).forGetter(MountPassengerData::getOptionalWeight),
            Codec.DOUBLE.optionalFieldOf(MountPassengerData.Fields.DISCARD_CHANCE).forGetter(MountPassengerData::getDiscardChances)
    ).apply(instance, MountPassengerData::new));

    private final Identifier entityType;
    private Identifier npcClass = null;
    private Integer weight = null;
    private Double discardChance = null;

    private MountPassengerData(
            Identifier entityType,
            Optional<Identifier> npcClass,
            Optional<Integer> weight,
            Optional<Double> discardChance) {
        this.entityType = entityType;
        this.npcClass = npcClass.orElse(null);
        this.weight = weight.orElse(null);
        this.discardChance = discardChance.orElse(null);
    }

    public MountPassengerData(EntityType<?> entityType){
        this.entityType = Registries.ENTITY_TYPE.getId(entityType);
    }

    public MountPassengerData(RegistryKey<NpcClass> npcClass){
        this.entityType = Registries.ENTITY_TYPE.getId(EntitiesME.NPC);
        this.npcClass = npcClass.getValue();
    }

    public Identifier getEntityType() {
        return entityType;
    }

    public Identifier getNpcClass(Identifier defaultNpcClass) {
        return npcClass == null ? defaultNpcClass : npcClass;
    }

    private Optional<Identifier> getOptionalNpcClass() {
        return npcClass == null ? Optional.empty() : Optional.of(npcClass);

    }

    public Integer getWeight(int defaultValue) {
        return weight == null ? defaultValue : weight;
    }

    private Optional<Integer> getOptionalWeight() {
        return weight == null || weight == 1 ? Optional.empty() : Optional.of(weight);
    }

    public MountPassengerData withWeight(int newWeight) {
        this.weight = newWeight;
        return this;
    }

    public MountPassengerData withDiscardChance(double chance){
        this.discardChance = chance;
        return this;
    }

    private Optional<Double> getDiscardChances() {
        return Optional.ofNullable(discardChance);
    }

    public boolean isDiscarded(Random random) {
        if(discardChance == null)
            return false;
        double obtained = random.nextDouble();
        return obtained <= discardChance;
    }

    public LivingEntity createEntity(ServerWorld serverWorld, LivingEntity owner) {
        EntityType<?> type = Registries.ENTITY_TYPE.get(this.entityType);
        var notLiving = type.create(serverWorld, SpawnReason.NATURAL);
        if(notLiving == null)
            return null;
        if(notLiving instanceof LivingEntity entity){
            serverWorld.spawnEntity(entity);
            entity.setPosition(owner.getEntityPos());
            if(entity instanceof NpcEntity npc){
                npc.prepareNpcIdentifier(npcClass);
                npc.prepare();
            }
            if (entity instanceof MobEntity mob) {
                mob.initialize(
                        serverWorld,
                        serverWorld.getLocalDifficulty(owner.getBlockPos()),
                        SpawnReason.EVENT,
                        null
                );
            }

            return entity;
        }
        return null;
    }
}
