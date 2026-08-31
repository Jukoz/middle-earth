package net.sevenstars.ofheraldsandhamlets.content.banners;

import net.minecraft.block.entity.BannerPatterns;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.DyeColor;
import net.sevenstars.ofheraldsandhamlets.dtos.banner.Banner;
import net.sevenstars.ofheraldsandhamlets.dtos.banner.PatternWithColor;
import net.sevenstars.ofheraldsandhamlets.registries.custom.BannerRegistryHH;

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
