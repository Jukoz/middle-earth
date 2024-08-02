package net.jukoz.me;

import net.fabricmc.api.ModInitializer;
import net.jukoz.me.block.*;
import net.jukoz.me.config.ModClientConfigs;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.gui.ModScreenHandlers;
import net.jukoz.me.item.*;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.network.ModNetworks;
import net.jukoz.me.particles.ModParticleTypes;
import net.jukoz.me.recipe.ModRecipeSerializer;
import net.jukoz.me.registries.ModRegistries;
import net.jukoz.me.resource.CustomServerDataResourceReloadListener;
import net.jukoz.me.statusEffects.ModStatusEffects;
import net.jukoz.me.recipe.ModRecipes;
import net.jukoz.me.sound.ModSounds;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.utils.LootModifiers;
import net.jukoz.me.utils.commands.ModCommandRegistry;
import net.jukoz.me.utils.resources.FileUtils;
import net.jukoz.me.world.map.MiddleEarthMapGeneration;
import net.jukoz.me.world.gen.ModWorldGeneration;
import net.jukoz.me.world.spawners.ModEntitySpawning;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.surface.MEBiomesData;
import net.jukoz.me.world.dimension.ModDimensions;

public class MiddleEarth implements ModInitializer {
	public static final String MOD_ID = "me";
	public static final String MOD_VERSION = "1.5.0-1.21.0-alpha";
	public static final boolean IS_DEBUG = true;
	@Override
	public void onInitialize() {
		new FileUtils(getClass().getClassLoader());

		LoggerUtil.logInfoMsg("");
		LoggerUtil.logInfoMsg("================ MiddleEarth ================");

		ModServerConfigs.registerConfigs();
		ModClientConfigs.registerConfigs();

		ModDataComponentTypes.registerModComponentTypes();

		ModCommandRegistry.register();
		ModStatusEffects.registerStatusEffects();

		OreRockSets.registerModBlockSets();
		ModWeaponItems.registerModItems();
		ModEquipmentItems.registerModItems();
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
		RoofBlockSets.registerModBlockSets();

		ModRegistries.registerFuels();
		ModRegistries.registerFlammableBlocks();
		ModRegistries.registerAgingCopperBlocks();
		ModRegistries.registerComposterBlocks();
		ModRegistries.registerCauldronBehaviour();

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
		MEBiomeKeys.registerModBiomes();
		MEBiomesData.loadBiomes();
		ModWorldGeneration.generateModWorldGen();

		LootModifiers.modifyLootTables();

		CustomServerDataResourceReloadListener.register();

		ModNetworks.registerC2SPackets();

		try {
			new MiddleEarthMapGeneration();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
