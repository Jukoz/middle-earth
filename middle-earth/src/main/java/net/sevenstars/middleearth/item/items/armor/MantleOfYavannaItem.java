package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class MantleOfYavannaItem extends BackAttachmentItem {
    private static final float COLOR_LERP = 0.07f;
    private int currentColor = 0x375729;
    private int targetColor = 0x375729;

    public MantleOfYavannaItem( Settings settings, ExtendedArmorMaterial material) {
        super(settings.armor(material.material(), EquipmentType.CHESTPLATE).maxCount(1), material);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);
        RegistryEntry<Biome> biomeEntry = world.getBiome(entity.getBlockPos());

        if(biomeEntry.getKey().isPresent()) {
            int color = biomeEntry.value().getFoliageColor();
            if(targetColor != color) {
                targetColor = color;
            }
        }

        if(currentColor != targetColor) {
            Color colorA = new Color(currentColor);
            Color colorB = new Color(targetColor);
            Color lerpColor = new Color(MathHelper.lerp(COLOR_LERP, colorA.getRed(), colorB.getRed()),
                    MathHelper.lerp(COLOR_LERP, colorA.getGreen(), colorB.getGreen()),
                    MathHelper.lerp(COLOR_LERP, colorA.getBlue(), colorB.getBlue()));
            currentColor = lerpColor.getRGB();
            stack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(currentColor));
        }
    }

    public static ItemStack applyBackAttachmentColor(ItemStack stack, ServerWorld world, Entity entity, BackAttachmentDataComponent backAttachmentDataComponent) {
        int targetColor;
        int currentColor = backAttachmentDataComponent.backAttachmentColor();
        RegistryEntry<Biome> biomeEntry = world.getBiome(entity.getBlockPos());

        if(biomeEntry.getKey().isPresent()) {
            int color = biomeEntry.value().getFoliageColor();
            targetColor = color;

            if(currentColor != targetColor) {
                Color colorA = new Color(currentColor);
                Color colorB = new Color(targetColor);
                Color lerpColor = new Color(MathHelper.lerp(COLOR_LERP, colorA.getRed(), colorB.getRed()),
                        MathHelper.lerp(COLOR_LERP, colorA.getGreen(), colorB.getGreen()),
                        MathHelper.lerp(COLOR_LERP, colorA.getBlue(), colorB.getBlue()));
                currentColor = lerpColor.getRGB();

                stack.set(DataComponentTypesME.BACK_ATTACHMENT_DATA, new BackAttachmentDataComponent(backAttachmentDataComponent.backAttachment(), currentColor));
            }
        }
        return stack;
    }
}
