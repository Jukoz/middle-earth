package net.sevenstars.of_beasts_and_wild_things;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.api.utils.IdentifierUtil;
import net.sevenstars.api.utils.ModLogger;
import net.sevenstars.of_beasts_and_wild_things.block.BlocksWT;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalVariantData;
import net.sevenstars.of_beasts_and_wild_things.compat.farm.FarmAnimalVariants;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ActivitiesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.MemoryModulesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.SchedulesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.SensorsWT;
import net.sevenstars.of_beasts_and_wild_things.item.EggItemsWT;
import net.sevenstars.of_beasts_and_wild_things.item.ItemGroupsWT;
import net.sevenstars.of_beasts_and_wild_things.item.ItemsWT;
import net.sevenstars.of_beasts_and_wild_things.sound.SoundsWT;
import net.sevenstars.of_beasts_and_wild_things.datageneration.DataGeneration;
import net.sevenstars.of_beasts_and_wild_things.world.gen.EntitySpawnsWT;
import net.sevenstars.of_beasts_and_wild_things.world.gen.WorldGenerationWT;

@Mod(OfBeastsAndWildThings.NEOFORGE_MOD_ID)
public class OfBeastsAndWildThings {
	public static final String NEOFORGE_MOD_ID = "wild_things";
	public static final String MOD_ID = "wild-things";
	public static final String MOD_VERSION = "1.0.2-1.21.1-beta-backport.1";
	public static final boolean IS_DEBUG = false;
	public static final ModLogger LOGGER = new ModLogger(MOD_ID, IS_DEBUG);

	public OfBeastsAndWildThings(IEventBus modEventBus, ModContainer modContainer) {
		RegistrationBridge.attach(modEventBus);
		onInitialize();
		modEventBus.addListener(EntitiesWT::registerAttributes);
		modEventBus.addListener(EntitySpawnsWT::registerSpawnPlacements);
		modEventBus.addListener(DataGeneration::gatherData);
		FarmAnimalVariants.register(modEventBus);
		if (FMLEnvironment.dist == Dist.CLIENT) {
			OfBeastsAndWildThingsClient.register(modEventBus);
		}
	}

	public void onInitialize() {
		FarmAnimalVariantData.registerSerializer();
		EntitiesWT.registerModEntities();
		SchedulesWT.registerModSchedules();
		ActivitiesWT.registerModActivities();
		SensorsWT.registerModSensors();
		MemoryModulesWT.registerModMemoryModules();
		SoundsWT.registerModSounds();
		ItemGroupsWT.register();
		BlocksWT.registerModBlocks();
		ItemsWT.registerModItems();
		EggItemsWT.registerModItems();
		WorldGenerationWT.generateModWorldGen();
	}

    public static ResourceLocation fetchId(String stringId){
        return IdentifierUtil.getIdentifierFromString(stringId);
    }

    public static ResourceLocation of(String path){
        return IdentifierUtil.build(MOD_ID, path);
    }

    public static ResourceLocation of(String... names){
        return IdentifierUtil.buildAggregate(MOD_ID, names);
    }
    public static String createAggregate(char splitter, String... names){
        return IdentifierUtil.createAggregateValue(splitter, names);
    }
}
