package net.sevenstars.middleearth.gui.lapidarytable;

/**
 * Where each piece of the Faceting screen sits in its texture (textures/gui/lapidary_faceting.png) and on the
 * screen: {u, v, width, height, screen x, screen y}, or null for none. Tiers index 0 bare, 1 Masterwork, 2 Relic.
 * Generated with the texture by the gem-rework tools (gui_atlas.py); not edited by hand.
 */
final class LapidaryBoardSprites {
    /** The texture: the screen (the table bare of its fittings) and the pieces beside it. */
    static final int TEXTURE_WIDTH = 512, TEXTURE_HEIGHT = 260;

    /** The wheel, by tier. */
    static final int[][] WHEEL = {{249, 0, 15, 94, 77, 24}, {265, 0, 15, 94, 77, 24}, {281, 0, 15, 94, 77, 24}};
    /** The lenses and the dop stand, by tier. */
    static final int[][] LENSES = {{297, 0, 126, 61, 17, 62}, {249, 95, 126, 61, 17, 62}, {376, 95, 126, 61, 17, 62}};
    /** Over the lenses: [lenses * 3 + wheel]. */
    static final int[][] LENS_PATCH = {{393, 157, 13, 29, 78, 89}, {407, 157, 13, 29, 78, 89}, {421, 157, 13, 29, 78, 89}, {435, 157, 13, 29, 78, 89}, {449, 157, 13, 29, 78, 89}, {463, 157, 13, 29, 78, 89}, {435, 157, 13, 29, 78, 89}, {449, 157, 13, 29, 78, 89}, {463, 157, 13, 29, 78, 89}};
    /** The lamp, by tier (none bare). */
    static final int[][] LAMP = {null, {249, 157, 71, 60, 7, 17}, {321, 157, 71, 60, 7, 17}};
    /** Over the lamp: [(lamp * 3 + wheel) * 3 + lenses] (lamp 1..2). */
    static final int[][] LAMP_PATCH = {null, null, null, null, null, null, null, null, null, {319, 218, 12, 11, 78, 64}, {319, 218, 12, 11, 78, 64}, {319, 218, 12, 11, 78, 64}, {332, 218, 12, 11, 78, 64}, {332, 218, 12, 11, 78, 64}, {332, 218, 12, 11, 78, 64}, {345, 218, 12, 11, 78, 64}, {345, 218, 12, 11, 78, 64}, {345, 218, 12, 11, 78, 64}, {358, 218, 12, 11, 78, 64}, {358, 218, 12, 11, 78, 64}, {358, 218, 12, 11, 78, 64}, {371, 218, 12, 11, 78, 64}, {371, 218, 12, 11, 78, 64}, {371, 218, 12, 11, 78, 64}, {384, 218, 12, 11, 78, 64}, {384, 218, 12, 11, 78, 64}, {384, 218, 12, 11, 78, 64}};
    /** The pump, by tier (none bare). */
    static final int[][] PUMP = {null, {283, 218, 17, 13, 90, 28}, {301, 218, 17, 13, 90, 28}};
    /** Over the pump: [((pump * 3 + wheel) * 3 + lenses) * 3 + lamp] (pump 1..2). */
    static final int[][] PUMP_PATCH = {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
    /** The water meter: {u, v} of the vessel and its water, Masterwork then Relic (16 x 16 each). */
    static final int[][] VESSELS = {{477, 157}, {494, 157}, {249, 218}, {266, 218}};

    private LapidaryBoardSprites() {
    }
}
