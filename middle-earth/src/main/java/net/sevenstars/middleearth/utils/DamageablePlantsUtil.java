package net.sevenstars.middleearth.utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.snow.SnowTrollEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.stone.StoneTrollEntity;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaEntity;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerEntity;
import net.sevenstars.middleearth.entity.spider.spawn.SpawnOfShelobEntity;

public class DamageablePlantsUtil {
    public static void tryDamageEntity(LivingEntity livingEntity, ServerWorld serverWorld, DamageSource damageSource) {
        if(isEntityImmune(livingEntity))
            return;

        if(isEntityArmored(livingEntity))
            return;

        livingEntity.damage(serverWorld, damageSource, 1.0F);
    }

    private static boolean isEntityArmored(LivingEntity livingEntity) {
        return livingEntity.getArmor() > 8;
    }

    private static boolean isEntityImmune(LivingEntity livingEntity) {
        // Shelob entities
        if(livingEntity instanceof ShelobiteLarvaEntity || livingEntity instanceof ShelobiteScuttlerEntity || livingEntity instanceof SpawnOfShelobEntity){
            return true;
        }

        // Trolls
        if(livingEntity instanceof CaveTrollEntity || livingEntity instanceof StoneTrollEntity || livingEntity instanceof SnowTrollEntity){
            return true;
        }

        return false;
    }
}
