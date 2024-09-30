package net.jukoz.me.item.items.weapons;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.ModWeaponTypes;
import net.jukoz.me.utils.ModFactions;
import net.jukoz.me.utils.ModSubFactions;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;

public class CustomDaggerWeaponItem extends ReachWeaponItem {
    public static final Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(MiddleEarth.MOD_ID, "entity_interaction_range");

    public CustomDaggerWeaponItem(ToolMaterial toolMaterial) {
        super(toolMaterial, ModWeaponTypes.DAGGER);
    }

    public CustomDaggerWeaponItem(ToolMaterial toolMaterial, ModFactions faction) {
        super(toolMaterial, faction, ModWeaponTypes.DAGGER);
    }

    public CustomDaggerWeaponItem(ToolMaterial toolMaterial, ModSubFactions subFaction) {
        super(toolMaterial, subFaction, ModWeaponTypes.DAGGER);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.of(""));
        if (Screen.hasShiftDown()) {
            if(this.type != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + this.type.name));
            }
            if(this.faction != null){
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + faction.getName())));
            }
            if (this.subFaction != null) {
                tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + subFaction.getName())));
            }
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".backstab"));

        } else {
            tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }

    public static boolean canBackStab(Entity target, Entity attacker) {
        Vector2f direction = new Vector2f((float) target.getRotationVector().x, (float) target.getRotationVector().z).normalize();
        Vector3f attackerPosDifference = target.getPos().add(attacker.getPos().multiply(-1)).toVector3f();
        Vector2f attackerDirection = new Vector2f(attackerPosDifference.x, attackerPosDifference.z).normalize();
        float dotProduct = direction.dot(attackerDirection);
        return dotProduct > 0.2f;
    }
}
