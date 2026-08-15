package net.sevenstars.middleearth.network.handlers;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.Util;
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
import java.util.concurrent.TimeUnit;

import static net.sevenstars.middleearth.network.handlers.OnboardingReturnResult.Status.DISABLED;
import static net.sevenstars.middleearth.network.handlers.OnboardingReturnResult.Status.INVALID_SESSION;
import static net.sevenstars.middleearth.network.handlers.OnboardingReturnResult.Status.NOT_READY;
import static net.sevenstars.middleearth.network.handlers.OnboardingReturnResult.Status.PERSISTENCE_FAILED;
import static net.sevenstars.middleearth.network.handlers.OnboardingReturnResult.Status.RETRY_LATER;
import static net.sevenstars.middleearth.network.handlers.OnboardingReturnResult.Status.TELEPORT_FAILED;

public final class OnboardingServerHandler {
    private static final long RETURN_RETRY_NANOS = TimeUnit.SECONDS.toNanos(1L);
    private static final Map<UUID, Session> SESSIONS = new HashMap<>();

    private OnboardingServerHandler() {
    }

    public static boolean begin(ServerPlayer player, InteractionHand hand) {
        OnboardingSessionPurpose purpose = player != null && ModDimensions.isInMiddleEarth(player.level())
                ? OnboardingSessionPurpose.PHIAL_RETURN
                : OnboardingSessionPurpose.PHIAL_ENTRY;
        return begin(player, hand, purpose);
    }

    public static boolean beginForced(ServerPlayer player) {
        return begin(player, InteractionHand.MAIN_HAND, OnboardingSessionPurpose.FORCED_ENTRY);
    }

    public static void clearSession(ServerPlayer player) {
        if (player != null) {
            SESSIONS.remove(player.getUUID());
        }
    }

    private static boolean begin(
            ServerPlayer player,
            InteractionHand hand,
            OnboardingSessionPurpose purpose
    ) {
        if (player == null || hand == null
                || purpose == null
                || (purpose.allowsReturn() && !ModDimensions.isInMiddleEarth(player.level()))
                || (purpose.allowsEntry() && !ModDimensions.isInOverworld(player.level()))) {
            return false;
        }
        if (purpose.requiresPhial() && !isHoldingPhial(player, hand)) {
            return false;
        }
        if (purpose.allowsReturn()
                && !ModServerConfigs.ENABLE_RETURN_TO_OVERWORLD) {
            return false;
        }

        double delaySeconds = player.hasInfiniteMaterials()
                ? 0.0D
                : Math.max(0.0D, ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION);
        int delayTicks = player.hasInfiniteMaterials()
                ? 0
                : Math.max(0, (int) Math.ceil(ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION * 20.0F));
        if (purpose.requiresPhial() && delayTicks > 0) {
            player.getCooldowns().addCooldown(player.getItemInHand(hand).getItem(), delayTicks);
        }
        long nowNanos = Util.getNanos();
        long readyAtNanos = OnboardingSessionClock.afterSeconds(nowNanos, delaySeconds);
        SESSIONS.put(player.getUUID(), new Session(
                player.level().dimension(),
                player.blockPosition(),
                hand,
                readyAtNanos,
                OnboardingSessionClock.addSaturated(readyAtNanos, purpose.lifetimeNanos()),
                purpose
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
        if (session == null || !session.purpose().allowsEntry()
                || factionId == null || raceId == null || spawnId == null) {
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
            if (!PlayerDataService.setOrigin(
                    player,
                    player.level(),
                    BuiltinDimensionTypes.OVERWORLD.location(),
                    session.origin()
            )) {
                MiddleEarth.LOGGER.logError("Onboarding could not persist the overworld origin; teleport cancelled.");
                return false;
            }
            if (!ModDimensions.teleportPlayerToMe(player, target, false, false)) {
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
        if (session == null || !session.purpose().allowsEntry()
                || !PlayerDataService.playerPassedOnboarding(player)) {
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

        BlockPos returnPos = PlayerDataService.getMiddleEarthReturnPos(player);
        boolean teleported = returnPos != null
                && ModDimensions.teleportPlayerToMeReturnPoint(player, returnPos, welcomeNeeded);
        if (!teleported) {
            SpawnData spawnData = PlayerDataService.getPlayerSpawnData(player, player.level());
            teleported = spawnData != null
                    && isFinite(spawnData.getCoordinates())
                    && ModDimensions.teleportPlayerToMe(
                            player,
                            spawnData.getBlockPos().getCenter(),
                            true,
                            welcomeNeeded
                    );
        }
        if (!teleported) {
            return false;
        }
        consumePhial(player, session);
        SESSIONS.remove(player.getUUID());
        return true;
    }

    public static OnboardingReturnResult returnToOverworld(ServerPlayer player, InteractionHand hand) {
        if (!ModServerConfigs.ENABLE_RETURN_TO_OVERWORLD) {
            return OnboardingReturnResult.failure(DISABLED);
        }
        if (player == null || hand == null) {
            return OnboardingReturnResult.failure(INVALID_SESSION);
        }

        Session session = SESSIONS.get(player.getUUID());
        if (session == null || !session.purpose().allowsReturn() || !isSessionContextValid(
                player,
                hand,
                null,
                ModDimensions.ME_WORLD_KEY,
                session
        )) {
            return OnboardingReturnResult.failure(INVALID_SESSION);
        }

        long nowNanos = Util.getNanos();
        if (OnboardingSessionClock.reached(nowNanos, session.expiresAtNanos())) {
            SESSIONS.remove(player.getUUID());
            return OnboardingReturnResult.failure(INVALID_SESSION);
        }
        int readyInMillis = OnboardingSessionClock.remainingMillis(nowNanos, session.readyAtNanos());
        if (readyInMillis > 0) {
            return OnboardingReturnResult.retry(NOT_READY, readyInMillis);
        }
        int retryInMillis = OnboardingSessionClock.remainingMillis(nowNanos, session.nextAttemptAtNanos());
        if (retryInMillis > 0) {
            return OnboardingReturnResult.retry(RETRY_LATER, retryInMillis);
        }
        if (!PlayerDataService.isValidMiddleEarthReturnPos(session.origin())) {
            return OnboardingReturnResult.failure(INVALID_SESSION);
        }

        session.deferNextAttempt(nowNanos, RETURN_RETRY_NANOS);
        boolean teleported;
        try {
            teleported = ModDimensions.teleportPlayerToOverworld(player);
        } catch (RuntimeException exception) {
            session.deferNextAttempt(Util.getNanos(), RETURN_RETRY_NANOS);
            throw exception;
        }
        if (!teleported) {
            long failedAtNanos = Util.getNanos();
            session.deferNextAttempt(failedAtNanos, RETURN_RETRY_NANOS);
            return OnboardingReturnResult.retry(
                    TELEPORT_FAILED,
                    OnboardingSessionClock.remainingMillis(failedAtNanos, session.nextAttemptAtNanos())
            );
        }
        if (!PlayerDataService.setMiddleEarthReturnPos(player, session.origin())) {
            SESSIONS.remove(player.getUUID());
            MiddleEarth.LOGGER.logError("Returned to the Overworld but could not persist the Middle-earth return point.");
            return OnboardingReturnResult.failure(PERSISTENCE_FAILED);
        }
        consumePhial(player, session);
        SESSIONS.remove(player.getUUID());
        return OnboardingReturnResult.success();
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
        if (session == null || !isSessionContextValid(player, hand, clientOrigin, expectedSource, session)) {
            return null;
        }
        long nowNanos = Util.getNanos();
        if (OnboardingSessionClock.reached(nowNanos, session.expiresAtNanos())) {
            SESSIONS.remove(player.getUUID());
            return null;
        }
        if (!OnboardingSessionClock.reached(nowNanos, session.readyAtNanos())) {
            return null;
        }
        return session;
    }

    private static boolean isSessionContextValid(
            ServerPlayer player,
            InteractionHand hand,
            BlockPos clientOrigin,
            ResourceKey<Level> expectedSource,
            Session session
    ) {
        return session.sourceDimension().equals(expectedSource)
                && player.level().dimension().equals(session.sourceDimension())
                && session.hand() == hand
                && Level.isInSpawnableBounds(session.origin())
                && player.blockPosition().closerThan(session.origin(), 5.0D)
                && (clientOrigin == null || clientOrigin.closerThan(session.origin(), 5.0D))
                && (!session.purpose().requiresPhial() || isHoldingPhial(player, hand));
    }

    private static boolean isHoldingPhial(ServerPlayer player, InteractionHand hand) {
        return player.getItemInHand(hand).getItem() instanceof StarlightPhialItem;
    }

    private static void consumePhial(ServerPlayer player, Session session) {
        if (!session.purpose().requiresPhial() || player.hasInfiniteMaterials()) {
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

    private static final class Session {
        private final ResourceKey<Level> sourceDimension;
        private final BlockPos origin;
        private final InteractionHand hand;
        private final long readyAtNanos;
        private final long expiresAtNanos;
        private final OnboardingSessionPurpose purpose;
        private long nextAttemptAtNanos;

        private Session(
                ResourceKey<Level> sourceDimension,
                BlockPos origin,
                InteractionHand hand,
                long readyAtNanos,
                long expiresAtNanos,
                OnboardingSessionPurpose purpose
        ) {
            this.sourceDimension = sourceDimension;
            this.origin = origin.immutable();
            this.hand = hand;
            this.readyAtNanos = readyAtNanos;
            this.expiresAtNanos = expiresAtNanos;
            this.purpose = purpose;
        }

        private ResourceKey<Level> sourceDimension() {
            return sourceDimension;
        }

        private BlockPos origin() {
            return origin;
        }

        private InteractionHand hand() {
            return hand;
        }

        private long readyAtNanos() {
            return readyAtNanos;
        }

        private long expiresAtNanos() {
            return expiresAtNanos;
        }

        private OnboardingSessionPurpose purpose() {
            return purpose;
        }

        private long nextAttemptAtNanos() {
            return nextAttemptAtNanos;
        }

        private void deferNextAttempt(long nowNanos, long delayNanos) {
            nextAttemptAtNanos = OnboardingSessionClock.addSaturated(nowNanos, delayNanos);
        }
    }
}
