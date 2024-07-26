package net.jukoz.me.resource.data;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public enum Alignment {
    Good,
    Neutral,
    Evil;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public MutableText getName() {
        return Text.translatable(Identifier.of(MiddleEarth.MOD_ID, "alignment." + toString()).toTranslationKey());
    }
}
