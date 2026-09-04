package net.sevenstars.ofhallsandheralds.registries.services;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.ofhallsandheralds.dtos.faction.Faction;
import net.sevenstars.ofhallsandheralds.dtos.playerdata.PlayerDataHolderHH;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FactionService {
    public static RegistryKey<Faction> createKey(Identifier key){
        return RegistryKey.of(DynamicRegistriesHH.FACTION, key);
    }
    public static RegistryEntry<Faction> fetchFaction(Item.TooltipContext context, Identifier factionIdentifier){
        return context.getRegistryLookup().getOrThrow(DynamicRegistriesHH.FACTION).getOrThrow(RegistryKey.of(DynamicRegistriesHH.FACTION, factionIdentifier));
    }
    public static Faction fetchFaction(World world, Identifier factionIdentifier){
        return world.getRegistryManager().getOrThrow(DynamicRegistriesHH.FACTION).get(createKey(factionIdentifier));
    }
    public static Faction fetchFaction(World world, RegistryKey<Faction> factionRegistryKey){
        return world.getRegistryManager().getOrThrow(DynamicRegistriesHH.FACTION).get(factionRegistryKey);
    }
    public static RegistryEntry<Faction> fetchFactionEntry(World world, Faction faction){
        return world.getRegistryManager().getOrThrow(DynamicRegistriesHH.FACTION).getEntry(faction);
    }

    public static void setFactionToPlayer(PlayerEntity playerEntity, RegistryEntry<Faction> faction){
        ((PlayerDataHolderHH) playerEntity).getPlayerDataHH().setFaction(faction.getKey().orElseThrow());
    }

    public static Optional<RegistryEntry<Faction>> getPlayerFaction(ServerPlayerEntity serverPlayerEntity) {
        return ((PlayerDataHolderHH) serverPlayerEntity).getPlayerDataHH().getCurrentFactionEntry(serverPlayerEntity.getWorld());
    }

    public static List<RegistryKey<Faction>> getAllFactionKeys(ServerWorld world) {
        return world.getRegistryManager().getOrThrow(DynamicRegistriesHH.FACTION).streamKeys().toList();
    }
    public static List<RegistryEntry.Reference<Faction>> getAllFactionEntries(RegistryWrapper.WrapperLookup lookup) {
        return lookup.getOrThrow(DynamicRegistriesHH.FACTION).streamEntries().toList();
    }

    public static List<RegistryEntry<Faction>> getAllJoinableFactionEntries(ServerWorld world) {
        Registry<Faction> factionRegistry = world.getRegistryManager().getOrThrow(DynamicRegistriesHH.FACTION);
        List<RegistryKey<Faction>> factionKeys = getAllFactionKeys(world);
        List<RegistryEntry<Faction>> availableFactions = new ArrayList<>();
        for(RegistryKey<Faction> key : factionKeys) {
            RegistryEntry<Faction> entry = factionRegistry.getEntry(key.getValue()).orElse(null);
            if(entry == null)
                continue;
            if(!entry.value().makeJoinable())
                continue;
            availableFactions.add(entry);
        }
        return availableFactions;
    }
}
