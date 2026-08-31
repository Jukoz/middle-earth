package net.sevenstars.ofheraldsandhamlets.content.banners;

import net.minecraft.util.DyeColor;
import net.sevenstars.ofheraldsandhamlets.dtos.banner.Banner;
import net.sevenstars.ofheraldsandhamlets.dtos.banner.PatternWithColor;

import java.util.List;

public abstract class AbstractBannerPool {
    public static Banner create(DyeColor base, List<PatternWithColor> patternWithColors) {
        return new Banner(base, patternWithColors);
    }
}
