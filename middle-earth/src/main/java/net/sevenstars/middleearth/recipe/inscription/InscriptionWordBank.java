package net.sevenstars.middleearth.recipe.inscription;

import com.google.common.collect.ArrayListMultimap;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.sevenstars.middleearth.item.ResourceItemsME;

public class InscriptionWordBank {
    public static final ArrayListMultimap<Item, String> wordBank = ArrayListMultimap.create();

    public static void addWordsToBank(){
        wordBank.put(null, "resilient");
        wordBank.put(null, "blessing");

        wordBank.put(null, "warded");
        wordBank.put(null, "tidal");
        wordBank.put(null, "traveller");

        wordBank.put(null, "edge");
        wordBank.put(null, "cutter");
        wordBank.put(null, "piercer");
        wordBank.put(null, "core");

        wordBank.put(null, "giant");
        wordBank.put(null, "collector");

        wordBank.put(null, "draw");
        wordBank.put(null, "point");

        wordBank.put(ResourceItemsME.RUBY, "fierce");
        wordBank.put(ResourceItemsME.RUBY, "forceful");
        wordBank.put(ResourceItemsME.RUBY, "noiseless");
        wordBank.put(ResourceItemsME.RUBY, "bane");

        wordBank.put(ResourceItemsME.SAPPHIRE, "pierce");
        wordBank.put(ResourceItemsME.SAPPHIRE, "flame");
        wordBank.put(ResourceItemsME.SAPPHIRE, "sturdy");

        wordBank.put(Items.EMERALD, "broad");
        wordBank.put(Items.EMERALD, "careful");
        wordBank.put(Items.EMERALD, "long");
        wordBank.put(Items.EMERALD, "gifted");

        wordBank.put(ResourceItemsME.ADAMANT, "swift");
    }
}
