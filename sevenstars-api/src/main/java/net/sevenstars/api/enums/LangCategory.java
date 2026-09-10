package net.sevenstars.api.enums;

import net.minecraft.util.Identifier;
import net.sevenstars.api.AbstractModInitializer;
import net.sevenstars.api.lang.LangKey;
import net.sevenstars.api.lang.LangPrefix;

/**
 * hard-coded categories that a translation key may start with
 *
 * <p>for translation key, check {@link LangKey} for more details
 */
public enum LangCategory {

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
    KEY_CATEGORY("key", "category"),

    // compat
    EMI("emi"),
    REI("rei"),
    MOD_MENU("modmenu"),

    // DEFAULT
    NONE("");

    private final LangPrefix prefix;

    LangCategory(String... segments) {
        this.prefix = LangPrefix.of(segments);
    }

    /**
     * the whole 'prefix' part of a key, rather than just a single word 'category'
     * <p> check {@link LangPrefix} for more details
     */
    public LangPrefix prefix() {
        return prefix;
    }

    /**
     * Builds {@code PREFIX.NAMESPACE.names...}.
     * <p>Examples:
     * <pre>{@code
     * LangCategory.ITEM.createKey("middle-earth", "mithril")
     * // item.middle-earth.mithril
     *
     * LangCategory.KEY_CATEGORY.createKey("middle-earth")
     * // key.category.middle-earth
     *
     * LangCategory.ALERT.createKey("middle-earth", "seat", "occupied")
     * // alert.middle-earth.seat.occupied
     * }</pre>
     */
    public String createKey(String namespace, String... names) {
        return LangKey.of(prefix, namespace, names);
    }

    /**
     * shortcut to get MOD_ID from {@link Identifier}
     * <p>especially for the situation that MOD_ID is private
     * <p>Examples:
     * <pre>
     *     {@code
     *     LangCategory.ITEM.createKey(MiddleEarth.id(ITEM_NAME), ITEM_NAME)
     *     // item.middle-earth.<ITEM_NAME>
     *     }
     * </pre>
     */
    public String createKey(Identifier value, String... names) {
        return createKey(value.getNamespace(), names);
    }


    /**
     * shortcut to get both MOD_ID and path from {@link Identifier}
     * <p>Examples:
     * <pre>
     *     {@code
     *     LangCategory.ITEM.createKey(MiddleEarth.id("artisan_table"))
     *     // item.middle-earth.artisan_table
     *     }
     * </pre>
     */
    public String createKey(Identifier value) {
        return createKey(value, value.getPath());
    }
}
