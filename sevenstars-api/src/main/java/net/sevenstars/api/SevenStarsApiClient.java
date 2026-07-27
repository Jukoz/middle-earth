package net.sevenstars.api;

public final class SevenStarsApiClient {
    private SevenStarsApiClient() {
    }

    public static void onInitializeClient() {
        SevenStarsApi.LOGGER.logInfoMsg("Testing the sevenstars api - CLIENT");
    }
}
