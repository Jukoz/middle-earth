package net.sevenstars.middleearth.resources.datas.structure_manager_datas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.Optional;

/// StructureSpawnNestPool is a list of npcs with spawn parameters such as weight, category, etc.
public class StructureSpawnNestPool {
    private static final int MAX_ENTITY_AMOUNT = 64;

    public static final Codec<StructureSpawnNestPool> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("entity_type").forGetter(StructureSpawnNestPool::getEntityType),
            Codec.INT.fieldOf("weight").forGetter(StructureSpawnNestPool::getWeight),
            Codec.INT.fieldOf("amount").forGetter(StructureSpawnNestPool::getAmount),
            ResourceKey.codec(DynamicRegistriesME.FACTION).optionalFieldOf("faction_key").forGetter(StructureSpawnNestPool::getFaction),
            ResourceLocation.CODEC.optionalFieldOf("npc_identifier").forGetter(StructureSpawnNestPool::getNpcIdentifier),
            Codec.INT.optionalFieldOf("max_amount").forGetter(StructureSpawnNestPool::getMaxAmount)
        ).apply(instance, StructureSpawnNestPool::new));

    private final EntityType<?> entityType;
    private int weight;
    private int amount;
    private Optional<ResourceKey<Faction>> factionKey;
    private Optional<ResourceLocation> npcIdentifier;
    private Optional<Integer> maxAmount;

    private StructureSpawnNestPool(EntityType entityType, int weight, int amount, Optional<ResourceKey<Faction>> factionKey, Optional<ResourceLocation> npcIdentifier, Optional<Integer> maxAmount) {
        this.entityType = entityType;
        this.weight = Math.max(1, weight);
        this.amount = Math.clamp(amount, 0, MAX_ENTITY_AMOUNT);
        this.factionKey = factionKey;
        this.npcIdentifier = npcIdentifier;
        this.maxAmount = maxAmount.map(value ->
                Math.clamp(value, this.amount, MAX_ENTITY_AMOUNT));
    }

    private int getAmount(){
        return this.amount;
    }

    private Optional<Integer> getMaxAmount(){
        return this.maxAmount;
    }

    public StructureSpawnNestPool(EntityType<?> entityType, int weight){
        this.entityType = entityType;
        this.weight = weight;
        this.amount = 1;
        this.factionKey = Optional.empty();
        this.npcIdentifier = Optional.empty();
        this.maxAmount = Optional.empty();
    }

    public StructureSpawnNestPool SetNpcData(ResourceKey<Faction> factionKey, ResourceKey<NpcType> npc){
        this.factionKey = Optional.of(factionKey);
        this.npcIdentifier = Optional.of(npc.location());
        return this;
    }
    public StructureSpawnNestPool SetFixAmount(int amount){
        this.maxAmount = Optional.empty();
        this.amount = Math.clamp(amount, 0, MAX_ENTITY_AMOUNT);
        return this;
    }
    public StructureSpawnNestPool SetRangeAmount(int minAmount, int maxAmount){
        int normalizedMin = Math.clamp(Math.min(minAmount, maxAmount), 0, MAX_ENTITY_AMOUNT);
        int normalizedMax = Math.clamp(Math.max(minAmount, maxAmount), normalizedMin, MAX_ENTITY_AMOUNT);
        this.amount = normalizedMin;
        this.maxAmount = Optional.of(normalizedMax);
        return this;
    }
    public StructureSpawnNestPool SetWeight(int weight) {
        if(weight <= 0)
            weight = 1;
        this.weight = weight;
        return this;
    }
    public EntityType<?> getEntityType() {
        return entityType;
    }
    public int getEntityAmount(RandomSource random){
        int maximum = maxAmount.orElse(amount);
        if(maximum > amount){
            return amount + random.nextInt(maximum - amount + 1);
        }
        return this.amount;
    }
    public Optional<ResourceLocation> getNpcIdentifier() {
        return this.npcIdentifier;
    }
    public Optional<ResourceKey<Faction>> getFaction() {
        return factionKey;
    }
    public int getWeight() {
        return this.weight;
    }
}
