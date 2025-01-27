package net.sevenstars.middleearth.client.screens.utils.widgets;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.screens.faction_selection.FactionSelectionController;
import net.sevenstars.middleearth.exceptions.FactionIdentifierException;
import net.sevenstars.middleearth.resources.datas.FactionType;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringHelper;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchBarWidget extends ModWidget {
    private static final Identifier SEARCH_WIDGET = Identifier.of(MiddleEarth.MOD_ID, "textures/gui/widget/search_widget.png");
    private static final List<Integer> KEYS_TO_IGNORE = List.of(260, 262, 264, 263, 265, 266, 267, 268, 269);
    private static final int MINIMAL_MARGIN = 4;
    static final int SEARCH_BAR_PANEL_X = 102;
    static final int SEARCH_BAR_PANEL_Y = 18;
    public static final int TOTAL_WIDTH = SEARCH_BAR_PANEL_X;
    public ButtonWidget searchBarToggleButton;
    private boolean searchResultToggle;
    private boolean searchBarToggle;
    private int currentSearchInputIndex;
    private String searchBarInput = "";
    public ButtonWidget screenClick;
    HashMap<Identifier, Text> pool;
    List<ButtonWidget> buttons;
    FactionSelectionController controller;
    private final int maximumShownLength;
    private int currentAmount;
    private int currentlyShownEntries;
    private int currentOffsetIndex = 0;
    private int currentSearchResultHeight;
    private Vector2d searchResultPanelStarts = new Vector2d();
    TextRenderer textRenderer;

    int endY = 0;

    public SearchBarWidget(HashMap<Identifier, Text> newPool, FactionSelectionController controller) {
        this.controller = controller;
        this.maximumShownLength = SEARCH_BAR_PANEL_X - 14 - MARGIN;
        searchBarToggle = false;
        searchResultToggle = false;
        setButtons();
        pool = newPool;
        buttons = new ArrayList<>();
        for (Identifier id : newPool.keySet()) {
            buttons.add(
                    ButtonWidget.builder(
                            newPool.get(id), x -> onPress(id)
                    ).build());
            buttons.getLast().active = false;
        }
        textRenderer = MinecraftClient.getInstance().textRenderer;
    }

    private void onPress(Identifier id) {
        controller.setFactionId(id);
    }

    private void setButtons() {
        ButtonWidget.PressAction searchBarInputToggle = button -> {
            if (!searchBarToggle)
                searchBarToggle = true;
        };
        searchBarToggleButton = ButtonWidget.builder(Text.translatable("ui.me.search.toggle_button"), searchBarInputToggle).build();

        // Screen click
        ButtonWidget.PressAction screenClickAction = button -> {
            clickOnScreen();
        };
        screenClick = ButtonWidget.builder(Text.translatable("ui.me.search.screen_click_button"), screenClickAction).build();
    }

    private void clickOnScreen() {
        toggleSearch(false);
        screenClick.active = false;
        for (ButtonWidget button : buttons)
            button.active = false;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public int drawSearchBarCentered(DrawContext context, int centerX, int startY, TextRenderer textRenderer) {
        int startX = centerX - (TOTAL_WIDTH / 2);
        return drawSearchBar(context, startX, startY, textRenderer);
    }

    public int drawSearchBarAnchored(DrawContext context, int anchorX, int startY, boolean isLeftAnchor, TextRenderer textRenderer) {
        int startX = anchorX;
        if (!isLeftAnchor)
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
        if (ModWidget.getFocusEnabled() && searchBarToggleButton.isFocused()) {
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
                startX + magnifyingGlassSizeX + MINIMAL_MARGIN - 1,
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
        int startX = centerX - (TOTAL_WIDTH / 2);
        return drawSearchResults(context, startX, startY);
    }

    public int drawSearchResultsAnchored(DrawContext context, int anchorX, int startY, boolean isLeftAnchor) {
        int startX = anchorX;
        if (!isLeftAnchor)
            startX -= TOTAL_WIDTH;
        return drawSearchResults(context, startX, startY);
    }

    public int drawSearchResults(DrawContext context, int startX, int startY) {
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
        if (searchResultToggle) {
            List<Identifier> results = new ArrayList<>();
            for (Identifier identifier : pool.keySet()) {
                if (identifier.toString().replace("_", " ").contains(searchBarInput.toLowerCase())) {
                    results.add(identifier);
                } else if(pool.get(identifier).getString().contains(searchBarInput.toLowerCase()) || pool.get(identifier).getString().contains(searchBarInput)){
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
            currentAmount = Math.min(currentlyShownEntries, maximumShownLength);

            for (int i = 0; i < currentAmount; i++) {
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
            if (currentAmount > 0 && !results.isEmpty() && results.size() - currentAmount != 0) {
                currentSearchResultHeight = (currentAmount * 14) + 4 + 11 + 4 - 9 - 2; // + top border + bottom + bottom border - scrollSize - bottom margin
                searchScrollbarButtonOffset = currentSearchResultHeight / (results.size() - currentAmount) * currentOffsetIndex;
                if (currentOffsetIndex == results.size() - currentAmount)
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
            for (int i = 0; i < currentAmount; i++) {
                Identifier id = results.get((i + currentOffsetIndex));
                activeIds.add(id);
                int buttonIndex = pool.keySet().stream().toList().indexOf(id);
                int valuePanelStartY = startY + panelBorderSizeY + ((i) * panelSizeY);
                boolean mouseIsOver = isMouseOver(valuePanelSizeX, valuePanelSizeY, startX, valuePanelStartY);
                try {
                    Faction faction = FactionLookup.getFactionById(client.world, id);
                    FactionType type = faction.getFactionType();
                    int uvY = mouseIsOver ? 89 : 75;
                    if (type == FactionType.SUBFACTION)
                        uvY = mouseIsOver ? 117 : 103;

                    context.drawTexture(SEARCH_WIDGET,
                            valuePanelStartX, valuePanelStartY,
                            0, uvY,
                            valuePanelSizeX, valuePanelSizeY
                    );
                    buttons.get(buttonIndex).setPosition(valuePanelStartX, valuePanelStartY);
                    buttons.get(buttonIndex).setDimensions(valuePanelSizeX, valuePanelSizeY);
                    if (!buttons.get(buttonIndex).active)
                        buttons.get(buttonIndex).active = true;

                    context.drawText(client.textRenderer, pool.get(id),
                            valuePanelStartX + 3, valuePanelStartY + 3,
                            0, false);
                } catch (FactionIdentifierException e) {
                    pool.remove(id);
                }
            }

            for (int i = 0; i < pool.size(); i++) {
                Identifier foundId = pool.keySet().stream().toList().get(i);
                if (!activeIds.contains(foundId)) {
                    int buttonIndex = pool.keySet().stream().toList().indexOf(foundId);
                    if (buttons.get(buttonIndex).active)
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

    private void setScreenClickbutton(int width, int height) {
        screenClick.setDimensionsAndPosition(width, height, 0, 0);
    }

    public void toggleSearch(boolean enabled) {
        searchBarToggle = enabled;
        searchResultToggle = enabled;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(KEYS_TO_IGNORE.contains(keyCode))
            return false;
        if (searchBarToggle && searchBarToggleButton.isFocused()) {
            switch (keyCode) {
                case 257:
                    triggerSearch();
                    return true;
                case 259:
                    erase(1);
                    return true;
                case 261:
                    this.erase();
                    return true;
                default:
                    if (Screen.isCopy(keyCode)) {
                        MinecraftClient.getInstance().keyboard.setClipboard(searchBarInput);
                        return true;
                    } else if (Screen.isPaste(keyCode)) {
                        this.write(searchBarInput + MinecraftClient.getInstance().keyboard.getClipboard());
                        return true;
                    } else {
                        if (Screen.isCut(keyCode)) {
                            MinecraftClient.getInstance().keyboard.setClipboard(searchBarInput);
                            this.write("");
                            return true;
                        }
                        return false;
                    }
            }
        }
        return false;
    }

    public boolean charTyped(char chr, int modifiers) {
        if (!searchBarToggle) {
            return false;
        } else if (StringHelper.isValidChar(chr)) {
            this.addText(chr);
            return true;
        } else {
            return false;
        }
    }

    private void addText(Character newChar) {
        write(searchBarInput + StringHelper.stripInvalidChars(Character.toString(newChar)));
    }

    private void write(String newText) {
        newText = StringHelper.stripInvalidChars(newText);
        if(textRenderer.getWidth(searchBarInput + newText) >= maximumShownLength){
            newText = textRenderer.trimToWidth(newText, maximumShownLength);
        }
        searchBarInput = newText;
        currentSearchInputIndex++;
        currentOffsetIndex = 0;
    }

    private void erase(int amount) {
        if(!searchBarInput.isEmpty()){
            amount = Math.min(searchBarInput.length(), amount);
            write(searchBarInput.substring(0, searchBarInput.length() - amount));
        }
    }

    private void erase() {
        if(!searchBarInput.isEmpty())
            write("");
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
