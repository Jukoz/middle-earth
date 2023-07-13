package net.jesteur.me.events;

import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.jesteur.me.item.ModRessourceItems;
import net.jesteur.me.world.dimension.ModDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3i;

/**
 * Event called when player starts sleeping (on right click)
 * If the player has a starlight phial in his inventory, then we proceed in getting his current and target world
 * Once we find the right coordinates, we wake up the player to free the bed and teleport him.
 */
public class OnPlayerSleepEvent implements EntitySleepEvents.StartSleeping {
    public static final Vector3i ME_SPAWN_LOCATION = new Vector3i(3640, 90, 2950);

    @Override
    public void onStartSleeping(LivingEntity entity, BlockPos sleepingPos) {
        if(entity.getType() == EntityType.PLAYER) {
            PlayerEntity player = (PlayerEntity) entity;
            if(player.getInventory().contains(ModRessourceItems.STARLIGHT_PHIAL.getDefaultStack())) {
                ModDimensions.teleportPlayerToME(player);
            }
        }
    }
}
