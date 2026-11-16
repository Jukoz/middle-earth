package net.sevenstars.middleearth.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
import net.minecraft.world.WorldProperties;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.EntityAttributesME;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import net.sevenstars.middleearth.statusEffects.StatusEffectRegistryME;
import net.sevenstars.middleearth.utils.IEntityDataSaver;
import net.sevenstars.middleearth.utils.PlayerMovementData;
import net.sevenstars.middleearth.world.StartInMiddleEarth;
import net.sevenstars.middleearth.world.dimension.DimensionRegistryME;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

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

    // alive is only true after a death, not when leaving the End
    @Inject(method = "getRespawnTarget", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    public void getRespawnTargetWithBrokenBed(boolean alive, TeleportTarget.PostDimensionTransition postDimensionTransition, CallbackInfoReturnable<TeleportTarget> cir) {
        if(DimensionRegistryME.isInMiddleEarth(this.getEntityWorld()) || (alive && StartInMiddleEarth.isEnabled(this.server))){
            if (tryToOverrideSpawn(postDimensionTransition, cir)) return;
            resetSpawn(postDimensionTransition, cir);
            return;
        }
        cir.setReturnValue(missingSpawnBlock(this.server.getOverworld(), postDimensionTransition));
    }

    @Inject(method = "getRespawnTarget", at = @At(value = "RETURN", ordinal = 2), cancellable = true)
    public void getRespawnTargetNatural(boolean alive, TeleportTarget.PostDimensionTransition postDimensionTransition, CallbackInfoReturnable<TeleportTarget> cir) {
        if(DimensionRegistryME.isInMiddleEarth(this.getEntityWorld()) || (alive && StartInMiddleEarth.isEnabled(this.server))){
            if (tryToOverrideSpawn(postDimensionTransition, cir)) return;
            resetSpawn(postDimensionTransition, cir);
            return;
        }
        cir.setReturnValue(missingSpawnBlock(this.server.getOverworld(), postDimensionTransition));
    }

    @Unique
    private Vec3d worldSpawnPos(ServerWorld world) {
        return this.getWorldSpawnPos(world, world.getSpawnPoint().getPos()).toBottomCenterPos();
    }

    @Unique
    private TeleportTarget missingSpawnBlock(ServerWorld world, TeleportTarget.PostDimensionTransition postDimensionTransition) {
        return new TeleportTarget(world, worldSpawnPos(world), Vec3d.ZERO, 0, 0, true, false, Set.of(), postDimensionTransition);
    }

    @Unique
    private void resetSpawn(TeleportTarget.PostDimensionTransition postDimensionTransition, CallbackInfoReturnable<TeleportTarget> cir){
        if(this.server == null) return;

        PlayerManager manager = this.server.getPlayerManager();
        ServerPlayerEntity foundPlayer = manager.getPlayer(this.getUuid());
        if(this.server == null) return;
        WorldProperties.SpawnPoint worldSpawn = foundPlayer.getEntityWorld().getServer().getOverworld().getSpawnPoint();
        ServerPlayerEntity.Respawn respawn = new ServerPlayerEntity.Respawn(WorldProperties.SpawnPoint.create(World.OVERWORLD, worldSpawn.getPos(), worldSpawn.yaw(), 0.0F), true);
        foundPlayer.setSpawnPoint(respawn, true);

        ServerWorld overworld = this.server.getOverworld();
        cir.setReturnValue(new TeleportTarget(overworld, worldSpawnPos(overworld), Vec3d.ZERO, 0, 0, postDimensionTransition));
    }

    @Unique
    private boolean tryToOverrideSpawn(TeleportTarget.PostDimensionTransition postDimensionTransition, CallbackInfoReturnable<TeleportTarget> cir) {
        MinecraftServer server = this.server;

        if(server == null) return false;
        PlayerManager manager = server.getPlayerManager();
        ServerPlayerEntity foundPlayer = manager.getPlayer(this.getUuid());

        if(foundPlayer == null) return false;
        if(PlayerDataService.getPlayerSpawnData(foundPlayer, getEntityWorld()) instanceof SpawnData data && data.getIdentifier() != null) {
            BlockPos spawnCoordinates = data.getWorldCoordinateBlockPos();
            if(spawnCoordinates != null){
                ServerWorld MEWorld = this.server.getWorld(DimensionRegistryME.ME_WORLD_KEY);
                if(MEWorld != null){
                    Vec3d coordinates = new Vec3d(spawnCoordinates.getX(), spawnCoordinates.getY() + 1, spawnCoordinates.getZ());

                    ServerPlayerEntity.Respawn respawn = new ServerPlayerEntity.Respawn(WorldProperties.SpawnPoint.create(DimensionRegistryME.ME_WORLD_KEY, new BlockPos((int) coordinates.x, (int) coordinates.y, (int) coordinates.z), 0, 0), true);
                    foundPlayer.setSpawnPoint(respawn, true);

                    cir.setReturnValue(new TeleportTarget(MEWorld, spawnCoordinates.toCenterPos(), Vec3d.ZERO, 0, 0, postDimensionTransition));
                    return true;
                }
            }
        }
        WorldProperties.SpawnPoint worldSpawn = server.getOverworld().getSpawnPoint();
        ServerPlayerEntity.Respawn respawn = new ServerPlayerEntity.Respawn(WorldProperties.SpawnPoint.create(World.OVERWORLD, worldSpawn.getPos(), worldSpawn.yaw(), 0.0F), true);
        foundPlayer.setSpawnPoint(respawn, true);

        cir.setReturnValue(new TeleportTarget(server.getOverworld(), worldSpawn.getPos().toCenterPos(), Vec3d.ZERO, 0, 0,postDimensionTransition));
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

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        PlayerMovementData.addAFKTime((IEntityDataSaver) this,1);
        if(isCreative() || isSpectator()) {
            if(hasStatusEffect(StatusEffectRegistryME.ENSHROUDED) && getStatusEffect(StatusEffectRegistryME.ENSHROUDED).isInfinite()){
                setStatusEffect(new StatusEffectInstance(StatusEffectRegistryME.ENSHROUDED, 40), this);
                setStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 40), this);
                setStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 40), this);
                setStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 40), this);
            }
            return;
        }

        long currentTick = getEntityWorld().getTickOrder();
        if(currentTick % 5 == 0){
            if(getEntityWorld() == null) return;
            PlayerData data = StateSaverAndLoader.getPlayerState(getEntityWorld().getPlayerByUuid(getUuid()));
            if(data == null) return;

            int currentLightLevel = getEntityWorld().getLightLevel(getBlockPos());

            double delversFearStrenght = getAttributeValue(EntityAttributesME.DELVERS_FEAR_STRENGTH);

            if(delversFearStrenght > 0.0 && currentLightLevel < 3 && !getEntityWorld().isSkyVisible(getBlockPos())) {
                data.addToDelversFearCountInSeconds();

                if(data.getDelversFearCountInSeconds() > delversFearStrenght){
                    addStatusEffect(new StatusEffectInstance(StatusEffectRegistryME.ENSHROUDED, -1));
                    addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, -1));
                    addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, -1));
                }
            } else {
                if(hasStatusEffect(StatusEffectRegistryME.ENSHROUDED) && getStatusEffect(StatusEffectRegistryME.ENSHROUDED).isInfinite()){
                    setStatusEffect(new StatusEffectInstance(StatusEffectRegistryME.ENSHROUDED, 40), this);
                    setStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 40), this);
                    setStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 40), this);

                }
                data.resetDelversFearCount();
            }
        }
    }
}