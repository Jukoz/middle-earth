package net.sevenstars.middleearth.recipe.lapidary;

import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Language;
import net.sevenstars.middleearth.item.DataComponentTypesME;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.resources.datas.gems.GemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Gem Studies: what a study teaches about a gem, the notes it is written into, and the Player Book that keeps
 * them (component {@link DataComponentTypesME#GEM_STUDIES}, bound in by {@link GemStudiesBookRecipe}). The book
 * is a second, in-world record next to the Lapidary advancements.
 */
public final class GemStudies {
    private static final String[] INFO = {"host", "depth", "region", "lore"};

    private GemStudies() {
    }

    public static boolean isPlayerBook(ItemStack stack) {
        return stack.isOf(ResourceItemsME.PLAYER_BOOK);
    }

    /** Notes for a study: the grade's notes item, made of the gem. */
    public static ItemStack notes(RegistryKey<GemType> gem, StudyGrade grade) {
        ItemStack stack = new ItemStack(ResourceItemsME.GEM_NOTES.get(grade));
        stack.set(DataComponentTypesME.GEM_TYPE, gem);
        return stack;
    }

    /**
     * What a grade teaches about a gem, one line each: host rock, then depth, region and lore.
     * Gems without a lore line simply have one line fewer at Masterful.
     */
    public static List<Text> infoLines(RegistryKey<GemType> gem, StudyGrade grade) {
        List<Text> lines = new ArrayList<>();
        String prefix = "gem_info." + gem.getValue().getNamespace() + "." + gem.getValue().getPath() + ".";
        for (int i = 0; i <= grade.ordinal(); i++) {
            String key = prefix + INFO[i];
            if (Language.getInstance().hasTranslation(key)) {
                lines.add(i == 3 ? Text.translatable(key).formatted(Formatting.DARK_PURPLE, Formatting.ITALIC)
                        : Text.translatable(key).formatted(Formatting.GRAY));
            }
        }
        return lines;
    }
}
