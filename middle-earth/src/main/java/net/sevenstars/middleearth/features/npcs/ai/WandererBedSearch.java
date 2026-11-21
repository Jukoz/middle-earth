package net.sevenstars.middleearth.features.npcs.ai;

import net.sevenstars.middleearth.features.npcs.NpcEntity;

// Claims a bed ahead of the walk; the claim is an effect, so it can't be part of canStart
final class WandererBedSearch extends Behaviour {
    private final WandererBedBehaviour walker;

    WandererBedSearch(WandererBedBehaviour walker) {
        super(WANDERER_BED_PRIORITY, NONE, MOVE | LOOK);
        this.walker = walker;
    }

    @Override
    boolean canStart(NpcEntity npc) {
        return walker.mayLookForBed(npc);
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        return false;
    }

    @Override
    void start(NpcEntity npc) {
        walker.lookForBed(npc);
    }
}
