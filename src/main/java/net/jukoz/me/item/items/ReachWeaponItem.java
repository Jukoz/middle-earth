package net.jukoz.me.item.items;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class ReachWeaponItem extends SwordItem {
    private final float rangeDistance;
    public ReachWeaponItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, float rangeDistance, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.rangeDistance = rangeDistance;
    }

    public float getRangeDistance() {
        return rangeDistance;
    }
}
