package net.sevenstars.middleearth.block.special.structureManager.features;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.initializer.NpcEntityBuilder;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerDataLookup;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

public class StructureManagerService {
    private static final String MANAGER_POS_TAG =
            MiddleEarth.NEOFORGE_MOD_ID + ":structure_manager_pos";
    private static final Map<Level, Set<BlockPos>> STRUCTURE_MANAGERS = new WeakHashMap<>();

    public static StructureManagerData getStructureManagerData(Level world, ResourceLocation structureManagerDataId){
        if(world == null)
            return null;
        var data =  StructureManagerDataLookup.getStructureManagerData(world, structureManagerDataId);
        return data.orElse(null);
    }

    public static LivingEntity spawnEntity(
            ServerLevel world,
            StructureSpawnNestPool pool,
            BlockPos pos,
            int spawnRadius,
            BlockPos managerPos
    ) {
        var random = world.getRandom();
        EntityType<?> entityType = pool.getEntityType();
        Entity entity;
        if(entityType == EntitiesME.NPC){
            ResourceLocation npcIdentifier = pool.getNpcIdentifier().orElse(null);
            if (npcIdentifier == null) {
                return null;
            }
            entity = new NpcEntityBuilder(world, null)
                    .withNpcType(npcIdentifier)
                    .build();
        } else {
            entity = entityType.create(world);
        }

        if (!(entity instanceof LivingEntity livingEntity) || entity.isRemoved()) {
            return null;
        }

        int radius = Math.max(0, spawnRadius);
        Iterable<BlockPos> candidates = radius == 0
                ? List.of(pos)
                : BlockPos.randomInCube(random, 16, pos, radius);
        for (BlockPos randomPos : candidates) {
            BlockPos candidate = randomPos.atY(pos.getY());
            if (!world.isInWorldBounds(candidate)
                    || !world.getWorldBorder().isWithinBounds(candidate)
                    || (SpawnPlacements.hasPlacement(entityType)
                    && !SpawnPlacements.isSpawnPositionOk(entityType, world, candidate))) {
                continue;
            }

            entity.setPos(candidate.getCenter());
            if (!world.noCollision(entity) || !world.isUnobstructed(entity)) {
                continue;
            }
            if (entity instanceof Mob mob && !mob.checkSpawnObstruction(world)) {
                continue;
            }

            if (entity instanceof Mob mob) {
                mob.finalizeSpawn(
                        world,
                        world.getCurrentDifficultyAt(candidate),
                        MobSpawnType.STRUCTURE,
                        null
                );
                mob.setPersistenceRequired();
            }
            bindManagedEntity(livingEntity, managerPos);
            if (world.addFreshEntity(entity)) {
                return livingEntity;
            }
            forgetManagedEntity(world, livingEntity.getUUID());
            clearBoundManager(livingEntity);
            return null;
        }
        return null;
    }

    public static void register(StructureManagerBlockEntity manager) {
        Level world = manager.getLevel();
        if (world == null || world.isClientSide() || !manager.isOperational())
            return;
        STRUCTURE_MANAGERS.computeIfAbsent(world, key -> new HashSet<>())
                          .add(manager.getBlockPos().immutable());
        applyRecordedDeaths(manager);
    }

    public static void unregister(StructureManagerBlockEntity manager) {
        Level world = manager.getLevel();
        if (world == null || world.isClientSide())
            return;
        Set<BlockPos> managers = STRUCTURE_MANAGERS.get(world);
        if (managers == null)
            return;

        managers.remove(manager.getBlockPos());
        if (managers.isEmpty())
            STRUCTURE_MANAGERS.remove(world);
    }

    public static boolean isClose(Level world, BlockPos pos, int radius) {
        Set<BlockPos> managers = STRUCTURE_MANAGERS.get(world);

        if (managers == null) {
            return false;
        }

        int radiusSquared = radius * radius;

        Iterator<BlockPos> iterator = managers.iterator();
        while (iterator.hasNext()) {
            BlockPos managerPos = iterator.next();
            BlockEntity blockEntity = world.getBlockEntity(managerPos);
            if (!(blockEntity instanceof StructureManagerBlockEntity manager)
                    || !manager.isOperational()) {
                iterator.remove();
                continue;
            }
            if (managerPos.distSqr(pos) <= radiusSquared) {
                return true;
            }
        }
        if (managers.isEmpty()) {
            STRUCTURE_MANAGERS.remove(world);
        }

        return false;
    }

    public static StructureManagerBlockEntity getClosest(
            Level world, BlockPos pos, int radius, ResourceLocation managerId
    ) {
        Set<BlockPos> managers = STRUCTURE_MANAGERS.get(world);

        if (managers == null) {
            return null;
        }

        int radiusSquared = radius * radius;
        double closestDistance = Double.MAX_VALUE;
        StructureManagerBlockEntity closest = null;

        Iterator<BlockPos> iterator = managers.iterator();
        while (iterator.hasNext()) {
            BlockPos managerPos = iterator.next();
            double distance = managerPos.distSqr(pos);

            if (distance > radiusSquared || distance >= closestDistance) {
                continue;
            }

            BlockEntity blockEntity = world.getBlockEntity(managerPos);

            if (!(blockEntity instanceof StructureManagerBlockEntity structureManager)
                    || !structureManager.isOperational()) {
                iterator.remove();
                continue;
            }
            if (structureManager.acceptsNest(managerId)) {
                closestDistance = distance;
                closest = structureManager;
            }
        }
        if (managers.isEmpty()) {
            STRUCTURE_MANAGERS.remove(world);
        }

        return closest;
    }

    public static void bindManagedEntity(LivingEntity entity, BlockPos managerPos) {
        entity.getPersistentData().putLong(MANAGER_POS_TAG, managerPos.asLong());
        if (entity instanceof NpcEntity npc) {
            npc.assignStructureManager(managerPos);
        }
        if (entity instanceof Mob mob) {
            mob.setPersistenceRequired();
        }
        recordManagedEntityPosition(entity, managerPos);
    }

    public static void recordManagedEntityPosition(
            LivingEntity entity, BlockPos managerPos
    ) {
        if (entity.level() instanceof ServerLevel world) {
            StructureManagedEntityData.get(world).track(
                    entity.getUUID(), managerPos, entity.blockPosition());
        }
    }

    @Nullable
    public static BlockPos getBoundManagerPos(LivingEntity entity) {
        if (!entity.getPersistentData().contains(MANAGER_POS_TAG)) {
            return null;
        }
        return BlockPos.of(entity.getPersistentData().getLong(MANAGER_POS_TAG));
    }

    public static void clearBoundManager(LivingEntity entity) {
        entity.getPersistentData().remove(MANAGER_POS_TAG);
        if (entity instanceof NpcEntity npc) {
            npc.clearStructureManager();
        }
    }

    public static void signalManagedEntityDeath(LivingEntity entity) {
        BlockPos managerPos = getBoundManagerPos(entity);
        if (managerPos == null) {
            return;
        }
        signalManagedEntityDeath(entity, managerPos);
    }

    public static void signalManagedEntityDeath(LivingEntity entity, BlockPos managerPos) {
        if (!(entity.level() instanceof ServerLevel world)) {
            return;
        }
        StructureManagedEntityData.get(world).markDead(
                entity.getUUID(), managerPos, entity.blockPosition());
        if (world.getBlockEntity(managerPos) instanceof StructureManagerBlockEntity manager) {
            manager.removeManagedEntity(entity.getUUID());
        }
    }

    public static void forgetManagedEntity(ServerLevel world, UUID uuid) {
        StructureManagedEntityData.get(world).remove(uuid);
    }

    private static void applyRecordedDeaths(StructureManagerBlockEntity manager) {
        Level world = manager.getLevel();
        if (!(world instanceof ServerLevel serverLevel)) {
            return;
        }
        for (UUID uuid : StructureManagedEntityData.get(serverLevel)
                .deadEntitiesFor(manager.getBlockPos())) {
            manager.removeManagedEntity(uuid);
        }
    }
}
