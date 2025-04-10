package net.sevenstars.middleearth.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class LanguageProvider extends FabricLanguageProvider {

    private Map<String, String> specialNames = new HashMap<>();

    public LanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);

        specialNames.put("Izheraban", "Izhêr'Aban");
        specialNames.put("Nurgon", "Núrgon");
        specialNames.put("Zigilaban", "Zigil'Aban");
        specialNames.put("Numenorean", "Númenórean");
        specialNames.put("Uruk Hai", "Uruk-Hai");
        specialNames.put("Burzum Steel", "Búrzum-Steel");
        specialNames.put("Edhel Steel", "Edhel-Steel");
        specialNames.put("Khazad Steel", "Khazâd-Steel");
        specialNames.put("Lothlorien", "Lothlórien");
        specialNames.put("Nurn", "Núrn");
        specialNames.put("Rhun", "Rhûn");
        specialNames.put("Lorien", "Lórien");
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {

        TranslationEntries.blockEntries.forEach(block -> {
            translateBlock(translationBuilder, block);
        });

        TranslationEntries.itemEntries.forEach(item -> {
            translationBuilder.add(item, generateName(Registries.ITEM.getId(item).getPath()));
        });

        TranslationEntries.entityEntries.forEach(entityType -> {
            translationBuilder.add(entityType, generateName(Registries.ENTITY_TYPE.getId(entityType).getPath()));
        });

        TranslationEntries.biomeEntries.forEach(name -> {
            translationBuilder.add("biome." + MiddleEarth.MOD_ID + "." + name, generateName(name));
        });

        TranslationEntries.manualEntries.forEach(translationBuilder::add);

    }

    public void translateBlock(TranslationBuilder translationBuilder, Block block){
        if (block == null) return;
        translationBuilder.add(block, generateName(Registries.BLOCK.getId(block).getPath()));
        if (block.asItem() == null) return;
        translationBuilder.add(block.asItem(), generateName(Registries.ITEM.getId(block.asItem()).getPath()));
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