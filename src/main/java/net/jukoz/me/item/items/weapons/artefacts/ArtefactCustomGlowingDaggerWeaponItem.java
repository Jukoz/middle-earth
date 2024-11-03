package net.jukoz.me.item.items.weapons.artefacts;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.orcs.OrcNpcEntity;
import net.jukoz.me.entity.uruks.UrukNpcEntity;
import net.jukoz.me.item.items.weapons.CustomDaggerWeaponItem;
import net.jukoz.me.item.items.weapons.utils.ArtefactUtils;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ArtefactCustomGlowingDaggerWeaponItem extends CustomDaggerWeaponItem {
    public static final Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(MiddleEarth.MOD_ID, "entity_interaction_range");

    public boolean glowing;
    private int counter = 0;

    public ArtefactCustomGlowingDaggerWeaponItem(ToolMaterial toolMaterial) {
        super(toolMaterial);
    }

    public ArtefactCustomGlowingDaggerWeaponItem(ToolMaterial toolMaterial, ModFactions faction) {
        super(toolMaterial, faction);
    }

    public ArtefactCustomGlowingDaggerWeaponItem(ToolMaterial toolMaterial, ModSubFactions subFaction) {
        super(toolMaterial, subFaction);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        ArtefactCustomGlowingDaggerWeaponItem item = (ArtefactCustomGlowingDaggerWeaponItem) stack.getItem();
        item.glowing = shouldBeGlowing(world, entity);
    }

    public static boolean shouldBeGlowing(World world, Entity entity){
        int range = 50;
        if (entity != null){
            return ArtefactUtils.isInBound(world, entity, OrcNpcEntity.class, range)
                    || ArtefactUtils.isInBound(world, entity, UrukNpcEntity.class, range);
        }
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of(""));
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".artefact").formatted(Formatting.AQUA));
            if(!(stack.getDamage() < stack.getMaxDamage() - 1)){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".artefact_broken").formatted(Formatting.ITALIC).append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".artefact")).formatted(Formatting.AQUA));
            } else {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".artefact").formatted(Formatting.AQUA));
            }
            if(this.type != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".weapon_type").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.type.name)));
            }
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".backstab"));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey(stack)).formatted(Formatting.AQUA).formatted(Formatting.ITALIC);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
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
        if (stack.getDamage() < stack.getMaxDamage() - 1){
            stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        }
        if (stack.getDamage() == stack.getMaxDamage() - 1){
            stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.builder()
                    .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID,
                            0.0f, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                    .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID,
                            -3.0f, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                    .add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(ENTITY_INTERACTION_RANGE_MODIFIER_ID,
                            0.0f, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                    .build());
        }
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        ToolComponent toolComponent = (ToolComponent)stack.get(DataComponentTypes.TOOL);
        if (toolComponent == null) {
            return false;
        } else {
            if (!world.isClient && state.getHardness(world, pos) != 0.0F && toolComponent.damagePerBlock() > 0) {
                if (stack.getDamage() < stack.getMaxDamage() - 1){
                    stack.damage(1, miner, EquipmentSlot.MAINHAND);
                }
                if (stack.getDamage() == stack.getMaxDamage() - 1){
                    stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.builder()
                            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID,
                                    0.0f, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                            .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID,
                                    -3.0f, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                            .add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(ENTITY_INTERACTION_RANGE_MODIFIER_ID,
                                    0.0f, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                            .build());
                }
            }
            return true;
        }
    }
}
