package net.jukoz.me.client.screens.controllers;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.network.packets.C2S.PacketSetAffiliation;
import net.jukoz.me.network.packets.C2S.PacketTeleportToCustomCoordinate;
import net.jukoz.me.network.packets.C2S.PacketTeleportToDynamicCoordinate;
import net.jukoz.me.network.packets.C2S.PacketSetSpawnData;
import net.jukoz.me.resources.ModFactionRegistry;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.faction.Faction;
import net.jukoz.me.resources.datas.faction.utils.SpawnDataHandler;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2i;

import java.util.*;

public class FactionSelectionController {
    private Map<Alignment, List<Faction>> factions = new HashMap<>();
    /**
     * Identifier and if the spawn data is from the dynamic pool. True(Dynamic) : False(Custom)
     */
    private Map<Identifier, Boolean> spawns = new HashMap<>();

    private int currentAlignementIndex;
    private int currentFactionIndex;
    private int currentSubFactionIndex;
    private int currentSpawnIndex;

    public FactionSelectionController(){
        factions.put(Alignment.GOOD, ModFactionRegistry.getFactionsByAlignment(Alignment.GOOD).values().stream().toList());
        factions.put(Alignment.NEUTRAL, ModFactionRegistry.getFactionsByAlignment(Alignment.NEUTRAL).values().stream().toList());
        factions.put(Alignment.EVIL, ModFactionRegistry.getFactionsByAlignment(Alignment.EVIL).values().stream().toList());
        updateSpawnList();
    }

    private void updateSpawnList() {
        Faction currentFaction = getCurrentlySelectedFaction();
        if(currentFaction == null) return;
        SpawnDataHandler foundSpawnDataHandler = currentFaction.getSpawnData();
        if(foundSpawnDataHandler == null) return;
        spawns = foundSpawnDataHandler.getSpawnList();
        currentSpawnIndex = 0;
    }


    public int randomizeFaction(int tentativeLeft){
        Random random = new Random();
        // Alignment randomizer
        currentAlignementIndex = random.nextInt(Alignment.values().length);
        Alignment alignment = Alignment.values()[currentAlignementIndex];

        // Recursive trigger
        if(factions.get(alignment) == null || factions.get(alignment).isEmpty()){
            if(tentativeLeft > 0){
                return tentativeLeft + randomizeFaction(tentativeLeft - 1);
            }
        }

        // Faction randomizer
        currentFactionIndex =
                (factions.get(alignment) == null || factions.get(alignment).isEmpty())
                        ? 0
                        : random.nextInt(factions.get(alignment).size());
        Faction faction =
                (factions.get(alignment) == null || factions.get(alignment).isEmpty() || currentFactionIndex >= factions.get(alignment).size())
                        ? null
                        : factions.get(alignment).get(currentFactionIndex);

        // Subfaction randomizer
        currentSubFactionIndex =
                (faction == null || faction.getSubFactions() == null || faction.getSubFactions().isEmpty())
                        ? 0
                        : random.nextInt(faction.getSubFactions().size());
        updateSpawnList();
        return 0;
    }

    public void alignmentUpdate(boolean add) {
        if(add){
            currentAlignementIndex++;
            if(currentAlignementIndex >= Alignment.values().length)
                currentAlignementIndex = 0;
            currentFactionIndex = 0;
            currentSubFactionIndex = 0;
        }
        else{
            currentAlignementIndex --;
            if(currentAlignementIndex < 0)
                currentAlignementIndex = Alignment.values().length - 1;

            currentFactionIndex = 0;
            currentSubFactionIndex = 0;
        }
        updateSpawnList();
    }

    public void factionUpdate(boolean add) {
        if(add){
            currentFactionIndex++;
            if(currentFactionIndex >= factions.get(getCurrentAlignment()).size())
                currentFactionIndex = 0;
            currentSubFactionIndex = 0;
        }
        else{
            currentFactionIndex--;
            if(currentFactionIndex < 0)
                currentFactionIndex = factions.get(getCurrentAlignment()).size() - 1;
            currentSubFactionIndex = 0;
        }
        updateSpawnList();
    }

    public void subfactionUpdate(boolean add){
        if(add){
            currentSubFactionIndex++;
            if(currentSubFactionIndex >= getCurrentFaction().getSubFactions().size())
                currentSubFactionIndex = 0;
        }
        else{
            currentSubFactionIndex--;
            if(currentSubFactionIndex < 0)
                currentSubFactionIndex = getCurrentFaction().getSubFactions().size() - 1;
        }
        updateSpawnList();
    }

    public void spawnIndexUpdate(boolean add){
        if(add){
            currentSpawnIndex++;
            if(currentSpawnIndex >= spawns.size())
                currentSpawnIndex = 0;
        }
        else{
            currentSpawnIndex--;
            if(currentSpawnIndex < 0)
                currentSpawnIndex = spawns.size() - 1;
        }
    }

    private Identifier getCurrentSpawnIdentifier(){
        return spawns.keySet().stream().toList().get(currentSpawnIndex);
    }
    public String getCurrentSpawnKey(){
        Identifier spawnId = getCurrentSpawnIdentifier();
        return SpawnDataHandler.getTranslatableKey(spawnId);
    }

    public void confirmSpawnSelection(AbstractClientPlayerEntity player){
        Faction faction = getCurrentlySelectedFaction();
        if(faction == null || spawns == null || spawns.isEmpty()) return;

        Identifier currentSpawnIdentifier = getCurrentSpawnIdentifier();
        if(spawns.get(currentSpawnIdentifier)){
            Vector2i coordinates = getCurrentlySelectedFaction().getSpawnData().findDynamicSpawn(currentSpawnIdentifier);
            ClientPlayNetworking.send(new PacketTeleportToDynamicCoordinate(coordinates.x, coordinates.y));
        } else {
            Vec3d coordinates = getCurrentlySelectedFaction().getSpawnData().findCustomSpawn(currentSpawnIdentifier);
            ClientPlayNetworking.send(new PacketTeleportToCustomCoordinate(coordinates.x, coordinates.y, coordinates.z));
        }

        ClientPlayNetworking.send(new PacketSetAffiliation(getCurrentAlignment().name(), getCurrentlySelectedFaction().getName(), currentSpawnIdentifier.getPath()));
        if(player != null){
            BlockPos overworldBlockPos = player.getBlockPos();
            ClientPlayNetworking.send(new PacketSetSpawnData(overworldBlockPos.getX(), overworldBlockPos.getY(), overworldBlockPos.getZ()));
        }
    }

    public Alignment getCurrentAlignment(){
        return Alignment.values()[currentAlignementIndex];
    }
    public Faction getCurrentFaction(){
        Alignment alignment = getCurrentAlignment();
        return (!factions.get(alignment).isEmpty()) ? factions.get(alignment).get(currentFactionIndex) : null;
    }

    public Faction getCurrentSubfaction(){
        Faction faction = getCurrentFaction();
        if(faction == null) return null;
        return faction.getSubfaction(currentSubFactionIndex);
    }

    public Faction getCurrentlySelectedFaction(){
        Faction faction = getCurrentFaction();
        Faction subfaction = getCurrentSubfaction();
        if(subfaction != null)
            faction = subfaction;

        return faction;
    }

    public boolean haveSubfaction(){
        return getCurrentSubfaction() != null;
    }

    public int getCurrentAlignmentFactionCount(){
        return factions.get(getCurrentAlignment()).size();
    }

    // Todo : possibily remove this method to have a more precise getter
    public Map<Alignment, List<Faction>> getFactions() {
        return factions;
    }
}
