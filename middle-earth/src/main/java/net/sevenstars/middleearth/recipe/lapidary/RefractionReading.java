package net.sevenstars.middleearth.recipe.lapidary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.util.Identifier;

/**
 * The geometry of the study minigame, "Refraction Reading". Shared by the server (which owns the game) and the
 * client (which draws the beam live), so both must give the same answers: plain double maths, no randomness
 * outside the seeded {@link Random}s.
 * <p>
 * A gem's outline is a convex polygon of radius 1 around the centre, with its own shape per gem type.
 * A lamp sits on a ring around it and shines at the centre. Light refracts into the gem (Snell's law), bounces
 * inside by total internal reflection while it can, refracts out and lands on the rune ring. The player turns
 * the gem and moves the lamp, both in 15 degree steps, to land the light on each rune and lock it.
 * Moves: each step the gem turns is one move; one drag of the lamp is one move however far it goes, so the
 * player can sweep the lamp to look around and only pays for the gem turns they choose.
 * <p>
 * Coordinates are maths-style: angles counter-clockwise from +x, y up. The screen flips y when drawing.
 */
public final class RefractionReading {
    /** Positions per turn for both the lamp and the gem: 24 steps of 15 degrees. */
    public static final int STEPS = 24;
    public static final double STEP_DEGREES = 360.0 / STEPS;
    public static final double LAMP_RING = 2.1;
    /** The runes are engraved on the board's brass rim, one slot every {@link #RUNE_SLOT_DEGREES}. */
    public static final double RUNE_RING = 2.65;
    public static final int RUNE_SLOTS = 36;
    public static final int RUNE_SLOT_DEGREES = 360 / RUNE_SLOTS;
    /** A rune is lit when the light lands within this many degrees of it. */
    public static final double RUNE_TOLERANCE = 4.0;
    /** Runes are at least this far apart, so one beam never lights two. */
    private static final int RUNE_SPACING = 30;
    /** Light that bounces this many times inside the gem without escaping counts as trapped. */
    private static final int MAX_SEGMENTS = 16;
    private static final double EPSILON = 1.0E-7;

    private RefractionReading() {
    }

    /**
     * The gem's outline, unrotated: {@code facets} vertices counter-clockwise, all within radius 1.
     * Each gem type gets its own fixed shape (seeded by its id), stretched a little along a tilted axis.
     */
    public static double[][] outline(Identifier gem, int facets) {
        Random random = new Random(gem.toString().hashCode() * 31L + facets);
        double step = 2.0 * Math.PI / facets;
        double stretch = 1.0 + random.nextDouble() * 0.35;
        double tilt = random.nextDouble() * Math.PI;
        double cosT = Math.cos(tilt);
        double sinT = Math.sin(tilt);
        double[][] points = new double[facets][2];
        double maxRadius = 0;
        for (int i = 0; i < facets; i++) {
            double angle = i * step + (random.nextDouble() - 0.5) * 0.6 * step;
            double x = Math.cos(angle);
            double y = Math.sin(angle);
            // Stretch along the tilted axis: rotate by -tilt, scale x, rotate back. Keeps it convex and CCW.
            double u = (x * cosT + y * sinT) * stretch;
            double v = -x * sinT + y * cosT;
            points[i][0] = u * cosT - v * sinT;
            points[i][1] = u * sinT + v * cosT;
            maxRadius = Math.max(maxRadius, Math.hypot(points[i][0], points[i][1]));
        }
        for (double[] point : points) {
            point[0] /= maxRadius;
            point[1] /= maxRadius;
        }
        return points;
    }

    /** The outline turned by {@code gemStep} steps counter-clockwise. */
    public static double[][] rotate(double[][] outline, int gemStep) {
        double angle = Math.toRadians(gemStep * STEP_DEGREES);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double[][] turned = new double[outline.length][2];
        for (int i = 0; i < outline.length; i++) {
            turned[i][0] = outline[i][0] * cos - outline[i][1] * sin;
            turned[i][1] = outline[i][0] * sin + outline[i][1] * cos;
        }
        return turned;
    }

    /**
     * The light's path from the lamp. {@code points} holds x, y pairs from the lamp to where it ends;
     * {@code exitDegrees} is where it lands on the rune ring, or NaN if it is trapped in the gem.
     */
    public record Beam(double[] points, double exitDegrees) {
        public boolean escaped() {
            return !Double.isNaN(exitDegrees);
        }
    }

    public static Beam trace(double[][] outline, double index, int lampStep, int gemStep) {
        double[][] poly = rotate(outline, gemStep);
        int n = poly.length;
        double lampAngle = Math.toRadians(lampStep * STEP_DEGREES);
        double px = LAMP_RING * Math.cos(lampAngle);
        double py = LAMP_RING * Math.sin(lampAngle);
        double dx = -Math.cos(lampAngle);
        double dy = -Math.sin(lampAngle);
        boolean inside = false;
        List<Double> points = new ArrayList<>();
        points.add(px);
        points.add(py);

        for (int segment = 0; segment < MAX_SEGMENTS; segment++) {
            double bestT = Double.MAX_VALUE;
            int bestEdge = -1;
            for (int i = 0; i < n; i++) {
                double ax = poly[i][0];
                double ay = poly[i][1];
                double ex = poly[(i + 1) % n][0] - ax;
                double ey = poly[(i + 1) % n][1] - ay;
                double denominator = dx * ey - dy * ex;
                if (Math.abs(denominator) < 1.0E-12) {
                    continue;
                }
                double wx = ax - px;
                double wy = ay - py;
                double t = (wx * ey - wy * ex) / denominator;
                double s = (wx * dy - wy * dx) / denominator;
                if (t > EPSILON && s >= 0 && s <= 1 && t < bestT) {
                    bestT = t;
                    bestEdge = i;
                }
            }
            if (bestEdge < 0) {
                if (inside) {
                    break; // numerical slip at a corner; call it trapped
                }
                // Out of the gem: carry on to the rune ring.
                double b = px * dx + py * dy;
                double c = px * px + py * py - RUNE_RING * RUNE_RING;
                double t = -b + Math.sqrt(Math.max(0, b * b - c));
                double hx = px + t * dx;
                double hy = py + t * dy;
                points.add(hx);
                points.add(hy);
                return new Beam(toArray(points), normalize(Math.toDegrees(Math.atan2(hy, hx))));
            }
            px += bestT * dx;
            py += bestT * dy;
            points.add(px);
            points.add(py);

            // Outward normal of a CCW edge (ex, ey) is (ey, -ex); turn it to face the incoming light.
            double ex = poly[(bestEdge + 1) % n][0] - poly[bestEdge][0];
            double ey = poly[(bestEdge + 1) % n][1] - poly[bestEdge][1];
            double length = Math.hypot(ex, ey);
            double nx = ey / length;
            double ny = -ex / length;
            if (dx * nx + dy * ny > 0) {
                nx = -nx;
                ny = -ny;
            }
            double cosI = -(dx * nx + dy * ny);
            double eta = inside ? index : 1.0 / index;
            double k = 1.0 - eta * eta * (1.0 - cosI * cosI);
            if (k < 0) {
                // Total internal reflection: the light stays in the gem.
                dx += 2.0 * cosI * nx;
                dy += 2.0 * cosI * ny;
            } else {
                double factor = eta * cosI - Math.sqrt(k);
                dx = eta * dx + factor * nx;
                dy = eta * dy + factor * ny;
                inside = !inside;
            }
            double dirLength = Math.hypot(dx, dy);
            dx /= dirLength;
            dy /= dirLength;
        }
        return new Beam(toArray(points), Double.NaN);
    }

    /** True if light landing at {@code exitDegrees} lights the rune at {@code runeDegrees}. */
    public static boolean lights(double exitDegrees, int runeDegrees) {
        return !Double.isNaN(exitDegrees) && angleBetween(exitDegrees, runeDegrees) <= RUNE_TOLERANCE;
    }

    /** Moves between two lamp or gem positions, the short way round. */
    public static int stepsBetween(int a, int b) {
        int d = Math.floorMod(a - b, STEPS);
        return Math.min(d, STEPS - d);
    }

    /**
     * One study: where the lamp and gem start, the runes (in whole degrees on the ring) and par, the fewest
     * moves that light and lock every rune.
     */
    public record Puzzle(int startLamp, int startGem, int[] runes, int par) {
    }

    /**
     * Builds a puzzle with {@code runeCount} runes. Every rune is placed where some lamp and gem position
     * lands the light, so every puzzle can be solved; none is lit at the start.
     */
    public static Puzzle generate(long seed, double[][] outline, double index, int runeCount) {
        Random random = new Random(seed);
        int states = STEPS * STEPS;
        double[] exits = new double[states];
        List<Integer> escaping = new ArrayList<>();
        for (int state = 0; state < states; state++) {
            exits[state] = trace(outline, index, state / STEPS, state % STEPS).exitDegrees();
            if (!Double.isNaN(exits[state])) {
                escaping.add(state);
            }
        }
        Puzzle best = null;
        for (int attempt = 0; attempt < 200 && !escaping.isEmpty(); attempt++) {
            int start = random.nextInt(states);
            List<Integer> shuffled = new ArrayList<>(escaping);
            java.util.Collections.shuffle(shuffled, random);
            int[] runes = new int[runeCount];
            int found = 0;
            for (int state : shuffled) {
                if (found == runeCount) {
                    break;
                }
                // Runes sit on the rim's engraved slots: use the slot this light lands on, if it is close enough.
                int angle = Math.floorMod((int) Math.round(exits[state] / RUNE_SLOT_DEGREES) * RUNE_SLOT_DEGREES, 360);
                if (!lights(exits[state], angle) || lights(exits[start], angle)) {
                    continue;
                }
                boolean clear = true;
                for (int i = 0; i < found; i++) {
                    clear &= angleBetween(runes[i], angle) >= RUNE_SPACING;
                }
                if (clear) {
                    runes[found++] = angle;
                }
            }
            if (found < runeCount) {
                continue;
            }
            Puzzle puzzle = new Puzzle(start / STEPS, start % STEPS, runes, par(exits, start, runes));
            if (best == null || puzzle.par() > best.par()) {
                best = puzzle;
            }
            // Keep a puzzle that needs some gem turning: more than one move a rune at best.
            if (puzzle.par() > runeCount + 1) {
                return puzzle;
            }
        }
        return best != null ? best : new Puzzle(0, 0, new int[0], 0);
    }

    /**
     * Par: the fewest moves (lamp drags and gem steps) from the start that visit, in any order, a position lighting
     * each rune. A search over (runes locked, last position) pairs, small enough for five runes.
     */
    static int par(double[] exits, int start, int[] runes) {
        int count = runes.length;
        int[][] lighting = new int[count][];
        for (int r = 0; r < count; r++) {
            List<Integer> list = new ArrayList<>();
            for (int state = 0; state < exits.length; state++) {
                if (lights(exits[state], runes[r])) {
                    list.add(state);
                }
            }
            lighting[r] = list.stream().mapToInt(Integer::intValue).toArray();
        }
        int full = (1 << count) - 1;
        // cost[mask][rune][i]: fewest moves to lock the runes in mask, ending on lighting[rune][i].
        int[][][] cost = new int[full + 1][count][];
        for (int mask = 0; mask <= full; mask++) {
            for (int r = 0; r < count; r++) {
                cost[mask][r] = new int[lighting[r].length];
                Arrays.fill(cost[mask][r], Integer.MAX_VALUE);
            }
        }
        for (int r = 0; r < count; r++) {
            for (int i = 0; i < lighting[r].length; i++) {
                cost[1 << r][r][i] = distance(start, lighting[r][i]);
            }
        }
        for (int mask = 1; mask <= full; mask++) {
            for (int r = 0; r < count; r++) {
                if ((mask & (1 << r)) == 0) {
                    continue;
                }
                for (int i = 0; i < lighting[r].length; i++) {
                    int here = cost[mask][r][i];
                    if (here == Integer.MAX_VALUE) {
                        continue;
                    }
                    for (int next = 0; next < count; next++) {
                        if ((mask & (1 << next)) != 0) {
                            continue;
                        }
                        int nextMask = mask | (1 << next);
                        for (int j = 0; j < lighting[next].length; j++) {
                            int total = here + distance(lighting[r][i], lighting[next][j]);
                            if (total < cost[nextMask][next][j]) {
                                cost[nextMask][next][j] = total;
                            }
                        }
                    }
                }
            }
        }
        int par = Integer.MAX_VALUE;
        for (int r = 0; r < count; r++) {
            for (int value : cost[full][r]) {
                par = Math.min(par, value);
            }
        }
        return par == Integer.MAX_VALUE ? 0 : par;
    }

    /** Moves from one position to another: one lamp drag if the lamp moves, plus each gem step. */
    private static int distance(int a, int b) {
        return (a / STEPS != b / STEPS ? 1 : 0) + stepsBetween(a % STEPS, b % STEPS);
    }

    private static double angleBetween(double a, double b) {
        double d = Math.abs(normalize(a) - normalize(b));
        return Math.min(d, 360.0 - d);
    }

    private static double normalize(double degrees) {
        double d = degrees % 360.0;
        return d < 0 ? d + 360.0 : d;
    }

    private static double[] toArray(List<Double> values) {
        double[] array = new double[values.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = values.get(i);
        }
        return array;
    }
}
