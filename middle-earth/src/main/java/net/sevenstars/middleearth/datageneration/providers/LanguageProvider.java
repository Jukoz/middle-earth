package net.sevenstars.middleearth.datageneration.providers;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.forge.MetalTypes;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.item.utils.armor.backAttachments.BackAttachmentsME;
import net.sevenstars.middleearth.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LanguageProvider extends net.neoforged.neoforge.common.data.LanguageProvider {

    private final Map<String, String> specialNames = new HashMap<>();
    private final Map<String, String> emittedTranslations = new HashMap<>();
    private final Set<String> manualTranslationKeys = new HashSet<>();

    public LanguageProvider(PackOutput output) {
        super(output, MiddleEarth.MOD_ID, "en_us");

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
        specialNames.put("Elvenkings Halls", "Elvenking's Halls");
        specialNames.put("Nurn", "Núrn");
        specialNames.put("Rhun", "Rhûn");
        specialNames.put("Lorien", "Lórien");
        specialNames.put("Nazgul", "Nazgûl");
        specialNames.put("Capital", "Dale Capital");
        specialNames.put("Kings Guard", "King's Guard");
        specialNames.put("Weaver Sting", "Weaver's Sting");
    }

    @Override
    protected void addTranslations() {
        TranslationEntries.manualEntries.forEach((key, value) -> {
            manualTranslationKeys.add(key);
            add(key, value);
        });

        TranslationEntries.blockEntries.forEach(block -> {
            translateBlock(block);
        });

        TranslationEntries.spawnEggEntries.forEach(spawnEgg -> {
            var path = spawnEgg.getPath();
            if(!path.contains("_spawn_egg"))
                path += "_spawn_egg";

            add("item." + MiddleEarth.MOD_ID + "." + path, generateName(path));
        });

        TranslationEntries.itemEntries.forEach(item -> {
            add(item, generateName(BuiltInRegistries.ITEM.getKey(item).getPath()));
        });

        TranslationEntries.entityEntries.forEach(entityType -> {
            add(entityType, generateName(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).getPath()));
        });

        TranslationEntries.biomeEntries.forEach(name -> {
            add("biome." + MiddleEarth.MOD_ID + "." + name, generateName(name));
        });

        TranslationEntries.bannerPatternEntries.forEach(name -> {
            createBannerTranslation("block", "banner." + name);
        });

        for (MetalTypes metalTypes : MetalTypes.values()){
            createTranslation("tooltip", "liquid_" + metalTypes.getName());
        }

        for (BackAttachmentsME cape : BackAttachmentsME.values()){
            createTranslation("tooltip", cape.getName());
        }

        for (HelmetAttachmentsME hood : HelmetAttachmentsME.values()){
            createTranslation("tooltip", hood.getName());
        }

        for (DispositionType dispositionType : DispositionType.values()){
            createTranslation("disposition", dispositionType.name().toLowerCase());
        }

        TranslationEntries.factionEntries.forEach(faction -> {
            createTranslation("faction", faction);
        });

        TranslationEntries.npcTypeEntries.forEach(npcData -> {
            createNpcDataTranslation("npc_type", npcData);
        });

        TranslationEntries.raceEntries.forEach(race -> {
            createTranslation("race", race);
        });

        TranslationEntries.structureManagerEntries.forEach(structureManagerData -> {
            createTranslation("structure_manager_data", structureManagerData.getId().getPath());
            for(SpawnNestNodeData spawnNest : structureManagerData.getNpcSpawnNest()) {
                createTranslation("structure_nest", spawnNest.getId().getPath());
            }
        });

        TranslationEntries.spawnEntries.forEach(faction -> {
            createTranslation("spawn", faction);
        });

        TranslationEntries.inscriptionEntries.forEach(inscription -> {
            createTranslation("inscription", inscription);
        });

        createBannerTranslation("item", "round_shield");
        createBannerTranslation("item", "heater_shield");
        createBannerTranslation("item", "kite_shield");
    }

    @Override
    public void add(String key, String value) {
        String existing = emittedTranslations.putIfAbsent(key, value);
        if (existing == null) {
            super.add(key, value);
            return;
        }
        if (manualTranslationKeys.contains(key) || existing.equals(value)) {
            return;
        }
        throw new IllegalStateException(
                "Conflicting generated translation key " + key + ": '" + existing + "' vs '" + value + "'"
        );
    }

    public void translateBlock(Block block){
        if (block == null) return;
        add(block, generateName(BuiltInRegistries.BLOCK.getKey(block).getPath()));
    }

    public void createTranslation(String prefix, String suffix){
        String suffixSplit = suffix;
        if (suffix.contains(".")){
            String [] sub = suffix.split("\\.");
            suffixSplit = Arrays.stream(sub).toList().getLast();
        }
        add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix, generateName(suffixSplit));
    }

    public void createNpcDataTranslation(String prefix, String suffix) {
        StringBuilder generatedName = new StringBuilder();
        if (suffix.contains(".")){
            String [] sub = suffix.split("\\.");
            var splitId = Arrays.stream(sub).toList();

            if(splitId.size() == 3) // Removes the faction when it's a subfaction (Longbeards Erebor Soldier -> Erebor Soldier)
            {
                splitId = Arrays.asList(splitId.get(1), splitId.get(2));
            }

            for(String rawName : splitId){
                if(!generatedName.isEmpty() && (!generatedName.toString().endsWith(" ") || !generatedName.toString().endsWith("-")))
                    generatedName.append(" ");

                generatedName.append(generateName(rawName));
            }
        }

        add(prefix + "." + MiddleEarth.MOD_ID + "." + suffix, generatedName.toString());
    }

    public void createBannerTranslation(String prefix, String suffix){
        String baseTranslationKey = prefix + "." + MiddleEarth.MOD_ID + "." + suffix;

        add(baseTranslationKey + ".black", "Black " + generateName(suffix));
        add(baseTranslationKey + ".blue", "Blue " + generateName(suffix));
        add(baseTranslationKey + ".brown", "Brown " + generateName(suffix));
        add(baseTranslationKey + ".cyan", "Cyan " + generateName(suffix));
        add(baseTranslationKey + ".gray", "Gray " + generateName(suffix));
        add(baseTranslationKey + ".green", "Green " + generateName(suffix));
        add(baseTranslationKey + ".light_blue", "Light Blue " + generateName(suffix));
        add(baseTranslationKey + ".light_gray", "light Gray " + generateName(suffix));
        add(baseTranslationKey + ".lime", "Lime " + generateName(suffix));
        add(baseTranslationKey + ".magenta", "Magenta " + generateName(suffix));
        add(baseTranslationKey + ".orange", "Orange " + generateName(suffix));
        add(baseTranslationKey + ".pink", "Pink " + generateName(suffix));
        add(baseTranslationKey + ".purple", "Purple " + generateName(suffix));
        add(baseTranslationKey + ".red", "Red " + generateName(suffix));
        add(baseTranslationKey + ".white", "White " + generateName(suffix));
        add(baseTranslationKey + ".yellow", "yellow " + generateName(suffix));
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

        result = result.replace(" Of ", " of ");
        result = result.replace(" The ", " the ");

        return result;
    }

}
