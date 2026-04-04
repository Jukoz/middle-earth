package net.sevenstars.middleearth.resources.datas.texture_presets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.common.CharacterMaterialTypes;
import net.sevenstars.middleearth.resources.datas.common.CharacterPatternTypes;
import net.sevenstars.middleearth.resources.datas.common.EntityCategories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class TexturePresetDatas {
    public static final Codec<TexturePresetDatas> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        NbtCompound.CODEC.fieldOf("categories").forGetter(TexturePresetDatas::getNbt)
    ).apply(instance, TexturePresetDatas::new));

    HashMap<EntityCategories, List<TexturePresetData>> presetsByCategory;

    public TexturePresetDatas(NbtCompound categories) {
        if(categories == null) return;
        presetsByCategory = new HashMap<>();

        for(EntityCategories category : EntityCategories.values()){
            var optList = categories.getList(category.name());
            if(optList.isEmpty()) continue;;
            NbtList nbtListPresets = optList.get();
            List<TexturePresetData> dataPresetList = new ArrayList<>();
            for(int i = 0; i < nbtListPresets.size(); i++){
                TexturePresetData fetchedPreset = new TexturePresetData(nbtListPresets.getCompound(i).get());
                dataPresetList.add(fetchedPreset);
            }
            presetsByCategory.put(category, dataPresetList);
        }
    }

    public TexturePresetDatas(HashMap<EntityCategories, List<TexturePresetData>> sourceDatas) {
        presetsByCategory = sourceDatas;
    }

    public static Identifier buildId(Identifier pattern, Identifier material) {
        if(pattern == null || material == null)
            return null;
        return MiddleEarth.of(pattern.getPath() + "_" + material.getPath());
    }

    public static Identifier buildAddonId(Identifier pattern, Identifier material) {
        return MiddleEarth.of(pattern.getPath() + "_addon_" + material.getPath());
    }

    public static ClothingData getClothing(Identity textureIdentity) {
        return textureIdentity.preset.getClothingData();
    }


    public NbtCompound getNbt() {
        NbtCompound newNbt = new NbtCompound();
        for(EntityCategories category : presetsByCategory.keySet()){
            List<TexturePresetData> presets = presetsByCategory.get(category);
            if(presets != null && !presets.isEmpty()){
                NbtList newNbtList = new NbtList();
                for (TexturePresetData preset : presets) {
                    newNbtList.add(preset.getNbt());
                }
                newNbt.put(category.name(), newNbtList);
            }
        }
        return newNbt;
    }

    public static Identifier getRawMaterial(Identity identity, CharacterMaterialTypes materialType) {
        var weightedItem = identity.preset.getMaterials(materialType).getRandom();
        if(weightedItem == null)
            return null;
        return weightedItem.getItem();
    }

    public static Identifier getRawPattern(Identity identity, CharacterPatternTypes patternType) {
        var weightedItem = identity.preset.getPatterns(patternType).getRandom();
        if(weightedItem == null)
            return null;
        if(weightedItem.isSame(TexturePresetData.EMPTY_VALUE_KEY.getItem()))
            return null;
        return weightedItem.getItem();
    }

    public Boolean haveEmissiveEyes(Identity identity) {
        return identity.preset.haveEmissiveEyes();
    }

    public EntityCategories getRandomCategory() {
        Random random = new Random();
        boolean containsMale = this.presetsByCategory.containsKey(EntityCategories.MALE);
        boolean containsFemale = this.presetsByCategory.containsKey(EntityCategories.FEMALE);
        // Shared is not a valid sex
        if(containsMale && containsFemale)
            return (random.nextBoolean()) ? EntityCategories.MALE : EntityCategories.FEMALE;
        else if(containsMale)
            return EntityCategories.MALE;
        else if(containsFemale)
            return EntityCategories.FEMALE;

        return EntityCategories.MALE;
    }

    public record Identity(EntityCategories category, TexturePresetData preset){
        public static Identity create(TexturePresetDatas data, EntityCategories entityCategories){
            if(!data.presetsByCategory.containsKey(entityCategories))
                return null;

            // TODO : get the right preset based on weight
            List<TexturePresetData> presets = data.presetsByCategory.get(entityCategories);
            presets = feedSharedToPresets(data, presets);

            Random random = new Random();
            return new Identity(entityCategories, presets.get(random.nextInt(presets.size())));
        }

        public static Identity create(TexturePresetDatas data){
            EntityCategories entityCategories = data.getRandomCategory();
            if(!data.presetsByCategory.containsKey(entityCategories))
                return null;

            // TODO : get the right preset based on weight
            List<TexturePresetData> presets = data.presetsByCategory.get(entityCategories);
            presets = feedSharedToPresets(data, presets);

            Random random = new Random();
            return new Identity(entityCategories, presets.get(random.nextInt(presets.size())));
        }

        private static List<TexturePresetData> feedSharedToPresets(TexturePresetDatas data, List<TexturePresetData> presets) {
            if(data.presetsByCategory.containsKey(EntityCategories.SHARED)){
                var shared = data.presetsByCategory.get(EntityCategories.SHARED).getFirst();
                if(shared != null){
                    for (TexturePresetData preset : presets) {
                        preset.withMaterials(CharacterMaterialTypes.SKIN, shared.getMaterials(CharacterMaterialTypes.SKIN));
                        preset.withPatterns(CharacterPatternTypes.BODY, shared.getPatterns(CharacterPatternTypes.BODY));
                        preset.withPatterns(CharacterPatternTypes.HEAD, shared.getPatterns(CharacterPatternTypes.HEAD));
                        preset.withPatterns(CharacterPatternTypes.EAR, shared.getPatterns(CharacterPatternTypes.EAR));
                        preset.withPatterns(CharacterPatternTypes.NOSE, shared.getPatterns(CharacterPatternTypes.NOSE));
                        preset.withPatterns(CharacterPatternTypes.SCAR, shared.getPatterns(CharacterPatternTypes.SCAR));
                        preset.withPatterns(CharacterPatternTypes.FEET, shared.getPatterns(CharacterPatternTypes.FEET));

                        preset.withMaterials(CharacterMaterialTypes.HAIR, shared.getMaterials(CharacterMaterialTypes.HAIR));
                        preset.withPatterns(CharacterPatternTypes.HAIR, shared.getPatterns(CharacterPatternTypes.HAIR));
                        preset.withPatterns(CharacterPatternTypes.BEARD, shared.getPatterns(CharacterPatternTypes.BEARD));
                        preset.withPatterns(CharacterPatternTypes.EYEBROW, shared.getPatterns(CharacterPatternTypes.EYEBROW));
                        preset.withEmissiveEyes(shared.haveEmissiveEyes());

                        preset.withMaterials(CharacterMaterialTypes.EYE, shared.getMaterials(CharacterMaterialTypes.EYE));
                        preset.withPatterns(CharacterPatternTypes.EYE, shared.getPatterns(CharacterPatternTypes.EYE));
                    }
                }
            }
            return presets;
        }
    }
}
