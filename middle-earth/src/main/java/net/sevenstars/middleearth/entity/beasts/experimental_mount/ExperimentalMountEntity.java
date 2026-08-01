package net.sevenstars.middleearth.entity.beasts.experimental_mount;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.RideableInventory;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sevenstars.api.entity.AbstractMountEntity;
import org.jetbrains.annotations.Nullable;

public class ExperimentalMountEntity extends AbstractMountEntity {
    protected SimpleInventory items;

    public ExperimentalMountEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createAnimalAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0.1f)
                .add(EntityAttributes.MAX_HEALTH, 20.0f)
                .add(EntityAttributes.ATTACK_DAMAGE, 10.0f)
                .add(EntityAttributes.SAFE_FALL_DISTANCE, 10.0f)
                .add(EntityAttributes.STEP_HEIGHT, 1.0f);
    }
}
