package net.jukoz.me.client.screens;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.data.FactionNpcPreviewData;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.item.ModBannerItems;
import net.jukoz.me.item.utils.ModBannerPatterns;
import net.jukoz.me.resource.data.Alignment;
import net.jukoz.me.resource.data.Race;
import net.jukoz.me.resource.data.faction.Faction;
import net.jukoz.me.resource.data.faction.ModFactions;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ScrollableWidget;
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
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.*;

@Environment(EnvType.CLIENT)
public class FactionSelectionScreen extends Screen {
    private static final Identifier FACTION_SELECTION_UI = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection.png");
    private static final Identifier FACTION_SELECTION_SEARCH_UI = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection_search.png");
    private static final Identifier FACTION_SELECTION_BANNER_UI = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection_banner.png");
    private static final Identifier FACTION_SELECTION_BUTTONS = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection_buttons.png");


    private static final Identifier MAP_BACKGROUND = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/map_background.png");
    private static final Identifier MAP_UI_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/map_ui.png");
    private static final Identifier MAP_TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/map.png");
    private static final Text ONBOARDING_TEXT = Text.of("Onboarding");
    private static boolean debug = false;
    AbstractClientPlayerEntity player;
    private static final Quaternionf ENTITY_ROTATION;
    private static final Vector3f VECTOR;
    private static final int MINIMAL_MARGIN = 8;

    private static List<FactionNpcPreviewData> NPC_PREVIEWS = new ArrayList<>();

    private ModelPart bannerField;

    private int currentAlignementIndex = 0;
    private int currentFactionIndex = 0;
    private int currentSubFactionIndex = 0;

    private LongbeardDwarfEntity dwarfEntity;
    private GondorHumanEntity humanEntity;
    private MordorOrcEntity orcEntity;
    private GaladhrimElfEntity elfEntity;
    private ShireHobbitEntity hobbitEntity;

    @Nullable
    LivingEntity entity;
    Map<Alignment, List<Faction>> factions = new HashMap<>();
    public ButtonWidget screenClick;
    public ButtonWidget searchBarToggleButton;
    public ScrollableWidget searchResultScrollBar;
    public ButtonWidget alignmentButtonLeft;
    public ButtonWidget alignmentButtonRight;
    public ButtonWidget factionButtonLeft;
    public ButtonWidget factionButtonRight;
    public ButtonWidget subfactionButtonLeft;
    public ButtonWidget subfactionButtonRight;
    public ButtonWidget factionRandomizerButton;

    private int mouseX;
    private int mouseY;
    private boolean searchResultToggle = false;
    private boolean searchBarToggle = false;
    private int currentSearchInputIndex = 0;
    private String searchBarInput = "";

    public FactionSelectionScreen() {
        super(ONBOARDING_TEXT);
    }
    @Override
    protected void init() {
        this.bannerField = this.client.getEntityModelLoader().getModelPart(EntityModelLayers.BANNER).getChild("flag");

        // Create faction list
        factions.put(Alignment.Good, ModFactions.getFactions(Alignment.Good));
        factions.put(Alignment.Neutral, ModFactions.getFactions(Alignment.Neutral));
        factions.put(Alignment.Evil,  ModFactions.getFactions(Alignment.Evil));
        // Initialize Buttons
        // Search bar
        searchBarToggle = false;
        ButtonWidget.PressAction searchBarInputToggle = button -> {
            if(!searchBarToggle)
                searchBarToggle = true;
        };
        searchBarToggleButton = ButtonWidget.builder(Text.of("Toggle search bar input"), searchBarInputToggle).build();
        addDrawableChild(searchBarToggleButton);

        // Search Result ScrollBar
        // TODO : Add scrollbar button

        // Alignment
        ButtonWidget.PressAction alignmentActionLeft = button -> {
            currentAlignementIndex--;
            if(currentAlignementIndex < 0)
                currentAlignementIndex = Alignment.values().length - 1;

            currentFactionIndex = 0;
            currentSubFactionIndex = 0;
            updatePreviewEquipment();
        };

        ButtonWidget.PressAction alignmentActionRight = button -> {
            currentAlignementIndex++;
            if(currentAlignementIndex >= Alignment.values().length)
                currentAlignementIndex = 0;
            currentFactionIndex = 0;
            currentSubFactionIndex = 0;
            updatePreviewEquipment();
        };

        alignmentButtonLeft = ButtonWidget.builder(Text.of("Left alignment selection clicked"), alignmentActionLeft).build();
        alignmentButtonRight = ButtonWidget.builder(Text.of("Right alignment selection clicked"), alignmentActionRight).build();
        addDrawableChild(alignmentButtonLeft);
        addDrawableChild(alignmentButtonRight);

        // Faction
        ButtonWidget.PressAction factionActionLeft = button -> {
            currentFactionIndex--;
            if(currentFactionIndex < 0)
                currentFactionIndex = factions.get(Alignment.values()[currentAlignementIndex]).size() - 1;
            currentSubFactionIndex = 0;
            updatePreviewEquipment();
        };

        ButtonWidget.PressAction factionActionRight = button -> {
            currentFactionIndex++;
            if(currentFactionIndex >= factions.get(Alignment.values()[currentAlignementIndex]).size())
                currentFactionIndex = 0;
            currentSubFactionIndex = 0;
            updatePreviewEquipment();
        };

        factionButtonLeft = ButtonWidget.builder(Text.of("Left faction selection clicked"), factionActionLeft).build();
        factionButtonRight = ButtonWidget.builder(Text.of("Right faction selection clicked"), factionActionRight).build();
        addDrawableChild(factionButtonLeft);
        addDrawableChild(factionButtonRight);

        // Subfaction
        ButtonWidget.PressAction subfactionActionLeft = button -> {
            currentSubFactionIndex--;
            if(currentSubFactionIndex < 0)
                currentSubFactionIndex = factions.get(Alignment.values()[currentAlignementIndex]).get(currentFactionIndex).getSubFactions().size() - 1;
            updatePreviewEquipment();
        };

        ButtonWidget.PressAction subfactionActionRight = button -> {
            currentSubFactionIndex++;
            if(currentSubFactionIndex >= factions.get(Alignment.values()[currentAlignementIndex]).get(currentFactionIndex).getSubFactions().size())
                currentSubFactionIndex = 0;
            updatePreviewEquipment();
        };

        subfactionButtonLeft = ButtonWidget.builder(Text.of("Left subfaction selection clicked"), subfactionActionLeft).build();
        subfactionButtonRight = ButtonWidget.builder(Text.of("Right subfaction selection clicked"), subfactionActionRight).build();
        addDrawableChild(subfactionButtonLeft);
        addDrawableChild(subfactionButtonRight);

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
                updatePreviewEquipment();
            }
        };
        factionRandomizerButton = ButtonWidget.builder(Text.of("Faction randomizer clicked"), factionRandomizer).build();
        addDrawableChild(factionRandomizerButton);

        humanEntity = new GondorHumanEntity(ModEntities.GONDORIAN_SOLDIER, this.client.world);
        dwarfEntity = new LongbeardDwarfEntity(ModEntities.LONGBEARD_SOLDIER, this.client.world);
        elfEntity = new GaladhrimElfEntity(ModEntities.LORIEN_SOLDIER, this.client.world);
        orcEntity = new MordorOrcEntity(ModEntities.MORDOR_ORC_SOLDIER, this.client.world);
        hobbitEntity = new ShireHobbitEntity(ModEntities.HOBBIT_CIVILIAN, this.client.world);
        updatePreviewEquipment();

        // Screen click
        ButtonWidget.PressAction screenClickButton = new ButtonWidget.PressAction() {
            @Override
            public void onPress(ButtonWidget button) {
                LoggerUtil.logDebugMsg("clicked in the screen");
                searchBarToggle = false;
                searchResultToggle = false;
            }
        };
        screenClick = ButtonWidget.builder(Text.of("Click on screen"), screenClickButton).build();
        addDrawableChild(screenClick);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        Entity cameraEntity = this.client.getCameraEntity();
        if (cameraEntity != null) {
            if (cameraEntity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity) {
                this.mouseX = mouseX;
                this.mouseY = mouseY;
                this.player = abstractClientPlayerEntity;
                screenClick.setDimensionsAndPosition(context.getScaledWindowWidth(), context.getScaledWindowHeight(), 0,0);
                this.renderBackground(context, mouseX, mouseY, delta);
                int guiScale = this.client.options.getGuiScale().getValue();
                this.drawPanels(context, guiScale);
            } else {
                this.player = null;
            }
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == 27){
            this.close();
            return true;
        }
        if (keyCode == 256) {
            this.close();
            return true;
        }
        if(searchBarToggle){
            if(keyCode == 257){
                triggerSearch();
            }
            if(((keyCode >= 65 && keyCode <= 90) || keyCode == 32) && searchBarInput.length() < 12) {
                String character = String.valueOf((char)keyCode);
                if(modifiers == 0)
                    character = character.toLowerCase();
                searchBarInput += character;
                currentSearchInputIndex ++;
            }
            else if(!searchBarInput.isEmpty()){
                if((keyCode == 259))
                    searchBarInput = searchBarInput.substring(0, searchBarInput.length() - 1);
                else if(keyCode == 261)
                    searchBarInput = "";
            }
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void triggerSearch() {
        searchResultToggle = true;
    }

    protected void drawPanels(DrawContext context, int guiScale){
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

        drawFactionBanner(context, startX + mainPanelWidth - 60, startY + 3);
    }

    private void drawFactionSelectionPanel(DrawContext context, int mainPanelWidth, int mainPanelHeight, int mouseX, int mouseY) {
        int endX = (int) ((context.getScaledWindowWidth() / 2f) - (mainPanelWidth / 2f) - MINIMAL_MARGIN);
        int startX = Math.max(MINIMAL_MARGIN, endX - mainPanelWidth);
        int startY = (int) ((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f));

        int widthX = endX - Math.max(MINIMAL_MARGIN, startX);

        int centerX = startX + (int) ((widthX / 2f));

        // Draw alignment option
        Alignment alignment = Alignment.values()[currentAlignementIndex];
        Faction faction = (!factions.get(alignment).isEmpty()) ? factions.get(alignment).get(currentFactionIndex) : null;
        Faction subFaction = (faction != null) ? faction.getSubfaction(currentSubFactionIndex) : null;

        int newStartY = startY + drawFactionSearchBarWidget(context, centerX, startY, "Search...");

        boolean showbuttons = !searchResultToggle && Alignment.values().length > 1;
        newStartY += drawSelectionWidget(context, 0,95, centerX, newStartY, alignment.getLangKey(), alignmentButtonLeft, alignmentButtonRight, showbuttons);
        showbuttons =  !searchResultToggle && !factions.get(alignment).isEmpty() && factions.get(alignment).size() > 1;
        newStartY += drawSelectionWidget(context, 0,114, centerX, newStartY, (faction == null) ? null : faction.getLangKey(), factionButtonLeft, factionButtonRight, showbuttons);
        showbuttons = !searchResultToggle && faction != null && (faction.getSubFactions() != null && faction.getSubFactions().size() > 1);
        drawSelectionWidget(context, 0,133, centerX, newStartY, (subFaction == null) ? null : subFaction.getLangKey(), subfactionButtonLeft, subfactionButtonRight, showbuttons);

        int endY = (int) ((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f)) + mainPanelHeight;

        drawSearchResultWidget(context, centerX, startY, endY);

        if(!searchResultToggle){
            drawNpcPreview(context, centerX, endY);
            drawFactionRandomizer(context, centerX, endY);
            factionRandomizerButton.active = true;
        } else{
            factionRandomizerButton.active = false;
        }
    }

    private void drawSearchResultWidget(DrawContext context, int centerX, int startY, int endY) {
        int previousPanelSizeY = 18;

        int panelSizeX = 102;
        int panelSizeY = 14;
        int panelBorderSizeY = 4;
        int footerPanelSizeY = 11;

        int sideMargins = MINIMAL_MARGIN / 2;

        // Search bar button
        int startX = (int) (centerX - (panelSizeX / 2f));
        startY += previousPanelSizeY + sideMargins / 2;

        // Popup
        if(searchResultToggle){
            int valueTotal = 0;
            Map<Alignment, List<Faction>> foundFactions = factions;
            for(int i = 0; i < foundFactions.size(); i++){
                List<Faction> f = foundFactions.get(Alignment.values()[i]);
                if(f == null || f.isEmpty()) continue;
                valueTotal ++; // Per alignment with values
                for(int j = 0; j < f.size(); j++){
                    valueTotal ++; // Per faction
                    HashMap<Identifier, Faction> subF = f.get(j).getSubFactions();
                    if(subF == null || subF.isEmpty()) continue;
                    valueTotal += subF.size(); // Per subFactions
                }
            }
            // Top
            context.drawTexture(FACTION_SELECTION_SEARCH_UI,
                    startX, startY,
                    0, 38,
                    panelSizeX,
                    panelBorderSizeY
            );
            // Center
            startY += panelBorderSizeY;
            int valueAmount = Math.min(
                    (endY - startY - panelBorderSizeY) / panelSizeY,
                    valueTotal
            );

            for(int i = 0; i < valueAmount; i++){
                context.drawTexture(FACTION_SELECTION_SEARCH_UI,
                        startX, startY + panelSizeY * i,
                        0, 43,
                        panelSizeX, panelSizeY
                );
            }

            // Footer
            context.drawTexture(FACTION_SELECTION_SEARCH_UI,
                    startX,
                    startY + (valueAmount * panelSizeY),
                    0, 58,
                    panelSizeX,
                    footerPanelSizeY
            );

            // End
            context.drawTexture(FACTION_SELECTION_SEARCH_UI,
                    startX,
                    startY + (valueAmount * panelSizeY) + footerPanelSizeY,
                    0, 70,
                    panelSizeX,
                    panelBorderSizeY
            );

            // Scroll Bar Button
            startY -= panelBorderSizeY;

            context.drawTexture(FACTION_SELECTION_SEARCH_UI,
                    startX + panelSizeX - 5,
                    startY + 1,
                    103, 39,
                    4,
                    9
            );

            int indexOffset = 0; // TODO : Adjust with scrollbar value/height

            int valuePanelSizeX = 93;
            int valuePanelSizeY = 14;

            int valuePanelStartX = startX + 3;
            // Create pool of resources with buttons
            for(int i = 0; i < valueAmount; i ++){
                int valuePanelStartY = startY + panelBorderSizeY + (i * panelSizeY);

                boolean mouseIsOver = isMouseOver(startX, valuePanelSizeX, valuePanelStartY, valuePanelSizeY);
                int uvY = mouseIsOver ? 89 : 75;

                if(i == 0){
                    uvY = 131;
                } else if(i % 3 == 0){
                    uvY = mouseIsOver ? 117 : 103;
                }

                context.drawTexture(FACTION_SELECTION_SEARCH_UI,
                        valuePanelStartX, valuePanelStartY,
                        0, uvY,
                        valuePanelSizeX, valuePanelSizeY
                );
            }


/*
            for(int i = 0; i < temporaryValues.size(); i++){
                MutableText text = Text.translatable(temporaryValues.get(i));
                text.asTruncatedString(15);

                context.drawTexture(FACTION_SELECTION_SEARCH_UI,
                        startX, newStartY,
                        0, isMouseOver(startX, 95, newStartY, 14) ? 69 : 55,
                        95, 14
                );

                context.drawText(textRenderer, text,
                        startX + MINIMAL_MARGIN,
                        newStartY + 3,
                        0, false);
                newStartY += 14;
                /*
                                newStartY += textRenderer.fontHeight + sideMargins;
                if(i != temporaryValues.size() - 1){
                    context.drawTexture(FACTION_SELECTION_SEARCH_UI,
                            startX, newStartY,
                            0, 52,
                            102, 3
                    );
                    newStartY += sideMargins;
                }
                 */
        }
    }

    protected int drawFactionSearchBarWidget(DrawContext context, int centerX, int startY, String key) {
        int panelSizeX = 102;
        int panelSizeY = 18;
        int sideMargins = MINIMAL_MARGIN / 2;
        int magnifyingGlassSizeX = 14;
        int magnifyingGlassSizeY = 14;

        // Search bar button
        int startX = (int) (centerX - (panelSizeX / 2f));
        context.drawTexture(FACTION_SELECTION_SEARCH_UI,
                startX,
                startY,
                0,
                isMouseOver(startX, panelSizeX, startY, panelSizeY) ? 19 : 0,
                panelSizeX,
                panelSizeY
        );
        searchBarToggleButton.setDimensionsAndPosition(panelSizeX, panelSizeY, startX, startY);

        MutableText text = Text.translatable((!searchBarToggle && searchBarInput.isEmpty()) ? key : searchBarInput);
        text.asTruncatedString(16);
        context.drawText(textRenderer, text,
                startX + magnifyingGlassSizeX + MINIMAL_MARGIN,
                startY + (int) ((panelSizeY / 2f) - (textRenderer.fontHeight / 2f)) + 1,
                16777215, false);


        // Search bar magnifying
        context.drawTexture(FACTION_SELECTION_SEARCH_UI,
                startX + sideMargins,
                startY + 2,
                102, 0,
                magnifyingGlassSizeX,
                magnifyingGlassSizeY
        );

        return panelSizeY + MINIMAL_MARGIN / 2;
    }

    protected int drawSelectionWidget(DrawContext context, int uvX, int uvY, int centerX, int startY, String key, ButtonWidget buttonLeft, ButtonWidget buttonRight, boolean showButtons){
        int panelSizeX = 102;
        int panelSizeY = 18;
        int buttonSizeX = 9;
        int buttonSizeY = 13;
        int sideMargins = MINIMAL_MARGIN / 2;

        if(key == null) {
            if(buttonLeft != null)
                buttonLeft.active = false;
            if(buttonRight != null)
                buttonRight.active = false;
            return panelSizeY + sideMargins;
        }


        // Text Background
        context.drawTexture(FACTION_SELECTION_BUTTONS,
                (int) (centerX - (panelSizeX / 2f)),
                startY,
                0, uvY,
                panelSizeX,
                panelSizeY
        );

        // Dynamic text panel
        MutableText text = Text.translatable(key);
        text.asTruncatedString(16);
        context.drawText(textRenderer, text,
                (int)(centerX - textRenderer.getWidth(text) / 2f),
                startY + (int) ((panelSizeY / 2f) - (textRenderer.fontHeight / 2f)) + 1,
                0, false);

        if(!showButtons){
            buttonLeft.active = false;
            buttonRight.active = false;
            return panelSizeY + sideMargins;
        }

        if(buttonLeft != null)
            buttonLeft.active = true;
        if(buttonRight != null)
            buttonRight.active = true;

        //  Left button
        int leftButtonStartX = (int) (centerX - (panelSizeX / 2f) - buttonSizeX) - sideMargins;
        int leftButtonStartY = (int) (startY + (panelSizeY / 2f) - (buttonSizeY / 2f));

        context.drawTexture(FACTION_SELECTION_BUTTONS,
                leftButtonStartX, leftButtonStartY,
                192, (isMouseOver(leftButtonStartX, buttonSizeX, leftButtonStartY, buttonSizeY)) ? 13 : 0,
                buttonSizeX, buttonSizeY
        );
        if(buttonRight != null)
            buttonRight.setDimensionsAndPosition(buttonSizeX, buttonSizeY, leftButtonStartX, leftButtonStartY);

        //  Right button
        int rightButtonStartX = (int) (centerX + (panelSizeX / 2f)) + sideMargins;
        int rightButtonStartY = (int) (startY + (panelSizeY / 2f) - (buttonSizeY / 2f));

        context.drawTexture(FACTION_SELECTION_BUTTONS,
                rightButtonStartX, rightButtonStartY,
                201, (isMouseOver(rightButtonStartX, buttonSizeX, rightButtonStartY, buttonSizeY)) ? 13 : 0,
                buttonSizeX, buttonSizeY
        );
        if(buttonLeft != null)
            buttonLeft.setDimensionsAndPosition(buttonSizeX, buttonSizeY, rightButtonStartX, rightButtonStartY);

        return panelSizeY + sideMargins;
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

        context.drawTexture(FACTION_SELECTION_BUTTONS,
                startX,
                startY,
                103, (isMouseOver(startX, sizeX, startY, sizeY)) ? 92 : 74,
                sizeX,
                sizeY
        );
        factionRandomizerButton.setDimensionsAndPosition(sizeX, sizeY, startX, startY);
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
        int selectionWidgetSizeX = 102;
        int selectionWidgetSizeY = 18;
        int selectionWidgetStartX = (int) (startX + ((widthX -MINIMAL_MARGIN) / 2f));
        int selectionWidgetStartY = startY + widthX + (MINIMAL_MARGIN /2);

        drawSelectionWidget(context, 0, 133, selectionWidgetStartX, selectionWidgetStartY, "none", null, null, true);

        int sizeX = 52;
        int sizeY = 18;

        context.drawTexture(FACTION_SELECTION_BUTTONS,
                (int)(startX + (widthX / 2f)) - (sizeX + 10),
                (int)((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f)) + mainPanelHeight - sizeY,
                103, 111,
                sizeX,
                sizeY
        );

        context.drawTexture(FACTION_SELECTION_BUTTONS,
                (int)(startX + (widthX / 2f)),
                (int)((context.getScaledWindowHeight() / 2f) - (mainPanelHeight / 2f)) + mainPanelHeight - sizeY,
                103, 19,
                sizeX,
                sizeY
        );
    }

    private void updatePreviewEquipment() {
        Alignment alignment = Alignment.values()[currentAlignementIndex];
        Faction faction = (!factions.get(alignment).isEmpty()) ? factions.get(alignment).get(currentFactionIndex) : null;
        Faction subFaction = (faction != null) ? faction.getSubfaction(currentSubFactionIndex) : null;
        updatePreviewEquipment(faction, subFaction);
    }

    private void updatePreviewEquipment(Faction faction, Faction subFaction){
        if(faction == null) {
            this.entity = null;
            return;
        }
        if(subFaction != null) faction = subFaction;

        updateEntityFromFaction(faction);

        if(this.entity == null) return;

        this.entity.bodyYaw = 210f;
        this.entity.setPitch(0f);
        this.entity.headYaw = this.entity.getBodyYaw();
        this.entity.prevHeadYaw = this.entity.getBodyYaw();

        this.entity.equipStack(EquipmentSlot.HEAD, new ItemStack(faction.getPreviewGearAt(EquipmentSlot.HEAD)));
        this.entity.equipStack(EquipmentSlot.CHEST, new ItemStack(faction.getPreviewGearAt(EquipmentSlot.CHEST)));
        this.entity.equipStack(EquipmentSlot.LEGS, new ItemStack(faction.getPreviewGearAt(EquipmentSlot.LEGS)));
        this.entity.equipStack(EquipmentSlot.FEET, new ItemStack(faction.getPreviewGearAt(EquipmentSlot.FEET)));
        this.entity.equipStack(EquipmentSlot.MAINHAND, new ItemStack(faction.getPreviewGearAt(EquipmentSlot.MAINHAND)));
        this.entity.equipStack(EquipmentSlot.OFFHAND, new ItemStack(faction.getPreviewGearAt(EquipmentSlot.OFFHAND)));
    }

    private void updateEntityFromFaction(Faction faction) {
        this.entity =
                switch ( faction.getPreviewRace() )
                {
                    case Race.Human -> humanEntity;
                    case Race.Dwarf -> dwarfEntity;
                    case Race.Orc -> orcEntity;
                    case Race.Elf -> elfEntity;
                    case Race.Hobbit -> hobbitEntity;
                    default -> humanEntity;
                };
    }

    private void drawNpcPreview(DrawContext context, float anchorX, float anchorY){
        float size = 40f;
        float x = anchorX;
        float y = anchorY - 18 - MINIMAL_MARGIN;

        DiffuseLighting.disableGuiDepthLighting();
        DiffuseLighting.disableForLevel();
        if(this.entity == null) return;
        InventoryScreen.drawEntity(context, x, y, size, VECTOR, ENTITY_ROTATION, (Quaternionf)null, this.entity);
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
    static {
        VECTOR = new Vector3f();
        // Vanilla values from SmithingScreen
        ENTITY_ROTATION = (new Quaternionf()).rotationXYZ(0.43633232F, 0.0F, 3.1415927F);
    }
}