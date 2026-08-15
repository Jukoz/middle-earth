package net.sevenstars.middleearth.network.handlers;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OnboardingFlowContractTest {
    private static final Path MAIN_JAVA = Path.of("src/main/java");

    @Test
    void returnScreenDoesNotRetryWithoutAResponse() throws IOException {
        String screen = source(
                "net/sevenstars/middleearth/gui/return_confirmation/ReturnConfirmationScreen.java"
        );

        assertTrue(screen.contains("if (requestPending)"));
        assertTrue(screen.contains("requestPending = true;"));
        assertTrue(screen.contains("requestPending = false;"));
        assertFalse(screen.contains("responseTimeoutTicks"));
        assertFalse(screen.contains("showFailure(\"timeout\")"));
    }

    @Test
    void eachServerOperationRequiresItsOwnSessionPurpose() throws IOException {
        String handler = source(
                "net/sevenstars/middleearth/network/handlers/OnboardingServerHandler.java"
        );
        String selection = section(handler, "public static boolean completeSelection", "public static boolean teleportCurrentSpawn");
        String currentSpawn = section(handler, "public static boolean teleportCurrentSpawn", "public static OnboardingReturnResult returnToOverworld");
        String phialReturn = section(handler, "public static OnboardingReturnResult returnToOverworld", "private static Session validateSession");

        assertTrue(selection.contains("OnboardingSessionPurpose.SELECTION"));
        assertTrue(currentSpawn.contains("OnboardingSessionPurpose.CURRENT_SPAWN"));
        assertTrue(phialReturn.contains("OnboardingSessionPurpose.PHIAL_RETURN"));
        assertTrue(handler.contains("session.supports(expectedPurpose)"));
        assertTrue(selection.contains("reportInvalidSession(player);"));
        assertTrue(currentSpawn.contains("reportInvalidSession(player);"));
    }

    @Test
    void failedTeleportStartsRetryDelayAtFailureCompletion() throws IOException {
        String handler = source(
                "net/sevenstars/middleearth/network/handlers/OnboardingServerHandler.java"
        );
        int teleport = handler.indexOf("teleported = ModDimensions.teleportPlayerToOverworld(player)");
        int failureTimestamp = handler.indexOf("long failedAtNanos = Util.getNanos()", teleport);
        int deferFromFailure = handler.indexOf(
                "session.deferNextAttempt(failedAtNanos, RETURN_RETRY_NANOS)",
                failureTimestamp
        );

        assertTrue(teleport >= 0);
        assertTrue(failureTimestamp > teleport);
        assertTrue(deferFromFailure > failureTimestamp);
    }

    private static String source(String relativePath) throws IOException {
        return Files.readString(MAIN_JAVA.resolve(relativePath));
    }

    private static String section(String source, String startMarker, String endMarker) {
        int start = source.indexOf(startMarker);
        int end = source.indexOf(endMarker, start + startMarker.length());
        assertTrue(start >= 0);
        assertTrue(end > start);
        return source.substring(start, end);
    }
}
