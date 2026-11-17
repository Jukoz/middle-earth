package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.utils.ItemTagsME;

// An npc holding a ranged weapon in one hand and a melee weapon in the other shoots from afar, swaps to the melee
// weapon when a foe gets close, and swaps back once the foe moves off. The middle-earth:npc_ranged_weapons tag decides
// which weapons count as ranged for swapping. The attack still comes from the bow and crossbow tags
public final class WeaponSwitch {
    // In blocks. The two distances differ so an npc with a foe at the edge doesn't swap back and forth
    private static final double CLOSE_QUARTERS = 3.5;
    private static final double SHOOTING_RANGE = 6;

    private WeaponSwitch() {}

    public static boolean isRanged(ItemStack stack) {
        return !stack.isEmpty() && stack.isIn(ItemTagsME.NPC_RANGED_WEAPONS);
    }

    public static void tick(NpcEntity npc) {
        LivingEntity target = npc.getTarget();
        // A routine prop isn't a weapon to swap to. It gets put away when the fight takes over
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
