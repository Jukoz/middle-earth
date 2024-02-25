package net.jukoz.me;

import net.fabricmc.api.ModInitializer;
import net.jukoz.me.block.*;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.gui.ModScreenHandlers;
import net.jukoz.me.item.*;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.statusEffects.ModStatusEffects;
import net.jukoz.me.recipe.ModRecipes;
import net.jukoz.me.sound.ModSounds;
import net.jukoz.me.utils.LootModifiers;
import net.jukoz.me.world.datas.MiddleEarthMapDatas;
import net.jukoz.me.world.gen.ModWorldGeneration;
import net.jukoz.me.world.spawners.ModEntitySpawning;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.MEBiomesData;
import net.jukoz.me.world.dimension.ModDimensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MiddleEarth implements ModInitializer {
	public static final String MOD_ID = "me";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	//TODO Vanilla wood walls and fences and slabs and stairs
	//TODO Second vertical slab model for sandstone, pillars, etc
	//TODO Packed Mud stairs and slabs
	//TODO Hobbit Window
	//TODO Wood Chair Tweak UVS ?
	//TODO Buds Loot Tables
	//TODO Wood Stairs and slabs
	//TODO Wheatgrass grayscale
	//TODO Pedestal Block
	//TODO Stone Lectern
	//TODO Tall Shrooms Placement like plant rn no good
	//TODO Lang

	// 1 : 6000 (24,000)
	// 2 : 12 000 (48,000)
	// 3 : 24 000 (96,000)
	// 4 : 48 000 (192,000)
	// 5 : 96 000 (384,000)
	// 6 : 192 000 (768,000)
	// 7 : 384 000 (1,536,000)
	public static final int MAP_ITERATION = 0;
	public static final boolean FORCE_GENERATION = false;
	private static MiddleEarthMapDatas middleEarthMapDatas;
	
	@Override
	public void onInitialize() {
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
		ModBlocks.registerModBlocks();
		ModDecorativeBlocks.registerModBlocks();
		ModNatureBlocks.registerModBlocks();
		RoofBlockSets.registerModBlockSets();

		ModBlockEntities.registerBlockEntities();

		ModScreenHandlers.registerAllScreenHandlers();
		ModRecipes.registerRecipes();

		ModEntities.registerModEntities();
		ModEntitySpawning.addSpawns();

		ModSounds.registerModSounds();

		ModDimensions.register();
		MEBiomeKeys.registerModBiomes();
		MEBiomesData.loadBiomes();
		ModWorldGeneration.generateModWorldGen();

		LootModifiers.modifyLootTables();

		try {
			middleEarthMapDatas = new MiddleEarthMapDatas(getClass().getClassLoader());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static MiddleEarthMapDatas GetWorldMapDatas(){
		return middleEarthMapDatas;
	}
}
