package net.sevenstars.ofhillsanddells;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.sevenstars.api.enums.LangCategory;
import net.sevenstars.api.lang.LangKey;
import net.sevenstars.api.utils.IdentifierUtil;
import net.sevenstars.api.utils.LoggerUtil;

public class OfHillsAndDells implements ModInitializer {
	private static final String MOD_ID = "ofhillsanddells";
	public static final boolean IS_DEBUG = true;
	public static final LoggerUtil LOGGER = new LoggerUtil(MOD_ID, IS_DEBUG);


	@Override
	public void onInitialize() {
		//RegistriesHD.register();
		//DynamicRegistriesHD.register();
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
}
