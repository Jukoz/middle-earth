package net.sevenstars.of_beasts_and_wild_things.datageneration.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.core.registries.BuiltInRegistries;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.datageneration.content.TranslationEntries;

import java.util.HashMap;
import java.util.Map;
public class LanguageProvider extends net.neoforged.neoforge.common.data.LanguageProvider {

    private Map<String, String> specialNames = new HashMap<>();

    public LanguageProvider(PackOutput output) {
        super(output, OfBeastsAndWildThings.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        TranslationEntries.blockEntries.forEach(block -> {
            add(block, generateName(BuiltInRegistries.BLOCK.getKey(block).getPath()));
        });

        TranslationEntries.itemEntries.forEach(item -> {
            add(item, generateName(BuiltInRegistries.ITEM.getKey(item).getPath()));
        });

        TranslationEntries.entityEntries.forEach(entityType -> {
            add(entityType, generateName(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).getPath()));
        });

        TranslationEntries.manualEntries.forEach(this::add);
    }

    public String generateName(String registryName) {

        String[] splitName = registryName.split("_");
        for(int i = 0; i < splitName.length; i++) {

            char[] characters = splitName[i].toCharArray();
            characters[0] = Character.toUpperCase(characters[0]);
            splitName[i] = new String(characters);
        }
        String result = String.join(" ", splitName);
        for (Map.Entry<String, String> map : this.specialNames.entrySet()){
            if (result.contains(map.getKey())){
                result = result.replaceAll(map.getKey(), map.getValue());
            }
        }
        return result;
    }

}
