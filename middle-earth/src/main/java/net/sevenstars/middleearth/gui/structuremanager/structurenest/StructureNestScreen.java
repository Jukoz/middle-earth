package net.sevenstars.middleearth.gui.structuremanager.structurenest;

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
import net.sevenstars.middleearth.gui.utils.CycledSelectionButtonType;
import net.sevenstars.middleearth.gui.utils.widgets.CycledSelectionWidget;
import net.sevenstars.middleearth.gui.utils.widgets.ModWidget;
import net.sevenstars.middleearth.gui.utils.widgets.SearchBarWidget;
import net.sevenstars.middleearth.gui.utils.widgets.searchbar.SearchBarResult;
import net.sevenstars.middleearth.gui.utils.widgets.searchbar.SearchBarResultType;
import net.sevenstars.middleearth.resources.StructureManagerDatasME;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StructureNestScreen extends HandledScreen<StructureNestScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/structure_manager.png");
    private static final int TEXT_COLOR = Color.WHITE.getRGB();


    public SearchBarWidget managerSearchBarWidget;
    public CycledSelectionWidget nestCycledSelection;
    public ButtonWidget isEnabledToggleButton;

    public StructureManagerData manager;
    public SpawnNestNodeData nest;

    public ArrayList<StructureManagerData> managers;

    public StructureNestScreen(StructureNestScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        World world = inventory.player.getWorld();

        this.managers = new ArrayList<>();
        var registryManager = world.getRegistryManager().getOptional(StructureManagerDatasME.KEY).get();
        for(RegistryKey<StructureManagerData> data : registryManager.getKeys()){
            this.managers.add(registryManager.get(data.getValue()));
        }
        this.manager = registryManager.get(handler.getManagerKey());
        if(manager != null)
            this.nest = manager.getNpcSpawnNest(handler.getNestKey());
    }

    @Override
    protected void init() {
        super.init();

        List<SearchBarResult> results = new ArrayList<>();
        for(StructureManagerData data : this.managers){
            results.add(new SearchBarResult(Text.translatable(data.getId().toTranslationKey("structure_manager_data")), data.getId(), SearchBarResultType.NORMAL, button -> selectManager(data)));
        }

        this.managerSearchBarWidget = new SearchBarWidget(9, results, x -> updateScreenInformation(), 170);
        addDrawableChild(this.managerSearchBarWidget.getSearchBarToggleButton());
        this.managerSearchBarWidget.getAllButtons().forEach(this::addDrawableChild);
        addDrawableChild(this.managerSearchBarWidget.getScreenClickButton());

        nestCycledSelection = new CycledSelectionWidget(
                x -> this.updateNestList(-1),
                x -> this.updateNestList(1),
                null,
                CycledSelectionButtonType.GOLD);
        nestCycledSelection.getButtons().forEach(this::addDrawableChild);
        updateNestList(0);

        isEnabledToggleButton = ButtonWidget.builder(Text.of("isEnabledToggleButton"),x -> toggleEnable()).build();
        isEnabledToggleButton.setDimensions(15, 15);
        addDrawableChild(isEnabledToggleButton);
    }

    private void toggleEnable() {
        this.handler.toggleToActivate();
    }

    private void updateNestList(int difference) {
        if(this.manager == null || this.manager.getNpcSpawnNest().isEmpty()){
            nestCycledSelection.enableArrows(false);
            nestCycledSelection.setText(null);
            return;
        }

        var nests = manager.getNpcSpawnNest();
        if(nests == null){
            nestCycledSelection.enableArrows(false);
            nestCycledSelection.setText(null);
            return;
        }

        if(this.nest == null){
            // Pick first by default
            this.nest = nests.getFirst();
        }

        if(difference != 0){
            int index =  nests.indexOf(this.nest);
            index += difference;
            if(index < 0)
                index = nests.size() - 1;
            if(index >= nests.size())
                index = 0;

            this.nest = nests.get(index);
        }

        this.handler.selectNestId(client.player, this.nest.getId());

        this.nestCycledSelection.enableArrows(nests.size() > 1);
        this.nestCycledSelection.setText(Text.of(this.nest.getId().toTranslationKey()).copy());
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
        managerSearchBarWidgetStartY += this.managerSearchBarWidget.drawSearchBar(context, centerX - 5 - this.managerSearchBarWidget.searchBarToggleButton.getWidth(), managerSearchBarWidgetStartY, textRenderer);
        this.managerSearchBarWidget.setEndY(startY + 500);

        if(this.managerSearchBarWidget.searchIsToggled()) {
            this.managerSearchBarWidget.drawSearchResults(context, centerX - 5 - this.managerSearchBarWidget.searchBarToggleButton.getWidth(), managerSearchBarWidgetStartY - 20);
        }

        Text managerIdText = (manager == null)
                ? Text.translatable("N/A")
                : Text.translatable(manager.getId().toTranslationKey("structure_manager_data"));

        context.drawText(this.textRenderer, Text.translatable("ui.middle-earth.structure_manager.label_selected_id", managerIdText).formatted(Formatting.BOLD).formatted(Formatting.WHITE), centerX + 5, startY + 5, TEXT_COLOR, false);

        if(manager != null && this.managerSearchBarWidget != null){
            startY += 25;

            Text nestIdText = (nest == null)
                    ? Text.translatable("N/A")
                    : Text.translatable(nest.getId().toTranslationKey("structure_nest"));

            this.nestCycledSelection.drawAnchored(context, centerX, startY, true, nestIdText.copy(), textRenderer);

            isEnabledToggleButton.active = true;
            isEnabledToggleButton.setPosition(centerX + CycledSelectionWidget.TOTAL_WIDTH + 5, startY);
            //isEnabledToggleButton.render(context, mouseX, mouseY, deltaTicks);
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
                context.drawTooltip(Text.of("[SET TO TRUE] To ready up the structure manager subscription."), isEnabledToggleButton.getX(), isEnabledToggleButton.getY());
        }
        else
            this.isEnabledToggleButton.active = false;
    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.managerSearchBarWidget.keyPressed(keyCode, scanCode, modifiers);
     if(keyCode <= 90 && keyCode >= 65)
            return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.managerSearchBarWidget.mouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        this.managerSearchBarWidget.keyReleased(keyCode, scanCode, modifiers);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        this.managerSearchBarWidget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        this.managerSearchBarWidget.charTyped(chr, modifiers);
        return super.charTyped(chr, modifiers);
    }

    private void selectManager(StructureManagerData data) {
        this.handler.selectManagerId(client.player, data.getId());
        this.manager = data;
        this.nest = null;
        updateNestList(0);
    }

    private void updateScreenInformation() {

    }
}
