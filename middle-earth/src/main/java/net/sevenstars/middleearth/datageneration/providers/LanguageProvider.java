package net.sevenstars.middleearth.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.sevenstars.middleearth.resources.datas.Disposition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class LanguageProvider extends FabricLanguageProvider {

    private final Map<String, String> specialNames = new HashMap<>();

    public LanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);

        specialNames.put("Izheraban", "Izhêr'Aban");
        specialNames.put("Khagalaban", "Khagal'Aban");
        specialNames.put("Burzulaban", "Burzul'Aban");
        specialNames.put("Nurgon", "Núrgon");
        specialNames.put("Zigilaban", "Zigil'Aban");
        specialNames.put("Simbelmyne", "Simbelmynë");
        specialNames.put("Numenorean", "Númenórean");
        specialNames.put("Uruk Hai", "Uruk-Hai");
        specialNames.put("Burzum Steel", "Búrzum-Steel");
        specialNames.put("Burzum", "Búrzum");
        specialNames.put("Edhel Steel", "Edhel-Steel");
        specialNames.put("Khazad Steel", "Khazâd-Steel");
        specialNames.put("Druwaith", "Drúwaith");
        specialNames.put("Lothlorien", "Lothlórien");
        specialNames.put("Nurn", "Núrn");
        specialNames.put("Rhun", "Rhûn");
        specialNames.put("Lorien", "Lórien");
        specialNames.put("Nazgul", "Nazgûl");
        specialNames.put("Capital", "Dale Capital");
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

        TranslationEntries.bannerPatternEntries.forEach(name -> {
            createBannerTranslation(translationBuilder, "block", "banner." + name);
        });

        for (MetalTypes metalTypes : MetalTypes.values()){
            createTranslation(translationBuilder, "tooltip", "liquid_" + metalTypes.getName());
        }

        for (BackAttachmentsME cape : BackAttachmentsME.values()){
            createTranslation(translationBuilder, "tooltip", cape.getName());
        }

        for (HelmetAttachmentsME hood : HelmetAttachmentsME.values()){
            createTranslation(translationBuilder, "tooltip", hood.getName());
        }

        for (Disposition disposition : Disposition.values()){
            createTranslation(translationBuilder, "disposition", disposition.name().toLowerCase());
        }

        TranslationEntries.factionEntries.forEach(faction -> {
            createTranslation(translationBuilder, "faction", faction);
        });

        TranslationEntries.spawnEntries.forEach(faction -> {
            createTranslation(translationBuilder, "spawn", faction);
        });

        TranslationEntries.manualEntries.forEach(translationBuilder::add);

        createBannerTranslation(translationBuilder, "item", "round_shield");
        createBannerTranslation(translationBuilder, "item", "heater_shield");
        createBannerTranslation(translationBuilder, "item", "kite_shield");
    }

    public void translateBlock(TranslationBuilder translationBuilder, Block block){
        if (block == null) return;
        translationBuilder.add(block, generateName(Registries.BLOCK.getId(block).getPath()));
        if (block.asItem() == null) return;
        translationBuilder.add(block.asItem(), generateName(Registries.ITEM.getId(block.asItem()).getPath()));
    }

    public void createTranslation(TranslationBuilder translationBuilder, String prefix, String suffix){
        String suffixSplit = suffix;
        if (suffix.contains(".")){
            String [] sub = suffix.split("\\.");
            suffixSplit = Arrays.stream(sub).toList().getLast();
        }
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix, generateName(suffixSplit));
    }

    public void createBannerTranslation(TranslationBuilder translationBuilder, String prefix, String suffix){
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix + ".black", "Black " + generateName(suffix));
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix + ".blue", "Blue " + generateName(suffix));
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix + ".brown", "Brown " + generateName(suffix));
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix + ".cyan", "Cyan " + generateName(suffix));
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix + ".gray", "Gray " + generateName(suffix));
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix + ".green", "Green " + generateName(suffix));
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix + ".light_blue", "Light Blue " + generateName(suffix));
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix + ".light_gray", "light Gray " + generateName(suffix));
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix + ".lime", "Lime " + generateName(suffix));
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix + ".magenta", "Magenta " + generateName(suffix));
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix + ".orange", "Orange " + generateName(suffix));
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix + ".pink", "Pink " + generateName(suffix));
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix + ".purple", "Purple " + generateName(suffix));
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix + ".red", "Red " + generateName(suffix));
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix + ".white", "White " + generateName(suffix));
        translationBuilder.add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix + ".yellow", "yellow " + generateName(suffix));
    }

    public String generateName(String registryName) {

        String[] splitName = registryName.split("[_.]");

        for (int i = 0; i < splitName.length; i++) {
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