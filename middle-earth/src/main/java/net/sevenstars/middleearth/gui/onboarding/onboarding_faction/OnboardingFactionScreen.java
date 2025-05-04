package net.sevenstars.middleearth.gui.onboarding.onboarding_faction;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.model.ModelBaker;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.utils.CycledSelectionButtonType;
import net.sevenstars.middleearth.gui.utils.widgets.CycledSelectionWidget;
import net.sevenstars.middleearth.gui.utils.widgets.ModWidget;
import net.sevenstars.middleearth.gui.utils.widgets.PlayableNpcPreviewWidget;
import net.sevenstars.middleearth.gui.utils.widgets.SearchBarWidget;
import net.sevenstars.middleearth.gui.utils.widgets.map.FactionSelectionMapWidget;
import net.sevenstars.middleearth.gui.utils.widgets.text.TextBlockWidget;
import net.sevenstars.middleearth.resources.datas.factions.data.BannerData;

import java.util.List;

@Environment(EnvType.CLIENT)
public class OnboardingFactionScreen extends Screen {
    private static final Identifier MAP_UI_IDENTIFIER = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection_map.png");
    private static final Identifier BUTTON_UI_IDENTIFIER = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/faction_selection_buttons.png");

    public class OnboardingFactionScreenElements {
        //region [Event Senders]
        public SearchBarWidget searchBarWidget;
        public PlayableNpcPreviewWidget npcPreviewWidget;
        public CycledSelectionWidget dispositionSelectionWidget;
        public CycledSelectionWidget factionSelectionWidget;
        public CycledSelectionWidget subfactionSelectionWidget;
        public ButtonWidget factionRandomizerButton;
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
            context.drawTexture(RenderLayer::getGuiTextured, texture, startX, startY, uvX, uvY, width, height, 256, 256);
        }
    }


    private static final Text TITLE = Text.translatable("screen.me.onboarding_faction_screen");


    // Public fields
    public OnboardingFactionScreenElements _elements;

    // Private fields
    private OnboardingFactionScreenController _controller;

    public OnboardingFactionScreen(OnboardingFactionScreenController controller) {
        super(TITLE);
        this._controller = controller;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
        _elements = new OnboardingFactionScreenElements();

        _elements.bannerField = this.client.getLoadedEntityModels().getModelPart(EntityModelLayers.STANDING_BANNER_FLAG).getChild("flag");

        ButtonWidget.PressAction searchBarWidgetPress = this::doNothingButton;
        _elements.searchBarWidget = new SearchBarWidget(9, searchBarWidgetPress);
        addDrawableChild(_elements.searchBarWidget.getSearchBarToggleButton());
        _elements.searchBarWidget.getAllButtons().forEach(this::addDrawableChild);

        _elements.npcPreviewWidget = new PlayableNpcPreviewWidget();
        _elements.npcPreviewWidget.getButtons().forEach(this::addDrawableChild);

        // Disposition
        _elements.dispositionSelectionWidget = new CycledSelectionWidget(
                x -> this._controller.updateDisposition(-1),
                x -> this._controller.updateDisposition(1),
                null,
                CycledSelectionButtonType.GOLD);
        _elements.dispositionSelectionWidget.getButtons().forEach(this::addDrawableChild);

        // PlayerFactionPayload
        _elements.factionSelectionWidget = new CycledSelectionWidget(
                x -> this._controller.updateFaction(-1),
                x -> this._controller.updateFaction(1),
                null,
                CycledSelectionButtonType.SILVER);
        _elements.factionSelectionWidget.getButtons().forEach(this::addDrawableChild);

        // Subfaction
        _elements.subfactionSelectionWidget = new CycledSelectionWidget(
                x -> this._controller.updateSubfaction(-1),
                x -> this._controller.updateSubfaction(1),
                null,
                CycledSelectionButtonType.NORMAL);
        _elements.subfactionSelectionWidget.getButtons().forEach(this::addDrawableChild);

        // PlayerFactionPayload Randomizer
        _elements.factionRandomizerButton = ButtonWidget.builder(Text.translatable("screen.me.button.faction_randomizer"),
                x -> this._controller.randomizeFaction()).build();
        _elements.factionRandomizerButton.setDimensions(52, 18);
        addDrawableChild(_elements.factionRandomizerButton);

        // Map Widget
        // TODO add the map widget and add drawables

        _elements.mapFocusButton = ButtonWidget.builder(Text.translatable("screen.me.button.focus_current"), this::doNothingButton).build(); // TODO
        _elements.mapFocusButton.setDimensions(10, 10);
        addDrawableChild(_elements.mapFocusButton);

        _elements.mapZoomInButton = ButtonWidget.builder(Text.translatable("screen.me.button.zoom_in"), this::doNothingButton).build(); // TODO
        _elements.mapZoomInButton.setDimensions(10, 10);
        addDrawableChild(_elements.mapZoomInButton);

        _elements.mapZoomOutButton = ButtonWidget.builder(Text.translatable("screen.me.button.zoom_out"), this::doNothingButton).build(); // TODO
        _elements.mapZoomOutButton.setDimensions(10, 10);
        addDrawableChild(_elements.mapZoomOutButton);

        // Race
        _elements.raceSelectionWidget = new CycledSelectionWidget(
                x -> this._controller.updateRace(-1),
                x -> this._controller.updateRace(1),
                null,
                CycledSelectionButtonType.NORMAL);
        _elements.raceSelectionWidget.getButtons().forEach(this::addDrawableChild);

        // Spawn Point
        _elements.spawnPointSelectionWidget = new CycledSelectionWidget(
                x -> this._controller.updateSpawnPoint(-1),
                x -> this._controller.updateSpawnPoint(1),
                null,
                CycledSelectionButtonType.NORMAL);
        _elements.spawnPointSelectionWidget.getButtons().forEach(this::addDrawableChild);


        // Random spawn selection
        _elements.fullRandomizerButton = ButtonWidget.builder(Text.translatable("screen.me.button.full_randomizer"),
                x -> _controller.randomizeAll()).build();
        addDrawableChild(_elements.fullRandomizerButton);

        _elements.spawnConfirmButton = ButtonWidget.builder(Text.translatable("screen.me.button.confirm"),
                x -> _controller.confirmSelection()).build();
        addDrawableChild(_elements.spawnConfirmButton);


        _elements.factionName = ((MutableText)Text.of("N/A")).formatted(Formatting.BOLD).formatted(Formatting.DARK_GRAY);
        _elements.subfactionName = Text.of("N/A");
        _elements.raceList.setText(List.of(Text.of("N/A")));
        _elements.descriptionTextBlock = new TextBlockWidget(0,0, _elements.informationPanel.width - 9,textRenderer.fontHeight * 10);
        _elements.descriptionTextBlock.setText(List.of(Text.of("N/A")));

        // TODO : this.elements.mapWidget = new FactionSelectionMapWidget(controller, 114, 114);
    }

    //region [GUI Render]
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        ModWidget.updateMouse(mouseX, mouseY);

        this.renderBackground(context, mouseX, mouseY, delta);
        this.renderDisplays(context, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        DiffuseLighting.disableGuiDepthLighting();

        super.renderBackground(context,mouseX,mouseY, deltaTicks);

        // Display center panel
        int centerPanelStartX = (int) ((context.getScaledWindowWidth() / 2f) - (_elements.informationPanel.width / 2f));
        int centerPanelStartY = (int) ((context.getScaledWindowHeight() / 2f) - (_elements.informationPanel.height / 2f));

        _elements.informationPanel.draw(context, centerPanelStartX, centerPanelStartY);

        // Display left panel
        // Nothing

        // Display right panel
        int rightPanelStartX = this._elements.informationPanel.startX + this._elements.informationPanel.width + 5;
        int rightPanelStartY = this._elements.informationPanel.startY;

        this._elements.mapPanel.draw(context, rightPanelStartX, rightPanelStartY);
    }

    private void renderDisplays(DrawContext context, int mouseX, int mouseY, float delta) {
        DiffuseLighting.disableGuiDepthLighting();

        int startX = _elements.informationPanel.startX + 5;
        int startY = _elements.informationPanel.startY + 5;

        int factionStartX = startX + ((_elements.informationPanel.width - 50) / 2 - (textRenderer.getWidth(_elements.factionName) / 2));
        context.drawText(textRenderer, _elements.factionName, factionStartX, startY, 0, false);

        // Subfaction
        if(_elements.subfactionName != null){
            startY += textRenderer.fontHeight + 3;
            Text subfactionTitle = Text.translatable("screen.me.information.subfaction");

            context.drawText(textRenderer, subfactionTitle, startX, startY, 0, false);
            context.drawText(textRenderer, _elements.subfactionName, startX + textRenderer.getWidth(subfactionTitle), startY, 0, false);
        }
        // Race.s
        if(_elements.raceList != null){
            startY += textRenderer.fontHeight + 3;
            var text = _elements.raceList.getValue().getFirst().getString();
            boolean hasManyRaces = text.contains(",");
            Text raceTitle = Text.translatable((hasManyRaces)
                    ? "screen.me.information.races.many"
                    : "screen.me.information.races").formatted(Formatting.UNDERLINE);
            context.drawText(client.textRenderer, raceTitle, startX, startY, 0, false);
            startY += textRenderer.fontHeight + 3;

            _elements.raceList.setStartX(startX);
            _elements.raceList.setStartY(startY);

            _elements.raceList.draw(context, false);
        }

        startY =  _elements.informationPanel.startY + 90;

        context.drawText(client.textRenderer, Text.translatable("screen.me.information.description").formatted(Formatting.UNDERLINE),
                startX,startY - textRenderer.fontHeight, 0, false);
        startY += 3;
        _elements.descriptionTextBlock.setStartX(startX);
        _elements.descriptionTextBlock.setStartY(startY);
        _elements.descriptionTextBlock.draw(context, true);

        // Banner
        if(_elements.bannerComponents != null && !_elements.bannerComponents.isEmpty()) {
            float size = 32f;
            // Positioning
            MatrixStack matrixStack = new MatrixStack();
            matrixStack.translate(startX  + (size / 2f) + 120, startY + 3, 1f);
            matrixStack.push();
            matrixStack.scale(-size, size, 0.1f);
            this._elements.bannerField.pitch = 0.0F;

            var bannerPatternRegistry = this.client.world.getRegistryManager().getOptional(RegistryKeys.BANNER_PATTERN);
            BannerPatternsComponent.Builder bannerBuilder = new BannerPatternsComponent.Builder();
            for(BannerData.BannerPatternWithColor entry : _elements.bannerComponents){
                if(entry == null) continue;
                RegistryEntry<BannerPattern> pattern = bannerPatternRegistry.get().getEntry(entry.pattern);
                bannerBuilder.add(pattern, entry.color);
            }

            context.draw((vertexConsumers) -> {
                BannerBlockEntityRenderer.renderCanvas(matrixStack, vertexConsumers, 15728880, OverlayTexture.DEFAULT_UV, this._elements.bannerField, ModelBaker.BANNER_BASE, true, DyeColor.GRAY, bannerBuilder.build());
            });
            matrixStack.pop();
            context.draw();
        }
        // Right panel

        // TODO : this.elements.mapWidget.drawAnchored(context,startX + 5, startY + 5, true);

        startX = this._elements.mapPanel.startX + 6;
        startY = this._elements.mapPanel.startY + this._elements.mapPanel.height - 16;

        this._elements.mapFocusButton.setPosition(startX, startY);
        context.drawTexture(RenderLayer::getGuiTextured, MAP_UI_IDENTIFIER,
                startX, startY, 235, (true /*this.elements.mapWidget.isForcingTargetMovement*/) ? 20 : _elements.mapFocusButton.isFocused() || _elements.mapFocusButton.isMouseOver(mouseX, mouseY) ? 10 : 0,
                _elements.mapFocusButton.getWidth(), _elements.mapFocusButton.getHeight(), 256, 256);

        startX = this._elements.mapPanel.startX + this._elements.mapPanel.width - 16;

        this._elements.mapZoomInButton.setPosition(startX, startY);
        context.drawTexture(RenderLayer::getGuiTextured, MAP_UI_IDENTIFIER,
                startX, startY, 224, false/*!this.elements.mapWidget.canZoomIn()*/ ? 20 : _elements.mapZoomInButton.isFocused() || _elements.mapZoomInButton.isMouseOver(mouseX, mouseY) ? 10 : 0,
                _elements.mapZoomInButton.getWidth(), _elements.mapZoomInButton.getHeight(), 256, 256);

        startX -= 12;

        this._elements.mapZoomOutButton.setPosition(startX, startY);
        context.drawTexture(RenderLayer::getGuiTextured, MAP_UI_IDENTIFIER,
                startX, startY, 213, false/*!this.elements.mapWidget.canZoomOut()*/ ? 20 : _elements.mapZoomOutButton.isFocused() || _elements.mapZoomOutButton.isMouseOver(mouseX, mouseY) ? 10 : 0,
                _elements.mapZoomOutButton.getWidth(), _elements.mapZoomOutButton.getHeight(), 256, 256);

        startY = this._elements.mapPanel.startY + this._elements.mapPanel.height + 4;
        startX = this._elements.mapPanel.startX;

        _elements.spawnPointSelectionWidget.drawAnchored(context, startX,  startY,true, textRenderer);

        startY += _elements.spawnPointSelectionWidget.TOTAL_HEIGHT + 4;

        _elements.raceSelectionWidget.drawAnchored(context, startX,  startY,true, textRenderer);

        startX = this._elements.mapPanel.startX + 4;
        startY = this._elements.informationPanel.startY + this._elements.informationPanel.height - _elements.fullRandomizerButton.getHeight();

        this._elements.fullRandomizerButton.setPosition(startX, startY);
        this._elements.fullRandomizerButton.setDimensions(52, 18);

        context.drawTexture(RenderLayer::getGuiTextured, BUTTON_UI_IDENTIFIER,
                this._elements.fullRandomizerButton.getX(), this._elements.fullRandomizerButton.getY(), 103, this._elements.fullRandomizerButton.isFocused() || this._elements.fullRandomizerButton.isMouseOver(mouseX, mouseY) ? 129 : 111,
                this._elements.fullRandomizerButton.getWidth(), this._elements.fullRandomizerButton.getHeight(), 256, 256);


        startX = this._elements.mapPanel.startX + this._elements.mapPanel.width - this._elements.spawnConfirmButton.getWidth() - 4;

        this._elements.spawnConfirmButton.setPosition(startX, startY);
        this._elements.spawnConfirmButton.setDimensions(52, 18);

        context.drawTexture(RenderLayer::getGuiTextured, BUTTON_UI_IDENTIFIER,
                this._elements.spawnConfirmButton.getX(), this._elements.spawnConfirmButton.getY(), 103, this._elements.spawnConfirmButton.isFocused() || this._elements.spawnConfirmButton.isMouseOver(mouseX, mouseY) ? 37 : 19,
                this._elements.spawnConfirmButton.getWidth(), this._elements.spawnConfirmButton.getHeight(), 256, 256);

        // Left panel
        startX = this._elements.informationPanel.startX - 6;
        startY = this._elements.informationPanel.startY;

        this._elements.dispositionSelectionWidget.drawAnchored(context, startX, startY, false, textRenderer);

        startY += this._elements.dispositionSelectionWidget.TOTAL_HEIGHT + 2;

        this._elements.factionSelectionWidget.drawAnchored(context, startX, startY, false, textRenderer);
        startY += this._elements.factionSelectionWidget.TOTAL_HEIGHT + 2;

        this._elements.subfactionSelectionWidget.drawAnchored(context, startX, startY, false, textRenderer);
        startY += this._elements.subfactionSelectionWidget.TOTAL_HEIGHT + 2;

        startX -= this._elements.subfactionSelectionWidget.TOTAL_WIDTH / 2;
        startY = this._elements.informationPanel.startY + this._elements.informationPanel.height - CycledSelectionWidget.TOTAL_HEIGHT;

        this._elements.factionRandomizerButton.setPosition(startX, startY);
        context.drawTexture(RenderLayer::getGuiTextured, BUTTON_UI_IDENTIFIER,
                this._elements.factionRandomizerButton.getX() - (this._elements.factionRandomizerButton.getWidth() / 2), this._elements.factionRandomizerButton.getY(), 103, (this._elements.factionRandomizerButton.isFocused() || this._elements.factionRandomizerButton.isMouseOver(mouseX, mouseY)) ? 92 : 74,
                this._elements.factionRandomizerButton.getWidth(), this._elements.factionRandomizerButton.getHeight(), 256, 256);

        this._elements.npcPreviewWidget.drawCenteredAnchoredBottom(context, startX, startY - 6);
    }
    //endregion

    //region [Button Events]
    private void doNothingButton(ButtonWidget button) {
        MiddleEarth.LOGGER.logDebugMsg(button.getMessage().toString());
    }


    @Override
    public void resize(MinecraftClient client, int width, int height) {
        super.resize(client, width, height);
        _controller.screenResize();
    }

    //endregion
}
