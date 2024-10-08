package net.jukoz.me.client.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.controllers.FactionSelectionController;
import net.jukoz.me.client.screens.utils.CycledSelectionButtonType;
import net.jukoz.me.client.screens.utils.widgets.*;
import net.jukoz.me.client.screens.utils.widgets.map.FactionSelectionMapWidget;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.data.BannerData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.awt.event.KeyEvent;
import java.util.*;

@Environment(EnvType.CLIENT)
public class FactionSelectionScreen extends Screen {
    private static final Identifier FACTION_SELECTION_UI = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection.png");
    private static final Identifier FACTION_SELECTION_BANNER_UI = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection_banner.png");
    private static final Identifier FACTION_SELECTION_BUTTONS = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection_buttons.png");
    private static final Identifier MAP_SELECTION = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection_map.png");
    private static final Text FACTION_SELECTION_TITLE = Text.translatable("screen.me.faction_selection_screen");
    private static final int MINIMAL_MARGIN = 4;
    private FactionSelectionController controller;
    private AbstractClientPlayerEntity player;
    private ModelPart bannerField;
    private SearchBarWidget searchBarWidget;
    private PlayableNpcPreviewWidget playableNpcPreviewWidget;
    private CycledSelectionWidget alignmentSelectionWidget;
    private CycledSelectionWidget factionSelectionWidget;
    private CycledSelectionWidget subfactionSelectionWidget;
    public ButtonWidget factionRandomizerButton;

    // Map buttons
    public ButtonWidget mapZoomInButton;
    public ButtonWidget mapZoomOutButton;
    public ButtonWidget mapFocusButton;
    public FactionSelectionMapWidget mapWidget;
    private CycledSelectionWidget raceCycledSelection;
    private CycledSelectionWidget spawnPointCycledSelection;
    public ButtonWidget spawnSelectionRandomizerButton;
    public ButtonWidget spawnSelectionConfirmButton;
    public FactionSelectionScreen() {
        super(FACTION_SELECTION_TITLE);
        ModWidget.enableFocus(false);
    }

    @Override
    protected void init() {
        assert this.client != null;
        this.bannerField = this.client.getEntityModelLoader().getModelPart(EntityModelLayers.BANNER).getChild("flag");
        Entity cameraEntity = this.client.getCameraEntity();
        if (cameraEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
            this.player = abstractClientPlayerEntity;
            controller = new FactionSelectionController(this, player);
        } else {
            LoggerUtil.logError("FactionSelectionScreen::Init:Couldn't find player");
        }

        // Initialize Buttons
        // Search bar
        searchBarWidget = new SearchBarWidget(controller.getSearchBarPool(player.getWorld()));
        addDrawableChild(searchBarWidget.getSearchBarToggleButton());

        // NpcPreview
        playableNpcPreviewWidget = new PlayableNpcPreviewWidget();

        addFactionSelectionPanelButtons();
        mapWidget = new FactionSelectionMapWidget(controller, 114, 114);
        mapWidget.selectSpawn(controller.getCurrentSpawnIndex());
        mapWidget.updateSelectedSpawn(controller.getCurrentSpawnIndex());
        addMapPanelButtonsAndWidgets();
        addDrawableChild(searchBarWidget.getScreenClickButton());
    }

    /**
     * Add all faction selection buttons
     * - Cycled widgets & Randomizer
     */
    private void addFactionSelectionPanelButtons() {
        // Alignment
        alignmentSelectionWidget = new CycledSelectionWidget(
                button -> {
                    controller.alignmentUpdate(false);
                    updateEquipment();
                },
                button -> {
                    controller.alignmentUpdate(true);
                    updateEquipment();
                },
                null,
                CycledSelectionButtonType.GOLD);
        for(ButtonWidget button: alignmentSelectionWidget.getButtons()){
            addDrawableChild(button);
        }

        // Faction
        factionSelectionWidget = new CycledSelectionWidget(
                button -> {
                    controller.factionUpdate(false);
                    updateEquipment();
                },
                button -> {
                    controller.factionUpdate(true);
                    updateEquipment();
                },
                null,
                CycledSelectionButtonType.SILVER);
        for(ButtonWidget button: factionSelectionWidget.getButtons()){
            addDrawableChild(button);
        }

        // Subfaction
        subfactionSelectionWidget = new CycledSelectionWidget(
                button -> {
                    controller.subfactionUpdate(false);
                    updateEquipment();
                },
                button -> {
                    controller.subfactionUpdate(true);
                    updateEquipment();
                },
                null,
                CycledSelectionButtonType.NORMAL);

        for(ButtonWidget button: subfactionSelectionWidget.getButtons()){
            addDrawableChild(button);
        }

        for(ButtonWidget button: playableNpcPreviewWidget.getButtons()){
            addDrawableChild(button);
        }
        // Faction Randomizer
        factionRandomizerButton = ButtonWidget.builder(
                Text.translatable("screen.me.button.faction_randomizer"),
                button -> {
                    controller.randomizeFaction(5);
                    updateEquipment();
                }).build();
        addDrawableChild(factionRandomizerButton);
    }

    /**
     * Add all map buttons
     * - Map widgets & Cycled widgets & Randomizer & Confirms
     */
    private void addMapPanelButtonsAndWidgets() {
        for(ButtonWidget button: mapWidget.getButtons()){
            addDrawableChild(button);
        }

        // Focus current spawn point (from data)
        mapFocusButton = ButtonWidget.builder(
                Text.translatable("screen.me.button.focus_current"),
                button -> {
                    controller.toggleMapFocus();
                    controller.setSpawnIndex(controller.getCurrentSpawnIndex());
                    mapWidget.addCooldown();
                }).build();
        addDrawableChild(mapFocusButton);

        // Zoom out the map to have a more broad view
        mapZoomOutButton = ButtonWidget.builder(
                Text.translatable("screen.me.button.zoom_out"),
                button -> {
                    mapWidget.dezoomClick();
                }).build();
        addDrawableChild(mapZoomOutButton);

        // Zoom into the map to have a closeup view
        mapZoomInButton = ButtonWidget.builder(
                Text.translatable("screen.me.button.zoom_in"),
                button -> {
                    mapWidget.zoomClick();
                }).build();
        addDrawableChild(mapZoomInButton);

        // Race Selection
        raceCycledSelection = new CycledSelectionWidget(
                button -> {
                    controller.raceIndexUpdate(false);
                    updateEquipment();
                },
                button -> {
                    controller.raceIndexUpdate(true);
                    updateEquipment();
                },
                null,
                CycledSelectionButtonType.NORMAL);
        for(ButtonWidget button: raceCycledSelection.getButtons()){
            addDrawableChild(button);
        }

        // Spawn Point Selection
        spawnPointCycledSelection = new CycledSelectionWidget(
                button -> {
                    controller.spawnIndexUpdate(false);
                },
                button -> {
                    controller.spawnIndexUpdate(true);
                },
                null,
                CycledSelectionButtonType.NORMAL);

        for(ButtonWidget button: spawnPointCycledSelection.getButtons()){
            addDrawableChild(button);
        }

        // Random spawn selection
        spawnSelectionRandomizerButton = ButtonWidget.builder(
                Text.translatable("screen.me.button.spawn_randomizer"),
                button -> {
                    // TODO : Add proper logic to randomized spawn selection
                }).build();
        addDrawableChild(spawnSelectionRandomizerButton);

        // Confirm spawn selection
        spawnSelectionConfirmButton = ButtonWidget.builder(
                Text.translatable("screen.me.button.confirm"),
                button -> {
                    controller.confirmSpawnSelection(player);
                }).build();
        addDrawableChild(spawnSelectionConfirmButton);
    }

    public void updateEquipment(){
        if(player == null) return;

        Faction faction = controller.getCurrentlySelectedFaction();

        if(faction != null)
            playableNpcPreviewWidget.updateEntity(controller.getCurrentPreview(), controller.getCurrentRace(), player.getWorld());
        else
            playableNpcPreviewWidget.updateToDefaultEntity(player.getWorld());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        ModWidget.updateMouse(mouseX, mouseY);
        this.renderBackground(context, mouseX, mouseY, delta);
        this.drawPanels(context);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Keybind : Escape || Other Escape
        if(!playableNpcPreviewWidget.keyPressed(keyCode, scanCode, modifiers)
        || !searchBarWidget.keyPressed(keyCode, scanCode, modifiers))
            return true;


        if(keyCode == KeyEvent.VK_ESCAPE){
            this.close();
            return true;
        }

        // Keybind : Tabulation
        if(keyCode == KeyEvent.VK_CODE_INPUT && !ModWidget.getFocusEnabled() && !CycledSelectionWidget.focusEnabled()){
            ModWidget.enableFocus(true);
            CycledSelectionWidget.toggleFocus();
            SearchBarWidget.toggleFocus();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        playableNpcPreviewWidget.keyReleased(keyCode, scanCode, modifiers);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    protected void drawPanels(DrawContext context){
        int mainPanelWidth = 169;
        int mainPanelHeight = 207;

        drawFactionSelectionPanel(context, mainPanelWidth, mainPanelHeight);
        drawInformationPanel(context, mainPanelWidth, mainPanelHeight);
        drawMapPanel(context, mainPanelWidth, mainPanelHeight);
    }

    private void drawInformationPanel(DrawContext context, int mainPanelWidth, int mainPanelHeight) {
        int startX = (int) ((context.getScaledWindowWidth() / 2f) - (mainPanelWidth / 2f));
        int startY = (int) ((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f));

        context.drawTexture(FACTION_SELECTION_UI,
                startX,
                startY,
                0, 0,
                mainPanelWidth,
                mainPanelHeight
        );

        drawFactionBanner(context, startX + mainPanelWidth - 50, startY + 6);
    }

    private void drawFactionSelectionPanel(DrawContext context, int mainPanelWidth, int mainPanelHeight) {
        int endX = (int) ((context.getScaledWindowWidth() / 2f) - (mainPanelWidth / 2f) - MINIMAL_MARGIN);
        int startX = Math.max(MINIMAL_MARGIN, endX  - mainPanelWidth);
        int startY = (int) ((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f));

        // Draw alignment option
        Alignment alignment = controller.getCurrentAlignment();
        Faction faction = controller.getCurrentFaction();
        Faction subFaction = controller.getCurrentSubfaction();

        int centerX = endX - CycledSelectionWidget.TOTAL_WIDTH / 2;
        int endY = (int) ((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f)) + mainPanelHeight;

        if(!playableNpcPreviewWidget.haveBeenInitialized)
            updateEquipment();



        int newStartY = startY + searchBarWidget.drawSearchBarCentered(context, centerX, startY, textRenderer);

        if(!searchBarWidget.searchIsToggled()){
            // Rendered first to be in the background
            playableNpcPreviewWidget.drawCenteredAnchoredBottom(context, centerX, endY - 18 - (MINIMAL_MARGIN * 2));
            drawFactionRandomizer(context, centerX, endY);
        }

        // List all widgets one after the other
        searchBarWidget.setEndY(endY);

        if(searchBarWidget.searchIsToggled()) {
            alignmentSelectionWidget.enableArrows(false);
            factionSelectionWidget.enableArrows(false);
            subfactionSelectionWidget.enableArrows(false);
            factionRandomizerButton.active = false;
            searchBarWidget.drawSearchResultsCentered(context, centerX, startY); // Todo : only give what's necessary/need to be showcased
            return;
        }

        // Alignment
        factionSelectionWidget.enableArrows(Alignment.values().length > 1);
        newStartY += MINIMAL_MARGIN + alignmentSelectionWidget.drawAnchored(context, endX, newStartY, false, alignment.getName(), textRenderer);

        // Faction
        int currentFactionCountForAlignment = controller.getCurrentAlignmentFactionCount();
        factionSelectionWidget.enableArrows(currentFactionCountForAlignment > 1);
        if(faction != null){
            newStartY += MINIMAL_MARGIN + factionSelectionWidget.drawAnchored(context, endX, newStartY, false, (faction == null) ? null : faction.tryGetShortName(), textRenderer);

            // Subfaction
            subfactionSelectionWidget.enableArrows(controller.haveSubfaction() && (faction.getSubFactions() != null && faction.getSubFactions().size() > 1));
            if(controller.haveSubfaction())
                subfactionSelectionWidget.drawAnchored(context, endX, newStartY, false, (subFaction == null) ? null : subFaction.tryGetShortName(), textRenderer);
        }

        if(!factionRandomizerButton.active)
            factionRandomizerButton.active = true;
    }


    private boolean isMouseOver(int startX, int sizeX, int startY, int sizeY) {
        return ModWidget.isMouseOver(sizeX, sizeY, startX, startY);
    }

    protected void drawFactionRandomizer(DrawContext context, int centerX, int endY) {
        if(factionRandomizerButton == null) return;

        int sizeX = 52;
        int sizeY = 18;
        int startX = (int) (centerX - (sizeX / 2f));
        int startY = endY - sizeY;
        boolean mouseOver = isMouseOver(startX, sizeX, startY, sizeY);
        context.drawTexture(FACTION_SELECTION_BUTTONS,
                startX,
                startY,
                103, (factionRandomizerButton.isFocused() || mouseOver) ? 92 : 74,
                sizeX,
                sizeY
        );
        factionRandomizerButton.setDimensionsAndPosition(sizeX, sizeY, startX, startY);
        if(ModWidget.getFocusEnabled() && factionRandomizerButton.isFocused()){
            context.drawTexture(FACTION_SELECTION_BUTTONS,
                    startX,
                    startY,
                    103, 148,
                    sizeX,
                    sizeY
            );
        }
    }


    private void drawMapPanel(DrawContext context, int mainPanelWidth, int mainPanelHeight) {
        int startX = (int) ((context.getScaledWindowWidth() / 2f) + (mainPanelWidth / 2f)) + MINIMAL_MARGIN;
        int startY = (int) ((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f));

        int mapBackgroundWidth = 124;
        int mapBackgroundHeight = 124;

        context.drawTexture(MAP_SELECTION,
                startX, startY,
                0, 0,
                mapBackgroundWidth,
                mapBackgroundHeight
        );

        mapWidget.drawAnchored(context,startX + 5, startY + 5, true);
        if(controller.mapFocusToggle != mapWidget.haveForcedMapTarget())
            controller.toggleMapFocus();

        // Arbritary
        int buttonStartX = startX + MINIMAL_MARGIN + 2;
        int buttonSize = 10;
        startY += mapBackgroundHeight;

        int smallButtonsStartY = startY - buttonSize - MINIMAL_MARGIN - 2;

        // Focus current
        context.drawTexture(MAP_SELECTION,
                buttonStartX, smallButtonsStartY,
                235, (controller.mapFocusToggle) ? 20 : mapFocusButton.isFocused() || isMouseOver(buttonStartX, buttonSize, smallButtonsStartY, buttonSize) ? 10 : 0,
                buttonSize,
                buttonSize
        );
        mapFocusButton.setDimensionsAndPosition(buttonSize, buttonSize, buttonStartX, smallButtonsStartY);
        if(mapFocusButton.isFocused() && ModWidget.getFocusEnabled()){
            highlightedFocusMapButton(context, buttonStartX - 1, smallButtonsStartY - 1);
        }

        // Zoom in
        buttonStartX = startX + mapBackgroundWidth - MINIMAL_MARGIN - buttonSize - 2;
        boolean canZoomIn = mapWidget.canZoomIn;
        mapZoomInButton.active = canZoomIn;
        context.drawTexture(MAP_SELECTION,
                buttonStartX, smallButtonsStartY,
                224, !canZoomIn ? 20 : mapZoomInButton.isFocused() || isMouseOver(buttonStartX, buttonSize, smallButtonsStartY, buttonSize) ? 10 : 0,
                buttonSize,
                buttonSize
        );
        mapZoomInButton.setDimensionsAndPosition(buttonSize, buttonSize, buttonStartX, smallButtonsStartY);
        if(canZoomIn && mapZoomInButton.isFocused() && ModWidget.getFocusEnabled()){
            highlightedFocusMapButton(context, buttonStartX - 1, smallButtonsStartY - 1);
        }

        // Zoom out
        buttonStartX -= buttonSize + MINIMAL_MARGIN;
        boolean canZoomOut = mapWidget.canZoomOut;
        mapZoomOutButton.active = canZoomOut;
        context.drawTexture(MAP_SELECTION,
                buttonStartX, smallButtonsStartY,
                213, !canZoomOut ? 20 : mapZoomOutButton.isFocused() || isMouseOver(buttonStartX, buttonSize, smallButtonsStartY, buttonSize) ? 10 : 0,
                buttonSize,
                buttonSize
        );
        mapZoomOutButton.setDimensionsAndPosition(buttonSize, buttonSize, buttonStartX, smallButtonsStartY);
        if(canZoomOut && mapZoomOutButton.isFocused() && ModWidget.getFocusEnabled()){
            highlightedFocusMapButton(context, buttonStartX - 1, smallButtonsStartY - 1);
        }

        // Race option
        startY += MINIMAL_MARGIN;
        spawnPointCycledSelection.drawAnchored(context, startX,  startY,true, Text.translatable(controller.getCurrentSpawnKey()), textRenderer);
        spawnPointCycledSelection.enableArrows(controller.haveManySpawns());

        // Spawn point option
        startY += MINIMAL_MARGIN + CycledSelectionWidget.TOTAL_HEIGHT;
        raceCycledSelection.drawAnchored(context, startX,  startY,true, Text.translatable(controller.getCurrentRaceKey()), textRenderer);
        raceCycledSelection.enableArrows(controller.haveManyRaces());

        // Draw selection option
        int sizeX = 52;
        int sizeY = 18;
        buttonStartX = (int)(startX + (mapBackgroundWidth / 2f)) - (sizeX + MINIMAL_MARGIN);
        int buttonStartY = (int)((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f)) + mainPanelHeight - sizeY;

        boolean mouseOver = isMouseOver(buttonStartX, sizeX, buttonStartY, sizeY);
        context.drawTexture(FACTION_SELECTION_BUTTONS,
                buttonStartX,
                buttonStartY,
                103, spawnSelectionRandomizerButton.isFocused() || mouseOver ? 129 : 111,
                sizeX,
                sizeY
        );
        spawnSelectionRandomizerButton.setDimensionsAndPosition(sizeX, sizeY, buttonStartX, buttonStartY);
        if(ModWidget.getFocusEnabled() && spawnSelectionRandomizerButton.isFocused()){
            context.drawTexture(FACTION_SELECTION_BUTTONS,
                    buttonStartX,
                    buttonStartY,
                    103, 148,
                    sizeX,
                    sizeY
            );
        }

        buttonStartX = (int)(startX + (mapBackgroundWidth / 2f)) + MINIMAL_MARGIN;
        mouseOver = isMouseOver(buttonStartX, sizeX, buttonStartY, sizeY);
        context.drawTexture(FACTION_SELECTION_BUTTONS,
                buttonStartX,
                buttonStartY,
                103, spawnSelectionConfirmButton.isFocused() || mouseOver ? 37 : 19,
                sizeX,
                sizeY
        );
        spawnSelectionConfirmButton.setDimensionsAndPosition(sizeX, sizeY, buttonStartX, buttonStartY);
        if(ModWidget.getFocusEnabled() && spawnSelectionConfirmButton.isFocused()){
            context.drawTexture(FACTION_SELECTION_BUTTONS,
                    buttonStartX,
                    buttonStartY,
                    103, 148,
                    sizeX,
                    sizeY
            );
        }
    }

    private void highlightedFocusMapButton(DrawContext context, int startX, int startY){
        context.drawTexture(MAP_SELECTION,
                startX,
                startY,
                200, 0,
                12,
                12
        );
    }

    private void drawFactionBanner(DrawContext context, float startX, float startY){
        DiffuseLighting.disableGuiDepthLighting();

        float size = 32f;

        float x = startX;
        float y = startY;

        int borderMarginX = 2;
        int borderMarginY = 2;

        // Positioning
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.translate(x + borderMarginX + (size / 2f) + 4, y + borderMarginY, 0f);
        matrixStack.push();
        matrixStack.scale(-size, size, 0.1f);
        this.bannerField.pitch = 0.0F;


        // Banner creation
        Faction faction = controller.getCurrentFaction();
        Faction subfaction = controller.getCurrentSubfaction();
        if(subfaction != null)
            faction = subfaction;
        if(faction == null) return;

        DyeColor color = faction.getBaseBannerColor();
        List<BannerData.BannerPatternWithColor> patterns = faction.getBannerPatternsWithColors(this.client.world);
        if(patterns == null || patterns.isEmpty()) {
            LoggerUtil.logError("FactionSelectionScreen::drawFactionBanner - Cannot create banner because values are empty or null");
            return;
        }

        var bannerPatternRegistry = this.client.world.getRegistryManager().get(RegistryKeys.BANNER_PATTERN);

        BannerPatternsComponent.Builder bannerBuilder = new BannerPatternsComponent.Builder();
        for(BannerData.BannerPatternWithColor entry : patterns){
            if(entry == null) continue;
            RegistryEntry<BannerPattern> pattern = bannerPatternRegistry.getEntry(entry.pattern);
            bannerBuilder.add(pattern, entry.color);
        }

        BannerBlockEntityRenderer.renderCanvas(matrixStack, context.getVertexConsumers(), 15728880, OverlayTexture.DEFAULT_UV, this.bannerField, ModelLoader.BANNER_BASE, true, color, bannerBuilder.build());
        matrixStack.pop();
        context.draw();
        DiffuseLighting.enableGuiDepthLighting();

        context.drawTexture(FACTION_SELECTION_BANNER_UI,
                (int) x - 2,
                (int) y - 2,
                0, 0,
                48,
                112
        );
    }



    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        mapWidget.mouseClicked(mouseX, mouseY, button);
        playableNpcPreviewWidget.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        mapWidget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        playableNpcPreviewWidget.mouseReleased(mouseX, mouseY, button);
        mapWidget.mouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        mapWidget.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
}