package net.sevenstars.ofhamletandheroes.content.banners;

import net.minecraft.util.DyeColor;
import net.sevenstars.ofhamletandheroes.dtos.banner.Banner;
import net.sevenstars.ofhamletandheroes.dtos.banner.PatternWithColor;

import java.util.List;

public abstract class AbstractBannerPool {
    public static Banner create(DyeColor base, List<PatternWithColor> patternWithColors) {
        return new Banner(base, patternWithColors);
    }
}
