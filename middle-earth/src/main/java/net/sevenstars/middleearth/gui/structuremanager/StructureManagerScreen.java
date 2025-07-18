package net.sevenstars.middleearth.gui.structuremanager;

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
    public Text selectedDataText;
    public Identifier selectedKey;
    public Text runtimeDataText;
    public Identifier runtimeKey;
    public ButtonWidget toggleButton;

    public ArrayList<Identifier> identifiers;

    private static final int TEXT_COLOR = Color.WHITE.getRGB();


    public StructureManagerScreen(StructureManagerScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);

        World world = playerInventory.player.getWorld();

        this.identifiers = new ArrayList<>();
        for(RegistryKey<StructureManagerData> data : world.getRegistryManager().getOptional(StructureManagerDatasME.KEY).get().getKeys()){
            this.identifiers.add(data.getValue());
        }

        this.selectedKey = handler.getSelectedKey();
        this.runtimeKey = handler.getRuntimeKey();
    }

    @Override
    protected void init() {
        super.init();

        List<SearchBarResult> results = new ArrayList<>();
        for(Identifier identifier : this.identifiers){
            results.add(new SearchBarResult(Text.translatable(identifier.toTranslationKey("structure_manager_data")), identifier, SearchBarResultType.NORMAL, button -> selectIdentifier(identifier)));
        }

        this.searchBarWidget = new SearchBarWidget(9, results, x -> updateScreenInformation());
        addDrawableChild(this.searchBarWidget.getSearchBarToggleButton());
        this.searchBarWidget.getAllButtons().forEach(this::addDrawableChild);
        addDrawableChild(this.searchBarWidget.getScreenClickButton());

        toggleButton = ButtonWidget.builder(Text.of("toggle button"),
                x -> this.handler.toggleActive()).build();
        toggleButton.setDimensions(100, 18);
        addDrawableChild(toggleButton);
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        ModWidget.updateMouse(mouseX, mouseY);

        // 600 is total "screen size"
        int centerX = (int) (client.currentScreen.width * 0.35f);
        int startY = 100;

        int searchBarWidgetStartY = startY;
        searchBarWidgetStartY += this.searchBarWidget.drawSearchBar(context, centerX - 5 - this.searchBarWidget.searchBarToggleButton.getWidth(), searchBarWidgetStartY, textRenderer);
        this.searchBarWidget.setEndY(startY + 500);

        if(this.searchBarWidget.searchIsToggled()) {
            this.searchBarWidget.drawSearchResults(context, centerX - 5 - this.searchBarWidget.searchBarToggleButton.getWidth(), searchBarWidgetStartY - 20);
        }
        Text selectedIdText = (selectedKey == null)
                ? Text.translatable("N/A")
                : Text.translatable(selectedKey.toTranslationKey("structure_manager_data"));
        this.selectedDataText = Text.translatable("ui.middle-earth.structure_manager.label_selected_id", selectedIdText).formatted(Formatting.BOLD).formatted(Formatting.WHITE);

        context.drawText(this.textRenderer, this.selectedDataText, centerX + 5, startY + 5, TEXT_COLOR, false);


        Text runtimeIdText = (runtimeKey == null)
                ? Text.translatable("N/A")
                : Text.translatable(runtimeKey.toTranslationKey("structure_manager_data"));
        this.runtimeDataText = Text.translatable("ui.middle-earth.structure_manager.label_runtime_id", runtimeIdText).formatted(Formatting.BOLD).formatted(Formatting.WHITE);

        context.drawText(this.textRenderer, this.runtimeDataText, centerX + 5, startY + 25, TEXT_COLOR, false);


        Text isEnabledText = Text.translatable("ui.middle-earth.structure_manager.label_enable_status", handler.getIsActive()).formatted(Formatting.BOLD).formatted(Formatting.WHITE);
        context.drawText(this.textRenderer, isEnabledText, centerX + 5, startY + 50, TEXT_COLOR, false);

        toggleButton.setPosition(centerX + 5, startY + 80);
        toggleButton.render(context, mouseX, mouseY, deltaTicks);
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
        this.selectedKey = identifier;
    }

    private void updateScreenInformation() {

    }


}
