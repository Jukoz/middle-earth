package net.sevenstars.middleearth.resources.datas.common;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

public enum DispositionType {
    GOOD,
    NEUTRAL,
    EVIL;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public MutableText getName() {
        return Text.translatable("disposition.".concat(Identifier.of(MiddleEarth.MOD_ID, toString()).toTranslationKey()));
    }
}
