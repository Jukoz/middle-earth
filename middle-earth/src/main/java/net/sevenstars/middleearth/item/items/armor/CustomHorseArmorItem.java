package net.sevenstars.middleearth.item.items.armor;

import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.Hand;

public class CustomHorseArmorItem extends Item {

    public CustomHorseArmorItem(ArmorMaterial material, Settings settings) {
        super(settings.horseArmor(material));
    }

    @Override
    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return super.allowComponentsUpdateAnimation(player, hand, oldStack, newStack);
    }

    @Override
    public boolean allowContinuingBlockBreaking(PlayerEntity player, ItemStack oldStack, ItemStack newStack) {
        return super.allowContinuingBlockBreaking(player, oldStack, newStack);
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        return super.getRecipeRemainder(stack);
    }

    @Override
    public boolean canBeEnchantedWith(ItemStack stack, RegistryEntry<Enchantment> enchantment, EnchantingContext context) {
        return super.canBeEnchantedWith(stack, enchantment, context);
    }

    @Override
    public boolean isEnabled(FeatureSet enabledFeatures) {
        return super.isEnabled(enabledFeatures);
    }
}
