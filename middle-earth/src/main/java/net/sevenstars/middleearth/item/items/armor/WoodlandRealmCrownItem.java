package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.CooldownDataComponent;
import net.sevenstars.middleearth.item.dataComponents.SeasonDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.particles.ModParticleTypes;
import net.sevenstars.middleearth.world.biomes.BiomeTagsME;

public class WoodlandRealmCrownItem extends CustomHelmetItem {
    private static final float OFFSET_XZ = 0.5f;
    private static final float OFFSET_Y = -0.3f;

    public WoodlandRealmCrownItem(ExtendedArmorMaterial material, Properties settings) {
        super(material, settings.stacksTo(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean selected) {
        super.inventoryTick(stack, level, entity, slotId, selected);
        if (!(level instanceof ServerLevel world)
                || !(entity instanceof LivingEntity livingEntity)
                || livingEntity.getItemBySlot(EquipmentSlot.HEAD) != stack) {
            return;
        }
        Holder<Biome> biomeEntry = world.getBiome(entity.blockPosition());

        CooldownDataComponent cooldownData = stack.get(DataComponentTypesME.COOLDOWN);
        int cooldown = cooldownData == null ? 5 : cooldownData.cooldown();
        int nextCooldown = Math.max(0, cooldown - 1);

        if(cooldown <= 0 && biomeEntry.unwrapKey().isPresent()) {
            SeasonDataComponent.Season newSeason = SeasonDataComponent.Season.SUMMER;
            if(biomeEntry.is(BiomeTagsME.SPRING)) {
                newSeason = SeasonDataComponent.Season.SPRING;
            } else if(biomeEntry.is(BiomeTagsME.AUTUMN)) {
                newSeason = SeasonDataComponent.Season.AUTUMN;
            } else if(biomeEntry.is(BiomeTagsME.WINTER)) {
                newSeason = SeasonDataComponent.Season.WINTER;
            } else if(biomeEntry.is(BiomeTagsME.DEAD)) {
                newSeason = SeasonDataComponent.Season.DEAD;
            }

            SeasonDataComponent seasonData = stack.get(DataComponentTypesME.SEASON_DATA);
            SeasonDataComponent.Season previousSeason =
                    seasonData == null ? SeasonDataComponent.Season.DEAD : seasonData.season();
            if(previousSeason != newSeason || seasonData == null) {
                nextCooldown = 35;
                double scale = 1.8f * livingEntity.getAttributeValue(Attributes.SCALE);
                Vec3 pos = entity.position().add(0, 1.8f * scale, 0).add(0, -1.1f, 0);

                if(previousSeason == SeasonDataComponent.Season.DEAD && newSeason != SeasonDataComponent.Season.WINTER) {
                    world.sendParticles(ParticleTypes.COMPOSTER, pos.x(), pos.y(), pos.z(), 9, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 1);
                } else if(previousSeason == SeasonDataComponent.Season.SPRING) {
                    world.sendParticles(ParticleTypes.CHERRY_LEAVES, pos.x(), pos.y(), pos.z(), 9, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 1);
                } else if(previousSeason == SeasonDataComponent.Season.SUMMER) {
                    ColorParticleOption leaves = ColorParticleOption.create(
                            ModParticleTypes.TINTED_LEAVES_PARTICLE,
                            biomeEntry.value().getFoliageColor()
                    );
                    world.sendParticles(leaves, pos.x(), pos.y(), pos.z(), 9, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 1);
                } else if(previousSeason == SeasonDataComponent.Season.AUTUMN) {
                    ColorParticleOption leaves = ColorParticleOption.create(
                            ModParticleTypes.TINTED_LEAVES_PARTICLE,
                            8930366
                    );
                    world.sendParticles(leaves, pos.x(), pos.y(), pos.z(), 9, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 1);
                } else if(previousSeason == SeasonDataComponent.Season.WINTER) {
                    world.sendParticles(ParticleTypes.SNOWFLAKE, pos.x(), pos.y(), pos.z(), 12, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 0.05f);
                }

                stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(modelIndex(newSeason)));
                stack.set(DataComponentTypesME.SEASON_DATA, new SeasonDataComponent(newSeason));
            }
        }

        if (cooldownData == null || cooldown != nextCooldown) {
            stack.set(DataComponentTypesME.COOLDOWN, new CooldownDataComponent(nextCooldown));
        }
    }

    private static int modelIndex(SeasonDataComponent.Season season) {
        return switch (season) {
            case DEAD -> 0;
            case SPRING -> 1;
            case SUMMER -> 2;
            case AUTUMN -> 3;
            case WINTER -> 4;
        };
    }
}
