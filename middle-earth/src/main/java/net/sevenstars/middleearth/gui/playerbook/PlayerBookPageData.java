package net.sevenstars.middleearth.gui.playerbook;

import net.minecraft.resources.ResourceLocation;

public class PlayerBookPageData {
    public String leftPageTitle;
    public String leftPageDescription;
    public String rightPageDescription;
    public ResourceLocation image;

    public PlayerBookPageData withTitle(String title) {
        this.leftPageTitle = title;
        return this;
    }

    public PlayerBookPageData withLeftPageDesc(String desc) {
        this.leftPageDescription = desc;
        return this;
    }

    public PlayerBookPageData withRightPageDesc(String desc) {
        this.rightPageDescription = desc;
        return this;
    }

    public PlayerBookPageData withImage(ResourceLocation image) {
        this.image = image;
        return this;
    }
}
