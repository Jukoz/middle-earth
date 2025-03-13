package net.sevenstars.middleearth.resources.datas.races.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.races.data.npctextures.NpcTextureType;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RaceTextureData {
    HashMap<NpcTextureDataCategory, List<NpcTextureDataPreset>> presetsByCategory;

    public RaceTextureData(NbtCompound compound) {
        if(compound == null) return;
        presetsByCategory = new HashMap<>();

        for(NpcTextureDataCategory category : NpcTextureDataCategory.values()){
            NbtList nbtListPresets = compound.getList(category.name(), NbtElement.COMPOUND_TYPE);
            if(nbtListPresets != null){
                List<NpcTextureDataPreset> dataPresetList = new ArrayList<>();
                for(int i = 0; i < nbtListPresets.size(); i++){
                    NpcTextureDataPreset fetchedPreset = new NpcTextureDataPreset(nbtListPresets.getCompound(i));
                    dataPresetList.add(fetchedPreset);
                }
                presetsByCategory.put(category, dataPresetList);
            }
        }
    }

    public RaceTextureData(HashMap<NpcTextureDataCategory, List<NpcTextureDataPreset>> sourceDatas) {
        presetsByCategory = sourceDatas;
    }

    public static Identifier buildId(Identifier pattern, Identifier material) {
        return IdentifierUtil.create(pattern.getPath() + "_" + material.getPath());
    }

    public static Identifier buildAddonId(Identifier pattern, Identifier material) {
        return IdentifierUtil.create(pattern.getPath() + "_addon_" + material.getPath());
    }


    public NbtCompound getNbt() {
        NbtCompound newNbt = new NbtCompound();
        for(NpcTextureDataCategory category : presetsByCategory.keySet()){
            List<NpcTextureDataPreset> presets = presetsByCategory.get(category);
            if(presets != null && !presets.isEmpty()){
                NbtList newNbtList = new NbtList();
                for (NpcTextureDataPreset preset : presets) {
                    newNbtList.add(preset.getNbt());
                }
                newNbt.put(category.name(), newNbtList);
            }
        }
        return newNbt;
    }

    public static Identifier getRawMaterial(Identity identity, NpcTextureType npcTextureType) {
        List<String> materials = identity.preset.getMaterials(npcTextureType);
        if(materials == null)
            return null;
        Random random = new Random();
        int materialIndex = random.nextInt(materials.size());
        return Identifier.of(MiddleEarth.MOD_ID, materials.get(materialIndex));
    }

    public static Identifier getRawPattern(Identity identity, NpcTextureType npcTextureType) {
        List<String> patterns = identity.preset.getPatterns(npcTextureType);
        if(patterns.isEmpty())
            return null;
        Random random = new Random();
        String value = patterns.get(random.nextInt(patterns.size()));
        if(value.equals(NpcTextureDataPreset.EMPTY_VALUE_KEY))
            return null;
        return Identifier.of(MiddleEarth.MOD_ID, value);
    }

    public static Identifier getTextureWithMaterial(Identity identity, NpcTextureType npcTextureType) {
        List<String> patterns = identity.preset.getPatterns(npcTextureType);
        List<String> materials = identity.preset.getMaterials(npcTextureType);
        if(patterns.isEmpty() || materials == null)
            return null;

        Random random = new Random();
        int patternIndex = random.nextInt(patterns.size());
        int materialIndex = random.nextInt(materials.size());
        return  Identifier.of(MiddleEarth.MOD_ID, patterns.get(patternIndex) + "_" + materials.get(materialIndex));
    }

    public Identifier getTextureWithMaterial(Identity identity, NpcTextureType npcTextureType, Identifier material) {
        List<String> patterns = identity.preset.getPatterns(npcTextureType);
        Random random = new Random();
        int patternIndex = random.nextInt(patterns.size());
        return Identifier.of(MiddleEarth.MOD_ID, patterns.get(patternIndex) + "_" + material.getPath());
    }


    public Boolean haveEmissiveEyes(Identity identity) {
        return identity.preset.haveEmissiveEyes();
    }

    public record Identity(NpcTextureDataCategory category, NpcTextureDataPreset preset){
        public static Identity create(RaceTextureData data, NpcTextureDataCategory npcTextureDataCategory){
            if(!data.presetsByCategory.containsKey(npcTextureDataCategory))
                return null;

            // TODO : get the right preset based on weight
            List<NpcTextureDataPreset> presets = data.presetsByCategory.get(npcTextureDataCategory);
            Random random = new Random();
            return new Identity(npcTextureDataCategory, presets.get(random.nextInt(presets.size())));
        }
    }
}
