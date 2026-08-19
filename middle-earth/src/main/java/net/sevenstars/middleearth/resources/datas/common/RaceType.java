package net.sevenstars.middleearth.resources.datas.common;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;

public enum RaceType {
    NONE,
    HUMAN,
    HOBBIT,
    DWARF,
    ELF,
    ORC,
    URUK,
    GOBLIN,
    SNAGA,
    BEAST,
    OTHER;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public MutableComponent getName() {
        return Component.translatable("race_type.".concat(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, toString()).toLanguageKey()));
    }
}
