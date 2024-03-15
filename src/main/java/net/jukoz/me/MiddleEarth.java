package net.jukoz.me;

import net.fabricmc.api.ModInitializer;
import net.jukoz.me.block.*;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.gui.ModScreenHandlers;
import net.jukoz.me.item.*;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.particles.ModParticleTypes;
import net.jukoz.me.statusEffects.ModStatusEffects;
import net.jukoz.me.recipe.ModRecipes;
import net.jukoz.me.sound.ModSounds;
import net.jukoz.me.utils.LootModifiers;
import net.jukoz.me.utils.resources.ImageReader;
import net.jukoz.me.world.datas.MiddleEarthMapDatas;
import net.jukoz.me.world.MiddleEarthMapGeneration;
import net.jukoz.me.world.gen.ModWorldGeneration;
import net.jukoz.me.world.spawners.ModEntitySpawning;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.MEBiomesData;
import net.jukoz.me.world.dimension.ModDimensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MiddleEarth implements ModInitializer {
	public static final String MOD_ID = "me";
	public static final boolean IS_DEBUG = true;

	private static MiddleEarthMapDatas middleEarthMapDatas;

	@Override
	public void onInitialize() {
		new ImageReader(getClass().getClassLoader());

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
		ModBlocks.registerFlammableBlocks();
		ModDecorativeBlocks.registerModBlocks();
		ModDecorativeBlocks.registerFlammableFurniture();
		ModNatureBlocks.registerModBlocks();
		RoofBlockSets.registerModBlockSets();

		ModBlockEntities.registerBlockEntities();

		ModScreenHandlers.registerAllScreenHandlers();
		ModRecipes.registerRecipes();

		ModEntities.registerModEntities();
		ModEntitySpawning.addSpawns();

		ModSounds.registerModSounds();
		ModParticleTypes.registerParticleTypes();

		ModDimensions.register();
		MEBiomeKeys.registerModBiomes();
		MEBiomesData.loadBiomes();
		ModWorldGeneration.generateModWorldGen();

		LootModifiers.modifyLootTables();

		try {
			middleEarthMapDatas = new MiddleEarthMapDatas(getClass().getClassLoader());
			new MiddleEarthMapGeneration();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static MiddleEarthMapDatas GetWorldMapDatas(){
		return middleEarthMapDatas;
	}
}
