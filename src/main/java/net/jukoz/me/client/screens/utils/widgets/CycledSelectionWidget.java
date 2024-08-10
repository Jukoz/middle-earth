package net.jukoz.me.client.screens.utils.widgets;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.screens.utils.CycledSelectionButtonType;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CycledSelectionWidget {
    private static final Identifier TEXTURE = Identifier.of(MiddleEarth.MOD_ID,"textures/gui/widget/cycled_selection_widget.png");
    private static boolean focusEnabled = false;
    private final ButtonWidget buttonLeft;
    private final ButtonWidget buttonRight;
    private final ButtonWidget selectionButton;
    private final CycledSelectionButtonType buttonType;
    private boolean shouldDisplay = true;
    static final int MARGIN = 4;
    static final int ARROW_SIZE_X = 7;
    static final int ARROW_SIZE_Y = 11;
    static final int PANEL_SIZE_X = CycledSelectionButtonType.WIDTH;
    static final int PANEL_SIZE_Y = CycledSelectionButtonType.HEIGHT;
    public static final int TOTAL_WIDTH = ((MARGIN + ARROW_SIZE_X) * 2) + PANEL_SIZE_X;
    public static final int TOTAL_HEIGHT = Math.max(PANEL_SIZE_Y, ARROW_SIZE_Y);

    int mouseX = 0;
    int mouseY = 0;

    public CycledSelectionWidget(ButtonWidget.PressAction leftAction, ButtonWidget.PressAction rightAction, ButtonWidget.PressAction selectionAction, CycledSelectionButtonType buttonType){
        this.buttonType = buttonType;
        buttonLeft = ButtonWidget.builder(Text.of("Cycled Selection Left"), leftAction).build();
        buttonLeft.setDimensions(ARROW_SIZE_X, ARROW_SIZE_Y);
        buttonRight = ButtonWidget.builder(Text.of("Cycled Selection Right"), rightAction).build();
        buttonRight.setDimensions(ARROW_SIZE_X, ARROW_SIZE_Y);

        selectionButton = ButtonWidget.builder(Text.of("Cycled Selection"), selectionAction).build();
        selectionButton.setDimensions(PANEL_SIZE_X, PANEL_SIZE_Y);
        if(selectionAction == null)
            selectionButton.active = false;
        focusEnabled = false;
    }

    public ButtonWidget getButtonLeft() {
        return buttonLeft;
    }

    public ButtonWidget getButtonRight() {
        return buttonRight;
    }

    public ButtonWidget getSelectionButton() {
        return selectionButton;
    }

    public void enableArrows(boolean activate){
        this.buttonLeft.active = activate;
        this.buttonRight.active = activate;
    }

    public void enableVisuals(boolean activate){
        shouldDisplay = activate;
        buttonLeft.active = activate;
        buttonRight.active = activate;
        selectionButton.active = activate;
    }

    public int drawAnchored(DrawContext context, int anchorX, int startY, boolean isLeftAnchor, MutableText text, TextRenderer textRenderer, int mouseX, int mouseY){
        int startX = anchorX;
        if(!isLeftAnchor)
            startX -= TOTAL_WIDTH;

        this.mouseX = mouseX;
        this.mouseY = mouseY;

        return draw(context, startX, startY, text, textRenderer);
    }

    public int drawCentered(DrawContext context, int centerX, int startY, MutableText text, TextRenderer textRenderer, int mouseX, int mouseY){
        int startX = centerX - (TOTAL_WIDTH / 2);

        this.mouseX = mouseX;
        this.mouseY = mouseY;

        return draw(context, startX, startY, text, textRenderer);
    }

    protected int draw(DrawContext context, int startX, int startY, MutableText text, TextRenderer textRenderer){
        if(!shouldDisplay){
            return 0;
        }

        int x = startX;
        int y = startY;

        int arrowStartOffsetY = (TOTAL_HEIGHT - ARROW_SIZE_Y) / 2;
        int buttonStartOffsetY = (TOTAL_HEIGHT - PANEL_SIZE_Y) / 2;

        boolean buttonIsHovered = false;

        // Left arrow
        if(buttonLeft.active){
            buttonLeft.setPosition(x, y + arrowStartOffsetY);
            buttonIsHovered = buttonLeft.isFocused() || isMouseOver(ARROW_SIZE_X, ARROW_SIZE_Y, x, y + arrowStartOffsetY);
            context.drawTexture(TEXTURE, x, y + arrowStartOffsetY, 206, buttonIsHovered ? 11 : 0, ARROW_SIZE_X, ARROW_SIZE_Y);
            if(buttonLeft.isFocused() && focusEnabled)
                context.drawTexture(TEXTURE, x, y + arrowStartOffsetY, 206, 33, ARROW_SIZE_X, ARROW_SIZE_Y);
        }

        // Center Button
        x += ARROW_SIZE_X + MARGIN;
        selectionButton.setPosition(x, y + buttonStartOffsetY);
        buttonIsHovered = selectionButton.active && (selectionButton.isFocused() || isMouseOver(PANEL_SIZE_X, PANEL_SIZE_Y, x, y + buttonStartOffsetY));
        context.drawTexture(TEXTURE, x, y + buttonStartOffsetY, buttonIsHovered ? buttonType.hoveredUvX : buttonType.uvX, buttonIsHovered ? buttonType.hoveredUvY : buttonType.uvY, PANEL_SIZE_X, PANEL_SIZE_Y);
        if(selectionButton.isFocused() && focusEnabled)
            context.drawTexture(TEXTURE, x, y + buttonStartOffsetY, CycledSelectionButtonType.FOCUS_UV_X, CycledSelectionButtonType.FOCUS_UV_Y, PANEL_SIZE_X, PANEL_SIZE_Y);

        if(text == null)
            text = Text.translatable("me.ui.selection.none");
        context.drawText(textRenderer, text,
                x + (int)((PANEL_SIZE_X - textRenderer.getWidth(text)) / 2f),
                startY + (int) ((PANEL_SIZE_Y / 2f) - (textRenderer.fontHeight / 2f)) + 1,
                0, false);

        x += PANEL_SIZE_X + MARGIN;
        // Left arrow
        if(buttonRight.active){
            buttonRight.setPosition(x, y + arrowStartOffsetY);
            buttonIsHovered = buttonRight.isFocused() || isMouseOver(ARROW_SIZE_X, ARROW_SIZE_Y, x, y + arrowStartOffsetY);
            context.drawTexture(TEXTURE, x, y + arrowStartOffsetY, 215, buttonIsHovered ? 11 : 0, ARROW_SIZE_X, ARROW_SIZE_Y);
            if(buttonRight.isFocused() && focusEnabled)
                context.drawTexture(TEXTURE, x, y + arrowStartOffsetY, 215, 33, ARROW_SIZE_X, ARROW_SIZE_Y);
        }

        return PANEL_SIZE_Y;
    }

    private boolean isMouseOver(int sizeX, int sizeY, int startX, int startY) {
        return mouseX >= startX && mouseX <= startX + sizeX
                && mouseY >= startY && mouseY <= startY + sizeY;
    }

    public static boolean focusEnabled() {
        return focusEnabled;
    }
    public static void toggleFocus() {
        focusEnabled = !focusEnabled;
    }
}
