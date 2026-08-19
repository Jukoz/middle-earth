package net.sevenstars.middleearth.utils;

public final class PlayerActionHandlingContext {
    private static final ThreadLocal<Integer> DEPTH = new ThreadLocal<>();

    private PlayerActionHandlingContext() {
    }

    public static void enter() {
        Integer depth = DEPTH.get();
        DEPTH.set(depth == null ? 1 : depth + 1);
    }

    public static void exit() {
        Integer depth = DEPTH.get();
        if (depth == null || depth <= 1) {
            DEPTH.remove();
        } else {
            DEPTH.set(depth - 1);
        }
    }

    public static boolean isActive() {
        return DEPTH.get() != null;
    }
}
