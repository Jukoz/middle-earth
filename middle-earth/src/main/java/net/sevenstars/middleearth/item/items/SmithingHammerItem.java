package net.sevenstars.middleearth.item.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SmithingHammerItem extends Item {
    private final int enchantmentValue;

    public SmithingHammerItem(Properties settings, Tier material, float speed, int enchantmentValue) {
        super(settings.stacksTo(1).attributes(createAttributeModifiers(speed)).durability(material.getUses()));
        this.enchantmentValue = enchantmentValue;
    }

    public static ItemAttributeModifiers createAttributeModifiers(float attackSpeed) {
        return ItemAttributeModifiers.builder().add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level world, BlockPos pos, net.minecraft.world.entity.player.Player user) {
        return !user.isCreative();
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }
}
