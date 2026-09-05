package net.sevenstars.ofhallsandheralds.dtos;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.DyeColor;

public class PatternWithColor {
    public static final Codec<PatternWithColor> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryKey.createCodec(RegistryKeys.BANNER_PATTERN).fieldOf("pattern").forGetter(PatternWithColor::getPattern),
            DyeColor.CODEC.fieldOf("dye_color").forGetter(PatternWithColor::getDyeColor)
    ).apply(instance, PatternWithColor::new));

    public RegistryKey<BannerPattern> pattern;
    public DyeColor color;

    public PatternWithColor(RegistryKey<BannerPattern> pattern, DyeColor dyeColor){
        this.pattern = pattern;
        this.color = dyeColor;
    }

    public DyeColor getDyeColor(){
        return this.color;
    }

    public RegistryKey<BannerPattern> getPattern(){
        return this.pattern;
    }
}
