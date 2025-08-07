package net.sevenstars.of_beasts_and_wild_things.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.of_beasts_and_wild_things.datageneration.content.TranslationEntries;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class LanguageProvider extends FabricLanguageProvider {

    private Map<String, String> specialNames = new HashMap<>();

    public LanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {

        TranslationEntries.blockEntries.forEach(block -> {
            translationBuilder.add(block, generateName(Registries.BLOCK.getId(block).getPath()));
        });

        TranslationEntries.itemEntries.forEach(item -> {
            translationBuilder.add(item, generateName(Registries.ITEM.getId(item).getPath()));
        });

        TranslationEntries.entityEntries.forEach(entityType -> {
            translationBuilder.add(entityType, generateName(Registries.ENTITY_TYPE.getId(entityType).getPath()));
        });

        TranslationEntries.manualEntries.forEach(translationBuilder::add);
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