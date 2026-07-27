package net.sevenstars.middleearth.gui.artisantable;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

enum ArtisanTableTabType {
    ABOVE(new Textures(ResourceLocation.withDefaultNamespace("advancements/tab_above_left_selected"), ResourceLocation.withDefaultNamespace("advancements/tab_above_middle_selected"), ResourceLocation.withDefaultNamespace("advancements/tab_above_right_selected")), new Textures(ResourceLocation.withDefaultNamespace("advancements/tab_above_left"), ResourceLocation.withDefaultNamespace("advancements/tab_above_middle"), ResourceLocation.withDefaultNamespace("advancements/tab_above_right")), 28, 32, 8),
    BELOW(new Textures(ResourceLocation.withDefaultNamespace("advancements/tab_below_left_selected"), ResourceLocation.withDefaultNamespace("advancements/tab_below_middle_selected"), ResourceLocation.withDefaultNamespace("advancements/tab_below_right_selected")), new Textures(ResourceLocation.withDefaultNamespace("advancements/tab_below_left"), ResourceLocation.withDefaultNamespace("advancements/tab_below_middle"), ResourceLocation.withDefaultNamespace("advancements/tab_below_right")), 28, 32, 8),
    LEFT(new Textures(ResourceLocation.withDefaultNamespace("advancements/tab_left_top_selected"), ResourceLocation.withDefaultNamespace("advancements/tab_left_middle_selected"), ResourceLocation.withDefaultNamespace("advancements/tab_left_bottom_selected")), new Textures(ResourceLocation.withDefaultNamespace("advancements/tab_left_top"), ResourceLocation.withDefaultNamespace("advancements/tab_left_middle"), ResourceLocation.withDefaultNamespace("advancements/tab_left_bottom")), 32, 28, 5),
    RIGHT(new Textures(ResourceLocation.withDefaultNamespace("advancements/tab_right_top_selected"), ResourceLocation.withDefaultNamespace("advancements/tab_right_middle_selected"), ResourceLocation.withDefaultNamespace("advancements/tab_right_bottom_selected")), new Textures(ResourceLocation.withDefaultNamespace("advancements/tab_right_top"), ResourceLocation.withDefaultNamespace("advancements/tab_right_middle"), ResourceLocation.withDefaultNamespace("advancements/tab_right_bottom")), 32, 28, 5);

    private final Textures selectedTextures;
    private final Textures unselectedTextures;
    private final int width;
    private final int height;
    private final int tabCount;

    ArtisanTableTabType(Textures selectedTextures, Textures unselectedTextures, int width, int height, int tabCount) {
        this.selectedTextures = selectedTextures;
        this.unselectedTextures = unselectedTextures;
        this.width = width;
        this.height = height;
        this.tabCount = tabCount;
    }

    public int getTabCount() {
        return this.tabCount;
    }

    public void drawBackground(GuiGraphics context, int x, int y, boolean selected, int index) {
        Textures textures;
        Textures textures2 = textures = selected ? this.selectedTextures : this.unselectedTextures;
        ResourceLocation identifier = index == 0 ? textures.first() : (index == this.tabCount - 1 ? textures.last() : textures.middle());
        context.blitSprite(identifier, x + this.getTabX(index), y + this.getTabY(index), this.width, this.height);
    }

    public void drawIcon(GuiGraphics context, int x, int y, int index, ItemStack stack) {
        int i = x + this.getTabX(index);
        int j = y + this.getTabY(index);
        switch (this.ordinal()) {
            case 0: {
                i += 6;
                j += 9;
                break;
            }
            case 1: {
                i += 6;
                j += 6;
                break;
            }
            case 2: {
                i += 10;
                j += 5;
                break;
            }
            case 3: {
                i += 6;
                j += 5;
            }
        }
        context.renderFakeItem(stack, i, j);
    }

    public int getTabX(int index) {
        return switch (this.ordinal()) {
            case 0, 1 -> (this.width + 4) * index;
            case 2 -> -this.width + 4;
            case 3 -> 248;
            default ->
                    throw new UnsupportedOperationException("Don't know what this tab type is!" + String.valueOf((Object) this));
        };
    }

    public int getTabY(int index) {
        return switch (this.ordinal()) {
            case 0 -> -this.height + 4;
            case 1 -> 136;
            case 2, 3 -> this.height * index;
            default ->
                    throw new UnsupportedOperationException("Don't know what this tab type is!" + String.valueOf((Object) this));
        };
    }

    public boolean isClickOnTab(int screenX, int screenY, int index, double mouseX, double mouseY) {
        int i = screenX + this.getTabX(index);
        int j = screenY + this.getTabY(index);
        return mouseX > (double)i && mouseX < (double)(i + this.width) && mouseY > (double)j && mouseY < (double)(j + this.height);
    }

    @OnlyIn(Dist.CLIENT)
    record Textures(ResourceLocation first, ResourceLocation middle, ResourceLocation last) {
    }
}

