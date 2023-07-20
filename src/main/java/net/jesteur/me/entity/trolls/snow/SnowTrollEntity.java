package net.jesteur.me.entity.trolls.snow;

import net.jesteur.me.entity.dwarves.durin.DurinDwarfEntity;
import net.jesteur.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jesteur.me.entity.hobbits.HobbitEntity;
import net.jesteur.me.entity.trolls.TrollEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class SnowTrollEntity extends TrollEntity {
    public SnowTrollEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public SnowTrollVariant getVariant() {
        return SnowTrollVariant.byId(this.getId());
    }
}
