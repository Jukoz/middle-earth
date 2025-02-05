package net.sevenstars.middleearth.item.items;

import net.sevenstars.middleearth.item.utils.ModToolMaterials;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SmithingHammerItem extends ToolItem {

    public SmithingHammerItem(Settings settings, ModToolMaterials material, float speed) {
        super(material, settings.maxCount(1).attributeModifiers(createAttributeModifiers(speed)));
    }

    public static AttributeModifiersComponent createAttributeModifiers(float attackSpeed) {
        return AttributeModifiersComponent.builder().add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return false;
    }
}
