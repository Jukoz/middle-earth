package net.sevenstars.of_beasts_and_wild_things;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.sevenstars.api.enums.LangCategory;
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
import net.sevenstars.of_beasts_and_wild_things.sound.SoundEventWT;
import net.sevenstars.of_beasts_and_wild_things.world.gen.WorldGenerationWT;

public class OfBeastsAndWildThings implements ModInitializer {
	public static final String MOD_ID = "wild-things";
	public static final boolean IS_DEBUG = true;
	public static final ModLogger LOGGER = new ModLogger(MOD_ID, IS_DEBUG);
	@Override
	public void onInitialize() {
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

	// Logger
	public static void logRegistryMsg(String registry) {
		LOGGER.logDebugMsg("Registering Mod " +  registry + " for " + MOD_ID);
	}
	// Identifiers
	public static Identifier id(String path){
		return IdentifierUtil.build(MOD_ID, path);
	}
	public static Identifier idAggregate(String... names){
		return IdentifierUtil.buildAggregate(MOD_ID, names);
	}
	public static String idAggregate(char delimiter, String... names){
		return IdentifierUtil.createAggregateValue(delimiter, names);
	}
	public static Identifier ofId(String stringId){
		return IdentifierUtil.getIdentifierFromString(stringId);
	}
	// Translation Keys
	public static String translationKey(LangCategory category, String value){
		return id(value).toTranslationKey(category.Prefix);
	}
	public static String translationKey(LangCategory category, Identifier value){
		return value.toTranslationKey(category.Prefix);
	}
}
