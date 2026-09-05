package net.sevenstars.ofhallsandheralds.content.banners;

import net.minecraft.util.DyeColor;
import net.sevenstars.ofhallsandheralds.dtos.Banner;
import net.sevenstars.ofhallsandheralds.dtos.PatternWithColor;

import java.util.List;

public abstract class AbstractBannerPool {
    public static Banner create(DyeColor base, List<PatternWithColor> patternWithColors) {
        return new Banner(base, patternWithColors);
    }
}
