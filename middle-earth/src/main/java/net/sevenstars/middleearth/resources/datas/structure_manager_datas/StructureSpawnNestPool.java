package net.sevenstars.middleearth.resources.datas.structure_manager_datas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

import java.util.Optional;

/// StructureSpawnNestPool is a list of npcs with spawn parameters such as weight, category, etc.
public class StructureSpawnNestPool {
    public static final Codec<StructureSpawnNestPool> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("npc_identifier").forGetter(StructureSpawnNestPool::getNpcIdentifier),
            Codec.INT.fieldOf("weight").forGetter(StructureSpawnNestPool::getWeight),
            Codec.INT.fieldOf("amount").forGetter(StructureSpawnNestPool::getAmount),
            Codec.INT.optionalFieldOf("max_amount").forGetter(StructureSpawnNestPool::getMaxAmount)
        ).apply(instance, StructureSpawnNestPool::new));

    private final Identifier npcIdentifier;
    private int weight;
    private int amount;
    private Optional<Integer> maxAmount;

    private StructureSpawnNestPool(Identifier npcIdentifier, int weight, int amount, Optional<Integer> maxAmount) {
        this.npcIdentifier = npcIdentifier;
        this.weight = 1;
        this.amount = amount;
        this.maxAmount = maxAmount;
    }

    private int getAmount(){
        return this.amount;
    }
    private Optional<Integer> getMaxAmount(){
        return this.maxAmount;
    }

    public StructureSpawnNestPool(Identifier npcIdentifier, int weight){
        this.npcIdentifier = npcIdentifier;
        this.weight = weight;
        this.amount = 1;
        this.maxAmount = Optional.empty();
    }

    public StructureSpawnNestPool SetFixAmount(int amount){
        this.maxAmount = Optional.empty();
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

    public Identifier getNpcIdentifier() {
        return this.npcIdentifier;
    }

    public int getWeight() {
        return this.weight;
    }
}
