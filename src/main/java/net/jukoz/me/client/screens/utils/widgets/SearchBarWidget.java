package net.jukoz.me.client.screens.utils.widgets;

import me.shedaniel.rei.impl.client.gui.widget.DraggableWidget;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.faction_selection.FactionSelectionController;
import net.jukoz.me.client.screens.faction_selection.FactionSelectionScreen;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.resources.datas.FactionType;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.utils.ModColors;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ScrollableWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import org.joml.Vector2d;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchBarWidget extends ModWidget{
    private static final Identifier SEARCH_WIDGET = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/widget/search_widget.png");
    private static final int MINIMAL_MARGIN = 4;
    static final int SEARCH_BAR_PANEL_X = 102;
    static final int SEARCH_BAR_PANEL_Y = 18;
    public static final int TOTAL_WIDTH = SEARCH_BAR_PANEL_X;
    public ButtonWidget searchBarToggleButton;
    private boolean holdingSearchButton = false;
    private boolean searchResultToggle = false;
    private boolean searchBarToggle = false;
    private int currentSearchInputIndex = 0;
    private String searchBarInput = "";
    public ButtonWidget screenClick;
    HashMap<Identifier, Text> pool;
    List<ButtonWidget> buttons;
    FactionSelectionController controller;
    private final int maximumShownAmount;
    private int currentAmount;
    private int currentlyShownEntries;
    private int currentOffsetIndex = 0;
    private int currentSearchResultHeight;
    private Vector2d searchResultPanelStarts = new Vector2d();

    int endY = 0;
    public SearchBarWidget(HashMap<Identifier, Text> newPool, FactionSelectionController controller, int maximumShownAmount){
        this.controller = controller;
        this.maximumShownAmount = maximumShownAmount;
        searchBarToggle = false;
        searchResultToggle = false;
        setButtons();
        pool = newPool;
        buttons = new ArrayList<>();
        for(Identifier id : newPool.keySet()){
            buttons.add(
                    ButtonWidget.builder(
                            newPool.get(id), x -> onPress(id)
                    ).build());
        }
    }

    private void onPress(Identifier id) {
        controller.setFactionId(id);
    }

    private void setButtons(){
        ButtonWidget.PressAction searchBarInputToggle = button -> {
            if(!searchBarToggle)
                searchBarToggle = true;
        };
        searchBarToggleButton = ButtonWidget.builder(Text.of("Toggle search bar input"), searchBarInputToggle).build();

        // Screen click
        ButtonWidget.PressAction screenClickAction = button -> {
            clickOnScreen();
        };
        screenClick = ButtonWidget.builder(Text.of("Click on screen"), screenClickAction).build();
    }

    private void clickOnScreen() {
        toggleSearch(false);
        screenClick.active = false;
    }

    public void setEndY(int endY){
        this.endY = endY;
    }

    public int drawSearchBarCentered(DrawContext context, int centerX, int startY, TextRenderer textRenderer){
        int startX = centerX - (TOTAL_WIDTH / 2);
        return drawSearchBar(context, startX, startY, textRenderer);
    }
    public int drawSearchBarAnchored(DrawContext context, int anchorX, int startY, boolean isLeftAnchor, TextRenderer textRenderer) {
        int startX = anchorX;
        if(!isLeftAnchor)
            startX -= TOTAL_WIDTH;
        return drawSearchBar(context, startX, startY, textRenderer);
    }
    public int drawSearchBar(DrawContext context, int startX, int startY, TextRenderer textRenderer) {
        int panelSizeX = 102;
        int panelSizeY = 18;
        int sideMargins = MINIMAL_MARGIN / 2;
        int magnifyingGlassSizeX = 14;
        int magnifyingGlassSizeY = 14;

        // Search bar button
        context.drawTexture(SEARCH_WIDGET,
                startX,
                startY,
                0,
                searchBarToggleButton.isFocused() || isMouseOver(panelSizeX, panelSizeY, startX, startY) ? 19 : 0,
                panelSizeX,
                panelSizeY
        );
        searchBarToggleButton.setDimensionsAndPosition(panelSizeX, panelSizeY, startX, startY);
        if(ModWidget.getFocusEnabled() && searchBarToggleButton.isFocused()){
            context.drawTexture(SEARCH_WIDGET,
                    startX,
                    startY,
                    0, 147,
                    panelSizeX,
                    panelSizeY
            );
        }

        MutableText text = Text.translatable((!searchBarToggle && searchBarInput.isEmpty()) ? "ui.me.search.label" : searchBarInput);
        text.asTruncatedString(16);
        context.drawText(textRenderer, text,
                startX + magnifyingGlassSizeX + MINIMAL_MARGIN,
                startY + (int) ((panelSizeY / 2f) - (textRenderer.fontHeight / 2f)) + 1,
                16777215, false);


        // Search bar magnifying
        context.drawTexture(SEARCH_WIDGET,
                startX + sideMargins,
                startY + 2,
                102, 0,
                magnifyingGlassSizeX,
                magnifyingGlassSizeY
        );

        return panelSizeY + MINIMAL_MARGIN;
    }

    public int drawSearchResultsCentered(DrawContext context, int centerX, int startY) {
        // TODO : Modify centerX to be startX
        int startX = centerX - (TOTAL_WIDTH / 2);
        return drawSearchResults(context, startX, startY);
    }
    public int drawSearchResultsAnchored(DrawContext context, int anchorX, int startY, boolean isLeftAnchor) {
        // TODO : Modify anchorX to be startX
        int startX = anchorX;
        if(!isLeftAnchor)
            startX -= TOTAL_WIDTH;
        return drawSearchResults(context, startX, startY);
    }

    public int drawSearchResults(DrawContext context, int startX, int startY) {
        // TODO : draw search bar results
        setScreenClickbutton(context.getScaledWindowWidth(), context.getScaledWindowHeight());
        int previousPanelSizeY = 18;

        int panelSizeX = 102;
        int panelSizeY = 14;
        int panelBorderSizeY = 4;
        int footerPanelSizeY = 11;
        int sideMargins = MINIMAL_MARGIN / 2;

        // Search bar button
        startY += previousPanelSizeY + sideMargins / 2;
        searchResultPanelStarts.y = startY;
        searchResultPanelStarts.x = startX;

        // Popup
        if(searchResultToggle){
            List<Identifier> results = new ArrayList<>();
            for(Identifier identifier : pool.keySet()){
                if(identifier.toString().replace("_", " ").contains(searchBarInput.toLowerCase())){
                    results.add(identifier);
                }
            }
            // Top
            context.drawTexture(SEARCH_WIDGET,
                    startX, startY,
                    0, 38,
                    panelSizeX,
                    panelBorderSizeY
            );
            // Center
            startY += panelBorderSizeY;
            currentlyShownEntries = Math.min((endY - startY - panelBorderSizeY) / panelSizeY, results.size());
            currentAmount = Math.min(currentlyShownEntries, maximumShownAmount);

            for(int i = 0; i < currentAmount; i++){
                context.drawTexture(SEARCH_WIDGET,
                        startX, startY + panelSizeY * i,
                        0, 43,
                        panelSizeX, panelSizeY
                );
            }

            // Footer
            context.drawTexture(SEARCH_WIDGET,
                    startX,
                    startY + (currentAmount * panelSizeY),
                    0, 58,
                    panelSizeX,
                    footerPanelSizeY
            );

            // End
            context.drawTexture(SEARCH_WIDGET,
                    startX,
                    startY + (currentAmount * panelSizeY) + footerPanelSizeY,
                    0, 70,
                    panelSizeX,
                    panelBorderSizeY
            );

            // Scroll Bar Button
            startY -= panelBorderSizeY;


            currentSearchResultHeight = 0;
            int searchScrollbarButtonOffset = 0;
            if(currentAmount > 0 && !results.isEmpty() && results.size() - currentAmount != 0){
                currentSearchResultHeight = (currentAmount * 14) + 4 + 11 + 4 - 9 - 2; // + top border + bottom + bottom border - scrollSize - bottom margin
                searchScrollbarButtonOffset = currentSearchResultHeight / (results.size() - currentAmount) * currentOffsetIndex;
                if(currentOffsetIndex == results.size() - currentAmount)
                    searchScrollbarButtonOffset = currentSearchResultHeight;
            }

            searchScrollbarButtonOffset = Math.min(currentSearchResultHeight, searchScrollbarButtonOffset);
            searchScrollbarButtonOffset = Math.max(0, searchScrollbarButtonOffset);

            context.drawTexture(SEARCH_WIDGET,
                    startX + panelSizeX - 5,
                    searchScrollbarButtonOffset + (startY + 1),
                    103, 39,
                    4,
                    9
            );

            int valuePanelSizeX = 93;
            int valuePanelSizeY = 14;

            int valuePanelStartX = startX + 3;
            // Create pool of resources with buttons
            int offset = 0;
            List<Identifier> activeIds = new ArrayList<>();
            for(int i = 0; i < currentAmount; i++){
                Identifier id = results.get((i + currentOffsetIndex));
                activeIds.add(id);
                int buttonIndex = pool.keySet().stream().toList().indexOf(id);
                int valuePanelStartY = startY + panelBorderSizeY + ((i) * panelSizeY);
                boolean mouseIsOver = isMouseOver(valuePanelSizeX, valuePanelSizeY, startX, valuePanelStartY);
                try{
                    Faction faction = FactionLookup.getFactionById(client.world, id);
                    FactionType type = faction.getFactionType();
                    int uvY = mouseIsOver ? 89 : 75;
                    if(type == FactionType.SUBFACTION)
                        uvY =  mouseIsOver ? 117 : 103;

                    context.drawTexture(SEARCH_WIDGET,
                            valuePanelStartX, valuePanelStartY,
                            0, uvY,
                            valuePanelSizeX, valuePanelSizeY
                    );
                    buttons.get(buttonIndex).setPosition(valuePanelStartX, valuePanelStartY);
                    buttons.get(buttonIndex).setDimensions(valuePanelSizeX, valuePanelSizeY);
                    if(!buttons.get(buttonIndex).active)
                        buttons.get(buttonIndex).active = true;

                    context.drawText(client.textRenderer, pool.get(id),
                            valuePanelStartX + 3, valuePanelStartY + 3,
                            0, false);
                } catch (FactionIdentifierException e) {
                    pool.remove(id);
                }
            }

            for(int i =0; i < pool.size(); i++) {
                Identifier foundId = pool.keySet().stream().toList().get(i);
                if(!activeIds.contains(foundId)){
                    int buttonIndex = pool.keySet().stream().toList().indexOf(foundId);
                    if(buttons.get(buttonIndex).active)
                        buttons.get(buttonIndex).active = false;
                }
            }
            
            // WIP - Will be continued to be worked on
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
        return 1;
    }


    public ButtonWidget getSearchBarToggleButton() {
        return searchBarToggleButton;
    }
    public ButtonWidget getScreenClickButton() {
        return screenClick;
    }
    private void setScreenClickbutton(int width, int height){
        screenClick.setDimensionsAndPosition(width, height, 0,0);
    }
    public void toggleSearch(boolean enabled) {
        searchBarToggle = enabled;
        searchResultToggle = enabled;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(searchBarToggle && searchBarToggleButton.isFocused()){
            // Keybind : Enter
            if(keyCode == 257){
                triggerSearch();
            }
            if(((keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z) || keyCode == KeyEvent.VK_SPACE) && searchBarInput.length() < 13) {
                String character = String.valueOf((char)keyCode);
                if(modifiers == 0)
                    character = character.toLowerCase();
                searchBarInput += character;
                currentSearchInputIndex ++;
                currentOffsetIndex = 0;
            }
            else if(!searchBarInput.isEmpty()){
                // Keybind : Return
                if((keyCode == 259))
                    searchBarInput = searchBarInput.substring(0, searchBarInput.length() - 1);
                    // Keybind : Delete
                else if(keyCode == 261)
                    searchBarInput = "";
            }
        }
        return true;
    }

    private void triggerSearch() {
        searchResultToggle = true;
        screenClick.active = true;
    }

    public boolean searchIsToggled() {
        return searchResultToggle;
    }

    public List<ButtonWidget> getAllButtons() {
        return buttons;
    }

    public void resetHeight() {
        currentOffsetIndex = 0;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if(searchBarToggle && isMouseOver(TOTAL_WIDTH, currentSearchResultHeight, (int) searchResultPanelStarts.x, (int) searchResultPanelStarts.y))
        currentOffsetIndex = Math.max(0, Math.min(currentlyShownEntries - currentAmount, currentOffsetIndex - (int) Math.round(verticalAmount)));
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }
}
