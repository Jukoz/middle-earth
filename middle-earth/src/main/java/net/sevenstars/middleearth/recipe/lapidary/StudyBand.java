package net.sevenstars.middleearth.recipe.lapidary;

/** How hard a gem type is to study: cost, runes and outline. */
public record StudyBand(int xpLevels, int runes, int facets, float index) {
    public static final StudyBand[] BANDS = {
        new StudyBand(2, 3, 6, 1.55F),
        new StudyBand(3, 4, 8, 1.75F),
        new StudyBand(5, 5, 10, 2.1F)
    };

    public static StudyBand of(int band) {
        return BANDS[Math.max(1, Math.min(BANDS.length, band)) - 1];
    }
}
