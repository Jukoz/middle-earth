package net.sevenstars.middleearth.resources.datas;

import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public enum FactionType {
    FACTION,
    SUBFACTION;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public MutableText getName() {
        return Text.translatable("faction_type.".concat(Identifier.of(MiddleEarth.MOD_ID, toString()).toTranslationKey()));
    }
}
