package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.ai.RangedAttackMob;

public interface CooldownRangedAttackMob extends RangedAttackMob {
    int getRangeAttackCooldown();
    void setRangeAttackCooldown(int cooldown);
}
