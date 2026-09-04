package net.sevenstars.ofhallsandheralds.dtos.playerdata;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.ofhallsandheralds.dtos.faction.Faction;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

import java.util.Optional;

public class PlayerDataHH {
    private RegistryKey<Faction> faction;

    public static final Codec<PlayerDataHH> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryKey.createCodec(DynamicRegistriesHH.FACTION).optionalFieldOf("faction").forGetter(PlayerDataHH::getOptionalFactionEntry)
    )
    .apply(instance, PlayerDataHH::new));

    private PlayerDataHH(Optional<RegistryKey<Faction>> faction) {
        this.faction = faction.orElse(null);
    }

    public PlayerDataHH() {
        this.faction = faction;
    }

    public void copyFrom(PlayerDataHH other) {
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
