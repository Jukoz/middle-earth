package net.sevenstars.api;

import net.fabricmc.api.ModInitializer;
import net.sevenstars.api.utils.ModLogger;

public class SevenStarsApi implements ModInitializer {
	public static final String MOD_ID = "sevenstars-api";
	public static final String MOD_VERSION = "1.5.1-1.21.1-alpha";
	public static final boolean IS_DEBUG = true;

	@Override
	public void onInitialize() {
		ModLogger.logInfoMsg("Testing the sevenstars api");
	}
}
