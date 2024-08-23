package net.jukoz.me.resources.datas;

import net.jukoz.me.MiddleEarth;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public enum Alignment {
    GOOD,
    NEUTRAL,
    EVIL;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public MutableText getName() {
        return Text.translatable("alignment.".concat(Identifier.of(MiddleEarth.MOD_ID, toString()).toTranslationKey()));
    }
}
