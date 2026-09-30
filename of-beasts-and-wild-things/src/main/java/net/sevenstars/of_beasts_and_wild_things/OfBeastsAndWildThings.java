package net.sevenstars.of_beasts_and_wild_things;

import net.sevenstars.api.AbstractModInitializer;
import net.sevenstars.of_beasts_and_wild_things.block.BlocksWT;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ActivitiesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.MemoryModulesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.SchedulesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.SensorsWT;
import net.sevenstars.of_beasts_and_wild_things.item.EggItemsWT;
import net.sevenstars.of_beasts_and_wild_things.item.ItemGroupsWT;
import net.sevenstars.of_beasts_and_wild_things.item.ItemsWT;
import net.sevenstars.of_beasts_and_wild_things.sound.SoundEventWT;
import net.sevenstars.of_beasts_and_wild_things.world.gen.WorldGenerationWT;

public class OfBeastsAndWildThings extends AbstractModInitializer {

	public OfBeastsAndWildThings() {
		super("wild-things", false);
	}

	@Override
	public void onInitialize() {
		registerAll();
	}

	public void registerAll() {
		EntitiesWT.register();
		SchedulesWT.register();
		ActivitiesWT.register();
		SensorsWT.register();
		MemoryModulesWT.register();
		SoundEventWT.register();
		ItemGroupsWT.register();
		BlocksWT.register();
		ItemsWT.register();
		EggItemsWT.register();
		WorldGenerationWT.register();
	}
}
