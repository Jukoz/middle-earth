package net.sevenstars.api.lang;

import java.util.Arrays;

/**
 * This class helps build the 'prefix' part of translatable keys,
 * <p>aka the part before the namespace / MOD_ID, which is:
 * <pre>
 *     common key form:
 *                        Identifier is this part
 *                        ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
 *     {@code
 *     category.[extra.xxx.]namespace.[path.xxx]
 *     }
 *     ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
 *     LangPrefix is this part
 *
 *     so it also would be:
 *     {@code
 *     LangPrefix DOT Identifier.namespace DOT Identifier.path
 *     }
 * </pre>
 * <p>
 *     the reason for this class is that some prefixes only have one word ({@code item.middle-earth.something})
 *     while some have multiple words ({@code tag.item.middle-earth.something_else})
 *     which are not that easy to be seen as one thing in the old method
 * </p>
 */
public final class LangPrefix {

    // default, for those keys directly start with MOD_ID
    public static final LangPrefix ROOT = new LangPrefix("");

    private final String value;
    static final String SPLITTER = ".";

    private LangPrefix(String value) {
        this.value = value;
    }

    /**
     * Builds a prefix from segments, split by dot '.'
     * <p>this method is to prevent manual raw string concat,
     * <pre>
     *     {@code
     *     'aaa' + 'bbb'
     *     }
     * </pre>
     * <p>which is inconvenient, and also might cause more or lack of dots
     * <p>Examples:
     * <pre>
     *     {@code
     *     LangPrefix.of("button")
     *     // "button"
     *
     *     LangPrefix.of("key", "category")
     *     // "key.category"
     *
     *     LangPrefix.of("")
     *     // ROOT
     *     }
     * </pre>
     */
    public static LangPrefix of(String... segments) {
        if (segments == null || segments.length == 0) {
            return ROOT;
        }

        StringBuilder value = new StringBuilder();
        for (String segment : segments) {
            if (segment == null || segment.isBlank()) {
                continue;
            }
            if (!value.isEmpty()) {
                value.append(SPLITTER);
            }
            value.append(segment);
        }

        return value.isEmpty() ? ROOT : new LangPrefix(value.toString());
    }

    /**
     * to prevent manual raw string concat as well
     * <p>Examples:
     * <pre>
     *     {@code
     *     LangPrefix.of("key").then("category")  // "key.category"
     *     }
     * </pre>
     */
    public LangPrefix then(String... segments) {
        if (segments == null || segments.length == 0) {
            return this;
        }

        String[] combined = Arrays.copyOf(segments, segments.length + 1);
        System.arraycopy(combined, 0, combined, 1, segments.length);
        combined[0] = value;
        return of(combined);
    }

    public LangPrefix then(LangPrefix extra) {
        if (extra == null) {
            return this;
        }
        return then(extra.value);
    }

    /**
     * the final result of the LangPrefix
     */
    public String value() {
        return value;
    }
}
