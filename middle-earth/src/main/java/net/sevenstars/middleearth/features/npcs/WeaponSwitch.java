package net.sevenstars.middleearth.features.npcs;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.utils.ItemTagsME;

// Shoots from afar, swaps to melee when a foe is close. The npc_ranged_weapons tag says what's ranged
public final class WeaponSwitch {
    // In blocks. Two distances so a foe at the edge doesn't cause swapping back and forth
    private static final double CLOSE_QUARTERS = 3.5;
    private static final double SHOOTING_RANGE = 6;

    private WeaponSwitch() {}

    public static boolean isRanged(ItemStack stack) {
        return !stack.isEmpty() && stack.isIn(ItemTagsME.NPC_RANGED_WEAPONS);
    }

    public static void tick(NpcEntity npc) {
        LivingEntity target = npc.getTarget();
        // A routine prop isn't a weapon; it's put away when the fight starts
        if(target == null || npc.hasRoutineProp())
            return;
        ItemStack main = npc.getMainHandStack();
        ItemStack off = npc.getOffHandStack();
        if(off.isEmpty() || isRanged(main) == isRanged(off))
            return;
        double distance = npc.squaredDistanceTo(target);
        boolean swap = isRanged(main) ? distance < CLOSE_QUARTERS * CLOSE_QUARTERS : distance > SHOOTING_RANGE * SHOOTING_RANGE;
        if(!swap)
            return;
        npc.equipStack(EquipmentSlot.MAINHAND, off);
        npc.equipStack(EquipmentSlot.OFFHAND, main);
        npc.updateAttackType();
    }
}
