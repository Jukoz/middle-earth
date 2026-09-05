package net.sevenstars.ofhallsandheralds.persistentdatas.reputation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.ofhallsandheralds.dtos.faction.Faction;
import net.sevenstars.ofhallsandheralds.dtos.reputation.Reputation;

import java.util.ArrayList;
import java.util.List;

public class ReputationPersistentData {
    private List<Reputation> reputations;

    public static final Codec<ReputationPersistentData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Reputation.CODEC.listOf().fieldOf("reputations").forGetter(ReputationPersistentData::getReputations)
    ).apply(instance, ReputationPersistentData::new));
    public static final PacketCodec<RegistryByteBuf, ReputationPersistentData> PACKET_CODEC = PacketCodec.tuple(
        Reputation.PACKET_CODEC.collect(PacketCodecs.toList()), ReputationPersistentData::getReputations,
        ReputationPersistentData::new);

    private List<Reputation> getReputations() {
        return reputations;
    }

    public ReputationPersistentData() {
        this.reputations = new ArrayList<>();
    }

    public ReputationPersistentData(List<Reputation> reputations) {
        this.reputations = reputations;
    }

    public void IncreaseReputationFor(RegistryKey<Faction> factionRegistryKey, int amount){
        Reputation reputation = findReputation(factionRegistryKey);
        reputation.increase(amount);
    }
    public void DecreaseReputationFor(RegistryKey<Faction> factionRegistryKey, int amount){
        Reputation reputation = findReputation(factionRegistryKey);
        reputation.decrease(amount);
    }
    public void DiscoverFaction(RegistryKey<Faction> factionRegistryKey){
        Reputation reputation = findReputation(factionRegistryKey);
        reputation.discover();
    }

    private Reputation findReputation(RegistryKey<Faction> factionRegistryKey) {
        return this.reputations.stream().filter(r -> r.getFaction().equals(factionRegistryKey))
                .findFirst()
                .orElseGet(() -> {
                    Reputation newReputation = new Reputation(factionRegistryKey);
                    this.reputations.add(newReputation);
                    return newReputation;
                });
    }
}
