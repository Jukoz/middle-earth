package net.sevenstars.middleearth.item.items.weapons.artefacts;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.sevenstars.middleearth.item.items.weapons.CustomDaggerWeaponItem;

public class WeaverStingItem extends CustomDaggerWeaponItem {
    public WeaverStingItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 50));
    }
}
