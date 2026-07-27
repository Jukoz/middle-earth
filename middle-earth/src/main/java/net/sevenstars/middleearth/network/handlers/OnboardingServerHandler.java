package net.sevenstars.middleearth.network.handlers;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.config.ModServerConfigs;
import net.sevenstars.middleearth.item.items.StarlightPhialItem;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.resources.datas.factions.FactionUtil;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.datas.races.Race;
import net.sevenstars.middleearth.resources.datas.races.RaceLookup;
import net.sevenstars.middleearth.resources.datas.races.RaceUtil;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class OnboardingServerHandler {
    private static final Map<UUID, Session> SESSIONS = new HashMap<>();

    private OnboardingServerHandler() {
    }

    public static boolean begin(ServerPlayer player, InteractionHand hand) {
        return begin(player, hand, true);
    }

    public static boolean beginForced(ServerPlayer player) {
        return begin(player, InteractionHand.MAIN_HAND, false);
    }

    public static void clearSession(ServerPlayer player) {
        if (player != null) {
            SESSIONS.remove(player.getUUID());
        }
    }

    private static boolean begin(ServerPlayer player, InteractionHand hand, boolean requiresPhial) {
        if (player == null || hand == null
                || (!ModDimensions.isInOverworld(player.level()) && !ModDimensions.isInMiddleEarth(player.level()))) {
            return false;
        }
        if (requiresPhial && !isHoldingPhial(player, hand)) {
            return false;
        }

        int delayTicks = player.hasInfiniteMaterials()
                ? 0
                : Math.max(0, (int) Math.ceil(ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION * 20.0F));
        if (requiresPhial && delayTicks > 0) {
            player.getCooldowns().addCooldown(player.getItemInHand(hand).getItem(), delayTicks);
        }
        SESSIONS.put(player.getUUID(), new Session(
                player.level().dimension(),
                player.blockPosition(),
                hand,
                player.level().getGameTime() + delayTicks,
                requiresPhial
        ));
        return true;
    }

    public static boolean completeSelection(
            ServerPlayer player,
            ResourceLocation factionId,
            ResourceLocation raceId,
            ResourceLocation spawnId,
            BlockPos origin,
            InteractionHand hand
    ) {
        Session session = validateSession(player, hand, origin, ModDimensions.OW_WORLD_KEY);
        if (session == null || factionId == null || raceId == null || spawnId == null) {
            return false;
        }
        if (PlayerDataService.playerPassedOnboarding(player) && !ModServerConfigs.ENABLE_FACTION_RESET) {
            return false;
        }

        try {
            Faction faction = FactionLookup.getFactionById(player.level(), factionId);
            Race race = RaceLookup.getRace(player.level(), raceId);
            SpawnData spawn = faction == null || faction.getSpawnData() == null
                    ? null
                    : faction.getSpawnData().findSpawn(spawnId);
            var allowedRaces = faction == null ? null : faction.getRaces(player.level());
            if (faction == null || !faction.isJoinable() || race == null || spawn == null
                    || allowedRaces == null
                    || allowedRaces.stream().noneMatch(candidate -> Objects.equals(candidate.getId(), raceId))
                    || !isFinite(spawn.getCoordinates())) {
                return false;
            }

            Vec3 target = spawn.getBlockPos().getCenter();
            if (!ModDimensions.teleportPlayerToMe(player, target, false, false)) {
                return false;
            }
            if (!PlayerDataService.setOrigin(
                    player,
                    player.level(),
                    BuiltinDimensionTypes.OVERWORLD.location(),
                    session.origin()
            )) {
                MiddleEarth.LOGGER.logError("Onboarding completed its dimension transition but could not persist the overworld origin.");
                return false;
            }

            Faction previousFaction = PlayerDataService.getPlayerFaction(player, player.level());
            SpawnData previousSpawn = PlayerDataService.getPlayerSpawnData(player, player.level());
            boolean sameFactionAndSpawn = previousFaction != null
                    && Objects.equals(previousFaction.getId(), factionId)
                    && previousSpawn != null
                    && Objects.equals(previousSpawn.getIdentifier(), spawnId);
            if (!sameFactionAndSpawn) {
                FactionUtil.updateFaction(player, faction, spawnId);
            }
            RaceUtil.updateRace(player, race, true);
            player.setRespawnPosition(
                    ModDimensions.ME_WORLD_KEY,
                    player.blockPosition(),
                    player.getYRot(),
                    true,
                    true
            );
            consumePhial(player, session);
            SESSIONS.remove(player.getUUID());
            return true;
        } catch (Exception exception) {
            MiddleEarth.LOGGER.logError("Rejected invalid onboarding selection.", exception);
            return false;
        }
    }

    public static boolean teleportCurrentSpawn(ServerPlayer player, InteractionHand hand, boolean welcomeNeeded) {
        Session session = validateSession(player, hand, null, ModDimensions.OW_WORLD_KEY);
        if (session == null || !PlayerDataService.playerPassedOnboarding(player)) {
            return false;
        }
        SpawnData spawnData = PlayerDataService.getPlayerSpawnData(player, player.level());
        if (spawnData == null || !isFinite(spawnData.getCoordinates())
                || !ModDimensions.teleportPlayerToMe(player, spawnData.getBlockPos().getCenter(), true, welcomeNeeded)) {
            return false;
        }
        if (!PlayerDataService.setOrigin(
                player,
                player.level(),
                BuiltinDimensionTypes.OVERWORLD.location(),
                session.origin()
        )) {
            return false;
        }
        consumePhial(player, session);
        SESSIONS.remove(player.getUUID());
        return true;
    }

    public static boolean returnToOverworld(ServerPlayer player, InteractionHand hand) {
        Session session = validateSession(player, hand, null, ModDimensions.ME_WORLD_KEY);
        if (session == null || !ModServerConfigs.ENABLE_RETURN_TO_OVERWORLD
                || !ModDimensions.teleportPlayerToOverworld(player)) {
            return false;
        }
        consumePhial(player, session);
        SESSIONS.remove(player.getUUID());
        return true;
    }

    private static Session validateSession(
            ServerPlayer player,
            InteractionHand hand,
            BlockPos clientOrigin,
            ResourceKey<Level> expectedSource
    ) {
        if (player == null || hand == null) {
            return null;
        }
        Session session = SESSIONS.get(player.getUUID());
        if (session == null
                || !session.sourceDimension().equals(expectedSource)
                || !player.level().dimension().equals(session.sourceDimension())
                || session.hand() != hand
                || player.level().getGameTime() < session.readyAt()
                || !Level.isInSpawnableBounds(session.origin())
                || !player.blockPosition().closerThan(session.origin(), 5.0D)
                || (clientOrigin != null && !clientOrigin.closerThan(session.origin(), 5.0D))
                || (session.requiresPhial() && !isHoldingPhial(player, hand))) {
            return null;
        }
        return session;
    }

    private static boolean isHoldingPhial(ServerPlayer player, InteractionHand hand) {
        return player.getItemInHand(hand).getItem() instanceof StarlightPhialItem;
    }

    private static void consumePhial(ServerPlayer player, Session session) {
        if (!session.requiresPhial() || player.hasInfiniteMaterials()) {
            return;
        }
        ItemStack stack = player.getItemInHand(session.hand());
        if (stack.getItem() instanceof StarlightPhialItem) {
            stack.shrink(1);
        }
    }

    private static boolean isFinite(Vec3 coordinates) {
        return Double.isFinite(coordinates.x)
                && Double.isFinite(coordinates.y)
                && Double.isFinite(coordinates.z);
    }

    private record Session(
            ResourceKey<Level> sourceDimension,
            BlockPos origin,
            InteractionHand hand,
            long readyAt,
            boolean requiresPhial
    ) {
    }
}
