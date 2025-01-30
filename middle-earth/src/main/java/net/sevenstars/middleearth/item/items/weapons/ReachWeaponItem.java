package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.MEEquipmentTooltip;
import net.sevenstars.middleearth.item.utils.ModWeaponTypes;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ReachWeaponItem extends Item implements MEEquipmentTooltip {

    public static final Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(MiddleEarth.MOD_ID, "entity_interaction_range");
    public float rangeDistance;

    public ModFactions faction;
    public ModSubFactions subFaction;
    public ModWeaponTypes type;

    public ReachWeaponItem(ToolMaterial toolMaterial, ModWeaponTypes type) {
        super(new Settings().attributeModifiers(createAttributeModifiers(toolMaterial, type.attack, type.attackSpeed, type.attackRange)));
        this.rangeDistance = type.attackRange;
        this.faction = ModFactions.NONE;
        this.subFaction = null;
        this.type = type;
    }

    public ReachWeaponItem(ToolMaterial toolMaterial, ModFactions faction, ModWeaponTypes type) {
        super(new Settings().attributeModifiers(createAttributeModifiers(toolMaterial, type.attack, type.attackSpeed, type.attackRange)));
        this.rangeDistance = type.attackRange;
        this.faction = faction;
        this.subFaction = null;
        this.type = type;
    }

    public ReachWeaponItem(ToolMaterial toolMaterial, ModSubFactions subFaction, ModWeaponTypes type) {
        super(new Settings().attributeModifiers(createAttributeModifiers(toolMaterial, type.attack, type.attackSpeed, type.attackRange)));
        this.rangeDistance = type.attackRange;
        this.faction = subFaction.getParent();
        this.subFaction = subFaction;
        this.type = type;
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, float baseAttackDamage, float attackSpeed, float rangeDistance) {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID,
                        (float)baseAttackDamage + material.attackDamageBonus(), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID,
                        attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(ENTITY_INTERACTION_RANGE_MODIFIER_ID,
                        rangeDistance, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    @Override
    public List<Text> getAdditionalShiftLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());

        list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".weapon_type").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.type.name)));

        return list;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        appendBaseTooltip(tooltip, stack, this.faction, this.subFaction);
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
        if(Registries.ITEM.getId(this).getPath().contains("_noble")
                || Registries.ITEM.getId(this).getPath().contains("_elite")
                || Registries.ITEM.getId(this).getPath().contains("uruk_hai")
                || Registries.ITEM.getId(this).getPath().contains("heyday")
                || Registries.ITEM.getId(this).getPath().contains("numenorean")){
            return Text.translatable(this.getTranslationKey()).formatted(Formatting.GOLD);
        }
        return super.getName(stack);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        ToolComponent toolComponent = (ToolComponent)stack.get(DataComponentTypes.TOOL);
        if (toolComponent == null) {
            return false;
        } else {
            if (!world.isClient && state.getHardness(world, pos) != 0.0F && toolComponent.damagePerBlock() > 0) {
                stack.damage(1, miner, EquipmentSlot.MAINHAND);
            }

            return true;
        }
    }
}
