package net.sevenstars.middleearth.gui.onboarding.onboarding_faction;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
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
import net.sevenstars.middleearth.resources.datas.npcs.data.NpcRank;
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

    World world;
    OnboardingFactionScreen screen;
    OnboardingFactionScreenService service;
    private float currentDelay;

    private HashMap<Disposition, List<Faction>> factions;

    private Disposition selectedDisposition;
    private Faction selectedFaction;
    private Faction selectedSubfaction;
    private SpawnData selectedSpawn;
    private Race selectedRace;
    private NpcEntity currentNpcEntity;
    private List<SearchBarResult> searchBarResults;

    public OnboardingFactionScreenController(World world, float delay) {
        screen = new OnboardingFactionScreen(this);
        this.world = world;
        this.currentDelay = delay;
        this.service = new OnboardingFactionScreenService(world);
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
        mc.setScreen(screen);
        if(selectedFaction == null){
            screen.elements.mapWidget.zoom(10);
            screen.elements.mapWidget.isForcingTargetMovement = true;
            setDisposition(factions.keySet().stream().findFirst().orElse(Disposition.GOOD));
        }
        screen.elements.spawnConfirmButton.active = currentDelay == 0;

        updateScreenInformation();
    }

    public void close(){
        this.screen.close();
        INSTANCE = null;
    }

    private void setupInitialDatas() {
        factions = new HashMap<>();
        for(Disposition disposition : Disposition.values()){
            List<Faction> foundFactions = service.getFactionsByDisposition(disposition);
            for(int i = 0; i < foundFactions.size(); i++){
                if(!foundFactions.get(i).isJoinable()){
                    foundFactions.remove(i);
                    continue;
                }
                if(foundFactions.get(i).getFactionType() == FactionType.SUBFACTION)
                    foundFactions.remove(i);
            }
            if(!foundFactions.isEmpty())
                factions.put(disposition, foundFactions);
        }

        this.searchBarResults = fetchAllPossibleSearchBarResults();
    }

    public void updateScreenInformation() {
        if(screen.elements.searchBarWidget.searchIsToggled()){
            screen.elements.dispositionSelectionWidget.enableVisuals(false);
            screen.elements.factionSelectionWidget.enableVisuals(false);
            screen.elements.subfactionSelectionWidget.enableVisuals(false);
            screen.elements.factionRandomizerButton.active = false;
        } else {
            screen.elements.dispositionSelectionWidget.enableVisuals(true);
            screen.elements.factionSelectionWidget.enableVisuals(true);
            screen.elements.subfactionSelectionWidget.enableVisuals(true);
            screen.elements.factionRandomizerButton.active = true;
        }

        this.screen.elements.factionName = (selectedFaction.tryGetShortName()).formatted(Formatting.BOLD).formatted(Formatting.DARK_GRAY);
        if(this.selectedSubfaction != null)
            this.screen.elements.subfactionName = (selectedSubfaction.tryGetShortName());
        else
            this.screen.elements.subfactionName = null;

        if(this.selectedDisposition != null){
            this.screen.elements.dispositionSelectionWidget.enableVisuals(true);
            this.screen.elements.dispositionSelectionWidget.enableArrows(factions.keySet().size() > 1);
            this.screen.elements.dispositionSelectionWidget.setText(selectedDisposition.getName());
        } else {
            this.screen.elements.dispositionSelectionWidget.enableVisuals(false);
        }

        if(this.selectedFaction != null){
            this.screen.elements.factionSelectionWidget.enableVisuals(true);
            this.screen.elements.factionSelectionWidget.enableArrows(this.factions.get(selectedDisposition).size() > 1);
            this.screen.elements.factionSelectionWidget.setText(this.selectedFaction.tryGetShortName());
        } else {
            this.screen.elements.factionSelectionWidget.enableVisuals(false);
        }

        if(this.selectedSubfaction != null){
            this.screen.elements.subfactionSelectionWidget.enableVisuals(true);
            this.screen.elements.subfactionSelectionWidget.enableArrows(this.selectedFaction.getSubFactions().size() > 1);
            this.screen.elements.subfactionSelectionWidget.setText(this.selectedSubfaction.tryGetShortName());
        } else {
            this.screen.elements.subfactionSelectionWidget.enableVisuals(false);
        }

        Faction factionToUse = getCurrentFaction();
        if(factionToUse == null){
            this.screen.elements.raceList.setText(null);
            this.screen.elements.descriptionTextBlock.setText(null);
            return;
        }

        screen.elements.mapWidget.setSpawns(factionToUse.getSpawnData().getSpawnList());

        if(this.selectedSpawn != null){
            this.screen.elements.spawnPointSelectionWidget.enableVisuals(true);
            this.screen.elements.spawnPointSelectionWidget.enableArrows(factionToUse.getSpawnAmount() > 1);
            this.screen.elements.spawnPointSelectionWidget.setText(selectedSpawn.getFullName());
        } else {
            this.screen.elements.spawnPointSelectionWidget.enableVisuals(false);
        }


        this.screen.elements.raceList.setText(List.of(getRaceText()));
        this.screen.elements.descriptionTextBlock.setText(factionToUse.getDescription());
        this.screen.elements.bannerComponents = factionToUse.getBannerPatternsWithColors(world);
        this.screen.elements.bannerColor = factionToUse.getBaseBannerColor();

        if(selectedRace != null){
            this.screen.elements.raceSelectionWidget.enableVisuals(true);
            this.screen.elements.raceSelectionWidget.enableArrows(factionToUse.getRaces(world).size() > 1);
            this.screen.elements.raceSelectionWidget.setText(selectedRace.getFullName());
        } else {
            this.screen.elements.raceSelectionWidget.enableVisuals(false);
        }

        updateNpcPreview();
    }

    //region [Helpers]
    private Text getRaceText() {
        Faction factionToUse = getCurrentFaction();
        if(factionToUse == null) return null;
        StringBuilder raceListStringBuilder = new StringBuilder();

        for(Race race : factionToUse.getRaces(world)){
            if(!raceListStringBuilder.isEmpty())
                raceListStringBuilder.append(", ");
             raceListStringBuilder.append(race.getFullName().getString());
        }

        return Text.of(raceListStringBuilder.toString());
    }

    private Faction getCurrentFaction() {
        if(this.selectedFaction == null)
            return null;
        Faction faction = selectedFaction;
        if(selectedSubfaction != null)
            faction = selectedSubfaction;
        return faction;
    }

    public void screenResize() {
        this.updateScreenInformation();
    }

    public void updateDisposition(int indexDifference){
        if(factions.keySet().size() == 1) return; // Doesn't change anything

        int currentDispositionIndex = selectedDisposition.ordinal();
        for(int i = 0; i < factions.keySet().size(); i++){
            if(factions.keySet().stream().toList().get(i) == selectedDisposition)
                currentDispositionIndex = i;
        }
        currentDispositionIndex += indexDifference;

        if(currentDispositionIndex < 0)
            currentDispositionIndex = factions.keySet().size() - 1;
        if(currentDispositionIndex >= factions.keySet().size())
            currentDispositionIndex = 0;

        setDisposition(factions.keySet().stream().toList().get(currentDispositionIndex));
        updateScreenInformation();
    }

    private void setDisposition(Disposition disposition){
        selectedDisposition = disposition;
        setFaction(0);
    }

    public void updateFaction(int indexDifference){
        int factionListSize = factions.get(selectedDisposition).size();
        if(factionListSize == 1) return; // Doesn't change anything

        int currentFactionIndex = factions.get(selectedDisposition).indexOf(selectedFaction);

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
            || selectedDisposition == null
            || factions.get(selectedDisposition).size() <= index){
            selectedFaction = null;
            return;
        }

        selectedFaction = factions.get(selectedDisposition).get(index);
        setSubfaction(0);
        setSpawnPoint(0);
        setRace(0);
    }

    public void updateSubfaction(int indexDifference){
        if(selectedFaction == null || selectedFaction.getSubFactions() == null || selectedFaction.getSubFactions().isEmpty()){
            setSubfaction(null);
            return;
        }

        int subfactionListSize =  selectedFaction.getSubFactions().size();
        if(subfactionListSize == 1){
            return; // Doesn't change anything
        }

        int currentSubfactionIndex = (selectedSubfaction == null) ? 0
            : selectedFaction.getSubFactions().indexOf(selectedSubfaction.getId());

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
            || selectedFaction == null
            || selectedFaction.getSubFactions() == null
            || selectedFaction.getSubFactions().isEmpty()
            ||  selectedFaction.getSubFactions().size() <= index){
            selectedSubfaction = null;
            return;
        }

        selectedSubfaction = selectedFaction.getSubfaction(world, index);
        setSpawnPoint(0);
        setRace(0);
    }

    public void updateRace(int indexDifference){
        Faction factionToUse = getCurrentFaction();

        if(factionToUse == null || factionToUse.getRaces(world) == null || factionToUse.getRaces(world).isEmpty()){
            setRace(null);
            return;
        }

        List<Race> raceList = factionToUse.getRaces(world);
        int currentRaceIndex = (selectedRace == null) ? 0
                : raceList.indexOf(selectedRace);

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
                || currentFaction.getRaces(world).size() <= index) {
            selectedRace = null;
            currentNpcEntity = null;
            return;
        }

        selectedRace = currentFaction.getRaces(world).get(index);
    }

    public void updateNpcPreview(){
        Faction currentFaction = getCurrentFaction();
        Identifier id =  currentFaction.getAllNpcDatas().get(NpcRank.SOLDIER).getFirst();
        this.screen.elements.npcPreviewWidget.updateEntity(id, selectedRace, world);
    }

    public void updateSpawnPoint(int indexDifference){
        Faction factionToUse = getCurrentFaction();

        if(factionToUse == null || factionToUse.getSpawnData().getSpawnList() == null || factionToUse.getSpawnData().getSpawnList().isEmpty()){
            setSpawnPoint(null);
            return;
        }

        List<SpawnData> spawnDataList = factionToUse.getSpawnData().getSpawnList();
        int currentSpawnPointIndex = (selectedSpawn == null) ? 0
                : spawnDataList.indexOf(selectedSpawn);

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
            selectedSpawn = null;
            return;
        }
        selectedSpawn = currentFaction.getSpawnData().getSpawnList().get(index);
        moveToCurrentSpawn();
        updateScreenInformation();
    }

    private void setSpawnPoint(Integer index){
        Faction currentFaction = getCurrentFaction();
        if(index == null
            || currentFaction == null
            || currentFaction.getSpawnAmount() <= index) {
            selectedSpawn = null;
            return;
        }

        selectedSpawn = currentFaction.getSpawnData().getSpawnList().get(index);
        screen.elements.mapWidget.selectSpawn(index);

        moveToCurrentSpawn();
        updateScreenInformation();
    }

    public void moveToCurrentSpawn(){
        if(screen.elements.mapWidget.isForcingTargetMovement){
            BlockPos blockPos = selectedSpawn.getBlockPos();
            Vector2i point = new Vector2i(blockPos.getX(), blockPos.getZ());
            screen.elements.mapWidget.moveTo(point, new Vector2d(3.5, 45.0));
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
        setDisposition(factions.keySet().stream().toList().get(random.nextInt(factions.keySet().size())));
        setFaction(random.nextInt(factions.get(selectedDisposition).size()));
        if(selectedFaction.getSubFactions() != null && !selectedFaction.getSubFactions().isEmpty())
            setSubfaction(random.nextInt(selectedFaction.getSubFactions().size()));
        else
            selectedSubfaction = null;

        if(spawn){
            Faction factionToUse = getCurrentFaction();
            setSpawnPoint(random.nextInt(factionToUse.getSpawnAmount()));
        }
        if(race){
            Faction factionToUse = getCurrentFaction();
            List<Race> races = factionToUse.getRaces(world);
            setRace((races == null || races.isEmpty()) ? 0 : random.nextInt(races.size()));
        }
    }

    public void confirmSelection(){
        Faction faction = getCurrentFaction();
        if(faction == null) return;

        Vec3d coordinate = selectedSpawn.getCoordinates();
        if(selectedSpawn.isDynamic()){
            ClientPlayNetworking.send(new PacketTeleportToDynamicCoordinate(coordinate.getX(), coordinate.getZ(), true));
        } else {
            ClientPlayNetworking.send(new PacketTeleportToCustomCoordinate(coordinate.getX(), coordinate.getY(), coordinate.getZ(), true));
        }

        ClientPlayNetworking.send(new PacketSetRace(selectedRace.getId().toString()));
        ClientPlayNetworking.send(new PacketSetAffiliation(selectedDisposition.name(), faction.getId().toString(), selectedSpawn.getIdentifier().toString()));
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player != null){
            BlockPos overworldBlockPos = player.getBlockPos();
            ClientPlayNetworking.send(new PacketSetSpawnData(overworldBlockPos.getX(), overworldBlockPos.getY(), overworldBlockPos.getZ()));
        }
        screen.close();
    }

    public int getMaxSpawnAmount() {
        final int[] maxMarkerCount = {0};
        this.factions.values().forEach(factionList -> factionList.forEach(faction -> {
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
        for(List<Faction> factions : this.factions.values()){
            count += factions.size();
        }
        return count;
    }

    private List<SearchBarResult> fetchAllPossibleSearchBarResults() {
        var newList = new ArrayList<SearchBarResult>();

        for(List<Faction> factions : this.factions.values()){
            for(Faction faction : factions) {
                if(faction.isJoinable()){
                    newList.add(getSearchBarResult(faction));
                    if(faction.getSubFactions() != null){
                        for(Identifier subfacId : faction.getSubFactions()){
                            try{
                                Faction subfac = FactionLookup.getFactionById(world, subfacId);
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
            Faction faction = FactionLookup.getFactionById(world, factionId);
            setDisposition(faction.getDisposition());
            setFaction(factions.get(selectedDisposition).indexOf(faction));
            if(subfactionId != null){
                setSubfaction(selectedFaction.getSubFactions().indexOf(subfactionId));
            }
        } catch (FactionIdentifierException ignored){
        }
    }

    public void tick() {
        if(this.currentDelay > 0){
            this.currentDelay = Math.max(0, this.currentDelay - 1f / 20);
            if(this.currentDelay == 0)
                screen.elements.spawnConfirmButton.active = true;
        }
    }

    public float getCurrentDelay(){
        return (Math.round(this.currentDelay * 10f) /10f);
    }

    public Text getCurrentFactionFullName() {
        return selectedFaction.getFullName();
    }

    public void drawRaceTooltip(AbstractClientPlayerEntity player, DrawContext context, TextRenderer textRenderer, int x, int y) {
        if(selectedRace == null)
            return;
        selectedRace.drawTooltip(player, context, textRenderer, x, y);
    }

    //endregion
}
