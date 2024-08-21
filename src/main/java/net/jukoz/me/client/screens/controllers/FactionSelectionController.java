package net.jukoz.me.client.screens.controllers;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.network.packets.c2s.AffiliationPacket;
import net.jukoz.me.network.packets.c2s.SpawnDataPacket;
import net.jukoz.me.network.packets.c2s.TeleportRequestPacket;
import net.jukoz.me.resources.ModFactionRegistry;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.faction.Faction;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector2i;
import org.joml.Vector3d;

import java.util.*;

public class FactionSelectionController {
    private Map<Alignment, List<Faction>> factions = new HashMap<>();
    private int currentAlignementIndex;
    private int currentFactionIndex;
    private int currentSubFactionIndex;
    private int currentSpawnIndex;

    public FactionSelectionController(){
        factions.put(Alignment.GOOD, ModFactionRegistry.getFactionsByAlignment(Alignment.GOOD).values().stream().toList());
        factions.put(Alignment.NEUTRAL, ModFactionRegistry.getFactionsByAlignment(Alignment.NEUTRAL).values().stream().toList());
        factions.put(Alignment.EVIL, ModFactionRegistry.getFactionsByAlignment(Alignment.EVIL).values().stream().toList());
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
    }

    public void factionUpdate(boolean add) {
        if(add){
            currentFactionIndex++;
            if(currentFactionIndex >= factions.get(Alignment.values()[currentAlignementIndex]).size())
                currentFactionIndex = 0;
            currentSubFactionIndex = 0;
        }
        else{
            currentFactionIndex--;
            if(currentFactionIndex < 0)
                currentFactionIndex = factions.get(Alignment.values()[currentAlignementIndex]).size() - 1;
            currentSubFactionIndex = 0;
        }
    }

    public void subfactionUpdate(boolean add){
        if(add){
            currentSubFactionIndex++;
            if(currentSubFactionIndex >= factions.get(Alignment.values()[currentAlignementIndex]).get(currentFactionIndex).getSubFactions().size())
                currentSubFactionIndex = 0;
        }
        else{
            currentSubFactionIndex--;
            if(currentSubFactionIndex < 0)
                currentSubFactionIndex = factions.get(Alignment.values()[currentAlignementIndex]).get(currentFactionIndex).getSubFactions().size() - 1;
        }
    }

    public void confirmSpawnSelection(AbstractClientPlayerEntity player){
        Faction faction = getCurrentlySelectedFaction();
        if(faction == null || (faction.getSpawnData().getDynamicSpawns().isEmpty() && faction.getSpawnData().getCustomSpawns().isEmpty())) return;

        Vector2i dynamicSpawn = getCurrentSpawn().values().stream().toList().get(currentSpawnIndex);
        int x = dynamicSpawn.x;
        int z = dynamicSpawn.y;
        
        ClientPlayNetworking.send(new TeleportRequestPacket(x, z));
        ClientPlayNetworking.send(new AffiliationPacket(getCurrentAlignment().name(), getCurrentlySelectedFaction().getName(), getCurrentSpawn().keySet().stream().toList().get(0).getPath()));
        if(player != null){
            BlockPos overworldBlockPos = player.getBlockPos();
            ClientPlayNetworking.send(new SpawnDataPacket(overworldBlockPos.getX(), overworldBlockPos.getY(), overworldBlockPos.getZ()));
        }
    }

    private HashMap<Identifier, Vector2i> getCurrentSpawn() {
        HashMap<Identifier, Vector2i> foundSpawn = new HashMap<>();
        Optional<Identifier> foundId = getCurrentlySelectedFaction().getSpawnData().getDynamicSpawns().keySet().stream().findFirst();
        foundId.ifPresent(identifier -> foundSpawn.put(identifier, getCurrentlySelectedFaction().getSpawnData().getDynamicSpawns().get(identifier)));
        return foundSpawn;
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
