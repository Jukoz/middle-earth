package net.sevenstars.middleearth.gui.cavetroll;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/** The horse screen layout with club and banner slots. */
@Environment(EnvType.CLIENT)
public class CaveTrollScreen extends HandledScreen<CaveTrollScreenHandler> {
    private static final Identifier SLOT_TEXTURE = Identifier.ofVanilla("container/slot");
    private static final Identifier CHEST_SLOTS_TEXTURE = Identifier.ofVanilla("container/horse/chest_slots");
    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/gui/container/horse.png");

    private final int columns;
    private float mouseX;
    private float mouseY;

    public CaveTrollScreen(CaveTrollScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, handler.getTroll().getDisplayName());
        this.columns = handler.getTroll().getInventoryColumns();
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int left = (this.width - this.backgroundWidth) / 2;
        int top = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, left, top, 0.0f, 0.0f, this.backgroundWidth, this.backgroundHeight, 256, 256);

        if(columns > 0) {
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, CHEST_SLOTS_TEXTURE, 90, 54, 0, 0, left + 79, top + 17, columns * 18, 54);
        }
        if(this.handler.getTroll().isTame()) {
            context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, SLOT_TEXTURE, left + 7, top + 17, 18, 18);
        }
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, SLOT_TEXTURE, left + 7, top + 35, 18, 18);
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, SLOT_TEXTURE, left + 7, top + 53, 18, 18);

        // Vanilla's size of 17 fits a horse, scale it to the troll's height
        int size = Math.max(1, (int)(17 * 1.6f / this.handler.getTroll().getHeight()));
        InventoryScreen.drawEntity(context, left + 26, top + 18, left + 78, top + 70, size, 0.25f, this.mouseX, this.mouseY, this.handler.getTroll());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
