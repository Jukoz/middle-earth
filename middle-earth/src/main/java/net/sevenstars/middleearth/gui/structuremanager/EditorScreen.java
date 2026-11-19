package net.sevenstars.middleearth.gui.structuremanager;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.input.KeyInput;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

// A panel drawn by EditorPanel, with search lists, that saves on enter
@Environment(value = EnvType.CLIENT)
public abstract class EditorScreen<T extends ScreenHandler> extends HandledScreen<T> {
    protected EditorScreen(T handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    protected abstract void submit();

    protected abstract ChoiceListWidget[] lists();

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
    }

    // A container screen closes on the inventory key, a letter players may want to type in search
    @Override
    public boolean keyPressed(KeyInput input) {
        int keyCode = input.key();
        if(keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            submit();
            return true;
        }
        if(keyCode != GLFW.GLFW_KEY_ESCAPE && getFocused() instanceof TextFieldWidget field && field.isFocused()) {
            field.keyPressed(input);
            return true;
        }
        return super.keyPressed(input);
    }

    // Container screens swallow the wheel, so pass it to the list under the mouse
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        for(ChoiceListWidget list : lists()) {
            if(list != null && list.visible && list.isMouseOver(mouseX, mouseY))
                return list.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
    }
}
