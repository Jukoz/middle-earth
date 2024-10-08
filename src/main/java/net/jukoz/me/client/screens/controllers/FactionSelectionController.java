package net.jukoz.me.client.screens.controllers;

import it.unimi.dsi.fastutil.Hash;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.client.screens.FactionSelectionScreen;
import net.jukoz.me.network.packets.C2S.*;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.FactionType;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.factions.data.NpcPreview;
import net.jukoz.me.resources.datas.factions.data.SpawnData;
import net.jukoz.me.resources.datas.factions.data.SpawnDataHandler;
import net.jukoz.me.resources.datas.races.Race;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector2d;
import org.joml.Vector2i;

import java.util.*;

public class FactionSelectionController {
    private Map<Alignment, List<Faction>> factions;
    /**
     * Identifier and if the spawn data is from the dynamic pool. True(Dynamic) : False(Custom)
     */
    private List<SpawnData> spawns;
    private List<Race> races = new ArrayList();

    private int currentAlignementIndex;
    private int currentFactionIndex;
    private int currentRaceIndex;
    private int currentSubFactionIndex;
    private int currentSpawnIndex;
    private AbstractClientPlayerEntity player;
    private FactionSelectionScreen screen;
    public boolean mapFocusToggle = true;

    public FactionSelectionController(FactionSelectionScreen screen, AbstractClientPlayerEntity player){
        this.player = player;
        this.screen = screen;

        factions = new HashMap<>();
        factions.put(Alignment.GOOD, FactionLookup.getFactionsByAlignment(player.getWorld(), Alignment.GOOD).values().stream().toList());
        factions.put(Alignment.NEUTRAL, FactionLookup.getFactionsByAlignment(player.getWorld(), Alignment.NEUTRAL).values().stream().toList());
        factions.put(Alignment.EVIL, FactionLookup.getFactionsByAlignment(player.getWorld(), Alignment.EVIL).values().stream().toList());
        if(getCurrentlySelectedFaction() == null){
            if(!factions.get(Alignment.EVIL).isEmpty()){
                currentAlignementIndex = 2;
            } else if(!factions.get(Alignment.NEUTRAL).isEmpty()){
                currentAlignementIndex = 1;
            }
        }
        if(getCurrentlySelectedFaction() == null){
            LoggerUtil.logError("FactionSelectionController::No faction available!");
        }
        updateSpawnList();
        updateRaces();
    }

    private void updateSpawnList() {
        Faction currentFaction = getCurrentlySelectedFaction();
        if(currentFaction == null) return;
        SpawnDataHandler foundSpawnDataHandler = currentFaction.getSpawnData();
        if(foundSpawnDataHandler == null) return;
        spawns = foundSpawnDataHandler.getSpawnList();
        currentSpawnIndex = 0;
        currentRaceIndex = 0;
        setSpawnIndex(currentSpawnIndex);
    }

    private void updateRaces() {
        races = null;
        Faction currentFaction = getCurrentlySelectedFaction();
        if(currentFaction == null) return;
        races = currentFaction.getRaces();
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
        updateRaces();
        return 0;
    }

    public void alignmentUpdate(boolean add) {
        if(add){
            currentAlignementIndex++;
            if(currentAlignementIndex >= Alignment.values().length)
                currentAlignementIndex = 0;
        }
        else{
            currentAlignementIndex --;
            if(currentAlignementIndex < 0)
                currentAlignementIndex = Alignment.values().length - 1;


        }
        currentFactionIndex = 0;
        currentSubFactionIndex = 0;
        currentRaceIndex = 0;

        updateSpawnList();
        updateRaces();
    }

    public void factionUpdate(boolean add) {
        if(add){
            currentFactionIndex++;
            if(currentFactionIndex >= factions.get(getCurrentAlignment()).size())
                currentFactionIndex = 0;
        }
        else{
            currentFactionIndex--;
            if(currentFactionIndex < 0)
                currentFactionIndex = factions.get(getCurrentAlignment()).size() - 1;
        }
        currentSubFactionIndex = 0;
        currentRaceIndex = 0;

        updateSpawnList();
        updateRaces();
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
        currentRaceIndex = 0;
        updateSpawnList();
        updateRaces();
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
        setSpawnIndex(currentSpawnIndex);
    }

    public void raceIndexUpdate(boolean add) {
        if(add){
            currentRaceIndex++;
            if(currentRaceIndex >= races.size())
                currentRaceIndex = 0;
        }
        else{
            currentRaceIndex--;
            if(currentRaceIndex < 0)
                currentRaceIndex = races.size() - 1;
        }
    }

    public Race getCurrentRace() {
        if(this.races != null){
            return races.get(currentRaceIndex);
        }
        return null;
    }

    public String getCurrentRaceKey() {
        if(getCurrentRace() != null)
            return getCurrentRace().getTranslatableKey();
        return "ooops"; // TODO : translatale
    }

    public boolean haveManyRaces(){
        if(races == null){
            return false;
        }
        return races.size() > 1;
    }

    private Identifier getCurrentSpawnIdentifier(){
        if(spawns == null || spawns.isEmpty())
            updateSpawnList();
        if(spawns.isEmpty())
            return null;
        return spawns.get(currentSpawnIndex).getIdentifier();
    }
    public String getCurrentSpawnKey(){
        Identifier spawnId = getCurrentSpawnIdentifier();
        return SpawnDataHandler.getTranslatableKey(spawnId);
    }
    public boolean haveManySpawns(){
        if(spawns == null){
            return false;
        }
        return spawns.size() > 1;
    }


    public void confirmSpawnSelection(AbstractClientPlayerEntity player){
        Faction faction = getCurrentlySelectedFaction();
        if(faction == null || spawns == null || spawns.isEmpty()) return;

        SpawnData spawn = spawns.get(currentSpawnIndex);
        Vec3d coordinate = spawn.getCoordinates();
        if(spawn.isDynamic()){
            ClientPlayNetworking.send(new PacketTeleportToDynamicCoordinate(coordinate.getX(), coordinate.getZ(), true));
        } else {
            ClientPlayNetworking.send(new PacketTeleportToCustomCoordinate(coordinate.getX(), coordinate.getY(), coordinate.getZ(), true));
        }

        ClientPlayNetworking.send(new PacketSetRace(races.get(currentRaceIndex).getId().toString()));
        ClientPlayNetworking.send(new PacketSetAffiliation(getCurrentAlignment().name(), getCurrentlySelectedFaction().getId().toString(), spawn.getIdentifier().toString()));
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
        return faction.getSubfaction(player.getWorld(), currentSubFactionIndex);
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

    public NpcPreview getCurrentPreview() {
        Faction currentFaction = getCurrentlySelectedFaction();
        NpcPreview data = currentFaction.getPreviewGear(races.get(currentRaceIndex));
        return data;
    }

    public SpawnDataHandler getCurrentSpawnDataHandler(){
        Faction faction = getCurrentlySelectedFaction();
        return (faction != null) ? faction.getSpawnData() : null;
    }

    public void setSpawnIndex(int index) {
        if(index != currentSpawnIndex)
            currentSpawnIndex = Math.min(spawns.size() - 1, Math.max(0, index));
        if(screen.mapWidget != null){
            screen.mapWidget.updateSelectedSpawn(index);
            if(mapFocusToggle){
                BlockPos blockPos = spawns.get(currentSpawnIndex).getBlockPos();
                Vector2i point = new Vector2i(blockPos.getX(), blockPos.getZ());
                screen.mapWidget.moveTo(point,5f);
            }
        }
    }

    public int getCurrentSpawnIndex() {
        return currentSpawnIndex;
    }

    public HashMap<Identifier, Text> getSearchBarPool(World world) {
        HashMap<Identifier, Text> pool = new HashMap<>();
        for(List<Faction> factionsByAlignment : factions.values()){
            for(Faction faction : factionsByAlignment){
                pool.put(faction.getId(), faction.tryGetShortName());
                if(faction.getFactionType() == FactionType.FACTION && faction.getSubFactions() != null){
                    for(Identifier identifier : faction.getSubFactions()){
                        Faction subfaction = faction.getSubfactionById(world, identifier);
                        pool.put(subfaction.getId(), subfaction.tryGetShortName());
                    }
                }
            }
        }
        return pool;
    }

    public void toggleMapFocus() {
        mapFocusToggle = !mapFocusToggle;
    }
}
