package net.sevenstars.middleearth.entity.npcs.pathing;

import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class NpcNavigation extends MobNavigation {
    public NpcNavigation(MobEntity mob, World world) {
        super(mob, world);
    }

    @Override
    protected PathNodeNavigator createPathNodeNavigator(int range) {
        this.nodeMaker = new NpcPathNodeMaker();
        return new PathNodeNavigator(this.nodeMaker, range);
    }

    // Off the ground on a ladder is still somewhere a path can be followed from
    @Override
    protected boolean isAtValidPosition() {
        return super.isAtValidPosition() || this.entity.isClimbing();
    }
}
