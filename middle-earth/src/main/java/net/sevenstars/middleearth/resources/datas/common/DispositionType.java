package net.sevenstars.middleearth.resources.datas.common;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;

public enum DispositionType {
    GOOD,
    NEUTRAL,
    EVIL;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public MutableComponent getName() {
        return Component.translatable("disposition.".concat(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, toString()).toLanguageKey()));
    }
}
