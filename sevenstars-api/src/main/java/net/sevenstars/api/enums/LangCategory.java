package net.sevenstars.api.enums;

public enum LangCategory {
    NONE(""),
    TOOLTIP("tooltip"),
    SCREEN("screen"),
    CONTAINER("container"),
    ADVANCEMENTS("advancements"),
    ITEM_GROUP("item_group"),
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
    EMI("emi"),
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
    WIDGET("widget");

    public String Prefix;
    LangCategory(String prefix){
        this.Prefix = prefix;
    }
}
