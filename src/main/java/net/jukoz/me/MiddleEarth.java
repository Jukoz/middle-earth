package net.jukoz.me;

import net.fabricmc.api.ModInitializer;
import net.jukoz.me.block.*;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.gui.ModScreenHandlers;
import net.jukoz.me.item.*;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.network.ModNetworks;
import net.jukoz.me.statusEffects.ModStatusEffects;
import net.jukoz.me.recipe.ModRecipes;
import net.jukoz.me.sound.ModSounds;
import net.jukoz.me.world.datas.WorldMapDatas;
import net.jukoz.me.world.spawners.ModEntitySpawning;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.MEBiomesData;
import net.jukoz.me.world.chunkgen.map.MapImageLoader;
import net.jukoz.me.world.dimension.ModDimensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class MiddleEarth implements ModInitializer {
	public static final String MOD_ID = "me";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	// 1 : 6000 (24,000)
	// 2 : 12 000 (48,000)
	// 3 : 24 000 (96,000)
	// 4 : 48 000 (192,000)
	// 5 : 96 000 (384,000)
	// 6 : 192 000 (768,000)
	// 7 : 384 000 (1,536,000)
	public static final int MAP_ITERATION = 4;
	public static final boolean FORCE_GENERATION = false;

	@Override
	public void onInitialize() {
		ModStatusEffects.registerStatusEffects();
		OreRockSets.registerModBlockSets();
		ModWeaponItems.registerModItems();
		ModEquipmentItems.registerModItems();
		ModToolItems.registerModItems();
		ModFoodItems.registerModItems();
		ModRessourceItems.registerModItems();
		ModEggItems.registerModItems();
		ModItemGroups.register();

		ModBlocks.registerModBlocks();
		ModDecorativeBlocks.registerModBlocks();
		ModNatureBlocks.registerModBlocks();
		ModDecorativeItems.registerModItems();
		SimpleBlockSets.registerModBlockSets();
		WoodBlockSets.registerModBlockSets();

		ModBlockEntities.registerBlockEntities();

		ModScreenHandlers.registerAllScreenHandlers();
		ModRecipes.registerRecipes();

		ModEntities.registerModEntities();
		ModEntitySpawning.addSpawns();

		ModSounds.registerModSounds();

		ModDimensions.register();
		MEBiomeKeys.registerModBiomes();
		MEBiomesData.loadBiomes();

		try {
			MapImageLoader.loadImage(getClass().getClassLoader());
		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
