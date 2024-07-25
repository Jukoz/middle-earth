package net.jukoz.me.item.utils;

import net.minecraft.util.StringIdentifiable;

public enum ModCapes implements StringIdentifiable {

    BASE_CAPE("base_cape"),
    SURCOAT("surcoat"),
    BASE_CLOAK("base_cloak"),

    FUR_CLOAK("fur_cloak"),

    GONDORIAN_CAPTAIN_CAPE("gondorian_captain_cape"),
    GONDORIAN_HERO_CAPE("gondorian_hero_cape"),
    GONDORIAN_KINGS_GUARD_CAPE("gondorian_kings_guard_cape"),
    GONDORIAN_CITADEL_GUARD_CAPE("gondorian_citadel_guard_cape"),
    GONDORIAN_FOUNTAIN_GUARD_CAPE("gondorian_fountain_guard_cape"),

    ROHIRRIC_CAPE("rohirric_cape"),
    ROHIRRIC_ROYAL_GUARD_CAPE("rohirric_royal_guard_cape"),
    EORLING_MARHSAL_CAPE("eorling_marshal_cape"),
    HORSE_LORD_CAPE("horse_lord_cape"),

    BARDING_SURCOAT("barding_surcoat"),
    DALISH_HEYDAY_CAPE("dalish_heyday_cape"),
    BARDING_SERGEANT_CAPE("barding_sergeant_cape"),

    EREBOR_CAPE("erebor_cape"),
    RAVENHILL_SENTINEL_CAPE("ravenhill_sentinel_cape"),

    LORIEN_MARCHWARDEN_CAPE("lorien_marchwarden_cape"),
    GALADDHRIM_CAPE("galadhrim_cape"),
    GALADHRIM_LORD_CAPE("galadhrim_lord_cape"),
    ;

    private final String name;

    private ModCapes(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
