package net.sevenstars.middleearth.entity.npcs.ai;

import net.sevenstars.middleearth.entity.npcs.NpcEntity;

abstract class AttackBehaviour extends Behaviour {
    private final NpcEntity.AttackType type;

    AttackBehaviour(NpcEntity.AttackType type) {
        super(ATTACK_PRIORITY, MOVE | LOOK);
        this.type = type;
    }

    @Override
    final boolean canStart(NpcEntity npc) {
        return npc.getAttackType() == type && canAttack(npc);
    }

    abstract boolean canAttack(NpcEntity npc);

    @Override
    boolean runsEveryTick() {
        return true;
    }
}
