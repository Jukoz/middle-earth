package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.SeasonDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.world.biomes.BiomeTagsME;
import org.jetbrains.annotations.Nullable;

public class WoodlandRealmCrownItem extends CustomHelmetItem {

    public WoodlandRealmCrownItem(ExtendedArmorMaterial material, Settings settings) {
        super(material, settings.armor(material.material(), EquipmentType.HELMET).maxCount(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);
        RegistryEntry<Biome> biomeEntry = world.getBiome(entity.getBlockPos());
        if(biomeEntry.getKey().isPresent()) {
            SeasonDataComponent seasonDataComponent = new SeasonDataComponent(SeasonDataComponent.Season.SUMMER);

            if(biomeEntry.isIn(BiomeTagsME.SPRING)) {
                seasonDataComponent = new SeasonDataComponent(SeasonDataComponent.Season.SPRING);
            } else if(biomeEntry.isIn(BiomeTagsME.AUTUMN)) {
                seasonDataComponent = new SeasonDataComponent(SeasonDataComponent.Season.AUTUMN);
            } else if(biomeEntry.isIn(BiomeTagsME.WINTER)) {
                seasonDataComponent = new SeasonDataComponent(SeasonDataComponent.Season.WINTER);
            } else if(biomeEntry.isIn(BiomeTagsME.DEAD)) {
                seasonDataComponent = new SeasonDataComponent(SeasonDataComponent.Season.DEAD);
            }

            stack.set(DataComponentTypesME.SEASON_DATA, seasonDataComponent);
        }
    }
}
