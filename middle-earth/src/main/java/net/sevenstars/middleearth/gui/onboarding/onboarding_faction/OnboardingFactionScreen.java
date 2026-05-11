package net.sevenstars.middleearth.gui.onboarding.onboarding_faction;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.render.states.BannerResultWithScaleGuiElementRenderState;
import net.sevenstars.middleearth.gui.utils.CycledSelectionButtonType;
import net.sevenstars.middleearth.gui.utils.widgets.CycledSelectionWidget;
import net.sevenstars.middleearth.gui.utils.widgets.ModWidget;
import net.sevenstars.middleearth.gui.utils.widgets.PlayableNpcPreviewWidget;
import net.sevenstars.middleearth.gui.utils.widgets.SearchBarWidget;
import net.sevenstars.middleearth.gui.utils.widgets.map.FactionSelectionMapWidget;
import net.sevenstars.middleearth.gui.utils.widgets.text.TextBlockWidget;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class OnboardingFactionScreen extends Screen {
    private static final Identifier MAP_UI_IDENTIFIER = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection_map.png");
    private static final Identifier BUTTON_UI_IDENTIFIER = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection_buttons.png");
    private static final int TEXT_COLOR = Color.BLACK.getRGB();

    public class OnboardingFactionScreenElements {
        //region [Event Senders]
        public SearchBarWidget searchBarWidget;
        public PlayableNpcPreviewWidget npcPreviewWidget;
        public CycledSelectionWidget dispositionSelectionWidget;
        public CycledSelectionWidget factionSelectionWidget;
        public CycledSelectionWidget subfactionSelectionWidget;
        public ButtonWidget npcRandomizerButton;
        public FactionSelectionMapWidget mapWidget;
        public ButtonWidget mapZoomInButton;
        public ButtonWidget mapZoomOutButton;
        public ButtonWidget mapFocusButton;
        public CycledSelectionWidget raceSelectionWidget;
        public CycledSelectionWidget spawnPointSelectionWidget;
        public ButtonWidget fullRandomizerButton;
        public ButtonWidget spawnConfirmButton;
        //endregion
        //region [Text and Displays]
        public DrawablePanel informationPanel =
                new DrawablePanel(169, 207, 0, 0, Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection.png"));
        public DrawablePanel mapPanel =
                new DrawablePanel(124, 124, 0, 0, MAP_UI_IDENTIFIER);

        public ModelPart bannerField;
        public Text factionName;
        public Text subfactionName;
        public TextBlockWidget raceList = new TextBlockWidget(0,0, informationPanel.width - 10, 30);
        public TextBlockWidget descriptionTextBlock;
        public List<BannerData.BannerPatternWithColor> bannerComponents;
        public DyeColor bannerColor;

        //endregion
    }
    private class DrawablePanel{
        public final int width;
        public final int height;
        public final int uvX;
        public final int uvY;
        public final Identifier texture;
        public int startX;
        public int startY;

        public DrawablePanel(int width, int height, int uvX, int uvY, Identifier texture){
            this.width = width;
            this.height = height;
            this.uvX = uvX;
            this.uvY = uvY;
            this.texture = texture;
        }

        public void draw(DrawContext context, int startX, int startY){
            this.startX = startX;
            this.startY = startY;
            context.drawTexture(RenderPipelines.GUI_TEXTURED, texture, startX, startY, uvX, uvY, width, height, 256, 256);
        }
    }

    private static final Text TITLE = Text.translatable("screen." + MiddleEarth.MOD_ID + ".onboarding_faction_screen");


    // Public fields
    public OnboardingFactionScreenElements elements;

    // Private fields
    private OnboardingFactionScreenController controller;

    public OnboardingFactionScreen(OnboardingFactionScreenController controller) {
        super(TITLE);
        this.controller = controller;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
        elements = new OnboardingFactionScreenElements();

        elements.bannerField = this.client.getLoadedEntityModels().getModelPart(EntityModelLayers.STANDING_BANNER_FLAG).getChild("flag");

        elements.searchBarWidget = new SearchBarWidget(9, controller.getAllSearchBarResults(), x -> controller.updateScreenInformation(), CycledSelectionWidget.TOTAL_WIDTH);
        addDrawableChild(elements.searchBarWidget.getSearchBarToggleButton());
        elements.searchBarWidget.getAllButtons().forEach(this::addDrawableChild);
        addDrawableChild(elements.searchBarWidget.getScreenClickButton());

        elements.npcPreviewWidget = new PlayableNpcPreviewWidget();
        elements.npcPreviewWidget.getButtons().forEach(this::addDrawableChild);

        // Disposition
        elements.dispositionSelectionWidget = new CycledSelectionWidget(
                x -> this.controller.updateDisposition(-1),
                x -> this.controller.updateDisposition(1),
                null,
                CycledSelectionButtonType.GOLD);
        elements.dispositionSelectionWidget.getButtons().forEach(this::addDrawableChild);

        // PlayerFactionPayload
        elements.factionSelectionWidget = new CycledSelectionWidget(
                x -> this.controller.updateFaction(-1),
                x -> this.controller.updateFaction(1),
                null,
                CycledSelectionButtonType.SILVER);
        elements.factionSelectionWidget.getButtons().forEach(this::addDrawableChild);

        // Subfaction
        elements.subfactionSelectionWidget = new CycledSelectionWidget(
                x -> this.controller.updateSubfaction(-1),
                x -> this.controller.updateSubfaction(1),
                null,
                CycledSelectionButtonType.NORMAL);
        elements.subfactionSelectionWidget.getButtons().forEach(this::addDrawableChild);

        // PlayerFactionPayload Randomizer
        elements.npcRandomizerButton = ButtonWidget.builder(Text.translatable("screen." + MiddleEarth.MOD_ID + ".button.faction_randomizer"),
                x -> this.controller.randomizeNpc()).build();
        addDrawableChild(elements.npcRandomizerButton);

        // Map Widget
        elements.mapFocusButton = ButtonWidget.builder(Text.translatable("screen." + MiddleEarth.MOD_ID + ".button.focus_current"), this::mapFocusToggle).build(); // TODO
        elements.mapFocusButton.setDimensions(10, 10);
        addDrawableChild(elements.mapFocusButton);

        elements.mapZoomInButton = ButtonWidget.builder(Text.translatable("screen." + MiddleEarth.MOD_ID + ".button.zoom_in"), this::mapZoomIn).build();
        elements.mapZoomInButton.setDimensions(10, 10);
        addDrawableChild(elements.mapZoomInButton);

        elements.mapZoomOutButton = ButtonWidget.builder(Text.translatable("screen." + MiddleEarth.MOD_ID + ".button.zoom_out"), this::mapZoomOut).build();

        elements.mapZoomOutButton.setDimensions(10, 10);
        addDrawableChild(elements.mapZoomOutButton);

        // Race
        elements.raceSelectionWidget = new CycledSelectionWidget(
                x -> this.controller.updateRace(-1),
                x -> this.controller.updateRace(1),
                null,
                CycledSelectionButtonType.NORMAL);
        elements.raceSelectionWidget.getButtons().forEach(this::addDrawableChild);

        // Spawn Point
        elements.spawnPointSelectionWidget = new CycledSelectionWidget(
                x -> this.controller.updateSpawnPoint(-1),
                x -> this.controller.updateSpawnPoint(1),
                null,
                CycledSelectionButtonType.NORMAL);
        elements.spawnPointSelectionWidget.getButtons().forEach(this::addDrawableChild);


        // Random spawn selection
        elements.fullRandomizerButton = ButtonWidget.builder(Text.translatable("screen." + MiddleEarth.MOD_ID + ".button.full_randomizer"),
                x -> controller.randomizeAll()).build();
        addDrawableChild(elements.fullRandomizerButton);

        elements.spawnConfirmButton = ButtonWidget.builder(Text.translatable("screen." + MiddleEarth.MOD_ID + ".button.confirm"),
                x -> controller.confirmSelection()).build();
        addDrawableChild(elements.spawnConfirmButton);

        elements.factionName = ((MutableText)Text.of("N/A")).formatted(Formatting.BOLD).formatted(Formatting.DARK_GRAY);
        elements.subfactionName = Text.of("N/A");
        elements.raceList.setText(List.of(Text.of("N/A")));
        elements.descriptionTextBlock = new TextBlockWidget(0,0, elements.informationPanel.width - 9,textRenderer.fontHeight * 10);
        elements.descriptionTextBlock.setText(List.of(Text.of("N/A")));

        this.elements.mapWidget = new FactionSelectionMapWidget(114, 114, this.controller.getMaxSpawnAmount());
        Arrays.stream(this.elements.mapWidget.getButtons()).forEach(this::addDrawableChild);
    }

    //region [GUI Render]
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        ModWidget.updateMouse(mouseX, mouseY);

        this.renderDisplays(context, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        //DiffuseLighting.disableGuiDepthLighting();
        super.renderBackground(context,mouseX,mouseY, deltaTicks);

        // Display center panel
        int centerPanelStartX = (int) ((context.getScaledWindowWidth() / 2f) - (elements.informationPanel.width / 2f));
        int centerPanelStartY = (int) ((context.getScaledWindowHeight() / 2f) - (elements.informationPanel.height / 2f));

        elements.informationPanel.draw(context, centerPanelStartX, centerPanelStartY);

        // Display left panel
        // Nothing

        // Display right panel
        int rightPanelStartX = this.elements.informationPanel.startX + this.elements.informationPanel.width + 5;
        int rightPanelStartY = this.elements.informationPanel.startY;

        this.elements.mapPanel.draw(context, rightPanelStartX, rightPanelStartY);
    }

    private void renderDisplays(DrawContext context, int mouseX, int mouseY, float delta) {
        //DiffuseLighting.disableGuiDepthLighting();

        int startX = elements.informationPanel.startX + 5;
        int startY = elements.informationPanel.startY + 5;

        int factionStartX = startX + ((elements.informationPanel.width - 50) / 2 - (textRenderer.getWidth(elements.factionName) / 2));

        context.drawText(textRenderer, elements.factionName, factionStartX, startY, TEXT_COLOR, false);
        if(isMouseOver(factionStartX, textRenderer.getWidth(elements.factionName), startY, textRenderer.fontHeight)){
            context.drawTooltip(textRenderer, List.of(controller.getCurrentFactionFullName()), ModWidget.getMouseX(), ModWidget.getMouseY());
        }

        // Subfaction
        if(elements.subfactionName != null){
            startY += textRenderer.fontHeight + 3;
            Text subfactionTitle = Text.translatable("screen." + MiddleEarth.MOD_ID + ".information.subfaction");

            context.drawText(textRenderer, subfactionTitle, startX, startY, TEXT_COLOR, false);
            context.drawText(textRenderer, elements.subfactionName, startX + textRenderer.getWidth(subfactionTitle), startY, TEXT_COLOR, false);
        }
        // Race.s
        if(elements.raceList != null){
            startY += textRenderer.fontHeight + 3;
            var text = elements.raceList.getValue().getFirst().getString();
            boolean hasManyRaces = text.contains(",");
            Text raceTitle = Text.translatable((hasManyRaces)
                    ? "screen." + MiddleEarth.MOD_ID + ".information.races.many"
                    : "screen." + MiddleEarth.MOD_ID + ".information.races").formatted(Formatting.UNDERLINE);
            context.drawText(client.textRenderer, raceTitle, startX, startY, TEXT_COLOR, false);
            startY += textRenderer.fontHeight + 3;

            elements.raceList.setStartX(startX);
            elements.raceList.setStartY(startY);

            elements.raceList.draw(context, false);
        }

        startY =  elements.informationPanel.startY + 90;

        context.drawText(client.textRenderer, Text.translatable("screen." + MiddleEarth.MOD_ID + ".information.description").formatted(Formatting.UNDERLINE),
                startX,startY - textRenderer.fontHeight, TEXT_COLOR, false);
        startY += 3;
        elements.descriptionTextBlock.setStartX(startX);
        elements.descriptionTextBlock.setStartY(startY);
        elements.descriptionTextBlock.draw(context, true);

        // Banner
        if(elements.bannerComponents != null && !elements.bannerComponents.isEmpty()) {
            var bannerPatternRegistry = this.client.world.getRegistryManager().getOptional(RegistryKeys.BANNER_PATTERN);
            BannerPatternsComponent.Builder bannerBuilder = new BannerPatternsComponent.Builder();
            for(BannerData.BannerPatternWithColor entry : elements.bannerComponents){
                if(entry == null) continue;
                RegistryEntry<BannerPattern> pattern = bannerPatternRegistry.get().getEntry(entry.pattern);
                bannerBuilder.add(pattern, entry.color);
            }
            int bannerX = elements.informationPanel.startX + elements.informationPanel.width - 48;
            int bannerY = elements.informationPanel.startY + 8;
            context.state.addSpecialElement(new BannerResultWithScaleGuiElementRenderState(this.elements.bannerField, DyeColor.GRAY, bannerBuilder.build(), bannerX, 0, bannerX + 40, bannerY + 80, 32f, context.scissorStack.peekLast()));
        }

        // Right panel
        startX = this.elements.mapPanel.startX;
        startY = this.elements.mapPanel.startY;

        this.elements.mapWidget.drawAnchored(context,startX + 5, startY + 5, true);

        startX = this.elements.mapPanel.startX + 6;
        startY = this.elements.mapPanel.startY + this.elements.mapPanel.height - 16;

        this.elements.mapFocusButton.setPosition(startX, startY);

        context.drawTexture(RenderPipelines.GUI_TEXTURED, MAP_UI_IDENTIFIER,
                startX, startY, 235, (this.elements.mapWidget.isForcingTargetMovement) ? 20 : elements.mapFocusButton.isFocused() || elements.mapFocusButton.isMouseOver(mouseX, mouseY) ? 10 : 0,
                elements.mapFocusButton.getWidth(), elements.mapFocusButton.getHeight(), 256, 256);

        startX = this.elements.mapPanel.startX + this.elements.mapPanel.width - 16;

        this.elements.mapZoomInButton.setPosition(startX, startY);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, MAP_UI_IDENTIFIER,
                startX, startY, 224, !this.elements.mapWidget.canZoomIn() ? 20 : elements.mapZoomInButton.isFocused() || elements.mapZoomInButton.isMouseOver(mouseX, mouseY) ? 10 : 0,
                elements.mapZoomInButton.getWidth(), elements.mapZoomInButton.getHeight(), 256, 256);

        startX -= 12;

        this.elements.mapZoomOutButton.setPosition(startX, startY);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, MAP_UI_IDENTIFIER,
                startX, startY, 213, !this.elements.mapWidget.canZoomOut() ? 20 : elements.mapZoomOutButton.isFocused() || elements.mapZoomOutButton.isMouseOver(mouseX, mouseY) ? 10 : 0,
                elements.mapZoomOutButton.getWidth(), elements.mapZoomOutButton.getHeight(), 256, 256);

        startY = this.elements.mapPanel.startY + this.elements.mapPanel.height + 4;
        startX = this.elements.mapPanel.startX;

        elements.spawnPointSelectionWidget.drawAnchored(context, startX,  startY,true, textRenderer);

        startY += elements.spawnPointSelectionWidget.TOTAL_HEIGHT + 4;

        elements.raceSelectionWidget.drawAnchored(context, startX,  startY,true, textRenderer);
        if(isMouseOver(startX, CycledSelectionWidget.TOTAL_WIDTH, startY, CycledSelectionWidget.TOTAL_HEIGHT)){
            controller.drawRaceTooltip(client.player, context, textRenderer, mouseX, mouseY);
        }

        startX = this.elements.mapPanel.startX + 4;
        startY = this.elements.informationPanel.startY + this.elements.informationPanel.height - elements.fullRandomizerButton.getHeight();

        this.elements.fullRandomizerButton.setPosition(startX, startY);
        this.elements.fullRandomizerButton.setDimensions(52, 18);

        context.drawTexture(RenderPipelines.GUI_TEXTURED, BUTTON_UI_IDENTIFIER,
                this.elements.fullRandomizerButton.getX(), this.elements.fullRandomizerButton.getY(), 103, this.elements.fullRandomizerButton.isFocused() || this.elements.fullRandomizerButton.isMouseOver(mouseX, mouseY) ? 129 : 111,
                this.elements.fullRandomizerButton.getWidth(), this.elements.fullRandomizerButton.getHeight(), 256, 256);


        startX = this.elements.mapPanel.startX + this.elements.mapPanel.width - this.elements.spawnConfirmButton.getWidth() - 4;

        this.elements.spawnConfirmButton.setPosition(startX, startY);
        this.elements.spawnConfirmButton.setDimensions(52, 18);

        if(elements.spawnConfirmButton.active){
            context.drawTexture(RenderPipelines.GUI_TEXTURED, BUTTON_UI_IDENTIFIER,
                    this.elements.spawnConfirmButton.getX(), this.elements.spawnConfirmButton.getY(), 103, this.elements.spawnConfirmButton.isFocused() || this.elements.spawnConfirmButton.isMouseOver(mouseX, mouseY) ? 37 : 19,
                    this.elements.spawnConfirmButton.getWidth(), this.elements.spawnConfirmButton.getHeight(), 256, 256);
        } else {
            context.drawTexture(RenderPipelines.GUI_TEXTURED, BUTTON_UI_IDENTIFIER,
                    this.elements.spawnConfirmButton.getX(), this.elements.spawnConfirmButton.getY(), 156, 55,
                    this.elements.spawnConfirmButton.getWidth(), this.elements.spawnConfirmButton.getHeight(), 256, 256);

            Text delayText = Text.literal(String.valueOf(controller.getCurrentDelay()));
            context.drawText(textRenderer, delayText,
                    this.elements.spawnConfirmButton.getX() + (52 / 2) - (textRenderer.getWidth(delayText) / 2),
                    this.elements.spawnConfirmButton.getY() + 5, Colors.LIGHT_RED, true);
        }

        // Left panel
        startX = this.elements.informationPanel.startX - 6;
        startY = this.elements.informationPanel.startY;

        int endY = (int) ((context.getScaledWindowHeight() / 2f) - (this.elements.informationPanel.height / 2f)) + this.elements.informationPanel.height;

        // Search Bar Widget
        startY += elements.searchBarWidget.drawSearchBarCentered(context, startX - (CycledSelectionWidget.TOTAL_WIDTH / 2), startY, textRenderer);
        elements.searchBarWidget.setEndY(elements.informationPanel.startY + elements.informationPanel.height);

        if(this.elements.searchBarWidget.searchIsToggled()) {
            this.elements.searchBarWidget.drawSearchResultsCentered(context, startX - (CycledSelectionWidget.TOTAL_WIDTH / 2), startY - 20);
            return;
        }

        this.elements.dispositionSelectionWidget.drawAnchored(context, startX, startY, false, textRenderer);

        startY += this.elements.dispositionSelectionWidget.TOTAL_HEIGHT + 2;

        this.elements.factionSelectionWidget.drawAnchored(context, startX, startY, false, textRenderer);
        startY += this.elements.factionSelectionWidget.TOTAL_HEIGHT + 2;

        this.elements.subfactionSelectionWidget.drawAnchored(context, startX, startY, false, textRenderer);
        startY += this.elements.subfactionSelectionWidget.TOTAL_HEIGHT + 2;

        startX -= this.elements.subfactionSelectionWidget.TOTAL_WIDTH / 2;
        startY = this.elements.informationPanel.startY + this.elements.informationPanel.height - CycledSelectionWidget.TOTAL_HEIGHT;

        this.elements.npcRandomizerButton.setDimensions(52, 18);
        this.elements.npcRandomizerButton.setPosition(startX - (this.elements.npcRandomizerButton.getWidth() / 2), startY);

        context.drawTexture(RenderPipelines.GUI_TEXTURED, BUTTON_UI_IDENTIFIER,
                this.elements.npcRandomizerButton.getX(), this.elements.npcRandomizerButton.getY(), 103, this.elements.npcRandomizerButton.isFocused() || this.elements.npcRandomizerButton.isMouseOver(mouseX, mouseY) ? 92 : 74,
                this.elements.npcRandomizerButton.getWidth(), this.elements.npcRandomizerButton.getHeight(), 256, 256);


        this.elements.npcPreviewWidget.drawCenteredAnchoredBottom(context, startX, startY - 6);
    }
    //endregion


    //region [Button Events]
    private void mapFocusToggle(ButtonWidget buttonWidget) {
        elements.mapWidget.isForcingTargetMovement = !elements.mapWidget.isForcingTargetMovement;
        controller.moveToCurrentSpawn();
    }

    private void mapZoomIn(ButtonWidget buttonWidget) {
        elements.mapWidget.zoomClick();
    }
    private void mapZoomOut(ButtonWidget buttonWidget) {
        elements.mapWidget.dezoomClick();
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        super.resize(client, width, height);
        controller.screenResize();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.elements.mapWidget.keyPressed(keyCode, scanCode, modifiers);
        this.elements.searchBarWidget.keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.elements.npcPreviewWidget.mouseReleased(mouseX, mouseY, button);
        this.elements.mapWidget.mouseReleased(mouseX, mouseY, button);
        this.elements.searchBarWidget.mouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        this.elements.npcPreviewWidget.keyReleased(keyCode, scanCode, modifiers);
        this.elements.mapWidget.keyReleased(keyCode, scanCode, modifiers);
        this.elements.searchBarWidget.keyReleased(keyCode, scanCode, modifiers);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        this.elements.mapWidget.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        this.elements.searchBarWidget.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        this.elements.mapWidget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        this.elements.searchBarWidget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        this.elements.searchBarWidget.charTyped(chr, modifiers);
        return super.charTyped(chr, modifiers);
    }

    @Override
    public void tick() {
        controller.tick();
        super.tick();
    }
    private boolean isMouseOver(int startX, int sizeX, int startY, int sizeY) {
        return ModWidget.isMouseOver(sizeX, sizeY, startX, startY);
    }
    //endregion
}
