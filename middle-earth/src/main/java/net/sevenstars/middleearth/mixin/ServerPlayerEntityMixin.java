package net.sevenstars.middleearth.mixin;

import com.mojang.authlib.GameProfile;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
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

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
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
        foundPlayer.setSpawnPoint(World.OVERWORLD, foundPlayer.getServer().getOverworld().getSpawnPos(), foundPlayer.getServer().getOverworld().getSpawnAngle(), true, true);

        cir.setReturnValue(new TeleportTarget(this.server.getOverworld(), this, postDimensionTransition));
    }

    @Unique
    private boolean tryToOverrideSpawn(TeleportTarget.PostDimensionTransition postDimensionTransition, CallbackInfoReturnable<TeleportTarget> cir) {
        MinecraftServer server = this.getServer();

        if(server == null) return false;
        PlayerManager manager = server.getPlayerManager();
        ServerPlayerEntity foundPlayer = manager.getPlayer(this.getUuid());

        if(foundPlayer == null) return false;
        if(ModDimensions.isInMiddleEarth(this.getWorld())) {
            PlayerData data = StateSaverAndLoader.getPlayerState(foundPlayer);
            if(data != null && data.hasAffilition()){
                Vec3d spawnCoordinates = data.getSpawnMiddleEarthCoordinate(getWorld());
                if(spawnCoordinates != null){
                    ServerWorld MEWorld = this.server.getWorld(ModDimensions.ME_WORLD_KEY);
                    if(MEWorld != null){
                        Vec3d coordinates = new Vec3d(spawnCoordinates.x, spawnCoordinates.y + 1, spawnCoordinates.z);
                        foundPlayer.setSpawnPoint(ModDimensions.ME_WORLD_KEY, new BlockPos((int) coordinates.x, (int) coordinates.y, (int) coordinates.z),0,true, true);
                        cir.setReturnValue(new TeleportTarget(MEWorld, spawnCoordinates, Vec3d.ZERO, 0, 0, postDimensionTransition));
                        return true;
                    }
                }
            }
        }
        foundPlayer.setSpawnPoint(World.OVERWORLD, server.getOverworld().getSpawnPos(), server.getOverworld().getSpawnAngle(), true, true);
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