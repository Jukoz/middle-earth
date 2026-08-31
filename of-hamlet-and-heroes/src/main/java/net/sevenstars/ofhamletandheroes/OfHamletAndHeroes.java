package net.sevenstars.ofhamletandheroes;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import net.sevenstars.api.enums.LangCategory;
import net.sevenstars.api.utils.IdentifierUtil;
import net.sevenstars.api.utils.ModLogger;
import net.sevenstars.ofhamletandheroes.registries.DynamicRegistriesHH;
import net.sevenstars.ofhamletandheroes.registries.RegistriesHH;

public class OfHamletAndHeroes implements ModInitializer {
	public static final String MOD_ID = "hamletheroes";
	public static final boolean IS_DEBUG = true;
	public static final ModLogger LOGGER = new ModLogger(MOD_ID, IS_DEBUG);


	@Override
	public void onInitialize() {
		RegistriesHH.register();
		DynamicRegistriesHH.register();
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
	public static String idAggregate(char splitter, String... names){
		return IdentifierUtil.createAggregateValue(splitter, names);
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
