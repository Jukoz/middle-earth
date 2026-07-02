package net.sevenstars.middleearth.gui.playerbook;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PlayerBookPageData {
    public String leftPageTitle;
    public Text leftPageDescription;
    public Text rightPageDescription;
    public Identifier image;

    public PlayerBookPageData withTitle(String title) {
        this.leftPageTitle = title;
        return this;
    }

    public PlayerBookPageData withLeftPageDesc(Text desc) {
        this.leftPageDescription = desc;
        return this;
    }

    public PlayerBookPageData withRightPageDesc(Text desc) {
        this.rightPageDescription = desc;
        return this;
    }

    public PlayerBookPageData withImage(Identifier image) {
        this.image = image;
        return this;
    }
}
