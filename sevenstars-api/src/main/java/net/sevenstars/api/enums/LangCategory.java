package net.sevenstars.api.enums;

import net.minecraft.text.MutableText;
import net.sevenstars.api.AbstractModInitializer;
import net.sevenstars.api.SevenStarsApi;

public enum LangCategory {
    NONE(""),

    BUTTON("button"),
    TOOLTIP("tooltip"),
    SCREEN("screen"),
    CONTAINER("container"),
    ADVANCEMENTS("advancements"),
    ITEM_GROUP("itemGroup"),
    EFFECT("effect"),
    ENCHANTMENT("enchantment"),
    BLOCK("block"),
    ITEM("item"),
    SOUND("sound"),
    TRIM_MATERIAL("trim_material"),
    FACTION("faction"),
    SPAWN("spawn"),
    ALERT("alert"),
    BIOME("biome"),
    COMMAND("command"),
    DESCRIPTION("description"),
    DISPOSITION("disposition"),
    ENTITY("entity"),
    EVENT("event"),
    EXCEPTION("exception"),
    NPC_DATA("npc_data"),
    NPC_TYPE("npc_type"),
    PAINTING("painting"),
    INSCRIPTION("inscription"),
    RACE("race"),
    SOUNDS("sounds"),
    STRUCTURE_MANAGER_DATA("structure_manager_data"),
    STRUCTURE_NEST("structure_nest"),
    TAG("tag"),
    TRIM_PATTERN("trim_pattern"),
    UI("ui"),
    KEY("key"),
    WIDGET("widget"),
    SEASON("season"),
    CONFIG("config"),

    // compat
    EMI("emi"),
    REI("rei"),
    MOD_MENU("modmenu");

    public String Prefix;
    LangCategory(String prefix){
        this.Prefix = prefix;
    }

    public String createKey(AbstractModInitializer modInitializer, String... names) {
        return modInitializer
                .id(modInitializer.idAggregate('.', names).toString())
                .toTranslationKey(Prefix);
    }
}
