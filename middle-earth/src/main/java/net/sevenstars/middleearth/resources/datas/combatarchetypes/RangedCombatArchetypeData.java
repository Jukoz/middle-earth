package net.sevenstars.middleearth.resources.datas.combatarchetypes;

import net.minecraft.nbt.NbtCompound;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.data.CombatArchetype;

public class RangedCombatArchetypeData extends CombatArchetypeData {
    private final float precisionModifier; // 1 being very precise, 0 being the opposite
    private final int optimalBlockRangeMinimum; // If the target is too close, they will try to fall back to that distance
    private final int optimalBlockRangeMaximum; // If the target is too far, they will try to fall in to that distance
    private final int ammoCountMax; // Maximum amount of projectiles before depletion
    private final int replenishmentRateInTicks; // tick amount per ammo
    private final int replenishmentDelayAfterHurt; // replenishment delay after getting hurt
    private final int replenishmentDelayAfterShooting; // replenishment delay after getting hurt

    public RangedCombatArchetypeData(float fleeMovementSpeedModifier,  float seekTargetMovementSpeedModifier, float precisionModifier, int optimalBlockRangeMinimum, int optimalBlockRangeMaximum, int ammoCountMax, int replenishmentRateInTicks, int replenishmentDelayAfterHurt, int replenishmentDelayAfterShooting) {
        super(fleeMovementSpeedModifier, seekTargetMovementSpeedModifier);
        this.precisionModifier = precisionModifier;
        this.optimalBlockRangeMinimum = optimalBlockRangeMinimum;
        this.optimalBlockRangeMaximum = optimalBlockRangeMaximum;
        this.ammoCountMax = ammoCountMax;
        this.replenishmentRateInTicks = replenishmentRateInTicks;
        this.replenishmentDelayAfterHurt = replenishmentDelayAfterHurt;
        this.replenishmentDelayAfterShooting = replenishmentDelayAfterShooting;
    }

    public RangedCombatArchetypeData(NbtCompound data) {
        super(data);
        this.precisionModifier = data.getFloat("precision_modifier", 1.0f);
        this.optimalBlockRangeMinimum = data.getInt("optimal_block_range_minimum", 5);
        this.optimalBlockRangeMaximum = data.getInt("optimal_block_range_maximum", 10);
        this.ammoCountMax = data.getInt("ammo_count_max", 5);
        this.replenishmentRateInTicks = data.getInt("replenishment_rate", 10);
        this.replenishmentDelayAfterHurt = data.getInt("replenishment_delay_after_hurt", 100);
        this.replenishmentDelayAfterShooting = data.getInt("replenishment_delay_after_shooting", 40);
    }

    @Override
    protected CombatArchetype getCombatArchetype(){
        return CombatArchetype.RANGED;
    };

    @Override
    protected NbtCompound getDataNbt() {
        NbtCompound nbt = super.getDataNbt();
        nbt.putFloat("precision_modifier", this.precisionModifier);
        nbt.putInt("optimal_block_range_minimum", this.optimalBlockRangeMinimum);
        nbt.putInt("optimal_block_range_maximum", this.optimalBlockRangeMaximum);

        nbt.putInt("ammo_count_max", this.ammoCountMax);
        nbt.putInt("replenishment_rate", this.replenishmentRateInTicks);
        nbt.putInt("replenishment_delay_after_hurt", this.replenishmentDelayAfterHurt);
        nbt.putInt("replenishment_delay_after_shooting", this.replenishmentDelayAfterShooting);

        return nbt;
    }

    public int getAmmoCount() {
        return this.ammoCountMax;
    }
}
