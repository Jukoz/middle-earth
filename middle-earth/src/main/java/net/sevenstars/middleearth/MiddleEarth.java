package net.sevenstars.middleearth;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.util.Identifier;
import net.sevenstars.api.enums.LangCategory;
import net.sevenstars.api.utils.LoggerUtil;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.commands.CommandRegistryME;
import net.sevenstars.middleearth.config.ServerConfigME;
import net.sevenstars.middleearth.enchantments.EnchantmentsME;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.entity.TrackedDataHandlerRegistryME;
import net.sevenstars.middleearth.entity.ai.brain.ActivitiesME;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.ai.brain.SensorsME;
import net.sevenstars.middleearth.event.EventRegistryME;
import net.sevenstars.middleearth.gui.ScreenHandlerRegistryME;
import net.sevenstars.middleearth.item.*;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;
import net.sevenstars.middleearth.network.ServerNetworkHandlerME;
import net.sevenstars.api.network.connections.ConnectionToClient;
import net.sevenstars.middleearth.particles.ParticleTypeRegistryME;
import net.sevenstars.middleearth.recipe.RecipeSerializerRegistryME;
import net.sevenstars.middleearth.recipe.RecipesME;
import net.sevenstars.middleearth.recipe.inscription.InscriptionWordBank;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.RegistriesME;
import net.sevenstars.middleearth.sound.SoundsME;
import net.sevenstars.middleearth.statusEffects.StatusEffectRegistryME;
import net.sevenstars.api.utils.IdentifierUtil;
import net.sevenstars.middleearth.utils.LootModifiers;
import net.sevenstars.middleearth.utils.resources.FileUtils;
import net.sevenstars.middleearth.world.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.world.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.world.biomes.surface.MapBiomeData;
import net.sevenstars.middleearth.world.dimension.DimensionRegistryME;
import net.sevenstars.middleearth.world.gen.CustomWorldGeneration;
import net.sevenstars.middleearth.world.map.MiddleEarthMapGeneration;
import net.sevenstars.middleearth.world.spawners.EntitySpawningME;

public class MiddleEarth implements ModInitializer {
	// [TODO] : Make the MOD_ID and OLD_MOD_ID Private, using dependencies and config files would be best.
	private static final String MOD_ID = "middle-earth";
	private static final String OLD_MOD_ID = "me";
	private static final String MOD_VERSION = "1.0.0-1.21.8-beta";
	public static final boolean IS_DEBUG = true;
	public static final boolean ENABLE_INSTANT_BOOTING = true;
	public static final LoggerUtil LOGGER = new LoggerUtil(getModId(), IS_DEBUG);

    @Override
	public void onInitialize() {
		new FileUtils(getClass().getClassLoader());

		LOGGER.logInfoMsg("");
		LOGGER.logInfoMsg("================ MiddleEarth ================");

		ServerNetworkHandlerME.register(new ConnectionToClient());
		EventRegistryME.register();

		// register on server start
		// *** FABRIC EVENT ***
		ServerLifecycleEvents.SERVER_STARTING.register(
				server -> ServerConfigME.registerConfigs()
		);

		RecipesME.registerRecipes();
		DataComponentTypesME.registerModComponentTypes();

		CommandRegistryME.register();
		StatusEffectRegistryME.registerStatusEffects();

		OreStoneSetRegistryME.registerModBlockSets();
		WeaponItemsME.registerModItems();
		EquipmentItemsME.registerModItems();
		DyeablePiecesME.addDyeablePieces();
		ToolItemsME.registerModItems();
		FoodItemsME.registerModItems();
		ResourceItemsME.registerModItems();
		EggItemsME.registerModItems();
		ItemGroupsME.register();
		EntityAttributesME.register();

		WoodBlockSetRegistryME.registerModBlockSets();
		StoneBlockSetRegistryME.registerModBlockSets();
		DecorativeItemsME.registerModItems();
		NatureBlockItemsME.registerModItems();
		BlockRegistryME.registerModBlocks();
		DecorativeBlockRegistryME.registerModBlocks();
		NatureBlockRegistryME.registerModBlocks();
		GenericBlockSetRegistryME.registerModBlockSets();

		EnchantmentsME.registerModEnchantmentEffects();

		RegistriesME.registerFuels();
		RegistriesME.registerToolTipAppenders();
		RegistriesME.registerFlammableBlocks();
		RegistriesME.registerTillableBlocks();
		RegistriesME.registerAgingCopperBlocks();
		RegistriesME.registerComposterBlocks();
		RegistriesME.registerCauldronBehaviour();
		RegistriesME.registerLandPathNodeTypesBlocks();

		BlockEntityRegistryME.registerBlockEntities();

		ScreenHandlerRegistryME.registerAllScreenHandlers();
		RecipeSerializerRegistryME.registerRecipeSerializers();

		TrackedDataHandlerRegistryME.register();



		EntitiesME.registerModEntities();
		EntitySpawningME.addSpawns();

		// Entity AI
		SensorsME.registerModSensors();
		ActivitiesME.registerModActivities();
		MemoryModulesME.registerModMemoryModules();

		SoundsME.registerModSounds();
		ParticleTypeRegistryME.registerParticleTypes();
		StatusEffectRegistryME.registerStatusEffects();

		DimensionRegistryME.register();
		MapBiomeData.loadBiomes();
		BiomeKeyRegistryME.registerModBiomes();
		MapBasedBiomePool.loadBiomes();

		CustomWorldGeneration.generateModWorldGen();
		LootModifiers.modifyLootTables();
		InscriptionWordBank.addWordsToBank();

		// Dynamic Data
        DynamicRegistriesME.register();
		RegistriesME.registerRegistryAliases();

		try {
			new MiddleEarthMapGeneration();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Getter & Setter
	public static String getModId() {
		return MiddleEarth.MOD_ID;
	}

	public static String getOldModId() {
		return MiddleEarth.OLD_MOD_ID;
	}
	public static String getModVersion() {
		return MiddleEarth.MOD_VERSION;
	}

	// Logger
	public static void logRegistryMsg(String registry) {
		LOGGER.logDebugMsg("Registering Mod " +  registry + " for " + getModId());
	}

	// Identifiers
	public static Identifier id(String namespace, String path) {
		return IdentifierUtil.build(namespace, path);
	}

	/**
	 * MiddleEarth.id(path) = MiddleEarth.id(path)
	 */
	public static Identifier id(String path){
		return id(getModId(), path);
	}

	public static Identifier idOld(String path){
		return id(getOldModId(), path);
	}

	public static Identifier idFilePath(String... names){
		return IdentifierUtil.build(getModId(), stringAggregate('/', names));
	}

	public static Identifier idVanilla(String... names){
		return IdentifierUtil.ofVanilla(stringAggregate('/', names));
	}

	public static Identifier idAggregate(String... names){
		return IdentifierUtil.buildAggregate(getModId(), names);
	}

	public static String stringAggregate(char delimiter, String... names){
		return IdentifierUtil.createAggregateValue(delimiter, names);
	}

	public static Identifier idAggregate(char delimiter, String... names){
		return IdentifierUtil.build(getModId(), IdentifierUtil.createAggregateValue(delimiter, names));
	}

	public static Identifier ofId(String stringId){
		return IdentifierUtil.getIdentifierFromString(stringId);
	}

	public static Identifier appendSuffix(Identifier base, String suffix) {
		String id = base.toString();
		return Identifier.of(id + suffix);
	}

	public static Identifier appendPrefix(Identifier base, Identifier prefixId) {
		if(base == null)
			return null;
		return base.withPrefixedPath(String.format("%s/", prefixId.getPath()));
	}

	// Translation Keys
	// for Identifier value
	public static String rawTranslationKey(String prefix, Identifier value){
		return value.toTranslationKey(prefix);
	}

	public static String rawTranslationKey(LangCategory category, Identifier value){
		return rawTranslationKey(category.Prefix, value);
	}

	// for String value
	public static String rawTranslationKey(String prefix, String value){
        if ("".equals(value)) {
            return prefix;
        }
        return prefix + "." + value;
    }
	public static String rawTranslationKeyWithModId(String prefix, String value){
		return rawTranslationKey(prefix + "." + getModId(), value);
	}

	public static String rawTranslationKey(LangCategory category, String value){
		return rawTranslationKey(category.Prefix, value);
	}

	/**
	 * <br/>Most common use,
	 * <br/>to replace the string inside the Text.translatable()
	 * <br/><br/>
	 * <br/>Example:
	 * <br/>		Text.translatable("some_category." + MOD_ID + ".something_else")
	 * <br/>	    ->
	 * <br/>	    Text.translatable(
	 * <br/>	    	MiddleEarth.rawTranslationKeyWithModId(LangCategory.SOME, "something_else")
	 * <br/>	    )
	 */
	public static String rawTranslationKeyWithModId(LangCategory category, String value){
		return rawTranslationKey(category.Prefix + "." + getModId(), value);
	}
}
