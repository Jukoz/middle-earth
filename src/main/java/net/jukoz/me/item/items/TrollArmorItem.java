package net.jukoz.me.item.items;

import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.jukoz.me.MiddleEarth;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TrollArmorItem extends AnimalArmorItem {

    private static final String ENTITY_TEXTURE_PREFIX = "textures/entity/troll/";
    private final String entityTexture;


    public TrollArmorItem(RegistryEntry<ArmorMaterial> material, Type type, boolean hasOverlay, Settings settings) {
        super(material, type, hasOverlay, settings);
        this.entityTexture = "textures/entity/troll/armor/troll_armor_" + name + ".png";
    }

    public Identifier getEntityTexture() {
        return new Identifier(MiddleEarth.MOD_ID ,this.entityTexture);
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
    public AttributeModifiersComponent getAttributeModifiers(ItemStack stack) {
        return super.getAttributeModifiers(stack);
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        return super.getRecipeRemainder(stack);
    }

    @Override
    public boolean canBeEnchantedWith(ItemStack stack, Enchantment enchantment, EnchantingContext context) {
        return super.canBeEnchantedWith(stack, enchantment, context);
    }

    @Override
    public TypedActionResult<ItemStack> equipAndSwap(Item item, World world, PlayerEntity user, Hand hand) {
        return super.equipAndSwap(item, world, user, hand);
    }

    @Override
    public boolean isEnabled(FeatureSet enabledFeatures) {
        return super.isEnabled(enabledFeatures);
    }
}
