package net.sevenstars.middleearth;

import net.fabricmc.api.ModInitializer;
import net.sevenstars.api.utils.ModLogger;
import net.sevenstars.middleearth.block.*;
import net.sevenstars.middleearth.commands.ModCommands;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.entity.ModEntities;
import net.sevenstars.middleearth.event.ModEvents;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.item.*;
import net.sevenstars.middleearth.item.utils.ModItemGroups;
import net.sevenstars.middleearth.item.utils.armor.ModDyeablePieces;
import net.sevenstars.middleearth.network.ModServerNetworkHandler;
import net.sevenstars.middleearth.network.connections.ConnectionToClient;
import net.sevenstars.middleearth.particles.ModParticleTypes;
import net.sevenstars.middleearth.recipe.ModRecipeSerializer;
import net.sevenstars.middleearth.recipe.ModRecipes;
import net.sevenstars.middleearth.registries.ModRegistries;
import net.sevenstars.middleearth.resources.MiddleEarthFactions;
import net.sevenstars.middleearth.resources.MiddleEarthNpcs;
import net.sevenstars.middleearth.resources.MiddleEarthRaces;
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
	public static final String MOD_ID = "me";
	public static final String MOD_VERSION = "1.5.1-1.21.1-alpha";
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

		ModDataComponentTypes.registerModComponentTypes();

		ModCommands.register();
		ModStatusEffects.registerStatusEffects();

		OreRockSets.registerModBlockSets();
		ModWeaponItems.registerModItems();
		ModEquipmentItems.registerModItems();
		ModDyeablePieces.addDyeablePieces();
		ModToolItems.registerModItems();
		ModFoodItems.registerModItems();
		ModResourceItems.registerModItems();
		ModEggItems.registerModItems();
		ModItemGroups.register();

		WoodBlockSets.registerModBlockSets();
		MushroomBlockSets.registerModBlockSets();
		StoneBlockSets.registerModBlockSets();
		ModDecorativeItems.registerModItems();
		ModNatureBlockItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModDecorativeBlocks.registerModBlocks();
		ModNatureBlocks.registerModBlocks();
		OtherBlockSets.registerModBlockSets();

		ModRegistries.registerFuels();
		ModRegistries.registerFlammableBlocks();
		ModRegistries.registerTillableBlocks();
		ModRegistries.registerAgingCopperBlocks();
		ModRegistries.registerComposterBlocks();
		ModRegistries.registerCauldronBehaviour();
		ModRegistries.registerLandPathNodeTypesBlocks();

		ModBlockEntities.registerBlockEntities();

		ModScreenHandlers.registerAllScreenHandlers();
		ModRecipes.registerRecipes();
		ModRecipeSerializer.registerRecipeSerializers();

		ModEntities.registerModEntities();
		ModEntitySpawning.addSpawns();

		ModSounds.registerModSounds();
		ModParticleTypes.registerParticleTypes();
		ModStatusEffects.registerStatusEffects();

		ModDimensions.register();
		MapBiomeData.loadBiomes();
		MEBiomeKeys.registerModBiomes();
		MapBasedBiomePool.loadBiomes();

		ModWorldGeneration.generateModWorldGen();
		LootModifiers.modifyLootTables();

		MiddleEarthRaces.register();
		MiddleEarthNpcs.register();
		MiddleEarthFactions.register();

		try {
			new MiddleEarthMapGeneration();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
