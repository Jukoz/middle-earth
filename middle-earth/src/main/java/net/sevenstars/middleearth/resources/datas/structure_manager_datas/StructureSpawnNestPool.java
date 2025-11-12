package net.sevenstars.middleearth.resources.datas.structure_manager_datas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.resources.FactionsME;
import net.sevenstars.middleearth.resources.datas.factions.Faction;

import java.util.Optional;
import java.util.Random;

/// StructureSpawnNestPool is a list of npcs with spawn parameters such as weight, category, etc.
public class StructureSpawnNestPool {
    public static final Codec<StructureSpawnNestPool> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EntityType.CODEC.fieldOf("entity_type").forGetter(StructureSpawnNestPool::getEntityType),
            Codec.INT.fieldOf("weight").forGetter(StructureSpawnNestPool::getWeight),
            Codec.INT.fieldOf("amount").forGetter(StructureSpawnNestPool::getAmount),
            RegistryKey.createCodec(FactionsME.KEY).optionalFieldOf("faction_key").forGetter(StructureSpawnNestPool::getFaction),
            Identifier.CODEC.optionalFieldOf("npc_identifier").forGetter(StructureSpawnNestPool::getNpcIdentifier),
            Codec.INT.optionalFieldOf("max_amount").forGetter(StructureSpawnNestPool::getMaxAmount)
        ).apply(instance, StructureSpawnNestPool::new));

    private final EntityType<?> entityType;
    private int weight;
    private int amount;
    private Optional<RegistryKey<Faction>> factionKey;
    private Optional<Identifier> npcIdentifier;
    private Optional<Integer> maxAmount;

    private StructureSpawnNestPool(EntityType entityType, int weight, int amount, Optional<RegistryKey<Faction>> factionKey, Optional<Identifier> npcIdentifier, Optional<Integer> maxAmount) {
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

    public StructureSpawnNestPool SetNpcData(RegistryKey<Faction> factionKey, Identifier npcIdentifier){
        this.factionKey = Optional.of(factionKey);
        this.npcIdentifier = Optional.of(npcIdentifier);
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
            return random.nextInt(amount, (maxAmount.get() + 1));
        }
        return this.amount;
    }
    public Optional<Identifier> getNpcIdentifier() {
        return this.npcIdentifier;
    }
    public Optional<RegistryKey<Faction>> getFaction() {
        return factionKey;
    }
    public int getWeight() {
        return this.weight;
    }
}
