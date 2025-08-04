package net.sevenstars.middleearth.item.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.utils.ModWeaponTypes;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class CustomDaggerWeaponItem extends ReachWeaponItem {
    public static final Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.of(MiddleEarth.MOD_ID, "entity_interaction_range");

    public CustomDaggerWeaponItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(toolMaterial, ModWeaponTypes.DAGGER, settings);
        this.type = ModWeaponTypes.DAGGER;
    }

    public static boolean canBackStab(Entity target, Entity attacker) {
        Vector2f direction = new Vector2f((float) target.getRotationVector().x, (float) target.getRotationVector().z).normalize();
        Vector3f attackerPosDifference = target.getPos().add(attacker.getPos().multiply(-1)).toVector3f();
        Vector2f attackerDirection = new Vector2f(attackerPosDifference.x, attackerPosDifference.z).normalize();
        float dotProduct = direction.dot(attackerDirection);
        return dotProduct > 0.1f;
    }
}
