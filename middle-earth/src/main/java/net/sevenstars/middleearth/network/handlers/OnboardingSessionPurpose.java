package net.sevenstars.middleearth.network.handlers;

import java.util.concurrent.TimeUnit;

enum OnboardingSessionPurpose {
    PHIAL_RETURN(true, true, false, TimeUnit.MINUTES.toNanos(2L)),
    PHIAL_ENTRY(true, false, true, TimeUnit.MINUTES.toNanos(30L)),
    FORCED_ENTRY(false, false, true, TimeUnit.MINUTES.toNanos(30L));

    private final boolean requiresPhial;
    private final boolean allowsReturn;
    private final boolean allowsEntry;
    private final long lifetimeNanos;

    OnboardingSessionPurpose(
            boolean requiresPhial,
            boolean allowsReturn,
            boolean allowsEntry,
            long lifetimeNanos
    ) {
        this.requiresPhial = requiresPhial;
        this.allowsReturn = allowsReturn;
        this.allowsEntry = allowsEntry;
        this.lifetimeNanos = lifetimeNanos;
    }

    boolean requiresPhial() {
        return requiresPhial;
    }

    boolean allowsReturn() {
        return allowsReturn;
    }

    boolean allowsEntry() {
        return allowsEntry;
    }

    long lifetimeNanos() {
        return lifetimeNanos;
    }
}
