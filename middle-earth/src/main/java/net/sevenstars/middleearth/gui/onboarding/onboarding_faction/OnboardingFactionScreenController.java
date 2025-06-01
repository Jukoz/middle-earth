package net.sevenstars.middleearth.gui.onboarding.onboarding_faction;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.gui.utils.widgets.searchbar.SearchBarResult;
import net.sevenstars.middleearth.gui.utils.widgets.searchbar.SearchBarResultType;
import net.sevenstars.middleearth.network.packets.C2S.*;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnDataHandler;
import net.sevenstars.middleearth.resources.datas.races.Race;
import org.joml.Vector2d;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class OnboardingFactionScreenController {
    public static OnboardingFactionScreenController INSTANCE;
    private static final Text TITLE = Text.translatable("screen." + MiddleEarth.MOD_ID + ".onboarding_faction_screen");
    private static final float DEFAULT_DELAY = 3;

    World _world;
    OnboardingFactionScreen _screen;
    OnboardingFactionScreenService _service;
    private float _currentDelay;

    private HashMap<Disposition, List<Faction>> _factions;

    private Disposition _selectedDisposition;
    private Faction _selectedFaction;
    private Faction _selectedSubfaction;
    private SpawnData _selectedSpawn;
    private Race _selectedRace;
    private NpcEntity _currentNpcEntity;
    private List<SearchBarResult> searchBarResults;

    public OnboardingFactionScreenController(World world, float delay) {
        _screen = new OnboardingFactionScreen(this);
        _world = world;
        this._currentDelay = delay;
        this._service = new OnboardingFactionScreenService(world);
        INSTANCE = this;
        setupInitialDatas();
    }
    public static OnboardingFactionScreenController getInstance(){
        return INSTANCE;
    }

    public void open(){
        MinecraftClient mc = MinecraftClient.getInstance();
        if(mc.currentScreen != null)
            mc.currentScreen.close();
        mc.setScreen(_screen);
        if(_selectedFaction == null){
            _screen.elements.mapWidget.zoom(10);
            _screen.elements.mapWidget.isForcingTargetMovement = true;
            setDisposition(_factions.keySet().stream().findFirst().orElse(Disposition.GOOD));
        }
        _screen.elements.spawnConfirmButton.active = _currentDelay == 0;

        updateScreenInformation();
    }

    public void close(){
        this._screen.close();
        INSTANCE = null;
    }

    private void setupInitialDatas() {
        _factions = new HashMap<>();
        for(Disposition disposition : Disposition.values()){
            List<Faction> foundFactions = _service.getFactionsByDisposition(disposition);
            for(int i = 0; i < foundFactions.size(); i++){
                if(!foundFactions.get(i).isJoinable()){
                    foundFactions.remove(i);
                    continue;
                }
                if(foundFactions.get(i).getFactionType() == FactionType.SUBFACTION)
                    foundFactions.remove(i);
            }
            if(!foundFactions.isEmpty())
                _factions.put(disposition, foundFactions);
        }

        this.searchBarResults = fetchAllPossibleSearchBarResults();
    }

    public void updateScreenInformation() {
        if(_screen.elements.searchBarWidget.searchIsToggled()){
            _screen.elements.dispositionSelectionWidget.enableVisuals(false);
            _screen.elements.factionSelectionWidget.enableVisuals(false);
            _screen.elements.subfactionSelectionWidget.enableVisuals(false);
            _screen.elements.factionRandomizerButton.active = false;
        } else {
            _screen.elements.dispositionSelectionWidget.enableVisuals(true);
            _screen.elements.factionSelectionWidget.enableVisuals(true);
            _screen.elements.subfactionSelectionWidget.enableVisuals(true);
            _screen.elements.factionRandomizerButton.active = true;
        }

        this._screen.elements.factionName = (_selectedFaction.tryGetShortName()).formatted(Formatting.BOLD).formatted(Formatting.DARK_GRAY);
        if(this._selectedSubfaction != null)
            this._screen.elements.subfactionName = (_selectedSubfaction.tryGetShortName());
        else
            this._screen.elements.subfactionName = null;

        if(this._selectedDisposition != null){
            this._screen.elements.dispositionSelectionWidget.enableVisuals(true);
            this._screen.elements.dispositionSelectionWidget.enableArrows(_factions.keySet().size() > 1);
            this._screen.elements.dispositionSelectionWidget.setText(_selectedDisposition.getName());
        } else {
            this._screen.elements.dispositionSelectionWidget.enableVisuals(false);
        }

        if(this._selectedFaction != null){
            this._screen.elements.factionSelectionWidget.enableVisuals(true);
            this._screen.elements.factionSelectionWidget.enableArrows(this._factions.get(_selectedDisposition).size() > 1);
            this._screen.elements.factionSelectionWidget.setText(this._selectedFaction.tryGetShortName());
        } else {
            this._screen.elements.factionSelectionWidget.enableVisuals(false);
        }

        if(this._selectedSubfaction != null){
            this._screen.elements.subfactionSelectionWidget.enableVisuals(true);
            this._screen.elements.subfactionSelectionWidget.enableArrows(this._selectedFaction.getSubFactions().size() > 1);
            this._screen.elements.subfactionSelectionWidget.setText(this._selectedSubfaction.tryGetShortName());
        } else {
            this._screen.elements.subfactionSelectionWidget.enableVisuals(false);
        }

        Faction factionToUse = getCurrentFaction();
        if(factionToUse == null){
            this._screen.elements.raceList.setText(null);
            this._screen.elements.descriptionTextBlock.setText(null);
            return;
        }

        _screen.elements.mapWidget.setSpawns(factionToUse.getSpawnData().getSpawnList());

        if(this._selectedSpawn != null){
            this._screen.elements.spawnPointSelectionWidget.enableVisuals(true);
            this._screen.elements.spawnPointSelectionWidget.enableArrows(factionToUse.getSpawnAmount() > 1);
            this._screen.elements.spawnPointSelectionWidget.setText(_selectedSpawn.getFullName());
        } else {
            this._screen.elements.spawnPointSelectionWidget.enableVisuals(false);
        }


        this._screen.elements.raceList.setText(List.of(getRaceText()));
        this._screen.elements.descriptionTextBlock.setText(factionToUse.getDescription());
        this._screen.elements.bannerComponents = factionToUse.getBannerPatternsWithColors(_world);
        this._screen.elements.bannerColor = factionToUse.getBaseBannerColor();

        if(_selectedRace != null){
            this._screen.elements.raceSelectionWidget.enableVisuals(true);
            this._screen.elements.raceSelectionWidget.enableArrows(factionToUse.getRaces(_world).size() > 1);
            this._screen.elements.raceSelectionWidget.setText(_selectedRace.getFullName());
        } else {
            this._screen.elements.raceSelectionWidget.enableVisuals(false);
        }

        this._screen.elements.npcPreviewWidget.setEntity(_currentNpcEntity);
    }

    //region [Helpers]
    private Text getRaceText() {
        Faction factionToUse = getCurrentFaction();
        if(factionToUse == null) return null;
        StringBuilder raceListStringBuilder = new StringBuilder();

        for(Race race : factionToUse.getRaces(_world)){
            if(!raceListStringBuilder.isEmpty())
                raceListStringBuilder.append(", ");
             raceListStringBuilder.append(race.getFullName().getString());
        }

        return Text.of(raceListStringBuilder.toString());
    }

    private Faction getCurrentFaction() {
        if(this._selectedFaction == null)
            return null;
        Faction faction = _selectedFaction;
        if(_selectedSubfaction != null)
            faction = _selectedSubfaction;
        return faction;
    }

    public void screenResize() {
        this.updateScreenInformation();
    }

    public void updateDisposition(int indexDifference){
        if(_factions.keySet().size() == 1) return; // Doesn't change anything

        int currentDispositionIndex = _selectedDisposition.ordinal();
        for(int i = 0; i < _factions.keySet().size(); i++){
            if(_factions.keySet().stream().toList().get(i) == _selectedDisposition)
                currentDispositionIndex = i;
        }
        currentDispositionIndex += indexDifference;

        if(currentDispositionIndex < 0)
            currentDispositionIndex = _factions.keySet().size() - 1;
        if(currentDispositionIndex >= _factions.keySet().size())
            currentDispositionIndex = 0;

        setDisposition(_factions.keySet().stream().toList().get(currentDispositionIndex));
        updateScreenInformation();
    }

    private void setDisposition(Disposition disposition){
        _selectedDisposition = disposition;
        setFaction(0);
    }

    public void updateFaction(int indexDifference){
        int factionListSize = _factions.get(_selectedDisposition).size();
        if(factionListSize == 1) return; // Doesn't change anything

        int currentFactionIndex = _factions.get(_selectedDisposition).indexOf(_selectedFaction);

        currentFactionIndex += indexDifference;

        if(currentFactionIndex < 0)
            currentFactionIndex = factionListSize - 1;
        if(currentFactionIndex >= factionListSize)
            currentFactionIndex = 0;

        setFaction(currentFactionIndex);

        updateScreenInformation();
    }

    public void setFaction(Integer index){
        if(index == null
            || _selectedDisposition == null
            || _factions.get(_selectedDisposition).size() <= index){
            _selectedFaction = null;
            return;
        }

        _selectedFaction = _factions.get(_selectedDisposition).get(index);
        setSubfaction(0);
        setSpawnPoint(0);
        setRace(0);
    }

    public void updateSubfaction(int indexDifference){
        if(_selectedFaction == null || _selectedFaction.getSubFactions() == null || _selectedFaction.getSubFactions().isEmpty()){
            setSubfaction(null);
            return;
        }

        int subfactionListSize =  _selectedFaction.getSubFactions().size();
        if(subfactionListSize == 1){
            return; // Doesn't change anything
        }

        int currentSubfactionIndex = (_selectedSubfaction == null) ? 0
            : _selectedFaction.getSubFactions().indexOf(_selectedSubfaction.getId());

        currentSubfactionIndex += indexDifference;

        if(currentSubfactionIndex < 0)
            currentSubfactionIndex = subfactionListSize - 1;
        if(currentSubfactionIndex >= subfactionListSize)
            currentSubfactionIndex = 0;

        setSubfaction(currentSubfactionIndex);
        updateScreenInformation();
    }

    public void setSubfaction(Integer index){
        if(index == null
            || _selectedFaction == null
            || _selectedFaction.getSubFactions() == null
            || _selectedFaction.getSubFactions().isEmpty()
            ||  _selectedFaction.getSubFactions().size() <= index){
            _selectedSubfaction = null;
            return;
        }

        _selectedSubfaction = _selectedFaction.getSubfaction(_world, index);
        setSpawnPoint(0);
        setRace(0);
    }

    public void updateRace(int indexDifference){
        Faction factionToUse = getCurrentFaction();

        if(factionToUse == null || factionToUse.getRaces(_world) == null || factionToUse.getRaces(_world).isEmpty()){
            setRace(null);
            return;
        }

        List<Race> raceList = factionToUse.getRaces(_world);
        int currentRaceIndex = (_selectedRace == null) ? 0
                : raceList.indexOf(_selectedRace);

        currentRaceIndex += indexDifference;

        if(currentRaceIndex < 0)
            currentRaceIndex = raceList.size() - 1;
        if(currentRaceIndex >= raceList.size())
            currentRaceIndex = 0;

        setRace(currentRaceIndex);

        updateScreenInformation();
    }

    private void setRace(Integer index){
        Faction currentFaction = getCurrentFaction();
        if(index == null
                || currentFaction == null
                || currentFaction.getRaces(_world).size() <= index) {
            _selectedRace = null;
            _currentNpcEntity = null;
            return;
        }

        _selectedRace = currentFaction.getRaces(_world).get(index);
        _currentNpcEntity = NpcEntity.create(_world);
    }

    public void updateSpawnPoint(int indexDifference){
        Faction factionToUse = getCurrentFaction();

        if(factionToUse == null || factionToUse.getSpawnData().getSpawnList() == null || factionToUse.getSpawnData().getSpawnList().isEmpty()){
            setSpawnPoint(null);
            return;
        }

        List<SpawnData> spawnDataList = factionToUse.getSpawnData().getSpawnList();
        int currentSpawnPointIndex = (_selectedSpawn == null) ? 0
                : spawnDataList.indexOf(_selectedSpawn);

        currentSpawnPointIndex += indexDifference;

        if(currentSpawnPointIndex < 0)
            currentSpawnPointIndex = spawnDataList.size() - 1;
        if(currentSpawnPointIndex >= spawnDataList.size())
            currentSpawnPointIndex = 0;

        setSpawnPoint(currentSpawnPointIndex);
    }

    public void assignNewSpawnIndex(Integer index){
        Faction currentFaction = getCurrentFaction();
        if(index == null
                || currentFaction == null
                || currentFaction.getSpawnAmount() <= index) {
            _selectedSpawn = null;
            return;
        }
        _selectedSpawn = currentFaction.getSpawnData().getSpawnList().get(index);
        moveToCurrentSpawn();
        updateScreenInformation();
    }

    private void setSpawnPoint(Integer index){
        Faction currentFaction = getCurrentFaction();
        if(index == null
            || currentFaction == null
            || currentFaction.getSpawnAmount() <= index) {
            _selectedSpawn = null;
            return;
        }

        _selectedSpawn = currentFaction.getSpawnData().getSpawnList().get(index);
        _screen.elements.mapWidget.selectSpawn(index);

        moveToCurrentSpawn();
        updateScreenInformation();
    }

    public void moveToCurrentSpawn(){
        if(_screen.elements.mapWidget.isForcingTargetMovement){
            BlockPos blockPos = _selectedSpawn.getBlockPos();
            Vector2i point = new Vector2i(blockPos.getX(), blockPos.getZ());
            _screen.elements.mapWidget.moveTo(point, new Vector2d(3.5, 45.0));
        }
    }

    public void randomizeFaction(){
        randomize(false, false);
        updateScreenInformation();
    }
    public void randomizeAll(){
        randomize( true, true);
        updateScreenInformation();
    }

    private void randomize(boolean spawn, boolean race){
        Random random = new Random();
        setDisposition(_factions.keySet().stream().toList().get(random.nextInt(_factions.keySet().size())));
        setFaction(random.nextInt(_factions.get(_selectedDisposition).size()));
        if(_selectedFaction.getSubFactions() != null && !_selectedFaction.getSubFactions().isEmpty())
            setSubfaction(random.nextInt(_selectedFaction.getSubFactions().size()));
        else
            _selectedSubfaction = null;

        if(spawn){
            Faction factionToUse = getCurrentFaction();
            setSpawnPoint(random.nextInt(factionToUse.getSpawnAmount()));
        }
        if(race){
            Faction factionToUse = getCurrentFaction();
            List<Race> races = factionToUse.getRaces(_world);
            setRace((races == null || races.isEmpty()) ? 0 : random.nextInt(races.size()));
        }
    }

    public void confirmSelection(){
        Faction faction = getCurrentFaction();
        if(faction == null) return;

        Vec3d coordinate = _selectedSpawn.getCoordinates();
        if(_selectedSpawn.isDynamic()){
            ClientPlayNetworking.send(new PacketTeleportToDynamicCoordinate(coordinate.getX(), coordinate.getZ(), true));
        } else {
            ClientPlayNetworking.send(new PacketTeleportToCustomCoordinate(coordinate.getX(), coordinate.getY(), coordinate.getZ(), true));
        }

        ClientPlayNetworking.send(new PacketSetRace(_selectedRace.getId().toString()));
        ClientPlayNetworking.send(new PacketSetAffiliation(_selectedDisposition.name(), faction.getId().toString(), _selectedSpawn.getIdentifier().toString()));
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player != null){
            BlockPos overworldBlockPos = player.getBlockPos();
            ClientPlayNetworking.send(new PacketSetSpawnData(overworldBlockPos.getX(), overworldBlockPos.getY(), overworldBlockPos.getZ()));
        }
        _screen.close();
    }

    public int getMaxSpawnAmount() {
        final int[] maxMarkerCount = {0};
        this._factions.values().forEach(factionList -> factionList.forEach(faction -> {
            SpawnDataHandler spawnDataHandler = faction.getSpawnData();
            if(spawnDataHandler != null && spawnDataHandler.getSpawnList() != null){
                int count = spawnDataHandler.getSpawnList().size();
                if(count > maxMarkerCount[0]){
                    maxMarkerCount[0] = count;
                }
            }
        }));
        return maxMarkerCount[0];
    }

    public int getAllJoinableFactionAmount() {
        int count = 0;
        for(List<Faction> factions : this._factions.values()){
            count += factions.size();
        }
        return count;
    }

    private List<SearchBarResult> fetchAllPossibleSearchBarResults() {
        var newList = new ArrayList<SearchBarResult>();

        for(List<Faction> factions : this._factions.values()){
            for(Faction faction : factions) {
                if(faction.isJoinable()){
                    newList.add(getSearchBarResult(faction));
                    if(faction.getSubFactions() != null){
                        for(Identifier subfacId : faction.getSubFactions()){
                            try{
                                Faction subfac = FactionLookup.getFactionById(_world, subfacId);
                                newList.add(getSearchBarResult(faction, subfac));
                            } catch (FactionIdentifierException ignored){
                            }
                        }
                    }
                }
            }
        }
        return newList;
    }

    private SearchBarResult getSearchBarResult(Faction faction) {
        MutableText text = faction.tryGetShortName();
        Identifier factionId = faction.getId();
        SearchBarResultType type = SearchBarResultType.NORMAL;
        return new SearchBarResult(text, factionId, type, button -> selectFactionByIdentifier(factionId, null));
    }

    private SearchBarResult getSearchBarResult(Faction faction, Faction subfaction) {
        MutableText text = subfaction.tryGetShortName();
        Identifier factionId = faction.getId();
        Identifier subfactionId = subfaction.getId();
        SearchBarResultType type = SearchBarResultType.SUB;
        return new SearchBarResult(text, subfactionId, type, button -> selectFactionByIdentifier(factionId, subfactionId));
    }

    public List<SearchBarResult> getAllSearchBarResults(){
        return searchBarResults;
    }

    public void selectFactionByIdentifier(Identifier factionId, Identifier subfactionId){
        try{
            Faction faction = FactionLookup.getFactionById(_world, factionId);
            setDisposition(faction.getDisposition());
            setFaction(_factions.get(_selectedDisposition).indexOf(faction));
            if(subfactionId != null){
                setSubfaction(_selectedFaction.getSubFactions().indexOf(subfactionId));
            }
        } catch (FactionIdentifierException ignored){
        }
    }

    public void tick() {
        if(this._currentDelay > 0){
            this._currentDelay = Math.max(0, this._currentDelay - 1f / 20);
            if(this._currentDelay == 0)
                _screen.elements.spawnConfirmButton.active = true;
        }
    }

    public float getCurrentDelay(){
        return (Math.round(this._currentDelay * 10f) /10f);
    }

    //endregion
}
