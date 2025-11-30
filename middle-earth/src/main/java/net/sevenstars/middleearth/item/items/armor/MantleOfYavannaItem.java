package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.TintedParticleEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.SeasonDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import net.sevenstars.middleearth.world.biomes.BiomeTagsME;
import org.jetbrains.annotations.Nullable;

public class MantleOfYavannaItem extends BackAttachmentItem {
    private int cooldown = 0;

    public MantleOfYavannaItem( Settings settings, ExtendedArmorMaterial material) {
        super(settings.armor(material.material(), EquipmentType.CHESTPLATE).maxCount(1), material);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);
        RegistryEntry<Biome> biomeEntry = world.getBiome(entity.getBlockPos());
        cooldown = Math.max(0, cooldown - 1);
        if(biomeEntry.getKey().isPresent()) {
            int color = biomeEntry.value().getFoliageColor();
            stack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(color));
            cooldown = 10;
        }
    }
}
