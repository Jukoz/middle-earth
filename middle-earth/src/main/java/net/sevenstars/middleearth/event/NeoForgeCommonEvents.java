package net.sevenstars.middleearth.event;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.level.ModifyCustomSpawnersEvent;
import net.neoforged.neoforge.event.level.SleepFinishedTimeEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagedEntityData;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.enchantments.EnchantmentsME;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.item.items.weapons.CustomDaggerWeaponItem;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventDataLookup;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.sevenstars.middleearth.world.spawners.ModEntitySpawning;
import net.sevenstars.middleearth.world.spawners.SpawnerNPCs;

public final class NeoForgeCommonEvents {
    private NeoForgeCommonEvents() {
    }

    @EventBusSubscriber(modid = MiddleEarth.NEOFORGE_MOD_ID)
    public static final class ModBus {
        private ModBus() {
        }

        @SubscribeEvent
        public static void addPlayerAttributes(EntityAttributeModificationEvent event) {
            event.add(EntityType.PLAYER, EntityAttributesME.POWDERED_SNOW_IMMUNITY);
            event.add(EntityType.PLAYER, EntityAttributesME.DELVERS_FEAR_STRENGTH);
            event.add(EntityType.PLAYER, EntityAttributesME.CLIMBING_STRENGTH);
            event.add(EntityType.PLAYER, EntityAttributesME.DETECTION_RANGE);
        }
    }

    @EventBusSubscriber(modid = MiddleEarth.NEOFORGE_MOD_ID)
    public static final class GameBus {
        private GameBus() {
        }

        @SubscribeEvent
        public static void checkNaturalSpawn(MobSpawnEvent.SpawnPlacementCheck event) {
            if (event.getSpawnType() != MobSpawnType.NATURAL
                    || !(event.getLevel() instanceof Level level)
                    || !level.dimension().equals(ModDimensions.ME_DIMENSION_KEY)
                    || event.getEntityType() == EntitiesME.NPC) {
                return;
            }

            if (!BiomeEventDataLookup.canEntitySpawn(
                    level,
                    level.getBiome(event.getPos()),
                    event.getPos(),
                    event.getEntityType(),
                    event.getRandom()
            )) {
                event.setResult(MobSpawnEvent.SpawnPlacementCheck.Result.FAIL);
            }
        }

        @SubscribeEvent
        public static void clearStructureManagers(LevelEvent.Unload event) {
            if (event.getLevel() instanceof Level level && !level.isClientSide) {
                StructureManagerService.clear(level);
            }
        }

        @SubscribeEvent
        public static void clearStructureManagers(ServerStoppedEvent event) {
            StructureManagerService.clearAll();
        }

        @SubscribeEvent
        public static void applyPlayerMeleeBonuses(LivingIncomingDamageEvent event) {
            if (event.getEntity().level().isClientSide
                    || !(event.getSource().getDirectEntity() instanceof Player player)) {
                return;
            }

            ItemStack weapon = player.getMainHandItem();
            float amount = event.getAmount();
            if (!weapon.isEmpty()) {
                Holder<Enchantment> firstStrike = player.level()
                        .registryAccess()
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(EnchantmentsME.FIRST_STRIKE);
                if (EnchantmentHelper.getItemEnchantmentLevel(firstStrike, weapon) > 0
                        && event.getEntity().getHealth() / event.getEntity().getMaxHealth() > 0.9F) {
                    amount *= 1.5F;
                }
            }

            if (weapon.getItem() instanceof CustomDaggerWeaponItem) {
                if (CustomDaggerWeaponItem.canBackStab(event.getEntity(), player)) {
                    amount *= 1.75F;
                }
                if (CustomDaggerWeaponItem.canSneakAttack(weapon)) {
                    amount *= 1.5F;
                }
            }

            event.setAmount(amount);
        }

        @SubscribeEvent
        public static void addNpcSpawner(ModifyCustomSpawnersEvent event) {
            if (ModEntitySpawning.hasSpawns()) {
                event.addCustomSpawner(new SpawnerNPCs());
            }
        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void handleManagedEntityDeath(LivingDeathEvent event) {
            if (event.isCanceled()) {
                return;
            }
            if (event.getEntity() instanceof NpcEntity npc
                    && npc.getStructureManagerHostPos() != null) {
                StructureManagerService.signalManagedEntityDeath(
                        npc, npc.getStructureManagerHostPos());
            } else {
                StructureManagerService.signalManagedEntityDeath(event.getEntity());
            }
        }

        @SubscribeEvent
        public static void trackManagedEntityJoin(EntityJoinLevelEvent event) {
            if (!(event.getLevel() instanceof ServerLevel)
                    || !(event.getEntity() instanceof LivingEntity living)) {
                return;
            }
            BlockPos managerPos = getManagedEntityManagerPos(living);
            if (managerPos != null) {
                StructureManagedEntityData.Entry tracked =
                        StructureManagedEntityData.get((ServerLevel) event.getLevel())
                                .get(living.getUUID());
                if (tracked != null && tracked.dead()) {
                    event.setCanceled(true);
                    StructureManagerService.clearBoundManager(living);
                    return;
                }
                StructureManagerService.bindManagedEntity(living, managerPos);
            }
        }

        @SubscribeEvent
        public static void trackManagedEntityLeave(EntityLeaveLevelEvent event) {
            if (!(event.getLevel() instanceof ServerLevel)
                    || !(event.getEntity() instanceof LivingEntity living)) {
                return;
            }
            BlockPos managerPos = getManagedEntityManagerPos(living);
            if (managerPos == null || living.getRemovalReason() == null) {
                return;
            }
            Entity.RemovalReason removalReason = living.getRemovalReason();
            if (removalReason == Entity.RemovalReason.KILLED) {
                return;
            }
            if (removalReason == Entity.RemovalReason.UNLOADED_TO_CHUNK
                    || removalReason == Entity.RemovalReason.UNLOADED_WITH_PLAYER) {
                StructureManagerService.recordManagedEntityPosition(living, managerPos);
            } else {
                StructureManagerService.signalManagedEntityDeath(living, managerPos);
                if (removalReason == Entity.RemovalReason.CHANGED_DIMENSION) {
                    StructureManagerService.clearBoundManager(living);
                }
            }
        }

        private static BlockPos getManagedEntityManagerPos(LivingEntity entity) {
            if (entity instanceof NpcEntity npc
                    && npc.getStructureManagerHostPos() != null) {
                return npc.getStructureManagerHostPos();
            }
            return StructureManagerService.getBoundManagerPos(entity);
        }

        @SubscribeEvent
        public static void advanceOverworldAfterSleep(SleepFinishedTimeEvent event) {
            if (!(event.getLevel() instanceof ServerLevel sleepingLevel)) {
                return;
            }

            ServerLevel overworld = sleepingLevel.getServer().overworld();
            if (sleepingLevel != overworld
                    && overworld.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT)) {
                long currentTimeOfDay = overworld.getDayTime() + 24000L;
                overworld.setDayTime(currentTimeOfDay - currentTimeOfDay % 24000L);
            }
        }

        @SubscribeEvent
        public static void addShovelFlattening(BlockEvent.BlockToolModificationEvent event) {
            if (event.getItemAbility() != ItemAbilities.SHOVEL_FLATTEN) {
                return;
            }

            BlockState state = event.getState();
            if (state.is(ModBlocks.DRY_DIRT)
                    || state.is(ModBlocks.DIRTY_ROOTS)
                    || state.is(ModBlocks.GRASSY_DIRT)
                    || state.is(ModBlocks.TURF)) {
                event.setFinalState(Blocks.DIRT_PATH.defaultBlockState());
            } else if (state.is(ModBlocks.CHALKSOIL_GRASS_BLOCK)
                    || state.is(ModBlocks.CHALKSOIL)
                    || state.is(ModBlocks.GRASSY_CHALKSOIL)
                    || state.is(ModBlocks.COARSE_CHALKSOIL)) {
                event.setFinalState(ModBlocks.CHALKSOIL_PATH.defaultBlockState());
            } else if (state.is(ModBlocks.LOAM_GRASS_BLOCK)
                    || state.is(ModBlocks.LOAM)
                    || state.is(ModBlocks.GRASSY_LOAM)
                    || state.is(ModBlocks.COARSE_LOAM)) {
                event.setFinalState(ModBlocks.LOAM_PATH.defaultBlockState());
            } else if (state.is(ModBlocks.PEAT_GRASS_BLOCK)
                    || state.is(ModBlocks.PEAT)
                    || state.is(ModBlocks.GRASSY_PEAT)
                    || state.is(ModBlocks.COARSE_PEAT)) {
                event.setFinalState(ModBlocks.PEAT_PATH.defaultBlockState());
            } else if (state.is(ModBlocks.SILT_GRASS_BLOCK)
                    || state.is(ModBlocks.SILT)
                    || state.is(ModBlocks.GRASSY_SILT)
                    || state.is(ModBlocks.COARSE_SILT)) {
                event.setFinalState(ModBlocks.SILT_PATH.defaultBlockState());
            }
        }
    }
}
