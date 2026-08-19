package net.sevenstars.middleearth.gui.artisantable;

import com.google.common.collect.Maps;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ArtisanTableTab {
    private final Minecraft client;
    private final ArtisanTableScreen screen;
    private final ArtisanTableTabType type;
    private final int index;
    private final ItemStack icon;
    private final Component title;
    private final ArtisanTableInputsShape inputShape;
    private final Map<AdvancementHolder, AdvancementWidget> widgets = Maps.newLinkedHashMap();
    private double originX;
    private double originY;
    private int minPanX = Integer.MAX_VALUE;
    private int minPanY = Integer.MAX_VALUE;
    private int maxPanX = Integer.MIN_VALUE;
    private int maxPanY = Integer.MIN_VALUE;
    private boolean initialized;

    public ArtisanTableTab(Minecraft client, ArtisanTableScreen screen, ArtisanTableTabType type, int index,
                           Component title, ItemStack icon, ArtisanTableInputsShape inputShape) {
        this.client = client;
        this.screen = screen;
        this.type = type;
        this.index = index;
        this.icon = icon;
        this.title = title;
        this.inputShape = inputShape;
    }

    public ArtisanTableTab(Minecraft client, ArtisanTableScreen screen, ArtisanTableTabType type, int index,
                           Component title, ItemStack icon) {
        this(client, screen, type, index, title, icon, ArtisanTableInputsShape.ANY);
    }

    public ArtisanTableTabType getType() {
        return this.type;
    }

    public int getIndex() {
        return this.index;
    }


    public Component getTitle() {
        return this.title;
    }

    public ArtisanTableInputsShape getInputShape() {
        return this.inputShape;
    }

    public void drawBackground(GuiGraphics context, int x, int y, boolean selected) {
        this.type.drawBackground(context, x, y, selected, this.index);
    }

    public void drawIcon(GuiGraphics context, int x, int y) {
        this.type.drawIcon(context, x, y, this.index, this.icon);
    }

    public void render(GuiGraphics context, int x, int y) {
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
            this.originX = Mth.clamp(this.originX + offsetX, (double)(-(this.maxPanX - 234)), 0.0);
        }
        if (this.maxPanY - this.minPanY > 113) {
            this.originY = Mth.clamp(this.originY + offsetY, (double)(-(this.maxPanY - 113)), 0.0);
        }
    }

    public ArtisanTableScreen getScreen() {
        return this.screen;
    }
}

