package net.jukoz.me.item.items.weapons;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.ModWeaponTypes;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ReachWeaponItem extends ToolItem {

    public static final Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(MiddleEarth.MOD_ID, "entity_interaction_range");
    public float rangeDistance;

    public MutableText faction;
    public MutableText subFaction;
    public ModWeaponTypes type;

    public ReachWeaponItem(ToolMaterial toolMaterial, ModWeaponTypes type) {
        super(toolMaterial, new Item.Settings().attributeModifiers(createAttributeModifiers(toolMaterial, type.attack, type.attackSpeed, type.attackRange)));
        this.rangeDistance = type.attackRange;
        this.faction = Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".generic");
        this.subFaction = null;
        this.type = type;
    }

    public ReachWeaponItem(ToolMaterial toolMaterial, MutableText faction, ModWeaponTypes type) {
        super(toolMaterial, new Item.Settings().attributeModifiers(createAttributeModifiers(toolMaterial, type.attack, type.attackSpeed, type.attackRange)));
        this.rangeDistance = type.attackRange;
        this.faction = faction;
        this.subFaction = null;
        this.type = type;
    }

    public ReachWeaponItem(ToolMaterial toolMaterial, MutableText faction, MutableText subFaction, ModWeaponTypes type) {
        super(toolMaterial, new Item.Settings().attributeModifiers(createAttributeModifiers(toolMaterial, type.attack, type.attackSpeed, type.attackRange)));
        this.rangeDistance = type.attackRange;
        this.faction = faction;
        this.subFaction = subFaction;
        this.type = type;
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, float baseAttackDamage, float attackSpeed, float rangeDistance) {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID,
                        (float)baseAttackDamage + material.getAttackDamage(), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID,
                        attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(ENTITY_INTERACTION_RANGE_MODIFIER_ID,
                        rangeDistance, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of(""));
        ProfileComponent profileComponent = stack.get(DataComponentTypes.PROFILE);
        if (Screen.hasShiftDown()) {
            if(this.type != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".weapon_type").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.type.name)));
            }
            if(this.faction != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(this.faction));
            }
            if (this.subFaction != null) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(this.subFaction));
            }
            if (profileComponent != null && profileComponent.name().isPresent()) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".artisan").append(profileComponent.name().get()).formatted(Formatting.GRAY));
            }
            tooltip.add(Text.of(""));
        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    public float getRangeDistance() {
        return rangeDistance;
    }

    public ModWeaponTypes getType() {
        return type;
    }

    @Override
    public Text getName(ItemStack stack) {
        if(Registries.ITEM.getId(stack.getItem()).getPath().contains("_noble") || Registries.ITEM.getId(stack.getItem()).getPath().contains("_elite")){
            return Text.translatable(this.getTranslationKey(stack)).formatted(Formatting.GOLD);
        }
        return super.getName(stack);
    }
}
