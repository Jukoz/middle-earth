package net.jukoz.me.gui.artisantable;

import com.google.common.collect.Maps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.util.Map;
import java.util.Optional;

@Environment(value= EnvType.CLIENT)
public class ArtisanTableTab {
    private final MinecraftClient client;
    private final ArtisanTableScreen screen;
    private final ArtisanTableTabType type;
    private final int index;
    private final ItemStack icon;
    private final Text title;
    private final ArtisanTableInputsShape inputShape;
    private final Map<AdvancementEntry, AdvancementWidget> widgets = Maps.newLinkedHashMap();
    private double originX;
    private double originY;
    private int minPanX = Integer.MAX_VALUE;
    private int minPanY = Integer.MAX_VALUE;
    private int maxPanX = Integer.MIN_VALUE;
    private int maxPanY = Integer.MIN_VALUE;
    private boolean initialized;

    public ArtisanTableTab(MinecraftClient client, ArtisanTableScreen screen, ArtisanTableTabType type, int index,
                           Text title, ItemStack icon, ArtisanTableInputsShape inputShape) {
        this.client = client;
        this.screen = screen;
        this.type = type;
        this.index = index;
        this.icon = icon;
        this.title = title;
        this.inputShape = inputShape;
    }

    public ArtisanTableTab(MinecraftClient client, ArtisanTableScreen screen, ArtisanTableTabType type, int index,
                           Text title, ItemStack icon) {
        this(client, screen, type, index, title, icon, ArtisanTableInputsShape.ANY);
    }

    public ArtisanTableTabType getType() {
        return this.type;
    }

    public int getIndex() {
        return this.index;
    }


    public Text getTitle() {
        return this.title;
    }

    public ArtisanTableInputsShape getInputShape() {
        return this.inputShape;
    }

    public void drawBackground(DrawContext context, int x, int y, boolean selected) {
        this.type.drawBackground(context, x, y, selected, this.index);
    }

    public void drawIcon(DrawContext context, int x, int y) {
        this.type.drawIcon(context, x, y, this.index, this.icon);
    }

    public void render(DrawContext context, int x, int y) {
        if (!this.initialized) {
            this.originX = 117 - (double) (this.maxPanX + this.minPanX) / 2;
            this.originY = 56 - (double) (this.maxPanY + this.minPanY) / 2;
            this.initialized = true;
        }
    }

    public boolean isClickOnTab(int screenX, int screenY, double mouseX, double mouseY) {
        return !this.type.isClickOnTab(screenX, screenY, this.index, mouseX, mouseY);
    }

    public void move(double offsetX, double offsetY) {
        if (this.maxPanX - this.minPanX > 234) {
            this.originX = MathHelper.clamp(this.originX + offsetX, (double)(-(this.maxPanX - 234)), 0.0);
        }
        if (this.maxPanY - this.minPanY > 113) {
            this.originY = MathHelper.clamp(this.originY + offsetY, (double)(-(this.maxPanY - 113)), 0.0);
        }
    }

    public ArtisanTableScreen getScreen() {
        return this.screen;
    }
}

