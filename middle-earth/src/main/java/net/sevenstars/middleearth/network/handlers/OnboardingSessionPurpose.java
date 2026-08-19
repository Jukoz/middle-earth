package net.sevenstars.middleearth.network.handlers;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

enum OnboardingSessionPurpose {
    PHIAL_RETURN(TimeUnit.MINUTES.toNanos(2L)),
    SELECTION(TimeUnit.MINUTES.toNanos(30L)),
    CURRENT_SPAWN(TimeUnit.MINUTES.toNanos(30L));

    private final long lifetimeNanos;

    OnboardingSessionPurpose(long lifetimeNanos) {
        this.lifetimeNanos = lifetimeNanos;
    }

    long lifetimeNanos() {
        return lifetimeNanos;
    }

    static long longestLifetime(Collection<OnboardingSessionPurpose> purposes) {
        long lifetimeNanos = 0L;
        for (OnboardingSessionPurpose purpose : purposes) {
            lifetimeNanos = Math.max(lifetimeNanos, purpose.lifetimeNanos());
        }
        return lifetimeNanos;
    }
}
