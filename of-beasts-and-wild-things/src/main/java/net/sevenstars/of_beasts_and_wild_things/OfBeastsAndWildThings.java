package net.sevenstars.of_beasts_and_wild_things;

import net.fabricmc.api.ModInitializer;
import net.sevenstars.api.utils.ModLogger;
import net.sevenstars.of_beasts_and_wild_things.entity.ModEntities;

public class OfBeastsAndWildThings implements ModInitializer {
	public static final String MOD_ID = "wild-things";
	public static final String MOD_VERSION = "1.5.3-1.21.4-alpha";
	public static final boolean IS_DEBUG = true;
	public static final ModLogger LOGGER = new ModLogger(MOD_ID, IS_DEBUG);
	@Override
	public void onInitialize() {
		ModEntities.registerModEntities();
	}
}
