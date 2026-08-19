package net.sevenstars.middleearth.gui.onboarding.onboarding_faction;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.render.BannerResultWithScaleGuiElementRenderer;
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

@OnlyIn(Dist.CLIENT)
public class OnboardingFactionScreen extends Screen {
    private static final ResourceLocation MAP_UI_IDENTIFIER = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID,"textures/gui/faction_selection_map.png");
    private static final ResourceLocation BUTTON_UI_IDENTIFIER = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID,"textures/gui/faction_selection_buttons.png");
    private static final int TEXT_COLOR = Color.BLACK.getRGB();

    public class OnboardingFactionScreenElements {
        //region [Event Senders]
        public SearchBarWidget searchBarWidget;
        public PlayableNpcPreviewWidget npcPreviewWidget;
        public CycledSelectionWidget dispositionSelectionWidget;
        public CycledSelectionWidget factionSelectionWidget;
        public CycledSelectionWidget subfactionSelectionWidget;
        public Button npcRandomizerButton;
        public FactionSelectionMapWidget mapWidget;
        public Button mapZoomInButton;
        public Button mapZoomOutButton;
        public Button mapFocusButton;
        public CycledSelectionWidget raceSelectionWidget;
        public CycledSelectionWidget spawnPointSelectionWidget;
        public Button fullRandomizerButton;
        public Button spawnConfirmButton;
        //endregion
        //region [Text and Displays]
        public DrawablePanel informationPanel =
                new DrawablePanel(169, 207, 0, 0, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID,"textures/gui/faction_selection.png"));
        public DrawablePanel mapPanel =
                new DrawablePanel(124, 124, 0, 0, MAP_UI_IDENTIFIER);

        public ModelPart bannerField;
        public Component factionName;
        public Component subfactionName;
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
        public final ResourceLocation texture;
        public int startX;
        public int startY;

        public DrawablePanel(int width, int height, int uvX, int uvY, ResourceLocation texture){
            this.width = width;
            this.height = height;
            this.uvX = uvX;
            this.uvY = uvY;
            this.texture = texture;
        }

        public void draw(GuiGraphics context, int startX, int startY){
            this.startX = startX;
            this.startY = startY;
            context.blit(texture, startX, startY, uvX, uvY, width, height, 256, 256);
        }
    }

    private static final Component TITLE = Component.translatable("screen." + MiddleEarth.MOD_ID + ".onboarding_faction_screen");


    // Public fields
    public OnboardingFactionScreenElements elements;

    // Private fields
    private OnboardingFactionScreenController controller;

    public OnboardingFactionScreen(OnboardingFactionScreenController controller) {
        super(TITLE);
        this.controller = controller;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
        elements = new OnboardingFactionScreenElements();

        elements.bannerField = this.minecraft.getEntityModels().bakeLayer(ModelLayers.BANNER).getChild("flag");

        elements.searchBarWidget = new SearchBarWidget(9, controller.getAllSearchBarResults(), x -> controller.updateScreenInformation(), CycledSelectionWidget.TOTAL_WIDTH);
        addRenderableWidget(elements.searchBarWidget.getSearchBarToggleButton());
        elements.searchBarWidget.getAllButtons().forEach(this::addRenderableWidget);
        addRenderableWidget(elements.searchBarWidget.getScreenClickButton());

        elements.npcPreviewWidget = new PlayableNpcPreviewWidget();
        elements.npcPreviewWidget.getButtons().forEach(this::addRenderableWidget);

        // Disposition
        elements.dispositionSelectionWidget = new CycledSelectionWidget(
                x -> this.controller.updateDisposition(-1),
                x -> this.controller.updateDisposition(1),
                null,
                CycledSelectionButtonType.GOLD);
        elements.dispositionSelectionWidget.getButtons().forEach(this::addRenderableWidget);

        // PlayerFactionPayload
        elements.factionSelectionWidget = new CycledSelectionWidget(
                x -> this.controller.updateFaction(-1),
                x -> this.controller.updateFaction(1),
                null,
                CycledSelectionButtonType.SILVER);
        elements.factionSelectionWidget.getButtons().forEach(this::addRenderableWidget);

        // Subfaction
        elements.subfactionSelectionWidget = new CycledSelectionWidget(
                x -> this.controller.updateSubfaction(-1),
                x -> this.controller.updateSubfaction(1),
                null,
                CycledSelectionButtonType.NORMAL);
        elements.subfactionSelectionWidget.getButtons().forEach(this::addRenderableWidget);

        // PlayerFactionPayload Randomizer
        elements.npcRandomizerButton = Button.builder(Component.translatable("screen." + MiddleEarth.MOD_ID + ".button.faction_randomizer"),
                x -> this.controller.randomizeNpc()).build();
        addRenderableWidget(elements.npcRandomizerButton);

        // Map Widget
        elements.mapFocusButton = Button.builder(Component.translatable("screen." + MiddleEarth.MOD_ID + ".button.focus_current"), this::mapFocusToggle).build(); // TODO
        elements.mapFocusButton.setSize(10, 10);
        addRenderableWidget(elements.mapFocusButton);

        elements.mapZoomInButton = Button.builder(Component.translatable("screen." + MiddleEarth.MOD_ID + ".button.zoom_in"), this::mapZoomIn).build();
        elements.mapZoomInButton.setSize(10, 10);
        addRenderableWidget(elements.mapZoomInButton);

        elements.mapZoomOutButton = Button.builder(Component.translatable("screen." + MiddleEarth.MOD_ID + ".button.zoom_out"), this::mapZoomOut).build();

        elements.mapZoomOutButton.setSize(10, 10);
        addRenderableWidget(elements.mapZoomOutButton);

        // Race
        elements.raceSelectionWidget = new CycledSelectionWidget(
                x -> this.controller.updateRace(-1),
                x -> this.controller.updateRace(1),
                null,
                CycledSelectionButtonType.NORMAL);
        elements.raceSelectionWidget.getButtons().forEach(this::addRenderableWidget);

        // Spawn Point
        elements.spawnPointSelectionWidget = new CycledSelectionWidget(
                x -> this.controller.updateSpawnPoint(-1),
                x -> this.controller.updateSpawnPoint(1),
                null,
                CycledSelectionButtonType.NORMAL);
        elements.spawnPointSelectionWidget.getButtons().forEach(this::addRenderableWidget);


        // Random spawn selection
        elements.fullRandomizerButton = Button.builder(Component.translatable("screen." + MiddleEarth.MOD_ID + ".button.full_randomizer"),
                x -> controller.randomizeAll()).build();
        addRenderableWidget(elements.fullRandomizerButton);

        elements.spawnConfirmButton = Button.builder(Component.translatable("screen." + MiddleEarth.MOD_ID + ".button.confirm"),
                x -> controller.confirmSelection()).build();
        addRenderableWidget(elements.spawnConfirmButton);

        elements.factionName = ((MutableComponent)Component.nullToEmpty("N/A")).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.DARK_GRAY);
        elements.subfactionName = Component.nullToEmpty("N/A");
        elements.raceList.setText(List.of(Component.nullToEmpty("N/A")));
        elements.descriptionTextBlock = new TextBlockWidget(0,0, elements.informationPanel.width - 9,font.lineHeight * 10);
        elements.descriptionTextBlock.setText(List.of(Component.nullToEmpty("N/A")));

        this.elements.mapWidget = new FactionSelectionMapWidget(114, 114, this.controller.getMaxSpawnAmount());
        Arrays.stream(this.elements.mapWidget.getButtons()).forEach(this::addRenderableWidget);
    }

    //region [GUI Render]
    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        ModWidget.updateMouse(mouseX, mouseY);

        this.renderDisplays(context, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
        //DiffuseLighting.disableGuiDepthLighting();
        super.renderBackground(context,mouseX,mouseY, deltaTicks);

        // Display center panel
        int centerPanelStartX = (int) ((context.guiWidth() / 2f) - (elements.informationPanel.width / 2f));
        int centerPanelStartY = (int) ((context.guiHeight() / 2f) - (elements.informationPanel.height / 2f));

        elements.informationPanel.draw(context, centerPanelStartX, centerPanelStartY);

        // Display left panel
        // Nothing

        // Display right panel
        int rightPanelStartX = this.elements.informationPanel.startX + this.elements.informationPanel.width + 5;
        int rightPanelStartY = this.elements.informationPanel.startY;

        this.elements.mapPanel.draw(context, rightPanelStartX, rightPanelStartY);
    }

    private void renderDisplays(GuiGraphics context, int mouseX, int mouseY, float delta) {
        //DiffuseLighting.disableGuiDepthLighting();

        int startX = elements.informationPanel.startX + 5;
        int startY = elements.informationPanel.startY + 5;

        int factionStartX = startX + ((elements.informationPanel.width - 50) / 2 - (font.width(elements.factionName) / 2));

        context.drawString(font, elements.factionName, factionStartX, startY, TEXT_COLOR, false);
        if(isMouseOver(factionStartX, font.width(elements.factionName), startY, font.lineHeight)){
            context.renderComponentTooltip(font, List.of(controller.getCurrentFactionFullName()), ModWidget.getMouseX(), ModWidget.getMouseY());
        }

        // Subfaction
        if(elements.subfactionName != null){
            startY += font.lineHeight + 3;
            Component subfactionTitle = Component.translatable("screen." + MiddleEarth.MOD_ID + ".information.subfaction");

            context.drawString(font, subfactionTitle, startX, startY, TEXT_COLOR, false);
            context.drawString(font, elements.subfactionName, startX + font.width(subfactionTitle), startY, TEXT_COLOR, false);
        }
        // Race.s
        if(elements.raceList != null){
            startY += font.lineHeight + 3;
            var text = elements.raceList.getValue().getFirst().getString();
            boolean hasManyRaces = text.contains(",");
            Component raceTitle = Component.translatable((hasManyRaces)
                    ? "screen." + MiddleEarth.MOD_ID + ".information.races.many"
                    : "screen." + MiddleEarth.MOD_ID + ".information.races").withStyle(ChatFormatting.UNDERLINE);
            context.drawString(minecraft.font, raceTitle, startX, startY, TEXT_COLOR, false);
            startY += font.lineHeight + 3;

            elements.raceList.setStartX(startX);
            elements.raceList.setStartY(startY);

            elements.raceList.draw(context, false);
        }

        startY =  elements.informationPanel.startY + 90;

        context.drawString(minecraft.font, Component.translatable("screen." + MiddleEarth.MOD_ID + ".information.description").withStyle(ChatFormatting.UNDERLINE),
                startX,startY - font.lineHeight, TEXT_COLOR, false);
        startY += 3;
        elements.descriptionTextBlock.setStartX(startX);
        elements.descriptionTextBlock.setStartY(startY);
        elements.descriptionTextBlock.draw(context, true);

        // Banner
        if(elements.bannerComponents != null && !elements.bannerComponents.isEmpty()) {
            var bannerPatternRegistry = this.minecraft.level.registryAccess().lookupOrThrow(Registries.BANNER_PATTERN);
            BannerPatternLayers.Builder bannerBuilder = new BannerPatternLayers.Builder();
            for(BannerData.BannerPatternWithColor entry : elements.bannerComponents){
                if(entry == null) continue;
                Holder<BannerPattern> pattern = bannerPatternRegistry.getOrThrow(
                        ResourceKey.create(Registries.BANNER_PATTERN, entry.id)
                );
                bannerBuilder.add(pattern, entry.color);
            }
            int bannerX = elements.informationPanel.startX + elements.informationPanel.width - 48;
            int bannerY = elements.informationPanel.startY + 8;
            BannerResultWithScaleGuiElementRenderer.render(
                    context,
                    this.elements.bannerField,
                    DyeColor.GRAY,
                    bannerBuilder.build(),
                    bannerX + 4,
                    bannerY + 64,
                    32.0F
            );
        }

        // Right panel
        startX = this.elements.mapPanel.startX;
        startY = this.elements.mapPanel.startY;

        this.elements.mapWidget.drawAnchored(context,startX + 5, startY + 5, true);

        startX = this.elements.mapPanel.startX + 6;
        startY = this.elements.mapPanel.startY + this.elements.mapPanel.height - 16;

        this.elements.mapFocusButton.setPosition(startX, startY);

        context.blit(MAP_UI_IDENTIFIER,
                startX, startY, 235, (this.elements.mapWidget.isForcingTargetMovement) ? 20 : elements.mapFocusButton.isFocused() || elements.mapFocusButton.isMouseOver(mouseX, mouseY) ? 10 : 0,
                elements.mapFocusButton.getWidth(), elements.mapFocusButton.getHeight(), 256, 256);

        startX = this.elements.mapPanel.startX + this.elements.mapPanel.width - 16;

        this.elements.mapZoomInButton.setPosition(startX, startY);
        context.blit(MAP_UI_IDENTIFIER,
                startX, startY, 224, !this.elements.mapWidget.canZoomIn() ? 20 : elements.mapZoomInButton.isFocused() || elements.mapZoomInButton.isMouseOver(mouseX, mouseY) ? 10 : 0,
                elements.mapZoomInButton.getWidth(), elements.mapZoomInButton.getHeight(), 256, 256);

        startX -= 12;

        this.elements.mapZoomOutButton.setPosition(startX, startY);
        context.blit(MAP_UI_IDENTIFIER,
                startX, startY, 213, !this.elements.mapWidget.canZoomOut() ? 20 : elements.mapZoomOutButton.isFocused() || elements.mapZoomOutButton.isMouseOver(mouseX, mouseY) ? 10 : 0,
                elements.mapZoomOutButton.getWidth(), elements.mapZoomOutButton.getHeight(), 256, 256);

        startY = this.elements.mapPanel.startY + this.elements.mapPanel.height + 4;
        startX = this.elements.mapPanel.startX;

        elements.spawnPointSelectionWidget.drawAnchored(context, startX,  startY,true, font);

        startY += elements.spawnPointSelectionWidget.TOTAL_HEIGHT + 4;

        elements.raceSelectionWidget.drawAnchored(context, startX,  startY,true, font);
        if(isMouseOver(startX, CycledSelectionWidget.TOTAL_WIDTH, startY, CycledSelectionWidget.TOTAL_HEIGHT)){
            controller.drawRaceTooltip(minecraft.player, context, font, mouseX, mouseY);
        }

        startX = this.elements.mapPanel.startX + 4;
        startY = this.elements.informationPanel.startY + this.elements.informationPanel.height - elements.fullRandomizerButton.getHeight();

        this.elements.fullRandomizerButton.setPosition(startX, startY);
        this.elements.fullRandomizerButton.setSize(52, 18);

        context.blit(BUTTON_UI_IDENTIFIER,
                this.elements.fullRandomizerButton.getX(), this.elements.fullRandomizerButton.getY(), 103, this.elements.fullRandomizerButton.isFocused() || this.elements.fullRandomizerButton.isMouseOver(mouseX, mouseY) ? 129 : 111,
                this.elements.fullRandomizerButton.getWidth(), this.elements.fullRandomizerButton.getHeight(), 256, 256);


        startX = this.elements.mapPanel.startX + this.elements.mapPanel.width - this.elements.spawnConfirmButton.getWidth() - 4;

        this.elements.spawnConfirmButton.setPosition(startX, startY);
        this.elements.spawnConfirmButton.setSize(52, 18);

        if(elements.spawnConfirmButton.active){
            context.blit(BUTTON_UI_IDENTIFIER,
                    this.elements.spawnConfirmButton.getX(), this.elements.spawnConfirmButton.getY(), 103, this.elements.spawnConfirmButton.isFocused() || this.elements.spawnConfirmButton.isMouseOver(mouseX, mouseY) ? 37 : 19,
                    this.elements.spawnConfirmButton.getWidth(), this.elements.spawnConfirmButton.getHeight(), 256, 256);
        } else {
            context.blit(BUTTON_UI_IDENTIFIER,
                    this.elements.spawnConfirmButton.getX(), this.elements.spawnConfirmButton.getY(), 156, 55,
                    this.elements.spawnConfirmButton.getWidth(), this.elements.spawnConfirmButton.getHeight(), 256, 256);

            Component delayText = Component.literal(String.valueOf(controller.getCurrentDelay()));
            context.drawString(font, delayText,
                    this.elements.spawnConfirmButton.getX() + (52 / 2) - (font.width(delayText) / 2),
                    this.elements.spawnConfirmButton.getY() + 5, CommonColors.SOFT_RED, true);
        }

        // Left panel
        startX = this.elements.informationPanel.startX - 6;
        startY = this.elements.informationPanel.startY;

        int endY = (int) ((context.guiHeight() / 2f) - (this.elements.informationPanel.height / 2f)) + this.elements.informationPanel.height;

        // Search Bar Widget
        startY += elements.searchBarWidget.drawSearchBarCentered(context, startX - (CycledSelectionWidget.TOTAL_WIDTH / 2), startY, font);
        elements.searchBarWidget.setEndY(elements.informationPanel.startY + elements.informationPanel.height);

        if(this.elements.searchBarWidget.searchIsToggled()) {
            this.elements.searchBarWidget.drawSearchResultsCentered(context, startX - (CycledSelectionWidget.TOTAL_WIDTH / 2), startY - 20);
            return;
        }

        this.elements.dispositionSelectionWidget.drawAnchored(context, startX, startY, false, font);

        startY += this.elements.dispositionSelectionWidget.TOTAL_HEIGHT + 2;

        this.elements.factionSelectionWidget.drawAnchored(context, startX, startY, false, font);
        startY += this.elements.factionSelectionWidget.TOTAL_HEIGHT + 2;

        this.elements.subfactionSelectionWidget.drawAnchored(context, startX, startY, false, font);
        startY += this.elements.subfactionSelectionWidget.TOTAL_HEIGHT + 2;

        startX -= this.elements.subfactionSelectionWidget.TOTAL_WIDTH / 2;
        startY = this.elements.informationPanel.startY + this.elements.informationPanel.height - CycledSelectionWidget.TOTAL_HEIGHT;

        this.elements.npcRandomizerButton.setSize(52, 18);
        this.elements.npcRandomizerButton.setPosition(startX - (this.elements.npcRandomizerButton.getWidth() / 2), startY);

        context.blit(BUTTON_UI_IDENTIFIER,
                this.elements.npcRandomizerButton.getX(), this.elements.npcRandomizerButton.getY(), 103, this.elements.npcRandomizerButton.isFocused() || this.elements.npcRandomizerButton.isMouseOver(mouseX, mouseY) ? 92 : 74,
                this.elements.npcRandomizerButton.getWidth(), this.elements.npcRandomizerButton.getHeight(), 256, 256);


        this.elements.npcPreviewWidget.drawCenteredAnchoredBottom(context, startX, startY - 6);
    }
    //endregion


    //region [Button Events]
    private void mapFocusToggle(Button buttonWidget) {
        elements.mapWidget.isForcingTargetMovement = !elements.mapWidget.isForcingTargetMovement;
        controller.moveToCurrentSpawn();
    }

    private void mapZoomIn(Button buttonWidget) {
        elements.mapWidget.zoomClick();
    }
    private void mapZoomOut(Button buttonWidget) {
        elements.mapWidget.dezoomClick();
    }

    @Override
    public void resize(Minecraft client, int width, int height) {
        super.resize(client, width, height);
        controller.screenResize();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.elements.mapWidget.keyPressed(keyCode, scanCode, modifiers);
        this.elements.searchBarWidget.keyPressed(keyCode, scanCode, modifiers);
        if(keyCode == 340 && modifiers == 1) {
            controller.modifyStateDetailed(true);
        }
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
        if(keyCode == 340 && modifiers == 0) {
            controller.modifyStateDetailed(false);
        }
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
