package net.jukoz.me.resources.datas.races;

import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.RaceType;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Optional;

public class RaceUtil {

    public static void updateRace(PlayerEntity player, Race race){
        PlayerData data = StateSaverAndLoader.getPlayerState(player);

        boolean havePreviousRace = data.getRace() != null;
        boolean raceExists = race != null;

        if(!ModServerConfigs.ENABLE_RACE_SWAP_ON_DIMENSION_SWAP && !ModDimensions.isInMiddleEarth(player.getWorld()))
            reset(player);

        // [RESET]
        if(havePreviousRace){
            RaceLookup.getRace(player.getWorld(), data.getRace()).reverseAttributes(player);
            data.setRace(null);
        }

        // [SET]
        if(raceExists){
            race.applyAttributes(player);
            data.setRace(race.getId());
        }
    }

    public static Race getRace(PlayerEntity player){
        PlayerData data = StateSaverAndLoader.getPlayerState(player);
        if(data == null) return null;
        return data.getRace(player.getWorld());
    }

    public static RaceType getRaceType(PlayerEntity player){
        Race race = getRace(player);
        if(race != null)
            return race.getRaceType();
        else
            return null;
    }

    public static void initializeRace(ServerPlayerEntity player) {
        Race race = getRace(player);
        if(race == null) return;

        reset(player);
        updateRace(player, race);
    }

    private static final HashMap<Identifier, Double> defaultAttributes = new HashMap<>(){{
        put(Identifier.of("minecraft:generic.armor"), 0.0);
        put(Identifier.of("minecraft:generic.armor_toughness"), 0.0);
        put(Identifier.of("minecraft:generic.attack_damage"), 0.9);
        put(Identifier.of("minecraft:generic.attack_knockback"), 0.0);
        put(Identifier.of("minecraft:generic.attack_speed"), 4.0);
        put(Identifier.of("minecraft:generic.burning_time"), 1.0);
        put(Identifier.of("minecraft:generic.explosion_knockback_resistance"), 0.0);
        put(Identifier.of("minecraft:generic.fall_damage_multiplier"), 0.225);
        put(Identifier.of("minecraft:generic.gravity"), 0.08);
        put(Identifier.of("minecraft:generic.jump_strength"), 0.41999998688697815);
        put(Identifier.of("minecraft:generic.knockback_resistance"), 0.0);
        put(Identifier.of("minecraft:generic.luck"), 0.0);
        put(Identifier.of("minecraft:generic.max_absorption"), 0.0);
        put(Identifier.of("minecraft:generic.max_health"), 20.0);
        put(Identifier.of("minecraft:generic.movement_efficiency"), 0.0);
        put(Identifier.of("minecraft:generic.movement_speed"), 0.10000000149011612);
        put(Identifier.of("minecraft:generic.oxygen_bonus"), 0.0);
        put(Identifier.of("minecraft:generic.oxygen_bonus"), 0.0);
        put(Identifier.of("minecraft:generic.safe_fall_distance"), 3.0);
        put(Identifier.of("minecraft:generic.scale"), 1.0);
        put(Identifier.of("minecraft:generic.step_height"), 0.6);
        put(Identifier.of("minecraft:generic.water_movement_efficiency"), 0.0);

        put(Identifier.of("minecraft:player.block_break_speed"), 1.0);
        put(Identifier.of("minecraft:player.block_interaction_range"), 	4.5);
        put(Identifier.of("minecraft:player.entity_interaction_range"), 3.0);
        put(Identifier.of("minecraft:player.mining_efficiency"), 0.0);
        put(Identifier.of("minecraft:player.sneaking_speed"), 0.3);
        put(Identifier.of("minecraft:player.submerged_mining_speed"), 0.2);
        put(Identifier.of("minecraft:player.sweeping_damage_ratio"), 0.0);
    }};
    public static void reset(LivingEntity entity) {
        final DynamicRegistryManager registryManager = entity.getWorld().getRegistryManager();

        for(Identifier id : defaultAttributes.keySet()){
            EntityAttribute attribute = registryManager.get(RegistryKeys.ATTRIBUTE).get(id);

            Optional<RegistryEntry.Reference<EntityAttribute>> attributeEntry =  Registries.ATTRIBUTE.getEntry(id);
            if(attribute != null && attributeEntry != null && attributeEntry.isPresent()){
                EntityAttributeInstance instance = entity.getAttributes().getCustomInstance(attributeEntry.get());
                if(instance != null){
                    instance.setBaseValue(attribute.getDefaultValue());
                }
            }
        }
        entity.heal(entity.getMaxHealth());
    }
}
