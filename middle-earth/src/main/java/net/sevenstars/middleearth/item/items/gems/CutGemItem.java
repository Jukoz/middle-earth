package net.sevenstars.middleearth.item.items.gems;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.registries.content.gems.GemTypeRegistry;
import net.sevenstars.middleearth.resources.datas.gems.GemType;

import java.util.function.Consumer;

/**
 * A cut gem: one item per cut; the gem type is the {@link DataComponentTypesME#GEM_TYPE} component on the stack.
 * Inscription charges are its durability (max damage = the tier's charges), like the Inscription Table's other
 * catalysts.
 */
public class CutGemItem extends Item {
    private final GemCut cut;

    public CutGemItem(Settings settings, GemCut cut) {
        super(settings.maxDamage(cut.charges()));
        this.cut = cut;
    }

    public GemCut getCut() {
        return cut;
    }

    /** "<Cut> Cut <Gem>", e.g. "Asscher Cut Ruby"; "Asscher Cut Gem" without a gem type. */
    @Override
    public Text getName(ItemStack stack) {
        RegistryKey<GemType> gemType = stack.get(DataComponentTypesME.GEM_TYPE);
        Text gemName = gemType != null
                ? Text.translatable(GemTypeRegistry.translationKey(gemType))
                : Text.translatable("item." + MiddleEarth.MOD_ID + ".cut_gem.no_type");
        return Text.translatable("item." + MiddleEarth.MOD_ID + ".cut_gem.name", Text.translatable(cut.translationKey()), gemName);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent,
                              Consumer<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, tooltip, type);
        tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".cut_gem.tier", cut.tier()).formatted(Formatting.GRAY));
        Integer quality = stack.get(DataComponentTypesME.GEM_QUALITY);
        if (quality != null) {
            net.sevenstars.middleearth.recipe.CraftQuality level = net.sevenstars.middleearth.recipe.CraftQuality.of(quality);
            tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".cut_gem.quality",
                Text.translatable(level.translationKey()).styled(style -> style.withColor(level.color()))).formatted(Formatting.GRAY));
        }
        int max = stack.getMaxDamage();
        tooltip.accept(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".cut_gem.charges", max - stack.getDamage(), max)
                .formatted(Formatting.GRAY));
        RawGemItem.GemTooltips.appendDetails(stack, tooltip);
    }
}
