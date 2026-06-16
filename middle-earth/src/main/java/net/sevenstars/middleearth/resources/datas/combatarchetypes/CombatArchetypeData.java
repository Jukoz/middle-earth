package net.sevenstars.middleearth.resources.datas.combatarchetypes;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.data.CombatArchetype;

public abstract class CombatArchetypeData {
    protected CombatArchetype combatArchetype;

    private final float fleeMovementSpeedModifier;
    private final float seekTargetMovementSpeedModifier;
    private final float optimalBlockRangeMinimum; // If the target is too close, they will try to fall back to that distance
    private final float optimalBlockRangeMaximum; // If the target is too far, they will try to fall in to that distance

    public CombatArchetypeData(float fleeMovementSpeedModifier, float seekTargetMovementSpeedModifier, float optimalBlockRangeMinimum, float optimalBlockRangeMaximum) {
        setArchetype(getCombatArchetype());
        this.fleeMovementSpeedModifier = fleeMovementSpeedModifier;
        this.seekTargetMovementSpeedModifier = seekTargetMovementSpeedModifier;
        this.optimalBlockRangeMinimum = optimalBlockRangeMinimum;
        this.optimalBlockRangeMaximum = optimalBlockRangeMaximum;
    }

    public CombatArchetypeData(NbtCompound data) {
        setArchetype(getCombatArchetype());

        this.fleeMovementSpeedModifier = data.getFloat("flee_movement_speed_modifier", 1f);
        this.seekTargetMovementSpeedModifier = data.getFloat("seek_target_movement_speed_modifier", 1f);
        this.optimalBlockRangeMinimum = data.getInt("optimal_block_range_minimum", 1);
        this.optimalBlockRangeMaximum = data.getInt("optimal_block_range_maximum", 3);
    }

    public NbtCompound getNbt(){
        NbtCompound nbt = new NbtCompound();
        if(this.combatArchetype == null)
            this.combatArchetype = CombatArchetype.MELEE;

        nbt.putString("type", combatArchetype.name());


        NbtCompound dataNbt =  getDataNbt();

        nbt.put("data", dataNbt);

        return nbt;
    }

    protected NbtCompound getDataNbt() {
        NbtCompound nbtCompound = new NbtCompound();

        nbtCompound.putFloat("flee_movement_speed_modifier", fleeMovementSpeedModifier);
        nbtCompound.putFloat("seek_target_movement_speed_modifier", seekTargetMovementSpeedModifier);
        nbtCompound.putFloat("optimal_block_range_minimum", optimalBlockRangeMinimum);
        nbtCompound.putFloat("optimal_block_range_maximum", optimalBlockRangeMaximum);

        return nbtCompound;
    }

    protected CombatArchetype getCombatArchetype(){
        return CombatArchetype.MELEE;
    };

    public CombatArchetype getArchetype() {
        return this.combatArchetype;
    }
    protected void setArchetype(CombatArchetype combatArchetype) {
        this.combatArchetype = combatArchetype;
    }

    public float getFleeSpeedModifier() {
        return this.fleeMovementSpeedModifier;
    }

    public float getSeekSpeedModifier() {
        return this.seekTargetMovementSpeedModifier;
    }

    public boolean isInOptimalRange(LivingEntity source, BlockPos target) {
        double distance = source.getBlockPos().toCenterPos().distanceTo(target.toCenterPos());
        return distance > optimalBlockRangeMinimum && distance < optimalBlockRangeMaximum;
    }

}
