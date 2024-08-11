package net.jukoz.me.client.screens.utils.widgets;

public abstract class ModWidget {
    protected static final int MARGIN = 4;
    private static int mouseX = 0;
    private static int mouseY = 0;
    private static boolean focusedEnabled = false;

    public static void updateMouse(int mouseX, int mouseY){
        ModWidget.mouseX = mouseX;
        ModWidget.mouseY = mouseY;
    }

    public int getMouseX() {return mouseX;}
    public int getMouseY() {return mouseY;}

    public static void enableFocus(boolean enabled){
        focusedEnabled = enabled;
    }
    public static boolean getFocusEnabled() {return focusedEnabled;}

    public static boolean isMouseOver(int sizeX, int sizeY, int startX, int startY) {
        return mouseX >= startX && mouseX <= startX + sizeX
                && mouseY >= startY && mouseY <= startY + sizeY;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return true;
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return true;
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return true;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        return true;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return true;
    }

    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return true;
    }
}
