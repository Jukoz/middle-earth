package net.sevenstars.middleearth.gui.playerbook;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PlayerBookPageData {
    public String leftPageTitle;
    public String leftPageDescription;
    public String rightPageDescription;
    public Identifier image;

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

    public PlayerBookPageData withImage(Identifier image) {
        this.image = image;
        return this;
    }
}
