package net.sevenstars.middleearth.resources.datas.npc_types.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class MountPassengerData {
    public static class Fields {
        public static final String ENTITY_TYPE = "entity_type";
        public static final String NPC_TYPE = "npc_type";
        public static final String WEIGHT = "weight";
        public static final String DISCARD_CHANCE = "discard_chance";
    }

    public static final Codec<MountPassengerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf(MountPassengerData.Fields.ENTITY_TYPE).forGetter(MountPassengerData::getEntityType),
            ResourceLocation.CODEC.optionalFieldOf(MountPassengerData.Fields.NPC_TYPE).forGetter(MountPassengerData::getOptionalNpcType),
            Codec.INT.optionalFieldOf(MountPassengerData.Fields.WEIGHT).forGetter(MountPassengerData::getOptionalWeight),
            Codec.DOUBLE.optionalFieldOf(MountPassengerData.Fields.DISCARD_CHANCE).forGetter(MountPassengerData::getDiscardChances)
    ).apply(instance, MountPassengerData::new));

    private final ResourceLocation entityType;
    private ResourceLocation npcType = null;
    private Integer weight = null;
    private Double discardChance = null;

    private List<MountPassengerSlotData> passengerSlots;

    private MountPassengerData(
            ResourceLocation entityType,
            Optional<ResourceLocation> npcType,
            Optional<Integer> weight,
            Optional<Double> discardChance) {
        this.entityType = entityType;
        this.npcType = npcType.orElse(null);
        this.weight = weight.orElse(null);
        this.discardChance = discardChance.orElse(null);
    }

    public MountPassengerData(EntityType<?> entityType){
        this.entityType = BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
    }

    public MountPassengerData(ResourceKey<NpcType> npcType){
        this.entityType = BuiltInRegistries.ENTITY_TYPE.getKey(EntitiesME.NPC);
        this.npcType = npcType.location();
    }

    public ResourceLocation getEntityType() {
        return entityType;
    }

    public ResourceLocation getNpcType(ResourceLocation defaultNpcType) {
        return npcType == null ? defaultNpcType : npcType;
    }

    private Optional<ResourceLocation> getOptionalNpcType() {
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

    public LivingEntity createEntity(ServerLevel serverWorld, LivingEntity owner) {
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(this.entityType);
        var notLiving = type.create(serverWorld);
        if(notLiving == null)
            return null;
        if(notLiving instanceof LivingEntity entity){
            serverWorld.addFreshEntity(entity);
            entity.setPos(owner.position());
            if(entity instanceof NpcEntity npc){
                npc.prepareNpcIdentifier(npcType);
                npc.prepare();
            }
            if (entity instanceof Mob mob) {
                mob.finalizeSpawn(
                        serverWorld,
                        serverWorld.getCurrentDifficultyAt(owner.blockPosition()),
                        MobSpawnType.EVENT,
                        null
                );
            }

            return entity;
        }
        return null;
    }
}
