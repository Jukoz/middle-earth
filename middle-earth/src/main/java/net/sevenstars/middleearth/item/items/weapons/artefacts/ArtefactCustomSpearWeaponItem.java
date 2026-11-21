package net.sevenstars.middleearth.item.items.weapons.artefacts;

import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.item.items.weapons.CustomSpearWeaponItem;
import net.sevenstars.middleearth.item.utils.WeaponTypesME;

public class ArtefactCustomSpearWeaponItem extends CustomSpearWeaponItem {

    public ArtefactCustomSpearWeaponItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(toolMaterial, settings);
        this.type = WeaponTypesME.SPEAR;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        if(stack.getDamage() == stack.getMaxDamage() - 1) {
            return false;
        } else if( stack.getDamage() >= 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.getDamage() == stack.getMaxDamage() - 1){
            stack.remove(DataComponentTypes.WEAPON);
        }

    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        ToolComponent toolComponent = (ToolComponent)stack.get(DataComponentTypes.TOOL);
        if (toolComponent == null) {
            return false;
        } else {
            if (!world.isClient() && state.getHardness(world, pos) != 0.0F && toolComponent.damagePerBlock() > 0) {
                if (stack.getDamage() == stack.getMaxDamage() - 1){
                    stack.remove(DataComponentTypes.WEAPON);
                }
            }
            return true;
        }
    }
}
