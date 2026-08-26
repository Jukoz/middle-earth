package net.sevenstars.middleearth;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.sevenstars.api.utils.ModLogger;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.commands.CommandRegistryME;
import net.sevenstars.middleearth.config.ClientConfigME;
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
import net.sevenstars.middleearth.recipe.ModRecipeSerializer;
import net.sevenstars.middleearth.recipe.RecipesME;
import net.sevenstars.middleearth.recipe.inscription.InscriptionWordBank;
import net.sevenstars.middleearth.registries.AtlasesME;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.RegistriesME;
import net.sevenstars.middleearth.sound.SoundsME;
import net.sevenstars.middleearth.statusEffects.ModStatusEffects;
import net.sevenstars.api.utils.IdentifierUtil;
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
	public static final String MOD_VERSION = "1.0.0-1.21.8-beta";
	public static final boolean IS_DEBUG = true;
	public static final boolean ENABLE_INSTANT_BOOTING = true;
	public static final ModLogger LOGGER = new ModLogger(MOD_ID, IS_DEBUG);

    @Override
	public void onInitialize() {
		new FileUtils(getClass().getClassLoader());

		LOGGER.logInfoMsg("");
		LOGGER.logInfoMsg("================ MiddleEarth ================");

		ServerNetworkHandlerME.register(new ConnectionToClient());
		EventRegistryME.register();
		ServerConfigME.registerConfigs();
		ClientConfigME.registerConfigs();

		AtlasesME.registerAtlas();
		RecipesME.registerRecipes();
		DataComponentTypesME.registerModComponentTypes();

		CommandRegistryME.register();
		ModStatusEffects.registerStatusEffects();

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
		ModRecipeSerializer.registerRecipeSerializers();

		TrackedDataHandlerRegistryME.register();



		EntitiesME.registerModEntities();
		ModEntitySpawning.addSpawns();

		// Entity AI
		SensorsME.registerModSensors();
		ActivitiesME.registerModActivities();
		MemoryModulesME.registerModMemoryModules();

		SoundsME.registerModSounds();
		ParticleTypeRegistryME.registerParticleTypes();
		ModStatusEffects.registerStatusEffects();

		ModDimensions.register();
		MapBiomeData.loadBiomes();
		MEBiomeKeys.registerModBiomes();
		MapBasedBiomePool.loadBiomes();

		ModWorldGeneration.generateModWorldGen();
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

    public static Identifier fetchId(String stringId){
        return IdentifierUtil.getIdentifierFromString(stringId);
    }

    public static Identifier ofPrefix(Identifier base, Identifier prefixId) {
		if(base == null)
			return null;
        return base.withPrefixedPath(String.format("%s/", prefixId.getPath()));
    }

    public static Identifier of(String path){
        return IdentifierUtil.build(MOD_ID, path);
    }



    public static Identifier of(String... names){
        return IdentifierUtil.buildAggregate(MOD_ID, names);
    }

	public static Identifier of(char splitter, String... names){
		return IdentifierUtil.build(MOD_ID, createAggregate(splitter, names));
	}
    public static Identifier ofPath(String... names){
        return IdentifierUtil.build(MOD_ID, createAggregate('/', names));
    }
	public static Identifier ofVanillaPath(String... names){
		return IdentifierUtil.ofVanilla(createAggregate('/', names));
	}
    public static Identifier append(Identifier base, String suffix) {
        String id = base.toString();
        return Identifier.of(id + suffix);
    }

    public static String createAggregate(char splitter, String... names){
        return IdentifierUtil.createAggregateValue(splitter, names);
    }

	public static boolean compareId(Identifier id1, Identifier id2) {
		return id1.compareTo(id2) == 0;
	}
}
