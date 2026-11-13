package net.sevenstars.middleearth.item.items.gems;

import net.sevenstars.middleearth.recipe.lapidary.StudyGrade;
import net.sevenstars.middleearth.MiddleEarth;

import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.recipe.RecipeSerializerRegistryME;

import java.util.function.Consumer;

import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.resources.datas.gems.GemType;
import net.sevenstars.middleearth.registries.content.gems.GemTypeRegistry;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * Notes written up from a finished study at the Lapidary Table, e.g. "Careful Ruby Notes". One item per grade;
 * the gem comes from the {@link DataComponentTypesME#GEM_TYPE} component. Bound into Middle-earth's Player Book at a
 * crafting table ({@link GemStudiesBookRecipe}), they add what that grade teaches to the book's Gem Studies.
 */
public class GemNotesItem extends Item {
    private final StudyGrade grade;

    public GemNotesItem(Settings settings, StudyGrade grade) {
        super(settings);
        this.grade = grade;
    }

    public StudyGrade getGrade() {
        return grade;
    }

    @Override
    public Text getName(ItemStack stack) {
        RegistryKey<GemType> gem = stack.get(DataComponentTypesME.GEM_TYPE);
        // Named for their gem in each grade's own wording (e.g. "Careful %s Notes"); "Gem" if they name none.
        Text gemName = gem == null ? Text.translatable("item.middle-earth.gem_notes.any_gem")
            : Text.translatable(GemTypeRegistry.translationKey(gem));
        Text name = Text.translatable(getTranslationKey() + ".gem", gemName);
        return name.copy().formatted(grade.formatting());
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return grade == StudyGrade.MASTERFUL || super.hasGlint(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent,
                              Consumer<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, tooltip, type);
        // Just what the notes cover; the text itself is read in the Player Book.
        tooltip.accept(Text.translatable("tooltip.middle-earth.gem_notes.covers." + grade.asString())
            .formatted(Formatting.GRAY));
        tooltip.accept(Text.translatable("tooltip.middle-earth.gem_notes.bind").formatted(Formatting.DARK_GRAY));
    }
}
