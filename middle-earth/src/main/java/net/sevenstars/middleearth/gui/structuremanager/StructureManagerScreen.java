package net.sevenstars.middleearth.gui.structuremanager;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.utils.widgets.ModWidget;
import net.sevenstars.middleearth.gui.utils.widgets.SearchBarWidget;
import net.sevenstars.middleearth.gui.utils.widgets.searchbar.SearchBarResult;
import net.sevenstars.middleearth.gui.utils.widgets.searchbar.SearchBarResultType;
import net.sevenstars.middleearth.resources.StructureManagerDatasME;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StructureManagerScreen extends HandledScreen<StructureManagerScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/structure_manager.png");

    public SearchBarWidget searchBarWidget;
    public Text runtimeDataText;
    public Identifier dataIdentifier;
    public ButtonWidget toInitializeToggleButton;
    public ButtonWidget isEnabledToggleButton;
    public ButtonWidget showAllButton;
    public ButtonWidget respawnAllButton;

    public ArrayList<Identifier> identifiers;

    private static final int TEXT_COLOR = Color.WHITE.getRGB();

    public StructureManagerScreen(StructureManagerScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);

        World world = playerInventory.player.getWorld();

        this.identifiers = new ArrayList<>();
        for(RegistryKey<StructureManagerData> data : world.getRegistryManager().getOptional(StructureManagerDatasME.KEY).get().getKeys()){
            this.identifiers.add(data.getValue());
        }

        this.dataIdentifier = handler.getDataIdentifier();
    }

    @Override
    protected void init() {
        super.init();

        List<SearchBarResult> results = new ArrayList<>();
        for(Identifier identifier : this.identifiers){
            results.add(new SearchBarResult(Text.translatable(identifier.toTranslationKey("structure_manager_data")), identifier, SearchBarResultType.NORMAL, button -> selectIdentifier(identifier)));
        }

        this.searchBarWidget = new SearchBarWidget(9, results, x -> updateScreenInformation(), 170);
        addDrawableChild(this.searchBarWidget.getSearchBarToggleButton());
        this.searchBarWidget.getAllButtons().forEach(this::addDrawableChild);
        addDrawableChild(this.searchBarWidget.getScreenClickButton());

        toInitializeToggleButton = ButtonWidget.builder(Text.of("toInitializeToggleButton"),x -> toggleToInitialize()).build();
        toInitializeToggleButton.setDimensions(15, 15);
        addDrawableChild(toInitializeToggleButton);

        isEnabledToggleButton = ButtonWidget.builder(Text.of("isEnabledToggleButton"),x -> toggleEnable()).build();
        isEnabledToggleButton.setDimensions(15, 15);
        addDrawableChild(isEnabledToggleButton);

        showAllButton = ButtonWidget.builder(Text.of("showAll"),x -> handler.triggerGlowOnAllEntities()).build();
        showAllButton.setDimensions(104, 20);
        addDrawableChild(showAllButton);

        respawnAllButton = ButtonWidget.builder(Text.of("showAll"),x -> handler.triggerRespawnAllEntities()).build();
        respawnAllButton.setDimensions(104, 20);
        addDrawableChild(respawnAllButton);
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        ModWidget.updateMouse(mouseX, mouseY);

        int centerX = (int) (client.currentScreen.width / 2f);
        int startY = 70;


        int managerSearchBarWidgetStartY = startY;
        managerSearchBarWidgetStartY += this.searchBarWidget.drawSearchBar(context, centerX - 5 - this.searchBarWidget.searchBarToggleButton.getWidth(), managerSearchBarWidgetStartY, textRenderer);
        this.searchBarWidget.setEndY(startY + 500);

        if(this.searchBarWidget.searchIsToggled()) {
            this.searchBarWidget.drawSearchResults(context, centerX - 5 - this.searchBarWidget.searchBarToggleButton.getWidth(), managerSearchBarWidgetStartY - 20);
        }

        Text selectedIdText = (dataIdentifier == null)
                ? Text.translatable("N/A")
                : Text.translatable(dataIdentifier.toTranslationKey("structure_manager_data"));
        this.runtimeDataText = Text.translatable("ui.middle-earth.structure_manager.label_selected_id", selectedIdText).formatted(Formatting.BOLD).formatted(Formatting.WHITE);

        context.drawText(this.textRenderer, this.runtimeDataText, centerX + 5, startY + 5, TEXT_COLOR, false);

        startY += 20;
        toInitializeToggleButton.setPosition(centerX + 5, startY);

        boolean toInitializeToggleButtonFocused = toInitializeToggleButton.isMouseOver(mouseX, mouseY) || toInitializeToggleButton.isFocused();
        int toInitializeToggleButtonUvY = 1;
        if(handler.getToInitialize())
            toInitializeToggleButtonUvY = toInitializeToggleButtonFocused ? 52 : 35;
        else if(toInitializeToggleButtonFocused)
            toInitializeToggleButtonUvY = 18;

        if(handler.getDataIdentifier() == null){
            toInitializeToggleButton.active = false;
            isEnabledToggleButton.active = false;
            showAllButton.active = false;
            respawnAllButton.active = false;
            return;
        } else {
            toInitializeToggleButton.active = true;
            isEnabledToggleButton.active = true;
            showAllButton.active = true;
            respawnAllButton.active = true;
        }

        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE,
                toInitializeToggleButton.getX(), toInitializeToggleButton.getY(),
                1, toInitializeToggleButtonUvY,
                toInitializeToggleButton.getWidth(), toInitializeToggleButton.getHeight(), 256, 256);
        if(toInitializeToggleButton.isMouseOver(mouseX, mouseY))
            context.drawTooltip(Text.of("[SET TO TRUE] Before saving a structure."), toInitializeToggleButton.getX(), toInitializeToggleButton.getY());

        isEnabledToggleButton.setPosition(centerX + 25, startY);
        boolean isEnabledToggleButtonFocused = isEnabledToggleButton.isMouseOver(mouseX, mouseY) || isEnabledToggleButton.isFocused();
        int isEnabledToggleButtonUvY = 1;
        if(handler.getIsEnabled())
            isEnabledToggleButtonUvY = isEnabledToggleButtonFocused ? 52 : 35;
        else if(isEnabledToggleButtonFocused)
            isEnabledToggleButtonUvY = 18;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE,
                isEnabledToggleButton.getX(), isEnabledToggleButton.getY(),
                18, isEnabledToggleButtonUvY,
                isEnabledToggleButton.getWidth(), isEnabledToggleButton.getHeight(), 256, 256);
        if(isEnabledToggleButton.isMouseOver(mouseX, mouseY))
            context.drawTooltip(Text.of("[SET TO FALSE] Before saving a structure."), isEnabledToggleButton.getX(), isEnabledToggleButton.getY());

        startY += 15;
        showAllButton.setPosition(centerX + 5, startY);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE,
                showAllButton.getX(), showAllButton.getY(),
                35, showAllButton.isMouseOver(mouseX, mouseY) ? 23 : 1,
                showAllButton.getWidth(), showAllButton.getHeight(), 256, 256);
        Text showAllText = Text.translatable("Show all");
        int showAllStartX = showAllButton.getX() + (showAllButton.getWidth() / 2) - (textRenderer.getWidth(showAllText) / 2);
        context.drawText(textRenderer, showAllText,showAllStartX,showAllButton.getY() + 6, Color.BLACK.getRGB(), false);

        startY += showAllButton.getHeight() + 4;
        respawnAllButton.setPosition(centerX + 5, startY);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE,
                respawnAllButton.getX(), respawnAllButton.getY(),
                35, respawnAllButton.isMouseOver(mouseX, mouseY) ? 23 : 1,
                respawnAllButton.getWidth(), respawnAllButton.getHeight(), 256, 256);
        Text respawnAllText = Text.translatable("Respawn all");
        int respawnAllStartX = respawnAllButton.getX() + (respawnAllButton.getWidth() / 2) - (textRenderer.getWidth(respawnAllText) / 2);
        context.drawText(textRenderer, respawnAllText, respawnAllStartX,respawnAllButton.getY() + 6, Color.BLACK.getRGB(), false);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.searchBarWidget.keyPressed(keyCode, scanCode, modifiers);
        if(keyCode <= 90 && keyCode >= 65)
            return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.searchBarWidget.mouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        this.searchBarWidget.keyReleased(keyCode, scanCode, modifiers);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        this.searchBarWidget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        this.searchBarWidget.charTyped(chr, modifiers);
        return super.charTyped(chr, modifiers);
    }

    private boolean isMouseOver(int startX, int sizeX, int startY, int sizeY) {
        return ModWidget.isMouseOver(sizeX, sizeY, startX, startY);
    }

    private void selectIdentifier(Identifier identifier) {
        this.handler.selectIdentifier(client.player, identifier);
        this.dataIdentifier = identifier;
    }

    private void toggleToInitialize() {
        this.handler.toggleToInitialize();
    }

    private void toggleEnable() {
        this.handler.toggleToActivate();
    }

    private void updateScreenInformation() {

    }
}
