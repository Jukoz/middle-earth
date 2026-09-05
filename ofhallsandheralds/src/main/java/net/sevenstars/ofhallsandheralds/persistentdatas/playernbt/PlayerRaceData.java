package net.sevenstars.ofhallsandheralds.persistentdatas.playernbt;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.ofhallsandheralds.dtos.Faction;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

import java.util.Optional;

public class PlayerRaceData {
    private RegistryKey<Faction> faction;

    public static final Codec<PlayerRaceData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryKey.createCodec(DynamicRegistriesHH.FACTION).optionalFieldOf("faction").forGetter(PlayerRaceData::getOptionalFactionEntry)
    )
    .apply(instance, PlayerRaceData::new));

    private PlayerRaceData(Optional<RegistryKey<Faction>> faction) {
        this.faction = faction.orElse(null);
    }

    public PlayerRaceData() {
        this.faction = faction;
    }

    public void copyFrom(PlayerRaceData other) {
        this.faction = other.faction;
    }

    public Optional<RegistryKey<Faction>> getOptionalFactionEntry() {
        return Optional.ofNullable(this.faction);
    }

    public void setFaction(RegistryKey<Faction> faction){
        this.faction = faction;
    }

    public Optional<RegistryEntry<Faction>> getCurrentFactionEntry(ServerWorld world) {
        if(this.faction == null)
            return Optional.empty();

        Registry<Faction> registry = world.getRegistryManager().getOrThrow(DynamicRegistriesHH.FACTION);
        return Optional.ofNullable(registry.getEntry(registry.get(this.faction.getValue())));
    }
}
