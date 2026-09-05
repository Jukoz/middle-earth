package net.sevenstars.ofhallsandheralds.persistentdatas;

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

public class PlayerPersistentData {
    private List<Reputation> reputations;

    public static final Codec<PlayerPersistentData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Reputation.CODEC.listOf().fieldOf("reputations").forGetter(PlayerPersistentData::getReputations)
    ).apply(instance, PlayerPersistentData::new));
    public static final PacketCodec<RegistryByteBuf, PlayerPersistentData> PACKET_CODEC = PacketCodec.tuple(
        Reputation.PACKET_CODEC.collect(PacketCodecs.toList()), PlayerPersistentData::getReputations,
        PlayerPersistentData::new);

    private List<Reputation> getReputations() {
        return reputations;
    }

    public PlayerPersistentData() {
        this.reputations = new ArrayList<>();
    }

    public PlayerPersistentData(List<Reputation> reputations) {
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
