package net.sevenstars.middleearth.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin extends PlayerEntity {
    @Shadow public MinecraftServer server;
    @Shadow public ServerPlayerInteractionManager interactionManager;

    public ServerPlayerEntityMixin(World world, GameProfile profile) {
        super(world, profile);
    }

    @Nullable
    @Override
    public GameMode getGameMode() {
        return this.interactionManager.getGameMode();
    }

    @Inject(method = "getRespawnTarget", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    public void getRespawnTargetWithBrokenBed(boolean alive, TeleportTarget.PostDimensionTransition postDimensionTransition, CallbackInfoReturnable<TeleportTarget> cir) {
        if(ModDimensions.isInMiddleEarth(this.getWorld())){
            if (tryToOverrideSpawn(postDimensionTransition, cir)) return;
            resetSpawn(postDimensionTransition, cir);
            return;
        }
        cir.setReturnValue(TeleportTarget.missingSpawnBlock(this.server.getOverworld(), this, postDimensionTransition));
    }

    @Inject(method = "getRespawnTarget", at = @At(value = "RETURN", ordinal = 2), cancellable = true)
    public void getRespawnTargetNatural(boolean alive, TeleportTarget.PostDimensionTransition postDimensionTransition, CallbackInfoReturnable<TeleportTarget> cir) {
        if(ModDimensions.isInMiddleEarth(this.getWorld())){
            if (tryToOverrideSpawn(postDimensionTransition, cir)) return;
            resetSpawn(postDimensionTransition, cir);
            return;
        }
        cir.setReturnValue(TeleportTarget.missingSpawnBlock(this.server.getOverworld(), this, postDimensionTransition));
    }

    @Unique
    private void resetSpawn(TeleportTarget.PostDimensionTransition postDimensionTransition, CallbackInfoReturnable<TeleportTarget> cir){
        if(this.getServer() == null) return;

        PlayerManager manager = this.getServer().getPlayerManager();
        ServerPlayerEntity foundPlayer = manager.getPlayer(this.getUuid());
        if(this.getServer() == null) return;
        ServerPlayerEntity.Respawn respawn = new ServerPlayerEntity.Respawn(World.OVERWORLD, foundPlayer.getServer().getOverworld().getSpawnPos(), foundPlayer.getServer().getOverworld().getSpawnAngle(), true);
        foundPlayer.setSpawnPoint(respawn, true);

        cir.setReturnValue(new TeleportTarget(this.server.getOverworld(), this, postDimensionTransition));
    }

    @Unique
    private boolean tryToOverrideSpawn(TeleportTarget.PostDimensionTransition postDimensionTransition, CallbackInfoReturnable<TeleportTarget> cir) {
        MinecraftServer server = this.getServer();

        if(server == null) return false;
        PlayerManager manager = server.getPlayerManager();
        ServerPlayerEntity foundPlayer = manager.getPlayer(this.getUuid());

        if(foundPlayer == null) return false;
        if(ModDimensions.isInMiddleEarth(this.getWorld()) && PlayerDataService.getPlayerSpawnData(foundPlayer, getWorld()) instanceof SpawnData data && data.getIdentifier() != null) {
            BlockPos spawnCoordinates = data.getWorldCoordinateBlockPos();
            if(spawnCoordinates != null){
                ServerWorld MEWorld = this.server.getWorld(ModDimensions.ME_WORLD_KEY);
                if(MEWorld != null){
                    Vec3d coordinates = new Vec3d(spawnCoordinates.getX(), spawnCoordinates.getY() + 1, spawnCoordinates.getZ());

                    ServerPlayerEntity.Respawn respawn = new ServerPlayerEntity.Respawn(ModDimensions.ME_WORLD_KEY, new BlockPos((int) coordinates.x, (int) coordinates.y, (int) coordinates.z),0,true);
                    foundPlayer.setSpawnPoint(respawn, true);

                    cir.setReturnValue(new TeleportTarget(MEWorld, spawnCoordinates.toCenterPos(), Vec3d.ZERO, 0, 0, postDimensionTransition));
                    return true;
                }
            }
        }
        ServerPlayerEntity.Respawn respawn = new ServerPlayerEntity.Respawn(World.OVERWORLD, server.getOverworld().getSpawnPos(), server.getOverworld().getSpawnAngle(), true);
        foundPlayer.setSpawnPoint(respawn, true);

        cir.setReturnValue(new TeleportTarget(server.getOverworld(), server.getOverworld().getSpawnPos().toCenterPos(), Vec3d.ZERO, 0, 0,postDimensionTransition));
        return false;
    }

    @Override
    public boolean isSpectator() {
        return this.interactionManager.getGameMode() == GameMode.SPECTATOR;
    }

    @Override
    public boolean isCreative() {
        return this.interactionManager.getGameMode() == GameMode.CREATIVE;
    }
}