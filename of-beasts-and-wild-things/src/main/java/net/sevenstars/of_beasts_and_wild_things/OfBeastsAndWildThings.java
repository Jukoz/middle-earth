package net.sevenstars.of_beasts_and_wild_things;

import net.fabricmc.api.ModInitializer;
import net.sevenstars.api.utils.ModLogger;
import net.sevenstars.of_beasts_and_wild_things.block.ModBlocks;
import net.sevenstars.of_beasts_and_wild_things.entity.ModEntities;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ModMemoryModules;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ModSchedule;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ModSensors;
import net.sevenstars.of_beasts_and_wild_things.item.ModEggItems;
import net.sevenstars.of_beasts_and_wild_things.item.ModItemGroups;
import net.sevenstars.of_beasts_and_wild_things.item.ModItems;

public class OfBeastsAndWildThings implements ModInitializer {
	public static final String MOD_ID = "wild-things";
	public static final String MOD_VERSION = "1.0.0-1.21.7-beta-dev";
	public static final boolean IS_DEBUG = true;
	public static final ModLogger LOGGER = new ModLogger(MOD_ID, IS_DEBUG);
	@Override
	public void onInitialize() {
		ModEntities.registerModEntities();
		ModSchedule.registerModSchedules();
		ModSensors.registerModSensors();
		ModMemoryModules.registerModMemoryModules();
		ModItemGroups.register();
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModEggItems.registerModItems();
	}
}
