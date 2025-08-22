package net.sevenstars.middleearth.entity.goals.interfaces;

import net.minecraft.entity.ai.RangedAttackMob;

public interface CooldownRangedAttackMob extends RangedAttackMob {
    int getRangeAttackCooldown();
    void setRangeAttackCooldown(int cooldown);
}
