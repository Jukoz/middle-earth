package net.sevenstars.middleearth.gui.utils.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import net.minecraft.util.StringUtil;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.gui.utils.widgets.searchbar.SearchBarResult;
import net.sevenstars.middleearth.gui.utils.widgets.searchbar.SearchBarResultType;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class SearchBarWidget extends ModWidget {
    private static final ResourceLocation SEARCH_WIDGET = ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "textures/gui/widget/search_widget.png");
    private static final List<Integer> KEYS_TO_IGNORE = List.of(260, 262, 264, 263, 265, 266, 267, 268, 269);
    private static final int MINIMAL_MARGIN = 4;
    private static final int DARK_GRAY = 0xFF404040;
    public int desiredWidth;
    public Button searchBarToggleButton;
    private boolean searchResultToggle;
    private boolean searchBarToggle;
    private int currentSearchInputIndex;
    private String searchBarInput = "";
    public Button screenClick;
    List<SearchBarResult> allPossibleResults;
    List<SearchBarResult> allCurrentResults;
    List<Button> resultButtons;
    private final int maximumShownLength;
    private int currentAmount;
    private int currentOffsetIndex = 0;
    private int currentSearchResultHeight;
    private Vector2d searchResultPanelStarts = new Vector2d();
    Font textRenderer;

    int endY = 0;

    public SearchBarWidget(int maxAmountOnScreen, List<SearchBarResult> allPossibleResults, Button.OnPress additionalScreenClickAction, int desiredWidth) {
        this.maximumShownLength = desiredWidth - 14 - MARGIN;
        searchBarToggle = false;
        searchResultToggle = false;
        this.desiredWidth = desiredWidth;
        this.allPossibleResults = allPossibleResults;
        this.allCurrentResults = new ArrayList<>();

        Button.OnPress searchBarInputToggle = button -> {
            if (!searchBarToggle)
                searchBarToggle = true;
        };
        searchBarToggleButton = Button.builder(Component.translatable("ui.%s.search.toggle_button".formatted(MiddleEarth.MOD_ID)), searchBarInputToggle).build();
        searchBarToggleButton.setSize(desiredWidth, searchBarToggleButton.getHeight());

        // Screen click
        screenClick = Button.builder(Component.translatable("ui.%s.search.screen_click_button".formatted(MiddleEarth.MOD_ID)), button -> clickOnScreen(button, additionalScreenClickAction)).build();
        screenClick.setAlpha(0);
        screenClick.setMessage(Component.nullToEmpty(""));

        resultButtons = new ArrayList<>();
        for(int i = 0; i < maxAmountOnScreen; i++){
            final int index = i;
            resultButtons.add(Button.builder(Component.nullToEmpty("N/A"), x -> buttonPress(x, index)).build());
            resultButtons.getLast().active = false;
        }
        textRenderer = Minecraft.getInstance().font;
    }

    public void buttonPress(Button button, int i){
        this.allCurrentResults.get(currentOffsetIndex + i).getAction().onPress(button);
    }


    private void clickOnScreen(Button button, Button.OnPress additionalAction) {
        toggleSearch(false);
        screenClick.active = false;
        for (Button resultButton : resultButtons)
            resultButton.active = false;

        additionalAction.onPress(button);
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public int drawSearchBarCentered(GuiGraphics context, int centerX, int startY, Font textRenderer) {
        int startX = centerX - (desiredWidth / 2);
        return drawSearchBar(context, startX, startY, textRenderer);
    }

    public int drawSearchBarAnchored(GuiGraphics context, int anchorX, int startY, boolean isLeftAnchor, Font textRenderer) {
        int startX = anchorX;
        if (!isLeftAnchor)
            startX -= desiredWidth;
        return drawSearchBar(context, startX, startY, textRenderer);
    }

    public int drawSearchBar(GuiGraphics context, int startX, int startY, Font textRenderer) {
        int panelSizeX = 102;
        int panelSizeY = 18;
        int sideMargins = MINIMAL_MARGIN / 2;
        int magnifyingGlassSizeX = 14;
        int magnifyingGlassSizeY = 14;

        // Search bar button
        context.blit(SEARCH_WIDGET,
                startX, startY, 0, searchBarToggleButton.isFocused() || isMouseOver(panelSizeX, panelSizeY, startX, startY) ? 19 : 0,
                panelSizeX, panelSizeY, 256, 256);

        searchBarToggleButton.setRectangle(desiredWidth, panelSizeY, startX, startY);
        drawSearchBarBackground(context, SEARCH_WIDGET, startX, startY);

        MutableComponent text = Component.translatable((!searchBarToggle && searchBarInput.isEmpty()) ? "ui.%s.search.label".formatted(MiddleEarth.MOD_ID) : searchBarInput);
        context.drawString(textRenderer, text,
                startX + magnifyingGlassSizeX + MINIMAL_MARGIN - 1,
                startY + (int) ((panelSizeY / 2f) - (textRenderer.lineHeight / 2f)) + 1,
                CommonColors.WHITE, false);


        // Search bar magnifying
        context.blit(SEARCH_WIDGET,
                startX + sideMargins, startY + 2, 102, 0,
                magnifyingGlassSizeX, magnifyingGlassSizeY, 256, 256);

        return panelSizeY + MINIMAL_MARGIN;
    }

    public int drawSearchResultsCentered(GuiGraphics context, int centerX, int startY) {
        int startX = centerX - (desiredWidth / 2);
        return drawSearchResults(context, startX, startY);
    }

    public int drawSearchResultsAnchored(GuiGraphics context, int anchorX, int startY, boolean isLeftAnchor) {
        int startX = anchorX;
        if (!isLeftAnchor)
            startX -= desiredWidth;
        return drawSearchResults(context, startX, startY);
    }

    public int drawSearchResults(GuiGraphics context, int startX, int startY) {
        setScreenClickbutton(context.guiWidth(), context.guiHeight());
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
            // Fill the search results
            allCurrentResults.clear();
            for(SearchBarResult possibleResult : allPossibleResults){
                if(possibleResult.getText().getContents().toString().contains(searchBarInput.toLowerCase())){
                    allCurrentResults.add(possibleResult);
                }
            }

            // Top
            startY = drawTop(context, SEARCH_WIDGET, startX, startY);

            // Center
            currentAmount = Math.min(Math.min((endY - startY - panelBorderSizeY) / panelSizeY, allCurrentResults.size()), resultButtons.size());

            int backgroundStartY = startY;
            backgroundStartY = drawCenter(context, SEARCH_WIDGET, startX, backgroundStartY, currentAmount);
            backgroundStartY = drawFooter(context, SEARCH_WIDGET, startX, backgroundStartY);
            drawBottom(context, SEARCH_WIDGET, startX, backgroundStartY);

            startY -= 4;

            currentSearchResultHeight = 0;
            int searchScrollbarButtonOffset = 0;
            if (currentAmount > 0 && !allCurrentResults.isEmpty() && allCurrentResults.size() - currentAmount != 0) {
                currentSearchResultHeight = (currentAmount * 14) + 4 + 11 + 4 - 9 - 2; // + top border + bottom + bottom border - scrollSize - bottom margin
                searchScrollbarButtonOffset = currentSearchResultHeight / (allCurrentResults.size() - currentAmount) * currentOffsetIndex;
                if (currentOffsetIndex == allCurrentResults.size() - currentAmount)
                    searchScrollbarButtonOffset = currentSearchResultHeight;
            }

            searchScrollbarButtonOffset = Math.min(currentSearchResultHeight, searchScrollbarButtonOffset - 1);
            searchScrollbarButtonOffset = Math.max(0, searchScrollbarButtonOffset);

            context.blit(SEARCH_WIDGET,
                    startX + desiredWidth - 5, searchScrollbarButtonOffset + (startY + 1), 31, 39,
                    4, 9, 256, 256);

            int valuePanelSizeX = SearchBarResultType.WIDTH;
            int valuePanelSizeY = SearchBarResultType.HEIGHT;

            int valuePanelStartX = startX + 3;
            // Create pool of resources with buttons
            for (int i = 0; i < currentAmount; i++) {
                SearchBarResult searchBarResult = allCurrentResults.get(i + currentOffsetIndex);

                int valuePanelStartY = startY + panelBorderSizeY + ((i) * panelSizeY);
                boolean mouseIsOver = isMouseOver(desiredWidth - 10, valuePanelSizeY, startX, valuePanelStartY);
                int uvX = mouseIsOver ? searchBarResult.getType().getActiveUvX() : searchBarResult.getType().getUvX();
                int uvY = mouseIsOver ? searchBarResult.getType().getActiveUvY() : searchBarResult.getType().getUvY();

                resultButtons.get(i).setPosition(valuePanelStartX, valuePanelStartY + 1);
                resultButtons.get(i).setSize(desiredWidth - 10, valuePanelSizeY - 2);

                valuePanelStartY = drawResultButtonBackground(context, SEARCH_WIDGET, valuePanelStartX, valuePanelStartY, uvX, uvY, desiredWidth - 10);

                if (!resultButtons.get(i).active)
                    resultButtons.get(i).active = true;

                context.drawString(client.font, searchBarResult.getText(),
                        valuePanelStartX + 3, valuePanelStartY + - 11,
                        DARK_GRAY, false);
            }

            // Disable all other buttons
            for (int i = currentAmount; i < resultButtons.size(); i++) {
                if (resultButtons.get(i).active)
                    resultButtons.get(i).active = false;
            }
        }
        return 1;
    }
    private int drawSearchBarBackground(GuiGraphics context, ResourceLocation searchWidget, int startX, int startY) {
        int uvY = (getFocusEnabled() && searchBarToggleButton.isFocused()) ? 19 : 0;

        context.blit(searchWidget,
                startX, startY, 0, uvY,10, 18, 256, 256);

        context.blit(searchWidget,
                startX + 10, startY, desiredWidth - 10 - 13, 18, 11.0F, uvY, 5, 18, 256, 256);

        context.blit(searchWidget,
                startX + desiredWidth - 13, startY, 17, uvY,13, 18, 256, 256);
        return startY + 4;
    }

    private int drawTop(GuiGraphics context, ResourceLocation searchWidget, int startX, int startY) {
        context.blit(searchWidget,
                startX, startY, 0, 38,10, 4, 256, 256);

        context.blit(searchWidget,
                startX + 10, startY, desiredWidth - 10 - 13, 4, 11.0F, 38.0F, 5, 4, 256, 256);

        context.blit(searchWidget,
                startX + desiredWidth - 13, startY, 17, 38,13, 4, 256, 256);
        return startY + 4;
    }

    private int drawFooter(GuiGraphics context, ResourceLocation searchWidget, int startX, int startY) {
        context.blit(searchWidget,
                startX, startY, 0, 58,10, 10, 256, 256);

        context.blit(searchWidget,
                startX + 10, startY, desiredWidth - 10 - 13, 10, 11.0F, 58.0F, 5, 10, 256, 256);

        context.blit(searchWidget,
                startX + desiredWidth - 13, startY, 17, 58,13, 10, 256, 256);
        return startY + 10;
    }

    private int drawBottom(GuiGraphics context, ResourceLocation searchWidget, int startX, int startY) {
        context.blit(searchWidget,
                startX, startY, 0, 69,10, 4, 256, 256);

        context.blit(searchWidget,
                startX + 10, startY, desiredWidth - 10 - 13, 4, 11.0F, 69.0F, 5, 4, 256, 256);

        context.blit(searchWidget,
                startX + desiredWidth - 13, startY, 17, 69,13, 4, 256, 256);
        return startY + 4;
    }

    private int drawCenter(GuiGraphics context, ResourceLocation searchWidget, int startX, int startY, int currentAmount) {
        context.blit(searchWidget,
                startX, startY, 10, 14 * currentAmount, 0.0F, 43.0F, 10, 14, 256, 256);

        context.blit(searchWidget,
                startX + 10, startY, desiredWidth - 10 - 13, 14 * currentAmount, 11.0F, 43.0F, 5, 14, 256, 256);

        context.blit(searchWidget,
                startX + desiredWidth - 13, startY, 13, 14 * currentAmount, 17.0F, 43.0F, 13, 14, 256, 256);
        return startY + (14 * currentAmount);
    }

    private int drawResultButtonBackground(GuiGraphics context, ResourceLocation searchWidget, int startX, int startY, int uvX, int uvY, int size) {
        context.blit(searchWidget,
                startX, startY, uvX, uvY,9, 14, 256, 256);
        startX += 9;

        context.blit(searchWidget,
                startX, startY, size - 18, 14, uvX + 10.0F, uvY, 5, 14, 256, 256);
        startX += size - 18;

        context.blit(searchWidget,
                startX, startY, uvX+16, uvY,9, 14, 256, 256);
        return startY + 14;
    }

    public Button getSearchBarToggleButton() {
        return searchBarToggleButton;
    }

    public Button getScreenClickButton() {
        return screenClick;
    }

    private void setScreenClickbutton(int width, int height) {
        screenClick.setRectangle(width, height, 0, 0);
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
                        Minecraft.getInstance().keyboardHandler.setClipboard(searchBarInput);
                        return true;
                    } else if (Screen.isPaste(keyCode)) {
                        this.write(searchBarInput + Minecraft.getInstance().keyboardHandler.getClipboard());
                        return true;
                    } else {
                        if (Screen.isCut(keyCode)) {
                            Minecraft.getInstance().keyboardHandler.setClipboard(searchBarInput);
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
        } else if (StringUtil.isAllowedChatCharacter(chr)) {
            this.addText(chr);
            return true;
        } else {
            return false;
        }
    }

    private void addText(Character newChar) {
        write(searchBarInput + StringUtil.filterText(Character.toString(newChar)));
    }

    private void write(String newText) {
        newText = StringUtil.filterText(newText);
        if(textRenderer.width(searchBarInput + newText) >= maximumShownLength){
            newText = textRenderer.plainSubstrByWidth(newText, maximumShownLength);
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

    public List<Button> getAllButtons() {
        return resultButtons;
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
        if(searchBarToggle && isMouseOver(desiredWidth, currentSearchResultHeight, (int) searchResultPanelStarts.x, (int) searchResultPanelStarts.y))
            currentOffsetIndex = Math.max(0, Math.min(allPossibleResults.size() - currentAmount, currentOffsetIndex - (int) Math.round(verticalAmount)));
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return super.mouseReleased(mouseX, mouseY, button);
    }
}
