package net.sevenstars.of_beasts_and_wild_things;

import net.fabricmc.api.ModInitializer;
import net.sevenstars.api.utils.ModLogger;
import net.sevenstars.of_beasts_and_wild_things.block.ModBlocks;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ActivitiesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.MemoryModulesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.SchedulesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.SensorsWT;
import net.sevenstars.of_beasts_and_wild_things.item.EggItemsWT;
import net.sevenstars.of_beasts_and_wild_things.item.ItemGroupsWT;
import net.sevenstars.of_beasts_and_wild_things.item.ItemsWT;
import net.sevenstars.of_beasts_and_wild_things.world.gen.WorldGenerationWT;

public class OfBeastsAndWildThings implements ModInitializer {
	public static final String MOD_ID = "wild-things";
	public static final String MOD_VERSION = "1.0.0-1.21.8-beta-dev";
	public static final boolean IS_DEBUG = true;
	public static final ModLogger LOGGER = new ModLogger(MOD_ID, IS_DEBUG);
	@Override
	public void onInitialize() {
		EntitiesWT.registerModEntities();
		SchedulesWT.registerModSchedules();
		ActivitiesWT.registerModActivities();
		SensorsWT.registerModSensors();
		MemoryModulesWT.registerModMemoryModules();
		ItemGroupsWT.register();
		ModBlocks.registerModBlocks();
		ItemsWT.registerModItems();
		EggItemsWT.registerModItems();
		WorldGenerationWT.generateModWorldGen();
	}
}
