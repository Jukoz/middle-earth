package net.jukoz.me.client.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.utils.CycledSelectionButtonType;
import net.jukoz.me.client.screens.utils.widgets.CycledSelectionWidget;
import net.jukoz.me.client.screens.utils.widgets.PlayableNpcPreviewWidget;
import net.jukoz.me.client.screens.utils.widgets.SearchBarWidget;
import net.jukoz.me.network.packets.AffiliationPacket;
import net.jukoz.me.network.packets.SpawnDataPacket;
import net.jukoz.me.network.packets.TeleportRequestPacket;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.datas.faction.Faction;
import net.jukoz.me.resources.datas.faction.ModFactions;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
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
import net.minecraft.util.math.BlockPos;

import java.awt.event.KeyEvent;
import java.util.*;

@Environment(EnvType.CLIENT)
public class FactionSelectionScreen extends Screen {
    private static final Identifier FACTION_SELECTION_UI = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection.png");
    private static final Identifier FACTION_SELECTION_BANNER_UI = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection_banner.png");
    private static final Identifier FACTION_SELECTION_BUTTONS = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection_buttons.png");
    private static final Identifier MAP_BACKGROUND = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/map_background.png");
    private static final Identifier MAP_UI_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/map_ui.png");
    private static final Identifier MAP_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/map.png");
    private static final Text FACTION_SELECTION_TITLE = Text.of("faction_selection_screen");
    AbstractClientPlayerEntity player;
    private static final int MINIMAL_MARGIN = 4;
    private boolean focusEnabled;
    private ModelPart bannerField;
    private int currentAlignementIndex = 0;
    private int currentFactionIndex = 0;
    private int currentSubFactionIndex = 0;
    Map<Alignment, List<Faction>> factions = new HashMap<>();
    private SearchBarWidget searchBarWidget;
    private PlayableNpcPreviewWidget playableNpcPreviewWidget;
    private CycledSelectionWidget alignmentSelectionWidget;
    private CycledSelectionWidget factionSelectionWidget;
    private CycledSelectionWidget subfactionSelectionWidget;
    public ButtonWidget factionRandomizerButton;
    public ButtonWidget spawnSelectionConfirmButton;
    public ButtonWidget spawnSelectionRandomizerButton;
    private int mouseX;
    private int mouseY;

    public FactionSelectionScreen() {
        super(FACTION_SELECTION_TITLE);
        focusEnabled = false;
    }

    @Override
    protected void init() {
        this.bannerField = this.client.getEntityModelLoader().getModelPart(EntityModelLayers.BANNER).getChild("flag");

        // Create faction list
        factions.put(Alignment.GOOD, ModFactions.getFactions(Alignment.GOOD));
        factions.put(Alignment.NEUTRAL, ModFactions.getFactions(Alignment.NEUTRAL));
        factions.put(Alignment.EVIL,  ModFactions.getFactions(Alignment.EVIL));
        // Initialize Buttons
        // Search bar
        searchBarWidget = new SearchBarWidget();
        addDrawableChild(searchBarWidget.getSearchBarToggleButton());

        // NpcPreview
        playableNpcPreviewWidget = new PlayableNpcPreviewWidget(this.client.world);
        updateEquipment();

        // Alignment
        ButtonWidget.PressAction alignmentActionLeft = button -> {
            currentAlignementIndex--;
            if(currentAlignementIndex < 0)
                currentAlignementIndex = Alignment.values().length - 1;

            currentFactionIndex = 0;
            currentSubFactionIndex = 0;
            updateEquipment();
        };

        ButtonWidget.PressAction alignmentActionRight = button -> {
            currentAlignementIndex++;
            if(currentAlignementIndex >= Alignment.values().length)
                currentAlignementIndex = 0;
            currentFactionIndex = 0;
            currentSubFactionIndex = 0;
            updateEquipment();
        };

        alignmentSelectionWidget = new CycledSelectionWidget(
                alignmentActionLeft,
                alignmentActionRight,
                null,
                CycledSelectionButtonType.GOLD);
        addDrawableChild(alignmentSelectionWidget.getButtonLeft());
        addDrawableChild(alignmentSelectionWidget.getButtonRight());

        // Faction
        ButtonWidget.PressAction factionActionLeft = button -> {
            currentFactionIndex--;
            if(currentFactionIndex < 0)
                currentFactionIndex = factions.get(Alignment.values()[currentAlignementIndex]).size() - 1;
            currentSubFactionIndex = 0;
            updateEquipment();
        };

        ButtonWidget.PressAction factionActionRight = button -> {
            currentFactionIndex++;
            if(currentFactionIndex >= factions.get(Alignment.values()[currentAlignementIndex]).size())
                currentFactionIndex = 0;
            currentSubFactionIndex = 0;
            updateEquipment();
        };

        factionSelectionWidget = new CycledSelectionWidget(
                factionActionLeft,
                factionActionRight,
                null,
                CycledSelectionButtonType.SILVER);
        addDrawableChild(factionSelectionWidget.getButtonLeft());
        addDrawableChild(factionSelectionWidget.getButtonRight());

        // Subfaction
        ButtonWidget.PressAction subfactionActionLeft = button -> {
            currentSubFactionIndex--;
            if(currentSubFactionIndex < 0)
                currentSubFactionIndex = factions.get(Alignment.values()[currentAlignementIndex]).get(currentFactionIndex).getSubFactions().size() - 1;
            updateEquipment();
        };

        ButtonWidget.PressAction subfactionActionRight = button -> {
            currentSubFactionIndex++;
            if(currentSubFactionIndex >= factions.get(Alignment.values()[currentAlignementIndex]).get(currentFactionIndex).getSubFactions().size())
                currentSubFactionIndex = 0;
            updateEquipment();
        };

        subfactionSelectionWidget = new CycledSelectionWidget(
                subfactionActionLeft,
                subfactionActionRight,
                null,
                CycledSelectionButtonType.NORMAL);
        addDrawableChild(subfactionSelectionWidget.getButtonLeft());
        addDrawableChild(subfactionSelectionWidget.getButtonRight());

        ButtonWidget.PressAction factionRandomizer = new ButtonWidget.PressAction() {
            private int randomize(int tentativeLeft){
                Random random = new Random();
                // Alignment randomizer
                currentAlignementIndex = random.nextInt(Alignment.values().length);
                Alignment alignment = Alignment.values()[currentAlignementIndex];

                // Recursive trigger
                if(factions.get(alignment) == null || factions.get(alignment).isEmpty()){
                    if(tentativeLeft > 0){
                        return tentativeLeft + randomize(tentativeLeft - 1);
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
            @Override
            public void onPress(ButtonWidget button) {
                this.randomize(5);
                updateEquipment();
            }
        };
        factionRandomizerButton = ButtonWidget.builder(Text.of("Faction randomizer clicked"), factionRandomizer).build();
        addDrawableChild(factionRandomizerButton);

        // Random spawn selection
        ButtonWidget.PressAction randomSpawnSelectionAction = button -> {
            // TODO : Add proper logic to randomized spawn selection
        };

        spawnSelectionRandomizerButton = ButtonWidget.builder(Text.of("Click on random spawn selection button"), randomSpawnSelectionAction).build();
        addDrawableChild(spawnSelectionRandomizerButton);

        // Confirm spawn selection
        ButtonWidget.PressAction confirmSpawnSelectionAction = button -> {
            Alignment alignment = Alignment.values()[currentAlignementIndex];
            Faction faction = (!factions.get(alignment).isEmpty()) ? factions.get(alignment).get(currentFactionIndex) : null;
            Faction subFaction = (faction != null) ? faction.getSubfaction(currentSubFactionIndex) : null;

            if(subFaction != null) faction = subFaction;

            if(faction == null || faction.getSpawnCoordinates() == null || faction.getSpawnCoordinates().isEmpty()) return;
            int x = faction.getSpawnCoordinates().get(0).x;
            int z = faction.getSpawnCoordinates().get(0).z;

            ClientPlayNetworking.send(new TeleportRequestPacket(x, z));
            ClientPlayNetworking.send(new AffiliationPacket(currentAlignementIndex, currentFactionIndex, currentSubFactionIndex));
            if(player != null){
                BlockPos overworldBlockPos = player.getBlockPos();
                BlockPos middleEarthblockPos = new BlockPos(x, (int) MiddleEarthHeightMap.getHeight(x, z), z);

                ClientPlayNetworking.send(new SpawnDataPacket(
                        overworldBlockPos.getX(), overworldBlockPos.getY(), overworldBlockPos.getZ(),
                        middleEarthblockPos.getX(), middleEarthblockPos.getY(), middleEarthblockPos.getZ()
                ));
            }
        };

        spawnSelectionConfirmButton = ButtonWidget.builder(Text.of("Click on spawn selection confirm button"), confirmSpawnSelectionAction).build();
        addDrawableChild(spawnSelectionConfirmButton);

        addDrawableChild(searchBarWidget.getScreenClickButton());
    }

    private void updateEquipment(){
        Alignment alignment = Alignment.values()[currentAlignementIndex];
        Faction faction = (!factions.get(alignment).isEmpty()) ? factions.get(alignment).get(currentFactionIndex) : null;
        Faction subFaction = (faction != null) ? faction.getSubfaction(currentSubFactionIndex) : null;
        if(subFaction != null) faction = subFaction;

        if(faction != null)
            playableNpcPreviewWidget.updateEntity(faction.getPreviewGear(), faction.getPreviewRace());
        else
            playableNpcPreviewWidget.displayDefaultEntity();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        Entity cameraEntity = this.client.getCameraEntity();
        if (cameraEntity != null) {
            if (cameraEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
                this.mouseX = mouseX;
                this.mouseY = mouseY;
                this.player = abstractClientPlayerEntity;
                this.renderBackground(context, mouseX, mouseY, delta);
                this.drawPanels(context);
            } else {
                this.player = null;
            }
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Keybind : Escape || Other Escape
        if(keyCode == KeyEvent.VK_ESCAPE){
            this.close();
            return true;
        }

        // Keybind : Tabulation
        if(keyCode == KeyEvent.VK_CODE_INPUT && !focusEnabled && !CycledSelectionWidget.focusEnabled()){
            focusEnabled = true;
            CycledSelectionWidget.toggleFocus();
            SearchBarWidget.toggleFocus();
            return true;
        }
        searchBarWidget.keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    protected void drawPanels(DrawContext context){
        int mainPanelWidth = 169;
        int mainPanelHeight = 207;

        drawFactionSelectionPanel(context, mainPanelWidth, mainPanelHeight, mouseX, mouseY);
        drawMapPanel(context, mainPanelWidth, mainPanelHeight);
        drawInformationPanel(context, mainPanelWidth, mainPanelHeight);
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

        drawFactionBanner(context, startX + mainPanelWidth - 50, startY + 5);
    }

    private void drawFactionSelectionPanel(DrawContext context, int mainPanelWidth, int mainPanelHeight, int mouseX, int mouseY) {
        int endX = (int) ((context.getScaledWindowWidth() / 2f) - (mainPanelWidth / 2f) - MINIMAL_MARGIN);
        int startX = Math.max(MINIMAL_MARGIN, endX  - mainPanelWidth);
        int startY = (int) ((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f));

        int widthX = endX - Math.max(MINIMAL_MARGIN, startX);

        // Draw alignment option
        Alignment alignment = Alignment.values()[currentAlignementIndex];
        Faction faction = (!factions.get(alignment).isEmpty()) ? factions.get(alignment).get(currentFactionIndex) : null;
        Faction subFaction = (faction != null) ? faction.getSubfaction(currentSubFactionIndex) : null;

        int centerX = endX - CycledSelectionWidget.TOTAL_WIDTH / 2;
        int endY = (int) ((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f)) + mainPanelHeight;

        int newStartY = startY + searchBarWidget.drawSearchBarCentered(context, centerX, startY, textRenderer);
        searchBarWidget.updateMouse(mouseX, mouseY);
        searchBarWidget.setEndY(endY);

        if(searchBarWidget.searchIsToggled()) {
            alignmentSelectionWidget.enableArrows(false);
            factionSelectionWidget.enableArrows(false);
            subfactionSelectionWidget.enableArrows(false);
            factionRandomizerButton.active = false;
            searchBarWidget.drawSearchResultsCentered(context, centerX, startY, factions);
            return;
        }

        // Alignment
        factionSelectionWidget.enableArrows(Alignment.values().length > 1);
        newStartY += MINIMAL_MARGIN + alignmentSelectionWidget.drawAnchored(context, endX, newStartY, false, alignment.getName(), textRenderer, mouseX, mouseY);

        // Faction
        factionSelectionWidget.enableArrows(!factions.get(alignment).isEmpty() && factions.get(alignment).size() > 1);
        if(!factions.get(alignment).isEmpty()){
            newStartY += MINIMAL_MARGIN + factionSelectionWidget.drawAnchored(context, endX, newStartY, false, (faction == null) ? null : faction.tryGetShortName(), textRenderer, mouseX, mouseY);

            // Subfaction
            subfactionSelectionWidget.enableArrows(faction != null && (faction.getSubFactions() != null && faction.getSubFactions().size() > 1));
            if(faction != null && (faction.getSubFactions() != null && !faction.getSubFactions().isEmpty()))
                subfactionSelectionWidget.drawAnchored(context, endX, newStartY, false, (subFaction == null) ? null : subFaction.tryGetShortName(), textRenderer, mouseX, mouseY);
        }
        playableNpcPreviewWidget.drawCenteredAnchoredBottom(context, centerX, endY - 18 - (MINIMAL_MARGIN * 2));
        drawFactionRandomizer(context, centerX, endY);

        if(!factionRandomizerButton.active)
            factionRandomizerButton.active = true;
    }


    private boolean isMouseOver(int startX, int sizeX, int startY, int sizeY) {
        return mouseX >= startX && mouseX <= startX + sizeX
                && mouseY >= startY && mouseY <= startY + sizeY;
    }

    protected void drawFactionRandomizer(DrawContext context, int centerX, int endY) {
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
        if(focusEnabled && factionRandomizerButton.isFocused()){
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
        int widthX = Math.min(context.getScaledWindowWidth() - startX, mainPanelWidth -MINIMAL_MARGIN);
        context.drawTexture(MAP_TEXTURE,
                startX, startY,
                0, 0,
                widthX -MINIMAL_MARGIN,
                widthX
        );

        // Draw selection option
        int sizeX = 52;
        int sizeY = 18;
        int buttonStartX = (int)(startX + (widthX / 2f)) - (sizeX + 10);
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
        if(focusEnabled && spawnSelectionRandomizerButton.isFocused()){
            context.drawTexture(FACTION_SELECTION_BUTTONS,
                    buttonStartX,
                    buttonStartY,
                    103, 148,
                    sizeX,
                    sizeY
            );
        }

        buttonStartX = (int)(startX + (widthX / 2f));
        mouseOver = isMouseOver(buttonStartX, sizeX, buttonStartY, sizeY);
        context.drawTexture(FACTION_SELECTION_BUTTONS,
                buttonStartX,
                buttonStartY,
                103, spawnSelectionConfirmButton.isFocused() || mouseOver ? 37 : 19,
                sizeX,
                sizeY
        );
        spawnSelectionConfirmButton.setDimensionsAndPosition(sizeX, sizeY, buttonStartX, buttonStartY);
        if(focusEnabled && spawnSelectionConfirmButton.isFocused()){
            context.drawTexture(FACTION_SELECTION_BUTTONS,
                    buttonStartX,
                    buttonStartY,
                    103, 148,
                    sizeX,
                    sizeY
            );
        }
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
        Alignment alignment = Alignment.values()[currentAlignementIndex];
        Faction faction = (!factions.get(alignment).isEmpty()) ? factions.get(alignment).get(currentFactionIndex) : null;
        Faction subFaction = (faction != null) ? faction.getSubfaction(currentSubFactionIndex) : null;
        if(subFaction != null) faction = subFaction;
        if(faction == null) return;

        DyeColor color = faction.getBaseBannerColor();
        List<Faction.BannerPatternWithColor> patterns = faction.getBannerPatternsWithColors(this.client.world);
        if(patterns == null || patterns.isEmpty()) {
            LoggerUtil.logError("FactionSelectionScreen::drawFactionBanner - Cannot create banner because values are empty or null");
            return;
        }

        var bannerPatternRegistry = this.client.world.getRegistryManager().get(RegistryKeys.BANNER_PATTERN);

        BannerPatternsComponent.Builder bannerBuilder = new BannerPatternsComponent.Builder();
        for(Faction.BannerPatternWithColor entry : patterns){
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

    // Very WIP, will be worked on once I start making the map widget
    /*
    private void drawMap(DrawContext context, float anchorX, float anchorY, int guiScale){
        int size = (int) (400 * guiScaleModifier.get(guiScale));

        int margin = 4;

        context.drawTexture(MAP_BACKGROUND,
                (int) anchorX - ((size + margin) / 2),
                (int) anchorY - ((size + margin) / 2),
                0, 0,
                size + margin,
                size + margin,
                size + margin,
                size + margin
        );

        context.drawTexture(MAP_TEXTURE,
                (int) anchorX - (size / 2),
                (int) anchorY - (size / 2),
                0, 0,
                size,
                size,
                size,
                size
        );

        drawMapMarkers(context, (int) anchorX - ((float) size / 2) - margin, (int) anchorY - ((float) size / 2) - margin, size, guiScale);
    }

    private void drawMapMarkers(DrawContext context, float anchorX, float anchorY, int size, int guiScale) {
        // TODO : Clean this up to be data driven!
        int markerSize = (int) (8);
        int x = (int) anchorX;
        int y = (int) anchorY;

        int mapSize = 96000;
        Vector2f coord01 = new Vector2f(48000, 48000);
        coord01.x = ((float) size / mapSize * coord01.x);
        coord01.y = ((float) size / mapSize * coord01.y);

        Vector2f coord02 = new Vector2f(64700, 23000);
        coord02.x = ((float) size / mapSize * coord02.x);
        coord02.y = ((float) size / mapSize * coord02.y);

        Vector2f coord03 = new Vector2f(62000, 57000);
        coord03.x = ((float) size / mapSize * coord03.x);
        coord03.y = ((float) size / mapSize * coord03.y);

        context.drawTexture(MAP_UI_TEXTURE,
                (int) (x + (coord01.x)),
                (int) (y + (coord01.y)),
                54, 0,
                markerSize,
                markerSize
        );

        context.drawTexture(MAP_UI_TEXTURE,
                (int) (x + (coord02.x)),
                (int) (y + (coord02.y)),
                54, 8,
                markerSize,
                markerSize
        );

        context.drawTexture(MAP_UI_TEXTURE,
                (int) (x + (coord03.x)),
                (int) (y + (coord03.y)),
                54, 16,
                markerSize,
                markerSize
        );
    }

     */
}