package net.sevenstars.api;

import net.fabricmc.api.ClientModInitializer;
import net.sevenstars.api.utils.ModLogger;

public class SevenStarsApiClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        SevenStarsApi.LOGGER.logInfoMsg("Testing the sevenstars api - CLIENT");
    }

}
