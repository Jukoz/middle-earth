package net.sevenstars.middleearth.gui.onboarding.onboarding_faction;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.races.Race;

import java.util.HashMap;
import java.util.List;

public class OnboardingFactionScreenController {
    public static OnboardingFactionScreenController INSTANCE;
    private static final Text TITLE = Text.translatable("screen.me.onboarding_faction_screen");
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
        if(mc.currentScreen == null)
            mc.setScreen(_screen);
        updateScreenInformation();
    }
    public void close(){
        this._screen.close();
        INSTANCE = null;
    }

    private void setupInitialDatas() {
        _factions = new HashMap<>();
        _factions.put(Disposition.GOOD, _service.getFactionsByDisposition(Disposition.GOOD));
        _factions.put(Disposition.NEUTRAL, _service.getFactionsByDisposition(Disposition.NEUTRAL));
        _factions.put(Disposition.EVIL, _service.getFactionsByDisposition(Disposition.EVIL));

        _selectedDisposition = Disposition.GOOD;
        _selectedFaction = _factions.get(Disposition.GOOD).get(2);
        if(_selectedFaction != null){
            if(_selectedFaction.getSubFactions() != null && !_selectedFaction.getSubFactions().isEmpty())
                _selectedSubfaction = _selectedFaction.getSubfaction(_world, 0);

            _selectedRace = _selectedFaction.getRaces(_world).getFirst();
            _selectedSpawn = _selectedFaction.getSpawnData().getSpawnList().getFirst();
        }
    }

    private void updateScreenInformation() {
        this._screen._elements.factionName = (_selectedFaction.tryGetShortName()).formatted(Formatting.BOLD).formatted(Formatting.DARK_GRAY);
        if(this._selectedSubfaction != null)
            this._screen._elements.subfactionName = (_selectedSubfaction.tryGetShortName());
        else
            this._screen._elements.subfactionName = null;

        Faction factionToUse = getCurrentFaction();
        if(factionToUse == null){
            this._screen._elements.raceList.setText(null);
            this._screen._elements.descriptionTextBlock.setText(null);
            return;
        }

        this._screen._elements.dispositionSelectionWidget.setText(factionToUse.getDisposition().getName());
        this._screen._elements.factionSelectionWidget.setText(this._selectedFaction.tryGetShortName());
        if(this._selectedSubfaction != null){
            this._screen._elements.subfactionSelectionWidget.enableVisuals(true);
            this._screen._elements.subfactionSelectionWidget.setText(this._selectedSubfaction.tryGetShortName());
        }
        else this._screen._elements.subfactionSelectionWidget.enableVisuals(false);


        this._screen._elements.raceList.setText(List.of(getRaceText()));
        this._screen._elements.descriptionTextBlock.setText(factionToUse.getDescription());
        this._screen._elements.bannerComponents = factionToUse.getBannerPatternsWithColors(_world);
        this._screen._elements.bannerColor = factionToUse.getBaseBannerColor();


        this._screen._elements.spawnPointSelectionWidget.setText(factionToUse.getSpawnData().getSpawnList().getFirst().getFullName());
        this._screen._elements.spawnPointSelectionWidget.enableArrows(!factionToUse.getSpawnData().getSpawnList().isEmpty());

        this._screen._elements.raceSelectionWidget.setText(Text.translatable(_selectedRace.getTranslatableKey()));


        this._screen._elements.npcPreviewWidget.updateToDefaultEntity(_world);
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

    //endregion
}
