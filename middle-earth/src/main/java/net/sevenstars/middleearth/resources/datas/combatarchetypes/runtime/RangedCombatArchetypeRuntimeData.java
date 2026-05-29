package net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime;

import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.MeleeCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.RangedCombatArchetypeData;

public class RangedCombatArchetypeRuntimeData extends CombatArchetypeRuntimeData {
    public int currentAmmoCount;
    public int lastHurtTick;
    public int lastShootTick;
    public int currentReplenish;

    public RangedCombatArchetypeRuntimeData(RangedCombatArchetypeData rangedCombatArchetypeData){
        super(rangedCombatArchetypeData);
        currentAmmoCount = rangedCombatArchetypeData.getAmmoCount();
    }

    @Override
    public void tick(NpcEntity npcEntity, World world){
        super.tick(npcEntity, world);
        if(this.lastShootTick < 1000)
            this.lastShootTick ++;
        if(this.lastHurtTick < 1000)
            this.lastHurtTick ++;
    }

    @Override
    public MeleeCombatArchetypeData getCombatArchetypeData() {
        return (MeleeCombatArchetypeData) combatArchetypeData;
    }

    public boolean canShoot(NpcEntity entity){
        if(entity.getLastAttackTime() > 5)
            return this.currentAmmoCount > 0;
        return false;
    }

    public void shoot(){
        this.lastShootTick = 0;
        this.currentAmmoCount --;
    }

    public void hurt(){
        this.lastHurtTick = 0;
    }
}
