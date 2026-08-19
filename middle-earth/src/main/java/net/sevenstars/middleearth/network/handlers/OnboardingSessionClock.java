package net.sevenstars.middleearth.network.handlers;

final class OnboardingSessionClock {
    private static final long NANOS_PER_SECOND = 1_000_000_000L;
    private static final long NANOS_PER_MILLISECOND = 1_000_000L;

    private OnboardingSessionClock() {
    }

    static long afterSeconds(long nowNanos, double seconds) {
        if (!Double.isFinite(seconds) || seconds <= 0.0D) {
            return nowNanos;
        }
        double requestedNanos = Math.ceil(seconds * NANOS_PER_SECOND);
        long delayNanos = requestedNanos >= Long.MAX_VALUE
                ? Long.MAX_VALUE
                : (long) requestedNanos;
        return addSaturated(nowNanos, delayNanos);
    }

    static long addSaturated(long nowNanos, long delayNanos) {
        if (delayNanos <= 0L) {
            return nowNanos;
        }
        if (nowNanos > Long.MAX_VALUE - delayNanos) {
            return Long.MAX_VALUE;
        }
        return nowNanos + delayNanos;
    }

    static boolean reached(long nowNanos, long deadlineNanos) {
        return nowNanos >= deadlineNanos;
    }

    static int remainingMillis(long nowNanos, long deadlineNanos) {
        if (reached(nowNanos, deadlineNanos)) {
            return 0;
        }
        long remainingNanos = deadlineNanos - nowNanos;
        long remainingMillis = remainingNanos / NANOS_PER_MILLISECOND;
        if (remainingNanos % NANOS_PER_MILLISECOND != 0L) {
            remainingMillis++;
        }
        return (int) Math.min(Integer.MAX_VALUE, remainingMillis);
    }
}
