package net.jukoz.me.client.screens.controllers;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.client.screens.FactionSelectionScreen;
import net.jukoz.me.network.packets.C2S.*;
import net.jukoz.me.resources.datas.Disposition;
import net.jukoz.me.resources.datas.FactionType;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.npcs.data.NpcGearData;
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
    private Map<Disposition, List<Faction>> factions;
    /**
     * Identifier and if the spawn data is from the dynamic pool. True(Dynamic) : False(Custom)
     */
    private List<SpawnData> spawns;
    private List<Race> races = new ArrayList();

    private int currentDispositionIndex;
    private int currentFactionIndex;
    private int currentRaceIndex;
    private int currentSubFactionIndex;
    private int currentSpawnIndex;
    private AbstractClientPlayerEntity player;
    private FactionSelectionScreen screen;
    public boolean mapFocusToggle = true;
    List<Disposition> dispositionsWithContent = new ArrayList<>();
    public FactionSelectionController(FactionSelectionScreen screen, AbstractClientPlayerEntity player){
        this.player = player;
        this.screen = screen;

        factions = new HashMap<>();
        addFactionsByDisposition(Disposition.GOOD);
        addFactionsByDisposition(Disposition.NEUTRAL);
        addFactionsByDisposition(Disposition.EVIL);

        if(getCurrentlySelectedFaction() == null){
            if(!factions.get(Disposition.EVIL).isEmpty()){
                currentDispositionIndex = 2;
            } else if(!factions.get(Disposition.NEUTRAL).isEmpty()){
                currentDispositionIndex = 1;
            }
        }
        if(getCurrentlySelectedFaction() == null){
            LoggerUtil.logError("FactionSelectionController::No faction available!");
            throw new RuntimeException();
        }
        updateSpawnList();
        updateRaces();
    }

    private void addFactionsByDisposition(Disposition disposition) {
        factions.put(disposition, FactionLookup.getFactionsByDisposition(player.getWorld(), disposition).values().stream().toList());
        if(!factions.get(disposition).isEmpty())
            dispositionsWithContent.add(disposition);
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
        races = currentFaction.getRaces(player.getWorld());
    }


    public int randomizeFaction(int tentativeLeft){
        Random random = new Random();
        // Disposition randomizer
        currentDispositionIndex = random.nextInt(dispositionsWithContent.size());
        Disposition disposition = dispositionsWithContent.get(currentDispositionIndex);

        // Recursive trigger
        if(factions.get(disposition) == null || factions.get(disposition).isEmpty()){
            if(tentativeLeft > 0){
                return tentativeLeft + randomizeFaction(tentativeLeft - 1);
            }
        }

        // Faction randomizer
        currentFactionIndex =
                (factions.get(disposition) == null || factions.get(disposition).isEmpty())
                        ? 0
                        : random.nextInt(factions.get(disposition).size());
        Faction faction =
                (factions.get(disposition) == null || factions.get(disposition).isEmpty() || currentFactionIndex >= factions.get(disposition).size())
                        ? null
                        : factions.get(disposition).get(currentFactionIndex);

        // Subfaction randomizer
        currentSubFactionIndex =
                (faction == null || faction.getSubFactions() == null || faction.getSubFactions().isEmpty())
                        ? 0
                        : random.nextInt(faction.getSubFactions().size());
        updateSpawnList();
        updateRaces();
        return 0;
    }

    public void dispositionUpdate(boolean add) {
        if(factions.isEmpty())
            return;
        if(add){
            currentDispositionIndex++;
        }
        else{
            currentDispositionIndex --;
        }
        currentDispositionIndex = dispositionsWithContent.indexOf(getCurrentDisposition());

        currentFactionIndex = 0;
        currentSubFactionIndex = 0;
        currentRaceIndex = 0;
        setFactionIndex(0);
        updateSpawnList();
        updateRaces();
    }

    private int getDispositionsIndex(Disposition disposition){
        return factions.keySet().stream().toList().indexOf(disposition);
    }

    public void factionUpdate(boolean add) {
        if(add){
            currentFactionIndex++;
            if(currentFactionIndex >= factions.get(getCurrentDisposition()).size())
                currentFactionIndex = 0;
        }
        else{
            currentFactionIndex--;
            if(currentFactionIndex < 0)
                currentFactionIndex = factions.get(getCurrentDisposition()).size() - 1;
        }
        setFactionIndex(currentFactionIndex);

        currentSubFactionIndex = 0;
        currentRaceIndex = 0;
        updateSpawnList();
        updateRaces();
    }

    private void setFactionIndex(int i) {
        screen.reassignTexts(getRaceListText(), getCurrentFactionDescriptions());
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
        setSubFactionIndex(currentSubFactionIndex);

        screen.reassignTexts(getRaceListText(), getCurrentFactionDescriptions());
        currentRaceIndex = 0;
        updateSpawnList();
        updateRaces();
    }
    
    private void setSubFactionIndex(int i) {
        if(getCurrentlySelectedFaction().getFactionType() == FactionType.SUBFACTION)
            screen.reassignTexts(getRaceListText(), getCurrentFactionDescriptions());
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

    public boolean haveSpawns(){
        return spawns != null && !spawns.isEmpty();
    }

    private Identifier getCurrentSpawnIdentifier(){
        if(!haveSpawns())
            updateSpawnList();
        if(!haveSpawns())
            return null;
        return spawns.get(currentSpawnIndex).getIdentifier();
    }
    public String getCurrentSpawnKey(){
        if(haveSpawns()){
            Identifier spawnId = getCurrentSpawnIdentifier();
            return SpawnDataHandler.getTranslatableKey(spawnId);
        }
        return "spawn.me.none";
    }
    public boolean haveManySpawns(){
        if(!haveSpawns()){
            return false;
        }
        return spawns.size() > 1;
    }


    public void confirmSpawnSelection(AbstractClientPlayerEntity player){
        Faction faction = getCurrentlySelectedFaction();
        if(faction == null || !haveSpawns()) return;

        SpawnData spawn = spawns.get(currentSpawnIndex);
        Vec3d coordinate = spawn.getCoordinates();
        if(spawn.isDynamic()){
            ClientPlayNetworking.send(new PacketTeleportToDynamicCoordinate(coordinate.getX(), coordinate.getZ(), true));
        } else {
            ClientPlayNetworking.send(new PacketTeleportToCustomCoordinate(coordinate.getX(), coordinate.getY(), coordinate.getZ(), true));
        }

        ClientPlayNetworking.send(new PacketSetRace(races.get(currentRaceIndex).getId().toString()));
        ClientPlayNetworking.send(new PacketSetAffiliation(getCurrentDisposition().name(), getCurrentlySelectedFaction().getId().toString(), spawn.getIdentifier().toString()));
        if(player != null){
            BlockPos overworldBlockPos = player.getBlockPos();
            ClientPlayNetworking.send(new PacketSetSpawnData(overworldBlockPos.getX(), overworldBlockPos.getY(), overworldBlockPos.getZ()));
        }
    }

    public Disposition getCurrentDisposition(){
        if(currentDispositionIndex >= dispositionsWithContent.size())
            currentDispositionIndex = 0;
        else if(currentDispositionIndex < 0)
            currentDispositionIndex = dispositionsWithContent.size() - 1;
        return dispositionsWithContent.get(currentDispositionIndex);
    }

    public Faction getCurrentFaction(){
        Disposition disposition = getCurrentDisposition();
        return (!factions.get(disposition).isEmpty()) ? factions.get(disposition).get(currentFactionIndex) : null;
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

    public int getCurrentDispositionFactionCount(){
        return factions.get(getCurrentDisposition()).size();
    }

    // Todo : possibily remove this method to have a more precise getter
    public Map<Disposition, List<Faction>> getFactions() {
        return factions;
    }

    public NpcGearData getCurrentPreview(World world) {
        Faction currentFaction = getCurrentlySelectedFaction();
        races = currentFaction.getRaces(world);
        NpcGearData data = currentFaction.getPreviewGear(world, races.get(currentRaceIndex));
        return data;
    }

    public SpawnDataHandler getCurrentSpawnDataHandler(){
        Faction faction = getCurrentlySelectedFaction();
        return (faction != null) ? faction.getSpawnData() : null;
    }

    public void setSpawnIndex(int index) {
        if(!haveSpawns()){
            currentSpawnIndex = 0;
            return;
        }
        if(index != currentSpawnIndex ||  spawns.isEmpty())
            currentSpawnIndex = Math.min(spawns.size() - 1, Math.max(0, index));
        if(screen.mapWidget != null){
            screen.mapWidget.updateSelectedSpawn(index);
            if(mapFocusToggle){
                SpawnData spawnData = spawns.get(currentSpawnIndex);
                if(spawnData != null){
                    BlockPos blockPos = spawns.get(currentSpawnIndex).getBlockPos();
                    Vector2i point = new Vector2i(blockPos.getX(), blockPos.getZ());
                    screen.mapWidget.moveTo(point, new Vector2d(3.5, 45.0));
                }
            }
        }
    }

    public int getCurrentSpawnIndex() {
        return currentSpawnIndex;
    }

    public HashMap<Identifier, Text> getSearchBarPool(World world) {
        HashMap<Identifier, Text> pool = new HashMap<>();
        for(List<Faction> factionsByDisposition : factions.values()){
            for(Faction faction : factionsByDisposition){
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
        if(!mapFocusToggle){
            screen.mapWidget.clearFocus();
        }
    }

    public List<Text> getCurrentFactionDescriptions() {
        Faction faction = getCurrentlySelectedFaction();
        if(faction != null){
            return faction.getDescription();
        }
        return null;
    }

    public List<Text> getRaceListText() {
        Faction faction = getCurrentlySelectedFaction();
        if(faction != null){
            return List.of(faction.getRaceListText(player.getWorld()));
        }
        return null;
    }
}
