package net.sevenstars.middleearth.datageneration.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.item.ModWeaponItems;
import net.sevenstars.middleearth.item.utils.ModItemGroups;

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
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {

        TranslationEntries.blocks.forEach(block -> {
            translateBlock(translationBuilder, block);
        });

        TranslationEntries.items.forEach(item -> {
            translationBuilder.add(item, generateName(Registries.ITEM.getId(item).getPath()));
        });

        TranslationEntries.entities.forEach(entityType -> {
            translationBuilder.add(entityType, generateName(Registries.ENTITY_TYPE.getId(entityType).getPath()));
        });

        translationBuilder.add("itemGroup.me.stone_blocks", "Middle-earth Stone Blocks");
        translationBuilder.add("itemGroup.me.wood_blocks", "Middle-earth Wood Blocks");
        translationBuilder.add("itemGroup.me.misc_blocks", "Middle-earth Misc Blocks");
        translationBuilder.add("itemGroup.me.nature_blocks", "Middle-earth Nature Blocks");
        translationBuilder.add("itemGroup.me.decorative_blocks", "Middle-earth Decorative Blocks");
        translationBuilder.add("itemGroup.me.food_items", "Middle-earth Food");
        translationBuilder.add("itemGroup.me.weapon_items", "Middle-earth Weapons");
        translationBuilder.add("itemGroup.me.equipment_items", "Middle-earth Equipment");
        translationBuilder.add("itemGroup.me.tool_items", "Middle-earth Tools");
        translationBuilder.add("itemGroup.me.resource_items", "Middle-earth Resources");
        translationBuilder.add("itemGroup.me.spawn_egg_items", "Middle-earth Spawn Eggs");
        
        translationBuilder.add(ModWeaponItems.AEGLOS, "your mom");
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