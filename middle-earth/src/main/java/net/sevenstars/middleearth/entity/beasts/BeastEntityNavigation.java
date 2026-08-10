package net.sevenstars.middleearth.entity.beasts;

import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class BeastEntityNavigation extends MobNavigation {
    public BeastEntityNavigation(MobEntity mobEntity, World world) {
        super(mobEntity, world);
    }

    @Override
    protected boolean isAtValidPosition() {
        return super.isAtValidPosition() && !((AbstractBeastEntity)entity).isSitting();
    }
}
