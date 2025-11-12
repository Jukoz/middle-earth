package net.sevenstars.middleearth;

import net.fabricmc.api.ModInitializer;
import net.sevenstars.api.utils.ModLogger;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.commands.ModCommands;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.entity.ModEntityAttributes;
import net.sevenstars.middleearth.entity.ModTrackedDataHandlerRegistry;
import net.sevenstars.middleearth.enchantments.EnchantmentEffectsME;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.entity.ai.brain.ActivitiesME;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.ai.brain.SensorsME;
import net.sevenstars.middleearth.entity.spider.SpiderVariants;
import net.sevenstars.middleearth.event.ModEvents;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.item.*;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;
import net.sevenstars.middleearth.network.ModServerNetworkHandler;
import net.sevenstars.middleearth.network.connections.ConnectionToClient;
import net.sevenstars.middleearth.particles.ModParticleTypes;
import net.sevenstars.middleearth.recipe.ModRecipeSerializer;
import net.sevenstars.middleearth.recipe.ModRecipes;
import net.sevenstars.middleearth.registries.RegistriesME;
import net.sevenstars.middleearth.resources.*;
import net.sevenstars.middleearth.sound.ModSounds;
import net.sevenstars.middleearth.statusEffects.ModStatusEffects;
import net.sevenstars.middleearth.utils.LootModifiers;
import net.sevenstars.middleearth.utils.resources.FileUtils;
import net.sevenstars.middleearth.world.biomes.MEBiomeKeys;
import net.sevenstars.middleearth.world.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.world.biomes.surface.MapBiomeData;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.sevenstars.middleearth.world.gen.ModWorldGeneration;
import net.sevenstars.middleearth.world.map.MiddleEarthMapGeneration;
import net.sevenstars.middleearth.world.spawners.ModEntitySpawning;

public class MiddleEarth implements ModInitializer {
	public static final String MOD_ID = "middle-earth";
	public static final String OLD_MOD_ID = "me";
	public static final String MOD_VERSION = "1.0.0-1.21.8-beta-dev";
	public static final boolean IS_DEBUG = true;
	public static final boolean ENABLE_INSTANT_BOOTING = true;
	public static final ModLogger LOGGER = new ModLogger(MOD_ID, IS_DEBUG);
	@Override
	public void onInitialize() {
		new FileUtils(getClass().getClassLoader());

		LOGGER.logInfoMsg("");
		LOGGER.logInfoMsg("================ MiddleEarth ================");

		ModServerNetworkHandler.register(new ConnectionToClient());
		ModEvents.register();
		ModServerConfigs.registerConfigs();
		ModClientConfigs.registerConfigs();

		ModRecipes.registerRecipes();
		DataComponentTypesME.registerModComponentTypes();

		ModCommands.register();
		ModStatusEffects.registerStatusEffects();

		OreRockSets.registerModBlockSets();
		WeaponItemsME.registerModItems();
		EquipmentItemsME.registerModItems();
		DyeablePiecesME.addDyeablePieces();
		ToolItemsME.registerModItems();
		FoodItemsME.registerModItems();
		ResourceItemsME.registerModItems();
		EggItemsME.registerModItems();
		ItemGroupsME.register();
		ModEntityAttributes.register();

		WoodBlockSets.registerModBlockSets();
		StoneBlockSets.registerModBlockSets();
		DecorativeItemsME.registerModItems();
		NatureBlockItemsME.registerModItems();
		ModBlocks.registerModBlocks();
		ModDecorativeBlocks.registerModBlocks();
		ModNatureBlocks.registerModBlocks();
		GenericBlockSets.registerModBlockSets();

		EnchantmentEffectsME.registerModEnchantmentEffects();

		RegistriesME.registerFuels();
		RegistriesME.registerToolTipAppenders();
		RegistriesME.registerFlammableBlocks();
		RegistriesME.registerTillableBlocks();
		RegistriesME.registerAgingCopperBlocks();
		RegistriesME.registerComposterBlocks();
		RegistriesME.registerCauldronBehaviour();
		RegistriesME.registerLandPathNodeTypesBlocks();

		ModBlockEntities.registerBlockEntities();

		ModScreenHandlers.registerAllScreenHandlers();
		ModRecipeSerializer.registerRecipeSerializers();

		ModTrackedDataHandlerRegistry.register();

		CharacterMaterialsME.register();
		CharacterPatternsME.register();
		StructureManagerDatasME.register();
		BiomeEventsME.register();

		ModEntities.registerModEntities();
		ModEntitySpawning.addSpawns();

		// Entity AI
		SensorsME.registerModSensors();
		ActivitiesME.registerModActivities();
		MemoryModulesME.registerModMemoryModules();

		ModSounds.registerModSounds();
		ModParticleTypes.registerParticleTypes();
		ModStatusEffects.registerStatusEffects();

		ModDimensions.register();
		MapBiomeData.loadBiomes();
		MEBiomeKeys.registerModBiomes();
		MapBasedBiomePool.loadBiomes();

		ModWorldGeneration.generateModWorldGen();
		LootModifiers.modifyLootTables();

		// Dynamic Data
		SpiderVariants.register();
        NpcTextureDatasME.register();
        RacesME.register();
		NpcME.register();
		FactionsME.register();

		RegistriesME.registerRegistryAliases();

		try {
			new MiddleEarthMapGeneration();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
