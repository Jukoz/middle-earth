package net.sevenstars.middleearth.mixin;

import net.minecraft.world.entity.player.Player;
import net.sevenstars.middleearth.utils.IEntityDataSaver;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public abstract class EntityDataSaverMixin implements IEntityDataSaver {
}
