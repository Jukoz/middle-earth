package net.sevenstars.middleearth.client;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;

public final class MEModelLoader {
    public static final Material KITE_SHIELD_BASE;
    public static final Material KITE_SHIELD_BASE_NO_PATTERN;

    public static final Material HEATER_SHIELD_BASE;
    public static final Material HEATER_SHIELD_BASE_NO_PATTERN;

    public static final Material ROUND_SHIELD_BASE;
    public static final Material ROUND_SHIELD_BASE_NO_PATTERN;

    private MEModelLoader() {
    }

    static {
        KITE_SHIELD_BASE = new Material(Sheets.SHIELD_SHEET, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "entity/kite_shield_base"));
        KITE_SHIELD_BASE_NO_PATTERN = new Material(Sheets.SHIELD_SHEET, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "entity/kite_shield_base_nopattern"));

        HEATER_SHIELD_BASE = new Material(Sheets.SHIELD_SHEET, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "entity/heater_shield_base"));
        HEATER_SHIELD_BASE_NO_PATTERN = new Material(Sheets.SHIELD_SHEET, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "entity/heater_shield_base_nopattern"));

        ROUND_SHIELD_BASE = new Material(Sheets.SHIELD_SHEET, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "entity/round_shield_base"));
        ROUND_SHIELD_BASE_NO_PATTERN = new Material(Sheets.SHIELD_SHEET, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "entity/round_shield_base_nopattern"));
    }
}
