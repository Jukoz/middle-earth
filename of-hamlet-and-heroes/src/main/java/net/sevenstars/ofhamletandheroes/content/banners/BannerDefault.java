package net.sevenstars.ofhamletandheroes.content.banners;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.DyeColor;
import net.sevenstars.ofhamletandheroes.content.dispositions.AbstractDispositionPool;
import net.sevenstars.ofhamletandheroes.dtos.banner.Banner;
import net.sevenstars.ofhamletandheroes.dtos.banner.PatternWithColor;
import net.sevenstars.ofhamletandheroes.dtos.disposition.Disposition;
import net.sevenstars.ofhamletandheroes.registries.custom.BannerRegistryHH;
import net.sevenstars.ofhamletandheroes.registries.custom.DispositionRegistryHH;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BannerDefault extends AbstractBannerPool {
    public static final Banner NONE = create(DyeColor.BLACK, List.of(
            new PatternWithColor(BannerPatterns.CROSS, DyeColor.RED)
    ));

    public static Map<RegistryKey<Banner>, Banner> fetch() {
        Map<RegistryKey<Banner>, Banner> map = new HashMap<>();
        map.put(BannerRegistryHH.NONE, NONE);
        return map;
    }
}
