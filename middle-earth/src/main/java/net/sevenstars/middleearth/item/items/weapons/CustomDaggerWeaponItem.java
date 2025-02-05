package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.item.Item;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.MEEquipmentTooltip;
import net.sevenstars.middleearth.item.utils.ModWeaponTypes;
import net.sevenstars.middleearth.utils.ModFactions;
import net.sevenstars.middleearth.utils.ModSubFactions;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class CustomDaggerWeaponItem extends ReachWeaponItem implements MEEquipmentTooltip {
    public static final Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(MiddleEarth.MOD_ID, "entity_interaction_range");

    public CustomDaggerWeaponItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(toolMaterial, ModWeaponTypes.DAGGER, settings);
    }

    public CustomDaggerWeaponItem(ToolMaterial toolMaterial, ModFactions faction, Item.Settings settings) {
        super(toolMaterial, faction, ModWeaponTypes.DAGGER, settings);
    }

    public CustomDaggerWeaponItem(ToolMaterial toolMaterial, ModSubFactions subFaction, Item.Settings settings) {
        super(toolMaterial, subFaction, ModWeaponTypes.DAGGER, settings);
    }

    @Override
    public List<Text> getAdditionalShiftLines(ItemStack stack) {
        List<Text> list = new ArrayList<>(List.of());

        list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".weapon_type").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.type.name)));
        list.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".backstab"));

        return list;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        appendBaseTooltip(tooltip, stack, this.faction, this.subFaction);
    }

    public static boolean canBackStab(Entity target, Entity attacker) {
        Vector2f direction = new Vector2f((float) target.getRotationVector().x, (float) target.getRotationVector().z).normalize();
        Vector3f attackerPosDifference = target.getPos().add(attacker.getPos().multiply(-1)).toVector3f();
        Vector2f attackerDirection = new Vector2f(attackerPosDifference.x, attackerPosDifference.z).normalize();
        float dotProduct = direction.dot(attackerDirection);
        return dotProduct > 0.2f;
    }
}
