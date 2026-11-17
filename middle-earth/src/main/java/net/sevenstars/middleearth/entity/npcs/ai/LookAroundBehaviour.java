package net.sevenstars.middleearth.entity.npcs.ai;

import net.sevenstars.middleearth.entity.npcs.NpcEntity;

final class LookAroundBehaviour extends Behaviour {
    private static final int LOOK_TIME = 20;
    private static final int LOOK_TIME_VARIATION = 20;

    private double deltaX;
    private double deltaZ;
    // In ticks
    private int lookTime;

    LookAroundBehaviour() {
        super(LOOK_PRIORITY, MOVE | LOOK);
    }

    @Override
    boolean canStart(NpcEntity npc) {
        return npc.getRandom().nextFloat() < LOOK_CHANCE;
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        return lookTime >= 0;
    }

    @Override
    void start(NpcEntity npc) {
        double angle = Math.PI * 2 * npc.getRandom().nextDouble();
        deltaX = Math.cos(angle);
        deltaZ = Math.sin(angle);
        lookTime = LOOK_TIME + npc.getRandom().nextInt(LOOK_TIME_VARIATION);
    }

    @Override
    boolean runsEveryTick() {
        return true;
    }

    @Override
    void tick(NpcEntity npc) {
        lookTime--;
        npc.getLookControl().lookAt(npc.getX() + deltaX, npc.getEyeY(), npc.getZ() + deltaZ);
    }
}
