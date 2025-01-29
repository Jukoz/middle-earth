package net.sevenstars.middleearth.item.items;

import net.minecraft.item.Item;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.registry.tag.BlockTags;
import net.sevenstars.middleearth.item.utils.ModToolMaterials;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SmithingHammerItem extends MiningToolItem {

    public SmithingHammerItem(Settings settings, ModToolMaterials material, float speed) {
        super(material, BlockTags.PICKAXE_MINEABLE, 0.0f, 0.0f, settings.maxCount(1).attributeModifiers(createAttributeModifiers(speed)));
    }

    public static AttributeModifiersComponent createAttributeModifiers(float attackSpeed) {
        return AttributeModifiersComponent.builder().add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return false;
    }
}
