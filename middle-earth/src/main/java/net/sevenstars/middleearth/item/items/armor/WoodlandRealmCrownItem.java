package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BiomeDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import org.jetbrains.annotations.Nullable;

public class WoodlandRealmCrownItem extends CustomHelmetItem {

    public WoodlandRealmCrownItem(ExtendedArmorMaterial material, Settings settings) {
        super(material, settings.armor(material.material(), EquipmentType.HELMET).maxCount(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);
        RegistryEntry<Biome> biome = world.getBiome(entity.getBlockPos());
        if(biome.getKey().isPresent()) {
            stack.set(DataComponentTypesME.BIOME_DATA, new BiomeDataComponent(biome.getKey().get().getValue()));
        }
    }
}
