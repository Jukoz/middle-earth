package net.sevenstars.ofhallsandheralds.persistentdatas.reputation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.sevenstars.ofhallsandheralds.dtos.Faction;
import net.sevenstars.ofhallsandheralds.dtos.Reputation;

import java.util.ArrayList;
import java.util.List;

public class reputationPersistentData {
    private List<Reputation> reputations;

    public static final Codec<reputationPersistentData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Reputation.CODEC.listOf().fieldOf("reputations").forGetter(reputationPersistentData::getReputations)
    ).apply(instance, reputationPersistentData::new));
    public static final PacketCodec<RegistryByteBuf, reputationPersistentData> PACKET_CODEC = PacketCodec.tuple(
        Reputation.PACKET_CODEC.collect(PacketCodecs.toList()), reputationPersistentData::getReputations,
        reputationPersistentData::new);

    private List<Reputation> getReputations() {
        return reputations;
    }

    public reputationPersistentData() {
        this.reputations = new ArrayList<>();
    }

    public reputationPersistentData(List<Reputation> reputations) {
        this.reputations = reputations;
    }

    public void setReputationFor(RegistryKey<Faction> factionRegistryKey, int amount){
        Reputation reputation = fetchReputation(factionRegistryKey);
        reputation.set(amount);
    }

    public void increaseReputationFor(RegistryKey<Faction> factionRegistryKey, int amount){
        Reputation reputation = fetchReputation(factionRegistryKey);
        reputation.increase(amount);
    }
    public void decreaseReputationFor(RegistryKey<Faction> factionRegistryKey, int amount){
        Reputation reputation = fetchReputation(factionRegistryKey);
        reputation.decrease(amount);
    }
    public void discoverFaction(RegistryKey<Faction> factionRegistryKey){
        Reputation reputation = fetchReputation(factionRegistryKey);
        reputation.discover();
    }

    public Reputation getReputationValue(RegistryKey<Faction> factionRegistryKey){
        return this.reputations.stream().filter(r -> r.getFaction().equals(factionRegistryKey))
                .findFirst().orElse(null);
    }

    private Reputation fetchReputation(RegistryKey<Faction> factionRegistryKey) {
        return this.reputations.stream().filter(r -> r.getFaction().equals(factionRegistryKey))
                .findFirst()
                .orElseGet(() -> {
                    Reputation newReputation = new Reputation(factionRegistryKey);
                    this.reputations.add(newReputation);
                    return newReputation;
                });
    }

    public boolean isFactionKnown(RegistryEntry<Faction> faction) {
        return this.reputations.stream().anyMatch(r -> r.getFaction().equals(faction.getKey().orElseThrow()));
    }
}
