package net.sevenstars.middleearth.resources.datas.npc_types.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class MountPassengerData {
    public static class Fields {
        public static final String ENTITY_TYPE = "EntityType";
        public static final String NPC_TYPE = "EntityType_Npc";
        public static final String WEIGHT = "Weight";
        public static final String DISCARD_CHANCE = "DiscardChance";
    }

    public static final Codec<MountPassengerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf(MountPassengerData.Fields.ENTITY_TYPE).forGetter(MountPassengerData::getEntityType),
            Identifier.CODEC.optionalFieldOf(MountPassengerData.Fields.NPC_TYPE).forGetter(MountPassengerData::getOptionalNpcType),
            Codec.INT.optionalFieldOf(MountPassengerData.Fields.WEIGHT).forGetter(MountPassengerData::getOptionalWeight),
            Codec.DOUBLE.optionalFieldOf(MountPassengerData.Fields.DISCARD_CHANCE).forGetter(MountPassengerData::getDiscardChances)
    ).apply(instance, MountPassengerData::new));

    private final Identifier entityType;
    private Identifier npcType = null;
    private Integer weight = null;
    private Double discardChance = null;

    private List<MountPassengerSlotData> passengerSlots;

    private MountPassengerData(
            Identifier entityType,
            Optional<Identifier> npcType,
            Optional<Integer> weight,
            Optional<Double> discardChance) {
        this.entityType = entityType;
        this.npcType = npcType.orElse(null);
        this.weight = weight.orElse(null);
        this.discardChance = discardChance.orElse(null);
    }

    public MountPassengerData(EntityType<?> entityType){
        this.entityType = Registries.ENTITY_TYPE.getId(entityType);
    }

    public MountPassengerData(RegistryKey<NpcType> npcType){
        this.entityType = Registries.ENTITY_TYPE.getId(EntitiesME.NPC);
        this.npcType = npcType.getValue();
    }

    public Identifier getEntityType() {
        return entityType;
    }

    public Identifier getNpcType(Identifier defaultNpcType) {
        return npcType == null ? defaultNpcType : npcType;
    }

    private Optional<Identifier> getOptionalNpcType() {
        return npcType == null ? Optional.empty() : Optional.of(npcType);

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
            entity.setPosition(owner.getPos());
            if(entity instanceof NpcEntity npc){
                npc.prepareNpcIdentifier(npcType);
                npc.prepare();
            }
            return entity;
        }
        return null;
    }
}
