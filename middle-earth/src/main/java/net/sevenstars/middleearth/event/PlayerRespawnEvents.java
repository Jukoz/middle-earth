package net.sevenstars.middleearth.event;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerRespawnPositionEvent;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

@EventBusSubscriber(modid = MiddleEarth.NEOFORGE_MOD_ID)
public final class PlayerRespawnEvents {
    private PlayerRespawnEvents() {
    }

    @SubscribeEvent
    public static void useMiddleEarthFactionSpawn(PlayerRespawnPositionEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)
                || !ModDimensions.isInMiddleEarth(player.level())) {
            return;
        }

        DimensionTransition originalTransition = event.getOriginalDimensionTransition();
        if (!originalTransition.missingRespawnBlock()
                && player.getRespawnPosition() != null) {
            return;
        }

        SpawnData spawnData = PlayerDataService.getPlayerSpawnData(player, player.level());
        if (spawnData == null || spawnData.getIdentifier() == null || spawnData.getCoordinates() == null) {
            return;
        }

        MinecraftServer server = player.getServer();
        if (server == null) {
            return;
        }

        ServerLevel middleEarth = server.getLevel(ModDimensions.ME_WORLD_KEY);
        if (middleEarth == null) {
            return;
        }

        BlockPos spawnCoordinates = spawnData.getWorldCoordinateBlockPos();
        var safeTarget = ModDimensions.findSafeMiddleEarthTeleportTarget(
                middleEarth,
                new Vec3(spawnCoordinates.getX(), spawnCoordinates.getY() + 1, spawnCoordinates.getZ()),
                player
        );
        if (safeTarget.isEmpty()) {
            MiddleEarth.LOGGER.logError("No safe Middle-earth respawn position was found; keeping the original respawn transition.");
            return;
        }
        Vec3 target = safeTarget.get();
        BlockPos targetPos = BlockPos.containing(target);
        DimensionTransition transition = event.getDimensionTransition();

        middleEarth.getChunkSource().addRegionTicket(
                TicketType.POST_TELEPORT,
                new ChunkPos(targetPos),
                1,
                player.getId()
        );
        player.setRespawnPosition(ModDimensions.ME_WORLD_KEY, targetPos, transition.yRot(), true, true);
        event.setCopyOriginalSpawnPosition(true);
        event.setDimensionTransition(new DimensionTransition(
                middleEarth,
                target,
                transition.speed(),
                transition.yRot(),
                transition.xRot(),
                false,
                transition.postDimensionTransition()
        ));
    }
}
