package net.jesteur.me;

import net.fabricmc.api.ModInitializer;
import net.jesteur.me.block.*;
import net.jesteur.me.entity.ModEntities;
import net.jesteur.me.entity.model.ModEntityModels;
import net.jesteur.me.events.ModEvents;
import net.jesteur.me.item.*;
import net.jesteur.me.item.utils.ModItemGroups;
import net.jesteur.me.statusEffects.ModStatusEffects;
import net.jesteur.me.world.spawners.ModEntitySpawning;
import net.jesteur.me.world.biomes.MEBiomeKeys;
import net.jesteur.me.world.biomes.MEBiomesData;
import net.jesteur.me.world.chunkgen.map.MapImageLoader;
import net.jesteur.me.world.dimension.ModDimensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class MiddleEarth implements ModInitializer {
	public static final String MOD_ID = "me";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

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
		ModBlocks.registerModBlocks();
		ModNatureBlocks.registerModBlocks();

		ModEntities.registerModEntities();
		ModEntitySpawning.addSpawns();

		ModItemGroups.register();

		SimpleBlockSets.registerModBlockSets();
		WoodBlockSets.registerModBlockSets();

		ModDimensions.register();
		MEBiomeKeys.registerModBiomes();
		MEBiomesData.loadBiomes();
		ModEvents.register();

		try {
			MapImageLoader.loadImage(getClass().getClassLoader());
		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
