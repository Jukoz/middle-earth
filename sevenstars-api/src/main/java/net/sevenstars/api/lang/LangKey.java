package net.sevenstars.api.lang;

import net.minecraft.util.Identifier;
import net.sevenstars.api.enums.LangCategory;

/**
 * This class helps build the basic form of the translatable keys, which is like:
 * <pre>
 *     {@code
 *     PREFIX[.extra.xxx].NAMESPACE[.name.xxx]
 *     }
 * </pre>
 */
public final class LangKey {
    private LangKey() {}
    static final String SPLITTER = ".";

    /**
     * Builds the whole key
     *
     * <pre>
     *     {@code
     *      LangKey.of(LangPrefix.of("item"), MOD_ID, "mithril")
     *      // item.middle-earth.mithril
     *
     *      LangKey.of(LangPrefix.of("key").then("category"), MOD_ID)
     *      // key.category.middle-earth
     *      }
     * </pre>
     *
     * @param prefix    the part before the MOD_ID, details in {@link LangPrefix}
     * @param namespace aka the MOD_ID
     * @param names     the remaining path, like which in the {@link Identifier}
     */
    public static String of(LangPrefix prefix, String namespace, String... names) {
        StringBuilder key = new StringBuilder(prefix == null ? "" : prefix.value());
        append(key, namespace);
        if (names != null) {
            for (String name : names) {
                append(key, name);
            }
        }

        if (key.isEmpty()) {
            throw new IllegalArgumentException("A translation key needs at least one non-blank segment");
        }
        return key.toString();
    }

    /**
     * shortcut for {@link LangCategory}
     */
    public static String of(LangCategory category, String namespace, String... names) {
        if (category == null) {
            throw new IllegalArgumentException("A translation key needs a non-null LangCategory");
        }
        return of(category.prefix(), namespace, names);
    }

    /**
     * shortcut to get MOD_ID from an {@link Identifier}
     */
    public static String of(LangPrefix prefix, Identifier value, String... names) {
        if (value == null) {
            throw new IllegalArgumentException("A translation key needs a non-null Identifier");
        }
        return of(prefix, value.getNamespace(), names);
    }

    /**
     * shortcut to get MOD_ID from an {@link Identifier},
     * <p>and directly get path from Identifier
     */
    public static String of(LangPrefix prefix, Identifier value) {
        if (value == null) {
            throw new IllegalArgumentException("A translation key needs a non-null Identifier");
        }
        return of(prefix, value, value.getPath());
    }

    /**
     * shortcut for both {@link LangCategory} and {@link Identifier}
     */
    public static String of(LangCategory category, Identifier value, String... names) {
        if (category == null) {
            throw new IllegalArgumentException("A translation key needs a non-null LangCategory");
        }
        return of(category.prefix(), value, names);
    }

    /**
     * shortcut for both {@link LangCategory} and {@link Identifier},
     * <p>and directly get path from Identifier
     */
    public static String of(LangCategory category, Identifier value) {
        if (category == null) {
            throw new IllegalArgumentException("A translation key needs a non-null LangCategory");
        }
        return of(category.prefix(), value, value.getPath());
    }

    /**
     * to replace the manual raw string concat
     * <p>which is {@code 'aaa' + '.' + 'bbb'}
     */
    private static void append(StringBuilder key, String segment) {
        if (segment == null || segment.isEmpty()) {
            return;
        }
        if (!key.isEmpty()) {
            key.append(SPLITTER);
        }
        key.append(segment);
    }
}
