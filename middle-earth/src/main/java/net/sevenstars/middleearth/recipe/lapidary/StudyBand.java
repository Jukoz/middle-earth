package net.sevenstars.middleearth.recipe.lapidary;

/**
 * How hard a gem is to study (Refraction Reading), set per gem type with "study_band" in its gem_type json.
 * Sapphire = Ruby = Beryl &lt; Opal = Onyx = Moonstone = Hadorite &lt; Adamant.
 *
 * @param xpLevels experience levels paid each time a study starts (the gem itself is not used up)
 * @param runes    runes to light and lock before the study is done
 * @param facets   sides of the gem's outline
 * @param index    refractive index; higher bends the light more and traps it more often
 */
public record StudyBand(int xpLevels, int runes, int facets, float index) {
    public static final StudyBand[] BANDS = {
        new StudyBand(2, 3, 6, 1.55F),
        new StudyBand(3, 4, 8, 1.75F),
        new StudyBand(5, 5, 10, 2.1F)
    };

    /** Band 1, 2 or 3; anything else is clamped. */
    public static StudyBand of(int band) {
        return BANDS[Math.max(1, Math.min(BANDS.length, band)) - 1];
    }
}
