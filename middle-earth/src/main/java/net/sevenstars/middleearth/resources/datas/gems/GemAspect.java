package net.sevenstars.middleearth.resources.datas.gems;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;
import net.sevenstars.middleearth.MiddleEarth;

/**
 * What a gem type is aligned to. The gem TYPE decides this; the cut only decides the tier.
 * Four general aspects plus one racial aspect per people.
 */
public enum GemAspect implements StringIdentifiable {
    ATTACK("attack"),
    DEFENSE("defense"),
    UTILITY("utility"),
    SPEED("speed"),
    MEN("men"),
    DWARVES("dwarves"),
    ELVES("elves"),
    MORGUL("morgul");

    public static final Codec<GemAspect> CODEC = StringIdentifiable.createCodec(GemAspect::values);

    private final String name;

    GemAspect(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }

    public String translationKey() {
        return "gem_aspect." + MiddleEarth.MOD_ID + "." + name;
    }
}
