package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.particle.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.SeasonDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.world.biomes.BiomeTagsME;
import org.jetbrains.annotations.Nullable;

public class WoodlandRealmCrownItem extends CustomHelmetItem {
    private static final float OFFSET_XZ = 0.5f;
    private static final float OFFSET_Y = -0.3f;
    private SeasonDataComponent.Season season = SeasonDataComponent.Season.DEAD;

    public WoodlandRealmCrownItem(ExtendedArmorMaterial material, Settings settings) {
        super(material, settings.armor(material.material(), EquipmentType.HELMET).maxCount(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);
        RegistryEntry<Biome> biomeEntry = world.getBiome(entity.getBlockPos());
        if(biomeEntry.getKey().isPresent()) {

            SeasonDataComponent.Season newSeason = SeasonDataComponent.Season.SUMMER;
            if(biomeEntry.isIn(BiomeTagsME.SPRING)) {
                newSeason = SeasonDataComponent.Season.SPRING;
            } else if(biomeEntry.isIn(BiomeTagsME.AUTUMN)) {
                newSeason = SeasonDataComponent.Season.AUTUMN;
            } else if(biomeEntry.isIn(BiomeTagsME.WINTER)) {
                newSeason = SeasonDataComponent.Season.WINTER;
            } else if(biomeEntry.isIn(BiomeTagsME.DEAD)) {
                newSeason = SeasonDataComponent.Season.DEAD;
            }

            if(season != newSeason) {
                LivingEntity livingEntity = (LivingEntity) entity;
                double scale = 1.8f * livingEntity.getAttributeValue(EntityAttributes.SCALE);
                Vec3d pos = entity.getPos().add(0, 1.8f * scale, 0).add(0, -1.1f, 0);

                if(season.equals(SeasonDataComponent.Season.DEAD) && !newSeason.equals(SeasonDataComponent.Season.WINTER)) {
                    world.spawnParticles(ParticleTypes.COMPOSTER, pos.getX(), pos.getY(), pos.getZ(), 9, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 1);
                } else if(season.equals(SeasonDataComponent.Season.SPRING)) {
                    world.spawnParticles(ParticleTypes.CHERRY_LEAVES, pos.getX(), pos.getY(), pos.getZ(), 9, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 1);
                } else if(season.equals(SeasonDataComponent.Season.SUMMER)) {
                    int leavesColor = world.getBiome(entity.getBlockPos()).value().getFoliageColor();
                    TintedParticleEffect tintedParticleEffect = TintedParticleEffect.create(ParticleTypes.TINTED_LEAVES, leavesColor);
                    world.spawnParticles((ParticleEffect)tintedParticleEffect, pos.getX(), pos.getY(), pos.getZ(), 9, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 1);
                } else if(season.equals(SeasonDataComponent.Season.AUTUMN)) {
                    TintedParticleEffect tintedParticleEffect = TintedParticleEffect.create(ParticleTypes.TINTED_LEAVES, 8930366);
                    world.spawnParticles((ParticleEffect)tintedParticleEffect, pos.getX(), pos.getY(), pos.getZ(), 9, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 1);
                } else if(season.equals(SeasonDataComponent.Season.WINTER)) {
                    world.spawnParticles(ParticleTypes.SNOWFLAKE, pos.getX(), pos.getY(), pos.getZ(), 12, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 0.05f);
                }
            }

            season = newSeason;
            stack.set(DataComponentTypesME.SEASON_DATA, new SeasonDataComponent(newSeason));
        }
    }
}
