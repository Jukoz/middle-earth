package net.sevenstars.middleearth.entity.npcs;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.sevenstars.middleearth.utils.ItemTagsME;

// Any npc given a ranged weapon in one hand and one for close quarters in the other shoots from afar and takes the close
// one in hand when a foe comes near, and the ranged one again once it is off. Which weapons are ranged is the
// middle-earth:npc_ranged_weapons tag, so another kind is a line in the tag
public final class WeaponSwitch {
    // In blocks, apart so that a foe at the edge does not have the npc swap back and forth
    private static final double CLOSE_QUARTERS = 3.5;
    private static final double SHOOTING_RANGE = 6;

    private WeaponSwitch() {}

    public static boolean isRanged(ItemStack stack) {
        return !stack.isEmpty() && stack.isIn(ItemTagsME.NPC_RANGED_WEAPONS);
    }

    public static void tick(NpcEntity npc) {
        LivingEntity target = npc.getTarget();
        if(target == null)
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
