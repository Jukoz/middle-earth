package net.sevenstars.middleearth.resources.datas.structure_manager_datas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.Optional;
import java.util.Random;

/// StructureSpawnNestPool is a list of npcs with spawn parameters such as weight, category, etc.
public class StructureSpawnNestPool {
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
        this.weight = weight;
        this.amount = amount;
        this.factionKey = factionKey;
        this.npcIdentifier = npcIdentifier;

        this.maxAmount = maxAmount;
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
        this.amount = amount;
        return this;
    }
    public StructureSpawnNestPool SetRangeAmount(int minAmount, int maxAmount){
        this.amount = minAmount;
        this.maxAmount = Optional.of(maxAmount);
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
    public int getEntityAmount(){
        if(maxAmount.isPresent()){
            Random random = new Random();
            return random.nextInt(amount, maxAmount.get() + 1);
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
