package net.sevenstars.of_beasts_and_wild_things;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.sevenstars.api.utils.IdentifierUtil;
import net.sevenstars.api.utils.ModLogger;
import net.sevenstars.of_beasts_and_wild_things.block.BlocksWT;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ActivitiesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.MemoryModulesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.SchedulesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.SensorsWT;
import net.sevenstars.of_beasts_and_wild_things.item.EggItemsWT;
import net.sevenstars.of_beasts_and_wild_things.item.ItemGroupsWT;
import net.sevenstars.of_beasts_and_wild_things.item.ItemsWT;
import net.sevenstars.of_beasts_and_wild_things.sound.SoundsWT;
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
		SoundsWT.registerModSounds();
		ItemGroupsWT.register();
		BlocksWT.registerModBlocks();
		ItemsWT.registerModItems();
		EggItemsWT.registerModItems();
		WorldGenerationWT.generateModWorldGen();
	}

    public static Identifier fetchId(String stringId){
        return IdentifierUtil.getIdentifierFromString(stringId);
    }

    public static Identifier of(String path){
        return IdentifierUtil.build(MOD_ID, path);
    }

    public static Identifier of(String... names){
        return IdentifierUtil.buildAggregate(MOD_ID, names);
    }
    public static String createAggregate(char splitter, String... names){
        return IdentifierUtil.createAggregateValue(splitter, names);
    }
}
