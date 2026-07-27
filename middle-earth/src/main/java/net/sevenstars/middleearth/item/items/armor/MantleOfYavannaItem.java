package net.sevenstars.middleearth.item.items.armor;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.dataComponents.BackAttachmentDataComponent;
import net.sevenstars.middleearth.item.utils.armor.ExtendedArmorMaterial;

import java.awt.*;

public class MantleOfYavannaItem extends BackAttachmentItem {
    private static final float COLOR_LERP = 0.06f;
    private static final int DEFAULT_FOLIAGE = 5875248;

    public MantleOfYavannaItem(Properties settings, ExtendedArmorMaterial material) {
        super(settings.stacksTo(1), material);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean selected) {
        super.inventoryTick(stack, level, entity, slotId, selected);
        if (!(level instanceof ServerLevel world)) {
            return;
        }
        DyedItemColor dyedColorComponent = stack.get(DataComponents.DYED_COLOR);
        if(dyedColorComponent == null) return;

        int targetColor = 4624722;
        int currentColor = dyedColorComponent.rgb();
        Holder<Biome> biomeEntry = world.getBiome(entity.blockPosition());

        if(biomeEntry.unwrapKey().isPresent()) {
            int color = biomeEntry.value().getFoliageColor();
            targetColor = color;
        }

        if(currentColor != targetColor) {
            Color colorA = new Color(stack.get(DataComponents.DYED_COLOR).rgb());
            Color colorB = new Color(targetColor);
            Color lerpColor = new Color(Mth.lerpInt(COLOR_LERP, colorA.getRed(), colorB.getRed()),
                    Mth.lerpInt(COLOR_LERP, colorA.getGreen(), colorB.getGreen()),
                    Mth.lerpInt(COLOR_LERP, colorA.getBlue(), colorB.getBlue()));
            currentColor = lerpColor.getRGB();
            stack.set(DataComponents.DYED_COLOR, new DyedItemColor(currentColor, true));
        }
    }

    public static ItemStack applyBackAttachmentColor(ItemStack stack, ServerLevel world, Entity entity, BackAttachmentDataComponent backAttachmentDataComponent) {
        int targetColor;
        int currentColor = backAttachmentDataComponent.backAttachmentColor();
        Holder<Biome> biomeEntry = world.getBiome(entity.blockPosition());

        if(biomeEntry.unwrapKey().isPresent()) {
            int color = biomeEntry.value().getFoliageColor();
            targetColor = color;
            if(targetColor == 0) targetColor = DEFAULT_FOLIAGE;

            if(currentColor != targetColor) {
                Color colorA = new Color(currentColor);
                Color colorB = new Color(targetColor);
                Color lerpColor = new Color(Mth.lerpInt(COLOR_LERP, colorA.getRed(), colorB.getRed()),
                        Mth.lerpInt(COLOR_LERP, colorA.getGreen(), colorB.getGreen()),
                        Mth.lerpInt(COLOR_LERP, colorA.getBlue(), colorB.getBlue()));
                currentColor = lerpColor.getRGB();

                stack.set(DataComponentTypesME.BACK_ATTACHMENT_DATA, new BackAttachmentDataComponent(backAttachmentDataComponent.backAttachment(), currentColor));
            }
        }
        return stack;
    }
}
