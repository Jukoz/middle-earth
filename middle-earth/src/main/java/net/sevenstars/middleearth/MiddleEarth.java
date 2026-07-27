package net.sevenstars.middleearth;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.sevenstars.api.utils.ModLogger;
import net.sevenstars.middleearth.block.ModBlockEntityCompatibility;
import net.sevenstars.middleearth.block.registration.*;
import net.sevenstars.middleearth.commands.ModCommands;
import net.sevenstars.middleearth.config.ModClientConfigs;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.enchantments.EnchantmentsME;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.entity.TrackedDataHandlerRegistryME;
import net.sevenstars.middleearth.entity.ai.brain.ActivitiesME;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.ai.brain.SensorsME;
import net.sevenstars.middleearth.event.ModEvents;
import net.sevenstars.middleearth.gui.ModScreenHandlers;
import net.sevenstars.middleearth.item.*;
import net.sevenstars.middleearth.item.utils.ItemGroupsME;
import net.sevenstars.middleearth.item.utils.armor.DyeablePiecesME;
import net.sevenstars.middleearth.network.ModServerNetworkHandler;
import net.sevenstars.middleearth.particles.ModParticleTypes;
import net.sevenstars.middleearth.permissions.PermissionsME;
import net.sevenstars.middleearth.recipe.ModRecipeSerializer;
import net.sevenstars.middleearth.recipe.RecipesME;
import net.sevenstars.middleearth.recipe.inscription.InscriptionWordBank;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.RegistriesME;
import net.sevenstars.middleearth.sound.SoundsME;
import net.sevenstars.middleearth.statusEffects.ModStatusEffects;
import net.sevenstars.api.utils.IdentifierUtil;
import net.sevenstars.middleearth.utils.LootModifiers;
import net.sevenstars.middleearth.world.biomes.MEBiomeKeys;
import net.sevenstars.middleearth.world.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.world.biomes.surface.MapBiomeData;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.sevenstars.middleearth.world.gen.ModWorldGeneration;
import net.sevenstars.middleearth.world.map.MiddleEarthMapGeneration;
import net.sevenstars.middleearth.world.spawners.ModEntitySpawning;

@Mod(MiddleEarth.NEOFORGE_MOD_ID)
public class MiddleEarth {
	public static final String NEOFORGE_MOD_ID = "middle_earth";
	public static final String MOD_ID = "middle-earth";
	public static final String OLD_MOD_ID = "me";
	public static final String MOD_VERSION = "1.0.1-1.21.1-beta";
	public static final boolean IS_DEBUG = false;
	public static final boolean ENABLE_INSTANT_BOOTING = true;
	public static final ModLogger LOGGER = new ModLogger(MOD_ID, IS_DEBUG);

	public MiddleEarth(IEventBus modEventBus) {
		modEventBus.addListener(ModServerNetworkHandler::register);
		modEventBus.addListener((DataPackRegistryEvent.NewRegistry event) -> DynamicRegistriesME.register(event));
		modEventBus.addListener(this::commonSetup);
		modEventBus.addListener(EntitiesME::registerAttributes);
		modEventBus.addListener(EntitiesME::registerSpawnPlacements);
		ModBlockEntityCompatibility.register(modEventBus);
		initialize();
	}

	private void initialize() {
		LOGGER.logInfoMsg("");
		LOGGER.logInfoMsg("================ MiddleEarth ================");

		ModEvents.register();
		PermissionsME.register();
		RegistriesME.registerPlatformHooks();
		ModServerConfigs.registerConfigs();
		ModClientConfigs.registerConfigs();

		RecipesME.registerRecipes();
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
		EntityAttributesME.register();

		WoodBlockSets.registerModBlockSets();
		StoneBlockSets.registerModBlockSets();
		DecorativeItemsME.registerModItems();
		NatureBlockItemsME.registerModItems();
		ModBlocks.registerModBlocks();
		ModDecorativeBlocks.registerModBlocks();
		ModNatureBlocks.registerModBlocks();
		GenericBlockSets.registerModBlockSets();

		EnchantmentsME.registerModEnchantmentEffects();

		ModBlockEntities.registerBlockEntities();

		ModScreenHandlers.registerAllScreenHandlers();
		ModRecipeSerializer.registerRecipeSerializers();

		TrackedDataHandlerRegistryME.register();



		EntitiesME.registerModEntities();
		ModEntitySpawning.addSpawns();

		// Entity AI
		SensorsME.registerModSensors();
		ActivitiesME.registerModActivities();
		MemoryModulesME.registerModMemoryModules();

		SoundsME.registerModSounds();
		ModParticleTypes.registerParticleTypes();

		ModDimensions.register();
		MapBiomeData.loadBiomes();
		MEBiomeKeys.registerModBiomes();
		MapBasedBiomePool.loadBiomes();

		ModWorldGeneration.generateModWorldGen();
		LootModifiers.modifyLootTables();
		InscriptionWordBank.addWordsToBank();

		try {
			new MiddleEarthMapGeneration();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			RegistriesME.registerFuels();
			RegistriesME.registerToolTipAppenders();
			RegistriesME.registerFlammableBlocks();
			RegistriesME.registerTillableBlocks();
			RegistriesME.registerAgingCopperBlocks();
			RegistriesME.registerComposterBlocks();
			RegistriesME.registerCauldronBehaviour();
			RegistriesME.registerLandPathNodeTypesBlocks();
			RegistriesME.registerRegistryAliases();
			ModDecorativeBlocks.registerFlowerPots();
		});
	}

    public static ResourceLocation fetchId(String stringId){
        return IdentifierUtil.getIdentifierFromString(stringId);
    }

    public static ResourceLocation ofPrefix(ResourceLocation base, ResourceLocation prefixId) {
		if(base == null)
			return null;
        return base.withPrefix(String.format("%s/", prefixId.getPath()));
    }

    public static ResourceLocation of(String path){
        return IdentifierUtil.build(MOD_ID, path);
    }



    public static ResourceLocation of(String... names){
        return IdentifierUtil.buildAggregate(MOD_ID, names);
    }

	public static ResourceLocation of(char splitter, String... names){
		return IdentifierUtil.build(MOD_ID, createAggregate(splitter, names));
	}
    public static ResourceLocation ofPath(String... names){
        return IdentifierUtil.build(MOD_ID, createAggregate('/', names));
    }
	public static ResourceLocation ofVanillaPath(String... names){
		return IdentifierUtil.ofVanilla(createAggregate('/', names));
	}
    public static ResourceLocation append(ResourceLocation base, String suffix) {
        String id = base.toString();
        return ResourceLocation.parse(id + suffix);
    }

    public static String createAggregate(char splitter, String... names){
        return IdentifierUtil.createAggregateValue(splitter, names);
    }

	public static boolean compareId(ResourceLocation id1, ResourceLocation id2) {
		return id1.compareTo(id2) == 0;
	}
}
