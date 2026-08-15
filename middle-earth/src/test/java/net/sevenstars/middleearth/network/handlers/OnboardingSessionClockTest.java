package net.sevenstars.middleearth.network.handlers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OnboardingSessionClockTest {
    @Test
    void elapsedDeadlineDoesNotDependOnWorldTicks() {
        long startedAt = 10_000_000_000L;
        long deadline = OnboardingSessionClock.afterSeconds(startedAt, 3.0D);

        assertFalse(OnboardingSessionClock.reached(startedAt + 2_999_999_999L, deadline));
        assertTrue(OnboardingSessionClock.reached(startedAt + 3_000_000_000L, deadline));
    }

    @Test
    void remainingMillisRoundsUpWithoutAllowingAnEarlyClick() {
        long deadline = 5_000_000L;

        assertEquals(1, OnboardingSessionClock.remainingMillis(4_000_001L, deadline));
        assertEquals(0, OnboardingSessionClock.remainingMillis(deadline, deadline));
    }

    @Test
    void deadlinesSaturateInsteadOfOverflowing() {
        assertEquals(
                Long.MAX_VALUE,
                OnboardingSessionClock.addSaturated(Long.MAX_VALUE - 10L, 20L)
        );
    }

    @Test
    void resultCodesAreStableAndUnknownCodesFailClosed() {
        for (OnboardingReturnResult.Status status : OnboardingReturnResult.Status.values()) {
            assertEquals(status, OnboardingReturnResult.Status.fromCode(status.code()));
        }
        assertEquals(
                OnboardingReturnResult.Status.INTERNAL_ERROR,
                OnboardingReturnResult.Status.fromCode(Integer.MAX_VALUE)
        );
    }

    @Test
    void sessionPurposesIsolateReturnAndEntryOperations() {
        assertTrue(OnboardingSessionPurpose.PHIAL_RETURN.allowsReturn());
        assertFalse(OnboardingSessionPurpose.PHIAL_RETURN.allowsEntry());
        assertTrue(OnboardingSessionPurpose.PHIAL_RETURN.requiresPhial());

        assertFalse(OnboardingSessionPurpose.PHIAL_ENTRY.allowsReturn());
        assertTrue(OnboardingSessionPurpose.PHIAL_ENTRY.allowsEntry());
        assertTrue(OnboardingSessionPurpose.PHIAL_ENTRY.requiresPhial());

        assertFalse(OnboardingSessionPurpose.FORCED_ENTRY.allowsReturn());
        assertTrue(OnboardingSessionPurpose.FORCED_ENTRY.allowsEntry());
        assertFalse(OnboardingSessionPurpose.FORCED_ENTRY.requiresPhial());
        assertTrue(
                OnboardingSessionPurpose.PHIAL_ENTRY.lifetimeNanos()
                        > OnboardingSessionPurpose.PHIAL_RETURN.lifetimeNanos()
        );
    }
}
